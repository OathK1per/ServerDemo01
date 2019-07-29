package Server06;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.net.URLDecoder.decode;


public class Request {
    //请求方式
    private String method;
    //请求的url
    private String url;
    //请求的参数
    private String query;
    //使用map把字符串参数转成对象形式
    private Map<String, List<String>> params;
    private final String CRLF = "\r\n";
    public Request(InputStream is) {
        // 1M足够接收请求头和请求体中的内容
        byte[] datas = new byte[0];
        try {
            datas = is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = new String(datas);
        params = new HashMap<>();
        getRequestInfo(str);
        convertToMap();
    }

    public Request(Socket client) throws IOException {
        this(client.getInputStream());
    }

    private void getRequestInfo(String str) {
        //请求方式
        method = str.substring(0, str.indexOf("/")).trim();
        //请求url
        String fullUrl = str.substring(str.indexOf("/") + 1, str.indexOf("HTTP/")).trim();
        String[] split = fullUrl.split("\\?");
        url = split[0];
        query = (split.length == 1) ? "" : split[1];
        //请求参数
        if (method.toLowerCase().equals("post")) {
            String queryPost = str.substring(str.lastIndexOf(CRLF)).trim();
            if (!query.equals("")) {
                query += "&";
            }
            query += "" + queryPost;
        }
        System.out.println(method + "-->" + url + "-->" + query);
    }

    private void convertToMap() {
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] parameter = pair.split("=");
            String[] param = Arrays.copyOf(parameter, 2);
            //在放入map前就需要解码，保证能够使其被正确获取
            String key = decodeCN(param[0]);
            String value = decodeCN((param[1] == null) ? "" : param[1]);
            if (!params.containsKey(key)) {
                params.put(key, new ArrayList<>());
            }
            params.get(key).add(value);
        }
    }
    //以数组形式输出多个值
    public String[] getParamsMap(String key) {
        List<String> values = params.get(key);
        if (values == null || values.size() == 0) {
            return new String[0];
        }
        return values.toArray(new String[0]);
    }

    //输出单个值
    public String getParamsValue(String key) {
        List<String> values = params.get(key);
        if (values == null || values.size() == 0) {
            return "";
        }
        return values.get(0);
    }

    //解码，使其能够在get方法中输出中文
    private String decodeCN(String value) {
        return decode(value, StandardCharsets.UTF_8);
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getQuery() {
        return query;
    }
}
