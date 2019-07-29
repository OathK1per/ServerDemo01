package Server06;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 整合xml配置文件，将原本再XMLParseWeb中手工输入的url通过浏览器传过来，并返回正确的响应
 * 实现多线程
 */
public class XmlServletServer {
    private ServerSocket serverSocket;
    private boolean isRunning = true;
    public static void main(String[] args) {
        XmlServletServer servletServer = new XmlServletServer();
        servletServer.start();
    }
    // 启动服务器
    void start() {
        try {
            serverSocket = new ServerSocket(9988);
            receive();
        } catch (IOException e) {
            System.out.println("服务器启动失败");
            stop(serverSocket);
        }
    }
    // 客户端连接此服务器
    void receive() {
        while (isRunning) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("一个客户端建立了连接");
                Dispatcher dispatcher = new Dispatcher(client);
                new Thread(dispatcher).start();
            } catch (IOException e) {
                System.out.println("客户端接收失败");
                stop(serverSocket);
            }
        }
    }

    void stop(Closeable... ios) {
        isRunning = false;
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
