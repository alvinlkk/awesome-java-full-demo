package com.alvin.java.core.io.bio.six;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
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
    private Socket socket;

    public ServerThreadReader(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String msg;
            while ((msg = br.readLine()) != null) {
                sendMsgToAllClient(msg);
            }
        } catch (IOException e) {
            System.out.println("当前有人下线了");
            Server.allSocketOnline.remove(socket);
        }
    }

    public void sendMsgToAllClient(String msg) {
        for (Socket socket : Server.allSocketOnline) {
            OutputStream os = null;
            try {
                os = socket.getOutputStream();
                PrintStream ps =new PrintStream(os);
                ps.println(msg);
                ps.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
