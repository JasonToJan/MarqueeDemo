package com.coocent.marquee;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MarqueeSwitchButton extends AppCompatImageView implements View.OnTouchListener{

    private boolean isChecked;
    private int mOnBg, mOffBg;
    private int width,height;
    private String mSavePreferenceTitle = "setting_preference";
    private String mPreferenceTitle = "preference_title";
    private boolean isSavePreference = false;
    private OnChangedListener onChangeListener = null;
    private Context mContext;
    private SharedPreferences mSharedPreference;

    public MarqueeSwitchButton(Context context){
        super(context);
        init(context, null);
    }

    public MarqueeSwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MarqueeSwitchButton(Context context, AttributeSet attrs, int defStyleAttr, boolean isChecked) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if(attrs != null) {
            TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.SwitchButton);
            mOnBg = t.getResourceId(R.styleable.SwitchButton_onImage, R.drawable.marquee_home_button1_open);
            mOffBg = t.getResourceId(R.styleable.SwitchButton_offImage, R.drawable.marquee_home_button_no);
            isSavePreference = t.getBoolean(R.styleable.SwitchButton_savePreference, false);
            mPreferenceTitle = t.getString(R.styleable.SwitchButton_preferenceTitle);
            t.recycle();
            Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), mOnBg);
            width = mBitmap.getWidth();
            height = mBitmap.getHeight();
            mBitmap.recycle();
            mBitmap = null;
        }
        mContext = context;
        setOnTouchListener(this);
        if(isSavePreference && mPreferenceTitle != null
                && !mPreferenceTitle.isEmpty()){
            mSharedPreference = mContext.getSharedPreferences(mSavePreferenceTitle, Context.MODE_PRIVATE);
            isChecked = mSharedPreference.getBoolean(mPreferenceTitle, true);
        }
    }
    
    /**
     * 初始化开跟关
     * */
    public void setBitmap(int _on,int _off){
        mOnBg = _on;
        mOffBg = _off;
        invalidate();
    }

    public void setOnBitmap(int _on){
        mOnBg = _on;
        invalidate();
    }

    public void setOffBitmap(int _off){
        mOffBg = _off;
        invalidate();
    }
    public void setSavePreference(boolean _savePreference){
        isSavePreference = _savePreference;
    }

    public boolean isSavePreference(){
        return isSavePreference;
    }

    public void setSavePreferenceTitle(String _mSavePreferenceTitle) {
        this.mSavePreferenceTitle = _mSavePreferenceTitle;
    }

    public void setPreferenceTitle(String _mPreferenceTitle) {
        this.mPreferenceTitle = _mPreferenceTitle;
    }

    /**
     * 设置回调接口
     * 当开关按钮状态改变  发出消息
     * */
    public void setOnchangeListener(OnChangedListener l){
        this.onChangeListener = l;
    }
    
    public void setIsShow(boolean isShow){
        this.isChecked = isShow;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*if(width > 0 && height > 0) {
            setMeasuredDimension(width, height+10);
        }else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isChecked){
            if(mOnBg != 0){
                setImageResource(mOnBg);
            }
        }else{
            if(mOffBg != 0){
                setImageResource(mOffBg);
            }
        }
//        Log.e("TAGF","btn_onDraw");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        try {
            switch(event.getAction()){
                case MotionEvent.ACTION_UP:
                    isChecked = !isChecked;//?false:true;
                    if(onChangeListener != null){
                        onChangeListener.OnChanged(isChecked);
                    }
                    if(isSavePreference && mSharedPreference != null){
                        SharedPreferences.Editor editor = mSharedPreference.edit();
                        editor.putBoolean(mPreferenceTitle, isChecked);
                        editor.apply();
                    }
                    break;
            }
            invalidate();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return true;
    }
    
    public boolean isChecked(){
        return isChecked;
    }
    
    public interface OnChangedListener{
        public void OnChanged(boolean CheckState);
    }
}