package com.qianfeng.courseworkshop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qianfeng.courseworkshop.asynctask.CommonAsyncTask;
import com.qianfeng.courseworkshop.asynctask.ImmageAsyncTask;
import com.qianfeng.courseworkshop.bean.Course;
import com.qianfeng.courseworkshop.bean.Tiku;
import com.qianfeng.courseworkshop.coursefragment.CourseFragment;
import com.qianfeng.courseworkshop.filter.ExpandTabView;
import com.qianfeng.courseworkshop.filter.FilterTabView;
import com.qianfeng.courseworkshop.inner.GetFileNameCallBack;
import com.qianfeng.courseworkshop.util.JsoupAnalyzeHtml;
import com.qianfeng.courseworkshop.util.JsoupAnalyzeTikuHtml;
import com.qianfeng.courseworkshop.util.JsoupAnalyzeTikuHtmlTwo;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class TikuFragment extends Fragment implements ExpandTabView.OnFilterSelected, GetFileNameCallBack {

    private FragmentActivity activity;
    private View view;//Fragment对应的布局文件
    private PullToRefreshListView ptrlv_tiku_id;//带下拉和上拉功能的listView
    private Map<String, Object> map;
    private ExpandTabView expand_tabview_tiku_id;
    private ArrayList<String> nameList;//筛选标题集合
    private String fileName = "tikuContent";
    private String tikuUrlStr;
    private int pageInt = 1;


    private SimpleAdapter adapter;//适配器
    private LinkedList<Map<String, Object>> data;//PullToRefreshListView的数据源
    private List<Tiku> tikus = new LinkedList<>();//课程集合，用于填充数据源
    private List<Tiku> tikuTwos = new LinkedList<>();//课程集合，用于填充数据源

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        tikuUrlStr = "http://www.kgc.cn/questions/0-0-0-0-0-1";
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
        expand_tabview_tiku_id = (ExpandTabView) view.findViewById(R.id.expand_tabview_tiku_id);
        expand_tabview_tiku_id.setOnFilterSelected(this);
        //数据源
        nameList = new ArrayList<>();
        nameList.add("技能分类");
        nameList.add("最新试题");
        nameList.add("知名企业");
        nameList.add("试题难度");
        expand_tabview_tiku_id.setNameList(nameList);

        //PullToRefreshListView控件的获得
        ptrlv_tiku_id = (PullToRefreshListView) view.findViewById(R.id.ptrlv_tiku_id);
        //关于PullToRefreshListView的操作
        aboutPullToRefreshListView();

        super.onActivityCreated(savedInstanceState);
    }

    //筛选选择
    @Override
    public void onSelected(FilterTabView tabView, int position, boolean singleCheck) {
        if (singleCheck) {
            if (position == 0) {

                expand_tabview_tiku_id.setExpandView(getjinengView());
            }
            if (position == 1) {
                expand_tabview_tiku_id.setExpandView(getzuixinView());
            }
            if (position == 2) {
                expand_tabview_tiku_id.setExpandView(getzhimingView());
            }
            if (position == 3) {
                expand_tabview_tiku_id.setExpandView(getnanduView());
            }
        }
    }

    //筛选下拉框数据
    private ListView getjinengView() {

        final String[] courseArrayList = new String[]{"全部", "Oracle","java","css","html"};
        ListView listView = new ListView(getActivity());
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(courseArrayList)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                expand_tabview_tiku_id.getTabViews().get(0).setText(courseArrayList[i]);
                expand_tabview_tiku_id.closeExpand();
                //第一次出现"-"的索引
                int firstindex = tikuUrlStr.indexOf('-') + 1;
                //第二次出现"-"的索引
                String tikuString = tikuUrlStr.substring(firstindex);
                int index = tikuString.indexOf('-')  + firstindex;
                StringBuilder sb = new StringBuilder();
                sb = sb.append(tikuUrlStr);
                String paixuPage = new String();
                //
                switch (i){
                    case 0:
                        paixuPage="0";
                        break;
                    case 1:
                        paixuPage="4";
                        break;
                    case 2:
                        paixuPage="17";
                        break;
                    case 3:
                        paixuPage="59";
                        break;
                    case 4:
                        paixuPage="487";
                        break;
                    default:
                }

                sb.replace(firstindex, index , paixuPage);
                tikuUrlStr = sb.toString();
                //tikuUrlStr="http://www.kgc.cn/questions/0-59-0-0-0-1";
                Log.i("courseUrlStr", tikuUrlStr);
                aboutPullToRefreshListView();

            }
        });


        return listView;
    }

    //筛选下拉框数据
    private View getzuixinView() {
        ListView listView = new ListView(getActivity());
        final String[] zuixin = new String[]{"最新试题", "最热试题"};
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(zuixin)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                expand_tabview_tiku_id.getTabViews().get(1).setText(zuixin[i]);
                expand_tabview_tiku_id.closeExpand();
            }
        });
        return listView;
    }

    private View getzhimingView() {
        ListView listView = new ListView(getActivity());
        final String[] zhiming = new String[]{"全部", "百度", "腾讯","阿里巴巴","网易"};
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(zhiming)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                expand_tabview_tiku_id.getTabViews().get(2).setText(zhiming[i]);
                expand_tabview_tiku_id.closeExpand();

            }
        });
        return listView;
    }

    private View getnanduView() {
        ListView listView = new ListView(getActivity());
        final String[] nandu = new String[]{"全部", "一星", "二星","三星","四星","五星"};
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(nandu)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                expand_tabview_tiku_id.getTabViews().get(3).setText(nandu[i]);
                expand_tabview_tiku_id.closeExpand();
            }
        });
        return listView;
    }




    private void aboutPullToRefreshListView() {

        //开启异步任务下载目录数据

        //tikuUrlStr = "http://www.kgc.cn/questions/0-0-0-0-0-1";

        CommonAsyncTask asyncTask = new CommonAsyncTask(this, fileName);
        asyncTask.execute(tikuUrlStr);

        //适配器前的操作
        ListView actualListView = ptrlv_tiku_id.getRefreshableView();
        registerForContextMenu(actualListView);
        data = new LinkedList<>();
        // 填充数据源
        fillDataSouce();

        adapter = new SimpleAdapter(getActivity(), data, R.layout.tiku_item, new String[]{
                "choosetype", "description",  "default", "study", "pinglun"}, new int[]{
                R.id.tv_choosetype_id, R.id.tv_descrition_tiku_id, R.id.tv_default_tiku_id,
                R.id.tv_study_num_tiku_id, R.id.tv_pinlun_num_id});

        ptrlv_tiku_id.setAdapter(adapter);
        //下拉刷新
        ptrlv_tiku_id.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlv_tiku_id.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //显示当前系统的时间
                String label = DateUtils.formatDateTime(getActivity(),
                        System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                //刷新当前的界面
                new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //显示当前系统的时间
                String label = DateUtils.formatDateTime(getActivity(),
                        System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                //底部拉动加载新的数据
                new GetDownDataTask().execute();
            }
        });
        //监听器跳转webView
        ptrlv_tiku_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);

                String tikuUrl="http://www.kgc.cn"+tikus.get(i-1).getTikuUrl();
                intent.putExtra("100",tikuUrl);

                startActivity(intent);
            }
        });

    }

    /**
     * 填充数据源
     */
    private void fillDataSouce() {
        //顶部刷新时，将页码重置为1
        pageInt = 1;

        //填充数据源前必须清空
        data.clear();
        tikus.clear();
        tikuTwos.clear();

        //本地缓存文件实例获取
        File file = new File(Environment.getExternalStorageDirectory(), fileName + ".html");
        //json解析目录数据
        tikus = JsoupAnalyzeTikuHtml.analysTikus(file);
        tikuTwos= JsoupAnalyzeTikuHtmlTwo.analysTikus(file);
        //如果courses里面有内容才填充数据，避免空指针异常
        if (tikus.size() > 0 && tikuTwos.size()>0) {
            Log.i("tag", "有数据源可以填充");
            for (int i = 0; i < tikus.size(); i++) {
                Tiku tiku = tikus.get(i);
                Tiku tiku2=tikuTwos.get(i);

                Map<String, Object> listem = new HashMap<String, Object>();


                listem.put("choosetype", tiku.getChooseType());
                listem.put("description", tiku.getDescription());
                listem.put("jibei", tiku2.getJibie());
                listem.put("default", tiku2.getDefaultTi());
                listem.put("study", tiku2.getStudyNum());
                listem.put("pinglun", tiku2.getPinlunNum());
                listem.put("tkUrl",tiku.getTikuUrl());
                data.add(listem);

            }
            Log.i("tag", "更新courseContetn的适配器");
            adapter.notifyDataSetChanged();
        }

    }





    /**
     * 顶部刷新
     */
    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            //刷新增加数据

            fillDataSouce();

            // Call onRefreshComplete when the list has been refreshed.
            ptrlv_tiku_id.onRefreshComplete();

            super.onPostExecute(result);
        }
    }

    /**
     * 底部加载
     */
    private class GetDownDataTask extends AsyncTask<Void, Void, String[]> {

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
            //要加载页面的url
            String tikuDownUrlStr = tikuUrlStr;
            int index = tikuDownUrlStr.length() - 1;
            StringBuilder sb = new StringBuilder();
            sb = sb.append(tikuDownUrlStr);
            //页码加1加载下一页
            pageInt = pageInt + 1;
            sb.replace(index, index + 1, Integer.toString(pageInt));
            tikuDownUrlStr = sb.toString();

            //底部加载数据缓冲文件名
            String fileDownName = tikuDownUrlStr.substring(tikuDownUrlStr.lastIndexOf("/"));
            Log.i("tag", "开启异步任务加载底部的数据：文件名是：" + fileDownName);
            CommonAsyncTask asyncTask = new CommonAsyncTask(TikuFragment.this, fileDownName);
            asyncTask.execute(tikuDownUrlStr);

            //刷新增加数据
            fillDownDataSouce(fileDownName);

            // Call onRefreshComplete when the list has been refreshed.
            ptrlv_tiku_id.onRefreshComplete();

            super.onPostExecute(result);
        }
    }

    /**
     * 底部加载数据源
     */
    private void fillDownDataSouce(String fileDownName) {

        //填充数据源前必须清空
        //courses.clear();

        //本地缓存文件实例获取
        File file = new File(Environment.getExternalStorageDirectory(), fileDownName + ".html");
        //json解析目录数据
        List<Tiku> tikuTemp = new LinkedList<>();
        List<Tiku> tikuTemp2 = new LinkedList<>();
        tikuTemp = JsoupAnalyzeTikuHtml.analysTikus(file);
        tikuTemp2=JsoupAnalyzeTikuHtmlTwo.analysTikus(file);
        if (tikuTemp.size() > 0) {
            //判断是否还有更多数据，如果没有就不加载，避免加载重复的课程
            if (!(tikuTemp.get(0).getTikuUrl()).equals(tikus.get(0).getTikuUrl())) {
                tikus.clear();
                tikuTwos.clear();
                tikus.addAll(tikuTemp);
                tikuTwos.addAll(tikuTemp2);


                Log.i("tag", "①实现了底部加载数据源中解析功能---》" + fileDownName);
                Log.i("tag", "地步加载course集合内容" + tikus.toString());


                Log.i("tag", "实现了解析的数据的填充");
                for (int i = 0; i < tikus.size(); i++) {
                    Tiku tiku = tikus.get(i);
                    Tiku tiku2 = tikuTwos.get(i);

                    Map<String, Object> listem = new HashMap<String, Object>();


                    listem.put("choosetype", tiku.getChooseType());
                    listem.put("description", tiku.getDescription());
                    listem.put("jibei", tiku2.getJibie());
                    listem.put("default", tiku2.getDefaultTi());
                    listem.put("study", tiku2.getStudyNum());
                    listem.put("pinglun", tiku2.getPinlunNum());
                    listem.put("tkUrl",tiku.getTikuUrl());
                    data.add(listem);

                }
                Log.i("tag", "更新适配器");
                adapter.notifyDataSetChanged();
            }
        }

    }

    /**
     * 接口回调文件名
     * @param fileName
     */
    @Override
    public void getFileName(String fileName) {
        if ("tikuContent".equals(fileName)) {
            Log.i("tag", "回调了准备数据源数据的方法");
            fillDataSouce();
        } else {
            Log.i("tag", "回调了底部加载数据的方法" + fileName);
            fillDownDataSouce(fileName);
        }
    }

}
