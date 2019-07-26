package Server04;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 目标：封装响应信息(下面两项可以通过外部添加，其余都封装好)
 * 1. 内容可以动态添加
 * 2. 关注状态码，拼接好响应的协议信息
 */
public class ResponseCapsuleServer {
    private ServerSocket serverSocket;
    public static void main(String[] args) {
        ResponseCapsuleServer server = new ResponseCapsuleServer();
        server.start();
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
            // 接收请求
            Socket client = serverSocket.accept();
            System.out.println("一个客户端建立了连接");
            InputStream is = new BufferedInputStream(client.getInputStream());
            // 1M足够接收请求头和请求体中的内容
            byte[] datas = new byte[1024 * 1024];
            int len = is.read(datas);
            System.out.println(new String(datas, 0, len));

            // 发送响应
            // 只关注正文内容和状态码
            Response response = new Response(client);
            response.print("<html>").print("<head>").print("<title>").print("服务器响应成功");
            response.print("</title>").print("</head>").print("<body>");
            response.print("***************************").print("</body>").print("</html>");

            response.toClient(200);
        } catch (IOException e) {
            System.out.println("客户端接收失败");
        }
    }
    //停止服务
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
