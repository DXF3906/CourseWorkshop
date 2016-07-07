package com.qianfeng.courseworkshop.Adapter;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * PagerAdapter自定义子类
 */
public final class MyAdapter extends PagerAdapter {
    private List<View> ds;
    private ViewPager mVp;

    public MyAdapter() {
    }

    public MyAdapter(List<View> ds, ViewPager mVp) {
        this.ds = ds;
        this.mVp = mVp;

    }

    @Override
    public int getCount() {
        return ds.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = ds.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mVp.removeView((View) object);
    }
}
