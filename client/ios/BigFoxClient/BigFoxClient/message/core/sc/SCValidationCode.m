//
//  SCValidationCode.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "SCValidationCode.h"
#import "ConnectionManager.h"
#import "CSClientInfo.h"
#import "CoreTags.h"

static const int TAG = SC_VALIDATION_CODE;
@implementation SCValidationCode
@synthesize validationCode;

- (id) init {
    self = [super init:true];
    if (self) {
        self.tag = TAG;
    }
    return self;
}

-(void) execute {
    [[ConnectionManager getInstance] setValidationCode: self.validationCode];
    CSClientInfo* info = [[CSClientInfo alloc] init];
    [[ConnectionManager getInstance] write: info];
}
@end
