//
//  SCInitSession.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "SCInitSession.h"
#import "ConnectionManager.h"
#import "PingThreadManager.h"
#import "CoreTags.h"
static const int TAG = SC_INIT_SESSION;
@implementation SCInitSession

@synthesize sessionStatus;

- (id) init {
    self = [super init : true];
    if (self) {
        self.tag = TAG;
    }
    return self;
}

- (void) execute {
    if (sessionStatus == START_NEW_SESSION) {
        [[ConnectionManager getInstance] onStartNewSession];
        [PingThreadManager getInstance];
    }else {
        [[ConnectionManager getInstance] resendOldMessages];
        [[ConnectionManager getInstance] onContinueOldSession];
    }
}
@end
