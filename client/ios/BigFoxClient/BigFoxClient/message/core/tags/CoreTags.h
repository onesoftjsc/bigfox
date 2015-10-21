//
//  CoreTags.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/29/15.
//  Copyright © 2015 QuyenNX. All rights reserved.
//

#ifndef CoreTags_h
#define CoreTags_h
enum {
    // client - server
    CS_PING            = 0x000002,
    CS_CLIENT_INFO     = 0x000003,
    
    
    // server - client
    SC_VALIDATION_CODE = 0x110000,
    SC_PING            = 0x110002,
    SC_INIT_SESSION    = 0x110003
};

#endif /* CoreTags_h */
