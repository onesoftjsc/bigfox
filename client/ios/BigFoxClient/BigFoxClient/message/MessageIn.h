//
//  MessageIn.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/10/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "BaseMessage.h"
#define _HEADER_LENGTH 17
@interface MessageIn : BaseMessage
- (id) initWith : (NSData*) data ;
- (void) execute ;
@end
