package Server06;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Response {
    private BufferedWriter bw;
    // 正文
    private StringBuilder contents;
    // 请求头
    private StringBuilder headInfo;
    private int contentsLength;
    private final String BLANK = " ";
    private final String CRLF = "\r\n";
    private Response() {
        contents = new StringBuilder();
        headInfo = new StringBuilder();
        contentsLength = 0;
    }

    public Response(Socket client) throws IOException {
        this(); //this只能在第一排
        bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
    }

    public Response(OutputStream os) {
        this();
        bw = new BufferedWriter(new OutputStreamWriter(os));
    }

    public Response print(String contents) {
        this.contents.append(contents);
        contentsLength += contents.getBytes().length;
        return this;
    }

    private void headConstruction(int code) {
        //返回
        //1、响应行: HTTP/1.1 200 OK
        headInfo.append("HTTP/1.1").append(BLANK);
        headInfo.append(code).append(BLANK);
        switch (code) {
            case 200:
                headInfo.append("OK").append(CRLF);
                break;
            case 404:
                headInfo.append("NOT FOUND").append(CRLF);
                break;
            case 500:
                headInfo.append("SERVER ERROR").append(CRLF);
                break;
        }
        //2、响应头(最后一行存在空行):
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Server:").append("shsxt Server/0.0.1;charset=GBK").append(CRLF);
        headInfo.append("Content-type:text/html").append(CRLF);
        headInfo.append("Content-length:").append(contentsLength).append(CRLF);
        headInfo.append(CRLF);
        headInfo.append(contents);
    }

    //返回内容到客户端并打印
    public void toClient(int code) throws IOException {
        headConstruction(code);
        bw.write(headInfo.toString());
        bw.flush();
    }
}
