//
//  CSBigData.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "CSBigData.h"
#import "Tags.h"
static int TAG = CS_BIGDATA;
@implementation CSBigData

@synthesize data;

- (id) initWithData:(NSData *)data {
    self = [super init];
    if (self) {
        self.tag = TAG;
        self.data = data;
    }
    return self;
}
@end
