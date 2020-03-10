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
public class SimpleComponent implements Component {

  private String title;

  private int img;

  public SimpleComponent(String title, int img) {
    this.title = title;
    this.img = img;
  }

  public SimpleComponent() {
  }

  @Override public View getView(LayoutInflater inflater) {

    LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.layer_frends, null);
    ll.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        MLog.i("test","---------引导1");
        Toast.makeText(view.getContext(), "引导层被点击了1", Toast.LENGTH_SHORT).show();
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
