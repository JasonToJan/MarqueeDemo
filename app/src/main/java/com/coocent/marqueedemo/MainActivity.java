package com.coocent.marqueedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.coocent.marquee.MarqueeActivity;
import com.coocent.marquee.MarqueeColorUtils;
import com.coocent.marquee.MarqueeThemeUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button defaultBtn1;
    private Button customBtn1;
    private Button customBtn2;
    private Button demoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defaultBtn1=findViewById(R.id.am_default_btn);
        customBtn1=findViewById(R.id.am_custom_btn);
        customBtn2=findViewById(R.id.am_custom2_btn);
        demoBtn=findViewById(R.id.am_demo_btn);

        defaultBtn1.setOnClickListener(this);
        customBtn1.setOnClickListener(this);
        customBtn2.setOnClickListener(this);
        demoBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.am_default_btn:
                NavigationUtils.skipToDefaultStyle(this);
                break;

            case R.id.am_custom_btn:
                int colorPrimary=0x11111;
                int colorAccent=0x22222;
                NavigationUtils.skipToCustom1Style(this,colorPrimary,colorAccent);
                break;

            case R.id.am_custom2_btn:
                NavigationUtils.skipToCustom2Style();
                break;

            case R.id.am_demo_btn:
                NavigationUtils.skipToDemo();
                break;
        }
    }

    /**
     * 跳转到默认风格样式
     */
    private void skipToDefaultStyle(){
        Intent intent=new Intent(MainActivity.this,MarqueeActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到自定义样式风格（EQ类App适用），直接替换所有图标资源
     */
    private void skipToCustom1Style(){
//        MarqueeThemeUtil.setMarqueeTheme(R.drawable.eq_button_on,
//                com.coocent.marquee.R.drawable.marquee_home_bg1_1,
//                R.drawable.eq_button01,
//                com.coocent.marquee.R.drawable.marquee_home_bg10_2,
//                R.drawable.eq_button01,
//                com.coocent.marquee.R.drawable.marquee_home_button1_2,
//                MarqueeColorUtils.int2Hex(colorPrimary),
//                MarqueeColorUtils.int2Hex(colorPrimary).substring(1),
//                com.coocent.marquee.R.drawable.marquee_btn_ripple01);
//        MarqueeThemeUtil.setUnEnablePointBitBg(R.drawable.eq_button01);//设置无法滑动时的Seekbar头部
//        MarqueeThemeUtil.setColorPrimaryFromOtherApp(colorPrimary);//设置带有颜色主题的App传递的主题色
//        MarqueeThemeUtil.setColorAccentFromOtherApp(colorAccent);//设置带有颜色主题的App传递的强调色
//
//        Intent intent=new Intent(MainActivity.this,MarqueeActivity.class);
//        startActivity(intent);
    }

    /**
     * 跳转到自定义样式风格，带有自己的主题色和强调色（决定Seekbar进度条颜色和switch颜色），同时也可以替换图标
     */
    private void skipToCustom2Style(int colorPrimary,int colorAccent){
//        MarqueeThemeUtil.setMarqueeTheme(R.drawable.eq_button_on,
//                com.coocent.marquee.R.drawable.marquee_home_bg1_1,
//                R.drawable.eq_button01,
//                com.coocent.marquee.R.drawable.marquee_home_bg10_2,
//                R.drawable.eq_button01,
//                com.coocent.marquee.R.drawable.marquee_home_button1_2,
//                MarqueeColorUtils.int2Hex(colorPrimary),
//                MarqueeColorUtils.int2Hex(colorPrimary).substring(1),
//                com.coocent.marquee.R.drawable.marquee_btn_ripple01);
//        MarqueeThemeUtil.setUnEnablePointBitBg(R.drawable.eq_button01);//设置无法滑动时的Seekbar头部
//        MarqueeThemeUtil.setColorPrimaryFromOtherApp(colorPrimary);//设置带有颜色主题的App传递的主题色
//        MarqueeThemeUtil.setColorAccentFromOtherApp(colorAccent);//设置带有颜色主题的App传递的强调色
//
//        Intent intent=new Intent(MainActivity.this,MarqueeActivity.class);
//        startActivity(intent);
    }
}
