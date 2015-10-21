//
//  SCChat.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "MessageIn.h"
//@Message(tag = SC_CHAT, name = @"SC_CHAT")
@interface SCChat : MessageIn
//@Property(name=@"msg")
@property (nonatomic, retain) NSString* msg;

-(void) execute;
@end
