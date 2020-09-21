package com.tino.spanapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

public class CustomReplacementSpan extends ReplacementSpan {

    private Context mContext;
    private int mSize;
    private int mBgColor;
    private int mTextColor;
    private int mRadius;
    private Bitmap mBitmap;
    private int mDrawableSpace;
    private float mBaseTextSize;
    private float mTextSize;

    /**
     * @param bgColor   背景色
     * @param textColor 文本颜色
     * @param radius    背景圆角大小(单位:dp)
     * @param textSize  文字大小(单位:sp)
     */
    public CustomReplacementSpan(TextView textView, @ColorInt int bgColor, @ColorInt int textColor, int radius, float textSize) {
        mContext = textView.getContext();
        mBgColor = bgColor;
        mTextColor = textColor;
        mRadius = Utils.dip2px(mContext, radius);
        mTextSize = Utils.sp2px(mContext, textSize);
        mBaseTextSize = textView.getTextSize();
    }

    /**
     * @param drawable        图标资源
     * @param drawableSize    图标大小(单位:dp)
     * @param drawablePadding 图标与文本间距(单位:dp)
     */
    public CustomReplacementSpan(TextView textView, @DrawableRes int drawable, int drawableSize, int drawablePadding, @ColorInt int bgColor, @ColorInt int textColor, int radius, float textSize) {
        this(textView, bgColor, textColor, radius, textSize);
        Bitmap bitmap = Utils.getBitmap(mContext, drawable);
        if (null != bitmap) {
            mBitmap = Bitmap.createScaledBitmap(bitmap, Utils.dip2px(mContext, drawableSize), Utils.dip2px(mContext, drawableSize), true);
            mDrawableSpace = mBitmap == null ? 0 : mBitmap.getWidth() + Utils.dip2px(mContext, drawablePadding);
        }
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        paint.setTextSize(mTextSize);
        mSize = (int) (paint.measureText(text, start, end) + 2 * mRadius + mDrawableSpace);
        return mSize;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        paint.setColor(mBgColor);
        paint.setAntiAlias(true);
        paint.setTextSize(mBaseTextSize);
        float ascent = y + paint.ascent();
        float descent = y + paint.descent();
        RectF oval = new RectF(x, ascent, x + mSize, descent);
        canvas.drawRoundRect(oval, mRadius, mRadius, paint);
        paint.setColor(mTextColor);
        paint.setTextSize(mTextSize);
        if (null != mBitmap) {
            canvas.drawBitmap(mBitmap, x + mRadius, ascent + (descent - ascent - mBitmap.getHeight()) / 2, paint);
        }
        canvas.drawText(text, start, end, x + mRadius + mDrawableSpace, (descent - paint.getFontMetricsInt().descent + ascent - paint.getFontMetricsInt().ascent) / 2, paint);
    }
}