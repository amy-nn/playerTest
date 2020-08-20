package com.example.playertest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * 自定义视图
 * 绘制一个圆形，绘制一张图片，图片不断旋转，绘制扇形
 * 要点:
 * 1.解决wrap_content属性无效问题
 * 2.事件分发时候：action_up做的处理要在action_cancel做同样的处理
 * 3.编写自定义属性
 *
 */
public class CustomView extends View {

    private Paint cPaint;
    private Paint iPaint;
    private Paint lPaint;
    private int centerX;
    private int centerY;
    private int centerRadius;
    private int defaultWidth = 80; //默认宽
    private int defaultHeight = 80; //默认高
    private int defaultRadius = 300; //默认半径
    private float angle = 90;

    private float radius;
    private int color;
    private int shape;


    public void setAngle(float angle){
        this.angle = angle;
    }

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initParames(context,attrs);
        initPaint();
    }


    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {

        cPaint = new Paint();
        cPaint.setColor(color);
        cPaint.setStyle(Paint.Style.FILL);//实心
        cPaint.setAntiAlias(true);//防锯齿
        cPaint.setDither(true);//防抖动

        iPaint = new Paint();
        iPaint.setAntiAlias(true);
        iPaint.setDither(true);

        lPaint = new Paint();
        lPaint.setAntiAlias(true);
        lPaint.setDither(true);
        lPaint.setColor(Color.RED);
        lPaint.setStyle(Paint.Style.STROKE);
        lPaint.setStrokeWidth(5.0f);
    }

    /**
     * 初始化属性
     */
    private void initParames(Context context,AttributeSet attrs) {

        if(attrs != null){

            TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomView);
            radius = typedArray.getDimension(R.styleable.CustomView_radius, defaultRadius);
            color = typedArray.getColor(R.styleable.CustomView_centerColor, Color.BLUE);
            shape = typedArray.getInt(R.styleable.CustomView_shape, 0);

            typedArray.recycle();//回收
        }
    }

    /**
     * 自定义View测量方法
     * @param widthMeasureSpec 是一个32位的数据，前2位决定模式，后30位决定尺寸（大小）
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //解决wrap_content属性无效的问题
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);

        int wSize = MeasureSpec.getSize(widthMeasureSpec); //屏幕宽：720
        int hSize = MeasureSpec.getSize(heightMeasureSpec); //屏幕高：1024

        //At_most代表Wrap_content属性值  EXACTLY代表MatchParent或xxxdp
        if(wMode == MeasureSpec.AT_MOST && hMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(defaultWidth,defaultHeight);
        }
        else if(wMode == MeasureSpec.AT_MOST ){
            setMeasuredDimension(defaultWidth,hSize);
        }
        else if(hMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(wSize,defaultHeight);
        }
    }

    /**
     * 自定义View布局
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 自定义View绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.record);
        centerX = getWidth()/2;
        centerY = getHeight()/2;
        centerRadius = (int)radius;//bitmap.getWidth()/2+100;

        canvas.drawCircle(centerX,centerY,centerRadius,cPaint);

        canvas.drawBitmap(bitmap,centerX-bitmap.getWidth()/2,centerY-bitmap.getHeight()/2,iPaint);

        RectF rectF = new RectF(centerX-radius,centerY-radius,centerX+radius,centerY+radius); //矩形（扇形绘制的范围）上左右下
        canvas.drawArc(rectF,0,angle,false,lPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            RotateAnimation animation = new RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            animation.setDuration(5*1000);//转一圈的用时
            animation.setRepeatCount(-1);//重复一直转
            animation.setInterpolator(new LinearInterpolator());//匀速
            this.startAnimation(animation);


        }

        return super.onTouchEvent(event);
    }
}
