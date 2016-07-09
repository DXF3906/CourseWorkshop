package com.qianfeng.courseworkshop;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


/**
 * Created by Administrator on 2016/7/8.
 */

public class WebViewActivity extends AppCompatActivity {
    private WebView wv_main_id;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        Intent intent=getIntent();
        String url=intent.getStringExtra("100");
        wv_main_id = (WebView) findViewById(R.id.wv_main_id);

        // 设置WebView背景颜色
        wv_main_id.setBackgroundColor(Color.rgb(255, 255, 250));

        // 设置字符集
//        webView_main.getSettings().setDefaultTextEncodingName("utf-8");

        //设置WebView的一些缩放功能点
        wv_main_id.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        wv_main_id.setHorizontalScrollBarEnabled(false);


        //设置WebView可触摸放大缩小
        wv_main_id.getSettings().setSupportZoom(true);
        wv_main_id.getSettings().setBuiltInZoomControls(true);
        //WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小
        wv_main_id.getSettings().setUseWideViewPort(true);

        // 设置WebView初始化尺寸，参数为百分比
        wv_main_id.setInitialScale(100);

        //允许JS执行（html）
        wv_main_id.getSettings().setJavaScriptEnabled(true);
        wv_main_id.setWebChromeClient(new WebChromeClient());


        // 阻止网络图片加載
        wv_main_id.getSettings().setBlockNetworkImage(true);

        // 点击超链接后不弹出浏览器窗口，而在WebView控件中加载URL
        wv_main_id.setWebViewClient(new WebViewClient());
        wv_main_id.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                wv_main_id.getSettings().setBlockNetworkImage(false);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //屏幕密度不一样的情况下,自动适配页面
        /*int densityDpi = getResources().getDisplayMetrics().densityDpi;
        if (densityDpi == 240) {
            webView_main.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (densityDpi == 160) {
            webView_main.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else {
            webView_main.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }*/


        //file:///android_asset/img021.jpg ,直接访问资产目录assets下的资源
//        String content = "<body style='width:50%;'><script>//alert('不可以为空！');document.write('用户注册信息：');</script><a href='http://www.baidu.com' target='_blank'><img src='/img/bdlogo.gif'></a>" +
//                "<br/><img src='file:///android_asset/img021.jpg' width='50px' height='50px'><div style='color:red;font-size:30px;border:solid 1px blue;'>用户注册</div>" +
//                "<form method='post' action='http://www.qq.com'>用户名：<input type='text' name=''><br>密码：<input type='password' name=''><br><input type='submit' value='注册'></form></body>";
        // 用法1：不可以加载本地资源文件
        // webView_main.loadData(content, "text/html;charset=utf-8", null);

        // 用法2：
        //  webView_main.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
//        wv_main_id.loadDataWithBaseURL("http://www.baidu.com", content, "text/html", "utf-8", "file:///android_asset/aboutus.html");

        // 用法3：
        wv_main_id.loadUrl(url);

        // 用法4：
        // webView_main.loadUrl("file:///android_asset/aboutus.html");
    }

    protected void onDestroy() {
        super.onDestroy();
        wv_main_id.stopLoading();
        wv_main_id.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (wv_main_id.canGoBack()) {
                wv_main_id.goBack(); //goBack()表示返回WebView的上一页面
            } else {
                finish();
            }
        }
        return false;
    }


}
