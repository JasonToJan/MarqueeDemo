package com.coocent.marquee;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Locale;

/**
 * Custom MarqueeSmallCircleView
 * Created by Coocent on 2019/01/26
 */
public class MarqueeSmallCircleView extends View {

    private int[] colors;
    private Paint paint = new Paint();
    private int radius;

    public MarqueeSmallCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        radius = dp2px(context, 4);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (colors != null){
            int x = 0;
            int length = colors.length;
            if (length >=2 && length <= 4){
                x = (5 - length) * radius + radius/2;
            }
//            if (length == 2){
//                x = 3 * radius + radius/2;
//            } else if (length == 3){
//                x = 2 * radius + radius/2;
//            } else if (length == 4){
//                x = radius + radius/2;
//            }
            for (int i=0;i<colors.length;i++){
                if (i > 4) break;
                paint.setColor(colors[i]);
                x += radius * 2 + radius /2;
//                int x =  radius / 2 + radius + i*2*radius + radius;
//                if (isRTL()){
//                    x = getWidth() - x;
//                }
                canvas.drawCircle(x, getHeight() / 2,radius,paint);
            }
        }
    }

    private int dp2px(@NonNull Context context, int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }

    public void setColors(int[] colors){
        this.colors = colors;
        invalidate();
    }

    /**
     * 是否为阿拉伯布局
     * @return
     */
    private boolean isRTL() {
//        ApplicationInfo applicationInfo = BaseApplication.getInstance().getApplicationInfo();
//        boolean hasRtlSupport = (applicationInfo.flags & FLAG_SUPPORTS_RTL) == FLAG_SUPPORTS_RTL;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL;
        }else{
            return false;
        }
    }
}
