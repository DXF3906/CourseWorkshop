package com.qianfeng.courseworkshop.coursefragment;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qianfeng.courseworkshop.MainActivity;
import com.qianfeng.courseworkshop.R;
import com.qianfeng.courseworkshop.WebViewActivity;
import com.qianfeng.courseworkshop.asynctask.CommonAsyncTask;
import com.qianfeng.courseworkshop.asynctask.ImmageAsyncTask;
import com.qianfeng.courseworkshop.bean.CommonData;
import com.qianfeng.courseworkshop.bean.Course;
import com.qianfeng.courseworkshop.filter.ExpandTabView;
import com.qianfeng.courseworkshop.filter.FilterTabView;
import com.qianfeng.courseworkshop.inner.GetFileNameCallBack;
import com.qianfeng.courseworkshop.util.JsoupAnalyzeHtml;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 课程对应的Fragmnet
 * fdb
 * Created by asus on 2016/7/6.
 */

public class CourseFragment extends Fragment implements ExpandTabView.OnFilterSelected, GetFileNameCallBack {
    private FragmentActivity activity;
    private View view;//Fragment对应的布局文件

    private ExpandTabView expand_tabview_course_id;//筛选控件实例
    private ArrayList<String> nameList;//筛选标题集合

    private PullToRefreshListView ptrlv_course_id;//带下拉和上拉功能的listView
    private SimpleAdapter adapter;//适配器
    private LinkedList<Map<String, Object>> data;//PullToRefreshListView的数据源
    private List<Course> courses = new LinkedList<>();//课程集合，用于填充数据源

    private String fileName = "courseContent";//初始显示的文件名
    private String courseUrlStr;//初始的网址
    private int pageInt = 1;//底部加载的页码数，默认为1

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        MainActivity activity1 = (MainActivity) getActivity();

        courseUrlStr = activity1.getCourseUrl();
        if (courseUrlStr == null) {
            courseUrlStr = CommonData.allCourse;
        }

