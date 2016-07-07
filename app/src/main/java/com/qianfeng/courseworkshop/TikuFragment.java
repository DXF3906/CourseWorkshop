package com.qianfeng.courseworkshop;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TikuFragment extends Fragment {

    private FragmentActivity activity;
    private View view;
    private PullToRefreshListView ptrlv_tiku_id;
    private LinkedList<Map<String, Object>> data;
    private Map<String, Object> map;
    private SimpleAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_tiku, null);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        ptrlv_tiku_id = (PullToRefreshListView) view
                .findViewById(R.id.ptrlv_tiku_id);
        ptrlv_tiku_id.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(),
                        System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                // Do work to refresh the list here.
                new GetDataTask().execute();
            }
        });
        ptrlv_tiku_id
                .setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

                    @Override
                    public void onLastItemVisible() {
                        // Toast.makeText(PullToRefreshListActivity.this,
                        // "End of List!", Toast.LENGTH_SHORT).show();
                    }
                });

        ListView actualListView = ptrlv_tiku_id.getRefreshableView();
        registerForContextMenu(actualListView);
        data = new LinkedList<>();
        // 填充数据源
        fillDataSouce(data);

        adapter = new SimpleAdapter(getActivity(), data, R.layout.tiku_item, new String[] {
                "tk_textView5", "tk_textView1", "tk_textView2" }, new int[] {
                R.id.tk_textView5, R.id.tk_textView1, R.id.tk_textView2 });

        // 经典之处：将适配器设置到ListView中
        // setListAdapter(adapter);
        ptrlv_tiku_id.setAdapter(adapter);
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
    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            // mListItems.addFirst("Added after refresh...");
            //刷新增加数据
            data.add(map);
            fillDataSouce(data);
            adapter.notifyDataSetChanged();

            // Call onRefreshComplete when the list has been refreshed.
            ptrlv_tiku_id.onRefreshComplete();

            super.onPostExecute(result);
        }
    }
}
