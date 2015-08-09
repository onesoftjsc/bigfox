package vn.com.onesoft.livetube.io.message.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Message {

    public int tag() default 0;

    public String name() default "";
}
