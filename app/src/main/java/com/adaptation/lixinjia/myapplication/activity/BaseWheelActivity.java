package com.adaptation.lixinjia.myapplication.activity;

import android.content.res.AssetManager;

import com.adaptation.lixinjia.myapplication.model.BBBModel;
import com.adaptation.lixinjia.myapplication.utils.XmlParserBBBHandler;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 作者：李忻佳
 * 时间：2018/6/5/005.
 * 说明：
 */

public abstract class BaseWheelActivity extends BaseActivity {

    private List<BBBModel> provinceList;

    protected List<BBBModel> initProvinceDatas(){
        try {
            AssetManager asset = getAssets();
            InputStream input = asset.open("bbb.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserBBBHandler handler = new XmlParserBBBHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return provinceList;
    }
}
