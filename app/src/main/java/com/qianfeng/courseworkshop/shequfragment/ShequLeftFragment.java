package com.qianfeng.courseworkshop.shequfragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.qianfeng.courseworkshop.R;
import com.qianfeng.courseworkshop.asynctask.CommonAsyncTask;
import com.qianfeng.courseworkshop.bean.CommonData;
import com.qianfeng.courseworkshop.bean.CourseLogcat;
import com.qianfeng.courseworkshop.inner.GetCourseLeftUrlCallBack;
import com.qianfeng.courseworkshop.inner.GetFileNameCallBack;
import com.qianfeng.courseworkshop.util.JsonAnalyze;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 社区对应的侧滑界面
 * fdb
 * Created by asus on 2016/7/11.
 */

public class ShequLeftFragment extends Fragment implements GetFileNameCallBack {//实现接口，用于回调数据

    private FragmentActivity activity;
    private String fileName = "shequLogcat";//目录json数据缓存到本地的文件名
    private List<CourseLogcat> shequLogcats = new LinkedList<>();

    private Button btn_1f_id;
    private Button btn_2f_id;
    private Button btn_3f_id;
    private Button btn_4f_id;
    private Button btn_5f_id;
    private Button btn_6f_id;
    private Button btn_7f_id;
    private Button btn_8f_id;


    //每个大类对应的ListView控件
    private ListView lv_xing_id;
    private ListView lv_resource_id;
    private ListView lv_ui_id;
    private ListView lv_web_id;
    private ListView lv_big_id;
    private ListView lv_yun_id;
    private ListView lv_re_id;
    private ListView lv_other_id;

    //每个ListView的数据源
    private List<Map<String, Object>> xingData;
    private List<Map<String, Object>> resourceData;
    private List<Map<String, Object>> uiData;
    private List<Map<String, Object>> webData;
    private List<Map<String, Object>> bigData;
    private List<Map<String, Object>> yunData;
    private List<Map<String, Object>> reData;
    private List<Map<String, Object>> otherData;


    //社区目录图片资源ID数组
    private int[] imgeXingIds = new int[]{R.mipmap.ic_exam_twoc, R.mipmap.ic_exam_office, R.mipmap.ic_exam_office
            , R.mipmap.ic_exam_office, R.mipmap.ic_exam_office, R.mipmap.ic_exam_office, R.mipmap.ic_exam_office};
    private int[] imgeResoiurceIds = new int[]{R.mipmap.ic_exam_twoc, R.mipmap.ic_exam_office, R.mipmap.ic_exam_office
            , R.mipmap.ic_exam_office};
    private int[] imgeUiIds = new int[]{R.mipmap.ic_exam_twoc, R.mipmap.ic_exam_office, R.mipmap.ic_exam_office
            ,  R.mipmap.ic_exam_office, R.mipmap.ic_exam_office, R.mipmap.ic_exam_office};
    private int[] imgeWebIds = new int[]{R.mipmap.ic_exam_twoc, R.mipmap.ic_exam_office, R.mipmap.ic_exam_office
            ,  R.mipmap.ic_exam_office, R.mipmap.ic_exam_office};
    private int[] imgeBigIds = new int[]{R.mipmap.ic_exam_twoc, R.mipmap.ic_exam_office, R.mipmap.ic_exam_office
            ,  R.mipmap.ic_exam_office, R.mipmap.ic_exam_office, R.mipmap.ic_exam_office, R.mipmap.ic_exam_office, R.mipmap.ic_exam_office};
    private int[] imgeYunIds = new int[]{R.mipmap.ic_exam_twoc, R.mipmap.ic_exam_office, R.mipmap.ic_exam_office
            ,  R.mipmap.ic_exam_office, R.mipmap.ic_exam_office};
    private int[] imgeReIds = new int[]{R.mipmap.ic_exam_twoc, R.mipmap.ic_exam_office, R.mipmap.ic_exam_office
            ,  R.mipmap.ic_exam_office, R.mipmap.ic_exam_office,R.mipmap.ic_exam_twoc, R.mipmap.ic_exam_office, R.mipmap.ic_exam_office
            ,  R.mipmap.ic_exam_office, R.mipmap.ic_exam_office,R.mipmap.ic_exam_twoc, R.mipmap.ic_exam_office, R.mipmap.ic_exam_office
            ,  R.mipmap.ic_exam_office, R.mipmap.ic_exam_office,R.mipmap.ic_exam_twoc, R.mipmap.ic_exam_office, R.mipmap.ic_exam_office
            ,  R.mipmap.ic_exam_office, R.mipmap.ic_exam_office};
    private int[] imgeOtherIds = new int[]{R.mipmap.ic_exam_twoc, R.mipmap.ic_exam_office, R.mipmap.ic_exam_office
            ,  R.mipmap.ic_exam_office, R.mipmap.ic_exam_office, R.mipmap.ic_exam_office};

