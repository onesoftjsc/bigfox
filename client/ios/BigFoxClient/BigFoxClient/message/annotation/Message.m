//
//  Message.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/11/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "Message.h"

@implementation Message
@synthesize tag ;
@synthesize name ;
@synthesize isCore;


- (id) init {
    self = [super init];
    if (self) {
        self.tag = 0;
        self.name = @"";
        self.isCore = false;
    }
    return self;
}
@end
