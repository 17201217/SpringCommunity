package com.nowcoder.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
@Target:用于指定自定义注解的使用范围，ElementType.METHOD作用于方法
 */
/*
@Retention用于修饰自定义注解的生存周期，或者可以保存多久
RetentionPolicy.RUNTIME：编译器将把注解记录在class文件中，
                        当运行java程序时，虚拟机保留注解，程序可以通过反射获取该注解；
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {

}
