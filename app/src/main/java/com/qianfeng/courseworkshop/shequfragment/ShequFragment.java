package com.qianfeng.courseworkshop.shequfragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.qianfeng.courseworkshop.Fragment1;
import com.qianfeng.courseworkshop.R;
import com.qianfeng.courseworkshop.TikuFragment;
import com.qianfeng.courseworkshop.coursefragment.CourseFragment;
import com.qianfeng.courseworkshop.filter.ExpandTabView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ShequFragment extends Fragment {

    private FragmentActivity activity;
    private View view;
    private LinkedList<Map<String, Object>> data;
    private Map<String, Object> map;
    private SimpleAdapter adapter;
    private ExpandTabView expandTabView;
    private ArrayList<String> nameList;//顶部tab条目列表
    private FragmentManager supportFragmentManager;
    private RadioGroup rg_shequ_id;
    private RadioGroup rg2_shequ_id;
    private WentiFragment wenti;
    private TieziFragment tiezi;
    private PullToRefreshScrollView ptrsv_shequ_id;
    private Boolean isScoll;
    int[] location = new int[2];
    int[] location2 = new int[2];
    private ListView lv_shequ_jingpin;
    private ListView lv_shequ_tiezi;
    private ListView lv_shequ_wenti;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shequ, null);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        initView();
        //设置不可见
        rg2_shequ_id.setVisibility(View.GONE);
        replaceContainerWidget(tiezi);
        ((RadioButton) rg_shequ_id.getChildAt(0)).setChecked(true);
        //监听器
        rg_shequ_id.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (((RadioButton) radioGroup.getChildAt(0)).isChecked()) {
                    replaceContainerWidget(tiezi);
                }
                if (((RadioButton) radioGroup.getChildAt(1)).isChecked()) {
                    replaceContainerWidget(wenti);
                }

            }
        });
        rg2_shequ_id.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (((RadioButton) radioGroup.getChildAt(0)).isChecked()) {
                    replaceContainerWidget(tiezi);
                }
                if (((RadioButton) radioGroup.getChildAt(1)).isChecked()) {
                    replaceContainerWidget(wenti);
                }
            }
        });

        //设置rg_shequ_id不可见时显示rg2_shequ_id。。未完成，有bug
//        ptrsv_shequ_id.setOnTouchListener(new View.OnTouchListener() {
//            private int lastY = 0;
//            private int touchEventId = -9983761;
//            Handler handler = new Handler() {
//                public void handleMessage(Message msg) {
//                    super.handleMessage(msg);
//                    if (msg.what == touchEventId) {
//                        if (lastY != ptrsv_shequ_id.getScrollY()) {
//                            //scrollview一直在滚动，会触发
//                            handler.sendMessageDelayed(
//                                    handler.obtainMessage(touchEventId, ptrsv_shequ_id), 5);
//                            lastY = ptrsv_shequ_id.getScrollY();
//                            rg_shequ_id.getLocationOnScreen(location);
//                            rg2_shequ_id.getLocationOnScreen(location2);
//                            //动的到静的位置时，静的显示。动的实际上还是网上滚动，但我们看到的是静止的那个
//                            Toast.makeText(getActivity(), "" + location[1] + "", Toast.LENGTH_SHORT).show();
//                            if (location[1] <= location2[1]) {
//                                rg2_shequ_id.setVisibility(View.VISIBLE);
//                            } else {
//                                //静止的隐藏了
//                                rg2_shequ_id.setVisibility(View.GONE);
//                            }
//                        }
//                    }
//                }
//            };
//
//            public boolean onTouch(View v, MotionEvent event) {
//                //必须两个都搞上，不然会有瑕疵
//                //没有这段，手指按住拖动的时候没有效果
//                if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                    handler.sendMessageDelayed(
//                            handler.obtainMessage(touchEventId, v), 5);
//                }
//                //没有这段，手指松开scroll继续滚动的时候，没有效果
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    handler.sendMessageDelayed(
//                            handler.obtainMessage(touchEventId, v), 5);
//                }
//                return false;
//            }
//
//        });

        super.onActivityCreated(savedInstanceState);
    }

    private void fillDataSouce(List<Map<String, Object>> data) {
        // TODO
        for (int i = 1; i <= 20; i++) {
            map = new LinkedHashMap<>();
            map.put("tk_textView5", i);
            map.put("tk_textView1", i);
            map.put("tk_textView2", i);
            data.add(map);
        }
    }

    private void replaceContainerWidget(Fragment fragment) {

        FragmentTransaction transaction = supportFragmentManager.beginTransaction();

        //替换部分
        transaction.replace(R.id.ll_shequ_content, fragment);


        transaction.commit();


    }

    private void initView() {


        //RadioGroup控件
        rg_shequ_id = (RadioGroup) view.findViewById(R.id.rg_shequ_id);
        lv_shequ_jingpin = (ListView) view.findViewById(R.id.lv_shequ_jingpin);
        lv_shequ_tiezi = (ListView) view.findViewById(R.id.lv_shequ_tiezi);
        lv_shequ_wenti = (ListView) view.findViewById(R.id.lv_shequ_wenti);
        rg2_shequ_id = (RadioGroup) view.findViewById(R.id.rg2_shequ_id);
        ptrsv_shequ_id = (PullToRefreshScrollView) view.findViewById(R.id.ptrsv_shequ_id);
        tiezi = new TieziFragment();
        wenti = new WentiFragment();
        supportFragmentManager = getActivity().getSupportFragmentManager();

    }


}
