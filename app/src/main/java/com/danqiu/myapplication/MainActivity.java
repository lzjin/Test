package com.danqiu.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.danqiu.myapplication.activity.AnnotationAct;
import com.danqiu.myapplication.activity.BaseRecyleViewAct;
import com.danqiu.myapplication.activity.BroadcastActivity;
import com.danqiu.myapplication.activity.ClickScreenActivity;
import com.danqiu.myapplication.activity.CustomViewActivity;
import com.danqiu.myapplication.activity.DataBindingAct;
import com.danqiu.myapplication.activity.DialogActivity;
import com.danqiu.myapplication.activity.EventBusActivity;
import com.danqiu.myapplication.activity.ExceleActivity;
import com.danqiu.myapplication.activity.FrescoActivity;
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
import com.danqiu.myapplication.activity.TestActivity;
import com.danqiu.myapplication.activity.VedioPlayerActivity;
import com.danqiu.myapplication.bean.MessageEvent;
import com.danqiu.myapplication.fragment.LoginDailogFragment;
import com.danqiu.myapplication.mp3.Mp3Activity;
import com.danqiu.myapplication.utils.IntentUtil;
import com.danqiu.myapplication.utils.MLog;

import org.angmarch.views.NiceSpinner;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.ll)
    LinearLayout ll;
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
    @BindView(R.id.bt_fresco)
    Button btFresco;
    @BindView(R.id.bt_event)
    Button bt_event;
    @BindView(R.id.bt_event2)
    Button bt_event2;
    @BindView(R.id.bt_update)
    Button bt_update;
    @BindView(R.id.bt_dialog)
    Button bt_dialog;
    @BindView(R.id.bt_cu)
    Button bt_cu;
    @BindView(R.id.bt_mp)
    Button bt_mp;
    @BindView(R.id.bt_recycle)
    Button bt_recycle;
    @BindView(R.id.bt_excele)
    Button bt_excele;
    @BindView(R.id.bt_annotation)
    Button bt_annotation;

    private ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);


        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        niceSpinner.attachDataSource(dataset);
        niceSpinner.setTextColor(Color.BLACK);
        niceSpinner.setTextSize(13);


    }

    //订阅方法，当接收到事件的时候，会调用该方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        MLog.i("test", "------------------" + messageEvent.getName());
        bt_event2.setText("接收=" + messageEvent.getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 事件
     *
     * @param view
     */
    @OnClick({R.id.bt_mp,R.id.bt_cu,R.id.bt_dialog,R.id.bt_update, R.id.bt_event,
            R.id.bt_fresco, R.id.bt_Broadcast, R.id.bt_service, R.id.bt_Notification,
            R.id.bt_loding, R.id.bt_pager, R.id.bt_push, R.id.bt_pay, R.id.bt_custom,
            R.id.bt_refresh, R.id.bt_dialogfragment, R.id.bt_img, R.id.bt_db,R.id.bt_recycle,
            R.id.bt_hand, R.id.bt_video, R.id.bt_tab, R.id.bt_take,R.id.bt_excele,R.id.bt_annotation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_annotation:
                IntentUtil.IntenToActivity(this, AnnotationAct.class);
                break;
            case R.id.bt_excele:
                IntentUtil.IntenToActivity(this, ExceleActivity.class);
                break;
            case R.id.bt_recycle:
                IntentUtil.IntenToActivity(this, BaseRecyleViewAct.class);
                break;
            case R.id.bt_mp:
                IntentUtil.IntenToActivity(this, Mp3Activity.class);
                break;
            case R.id.bt_cu:
                IntentUtil.IntenToActivity(this, TestActivity.class);
                break;
            case R.id.bt_dialog:
                IntentUtil.IntenToActivity(this, DialogActivity.class);
                break;
            case R.id.bt_update:
                IntentUtil.IntenToActivity(this, DataBindingAct.class);
                break;
            case R.id.bt_event:
                IntentUtil.IntenToActivity(this, EventBusActivity.class);
                break;
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
                IntentUtil.IntenToActivity(this, Pager3DActivity.class);
                break;
            case R.id.bt_push:
                IntentUtil.IntenToActivity(this, ClickScreenActivity.class);
                break;
            case R.id.bt_pay:
                IntentUtil.IntenToActivity(this, PayViewActivity.class);

                break;
            case R.id.bt_custom:
                IntentUtil.IntenToActivity(this, CustomViewActivity.class);
                break;
            case R.id.bt_refresh:
                IntentUtil.IntenToActivity(this, RefreshActivity.class);
                break;
            case R.id.bt_dialogfragment:
                LoginDailogFragment fragment = new LoginDailogFragment();
                //fragment.setTargetFragment( this, 10);
                fragment.show(getSupportFragmentManager(), "login");
                break;
            case R.id.bt_tab:
                IntentUtil.IntenToActivity(this, TabLayoutAct.class);
                break;
            case R.id.bt_img:
                IntentUtil.IntenToActivity(this, PreviewAct.class);
                break;
            case R.id.bt_fresco:
                IntentUtil.IntenToActivity(this, FrescoActivity.class);
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


}
