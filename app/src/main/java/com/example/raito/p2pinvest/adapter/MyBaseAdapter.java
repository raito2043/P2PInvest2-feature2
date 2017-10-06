package com.example.raito.p2pinvest.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Raito on 2017/10/6.
 *
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
         List<T> list;
     MyBaseAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list==null?6:list.size();
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
        View view = myGetView(position ,convertView ,parent);//引用对象内部要是有重新生成的对象 必须接收一下让内部对象生成
        return view;
    }

    //抽象方法
    abstract View myGetView(int position, View convertView, ViewGroup parent);
}
