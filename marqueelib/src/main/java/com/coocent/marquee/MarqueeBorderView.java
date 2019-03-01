package com.coocent.marquee;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Custom MarqueeBorderView
 * Created by Coocent on 2019/01/26
 */
public class MarqueeBorderView extends View {

    private Paint paint = new Paint();

    public MarqueeBorderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setStrokeWidth(dp2px(context, 3));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        canvas.drawLine(0, 0, getWidth(), 0, paint);
        canvas.drawLine(0, 0, 0, getHeight(), paint);
        canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), paint);
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), paint);
        super.onDraw(canvas);
    }

    private int dp2px(@NonNull Context context, int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }
}
