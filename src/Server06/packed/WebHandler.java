package Server06.packed;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析数据，把所有数据存放在一个容器中，等待上下文进行处理
 */
public class WebHandler extends DefaultHandler {
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

    public List<ServletEntity> getServletEntities() {
        return servletEntities;
    }
}
