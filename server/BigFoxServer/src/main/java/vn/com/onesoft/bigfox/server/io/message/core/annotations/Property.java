package vn.com.onesoft.bigfox.server.io.message.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Property {
      public String name() default "";
}
