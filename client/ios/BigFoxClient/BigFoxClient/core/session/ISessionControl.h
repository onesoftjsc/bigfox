//
//  ISessionControl.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol ISessionControl <NSObject>
- (void) onStartSession;
- (void) onReconnectedSession;
@end
