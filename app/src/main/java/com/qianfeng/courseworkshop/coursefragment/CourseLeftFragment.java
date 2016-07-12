package com.qianfeng.courseworkshop.coursefragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
 * 课程对应的侧滑界面
 * fdb
 * Created by asus on 2016/7/6.
 */

public class CourseLeftFragment extends Fragment implements GetFileNameCallBack {//实现接口，用于回调数据

    private FragmentActivity activity;
    private String fileName = "courseLogcat";//目录json数据缓存到本地的文件名
    private List<CourseLogcat> courseLogcats = new LinkedList<>();

    private GetCourseLeftUrlCallBack callCourseLeftUrlCallBack;

    //每个大类对应的课程数控件
    private TextView tv_allcourse_id;
    private TextView tv_program_id;
    private TextView tv_spread_id;
    private TextView tv_seeing_id;
    private TextView tv_net_id;
    private TextView tv_carrer_id;
    private TextView tv_exam_id;

    private LinearLayout ll_allcourse_id;
    private LinearLayout ll_program_id;
    private LinearLayout ll_spread_id;
    private LinearLayout ll_seeing_id;
    private LinearLayout ll_net_id;
    private LinearLayout ll_carrer_id;
    private LinearLayout ll_exam_id;

    //每个大类对应的ListView控件
    private ListView lv_program_id;
    private ListView lv_spread_id;
    private ListView lv_seeing_id;
    private ListView lv_net_id;
    private ListView lv_carrer_id;
    private ListView lv_exam_id;

    //每个ListView的数据源
    private List<Map<String, Object>> programData;
    private List<Map<String, Object>> spreadData;
    private List<Map<String, Object>> seeingData;
    private List<Map<String, Object>> netData;
    private List<Map<String, Object>> carrerData;
    private List<Map<String, Object>> examData;

    //研发编程目录图片资源ID数组
    private int[] imgProgramIds = new int[]{R.mipmap.ic_program_carrer, R.mipmap.icon_program_html5,
            R.mipmap.icon_program_java, R.mipmap.icon_program_php, R.mipmap.icon_program_android,
            R.mipmap.icon_program_python, R.mipmap.ic_program_swift, R.mipmap.ic_program_ios, R.mipmap.ic_program_javascript,
            R.mipmap.ic_program_bootstrap, R.mipmap.ic_program_jquerymobile, R.mipmap.ic_program_haddop,
            R.mipmap.ic_program_mysql, R.mipmap.ic_program_orcale, R.mipmap.ic_program_testbase,
            R.mipmap.ic_program_loadrunner, R.mipmap.ic_program_uft};
    //推广运营目录图片资源ID数组
    private int[] imgspreadIds = new int[]{R.mipmap.ic_spread_carrer, R.mipmap.ic_spread_weixin, R.mipmap.ic_spread_buy,
            R.mipmap.ic_spread_seo, R.mipmap.ic_spread_sem, R.mipmap.ic_spread_social, R.mipmap.ic_spread_base};
    //视觉创意目录图片资源ID数组
    private int[] imgseeingIds = new int[]{R.mipmap.ic_seeing_carrer, R.mipmap.ic_seeing_app, R.mipmap.ic_seeing_design,
            R.mipmap.ic_seeing_ps, R.mipmap.ic_seeing_hand, R.mipmap.ic_seeing_ai, R.mipmap.ic_seeing_net};
    //网络安全目录图片资源ID数组
    private int[] imgnetIds = new int[]{R.mipmap.ic_net_carrer, R.mipmap.ic_net_vr, R.mipmap.ic_net_ar, R.mipmap.ic_net_linux,
            R.mipmap.ic_net_sheel};
    //职场心理目录图片资源ID数组
    private int[] imgcarrerIds = new int[]{R.mipmap.ic_carrer_carrer, R.mipmap.ic_carrer_soft, R.mipmap.ic_carrer_plan,
            R.mipmap.ic_carrer_new};
    //考试认证目录图片资源ID数组
    private int[] imgexamIds = new int[]{R.mipmap.ic_exam_twoc, R.mipmap.ic_exam_office};

    //适配器实例
    private SimpleAdapter programAdapter;
    private SimpleAdapter spreadAdapter;
    private SimpleAdapter seeingAdapter;
    private SimpleAdapter netAdapter;
    private SimpleAdapter carrerAdapter;
    private SimpleAdapter examAdapter;

    public CourseLeftFragment() {
    }

