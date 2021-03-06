package com.qianfeng.courseworkshop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.qianfeng.courseworkshop.Adapter.MyAdapter;
import com.qianfeng.courseworkshop.activity.SearchActivity;
import com.qianfeng.courseworkshop.activity.SearchView;
import com.qianfeng.courseworkshop.asynctask.CommonAsyncTask;
import com.qianfeng.courseworkshop.asynctask.ImmageAsyncTask;
import com.qianfeng.courseworkshop.bean.MainBeen;
import com.qianfeng.courseworkshop.inner.GetFileNameCallBack;
import com.qianfeng.courseworkshop.util.JsonMain;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Fragment1 extends Fragment implements GetFileNameCallBack {
    private FragmentActivity activity;
    private ViewPager vp_main_fragment_id;
    private ImageView iv_main_dot_id;
    private ImageView iv_main_dot2_id;
    private ImageView iv_main_dot3_id;
    private LinkedList<View> ds;
    private int count;
    private boolean mIsChanged = false;
    private int mCurrentPagePosition = 1;
    private ScheduledExecutorService scheduledExecutorService;
    private ImageView iv_button_index_id;
    private ImageView iv_button_index_id2;
    private ImageView iv_button_index_id3;
    private ImageView iv_button_index_id4;
    private TextView tv_icon_edit_id;
    private TextView tv_icon_edit_id2;
    private TextView tv_icon_edit_id3;
    private List<MainBeen> courses;
    @ViewInject(R.id.iv_main_1_id)
    private ImageView img1;
    @ViewInject(R.id.iv_main_2_id)
    private ImageView img2;
    @ViewInject(R.id.iv_main_3_id)
    private ImageView img3;
    @ViewInject(R.id.iv_main_4_id)
    private ImageView img4;
    @ViewInject(R.id.iv_main_5_id)
    private ImageView img5;
    @ViewInject(R.id.iv_main_6_id)
    private ImageView img6;
    @ViewInject(R.id.iv_main_7_id)
    private ImageView img7;
    @ViewInject(R.id.iv_main_8_id)
    private ImageView img8;
    @ViewInject(R.id.iv_main_9_id)
    private ImageView img9;
    @ViewInject(R.id.iv_main_10_id)
    private ImageView img10;
    @ViewInject(R.id.iv_main_11_id)
    private ImageView img11;
    @ViewInject(R.id.iv_main_12_id)
    private ImageView img12;
    @ViewInject(R.id.tv_main_textView1_1)
    private TextView tv_main_textView1_1;
    @ViewInject(R.id.tv_main_textView1_2)
    private TextView tv_main_textView1_2;
    @ViewInject(R.id.tv_main_textView1_3)
    private TextView tv_main_textView1_3;
    @ViewInject(R.id.tv_main_textView2_1)
    private TextView tv_main_textView2_1;
    @ViewInject(R.id.tv_main_textView2_2)
    private TextView tv_main_textView2_2;
    @ViewInject(R.id.tv_main_textView2_3)
    private TextView tv_main_textView2_3;
    @ViewInject(R.id.tv_main_textView3_1)
    private TextView tv_main_textView3_1;
    @ViewInject(R.id.tv_main_textView3_2)
    private TextView tv_main_textView3_2;
    @ViewInject(R.id.tv_main_textView3_3)
    private TextView tv_main_textView3_3;
    @ViewInject(R.id.tv_main_textView4_1)
    private TextView tv_main_textView4_1;
    @ViewInject(R.id.tv_main_textView4_2)
    private TextView tv_main_textView4_2;
    @ViewInject(R.id.tv_main_textView4_3)
    private TextView tv_main_textView4_3;
    @ViewInject(R.id.tv_main_textView5_1)
    private TextView tv_main_textView5_1;
    @ViewInject(R.id.tv_main_textView5_2)
    private TextView tv_main_textView5_2;
    @ViewInject(R.id.tv_main_textView5_3)
    private TextView tv_main_textView5_3;
    @ViewInject(R.id.tv_main_textView6_1)
    private TextView tv_main_textView6_1;
    @ViewInject(R.id.tv_main_textView6_2)
    private TextView tv_main_textView6_2;
    @ViewInject(R.id.tv_main_textView6_3)
    private TextView tv_main_textView6_3;
    @ViewInject(R.id.tv_main_textView7_1)
    private TextView tv_main_textView7_1;
    @ViewInject(R.id.tv_main_textView7_2)
    private TextView tv_main_textView7_2;
    @ViewInject(R.id.tv_main_textView7_3)
    private TextView tv_main_textView7_3;
    @ViewInject(R.id.tv_main_textView8_1)
    private TextView tv_main_textView8_1;
    @ViewInject(R.id.tv_main_textView8_2)
    private TextView tv_main_textView8_2;
    @ViewInject(R.id.tv_main_textView8_3)
    private TextView tv_main_textView8_3;
    @ViewInject(R.id.tv_main_textView9_1)
    private TextView tv_main_textView9_1;
    @ViewInject(R.id.tv_main_textView9_2)
    private TextView tv_main_textView9_2;
    @ViewInject(R.id.tv_main_textView9_3)
    private TextView tv_main_textView9_3;
    @ViewInject(R.id.tv_main_textView10_1)
    private TextView tv_main_textView10_1;
    @ViewInject(R.id.tv_main_textView10_2)
    private TextView tv_main_textView10_2;
    @ViewInject(R.id.tv_main_textView10_3)
    private TextView tv_main_textView10_3;
    @ViewInject(R.id.tv_main_textView11_1)
    private TextView tv_main_textView11_1;
    @ViewInject(R.id.tv_main_textView11_2)
    private TextView tv_main_textView11_2;
    @ViewInject(R.id.tv_main_textView11_3)
    private TextView tv_main_textView11_3;
    @ViewInject(R.id.tv_main_textView12_1)
    private TextView tv_main_textView12_1;
    @ViewInject(R.id.tv_main_textView12_2)
    private TextView tv_main_textView12_2;
    @ViewInject(R.id.tv_main_textView12_3)
    private TextView tv_main_textView12_3;
    @ViewInject(R.id.iv_main_search_id)
    private ImageView iv_main_search_id;
    @ViewInject(R.id.ptrsv_main_id)
    private PullToRefreshScrollView ptrsv_main_id;
    private CommonAsyncTask asyncTask;
    private static final String PICASSO_CACHE = "picasso-cache";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.iv_main_1_id, R.id.iv_main_2_id, R.id.iv_main_3_id, R.id.iv_main_4_id, R.id.iv_main_5_id, R.id.iv_main_6_id, R.id.iv_main_7_id, R.id.iv_main_8_id, R.id.iv_main_9_id, R.id.iv_main_10_id, R.id.iv_main_11_id, R.id.iv_main_12_id})
    public void clickMethod(View v) {
//        http://www.kgc.cn/course/16028.shtml

        String id = null;
        Intent intent = new Intent();
        intent.setClass(getActivity(), WebViewActivity.class);
        switch (v.getId()) {
            case R.id.iv_main_1_id:
                id = courses.get(0).getInfo().getNewX().get(0).getId();
                break;
            case R.id.iv_main_2_id:
                id = courses.get(0).getInfo().getNewX().get(1).getId();
                break;
            case R.id.iv_main_3_id:
                id = courses.get(0).getInfo().getNewX().get(2).getId();
                break;
            case R.id.iv_main_4_id:
                id = courses.get(0).getInfo().getNewX().get(3).getId();
                break;
            case R.id.iv_main_5_id:
                id = courses.get(0).getInfo().getHot().get(0).getId();
                break;
            case R.id.iv_main_6_id:
                id = courses.get(0).getInfo().getHot().get(1).getId();
                break;
            case R.id.iv_main_7_id:
                id = courses.get(0).getInfo().getHot().get(2).getId();
                break;
            case R.id.iv_main_8_id:
                id = courses.get(0).getInfo().getHot().get(3).getId();
                break;
            case R.id.iv_main_9_id:
                id = courses.get(0).getInfo().getFree().get(0).getId();
                break;
            case R.id.iv_main_10_id:
                id = courses.get(0).getInfo().getFree().get(1).getId();
                break;
            case R.id.iv_main_11_id:
                id = courses.get(0).getInfo().getFree().get(2).getId();
                break;
            case R.id.iv_main_12_id:
                id = courses.get(0).getInfo().getFree().get(3).getId();
                break;

        }
        intent.putExtra("100", "http://www.kgc.cn/course/" + id + ".shtml");

        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, null);
        ViewUtils.inject(this, view);

        vp_main_fragment_id = (ViewPager) view.findViewById(R.id.vp_main_fragment_id);
        iv_main_dot_id = (ImageView) view.findViewById(R.id.iv_main_dot_id);
        iv_main_dot2_id = (ImageView) view.findViewById(R.id.iv_main_dot2_id);
        iv_main_dot3_id = (ImageView) view.findViewById(R.id.iv_main_dot3_id);
        iv_button_index_id = (ImageView) view.findViewById(R.id.iv_button_index_id);
        iv_button_index_id2 = (ImageView) view.findViewById(R.id.iv_button_index_id2);
        iv_button_index_id3 = (ImageView) view.findViewById(R.id.iv_button_index_id3);
        iv_button_index_id4 = (ImageView) view.findViewById(R.id.iv_button_index_id4);
        tv_icon_edit_id = (TextView) view.findViewById(R.id.tv_icon_edit_id);
        tv_icon_edit_id2 = (TextView) view.findViewById(R.id.tv_icon_edit_id2);
        tv_icon_edit_id3 = (TextView) view.findViewById(R.id.tv_icon_edit_id3);


        ds = new LinkedList<>();
        int[] imageIds = {R.mipmap.shili3, R.mipmap.shili, R.mipmap.shili2, R.mipmap.shili3, R.mipmap.shili};
        for (int imageId : imageIds) {
            ImageView iv = new ImageView(getActivity());
            iv.setImageResource(imageId);
            ds.add(iv);
        }
        Picasso.with(getActivity()).load("http://assets.kgc.cn/upload/ad/20160606/1465195545571261.jpg").into((ImageView) ds.get(0));
        Picasso.with(getActivity()).load("http://assets.kgc.cn/upload/ad/20160623/1466665711913388.jpg").into((ImageView) ds.get(1));
        Picasso.with(getActivity()).load("http://assets.kgc.cn/upload/ad/20160517/1463450580414471.jpg").into((ImageView) ds.get(2));
        Picasso.with(getActivity()).load("http://assets.kgc.cn/upload/ad/20160606/1465195545571261.jpg").into((ImageView) ds.get(3));
        Picasso.with(getActivity()).load("http://assets.kgc.cn/upload/ad/20160623/1466665711913388.jpg").into((ImageView) ds.get(4));

        //③设置适配器
        PagerAdapter adapter = new MyAdapter();
        vp_main_fragment_id.setAdapter(adapter);

        vp_main_fragment_id.setCurrentItem(1);

        vp_main_fragment_id.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int i) {
                //循环效果
                if (i == ViewPager.SCROLL_STATE_IDLE) {
                    if (mCurrentPagePosition == vp_main_fragment_id.getAdapter().getCount() - 1) {
                        //false:表示无动画
                        vp_main_fragment_id.setCurrentItem(1, false);
                    } else if (mCurrentPagePosition == 0) {
                        vp_main_fragment_id.setCurrentItem(vp_main_fragment_id.getAdapter().getCount() - 2, false);
                    }
                }
            }

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(final int i) {
                mCurrentPagePosition = i;

                //广告监听器
                ds.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), WebViewActivity.class);
                        if (i == 1) {
                            intent.putExtra("100", "http://www.kgc.cn/course/16028.shtml");
                        }
                        if (i == 2) {
                            intent.putExtra("100", "http://www.kgc.cn/course/17801.shtml");
                        }
                        if (i == 3) {
                            intent.putExtra("100", "http://www.kgc.cn/course/17802.shtml");
                        }
                        startActivity(intent);
                    }
                });
                iv_main_dot_id.setEnabled(true);
                iv_main_dot2_id.setEnabled(true);
                iv_main_dot3_id.setEnabled(true);
                if (i == 3) {
                    iv_main_dot_id.setEnabled(false);
                }
                if (i == 0) {
                    iv_main_dot_id.setEnabled(false);
                }
                if (i == 2) {
                    iv_main_dot2_id.setEnabled(false);
                }
                if (i == 1) {
                    iv_main_dot3_id.setEnabled(false);

                }
                if (i == 4) {
                    iv_main_dot3_id.setEnabled(false);

                }
            }


        });
