package vn.com.onesoft.bigfox.server.io.message.base;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vn.com.onesoft.bigfox.server.io.message.annotations.Message;


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

    
        public static Map<String, Class<?>> findObject(String scannedPackage) {
        String scannedPath = scannedPackage.replace(DOT, SLASH);
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
        }
        File scannedDir = new File(scannedUrl.getFile());
        Map<String, Class<?>> classes = new HashMap<String, Class<?>>();
        for (File file : scannedDir.listFiles()) {
            find(file, scannedPackage, classes);
        }
        return classes;
    }

    private static void find(File file, String scannedPackage, Map<String, Class<?>> classes) {
        String resource = scannedPackage + DOT + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                find(child, resource, classes);
            }
        } else if (resource.endsWith(CLASS_SUFFIX)) {
            int endIndex = resource.length() - CLASS_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            try {
                Class<?> cl = Class.forName(className);
                Message msg = cl.getAnnotation(Message.class);
                if (msg != null) {
                    classes.put(msg.name(), cl);
                }
            } catch (ClassNotFoundException ignore) {
            }
        }
    }
}