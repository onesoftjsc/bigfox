
var appUrl = "ws://localhost:33332/livetube";
var version = 1;
var username = "quanph";
var password = "123456";
var device = "android";
var imei = "imei11111";
var screenW = 320;
var screenH = 460;

var wsManager = new WSManager();
wsManager.connect();

var tags = new Tags();
var msgReaderFactory = new MessageReaderFactory();
var msgBuilder = new MessageBuilder();

for (var property in msgReaderFactory) {
    console.log(msgReaderFactory.tag);
}


function Tags() {
    this.CS_CHAT = 0x1001;
    this.CS_PING = 0x1002;
    this.CS_CLIENT_INFO = 0x1003;

    this.SC_VALIDATION_CODE = 0x110000;
    this.SC_CHAT = 0x110001;
    this.SC_PING = 0x110002;
}

function WSManager() {
    var instance = this;
    var ws;
    this.curMSequence = -1;
    this.curSSequence = -1;
    this.validationCode = -1;
    this.isFirstMessage = true;

    this.connect = function() {
        ws = new WebSocket(appUrl);
        ws.binaryType = 'arraybuffer';
        ws.onmessage = instance.onMessage;
        this.isFirstMessage = true;
    };

    this.send = function(message) {
        ws.send(message.toBytes());
    };

    this.onMessage = function(msg) {
        var byteArr = new Uint8Array(msg.data);
        if (wsManager.isFirstMessage) {
            wsManager.isFirstMessage = false;

            msgReaderFactory.onSValidationCode(byteArr);
        } else {
            for (var i = 4; i < byteArr.length; i++) {
                byteArr[i] = byteArr[i] ^ wsManager.validationCode;

            }
            var mr = new MessageReader(byteArr);
            mr.readHeader();

        }
    };

}

function MessageWriter(tagMessage) {
    var buffer = new Array();
    var ___tag = tagMessage;

    this.writeHeader = function() {
        this.writeInt(0); // length
        this.writeInt(tagMessage); //tag
        this.writeInt(0); //status
        wsManager.curMSequence++;
        this.writeInt(wsManager.curMSequence); //msequence
        this.writeInt(wsManager.curSSequence); //ssequence
        this.writeInt(0);// checksum
    };

    // writeBoolean
    this.writeBoolean = function(byteValue) {
        var data = [];
        data[0] = byteValue;
        buffer[buffer.length] = data;
        return data;
    };
    // writeByte
    this.writeByte = function(byteValue) {
        var data = [];
        data[0] = byteValue;
        buffer[buffer.length] = data;
        return data;
    };
    // writeShort
    this.writeShort = function(shortValue) {
        var data = [];
        data[0] = (shortValue >> 8) & 0xFF;
        data[1] = (shortValue >> 0) & 0xFF;
        buffer[buffer.length] = data;
        return data;
    };
    // writeChar
    this.writeChar = function(charValue) {
        var data = [];
        data[0] = (charValue >> 8) & 0xFF;
        data[1] = (charValue >> 0) & 0xFF;
        buffer[buffer.length] = data;
        return data;
    };
    // writeInt
    this.writeInt = function(intValue) {
        var data = [];
        data[0] = (intValue >> 24) & 0xFF;
        data[1] = (intValue >> 16) & 0xFF;
        data[2] = (intValue >> 8) & 0xFF;
        data[3] = (intValue >> 0) & 0xFF;
        buffer[buffer.length] = data;
        return data;
    };
    // writeLong
    this.writeLong = function(longValue) {
        var data = [];
        data[0] = (longValue >> 56) & 0xFF;
        data[1] = (longValue >> 48) & 0xFF;
        data[2] = (longValue >> 40) & 0xFF;
        data[3] = (longValue >> 32) & 0xFF;
        data[5] = (longValue >> 24) & 0xFF;
        data[6] = (longValue >> 16) & 0xFF;
        data[7] = (longValue >> 8) & 0xFF;
        data[8] = (longValue >> 0) & 0xFF;
        buffer[buffer.length] = data;
        return data;
    };

    // writeUTF
    this.writeUTF = function(str) {

        var utf8 = [];
        for (var i = 0; i < str.length; i++) {
            var charcode = str.charCodeAt(i);
            if (charcode < 0x80)
                utf8.push(charcode);
            else if (charcode < 0x800) {
                utf8.push(0xc0 | (charcode >> 6), 0x80 | (charcode & 0x3f));
            } else if (charcode < 0xd800 || charcode >= 0xe000) {
                utf8.push(0xe0 | (charcode >> 12),
                        0x80 | ((charcode >> 6) & 0x3f),
                        0x80 | (charcode & 0x3f));
            } else {// surrogate pair
                i++;
                // UTF-16 encodes 0x10000-0x10FFFF by subtracting 0x10000 and
                // splitting the 20 bits of 0x0-0xFFFFF into two halves
                charcode = 0x10000 + (((charcode & 0x3ff) << 10) | (str
                        .charCodeAt(i) & 0x3ff));
                utf8.push(0xf0 | (charcode >> 18),
                        0x80 | ((charcode >> 12) & 0x3f),
                        0x80 | ((charcode >> 6) & 0x3f),
                        0x80 | (charcode & 0x3f));
            }
        }

        this.writeShort(utf8.length);// Viết theo kiểu UTF
        buffer[buffer.length] = utf8;

    };

    // writeByteArray
    this.writeByteArray = function(byteArrayValue) {
        if (byteArrayValue === null)
            writeByte(0);
        else {
            writeByte(1);
            writeInt(byteArrayValue.length);
            buffer[byteArrayValue] = utf8;
        }
    };

    this.getBytes = function() {
        var dataBuffer = [];
        var dataLeng = 0;
        var numberBuffer = buffer.length;
        var bufferElement;
        var elementLength;
        for (var i = 0; i < numberBuffer; i++) {
            bufferElement = buffer[i];
            if (bufferElement !== null) {
                elementLength = bufferElement.length;
                for (var j = 0; j < elementLength; j++) {
                    dataBuffer[dataLeng] = bufferElement[j];
                    dataLeng++;
                }
            }
        }
        var intValue = dataBuffer.length;
        dataBuffer[0] = (intValue >> 24) & 0xFF;
        dataBuffer[1] = (intValue >> 16) & 0xFF;
        dataBuffer[2] = (intValue >> 8) & 0xFF;
        dataBuffer[3] = (intValue >> 0) & 0xFF;
        var bytearray = new Uint8Array(intValue);
        var contentBasic = "";
        var contentObfuscation = "";
        for (var i = 0; i < dataBuffer.length; i++) {
            contentBasic = contentBasic + dataBuffer[i] + ",";
            if (i > 3)
                bytearray[i] = ((dataBuffer[i] ^ wsManager.validationCode) & 0x00ff);
            else
                bytearray[i] = dataBuffer[i];
            contentObfuscation = contentObfuscation + bytearray[i] + ",";
        }
        return bytearray;
    };

}

