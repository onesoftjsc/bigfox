//
//  ClientInfo.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "ClientInfo.h"

@implementation ClientInfo
@synthesize device;
@synthesize imei;
@synthesize version;
@synthesize sessionId;
@synthesize metadata;
@synthesize zone;
-(id)init{
    self = [super init];
    if(self != nil){
        zone = @"classes";
    }
    return self;
}
@end
