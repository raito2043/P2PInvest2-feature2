package com.example.raito.p2pinvest.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.utils.UiUtils;

/**
 * Created by Raito on 2017/9/30.
 */

public class RoundView extends View {

    //自定义属性取代
 /*   private int roundWidth = UiUtils.dp2px(8);//圆环宽
    private int textSize = UiUtils.dp2px(20);//字体大小

    private int roundColor = Color.argb(255,200,100,100);
    private int roundProgressColor = Color.RED;
    private int textColor = Color.GRAY;

    private float max = 100;//圆环总大小
    private float progress = 30;//圆环进度*/
    private float roundWidth;//圆环宽
    private float textSize;//字体大小

    private int roundColor;
    private int roundProgressColor;
    private int textColor;

    private float max ;//圆环总大小
    private float progress ;//圆环进度
    private Paint paint;
    private int width;
    private String text = "00%";

    public RoundView(Context context) {
        this(context, null);
    }

    public RoundView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public RoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        //1.获取TypedArray对象 attrs自定义属性一定要传入
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundView);
        //2.从TypedArray对象中找到属性  如果不写attrs 就会去默认值
        roundColor = typedArray.getColor(R.styleable.RoundView_roundColor, Color.BLACK);
        roundProgressColor= typedArray.getColor(R.styleable.RoundView_roundProgressColor, Color.YELLOW);
        max= typedArray.getInteger(R.styleable.RoundView_max, 100);
        progress = typedArray.getInteger(R.styleable.RoundView_progress, 10);
        roundWidth =  typedArray.getDimension(R.styleable.RoundView_roundWidth, UiUtils.dp2px(10));
        textColor = typedArray.getColor(R.styleable.RoundView_textColor, Color.BLACK);
        textSize =  typedArray.getDimension(R.styleable.RoundView_textSize, UiUtils.dp2px(20));

        //3.回收处理
        typedArray.recycle();
        initView();

    }


    //初始化界面
    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿


    }

    //设置当前进度
    public void setProgress(int progress){
        this.progress = progress;
    }

    public void setText(int progress){
        //text = String.valueOf(progress) +"%";
    }

    //获取视图宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = getMeasuredWidth();

    }


    //Canvas 画布 对应视图再控件中的的生成位置
    @Override
    protected void onDraw(Canvas canvas) {
        //绘制圆环
        //获取圆心坐标
        //抗锯齿
        //canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        int cx = width / 2;
        int cy = width / 2;
        float cRadius = width / 2 - roundWidth / 2;
        paint.setColor(roundColor);
        paint.setAntiAlias(true);//抗锯齿
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        canvas.drawCircle(cx, cy, cRadius, paint);
        //绘制圆弧 在创建的过程中有可能内存回收出错 SuppressLint禁止回收

        @SuppressLint("DrawAllocation") RectF rectF = new RectF(roundWidth / 2, roundWidth / 2, width - roundWidth / 2, width - roundWidth / 2);
        paint.setColor(roundProgressColor);
        paint.setStrokeWidth(7);//圆环宽度
        canvas.drawArc(rectF, 0, (progress / max) * 360, false, paint);

        //绘制文本
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(0);


        @SuppressLint("DrawAllocation") Rect rect = new Rect();//创建一个矩形，此时矩形没有具体宽高
        paint.getTextBounds(text, 0, text.length(), rect);//此时text宽高为包裹内容 rect来储存text的数据
        int x = width / 2 - rect.width() / 2;
        int y = width / 2 + rect.height() / 2;
        canvas.drawText(text, x, y, paint);


    }
}
