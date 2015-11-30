package vn.com.onesoft.bigfox.io.core.session;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;
import vn.com.onesoft.bigfox.MainActivity;
import vn.com.onesoft.bigfox.io.message.annotations.Message;

public class ClassFinder {

	private static final char DOT = '.';

	private static final char SLASH = '/';

	private static final String CLASS_SUFFIX = ".class";

	private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

	public static List<Class<?>> find(Class<?> parentType) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		String packageName = parentType.getPackage().getName();
		try {
			DexFile df = new DexFile(MainActivity.getInstance()
					.getPackageCodePath());
			for (Enumeration<String> iter = df.entries(); iter
					.hasMoreElements(); ) {
				String className = iter.nextElement();
				try {
					if (className.startsWith(packageName)) {
						Class<?> cl = Class.forName(className);
						if (parentType.isAssignableFrom(cl)
								&& !cl.isAssignableFrom(parentType)) {
							classes.add(cl);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
		}
		return classes;
	}

	public static Map<String, Class<?>> findObjects(Class<?> parentType) {
		Map<String, Class<?>> classes = new HashMap<String, Class<?>>();
		String packageName = parentType.getPackage().getName();
		try {
			DexFile df = new DexFile(MainActivity.getInstance()
					.getPackageCodePath());
			for (Enumeration<String> iter = df.entries(); iter
					.hasMoreElements(); ) {
				String className = iter.nextElement();
				if (className.startsWith(packageName)) {
					try {
						Class<?> cl = Class.forName(className);
						Message msg = cl.getAnnotation(Message.class);
						if (msg != null) {
//                        Logger.log("put: " + msg.name());
							classes.put(msg.name(), cl);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
		}
		return classes;
	}
}
