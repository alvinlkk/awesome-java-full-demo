package com.alvin.java.core.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * <p>描 述：</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/3/15  20:30
 */
public class FileCopy02_2 {
    public static void main(String[] args) {
        //使用普通的 Reader 不方便整行读取,可以使用 BufferReader 包装,资源变量要定义在 try()中,否则不会自动关闭
        try (FileReader fr = new FileReader("java-core/src/main/resources/file01.txt");
             FileWriter fw = new FileWriter("file01_copy2_2.txt");
             BufferedReader bufferedReader = new BufferedReader(fr);
             BufferedWriter bufferedWriter = new BufferedWriter(fw)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //每次读取一行、写入一行
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
