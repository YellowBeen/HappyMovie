package com.yellobeansoft.happymovie;

import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Beboyz on 1/12/15 AD.
 */
public class XMLParser {

    public static ArrayList<Cinema> parse(InputStream is) {
        ArrayList<Cinema> cinemas = null;
        try {
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser()
                    .getXMLReader();
            XMLHandler saxHandler = new XMLHandler();
            xmlReader.setContentHandler(saxHandler);
            xmlReader.parse(new InputSource(is));
            cinemas = saxHandler.getCinemas();

        } catch (Exception ex) {
            Log.d("XML", "XMLParser: parse() failed");
        }

        // return cinemas
        return cinemas;
    }

}
