package com.homerunsb.bookbook;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by homer on 2016-06-15.
 */
public class BookXmlPullParser {
    static final String TAG = BookXmlPullParser.class.getSimpleName();
    BookInformationData arrayList = new BookInformationData();
    BookInformationData bookInformationData = null;
    String xml;

    public BookInformationData parseXml(String str) {
        xml = str;
        boolean hasTitle = false;
        boolean hasLink = false;
        boolean hasImage = false;
        boolean hasAuthor = false;
        boolean hasPrice = false;
        boolean hasDiscount = false;
        boolean hasPublisher = false;
        boolean hasPubdate = false;
        boolean hasIsbn = false;
        boolean hasDescription = false;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(xml));
            int eventType = xpp.getEventType();
            bookInformationData = new BookInformationData();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();

                //if (tagName != null && tagName.equals("item")) {
                    switch (eventType) {

                        case XmlPullParser.START_TAG:
                            //arrayList = new BookInformationData();
                            int tagDepth = xpp.getDepth();
                            if(xpp.getName() != null){ tagName = xpp.getName();}
                            //Log.d(TAG, "START_TAG / xpp.getName() : " + tagName );

                            if (tagName.equals("title") && tagDepth > 3) {
                                hasTitle = true;
                            } else if (tagName.equals("link") && tagDepth > 3) {
                                hasLink = true;
                            } else if (tagName.equals("image") && tagDepth > 3) {
                                hasImage = true;
                            } else if (tagName.equals("author") && tagDepth > 3) {
                                hasAuthor = true;
                            } else if (tagName.equals("price") && tagDepth > 3) {
                                hasPrice = true;
                            } else if (tagName.equals("discount") && tagDepth > 3) {
                                hasDiscount = true;
                            } else if (tagName.equals("publisher") && tagDepth > 3) {
                                hasPublisher = true;
                            } else if (tagName.equals("pubdate") && tagDepth > 3) {
                                hasPubdate = true;
                            } else if (tagName.equals("isbn") && tagDepth > 3) {
                                hasIsbn = true;
                            } else if (tagName.equals("description") && tagDepth > 3) {
                                hasDescription = true;
                            }
                            break;

                        case XmlPullParser.TEXT:
                            String tagText = xpp.getText();
                            //Log.d(TAG, "TEXT / xpp.getText() : " + tagText );

                            if (hasTitle) {
                                bookInformationData.setTitle(tagText);
                                hasTitle = false;
                            } else if (hasLink) {
                                bookInformationData.setLink(tagText);
                                hasLink = false;
                            } else if (hasImage) {
                                bookInformationData.setImage(tagText);
                                hasImage = false;
                            } else if (hasAuthor) {
                                bookInformationData.setAuthor(tagText);
                                hasAuthor = false;
                            } else if (hasPrice) {
                                bookInformationData.setPrice(tagText);
                                hasPrice = false;
                            } else if (hasDiscount) {
                                bookInformationData.setDiscount(tagText);
                                hasDiscount = false;
                            } else if (hasPublisher) {
                                bookInformationData.setPublisher(tagText);
                                hasPublisher = false;
                            } else if (hasPubdate) {
                                bookInformationData.setPubdate(tagText);
                                hasPubdate = false;
                            } else if (hasIsbn) {
                                bookInformationData.setIsbn(tagText);
                                hasIsbn = false;
                            } else if (hasDescription) {
                                bookInformationData.setDescription(tagText);
                                hasDescription = false;
                            }
                            break;

                        case XmlPullParser.END_TAG:
/*                            if(tagName.equals("item")) {
                                Log.d(TAG, "**************************** XmlPullParser.END_TAG 진입 및 담기 *******************************");
                                //arrayList.add(bookInformationData);
                            }*/
                            break;
                    }// switch end
                //}// if end
                eventType = xpp.next();
            } // while end
                //Log.d(TAG, "While문 끝");
        }catch (Exception e){
            e.printStackTrace();
        }
        return bookInformationData;
    }
}
