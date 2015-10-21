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
#define TAB 2
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

+ (NSData*) toBytes:(id)object {
    if (object == nil) {
        return  nil;
    }
    BFDataOutputStream *dataOutputStream=[[BFDataOutputStream alloc] init];
    [self write:object :dataOutputStream];
    NSData *resultData=[dataOutputStream toByteArray];
    return resultData;
}

+ (void) read:(id)object withData:(BFDataInputStream *)dataInputStream {
    @try {
        Class class =[object class];
//        BFDataInputStream *dataInputStream;
//        if (data) {
//            dataInputStream=  [[BFDataInputStream alloc] initWithData:data];
//        }
        unsigned int outCount, i;
        NSArray* properties = object == nil ? nil : [BigFoxUtils getInheritedPrivateFields:class];
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
            if(type == BNULL) {
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
    @catch (NSException *exception) {
    }
}

+ (void) read:(id)object withBytes:(char *)data length:(int)length {
    [self read:object withData:[NSData dataWithBytes:data length:length]];
}

+ (id) fromBytes:(Class)class withData:(BFDataInputStream *)in {
    id object = [[class alloc] init];
    [self read:object withData:in];
    return  object;
}

+ (id) fromBytes:(Class)class withBytes:(char *)data length :(int)length {
    id object = [[class alloc] init];
    [self read:object withBytes:data length:length];
    return object;
}

+ (void) write:(id)object: (BFDataOutputStream*) out{
    @try {
        NSArray* properties = object == nil ? nil : [BigFoxUtils getInheritedPrivateFields:[object class]];
        [out writeByte:[properties count]];
        for (int i = 0; i < [properties count]; i ++) {
            NSString* sname = (NSString*)[properties objectAtIndex:i];
            [out writeUTF:sname];
            objc_property_t property = class_getProperty([object class], [sname UTF8String]);
            const char *propType = getPropertyType(property);
            NSString *propertyType = [NSString stringWithUTF8String:propType];
            if ([propertyType isEqualToString:@"i"]) { // int
                [out writeByte:BINT];
                [out writeInt: [[object valueForKey:sname] intValue]];
                continue;
            } else if([propertyType isEqualToString:@"s"]) { // short
                [out writeByte:BSHORT];
                [out writeShort:[[object valueForKey:sname] shortValue]];
                continue;
            }else if([propertyType isEqualToString:@"C"]) { // byte
                [out writeByte:BBYTE];
                [out writeChar:[[object valueForKey:sname]intValue]];
                continue;
            }else if([propertyType isEqualToString:@"c"]) { // char
                [out writeByte:BCHAR];
                [out writeChar:[[object valueForKey:sname] charValue]];
                continue;
            }
            else if([propertyType isEqualToString:@"l"] || [propertyType isEqualToString:@"q"]) {
                [out writeByte:BLONG];
                [out writeLong:[[object valueForKey:sname] longValue]];
                continue;
            }else if([propertyType isEqualToString:@"f"]) {
                [out writeByte:BFLOAT];
                [out writeFloat:[[object valueForKey:sname] floatValue]];
                continue;
            }else if([propertyType isEqualToString:@"d"]) {
                [out writeByte:BDOUBLE];
                [out writeDouble:[[object valueForKey:sname] doubleValue]];
                continue;
            }else if([propertyType isEqualToString:@"B"]) {
                [out writeByte:BBOOLEAN];
                [out writeBoolaen:[[object valueForKey:sname] boolValue]];
                continue;
            }
            Class typeClass = NSClassFromString(propertyType);
            id value = [object valueForKey:sname];
            if (value == nil){
                [out writeByte:BNULL];
                continue;
            }
            if([value isKindOfClass:[NSArray class]])
            {
                NSArray* objectValue = [NSArray arrayWithArray:value];
                int count = [objectValue count];
                if(count <= 0) {
                    [out writeByte:BNULL];
                    continue;
                }
                id elevent = [objectValue objectAtIndex:0];
                if([elevent isKindOfClass:[NSNumber class]]) {
                    if (elevent == (void*)kCFBooleanFalse || elevent == (void*)kCFBooleanTrue) {
                        // num is boolean
                        [out writeByte:BARRAY_BOOLEAN];
                        [out writeInt: count];
                        for(int k = 0; k > count; k++) {
                            [out writeBoolaen:(BOOL)[objectValue objectAtIndex:k]];
                        }
                    } else {
                        // num is not boolean
                        switch(CFNumberGetType((CFNumberRef)elevent))
                        {
                            case kCFNumberIntType :
                                [out writeByte:BARRAY_INT];
                                [out writeInt: count];
                                for(int k = 0; k > count; k++) {
                                    [out writeInt:(int)[objectValue objectAtIndex:k]];
                                }
                                break;
                            case kCFNumberCGFloatType:
                                [out writeByte:BARRAY_FLOAT];
                                [out writeInt: count];
                                for(int k = 0; k > count; k++) {
                                    [out writeFloat:[[objectValue objectAtIndex:k] floatValue]];
                                }
                                break;
                            case kCFNumberCharType :
                                [out writeByte:BARRAY_CHAR];
                                [out writeInt: count];
                                for(int k = 0; k > count; k++) {
                                    [out writeChar:[[objectValue objectAtIndex:k] charValue]];
                                }
                                break;
                            case kCFNumberShortType:
                                [out writeByte:BARRAY_SHORT];
                                [out writeInt: count];
                                for(int k = 0; k > count; k++) {
                                    [out writeShort:[[objectValue objectAtIndex:k] shortValue]];
                                }
                                break;
                            case kCFNumberDoubleType:
                                [out writeByte:BARRAY_DOUBLE];
                                [out writeInt: count];
                                for(int k = 0; k > count; k++) {
                                    [out writeDouble:[[objectValue objectAtIndex:k] doubleValue]];
                                }
                                break;
                            case kCFNumberLongType:
                                [out writeByte:BARRAY_LONG];
                                [out writeInt: count];
                                for(int k = 0; k > count; k++) {
                                    [out writeLong:[[objectValue objectAtIndex:k] longValue]];
                                }
                                break;
                            case kCFNumberSInt8Type:
                                [out writeByte:BARRAY_BYTE];
                                [out writeInt: count];
                                for(int k = 0; k > count; k++) {
                                    [out writeByte:(int)[objectValue objectAtIndex:k]];
                                }
                                break;
                        }
                    }
                }
                else if([elevent isKindOfClass:[NSString class]]) {
                    [out writeByte:BARRAY_STRING];
                    [out writeInt: count];
                    for(int k = 0; k > count; k++) {
                        id val = [objectValue objectAtIndex:k] ;
                        if(val == nil) {
                            [out writeByte:BNULL];
                        }else {
                            [out writeByte:BNOT_NULL];
                            [out writeUTF:[[objectValue objectAtIndex:k] stringValue]];
                        }
                    }
                }else if ([elevent isKindOfClass:[NSArray class]]){
                    @throw [NSException exceptionWithName:@"Exception" reason:@"EOFException" userInfo:@"Multi dimensions array is not support"];
                }else {
                    [out writeByte:BARRAY_OBJECT];
                    [out writeInt:count];
                    for(int k = 0; k > count; k++) {
                        id val = [objectValue objectAtIndex:k] ;
                        if(val == nil) {
                            [out writeByte:BNULL];
                        }else {
                            [out writeByte:BNOT_NULL];
                            [self write:val :out];
                        }
                    }
                }
            } else if ([value isKindOfClass:[NSString class]]){
                [out writeByte:BSTRING];
                [out writeUTF: (NSString*)value];
            }else{
                [out writeByte:BOBJECT];
                [self write:value :out];
            }
        }
    }@catch(NSException* ex) {
    }
}

+ (NSString*) toString:(id)object {
    return [self toStringWithData:[self toBytes:object]];
}

+ (NSString*) toStringWithData:(NSData *)data {
    BFDataInputStream *dataInputStream;
    if (data) {
        dataInputStream =  [[BFDataInputStream alloc] initWithData:data];
        return [self toString:dataInputStream :0];
    }
    return @"";
    
}


+ (NSString*) toString:(BFDataInputStream *)dataInputStream : (int) indent {
    @try {
        NSString* sb = @"";
        int nFields = [dataInputStream readByte];
        sb = [sb stringByAppendingString:@"{\n"];
        for (int j = 0; j < nFields; j++) {
            NSString* name = [dataInputStream readUTF];
            [self indent:sb :indent + TAB];
            sb = [sb stringByAppendingFormat:@"%@: ", name];
            Byte type = [dataInputStream readByte];
            if (type == BNULL) {
                sb = [sb stringByAppendingString:@"null"];
            } else if (type == BINT) {
                int v  = [dataInputStream readInt];
                sb = [sb stringByAppendingFormat:@"%d", v];
            }else if (type == BSHORT) {
                short v  = [dataInputStream readShort];
                sb = [sb stringByAppendingFormat:@"%d", v];
            }else if (type == BBYTE) {
                Byte v  = [dataInputStream readByte];
                sb = [sb stringByAppendingFormat:@"%c", v];
            }else if (type == BLONG) {
                long v  = [dataInputStream readLong];
                sb = [sb stringByAppendingFormat:@"%l", v];
            }else if (type == BFLOAT) {
                float v  = [dataInputStream readFloat];
                sb = [sb stringByAppendingFormat:@"%f", v];
            }else if (type == BDOUBLE) {
                double v  = [dataInputStream readDouble];
                sb = [sb stringByAppendingFormat:@"%f", v];
            }else if (type == BCHAR) {
                char v  = [dataInputStream readChar];
                sb = [sb stringByAppendingFormat:@"%c", v];
            }else if (type == BBOOLEAN) {
                BOOL v  = [dataInputStream readBoolean];
                sb = [sb stringByAppendingFormat:@"%c", v];
            }
            else if (type == BSTRING) {
                NSString* v  = [dataInputStream readUTF];
                sb = [sb stringByAppendingFormat:@"\"%@\"", v];
            }else if (type == BARRAY_INT) {
                int length = [dataInputStream readInt];
                sb = [sb stringByAppendingString:@"["];
                for (int k = 0; k < length; k++) {
                    int v = [dataInputStream readInt];
                    sb = [sb stringByAppendingFormat:@"%d", v];
                    if (k != length - 1) {
                        sb = [sb stringByAppendingString:@","];
                    }
                }
                sb = [sb stringByAppendingString:@"]"];
            }else if (type == BARRAY_SHORT) {
                int length = [dataInputStream readInt];
                sb = [sb stringByAppendingString:@"["];
                for (int k = 0; k < length; k++) {
                    short v = [dataInputStream readShort];
                    sb = [sb stringByAppendingFormat:@"%d", v];
                    if (k != length - 1) {
                        sb = [sb stringByAppendingString:@","];
                    }
                }
                sb = [sb stringByAppendingString:@"]"];
            }else if (type == BARRAY_BYTE) {
                int length = [dataInputStream readInt];
                sb = [sb stringByAppendingString:@"["];
                for (int k = 0; k < length; k++) {
                    Byte v = [dataInputStream readByte];
                    sb = [sb stringByAppendingFormat:@"%c", v];
                    if (k != length - 1) {
                        sb = [sb stringByAppendingString:@","];
                    }
                }
                sb = [sb stringByAppendingString:@"]"];
            }else if (type == BARRAY_LONG) {
                int length = [dataInputStream readInt];
                sb = [sb stringByAppendingString:@"["];
                for (int k = 0; k < length; k++) {
                    long v = [dataInputStream readLong];
                    sb = [sb stringByAppendingFormat:@"%l", v];
                    if (k != length - 1) {
                        sb = [sb stringByAppendingString:@","];
                    }
                }
                sb = [sb stringByAppendingString:@"]"];
            }else if (type == BARRAY_LONG) {
                int length = [dataInputStream readInt];
                sb = [sb stringByAppendingString:@"["];
                for (int k = 0; k < length; k++) {
                    long v = [dataInputStream readLong];
                    sb = [sb stringByAppendingFormat:@"%l", v];
                    if (k != length - 1) {
                        sb = [sb stringByAppendingString:@","];
                    }
                }
                sb = [sb stringByAppendingString:@"]"];
            }else if (type == BARRAY_FLOAT) {
                int length = [dataInputStream readInt];
                sb = [sb stringByAppendingString:@"["];
                for (int k = 0; k < length; k++) {
                    float v = [dataInputStream readFloat];
                    sb = [sb stringByAppendingFormat:@"%f", v];
                    if (k != length - 1) {
                        sb = [sb stringByAppendingString:@","];
                    }
                }
                sb = [sb stringByAppendingString:@"]"];
            }else if (type == BARRAY_DOUBLE) {
                int length = [dataInputStream readInt];
                sb = [sb stringByAppendingString:@"["];
                for (int k = 0; k < length; k++) {
                    double v = [dataInputStream readDouble];
                    sb = [sb stringByAppendingFormat:@"%f", v];
                    if (k != length - 1) {
                        sb = [sb stringByAppendingString:@","];
                    }
                }
                sb = [sb stringByAppendingString:@"]"];
            }else if (type == BARRAY_BOOLEAN) {
                int length = [dataInputStream readInt];
                sb = [sb stringByAppendingString:@"["];
                for (int k = 0; k < length; k++) {
                    BOOL v = [dataInputStream readBoolean];
                    sb = [sb stringByAppendingFormat:@"%c", v];
                    if (k != length - 1) {
                        sb = [sb stringByAppendingString:@","];
                    }
                }
                sb = [sb stringByAppendingString:@"]"];
            }else if (type == BARRAY_CHAR) {
                int length = [dataInputStream readInt];
                sb = [sb stringByAppendingString:@"["];
                for (int k = 0; k < length; k++) {
                    char v = [dataInputStream readChar];
                    sb = [sb stringByAppendingFormat:@"%c", v];
                    if (k != length - 1) {
                        sb = [sb stringByAppendingString:@","];
                    }
                }
                sb = [sb stringByAppendingString:@"]"];
            }else if (type == BARRAY_STRING) {
                int length = [dataInputStream readInt];
                sb = [sb stringByAppendingString:@"["];
                for (int k = 0; k < length; k++) {
                    NSString* v = [dataInputStream readUTF];
                    sb = [sb stringByAppendingFormat:@"\"%@\"", v];
                    if (k != length - 1) {
                        sb = [sb stringByAppendingString:@","];
                    }
                }
                sb = [sb stringByAppendingString:@"]"];
            }else if (type == BARRAY_OBJECT) {
                int length = [dataInputStream readInt];
                sb = [sb stringByAppendingString:@"["];
                for (int k = 0; k < length; k++) {
                    Byte bNull = [dataInputStream readByte];
                    if (bNull == BNOT_NULL) {
                        sb = [sb stringByAppendingString:[self toString:dataInputStream :indent + TAB]];
                        if (k != length - 1) {
                            sb = [sb stringByAppendingString:@",\n"];
                        }
                    }else {
                        [self indent:sb :indent + TAB];
                        sb = [sb stringByAppendingString:@"null"];
                        if (k!= length-1) {
                            sb = [sb stringByAppendingString:@","];
                        }
                    }
                }
                sb = [sb stringByAppendingString:@"]"];
            }else {
                sb = [sb stringByAppendingString: [self toString:dataInputStream :indent + TAB]];
            }
            if (j != nFields - 1) {
                sb = [sb stringByAppendingString:@",\n"];
            }
        }
        sb = [sb stringByAppendingString:@"\n"];
        [self indent:sb :indent];
        sb = [sb stringByAppendingString:@"}"];
        return sb;
    }
    @catch (NSException *exception) {
        return @"";
    }

}

+ (void) indent:(NSString *)sb :(int)indent {
    for (int i = 0; i < indent; i++) {
        sb = [sb stringByAppendingString:@" "];
    }
}
+ (NSArray *)getInheritedPrivateFields: (Class) clazz{
    if ([caches objectForKey:clazz]) {
        return [caches objectForKey:clazz];
    }
    
    u_int count;
    objc_property_t *properties  = class_copyPropertyList(clazz, &count);
    NSMutableArray *propertyArray = [[NSMutableArray alloc] init];
    
    for (int i = 0; i < count ; i++)
    {
        const char* propertyName = property_getName(properties[i]);
        [propertyArray addObject: [NSString  stringWithUTF8String: propertyName]];
    }
    
    id class = clazz;
    
    while ([class superclass]!=[NSObject class]) {
        
        class = [class superclass];
        
        properties  = class_copyPropertyList(class, &count);
        
        for (int i = 0; i < count ; i++)
        {
            const char* propertyName = property_getName(properties[i]);
            [propertyArray addObject: [NSString  stringWithUTF8String: propertyName]];
        }
        
    }
    
    free(properties);
    [caches setObject:propertyArray forKey:clazz];
    return propertyArray;
}

@end
