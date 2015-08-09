package vn.com.onesoft.livetube.io.message.object;

import vn.com.onesoft.livetube.io.message.core.annotations.Property;

public class Video {

    @Property(name = "id")
    public int id;
    @Property(name = "title")
    public String title;
    @Property(name = "author")
    public String author;
    @Property(name = "thumbnail")
    public String thumbnail;
    @Property(name = "viewedOrTime")
    public long viewedOrTime;
    @Property(name = "isRecorded")
    public boolean isRecorded;
    @Property(name = "authorId")
    public int authorId;
}
