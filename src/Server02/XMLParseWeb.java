package Server02;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;

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
        WebContext webContext = new WebContext( handler.getServletMappings(), handler.getServletEntities());
        String servletClass = webContext.getServletClass("/reg");
        //使用反射获取类
        Class servlet = Class.forName(servletClass);
        Servlet instance = (Servlet) servlet.getConstructor().newInstance();
        instance.print();
    }
}

class WebHandler extends DefaultHandler {
    private List<ServletMapping> servletMappings;
    private List<ServletEntity> servletEntities;
    private ServletEntity servletEntity;
    private ServletMapping servletMapping;
    // 用于传递具体的标签
    private String tag;
    // 用于传递大的标签
    private boolean isMapping;

    public WebHandler() {
        servletMappings = new ArrayList<>();
        servletEntities = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("servlet")) {
            servletEntity = new ServletEntity();
            isMapping = false;
        } else if (qName.equals("servlet-mapping")) {
            servletMapping = new ServletMapping();
            isMapping = true;
        }
        tag = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String contents = new String(ch, start, length).trim();
        if (contents.length() != 0) {
            if (!isMapping) {
                if (tag.equals("servlet-name")) {
                    servletEntity.setServletName(contents);
                } else if (tag.equals("servlet-class")) {
                    servletEntity.setServletClass(contents);
                }
            } else{
                if (tag.equals("servlet-name")) {
                    servletMapping.setServletName(contents);
                } else if (tag.equals("url-pattern")) {
                    servletMapping.addUrlPattern(contents);
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("servlet")) {
            servletEntities.add(servletEntity);
        } else if (qName.equals("servlet-mapping")) {
            servletMappings.add(servletMapping);
        }
    }

    public List<ServletMapping> getServletMappings() {
        return servletMappings;
    }

    public void setServletMappings(List<ServletMapping> servletMappings) {
        this.servletMappings = servletMappings;
    }

    public List<ServletEntity> getServletEntities() {
        return servletEntities;
    }

    public void setServletEntities(List<ServletEntity> servletEntities) {
        this.servletEntities = servletEntities;
    }
}
