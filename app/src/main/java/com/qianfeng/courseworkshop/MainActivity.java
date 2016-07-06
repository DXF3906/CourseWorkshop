package com.qianfeng.courseworkshop;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nineoldandroids.view.ViewHelper;
import com.qianfeng.courseworkshop.coursefragment.CourseFragment;
import com.qianfeng.courseworkshop.coursefragment.CourseLeftFragment;


public class MainActivity extends FragmentActivity {
    private DrawerLayout drwaerLyout;//抽屉控件
    private Fragment fragment1;//首页
    private Fragment fragment2;//课程
    private RadioGroup rg_main_id;//底部界面切换控件
    private FragmentManager supportFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1.初始化操作
        supportFragmentManager = getSupportFragmentManager();

        //2.界面控件初始化
        initView();

        //3.关于RadioButton的操作
        aboutRadioButton();

        //4.默认为首页
        replaceContainerWidget(fragment1, null);

        //5.关于侧滑的操作
        initEvents();

    }

    /**
     * 关于侧滑动画的操作
     */
    private void initEvents() {
        drwaerLyout.setDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = drwaerLyout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals("LEFT")) {

                    float leftScale = 1 - 0.3f * scale;

                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                    ViewHelper.setTranslationX(mContent, mMenu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                }

            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }
        });
    }


    /**
     * 关于RadioButton的操作
     */
    private void aboutRadioButton() {
        //①程序初始界面为首页
        ((RadioButton) rg_main_id.getChildAt(0)).setChecked(true);

        //②给RadioButton添加监听器
        rg_main_id.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //首页
                if (((RadioButton) radioGroup.getChildAt(0)).isChecked()) {
                    replaceContainerWidget(fragment1, null);
                }
                //课程
                if (((RadioButton) radioGroup.getChildAt(1)).isChecked()) {
                    replaceContainerWidget(fragment2, new CourseLeftFragment());
                }
                //社区
                if (((RadioButton) radioGroup.getChildAt(2)).isChecked()) {
                    //replaceContainerWidget(fragment2);
                }
                //题库
                if (((RadioButton) radioGroup.getChildAt(3)).isChecked()) {
                    //replaceContainerWidget(fragment2);
                }
                //我
                if (((RadioButton) radioGroup.getChildAt(4)).isChecked()) {
                    //replaceContainerWidget(fragment2);
                }
            }
        });
    }

    /**
     * 界面控件初始化
     */
    private void initView() {
        //抽屉父控件
        drwaerLyout = (DrawerLayout) findViewById(R.id.drwaerlLayout_id);

        //抽屉效果不变暗的重要代码******
        drwaerLyout.setScrimColor(android.R.color.transparent);

        //RadioGroup控件
        rg_main_id = (RadioGroup) findViewById(R.id.rg_main_id);
        //首页对应的fragment
        fragment1 = new Fragment1();
        //课程对应的fragment
        fragment2 = new CourseFragment();
    }

    /**
     * 替换占位的fragment控件
     * @param fragment
     * @param leftFragment
     */
    private void replaceContainerWidget(Fragment fragment, Fragment leftFragment) {

        FragmentTransaction transaction = supportFragmentManager.beginTransaction();

        //替换主体部分
        transaction.replace(R.id.ll_content_id, fragment);

        //如果当前界面是课程或社区，将指定的左侧滑动的界面替换
        if (leftFragment != null) {
            drwaerLyout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);//滑动解锁
            transaction.replace(R.id.id_left_menu, leftFragment);
        } else {
            drwaerLyout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//滑动锁定，不允许滑动
        }
        transaction.commit();


    }
    /**
     * 点击触发侧滑效果(三横线)
     * @param view
     */
    public void OpenLeftMenu(View view){
        drwaerLyout.openDrawer(Gravity.LEFT);
    }
    public  void morefragment(){
        ((RadioButton) rg_main_id.getChildAt(1)).setChecked(true);
    }
}
