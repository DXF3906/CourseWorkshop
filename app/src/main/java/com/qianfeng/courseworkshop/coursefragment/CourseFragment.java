package com.qianfeng.courseworkshop.coursefragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.qianfeng.courseworkshop.R;
import com.qianfeng.courseworkshop.TikuFragment;
import com.qianfeng.courseworkshop.asynctask.CommonAsyncTask;
import com.qianfeng.courseworkshop.asynctask.ImmageAsyncTask;
import com.qianfeng.courseworkshop.bean.CommonData;
import com.qianfeng.courseworkshop.bean.Course;
import com.qianfeng.courseworkshop.filter.ExpandTabView;
import com.qianfeng.courseworkshop.filter.FilterTabView;
import com.qianfeng.courseworkshop.inner.GetFileBitmapCallBack;
import com.qianfeng.courseworkshop.inner.GetFileNameCallBack;
import com.qianfeng.courseworkshop.util.JsoupAnalyzeHtml;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 课程对应的Fragmnet
 * fdb
 * Created by asus on 2016/7/6.
 */

public class CourseFragment extends Fragment implements ExpandTabView.OnFilterSelected, GetFileNameCallBack {
    private FragmentActivity activity;
    private View view;
    private ExpandTabView expand_tabview_course_id;
    private ArrayList<String> nameList;
    private PullToRefreshListView ptrlv_course_id;
    private SimpleAdapter adapter;
    private LinkedList<Map<String, Object>> data;
    private List<Course> courses = new LinkedList<>();
    private String fileName = "courseContent";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_course, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //筛选功能
        expand_tabview_course_id = (ExpandTabView) view.findViewById(R.id.expand_tabview_course_id);
        expand_tabview_course_id.setOnFilterSelected(this);
        //数据源
        nameList = new ArrayList<>();
        nameList.add("排序");
        nameList.add("课程价格");
        nameList.add("难度等级");
        expand_tabview_course_id.setNameList(nameList);

        //关于PullToRefreshListView的操作
        ptrlv_course_id = (PullToRefreshListView) view.findViewById(R.id.ptrlv_course_id);
        aboutPullToRefreshListView();

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 关于PullToRefreshListView的操作
     */
    private void aboutPullToRefreshListView() {
        //拉动效果配置
        ptrlv_course_id.setMode(PullToRefreshBase.Mode.BOTH);

        //刷新（待处理）
        ptrlv_course_id.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(),
                        System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }



        });
        //到底刷新（待处理）
        ptrlv_course_id.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {

            }
        });

        //开启异步任务下载目录数据
        //TODO
        CommonAsyncTask asyncTask = new CommonAsyncTask(this, fileName);
        asyncTask.execute("http://www.kgc.cn/list/230-6-9-9.shtml");

        //适配器的操作
        ListView actualListView = ptrlv_course_id.getRefreshableView();
        registerForContextMenu(actualListView);
        data = new LinkedList<>();
        // 填充数据源
        fillDataSouce();

        adapter = new SimpleAdapter(getActivity(), data, R.layout.course_item, new String[]{
                "img", "title", "description", "num", "price"}, new int[]{
                R.id.iv_course_id, R.id.tv_course_title_id, R.id.tv_descrition_id, R.id.tv_nums_id, R.id.tv_price_id});
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {

            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if (view instanceof ImageView) {
                    if (data instanceof Bitmap){

                        if (data != null  ) {

                            ImageView iv = (ImageView) view;
                            iv.setImageBitmap((Bitmap) data);
                            return true;
                        }else{
                            ImageView iv = (ImageView) view;
                            iv.setImageResource(R.mipmap.course_img);
                            return true;

                        }
                    }
                }
                return false;
            }
        });
        // 经典之处：将适配器设置到ListView中
        // setListAdapter(adapter);
        ptrlv_course_id.setAdapter(adapter);


    }

    /**
     * 填充数据源
     */
    private void fillDataSouce() {
        //填充数据源前必须清空
        courses.clear();

        //本地缓存文件实例获取
        File file = new File(Environment.getExternalStorageDirectory(), fileName + ".html");
        //json解析目录数据
        courses = JsoupAnalyzeHtml.analysCourses(file);

        if (courses.size() > 0) {
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
                    data.add(listem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            adapter.notifyDataSetChanged();
        }

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

            }
        });
        return listView;
    }

    //筛选下拉框数据
    private View getCoursePriceView() {
        final String[] courseArrayList = new String[]{"全部", "免费", "付费"};
        ListView listView = new ListView(getActivity());
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(new String[]{"全部", "免费", "付费"})));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                expand_tabview_course_id.getTabViews().get(1).setText(courseArrayList[i]);
                expand_tabview_course_id.closeExpand();

            }
        });
        return listView;
    }

    private View getCourseDifficultyView() {
        final String[] courseArrayList = new String[]{"全部", "零基础", "基础", "中级", "高级"};
        ListView listView = new ListView(getActivity());
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(new String[]{"全部", "零基础", "基础", "中级", "高级"})));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                expand_tabview_course_id.getTabViews().get(1).setText(courseArrayList[i]);
                expand_tabview_course_id.closeExpand();

            }
        });
        return listView;
    }

    /**
     * 接口回调文件名
     *
     * @param fileName
     */
    @Override
    public void getFileName(String fileName) {
        fillDataSouce();
    }


}