    public CourseLeftFragment(GetCourseLeftUrlCallBack callCourseLeftUrlCallBack) {
        this.callCourseLeftUrlCallBack = callCourseLeftUrlCallBack;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //布局控件的获取
        View view = inflater.inflate(R.layout.course_left_fragment, null);

        //初始化所有子控件
        initView(view);

        //开启异步任务下载目录数据
        CommonAsyncTask asyncTask = new CommonAsyncTask(this, fileName);
        asyncTask.execute(CommonData.CourseCatalog);

        //准备数据源
        programData = new LinkedList<Map<String, Object>>();
        spreadData = new LinkedList<Map<String, Object>>();
        seeingData = new LinkedList<Map<String, Object>>();
        netData = new LinkedList<Map<String, Object>>();
        carrerData = new LinkedList<Map<String, Object>>();
        examData = new LinkedList<Map<String, Object>>();
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
        lv_program_id.setAdapter(programAdapter);
        lv_spread_id.setAdapter(spreadAdapter);
        lv_seeing_id.setAdapter(seeingAdapter);
        lv_net_id.setAdapter(netAdapter);
        lv_carrer_id.setAdapter(carrerAdapter);
        lv_exam_id.setAdapter(examAdapter);
    }

    /**
     * 适配器
     */
    private void aboutAdapter() {
        programAdapter = new SimpleAdapter(activity, programData, R.layout.course_logcat_item, new String[]{"name", "imgId", "total"}, new int[]{R.id.tv_title_id, R.id.iv_id, R.id.tv_num_id});
        spreadAdapter = new SimpleAdapter(activity, spreadData, R.layout.course_logcat_item, new String[]{"name", "imgId", "total"}, new int[]{R.id.tv_title_id, R.id.iv_id, R.id.tv_num_id});
        seeingAdapter = new SimpleAdapter(activity, seeingData, R.layout.course_logcat_item, new String[]{"name", "imgId", "total"}, new int[]{R.id.tv_title_id, R.id.iv_id, R.id.tv_num_id});
        netAdapter = new SimpleAdapter(activity, netData, R.layout.course_logcat_item, new String[]{"name", "imgId", "total"}, new int[]{R.id.tv_title_id, R.id.iv_id, R.id.tv_num_id});
        carrerAdapter = new SimpleAdapter(activity, carrerData, R.layout.course_logcat_item, new String[]{"name", "imgId", "total"}, new int[]{R.id.tv_title_id, R.id.iv_id, R.id.tv_num_id});
        examAdapter = new SimpleAdapter(activity, examData, R.layout.course_logcat_item, new String[]{"name", "imgId", "total"}, new int[]{R.id.tv_title_id, R.id.iv_id, R.id.tv_num_id});

    }

