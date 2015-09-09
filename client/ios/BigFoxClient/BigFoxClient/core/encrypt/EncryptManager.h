//
//  EncryptManager.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface EncryptManager : NSObject
+ (char*) crypt : (char*) data : (int) length;
@end
