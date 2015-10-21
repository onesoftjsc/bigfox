//
//  CSName.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "CSName.h"
#import "Tags.h"
static short TAG = CS_NAME;
@implementation CSName
@synthesize msg;

- (id) initWithMessage:(NSString *)_msg {
    self = [super init];
    if (self) {
        self.tag = TAG;
        self.msg = _msg;
    }
    return self;
}
@end
