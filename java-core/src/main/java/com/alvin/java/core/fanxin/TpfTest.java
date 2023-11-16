/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.java.core.fanxin;

import java.util.ArrayList;
import java.util.List;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/9/5
 * @since 1.0
 **/
public class TpfTest {

    public static void animalEat(AnimalWrapper<? extends Animal> animalWrapper) {
        animalWrapper.eat();
    }

    public static void main(String[] args) {
        AnimalWrapper<Cat> catWrapper = new AnimalWrapper(new Cat());

        animalEat(catWrapper);
    }

}

class AnimalWrapper<T extends Animal> {
    private final T animal;

    AnimalWrapper(T animal) {
        this.animal = animal;
    }

    public void eat() {
       animal.eat();
    }

}

class Animal {
    private String name;

    public void eat() {
        System.out.println("animal eat -----");
    }
}

class Cat extends Animal {

    @Override
    public void eat() {
        System.out.println(" cat eat -----");
    }
}