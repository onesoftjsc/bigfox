//
//  CSPing.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "CSPing.h"
#import "CoreTags.h"
static short TAG = CS_PING;
@implementation CSPing
@synthesize clientTime;

- (id) init {
    self = [super init:true];
    if (self) {
        self.tag = TAG;
    }
    return  self;
}
@end
