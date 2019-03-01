package com.coocent.marquee;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import static com.coocent.marquee.MarqueeConstant.MARQUEE_SPEED_VALUE;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_WIDTH_VALUE;


/**
 * Custom Marquee
 * Created by Coocent on 2019/01/12
 */
public class MarqueeSweepGradientView extends View {

	private Context context;
	private float x = 530;
	private float y = 400;
	private int baseRotate;
	private Paint mPaint;
	private Path path = new Path();
	private Shader mShader;
	private Matrix mMatrix = new Matrix();
	private RectF rectF = new RectF();
	private float effectiveWidth;
	private float effectiveRadius;
	private float mRotate;
	private int[] colors;
	private Path fulPath = new Path();
	private RectF fullRectF = new RectF();

	public MarqueeSweepGradientView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MarqueeSweepGradientView);
		baseRotate = a.getInt(R.styleable.MarqueeSweepGradientView_baseRotate, MARQUEE_SPEED_VALUE);
		effectiveWidth = dp2px(a.getInt(R.styleable.MarqueeSweepGradientView_effectiveWidth, MARQUEE_WIDTH_VALUE));
		a.recycle();
		effectiveRadius = 0;
		colors = new int[]{Color.parseColor("#01000000"),Color.parseColor("#01000000")};
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setFilterBitmap(true);
		initShader();
	}

	private void initShader(){
		mShader = new SweepGradient(x, y, colors, null);
		mPaint.setShader(mShader);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		rectF.left = effectiveWidth;
		rectF.top = effectiveWidth;
		rectF.right = getWidth() - effectiveWidth;
		rectF.bottom = getHeight() - effectiveWidth;

		fullRectF.left = 0;
		fullRectF.top = 0;
		fullRectF.right = getWidth();
		fullRectF.bottom = getHeight();

		x = getWidth() / 2;
		y = getHeight() / 2;
		initShader();
		path.reset();
		path.addRoundRect(rectF,effectiveRadius,effectiveRadius,Path.Direction.CW);
		fulPath.reset();
		fulPath.addRoundRect(fullRectF,effectiveRadius,effectiveRadius,Path.Direction.CW);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		canvas.clipPath(fulPath,Region.Op.DIFFERENCE);
		canvas.drawColor(Color.BLACK);
		canvas.restore();

		canvas.save();
		canvas.clipPath(path,Region.Op.DIFFERENCE);
		mMatrix.setRotate(mRotate, x, y);
		mShader.setLocalMatrix(mMatrix);
		canvas.drawRoundRect(fullRectF,effectiveRadius,effectiveRadius,mPaint);
		canvas.restore();

		mRotate += baseRotate;
		if (mRotate >= 360) {
			mRotate = 0;
		}
		invalidate();
	}

	public void setBuilder(int radius, int width, int speed, int[] color){
        effectiveRadius = dp2px(radius);
        effectiveWidth = dp2px(width);
        this.baseRotate = speed;
        rectF.left = effectiveWidth;
        rectF.top = effectiveWidth;
        rectF.right = getWidth() - effectiveWidth;
        rectF.bottom = getHeight() - effectiveWidth;
        path.reset();
        path.addRoundRect(rectF,effectiveRadius,effectiveRadius,Path.Direction.CW);
		fulPath.reset();
		fulPath.addRoundRect(fullRectF,effectiveRadius,effectiveRadius,Path.Direction.CW);
        if (color == null){
			color = new int[] {Color.WHITE, Color.parseColor("#"+MarqueeThemeUtil.getThemeColor()), Color.WHITE};
//            color = new int[] {Color.WHITE, t.getBrightColor(), Color.WHITE};
        }
		colors = color;
        mShader = new SweepGradient(x, y, colors, null);
        mPaint.setShader(mShader);
        invalidate();
	}

	/**
	 * 设置速度
	 * @param speed
	 */
	public void setBaseRotate(int speed){
		this.baseRotate = speed;
		invalidate();
	}

	public void setColors(int[] color){
        if (color == null){
            color = new int[] {Color.WHITE, Color.parseColor("#"+MarqueeThemeUtil.getThemeColor()), Color.WHITE};
//            color = new int[] {Color.WHITE, t.getBrightColor(), Color.WHITE};
        }
		colors = color;
        mShader = new SweepGradient(x, y, colors, null);
		mPaint.setShader(mShader);
		invalidate();
	}

	public void setWidth(int width){
		effectiveWidth = dp2px(width);
		rectF.left = effectiveWidth;
		rectF.top = effectiveWidth;
		rectF.right = getWidth() - effectiveWidth;
		rectF.bottom = getHeight() - effectiveWidth;
		path.reset();
		path.addRoundRect(rectF,effectiveRadius,effectiveRadius,Path.Direction.CW);
		invalidate();
	}

	public void setRadius(int radius){
		effectiveRadius = dp2px(radius);
		path.reset();
		path.addRoundRect(rectF,effectiveRadius,effectiveRadius,Path.Direction.CW);
		fulPath.reset();
		fulPath.addRoundRect(fullRectF,effectiveRadius,effectiveRadius,Path.Direction.CW);
		invalidate();
	}

	private int dp2px(int dp){
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
	}
}