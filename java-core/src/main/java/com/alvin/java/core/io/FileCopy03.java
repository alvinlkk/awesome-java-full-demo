package com.alvin.java.core.io;

import java.io.RandomAccessFile;

/**
 * <p>描 述：</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/3/15  20:31
 */
public class FileCopy03 {
    public static void main(String[] args) {
        try (RandomAccessFile in = new RandomAccessFile("java-core/src/main/resources/file01.txt","rw");
             RandomAccessFile out = new RandomAccessFile("file01_copy3.txt","rw")) {
            byte[] buf = new byte[2];
            int hasRead = 0;
            while ((hasRead = in.read(buf)) > 0) {
                //每次读取多少就写多少
                out.write(buf, 0, hasRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
