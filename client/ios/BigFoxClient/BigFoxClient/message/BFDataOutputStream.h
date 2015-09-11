//
//  BFDataOutputStream.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/7/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface BFDataOutputStream : NSObject

- (void)writeChar:(int8_t)v;

- (void)writeShort:(int16_t)v;

- (void)writeInt:(int32_t)v;

- (void)writeLong:(int64_t)v;

- (void)writeUTF:(NSString *)v;

- (void)writeBytes:(NSData *)v;

- (void)writeBytes2:(NSData *)v;

- (NSData *)toByteArray;

- (void)writeBoolaen:(bool)v;

- (void)writeFloat:(float)v;

- (void)writeDouble:(double)v;

- (void) writeByte : (int)v;

@end
