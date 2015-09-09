//
//  DefaultSessionControl.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ISessionControl.h"
@interface DefaultSessionControl : NSObject <ISessionControl>
- (void) onStartSession;
- (void) onReconnectedSession;
@end
