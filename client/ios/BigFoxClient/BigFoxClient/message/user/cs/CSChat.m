//
//  CSChat.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "CSChat.h"
#import "Tags.h"
static short TAG = CS_CHAT;
@implementation CSChat
@synthesize msg;

- (id) initWithMessage:(NSString *)msg {
    self = [super init];
    if (self) {
        self.tag = TAG;
        self.msg = msg;
    }
    return self;
}
@end
