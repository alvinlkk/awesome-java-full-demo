package com.alvin.java.core.io.bio.one;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * <p>描 述：</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/3/19  15:00
 */
public class Server {

    public static void main(String[] args) {
        try {
            System.out.println("服务端启动");
            //1. 定义服务对象
            ServerSocket ss = new ServerSocket(9999);
            //2. 监听客户端的socket请求
            Socket socket = ss.accept();
            //3.从socket管道中获取一个字节驶入流
            InputStream is = socket.getInputStream();
            //4.字节输入流包装字符输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String msg;
            if ((msg = br.readLine()) != null) {
                System.out.println("服务端接收到: " + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
