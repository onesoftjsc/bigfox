//
//  BigFoxUtils.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "BigFoxUtils.h"
#import "BFDataInputStream.h"
#import "BFDataOutputStream.h"
#include <objc/message.h>
#import <objc/objc-runtime.h>
#import <Foundation/NSObjCRuntime.h>

static NSCache* caches = nil ;
@implementation BigFoxUtils {
}

+ (void) initialize {
    if (!caches) {
        caches = [[NSCache alloc] init];
    }
}

static const char * getPropertyType(objc_property_t property) {
    const char *attributes = property_getAttributes(property);
    //printf("attributes=%s\n", attributes);
    char buffer[1 + strlen(attributes)];
    strcpy(buffer, attributes);
    char *state = buffer, *attribute;
    while ((attribute = strsep(&state, ",")) != NULL) {
        if (attribute[0] == 'T' && attribute[1] != '@') {
            //
            /*
             */
            return (const char *)[[NSData dataWithBytes:(attribute + 1) length:strlen(attribute) - 1] bytes];
        }
        else if (attribute[0] == 'T' && attribute[1] == '@' && strlen(attribute) == 2) {
            //
            return "id";
        }
        else if (attribute[0] == 'T' && attribute[1] == '@') {
            //
            return (const char *)[[NSData dataWithBytes:(attribute + 3) length:strlen(attribute) - 4] bytes];
        }
    }
    return "";
}

+ (char*) toBytes:(id)object {
    if (object == nil) {
        return  nil;
    }
    BFDataOutputStream *dataOutputStream=[[BFDataOutputStream alloc] init];
    
    unsigned int outCount, i;
    objc_property_t *properties = class_copyPropertyList([object class], &outCount);
    for (i = 0; i < outCount; i++) {
        objc_property_t property = properties[i];
        const char *propName = property_getName(property);
        const char *propType = getPropertyType(property);
        NSString *propertyName = [NSString stringWithUTF8String:propName];
        NSString *propertyType = [NSString stringWithUTF8String:propType];
        
        if ([propertyType isEqualToString:@"NSString"]) {
            [dataOutputStream writeUTF:[object valueForKey:propertyName]];
        }
        else if([propertyType isEqualToString:@"i"]){
            [dataOutputStream writeInt:(int)[object valueForKey:propertyName]];
        }
        else if([propertyType isEqualToString:@"f"]){
            [dataOutputStream writeFloat:[[object valueForKey:propertyName] floatValue]];
        }
        else if ([propertyType isEqualToString:@"B"]) {
            [dataOutputStream  writeBoolaen:(bool)[object valueForKey:propertyName]];
        }
        else if([propertyType isEqualToString:@"NSData"]) {
            
            [dataOutputStream writeBytes:[object valueForKey:propertyName]];
            
        }
    }
    free(properties);
    NSData *resultData=[dataOutputStream toByteArray];
    return (char*)[resultData bytes];
}

