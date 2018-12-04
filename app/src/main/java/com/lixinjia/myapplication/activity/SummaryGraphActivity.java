package com.lixinjia.myapplication.activity;

import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.jiage.tool.Adaptation;
import com.jiage.view.HistogramView;
import com.jiage.view.PieChartPointerView;
import com.jiage.view.PieChartView;
import com.jiage.view.ShapePointView;
import com.jiage.view.SpotLineView;
import com.jiage.view.StraightLineView;
import com.jiage.view.TransverseHistogramView;
import com.lixinjia.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：李忻佳
 * 日期：2018/11/1
 * 说明：统计图
 */

public class SummaryGraphActivity extends BaseActivity {

    private PieChartView pieChartView;
    private Adaptation adaptation;
    private int width;
    private int height;
    private LinearLayout linearLayout;
    private PieChartPointerView pieChartPointer;
    private SpotLineView lineView;
    private ShapePointView spotView;
    private StraightLineView straightLineView;
    private HistogramView histogramView;
    private TransverseHistogramView transverseHistogramView;

    @Override
    public int bindLayout() {
        return R.layout.act_summary_graph;
    }

    @Override
    public void doView() {
        linearLayout = $(R.id.act_summary_graph);
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("统计图");
        init();
    }


    public void huan(View view) {
        huan();
    }

    public void hengzhuzhuang(View view) {
        hengzhuzhuang();
    }

    public void bing(View view) {
        bingzi();
    }

    public void zhizheng(View view) {
        yuanZheng();
    }

    public void zhedian(View view) {
        zheXianData();
    }

    public void dian(View view) {
        dianData();
    }

    public void zhexian(View view) {
        zheDianData();
    }

    public void zhuzi(View view) {
        zhuData();
    }

    public void qinkong(View view) {
        qinKong();
    }

    private void qinKong() {
        linearLayout.removeAllViews();
    }

