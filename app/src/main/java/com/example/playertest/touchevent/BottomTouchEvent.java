package com.example.playertest.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;

public class BottomTouchEvent extends AppCompatTextView {

    public BottomTouchEvent(Context context) {
        super(context);
    }

    public BottomTouchEvent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 事件分发
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("amy", "dispatchTouchEvent: "+"控件");
        return super.dispatchTouchEvent(event);
    }


    /**
     * 事件处理
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("amy", "onTouchEvent: "+"控件");
        return super.onTouchEvent(event);
    }
}
