package com.example.raito.p2pinvest.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Raito on 2017/10/6.
 *
 */

public abstract class MyBaseAdapter2<T> extends BaseAdapter {
    List<T> list;

    MyBaseAdapter2(List<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 6 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BaseHolder<T> baseHolder;
        if (convertView == null) {
            baseHolder = getHolder();

        } else {

            baseHolder = (BaseHolder<T>) convertView.getTag();
        }

        T t = list.get(position);

        //封装数据
        baseHolder.setData(t);

        return baseHolder.getView();

    }

    abstract BaseHolder<T> getHolder();
}
