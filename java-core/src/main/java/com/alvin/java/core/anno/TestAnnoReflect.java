package com.alvin.java.core.anno;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.AnnotatedWildcardType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.List;

@FullAnnoTest("testAnnoReflect")
public class TestAnnoReflect<@FullAnnoTest("parameter") T > extends @FullAnnoTest("parent")ParentObj {

    @FullAnnoTest("constructor")
    TestAnnoReflect() {
    }

    //注解字段域
    private @FullAnnoTest("name") String name;
    //注解泛型字段域
    private @FullAnnoTest("value") T value;
    //注解通配符
    private @FullAnnoTest("list") List<@FullAnnoTest("generic") ?> list;
    //注解方法
    @FullAnnoTest("method")                       //注解方法参数
    public String hello(@FullAnnoTest("methodParameter") String name)
            throws @FullAnnoTest("Exception") Exception { // 注解抛出异常
        //注解局部变量,现在运行时暂时无法获取（忽略）
        @FullAnnoTest("result") String result;
        result = "siting";
        System.out.println(name);
        return  result;
    }

    public static void main(String[] args) throws Exception {

        TestAnnoReflect<String>  TestAnnoReflect = new TestAnnoReflect<> ();
        Class<TestAnnoReflect<Object>> clazz = (Class<TestAnnoReflect<Object>>) TestAnnoReflect.getClass();
        //class的注解
        Annotation[] annotations = clazz.getAnnotations();
        FullAnnoTest testTmp = (FullAnnoTest) annotations[0];
        System.out.println("修饰TestAnnoReflect.class注解value: "+testTmp.value());
        //构造器的注解
        Constructor<TestAnnoReflect<Object>> constructor = (Constructor<TestAnnoReflect<Object>>) clazz.getDeclaredConstructors()[0];
        testTmp = constructor.getAnnotation(FullAnnoTest.class);
        System.out.println("修饰构造器的注解value: "+testTmp.value());
        //继承父类的注解
        AnnotatedType annotatedType = clazz.getAnnotatedSuperclass();
        testTmp = annotatedType.getAnnotation(FullAnnoTest.class);
        System.out.println("修饰继承父类的注解value: "+testTmp.value());
        //注解的注解
        AnnoTest AnnoTest = testTmp.annotationType().getAnnotation(AnnoTest.class);
        System.out.println("修饰注解的注解AnnoTest-value: "+AnnoTest.value());
        //泛型参数 T 的注解
        TypeVariable<Class<TestAnnoReflect<Object>>> variable = clazz.getTypeParameters()[0];
        testTmp = variable.getAnnotation(FullAnnoTest.class);
        System.out.println("修饰泛型参数T注解value: "+testTmp.value());
        //普通字段域 的注解
        Field[] fields = clazz.getDeclaredFields();
        Field nameField = fields[0];
        testTmp = nameField.getAnnotation(FullAnnoTest.class);
        System.out.println("修饰普通字段域name注解value: "+testTmp.value());
        //泛型字段域 的注解
        Field valueField = fields[1];
        testTmp = valueField.getAnnotation(FullAnnoTest.class);
        System.out.println("修饰泛型字段T注解value: "+testTmp.value());
        //通配符字段域 的注解
        Field listField = fields[2];
        AnnotatedParameterizedType annotatedPType = (AnnotatedParameterizedType)listField.getAnnotatedType();
        testTmp = annotatedPType.getAnnotation(FullAnnoTest.class);
        System.out.println("修饰泛型注解value: "+testTmp.value());
        //通配符注解 的注解
        AnnotatedType[] annotatedTypes = annotatedPType.getAnnotatedActualTypeArguments();
        AnnotatedWildcardType annotatedWildcardType = (AnnotatedWildcardType) annotatedTypes[0];
        testTmp = annotatedWildcardType.getAnnotation(FullAnnoTest.class);
        System.out.println("修饰通配符注解value: "+testTmp.value());
        //方法的注解
        Method method = clazz.getDeclaredMethod("hello", String.class);
        annotatedType = method.getAnnotatedReturnType();
        testTmp = annotatedType.getAnnotation(FullAnnoTest.class);
        System.out.println("修饰方法的注解value: "+testTmp.value());
        //异常的注解
        annotatedTypes =  method.getAnnotatedExceptionTypes();
        testTmp = annotatedTypes[0].getAnnotation(FullAnnoTest.class);
        System.out.println("修饰方法抛出错误的注解value: "+testTmp.value());
        //方法参数的注解
        annotatedTypes = method.getAnnotatedParameterTypes();
        testTmp = annotatedTypes[0].getAnnotation(FullAnnoTest.class);
        System.out.println("修饰方法参数注解value: "+testTmp.value());
        //包的注解
        Package p = Package.getPackage("com.alvin.java.core.anno");
        testTmp = p.getAnnotation(FullAnnoTest.class);
        System.out.println("修饰package注解value: "+testTmp.value());
        TestAnnoReflect.hello("hello");
    }

}
