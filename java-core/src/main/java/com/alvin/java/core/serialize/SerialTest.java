/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.java.core.serialize;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/10/1
 * @since 1.0
 **/
public class SerialTest {

    @Test
    public void testSerializable() throws FileNotFoundException {
        User user = new User("alvin", 19);
        // 写到内存中，当然也可以写到文件中
        FileOutputStream bout = new FileOutputStream("user.dat");
        try (ObjectOutputStream out = new ObjectOutputStream(bout)) {
            // 序列化11
            out.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeSerializable() throws FileNotFoundException {
        User user = null;
        // 写到内存中，当然也可以写到文件中
        FileInputStream fis = new FileInputStream("user.dat");
        try (ObjectInputStream in = new ObjectInputStream(fis)) {
            // 反序列化 readObject
            user = (User) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Assert.assertEquals("alvin", user.getUsername());
    }
}
