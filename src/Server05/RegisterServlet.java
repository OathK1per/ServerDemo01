package Server05;

import Server04.Request;
import Server04.Response;

public class RegisterServlet implements Servlet {

    @Override
    public void service(Request request, Response response) {
        response.print("<html>").print("<head>").print("<title>").print("服务器响应成功");
        response.print("</title>").print("</head>").print("<body>");
        response.print("Please login");
        response.print("</body>").print("</html>");
    }
}
