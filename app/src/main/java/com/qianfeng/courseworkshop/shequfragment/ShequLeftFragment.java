package com.qianfeng.courseworkshop.shequfragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.qianfeng.courseworkshop.R;
import com.qianfeng.courseworkshop.WebViewActivity;
import com.qianfeng.courseworkshop.asynctask.CommonAsyncTask;
import com.qianfeng.courseworkshop.asynctask.ImmageAsyncTask;
import com.qianfeng.courseworkshop.bean.CommonData;
import com.qianfeng.courseworkshop.bean.CourseLogcat;
import com.qianfeng.courseworkshop.bean.Shequlogcat;
import com.qianfeng.courseworkshop.inner.GetCourseLeftUrlCallBack;
import com.qianfeng.courseworkshop.inner.GetFileNameCallBack;
import com.qianfeng.courseworkshop.util.JsonAnalyze;
import com.qianfeng.courseworkshop.util.JsonShequ;

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
    private List<Shequlogcat> shequLogcats = new LinkedList<>();

    private Button btn_1f_id;
    private Button btn_2f_id;
    private Button btn_3f_id;
    private Button btn_4f_id;
    private Button btn_5f_id;
    private Button btn_6f_id;
    private Button btn_7f_id;
    private Button btn_8f_id;

    private int xingInt=1;
    private int resourceInt=1;
    private int uiInt=1;
    private int webInt=1;
    private int bigInt=1;
    private int yunInt=1;
    private int reInt=1;
    private int otherInt=1;


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

        //添加监听器
        aboutButtonAction();


        return view;
    }

    public void aboutButtonAction() {
        btn_1f_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lv_xing_id.getVisibility()==View.GONE){
                    lv_xing_id.setVisibility(View.VISIBLE);
                }else{
                    lv_xing_id.setVisibility(View.GONE);
                }
                if (xingInt==1) {
                    Drawable drawable= getResources().getDrawable(R.mipmap.close_ep);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btn_1f_id.setCompoundDrawables(drawable,null,null,null);
                    xingInt=0;
                }else{
                    Drawable drawable= getResources().getDrawable(R.mipmap.open_ep);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btn_1f_id.setCompoundDrawables(drawable,null,null,null);
                    xingInt=1;
                }
            }
        });
        btn_2f_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lv_resource_id.getVisibility()==View.GONE){
                    lv_resource_id.setVisibility(View.VISIBLE);
                }else{
                    lv_resource_id.setVisibility(View.GONE);
                }
                if (resourceInt==1) {
                    Drawable drawable= getResources().getDrawable(R.mipmap.close_ep);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btn_2f_id.setCompoundDrawables(drawable,null,null,null);
                    resourceInt=0;
                }else{
                    Drawable drawable= getResources().getDrawable(R.mipmap.open_ep);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btn_2f_id.setCompoundDrawables(drawable,null,null,null);
                    resourceInt=1;
                }
            }
        });
        btn_3f_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lv_ui_id.getVisibility()==View.GONE){
                    lv_ui_id.setVisibility(View.VISIBLE);
                }else{
                    lv_ui_id.setVisibility(View.GONE);
                }
                if (uiInt==1) {
                    Drawable drawable= getResources().getDrawable(R.mipmap.close_ep);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btn_3f_id.setCompoundDrawables(drawable,null,null,null);
                    uiInt=0;
                }else{
                    Drawable drawable= getResources().getDrawable(R.mipmap.open_ep);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btn_3f_id.setCompoundDrawables(drawable,null,null,null);
                    uiInt=1;
                }
            }
        });
        btn_4f_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lv_web_id.getVisibility()==View.GONE){
                    lv_web_id.setVisibility(View.VISIBLE);
                }else{
                    lv_web_id.setVisibility(View.GONE);
                }
                if (webInt==1) {
                    Drawable drawable= getResources().getDrawable(R.mipmap.close_ep);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btn_4f_id.setCompoundDrawables(drawable,null,null,null);
                    webInt=0;
                }else{
                    Drawable drawable= getResources().getDrawable(R.mipmap.open_ep);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btn_4f_id.setCompoundDrawables(drawable,null,null,null);
                    webInt=1;
                }
            }
        });
        btn_5f_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lv_big_id.getVisibility()==View.GONE){
                    lv_big_id.setVisibility(View.VISIBLE);
                }else{
                    lv_big_id.setVisibility(View.GONE);
                }
                if (bigInt==1) {
                    Drawable drawable= getResources().getDrawable(R.mipmap.close_ep);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btn_5f_id.setCompoundDrawables(drawable,null,null,null);
                    bigInt=0;
                }else{
                    Drawable drawable= getResources().getDrawable(R.mipmap.open_ep);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btn_5f_id.setCompoundDrawables(drawable,null,null,null);
                    bigInt=1;
                }
            }
        });
        btn_6f_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lv_yun_id.getVisibility()==View.GONE){
                    lv_yun_id.setVisibility(View.VISIBLE);
                }else{
                    lv_yun_id.setVisibility(View.GONE);
                }
                if (yunInt==1) {
                    Drawable drawable= getResources().getDrawable(R.mipmap.close_ep);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btn_6f_id.setCompoundDrawables(drawable,null,null,null);
                    yunInt=0;
                }else{
                    Drawable drawable= getResources().getDrawable(R.mipmap.open_ep);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btn_6f_id.setCompoundDrawables(drawable,null,null,null);
                    yunInt=1;
                }
            }
        });
        btn_7f_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lv_re_id.getVisibility()==View.GONE){
                    lv_re_id.setVisibility(View.VISIBLE);
                }else{
                    lv_re_id.setVisibility(View.GONE);
                }
                if (reInt==1) {
                    Drawable drawable= getResources().getDrawable(R.mipmap.close_ep);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btn_7f_id.setCompoundDrawables(drawable,null,null,null);
                    reInt=0;
                }else{
                    Drawable drawable= getResources().getDrawable(R.mipmap.open_ep);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btn_7f_id.setCompoundDrawables(drawable,null,null,null);
                    reInt=1;
                }
            }
        });
        btn_8f_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lv_other_id.getVisibility()==View.GONE){
                    lv_other_id.setVisibility(View.VISIBLE);
                }else{
                    lv_other_id.setVisibility(View.GONE);
                }
                if (otherInt==1) {
                    Drawable drawable= getResources().getDrawable(R.mipmap.close_ep);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btn_8f_id.setCompoundDrawables(drawable,null,null,null);
                    otherInt=0;
                }else{
                    Drawable drawable= getResources().getDrawable(R.mipmap.open_ep);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btn_8f_id.setCompoundDrawables(drawable,null,null,null);
                    otherInt=1;
                }
            }
        });

    }


    /**
     * 绑定适配器
     */
    private void bindAdapter() {
        lv_xing_id.setAdapter(xingAdapter);
        lv_resource_id.setAdapter(resourcrAdapter);
        lv_ui_id.setAdapter(uiAdapter);
        lv_web_id.setAdapter(webAdapter);
        lv_big_id.setAdapter(bigAdapter);
        lv_yun_id.setAdapter(yunAdapter);
        lv_re_id.setAdapter(reAdapter);
        lv_other_id.setAdapter(otherAdapter);


    }

    /**
     * 适配器
     */
    private void aboutAdapter() {
        //1f
        xingAdapter=new SimpleAdapter(activity, xingData, R.layout.course_logcat_item, new String[]{"name", "img", "num"}, new int[]{R.id.tv_title_id, R.id.iv_id, R.id.tv_num_id});
        //图片的处理
        xingAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {

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
        //2f
        resourcrAdapter=new SimpleAdapter(activity, resourceData, R.layout.course_logcat_item, new String[]{"name", "img", "num"}, new int[]{R.id.tv_title_id, R.id.iv_id, R.id.tv_num_id});
        //图片的处理
        resourcrAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {

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
        //3f
        uiAdapter=new SimpleAdapter(activity, uiData, R.layout.course_logcat_item, new String[]{"name", "img", "num"}, new int[]{R.id.tv_title_id, R.id.iv_id, R.id.tv_num_id});
        //图片的处理
        uiAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {

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
        //4f
        webAdapter=new SimpleAdapter(activity, webData, R.layout.course_logcat_item, new String[]{"name", "img", "num"}, new int[]{R.id.tv_title_id, R.id.iv_id, R.id.tv_num_id});
        //图片的处理
        webAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {

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
        //5f
        bigAdapter=new SimpleAdapter(activity, bigData, R.layout.course_logcat_item, new String[]{"name", "img", "num"}, new int[]{R.id.tv_title_id, R.id.iv_id, R.id.tv_num_id});
        //图片的处理
        bigAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {

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
        //6f
        yunAdapter=new SimpleAdapter(activity, yunData, R.layout.course_logcat_item, new String[]{"name", "img", "num"}, new int[]{R.id.tv_title_id, R.id.iv_id, R.id.tv_num_id});
        //图片的处理
        yunAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {

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
        //7f
        reAdapter=new SimpleAdapter(activity, reData, R.layout.course_logcat_item, new String[]{"name", "img", "num"}, new int[]{R.id.tv_title_id, R.id.iv_id, R.id.tv_num_id});
        //图片的处理
        reAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {

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
        //8f
        otherAdapter=new SimpleAdapter(activity, otherData, R.layout.course_logcat_item, new String[]{"name", "img", "num"}, new int[]{R.id.tv_title_id, R.id.iv_id, R.id.tv_num_id});
        //图片的处理
        otherAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {

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
        shequLogcats = JsonShequ.shequBean(file);

        if (shequLogcats.size() > 0) {
            //准备每类数据源
            //1f
            List<Shequlogcat.ListBean.ChildBean> xingChilds= shequLogcats.get(0).getList().get(0).getChild();

            for (int i = 0; i < xingChilds.size(); i++) {
                Shequlogcat.ListBean.ChildBean child = xingChilds.get(i);

                Map<String, Object> listem = new HashMap<String, Object>();
                String imgUrlStr = child.getImg();
                try {

                    ImmageAsyncTask asyncTask = new ImmageAsyncTask(activity);
                    asyncTask.execute(imgUrlStr);
                    Bitmap bitmap = asyncTask.get();
                    listem.put("img", bitmap);
                    listem.put("name", child.getName());
                    listem.put("num", "/"+child.getNewPost());
                    listem.put("url", child.getId());

                    xingData.add(listem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //2f
            List<Shequlogcat.ListBean.ChildBean> resourceChilds= shequLogcats.get(0).getList().get(1).getChild();

            for (int i = 0; i < resourceChilds.size(); i++) {
                Shequlogcat.ListBean.ChildBean child = resourceChilds.get(i);

                Map<String, Object> listem = new HashMap<String, Object>();
                String imgUrlStr = child.getImg();
                try {

                    ImmageAsyncTask asyncTask = new ImmageAsyncTask(activity);
                    asyncTask.execute(imgUrlStr);
                    Bitmap bitmap = asyncTask.get();
                    listem.put("img", bitmap);
                    listem.put("name", child.getName());
                    listem.put("num", "/"+child.getNewPost());
                    listem.put("url", child.getId());

                    resourceData.add(listem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //3f
            List<Shequlogcat.ListBean.ChildBean> uiChilds= shequLogcats.get(0).getList().get(2).getChild();

            for (int i = 0; i < uiChilds.size(); i++) {
                Shequlogcat.ListBean.ChildBean child = uiChilds.get(i);

                Map<String, Object> listem = new HashMap<String, Object>();
                String imgUrlStr = child.getImg();
                try {

                    ImmageAsyncTask asyncTask = new ImmageAsyncTask(activity);
                    asyncTask.execute(imgUrlStr);
                    Bitmap bitmap = asyncTask.get();
                    listem.put("img", bitmap);
                    listem.put("name", child.getName());
                    listem.put("num", "/"+child.getNewPost());
                    listem.put("url", child.getId());

                    uiData.add(listem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //4f
            List<Shequlogcat.ListBean.ChildBean> webChilds= shequLogcats.get(0).getList().get(3).getChild();

            for (int i = 0; i < webChilds.size(); i++) {
                Shequlogcat.ListBean.ChildBean child = webChilds.get(i);

                Map<String, Object> listem = new HashMap<String, Object>();
                String imgUrlStr = child.getImg();
                try {

                    ImmageAsyncTask asyncTask = new ImmageAsyncTask(activity);
                    asyncTask.execute(imgUrlStr);
                    Bitmap bitmap = asyncTask.get();
                    listem.put("img", bitmap);
                    listem.put("name", child.getName());
                    listem.put("num", "/"+child.getNewPost());
                    listem.put("url", child.getId());

                    webData.add(listem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //5f
            List<Shequlogcat.ListBean.ChildBean> bigChilds= shequLogcats.get(0).getList().get(4).getChild();

            for (int i = 0; i < bigChilds.size(); i++) {
                Shequlogcat.ListBean.ChildBean child = bigChilds.get(i);

                Map<String, Object> listem = new HashMap<String, Object>();
                String imgUrlStr = child.getImg();
                try {

                    ImmageAsyncTask asyncTask = new ImmageAsyncTask(activity);
                    asyncTask.execute(imgUrlStr);
                    Bitmap bitmap = asyncTask.get();
                    listem.put("img", bitmap);
                    listem.put("name", child.getName());
                    listem.put("num", "/"+child.getNewPost());
                    listem.put("url", child.getId());

                    bigData.add(listem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //6f
            List<Shequlogcat.ListBean.ChildBean> yunChilds= shequLogcats.get(0).getList().get(5).getChild();

            for (int i = 0; i < yunChilds.size(); i++) {
                Shequlogcat.ListBean.ChildBean child = yunChilds.get(i);

                Map<String, Object> listem = new HashMap<String, Object>();
                String imgUrlStr = child.getImg();
                try {

                    ImmageAsyncTask asyncTask = new ImmageAsyncTask(activity);
                    asyncTask.execute(imgUrlStr);
                    Bitmap bitmap = asyncTask.get();
                    listem.put("img", bitmap);
                    listem.put("name", child.getName());
                    listem.put("num", "/"+child.getNewPost());
                    listem.put("url", child.getId());

                    yunData.add(listem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //7f
            List<Shequlogcat.ListBean.ChildBean> reChilds= shequLogcats.get(0).getList().get(6).getChild();

            for (int i = 0; i < reChilds.size(); i++) {
                Shequlogcat.ListBean.ChildBean child = reChilds.get(i);

                Map<String, Object> listem = new HashMap<String, Object>();
                String imgUrlStr = child.getImg();
                try {

                    ImmageAsyncTask asyncTask = new ImmageAsyncTask(activity);
                    asyncTask.execute(imgUrlStr);
                    Bitmap bitmap = asyncTask.get();
                    listem.put("img", bitmap);
                    listem.put("name", child.getName());
                    listem.put("num", "/"+child.getNewPost());
                    listem.put("url", child.getId());

                    reData.add(listem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //8f
            List<Shequlogcat.ListBean.ChildBean> otherChilds= shequLogcats.get(0).getList().get(7).getChild();

            for (int i = 0; i < otherChilds.size(); i++) {
                Shequlogcat.ListBean.ChildBean child = otherChilds.get(i);

                Map<String, Object> listem = new HashMap<String, Object>();
                String imgUrlStr = child.getImg();
                try {

                    ImmageAsyncTask asyncTask = new ImmageAsyncTask(activity);
                    asyncTask.execute(imgUrlStr);
                    Bitmap bitmap = asyncTask.get();
                    listem.put("img", bitmap);
                    listem.put("name", child.getName());
                    listem.put("num", "/"+child.getNewPost());
                    listem.put("url", child.getId());

                    otherData.add(listem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        //1f
        lv_xing_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO
                String courseUrl=new String();
                String catalogId = (String) xingData.get(i).get("url");

                courseUrl="http://www.kgc.cn/bbs/list/"+catalogId+".shtml";
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra("100",courseUrl);
                startActivity(intent);
            }
        });
        //2f
        lv_resource_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO
                String courseUrl=new String();
                String catalogId = (String) resourceData.get(i).get("url");

                courseUrl="http://www.kgc.cn/bbs/list/"+catalogId+".shtml";
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra("100",courseUrl);
                startActivity(intent);
            }
        });
        //3f
        lv_ui_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO
                String courseUrl=new String();
                String catalogId = (String) uiData.get(i).get("url");

                courseUrl="http://www.kgc.cn/bbs/list/"+catalogId+".shtml";
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra("100",courseUrl);
                startActivity(intent);
            }
        });
        //4f
        lv_web_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO
                String courseUrl=new String();
                String catalogId = (String) webData.get(i).get("url");

                courseUrl="http://www.kgc.cn/bbs/list/"+catalogId+".shtml";
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra("100",courseUrl);
                startActivity(intent);
            }
        });
        //5f
        lv_big_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO
                String courseUrl=new String();
                String catalogId = (String) bigData.get(i).get("url");

                courseUrl="http://www.kgc.cn/bbs/list/"+catalogId+".shtml";
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra("100",courseUrl);
                startActivity(intent);
            }
        });
        //6f
        lv_yun_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO
                String courseUrl=new String();
                String catalogId = (String) yunData.get(i).get("url");

                courseUrl="http://www.kgc.cn/bbs/list/"+catalogId+".shtml";
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra("100",courseUrl);
                startActivity(intent);
            }
        });
        //7f
        lv_re_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO
                String courseUrl=new String();
                String catalogId = (String) reData.get(i).get("url");

                courseUrl="http://www.kgc.cn/bbs/list/"+catalogId+".shtml";
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra("100",courseUrl);
                startActivity(intent);
            }
        });
        //8f
        lv_other_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO
                String courseUrl=new String();
                String catalogId = (String) otherData.get(i).get("url");

                courseUrl="http://www.kgc.cn/bbs/list/"+catalogId+".shtml";
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra("100",courseUrl);
                startActivity(intent);
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
        xingAdapter.notifyDataSetChanged();
        resourcrAdapter.notifyDataSetChanged();
        uiAdapter.notifyDataSetChanged();
        webAdapter.notifyDataSetChanged();
        bigAdapter.notifyDataSetChanged();
        yunAdapter.notifyDataSetChanged();
        reAdapter.notifyDataSetChanged();
        otherAdapter.notifyDataSetChanged();


    }
}
