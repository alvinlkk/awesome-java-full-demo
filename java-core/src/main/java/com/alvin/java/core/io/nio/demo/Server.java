package com.alvin.java.core.io.nio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 目标：NIO非阻塞通信下的入门案例：服务端开发
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/4/9  14:02
 */
public class Server {

    public static void main(String[] args) throws IOException {
        System.out.println("---服务端启动---");
        // 1. 获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        //2. 切换为非阻塞模式
        ssChannel.configureBlocking(false);
        //3. 端口
        ssChannel.bind(new InetSocketAddress(9999));
        //4. 获取选择器
        Selector selector = Selector.open();
        //5. 通道注册到选择器上，并且开始监听事件
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        //6. 使用Selector选择器轮询已经就绪好的事件
        while (selector.select() > 0) {
            System.out.println("开始一轮事件处理~~~");
            //7.获取选择器中的所有注册的通道中已经就序好的事件
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            //8.开始遍历这些准备好的事件
            while (it.hasNext()) {
                //提取当前这个事件
                SelectionKey sk = it.next();
                //9.判断这个事件具体是什么事件
                if (sk.isAcceptable()) {
                    //10.直接获取当前接入的客户端通道
                    SocketChannel schannel = ssChannel.accept();
                    //11.将客户端通道也设置为非阻塞式的
                    schannel.configureBlocking(false);
                    //12.将客户端通道也注册到选择器Selector上
                    schannel.register(selector, SelectionKey.OP_READ);
                } else if (sk.isReadable()) {
                    //13.获取当前选择器上的”读就绪事件“
                    SocketChannel sChannel = (SocketChannel) sk.channel();
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    int len = 0;
                    while ((len = sChannel.read(buf)) > 0) {
                        buf.flip();
                        System.out.println(new String(buf.array(), 0, len));
                        buf.clear();
                    }
                }
                //处理完毕当前事件后，需要移除掉当前事件.否则会重复处理
                 it.remove();
            }
        }
    }
}
