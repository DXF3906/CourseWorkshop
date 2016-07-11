package com.qianfeng.courseworkshop.shequfragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.qianfeng.courseworkshop.Fragment1;
import com.qianfeng.courseworkshop.MainActivity;
import com.qianfeng.courseworkshop.R;
import com.qianfeng.courseworkshop.TikuFragment;
import com.qianfeng.courseworkshop.WebViewActivity;
import com.qianfeng.courseworkshop.asynctask.CommonAsyncTask;
import com.qianfeng.courseworkshop.asynctask.ImmageAsyncTask;
import com.qianfeng.courseworkshop.bean.Course;
import com.qianfeng.courseworkshop.bean.Tiezi;
import com.qianfeng.courseworkshop.coursefragment.CourseFragment;
import com.qianfeng.courseworkshop.filter.ExpandTabView;
import com.qianfeng.courseworkshop.inner.GetFileNameCallBack;
import com.qianfeng.courseworkshop.util.JsoupAnalyzeHtml;
import com.qianfeng.courseworkshop.util.JsoupAnalyzeShequHtml;
import com.qianfeng.courseworkshop.util.JsoupAnalyzeShequHtml2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ShequFragment extends Fragment implements GetFileNameCallBack {

    private FragmentActivity activity;
    private View view;
    private LinkedList<Map<String, Object>> data;
    private Map<String, Object> map;
    private SimpleAdapter adapter;
    private ExpandTabView expandTabView;
    private ArrayList<String> nameList;//顶部tab条目列表
    private RadioGroup rg_shequ_id;
    private RadioButton rb_tiezi_id;
    private RadioButton rb_wenti_id;

    private RadioGroup rg2_shequ_id;
    private PullToRefreshScrollView ptrsv_shequ_id;
    private Boolean isScoll;
    int[] location = new int[2];
    int[] location2 = new int[2];

    private ImageView iv_ui_id;//最上面ui控件
    private ListView lv_shequ_content;
    private String shequUrl;
    private String fileName = "shequContent";
    private int pageInt = 1;
    private List<Tiezi> tiezis = new LinkedList<>();
    private List<Tiezi> tiezis2 = new LinkedList<>();

    //水平滚动控件
    private ImageView iv_hsv_java_id;
    private ImageView iv_hsv_youjiang_id;
    private ImageView iv_hsv_guanshui_id;
    private ImageView iv_hsv_xinshou_id;
    private ImageView iv_hsv_txt_id;

    private ImageView iv_hsv_ui_id;
    private ImageView iv_hsv_android_id;
    private ImageView iv_hsv_soft_id;
    private ImageView iv_hsv_design_id;
    private ImageView iv_hsv_suggestion_id;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        shequUrl = "http://www.kgc.cn/bbs/list/959/0-7-1.shtml";
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shequ, null);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //初始化控件
        initView();
        //关于水平滚动控件的操作
        aboutHorizontalScroll();

        //图片ui添加监听器
        iv_ui_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(activity, WebViewActivity.class);
                String uiUrl = "http://www.kgc.cn/bbs/post/25748.shtml";
                intent.putExtra("100", uiUrl);

                startActivity(intent);

            }
        });

        //关于社区lv_shequ_content的操作
        aboutShequListView();

        //关于社区PullToRefreshScrollview的刷新和加载的操作
        aboutPullToRefreshScrollView();

        //设置不可见
        rg2_shequ_id.setVisibility(View.GONE);
        ((RadioButton) rg_shequ_id.getChildAt(0)).setChecked(true);
        //监听器
        rg_shequ_id.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (((RadioButton) radioGroup.getChildAt(0)).isChecked()) {
                    //TODO
                    rb_tiezi_id.setTextColor(Color.rgb(0,210,87));
                    rb_wenti_id.setTextColor(Color.BLACK);
                    shequUrl = "http://www.kgc.cn/bbs/list/959/0-7-1.shtml";
                    aboutShequListView();

                }
                if (((RadioButton) radioGroup.getChildAt(1)).isChecked()) {
                    rb_tiezi_id.setTextColor(Color.BLACK);
                    rb_wenti_id.setTextColor(Color.rgb(0,210,87));
                    shequUrl = "http://www.kgc.cn/bbs/list/959/1-7-1.shtml";
                    aboutShequListView();
                }

            }
        });
        rg2_shequ_id.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (((RadioButton) radioGroup.getChildAt(0)).isChecked()) {
                    shequUrl = "http://www.kgc.cn/bbs/list/959/0-7-1.shtml";
                    aboutShequListView();
                }
                if (((RadioButton) radioGroup.getChildAt(1)).isChecked()) {
                    shequUrl = "http://www.kgc.cn/bbs/list/959/1-7-1.shtml";
                    aboutShequListView();
                }
            }
        });

        //设置rg_shequ_id不可见时显示rg2_shequ_id。。未完成，有bug
