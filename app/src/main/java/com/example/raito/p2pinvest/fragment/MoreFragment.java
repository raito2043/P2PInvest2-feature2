package com.example.raito.p2pinvest.fragment;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.common.BaseFragment;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends BaseFragment {


    @BindView(R.id.img_pre)
    ImageView imgPre;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    Unbinder unbinder;

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String contents, View view_success) {

    }

    //初始化title
    protected void initTitle() {
        imgPre.setVisibility(View.INVISIBLE);
        imgSetting.setVisibility(View.VISIBLE);
        tvTitle.setText("更多");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_more;
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
