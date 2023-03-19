package com.alvin.java.core.io.bio.five;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.alvin.java.core.io.bio.four.ServerRunnableTarget;

/**
 * 目标：可以实现接受客户端任意文件类型，并保存到服务端
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/3/19  15:52
 */
public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8888);
            while (true) {
                Socket socket = ss.accept();
                new ServerThreadReader(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
