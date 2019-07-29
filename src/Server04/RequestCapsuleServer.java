package Server04;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 封装请求信息，重点是请求方式，url和参数
 * 这里需要关闭不再使用的流，不然容易缺失掉请求体中的信息
 */
public class RequestCapsuleServer {
    private ServerSocket serverSocket;
    public static void main(String[] args) {
        RequestCapsuleServer requestCapsuleServer = new RequestCapsuleServer();
        requestCapsuleServer.start();
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
            Request request = new Request(is);
            System.out.println(request.getQuery());
            String[] ids = request.getParamsMap("id");
            for (String id : ids) {
                System.out.println(id);
            }
            // 发送响应
            // 只关注正文内容和状态码
            Response response = new Response(client);
            response.print("<html>").print("<head>").print("<title>").print("服务器响应成功");
            response.print("</title>").print("</head>").print("<body>");
            response.print("**************欢迎" + request.getParamsValue("用户名") + "来到此页面*************");
            response.print("</body>").print("</html>");

            response.toClient(200);

            stop(is, client, serverSocket);
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
