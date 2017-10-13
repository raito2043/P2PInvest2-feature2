package com.example.raito.p2pinvest.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.activity.BarChartActivity;
import com.example.raito.p2pinvest.activity.LineChartActivity;
import com.example.raito.p2pinvest.activity.LoginActivity;

import com.example.raito.p2pinvest.activity.PinChartActivity;
import com.example.raito.p2pinvest.activity.RechargeActivity;
import com.example.raito.p2pinvest.activity.UserInfoActivity;
import com.example.raito.p2pinvest.activity.WithdrawCashActivity;
import com.example.raito.p2pinvest.bean.User;
import com.example.raito.p2pinvest.common.BaseActivity;
import com.example.raito.p2pinvest.common.BaseFragment;
import com.example.raito.p2pinvest.utils.BitmapUtils;
import com.example.raito.p2pinvest.utils.UiUtils;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Raito on 2017/10/4.
 */

public class MineFragment extends BaseFragment {


    @BindView(R.id.img_pre)
    ImageView imgPre;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.iv_me_icon)
    ImageView ivMeIcon;
    @BindView(R.id.rl_me_icon)
    RelativeLayout rlMeIcon;
    @BindView(R.id.tv_me_name)
    TextView tvMeName;
    @BindView(R.id.rl_me)
    RelativeLayout rlMe;
    @BindView(R.id.recharge)
    ImageView recharge;
    @BindView(R.id.withdraw)
    ImageView withdraw;
    @BindView(R.id.ll_touzi)
    TextView llTouzi;
    @BindView(R.id.ll_touzi_zhiguan)
    TextView llTouziZhiguan;
    @BindView(R.id.ll_zichan)
    TextView llZichan;
    Unbinder unbinder;
    private boolean isLogin = false; //是否登录
    private String name;

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
        //判断用户是否登录
        isLogin();


    }

    //判断用户是否登录
    private void isLogin() {
        //查看本地是否有用户的登录信息
        SharedPreferences sp = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        name = sp.getString("name", "");
        if (TextUtils.isEmpty(name)) {
            //本地没有保存过用户信息，点击任意有关需要登录的操作时 跳转登录页登录，每个需要登录的操作都要判断isLogin是否为true；
            isLogin = false;
            doLogin();

        } else {
            //已经登录过，则直接加载用户的信息并显示
            isLogin = true;
            doUser();
        }
    }

    private void doUser() {

        User user = ((BaseActivity) this.getActivity()).getUser();//强转
        //设置数据
        tvMeName.setText(user.getName());
        //图片网络路径，需要联网重取 Picasso
        //这里的图片服务器下载什么样就存的什么样，需要做圆形处理
        Picasso.with(this.getActivity()).load(user.getImageUrl()).transform(new Transformation() {
            //这里的Transformation 就是对下载得到的图片做变形处理
            @Override
            public Bitmap transform(Bitmap source) {
                //图片压缩 图片大小可以动态获取
                //Bitmap bitmap = BitmapUtils.zoom(source, UiUtils.dp2px(62),UiUtils.dp2px(62));

                //圆形工具类
                Bitmap bitmap = BitmapUtils.circleBitmap(source);
                bitmap.recycle();//回收bitmap资源，这里只负责处理 ，处理完用的不是此bitmap，所以回收掉
                return bitmap;
            }

            @Override
            public String key() {//需要保证返回值不能为空，负责报错
                return "";
            }
        }).into(ivMeIcon);
    }

    private void doLogin() {

    }

    @Override
    protected void initTitle() {
        imgPre.setVisibility(View.INVISIBLE);
        tvTitle.setText("我的资产");
        imgSetting.setVisibility(View.VISIBLE);
        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "进入设置", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }


    @OnClick({R.id.img_pre, R.id.img_setting, R.id.iv_me_icon, R.id.rl_me_icon, R.id.tv_me_name, R.id.rl_me, R.id.recharge, R.id.withdraw, R.id.ll_touzi, R.id.ll_touzi_zhiguan, R.id.ll_zichan})
    public void onViewClicked(View view) {

        if (!TextUtils.isEmpty(name)) {//未登录跳转登录界面
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }else{

            switch (view.getId()) {
                case R.id.iv_me_icon:
                    ((BaseActivity)this.getActivity()).goToActivity(UserInfoActivity.class,null);
                    break;
                case R.id.rl_me_icon:
                    break;
                case R.id.tv_me_name:
                    break;
                case R.id.rl_me:
                    break;
                case R.id.recharge:
                    //进入充值界面
                    ((BaseActivity)this.getActivity()).goToActivity(RechargeActivity.class,null);
                    break;
                case R.id.withdraw:
                    ((BaseActivity)this.getActivity()).goToActivity(WithdrawCashActivity.class,null);
                    break;
                case R.id.ll_touzi:
                    ((BaseActivity)this.getActivity()).goToActivity(LineChartActivity.class,null);
                    break;
                case R.id.ll_touzi_zhiguan:
                    ((BaseActivity)this.getActivity()).goToActivity(BarChartActivity.class,null);
                    break;
                case R.id.ll_zichan:
                    ((BaseActivity)this.getActivity()).goToActivity(PinChartActivity.class,null);
                    break;
            }
        }

    }


}