        super.onCreate(savedInstanceState);
    }

    /**
     * 获得fragment对应的布局文件
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_course, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        //筛选功能控件的获得
        expand_tabview_course_id = (ExpandTabView) view.findViewById(R.id.expand_tabview_course_id);
        expand_tabview_course_id.setOnFilterSelected(this);
        //筛选功能数据源
        nameList = new ArrayList<>();
        nameList.add("排序");
        nameList.add("课程价格");
        nameList.add("难度等级");
        expand_tabview_course_id.setNameList(nameList);


        //PullToRefreshListView控件的获得
        ptrlv_course_id = (PullToRefreshListView) view.findViewById(R.id.ptrlv_course_id);
        //关于PullToRefreshListView的操作
        aboutPullToRefreshListView();


        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 筛选选择
     *
     * @param tabView     选中的tab
     * @param position    选中tab的position
     * @param singleCheck 是否为单次选中，为true的时候调出选择view，为false的时候隐藏选择view
     */
    @Override
    public void onSelected(FilterTabView tabView, int position, boolean singleCheck) {
        if (singleCheck) {
            if (position == 0) {

                expand_tabview_course_id.setExpandView(getCourseOrderView());
            }
            if (position == 1) {
                expand_tabview_course_id.setExpandView(getCoursePriceView());
            }
            if (position == 2) {
                expand_tabview_course_id.setExpandView(getCourseDifficultyView());
            }

        }

    }
    //筛选下拉框数据
    private ListView getCourseOrderView() {
        final String[] courseArrayList = new String[]{"最新", "最热"};
        ListView listView = new ListView(getActivity());
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(courseArrayList)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                expand_tabview_course_id.getTabViews().get(0).setText(courseArrayList[i]);
                expand_tabview_course_id.closeExpand();
                //第一次出现"-"的索引
                int firstindex = courseUrlStr.indexOf('-') + 1;
                //第二次出现"-"的索引
                String tempString = courseUrlStr.substring(firstindex);
                int index = tempString.indexOf('-') + 1 + firstindex;
                StringBuilder sb = new StringBuilder();
                sb = sb.append(courseUrlStr);
                String paixuPage = new String();
                //
                if (i == 0) {
                    paixuPage = "6";
                } else {
                    paixuPage = "4";
                }
                sb.replace(index, index + 1, paixuPage);
                courseUrlStr = sb.toString();
                Log.i("courseUrlStr", courseUrlStr);
                aboutPullToRefreshListView();

            }
        });
        return listView;
    }

    //筛选下拉框数据
    private View getCoursePriceView() {
        final String[] courseArrayList = new String[]{"全部", "免费", "付费"};
        ListView listView = new ListView(getActivity());
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(courseArrayList)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                expand_tabview_course_id.getTabViews().get(1).setText(courseArrayList[i]);
                expand_tabview_course_id.closeExpand();

                //第一次出现"-"的索引
                int firstindex = courseUrlStr.indexOf('-') + 1;
                //第二次出现"-"的索引
                String tempString = courseUrlStr.substring(firstindex);
                int twoindex = tempString.indexOf('-') + 1;
                //第三次出现"-"的索引
                String tempString2 = tempString.substring(twoindex);
                int threeindex = tempString2.indexOf('-') + 1 + firstindex + twoindex;
                StringBuilder sb = new StringBuilder();
                sb = sb.append(courseUrlStr);
                String pricePage = new String();
                //
                switch (i) {
                    case 0:
                        pricePage = "0";
                        break;
                    case 1:
                        pricePage = "1";
                        break;
                    case 2:
                        pricePage = "2";
                        break;
                    default:
                }

                sb.replace(threeindex, threeindex + 1, pricePage);
                courseUrlStr = sb.toString();
                Log.i("courseUrlStr", courseUrlStr);
                aboutPullToRefreshListView();


            }
        });
        return listView;
    }

    private View getCourseDifficultyView() {
        final String[] courseArrayList = new String[]{"零基础", "基础", "中级", "高级", "全部"};
        ListView listView = new ListView(getActivity());
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(courseArrayList)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                expand_tabview_course_id.getTabViews().get(2).setText(courseArrayList[i]);
                expand_tabview_course_id.closeExpand();
                //第一次出现"-"的索引
                int firstindex = courseUrlStr.indexOf('-') + 1;
                //第二次出现"-"的索引
                String tempString = courseUrlStr.substring(firstindex);
                int twoindex = tempString.indexOf('-') + 1;
                //第三次出现"-"的索引
                String tempString2 = tempString.substring(twoindex);
                int threeindex = tempString2.indexOf('-') + 1;
                //第四次出现"-"的索引
                String tempString3 = tempString2.substring(threeindex);
                int fourindex = tempString3.indexOf('-') + 1 + firstindex + twoindex + threeindex;

                StringBuilder sb = new StringBuilder();
                sb = sb.append(courseUrlStr);
                String defaultPage = i + 1 + "";


                sb.replace(fourindex, fourindex + 1, defaultPage);
                courseUrlStr = sb.toString();
                Log.i("courseUrlStr", courseUrlStr);
                aboutPullToRefreshListView();

            }
        });
        return listView;
    }


    /**
     * 关于PullToRefreshListView的操作
     */
    private void aboutPullToRefreshListView() {

        //开启异步任务下载课程数据
        //courseUrlStr = "http://www.kgc.cn/list/230-1-6-9-9.shtml";
        CommonAsyncTask asyncTask = new CommonAsyncTask(this, fileName);
        asyncTask.execute(courseUrlStr);

        //适配器前的操作
        ListView actualListView = ptrlv_course_id.getRefreshableView();
        registerForContextMenu(actualListView);
        data = new LinkedList<>();

        // 填充数据源
        fillDataSouce();

        //准备适配器
        adapter = new SimpleAdapter(getActivity(), data, R.layout.course_item, new String[]{
                "img", "title", "description", "num", "price"}, new int[]{
                R.id.iv_course_id, R.id.tv_course_title_id, R.id.tv_descrition_id, R.id.tv_nums_id, R.id.tv_price_id});
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
        ptrlv_course_id.setAdapter(adapter);

        //拉动效果配置
        ptrlv_course_id.setMode(PullToRefreshBase.Mode.BOTH);
        //刷新和加载
        ptrlv_course_id.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            /**
             * 顶部拉动刷新界面
             * @param refreshView
             */
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //显示当前系统的时间
                //TODO
                String label = DateUtils.formatDateTime(getActivity(),
                        System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                //刷新当前的界面
                new GetDataTask().execute();

            }

            /**
             * 底部拉动加载新的数据
             * @param refreshView
             */
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
        ptrlv_course_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                String courUrl="http://www.kgc.cn"+(String) data.get(i).get("cour");
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
        courses.clear();

        //本地缓存文件实例获取
        File file = new File(Environment.getExternalStorageDirectory(), fileName + ".html");
        //json解析目录数据
        courses = JsoupAnalyzeHtml.analysCourses(file);
        //如果courses里面有内容才填充数据，避免空指针异常
        if (courses.size() > 0) {
            Log.i("tag", "有数据源可以填充");
            for (int i = 0; i < courses.size(); i++) {
                Course course = courses.get(i);

                Map<String, Object> listem = new HashMap<String, Object>();
                String imgUrlStr = course.getImgUrlStr();
                try {

                    ImmageAsyncTask asyncTask = new ImmageAsyncTask(activity);
                    asyncTask.execute(imgUrlStr);
                    Bitmap bitmap = asyncTask.get();
                    listem.put("img", bitmap);
                    listem.put("title", course.getTitle());
                    listem.put("description", course.getDescription());
                    listem.put("num", course.getStudyNum());
                    listem.put("price", course.getMoney());
                    listem.put("cour",course.getCourseUrl());
                    data.add(listem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            ptrlv_course_id.onRefreshComplete();

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
            String courseDownUrlStr = courseUrlStr;
            int index = courseDownUrlStr.indexOf('-') + 1;
            StringBuilder sb = new StringBuilder();
            sb = sb.append(courseDownUrlStr);
            //页码加1加载下一页
            pageInt = pageInt + 1;
            sb.replace(index, index + 1, Integer.toString(pageInt));
            courseDownUrlStr = sb.toString();

            //底部加载数据缓冲文件名
            String fileDownName = courseDownUrlStr.substring(courseDownUrlStr.lastIndexOf("/") + 1);
            Log.i("tag", "开启异步任务加载底部的数据：文件名是：" + fileDownName);
            CommonAsyncTask asyncTask = new CommonAsyncTask(CourseFragment.this, fileDownName);
            asyncTask.execute(courseDownUrlStr);

            //刷新增加数据
            fillDownDataSouce(fileDownName);
            Log.i("tag", courseDownUrlStr);
            Log.i("tag", fileDownName);

            // Call onRefreshComplete when the list has been refreshed.
            ptrlv_course_id.onRefreshComplete();

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
        List<Course> coursesTemp = new LinkedList<>();
        coursesTemp = JsoupAnalyzeHtml.analysCourses(file);
        if (coursesTemp.size() > 0) {
            //判断是否还有更多数据，如果没有就不加载，避免加载重复的课程
            if (!(coursesTemp.get(0).getTitle()).equals(courses.get(0).getTitle())) {
                courses.clear();
                courses.addAll(coursesTemp);


                Log.i("tag", "①实现了底部加载数据源中解析功能---》" + fileDownName);
                Log.i("tag", "地步加载course集合内容" + courses.toString());


                Log.i("tag", "实现了解析的数据的填充");
                for (int i = 0; i < courses.size(); i++) {
                    Course course = courses.get(i);

                    Map<String, Object> listem = new HashMap<String, Object>();
                    String imgUrlStr = course.getImgUrlStr();

                    try {
                        ImmageAsyncTask asyncTask = new ImmageAsyncTask(activity);
                        asyncTask.execute(imgUrlStr);
                        Bitmap bitmap = asyncTask.get();
                        listem.put("img", bitmap);
                        listem.put("title", course.getTitle());
                        listem.put("description", course.getDescription());
                        listem.put("num", course.getStudyNum());
                        listem.put("price", course.getMoney());
                        listem.put("cour",course.getCourseUrl());
                        data.add(listem);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Log.i("tag", "更新适配器");
                adapter.notifyDataSetChanged();
            }
        }

    }


    /**
     * 接口回调文件名
     *
     * @param fileName
     */
    @Override
    public void getFileName(String fileName) {
        if ("courseContent".equals(fileName)) {
            Log.i("tag", "回调了准备数据源数据的方法");
            fillDataSouce();
        } else {
            Log.i("tag", "回调了底部加载数据的方法" + fileName);
            fillDownDataSouce(fileName);
        }
    }
}
