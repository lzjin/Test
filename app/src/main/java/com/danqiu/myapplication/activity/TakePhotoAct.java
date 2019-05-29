package com.danqiu.myapplication.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.config.Constants;
import com.danqiu.myapplication.fresco.ImageLoader;
import com.danqiu.myapplication.model.LoginModel;
import com.danqiu.myapplication.utils.File_Utils;
import com.danqiu.myapplication.utils.LogUtil;
import com.danqiu.myapplication.utils.MLog;
import com.danqiu.myapplication.views.ComDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/10/22.
 * 拍照框架  如果是集成BaseActivity话，无法再onCreate的super只写getTakePhoto...
 *           所以选择相册必须压缩
 */

public class TakePhotoAct extends BaseActivity implements InvokeListener, TakePhoto.TakeResultListener {

    /**
     * 头像弹框
     */
    TakePhoto takePhoto;
    InvokeParam invokeParam;
    Uri imageUri;
    @BindView(R.id.iv_head)
    SimpleDraweeView ivHead;
    @BindView(R.id.bt_choice)
    Button btChoice;
    @BindView(R.id.bt_take)
    Button btTake;
    @BindView(R.id.bt_photo)
    Button btPhoto;
    private File photoFile;
    private String fileName = "test", ok_fileName;
    private String filePath = Constants.SOFT_FILE_PATH + Constants.HEAD_IMA_PATH;
    private ComDialog dialogHead;//弹框选择
    LoginModel loginModel;

    @Override
    public void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_take);
        ButterKnife.bind(this);
        loginModel = new LoginModel(this, dialog);
        getTakePhoto().onCreate(savedInstanceState);
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
        ImageLoader.loadFile(ivHead,result.getImages().get(0).getCompressPath());
        MLog.e("test","---------成功路径-----"+result.getImages().get(0).getCompressPath());
        File_Utils.copyFile(result.getImages().get(0).getCompressPath(),photoFile.getAbsolutePath());
        //loginModel.upLoadImg("vRgLZ1h3us5qiwN+Vu/GReeiDoAEF40s",filePath+fileName+".png");
    }

    @Override
    public void takeFail(TResult result, String msg) {
        showToast("拍照失败,请重试!");
    }

    @Override
    public void takeCancel() {
        showToast("已取消拍照!");
    }
    /**
     *  相机权限处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }
    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }




    @OnClick({R.id.bt_choice, R.id.bt_take, R.id.bt_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_choice:
                showChoosePicDialog();
                break;
            case R.id.bt_take://拍照
                photoFile =  File_Utils.mkFile(filePath, fileName + ".png");
                imageUri = Uri.fromFile(photoFile);
                configTakePhoto(takePhoto);//压缩
                //takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());//剪切
                takePhoto.onPickFromCapture(imageUri);//不剪切
                MLog.e("test","---------拍照imageUri-----"+imageUri.getPath());

                break;
            case R.id.bt_photo://相册
                photoFile =  File_Utils.mkFile(filePath, fileName + ".png");
                imageUri = Uri.fromFile(photoFile);
                configTakePhoto(takePhoto);//压缩
                //takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());//剪切
                 takePhoto.onPickFromGallery();//不剪切
                //takePhoto.onPickMultiple(8);//多选

                LogUtil.e("test","---------相册imageUri-----"+imageUri.getPath());

                break;
        }
    }

    /**
     * 相机、相册========================================================
     */
    //选中方式
    private void showChoosePicDialog() {

        dialogHead=new ComDialog(this, R.style.photobgWindowStyle,R.layout.dialog_photo_choose);
        dialogHead.setAlertDialog()
                .setOutColse(true)
                .setWindowSize(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.5f)
                .setGravity(R.style.choose_photo_animstyle, Gravity.BOTTOM)
                .setDialogListener(new ComDialog.OnDialogListener() {
                    @Override
                    public void onViewClick(AlertDialog mDialog, View mianView) {
                        Button btnGallery = (Button) mianView.findViewById(R.id.gallery);
                        Button btnCamera = (Button) mianView.findViewById(R.id.camera);
                        Button btnCancel = (Button) mianView.findViewById(R.id.cancel);
                        btnGallery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {//相册
                                dialogHead.dismiss();
                                photoFile =  File_Utils.mkFile(filePath, fileName + ".png");
                                imageUri = Uri.fromFile(photoFile);
                                configTakePhoto(takePhoto);//压缩
                                //takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());//剪切
                                takePhoto.onPickFromGallery();//不剪切
                                LogUtil.e("test","---------相册imageUri-----"+imageUri.getPath());
                            }
                        });
                        btnCamera.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {//拍照
                                dialogHead.dismiss();
                                photoFile =  File_Utils.mkFile(filePath, fileName + ".png");
                                imageUri = Uri.fromFile(photoFile);
                                configTakePhoto(takePhoto);//压缩
                                //takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());//剪切
                                takePhoto.onPickFromCapture(imageUri);//不剪切
                                MLog.e("test","---------拍照imageUri-----"+imageUri.getPath());


                            }
                        });
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogHead.dismiss();//取消拍照弹框
                            }
                        });
                    }
                }).setListener();

    }

    /**
     * 是否剪切 004
     * @return
     */
    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        //宽高大小
        builder.setAspectX(200).setAspectY(200);
        //宽高比例
        //builder.setOutputX(1).setOutputY(1);
        builder.setWithOwnCrop(true);//是否剪切
        return builder.create();
    }

    /**
     *  相机 配置 003
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
                .setMaxSize(1024*50)
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