    /**
     * 环形
     */
    private void huan() {
        pieChartView = new PieChartView(this);
        pieChartView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width - width / 3));
        pieChartView.setRingWidth(80);
        pieChartView.setClick(true);
        pieChartView.setClickRingWidth(110);
        pieChartView.setClickWidth(40);
        pieChartView.setRingDistance(2);
        pieChartView.setDataText(false);
        String[] data = new String[4];
        data[0] = "20.5";
        data[1] = "30.8";
        data[2] = "40";
        data[3] = "60";
        pieChartView.setData(data);
        String[] details = new String[4];
        details[0] = "我就是我";
        details[1] = "不一样的烟火";
        details[2] = "哈哈";
        details[3] = "呵呵呵";
        pieChartView.setDetails(details);
        int[] color = new int[4];
        color[0] = Color.parseColor("#FBD84F");
        color[1] = Color.parseColor("#39D670");
        color[2] = Color.parseColor("#FBAF1E");
        color[3] = Color.parseColor("#F84945");
        pieChartView.setDataColor(color);
        linearLayout.addView(pieChartView);
    }

    /**
     * 横向柱状
     */
    private void hengzhuzhuang() {
        transverseHistogramView = new TransverseHistogramView(this);
        transverseHistogramView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width - width / 4));
        transverseHistogramView.setCompany("次");
        //数据
        String[] data = new String[5];
        data[0] = "140";
        data[1] = "200";
        data[2] = "400";
        data[3] = "500";
        data[4] = "600";
        transverseHistogramView.setXData(data);
        transverseHistogramView.setxValueMax(adaptation.getValueMaxLargeValue(adaptation.getValueMaxMin(data).get("max")));
        //描述
        String[] text = new String[5];
        text[0] = "精神科";
        text[1] = "外科";
        text[2] = "内科";
        text[3] = "妇产科";
        text[4] = "皮肤科";
        transverseHistogramView.setXText(text);
        //柱体颜色
        String[] color = new String[5];
        color[0] = "#CDEDE4";
        color[1] = "#FADADD";
        color[2] = "#CDEDE4";
        color[3] = "#FADADD";
        color[4] = "#CDEDE4";
        transverseHistogramView.setColors(color);
        //柱体边框颜色
        String[] borderColor = new String[5];
        borderColor[0] = "#04A777";
        borderColor[1] = "#E84855";
        borderColor[2] = "#04A777";
        borderColor[3] = "#E84855";
        borderColor[4] = "#04A777";
        transverseHistogramView.setBorderColors(borderColor);
        transverseHistogramView.setDataText(true);
        linearLayout.addView(transverseHistogramView);
    }

    /**
     * 柱状图
     */
    private void zhuData() {
        histogramView = new HistogramView(this);
        histogramView.setDataXRightlength(60);
        histogramView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width - width / 4));
        //数据
        String[] data = new String[24];
        data[0] = "0";
        data[1] = "0";
        data[2] = "90";
        data[3] = "0";
        data[4] = "0";
        data[5] = "6000";
        data[6] = "0";
        data[7] = "0";
        data[8] = "8000";
        data[9] = "0";
        data[10] = "3000";
        data[11] = "0";
        data[12] = "0";
        data[13] = "10895";
        data[14] = "0";
        data[15] = "1314";
        data[16] = "0";
        data[17] = "0";
        data[18] = "15897";
        data[19] = "0";
        data[20] = "7000";
        data[21] = "0";
        data[22] = "80";
        data[23] = "0";
        histogramView.setXData(data);

        histogramView.setYValueMax(adaptation.getValueMaxLargeValue(adaptation.getValueMaxMin(data).get("max")));

        //X轴描述
        String[] text = new String[24];
        text[0] = "";
        text[1] = "";
        text[2] = "";
        text[3] = "";
        text[4] = "";
        text[5] = "6:00";
        text[6] = "";
        text[7] = "";
        text[8] = "";
        text[9] = "";
        text[10] = "";
        text[11] = "12:00";
        text[12] = "";
        text[13] = "";
        text[14] = "";
        text[15] = "";
        text[16] = "";
        text[17] = "18:00";
        text[18] = "";
        text[19] = "";
        text[20] = "";
        text[21] = "";
        text[22] = "";
        text[23] = "24:00";
        histogramView.setXText(text);
        //是否显示Y轴数据对应的网格
        Boolean[] isBrokenX = new Boolean[24];
        isBrokenX[0] = true;
        isBrokenX[1] = false;
        isBrokenX[2] = false;
        isBrokenX[3] = false;
        isBrokenX[4] = false;
        isBrokenX[5] = true;
        isBrokenX[6] = false;
        isBrokenX[7] = false;
        isBrokenX[8] = false;
        isBrokenX[9] = false;
        isBrokenX[10] = false;
        isBrokenX[11] = true;
        isBrokenX[12] = false;
        isBrokenX[13] = false;
        isBrokenX[14] = false;
        isBrokenX[15] = false;
        isBrokenX[16] = false;
        isBrokenX[17] = true;
        isBrokenX[18] = false;
        isBrokenX[19] = false;
        isBrokenX[20] = false;
        isBrokenX[21] = false;
        isBrokenX[22] = false;
        isBrokenX[23] = true;
        histogramView.setIsBrokenX(isBrokenX);
        histogramView.setDataText(true);
        linearLayout.addView(histogramView);
    }

    /**
     * 折线
     */
    private void zheDianData() {
        straightLineView = new StraightLineView(this);
        straightLineView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width - width / 4));
        straightLineView.setYValueMax(10000);
        straightLineView.setYValueMin(-10000);
        straightLineView.setDataXRightlength(40);
        straightLineView.setSplitLine(true);
        straightLineView.setTitle(true);
        straightLineView.setRing(true);
        straightLineView.setDataText(true);
        straightLineView.setTitleHeight(140);
        straightLineView.setTitleLeftText("哈哈");
        straightLineView.setTitleLeftImag(true);
        straightLineView.setTitleRight(true);
        straightLineView.setTitleLeftImgSrc(R.mipmap.ic_launcher);
        //X轴的text
        String[] text4 = new String[30];
        text4[0] = "1";
        text4[1] = "";
        text4[2] = "";
        text4[3] = "";
        text4[4] = "5";
        text4[5] = "";
        text4[6] = "";
        text4[7] = "";
        text4[8] = "";
        text4[9] = "10";
        text4[10] = "";
        text4[11] = "";
        text4[12] = "";
        text4[13] = "";
        text4[14] = "15";
        text4[15] = "";
        text4[16] = "";
        text4[17] = "";
        text4[18] = "";
        text4[19] = "20";
        text4[20] = "";
        text4[21] = "";
        text4[22] = "";
        text4[23] = "";
        text4[24] = "25";
        text4[25] = "";
        text4[26] = "";
        text4[27] = "";
        text4[28] = "";
        text4[29] = "30";
        straightLineView.setXText(text4);
        //是否显示Y轴数据对应的网格
        Boolean[] isBrokenX2 = new Boolean[30];
        isBrokenX2[0] = true;
        isBrokenX2[1] = false;
        isBrokenX2[2] = false;
        isBrokenX2[3] = false;
        isBrokenX2[4] = true;
        isBrokenX2[5] = false;
        isBrokenX2[6] = false;
        isBrokenX2[7] = false;
        isBrokenX2[8] = false;
        isBrokenX2[9] = true;
        isBrokenX2[10] = false;
        isBrokenX2[11] = false;
        isBrokenX2[12] = false;
        isBrokenX2[13] = false;
        isBrokenX2[14] = true;
        isBrokenX2[15] = false;
        isBrokenX2[16] = false;
        isBrokenX2[17] = false;
        isBrokenX2[18] = false;
        isBrokenX2[19] = true;
        isBrokenX2[20] = false;
        isBrokenX2[21] = false;
        isBrokenX2[22] = false;
        isBrokenX2[23] = false;
        isBrokenX2[24] = true;
        isBrokenX2[25] = false;
        isBrokenX2[26] = false;
        isBrokenX2[27] = false;
        isBrokenX2[28] = false;
        isBrokenX2[29] = true;
        straightLineView.setIsBrokenX(isBrokenX2);
        //数据
        List<int[]> listdata4 = new ArrayList<>();
        int[] data4 = new int[30];
        data4[0] = -9999;
        data4[1] = -5000;
        data4[2] = -1000;
        data4[3] = 0;
        data4[4] = 500;
        data4[5] = 900;
        data4[6] = 1000;
        data4[7] = 1200;
        data4[8] = 1400;
        data4[9] = 1000;
        data4[10] = 800;
        data4[11] = 300;
        data4[12] = 52;
        data4[13] = 0;
        data4[14] = 100;
        data4[15] = 500;
        data4[16] = 800;
        data4[17] = 1200;
        data4[18] = 3577;
        data4[19] = 4788;
        data4[20] = 5388;
        data4[21] = 7777;
        data4[22] = -4000;
        data4[23] = -7000;
        data4[24] = -1000;
        data4[25] = 2222;
        data4[26] = 999;
        data4[27] = 111;
        data4[28] = 22;
        data4[29] = 8888;
        listdata4.add(data4);
        int[] data5 = new int[30];
        data5[0] = 9999;
        data5[1] = 8888;
        data5[2] = 7777;
        data5[3] = 6666;
        data5[4] = 5555;
        data5[5] = 4444;
        data5[6] = 3333;
        data5[7] = 2222;
        data5[8] = 1111;
        data5[9] = 0;
        data5[10] = 4444;
        data5[11] = 5555;
        data5[12] = 7777;
        data5[13] = 8888;
        data5[14] = 9999;
        data5[15] = 6666;
        data5[16] = 5555;
        data5[17] = 4444;
        data5[18] = 3333;
        data5[19] = 2222;
        data5[20] = 1111;
        data5[21] = 999;
        data5[22] = 888;
        data5[23] = 777;
        data5[24] = 666;
        data5[25] = 555;
        data5[26] = 444;
        data5[27] = 333;
        data5[28] = 222;
        data5[29] = 111;
        listdata4.add(data5);
        straightLineView.setXData(listdata4);
        //数据渐变颜色
        List<int[]> gradientColorList = new ArrayList<>();
        int[] gradientColor1 = new int[2];
        gradientColor1[0] = Color.DKGRAY;
        gradientColor1[1] = Color.WHITE;
        gradientColorList.add(gradientColor1);
        int[] gradientColor2 = new int[2];
        gradientColor2[0] = Color.GREEN;
        gradientColor2[1] = Color.WHITE;
        gradientColorList.add(gradientColor2);
        straightLineView.setGradientColor(gradientColorList);
        //数据对应右上角的字体描述
        String[] dataTitleRightText = new String[2];
        dataTitleRightText[0] = "梦想";
        dataTitleRightText[1] = "现实";
        straightLineView.setDataTitleRightText(dataTitleRightText);
        //数据对应右上角的字体描述左边的长方形内的颜色
        int[] titleRightRectangleWithinColor = new int[2];
        titleRightRectangleWithinColor[0] = Color.CYAN;
        titleRightRectangleWithinColor[1] = Color.RED;
        straightLineView.setTitleRightRectangleWithinColorList(titleRightRectangleWithinColor);
        //数据颜色
        int[] listdataColor4 = new int[2];
        listdataColor4[0] = Color.RED;
        listdataColor4[1] = Color.CYAN;
        straightLineView.setXDataColor(listdataColor4);
        //数据对应的圆环颜色
        int[] ringColor = new int[2];
        ringColor[0] = Color.BLUE;
        ringColor[1] = Color.RED;
        straightLineView.setXDataRingColor(ringColor);
        //总目标值
        int[] totalTargetValue = new int[2];
        totalTargetValue[0] = 5000;
        totalTargetValue[1] = 7500;
        straightLineView.settotalTargetValue(totalTargetValue);
        //总目标值颜色
        int[] totalTargetValueColor = new int[2];
        totalTargetValueColor[0] = Color.GRAY;
        totalTargetValueColor[1] = Color.GREEN;
        straightLineView.settotalTargetValueColor(totalTargetValueColor);
        //总目标值右上角描述
        String[] totalTargetTitleRightText = new String[2];
        totalTargetTitleRightText[0] = "日天";
        totalTargetTitleRightText[1] = "日地";
        straightLineView.setTotalTargetTitleRightText(totalTargetTitleRightText);
        //阶段目标值
        List<int[]> targetValueList = new ArrayList<>();
        int[] targetValue = new int[30];
        targetValue[0] = 200;
        targetValue[1] = 200;
        targetValue[2] = 100;
        targetValue[3] = 200;
        targetValue[4] = 300;
        targetValue[5] = 400;
        targetValue[6] = 5000;
        targetValue[7] = 5200;
        targetValue[8] = 5400;
        targetValue[9] = 5000;
        targetValue[10] = 1800;
        targetValue[11] = 2300;
        targetValue[12] = 352;
        targetValue[13] = 40;
        targetValue[14] = 1000;
        targetValue[15] = 5000;
        targetValue[16] = 8000;
        targetValue[17] = 1800;
        targetValue[18] = 7577;
        targetValue[19] = 8788;
        targetValue[20] = 6388;
        targetValue[21] = 4777;
        targetValue[22] = 1888;
        targetValue[23] = 2999;
        targetValue[24] = 3555;
        targetValue[25] = 8222;
        targetValue[26] = 4999;
        targetValue[27] = 1511;
        targetValue[28] = 9922;
        targetValue[29] = 2888;
        targetValueList.add(targetValue);
        straightLineView.setTargetValue(targetValueList);
        //阶段目标值颜色
        int[] targetValueColor = new int[1];
        targetValueColor[0] = Color.YELLOW;
        straightLineView.setTargetValueColor(targetValueColor);
        //阶段目标值右上角描述
        String[] targetValueTitleRightText = new String[1];
        targetValueTitleRightText[0] = "傻逼";
        straightLineView.setTargetValueTitleRightText(targetValueTitleRightText);
        linearLayout.addView(straightLineView);
    }

    /**
     * 点图
     */
    private void dianData() {
        spotView = new ShapePointView(this);
        spotView.setDataXRightlength(60);
        spotView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width - width / 4));
        spotView.setDataText(true);
        //X轴的text右边的图片
        int[] imgas2 = new int[5];
        imgas2[0] = 0;
        imgas2[1] = R.drawable.icon_warning;
        imgas2[2] = 0;
        imgas2[3] = R.drawable.icon_warning;
        imgas2[4] = 0;
        spotView.setTextRightImage(imgas2);
        //X轴的text
        String[] text3 = new String[5];
        text3[0] = "";
        text3[1] = "6:00";
        text3[2] = "12:00";
        text3[3] = "18:00";
        text3[4] = "21:00";
        spotView.setXText(text3);
        //三角形数据
        int[] Triangle = new int[5];
        Triangle[0] = 0;
        Triangle[1] = 100;
        Triangle[2] = 0;
        Triangle[3] = 0;
        Triangle[4] = 0;
        spotView.setTriangleData(Triangle);
        //三角形数据颜色
        int[] TriangleColor = new int[5];
        TriangleColor[0] = 0;
        TriangleColor[1] = Color.RED;
        TriangleColor[2] = 0;
        TriangleColor[3] = 0;
        TriangleColor[4] = 0;
        spotView.setTriangleDataColor(TriangleColor);
        //圆形数据
        int[] Circular = new int[5];
        Circular[0] = 0;
        Circular[1] = 0;
        Circular[2] = 0;
        Circular[3] = 0;
        Circular[4] = 150;
        spotView.setCircularData(Circular);
        //圆形数据颜色
        int[] CircularColor = new int[5];
        CircularColor[0] = 0;
        CircularColor[1] = 0;
        CircularColor[2] = 0;
        CircularColor[3] = 0;
        CircularColor[4] = Color.BLUE;
        spotView.setCircularDataColor(CircularColor);
        //正方形数据
        int[] Square = new int[5];
        Square[0] = 0;
        Square[1] = 0;
        Square[2] = 150;
        Square[3] = 100;
        Square[4] = 0;
        spotView.setSquareData(Square);
        //正方形数据颜色
        int[] SquareColor = new int[5];
        SquareColor[0] = 0;
        SquareColor[1] = 0;
        SquareColor[2] = Color.CYAN;
        SquareColor[3] = Color.BLACK;
        SquareColor[4] = 0;
        spotView.setSquareDataColor(SquareColor);

        spotView.setTitle(true);
        spotView.setTitleHeight(140);
        spotView.setTitleLeftText("哈哈");
        spotView.setTitleLeftImag(true);
        spotView.setTitleRight(true);
        spotView.setTitleLeftImgSrc(R.mipmap.ic_launcher);
        spotView.setTitleLeftImgWidth(100);
        spotView.setTitleLeftImgheight(100);
        spotView.setTitleRight(true);

        List<HashMap<String, Object>> titleRightList = new ArrayList<>();
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("color", Color.RED);
        map1.put("text", "哈哈");
        map1.put("type", "1");
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("color", Color.DKGRAY);
        map2.put("text", "二哈");
        map2.put("type", "2");
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put("color", Color.BLUE);
        map3.put("text", "一哈");
        map3.put("type", "3");
        titleRightList.add(map1);
        titleRightList.add(map2);
        titleRightList.add(map3);
        spotView.setTitleRightData(titleRightList);

        linearLayout.addView(spotView);
    }

    /**
     * 折线图
     */
    private void zheXianData() {
        lineView = new SpotLineView(this);
        lineView.setDataXRightlength(40);
        lineView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width - width / 4));
        lineView.setDataText(true);
        //数据集合
        List<int[]> listData = new ArrayList<>();
        //目标范围集合
        List<int[]> targetRange = new ArrayList<>();
        //数据点颜色集合
        List<int[]> listDataPointColor = new ArrayList<>();
        //数据点后面的线的颜色集合
        List<int[]> listDataPointLineColor = new ArrayList<>();
        //数据点是否为空心集合
        List<Boolean[]> listDataPointIsHollow = new ArrayList<>();
        //数据1
        int[] data2 = new int[5];
        data2[0] = 0;
        data2[1] = 50;
        data2[2] = 180;
        data2[3] = 60;
        data2[4] = 100;
        listData.add(data2);
        //数据1点颜色
        int[] dataPointColor = new int[5];
        dataPointColor[0] = Color.RED;
        dataPointColor[1] = Color.BLUE;
        dataPointColor[2] = Color.BLACK;
        dataPointColor[3] = Color.RED;
        dataPointColor[4] = Color.RED;
        listDataPointColor.add(dataPointColor);
        //数据1点后面的线的颜色
        int[] dataPointLineColor = new int[4];
        dataPointLineColor[0] = Color.RED;
        dataPointLineColor[1] = Color.BLACK;
        dataPointLineColor[2] = Color.RED;
        dataPointLineColor[3] = Color.BLUE;
        listDataPointLineColor.add(dataPointLineColor);
        //数据1的值的圆环是否为空心
        Boolean[] isDataPointIsHollow = new Boolean[5];
        isDataPointIsHollow[0] = true;
        isDataPointIsHollow[1] = false;
        isDataPointIsHollow[2] = true;
        isDataPointIsHollow[3] = false;
        isDataPointIsHollow[4] = true;
        listDataPointIsHollow.add(isDataPointIsHollow);
        //数据2
        int[] data3 = new int[5];
        data3[0] = 0;
        data3[1] = 150;
        data3[2] = 30;
        data3[3] = 40;
        data3[4] = 20;
        listData.add(data3);
        //数据2点颜色
        int[] dataPointColor2 = new int[5];
        dataPointColor2[0] = Color.RED;
        dataPointColor2[1] = Color.CYAN;
        dataPointColor2[2] = Color.BLACK;
        dataPointColor2[3] = Color.GREEN;
        dataPointColor2[4] = Color.RED;
        listDataPointColor.add(dataPointColor2);
        //数据2点后面的线的颜色
        int[] dataPointLineColor2 = new int[4];
        dataPointLineColor2[0] = Color.WHITE;
        dataPointLineColor2[1] = Color.BLACK;
        dataPointLineColor2[2] = Color.DKGRAY;
        dataPointLineColor2[3] = Color.BLUE;
        listDataPointLineColor.add(dataPointLineColor2);
        //数据2的值的圆环是否为空心
        Boolean[] isDataPointIsHollow2 = new Boolean[5];
        isDataPointIsHollow2[0] = false;
        isDataPointIsHollow2[1] = true;
        isDataPointIsHollow2[2] = false;
        isDataPointIsHollow2[3] = true;
        isDataPointIsHollow2[4] = false;
        listDataPointIsHollow.add(isDataPointIsHollow2);
        //X轴的text
        String[] text2 = new String[5];
        text2[0] = "";
        text2[1] = "6:00";
        text2[2] = "12:00";
        text2[3] = "18:00";
        text2[4] = "21:00";
        //X轴的text右边的图片
        int[] imgas = new int[5];
        imgas[0] = 0;
        imgas[1] = R.drawable.icon_warning;
        imgas[2] = 0;
        imgas[3] = R.drawable.icon_warning;
        imgas[4] = 0;
        int[] range = new int[2];
        range[0] = 150;
        range[1] = 100;
        targetRange.add(range);
        int[] range2 = new int[2];
        range2[0] = 60;
        range2[1] = 30;
        targetRange.add(range2);
        int[] rangeColor = new int[2];
        rangeColor[0] = Color.parseColor("#FBD84F");
        rangeColor[1] = Color.parseColor("#39D670");
        lineView.setTargetRangeColor(rangeColor);
        lineView.setTargetRange(targetRange);
        lineView.setTargetRangeAlpha(50);
        lineView.setTextRightImage(imgas);
        lineView.setXText(text2);
        lineView.setXData(listData);
        lineView.setDataPointColor(listDataPointColor);
        lineView.setDataPointLineColor(listDataPointLineColor);
        lineView.setDataPointIsHollow(listDataPointIsHollow);

        lineView.setTitle(true);
        lineView.setTitleHeight(140);
        lineView.setTitleLeftText("哈哈");
        lineView.setTitleLeftImag(true);
        lineView.setTitleRight(true);
        lineView.setTitleLeftImgSrc(R.mipmap.ic_launcher);
        lineView.setTitleLeftImgWidth(100);
        lineView.setTitleLeftImgheight(100);
        lineView.setTitleRight(true);

        List<HashMap<String, Object>> titleRightList = new ArrayList<>();
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("hollow", true);
        map1.put("color", Color.RED);
        map1.put("text", "哈哈");
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("hollow", false);
        map2.put("color", Color.DKGRAY);
        map2.put("text", "二哈");
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put("hollow", true);
        map3.put("color", Color.BLUE);
        map3.put("text", "一哈");
        titleRightList.add(map1);
        titleRightList.add(map2);
        titleRightList.add(map3);
        lineView.setTitleRightData(titleRightList);

        linearLayout.addView(lineView);
    }

    /**
     * 圆形指针
     */
    private void yuanZheng() {
        pieChartPointer = new PieChartPointerView(this);
        pieChartPointer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width + width / 7));
        pieChartPointer.setPointerData("30");
        pieChartPointer.setTitle("BmI");
        pieChartPointer.setTitle2("健康体重");
        pieChartPointer.setDynamic(true);
        String[] text6 = new String[5];
        text6[0] = "10";
        text6[1] = "18";
        text6[2] = "24";
        text6[3] = "28";
        text6[4] = "40";
        pieChartPointer.setData(text6);
        int[] color = new int[4];
        color[0] = Color.parseColor("#FBD84F");
        color[1] = Color.parseColor("#39D670");
        color[2] = Color.parseColor("#FBAF1E");
        color[3] = Color.parseColor("#F84945");
        pieChartPointer.setDataColor(color);
        String[] dataDescribe = new String[4];
        dataDescribe[0] = "宝宝";
        dataDescribe[1] = "贝贝";
        dataDescribe[2] = "小小";
        dataDescribe[3] = "乖乖";
        pieChartPointer.setDataDescribe(dataDescribe);
        linearLayout.addView(pieChartPointer);
    }

    /**
     * 饼图
     */
    private void bingzi() {
        pieChartView = new PieChartView(this);
        pieChartView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width - width / 4));

        pieChartView.setTitle(true);
        pieChartView.setTitleHeights(140);

        String[] data = new String[4];
        data[0] = "20.5";
        data[1] = "30";
        data[2] = "40";
        data[3] = "10.5";
        pieChartView.setData(data);
        String[] text = new String[4];
        text[0] = "白痴";
        text[1] = "白痴的";
        text[2] = "白痴分";
        text[3] = "白个痴";
        pieChartView.setDataText(text);
        int[] color = new int[4];
        color[0] = Color.parseColor("#FBD84F");
        color[1] = Color.parseColor("#39D670");
        color[2] = Color.parseColor("#FBAF1E");
        color[3] = Color.parseColor("#F84945");
        pieChartView.setDataColor(color);
        linearLayout.addView(pieChartView);
    }

    private void init() {
        adaptation = new Adaptation(this);
        WindowManager wm = this.getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
    }
}
