package com.qianfeng.courseworkshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/7/6.
 */

public class settingActivity extends AppCompatActivity{
        private ImageView back;
        private LinearLayout all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        back=(ImageView)findViewById(R.id.iv_back_id);
        all=(LinearLayout)findViewById(R.id.all_id);
        LinearLayout.OnClickListener setlistener=new MySetListener();
        all.setOnClickListener(setlistener);
        ImageView.OnClickListener bklistener=new MyBkListener();
        back.setOnClickListener(bklistener);
    }


    private class MyBkListener implements ImageView.OnClickListener {
        public void onClick(View v){
            finish();
        }
    }

    private class MySetListener implements LinearLayout.OnClickListener {
        public void onClick(View v){
            Toast.makeText(settingActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
        }
    }
}