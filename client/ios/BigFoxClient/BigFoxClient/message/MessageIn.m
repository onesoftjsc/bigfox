//
//  MessageIn.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/10/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "MessageIn.h"
#import "BFDataInputStream.h"
static short TAG = 0;
@implementation MessageIn {
    NSData* data;
    BFDataInputStream* in;
}

- (id) initWith:(NSData *)_data {
    self = [super init];
    if (self) {
        if (_data) {
            data = _data;
            in = [[BFDataInputStream alloc] initWithData:data];
        }
    }
    return self;
}



@end