    /**
     * 准备数据源
     */
    private void fillDataSource() {
        //填充数据源前必须清空
        courseLogcats.clear();

        //本地缓存文件实例获取
        File file = new File(Environment.getExternalStorageDirectory(), fileName + ".html");
        //json解析目录数据
        courseLogcats = JsonAnalyze.analysCoursesLogcat(file);

        if (courseLogcats.size() > 0) {
            //设置几个大类的课程数
            tv_allcourse_id.setText("/" + (Integer.parseInt(courseLogcats.get(0).getTotal()) + Integer.parseInt(courseLogcats.get(23).getTotal()) +
                    Integer.parseInt(courseLogcats.get(31).getTotal()) + Integer.parseInt(courseLogcats.get(39).getTotal()) +
                    Integer.parseInt(courseLogcats.get(46).getTotal()) + Integer.parseInt(courseLogcats.get(51).getTotal())));
            tv_program_id.setText("/" + courseLogcats.get(0).getTotal());
            tv_spread_id.setText("/" + courseLogcats.get(23).getTotal());
            tv_seeing_id.setText("/" + courseLogcats.get(31).getTotal());
            tv_net_id.setText("/" + courseLogcats.get(39).getTotal());
            tv_carrer_id.setText("/" + courseLogcats.get(46).getTotal());
            tv_exam_id.setText("/" + courseLogcats.get(51).getTotal());

            //准备每类数据源
            //研发
            int program = 1;
            for (int i = 0; i < imgProgramIds.length; i++) {
                CourseLogcat courseLogcat = courseLogcats.get(program++);
                while (Integer.parseInt(courseLogcat.getTotal()) == 0) {
                    courseLogcat = courseLogcats.get(program++);
                }
                Map<String, Object> listem = new HashMap<String, Object>();
                listem.put("imgId", imgProgramIds[i]);
                listem.put("name", courseLogcat.getName());
                listem.put("total", courseLogcat.getTotal());

                listem.put("catalogId",courseLogcat.getCatalogId());
                listem.put("isJob",courseLogcat.getIsJob());

                programData.add(listem);
            }
            //推广
            int spread = 24;
            for (int i = 0; i < imgspreadIds.length; i++) {
                CourseLogcat courseLogcat = courseLogcats.get(spread++);
                if (Integer.parseInt(courseLogcat.getTotal()) == 0) {
                    courseLogcat = courseLogcats.get(spread++);
                }
                Map<String, Object> listem = new HashMap<String, Object>();
                listem.put("imgId", imgspreadIds[i]);
                listem.put("name", courseLogcat.getName());
                listem.put("total", courseLogcat.getTotal());
                listem.put("catalogId",courseLogcat.getCatalogId());
                listem.put("isJob",courseLogcat.getIsJob());
                spreadData.add(listem);
            }
            //视觉
            int see = 32;
            for (int i = 0; i < imgseeingIds.length; i++) {
                CourseLogcat courseLogcat = courseLogcats.get(see++);
                if (Integer.parseInt(courseLogcat.getTotal()) == 0) {
                    courseLogcat = courseLogcats.get(see++);
                }
                Map<String, Object> listem = new HashMap<String, Object>();
                listem.put("imgId", imgseeingIds[i]);
                listem.put("name", courseLogcat.getName());
                listem.put("total", courseLogcat.getTotal());
                listem.put("catalogId",courseLogcat.getCatalogId());
                listem.put("isJob",courseLogcat.getIsJob());
                seeingData.add(listem);
            }
            //网络
            int net = 40;
            for (int i = 0; i < imgnetIds.length; i++) {
                CourseLogcat courseLogcat = courseLogcats.get(net++);
                if (Integer.parseInt(courseLogcat.getTotal()) == 0) {
                    courseLogcat = courseLogcats.get(net++);
                }
                Map<String, Object> listem = new HashMap<String, Object>();
                listem.put("imgId", imgnetIds[i]);
                listem.put("name", courseLogcat.getName());
                listem.put("total", courseLogcat.getTotal());
                listem.put("catalogId",courseLogcat.getCatalogId());
                listem.put("isJob",courseLogcat.getIsJob());
                netData.add(listem);
            }
            //职场心理
            int carrer = 47;
            for (int i = 0; i < imgcarrerIds.length; i++) {
                CourseLogcat courseLogcat = courseLogcats.get(carrer++);
                if (Integer.parseInt(courseLogcat.getTotal()) == 0) {
                    courseLogcat = courseLogcats.get(carrer++);
                }
                Map<String, Object> listem = new HashMap<String, Object>();
                listem.put("imgId", imgcarrerIds[i]);
                listem.put("name", courseLogcat.getName());
                listem.put("total", courseLogcat.getTotal());
                listem.put("catalogId",courseLogcat.getCatalogId());
                listem.put("isJob",courseLogcat.getIsJob());
                carrerData.add(listem);
            }
            //考试
            int exam = 52;
            for (int i = 0; i < imgexamIds.length; i++) {
                CourseLogcat courseLogcat = courseLogcats.get(exam++);
                if (Integer.parseInt(courseLogcat.getTotal()) == 0) {
                    courseLogcat = courseLogcats.get(exam++);
                }
                Map<String, Object> listem = new HashMap<String, Object>();
                listem.put("imgId", imgexamIds[i]);
                listem.put("name", courseLogcat.getName());
                listem.put("total", courseLogcat.getTotal());
                listem.put("catalogId",courseLogcat.getCatalogId());
                listem.put("isJob",courseLogcat.getIsJob());
                examData.add(listem);
            }
        }


    }

    /**
     * 初始化所有子控件
     */
    private void initView(View view) {

        ll_allcourse_id=(LinearLayout) view.findViewById(R.id.ll_allcourse_id);
        tv_allcourse_id = (TextView) view.findViewById(R.id.tv_allcourse_id);//全部课程数

        ll_program_id=(LinearLayout) view.findViewById(R.id.ll_program_id);
        tv_program_id = (TextView) view.findViewById(R.id.tv_program_id);//研发.编程
        lv_program_id = (ListView) view.findViewById(R.id.lv_program_id);//研发.编程

        ll_spread_id=(LinearLayout) view.findViewById(R.id.ll_spread_id);
        tv_spread_id = (TextView) view.findViewById(R.id.tv_spread_id);//运营.推广
        lv_spread_id = (ListView) view.findViewById(R.id.lv_spread_id);//运营.推广

        ll_seeing_id=(LinearLayout) view.findViewById(R.id.ll_seeing_id);
        tv_seeing_id = (TextView) view.findViewById(R.id.tv_seeing_id);//视觉.创意
        lv_seeing_id = (ListView) view.findViewById(R.id.lv_seeing_id);//视觉.创意

        ll_net_id=(LinearLayout) view.findViewById(R.id.ll_net_id);
        tv_net_id = (TextView) view.findViewById(R.id.tv_net_id);//网络.安全
        lv_net_id = (ListView) view.findViewById(R.id.lv_net_id);//网络.安全

        ll_carrer_id=(LinearLayout) view.findViewById(R.id.ll_carrer_id);
        tv_carrer_id = (TextView) view.findViewById(R.id.tv_carrer_id);//职场.心里
        lv_carrer_id = (ListView) view.findViewById(R.id.lv_carrer_id);//职场.心里

        ll_exam_id=(LinearLayout) view.findViewById(R.id.ll_exam_id);
        tv_exam_id = (TextView) view.findViewById(R.id.tv_exam_id);//考试.认证
        lv_exam_id = (ListView) view.findViewById(R.id.lv_exam_id);//考试.认证
    }

