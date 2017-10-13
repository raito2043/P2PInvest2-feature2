package com.example.raito.p2pinvest.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.activity.FeedbackActivity;
import com.example.raito.p2pinvest.activity.GestureEditActivity;
import com.example.raito.p2pinvest.activity.GestureVerifyActivity;
import com.example.raito.p2pinvest.activity.RegisterActivity;
import com.example.raito.p2pinvest.common.BaseActivity;
import com.example.raito.p2pinvest.common.BaseFragment;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;

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

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String number;

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
        sp = this.getActivity().getSharedPreferences("secret_protect", Context.MODE_PRIVATE);//只有activity能调用
        //检查手势密码状态初始化
        initGesturePassword();
        //保存手势密码状态
        saveGesturePassword();


    }

    //检查手势密码状态初始化
    private void initGesturePassword() {
        boolean isChecked = sp.getBoolean("isChecked", false);
        String inputCode = sp.getString("inputCode", null);
        if(isChecked&&!TextUtils.isEmpty(inputCode)){
            toggleMore.setChecked(true);
        }else{
            toggleMore.setChecked(false);
        }


    }

    private void saveGesturePassword() {
        //设置手势密码开关监听
        toggleMore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor = sp.edit();
                if (isChecked) {
                    //保存toggle的状态，作用域在界面之间用sp

                    editor.putBoolean("isChecked", true);
                    //判断手势密码是否存在
                    String inputCode = sp.getString("inputCode", null);
                    if (TextUtils.isEmpty(inputCode)) {
                        //直接跳转设计手势密码界面
                        Intent intent = new Intent(getContext(), GestureEditActivity.class);
                        startActivity(intent);


                    } else {
                        Toast.makeText(getContext(), "手势密码已开启", Toast.LENGTH_SHORT).show();


                    }

                } else {


                    Toast.makeText(getContext(), "手势密码已关闭", Toast.LENGTH_SHORT).show();
                     editor.putBoolean("isChecked", false);
//                    editor.remove("inputCode");

                }
                editor.apply();
            }
        });
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
                ((BaseActivity) this.getActivity()).goToActivity(RegisterActivity.class, null);
                break;

            case R.id.tv_more_reset:
                String inputCode = sp.getString("inputCode", null);
                boolean isChecked = sp.getBoolean("isChecked", false);
                if (isChecked && !TextUtils.isEmpty(inputCode)) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("request", 2);

                    //重置手势密码
                    ((BaseActivity) this.getActivity()).goToActivity(GestureVerifyActivity.class, bundle);
                }else{

                    Toast.makeText(getContext(), "手势密码未开启", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_more_phone:

                break;
            case R.id.rl_more_contact:
                number = tvMorePhone.getText().toString().trim();
                new AlertDialog.Builder(MoreFragment.this.getActivity())
                        .setTitle("联系客服")
                        .setMessage("联系客服101-16564888")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //联系客服
                                callCustomService();
                            }
                        }).setNegativeButton("取消",null).show();

                break;
            case R.id.tv_more_fankui:
                //反馈
                ((BaseActivity)MoreFragment.this.getActivity()).goToActivity(FeedbackActivity.class,null);


                break;
            case R.id.tv_more_share:
                //分享
                break;
            case R.id.tv_more_about:
                //关于
                break;
        }
    }

    private void callCustomService() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+number));
        startActivity(intent);

    }
}
