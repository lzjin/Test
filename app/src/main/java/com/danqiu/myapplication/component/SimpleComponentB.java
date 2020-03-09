package com.danqiu.myapplication.component;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.binioter.guideview.Component;
import com.danqiu.myapplication.R;
import com.danqiu.myapplication.utils.MLog;


/**
 * Created by binIoter on 16/6/17.
 * activity新手引导：高亮区域 布局
 */
public class SimpleComponentB implements Component {

  @Override public View getView(LayoutInflater inflater) {

    LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.layer_frends_b, null);
    ll.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        MLog.i("test","---------引导2");
        Toast.makeText(view.getContext(), "引导层被点击了2", Toast.LENGTH_SHORT).show();
      }
    });
    return ll;
  }

  @Override public int getAnchor() {
    //位置下方
    return Component.ANCHOR_BOTTOM;
  }

  @Override public int getFitPosition() {
    //下方居中或左边或右边
    return Component.FIT_CENTER;
  }

  @Override public int getXOffset() {
    return 0;
  }

  @Override public int getYOffset() {
    return 10;
  }
}
