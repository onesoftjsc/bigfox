package vn.com.onesoft.bigfox.io.core.session;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import vn.com.onesoft.bigfox.MainActivity;
import vn.com.onesoft.bigfox.io.message.core.sc.SCValidationCode;
import dalvik.system.DexFile;

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
					.hasMoreElements();) {
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

}