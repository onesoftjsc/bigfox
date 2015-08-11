package vn.com.onesoft.bigfox.io.message.object;

import vn.com.onesoft.bigfox.io.message.core.annotations.Property;

public class Subcriber {

    @Property(name = "id")
    public int id;
    @Property(name = "name")
    public String name;
    @Property(name = "following")
    public int following;
}