function MessageReader(dataArray) {
    var currentInstance = this;

    this._length = -1;
    this._tag = -1;
    this._status = -1;
    this._mSequence = -1;
    this._sSequence = -1;
    this._checksum = -1;

    var buffer = dataArray;
    var bufferLength = dataArray.length;
    var currentPosition = 0;

    this.avaiable = function() {
        return bufferLength - currentPosition;
    };

    this.getBuffer = function(numberByte) {
        if (currentPosition + numberByte > bufferLength) {
            return null;
        }
        var dataByte = [];
        for (var i = 0; i < numberByte; i++) {
            dataByte[i] = buffer[currentPosition];
            currentPosition++;
        }
        return dataByte;
    };

    this.readHeader = function() {
        this._length = currentInstance.readInt();
        this._tag = currentInstance.readInt();
        this._status = currentInstance.readInt();
        this._mSequence = currentInstance.readInt();
        this._sSequence = currentInstance.readInt();
        this._checksum = currentInstance.readInt();
    };

    this.readBoolean = function(name) {
        var data = this.getBuffer(1);
        return data[0];
    };

    this.readByte = function() {
        var data = this.getBuffer(1);
        return data[0];
    };

    this.readShort = function() {
        var data = this.getBuffer(2);
        return (data[0] << 8) + (data[1] << 0);
    };

    this.readChar = function() {
        var data = this.getBuffer(2);
        return (data[0] << 8) + (data[1] << 0);
    };
    this.readInt = function() {
        var data = this.getBuffer(4);
        return (data[0] << 24) + (data[1] << 16) + (data[2] << 8)
                + (data[3] << 0);
    };
    this.readLong = function() {
        var data = this.getBuffer(8);
        return (data[0] << 56) + (data[1] << 48) + (data[2] << 40)
                + (data[3] << 32) + (data[4] << 24) + (data[5] << 16)
                + (data[6] << 8) + (data[7] << 0);
    };

    this.readUTF = function() {
        var shortLength = this.readShort();
        var arrayConvertToString = this.getBuffer(shortLength);
        var out, i, len, c;
        var char2, char3;
        out = "";
        len = arrayConvertToString.length;
        i = 0;
        while (i < len) {
            c = arrayConvertToString[i++];
            switch (c >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    // 0xxxxxxx
                    out += String.fromCharCode(c);
                    break;
                case 12:
                case 13:
                    // 110x xxxx 10xx xxxx
                    char2 = arrayConvertToString[i++];
                    out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));
                    break;
                case 14:
                    // 1110 xxxx 10xx xxxx 10xx xxxx
                    char2 = arrayConvertToString[i++];
                    char3 = arrayConvertToString[i++];
                    out += String.fromCharCode(((c & 0x0F) << 12)
                            | ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0));
                    break;
            }
        }
        return out;
    };
}

function MessageReaderFactory() {
    this.tag = 1;
    this.onSValidationCode = function(byteArr) {
        this.tag = Tags.SC_VALIDATION_CODE;
        var mr = new MessageReader(byteArr);
        mr.readHeader();
        wsManager.validationCode = mr.readInt();

        wsManager.send(new msgBuilder.CSClientInfo());
        wsManager.send(new msgBuilder.CSChat("Hello WebRtc"));
    };


    this.onSChat = function(byteArr) {
        this.tag = Tags.SC_CHAT;
        var mr = new MessageReader(byteArr);
        mr.readHeader();
        var msg = mr.readUTF();
        alert(msg);
    };
}

function MessageBuilder() {
    this.CSClientInfo = function() {
        this.toBytes = function() {
            var mr = new MessageWriter(tags.CS_CLIENT_INFO);
            mr.writeHeader();
            mr.writeByte(1);
            mr.writeInt(version);
            mr.writeUTF(device);
            mr.writeUTF(username); // username
            mr.writeUTF(password); // password
            mr.writeInt(0); // type
            mr.writeUTF(imei); // imei
            mr.writeUTF("device_info"); // device_info
            mr.writeInt(screenW); // screen width
            mr.writeInt(screenH); // screen height

            return mr.getBytes();
        };
    };

    this.CSPingReq = function() {
        this.toBytes = function() {
            var mr = new MessageWriter(tags.CS_PING_REQ);
            mr.writeHeader();
            return mr.getBytes();
        };
    };

    this.CSChat = function(msg) {
        this.toBytes = function() {
            var mr = new MessageWriter(tags.CS_CHAT);
            mr.writeHeader();
            mr.writeUTF(msg);
            return mr.getBytes();
        };
    };

}

