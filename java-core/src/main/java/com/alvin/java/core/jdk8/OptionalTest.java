/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.java.core.jdk8;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import com.sun.tools.corba.se.idl.InterfaceGen;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/9/30
 * @since 1.0
 **/
public class OptionalTest {

    @Test
    public void testOf() {
        Integer value = 2;
        // 正常
        Optional<Integer> op = Optional.of(value);

        value = null;
        // 报空指针
        op = Optional.of(value);
    }

    @Test
    public void testOfNullable() {
        Integer value = 2;
        // 正常
        Optional<Integer> op = Optional.ofNullable(value);
        value = null;
        // 不报错
        op = Optional.ofNullable(value);
    }

    @Test
    public void testGet() {
        Integer value = 2;
        // 正常
        Optional<Integer> op = Optional.ofNullable(value);
        Integer opVal = op.get();
        Assert.assertEquals(opVal, value);

        op = Optional.ofNullable(null);
        // 会抛出异常
        op.get();
    }

    @Test
    public void testOrElse() {
        // 正常
        Optional<Integer> op = Optional.ofNullable(2);
        Integer opVal = op.orElse(3);
        Assert.assertEquals(opVal, new Integer(2));

        op = Optional.ofNullable(null);
        // 为空，则返回3
        opVal = op.orElse(3);
        Assert.assertEquals(opVal, new Integer(3));
    }

    @Test
    public void testOrElseGet() {
        // 正常
        Optional<Integer> op = Optional.ofNullable(2);
        Integer opVal = op.orElseGet(()-> {
            return new Integer(3);
        });
        Assert.assertEquals(opVal, new Integer(2));

        op = Optional.ofNullable(null);
        // 为空，则返回3
        opVal = op.orElseGet(()-> {
            return new Integer(3);
        });
        Assert.assertEquals(opVal, new Integer(3));
    }

    @Test
    public void testOrElseThrow() {
        Optional<Integer> op = Optional.ofNullable(null);
        // 为空，则抛出指定的异常类型
        Integer opVal = op.orElseThrow(()-> {
            return new RuntimeException();
        });
        // 或抛出runtime异常
        Assert.assertEquals(opVal, new Integer(3));
    }

    @Test
    public void testIsPresent() {
        Optional<Integer> op = Optional.ofNullable(null);
        // 为空
        Assert.assertFalse(op.isPresent());
    }

    @Test
    public void testIfPresent() {
        Optional<Integer> op = Optional.ofNullable(5);
        op.ifPresent(value -> {
            System.out.println(value);
        });
    }

    @Test
    public void testFilter() {
        // 不满足过滤条件，返回空 Optional
        Optional<String> op = Optional.ofNullable("10").filter(item -> "15".equals(item));
        Assert.assertFalse(op.isPresent());
    }

    @Test
    public void testMap() {
        Optional<Optional<Integer>> integer = Optional.ofNullable("5").map(item -> Optional.of(Integer.valueOf(item)));
    }

    @Test
    public void testflatMap() {
        Optional<Integer> integer = Optional.ofNullable("5").flatMap(item -> Optional.of(Integer.valueOf(item)));
    }
}

class User {
    String name;
    Integer age;

    public User(){

    }
    public User(String name, Integer age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
