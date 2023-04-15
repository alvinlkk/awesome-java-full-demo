package com.alvin.java.core.io;

import java.io.FileReader;
import java.io.FileWriter;

/**
 * <p>描 述：</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/3/15  20:28
 */
public class FileCopy02 {

    public static void main(String[] args) {
        //使用 jdk7 引入的自动关闭资源的 try 语句
        try (FileReader fr = new FileReader("java-core/src/main/resources/file01.txt");
             FileWriter fw = new FileWriter("file01_copy2.txt")) {
            char[] buf = new char[2];
            int hasRead = 0;
            while ((hasRead = fr.read(buf)) > 0) {
                //每次读取多少就写多少
                fw.write(buf, 0, hasRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
