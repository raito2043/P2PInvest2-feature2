package com.example.raito.p2pinvest.common;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.raito.p2pinvest.view.LoadingPage;
import com.loopj.android.http.RequestParams;

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

        return new LoadingPage(container.getContext()) {
            @Override
            public int layoutId() {
                return getLayoutId();
            }

            @Override
            protected void onSuccessState(ResultState resultState, View view_success) {
                unbinder = ButterKnife.bind(BaseFragment.this, view_success);
                //将封装的数据继续传给相应布局
                initTitle();
                initData(resultState.getContent());
            }

            @Override
            protected RequestParams params() {
                return getParams();
            }

            @Override
            protected String url() {
                return getUrl();
            }
        };
    }

    protected abstract RequestParams getParams();

    protected abstract String getUrl();

    //初始化数据
    protected abstract void initData(String content);

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