//
        //回调morefragment()方法，跳转界面
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.iv_button_index_id:
                        ((MainActivity) getActivity()).morefragment(courses.get(0).getCategoryImg().get(0).getCategoryName());
                        break;
                    case R.id.iv_button_index_id2:
                        ((MainActivity) getActivity()).morefragment(courses.get(0).getCategoryImg().get(1).getCategoryName());
                        break;
                    case R.id.iv_button_index_id3:
                        ((MainActivity) getActivity()).morefragment(courses.get(0).getCategoryImg().get(2).getCategoryName());
                        break;
                    case R.id.iv_button_index_id4:
                        ((MainActivity) getActivity()).morefragment(courses.get(0).getCategoryImg().get(3).getCategoryName());
                        break;
                    case R.id.tv_icon_edit_id:
                        ((MainActivity) getActivity()).morefragment("最新课程");
                        break;
                    case R.id.tv_icon_edit_id2:
                        ((MainActivity) getActivity()).morefragment("热门课程");
                        break;
                    case R.id.tv_icon_edit_id3:
                        ((MainActivity) getActivity()).morefragment("免费课程");
                        break;
                }

            }
        };
        iv_button_index_id.setOnClickListener(listener);
        iv_button_index_id2.setOnClickListener(listener);
        iv_button_index_id3.setOnClickListener(listener);
        iv_button_index_id4.setOnClickListener(listener);
        tv_icon_edit_id.setOnClickListener(listener);
        tv_icon_edit_id2.setOnClickListener(listener);
        tv_icon_edit_id3.setOnClickListener(listener);
        iv_main_search_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        asyncTask = new CommonAsyncTask(this, "main");
        asyncTask.execute("http://api.kgc.cn/services/youke?free_num=4&hot_num=4&mechanism=kgc&method=homepage&new_num=4&osType=android&osVersion=3.9.1&showJob=true&auth=3afd110774603f7121276fd4fb9fd706");

        if (asyncTask.isCancelled()) {

            fillDataSouce();
        }

        ptrsv_main_id.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(),
                        System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                //刷新当前的界面
                new GetDataTask().execute();
            }


        });

        super.onActivityCreated(savedInstanceState);
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> implements GetFileNameCallBack {

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

            asyncTask = new CommonAsyncTask(this, "main");
            asyncTask.execute("http://api.kgc.cn/services/youke?free_num=4&hot_num=4&mechanism=kgc&method=homepage&new_num=4&osType=android&osVersion=3.9.1&showJob=true&auth=3afd110774603f7121276fd4fb9fd706");

            if (asyncTask.isCancelled()) {

                fillDataSouce();
            }
            // Call onRefreshComplete when the list has been refreshed.
            ptrsv_main_id.onRefreshComplete();
            Picasso.with(getActivity()).load("http://assets.kgc.cn/upload/ad/20160606/1465195545571261.jpg").into((ImageView) ds.get(0));
            Picasso.with(getActivity()).load("http://assets.kgc.cn/upload/ad/20160623/1466665711913388.jpg").into((ImageView) ds.get(1));
            Picasso.with(getActivity()).load("http://assets.kgc.cn/upload/ad/20160517/1463450580414471.jpg").into((ImageView) ds.get(2));
            Picasso.with(getActivity()).load("http://assets.kgc.cn/upload/ad/20160606/1465195545571261.jpg").into((ImageView) ds.get(3));
            Picasso.with(getActivity()).load("http://assets.kgc.cn/upload/ad/20160623/1466665711913388.jpg").into((ImageView) ds.get(4));


            super.onPostExecute(result);
        }

        @Override
        public void getFileName(String fileName) {
            fillDataSouce();
        }
    }

    @Override
    public void onStart() {
        //用一个定时器  来完成图片切换
        //Timer 与 ScheduledExecutorService 实现定时器的效果

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        //通过定时器 来完成 每2秒钟切换一个图片
        //经过指定的时间后，执行所指定的任务
        //scheduleAtFixedRate(command, initialDelay, period, unit)
        //command 所要执行的任务
        //initialDelay 第一次启动时 延迟启动时间
        //period  每间隔多次时间来重新启动任务
        //unit 时间单位
        scheduledExecutorService.scheduleAtFixedRate(new ViewPagerTask(), 1, 3, TimeUnit.SECONDS);

        super.onStart();
    }

    @Override
    public void onStop() {
        //停止图片切换
        scheduledExecutorService.shutdown();

        super.onStop();
    }

    @Override
    public void getFileName(String fileName) {

        fillDataSouce();


    }


    //用来完成图片切换的任务
    private class ViewPagerTask implements Runnable {

        public void run() {
            //实现我们的操作
            //改变当前页面
            mCurrentPagePosition = (mCurrentPagePosition + 1) % ds.size();
            //Handler来实现图片切换
            handler.obtainMessage().sendToTarget();
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            //设定viewPager当前页面
            vp_main_fragment_id.setCurrentItem(mCurrentPagePosition);
        }
    };

    public class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return ds.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View iv = ds.get(position);

            container.addView(iv);// ？，此处容易忽略！不要忘了！作用：在ViewPager容器控件中添加上相应的视图。
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) ds.get(position));
        }
    }

    private void fillDataSouce() {
//        SystemClock.sleep(7000);
        File file = new File(Environment.getExternalStorageDirectory(), "main.html");


        courses = JsonMain.mainBeen(file);
        Log.i("课程1111", courses.toString());
        if (courses != null) {
            Picasso.with(getActivity()).load(courses.get(0).getInfo().getNewX().get(0).getPic()).into(img1);
            Picasso.with(getActivity()).load(courses.get(0).getInfo().getNewX().get(1).getPic()).into(img2);
            Picasso.with(getActivity()).load(courses.get(0).getInfo().getNewX().get(2).getPic()).into(img3);
            Picasso.with(getActivity()).load(courses.get(0).getInfo().getNewX().get(3).getPic()).into(img4);
            Picasso.with(getActivity()).load(courses.get(0).getInfo().getHot().get(0).getPic()).into(img5);
            Picasso.with(getActivity()).load(courses.get(0).getInfo().getHot().get(1).getPic()).into(img6);
            Picasso.with(getActivity()).load(courses.get(0).getInfo().getHot().get(2).getPic()).into(img7);
            Picasso.with(getActivity()).load(courses.get(0).getInfo().getHot().get(3).getPic()).into(img8);
            Picasso.with(getActivity()).load(courses.get(0).getInfo().getFree().get(0).getPic()).into(img9);
            Picasso.with(getActivity()).load(courses.get(0).getInfo().getFree().get(1).getPic()).into(img10);
            Picasso.with(getActivity()).load(courses.get(0).getInfo().getFree().get(2).getPic()).into(img11);
            Picasso.with(getActivity()).load(courses.get(0).getInfo().getFree().get(3).getPic()).into(img12);
            tv_main_textView1_1.setText(courses.get(0).getInfo().getNewX().get(0).getTitle());
            tv_main_textView1_2.setText(courses.get(0).getInfo().getNewX().get(0).getStuNums() + "");
            if ("0".equals(courses.get(0).getInfo().getNewX().get(0).getBeans())) {
                tv_main_textView1_3.setText("免费");
            } else {
                tv_main_textView1_3.setText(courses.get(0).getInfo().getNewX().get(0).getBeans() + "k币");
            }
            tv_main_textView2_1.setText(courses.get(0).getInfo().getNewX().get(1).getTitle());
            tv_main_textView2_2.setText(courses.get(0).getInfo().getNewX().get(1).getStuNums() + "");
            if ("0".equals(courses.get(0).getInfo().getNewX().get(1).getBeans())) {
                tv_main_textView2_3.setText("免费");
            } else {
                tv_main_textView2_3.setText(courses.get(0).getInfo().getNewX().get(1).getBeans() + "k币");
            }
            tv_main_textView3_1.setText(courses.get(0).getInfo().getNewX().get(2).getTitle());
            tv_main_textView3_2.setText(courses.get(0).getInfo().getNewX().get(2).getStuNums() + "");
            if ("0".equals(courses.get(0).getInfo().getNewX().get(2).getBeans())) {
                tv_main_textView3_3.setText("免费");
            } else {
                tv_main_textView3_3.setText(courses.get(0).getInfo().getNewX().get(2).getBeans() + "k币");
            }
            tv_main_textView4_1.setText(courses.get(0).getInfo().getNewX().get(3).getTitle());
            tv_main_textView4_2.setText(courses.get(0).getInfo().getNewX().get(3).getStuNums() + "");
            if ("0".equals(courses.get(0).getInfo().getNewX().get(3).getBeans())) {
                tv_main_textView4_3.setText("免费");
            } else {
                tv_main_textView4_3.setText(courses.get(0).getInfo().getNewX().get(3).getBeans() + "k币");
            }
            tv_main_textView5_1.setText(courses.get(0).getInfo().getHot().get(0).getTitle());
            tv_main_textView5_2.setText(courses.get(0).getInfo().getHot().get(0).getStuNums() + "");
            if ("0".equals(courses.get(0).getInfo().getHot().get(0).getBeans())) {
                tv_main_textView5_3.setText("免费");
            } else {
                tv_main_textView5_3.setText(courses.get(0).getInfo().getHot().get(0).getBeans() + "k币");
            }
            tv_main_textView6_1.setText(courses.get(0).getInfo().getHot().get(1).getTitle());
            tv_main_textView6_2.setText(courses.get(0).getInfo().getHot().get(1).getStuNums() + "");
            if ("0".equals(courses.get(0).getInfo().getHot().get(1).getBeans())) {
                tv_main_textView6_3.setText("免费");
            } else {
                tv_main_textView6_3.setText(courses.get(0).getInfo().getHot().get(1).getBeans() + "k币");
            }
            tv_main_textView7_1.setText(courses.get(0).getInfo().getHot().get(2).getTitle());
            tv_main_textView7_2.setText(courses.get(0).getInfo().getHot().get(2).getStuNums() + "");
            if ("0".equals(courses.get(0).getInfo().getHot().get(2).getBeans())) {
                tv_main_textView7_3.setText("免费");
            } else {
                tv_main_textView7_3.setText(courses.get(0).getInfo().getHot().get(2).getBeans() + "k币");
            }
            tv_main_textView8_1.setText(courses.get(0).getInfo().getHot().get(3).getTitle());
            tv_main_textView8_2.setText(courses.get(0).getInfo().getHot().get(3).getStuNums() + "");
            if ("0".equals(courses.get(0).getInfo().getHot().get(3).getBeans())) {
                tv_main_textView8_3.setText("免费");
            } else {
                tv_main_textView8_3.setText(courses.get(0).getInfo().getHot().get(3).getBeans() + "k币");
            }
            tv_main_textView9_1.setText(courses.get(0).getInfo().getFree().get(0).getTitle());
            tv_main_textView9_2.setText(courses.get(0).getInfo().getFree().get(0).getStuNums() + "");
            if ("0".equals(courses.get(0).getInfo().getFree().get(0).getBeans())) {
                tv_main_textView9_3.setText("免费");
            } else {
                tv_main_textView9_3.setText(courses.get(0).getInfo().getFree().get(0).getBeans() + "k币");
            }
            tv_main_textView10_1.setText(courses.get(0).getInfo().getFree().get(1).getTitle());
            tv_main_textView10_2.setText(courses.get(0).getInfo().getFree().get(1).getStuNums() + "");
            if ("0".equals(courses.get(0).getInfo().getFree().get(1).getBeans())) {
                tv_main_textView10_3.setText("免费");
            } else {
                tv_main_textView10_3.setText(courses.get(0).getInfo().getFree().get(1).getBeans() + "k币");
            }
            tv_main_textView11_1.setText(courses.get(0).getInfo().getFree().get(2).getTitle());
            tv_main_textView11_2.setText(courses.get(0).getInfo().getFree().get(2).getStuNums() + "");
            if ("0".equals(courses.get(0).getInfo().getFree().get(2).getBeans())) {
                tv_main_textView11_3.setText("免费");
            } else {
                tv_main_textView11_3.setText(courses.get(0).getInfo().getFree().get(2).getBeans() + "k币");
            }
            tv_main_textView12_1.setText(courses.get(0).getInfo().getFree().get(3).getTitle());
            tv_main_textView12_2.setText(courses.get(0).getInfo().getFree().get(3).getStuNums() + "");
            if ("0".equals(courses.get(0).getInfo().getFree().get(3).getBeans())) {
                tv_main_textView12_3.setText("免费");
            } else {
                tv_main_textView12_3.setText(courses.get(0).getInfo().getFree().get(3).getBeans() + "k币");
            }
        }
    }


}
