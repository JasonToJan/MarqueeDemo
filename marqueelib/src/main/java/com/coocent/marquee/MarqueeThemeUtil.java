package com.coocent.marquee;

import android.text.TextUtils;

/**
 * desc: 跑马灯主题工具类
 * *
 * user: JasonJan 1211241203@qq.com
 * time: 2019/2/22 13:44
 **/
public class MarqueeThemeUtil {

    //Theme实体列表
    private static int totalSwitch;
    private static int arcProgressView;
    private static int arcPercentBg;
    private static int eqSeekBarBg;
    private static int arcPoint;
    private static int seekPointBg;
    private static String visualizerBg;
    private static String themeColor;
    private static int rippleColor;

    //Seekbar相关颜色值
    private static int radianViewBg;
    private static int widthViewBg;
    private static int speedViewBg;

    //默认值设置
    private static int unEnablePointBitBg=R.drawable.marquee_bass_progress_bar_bg_dot_turn_off;
    private static int seekbarColorBgDefault=R.drawable.marquee_home_bg10_2;
    private static int seekbarColorBgUnEnableDefault=R.drawable.marquee_bass_progress_bar_bg_turn_off_equalizer;
    private static int colorPrimaryFromOtherApp=0;//来自其他App传入的颜色值,以此确定改变swithButton模式
    private static int colorAccentFromOtherApp=0;//来自其他App传入的颜色值
    private static String colorDefault1="#00D3FF";//跑马灯默认第一级颜色
    private static String colorDefault2="#000000";//跑马灯默认第一级颜色


    public static void setMarqueeThemeDefault(){
        totalSwitch=R.drawable.marquee_home_button1_open;
        arcProgressView=R.drawable.marquee_home_bg1_1;
        arcPercentBg=R.drawable.marquee_home_button1_on;
        eqSeekBarBg=R.drawable.marquee_home_bg1_2;
        arcPoint=R.drawable.marquee_home_button1;
        seekPointBg=R.drawable.marquee_home_button1_2;
        visualizerBg="#20f7ce89";
        themeColor="FF5722";
        rippleColor=R.drawable.marquee_btn_ripple01;
    }

    public static void setMarqueeSeekbarDefault(){
        radianViewBg=R.drawable.marquee_home_bg10_2;
        widthViewBg=R.drawable.marquee_home_bg10_2;
        speedViewBg=R.drawable.marquee_home_bg10_2;
    }

    public static void setMarqueeTheme(int totalSwitch1,int arcProgressView1,int arcPercentBg1,int eqSeekBarBg1,int arcPoint1,
        int seekPointBg1,String visualizerBg1,String themeColor1,int rippleColor1){
        
        totalSwitch=totalSwitch1;
        arcProgressView=arcProgressView1;
        arcPercentBg=arcPercentBg1;
        eqSeekBarBg=eqSeekBarBg1;
        arcPoint=arcPoint1;
        seekPointBg=seekPointBg1;
        visualizerBg=visualizerBg1;
        themeColor=themeColor1;
        rippleColor=rippleColor1;
    }

    public static void setVisualizerBg(String visualizerBg2){
        visualizerBg = visualizerBg2;
    }
    
    public static void setTotalSwitch(int totalSwitch2) {
        totalSwitch = totalSwitch2;
    }

    public static void setArcProgressView(int arcProgressView2) {
        arcProgressView = arcProgressView2;
    }

    public static void setArcPercentBg(int arcPercentBg2) {
        arcPercentBg = arcPercentBg2;
    }

    public static void setEqSeekBarBg(int eqSeekBarBg2) {
        eqSeekBarBg = eqSeekBarBg2;
    }

    public static void setArcPoint(int arcPoint2) {
        arcPoint = arcPoint2;
    }
    
    public static void setSeekPointBg(int seekPointBg2) {
        seekPointBg = seekPointBg2;
    }

    public static void setThemeColor(String themeColor2) {
        themeColor = themeColor2;
    }

    public static void setRippleColor(int rippleColor2){
        rippleColor = rippleColor2;
    }

    public static int getTotalSwitch() {
        if(totalSwitch==0){
            setMarqueeThemeDefault();
        }
        return totalSwitch;
    }

    public static int getArcProgressView() {
        if(arcProgressView==0){
            setMarqueeThemeDefault();
        }
        return arcProgressView;
    }

