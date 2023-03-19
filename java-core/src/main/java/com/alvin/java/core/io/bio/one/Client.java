package com.alvin.java.core.io.bio.one;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * <p>描 述：</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/3/19  15:04
 */
public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 9999);
            OutputStream os = socket.getOutputStream();

            PrintStream ps = new PrintStream(os);
            ps.println("hello world, 服务端你好！");
            ps.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
