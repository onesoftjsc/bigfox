//
//  BFLogger.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "BFLogger.h"

@implementation BFLogger
static BFLogger* _instance;
+ (BFLogger*) getInstance {
    if (_instance == nil) {
        _instance = [[BFLogger alloc] init];
    }
    return _instance;
}

- (void) info:(NSObject *)obj {
    NSLog([obj description]);
}

- (void) error:(NSObject *)obj {
     NSLog([obj description]);
}

-(void) error:(NSObject *)obj :(NSException *)ex {
    NSLog([NSString stringWithFormat:@"%@ %@", [obj description], [ex description]]);
}
@end
