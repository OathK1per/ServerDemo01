package Server01;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * XML解析是一种流解析，需要同步储存信息，否则信息会丢失
 */
public class XMLParse2 {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        //SAX解析
        //1、获取解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //2、从解析工厂获取解析器
        SAXParser parse = factory.newSAXParser();
        //3、编写处理器
        //4、加载文档 Document 注册处理器
        PersonHandler handler = new PersonHandler();
        //5、解析
        parse.parse(Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("resources/p.xml"), handler);
    }
}

class PersonHandler extends DefaultHandler {
    private List<Person> people;
    private Person person;
    private String tag;

    @Override
    public void startDocument() throws SAXException {
        people = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("person")) {
            person = new Person();
        }
        tag = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String contents = new String(ch, start, length).trim();
        if (contents.length() != 0) {
            if (tag.equals("name")) {
                person.setName(contents);
            } else if (tag.equals("age")) {
                person.setAge(Integer.valueOf(contents));
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("person")) {
            people.add(person);
        }
    }
    @Override
    public void endDocument() throws SAXException {
        for (Person person : people) {
            System.out.println(person.getName() + "-->" + person.getAge());
        }
    }
}
