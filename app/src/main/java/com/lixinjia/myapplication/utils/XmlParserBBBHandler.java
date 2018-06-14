package com.lixinjia.myapplication.utils;

import com.lixinjia.myapplication.model.BBBModel;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XmlParserBBBHandler extends DefaultHandler {
    /**
     * 存储所有的解析对象
     */
    private List<BBBModel> provinceList = new ArrayList<>();
    private BBBModel itemData;
    private boolean startGo = false;
    private int index = 0;
    private String textHh = "";
    private String chatstr;

    public XmlParserBBBHandler() {

    }

    public List<BBBModel> getDataList() {
        return provinceList;
    }

    @Override
    public void startDocument() throws SAXException {
        // 当读到第一个开始标签的时候，会触发这个方法
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        // 当遇到开始标记的时候，调用这个方法
        if (qName.equals("Table")) {
            startGo = true;
        }
        if (qName.equals("Row")) {
            itemData = new BBBModel();
            itemData.setList(new ArrayList<BBBModel.listData>());
            index = 1;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        // 遇到结束标记的时候，会调用这个方法
        if (qName.equals("Cell")) {
            if(index == 1){
                itemData.setName(chatstr);
            }
            index ++;
            BBBModel.listData listData = new BBBModel.listData();
            textHh = textHh.replace("外勤", "外勤 ");
            listData.setDatas(textHh);
            String[] texts = textHh.trim().split(" ");
            if(texts.length>1) {
                String lastText = texts[texts.length - 1];
                listData.setLastData(lastText);
                lastText = lastText.replace("外勤", "");
                String[] lastTexts = lastText.split(":");
                int a1 = Integer.parseInt(lastTexts[0]);
                if(a1<6) a1 += 24;
                int b1 = a1-18;
                int b2 = Integer.parseInt(lastTexts[1])-30;
                if(b1<0 && b2<30) {
                    listData.setSurplus("没有加班");
                    listData.setZong("0");
                }else{
                    if(b2<0){
                        if(b1 == 0) {
                            listData.setSurplus("没有加班");
                            listData.setZong("0");
                        }else {
                            listData.setSurplus((b1-1) + "小时" + (60 + b2) + "分钟");
                            listData.setZong((b1-1) + ":" + (60 + b2));
                        }
                    }else {
                        listData.setSurplus(b1 + "小时" + b2 + "分钟");
                        listData.setZong(b1 + ":" + b2);
                    }
                }
            }else if(textHh.trim().length() == 5){
                textHh = textHh.replace(" ", "");
                listData.setLastData(textHh);
                textHh = textHh.replace("外勤", "");
                String[] lastTexts = textHh.split(":");
                int a1 = Integer.parseInt(lastTexts[0]);
                if(a1<6) a1 += 24;
                int b1 = a1-18;
                int b2 = Integer.parseInt(lastTexts[1])-30;
                if(b1<6 && b2<30) {
                    listData.setSurplus("没有加班");
                    listData.setZong("0");
                }else{
                    if(b2<0){
                        if(b1 == 0) {
                            listData.setSurplus("没有加班");
                            listData.setZong("0");
                        }else {
                            listData.setSurplus((b1-1) + "小时" + (60 + b2) + "分钟");
                            listData.setZong((b1-1) + ":" + (60 + b2));
                        }
                    }else {
                        listData.setSurplus(b1 + "小时" + b2 + "分钟");
                        listData.setZong(b1 + ":" + b2);
                    }
                }
            }
            itemData.getList().add(listData);
            textHh = "";
        }
        if (qName.equals("Row")) {
            provinceList.add(itemData);
        }
        if (qName.equals("Table")) {
            startGo = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(startGo) {
            chatstr = new String(ch, start, length);
            //去掉文本中原有的换行
            if (chatstr.equals("\n")){
                textHh += "";
            }else{
                textHh += chatstr;
            }
        }
    }

}
