package com.tino.spanapp;

import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

public class CustomLinkMovementMethod extends LinkMovementMethod {

    public static CustomLinkMovementMethod getInstance() {
        return new CustomLinkMovementMethod();
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        // 因为TextView没有点击事件，所以点击TextView的非富文本时，super.onTouchEvent()返回false；
        // 此时可以让TextView的父容器执行点击事件；
        boolean isConsume = super.onTouchEvent(widget, buffer, event);
        Log.i("iiiiiiiiiiii", isConsume + "");
        if (!isConsume && event.getAction() == MotionEvent.ACTION_UP) {
            ViewParent parent = widget.getParent();
            if (parent instanceof ViewGroup) {
                // 获取被点击控件的父容器，让父容器执行点击；
                ((ViewGroup) parent).performClick();
            }
        }
        return isConsume;
    }
}