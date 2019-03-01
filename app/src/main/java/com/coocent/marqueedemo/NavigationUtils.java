package com.coocent.marqueedemo;

import android.app.Activity;
import android.content.Intent;

import com.coocent.marquee.MarqueeActivity;
import com.coocent.marquee.MarqueeColorUtils;
import com.coocent.marquee.MarqueeThemeUtil;

/**
 * desc: 跳转工具
 * *
 * user: JasonJan 1211241203@qq.com
 * time: 2019/3/1 17:15
 **/
public class NavigationUtils {

    /**
     * 跳转到默认风格样式
     */
    public static void skipToDefaultStyle(Activity activity){
        Intent intent=new Intent(activity,MarqueeActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 跳转到自定义样式风格（EQ类App适用），直接替换所有图标资源
     */
    public static void skipToCustom1Style(Activity activity){
        MarqueeThemeUtil.setMarqueeTheme(
                R.drawable.eq_button01_on,//顶部开关
                com.coocent.marquee.R.drawable.marquee_home_bg1_1,
                R.drawable.eq_button01,
                com.coocent.marquee.R.drawable.marquee_home_bg10_2,
                R.drawable.eq_button01,
                com.coocent.marquee.R.drawable.marquee_home_button1_2,
                "#20f7ce89",//注意这里有#号
                "5e84ff",//注意这里没#
                com.coocent.marquee.R.drawable.marquee_btn_ripple01);
        MarqueeThemeUtil.setUnEnablePointBitBg(R.drawable.eq_button01);//设置无法滑动时的Seekbar头部

        Intent intent=new Intent(activity,MarqueeActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 跳转到自定义样式风格，带有自己的主题色和强调色（决定Seekbar进度条颜色和switch开启颜色）
     * 同时也可以替换图标
     * @param activity
     */
    public static void skipToCustom2Style(Activity activity){
        int colorPrimary=activity.getResources().getColor(R.color.colorPrimary);
        int colorAccent=activity.getResources().getColor(R.color.colorAccent);
        MarqueeThemeUtil.setMarqueeTheme(
                R.drawable.panseekbutton,//顶部开关，开启会默认填充App强调色
                com.coocent.marquee.R.drawable.marquee_home_bg1_1,
                R.drawable.panseekbutton,
                com.coocent.marquee.R.drawable.marquee_home_bg10_2,
                R.drawable.panseekbutton,
                com.coocent.marquee.R.drawable.marquee_home_button1_2,
                MarqueeColorUtils.int2Hex(colorPrimary),
                MarqueeColorUtils.int2Hex(colorPrimary).substring(1),
                com.coocent.marquee.R.drawable.marquee_btn_ripple01);
        MarqueeThemeUtil.setUnEnablePointBitBg(R.drawable.panseekbutton_off);//设置无法滑动时的Seekbar头部
        MarqueeThemeUtil.setColorPrimaryFromOtherApp(colorPrimary);//设置带有颜色主题的App传递的主题色
        MarqueeThemeUtil.setColorAccentFromOtherApp(colorAccent);//设置带有颜色主题的App传递的强调色

        Intent intent=new Intent(activity,MarqueeActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 提供案例使用
     */
    public static void skipToDemo(Activity activity){

    }
}
