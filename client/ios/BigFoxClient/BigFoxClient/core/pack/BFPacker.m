//
//  BFPacker.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/25/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "BFPacker.h"
#import "BaseMessage.h"

static NSMutableDictionary* mapChannelToByteBuffer = nil ;
@implementation BFPacker

+ (void) initialize {
    if (mapChannelToByteBuffer == nil) {
        mapChannelToByteBuffer = [[NSMutableDictionary alloc]init] ;
    }
}

+ (NSData*) pack:(NSStream*) is : (NSData *)data {
    NSData * dataBuf = [mapChannelToByteBuffer objectForKey:is];
    if (dataBuf == nil) {
        dataBuf = [[NSData alloc] init];
    }
    int length = [data length];
    char* cData = [data bytes];
    int lenResult = dataBuf.length + length;
    char* result = (char*) malloc(dataBuf.length + length);
    if (dataBuf.length > 0) {
        char* buf = [dataBuf bytes];
        for (int i= 0; i< dataBuf.length ; i ++) {
            result[i] = buf[i];
        }
    }
    
    for (int i = dataBuf.length; i < lenResult; i++) {
        result[i] = cData[i - dataBuf.length];
    }
    int status = (int) (((cData[16] & 0xff) << 24) | ((cData[17] & 0xff) << 16) | ((cData[18] & 0xff) << 8) | ((cData[19] & 0xff)));
    if ((status & STATUS_CONTINUE) == 0) {
        result[0] = (char) ((lenResult >> 24) & 0x00ff);
        result[1] = (char) ((lenResult >> 16) & 0x00ff);
        result[2] = (char) ((lenResult >> 8) & 0x00ff);
        result[3] = (char) ((lenResult) & 0x00ff);
        if (lenResult > 10000){
            int a = 0;
        }
        
        [mapChannelToByteBuffer removeObjectForKey:is];
        return [NSData dataWithBytes:result length:lenResult];
    }else{
        [mapChannelToByteBuffer setObject:[NSData dataWithBytes:result length:lenResult] forKey:is];
        return nil;
    }
}

+ (NSArray*) slide : (NSData*) data {
    NSMutableArray* result = [[NSMutableArray alloc] init];
    if ([data length] <= MAX_LENGTH) {
        [result addObject:data];
    }else{
        int length = [data length];
        char* cData = [data bytes];
        char* header = (char*)malloc(24);
        char* body = (char*) malloc(length - 24);
        for (int i= 0; i < length; i++) {
            if (i < 24) {
                header[i] = cData[i];
            }else{
                body[i - 24] = cData[i];
            }
        }
        int lengthBody = length - 24;
        for (int j = 0; j < lengthBody / MAX_LENGTH; j++) {
            int lenData = MAX_LENGTH + 24;
            char* iData = (char*)malloc(lenData);
            for (int k = 0; k < MAX_LENGTH; k ++) {
                if (k < 24) {
                    iData[k] = header[k];
                }else{
                    iData[k] = body[(j * MAX_LENGTH) + k - 24];
                }
            }
            [self normalData:STATUS_CONTINUE :iData : (MAX_LENGTH + 24)];
            [result addObject:[NSData dataWithBytes:iData length:lenData]];
        }
        
        if(length > MAX_LENGTH + (length / MAX_LENGTH)) {
            int len = lengthBody - MAX_LENGTH * (lengthBody / MAX_LENGTH);
            int kLenData = len + 24;
            char* kData = (char*) malloc(kLenData);
            for (int h = 0; h < len; h++) {
                if (h < 24) {
                    kData[h] = header[h];
                }else{
                    kData[h] = body[(lengthBody - len) + h - 24];
                }
            }
            [self normalData:0 :kData :kLenData];
           [result addObject:[NSData dataWithBytes:kData length:kLenData]];
        }
    }
    return result;
}

+ (void) normalData : (int) status : (char*) data : (int)length {
    data[0] = (char) ((length >> 24) & 0x00ff);
    data[1] = (char) ((length >> 16) & 0x00ff);
    data[2] = (char) ((length >> 8) & 0x00ff);
    data[3] = (char) ((length) & 0x00ff);
    
    data[16] = (char) ((status >> 24) & 0x00ff);
    data[17] = (char) ((status >> 16) & 0x00ff);
    data[18] = (char) ((status >> 8) & 0x00ff);
    data[19] = (char) ((status) & 0x00ff);
}

@end
