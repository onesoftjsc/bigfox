//
//  PingThreadManager.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "PingThreadManager.h"

@implementation PingThreadManager
static PingThreadManager* _instance;

+ (PingThreadManager*) getInstance {
    if (_instance == nil) {
        _instance = [[PingThreadManager alloc]init];
    }
    return _instance;
}

- (void) start {
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        while (true) {
         //   ConnectionManager.getInstance().write(new CSPing());
            sleep(5);
        }
        
    });
}
@end
