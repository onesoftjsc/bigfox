//
//  MessageExecute.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/11/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MessageExecute : NSObject

+ (MessageExecute*) getInstance;
- (void) loadCoreClasses ;
+ (NSCache*) getCacheTagCore;
+ (NSCache*) getCacheTagUser;
- (void) onMessage : (NSStream*) stream : (NSData*) data  ;
@end
