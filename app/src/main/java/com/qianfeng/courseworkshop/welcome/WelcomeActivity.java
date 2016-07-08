package com.qianfeng.courseworkshop.welcome;

import android.support.v4.view.PagerAdapter;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.qianfeng.courseworkshop.Adapter.MyAdapter;
import com.qianfeng.courseworkshop.MainActivity;
import com.qianfeng.courseworkshop.R;


import java.util.LinkedList;
import java.util.List;

/**
 * 欢迎界面
 */

public class WelcomeActivity extends AppCompatActivity {
    private ViewPager mVp;
    //private ViewPager mVp;
    private LinearLayout mLlcontainer;
    //@ViewInject(R.id.ll_container_id)
    //private LinearLayout mLlcontainer;
    private List<View> ds;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_welcome);
        //思路：
        //界面控件实例的获取
        mLlcontainer = (LinearLayout) findViewById(R.id.ll_container_id);
        mVp = (ViewPager) findViewById(R.id.vp_id);
        //ViewUtils.inject(this);
        //ViewPager决定小圆点随之联动
        aboutViewPager();
        //小圆点决定ViewPager页面的联动
        aboutLittleDots();
    }

    private void aboutLittleDots() {
        //思路
        //小圆点的个数由ViewPager的页数决定
        //通过循环，在父控件中添加小圆点对应的控件ImageView的实例
        View.OnClickListener myListener = new MyDotClickListener();

        for (int i = 0; i < ds.size(); i++) {
            //构建ImageView的实例
            ImageView iv = new ImageView(this);
            //设置ImageView的属性
            iv.setImageResource(R.drawable.dot_selector);
            iv.setEnabled(true);
            //添加监听器决定viewpager目前所选的界面
            iv.setTag(i);
            iv.setOnClickListener(myListener);
            //将控件设到父控件上
            mLlcontainer.addView(iv);
        }
        mLlcontainer.getChildAt(0).setEnabled(false);
    }

    /**
     * OnClickListener自定义实现类
     */
    private final class MyDotClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            //决定ViewPager随之联动
            mVp.setCurrentItem((Integer) (view.getTag()));
        }
    }

    private void aboutViewPager() {
        //思路
        //数据源
        ds = new LinkedList<>();
        fillDataSource();
        //适配器
        PagerAdapter adapter = new MyAdapter(ds, mVp);
        //设置适配器
        mVp.setAdapter(adapter);
        //监听器
        mVp.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //ViewPager决定小圆点的状态
                //思路：
                //①从LinearLayout中找子控件，让所有的小圆点都可以被点击

                for (int i = 0; i < mLlcontainer.getChildCount(); i++) {
                    //① 获得当前的子控件实例
                    View view = mLlcontainer.getChildAt(i);

                    //②设置View的属性
                    view.setEnabled(true);
                }

                //②让当前位置的小圆点不可点击
                if (position < mLlcontainer.getChildCount()) {
                    mLlcontainer.getChildAt(position).setEnabled(false);
                }
            }
        });

    }

    /*
     *填充数据
     */
    private void fillDataSource() {
        //添加图片
        int[] imageIds = {R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3, R.mipmap.guide_4,};
        for (int imageId : imageIds) {
            ImageView iv = new ImageView(this);
//            iv.setImageResource(imageId);
            iv.setBackgroundResource(imageId);
            ds.add(iv);
        }
        //最后一张图片通过布局文件来单独定制
        View view = View.inflate(this, R.layout.last_welcome_page, null);
        //关于子控件的操作
        ImageView enterIv = (ImageView) view.findViewById(R.id.iv_id);
        enterIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        });
        ds.add(view);
    }
}