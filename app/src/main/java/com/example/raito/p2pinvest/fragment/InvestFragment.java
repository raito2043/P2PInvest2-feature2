package com.example.raito.p2pinvest.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.common.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvestFragment extends BaseFragment {


    @BindView(R.id.img_pre)
    ImageView imgPre;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    Unbinder unbinder;

    public InvestFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initData() {

    }

    //初始化title
    protected void initTitle() {
        imgPre.setVisibility(View.INVISIBLE);
        imgSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_invest;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unbinder.unbind();
    }

    @OnClick({R.id.img_pre, R.id.img_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_pre:
                break;
            case R.id.img_setting:
                break;
        }
    }
}
