package com.danqiu.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.danqiu.myapplication.config.Constants;
import com.danqiu.myapplication.fresco.ImageLoader;
import com.danqiu.myapplication.utils.File_Utils;
import com.danqiu.myapplication.utils.LogUtil;
import com.danqiu.myapplication.utils.MLog;
import com.danqiu.myapplication.utils.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/9/18.
 */

public class TabLAyoutFrg extends Fragment implements InvokeListener, TakePhoto.TakeResultListener {
    private String mtitle;


    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.bt_add)
    Button bt;
    @BindView(R.id.iv)
    SimpleDraweeView iv;

    /**
     * 头像弹框
     */
    TakePhoto takePhoto;
    InvokeParam invokeParam;
    Uri imageUri;

    private File photoFile;
    private String fileName = "test", ok_fileName;
    private String filePath = Constants.SOFT_FILE_PATH + Constants.HEAD_IMA_PATH;

    Unbinder unbinder;
    private View view;


    public static TabLAyoutFrg getFragmentA(String title) {
        TabLAyoutFrg f = new TabLAyoutFrg();
        Bundle bdl = new Bundle();
        bdl.putString("title", title);
        f.setArguments(bdl);


        return f;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab, container, false);
        unbinder = ButterKnife.bind(this, view);
        mtitle = getArguments().getString("title");
        getTakePhoto().onCreate(savedInstanceState);

        LogUtil.i("TabLAyoutFrg", "------------新创建--" + mtitle);
        // 注册订阅者
        EventBus.getDefault().register(this);
        initData();

        return view;
    }

    @OnClick(R.id.bt_add)
    public void onViewClicked() {

        photoFile = File_Utils.mkFile(filePath, fileName + ".png");
        imageUri = Uri.fromFile(photoFile);
        configTakePhoto(takePhoto);//压缩
        //takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());//剪切
        takePhoto.onPickFromCapture(imageUri);//不剪切
        MLog.e("test", "---------拍照imageUri-----" + imageUri.getPath());

    }

    /**
     * 初始化数据
     */
    private void initData() {
        // tv.setText(mtitle);



    }

    //订阅方法，当接收到事件的时候，会调用该方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String messageEvent) {
        tv.setText(messageEvent);
        LogUtil.i("TabLAyoutFrg", "------------接收2--" + messageEvent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 得到实列 001
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        ImageLoader.loadFile(iv, result.getImages().get(0).getCompressPath());
        MLog.e("test", "---------成功路径-----" + result.getImages().get(0).getCompressPath());
        File_Utils.copyFile(result.getImages().get(0).getCompressPath(), photoFile.getAbsolutePath());
        //loginModel.upLoadImg("vRgLZ1h3us5qiwN+Vu/GReeiDoAEF40s",filePath+fileName+".png");
    }

    @Override
    public void takeFail(TResult result, String msg) {
        ToastUtil.showShort(getActivity(), "拍照失败,请重试!");
    }

    @Override
    public void takeCancel() {
        ToastUtil.showShort(getActivity(), "已取消拍照!");
    }

    /**
     * 相机权限处理
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 相机 配置 003
     *
     * @param takePhoto
     */
    private void configTakePhoto(TakePhoto takePhoto) {
        //相册
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        //是否 使用自带相册
        builder.setWithOwnGallery(true);
        //是否 纠正拍照的照片旋转角度
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());
        // takePhoto.onPickFromGallery();//从相册中获取图片（不裁剪）
        //takePhoto.onPickFromCapture(Uri outPutUri);//相机获取图片(不裁剪)
        //压缩工具：自带
        CompressConfig config = new CompressConfig.Builder()
                .setMaxSize(1024 * 50)
                .setMaxPixel(800)
                .enableReserveRaw(false) //是否保存压缩前原图
                .create();
//            //压缩工具Luban 2
//            LubanOptions option = new LubanOptions.Builder().setMaxHeight(height).setMaxWidth(width).setMaxSize(maxSize).create();
//            config = CompressConfig.ofLuban(option);
//            config.enableReserveRaw(enableRawFile);

        takePhoto.onEnableCompress(config, false);//参数一压缩配置  参数二  是否显示压缩进度
    }


}
