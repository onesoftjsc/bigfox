//
//  CSBigData.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "MessageOut.h"

//@Message(tag = CS_BIGDATA, name = @"CS_BIGDATA")
@interface CSBigData : MessageOut
//@Property(name="data")
@property NSData* data;

-(id) initWithData : (NSData*) data ;

@end
