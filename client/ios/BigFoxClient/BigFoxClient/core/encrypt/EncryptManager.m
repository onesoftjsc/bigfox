//
//  EncryptManager.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "EncryptManager.h"

@implementation EncryptManager
+ (char*) crypt:(char *)data :(int)length {
    if (length <= 24) {
        return data;
    }else{
        int validationCode = 0 ; // ConnectionManager getInstance].validationCode;
        for (int i = 24; i < length; i++) {
            data[i] = (char) ((data[i]^ validationCode) & 0x00ff);
        }
        return  data;
    }
    
}
@end
