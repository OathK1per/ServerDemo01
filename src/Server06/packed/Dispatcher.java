package Server06.packed;

import java.io.*;
import java.net.Socket;

/**
 * 分发器
 */
public class Dispatcher implements Runnable {
    private Socket client;
    private InputStream is;
    private OutputStream os;
    private Request request;
    private Response response;

    public Dispatcher(Socket client) {
        this.client = client;
        try {
            is = new BufferedInputStream(client.getInputStream());
            os = new BufferedOutputStream(client.getOutputStream());
            request = new Request(is);
            response = new Response(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            if(request.getUrl() == null || request.getUrl().equals("")) {
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("index.html");
                response.print(new String(is.readAllBytes()));
                response.toClient(200);
                is.close();
                return ;
            }
            Servlet servlet = WebApp.getServletByUrl(request.getUrl());
            if (servlet != null) {
                servlet.service(request, response);
                response.toClient(200);
            } else {
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("error.html");
                response.print(new String(is.readAllBytes()));
                response.toClient(404);
                is.close();
            }
        } catch (IOException e) {
            try {
                response.toClient(500);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } finally {
            release();
        }
    }

    private void release() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
