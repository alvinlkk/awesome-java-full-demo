package com.alvin.java.core.io.bio.six;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.alvin.java.core.io.bio.five.ServerThreadReader;

/**
 * 目标： BIO模式下的端口转发思想，服务端实现
 *  1.注册端口
 *  2.接受客户端socket的连接，交给独立线程处理
 *  3.把当前客户端放到金和中
 *  4.接收客户端消息，推送到所有在线socket
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/3/19  16:04
 */
public class Server {
    public static List<Socket> allSocketOnline = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(9000);
            while (true) {
                Socket socket = ss.accept();
                allSocketOnline.add(socket);
                new ServerThreadReader(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
