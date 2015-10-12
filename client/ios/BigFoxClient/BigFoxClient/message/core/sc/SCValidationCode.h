//
//  SCValidationCode.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "MessageIn.h"
//@Message(tag = SC_VALIDATION_CDOE, name = @"SC_VALIDATION_CDOE", isCore = true)
@interface SCValidationCode : MessageIn
//@Property (name = @"validationCode")
@property int validationCode;
-(void) execute;
@end
