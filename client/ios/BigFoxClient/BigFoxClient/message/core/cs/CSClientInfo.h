//
//  CSClientInfo.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "MessageOut.h"
#import "ClientInfo.h"

//@Message(tag = CS_CLIENT_INFO, name = @"CS_CLIENT_INFO", isCore = true)
@interface CSClientInfo : MessageOut
//@Property (name = @"clientInfo")
@property (nonatomic) ClientInfo* clientInfo;
@end
