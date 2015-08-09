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
import vn.com.onesoft.livetube.io.message.objects.Subcriber;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.SC_REQUEST_LIST_SUBCRIBERS, name = "SC_REQUEST_LIST_SUBCRIBERS")
public class SCRequestListSubcribers extends MessageOut {

    @Property(name = "subcribers")
    public  List<Subcriber> subcribers;

    public SCRequestListSubcribers(List<Subcriber> subcribers) {
        super();
        this.subcribers = subcribers;
    }
    
    
}
