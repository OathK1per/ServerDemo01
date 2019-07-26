package Server03;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ResponseServer {
    private ServerSocket serverSocket;
    public static void main(String[] args) {
        ResponseServer server = new ResponseServer();
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
            StringBuilder content = new StringBuilder();
            content.append("<html>");
            content.append("<head>");
            content.append("<title>");
            content.append("服务器响应成功");
            content.append("</title>");
            content.append("</head>");
            content.append("<body>");
            content.append("***************************");
            content.append("</body>");
            content.append("</html>");
            int size = content.toString().getBytes().length; //必须获取字节长度，而不是字符的长度
            StringBuilder responseInfo = new StringBuilder();
            String blank = " ";
            String CRLF = "\r\n";
            //返回
            //1、响应行: HTTP/1.1 200 OK
            responseInfo.append("HTTP/1.1").append(blank);
            responseInfo.append(200).append(blank);
            responseInfo.append("OK").append(CRLF);
            //2、响应头(最后一行存在空行):
			/*
			 Date:Mon,31Dec209904:25:57GMT
			Server:shsxt Server/0.0.1;charset=GBK
			Content-type:text/html
			Content-length:39725426
			 */
            responseInfo.append("Date:").append(new Date()).append(CRLF);
            responseInfo.append("Server:").append("shsxt Server/0.0.1;charset=GBK").append(CRLF);
            responseInfo.append("Content-type:text/html").append(CRLF);
            responseInfo.append("Content-length:").append(size).append(CRLF);
            responseInfo.append(CRLF);
            //3、正文
            responseInfo.append(content.toString());

            //写出到客户端
            System.out.println(responseInfo.toString());
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            bw.write(responseInfo.toString());
            bw.flush();
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
