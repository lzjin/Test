package com.danqiu.myapplication;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.danqiu.myapplication.activity.BroadcastActivity;
import com.danqiu.myapplication.activity.ClickScreenActivity;
import com.danqiu.myapplication.activity.CustomViewActivity;
import com.danqiu.myapplication.activity.GreenDaoAct;
import com.danqiu.myapplication.activity.HandSlideAct;
import com.danqiu.myapplication.activity.LoaingActivity;
import com.danqiu.myapplication.activity.NotificationActivity;
import com.danqiu.myapplication.activity.Pager3DActivity;
import com.danqiu.myapplication.activity.PayViewActivity;
import com.danqiu.myapplication.activity.PreviewAct;
import com.danqiu.myapplication.activity.RefreshActivity;
import com.danqiu.myapplication.activity.ServiceActivity;
import com.danqiu.myapplication.activity.TakePhotoAct;
import com.danqiu.myapplication.activity.VedioPlayerActivity;
import com.danqiu.myapplication.fragment.LoginDailogFragment;
import com.danqiu.myapplication.utils.IntentUtil;
import com.danqiu.myapplication.utils.MLog;
import com.danqiu.myapplication.utils.ToastUtil;
import com.danqiu.myapplication.views.ComDialog;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    @BindView(R.id.bt_img)
    Button btImg;
    @BindView(R.id.bt_dialogfragment)
    Button dialogfragment;
    @BindView(R.id.bt_db)
    Button btDb;
    @BindView(R.id.bt_hand)
    Button btHand;
    @BindView(R.id.bt_video)
    Button btVideo;
    @BindView(R.id.bt_tab)
    Button btTab;
    @BindView(R.id.bt_take)
    Button btTake;

    @BindView(R.id.nice_spinner)
    NiceSpinner niceSpinner;

    @BindView(R.id.bt_refresh)
    Button bRefresh;
    @BindView(R.id.bt_custom)
    Button btCustom;
    @BindView(R.id.bt_pay)
    Button btPay;
    @BindView(R.id.bt_push)
    Button btPush;
    @BindView(R.id.bt_pager)
    Button btPager;
    @BindView(R.id.bt_loding)
    Button bt_loding;
    @BindView(R.id.bt_Notification)
    Button bt_Notification;
    @BindView(R.id.bt_service)
    Button btService;
    @BindView(R.id.bt_Broadcast)
    Button btBroadcast;



    ComDialog  payDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
       // requestPermission();

        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        niceSpinner.attachDataSource(dataset);
        niceSpinner.setTextColor(Color.BLACK);
        niceSpinner.setTextSize(13);


    }
    private void requestPermission(){
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                ,Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            ToastUtil.showShort(this,"已授权");

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "为了更好的用户体验需要获取以下权限", 1, perms);
        }
    }

    @OnClick({R.id.bt_Broadcast,R.id.bt_service,R.id.bt_Notification,R.id.bt_loding,R.id.bt_pager,R.id.bt_push,R.id.bt_pay,R.id.bt_custom,
               R.id.bt_refresh,R.id.bt_dialogfragment,R.id.bt_img, R.id.bt_db, R.id.bt_hand, R.id.bt_video, R.id.bt_tab,R.id.bt_take})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_Broadcast:
                IntentUtil.IntenToActivity(this, BroadcastActivity.class);
                break;
            case R.id.bt_service:
                IntentUtil.IntenToActivity(this, ServiceActivity.class);
                break;
            case R.id.bt_Notification:
                IntentUtil.IntenToActivity(this, NotificationActivity.class);
                break;
            case R.id.bt_loding:
                IntentUtil.IntenToActivity(this, LoaingActivity.class);
                break;
            case R.id.bt_pager:
                IntentUtil.IntenToActivity(this,Pager3DActivity.class);
                break;
            case R.id.bt_push:
                IntentUtil.IntenToActivity(this,ClickScreenActivity.class);
                break;
            case R.id.bt_pay:
                IntentUtil.IntenToActivity(this,PayViewActivity.class);

                break;
            case R.id.bt_custom:
                IntentUtil.IntenToActivity(this, CustomViewActivity.class);
                break;
            case R.id.bt_refresh:
                    IntentUtil.IntenToActivity(this,RefreshActivity.class);
                break;
            case R.id.bt_dialogfragment:
                LoginDailogFragment   fragment= new LoginDailogFragment();
                //fragment.setTargetFragment( this, 10);
                fragment.show(getSupportFragmentManager(), "login");

                break;
            case R.id.bt_tab:
                IntentUtil.IntenToActivity(this, TabLayoutAct.class);
                break;
            case R.id.bt_img:
                IntentUtil.IntenToActivity(this, PreviewAct.class);
                break;
            case R.id.bt_db:
                IntentUtil.IntenToActivity(this, GreenDaoAct.class);
                break;
            case R.id.bt_hand:
                IntentUtil.IntenToActivity(this, HandSlideAct.class);
                break;
            case R.id.bt_video:
                IntentUtil.IntenToActivity(this, VedioPlayerActivity.class);
                break;
            case R.id.bt_take:
                IntentUtil.IntenToActivity(this, TakePhotoAct.class);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MLog.i("test", "---------------结束");
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        ToastUtil.showShort(this,"授权成功");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        ToastUtil.showShort(this,"您已拒绝相关权限，可到设置里自行开启");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
