package com.example.playertest.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 上级事件
 */
public class TopTuchEvent extends LinearLayout {


    public TopTuchEvent(Context context) {
        super(context);
    }

    public TopTuchEvent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 事件分发
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("amy", "dispatchTouchEvent: "+"top");
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 拦截事件
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("amy", "onInterceptTouchEvent: "+"top");
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 事件处理
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("amy", "onTouchEvent: "+"top");
        return super.onTouchEvent(event);
    }
}
