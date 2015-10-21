//
//  CSName.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "MessageOut.h"

//@Message(tag = CS_NAME, name = @"CS_NAME")
@interface CSName : MessageOut
//@Property(name=@"msg")
@property (nonatomic, retain) NSString* msg;

- (id) initWithMessage : (NSString*) msg;
@end
