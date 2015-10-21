//
//  CSClientInfo.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "CSClientInfo.h"
#import "ConnectionManager.h"
#import "BFUtils.h"
#import "BFConfig.h"
#import "CoreTags.h"


static int TAG = CS_CLIENT_INFO;
@implementation CSClientInfo
@synthesize clientInfo;

- (id) init {
    self = [super init:true];
    if (self) {
        self.tag = TAG;
        clientInfo = [[ClientInfo alloc] init];
        clientInfo.device = DEVICE_IOS;
        clientInfo.imei = @"";
        
        if ([ConnectionManager getInstance].sessionId.length == 0) {
            [ConnectionManager getInstance].sessionId = [BFUtils genRandStringLength:10];
        }
        clientInfo.sessionId = [[ConnectionManager getInstance] sessionId];
        clientInfo.version = VERSION;
        clientInfo.metadata= @"";
    }
    return  self;
}
@end
