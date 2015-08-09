package vn.com.onesoft.livetube.io.message.objects;

import java.sql.Timestamp;
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
    //huongns add
    @Property(name="privacy")
    public int privacy;// 1: public |  2: private | 3: subscribe
    @Property(name = "chat")
    public int chat;// 1: on chat | 2: off chat
    @Property(name = "description")
    public String description;
    @Property(name = "created_date")
    public long created_date;
    @Property(name = "start_time")
    public long start_time;
    @Property(name = "end_time")
    public long end_time;
    
    public Video() {
    }

    public Video(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Video(int id, String title, String author, String thumbnail, long viewedOrTime, boolean isRecorded, int authorId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.thumbnail = thumbnail;
        this.viewedOrTime = viewedOrTime;
        this.isRecorded = isRecorded;
        this.authorId = authorId;
    }

    public Video(int id, String title, String author, long viewedOrTime, boolean isRecorded, int authorId, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.viewedOrTime = viewedOrTime;
        this.isRecorded = isRecorded;
        this.authorId = authorId;
        this.description = description;
    }

   
    

}
