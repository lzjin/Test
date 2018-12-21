package com.danqiu.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.danqiu.myapplication.R;
import com.lzj.pass.dialog.PayPassDialog;
import com.lzj.pass.dialog.PayPassView;

/**
 * Created by Administrator on 2018/11/15.
 */

public class PayViewActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payview);

        payDialog();
        //payDialong2();
    }

    /**
     * 使用默认方式一
     */
    private void payDialog() {
     final PayPassDialog dialog=new PayPassDialog(this);
      dialog.getPayViewPass()
            .setPayClickListener(new PayPassView.OnPayClickListener() {
               @Override
               public void onPassFinish(String passContent) {
                   //6位输入完成,进行服务器验证
               }
               @Override
               public void onPayClose() {
                 dialog.dismiss();
                   //关闭
               }

               @Override
               public void onPayForget() {
                dialog.dismiss();
                  //忘记密码
               }
            });
    }

    /**
     * 使方式二
     * 自主配置
     */
    private void payDialong() {
        final PayPassDialog dialog=new PayPassDialog(this,R.style.dialogCommonTheme);
        dialog.setAlertDialog(false)
                .setWindowSize(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.4f)
                .setOutColse(false)
                .setGravity(R.style.commonAnimation, Gravity.BOTTOM);
        PayPassView payView=dialog.getPayViewPass();
        payView.setForgetText("忘记密码?");
        payView.setForgetColor(getResources().getColor(R.color.blue3bafd9));
        payView.setForgetSize(16);
        payView.setPayClickListener(new PayPassView.OnPayClickListener() {
            @Override
            public void onPassFinish(String passContent) {
                //6位输入完成,进行服务器验证
            }
            @Override
            public void onPayClose() {
                dialog.dismiss();
                //关闭
            }
            @Override
            public void onPayForget() {
                dialog.dismiss();
                //忘记密码
            }
        });
    }
}
