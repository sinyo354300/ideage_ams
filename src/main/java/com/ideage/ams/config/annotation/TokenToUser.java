package com.ideage.ams.config.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenToUser {

    /**
     * ユーザーID
     *
     * @return　ユーザーID
     */
    String value() default "user";

}
