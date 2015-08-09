package vn.com.onesoft.livetube.io.message.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;

public class Category implements Serializable {

    @Property(name = "id")
    public int id;
    @Property(name = "name")
    public String name;
    @Property(name = "icon")
    public String icon;
    @Property(name = "videos")
    public List<Video> videos;

    public Category() {
        videos = new ArrayList<>();
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        videos = new ArrayList<>();
    }

    public Category(int id, String name, List<Video> videos) {
        this.id = id;
        this.name = name;
        this.videos = videos;

    }

    public Category(int id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        videos = new ArrayList<>();
    }

    public Category(int id, String name, List<Video> videos, String icon) {
        this.id = id;
        this.name = name;
        this.videos = videos;
        this.icon = icon;
    }

}
