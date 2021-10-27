package com.example.demo;

import java.lang.annotation.*;

/**
 * @author zhangjun
 * @date 2021/6/15  16:23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface WebLog {
    String value() default "";

    String data() default "";
}
