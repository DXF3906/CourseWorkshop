package com.qianfeng.courseworkshop.shequfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qianfeng.courseworkshop.R;
import com.qianfeng.courseworkshop.filter.ExpandTabView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class TieziFragment extends Fragment {

    private FragmentActivity activity;
    private View view;
    private LinkedList<Map<String, Object>> data;
    private LinkedList<Map<String, Object>> data2;
    private Map<String, Object> map;
    private SimpleAdapter adapter;
    private SimpleAdapter adapter2;
    private ExpandTabView expandTabView;
    private ArrayList<String> nameList;//顶部tab条目列表
    private ListView lv_shequ_jingpin;
    private ListView lv_shequ_tiezi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.shequ_tiezi, null);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        lv_shequ_jingpin= (ListView) view.findViewById(R.id.lv_shequ_jingpin);
        lv_shequ_tiezi= (ListView) view.findViewById(R.id.lv_shequ_tiezi);

        data=new LinkedList<>();
        data2=new LinkedList<>();
        fillDataSouce(data);
        fillDataSouce2(data2);

        adapter = new SimpleAdapter(getActivity(), data, R.layout.shequ_tiezi_item1, new String[] {
                 "tv_shequ_jingpin" }, new int[] {
                 R.id.tv_shequ_jingpin });
        adapter2 = new SimpleAdapter(getActivity(), data2, R.layout.shequ_tiezi_item2, new String[] {
                 "sq_textView1","sq_textView2" }, new int[] {
                 R.id.sq_textView1 ,R.id.sq_textView2 });

        // 经典之处：将适配器设置到ListView中
        // setListAdapter(adapter);
        lv_shequ_jingpin.setAdapter(adapter);
        lv_shequ_tiezi.setAdapter(adapter2);
        super.onActivityCreated(savedInstanceState);
    }
    private void fillDataSouce(List<Map<String, Object>> data) {
        // TODO
        for (int i = 1; i <= 5; i++) {
            map = new LinkedHashMap<>();
            map.put("tv_shequ_jingpin",  i);

            data.add(map);
        }
    }
    private void fillDataSouce2(List<Map<String, Object>> data) {
        // TODO
        for (int i = 1; i <= 10; i++) {
            map = new LinkedHashMap<>();
            map.put("sq_textView1",  i);
            map.put("sq_textView2",  i);

            data2.add(map);
        }
    }





}
