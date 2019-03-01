package com.coocent.marquee;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class MarqueeSystemBarUtil {
    /**
     * 初始化状态栏和导航栏
     * @param activity activity
     * @param isLight 状态栏图标和文字是否浅色
     */
    public static void init(@NonNull Activity activity, boolean isLight){
        //布局延伸到状态栏和导航栏区域
        Window window = activity.getWindow();
        if (isLight){//状态栏图标和文字颜色为浅色
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        } else {//状态栏图标和文字颜色为暗色
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //透明状态栏-导航栏FLAG_TRANSLUCENT_NAVIGATION
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//设置透明导航栏
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//设置透明导航栏
//            WindowManager.LayoutParams winParams = window.getAttributes();
//            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//            winParams.flags |= bits;
//            window.setAttributes(winParams);
        }
    }

    /**
     * 设置状态栏图标和文字是否浅色
     * @param activity activity
     * @param isLight 状态栏图标和文字是否浅色
     */
    public static void setStatusModel(@NonNull Activity activity, boolean isLight){
        Window window = activity.getWindow();
        if (isLight){
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }



    private static final int INVALID_VAL = -1;
    private static final int COLOR_DEFAULT = Color.parseColor("#424242");

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void compat(Activity activity, int statusColor)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            if (statusColor != INVALID_VAL)
            {
                activity.getWindow().setStatusBarColor(statusColor);
            }
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            int color = COLOR_DEFAULT;
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            if (statusColor != INVALID_VAL)
            {
                color = statusColor;
            }
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(color);
            contentView.addView(statusBarView, lp);
        }

    }

    public static void compat(Activity activity)
    {
        compat(activity, INVALID_VAL);
    }


    public static int getStatusBarHeight(Context context)
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
