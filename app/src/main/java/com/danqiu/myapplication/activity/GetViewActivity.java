package com.danqiu.myapplication.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.config.Constants;
import com.danqiu.myapplication.fresco.ImageLoader;
import com.danqiu.myapplication.utils.BitmapUtil;
import com.danqiu.myapplication.utils.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by lzj on 2020/3/9
 * Describe ：保存截图
 */
public class GetViewActivity extends AppCompatActivity {
    @BindView(R.id.rel_title)
    RelativeLayout rel_title;
    @BindView(R.id.img_bg)
    ImageView imgBg;
    @BindView(R.id.simple_bg)
    SimpleDraweeView simpleBg;
    @BindView(R.id.liner_bg)
    LinearLayout linerBg;
    private String url = "http://www.people.com.cn/mediafile/pic/20161022/76/4315084153778263996.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        ButterKnife.bind(this);
        ImageLoader.loadImage(simpleBg, url);//普通
        BitmapUtil.mkFile(Constants.HEAD_IMA_PATH);
    }

    @OnClick({R.id.rel_title,R.id.img_bg, R.id.simple_bg, R.id.liner_bg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rel_title://截图全屏
                Bitmap bitmap1=BitmapUtil.shotActivityNoStatusBar(this);
                if(bitmap1!=null){
                    BitmapUtil.saveBitmap(bitmap1, Constants.CAMERA_IMA_PATH+"a.jpg");
                    ToastUtil.showShort(this,"保存成功");
                    openScreenshot(Constants.CAMERA_IMA_PATH+"a.jpg");
                }else {
                    ToastUtil.showShort(this,"保存失败");
                }
                break;
            case R.id.img_bg://截图ImageView
                Bitmap bitmap2=BitmapUtil.getViewBitmap(imgBg);
                if(bitmap2!=null){
                    BitmapUtil.saveBitmap(bitmap2, Constants.CAMERA_IMA_PATH+"b.jpg");
                    ToastUtil.showShort(this,"保存成功");
                    openScreenshot(Constants.CAMERA_IMA_PATH+"b.jpg");
                }else {
                    ToastUtil.showShort(this,"保存失败");
                }
                break;
            case R.id.simple_bg://SimpleDraweeView
                Bitmap bitmap3=BitmapUtil.getViewBitmap(simpleBg);
                if(bitmap3!=null){
                    BitmapUtil.saveBitmap(bitmap3, Constants.CAMERA_IMA_PATH+"c.jpg");
                    ToastUtil.showShort(this,"保存成功");
                    openScreenshot(Constants.CAMERA_IMA_PATH+"c.jpg");
                }else {
                    ToastUtil.showShort(this,"保存失败");
                }
                break;
            case R.id.liner_bg:
                Bitmap bitmap4=BitmapUtil.getViewBitmap(linerBg);
                if(bitmap4!=null){
                    BitmapUtil.saveBitmap(bitmap4, Constants.CAMERA_IMA_PATH+"d.jpg");
                    ToastUtil.showShort(this,"保存成功");
                    openScreenshot(Constants.CAMERA_IMA_PATH+"d.jpg");
                }else {
                    ToastUtil.showShort(this,"保存失败");
                }
                break;
        }
    }

    public  void openScreenshot(String filePath) {
        //openImage(filePath);
        File imageFile=new File(filePath);
        if (imageFile.exists()){
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivity(intent);
        }

    }


    public void openImage(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setType( "image/*");
            startActivity(intent);
        }else{
            Toast.makeText(this, "图片不存在", Toast.LENGTH_SHORT).show();
        }

    }

}
