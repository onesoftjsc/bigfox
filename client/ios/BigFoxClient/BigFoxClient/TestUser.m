//
//  TestUser.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/10/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "TestUser.h"

@implementation TestUser 
@synthesize name;
@synthesize textChat;

- (id) init {
    self = [super init];
    if (self) {
        old = @"bhj";
        self.name= @"quyenanh";
        self.textChat = @"xin chao";
    }
    return self;
}

@end
