//
//  BigFoxUtils.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BFDataInputStream.h"
enum {
    BNULL = 0,
    BNOT_NULL = 1,
    BINT = 1,
    BSHORT = 2,
    BBYTE = 3,
    BLONG = 4,
    BFLOAT = 5,
    BDOUBLE = 6,
    BBOOLEAN = 7,
    BCHAR = 8,
    BSTRING = 9,
    BOBJECT = 10,
    BARRAY_INT = 11,
    BARRAY_SHORT = 12,
    BARRAY_BYTE = 13,
    BARRAY_LONG = 14,
    BARRAY_FLOAT = 15,
    BARRAY_DOUBLE = 16,
    BARRAY_BOOLEAN = 17,
    BARRAY_CHAR = 18,
    BARRAY_STRING = 19,
    BARRAY_OBJECT = 20
};
@interface BigFoxUtils : NSObject
+ (char*) toBytes: (id) object ;
+ (void) read :(id) object withData: (NSData*) data;
+ (void) read : (id) object withBytes:(char*) data length: (int) length;
+ (id) fromBytes : (Class) class withBytes: (char*) data : length: (int)length;
+ (id) fromBytes: (Class) class withData: (NSData*) in ;
+ (void) write : (id)object : (BFDataOutputStream*) out ;
+ (void) write:(id)object withBytes :(char*)data length : (int)length ;
+ (NSString*) toString : (id) object;
+ (NSString*) toString:(char*) data : (int) length;
+ (NSString*) toStringWithData: (BFDataInputStream*) in ;
@end
