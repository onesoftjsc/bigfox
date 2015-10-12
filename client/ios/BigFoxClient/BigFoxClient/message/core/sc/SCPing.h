//
//  SCPing.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "MessageIn.h"
//@Message(tag = SC_PING, name = @"SC_PING", isCore = true)
@interface SCPing : MessageIn
//@Property (name = @"serverTime"
@property long serverTime;

-(void) execute;
@end
