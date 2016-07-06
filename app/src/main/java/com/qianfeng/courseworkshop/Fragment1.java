package com.qianfeng.courseworkshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.LinkedList;


public class Fragment1 extends Fragment {
    private FragmentActivity activity;
    private ViewPager vp_main_fragment_id;
    private ImageView iv_main_dot_id;
    private ImageView iv_main_dot2_id;
    private ImageView iv_main_dot3_id;
    private LinkedList<View> ds;

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
        ds = new LinkedList<>();
        int[] imageIds = {R.mipmap.shili, R.mipmap.shili2, R.mipmap.shili3};
        for (int imageId : imageIds) {
            ImageView iv = new ImageView(getActivity());
            iv.setImageResource(imageId);
            ds.add(iv);
        }


        //③设置适配器
        vp_main_fragment_id.setAdapter(new PagerAdapter() {
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
                View iv =  ds.get(position);

                container.addView(iv);// ？，此处容易忽略！不要忘了！作用：在ViewPager容器控件中添加上相应的视图。
                return iv;
            }

//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//
//                container.removeView((View) ds.get(position));
//            }
        });
        vp_main_fragment_id.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                iv_main_dot_id.setEnabled(true);
                iv_main_dot2_id.setEnabled(true);
                iv_main_dot3_id.setEnabled(true);
                if (i == 0) {
                    iv_main_dot_id.setEnabled(false);
                }
                if (i == 1) {
                    iv_main_dot2_id.setEnabled(false);
                }
                if (i == 2) {
                    iv_main_dot3_id.setEnabled(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        return view;
    }

}
