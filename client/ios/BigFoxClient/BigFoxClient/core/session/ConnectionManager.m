//
//  ConnectionManager.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "ConnectionManager.h"
#import "ISessionControl.h"
#import "BFConfig.h"
#import "BFLogger.h"
#import "BFPacker.h"
#import "CompressManager.h"
#import "EncryptManager.h"
#import "MessageExecute.h"
#import "SCValidationCode.h"
#import "SCInitSession.h"
#import "MessageOut.h"
#import "DefaultSessionControl.h"
#import "SCPing.h"

static ConnectionManager* _instance = nil;
@implementation ConnectionManager {
    NSInputStream* is;
    NSOutputStream* os;
    NSMutableData* bufferOS;
    int validationCode;
    int mSequenceFromServer;
    BOOL isWorking;
    NSMutableArray* queueOutMessage;
    id<ISessionControl> sessionControl;
    int byteIndex;
}

@synthesize curMSequence;
@synthesize curSSequence;
@synthesize sessionId;
@synthesize lastPingReceivedTime;

+ (ConnectionManager*)getInstance {
    if (_instance == nil) {
        _instance = [[ConnectionManager alloc] init];
    }
    return _instance;
}

-(id) init {
    self = [super init];
    if (self) {
        queueOutMessage = [[NSMutableArray alloc] init];
        sessionId = @"";
        sessionControl = [[DefaultSessionControl alloc] init];
    }
    return self;
}

-(void) startThread {
//    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_BACKGROUND, 0), ^{
//        [self initConnect];
//    });
    [self initConnect];
//    dispatch_async(dispatch_get_main_queue(), ^{
//         [self initConnect];
//    });
}

- (void) initConnect {
    @try {
        isWorking = false;
        is = nil;
        os = nil;
        CFReadStreamRef readStream;
        CFWriteStreamRef writeStream;
        CFStreamCreatePairWithSocketToHost(NULL, (__bridge CFStringRef) SERVER, PORT, &readStream, &writeStream);
        is = (__bridge NSInputStream *) readStream;
        os = (__bridge NSOutputStream *) writeStream;
        bufferOS = [[NSMutableData alloc] init];
        [is setDelegate: self];
        [os setDelegate:self];
        [is scheduleInRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
        [os scheduleInRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
        [is open];
        [os open];
    } @catch (NSException* e) {
    }
}

- (void) onMessage:(NSStream *)stream :(MessageIn *)mIn {
    
    if (![mIn isKindOfClass:[SCPing class]]) {
        [[BFLogger getInstance] log: @"rev : "];
        [[BFLogger getInstance] info:mIn];
    } else {
        [[BFLogger getInstance] log: @"rev ping...... "];
    }
    if (!([mIn isKindOfClass:[SCValidationCode class]]) && !([mIn isKindOfClass:[SCInitSession class]])) {
        if ([mIn sSequence] <= curSSequence) {
            return ; // dont process message processed
        }
        curSSequence = [mIn sSequence];
        for (int i = mSequenceFromServer; i <= [mIn mSequence]; i++) {
            if ([queueOutMessage count] > 0) {
                [queueOutMessage removeObjectAtIndex:0];
            }
        }
        mSequenceFromServer = [mIn mSequence];
    }
    [mIn execute];
}

- (void) write:(MessageOut *)mOut {
    self.curMSequence ++ ;
    mOut.mSequence = self.curMSequence;
    if (![mOut isCore]) {
        [queueOutMessage addObject:mOut];
    }
    [self flush:mOut];
}

- (void) flush : (MessageOut*) mOut {
    [[BFLogger getInstance] log:@"send data :"];
//    [[BFLogger getInstance] info:mOut];
    NSData* data = [mOut toBytes];
    data = [EncryptManager crypt:data ];
    data = [[CompressManager getInstance] compress:data ];
    // send data
    [os write:[data bytes] maxLength:[data length]];
}

-(void) stream:(NSStream *)aStream handleEvent:(NSStreamEvent)eventCode {
    switch(eventCode) {
        case NSStreamEventNone:
            isWorking = true;
            break;
        case NSStreamEventOpenCompleted:
            isWorking = true;
            [[BFLogger getInstance] log:@"client connected : "];
//            [WMessage getInstance].m_sequence = 0;
//            contextB1.s_sequence = -1;
            break;
        case NSStreamEventHasSpaceAvailable: {
            [[BFLogger getInstance] log:@"client has space available : "];
//            int minL = MIN(1024, bufferOS.length);
//            if (bufferOS.length > 0) {
//                int numbytes = [os write: bufferOS.bytes maxLength: minL];
//                if (numbytes != -1) {
//                    [bufferOS replaceBytesInRange:NSMakeRange(0, numbytes) withBytes:"" length: 0];
//                }
//            }
        }
             break;
        case NSStreamEventHasBytesAvailable:{
            [[BFLogger getInstance] log:@"client has byte available : "];
            if (aStream == is) {
                unsigned char buffer[10240];
                int len = 0;
                while ([is hasBytesAvailable]) {
                    len = [is read:buffer maxLength: 10240];
                    if (len > 0) {
                        NSData* dataPack = [BFPacker pack : is : [NSData dataWithBytes:buffer length:len]];
                        if (dataPack != nil) {
                            @try {
                                dataPack = [[CompressManager getInstance] decompress:dataPack ];
                                dataPack = [EncryptManager crypt:dataPack];
                                [[MessageExecute getInstance] onMessage:is :dataPack];
                            }
                            @catch (NSException *exception) {
                                [aStream close];
                                [aStream removeFromRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
                                 aStream = nil;
                            }

                        }
                    }
                }
            }
            break;
        }
        case NSStreamEventErrorOccurred:
            // error connect
            [[BFLogger getInstance] log:@"error connect : "];
            break;
            
        case NSStreamEventEndEncountered:
            [[BFLogger getInstance] log:@"End Encountered "];
            [aStream close];
            [aStream removeFromRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
            aStream = nil;
            break;
    }
}

- (void) onStartNewSession {
    [[BFLogger getInstance] log:@"start new session"];
    [sessionControl onStartSession];
}

- (void) resendOldMessages {
    @try {
        [[BFLogger getInstance] log:@"resend old"];
        for (int i = mSequenceFromServer + 1; i < curMSequence; i++) {
            MessageOut* bm = [queueOutMessage objectAtIndex:i];
            if (bm != nil) {
                [self flush:bm];
            }
        }
    }
    @catch (NSException *exception) {
        [[BFLogger getInstance] error:exception];
    }
}

- (void) onContinueOldSession {
    [[BFLogger getInstance] log:@"on reconnect"];
    [sessionControl onReconnectedSession];
}

- (void) setSessionControl:(id<ISessionControl>)_sessionControl {
    sessionControl = _sessionControl;
}

- (int) getValidationCode {
    return validationCode;
}

- (void) setValidationCode:(int)_validationCode  {
    validationCode = _validationCode;
}

-(void) showAlert : (NSString*) title : (NSString*) message {
    
}


@end
