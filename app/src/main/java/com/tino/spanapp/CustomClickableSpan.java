package com.tino.spanapp;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

public class CustomClickableSpan extends ClickableSpan {

    private View.OnClickListener mSpanClickListener;
    private View.OnClickListener mUnSpanClickListener;
    private boolean mClickedSpan;
    private int mTextColor = -1;
    private boolean mUnderLine;

    public CustomClickableSpan(TextView textView, View.OnClickListener spanClickListener, View.OnClickListener unSpanClickListener) {
        mSpanClickListener = spanClickListener;
        mUnSpanClickListener = unSpanClickListener;
        textView.setMovementMethod(CustomLinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickedSpan) {
                    mClickedSpan = false;
                    return;
                }
                if (null != mUnSpanClickListener) mUnSpanClickListener.onClick(view);
            }
        });
    }

    public CustomClickableSpan(TextView textView, @ColorInt int textColor, boolean underLine, View.OnClickListener spanClickListener, View.OnClickListener unSpanClickListener) {
        this(textView, spanClickListener, unSpanClickListener);
        mTextColor = textColor;
        mUnderLine = underLine;
    }

    @Override
    public void onClick(@NonNull View view) {
        mClickedSpan = true;
        if (null != mSpanClickListener) mSpanClickListener.onClick(view);
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        if (mTextColor != -1) {
            ds.setColor(mTextColor);
        }
        ds.setUnderlineText(mUnderLine);
    }
}