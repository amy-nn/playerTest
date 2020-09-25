package com.example.playertest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.text.DecimalFormat;

/**
 * 绘制圆弧
 * 注意：1.测量宽高，使终保持一个正方形(默认wrap_content无效）
 *      2.文字绘制到矩形正中心
 * 解决：1.wrap_content
 */
public class MyView extends View {

    private Paint paintRed;
    private Paint paintGray;
    private Paint paintWord;
    //这是一个点
    private PointF pointF;

    private int defaultWidth = 500;
    private int defaultHeight = 500;
    private int defaultSize;
    private float paintWidth = 5f;
    private int allAngle = 300; //圆弧的角度
    private int rotateAngle = 120;

    private float offest;
    private int angle;
    private String angleText = "开始";

    /**
     * 扇形的所在范围（画扇形前先画个矩形）
     */
    private RectF rectF;
    private DrawThread drawThread;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initParam(context,attrs);
        initDate();
        initPaint();

    }

    /**
     * 测量方法
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     * 这个方法就是让wrap_content生效
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * 这里存在个问题：android:layout_width="200dp"
         * 布局里设置的是200，但实际测量的是400
         */
        defaultWidth = measureWidth(widthMeasureSpec); //测量到的真实宽
        defaultHeight = intmeasureHeight(heightMeasureSpec); //测量到的真实高

        //得到宽高中的最小值来决定正方形的大小  得到的是400
        defaultSize = Math.min(defaultWidth,defaultHeight);
        //设置测量尺寸
        setMeasuredDimension(defaultSize,defaultSize);
    }


    /**
     * 尺寸发生变化时执行此方法
     * @param w 这个值就是测量到的宽400
     * @param h 这个值就是测量到的高400
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //找中心（在中心绘制文字）
        pointF.x = w >> 1;
        pointF.y = h >> 1;

        rectF.left = 0+paintWidth/2;
        rectF.top = 0+paintWidth/2;
        rectF.right = defaultSize-paintWidth/2;
        rectF.bottom = defaultSize-paintWidth/2;

        Log.d("amy", "onSizeChanged: "+rectF.left+"-"+rectF.top+"-"+rectF.right+"-"+rectF.bottom);
        //文字测量
        Paint.FontMetrics fontMetrics = paintWord.getFontMetrics();
        //文字偏移量
        offest = (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent+pointF.y;
    }


    /**
     * 测量宽度
     * @param widthMeasureSpec
     * @return
     */
    public int measureWidth(int widthMeasureSpec){

        int wMode = MeasureSpec.getMode(widthMeasureSpec);//宽的模式
        int wSize = MeasureSpec.getSize(widthMeasureSpec);//宽尺寸
        int result = 0;

        switch (wMode){
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = wSize;
                break;
                default:
                    result = defaultWidth;
                break;
        }
        return result;
    }


    /**
     * 测量高度
     * @param heightMeasureSpec
     * @return
     */
    public int intmeasureHeight(int heightMeasureSpec){

        int result = 0;

        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);

        switch (hMode){
            case MeasureSpec.AT_MOST:
                case MeasureSpec.EXACTLY:
                    result = hSize;
                    break;
                    default:
                        result = defaultHeight;
        }

        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawArc(canvas);
        drawText(canvas);
    }


    public void drawArc(Canvas canvas){
        //保存画布当前状态（未旋转前）
        canvas.save();
        //旋转画布
        canvas.rotate(rotateAngle,pointF.x,pointF.y);
        //绘制扇形
        canvas.drawArc(rectF,0,allAngle,false, paintRed);
        canvas.drawArc(rectF,0,angle,false, paintGray);
        //再加画布还原回未旋转的样子
        canvas.restore();

        if(drawThread == null){
            drawThread = new DrawThread();
            drawThread.start();
        }
    }

    /**
     * 绘制灰色的外圈线
     */
    class DrawThread extends Thread{
        @Override
        public void run() {
            super.run();

            while (true){
                try {
                    //每隔2秒绘制一次
                    Thread.sleep(2000);
                    angle+=20;
                    if(angle > allAngle){
                        angle = 0;
                        angleText = "开始";
                    }
                    else if(angle == allAngle){
                        angleText ="加载完成";
                    }
                    else {
                        Log.d("amy", "run: "+angle);
                        float num = (float)angle/allAngle*100;

                        DecimalFormat format = new DecimalFormat("#");
                        String num1 = format.format(num);
                        Log.d("amy", "num: "+num1);
                        angleText ="正在加载："+num1+"%";
                    }
                    postInvalidate();//重新调用onDraw方法
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 绘制文本
     * @param canvas
     */
    public void drawText(Canvas canvas){
        canvas.drawText(angleText,pointF.x,offest,paintWord);
    }

    /**
     * 初始化画笔
     */
    private void initPaint(){
        paintRed = new Paint();
        paintRed.setColor(Color.RED);
        paintRed.setStyle(Paint.Style.STROKE);
        paintRed.setStrokeWidth(paintWidth);
        paintRed.setAntiAlias(true);
        paintRed.setDither(true);

        paintWord = new Paint();
        paintWord.setColor(Color.BLACK);
        paintWord.setStyle(Paint.Style.FILL);
        paintWord.setStrokeWidth(3);
        paintWord.setTextSize(30);
        paintWord.setDither(true);
        paintWord.setAntiAlias(true);
        paintWord.setTextAlign(Paint.Align.CENTER);

        paintGray = new Paint();
        paintGray.setColor(Color.GRAY);
        paintGray.setStyle(Paint.Style.STROKE);
        paintGray.setStrokeWidth(paintWidth);
        paintGray.setDither(true);
        paintGray.setAntiAlias(true);
    }

    /**
     * 初始化各种数据
     */
    private void initDate(){
        pointF = new PointF();
        rectF = new RectF();
    }

    /**
     * 初始化参数
     */
    private void initParam(Context context, AttributeSet set){
        if(set != null)
        {
            TypedArray typedArray = context.obtainStyledAttributes(set,R.styleable.MyView);
            allAngle = typedArray.getInt(R.styleable.MyView_allAngle,300);
            angle = typedArray.getInt(R.styleable.MyView_rotateAngle,120);
            paintWidth = typedArray.getDimension(R.styleable.MyView_paintWidth,5.0f);
            //释放内存
            typedArray.recycle();
        }
    }
}
