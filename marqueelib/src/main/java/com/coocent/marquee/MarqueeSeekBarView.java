package com.coocent.marquee;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import java.util.Locale;

/**
 * Created by Administrator on 2018/1/22.
 *
 */

public class MarqueeSeekBarView extends View {

    private boolean isEnable = true;
    private OnSeekBarChangeListener onSeekBarChangeListener;
    private float perDbWidth = 1f;
    private RectF rectF = new RectF();
    private Bitmap targetBgBit = null;
//    private Bitmap colorBitmap;
    private Bitmap targetColorBit = null;
    private Bitmap pointBit = null;
    private int centerY = 0;
    private int realW = 0;
    private float mLastX = 0f;
    private float mAddX = 0f;
    private int currentValue = 0;
    private boolean hasTouchFromUser = false;
    private int maxValue;
    private boolean isVib = true;
//    private Matrix matrix;
    private boolean isChangeTop = true;//修复连续按home切换数值会动

    public MarqueeSeekBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MarqueeSeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        isChangeTop = true;
//        TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == LayoutDirection.RTL

        if(isEnable){
            pointBit = BitmapFactory.decodeResource(getResources(), MarqueeThemeUtil.getArcPoint());
        } else {
           pointBit = BitmapFactory.decodeResource(getResources(),MarqueeThemeUtil.getUnEnablePointBitBg());
        }
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l){
        onSeekBarChangeListener = l;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF.top = 0;
        rectF.bottom = h;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            if (isRTL()){
                rectF.left = getWidth() - getPaddingStart();
                rectF.right = getWidth() - getPaddingStart();
            } else {
                rectF.left = getPaddingStart();
                rectF.right = getPaddingStart();
            }
            realW = getWidth() - getPaddingStart() - getPaddingEnd();
        }else{
            if (isRTL()){
                rectF.left = getWidth() - getPaddingLeft();
                rectF.right = getWidth() - getPaddingLeft();
            } else {
                rectF.left = getPaddingLeft();
                rectF.right = getPaddingLeft();
            }
            realW = getWidth() - getPaddingLeft() - getPaddingRight();
        }

        perDbWidth = realW*1.0f/maxValue;
        setCurrentValue(currentValue);
        initBitmap2();
    }

    private int colorBg =MarqueeThemeUtil.getSeekbarColorBgDefault();
    boolean isFromSetEnable = false;

    public void setEqEnable(int id, boolean isEnable){
        isFromSetEnable = true;
        colorBg = isEnable?id: MarqueeThemeUtil.getSeekbarColorBgUnEnableDefault();
        initBitmap2();
        isChangeTop = true;
        invalidate();
    }


    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap)
    {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (resizedBitmap != bitmap && bitmap != null && !bitmap.isRecycled())
        {
            bitmap.recycle();
            bitmap = null;
        }

        return resizedBitmap;
    }

    private void initBitmap2() {
        if(getWidth() <= 0 || getHeight() <= 0)
            return;
//        if(colorBitmap!=null){
//            colorBitmap.recycle();
//            colorBitmap = null;
//        }
        try {
            pointBit = BitmapFactory.decodeResource(getResources(), isEnable?MarqueeThemeUtil.getArcPoint():MarqueeThemeUtil.getUnEnablePointBitBg());
            Bitmap colorBitmap;
            colorBitmap = rotaingImageView(90,BitmapFactory.decodeResource(getResources(), colorBg));
            if(MarqueeThemeUtil.getColorAccentFromOtherApp()!=0&&isEnable){
               colorBitmap=MarqueeColorUtils.tintBitmap(colorBitmap,MarqueeThemeUtil.getColorAccentFromOtherApp());
            }
            Matrix matrix = new Matrix();
            matrix.postScale( realW*1.0f/colorBitmap.getWidth(),1.0f);
            targetColorBit = Bitmap.createBitmap(colorBitmap, 0, 0,colorBitmap.getWidth() ,colorBitmap.getHeight(), matrix, false);
            colorBitmap.recycle();
            colorBitmap = null;
            Bitmap bg = rotaingImageView(90,BitmapFactory.decodeResource(getResources(), MarqueeThemeUtil.getSeekbarColorBgUnEnableDefault()));
            targetBgBit = Bitmap.createBitmap(bg,0,0,bg.getWidth(),bg.getHeight(), matrix, false);
            bg.recycle();
            bg = null;

            centerY = (getHeight() - targetColorBit.getHeight())/2;
        } catch (OutOfMemoryError e){
            e.printStackTrace();
        }
//        colorBitmap.recycle();
//        colorBitmap = null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        isVib = false;
    }

    public int getValue(){
        return currentValue;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            if(targetBgBit != null)//绘制背景图
                canvas.drawBitmap(targetBgBit, getPaddingStartcompatible(),centerY, null);
            if (!isFromSetEnable) {
                if (isRTL()){
                        rectF.left = rectF.left + mAddX < getPaddingEndcompatible()? getPaddingEndcompatible(): rectF.left + mAddX > realW + getPaddingEndcompatible()? realW+getPaddingEndcompatible():rectF.left+mAddX;
                    double v = (realW + getPaddingEndcompatible() - rectF.left) / perDbWidth;
                    currentValue = (int) (Math.rint(v));
                } else {
                        rectF.right = rectF.right + mAddX < getPaddingStartcompatible()? getPaddingStartcompatible(): rectF.right + mAddX > realW + getPaddingStartcompatible()?realW+getPaddingStartcompatible():rectF.right + mAddX;
                    double v = (rectF.right - getPaddingStartcompatible()) / perDbWidth;
                    currentValue = (int) (Math.rint(v));
                }
                if (onSeekBarChangeListener != null) {
                    if (isChangeTop){
                        onSeekBarChangeListener.onSeekBarChange(currentValue, hasTouchFromUser, isVib);
                    }
                    isVib = true;
                }
            }
            hasTouchFromUser = false;
            if(targetColorBit != null) {//裁剪有色进度条
                canvas.save();
                canvas.clipRect(rectF);
                canvas.drawBitmap(targetColorBit,  getPaddingStartcompatible(),centerY, null);
                canvas.restore();
            }
            if(pointBit != null) {//绘制滑动块
                if (isRTL()){
                    canvas.drawBitmap(pointBit, rectF.left - pointBit.getWidth()/2, (getHeight()-pointBit.getHeight())/2, null);
                } else {
                    canvas.drawBitmap(pointBit, rectF.right - pointBit.getWidth()/2, (getHeight()-pointBit.getHeight())/2, null);
                }
            }
            isFromSetEnable = false;
            isChangeTop = false;
        } catch (RuntimeException e){
        }
    }

    public int getPaddingStartcompatible(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            return getPaddingStart();
        }else{
            return getPaddingLeft();
        }
    }

    public int getPaddingEndcompatible(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            return getPaddingEnd();
        }else{
            return getPaddingRight();
        }
    }

    public void setEnable(boolean _isEnable){
        isEnable = _isEnable;
    }

    //设置 预设值
    public void setCurrentValue(int _dbValue){
        isVib = false;
        currentValue = _dbValue;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            if (isRTL()){
                rectF.left = getPaddingEnd() + realW - (float)(_dbValue*perDbWidth);
            } else {
                rectF.right = getPaddingStart() + (float)(_dbValue*perDbWidth);
            }
        }else{
            if (isRTL()){
                rectF.left = getPaddingRight() + realW - (float)(_dbValue*perDbWidth);
            } else {
                rectF.right = getPaddingLeft() + (float)(_dbValue*perDbWidth);
            }
        }

        mAddX = 0;
        isChangeTop = true;
        invalidate();
    }

    public void setMaxValue(int volValue){
        maxValue = volValue;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isEnable){
            return false;
        }
        hasTouchFromUser = true;
        float x = event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float minWidth;
                float maxWidth;
                if (isRTL()){
                    minWidth = rectF.left - pointBit.getWidth() * 2 / 3;//TODO:
                    maxWidth = rectF.left + pointBit.getWidth() * 2 / 3;
                } else {
                    minWidth = rectF.right - pointBit.getWidth() * 2 / 3;//TODO:
                    maxWidth = rectF.right + pointBit.getWidth() * 2 / 3;
                }
                if (x < minWidth || x > maxWidth){
                    getParent().requestDisallowInterceptTouchEvent(false);//父拦截
                    return false;
                }

//                pointBit = BitmapFactory.decodeResource(getResources(), t.getVolumeThumeBright());
                mLastX = x;
                isChangeTop = true;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                mAddX = x - mLastX;
                mLastX = x;
                isChangeTop = true;
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
//                pointBit = BitmapFactory.decodeResource(getResources(), t.getArcPoint());
                isChangeTop = true;
                invalidate();
                break;
        }
        return true;
    }

    public interface OnSeekBarChangeListener{
        void onSeekBarChange(int dbValue, boolean fromUser, boolean isVib);
    }

    /**
     * 是否为阿拉伯布局
     * @return
     */
    private boolean isRTL() {
//        ApplicationInfo applicationInfo = BaseApplication.getInstance().getApplicationInfo();
//        boolean hasRtlSupport = (applicationInfo.flags & FLAG_SUPPORTS_RTL) == FLAG_SUPPORTS_RTL;

        if(Build.VERSION.SDK_INT>=17){
            boolean isRtl = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL;
            return isRtl;
        }else{
            return false;
        }
    }

}
