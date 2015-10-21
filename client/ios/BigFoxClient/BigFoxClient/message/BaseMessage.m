//
//  BaseMessage.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "BaseMessage.h"
@implementation BaseMessage {
    BOOL isCore;
    
}
@synthesize length;
@synthesize tag;
@synthesize mSequence;
@synthesize sSequence;
@synthesize status;
@synthesize checkSum;

-(id) init {
    self = [super init];
    if (self) {
        isCore = false;
    }
    return self;
}

- (id) init:(bool) _isCore {
    self = [super init];
    if (self) {
        isCore = _isCore;
        
    }
    return self;
}

- (NSString*) toString {
    return [NSString stringWithFormat:@"%@ ", NSStringFromClass([self class])];
}
- (int) getStatus {
        if (isCore) {
            self.status = self.status | STATUS_CORE;
        }
        return self.status;
}

//- (int) getLength {
//    return self.length;
//}
//- (void)setLength:(int)_length {
//    self.length= _length;
//}
//
//-(int) getTag {
//    return self.tag;
//}
//
//- (void) setTag:(int)tag    {
//    self.tag = tag;
//}
//
//-(int) getStatus {
//    if (isCore) {
//        self.status = self.status | STATUS_CORE;
//    }
//    return self.status;
//}
//
//-(void) setStatus:(int)status {
//    self.status = status;
//}
//
//-(int) getMSequence {
//    return self.mSequence;
//}
//-(void) setMSequence:(int)_mSequence {
//    self.mSequence = _mSequence;
//}
//
//-(int) getSSequence {
//    return  self.sSequence;
//}
//
//-(void) setSSequence:(int)_sSequence {
//    self.sSequence = _sSequence;
//}
//
//-(int) getCheckSum {
//    return self.checkSum;
//}
//
//-(void) setCheckSum:(int)_checkSum {
//    self.checkSum = _checkSum;
//}

- (BOOL) isCore {
    return isCore;
}

- (void) execute {
    
}
@end
