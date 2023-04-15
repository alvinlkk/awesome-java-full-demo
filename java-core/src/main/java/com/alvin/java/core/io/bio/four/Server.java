package com.alvin.java.core.io.bio.four;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 目标：开发实现伪异步通信架构
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/3/19  15:33
 */
public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(9000);

            HandlerSocketServerPool pool = new HandlerSocketServerPool(2, 10);
            while (true) {
                Socket socket = ss.accept();
                Runnable target = new ServerRunnableTarget(socket);
                pool.execute(target);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
