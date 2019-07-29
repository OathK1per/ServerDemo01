package Server06;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 使用静态块将原本XMLParseWeb中的配置信息存放过来，同时实现url到具体servlet的功能
 */
public class WebApp {
    private static WebContext webContext;
    static {
        try {
            //SAX解析
            //1、获取解析工厂
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //2、从解析工厂获取解析器
            SAXParser parse = factory.newSAXParser();
            //3、编写处理器
            //4、加载文档 Document 注册处理器
            WebHandler handler = new WebHandler();
            //5、解析
            parse.parse(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("resources/web.xml"), handler);
            webContext = new WebContext(handler.getServletMappings(), handler.getServletEntities());
        } catch (Exception e) {
            System.out.println("XML配置信息错误");
        }
    }

    public static Servlet getServletByUrl(String url) {
        //6、获取数据
        //在这里获得的url是不带/的，所以需要添加一个
        String servletClass = webContext.getServletClass("/" + url);
        //使用反射获取类
        Class servlet = null;
        try {
            servlet = Class.forName(servletClass);
            return (Servlet) servlet.getConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
