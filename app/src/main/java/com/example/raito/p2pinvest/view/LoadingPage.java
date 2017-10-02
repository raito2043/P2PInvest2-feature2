package com.example.raito.p2pinvest.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.utils.UiUtils;

/**
 * Created by Raito on 2017/10/2.
 * 每个展示界面的四个状态界面的抽取
 */

public abstract class LoadingPage extends FrameLayout {
    //四种显示状态
    private static final int STATE_LOADING = 1;
    private static final int STATE_ERROR = 2;
    private static final int STATE_EMPTY = 3;
    private static final int STATE_SUCCESS = 4;
    private int state_current = STATE_LOADING;//每个状态都不同的一个变量,默认情况下为加载
    //四种显示界面
    private View view_loading;
    private View view_error;
    private View view_empty;
    private View view_success;
    private LayoutParams layoutParams;


    public LoadingPage(@NonNull Context context) {
        this(context, null);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化方法写在这里
        init();
    }

    private void init() {
        //实例化view
        //提供布局参数
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //view_loading
        if (view_loading == null) {
            //加载布局
            view_loading = UiUtils.getView(R.layout.page_loading);
            //添加到frameLayout
            addView(view_loading, layoutParams);
        }



        //view_error
        if (view_error == null) {
            //加载布局
            view_error = UiUtils.getView(R.layout.page_error);
            //添加到frameLayout
            addView(view_error, layoutParams);
        }


        //view_empty
        if (view_empty == null) {
            //加载布局
            view_empty = UiUtils.getView(R.layout.page_empty);
            //添加到frameLayout
            addView(view_empty, layoutParams);
        }



        //不确定此时是不是在其他线程中操作，但修改UI必须在主线程中
        showSafePage();
    }

    //保证此方法在主线程中执行，更改显示
    private void showSafePage() {

        //在工具类中实现在主线程中调用
        UiUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //显示界面
                showPage();
            }
        });
    }

    //根据状态显示界面
    private void showPage() {
        view_loading.setVisibility(state_current == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        view_error.setVisibility(state_current == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        view_empty.setVisibility(state_current == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);

        //view_success
        if (view_success == null) {
            //加载view_success，非公共布局创建抽象方法待调用者实现
            view_success = UiUtils.getView(layoutId());
            //添加到frameLayout
            addView(view_success,layoutParams);
        }
        view_success.setVisibility(state_current == STATE_SUCCESS?View.VISIBLE:View.INVISIBLE);
    }

    //非公共布局创建抽象方法待调用者实现
    public abstract int layoutId();
}
