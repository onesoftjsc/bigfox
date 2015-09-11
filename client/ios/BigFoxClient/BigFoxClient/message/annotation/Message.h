//
//  Message.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/11/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "ROAnnotation.h"

@interface Message : ROAnnotation
@property int tag;
@property(nonatomic, strong) NSString* name;
@property BOOL isCore;
@end
