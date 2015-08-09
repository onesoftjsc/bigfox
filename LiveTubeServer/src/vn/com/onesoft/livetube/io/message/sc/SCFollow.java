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

@Message(tag = Tags.SC_FOLLOW, name = "SC_FOLLOW")
public class SCFollow extends MessageOut {

    @Property(name = "responseCode")
    public int responseCode;
    @Property(name = "subcriberId")
    public int subcriberId;
    @Property(name = "currentStatus")
    public int currentStatus; // 0: following, 1: unfollow
    public static final int STATUS_FOLLOWING = 0;
    public static final int STATUS_UNFOLLOW = 1;

    public SCFollow(int responseCode, int subcriberId, int currentStatus) {
        super();
        this.responseCode = responseCode;
        this.subcriberId = subcriberId;
        this.currentStatus = currentStatus;
    }

}
