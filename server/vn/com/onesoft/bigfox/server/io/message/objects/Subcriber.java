package vn.com.onesoft.livetube.io.message.objects;

import vn.com.onesoft.livetube.io.message.core.annotations.Property;

public class Subcriber {
	@Property(name = "id")
	public int id;
	@Property(name = "name")
	public String name;
	@Property(name = "following")
	public int following;
}
