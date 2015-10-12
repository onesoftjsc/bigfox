//
//  MessageOut.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/14/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "BaseMessage.h"

@interface MessageOut : BaseMessage
-(NSData*) toBytes;
- (MessageOut*) clone ;

@end
