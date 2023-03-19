package com.alvin.java.core.io.bio.three;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 目标： 实现服务端可以接受多个客户端socket的通信需求
 * 思路： 服务端每接收到一个客户端socket请求对象之后都交给一个独立的线程来处理
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/3/19  15:17
 */
public class Server {

    public static void main(String[] args) {
        try {
            // 1.注册服务端端口
            ServerSocket ss = new ServerSocket(9000);
            // 2.定义循环，负责不断接受客户端的连接
            while (true) {
                Socket socket = ss.accept();
                // 3. 创建独立线程处理socket的通信需求
                new ServerThreadReader(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
