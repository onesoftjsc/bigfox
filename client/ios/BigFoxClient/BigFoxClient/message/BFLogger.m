//
//  BFLogger.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "BFLogger.h"
#import "BigFoxUtils.h"

@implementation BFLogger
static BFLogger* _instance;
+ (BFLogger*) getInstance {
    if (_instance == nil) {
        _instance = [[BFLogger alloc] init];
    }
    return _instance;
}

- (void) log : (NSString*) str {
    NSLog(str);
}
- (void) info:(NSObject *)obj {
    NSLog([NSString stringWithFormat:@"%@: %@",NSStringFromClass([obj class]), [BigFoxUtils toString:obj]]);
}

- (void) error:(NSObject *)obj {
     NSLog([NSString stringWithFormat:@"error at object: %@",[BigFoxUtils toString:obj]]);
}

-(void) error:(NSObject *)obj :(NSException *)ex {
    NSLog([NSString stringWithFormat:@"%@ %@", [BigFoxUtils toString:obj], [ex description]]);
}

-(void) logChat:(char *)data : (int) length{
    NSString* result = @"";
    for ( int i = 0; i < length; i++) {
        result = [result stringByAppendingString:[NSString stringWithFormat:@"%d",data[i]]];

    }
            NSLog(result);
}
@end
