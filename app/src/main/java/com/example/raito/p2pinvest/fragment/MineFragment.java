package com.example.raito.p2pinvest.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.common.BaseFragment;
import com.loopj.android.http.RequestParams;

/**
 * Created by Raito on 2017/10/4.
 */

public class MineFragment extends BaseFragment {




    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content, View view_success) {
        Log.i("s","到这里initData"+view_success);
        TextView tvText=  view_success.findViewById(R.id.tv_text);
        tvText.setText("niaho ");
        Log.i("s","到这里");
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }





}
