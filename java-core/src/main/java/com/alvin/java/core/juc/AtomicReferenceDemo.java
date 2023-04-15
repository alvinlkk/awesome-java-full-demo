package com.alvin.java.core.juc;

import java.util.concurrent.atomic.AtomicReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/12/5  20:24
 * @version: 1.0.0
 */
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        User user1 = new User("旭阳");

        // 创建原子引用包装类
        AtomicReference<User> atomicReference = new AtomicReference<>(user1);

        while (true) {
            User user2 = new User("alvin");
            // 比较并交换
            if (atomicReference.compareAndSet(user1, user2)) {
                break;
            }
        }
        System.out.println(atomicReference.get());
    }
}

@Data
@AllArgsConstructor
@ToString
class User {
    private String name;
}