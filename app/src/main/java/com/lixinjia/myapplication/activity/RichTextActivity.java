package com.lixinjia.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import com.lixinjia.myapplication.R;

import jp.wasabeef.richeditor.RichEditor;

/**
 * 作者：李忻佳
 * 时间：2018/3/7/007.
 * 描述：富文本编辑器
 */

public class RichTextActivity extends BaseActivity {
    private Button text;
    private Button redo;
    private Button eliminate;
    private Button italic;
    private Button title;
    private Button deletingline;
    private Button bold;
    private Button underline;
    private Button title1;
    private Button title2;
    private Button title3;
    private Button quote;
    private Button title4;
    private Button title5;
    private Button title6;
    private Button alignment;
    private Button alignmentRight;
    private Button alignmentCenter;
    private Button alignmentLeft;
    private Button selectionbox;
    private RichEditor mEditor;
    private HorizontalScrollView titlell;
    private HorizontalScrollView alignmentll;

    @Override
    public int bindLayout() {
        return R.layout.act_richtext;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("富文本编辑器");
        text = $(R.id.act_richtext_text);
        italic = $(R.id.act_richtext_italic);
        title = $(R.id.act_richtext_title);
        deletingline = $(R.id.act_richtext_deletingline);
        underline = $(R.id.act_richtext_underline);
        eliminate = $(R.id.act_richtext_eliminate);
        redo = $(R.id.act_richtext_redo);
        bold = $(R.id.act_richtext_bold);
        mEditor = $(R.id.act_richtext_editor);
        titlell = $(R.id.act_richtext_titlell);
        title1 = $(R.id.act_richtext_title1);
        title2 = $(R.id.act_richtext_title2);
        title3 = $(R.id.act_richtext_title3);
        title4 = $(R.id.act_richtext_title4);
        title5 = $(R.id.act_richtext_title5);
        title6 = $(R.id.act_richtext_title6);
        alignment = $(R.id.act_richtext_alignment);
        alignmentll = $(R.id.act_richtext_alignmentll);
        alignmentCenter = $(R.id.act_richtext_alignment_center);
        alignmentLeft = $(R.id.act_richtext_alignment_left);
        alignmentRight = $(R.id.act_richtext_alignment_right);
        quote = $(R.id.act_richtext_quote);
        selectionbox = $(R.id.act_richtext_selectionbox);


        //初始化字体大小
        mEditor.setEditorFontSize(22);
        //初始化字体颜色
        mEditor.setEditorFontColor(Color.BLACK);
        //初始化内边距
        mEditor.setPadding(10, 10, 10, 10);
        //设置编辑框背景，可以是网络图片
        // mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        // mEditor.setBackgroundColor(Color.BLUE);
        //设置默认显示语句
        mEditor.setPlaceholder("Insert text here...");
        //设置编辑器是否可用
        mEditor.setInputEnabled(true);
    }

    @Override
    public void addListener() {
        text.setOnClickListener(this);
        redo.setOnClickListener(this);
        selectionbox.setOnClickListener(this);
        eliminate.setOnClickListener(this);
        italic.setOnClickListener(this);
        title.setOnClickListener(this);
        deletingline.setOnClickListener(this);
        bold.setOnClickListener(this);
        underline.setOnClickListener(this);
        title1.setOnClickListener(this);
        title2.setOnClickListener(this);
        title3.setOnClickListener(this);
        title4.setOnClickListener(this);
        title5.setOnClickListener(this);
        title6.setOnClickListener(this);
        alignmentCenter.setOnClickListener(this);
        alignment.setOnClickListener(this);
        quote.setOnClickListener(this);
        alignmentLeft.setOnClickListener(this);
        alignmentRight.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        if (v == text) {
            Log.d("Html", mEditor.getHtml());
        }
        if (v == title) {//标题
            if(titlell.getVisibility() == View.VISIBLE){
                titlell.setVisibility(View.GONE);
            }else{
                titlell.setVisibility(View.VISIBLE);
            }
        }
        if (v == eliminate) {//撤销当前标签状态下所有内容
            mEditor.undo();
        }
        if (v == redo) {//恢复撤销的内容
            mEditor.redo();
        }
        if (v == bold) {//加粗
            mEditor.focusEditor();
            mEditor.setBold();
        }
        if (v == italic) {//斜体
            mEditor.focusEditor();
            mEditor.setItalic();
        }
        if (v == deletingline) {//删除线
            mEditor.focusEditor();
            mEditor.setStrikeThrough();
        }
        if (v == deletingline) {//下划线
            mEditor.focusEditor();
            mEditor.setUnderline();
        }
        if(v == title1){//标题1
            mEditor.setHeading(1);
        }
        if(v == title2){//标题2
            mEditor.setHeading(2);
        }
        if(v == title3){//标题3
            mEditor.setHeading(3);
        }
        if(v == title4){//标题4
            mEditor.setHeading(4);
        }
        if(v == title5){//标题5
            mEditor.setHeading(5);
        }
        if(v == title6){//标题6
            mEditor.setHeading(6);
        }
        if(v == alignment){//对齐
            if(alignmentll.getVisibility() == View.VISIBLE){
                alignmentll.setVisibility(View.GONE);
            }else{
                alignmentll.setVisibility(View.VISIBLE);
            }
        }
        if(v == alignmentCenter){//文章居中
            mEditor.setAlignCenter();
        }
        if(v == alignmentCenter){//文章左对齐
            mEditor.focusEditor();
            mEditor.setAlignLeft();
        }
        if(v == alignmentRight){//文章右对齐
            mEditor.setAlignRight();
        }
        if(v == quote){//引用
            mEditor.setBlockquote();
        }
        if(v == selectionbox){//选择框
            mEditor.focusEditor();
            mEditor.insertTodo();
        }
    }

    @Override
    public void doBusiness() {

    }

    /**
     * 需要重写这个方法选择图片、拍照才有用哦
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "取消操作", Toast.LENGTH_LONG).show();
            return;
        }
    }
}
