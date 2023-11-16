package com.alvin.java.core.io.bio.five;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 目标：实现客户端上传任意类型的文件数据给服务端保存起来
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/3/19  15:46
 */
public class Client {

    Socket socket;

    {
        try(InputStream is = new FileInputStream("java.png")) {
            socket = new Socket("127.0.0.1", 9000);
            // 把字节输出流包装程一个数据输出流
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            // 先发送上传文件的后缀给输出流
            dos.writeUTF(".png");
            byte[] buffer = new byte[1024];
            int len;
            while((len = is.read(buffer)) > 0) {
                dos.write(buffer, 0, len);
            }
            dos.flush();
            // 通知服务端数据发送完毕了
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
