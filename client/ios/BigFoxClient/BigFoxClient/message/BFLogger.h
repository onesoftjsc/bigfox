//
//  BFLogger.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface BFLogger : NSObject
+ (BFLogger*) getInstance;
- (void) info: (NSObject*) obj;
-(void) error : (NSObject*) obj : (NSException*) ex ;
- (void) error:(NSObject*) obj;
@end
