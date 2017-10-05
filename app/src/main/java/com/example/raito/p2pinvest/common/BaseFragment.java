package com.example.raito.p2pinvest.common;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.utils.UiUtils;
import com.example.raito.p2pinvest.view.LoadingPage;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Raito on 2017/10/2.
 * Fragment 基础类
 */

public abstract class BaseFragment extends android.support.v4.app.Fragment {

    private LoadingPage loadingPager;
    private Unbinder unbinde;


    //加载视图
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //View view = inflater.inflate(R.layout.fragment_invest, container, false);

        //View view = inflater.inflate(getLayoutId(), container, false);
        //使用loadingPage动态加载视图
        //加载LoadingPager
        loadingPager = new LoadingPage(container.getContext()) {

            @Override
            public int layoutId() {
                return getLayoutId();
            }

            @Override
            protected void onSuccessState(ResultState resultState, View view_success) {
                Log.i("s","到这里onSuccessState"+view_success);
                //百度
                unbinde = ButterKnife.bind(BaseFragment.this, view_success);
                //将封装的数据继续传给相应布局
                initTitle();
                initData(resultState.getContent(),view_success);

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

        return loadingPager;
    }


    //onActivityCreated 在 onCreateView后调用
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //模拟联网操作的延迟
      /*  UiUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //视图加载好后加载数据

            }
        }, 2000);*/
        loadingPager.show();


    }

    protected abstract RequestParams getParams();

    protected abstract String getUrl();

    //初始化数据
    protected abstract void initData(String content, View view_success);

    //初始化标题
    protected abstract void initTitle();

    //获取布局
    public abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinde.unbind();

    }
}
