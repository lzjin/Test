package com.danqiu.myapplication.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.config.Constants;
import com.danqiu.myapplication.fresco.ImageLoader;
import com.danqiu.myapplication.model.LoginModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/9/26.
 * 图片框架使用
 */

public class FrescoActivity extends BaseActivity {
    @BindView(R.id.drawee_img)
    SimpleDraweeView draweeImg;

    LoginModel loginModel;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.bt_head)
    Button btHead;
    @BindView(R.id.bt_heads)
    Button btHeads;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    private String apkPath = Constants.SOFT_FILE_PATH + Constants.HEAD_DOWN_NAME;

    @Override
    public void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fresco);
        ButterKnife.bind(this);

        String url = "http://www.people.com.cn/mediafile/pic/20161022/76/4315084153778263996.jpg";
        ImageLoader.loadImageBlur(draweeImg, url);

        //initDate();
    }

    private void initDate() {

        //showDialog();
        // movieModel=new MovieModelImpl(this,dialog);
        // movieModel.getMovies(5,10);
        loginModel = new LoginModel(this, dialog);

        HashMap<String, String> params = new HashMap<>();
//        params.put("account", "18482128607");
//        params.put("password", "12312");
//        loginModel.login(params);
        params.put("pageSize", "6");
        params.put("pageNo", "1");

        //loginModel.getEvaluationList("vRgLZ1h3us5qiwN+Vu/GReeiDoAEF40s", 1, 3);
        loginModel.downloadFiles("http://img-1253650823.cosgz.myqcloud.com/dqonline.apk",apkPath,Constants.APP_FILE_NAME_APK,progressBar);

    }









}