+ (void) read:(id)object withData:(NSData *)data {
    Class class =[object class];
    BFDataInputStream *dataInputStream;
    if (data) {
        dataInputStream=  [[BFDataInputStream alloc] initWithData:data];
    }
    unsigned int outCount, i;
    NSArray* properties = object == nil ? nil : [BigFoxUtils getInheritedPrivateFields:[object class]];
    int nFields = [dataInputStream readByte];
    for (int j = 0; j < nFields; j++) {
        NSString* name = [dataInputStream readUTF];
        Byte type = [dataInputStream readByte];
        objc_property_t f = nil ;
        if (object != nil) {
            for (int i = 0, n = [properties count]; i < n; i++) {
                NSString* sname = (NSString*)[properties objectAtIndex:i];
                if ([name isEqualToString:sname]) {
                    f = class_getProperty(class, [name UTF8String]);
                    break;
                }
            }
        }
        if(type == nil) {
            
        }else if (type == BINT) {
            int v = [dataInputStream readInt];
            if (f != nil) {
                [object  setValue:[NSNumber numberWithInt:v] forKey:name];
            }
        }else if (type == BSHORT) {
            short v = [dataInputStream readShort];
            if (f != nil) {
                 [object  setValue:[NSNumber numberWithShort: v] forKey:name];
            }
        } else if (type == BBYTE) {
            Byte v = [dataInputStream readByte];
            if (f != nil) {
                [object setValue:[NSNumber numberWithUnsignedChar:v] forKey:name];
            }
        }else if (type == BLONG) {
            long v = [dataInputStream readLong];
            if (f != nil) {
                [object setValue:[NSNumber numberWithLong:v] forKey:name];
            }
        } else if (type == BFLOAT) {
            long v = [dataInputStream readFloat];
            if (f != nil) {
                [object setValue:[NSNumber numberWithFloat:v] forKey:name];
            }
        } else if (type == BDOUBLE) {
            long v = [dataInputStream readDouble];
            if (f != nil) {
                [object setValue:[NSNumber numberWithDouble:v] forKey:name];
            }
        } else if (type == BBOOLEAN) {
            long v = [dataInputStream readBoolean];
            if (f != nil) {
                [object setValue:[NSNumber numberWithBool:v] forKey:name];
            }
        } else if(type == BCHAR) {
             char v = [dataInputStream readChar];
            if (f != nil) {
                [object setValue:[NSNumber numberWithChar:v] forKey:name];
            }
        }else if(type == BSTRING) {
            NSString* v = [dataInputStream readUTF];
            if(f != nil) {
                [object setValue:v forKey:name];
            }
        } else if(type == BARRAY_INT) {
            int length = [dataInputStream readInt];
            NSMutableArray *val = [NSMutableArray array];
            for (int i = 0; i < length; i++) {
                [val addObject:[NSNumber numberWithInteger:[dataInputStream readInt]]];
            }
            if (f != nil) {
                [object setValue:val forKey:name];
            }
        }else if(type == BARRAY_SHORT) {
            int length = [dataInputStream readInt];
            NSMutableArray *val = [NSMutableArray array];
            for (int i = 0; i < length; i++) {
                [val addObject:[NSNumber numberWithShort:[dataInputStream readShort]]];
            }
            if (f != nil) {
                [object setValue:val forKey:name];
            }
        }else if(type == BARRAY_BYTE) {
            if (f != nil) {
                [object setValue:[dataInputStream readData] forKey:name];
            }
        }else if(type == BARRAY_LONG) {
            int length = [dataInputStream readInt];
            NSMutableArray *val = [NSMutableArray array];
            for (int i = 0; i < length; i++) {
                [val addObject:[NSNumber numberWithLong:[dataInputStream readLong]]];
            }
            if (f != nil) {
                [object setValue:val forKey:name];
            }
        }else if(type == BARRAY_FLOAT) {
            int length = [dataInputStream readInt];
            NSMutableArray *val = [NSMutableArray array];
            for (int i = 0; i < length; i++) {
                [val addObject:[NSNumber numberWithFloat:[dataInputStream readFloat]]];
            }
            if (f != nil) {
                [object setValue:val forKey:name];
            }
        } else if(type == BARRAY_DOUBLE) {
            int length = [dataInputStream readInt];
            NSMutableArray *val = [NSMutableArray array];
            for (int i = 0; i < length; i++) {
                [val addObject:[NSNumber numberWithDouble:[dataInputStream readDouble]]];
            }
            if (f != nil) {
                [object setValue:val forKey:name];
            }
        }else if(type == BARRAY_BOOLEAN) {
            int length = [dataInputStream readInt];
            NSMutableArray *val = [NSMutableArray array];
            for (int i = 0; i < length; i++) {
                [val addObject:[NSNumber numberWithBool:[dataInputStream readBoolean]]];
            }
            if (f != nil) {
                [object setValue:val forKey:name];
            }
        }else if(type == BARRAY_CHAR) {
            int length = [dataInputStream readInt];
            NSMutableArray *val = [NSMutableArray array];
            for (int i = 0; i < length; i++) {
                [val addObject:[NSNumber numberWithChar:[dataInputStream readChar]]];
            }
            if (f != nil) {
                [object setValue:val forKey:name];
            }
        }else if(type == BARRAY_STRING) {
            int length = [dataInputStream readInt];
            NSMutableArray *val = [NSMutableArray array];
            for (int i = 0; i < length; i++) {
                [val addObject: [dataInputStream readUTF]];
            }
            if (f != nil) {
                [object setValue:val forKey:name];
            }
        } else if(type == BARRAY_STRING) {
            int length = [dataInputStream readInt];
            NSMutableArray *val = [NSMutableArray array];
            for (int k = 0; k < length; k++) {
                Byte bNull = [dataInputStream readByte];
                if (bNull == BNOT_NULL) {
                    [val addObject: [dataInputStream readUTF]];
                }
            }
            if (f != nil) {
                [object setValue:val forKey:name];
            }
        } else if(type == BARRAY_OBJECT) {
            int length = [dataInputStream readInt];
            NSMutableArray *val = [NSMutableArray array];
            if(f != nil) {
                const char *propType = getPropertyType(f);
                NSString *propertyType = [NSString stringWithUTF8String:propType];
                Class typeClass = NSClassFromString(propertyType);
                // Here is the corresponding class even for nil values
                for (int k = 0; k < length; k++) {
                    Byte bNull = [dataInputStream readByte];
                    if (bNull == BNOT_NULL) {
                        id e = [[typeClass alloc] init];
                        [self read:e withData:dataInputStream];
                        [val insertObject:e atIndex:k];
                    }
                }
                [object setValue:val forKey:name];
            } else {
                for (int k = 0; k < length; k++) {
                    Byte bNull = [dataInputStream readByte];
                    if (bNull == BNOT_NULL) {
                        [self read: nil withData:dataInputStream];
                    }
                }
            }
        }else{
            if (f != nil) {
                const char *propType = getPropertyType(f);
                NSString *propertyType = [NSString stringWithUTF8String:propType];
                Class typeClass = NSClassFromString(propertyType);
                id e = [[typeClass alloc] init];
                [self read :e withData: dataInputStream];
                [object setValue:e forKey:name];
            } else {
                [self read :nil withData: dataInputStream];
            }
        }
    }
}

