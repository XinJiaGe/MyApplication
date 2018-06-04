package com.adaptation.lixinjia.myapplication.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;

import com.adaptation.lixinjia.myapplication.BuildConfig;

import java.io.File;

/**
 * 作者：李忻佳
 * 时间：2018/5/23/023.
 * 说明：图片相册选择器
 */

public class PhotoHandler {
    private FragmentActivity mActivity;
    private Fragment mFragment;
    private boolean isCutting = true;//是否裁剪
    private String mImagePath;
    private File mCurrentFile;
    private Uri mUritempFile;
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private int CODE_CAMERA_REQUEST = 10001;
    private int CODE_GALLERY_REQUEST = 10002;
    private int CODE_RESULT_REQUEST = 10003;
    private int PHOTOGRAPHING_AUTHORITY_REQUEST = 10004;
    private PhotoHandlerListener mListener;

    public void setmListener(PhotoHandlerListener mListener) {
        this.mListener = mListener;
    }

    public PhotoHandler(FragmentActivity mActivity) {
        this.mActivity = mActivity;
        init();
    }

    public PhotoHandler(Fragment mFragment) {
        this.mFragment = mFragment;
        if (mFragment != null) {
            this.mActivity = mFragment.getActivity();
        }
        init();
    }

    private void init() {
        mImagePath = mActivity.getExternalCacheDir().getPath();
    }

    public void getPhotoFromAlbum() {
        choosePicture();
    }

    public void getPhotoFromCamera() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(mActivity, PERMISSIONS_STORAGE[0]);
            int duPermission = ContextCompat.checkSelfPermission(mActivity, PERMISSIONS_STORAGE[1]);
            int xiePermission = ContextCompat.checkSelfPermission(mActivity, PERMISSIONS_STORAGE[2]);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED ||
                    duPermission != PackageManager.PERMISSION_GRANTED ||
                    xiePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mActivity, PERMISSIONS_STORAGE, PHOTOGRAPHING_AUTHORITY_REQUEST);
                return;
            } else {
                takePicture();
            }
        } else {
            takePicture();
        }
    }

    /**
     * 相册
     */
    private void choosePicture() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, CODE_GALLERY_REQUEST);
    }

    /**
     * 拍照
     */
    private void takePicture() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(mActivity.getPackageManager()) != null) {
            File path = new File(mImagePath);
            if (!path.exists()) {
                path.mkdir();
            }
            mCurrentFile = new File(mImagePath, "take_photo.jpg");

            Uri uri = FileProvider.getUriForFile(mActivity, mActivity.getPackageName() + ".provider", mCurrentFile);
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(cameraIntent, CODE_CAMERA_REQUEST);
        }
    }

    /**
     * 裁剪
     *
     * @param uri
     */
    public void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", false);

        //uritempFile为Uri类变量，实例化uritempFile，转化为uri方式解决问题
        mUritempFile = Uri.parse("file://" + "/" + mActivity.getExternalCacheDir() + "/" + "take_photo.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 用户没有进行有效的设置操作，返回
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        if (requestCode == CODE_CAMERA_REQUEST) {//拍照
            if (resultCode == Activity.RESULT_OK) {
                if (!isCutting()) {
                    if (mListener != null) {
                        mListener.onResultFromAlbum(mCurrentFile);
                    }
                } else
                    cropRawPhoto(FileProvider.getUriForFile(mActivity, BuildConfig.APPLICATION_ID + ".provider", mCurrentFile));
            }
        }
        if (requestCode == CODE_GALLERY_REQUEST) {//相册
            if (resultCode == Activity.RESULT_OK) {
                if (!isCutting()) {
                    Uri path = geturi(data);
                    String paths = path.getPath();
                    if ("/raw/".equals(paths.substring(0, 5))) {
                        paths = paths.substring(4, paths.length());
                    }
                    if (mListener != null) {
                        mListener.onResultFromCamera(new File(paths));
                    }
                } else
                    cropRawPhoto(data.getData());
            }
        }
        if (requestCode == CODE_RESULT_REQUEST) {//裁剪
            if (resultCode == Activity.RESULT_OK) {
                if (mListener != null) {
                    mListener.onResultFromAlbum(new File(mUritempFile.getPath()));
                }
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PHOTOGRAPHING_AUTHORITY_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture();
            } else {
                if (mListener != null) {
                    mListener.onFailure("权限被禁止，请前往应用开启权限");
                }
            }
        }
    }

    /**
     * 解决小米手机上获取图片路径为null的情况
     *
     * @param intent
     * @return
     */
    public Uri geturi(android.content.Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = mActivity.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    public void startActivity(Intent intent) {
        if (mFragment != null) {
            mFragment.startActivity(intent);
        } else {
            if (mActivity != null) {
                mActivity.startActivity(intent);
            }
        }
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        if (mFragment != null) {
            mFragment.startActivityForResult(intent, requestCode);
        } else {
            if (mActivity != null) {
                mActivity.startActivityForResult(intent, requestCode);
            }
        }
    }

    public interface PhotoHandlerListener {
        public void onResultFromAlbum(File file);

        public void onResultFromCamera(File file);

        public void onFailure(String msg);
    }

    public boolean isCutting() {
        return isCutting;
    }

    public void setCutting(boolean cutting) {
        isCutting = cutting;
    }
}
