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
    int random = arc4random_uniform(32);
    return [NSString stringWithFormat:@"%d", random];
}


+ (NSString *)genRandStringLength :(int)len {
    static NSString *letters = @"qrtyuiopasdfghjklzxcvbnm0123456789";
    NSMutableString *randomString = [NSMutableString stringWithCapacity: 32];
    for (int i=0; i < 32; i++) {
        [randomString appendFormat: @"%C", [letters characterAtIndex: arc4random() % [letters length]]];
    }
    return randomString;
}
@end
