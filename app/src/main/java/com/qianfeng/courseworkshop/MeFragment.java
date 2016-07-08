package com.qianfeng.courseworkshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.view.View;//不知道对不对


/**
 * Created by Administrator on 2016/7/8.
 */

public class MeFragment extends Fragment{
    private FragmentActivity activity;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState){
        activity=getActivity();
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view=inflater.inflate(R.layout.fragment_me,null);
        //缺东西----
        return view;
    }

    public void onActivityCreate(Bundle savedInstanceState){
        //点击登录跳转至登录页面

        //点击注册跳转到注册界面

        //点击任意一个RelativeLayout跳转到登录界面

        //点击齿轮跳转到设置界面

    }


}
