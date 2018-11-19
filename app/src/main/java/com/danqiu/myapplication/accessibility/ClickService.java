package com.danqiu.myapplication.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

import com.danqiu.myapplication.utils.MLog;

/**
 * Created by Administrator on 2018/11/16.
 * 点击 辅助服务
 * 提供的查找节点与模拟点击相关的接口即可实现权限节点的查找与点击。
 */

public class ClickService extends AccessibilityService {

    /**
     *  此方法用了接受系统发来的event。在你注册的event发生是被调用。在整个生命周期会被调用多次。
     *  具体的功能实现需要在这里
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // 此方法是在主线程中回调过来的，所以消息是阻塞执行的
        // 获取包名
        String pkgName = event.getPackageName().toString();
        int eventType = event.getEventType();
        // AccessibilityOperator封装了辅助功能的界面查找与模拟点击事件等操作
        AccessibilityOperator.getInstance().updateEvent(this, event);
        MLog.i("test","eventType: " + eventType + " pkgName: " + pkgName);
        switch (eventType) {
            //通知栏
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                break;
        }

//        //点击操作
//        root.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//
//        //滑动操作
//        root.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);

    }

    /**
     *  必须重写的方法：系统要中断此service返回的响应时会调用。在整个生命周期会被调用多次。
     */
    @Override
    public void onInterrupt() {

    }

    /**
     *  当系统连接上你的服务时被调用
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }


    /**
     *  在系统要关闭此service时调用。
     */
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

}
