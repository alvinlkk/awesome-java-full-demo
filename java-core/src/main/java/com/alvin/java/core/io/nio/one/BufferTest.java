package com.alvin.java.core.io.nio.one;

import java.nio.ByteBuffer;

import org.junit.Test;

/**
 * 目标：对缓冲区Buffer常用API进行案例实现
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/3/19  16:51
 */
public class BufferTest {

    @Test
    public void test01() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
    }

}
