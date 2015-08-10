package vn.com.onesoft.bigfox.server.io.message.core;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClassFinder {

	private static final char DOT = '.';

	private static final char SLASH = '/';

	private static final String CLASS_SUFFIX = ".class";

	private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

	public static List<Class<?>> find(String scannedPackage) {
		return find(scannedPackage, Object.class);
	}

	public static List<Class<?>> find(String scannedPackage, Class<?> parentType) {
		String scannedPath = scannedPackage.replace(DOT, SLASH);
		URL scannedUrl = Thread.currentThread().getContextClassLoader()
				.getResource(scannedPath);
		if (scannedUrl == null) {
			throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR,
					scannedPath, scannedPackage));
		}
		File scannedDir = new File(scannedUrl.getFile());
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (File file : scannedDir.listFiles()) {
			classes.addAll(find(file, scannedPackage, parentType));
		}
		return classes;
	}

	private static List<Class<?>> find(File file, String scannedPackage,
			Class<?> parentType) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		String resource = scannedPackage + DOT + file.getName();
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				classes.addAll(find(child, resource, parentType));
			}
		} else if (resource.endsWith(CLASS_SUFFIX)) {
			int endIndex = resource.length() - CLASS_SUFFIX.length();
			String className = resource.substring(0, endIndex);
			try {
				Class<?> cl = Class.forName(className);
				if (parentType.isAssignableFrom(cl)) {
					classes.add(Class.forName(className));
				}
			} catch (ClassNotFoundException ignore) {
			}
		}
		return classes;
	}

}