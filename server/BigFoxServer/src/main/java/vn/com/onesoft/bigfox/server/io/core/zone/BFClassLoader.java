/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.zone;

import com.google.common.collect.MapMaker;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;

/**
 *
 * @author QuanPH
 */
public class BFClassLoader extends ClassLoader {

    private Map<String, Class> mapPathToClass = new MapMaker().makeMap();

    public BFClassLoader(ClassLoader parent) {
        super(parent);
    }

    public Class loadFile(String classPath) throws IOException, FileNotFoundException {
        File file = new File(classPath);
        URL myUrl = file.toURL();
        URLConnection connection = myUrl.openConnection();
        InputStream input = connection.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int data = input.read();

        while (data != -1) {
            buffer.write(data);
            data = input.read();
        }

        input.close();

        byte[] classData = buffer.toByteArray();

        String className = classPath.replace(File.separatorChar, '.').substring(0, classPath.length() - 6);
        className = className.substring(className.indexOf(".vn.") + 1);
        BFLogger.getInstance().info("define class " + className);
        Class cl = defineClass(className,
                classData, 0, classData.length);
        mapPathToClass.put(classPath, cl);

        return cl;
    }

    public void loadFolder(String foldPath) throws IOException {
        File folder = new File(foldPath);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getAbsolutePath().contains(".class")) {
                loadFile(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                loadFolder(file.getAbsolutePath());
            }
        }
    }

    public Map<String, Class> getMapPathToClass() {
        return mapPathToClass;
    }
    

}
