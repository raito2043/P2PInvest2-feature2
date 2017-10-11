package com.example.raito.p2pinvest.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.common.BaseActivity;
import com.example.raito.p2pinvest.fragment.MineFragment;
import com.example.raito.p2pinvest.utils.BitmapUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Raito on 2017/10/10.
 */

public class UserInfoActivity extends BaseActivity {
    private static final int PICTURE = 100;
    private static final int CAMERA = 200;
    @BindView(R.id.img_pre)
    ImageView imgPre;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @BindView(R.id.tv_user_change)
    TextView tvUserChange;
    @BindView(R.id.btn_user_logout)
    Button btnUserLogout;
    private Intent picture;


    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {

        btnUserLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //移除用户信息
                removeUserInfo();
                //Toast.makeText(UserInfoActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
                new MainActivity().setTextColorAndImg(2);
                goToActivity(MineFragment.class,null);
                finish();
            }
        });
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }


    @OnClick({R.id.iv_user_icon, R.id.btn_user_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_icon:
                //修改头像
                String[] item = new String[]{"从手机相册选择", "拍照"};
                //显示对话框
                new AlertDialog.Builder(this).setTitle("选择头像").setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //判断点击的哪一个
                        switch (which) {
                            case 0:
                                //隐式意图开启图库，带返回值
                                picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(picture, PICTURE);

                                break;
                            case 1:
                                //调用照相机
                                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(camera, CAMERA);
                                break;

                        }
                    }//点击空白无效
                }).setCancelable(true).show();

                break;
            case R.id.btn_user_logout:
                break;
        }
    }

    //重写启动新的activity以后的回调方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA && resultCode == RESULT_OK && data != null) {
            //得到返回数据
            Bundle bundle = data.getExtras();

            //强转泛型
            Bitmap bitmap = (Bitmap) bundle.get("data");
            //压缩图片&&做圆形处理
            //bitmap = BitmapUtils.zoom(bitmap, ivUserIcon.getWidth(), ivUserIcon.getHeight());
            bitmap = BitmapUtils.circleBitmap(bitmap);
            //设置到布局
            ivUserIcon.setImageBitmap(bitmap);

            //上传服务器
            //保存本地
            //保存到本地
            saveImage(bitmap);
        } else if (requestCode == PICTURE && resultCode == RESULT_OK && data != null) {
            //图库地址不同，不同的Android系统提供的uri的B部分不同
            //图库

            Uri uri = data.getData();

            Uri uri1 = geturi(data);
            //android各个不同的系统版本,对于获取外部存储上的资源，返回的Uri对象都可能各不一样,
            // 所以要保证无论是哪个系统版本都能正确获取到图片资源的话就需要针对各种情况进行一个处理了
            //这里返回的uri情况就有点多了
            //在4.4.2之前返回的uri是:content://media/external/images/media/3951或者file://....
            // 在4.4.2返回的是content://com.android.providers.media.documents/document/image
            Log.i("s", "返回数据" + uri + "----" + uri1);

            String pathResult = getPath(uri);//不同系统下获取uri地址 /external/images/media/24
            //String pathResult1 = uri.getPath();
            Log.i("s", "返回数据" + pathResult);
            //存储--->内存
            Bitmap decodeFile = BitmapFactory.decodeFile(pathResult);
            Log.i("s", "返回数据" + decodeFile);
            // Bitmap zoomBitmap = BitmapUtils.zoom(decodeFile, ivUserIcon.getWidth(), ivUserIcon.getHeight());
            //bitmap圆形裁剪
            Bitmap circleImage = BitmapUtils.circleBitmap(decodeFile);

            //加载显示
            ivUserIcon.setImageBitmap(circleImage);
            //上传到服务器（省略）

            //保存到本地
            saveImage(circleImage);


        } else {

            Log.i("s", "没有获取成功");
        }


    }

    //将Bitmap保存到本地的操作

    /**
     * 数据的存储。（5种）
     * Bimap:内存层面的图片对象。
     * <p>
     * 存储--->内存：
     * BitmapFactory.decodeFile(String filePath);
     * BitmapFactory.decodeStream(InputStream is);
     * 内存--->存储：
     * bitmap.compress(Bitmap.CompressFormat.PNG,100,OutputStream os);
     */
    private void saveImage(Bitmap bitmap) {
        File filesDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//判断sd卡是否挂载
            //路径1：storage/sdcard/Android/data/包名/files
            filesDir = this.getExternalFilesDir("");

        } else {//手机内部存储
            //路径：data/data/包名/files
            filesDir = this.getFilesDir();

        }
        FileOutputStream fos = null;
        try {
            File file = new File(filesDir, "icon.png");
            fos = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //小米手机不能正确获取图片uri的问题
    public Uri geturi(android.content.Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
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

    /**
     * 根据系统相册选择的文件获取路径
     *
     * @param uri
     */
    @SuppressLint("NewApi")
    private String getPath(Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        //高于4.4.2的版本
        if (sdkVersion >= 19) {
            Log.e("TAG", "uri auth: " + uri.getAuthority());
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(this, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(this, contentUri, selection, selectionArgs);
            } else if (isMedia(uri)) {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor = this.managedQuery(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                return actualimagecursor.getString(actual_image_column_index);
            }


        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * uri路径查询字段
     *
     * @param context
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isMedia(Uri uri) {
        return "media".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
