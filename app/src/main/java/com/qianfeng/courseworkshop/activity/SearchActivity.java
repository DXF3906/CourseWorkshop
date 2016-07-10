package com.qianfeng.courseworkshop.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qianfeng.courseworkshop.R;
import com.qianfeng.courseworkshop.WebViewActivity;
import com.qianfeng.courseworkshop.asynctask.CommonAsyncTask;
import com.qianfeng.courseworkshop.asynctask.ImmageAsyncTask;
import com.qianfeng.courseworkshop.bean.Course;
import com.qianfeng.courseworkshop.bean.SearchCourse;
import com.qianfeng.courseworkshop.coursefragment.CourseFragment;
import com.qianfeng.courseworkshop.inner.GetFileNameCallBack;
import com.qianfeng.courseworkshop.util.JsoupAnalyzeHtml;
import com.qianfeng.courseworkshop.util.JsoupAnalyzeSearchCourseHtml;
import com.qianfeng.courseworkshop.util.JsoupAnalyzeSearchCourseHtmlImgUrl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 搜索课程界面
 * Created by asus on 2016/7/10.
 */

public class SearchActivity extends AppCompatActivity implements SearchView.SearchViewListener, GetFileNameCallBack {
    /**
     * 搜索结果列表view
     */
    private PullToRefreshListView lvResults;
    private String fileName = "SearchContent";

    /**
     * 搜索view
     */
    private SearchView searchView;


    /**
     * 热搜框列表adapter
     */
    private ArrayAdapter<String> hintAdapter;

    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter;

    /**
     * 搜索结果列表adapter
     */
    private SimpleAdapter resultAdapter;
    private LinkedList<Map<String, Object>> data = new LinkedList<>();//PullToRefreshListView的数据源
    private int pageInt = 1;

    /**
     * 数据库数据，总数据
     */
    private List<String> dbData;

    /**
     * 热搜版数据
     */
    private List<String> hintData;

    /**
     * 搜索过程中自动补全数据
     */
    private List<String> autoCompleteData;

    /**
     * 搜索结果的数据
     */
    private List<SearchCourse> resultData = new LinkedList<>();
    private List<SearchCourse> resultData2 = new LinkedList<>();//只放图片url

    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE = 4;

    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;

    /**
     * 设置提示框显示项的个数
     *
     * @param hintSize 提示框显示个数
     */
    public static void setHintSize(int hintSize) {
        SearchActivity.hintSize = hintSize;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        initData();
        initViews();
    }

    /**
     * 初始化视图
     */
    private void initViews() {
        Log.i("Search", "初始化控件执行了");
        lvResults = (PullToRefreshListView) findViewById(R.id.main_lv_search_results);
        lvResults.setVisibility(View.GONE);
        searchView = (SearchView) findViewById(R.id.main_search_layout);
        //设置监听
        searchView.setSearchViewListener(this);
        //设置adapter
        searchView.setTipsHintAdapter(hintAdapter);
        //searchView.setAutoCompleteAdapter(autoCompleteAdapter);

        aboutListView();


    }

