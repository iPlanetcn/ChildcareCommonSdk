package com.childcare.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 代金券View
 * <p>
 * <p>设置高亮颜色 {@link #setAccentColor(int)}</p>
 *
 * @author john
 * @since 2017-08-14
 */
public class CouponView extends FrameLayout {
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 小圆半径
     */
    private float mRadius;
    /**
     * 百分比
     */
    private float mPercent;
    /**
     * 绘制三角形的个数
     */
    private int mTriangleCount;
    /**
     * 偏移量
     */
    private float mOffset;
    /**
     * 高亮色(右边方框的填充色)
     */
    private int mAccentColor;
    /**
     * 低亮色(圆的颜色)
     */
    private int mDecentColor;
    /**
     * 主内容颜色(右边方框的填充色)
     */
    private int mContentColor;


    public CouponView(Context context) {
        this(context, null);
    }

    public CouponView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CouponView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CouponView, 0, 0);

        mAccentColor = a.getColor(R.styleable.CouponView_couponAccentColor,
                ContextCompat.getColor(context, R.color.orange));
        mDecentColor = a.getColor(R.styleable.CouponView_couponDecentColor,
                ContextCompat.getColor(context, R.color.grey));
        mContentColor = a.getColor(R.styleable.CouponView_couponContentColor,
                ContextCompat.getColor(context, R.color.white));
        mRadius = a.getDimension(R.styleable.CouponView_couponRadius, 16);
        mPercent = a.getFloat(R.styleable.CouponView_couponPercent, 0.7f);
        a.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setBackgroundColor(Color.TRANSPARENT);
    }

    private void setDecentPaint() {
        mPaint.setColor(mDecentColor);
    }

    private void setAccentPaint() {
        mPaint.setColor(mAccentColor);
    }

    private void setContentPaint() {
        mPaint.setColor(mContentColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleCount = (int) ((h - 2 * mRadius) / mRadius);
        mOffset = (h - 2 * mRadius) % mRadius / mTriangleCount;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x = mPercent * getWidth();
        drawRoundRectLeft(canvas, x);
        drawRoundRectRight(canvas, x);
        drawCircles(canvas, x);
        drawTriangles(canvas, x);
    }

    /**
     * 绘制锯齿三角形
     */
    private void drawTriangles(Canvas canvas, float x) {
        setAccentPaint();
        for (int i = 0; i < mTriangleCount; i++) {
            canvas.drawPath(calculateTriangle(x, mRadius + i * (mRadius + mOffset)), mPaint);
        }

    }

    /**
     * 计算单个三角形路径
     *
     * @param firstPointX 第一个点的X坐标
     * @param startPointY 第一个点的Y坐标
     * @return 闭合的三角形路径PATH
     */
    private Path calculateTriangle(float firstPointX, float startPointY) {
        Path path = new Path();
        path.moveTo(firstPointX, startPointY);
        path.lineTo(firstPointX, startPointY + mRadius);
        path.lineTo(firstPointX - mRadius / 2, startPointY + mRadius / 2);
        path.close();
        return path;
    }

    /**
     * 绘制圆角方形(右边)
     */
    private void drawRoundRectRight(Canvas canvas, float x) {
        setAccentPaint();
        canvas.drawRoundRect(new RectF(x, 0, getWidth(), getHeight()), mRadius, mRadius, mPaint);
    }

    /**
     * 绘制圆角方形(左边)
     */
    private void drawRoundRectLeft(Canvas canvas, float x) {
        setContentPaint();
        canvas.drawRoundRect(new RectF(0, 0, x, getHeight()), mRadius, mRadius, mPaint);
    }

    /**
     * 绘制圆形
     */
    private void drawCircles(Canvas canvas, float x) {
        setDecentPaint();
        canvas.drawCircle(x, 0, mRadius, mPaint);
        canvas.drawCircle(x, getHeight(), mRadius, mPaint);
    }

    /**
     * 设置高亮色(右边区域的颜色)
     */
    public void setAccentColor(int accentColor) {
        mAccentColor = accentColor;
        invalidate();
    }
}
