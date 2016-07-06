package com.qianfeng.courseworkshop.coursefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.qianfeng.courseworkshop.R;

/**
 * 课程对应的侧滑界面
 * fdb
 * Created by asus on 2016/7/6.
 */

public class CourseLeftFragment extends Fragment {
    private FragmentActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_left_fragment, null);

        TextView tv_allcourse_id = (TextView) view.findViewById(R.id.tv_allcourse_id);//全部课程数
        ListView lv_program_id=(ListView)view.findViewById(R.id.lv_program_id);//研发.编程
        ListView lv_spread_id=(ListView)view.findViewById(R.id.lv_spread_id);//运营.推广
        ListView lv_seeing_id=(ListView)view.findViewById(R.id.lv_seeing_id);//视觉.创意
        ListView lv_net_id=(ListView)view.findViewById(R.id.lv_net_id);//网络.安全
        ListView lv_carrer_id=(ListView)view.findViewById(R.id.lv_carrer_id);//职场.心里
        ListView lv_exam_id=(ListView)view.findViewById(R.id.lv_exam_id);//考试.认证

        return view;
    }

}