    //适配器实例
    private SimpleAdapter xingAdapter;
    private SimpleAdapter resourcrAdapter;
    private SimpleAdapter uiAdapter;
    private SimpleAdapter webAdapter;
    private SimpleAdapter bigAdapter;
    private SimpleAdapter yunAdapter;
    private SimpleAdapter reAdapter;
    private SimpleAdapter otherAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //布局控件的获取
        View view = inflater.inflate(R.layout.shequ_left_fragment, null);

        //初始化所有子控件
        initView(view);

        //开启异步任务下载目录数据
        CommonAsyncTask asyncTask = new CommonAsyncTask(this, fileName);
        asyncTask.execute(CommonData.ShequCatalog);

        //准备数据源
        xingData = new LinkedList<Map<String, Object>>();
        resourceData = new LinkedList<Map<String, Object>>();
        uiData = new LinkedList<Map<String, Object>>();
        webData = new LinkedList<Map<String, Object>>();
        bigData = new LinkedList<Map<String, Object>>();
        yunData = new LinkedList<Map<String, Object>>();
        reData = new LinkedList<Map<String, Object>>();
        otherData = new LinkedList<Map<String, Object>>();
        fillDataSource();

        //适配器
        aboutAdapter();

        //绑定适配器
        bindAdapter();

        //添加监听器
        aboutListener();



        return view;
    }



    /**
     * 绑定适配器
     */
    private void bindAdapter() {

    }

    /**
     * 适配器
     */
    private void aboutAdapter() {

    }

    /**
     * 准备数据源
     */
    private void fillDataSource() {
        //填充数据源前必须清空
        shequLogcats.clear();

        //本地缓存文件实例获取
        File file = new File(Environment.getExternalStorageDirectory(), fileName + ".html");
        //json解析目录数据
        shequLogcats = JsonAnalyze.analysCoursesLogcat(file);

        if (shequLogcats.size() > 0) {
            //准备每类数据源
            //研发
            int program = 1;
            for (int i = 0; i < imgeXingIds.length; i++) {
                CourseLogcat courseLogcat = shequLogcats.get(program++);
                while (Integer.parseInt(courseLogcat.getTotal()) == 0) {
                    courseLogcat = shequLogcats.get(program++);
                }
                Map<String, Object> listem = new HashMap<String, Object>();
                listem.put("imgId", imgeXingIds[i]);
                listem.put("name", courseLogcat.getName());
                listem.put("total", courseLogcat.getTotal());

                listem.put("catalogId",courseLogcat.getCatalogId());
                listem.put("isJob",courseLogcat.getIsJob());

                xingData.add(listem);
            }
        }

    }

    /**
     * 初始化所有子控件
     */
    private void initView(View view) {

        //每个listView目录
        lv_xing_id = (ListView) view.findViewById(R.id.lv_xing_id);
        lv_resource_id = (ListView) view.findViewById(R.id.lv_resource_id);
        lv_ui_id = (ListView) view.findViewById(R.id.lv_ui_id);
        lv_web_id = (ListView) view.findViewById(R.id.lv_web_id);

        lv_big_id = (ListView) view.findViewById(R.id.lv_big_id);
        lv_yun_id = (ListView) view.findViewById(R.id.lv_yun_id);
        lv_re_id = (ListView) view.findViewById(R.id.lv_re_id);
        lv_other_id = (ListView) view.findViewById(R.id.lv_other_id);
        //按钮控件实现缩放
        btn_1f_id=(Button)view.findViewById(R.id.btn_1f_id);
        btn_2f_id=(Button)view.findViewById(R.id.btn_2f_id);
        btn_3f_id=(Button)view.findViewById(R.id.btn_3f_id);
        btn_4f_id=(Button)view.findViewById(R.id.btn_4f_id);
        btn_5f_id=(Button)view.findViewById(R.id.btn_5f_id);
        btn_6f_id=(Button)view.findViewById(R.id.btn_6f_id);
        btn_7f_id=(Button)view.findViewById(R.id.btn_7f_id);
        btn_8f_id=(Button)view.findViewById(R.id.btn_8f_id);

    }

    /**
     * 添加监听器
     */
    private void aboutListener() {

    }



    /**
     * 接口回调文件名
     *
     * @param fileName
     */
    @Override
    public void getFileName(String fileName) {

        fillDataSource();
        //programAdapter.notifyDataSetChanged();


    }
}
