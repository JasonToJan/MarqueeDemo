package com.coocent.marquee;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class MarqueeSwitchButton2 extends View implements View.OnTouchListener {

    private boolean NOWSHOW = false;
    private int onBg, offBg;
    private int width, height;
    private OnChangedListener onChangeListener = null;
    Bitmap onBgTint;
    Drawable selectedDrawableTint;

    public MarqueeSwitchButton2(Context context) {
        super(context);
        init();
    }

    public MarqueeSwitchButton2(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SwitchButton2);
        onBg = a.getResourceId(R.styleable.SwitchButton2_onImage2,
                R.drawable.marquee_eq_button_on_bg);
        offBg = a.getResourceId(R.styleable.SwitchButton2_offImage2,
                R.drawable.marquee_eq_button_off_bg);
        NOWSHOW = a.getBoolean(R.styleable.SwitchButton2_defaultState, true);


        Drawable bgSelectedDrawable = getResources().getDrawable(onBg);
        selectedDrawableTint = MarqueeColorUtils.tintDrawable(bgSelectedDrawable,ColorStateList.valueOf(MarqueeThemeUtil.getColorAccentFromOtherApp()));
        onBgTint = MarqueeColorUtils.drawableToBitmap(selectedDrawableTint);

        width = onBgTint.getWidth();
        height = onBgTint.getHeight();
        onBgTint.recycle();
        onBgTint = null;
        a.recycle();
        init();


    }

    private void init() {
        setOnTouchListener(this);
    }

    /**
     * 设置回调接口 当开关按钮状态改 发出消息
     */
    public void setOnchangeListener(OnChangedListener l) {
        this.onChangeListener = l;
    }

    public void setIsShow(boolean isShow) {
        this.NOWSHOW = isShow;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, height);
        // super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (NOWSHOW) {
//            setBackgroundResource(onBg);
            if (selectedDrawableTint != null) {
                if(Build.VERSION.SDK_INT>=16){
                    setBackground(selectedDrawableTint);
                }
            }
        } else {
            setBackgroundResource(offBg);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (NOWSHOW) {
                    NOWSHOW = false;
                } else {
                    NOWSHOW = true;
                }
                if (onChangeListener != null) {
                    onChangeListener.OnChanged(NOWSHOW);
                }
                break;
        }
        invalidate();
        return true;
    }

    public boolean isShow() {
        return NOWSHOW;
    }

    public interface OnChangedListener {
        public void OnChanged(boolean CheckState);
    }
}