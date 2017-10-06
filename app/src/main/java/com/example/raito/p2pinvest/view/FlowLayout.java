package com.example.raito.p2pinvest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raito on 2017/10/6.
 * 流式布局 ViewGroup容器 用来显示子布局
 *
 * （不知道用户要用什么显示模式，自定义中要做判断）
 */

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        this(context,null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    //设置当前宽高,确定自定义视图宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);//自动设置宽高
        //手动设置宽高
        //获取宽高的模式和具体值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);//测量时充满，真实情况下是不一定的,需要判断

        int width = 0;  //真实宽
        int height = 0; //真实高
        int lineWidth = 0;
        int lineHeight = 0;

        //获取子视图
        int count = getChildCount();//子视图总数
        for(int i=0;i<count;i++){
            View childView = getChildAt(i);

            //只有调用了如下方法才能获取子视图宽高
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);

            //子视图宽
            int widthChild = childView.getMeasuredWidth();
            //子视图高
            int heightChild = childView.getMeasuredHeight();

            //要想获取子视图的边距参数，必须重写此方法 generateLayoutParams（）不重写这里强转就会出错
            MarginLayoutParams childParams = (MarginLayoutParams) childView.getLayoutParams();
            //确定自定义宽高
            if(lineWidth+widthChild+childParams.leftMargin+childParams.rightMargin<=widthSize){
                //行本来宽+子视图宽+左间距+右间距<至多模式的宽
                lineWidth+=widthChild+childParams.leftMargin+childParams.rightMargin;
                //取最大高度
                lineHeight = Math.max(lineHeight,heightChild+childParams.topMargin+childParams.bottomMargin);
            }else{//换行

                //设置总布局宽高
                width = Math.max(lineWidth,width);
                height += lineHeight;

                //重置行宽高
                lineWidth = widthChild+childParams.leftMargin+childParams.rightMargin;
                lineHeight = heightChild+childParams.topMargin+childParams.bottomMargin;


            }
            //最后一个元素
            if(i==count-1){
                width = Math.max(lineWidth,width);
                height += lineHeight;
            }

        }
        //设置当前流布局的宽高
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize : width, (heightMode == MeasureSpec.EXACTLY) ? heightSize : height);
    }

    //重写此方法，获取子视图边距
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }




    //重写的目的：给每一个子视图指定显示的位置：childView.layout(l,t,r,b);
    private List<List<View>> allViews = new ArrayList<>();//每一行的子视图的集合构成的集合。
    private List<Integer> allHeights = new ArrayList<>();//每一行的高度构成的集合。
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //一、给两个集合添加元素。

        //每一行的宽高值
        int lineWidth = 0;
        int lineHeight = 0;

        //提供一个集合，保存一行childView
        List<View> lineList = new ArrayList<>();
        //获取布局的宽度
        int width = this.getMeasuredWidth();

        int childCount = getChildCount();
        for(int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //获取视图的测量宽高、边距
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            MarginLayoutParams mp = (MarginLayoutParams) childView.getLayoutParams();

            if(lineWidth + childWidth + mp.leftMargin + mp.rightMargin <= width){//不换行
                lineList.add(childView);
                lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = Math.max(lineHeight,childHeight + mp.topMargin + mp.bottomMargin);

            }else{//换行
                allViews.add(lineList);
                allHeights.add(lineHeight);

                lineWidth = childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = childHeight + mp.topMargin + mp.bottomMargin;
                lineList = new ArrayList<>();
                lineList.add(childView);
            }

            if(i == childCount - 1){//如果是最后一个元素
                allViews.add(lineList);
                allHeights.add(lineHeight);
            }

        }

        Log.e("TAG", "allViews.size = " + allViews.size() + ",allHeights.size = " + allHeights.size());

        //二、给每一个子视图指定显示的位置
        int x = 0;
        int y = 0;
        for(int i = 0; i < allViews.size(); i++) {//每遍历一次，对应一行元素
            List<View> lineViews = allViews.get(i);//取出当前行构成的集合
            for(int j = 0; j < lineViews.size(); j++) {
                View childView = lineViews.get(j);
                int childWidth = childView.getMeasuredWidth();
                int childHeight = childView.getMeasuredHeight();

                MarginLayoutParams mp = (MarginLayoutParams) childView.getLayoutParams();

                int left = x + mp.leftMargin;
                int top = y + mp.topMargin;
                int right = left + childWidth;
                int bottom = top + childHeight;

                childView.layout(left,top,right,bottom);

                x +=  childWidth + mp.leftMargin + mp.rightMargin;

            }
            y += allHeights.get(i);
            x = 0;
        }
    }
}
