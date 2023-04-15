package com.alvin.java.core.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <p>描 述：</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/3/15  20:37
 */
public class FileCopy04 {
    public static void main(String[] args) {
        try(FileInputStream fis = new FileInputStream("java-core/src/main/resources/file01.txt");
            FileOutputStream fos = new FileOutputStream("file01_copy4.txt");
            FileChannel inc = fis.getChannel();
            FileChannel outc = fos.getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(4);
            while (inc.read(buffer) != -1) {
                buffer.flip();
                outc.write(buffer);
                buffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
