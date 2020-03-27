package com.danqiu.myapplication.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.config.Constants;
import com.danqiu.myapplication.fresco.ImageLoader;
import com.danqiu.myapplication.glide.GlideUtils;
import com.danqiu.myapplication.model.LoginModel;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2018/9/26.
 * 图片框架使用
 */

public class FrescoActivity extends BaseActivity {
    @BindView(R.id.iv_glide)
    ImageView ivGlide;
    @BindView(R.id.iv_Radius)
    ImageView ivRadius;
    @BindView(R.id.iv_Radius_Top)
    ImageView ivRadiusTop;
    @BindView(R.id.iv_Circle)
    ImageView ivCircle;
    private String apkPath = Constants.SOFT_FILE_PATH + Constants.HEAD_DOWN_NAME;
    private String url = "http://www.people.com.cn/mediafile/pic/20161022/76/4315084153778263996.jpg";

    @BindView(R.id.bt_download)
    Button btDownload;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.simple_GaussianBlur)
    SimpleDraweeView simpleGaussianBlur;//高斯模糊

    @BindView(R.id.simple_Ptong)
    SimpleDraweeView simplePtong;//普通

    @BindView(R.id.simple_Radius)
    SimpleDraweeView simpleRadius;//圆角

    @BindView(R.id.simple_Circle)
    SimpleDraweeView simpleCircle;//圆图


    LoginModel loginModel;


    @Override
    public void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fresco);
        ButterKnife.bind(this);

        ImageLoader.loadImage(simplePtong, url);//普通
        ImageLoader.loadImage(simpleRadius, url);//圆角
        ImageLoader.loadImage(simpleCircle, url);//圆图
        ImageLoader.loadImageBlur(simpleGaussianBlur, url);//高斯模糊

        GlideUtils.loadImage(this,url,ivGlide);
        GlideUtils.loadImageCorners(this,url,20,ivRadius);
        GlideUtils.loadImageCorners(this,url,20,RoundedCornersTransformation.CornerType.TOP,ivRadiusTop);
        GlideUtils.loadCircleImage(this,url,ivCircle);

    }


    /**
     * 事件 下载
     */
    @OnClick(R.id.bt_download)
    public void onViewClicked() {
        loginModel = null;
        loginModel = new LoginModel(this, dialog);
        //loginModel.getEvaluationList("vRgLZ1h3us5qiwN+Vu/GReeiDoAEF40s", 1, 3);
        loginModel.downloadFile("http://img-1253650823.cosgz.myqcloud.com/dqonline.apk", apkPath, Constants.APP_FILE_NAME_APK, progressBar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
