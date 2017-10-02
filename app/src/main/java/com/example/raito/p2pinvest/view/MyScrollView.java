package com.example.raito.p2pinvest.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by Raito on 2017/10/1.
 */

public class MyScrollView extends ScrollView {
    private View childView;
    private int eventY;
    private int lastY;
    final Rect rect = new Rect();
    private boolean isFinishAnim = true;

    public MyScrollView(Context context) {
        super(context);//不要随便用this
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //事件处理
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (childView == null || !isFinishAnim) { //如果没有子布局||如果动画没有结束直接交给父布局处理
            return super.onTouchEvent(ev);
        }

        eventY = (int) ev.getY();//获取当前y轴坐标，此方法放在这里只要滑动就会持续取值复用
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //布局位置，手指按下不在取值复用布局位置
                lastY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算差值
                int dY = eventY - lastY;
                Log.i("s", "偏移量" + dY);
                //是否需要使用自定义拖拽
                if (isNeedMine()) {
                    if (rect.isEmpty()) {//记录初始化是固定值，要判断是否已经被记录如果记录就不在重复

                        //松开时要回到拖拽前的位置，这里对初始化位置做一记录，用一个能存放四个值得容器来存放四个坐标信息
                        rect.set(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom());
                    }
                    //重置布局位置 上下为变量 随移动变化 取移动的一半 效果上有紧绷感
                    childView.layout(childView.getLeft(), childView.getTop() + dY / 3, childView.getRight(), childView.getBottom() + dY / 3);
                }
                //重新赋值
                lastY = eventY;
                break;
            case MotionEvent.ACTION_UP://使用位移动画让视图回到原来位置
                if (isNeedAnim()) {
                    //初始位置
                    int normal = rect.bottom;
                    //移动后位置
                    int current = childView.getBottom();
                    //布局坐标点移动距离
                    int translateY = current - normal;

                    TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -translateY);
                    translateAnimation.setDuration(300);
                    //translateAnimation.setFillAfter(true);//停留到动画结束位置，但是控件属性不发生变化,需要动画监听来修改位置
                    translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                        //动画开始
                        @Override
                        public void onAnimationStart(Animation animation) {
                            isFinishAnim = false;
                        }

                        //动画结束
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            isFinishAnim = true;
                            //清空动画
                            childView.clearAnimation();
                            //动画结束后，修改相应位置属性
                            childView.layout(rect.left, rect.top, rect.right, rect.bottom);
                            //将rect设置为空
                            rect.setEmpty();
                        }

                        //动画重复
                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    childView.startAnimation(translateAnimation);


                }


                break;

        }
        return super.onTouchEvent(ev);
    }

    private boolean isNeedAnim() {

        return !rect.isEmpty();
    }

    //是否需要使用自定义拖拽
    private boolean isNeedMine() {
        //获取子视图高
        int heightChildView = childView.getMeasuredHeight();
        //获取ScrollView高
        int heightThis = this.getMeasuredHeight();
        int heightY = heightChildView - heightThis;
        //获取Y轴偏移量
        int scrollY = getScrollY();
        if (scrollY <= 0 || scrollY >= heightY) {
            return true;//超出临界值，使用自定义处理
        }
        return false;//为超出临界值交给父类处理

    }


    //获取子视图
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            //获取子视图
            childView = getChildAt(0);
        }

    }
}
