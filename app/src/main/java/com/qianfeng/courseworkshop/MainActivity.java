package com.qianfeng.courseworkshop;

        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;


public class MainActivity extends AppCompatActivity {
    private Fragment fragment1;
    private Fragment fragment2;
    private RadioGroup rg_main_id;
    private FragmentManager supportFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        supportFragmentManager = getSupportFragmentManager();
        rg_main_id=(RadioGroup)findViewById(R.id.rg_main_id);
        fragment1=new Fragment1();
        fragment2=new Fragment2();
        replaceContainerWidget(fragment1);
        ((RadioButton)rg_main_id.getChildAt(0)).setChecked(true);
        rg_main_id.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
              if(((RadioButton)radioGroup.getChildAt(0)).isChecked()){
                  replaceContainerWidget(fragment1);
              }
              if(((RadioButton)radioGroup.getChildAt(1)).isChecked()){
                  replaceContainerWidget(fragment2);
              }
              if(((RadioButton)radioGroup.getChildAt(2)).isChecked()){
//                  replaceContainerWidget(fragment2);
              }
              if(((RadioButton)radioGroup.getChildAt(3)).isChecked()){
//                  replaceContainerWidget(fragment2);
              }
              if(((RadioButton)radioGroup.getChildAt(4)).isChecked()){
//                  replaceContainerWidget(fragment2);
              }
            }
        });
    }
    private void replaceContainerWidget(Fragment fragment) {
        FragmentTransaction transaction = supportFragmentManager
                .beginTransaction();
        transaction.replace(R.id.ll_content_id, fragment);

        transaction.commit();


    }
    public void morefragment(){
        ((RadioButton)rg_main_id.getChildAt(1)).setChecked(true);
    }
}
