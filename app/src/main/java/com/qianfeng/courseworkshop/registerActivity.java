package com.qianfeng.courseworkshop;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
/**
 * Created by Administrator on 2016/7/6.
 *

 public class registerActivity extends AppCompatActivity {
 private TextView lg;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_register);
    lg = (TextView) findViewById(R.id.textView6);
    TextView.OnClickListener Lglistener = new MyLgListener();
    lg.setOnClickListener(Lglistener);

    super.onCreate(savedInstanceState);
    }

    private class MyLgListener implements TextView.OnClickListener {
    public void onClick(View view) {
    startActivity(new Intent(registerActivity.this, loginActivity.class));
    }
    }
    }*/


public class registerActivity extends Activity implements OnClickListener {
    //TextView openidTextView;
    //TextView nicknameTextView;
    ImageView loginButton;
    ImageView userlogo;
    private Tencent mTencent;
    public static QQAuth mQQAuth;
    public static String mAppid;
    public static String openidString;
    public static String nicknameString;
    public static String TAG = "MainActivity";

    //Bitmap bitmap = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //用来登录的Button
        loginButton = (ImageView) findViewById(R.id.iv_qq_login);
        loginButton.setOnClickListener(this);
        //用来显示OpenID的textView
        //openidTextView=(TextView)findViewById(R.id.user_openid);
        //用来显示昵称的textview
        //nicknameTextView=(TextView)findViewById(R.id.user_nickname);
        //用来显示头像的Imageview
        //userlogo=(ImageView)findViewById(R.id.user_logo);

    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.iv_qq_login:
                LoginQQ();
                break;

            default:
                break;
        }
    }

    //这里是调用QQ登录的关键代码
    public void LoginQQ() {
        //这里的APP_ID请换成你应用申请的APP_ID，我这里使用的是DEMO中官方提供的测试APP_ID 222222
        mAppid = AppConstant.APP_ID;
        //第一个参数就是上面所说的申请的APPID，第二个是全局的Context上下文，这句话实现了调用QQ登录
        mTencent = Tencent.createInstance(mAppid, getApplicationContext());
        /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
         官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
         第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
        mTencent.login(registerActivity.this, "all",new BaseUiListener());
    }

    private class BaseUiListener implements IUiListener {

        public void onCancel() {
            // TODO Auto-generated method stub
        }

        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError uiError) {

        }
    }
}