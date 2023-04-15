package com.alvin.java.core.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * <p>描 述：</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/3/15  20:22
 */
public class FileCopy01 {
    public static void main(String[] args) {
        //使用 jdk7 引入的自动关闭资源的 try 语句（该资源类要实现 AutoCloseable 或 Closeable 接口）
        try (FileInputStream fis = new FileInputStream("java-core/src/main/resources/file01.txt");
             FileOutputStream fos = new FileOutputStream("file01_copy.txt")) {
            byte[] buf = new byte[126];
            int hasRead = 0;
            while ((hasRead = fis.read(buf)) > 0) {
                //每次读取多少就写多少
                fos.write(buf, 0, hasRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
