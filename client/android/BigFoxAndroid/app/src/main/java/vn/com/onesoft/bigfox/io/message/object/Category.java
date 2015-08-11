package vn.com.onesoft.bigfox.io.message.object;

import java.util.List;

import vn.com.onesoft.bigfox.io.message.core.annotations.Property;

public class Category {

	@Property(name = "id")
	public int id;
	@Property(name = "name")
	public String name;
	@Property(name = "videos")
	public List<Video> videos;
}