    /**
     * 添加监听器
     */
    private void aboutListener() {
        //编程监听器
        lv_program_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO
                String courseUrl=new String();
                String catalogId = (String) programData.get(i).get("catalogId");
                int isJob = (int) programData.get(i).get("isJob");
                if (isJob==1){
                    courseUrl="http://www.kgc.cn/list/230-1-6-9-9.shtml";
                }else{
                    courseUrl="http://www.kgc.cn/list/"+catalogId+"-1-6-9-9.shtml";
                }
                callCourseLeftUrlCallBack.gettCourseLeftUrl(courseUrl);
            }
        });
        //编程监听器
        lv_spread_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO
                String courseUrl=new String();
                String catalogId = (String) spreadData.get(i).get("catalogId");
                int isJob = (int) spreadData.get(i).get("isJob");
                if (isJob==1){
                    courseUrl="http://www.kgc.cn/list/230-1-6-9-9.shtml";
                }else{
                    courseUrl="http://www.kgc.cn/list/"+catalogId+"-1-6-9-9.shtml";
                }
                callCourseLeftUrlCallBack.gettCourseLeftUrl(courseUrl);
            }
        });
        //推广监听
        lv_seeing_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO
                String courseUrl=new String();
                String catalogId = (String) seeingData.get(i).get("catalogId");
                int isJob = (int) seeingData.get(i).get("isJob");
                if (isJob==1){
                    courseUrl="http://www.kgc.cn/list/230-1-6-9-9.shtml";
                }else{
                    courseUrl="http://www.kgc.cn/list/"+catalogId+"-1-6-9-9.shtml";
                }
                callCourseLeftUrlCallBack.gettCourseLeftUrl(courseUrl);
            }
        });
        //网络监听
        lv_net_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO
                String courseUrl=new String();
                String catalogId = (String) netData.get(i).get("catalogId");
                int isJob = (int) netData.get(i).get("isJob");
                if (isJob==1){
                    courseUrl="http://www.kgc.cn/list/230-1-6-9-9.shtml";
                }else{
                    courseUrl="http://www.kgc.cn/list/"+catalogId+"-1-6-9-9.shtml";
                }
                callCourseLeftUrlCallBack.gettCourseLeftUrl(courseUrl);
            }
        });
        //职场监听
        lv_carrer_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO
                String courseUrl=new String();
                String catalogId = (String) carrerData.get(i).get("catalogId");
                int isJob = (int) carrerData.get(i).get("isJob");
                if (isJob==1){
                    courseUrl="http://www.kgc.cn/list/230-1-6-9-9.shtml";
                }else{
                    courseUrl="http://www.kgc.cn/list/"+catalogId+"-1-6-9-9.shtml";
                }
                callCourseLeftUrlCallBack.gettCourseLeftUrl(courseUrl);
            }
        });
        //考试监听
        lv_exam_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO
                String courseUrl=new String();
                String catalogId = (String) examData.get(i).get("catalogId");
                int isJob = (int) examData.get(i).get("isJob");
                if (isJob==1){
                    courseUrl="http://www.kgc.cn/list/230-1-6-9-9.shtml";
                }else{
                    courseUrl="http://www.kgc.cn/list/"+catalogId+"-1-6-9-9.shtml";
                }
                callCourseLeftUrlCallBack.gettCourseLeftUrl(courseUrl);
            }
        });
    }



    /**
     * 接口回调文件名
     *
     * @param fileName
     */
    @Override
    public void getFileName(String fileName) {

        fillDataSource();
        programAdapter.notifyDataSetChanged();
        spreadAdapter.notifyDataSetChanged();
        seeingAdapter.notifyDataSetChanged();
        netAdapter.notifyDataSetChanged();
        carrerAdapter.notifyDataSetChanged();
        examAdapter.notifyDataSetChanged();

    }
}
