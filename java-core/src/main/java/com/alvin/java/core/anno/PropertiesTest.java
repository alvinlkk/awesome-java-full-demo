package com.alvin.java.core.anno;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.junit.Test;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/9/14  20:10
 * @version: 1.0.0
 */
public class PropertiesTest {

    @Test
    public void test1() throws IOException {
        Properties properties = new Properties();
        // 使用load inputstream
        properties.load(this.getClass().getClassLoader().getResourceAsStream("app.properties"));
        // 出现乱码
        System.out.println(properties);
        // 转码
        System.out.println(new String(properties.getProperty("user.name").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));

        Properties properties2 = new Properties();
        // 使用load read
        BufferedReader bf = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("app.properties"), "UTF-8"));
        properties2.load(bf);
        System.out.println(properties2);
    }

    @Test
    public void test2() throws IOException {
        Properties properties2 = new Properties();
        // 使用load read
        BufferedReader bf = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("app.properties"), "UTF-8"));
        properties2.load(bf);
        System.out.println(properties2);

        // 保存到xml
        FileOutputStream fileOutputStream = new FileOutputStream("app.xml");
        properties2.storeToXML(fileOutputStream, "alvin info", "UTF-8");
    }
}
