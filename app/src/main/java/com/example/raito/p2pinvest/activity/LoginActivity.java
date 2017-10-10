package com.example.raito.p2pinvest.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.bean.User;
import com.example.raito.p2pinvest.common.AppNetConfig;
import com.example.raito.p2pinvest.common.BaseActivity;
import com.example.raito.p2pinvest.fragment.MineFragment;
import com.example.raito.p2pinvest.utils.MD5Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.img_pre)
    ImageView imgPre;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.tv_login_number)
    TextView tvLoginNumber;
    @BindView(R.id.et_login_number)
    EditText etLoginNumber;
    @BindView(R.id.rl_login)
    RelativeLayout rlLogin;
    @BindView(R.id.tv_login_pwd)
    TextView tvLoginPwd;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void initTitle() {
        tvTitle.setText("用户登录");
        imgSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_pre, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_pre:
                //移除所有activity
                removeAllActivity();
                goToActivity(MainActivity.class, null);//只能在activity之间正常使用


                break;
            case R.id.btn_login:

                String phone = etLoginNumber.getText().toString().trim();
                String password = etLoginPwd.getText().toString().trim();
                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)) {
                    //联网获取请求参数效验用户名密码是否正确
                    //url
                    String url = AppNetConfig.LOGIN;
                    Log.i("s","登录"+url);
                    //携带数据
                    RequestParams params = new RequestParams();
                    params.put("phone", phone);//这里的键值和服务器的相同，由接口文档提供
                    params.put("password", MD5Utils.MD5(password));//发送之前Md5加密

                    //当前需判断是否联网 如果未联网提示，联网执行下面方法

                    //post请求 //服务器连接数据库经常查找比对
                    client.post(url, params, new AsyncHttpResponseHandler() {//根据参数联网在服务器中查找相应结果
                        @Override
                        public void onSuccess(String content) {//其中包括返回码 200    404等各种状态
                            if (!TextUtils.isEmpty(content)) {//防止解析时空指针
                                JSONObject jsonObject = JSON.parseObject(content);
                                Boolean success = jsonObject.getBoolean("success");//json中的一个确定是否有值的键值，先解析出来
                                if (success) {
                                    //如果有，解析并封装到user，得到user
                                    String data = jsonObject.getString("data");
                                    User user = JSONObject.parseObject(data, User.class);
                                    //保存用户信息到本地用来显示个人信息界面
                                    saveUser(user);
                                    //移除所有activity
                                    removeAllActivity();
                                    //进入mineActivity
                                    goToActivity(MineFragment.class, null);
                                } else {

                                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                                }

                            } else {

                                Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Throwable error, String content) {
                            //不成功时返回
                            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    Toast.makeText(this, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
        }


    }
}
