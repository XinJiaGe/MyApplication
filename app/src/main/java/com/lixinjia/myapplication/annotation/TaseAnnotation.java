package com.lixinjia.myapplication.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：忻佳
 * 日期：2019-10-23
 * 描述：注解测试
 *
 * @Deprecated  过时注解
 * @Override    提示子类要复写父类中被 @Override 修饰的方法
 * @SuppressWarnings   阻止警告的意思 ，比如不让过时代码有横线
 * @SafeVarargs   参数安全类型注解
 */

/**
 * RetentionPolicy.SOURCE 注解只在源码阶段保留，在编译器进行编译时它将被丢弃忽视。
 * RetentionPolicy.CLASS 注解只被保留到编译进行的时候，它并不会被加载到 JVM 中
 * RetentionPolicy.RUNTIME 注解可以保留到程序运行的时候，它会被加载进入到 JVM 中，所以在程序运行时可以获取到它们。
 */
@Retention(RetentionPolicy.RUNTIME)
/**
 * @Documented   顾名思义，这个元注解肯定是和文档有关。它的作用是能够将注解中的元素包含到 Javadoc 中去。
 */

/**
 * @Target   @Target 指定了注解运用的地方。
 * ElementType.ANNOTATION_TYPE 可以给一个注解进行注解
 * ElementType.CONSTRUCTOR 可以给构造方法进行注解
 * ElementType.FIELD 可以给属性进行注解
 * ElementType.LOCAL_VARIABLE 可以给局部变量进行注解
 * ElementType.METHOD 可以给方法进行注解
 * ElementType.PACKAGE 可以给一个包进行注解
 * ElementType.PARAMETER 可以给一个方法内的参数进行注解
 * ElementType.TYPE 可以给一个类型进行注解，比如类、接口、枚举
 */
//@Target(ElementType.TYPE)

/**
 * @Inherited
 * Inherited 是继承的意思，但是它并不是说注解本身可以继承，而是说如果一个超类被 @Inherited 注解过的注解进行注解的话，那么如果它的子类没有被任何注解应用的话，那么这个子类就继承了超类的注解。
 *
 * @Test
 * public class A {}
 * public class B extends A {}
 *
 * 可以这样理解：
 * 老子非常有钱，所以人们给他贴了一张标签叫做富豪。
 * 老子的儿子长大后，只要没有和老子断绝父子关系，虽然别人没有给他贴标签，但是他自然也是富豪。
 * 老子的孙子长大了，自然也是富豪。
 */

/**
 * @Repeatable
 * Repeatable 自然是可重复的意思。@Repeatable 是 Java 1.8 才加进来的，所以算是一个新的特性。
 *
 * @interface Persons {
 * 	Person[]  value();
 * }
 * @Repeatable(Persons.class)
 * @interface Person{
 * 	String role default "";
 * }
 * @Person(role="artist")
 * @Person(role="coder")
 * @Person(role="PM")
 * public class SuperMan{
 * }
 *
 */

public @interface TaseAnnotation {

    String name() default "";
    int id() default 0;
}
