package com.example.raito.p2pinvest.adapter;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Raito on 2017/10/6.
 *  ViewHolder 基类 处理view的数据设置
 */

abstract class BaseHolder<T> {
    private View rootView;//提供布局
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        refreshData();
    }

    abstract void refreshData();

    BaseHolder() {
        rootView = initView();//抽出布局
        rootView.setTag(this);//绑定ViewHolder
        ButterKnife.bind(this,rootView);//绑定Knife

    }

    abstract View initView();

    public View getView() {
        return rootView;
    }
}
