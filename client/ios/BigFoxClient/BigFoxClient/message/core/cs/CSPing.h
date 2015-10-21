//
//  CSPing.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "MessageOut.h"
//@Message(tag = CS_PING, name = @"CS_PING", isCore = true)
@interface CSPing : MessageOut
//@Property(name=@"clientTime")
@property long clientTime;
@end
