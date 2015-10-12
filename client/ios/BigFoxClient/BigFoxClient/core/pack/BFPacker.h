//
//  BFPacker.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/25/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import <Foundation/Foundation.h>

#define MAX_LENGTH 100000

@interface BFPacker : NSObject

+ (NSData*) pack :(NSStream*) is : (NSData*)data ;
+ (NSArray*) slide : (NSData*) data ;
@end
