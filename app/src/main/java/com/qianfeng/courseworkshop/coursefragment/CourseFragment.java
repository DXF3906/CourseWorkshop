package com.qianfeng.courseworkshop.coursefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.qianfeng.courseworkshop.R;
import com.qianfeng.courseworkshop.filter.ExpandTabView;
import com.qianfeng.courseworkshop.filter.FilterTabView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 课程对应的Fragmnet
 * fdb
 * Created by asus on 2016/7/6.
 */

public class CourseFragment extends Fragment implements ExpandTabView.OnFilterSelected{
    private FragmentActivity activity;
    private View view;
    private ExpandTabView expand_tabview_course_id;
    private ArrayList<String> nameList;

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
        expand_tabview_course_id = (ExpandTabView)view.findViewById(R.id.expand_tabview_course_id);
        expand_tabview_course_id.setOnFilterSelected(this);
        //数据源
        nameList = new ArrayList<>();
        nameList.add("排序");
        nameList.add("课程价格");
        nameList.add("难度等级");
        expand_tabview_course_id.setNameList(nameList);
        //关于PullToRefreshListView的操作
        aboutPullToRefreshListView();
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 关于PullToRefreshListView的操作
     */
    private void aboutPullToRefreshListView() {
    }

    /**
     *筛选选择
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
        final String [] courseArrayList=new String[]{"最新", "最热"};
        ListView listView = new ListView(getActivity());
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(courseArrayList)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });
        return listView;
    }
    //筛选下拉框数据
    private View getCoursePriceView() {
        ListView listView = new ListView(getActivity());
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(new String[]{"全部", "免费", "付费"})));
        return listView;
    }
    private View getCourseDifficultyView() {
        ListView listView = new ListView(getActivity());
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(new String[]{"全部", "零基础", "基础","中级","高级"})));
        return listView;
    }

}
