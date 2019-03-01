package com.coocent.marquee;

import android.content.Context;
import android.util.Log;
import android.util.Xml;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MarqueeLoader {

    private static MarqueeLoader mInstance = null;
    private final Context context;

    private MarqueeLoader(Context context){
        this.context = context;
    }

    public static MarqueeLoader getInstance(Context context){
        if (mInstance == null){
            synchronized ((MarqueeLoader.class)){
                if (mInstance == null){
                    mInstance = new MarqueeLoader(context);
                }
            }
        }
        return mInstance;
    }

    public ArrayList<MarqueeEntity> getData(){
        File tmpFile = new File(context.getFilesDir()+"/"+"marquee.xml");
        ArrayList<MarqueeEntity> marqueeLists = new ArrayList<>();
        if (tmpFile.exists()){//文件存在
            DocumentBuilder builder;
            DocumentBuilderFactory factory = null;
            Document document = null;
            InputStream inputStream = null;
            factory = DocumentBuilderFactory.newInstance();
            try {
                builder = factory.newDocumentBuilder();
                inputStream = new FileInputStream(tmpFile);
                document = builder.parse(inputStream);
                Element root = document.getDocumentElement();
                NodeList nodeList = root.getElementsByTagName("marquee");
                MarqueeEntity marqueeEntity;
                for (int i=0;i<nodeList.getLength();i++){
                    marqueeEntity = new MarqueeEntity();
                    Element marqueeElement = (Element)(nodeList.item(i));
                    Element nameElement = (Element)marqueeElement.getElementsByTagName("name").item(0);
                    marqueeEntity.setName(nameElement.getFirstChild().getNodeValue());
                    Element colorElement = (Element)marqueeElement.getElementsByTagName("color").item(0);
                    marqueeEntity.setColor(colorElement.getFirstChild().getNodeValue());
                    marqueeLists.add(marqueeEntity);
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        } else {
            //初始化两个Item
            MarqueeEntity marqueeEntity;
            ArrayList<MarqueeEntity> baseLists = new ArrayList<>();

            marqueeEntity = new MarqueeEntity();

            String strColor;
//            if(MarqueeThemeUtil.getColorAccentFromOtherApp()!=0){
//                strColor=MarqueeColorUtils.int2Hex(MarqueeThemeUtil.getColorAccentFromOtherApp());
//            }else{
//                strColor= "#"+MarqueeThemeUtil.getThemeColor();
//            }
            strColor=MarqueeThemeUtil.getColorDefault1();

//            String strColor = String.format("#%08X", t.getBrightColor());
            marqueeEntity.setName(context.getResources().getString(R.string.marquee_color_name) + " " + 1);
//            marqueeEntity.setName(strColor);
            marqueeEntity.setColor(strColor);
            baseLists.add(marqueeEntity);

            String strColor2;
//            if(MarqueeThemeUtil.getColorPrimaryFromOtherApp()!=0){
//                strColor2=MarqueeColorUtils.int2Hex(MarqueeThemeUtil.getColorPrimaryFromOtherApp());
//            }else{
//                strColor2="#FFFFFFFF";
//            }
            strColor2=MarqueeThemeUtil.getColorDefault2();

            Log.d("测试--",this.getClass().getSimpleName()+"#strColor1=#"+strColor+" strColor2="+strColor2);

            marqueeEntity = new MarqueeEntity();
            marqueeEntity.setName(context.getResources().getString(R.string.marquee_color_name) + " " + 2);
            marqueeEntity.setColor(strColor2);
            baseLists.add(marqueeEntity);

            setData(baseLists);
            marqueeLists = getData();
        }
        return marqueeLists;
    }

    public void setData(ArrayList<MarqueeEntity> marqueeLists){
        File xmlFile = new File(context.getFilesDir()+"/"+"marquee.xml");
        try {
            if (xmlFile.exists()){
                xmlFile.delete();
            }
            xmlFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(xmlFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XmlSerializer xmlSerializer = Xml.newSerializer();
        try {
            xmlSerializer.setOutput(fileOutputStream,"UTF-8");
            xmlSerializer.startDocument(null,true);
            xmlSerializer.startTag(null, "marquees");
            for (int i=0;i<marqueeLists.size();i++){
                xmlSerializer.startTag(null, "marquee");
                xmlSerializer.startTag(null, "name");
                xmlSerializer.text(marqueeLists.get(i).getName());
                xmlSerializer.endTag(null, "name");
                xmlSerializer.startTag(null, "color");
                xmlSerializer.text(marqueeLists.get(i).getColor());
                xmlSerializer.endTag(null, "color");
                xmlSerializer.endTag(null, "marquee");
            }
            xmlSerializer.endTag(null, "marquees");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            if (fileOutputStream != null)
                fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
