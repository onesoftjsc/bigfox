//
//  SCInitSession.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import "MessageIn.h"
#import "CoreTags.h"

#define START_NEW_SESSION 0x01
#define CONTINUE_OLD_SESSION 0x02
//@Message(tag = SC_INIT_SESSION, name = @"SC_INIT_SESSION", isCore = true)
@interface SCInitSession : MessageIn
//@Property(name = @"sessionStatus")
@property int sessionStatus;

- (void) execute ;
@end
