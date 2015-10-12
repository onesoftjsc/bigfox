//
//  EncryptManager.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "EncryptManager.h"
#import "ConnectionManager.h"

@implementation EncryptManager
+ (NSData*) crypt:(NSData *)data {
    if ([data length] <= 24) {
        return data;
    }else{
        char* cData = [data bytes];
        int length = [data length];
        int validationCode = [[ConnectionManager getInstance]getValidationCode];
        for (int i = 24; i < length; i++) {
            cData[i] = (char) ((cData[i]^ validationCode) & 0x00ff);
        }
        return  [NSData dataWithBytes:cData length:length];
    }
    
}
@end
