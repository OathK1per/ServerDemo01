package Server03;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 使用ServerSocket与浏览器建立连接，获取请求协议
 */
public class RequestServer {
    private ServerSocket serverSocket;
    public static void main(String[] args) {
        RequestServer requestServer = new RequestServer();
        requestServer.start();
    }
    // 启动服务器
    void start() {
        try {
            serverSocket = new ServerSocket(9988);
            receive();
        } catch (IOException e) {
            System.out.println("服务器启动失败");
        }
    }
    // 客户端连接此服务器
    void receive() {
        try {
            Socket client = serverSocket.accept();
            System.out.println("一个客户端建立了连接");
            InputStream is = new BufferedInputStream(client.getInputStream());
            byte[] datas = new byte[1024];
            int len = -1;
            while ((len = is.read(datas)) != -1) {
                System.out.print(new String(datas, 0, len));
            }
            stop(is, client);
        } catch (IOException e) {
            System.out.println("客户端接收失败");
        }
    }

    void stop(Closeable... ios) {
        try {
            for (Closeable io : ios) {
                if (io != null) {
                    io.close();
                }
            }
        } catch (IOException e) {
            System.out.println("服务器关闭出现错误");
        }
    }
}
