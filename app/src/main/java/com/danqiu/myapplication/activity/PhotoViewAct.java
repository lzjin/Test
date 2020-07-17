package com.danqiu.myapplication.activity;

import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.danqiu.myapplication.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lzj on 2020/3/27
 * Describe ：图片缩放库
 * 长图显示可缩放 库subsampling-scale-image-view
 * https://github.com/davemorrissey/subsampling-scale-image-view
 */
public class PhotoViewAct extends BaseActivity {
    @BindView(R.id.photo_view)
    PhotoView photoView;
    @Override
    public void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_photo_view);
        ButterKnife.bind(this);
        ImmersionBar.with(this).init();
        //本地
        // photoView.setImageResource(R.mipmap.a);
        //网络
        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594877186625&di=5890d642e62f71f69bda9926232d53a5&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn17%2F662%2Fw640h822%2F20180806%2F7a40-hhhczfc6181567.jpg").into(photoView);
    }



}
