/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.cs;

import io.netty.channel.Channel;
import java.io.FileOutputStream;
import vn.com.onesoft.livetube.io.message.core.MessageIn;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;
import vn.com.onesoft.livetube.io.message.sc.SCUpload;
import vn.com.onesoft.livetube.io.session.BFSessionManager;
import vn.com.onesoft.livetube.main.Main;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_UPLOAD, name = "CS_UPLOAD")
public class CSUpload extends MessageIn {

    @Property(name = "byteArray")
    private byte[] byteArray;

    @Override
    public void execute(Channel channel) {

        try {
            //convert array of bytes into file
            FileOutputStream fileOuputStream
                    = new FileOutputStream("G:\\livetube\\code\\core\\WebApplicationUpload\\build\\web\\livetube\\2015\\08\\06\\anh1.gif");
            fileOuputStream.write(byteArray);
            fileOuputStream.close();

            Main.logger.info("Upload success");
            String urlUpload = "http://192.168.1.172:8080/WebApplicationUpload/livetube/2015/08/06/Hydrangeas.jpg";
            BFSessionManager.getInstance().sendMessage(channel, new SCUpload(urlUpload));
        } catch (Exception e) {
            Main.logger.error(e.getMessage(), e);
        }

    }
}
