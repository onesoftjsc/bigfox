//
//  BFUtils.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "BFUtils.h"

@implementation BFUtils
+ (long) getCurrentTime {
    return (long)(NSTimeInterval)([[NSDate date] timeIntervalSince1970]);
}

+ (NSString*) genRandomString:(int)length {
    uint8_t randomBytes[32];
    int bi = SecRandomCopyBytes(kSecRandomDefault, 32, &randomBytes);
    return [NSString stringWithFormat:@"%d", bi];
}
@end
