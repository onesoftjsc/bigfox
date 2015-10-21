//
//  ClientInfo.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#import <Foundation/Foundation.h>
#define DEVICE_IOS @"ios"
#define DEVICE_ANDROID @"android"
#define DEVICE_WP @"wp"
#define DEVICE_WEB @"web"
#define DEVICE_DESKTOP @"desktop"

@interface ClientInfo : NSObject
//@Property (name = @"device")
@property (nonatomic, retain)NSString* device;
//@Property (name = @"imei")
@property (nonatomic, retain)NSString* imei;
//@Property (name = @"version")
@property int version;
//@Property (name = @"sessionId")
@property (nonatomic, retain)NSString* sessionId;
//@Property (name = @"metadata")
@property (nonatomic, retain)NSString* metadata;
@end
