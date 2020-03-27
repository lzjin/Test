package com.danqiu.myapplication.activity;

import android.os.Bundle;

import com.danqiu.myapplication.R;
import com.github.chrisbanes.photoview.PhotoView;

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

         photoView.setImageResource(R.mipmap.a);
        // photoView.setImageURI();
    }



}
