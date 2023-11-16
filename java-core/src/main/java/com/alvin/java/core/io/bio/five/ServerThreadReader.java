package com.alvin.java.core.io.bio.five;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

/**
 * <p>描 述：</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/3/19  15:20
 */
public class ServerThreadReader extends Thread {
    private final Socket socket;

    public ServerThreadReader(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            // 读取文件类型
            String suffix = dis.readUTF();
            System.out.println("服务端已经成功接收到了文件类型：" + suffix);
            OutputStream os = new FileOutputStream("D:\\servers\\" + UUID.randomUUID() + suffix);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = dis.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
            os.close();
            System.out.println("服务端接口文件保存成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
