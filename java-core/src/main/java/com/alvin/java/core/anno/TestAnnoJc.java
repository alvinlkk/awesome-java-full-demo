package com.alvin.java.core.anno;

import org.springframework.core.annotation.AnnotatedElementUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/12/13  16:04
 * @version: 1.0.0
 */
@Slf4j(topic = "c.TestAnnoJc")
public class TestAnnoJc {

    public static void main(String[] args) throws NoSuchMethodException {

        Parent parent = new Parent();
        log.info("ParentClass: {}", getAnnoValue(parent.getClass().getAnnotation(TestAnnotation.class)));
        log.info("ParentMethod: {}", getAnnoValue(parent.getClass().getMethod("method").getAnnotation(TestAnnotation.class)));

        Child child = new Child();
        log.info("ChildClass: {}", getAnnoValue(child.getClass().getAnnotation(TestAnnotation.class)));
        //log.info("ChildMethod: {}", getAnnoValue(child.getClass().getMethod("method").getAnnotation(TestAnnotation.class)));

        log.info("ChildMethod: {}", getAnnoValue(AnnotatedElementUtils.findMergedAnnotation(child.getClass().getMethod("method"), TestAnnotation.class)));

    }

    private static String getAnnoValue(TestAnnotation annotation) {
        if(annotation == null) {
            return "未找到注解";
        }

        return annotation.value();
    }


    @TestAnnotation(value = "Class")
    static class Parent {

        @TestAnnotation(value = "Method")
        public void method() {

        }
    }

    static class Child extends Parent {

        @Override
        public void method() {

        }

    }
}


