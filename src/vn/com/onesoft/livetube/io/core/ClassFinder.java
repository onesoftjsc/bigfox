package vn.com.onesoft.livetube.io.core;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import vn.com.onesoft.livetube.Logger;
import vn.com.onesoft.livetube.MainActivity;
import vn.com.onesoft.livetube.io.message.sc.SCValidationCode;
import dalvik.system.DexFile;

public class ClassFinder {

	private static final char DOT = '.';

	private static final char SLASH = '/';

	private static final String CLASS_SUFFIX = ".class";

	private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

	public static List<Class<?>> find(Class<?> parentType) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		String packageName = SCValidationCode.class.getPackage().getName();
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
