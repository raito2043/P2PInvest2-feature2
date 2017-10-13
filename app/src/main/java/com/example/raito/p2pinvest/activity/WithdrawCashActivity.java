package com.example.raito.p2pinvest.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WithdrawCashActivity extends BaseActivity {


    @BindView(R.id.img_pre)
    ImageView imgPre;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.account_zhifubao)
    TextView accountZhifubao;
    @BindView(R.id.select_bank)
    RelativeLayout selectBank;
    @BindView(R.id.chongzhi_text)
    TextView chongzhiText;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.et_input_money)
    EditText etInputMoney;
    @BindView(R.id.chongzhi_text2)
    TextView chongzhiText2;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.btn_tixian)
    Button btnTixian;

    @Override
    protected void initTitle() {
        tvTitle.setText("提现");
        imgPre.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        etInputMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String money = etInputMoney.getText().toString().trim();
                if(TextUtils.isEmpty(money)){
                    btnTixian.setClickable(false);
                    btnTixian.setBackgroundResource(R.drawable.btn_bg_noclick);
                }else{
                    btnTixian.setClickable(true);
                    btnTixian.setBackgroundResource(R.drawable.btn_home_selector);
                }
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw_cash;
    }



    @OnClick({R.id.img_pre, R.id.btn_tixian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_pre:
                break;

            case R.id.btn_tixian:
                //前台提交数据申请，有后台审核后操作执行
                Toast.makeText(this, "您的提现已成功受理，24小时内到账", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        removeActivity(WithdrawCashActivity.this);
                    }
                },2000);
                break;
        }
    }
}
