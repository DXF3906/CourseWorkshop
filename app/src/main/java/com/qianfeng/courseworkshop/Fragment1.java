package com.qianfeng.courseworkshop;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Fragment1 extends Fragment {
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
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


        //③设置适配器
        PagerAdapter adapter = new MyAdapter();
        vp_main_fragment_id.setAdapter(adapter);

        vp_main_fragment_id.setCurrentItem(1);

        vp_main_fragment_id.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mIsChanged = true;
                if (i > 3) {
                    mCurrentPagePosition = 1;
                } else if (i < 1) {
                    mCurrentPagePosition = 3;
                } else {
                    mCurrentPagePosition = i;
                }

                vp_main_fragment_id.setCurrentItem(mCurrentPagePosition);
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

            @Override
            public void onPageScrollStateChanged(int i) {
                if (ViewPager.SCROLL_STATE_IDLE == i) {
                    if (mIsChanged) {
                        mIsChanged = false;
                        vp_main_fragment_id.setCurrentItem(mCurrentPagePosition, false);
                    }
                }
            }


        });
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).morefragment();
            }
        };
        iv_button_index_id.setOnClickListener(listener);
        iv_button_index_id2.setOnClickListener(listener);
        iv_button_index_id3.setOnClickListener(listener);
        iv_button_index_id4.setOnClickListener(listener);
        tv_icon_edit_id.setOnClickListener(listener);
        tv_icon_edit_id2.setOnClickListener(listener);
        tv_icon_edit_id3.setOnClickListener(listener);


        return view;
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


}
