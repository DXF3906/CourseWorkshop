package com.qianfeng.courseworkshop.welcome;

import android.os.SystemClock;


        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.os.SystemClock;
        import android.support.v7.app.AppCompatActivity;

        import com.qianfeng.courseworkshop.MainActivity;
        import com.qianfeng.courseworkshop.R;


/**
 * Created by Administrator on 2016/7/6.
 */

public class InitActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        //思路：
        SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
        if (config.getBoolean("isFirstStart", true)) {//约定：isFirstStart键中存储的值，true--》是第一次启动；false--》不是
            //①第一次启动应用，跳到初始界面，然后跳到欢迎界面，最后到主界面


            //将该应用的使用 通过SharedPreferences固化起来
            config.edit().putBoolean("isFirstStart",false ).commit();

            //开启子线程，睡眠片刻，尔后跳转到欢迎界面
            new Thread() {
                @Override
                public void run() {
                    SystemClock.sleep(1000);
                    InitActivity.this.startActivity(new Intent(InitActivity.this, WelcomeActivity.class));
                    finish();
                }
            }.start();
        } else {
            //②以后，启动应用，直接跳转到主界面
            new Thread() {
                @Override
                public void run() {
                    SystemClock.sleep(1000);
                    startActivity(new Intent(InitActivity.this, MainActivity.class));
                    finish();
                }
            }.start();

        }
    }
}