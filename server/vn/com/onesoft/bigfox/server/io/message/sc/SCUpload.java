/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.sc;

import vn.com.onesoft.livetube.io.message.core.MessageOut;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.SC_UPLOAD, name = "SC_UPLOAD")
public class SCUpload extends MessageOut {

    @Property(name = "urlUpload")
    private final String urlUpload;

    public SCUpload(String urlUpload) {
        super();
        this.urlUpload = urlUpload;
    }

}
