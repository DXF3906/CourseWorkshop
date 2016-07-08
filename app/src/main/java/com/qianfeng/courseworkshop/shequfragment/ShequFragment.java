package com.qianfeng.courseworkshop.shequfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.support.v4.app.FragmentManager;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.qianfeng.courseworkshop.Fragment1;
import com.qianfeng.courseworkshop.R;
import com.qianfeng.courseworkshop.TikuFragment;
import com.qianfeng.courseworkshop.coursefragment.CourseFragment;
import com.qianfeng.courseworkshop.filter.ExpandTabView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ShequFragment extends Fragment {

    private FragmentActivity activity;
    private View view;
    private LinkedList<Map<String, Object>> data;
    private Map<String, Object> map;
    private SimpleAdapter adapter;
    private ExpandTabView expandTabView;
    private ArrayList<String> nameList;//顶部tab条目列表
    private FragmentManager supportFragmentManager;
    private RadioGroup rg_shequ_id;
    private WentiFragment wenti;
    private TieziFragment tiezi;
    private PullToRefreshScrollView ptrsv_shequ_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_shequ, null);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        initView();
        replaceContainerWidget(tiezi);
        rg_shequ_id.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(((RadioButton)radioGroup.getChildAt(0)).isChecked()){
                    replaceContainerWidget(tiezi);
                }
                if(((RadioButton)radioGroup.getChildAt(1)).isChecked()){
                    replaceContainerWidget(wenti);
                }
            }
        });

        super.onActivityCreated(savedInstanceState);
    }
    private void fillDataSouce(List<Map<String, Object>> data) {
        // TODO
        for (int i = 1; i <= 20; i++) {
            map = new LinkedHashMap<>();
            map.put("tk_textView5", i);
            map.put("tk_textView1",  i);
            map.put("tk_textView2", i);
            data.add(map);
        }
    }

    private void replaceContainerWidget(Fragment fragment) {

        FragmentTransaction transaction = supportFragmentManager.beginTransaction();

        //替换部分
        transaction.replace(R.id.ll_shequ_content, fragment);


        transaction.commit();


    }
    private void initView() {


        //RadioGroup控件
        rg_shequ_id = (RadioGroup)view. findViewById(R.id.rg_shequ_id);
        ptrsv_shequ_id = (PullToRefreshScrollView)view. findViewById(R.id.ptrsv_shequ_id);
        tiezi = new TieziFragment();
        wenti = new WentiFragment();
        supportFragmentManager =getActivity().getSupportFragmentManager();
    }


}
