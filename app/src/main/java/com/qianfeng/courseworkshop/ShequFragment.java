package com.qianfeng.courseworkshop;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qianfeng.courseworkshop.filter.ExpandTabView;
import com.qianfeng.courseworkshop.filter.FilterTabView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ShequFragment extends Fragment {

    private FragmentActivity activity;
    private View view;
    private PullToRefreshListView ptrlv_tiku_id;
    private LinkedList<Map<String, Object>> data;
    private Map<String, Object> map;
    private SimpleAdapter adapter;
    private ExpandTabView expandTabView;
    private ArrayList<String> nameList;//顶部tab条目列表
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





}
