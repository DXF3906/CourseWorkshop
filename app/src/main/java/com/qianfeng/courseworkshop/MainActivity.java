package com.qianfeng.courseworkshop;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;
import com.qianfeng.courseworkshop.activity.SearchActivity;
import com.qianfeng.courseworkshop.bean.CommonData;
import com.qianfeng.courseworkshop.coursefragment.CourseFragment;
import com.qianfeng.courseworkshop.coursefragment.CourseLeftFragment;
import com.qianfeng.courseworkshop.inner.GetCourseLeftUrlCallBack;
import com.qianfeng.courseworkshop.shequfragment.ShequFragment;

import org.w3c.dom.Text;


public class MainActivity extends FragmentActivity implements GetCourseLeftUrlCallBack {
    private DrawerLayout drwaerLyout;//抽屉控件
    private Fragment fragment1;//首页
    private Fragment fragment2;//课程
    private Fragment fragment3;//社区
    private Fragment fragment4;//题库
    private Fragment fragment5;//我
    private RadioGroup rg_main_id;//底部界面切换控件
    private FragmentManager supportFragmentManager;

    private Fragment courseLeftFragment;


    //使用get和set传递值
    private String courseUrl;
    private TextView textView;

    public String getCourseUrl() {
        return courseUrl;
    }
    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }

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
                    replaceContainerWidget(fragment2, courseLeftFragment);
                }
                //社区
                if (((RadioButton) radioGroup.getChildAt(2)).isChecked()) {
                    replaceContainerWidget(fragment3, courseLeftFragment);
                }
                //题库
                if (((RadioButton) radioGroup.getChildAt(3)).isChecked()) {
                    replaceContainerWidget(fragment4,null);
                }
                //我
                if (((RadioButton) radioGroup.getChildAt(4)).isChecked()) {
                    replaceContainerWidget(fragment5,null);
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
        fragment3 = new ShequFragment();
        fragment4 = new TikuFragment();
        fragment5 = new MeFragment();
        courseLeftFragment=new CourseLeftFragment(this);


    }

    /**
     * 替换占位的fragment控件
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
     */
    public void OpenLeftCourseMenu(View view){
        drwaerLyout.openDrawer(Gravity.LEFT);
    }

    /**
     * 课程目录点击监听事件
     */
    public void CloseLeftCourseMenu(View view){
        switch (view.getId()) {
            case R.id.ll_allcourse_id:
                setCourseUrl(CommonData.allCourse);
                break;
            case R.id.ll_program_id:
                setCourseUrl(CommonData.programCourse);
                break;
            case R.id.ll_spread_id:
                setCourseUrl(CommonData.spreadCourse);
                break;
            case R.id.ll_seeing_id:
                setCourseUrl(CommonData.seeingCourse);
                break;
            case R.id.ll_net_id:
                setCourseUrl(CommonData.netCourse);
                break;
            case R.id.ll_exam_id:
                setCourseUrl(CommonData.examCourse);
                break;
            default:
        }
        replaceContainerWidget(new CourseFragment(),courseLeftFragment);
        drwaerLyout.closeDrawer(Gravity.LEFT);

    }

    /**
     * 课程搜索功能
     */
    public void searchCourse(View view){
        Intent intent=new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    public void morefragment(String string){
        ((RadioButton) rg_main_id.getChildAt(1)).setChecked(true);
        Bundle data = new Bundle();
        data.putString("TEXT", string);
        fragment2.setArguments(data);//通过Bundle向fragment中传递值
    }

    /**
     * 接口回调，课程侧滑点击ListView的某一项，回传的网址
     * @param result
     */
    @Override
    public void gettCourseLeftUrl(String result) {
        setCourseUrl(result);
        replaceContainerWidget(new CourseFragment(),courseLeftFragment);
        drwaerLyout.closeDrawer(Gravity.LEFT);

    }
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
