package Server02;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * XML解析是一种流解析，需要同步储存信息，否则信息会丢失
 */
public class XMLParseWeb {
    public static void main(String[] args) throws Exception {
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
        //6、获取数据
        WebContext webContext = new WebContext(handler.getServletMappings(), handler.getServletEntities());
        String servletClass = webContext.getServletClass("/reg");
        //使用反射获取类
        Class servlet = Class.forName(servletClass);
        Servlet instance = (Servlet) servlet.getConstructor().newInstance();
        instance.print();
    }
}