//        ptrsv_shequ_id.setOnTouchListener(new View.OnTouchListener() {
//            private int lastY = 0;
//            private int touchEventId = -9983761;
//            Handler handler = new Handler() {
//                public void handleMessage(Message msg) {
//                    super.handleMessage(msg);
//                    if (msg.what == touchEventId) {
//                        if (lastY != ptrsv_shequ_id.getScrollY()) {
//                            //scrollview一直在滚动，会触发
//                            handler.sendMessageDelayed(
//                                    handler.obtainMessage(touchEventId, ptrsv_shequ_id), 5);
//                            lastY = ptrsv_shequ_id.getScrollY();
//                            rg_shequ_id.getLocationOnScreen(location);
//                            rg2_shequ_id.getLocationOnScreen(location2);
//                            //动的到静的位置时，静的显示。动的实际上还是网上滚动，但我们看到的是静止的那个
//                            Toast.makeText(getActivity(), "" + location[1] + "", Toast.LENGTH_SHORT).show();
//                            if (location[1] <= location2[1]) {
//                                rg2_shequ_id.setVisibility(View.VISIBLE);
//                            } else {
//                                //静止的隐藏了
//                                rg2_shequ_id.setVisibility(View.GONE);
//                            }
//                        }
//                    }
//                }
//            };
//
//            public boolean onTouch(View v, MotionEvent event) {
//                //必须两个都搞上，不然会有瑕疵
//                //没有这段，手指按住拖动的时候没有效果
//                if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                    handler.sendMessageDelayed(
//                            handler.obtainMessage(touchEventId, v), 5);
//                }
//                //没有这段，手指松开scroll继续滚动的时候，没有效果
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    handler.sendMessageDelayed(
//                            handler.obtainMessage(touchEventId, v), 5);
//                }
//                return false;
//            }
//
//        });

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 关于水平滚动控件的操作
     */
    private void aboutHorizontalScroll() {
        iv_hsv_java_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                String courUrl="http://www.kgc.cn/bbs/list/951/0-7-1.shtml";
                intent.putExtra("100",courUrl);
                startActivity(intent);
            }
        });
        iv_hsv_youjiang_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                String courUrl="http://www.kgc.cn/bbs/list/1029/0-7-1.shtml";
                intent.putExtra("100",courUrl);
                startActivity(intent);
            }
        });
        iv_hsv_guanshui_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                String courUrl="http://www.kgc.cn/bbs/list/1005/0-7-1.shtml";
                intent.putExtra("100",courUrl);
                startActivity(intent);
            }
        });
        iv_hsv_xinshou_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                String courUrl="http://www.kgc.cn/bbs/list/1011/0-7-1.shtml";
                intent.putExtra("100",courUrl);
                startActivity(intent);
            }
        });
        iv_hsv_txt_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                String courUrl="http://www.kgc.cn/bbs/list/1009/0-7-1.shtml";
                intent.putExtra("100",courUrl);
                startActivity(intent);
            }
        });
        iv_hsv_ui_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                String courUrl="http://www.kgc.cn/bbs/list/979/0-7-1.shtml";
                intent.putExtra("100",courUrl);
                startActivity(intent);
            }
        });
        iv_hsv_android_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                String courUrl="http://www.kgc.cn/bbs/list/959/0-7-1.shtml";
                intent.putExtra("100",courUrl);
                startActivity(intent);
            }
        });
        iv_hsv_soft_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                String courUrl="http://www.kgc.cn/bbs/list/1010/0-7-1.shtml";
                intent.putExtra("100",courUrl);
                startActivity(intent);
            }
        });
        iv_hsv_design_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                String courUrl="http://www.kgc.cn/bbs/list/1153/0-7-1.shtml";
                intent.putExtra("100",courUrl);
                startActivity(intent);
            }
        });
        iv_hsv_suggestion_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                String courUrl="http://www.kgc.cn/bbs/list/1008/0-7-1.shtml";
                intent.putExtra("100",courUrl);
                startActivity(intent);
            }
        });
    }



    /**
     * 关于社区PullToRefreshScrollview的刷新和加载的操作
     */
    private void aboutPullToRefreshScrollView() {
        ptrsv_shequ_id.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
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
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
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
    }

    /**
     * 初始化控件
     */
    private void initView() {
        //图片ui控件的获得
        iv_ui_id = (ImageView) view.findViewById(R.id.iv_ui_id);
        //水平滚动
        iv_hsv_java_id=(ImageView)view.findViewById(R.id.iv_hsv_java_id);
        iv_hsv_youjiang_id=(ImageView)view.findViewById(R.id.iv_hsv_youjiang_id);
        iv_hsv_guanshui_id=(ImageView)view.findViewById(R.id.iv_hsv_guanshui_id);
        iv_hsv_xinshou_id=(ImageView)view.findViewById(R.id.iv_hsv_xinshou_id);
        iv_hsv_txt_id=(ImageView)view.findViewById(R.id.iv_hsv_txt_id);

        iv_hsv_ui_id=(ImageView)view.findViewById(R.id.iv_hsv_ui_id);
        iv_hsv_android_id=(ImageView)view.findViewById(R.id.iv_hsv_android_id);
        iv_hsv_soft_id=(ImageView)view.findViewById(R.id.iv_hsv_soft_id);
        iv_hsv_design_id=(ImageView)view.findViewById(R.id.iv_hsv_design_id);
        iv_hsv_suggestion_id=(ImageView)view.findViewById(R.id.iv_hsv_suggestion_id);


        //RadioGroup控件
        rg_shequ_id = (RadioGroup) view.findViewById(R.id.rg_shequ_id);
        rb_tiezi_id=(RadioButton) view.findViewById(R.id.rb_tiezi_id);
        rb_wenti_id=(RadioButton) view.findViewById(R.id.rb_wenti_id);
        lv_shequ_content = (ListView) view.findViewById(R.id.lv_shequ_content);
        rg2_shequ_id = (RadioGroup) view.findViewById(R.id.rg2_shequ_id);
        ptrsv_shequ_id = (PullToRefreshScrollView) view.findViewById(R.id.ptrsv_shequ_id);

    }

    /**
     * 关于社区lv_shequ_content的操作
     */
    private void aboutShequListView() {
        //开启异步任务下载课程数据

        //courseUrlStr = "http://www.kgc.cn/list/230-1-6-9-9.shtml";
        CommonAsyncTask asyncTask = new CommonAsyncTask(this, fileName);
        asyncTask.execute(shequUrl);

        data = new LinkedList<>();

        // 填充数据源
        fillDataSouce();

        //准备适配器
        adapter = new SimpleAdapter(getActivity(), data, R.layout.shequ_tiezi_item2, new String[]{
                "img", "title", "time1", "time2", "study", "pinglun"}, new int[]{
                R.id.iv_shequ_touxiang_id, R.id.tv_shequ_title_id, R.id.tv_shequ_time1_id, R.id.tv_shequ_time2_id, R.id.tv_shequ_sutdy_num_id, R.id.tv_shequ_pinglun_num_id});
        //图片的处理
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {

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
        lv_shequ_content.setAdapter(adapter);
        setListViewHeight(lv_shequ_content);
        //监听器跳转webView
        lv_shequ_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                String courUrl="http://www.kgc.cn"+(String) data.get(i).get("url");
                intent.putExtra("100",courUrl);

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
        tiezis.clear();
        tiezis2.clear();

        //本地缓存文件实例获取
        File file = new File(Environment.getExternalStorageDirectory(), fileName + ".html");
        //json解析目录数据
        tiezis = JsoupAnalyzeShequHtml2.analysTiezis(file);
        tiezis2 = JsoupAnalyzeShequHtml.analysTiezis(file);
        //如果courses里面有内容才填充数据，避免空指针异常
        if (tiezis2.size() > 0) {
            Log.i("tag", "有数据源可以填充");
            for (int i = 0; i < tiezis2.size(); i++) {
                Tiezi tiezi = tiezis.get(i);
                Tiezi tiezi2 = tiezis2.get(i);

                Map<String, Object> listem = new HashMap<String, Object>();
                String imgUrlStr = tiezi2.getImgUrl();
                try {

                    ImmageAsyncTask asyncTask = new ImmageAsyncTask(activity);
                    asyncTask.execute(imgUrlStr);
                    Bitmap bitmap = asyncTask.get();
                    listem.put("img", bitmap);
                    listem.put("title", tiezi.getTitle());
                    listem.put("time1", tiezi.getTime1());
                    listem.put("time2", tiezi.getTime2());
                    listem.put("study", tiezi.getStudyNum());
                    listem.put("pinglun", tiezi.getPinglunNum());
                    listem.put("url", tiezi.getTieziUrl());

                    data.add(listem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.i("tag", "更新courseContetn的适配器");
            adapter.notifyDataSetChanged();
            setListViewHeight(lv_shequ_content);
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
            ptrsv_shequ_id.onRefreshComplete();

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
            String courseDownUrlStr = shequUrl;
            int index = courseDownUrlStr.lastIndexOf('.') - 1;
            StringBuilder sb = new StringBuilder();
            sb = sb.append(courseDownUrlStr);
            //页码加1加载下一页
            pageInt = pageInt + 1;
            sb.replace(index, index + 1, Integer.toString(pageInt));
            courseDownUrlStr = sb.toString();

            //底部加载数据缓冲文件名
            String fileDownName = courseDownUrlStr.substring(courseDownUrlStr.lastIndexOf("/") + 1);
            Log.i("tag", "开启异步任务加载底部的数据：文件名是：" + fileDownName);
            CommonAsyncTask asyncTask = new CommonAsyncTask(ShequFragment.this, fileDownName);
            asyncTask.execute(courseDownUrlStr);

            //刷新增加数据
            fillDownDataSouce(fileDownName);
            Log.i("tag", courseDownUrlStr);
            Log.i("tag", fileDownName);

            // Call onRefreshComplete when the list has been refreshed.
            ptrsv_shequ_id.onRefreshComplete();

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
        List<Tiezi> TiezisTemp = new LinkedList<>();
        List<Tiezi> TiezisTemp2 = new LinkedList<>();
        TiezisTemp2 = JsoupAnalyzeShequHtml.analysTiezis(file);
        TiezisTemp = JsoupAnalyzeShequHtml2.analysTiezis(file);
        if (TiezisTemp.size()>3) {
            TiezisTemp.remove(0);
            TiezisTemp.remove(0);
            TiezisTemp.remove(0);
            TiezisTemp.remove(0);

            TiezisTemp2.remove(0);
            TiezisTemp2.remove(0);
            TiezisTemp2.remove(0);
            TiezisTemp2.remove(0);
        }

        if (TiezisTemp.size() > 0) {
            //判断是否还有更多数据，如果没有就不加载，避免加载重复的课程
            if (!(TiezisTemp.get(0).getTitle()).equals(tiezis.get(0).getTitle())) {
                tiezis.clear();
                tiezis2.clear();
                tiezis.addAll(TiezisTemp);
                tiezis2.addAll(TiezisTemp2);




                Log.i("tag", "①实现了底部加载数据源中解析功能---》" + fileDownName);


                Log.i("tag", "实现了解析的数据的填充");
                for (int i = 0; i < tiezis.size(); i++) {
                    Tiezi tiezi = tiezis.get(i);
                    Tiezi tiezi2 = tiezis2.get(i);

                    Map<String, Object> listem = new HashMap<String, Object>();
                    String imgUrlStr = tiezi2.getImgUrl();

                    try {
                        ImmageAsyncTask asyncTask = new ImmageAsyncTask(activity);
                        asyncTask.execute(imgUrlStr);
                        Bitmap bitmap = asyncTask.get();
                        listem.put("img", bitmap);
                        listem.put("title", tiezi.getTitle());
                        listem.put("time1", tiezi.getTime1());
                        listem.put("time2", tiezi.getTime2());
                        listem.put("study", tiezi.getStudyNum());
                        listem.put("pinglun", tiezi.getPinglunNum());
                        data.add(listem);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Log.i("tag", "更新适配器");
                adapter.notifyDataSetChanged();
                setListViewHeight(lv_shequ_content);
            }
        }

    }


    /**
     * 设置控件的高度
     *
     * @param listView
     */
    public void setListViewHeight(ListView listView) {

        // 获取ListView对应的Adapter

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    /**
     * 下载完成接口回调
     * @param fileName
     */
    @Override
    public void getFileName(String fileName) {
        if ("shequContent".equals(fileName)) {
            Log.i("tag", "回调了准备数据源数据的方法");
            fillDataSouce();
        } else {
            Log.i("tag", "回调了底部加载数据的方法" + fileName);
            fillDownDataSouce(fileName);
        }
    }
}
