//
//  ConnectionManager.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ISessionControl.h"
#import "MessageIn.h"
#import "MessageOut.h"
#define MAX_TIMEOUT 20

@interface ConnectionManager : NSObject<NSStreamDelegate>


@property int curSSequence;
@property int curMSequence;
@property long lastPingReceivedTime;
@property (nonatomic, retain) NSString* sessionId;
- (void) startThread;
- (void) onStartNewSession;
- (void) resendOldMessages;
- (void) onContinueOldSession;
- (void) setSessionControl : (id<ISessionControl>) sessionControl ;
- (int) getValidationCode;
- (void) setValidationCode : (int) validationCode;
+ (ConnectionManager*) getInstance;
- (void) onMessage : (NSStream*) stream : (MessageIn*) mIn;
-(void) write : (MessageOut*) mOut;
@end
