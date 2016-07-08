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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qianfeng.courseworkshop.filter.ExpandTabView;
import com.qianfeng.courseworkshop.filter.FilterTabView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TikuFragment extends Fragment implements ExpandTabView.OnFilterSelected {

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
         view = inflater.inflate(R.layout.fragment_tiku, null);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //筛选功能
        expandTabView = (ExpandTabView)view. findViewById(R.id.expand_tabview);
        expandTabView.setOnFilterSelected(this);
        //数据源
        nameList = new ArrayList<>();
        nameList.add("技能分类");
        nameList.add("最新试题");
        nameList.add("知名企业");
        nameList.add("试题难度");
        expandTabView.setNameList(nameList);
        ptrlv_tiku_id = (PullToRefreshListView) view
                .findViewById(R.id.ptrlv_tiku_id);
        //下拉刷新
        ptrlv_tiku_id.setMode(PullToRefreshBase.Mode.BOTH);
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
    //筛选下拉框数据
    private ListView getjinengView() {
        ListView listView = new ListView(getActivity());
        final String []jineng=new String[]{"不限", "男", "女"};
        final ArrayAdapter adapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(jineng));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                nameList.set(0,jineng[i]);
                expandTabView.getTabViews().get(0).setText(jineng[i]);
//                nameList.notifyAll();
//                expandTabView.setNameList(nameList);

            }
        });
        return listView;
    }
    //筛选下拉框数据
    private View getzuixinView() {
        ListView listView = new ListView(getActivity());
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(new String[]{"不限", "男", "女"})));
        return listView;
    }
    private View getzhimingView() {
        ListView listView = new ListView(getActivity());
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(new String[]{"不限", "男", "女"})));
        return listView;
    }
    private View getnanduView() {
        ListView listView = new ListView(getActivity());
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(new String[]{"不限", "男", "女"})));
        return listView;
    }
    //筛选选择
    @Override
    public void onSelected(FilterTabView tabView, int position, boolean singleCheck) {
        if (singleCheck) {
            if (position == 0) {

                expandTabView.setExpandView(getjinengView());
            }
            if (position == 1) {
                expandTabView.setExpandView(getzuixinView());
            }
            if (position == 2) {
                expandTabView.setExpandView(getzhimingView());
            }
            if (position == 3) {
                expandTabView.setExpandView(getnanduView());
            }
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
