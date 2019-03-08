package com.danqiu.myapplication.activity;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.utils.MLog;
import com.lzj.xdialog.XComDialog;
import com.lzj.xdialog.XComPopupWindow;
import com.lzj.xdialog.XDialog;
import com.lzj.xdialog.XProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/19.
 * 封装的弹框使用
 */

public class DialogActivity extends AppCompatActivity {
    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.bt2)
    Button bt2;
    @BindView(R.id.bt3)
    Button bt3;
    @BindView(R.id.bt4)
    Button bt4;
    @BindView(R.id.bt5)
    TextView bt5;

    XProgressDialog x=null;
    XComDialog xx=null;
    XComPopupWindow popupWindow=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3,R.id.bt4,R.id.bt5})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.bt1:

                new XDialog(this)
                    .init("标题","这是内容")
                    .defaultShow(new XDialog.OnDialogListener() {
                    @Override
                    public void onCancel() {
                        MLog.i("test","----------------qu");
                    }

                    @Override
                    public void onSure() {
                        MLog.i("test","----------------ok");
                    }
                });

                break;
            case R.id.bt2:
                x=new XProgressDialog(this).onShow();
                break;
            case R.id.bt3:
                x=new XProgressDialog(this,"登录中",true).onShowN(true,false);
                break;
            case R.id.bt4://通用弹框
                dialogComm();
                break;
            case R.id.bt5://通用窗口
                showPopupWindow();
                break;

        }
    }

    /**
     * 弹框使用
     */
    private void dialogComm() {
        xx=new XComDialog(this,R.layout.view_xcom_dialog)
        .init()
        .setWindowSize()
        .setGravity(R.style.commonAnimation, Gravity.BOTTOM)
        .setDialogListener(new XComDialog.OnDialogListener() {
          @Override
          public void onViewClick(View mLayoutView, final AlertDialog mDialog) {
             Button button= mLayoutView.findViewById(R.id.bt_colse);
             button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                     mDialog.dismiss();
               }
             });
          }
        });



    }

    /**
     * PopupWindow 使用
     */
    private void showPopupWindow() {
       popupWindow = new XComPopupWindow(this, R.layout.view_xcom_dialog)
        .init()
         //.addAnimation(R.style.commonAnimation)
        .showAsDropDown(bt5)
        .setListener(new XComPopupWindow.OnPopupListener() {
          @Override
           public void onViewClick(View popupLayout) {
              Button button= popupLayout.findViewById(R.id.bt_colse);
              button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                       popupWindow.dismiss();
                }
               });
           }
         });
    }

}