    /**
     * 关于ListView的操作
     */
    private void aboutListView() {

        //适配器前的操作
        ListView actualListView = lvResults.getRefreshableView();
        registerForContextMenu(actualListView);

        data = new LinkedList<>();

        //准备适配器
        resultAdapter = new SimpleAdapter(this, data, R.layout.search_course_item, new String[]{
                "img", "title", "description", "tag1", "tag2", "tag3"}, new int[]{
                R.id.iv_search_course_title_id, R.id.tv_search_course_title_id, R.id.tv_search_descrition_id, R.id.tv_tag1_id, R.id.tv_tag2_id, R.id.tv_tag3_id});
        //图片的处理
        resultAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {

            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if (view instanceof ImageView) {
                    if (data instanceof Bitmap) {
                        //如果图片没有加载出来，用默认的图片代替
                        if (data != null) {
                            ImageView iv = (ImageView) view;
                            iv.setImageBitmap((Bitmap) data);
                            return true;
                        } else {
                            ImageView iv = (ImageView) view;
                            iv.setImageResource(R.mipmap.course_img);
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        // 绑定适配器

        lvResults.setAdapter(resultAdapter);
        //下拉刷新
        lvResults.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //显示当前系统的时间

                String label = DateUtils.formatDateTime(SearchActivity.this,
                        System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                //刷新当前的界面
                new GetDataTask().execute();
            }
        });

        //监听器跳转webView
        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent();
                intent.setClass(SearchActivity.this, WebViewActivity.class);
                String courUrl = "http://www.kgc.cn" + (String) data.get(i).get("url");
                intent.putExtra("100", courUrl);
                startActivity(intent);
            }
        });
    }

    /**
     * 顶部刷新
     */
    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            //刷新增加数据

            //fillDataSouce();

            // Call onRefreshComplete when the list has been refreshed.
            lvResults.onRefreshComplete();

            super.onPostExecute(result);
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //从数据库获取数据
        getDbData();
        //初始化热搜版数据
        getHintData();
        //初始化自动补全数据
        getAutoCompleteData(null);
    }

    /**
     * 获取db 数据
     */
    private void getDbData() {
        int size = 100;
        dbData = new ArrayList<>(size);

        dbData.add("listview");
        dbData.add("view");
        dbData.add("android");
        dbData.add("java");
        dbData.add("php");
        dbData.add("ios");
        dbData.add("直播");
        dbData.add("大数据");


    }

    /**
     * 获取热搜版data 和adapter
     */
    private void getHintData() {
        hintData = new ArrayList<>(hintSize);

        hintData.add("大数据");
        hintData.add("Java");
        hintData.add("直播");
        hintData.add("HTML5");
        hintData.add("职场");
        hintData.add("SEM");

        hintAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hintData);
    }

    /**
     * 获取自动补全data 和adapter(未用)
     */
    private void getAutoCompleteData(String text) {
        if (autoCompleteData == null) {
            //初始化
            autoCompleteData = new ArrayList<>(hintSize);
        } else {
            // 根据text 获取auto data
            autoCompleteData.clear();
            for (int i = 0, count = 0; i < dbData.size()
                    && count < hintSize; i++) {
                if (dbData.get(i).contains(text.trim())) {
                    autoCompleteData.add(dbData.get(i));
                    count++;
                }
            }
        }
        if (autoCompleteAdapter == null) {
            autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autoCompleteData);
        } else {
            autoCompleteAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String courseUrl) {

        //开启异步任务下载课程数据

        //courseUrlStr = "http://www.kgc.cn/list/230-1-6-9-9.shtml";
        CommonAsyncTask asyncTask = new CommonAsyncTask(this, fileName);
        asyncTask.execute(courseUrl);

        //填充数据源
        fillDataSouce();


    }

    //TODO
    private void fillDataSouce() {
        //顶部刷新时，将页码重置为1
        pageInt = 1;
        //填充数据源前必须清空
        data.clear();
        resultData.clear();
        resultData2.clear();

        //本地缓存文件实例获取
        File file = new File(Environment.getExternalStorageDirectory(), fileName + ".html");
        //json解析目录数据
        resultData = JsoupAnalyzeSearchCourseHtml.analysSearchCourses(file);
        resultData2 = JsoupAnalyzeSearchCourseHtmlImgUrl.analysSearchCourses(file);
        //如果courses里面有内容才填充数据，避免空指针异常
        if (resultData.size() > 0) {
            Log.i("tag", "有数据源可以填充");
            for (int i = 0; i < resultData.size(); i++) {
                SearchCourse course = resultData.get(i);
                SearchCourse course2 = resultData2.get(i+1);

                Map<String, Object> listem = new HashMap<String, Object>();
                String imgUrlStr = course2.getImgUrl();
                try {

                    ImmageAsyncTask asyncTask = new ImmageAsyncTask(this);
                    asyncTask.execute(imgUrlStr);
                    Bitmap bitmap = asyncTask.get();
                    listem.put("img", bitmap);
                    listem.put("title", course.getTitle());
                    listem.put("description", course.getDescription());
                    listem.put("tag1", course.getTag1());
                    listem.put("tag2", course.getTag2());
                    listem.put("tag3", course.getTag3());
                    listem.put("url", course.getCourseUrl());

                    data.add(listem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.i("tag", "更新courseContetn的适配器");

            resultAdapter.notifyDataSetChanged();
        }
    }


    /**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     *
     * @param text
     */
    @Override
    public void onRefreshAutoComplete(String text) {
        //更新数据
        getAutoCompleteData(text);
    }

    /**
     * 点击搜索键时edit text触发的回调
     */
    @Override
    public void onSearch(String text) {
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //TODO
        Log.i("Search", "onSearch方法执行了");
        lvResults.setVisibility(View.VISIBLE);
        //更新result数据:http://www.kgc.cn/search?type=course&keyword=view
        getResultData("http://www.kgc.cn/search?type=course&keyword=" + text);

    }

    @Override
    public void deleteAction() {
        lvResults.setVisibility(View.GONE);
    }

    /**
     * 搜索下载完成的接口回调
     *
     * @param fileName
     */
    @Override
    public void getFileName(String fileName) {

        fillDataSouce();


    }
}
