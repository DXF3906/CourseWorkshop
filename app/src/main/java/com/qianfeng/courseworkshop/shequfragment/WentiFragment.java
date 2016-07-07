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


public class WentiFragment extends Fragment {

    private FragmentActivity activity;
    private View view;
    private PullToRefreshListView ptrlv_tiku_id;
    private LinkedList<Map<String, Object>> data;
    private Map<String, Object> map;
    private SimpleAdapter adapter;
    private ExpandTabView expandTabView;
    private ArrayList<String> nameList;//顶部tab条目列表
    private ListView lv_shequ_wenti;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shequ_wenti, null);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        lv_shequ_wenti = (ListView) view.findViewById(R.id.lv_shequ_wenti);
        data = new LinkedList<>();
        fillDataSouce(data);

        adapter = new SimpleAdapter(getActivity(), data, R.layout.shequ_tiezi_item2, new String[]{
                "sq_textView1", "sq_textView2"}, new int[]{
                R.id.sq_textView1, R.id.sq_textView2});

        // 经典之处：将适配器设置到ListView中
        // setListAdapter(adapter);
        lv_shequ_wenti.setAdapter(adapter);
        super.onActivityCreated(savedInstanceState);
    }

    private void fillDataSouce(List<Map<String, Object>> data) {
        // TODO

        for (int i = 1; i <= 20; i++) {
            map = new LinkedHashMap<>();
            map.put("sq_textView1", i);
            map.put("sq_textView2", i);

            data.add(map);

        }
    }


}
