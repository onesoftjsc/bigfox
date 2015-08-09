/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.sc;

import java.util.List;
import vn.com.onesoft.livetube.io.message.core.MessageOut;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;
import vn.com.onesoft.livetube.io.message.objects.Category;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.SC_REQUEST_LIST_VIDEO_UP_COMING, name = "SC_REQUEST_LIST_VIDEO_UP_COMING")
public class SCRequestListVideoUpComing extends MessageOut {

    @Property(name = "categories")
    public List<Category> categories;

    public SCRequestListVideoUpComing(List<Category> categories) {
        super();
        this.categories = categories;
    }

}
