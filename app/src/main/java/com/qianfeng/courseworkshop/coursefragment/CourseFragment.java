package com.qianfeng.courseworkshop.coursefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qianfeng.courseworkshop.R;
/**
 * 课程对应的Fragmnet
 * fdb
 * Created by asus on 2016/7/6.
 */

public class CourseFragment extends Fragment {
    private FragmentActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, null);


        return view;
    }
}
