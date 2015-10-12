//
//  BFZip.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "BFZip.h"
#include <zlib.h>
#import "BaseMessage.h"

@implementation BFZip
- (NSData*)compress:(NSData *)data{
    if ([data length] <= 1200) {
        return  data;
    }
    else {
        char* cData = [data bytes];
        char* uData = malloc([data length] - 24);
        for (int i = 0; i < [data length]- 24; i++) {
            uData[i] = cData[i+24];
        }
        @try {
            z_stream strm;
            strm.zalloc = Z_NULL;
            strm.zfree = Z_NULL;
            strm.opaque = Z_NULL;
            if (Z_OK != deflateInit(&strm, Z_DEFAULT_COMPRESSION))
            {
                [NSException raise: NSInternalInconsistencyException
                            format: @"deflateInit failed"];
            }
            
            NSMutableData *compressed = [NSMutableData dataWithLength: deflateBound(&strm, [data length] - 24)];
            strm.next_out = [compressed mutableBytes];
            strm.avail_out = [compressed length];
            strm.next_in = (void *)uData;
            strm.avail_in = ([data length] - 24);
            
            while (deflate(&strm, Z_FINISH) != Z_STREAM_END)
            {
                // deflate should return Z_STREAM_END on the first call
                [compressed setLength: [compressed length] * 1.5];
                strm.next_out = [compressed mutableBytes] + strm.total_out;
                strm.avail_out = [compressed length] - strm.total_out;
            }
            
            [compressed setLength: strm.total_out];
            
            deflateEnd(&strm);
            int lengthCompressed = [compressed length];
            if (lengthCompressed < [data length] - 24) {
                char* compressData = [compressed bytes];
                int lengthResult = lengthCompressed + 24;
                char* resultData = malloc(lengthResult);
                for (int i = 0; i < lengthResult; i++) {
                    if (i < 24) {
                        resultData[i] = cData[i];
                    }else{
                        resultData[i] = compressData[i - 24];
                    }
                }
                
                resultData[0] = (char) ((lengthResult >> 24) & 0x00ff);
                resultData[1] = (char) ((lengthResult >> 16) & 0x00ff);
                resultData[2] = (char) ((lengthResult >> 8) & 0x00ff);
                resultData[3] = (char) ((lengthResult) & 0x00ff);
                resultData[19] = (char) (resultData[19] | STATUS_ZIP);
                return [NSData dataWithBytes:resultData length:lengthResult];
            } else{
                return  data;
            }
        }
        @catch (NSException *exception) {
            return  data;
        }
    }
}

-(NSData*) decompress:(NSData *)data {
    if ([data length] < 24) {
        return  nil;
    }
    int length = [data length];
    char* cData = [data bytes];
    if ((cData[19] & STATUS_ZIP) != STATUS_ZIP) {
        return data;
    }else {
        @try {
            char* uData = malloc(length - 24);
            for (int i = 0; i < length - 24; i++) {
                uData[i] = cData[i + 24];
            }
            z_stream strm;
            strm.zalloc = Z_NULL;
            strm.zfree = Z_NULL;
            strm.opaque = Z_NULL;
            if (Z_OK != inflateInit(&strm))
            {
                [NSException raise: NSInternalInconsistencyException
                            format: @"deflateInit failed"];
            }
            
            NSMutableData *decompressed = [NSMutableData dataWithLength: (length - 24)*2.5];
            strm.next_out = [decompressed mutableBytes];
            strm.avail_out = [decompressed length];
            strm.next_in = (void *)uData;
            strm.avail_in = length - 24;
            
            while (inflate(&strm, Z_FINISH) != Z_STREAM_END)
            {
                // inflate should return Z_STREAM_END on the first call
                [decompressed setLength: [decompressed length] * 1.5];
                strm.next_out = [decompressed mutableBytes] + strm.total_out;
                strm.avail_out = [decompressed length] - strm.total_out;
            }
            
            [decompressed setLength: strm.total_out];
            
            inflateEnd(&strm);
            int lengthDecompressed = [decompressed length];
            char* decompressedData = [decompressed bytes];
            int lengthResult = lengthDecompressed + 24;
            char* resultData = malloc(lengthResult);
            for (int i = 0; i < lengthResult; i++) {
                if(i < 24) {
                    resultData[i] = cData[i];
                }else{
                    resultData[i] = decompressedData[i - 24];
                }
            }
            
            resultData[0] = (char) ((lengthResult >> 24) & 0x00ff);
            resultData[1] = (char) ((lengthResult >> 16) & 0x00ff);
            resultData[2] = (char) ((lengthResult >> 8) & 0x00ff);
            resultData[3] = (char) ((lengthResult) & 0x00ff);
            
            return [NSData dataWithBytes:resultData length:lengthResult];
        }
        @catch (NSException *exception) {
            return data;
        }
    }
}
@end
