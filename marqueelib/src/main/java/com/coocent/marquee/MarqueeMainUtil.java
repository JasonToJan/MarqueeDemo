package com.coocent.marquee;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import static com.coocent.marquee.MarqueeConstant.MARQUEE_ENABLE;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_ENABLE_VALUE;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_RADIAN;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_RADIAN_VALUE;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_SPEED;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_SPEED_VALUE;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_WIDTH;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_WIDTH_VALUE;


public class MarqueeMainUtil {

    /**
     * 设置或更新跑马灯参数，注意：第一次初始化MarqueeSweepGradientView需要在MarqueeSweepGradientView.post方法里执行
     * @param context
     * @param marqueeSmallCircleView
     * @param isVisible
     */
    public static void resetSweepGradientView(Context context, MarqueeSweepGradientView marqueeSmallCircleView,boolean isVisible){
        if (context != null){
            SharedPreferences sp = context.getSharedPreferences("setting_preference", Context.MODE_PRIVATE);
            int radianValue = sp.getInt(MARQUEE_RADIAN,MARQUEE_RADIAN_VALUE);
            int widthValue = sp.getInt(MARQUEE_WIDTH,MARQUEE_WIDTH_VALUE);
            int speedValue = sp.getInt(MARQUEE_SPEED, MARQUEE_SPEED_VALUE);
            ArrayList<MarqueeEntity> marqueeLists = MarqueeLoader.getInstance(context).getData();
            int[] colors = new int[marqueeLists.size()+1];//保证头尾颜色值一样
            for (int i=0;i<colors.length;i++){
                if (i == colors.length -1){
                    colors[i] = colors[0];
                } else {
                    colors[i] = Color.parseColor(marqueeLists.get(i).getColor());
                }
            }
            if (marqueeSmallCircleView != null){
                marqueeSmallCircleView.setBuilder(radianValue, widthValue, speedValue,colors);
                showAndHideSweView(context, marqueeSmallCircleView, isVisible);
            }
        }
    }

    /**
     * 显示或隐藏跑马灯
     * @param context
     * @param marqueeSmallCircleView 跑马灯View
     * @param isVisible 控制跑马灯是否显示，不唯一控制参数（提示：还有一个marqueeEnable参数与isVisible相&&来达到完全控制）
     */
    public static void showAndHideSweView(Context context, MarqueeSweepGradientView marqueeSmallCircleView, boolean isVisible){
        if (context != null && marqueeSmallCircleView != null){
            SharedPreferences sp = context.getSharedPreferences("setting_preference", Context.MODE_PRIVATE);
            boolean marqueeEnable = sp.getBoolean(MARQUEE_ENABLE, MARQUEE_ENABLE_VALUE);
            if(marqueeEnable&&isVisible){
                Log.d("测试--","#MarqueeMainUtil#showAndHideSweView"+" ...显示走马灯吧...");
            }
            marqueeSmallCircleView.setVisibility(marqueeEnable && isVisible ? View.VISIBLE:View.GONE);
        }
    }

    /**
     * 设置或更新侧边栏Item小圆圈的颜色,一般初始化侧边栏和MarqueeActivity退出栈调用来更新小圆圈
     * @param context
     * @param marqueeSmallCircleView 小圆圈View
     */
    public static void resetSmallCircleView(Context context, MarqueeSmallCircleView marqueeSmallCircleView) {
        if (context != null){
            ArrayList<MarqueeEntity> marqueeLists = MarqueeLoader.getInstance(context).getData();
            int[] colors = new int[marqueeLists.size()];//保证头尾颜色值一样
            for (int i=0;i<colors.length;i++){
                colors[i] = Color.parseColor(marqueeLists.get(i).getColor());
            }
            if (marqueeSmallCircleView != null)
                marqueeSmallCircleView.setColors(colors);
        }
    }
}
