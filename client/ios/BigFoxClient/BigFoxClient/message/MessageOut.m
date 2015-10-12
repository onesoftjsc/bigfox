//
//  MessageOut.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/14/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "MessageOut.h"
#import "BFDataOutputStream.h"
#import "BigFoxUtils.h"
#import "BFLogger.h"
@implementation MessageOut {
}

- (NSData*) toBytes {
    @try {
        BFDataOutputStream* out = [[BFDataOutputStream alloc]init];
        [out writeInt:0];
        [out writeInt:self.tag];
        [out writeInt:[self mSequence]];
        [out writeInt:[self sSequence]];
        [out writeInt:[self getStatus]];
        [out writeInt:[self checkSum]];
        [BigFoxUtils write:self :out];
        NSData* data = [out toByteArray];
        self.length = [data length];
        int32_t lengData = self.length;
        char* dataBytes = [data bytes];
        dataBytes[0] = (char) ((lengData >> 24) & 0x00ff);
        dataBytes[1] = (char) ((lengData >> 16) & 0x00ff);
        dataBytes[2] = (char) ((lengData >> 8) & 0x00ff);
        dataBytes[3] = (char) ((lengData) & 0x00ff);
        return [NSData dataWithBytes:dataBytes length:[data length]];
    }
    @catch (NSException *exception) {
        return nil;
    }
}

-(MessageOut*) clone {
    return self;
}
@end
