//
//  BFDataInputStream.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/7/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface BFDataInputStream : NSObject
- (id)initWithData:(NSData *)data;
-(id) initWithBytes: (char*)data : (int) length;
+ (id)dataInputStreamWithData:(NSData *)data;

-(int32_t)read;

- (Byte)readByte;

- (int8_t)readChar;

- (bool) readBoolean;

- (int16_t)readShort;

- (int32_t)readInt;

- (int64_t)readLong;

- (NSString *)readUTF;

-(float_t)readFloat;

-(NSData *)readData;

-(double)readDouble;

@end
