//
//  CompressManager.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "CompressManager.h"
#import "BFZip.h"

@implementation CompressManager {
    id<ICompress> cp;
}

static CompressManager* _instance = nil;

+ (CompressManager*) getInstance {
    if(_instance == nil) {
        _instance = [[CompressManager alloc] init];
        [_instance setCompress:[[BFZip alloc]init]];
    }
    return _instance;
}

- (void) setCompress : (id<ICompress>) compress {
    cp = compress;
}
-(char*) compress:(char *)data :(int)length {
    return [cp compress:data :length];
}

-(char*) decompress:(char *)data :(int)length {
    return [cp decompress:data :length];
}
@end
