package com.example.playertest.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 事件中间层
 */
public class MiddleTouchEvent extends LinearLayout {

    public MiddleTouchEvent(Context context) {
        super(context);
    }

    public MiddleTouchEvent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 事件分发(我和父容器的关系）
     * @param ev
     * @return true:不向下分发
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("amy", "dispatchTouchEvent: "+"middle");
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 事件拦截
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("amy", "onInterceptTouchEvent: "+"middle");
        return false;
    }

    /**
     * 事件处理
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("amy", "onTouchEvent: "+"middle");
        return super.onTouchEvent(event);
    }
}
