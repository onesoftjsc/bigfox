//
//  SCChat.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "SCChat.h"
#import "Tags.h"
#import "ViewController.h"

static const int TAG = SC_CHAT;
@implementation SCChat
@synthesize msg;

- (id) init {
    self = [super init];
    if (self) {
        self.tag = TAG;
    }
    return self;
}

- (void) execute {
    dispatch_async(dispatch_get_main_queue(),
    ^{
        self.msg = [self.msg stringByReplacingOccurrencesOfString:@"\n" withString:@" "];
         [[ViewController getInstance] addMessage:self.msg];
    });
}
@end
