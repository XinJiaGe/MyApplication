package com.adaptation.lixinjia.myapplication.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.adaptation.lixinjia.myapplication.R;
import com.adaptation.lixinjia.myapplication.utils.PhotoHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 作者：李忻佳
 * 时间：2018/5/21/021.
 * 说明：
 */

public class PhotoAlbumPhotoActivity extends BaseActivity implements PhotoHandler.PhotoHandlerListener, CompoundButton.OnCheckedChangeListener {
    private CheckBox checkBox;
    private Button phont;
    private Button album;
    private TextView text;
    private ImageView iamge;
    private PhotoHandler photoHandler;

    @Override
    public int bindLayout() {
        return R.layout.act_photoalbum;
    }

    @Override
    public void initView(View view) {
        checkBox = $(R.id.act_photo_is);
        phont = $(R.id.act_photo_phont);
        album = $(R.id.act_photo_album);
        text = $(R.id.act_photo_text);
        iamge = $(R.id.act_photo_iamge);

        mTitle.setCenterText("相册拍照图片");
    }

    @Override
    public void addListener() {
        phont.setOnClickListener(this);
        album.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        if (v == phont) {//相册
            photoHandler.getPhotoFromAlbum();
        }
        if (v == album) {//拍照
            photoHandler.getPhotoFromCamera();
        }
    }

    @Override
    public void doBusiness() {
        photoHandler = new PhotoHandler(this);
        photoHandler.setmListener(this);
        checkBox.setOnCheckedChangeListener(this);
    }

    /**
     * 设置图片
     *
     * @param path
     */
    private void setImage(String path) {
        text.setText(path);
        try {
            FileInputStream fis = new FileInputStream(path);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            iamge.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photoHandler.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        photoHandler.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onResultFromAlbum(File file) {
        setImage(file.getPath());
    }

    @Override
    public void onResultFromCamera(File file) {
        setImage(file.getPath());
    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        photoHandler.setCutting(isChecked);
    }
}