+ (void) read:(id)object withBytes:(char *)data length:(int)length {
    [self read:object withData:[NSData dataWithBytes:data length:length]];
}

+ (id) fromBytes:(Class)class withData:(NSData *)in {
    id object = [[class alloc] init];
    [self read:object withData:in];
    return  object;
}

+ (id) fromBytes:(Class)class withBytes:(char *)data :(id)length :(int)length {
    id object = [[class alloc] init];
    [self read:object withBytes:data length:length];
    return object;
}

+ (void) write:(id)object: (BFDataOutputStream*) out {
    NSArray* properties = object == nil ? nil : [BigFoxUtils getInheritedPrivateFields:[object class]];
    [out writeInt:[properties count]];
    for (int i = 0; i < [properties count]; i ++) {
        NSString* sname = (NSString*)[properties objectAtIndex:i];
        [out writeUTF:sname];
        objc_property_t property = class_getProperty(class, [sname UTF8String]);
        const char *propType = getPropertyType(property);
        NSString *propertyType = [NSString stringWithUTF8String:propType];
        Class typeClass = NSClassFromString(propertyType);
        if ([propertyType isEqualToString:@"i"]) {
            [out writeByte:BINT];
            [out writeInt:(int)[object valueForKey:sname]];
        } else if([propertyType isEqualToString:@"s"]) {
            [out writeByte:BSHORT];
            [out writeShort:(short)[object valueForKey:sname]];
        }else if([propertyType isEqualToString:@"c"]) {
            [out writeByte:BSHORT];
            [out writeShort:(short)[object valueForKey:sname]];
        }
    }
}

+ (NSArray *)getInheritedPrivateFields: (Class) class
{
    if ([caches objectForKey:class]) {
        return [caches objectForKey:class];
    }
    unsigned count;
    objc_property_t *properties = class_copyPropertyList(class, &count);
    
    NSMutableArray *result = [NSMutableArray array];
    
    unsigned i;
    for (i = 0; i < count; i++)
    {
        objc_property_t property = properties[i];
        if (property) {
            NSString *name = [NSString stringWithUTF8String:property_getName(property)];
            if ([name isEqualToString:@"length"]) {
                [result insertObject:name atIndex:0];
            }else{
                [result addObject:name];
            }
        }
    }
    free(properties);
    [caches setObject:result forKey:class];
    return result;
}

@end
