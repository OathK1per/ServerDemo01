package Server06.servlet;

import Server06.packed.Request;
import Server06.packed.Response;
import Server06.packed.Servlet;

public class LoginServlet implements Servlet {

    @Override
    public void service(Request request, Response response) {
        response.print("<html>").print("<head>").print("<title>").print("服务器响应成功");
        response.print("</title>").print("</head>").print("<body>");
        response.print("**************欢迎" + request.getParamsValue("username") + "来到此页面*************");
        response.print("</body>").print("</html>");
    }
}
