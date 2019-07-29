package Server05;

import Server04.Request;
import Server04.Response;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 使用servlet存放响应信息，解耦业务代码
 */
public class ServletServer {
    private ServerSocket serverSocket;
    public static void main(String[] args) {
        ServletServer servletServer = new ServletServer();
        servletServer.start();
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

            Response response = new Response(client);

            //添加新的servlet都需要在这里添加，比xml配置文件麻烦很多
            if (request.getUrl().equals("login")) {
                Servlet servlet = new LoginServlet();
                servlet.service(request, response);
            } else if (request.getUrl().equals("reg")) {
                Servlet servlet = new RegisterServlet();
                servlet.service(request, response);
            }

            response.toClient(200);

            stop(is, client, serverSocket);
            System.out.println(is);
            System.out.println(client);
            System.out.println(serverSocket);
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
