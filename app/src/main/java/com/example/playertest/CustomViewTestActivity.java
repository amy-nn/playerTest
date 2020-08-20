package com.example.playertest;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Looper;

public class CustomViewTestActivity extends AppCompatActivity {

    private CustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_test);

        customView = findViewById(R.id.custom_view);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f,360f);
        valueAnimator.setDuration(10*1000);
//        valueAnimator.setRepeatCount(1);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //一直变化的角度
                float value = (float) animation.getAnimatedValue();
                //设置角度给画布
                customView.setAngle(value);
                //刷新画布(用于主线程）
                if(Looper.getMainLooper().getThread() == Thread.currentThread()){
                    customView.invalidate();
                }
                else
                    //子线程中的刷新画布方法
                    customView.postInvalidate();

            }
        });

        //启动属性动画
        valueAnimator.start();
    }
}
