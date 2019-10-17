package com.danqiu.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.danqiu.myapplication.MainActivity;
import com.danqiu.myapplication.R;
import com.danqiu.myapplication.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lzj on 2019/4/18
 * Describe ：注释
 */
public class WebViewAct extends AppCompatActivity {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.btn_js)
    Button btnJs;
    @BindView(R.id.btn_app)
    Button btnApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        initSet();
    }

    private void initSet() {

        webView.getSettings().setDomStorageEnabled(true);// 主要是这句
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        // 设置WebView属性，能够执行JavaScript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        // 为图片添加放大缩小功能
        webView.getSettings().setUseWideViewPort(true);

        webView.setInitialScale(70);   //100代表不缩放
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new InSideWebViewClient());

        webView.addJavascriptInterface(new AndroidtoJs(), "android");

        webView.loadUrl("http://10.0.255.228:5500/test.html");

    }

    /**
     *
     * @param view
     */
    @OnClick({R.id.btn_js, R.id.btn_app})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_js:
                appToh5();
                break;
            case R.id.btn_app:
                break;
        }
    }
    /**
     * app调用h5
     */
    private void appToh5() {
        webView.evaluateJavascript("window.app.hello()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String msg) {
                ToastUtil.showShort(WebViewAct.this, "h5消息为:" + msg);
            }
        });
    }

    /**
     * h5调用app
     * 方法一：js注入方法
     */
    public class AndroidtoJs extends Object {
        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void toTwoPage(String num) { // 这里采用的是原生的跳转方法
            Log.i("test","--toTwoPage-->"+num);
            Intent intent = new Intent(WebViewAct.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        @JavascriptInterface
        public void onSumResult(int result) { // 获取H5 的方法的返回值
            Log.i("test", "---result->" + result);
        }
        @JavascriptInterface
        public String hello() {
            String str = "你好";
            Log.i("test","-------"+str);
            ToastUtil.showShort(WebViewAct.this, "h5调用app消息为:" + str);
            //使用:window.android.hello();
            return str;
        }
    }



    /**
     * WebViewClient 内部设置
     */
    class InSideWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            if (url.contains("sdk://double")) {
//                String content = "test";
//                String testResult = "javascript:testResult('" + content + "')";
//                view.loadUrl(testResult);
            } else {
                view.loadUrl(url);
            }

            return true;
        }
    }

    /**
     * 返回
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
