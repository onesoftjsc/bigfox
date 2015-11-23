/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.bigfox.io.message.user.cs;

import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.annotations.Property;
import vn.com.onesoft.bigfox.io.message.base.MessageOut;
import vn.com.onesoft.bigfox.io.message.user.tags.Tags;

/**
 *
 * @author QuanPH
 */
@Message(tag = Tags.CS_DATA, name = "CS_DATA")
public class CSData extends MessageOut{
    @Property(name = "xCor")
    private int x;
    @Property(name = "yCor")
    private int y;

    
    public CSData(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
