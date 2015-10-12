//
//  PingThreadManager.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "PingThreadManager.h"
#import "CSPing.h"
#import "ConnectionManager.h"

@implementation PingThreadManager
static PingThreadManager* _instance;

+ (PingThreadManager*) getInstance {
    if (_instance == nil) {
        _instance = [[PingThreadManager alloc]init];
        [_instance start];
    }
    return _instance;
}

- (void) start {
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        while (true) {
            [[ConnectionManager getInstance] write:[[CSPing alloc] init]];
            sleep(5);
        }
    });
}
@end
