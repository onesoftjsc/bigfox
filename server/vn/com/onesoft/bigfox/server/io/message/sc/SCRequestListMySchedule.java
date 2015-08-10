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
@Message(tag = Tags.SC_REQUEST_LIST_MY_SCHEDULE, name = "SC_REQUEST_LIST_MY_SCHEDULE")
public class SCRequestListMySchedule extends MessageOut {

    @Property(name = "categories")
    public List<Category> categories;

    public SCRequestListMySchedule(List<Category> categories) {
        super();
        this.categories = categories;
    }

}
