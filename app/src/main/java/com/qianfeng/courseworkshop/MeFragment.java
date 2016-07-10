package com.qianfeng.courseworkshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ImageView;


public class MeFragment extends Fragment {
    private View view;
    private FragmentActivity activity;
    private Button rb;
    private LinearLayout rt;
    private ImageView setting;
    private Button ln;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity=getActivity();
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    view=inflater.inflate(R.layout.fragment_me,null);
            return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //点击登录跳转至登录界面

        //rb=(Button)view.findViewById(R.id.btn_register_id);
        //登录
        rb=(Button)view.findViewById(R.id.btn_register_id);
        rt=(LinearLayout)view.findViewById(R.id.ll_rt_id);
        View.OnClickListener RegistListener=new MyOnClickListener();
        rb.setOnClickListener(RegistListener);
        rt.setOnClickListener(RegistListener);
        //注册
        ln=(Button)view.findViewById(R.id.btn_login_id);
        View.OnClickListener LoginListener=new MyOnLoginListener();
        ln.setOnClickListener(LoginListener);
        //点击小齿轮跳转至设置界面
        setting=(ImageView)view.findViewById(R.id.setting_id);
        View.OnClickListener SettingListener=new MySettingListener();
        setting.setOnClickListener(SettingListener);

        super.onActivityCreated(savedInstanceState);
    }
    //点击注册登录实现跳转
    //public  void  toRegist(View view){
    //    startActivity(new Intent(getActivity(),registerActivity.class));
    //}
    //public void toLogin(View view){
    //    startActivity(new Intent(getActivity(), loginActivity.class));
    //}

    private class MyOnClickListener implements View.OnClickListener {
        public void onClick(View view){
            startActivity(new Intent(getActivity(),registerActivity.class));
        }
    }

    private class MySettingListener implements View.OnClickListener {
        public void onClick(View view){
            startActivity(new Intent(getActivity(),settingActivity.class));
        }
    }

    private class MyOnLoginListener implements View.OnClickListener {
        public void onClick(View view){
            startActivity(new Intent(getActivity(),loginActivity.class));
        }
    }
}
