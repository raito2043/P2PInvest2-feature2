package com.example.raito.p2pinvest.common;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.raito.p2pinvest.view.LoadingPage;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Raito on 2017/10/2.
 * Fragment 基础类
 */

public abstract class BaseFragment extends android.support.v4.app.Fragment {
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        //View view = inflater.inflate(getLayoutId(), container, false);
        //使用loadingPage动态加载视图
        LoadingPage loadingPage = new LoadingPage(container.getContext()) {
            @Override
            public int layoutId() {
                return getLayoutId();
            }
        };

        unbinder = ButterKnife.bind(this, loadingPage);

       // initTitle();
       // initData();
        return loadingPage;
    }
    //初始化数据
    protected abstract void initData();

    //初始化标题
    protected abstract void initTitle();

    //获取布局
    public abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
