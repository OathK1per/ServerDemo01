package Server06.servlet;

import Server06.packed.Request;
import Server06.packed.Response;
import Server06.packed.Servlet;

public class RegisterServlet implements Servlet {

    @Override
    public void service(Request request, Response response) {
        String username = request.getParamsValue("username");
        String[] favs =request.getParamsMap("fav");
        response.print("<html>").print("<head>")
                .print("<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\">" )
                .print("<title>").print("注册成功").print("</title>").print("</head>").print("<body>")
                .print("你注册的信息为:" + username).print("你喜欢的类型为:");
        for(String fav : favs) {
            if(fav.equals("0")) {
                response.print("萝莉型");
            }else if(fav.equals("1")) {
                response.print("豪放型");
            }else if(fav.equals("2")) {
                response.print("经济节约型");
            }
        }
        response.print("</body>");
        response.print("</html>");

    }
}