    public static int getArcPercentBg() {
        if(arcPercentBg==0){
            setMarqueeThemeDefault();
        }
        return arcPercentBg;
    }

    public static int getEqSeekBarBg() {
        if(eqSeekBarBg==0){
            setMarqueeThemeDefault();
        }
        return eqSeekBarBg;
    }

    public static int getArcPoint() {
        if(arcPoint==0){
            setMarqueeThemeDefault();
        }
        return arcPoint;
    }

    public static int getSeekPointBg() {
        if(seekPointBg==0){
            setMarqueeThemeDefault();
        }
        return seekPointBg;
    }

    public static String getVisualizerBg() {
        if(TextUtils.isEmpty(visualizerBg)){
            setMarqueeThemeDefault();
        }
        return visualizerBg;
    }

    public static String getThemeColor() {
        if(TextUtils.isEmpty(themeColor)){
            setMarqueeThemeDefault();
        }
        return themeColor;
    }

    public static int getRippleColor() {
        if(rippleColor==0){
            setMarqueeThemeDefault();
        }
        return rippleColor;
    }

    /**
     * 设置跑马灯圆角弧度设置的背景资源
     * @return
     */
    public static int getRadianViewBg() {
        if(MarqueeThemeUtil.radianViewBg==0){
            setMarqueeSeekbarDefault();
        }
        return radianViewBg;
    }

    public static void setRadianViewBg(int radianViewBg) {
        MarqueeThemeUtil.radianViewBg = radianViewBg;
    }

    /**
     * 设置跑马灯圆角宽度设置的背景资源
     * @return
     */
    public static int getWidthViewBg() {
        if(MarqueeThemeUtil.widthViewBg==0){
            setMarqueeSeekbarDefault();
        }
        return widthViewBg;
    }

    public static void setWidthViewBg(int widthViewBg) {
        MarqueeThemeUtil.widthViewBg = widthViewBg;
    }

    /**
     * 设置跑马灯圆角速度设置的背景资源
     * @return
     */
    public static int getSpeedViewBg() {
        if(MarqueeThemeUtil.speedViewBg==0){
            setMarqueeSeekbarDefault();
        }
        return speedViewBg;
    }

    public static void setSpeedViewBg(int speedViewBg) {
        MarqueeThemeUtil.speedViewBg = speedViewBg;
    }

    public static int getUnEnablePointBitBg() {
        return unEnablePointBitBg;
    }

    public static void setUnEnablePointBitBg(int unEnablePointBitBg) {
        MarqueeThemeUtil.unEnablePointBitBg = unEnablePointBitBg;
    }

    public static int getSeekbarColorBgDefault() {
        return seekbarColorBgDefault;
    }

    public static void setSeekbarColorBgDefault(int seekbarColorBgDefault) {
        MarqueeThemeUtil.seekbarColorBgDefault = seekbarColorBgDefault;
    }

    public static int getSeekbarColorBgUnEnableDefault() {
        return seekbarColorBgUnEnableDefault;
    }

    public static void setSeekbarColorBgUnEnableDefault(int seekbarColorBgUnEnableDefault) {
        MarqueeThemeUtil.seekbarColorBgUnEnableDefault = seekbarColorBgUnEnableDefault;
    }

    public static int getColorPrimaryFromOtherApp() {
        return colorPrimaryFromOtherApp;
    }

    public static void setColorPrimaryFromOtherApp(int colorPrimaryFromOtherApp) {
        MarqueeThemeUtil.colorPrimaryFromOtherApp = colorPrimaryFromOtherApp;
    }

    public static int getColorAccentFromOtherApp() {
        return colorAccentFromOtherApp;
    }

    public static void setColorAccentFromOtherApp(int colorAccentFromOtherApp) {
        MarqueeThemeUtil.colorAccentFromOtherApp = colorAccentFromOtherApp;
    }

    public static String getColorDefault1() {
        return colorDefault1;
    }

    public static void setColorDefault1(String colorDefault1) {
        MarqueeThemeUtil.colorDefault1 = colorDefault1;
    }

    public static String getColorDefault2() {
        return colorDefault2;
    }

    public static void setColorDefault2(String colorDefault2) {
        MarqueeThemeUtil.colorDefault2 = colorDefault2;
    }
}
