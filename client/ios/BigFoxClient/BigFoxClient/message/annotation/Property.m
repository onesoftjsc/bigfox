//
//  Property.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/11/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "Property.h"

@implementation Property
@synthesize name;
- (id) init {
    self = [super init];
    if (self) {
        self.name = @"";
    }
    return self;
}
@end
