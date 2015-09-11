/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.server.helper.classmanager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import static javassist.bytecode.InnerClassesAttribute.tag;
import vn.com.onesoft.bigfox.server.io.message.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.io.message.base.MessageExecute;
import vn.com.onesoft.bigfox.server.io.message.base.MessageIn;
import vn.com.onesoft.bigfox.server.main.Main;

/**
 *
 * @author TOSHIBA
 */
public class ClassReloader extends ClassLoader {

    public ClassReloader(ClassLoader parent) {
        super(parent);
    }

    private Class doLoadClass(String classPath) {
        try {
//            String mainPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//            mainPath = mainPath.substring(0, mainPath.lastIndexOf(File.separatorChar) + 1);
//            String fullPath = mainPath + classPath.replace('.', File.separatorChar);
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
            return defineClass(className,
                    classData, 0, classData.length);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void reloadClass(String classPath) {
        try {
            ClassReloader classReloader = new ClassReloader(Main.class.getClassLoader());
            Class cls = classReloader.doLoadClass(classPath);
            MessageIn mess = (MessageIn) cls.newInstance();
            Message m = (Message)mess.getClass().getAnnotation(Message.class);
            MessageExecute.mapTagToUserMessage.put(m.tag(), mess);
        } catch (Exception ex) {
            BFLogger.getInstance().error(ex.getMessage(), ex);
        }

    }

}
