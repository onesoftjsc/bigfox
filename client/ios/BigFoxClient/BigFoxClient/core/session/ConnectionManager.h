//
//  ConnectionManager.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ISessionControl.h"
#define TIME_OUT 20

@interface ConnectionManager : NSObject
+ (ConnectionManager*) getInstance;

@property int curSSequence;
@property int curMSequence;
@property long lastPingReceivedTime;
@property (nonatomic, retain) NSString* sessionId;
- (void) initConnect;
- (void) onStartNewSession;
- (void) resendOldMessages;
- (void) onContinueOldSession;
- (void) setSessionControl : (id<ISessionControl>) sessionControl ;
- (int) getValidationCode;
- (void) setValidationCode : (int) validationCode;
@end
