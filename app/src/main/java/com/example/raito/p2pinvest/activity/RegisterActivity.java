package com.example.raito.p2pinvest.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.common.AppNetConfig;
import com.example.raito.p2pinvest.common.BaseActivity;
import com.example.raito.p2pinvest.utils.MD5Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.img_pre)
    ImageView imgPre;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.et_register_number)
    EditText etRegisterNumber;
    @BindView(R.id.et_register_name)
    EditText etRegisterName;
    @BindView(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @BindView(R.id.et_register_pwdagain)
    EditText etRegisterPwdagain;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.tv_declare)
    TextView tvDeclare;

    @Override
    protected void initTitle() {
        tvTitle.setText("注册");
        imgPre.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {


        //在最后一个Eidt上监听数据是否为空
        btnRegister.setClickable(false);
        etRegisterPwdagain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //text改变后
            @Override
            public void afterTextChanged(Editable s) {
                //获取Edit上的数据
                String name = etRegisterName.getText().toString().trim();
                String number = etRegisterNumber.getText().toString().trim();
                String pwd = etRegisterPwd.getText().toString().trim();
                String pweAgain = etRegisterPwdagain.getText().toString().trim();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number) && !TextUtils.isEmpty(pwd) &&
                        !TextUtils.isEmpty(pweAgain)) {
                    btnRegister.setClickable(true);
                    btnRegister.setBackgroundResource(R.drawable.btn_home_selector);


                } else {
                    btnRegister.setClickable(false);
                    btnRegister.setBackgroundResource(R.drawable.btn_bg_noclick);

                }


            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }


    @OnClick({R.id.img_pre, R.id.btn_register, R.id.tv_declare})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_pre:
                //销毁当前页
                removeActivity(this);
                break;
            case R.id.btn_register:
               //注册
                register();


                break;
            case R.id.tv_declare:
                break;
        }
    }

    private void register() {
        //注册按钮
        //获取Edit上的数据
        String name = etRegisterName.getText().toString().trim();//中文编码乱码
        String number = etRegisterNumber.getText().toString().trim();
        String pwd = etRegisterPwd.getText().toString().trim();
        String pweAgain = etRegisterPwdagain.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number) || TextUtils.isEmpty(pwd) ||
                TextUtils.isEmpty(pweAgain)) {
            Toast.makeText(this, "用户名密码不能为空", Toast.LENGTH_SHORT).show();

        } else if (!pwd.equals(pweAgain)) {

            Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            //密码置空
            etRegisterPwd.setText("");
            etRegisterPwdagain.setText("");
        } else {
            //Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
            //发送注册信息给服务器
            String url = AppNetConfig.USERREGISTER;
            RequestParams params = new RequestParams();//请求参数
            params.put("name", name);//根据接口文档封装请求参数
            params.put("password", MD5Utils.MD5(pwd));//MD5加密
            params.put("phone", number);
            //post请求
            client.post(url, params, new AsyncHttpResponseHandler() {
                //联网成功后
                @Override
                public void onSuccess(int statusCode, String content) {

                    //根据接口文档分析content中都封装了什么
                    //服务器首先判断手机号是否被注册，并返回一个isExist 是否存在 true存在，false不存在
                    //fastJSON解析content
                    com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(content);
                    Boolean isExist = jsonObject.getBoolean("isExist");
                    if (isExist){
                        Toast.makeText(RegisterActivity.this, "手机号已存在", Toast.LENGTH_SHORT).show();

                    }else{

                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Log.i("s","注册成功");
                    }
                }

                //联网失败后
                @Override
                public void onFailure(Throwable error, String content) {
                    super.onFailure(error, content);
                    Toast.makeText(RegisterActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
