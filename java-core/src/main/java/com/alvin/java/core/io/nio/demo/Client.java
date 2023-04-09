package com.alvin.java.core.io.nio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * <p>目标：客户端案例实现-基于NIO非阻塞通信</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/4/9  14:14
 */
public class Client {

    public static void main(String[] args) throws IOException {
        // 1. 获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
        // 2. 切换非阻塞模式
        sChannel.configureBlocking(false);
        // 3. 分配指定缓存区大小
        ByteBuffer buf = ByteBuffer.allocate(1024);
        // 4. 发送数据到客户端
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入：");
            String msg = sc.nextLine();
            buf.put(("旭阳：" + msg).getBytes());
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

    }
}
