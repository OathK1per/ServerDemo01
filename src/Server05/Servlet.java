package Server05;

import Server04.Request;
import Server04.Response;

public interface Servlet {
    void service(Request request, Response response);
}
