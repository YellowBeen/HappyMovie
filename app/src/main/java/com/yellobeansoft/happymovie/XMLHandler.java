package com.yellobeansoft.happymovie;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Beboyz on 1/12/15 AD.
 */
public class XMLHandler extends DefaultHandler {

    private ArrayList<Cinema> cinemas = new ArrayList<Cinema>();
    private Cinema tempCinemas;
    private String tempValue;

    public ArrayList<Cinema> getCinemas() {
        return cinemas;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equalsIgnoreCase("cinema")) {
            tempCinemas = new Cinema();
            tempCinemas.setId(new Integer(attributes.getValue("id")));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (localName.equalsIgnoreCase("name")) {
            tempCinemas.setName(tempValue);
        }else if (localName.equalsIgnoreCase("brand")) {
            tempCinemas.setBrand(tempValue);
        }else if (localName.equalsIgnoreCase("group")) {
            tempCinemas.setGroup(tempValue);
        }else if (localName.equalsIgnoreCase("latitude")) {
            tempCinemas.setLatitude(tempValue);
        }else if (localName.equalsIgnoreCase("longtitude")) {
            tempCinemas.setLongtitude(tempValue);
        }else if (localName.equalsIgnoreCase("cinema")) {
            cinemas.add(tempCinemas);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        tempValue = new String(ch, start, length);


    }
}
