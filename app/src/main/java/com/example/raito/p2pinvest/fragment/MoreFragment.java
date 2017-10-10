package com.example.raito.p2pinvest.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.activity.RegisterActivity;
import com.example.raito.p2pinvest.common.BaseActivity;
import com.example.raito.p2pinvest.common.BaseFragment;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.tv_more_regist)
    TextView tvMoreRegist;
    @BindView(R.id.toggle_more)
    ToggleButton toggleMore;
    @BindView(R.id.tv_more_reset)
    TextView tvMoreReset;
    @BindView(R.id.tv_more_phone)
    TextView tvMorePhone;
    @BindView(R.id.rl_more_contact)
    RelativeLayout rlMoreContact;
    @BindView(R.id.tv_more_fankui)
    TextView tvMoreFankui;
    @BindView(R.id.tv_more_share)
    TextView tvMoreShare;
    @BindView(R.id.tv_more_about)
    TextView tvMoreAbout;
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



    @OnClick({R.id.img_pre, R.id.tv_title, R.id.img_setting, R.id.tv_more_regist, R.id.toggle_more, R.id.tv_more_reset, R.id.tv_more_phone, R.id.rl_more_contact, R.id.tv_more_fankui, R.id.tv_more_share, R.id.tv_more_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_pre:
                break;
            case R.id.tv_title:
                break;
            case R.id.img_setting:
                break;
            case R.id.tv_more_regist:
                //跳转到注册界面
                ((BaseActivity)this.getActivity()).goToActivity(RegisterActivity.class,null);
                break;
            case R.id.toggle_more:
                //开启手势密码
                break;
            case R.id.tv_more_reset:
                //重置手势密码
                break;
            case R.id.tv_more_phone:

                break;
            case R.id.rl_more_contact:

                break;
            case R.id.tv_more_fankui:
                //联系客服
                break;
            case R.id.tv_more_share:
                //分享
                break;
            case R.id.tv_more_about:
                //关于
                break;
        }
    }
}
