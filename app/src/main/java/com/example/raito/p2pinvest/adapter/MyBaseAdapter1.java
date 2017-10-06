package com.example.raito.p2pinvest.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Raito on 2017/10/6.
 */

public abstract class MyBaseAdapter1<T> extends BaseAdapter {
    List<T> list;

    MyBaseAdapter1(List<T> list) {
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
        //View view = myGetView(position, convertView, parent);//引用对象内部要是有重新生成的对象 必须接收一下让内部对象生成
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = initView(parent.getContext());
            viewHolder = new ViewHolder(convertView);
        }else{

            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (list!=null&&list.size()>0){
            T t = list.get(position);
            //设置数据
            setDate(t,convertView);//没有用到ViewHolder  没有做list优化
        }


        return convertView;
    }

     abstract void setDate(T t, View convertView);


    private class ViewHolder{
        ViewHolder(View view) {
            view.setTag(this);
        }
    }
    abstract View initView(Context context);

    //抽象方法
    //abstract View myGetView(int position, View convertView, ViewGroup parent);
}
