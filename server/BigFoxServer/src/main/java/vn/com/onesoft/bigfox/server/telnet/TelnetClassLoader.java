/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.telnet;

import com.google.common.collect.MapMaker;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.main.Main;

/**
 *
 * @author QuanPH
 */
public class TelnetClassLoader extends ClassLoader {

    private Map<String, Class> mapPathToClass = new MapMaker().makeMap();

    private static TelnetClassLoader _instance = null;

    private TelnetClassLoader(ClassLoader parrent) {
        super(parrent);
    }

    public static TelnetClassLoader getInstance() {
        if (_instance == null) {
            _instance = new TelnetClassLoader(Main.class.getClassLoader());
        }
        return _instance;
    }

    public void loadJar(String pathToJar) throws IOException {
        java.util.jar.JarFile jar = new java.util.jar.JarFile(pathToJar);
        java.util.Enumeration enumEntries = jar.entries();
        while (enumEntries.hasMoreElements()) {
            java.util.jar.JarEntry file = (java.util.jar.JarEntry) enumEntries.nextElement();
            if (!file.getName().contains(".class")) {
                continue;
            }
            if (Main.isDebug && !file.getName().contains("CMD")) {
                continue;
            }
            java.io.InputStream input = jar.getInputStream(file);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();

            while (data != -1) {
                buffer.write(data);
                data = input.read();
            }

            input.close();

            byte[] classData = buffer.toByteArray();
            String classPath = file.getName();
            String className = classPath.replace(File.separatorChar, '.').substring(0, classPath.length() - 6);
            className = className.replace('/', '.').substring(0, classPath.length() - 6);
            className = className.substring(className.indexOf(".vn.") + 1);
            BFLogger.getInstance().info("define class " + className);
            Class cl = defineClass(className,
                    classData, 0, classData.length);
            mapPathToClass.put(classPath, cl);

        }
    }
    
    public Class getClass(String path) throws ClassNotFoundException {
        if (mapPathToClass.get(path) != null)
            return mapPathToClass.get(path);
        else
            return loadClass(path);
    }
}
