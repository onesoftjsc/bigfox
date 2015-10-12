//
//  SCPing.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "SCPing.h"
#import "CoreTags.h"
static const int TAG = SC_PING;
@implementation SCPing

@synthesize serverTime;

- (id) init {
    self = [super init : true];
    if (self) {
        self.tag = TAG;
    }
    return self;
}

- (void) execute {
    
}
@end
