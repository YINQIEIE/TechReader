package com.yq.eie.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.yq.eie.R;

/**
 * Created by yinqi on 2017/9/14.
 */

public class SideNavigationView extends View {

    // 26个字母
    public static String[] items = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};

    int itemHeight = 30;//最小高度 30 px
    private Paint mPaint;
    private int touchPos = -1;//触摸位置
    private TextView tv;

    public SideNavigationView(Context context) {
        this(context, null);
    }

    public SideNavigationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private void init() {
        setBackgroundColor(getResources().getColor(R.color.sideNavColor));
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.side_nav_text_size));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int averageItemHeight = getMeasuredHeight() / items.length;
        itemHeight = itemHeight > averageItemHeight ? itemHeight : averageItemHeight;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchY = event.getY();
        touchPos = getPosition(touchY);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                invalidate();
                if (null != tv) {
                    tv.setText(items[touchPos]);
                    tv.setVisibility(VISIBLE);
                }
                if (null != mListener)
                    mListener.onTouch(touchPos, items[touchPos]);
                return true;

            case MotionEvent.ACTION_MOVE:
                invalidate();

                if (null != tv)
                    tv.setText(items[touchPos]);

                if (null != mListener)
                    mListener.onMoving(touchPos, items[touchPos]);
                return true;

            case MotionEvent.ACTION_UP:
                if (null != tv)
                    tv.setVisibility(GONE);
                return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 根据触摸位置计算对应的点击索引
     *
     * @param touchY
     * @return
     */
    private int getPosition(float touchY) {
        if (touchY < 10) return 0;
        int y = (int) touchY - 10;
        return y / itemHeight > items.length ? items.length : y / itemHeight;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float baseLineY = itemHeight / 2 - (fontMetrics.descent + fontMetrics.ascent) / 2;
        for (int i = 0; i < items.length; i++) {
            if (i == touchPos)
                mPaint.setColor(getResources().getColor(R.color.themeColor));
            else
                mPaint.setColor(Color.WHITE);
            canvas.drawText(items[i], getWidth() / 2, 10 + baseLineY + i * itemHeight, mPaint);
        }
    }

    public void setTv(TextView tv) {
        this.tv = tv;
        tv.setVisibility(GONE);
    }

    private GestureListener mListener;

    public void setMovingListener(GestureListener mListener) {
        this.mListener = mListener;
    }

    public interface GestureListener {

        void onMoving(int pos, String s);

        void onTouch(int pos, String s);
    }

    public void setNowChar(String c) {
        touchPos = -1;
        for (int i = 0; i < items.length; i++) {
            if (c.equals(items[i])) {
                touchPos = i;
                break;
            }
        }
        invalidate();
    }

}
