/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.business.zone;

import com.google.common.collect.MapMaker;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.main.Main;

/**
 *
 * @author QuanPH
 */
public class BFClassLoaderZone extends ClassLoader {

    private Map<String, Class> mapPathToClass = new MapMaker().makeMap();
    private Hashtable classes = new Hashtable();
    IBFZone zone;

    public BFClassLoaderZone(ClassLoader parent, IBFZone zone) {
        super(parent);
        this.zone = zone;
    }

    public void loadJar(String absolutePath) throws IOException {
        java.util.jar.JarFile jar = new java.util.jar.JarFile(absolutePath);
        java.util.Enumeration enumEntries = jar.entries();
        while (enumEntries.hasMoreElements()) {
            java.util.jar.JarEntry file = (java.util.jar.JarEntry) enumEntries.nextElement();
            if (!file.getName().contains(".class"))
                continue;
            if (Main.isDebug && !file.getName().contains("CS") && !file.getName().contains("SC") )
                continue;
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

    @Override
    public Class loadClass(String className) throws ClassNotFoundException {
        BFLogger.getInstance().info("BFClassLoader load " + className);
        return findClass(className);
    }

    @Override
    public Class findClass(String className) {
        byte classByte[];
        Class result = null;

        result = (Class) classes.get(className); //checks in cached classes  
        if (result != null) {
            return result;
        }

        try {
            return findSystemClass(className);
        } catch (Exception e) {
        }

        File file = new File(zone.getAbsolutePath() + File.separatorChar + "lib");
        if (!file.exists()) {
            return null;
        }
        File[] files = file.listFiles();
        for (File fileJar : files) {
            try {
                JarFile jar = new JarFile(fileJar);
                String jarName = className.replace('.', File.separatorChar) + ".class";
                JarEntry entry = jar.getJarEntry(jarName);
                InputStream is = jar.getInputStream(entry);
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                int nextValue = is.read();
                while (-1 != nextValue) {
                    byteStream.write(nextValue);
                    nextValue = is.read();
                }

                classByte = byteStream.toByteArray();

                result = defineClass(className, classByte, 0, classByte.length, null);
                classes.put(className, result);
                return result;
            } catch (Exception e) {

            }
        }
        return null;
    }
}
