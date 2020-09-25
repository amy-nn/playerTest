package com.example.playertest;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * drawable文件夹下的动画是帧动画（一般用于加载页面的情况）
 * 常用快捷键：shift+回车 下一行
 *           command+alt+L  对齐格式
 *
 */
public class Main5Activity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    AnimationDrawable animation;
    Button button;
    Button buttonRotate;
    Button buttonScale;
    Button buttonAlpha;
    Button buttonGroup;
    Button buttonValueTranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        imageView = findViewById(R.id.image_1);
        button = findViewById(R.id.btn_translate);
        buttonRotate = findViewById(R.id.btn_rotate);
        buttonScale = findViewById(R.id.btn_scale);
        buttonAlpha = findViewById(R.id.btn_alpha);
        buttonGroup = findViewById(R.id.btn_group);
        buttonValueTranslate = findViewById(R.id.btn_vAnimTranslate);

        animation = (AnimationDrawable) imageView.getBackground();

        button.setOnClickListener(this);
        buttonRotate.setOnClickListener(this);
        buttonScale.setOnClickListener(this);
        buttonAlpha.setOnClickListener(this);
        buttonGroup.setOnClickListener(this);
        buttonValueTranslate.setOnClickListener(this);
    }


    public void startAnim(View view) {
        animation.start();

        animation.start();
    }

    public void stopAnim(View view) {
        animation.stop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_translate:

                TranslateAnimation translateAnimation =
                        (TranslateAnimation) AnimationUtils.loadAnimation(this,R.anim.image_anim);
                //动画结束后不回到起点
//                translateAnimation.setFillAfter(true);
//                translateAnimation.setRepeatCount(1);
                //REVERSE 从终点重复   RESTART 回到起点重复
//                translateAnimation.setRepeatMode(TranslateAnimation.RESTART);
                imageView.startAnimation(translateAnimation);

                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        Log.d("amy", "onAnimationStart: ");
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Log.d("amy", "onAnimationEnd: ");
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        //设置了重复数量才会走到这个方法中
                        Log.d("amy", "onAnimationRepeat: ");
                    }
                });
                break;
            case R.id.btn_rotate:
                RotateAnimation rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(this,R.anim.image_anim_rotate);
                imageView.startAnimation(rotateAnimation);
                break;
            case R.id.btn_scale:
                ScaleAnimation animation = (ScaleAnimation) AnimationUtils.loadAnimation(this,R.anim.image_anim_scale);
                animation.setDuration(2000);
                imageView.startAnimation(animation);
                break;
            case R.id.btn_alpha:
                AlphaAnimation alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(this,R.anim.image_anim_alpha);
                alphaAnimation.setDuration(2000);
                imageView.startAnimation(alphaAnimation);
                break;
            case R.id.btn_group:
                AnimationSet set = new AnimationSet(true);
                AlphaAnimation alphaAnimation1 = (AlphaAnimation) AnimationUtils.loadAnimation(this,R.anim.image_anim_alpha);
                alphaAnimation1.setDuration(2000);

                ScaleAnimation scaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(this,R.anim.image_anim_scale);
                scaleAnimation.setDuration(2000);
                set.addAnimation(alphaAnimation1);
                set.addAnimation(scaleAnimation);
                imageView.startAnimation(set);
                break;
            case R.id.btn_vAnimTranslate:
                ValueAnimator valueAnimator = ValueAnimator.ofInt(0,100);
                valueAnimator.setDuration(2000);
                valueAnimator.setInterpolator(new LinearInterpolator()); //自由落体插值器
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                        layoutParams.leftMargin = (int) animation.getAnimatedValue();
//                        layoutParams.bottomMargin = (int) animation.getAnimatedValue();
                        imageView.setLayoutParams(layoutParams);
                    }
                });
                valueAnimator.start();
                break;
        }
    }
}
