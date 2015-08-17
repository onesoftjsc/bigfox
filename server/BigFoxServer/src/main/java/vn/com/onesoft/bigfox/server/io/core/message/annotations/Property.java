package vn.com.onesoft.bigfox.server.io.core.annotat.messageions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Property {
      public String name() default "";
}
