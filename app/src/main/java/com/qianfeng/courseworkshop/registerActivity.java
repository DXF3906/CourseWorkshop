package com.qianfeng.courseworkshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;


/**
 * Created by Administrator on 2016/7/6.
 */

public class registerActivity extends AppCompatActivity{
        private TextView lg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        lg=(TextView)findViewById(R.id.textView6);
        TextView.OnClickListener Lglistener=new MyLgListener();
        lg.setOnClickListener(Lglistener);

        super.onCreate(savedInstanceState);
    }

    private class MyLgListener implements TextView.OnClickListener {
        public void onClick(View view){
            startActivity(new Intent(registerActivity.this,loginActivity.class));
        }
    }
}