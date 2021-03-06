package com.plachkovskyy.jdbc.database;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SAXPars {

    private static HashMap<String, String> map = new HashMap<String, String>();

    public static Map<String, String> parse() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        AdvancedXMLHandler handler = new AdvancedXMLHandler();
        parser.parse(new File("resources/dataSources/dataSource2.xml"), handler);
//        parser.parse(new File(".idea/dataSources/dataSource2.xml"), handler);

//-- Check for class working -----------------------------------------------------------------------------------------//
//        for (Map.Entry entry: map.entrySet()) {
//            System.out.println(entry);
//        }
//        System.out.println("driver-class: " + map.get("driver-class"));

        return map;

    }

    private static class AdvancedXMLHandler extends DefaultHandler {
        private String sourceName, driverClass, connectionUrl, userName, password, lastElementName;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            lastElementName = qName;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String information = new String(ch, start, length);

            information = information.replace("\n", "").trim();

            if (!information.isEmpty()) {
                if (lastElementName.equals("source-name"))
                    sourceName = information;
                if (lastElementName.equals("driver-class"))
                    driverClass = information;
                if (lastElementName.equals("connection-url"))
                    connectionUrl = information;
                if (lastElementName.equals("user-name"))
                    userName = information;
                if (lastElementName.equals("password"))
                    password = information;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ( (sourceName != null && !sourceName.isEmpty()) &&
                 (driverClass != null && !driverClass.isEmpty()) &&
                 (connectionUrl != null && !connectionUrl.isEmpty()) &&
                 (userName != null && !userName.isEmpty()) &&
                 (password != null && !password.isEmpty()) ) {
                map.put("source-name", sourceName);
                map.put("driver-class", driverClass);
                map.put("connection-url", connectionUrl);
                map.put("user-name", userName);
                map.put("password", password);
                sourceName = null;
                driverClass = null;
                connectionUrl = null;
                userName = null;
                password = null;
            }
        }

    }

//----------- Example working with xml-type:  <source-name value="oracle-jdbc" /> ------------------------------------//
/*    private static boolean isFound;

//    public SAXPars() throws ParserConfigurationException, SAXException, IOException {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        SearchingXMLHandler handler = new SearchingXMLHandler("datasources");
        parser.parse(new File(".idea/dataSources/dataSource.xml"), handler);

        if (!isFound)
            System.out.println("Элемент не был найден.");

    }

    private static class SearchingXMLHandler extends DefaultHandler {
        private String element;
        private boolean isEntered;

        public SearchingXMLHandler(String element) {
            this.element = element;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (isEntered) {
                System.out.println(String.format("Найден элемент <%s>, его атрибуты:", qName));

                int length = attributes.getLength();
                for(int i = 0; i < length; i++)
                    System.out.println(String.format("Имя атрибута: %s, его значение: %s", attributes.getQName(i), attributes.getValue(i)));
            }

            if (qName.equals(element)) {
                isEntered = true;
                isFound = true;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equals(element))
                isEntered = false;
        }
    }
*/
}
