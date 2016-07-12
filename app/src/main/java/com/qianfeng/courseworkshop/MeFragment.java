package com.qianfeng.courseworkshop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.courseworkshop.util.Util;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;



public class MeFragment extends Fragment {
    private View view;
    private FragmentActivity activity;
    private Button btn_login_id;
    private Button btn_regist_id;
    private ImageView iv_login_touxiang_id;
    private TextView tv_login_name_id;

    private RelativeLayout rl_mycourse_id;
    private RelativeLayout rl_huancun_id;
    private RelativeLayout rl_money_id;
    private RelativeLayout rl_mytiezi_id;
    private RelativeLayout rl_mymessage_id;

    public static String mAppid;
    public static Tencent mTencent;
    public static String openidString;
    public static String nicknameString;
    Bitmap bitmap = null;
    private MainActivity context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        context= (MainActivity) getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //初始化控件实例
        initWidget();
        btn_login_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginQQ();

            }
        });
        btn_regist_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity2.class);
                String courUrl="http://www.kgc.cn/member/reg-mobile";
                intent.putExtra("100",courUrl);
                startActivity(intent);

            }
        });
        //我的课程
        rl_mycourse_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity2.class);
                String courUrl="http://www.kgc.cn/my/course.shtml";
                intent.putExtra("100",courUrl);
                startActivity(intent);

            }
        });
        //缓存视频
        rl_huancun_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity2.class);
                String courUrl="http://www.kgc.cn/my/favorite.shtml";
                intent.putExtra("100",courUrl);
                startActivity(intent);

            }
        });
        //我的钱包
        rl_money_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity2.class);
                String courUrl="http://www.kgc.cn/my/wallet/cz.shtml";
                intent.putExtra("100",courUrl);
                startActivity(intent);

            }
        });
        //我的帖子
        rl_mytiezi_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity2.class);
                String courUrl="http://www.kgc.cn/my/post";
                intent.putExtra("100",courUrl);
                startActivity(intent);

            }
        });
        //我的消息
        rl_mymessage_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), WebViewActivity2.class);
                String courUrl="http://www.kgc.cn/my/message.shtml";
                intent.putExtra("100",courUrl);
                startActivity(intent);

            }
        });

        super.onActivityCreated(savedInstanceState);

    }

    /**
     * 初始化控件实例
     */
    private void initWidget() {
        //登陆
        btn_login_id=(Button)view.findViewById(R.id.btn_login_id);
        //注册
        btn_regist_id=(Button)view.findViewById(R.id.btn_regist_id);
        //头像
        iv_login_touxiang_id=(ImageView)view.findViewById(R.id.iv_login_touxiang_id);
        //登陆用户名
        tv_login_name_id=(TextView)view.findViewById(R.id.tv_login_name_id);

        rl_mycourse_id=(RelativeLayout)view.findViewById(R.id.rl_mycourse_id);
        rl_huancun_id=(RelativeLayout)view.findViewById(R.id.rl_huancun_id);
        rl_money_id=(RelativeLayout)view.findViewById(R.id.rl_money_id);
        rl_mytiezi_id=(RelativeLayout)view.findViewById(R.id.rl_mytiezi_id);
        rl_mymessage_id=(RelativeLayout)view.findViewById(R.id.rl_mymessage_id);
    }

    /**
     * 登陆qq
     */
    private void LoginQQ() {
        //这里的APP_ID请换成你应用申请的APP_ID，我这里使用的是DEMO中官方提供的测试APP_ID 222222
        mAppid = "1105464007";
        //第一个参数就是上面所说的申请的APPID，第二个是全局的Context上下文，这句话实现了调用QQ登录
        mTencent = Tencent.createInstance(mAppid,context);
        /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
         官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
         第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
        if (mTencent!=null) {
            mTencent.login(context, "all", new BaseUiListener());
            Toast.makeText(context,"执行了",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "没有执行", Toast.LENGTH_LONG).show();
        }
    }
    /**当自定义的监听器实现IUiListener接口后，必须要实现接口的三个方法，
     * onComplete  onCancel onError
     *分别表示第三方登录成功，取消 ，错误。*/
    private class BaseUiListener implements IUiListener {

        public void onCancel() {

        }
        public void onComplete(Object response) {
            Toast.makeText(activity.getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
            try {
                //获得的数据是JSON格式的，获得你想获得的内容
                //如果你不知道你能获得什么，看一下下面的LOG
                openidString = ((JSONObject) response).getString("openid");
                // tv_login_name_id.setText(openidString);
                //access_token= ((JSONObject) response).getString("access_token");
                // expires_in = ((JSONObject) response).getString("expires_in");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /**到此已经获得OpneID以及其他你想获得的内容了
             QQ登录成功了，我们还想获取一些QQ的基本信息，比如昵称，头像什么的，这个时候怎么办？
             sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我么可以通过这个类拿到这些信息
             如何得到这个UserInfo类呢？  */
            QQToken qqToken = mTencent.getQQToken();
            UserInfo info = new UserInfo(activity.getApplicationContext(), qqToken);
            //这样我们就拿到这个类了，之后的操作就跟上面的一样了，同样是解析JSON
            info.getUserInfo(new IUiListener() {

                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                    /**由于图片需要下载所以这里使用了线程，如果是想获得其他文字信息直接
                     * 在mHandler里进行操作
                     *
                     */
                    new Thread(){

                        @Override
                        public void run() {
                            JSONObject json = (JSONObject)response;
                            try {
                                bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Message msg = new Message();
                            msg.obj = bitmap;
                            msg.what = 1;
                            mHandler.sendMessage(msg);
                        }
                    }.start();
                }
                public void onCancel() {
                }
                public void onError(UiError arg0) {
                }

            });

        }

        public void onError(UiError arg0) {

        }

    }
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
                    try {
                        nicknameString=response.getString("nickname");

                        tv_login_name_id.setText(nicknameString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }else if(msg.what == 1){
                Bitmap bitmap = (Bitmap)msg.obj;
                iv_login_touxiang_id.setImageBitmap(bitmap);
            }
        }

    };

}
