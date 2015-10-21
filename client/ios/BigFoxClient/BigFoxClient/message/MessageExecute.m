//
//  MessageExecute.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/11/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//
#import "MessageExecute.h"
#import <Foundation/NSObjCRuntime.h>
#import <objc/objc-runtime.h>
#include <stdio.h>
#import <Foundation/Foundation.h>
#import "MessageIn.h"
// #import "Message.h"
#import "BFLogger.h"
#import "BFDataInputStream.h"
#import "BigFoxUtils.h"
#import "ConnectionManager.h"

static MessageExecute* _instance = nil;
static NSMutableDictionary * cacheTagCoreMessage = nil;
static NSMutableDictionary * cacheTagUserMessage = nil ;
@implementation MessageExecute

+ (MessageExecute*) getInstance {
    if (_instance == nil) {
        _instance = [[MessageExecute alloc] init];
        if (!cacheTagCoreMessage) {
            cacheTagCoreMessage = [[NSMutableDictionary alloc] init];
        }
        
        if (!cacheTagUserMessage) {
            cacheTagUserMessage =[[NSMutableDictionary alloc] init];
        }
        
        [_instance loadCoreClasses];
    }
    return _instance;
}

+ (NSCache*) getCacheTagCore {
    if (!cacheTagCoreMessage) {
        cacheTagCoreMessage = [[NSMutableDictionary alloc] init];
    }
    return cacheTagCoreMessage;
}

+ (NSCache*) getCacheTagUser {
    if (!cacheTagUserMessage) {
        cacheTagUserMessage =[[NSMutableDictionary alloc] init];
    }
    return cacheTagUserMessage;
}

- (void) loadCoreClasses{
    NSArray *messageInClasses= [self ClassGetSubclasses:[MessageIn class]];
    if (messageInClasses.count > 0) {
        for(Class mClassMI in messageInClasses) {
            @try {
                MessageIn* message = (MessageIn*)[[mClassMI alloc] init] ;
                if ([message isCore]) {
                    [cacheTagCoreMessage setObject: message forKey: [NSNumber numberWithInt: message.tag]];
                }else{
                    [cacheTagUserMessage setObject: message forKey:[NSNumber numberWithInt: message.tag]];
                }
            }
            @catch (NSException *exception) {
                [[BFLogger getInstance] error:exception];
            }
        }
    }
}

- (NSArray*)ClassGetSubclasses: (Class)parentClass{
    int numClasses = objc_getClassList(NULL, 0);
    Class *classes = NULL;
    classes = (Class*) malloc(sizeof(Class) * numClasses);
    numClasses = objc_getClassList(classes, numClasses);
    
    NSMutableArray *result = [NSMutableArray array];
    NSInteger i;
    
    for ( i = 0; i < numClasses; i++)
    {
        Class superClass = classes[i];
        do
        {
            superClass = class_getSuperclass(superClass);
        } while(superClass && superClass != parentClass);
        
        if (superClass == nil)
        {
            continue;
        }
        
        [result addObject:classes[i]];
    }
    
    free(classes);
    
    return result;
}

- (void) onMessage:(NSStream *)stream :(NSData *)data  {
//    char* cData = [data bytes];
//    int tag = (int) (((cData[4] & 0xff) << 24) | ((cData[5] & 0xff) << 16) | ((cData[6] & 0xff) << 8) | ((cData[7] & 0xff)));
//    int status = (int) (((cData[16] & 0xff) << 24) | ((cData[17] & 0xff) << 16) | ((cData[18] & 0xff) << 8) | ((cData[19] & 0xff)));
//    BOOL isCore = (status & 0x01) == 0x01;
    BFDataInputStream* is = [[BFDataInputStream alloc] initWithData:data];
    int tag = 0;
    int length = 0;
    @try {
        length = [is readInt];
        tag = [is readInt];
        int mSequence = [is readInt]; // msequence
        int sSequence = [is readInt]; // ssequence
        int status    = [is readInt];// status
        int checkSum  = [is readInt];
        id clazz = nil ;
        if ((status & 0x01) == 0x01) {
            clazz = [cacheTagCoreMessage objectForKey:[NSNumber numberWithInt:tag]];
        }else{
            clazz = [cacheTagUserMessage objectForKey:[NSNumber numberWithInt:tag]];
        }
        if (clazz != nil ) {
            MessageIn* message = (MessageIn*) [BigFoxUtils fromBytes:[clazz class] withData: is];
            message.length= length;
            message.tag = tag  ;
            message.mSequence = mSequence;
            message.sSequence = sSequence;
            message.status = status;
            message.checkSum = checkSum;
            [[ConnectionManager getInstance] onMessage:stream :message];
        }
    }
    @catch (NSException *exception) {
        [[BFLogger getInstance] error:exception];
    }
}

- (BOOL) isValid : (NSStream*) stream : (NSData*) data  {
    return true;
}
@end
