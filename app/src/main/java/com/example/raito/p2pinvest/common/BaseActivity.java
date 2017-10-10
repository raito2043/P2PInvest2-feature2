package com.example.raito.p2pinvest.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.view.Window;


import com.example.raito.p2pinvest.bean.User;
import com.loopj.android.http.AsyncHttpClient;

import butterknife.ButterKnife;

/**
 * Created by Raito on 2017/10/8.
 *
 */

public abstract class BaseActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        MyActivityManager.getInstance().add(this);//加入栈

        initData();
        initTitle();

    }

    //初始化标题
    protected abstract void initTitle();

    //初始化数据
    protected abstract void initData();

    //获取布局
    protected abstract int getLayoutId();

    //联网
    public AsyncHttpClient client = new AsyncHttpClient();

    //带数据启动一个activity
    public void goToActivity(Class Activity, Bundle bundle) {
        Intent intent = new Intent(this, Activity);
        //携带数据
        if (bundle != null && bundle.size() > 0) {
            intent.putExtra("data", bundle);
        }

        startActivity(intent);
    }

    //销毁当前Activity
    public void removeActivity(Activity activity) {
        MyActivityManager.getInstance().removeActivity(activity);
    }

    //销毁所有Activity
    public void removeAllActivity() {

        MyActivityManager.getInstance().removeAll();
    }

    //服务器查询的数据保存在本地
    public void saveUser(User user) {
        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", user.getName());
        editor.putString("imageurl", user.getImageUrl());
        editor.putBoolean("iscredit", user.isCredit());
        editor.putString("phone", user.getPhone());
        editor.apply();//必须提交，否则保存不成功

    }

    //取出本地保存的数据
    public User getUser() {
        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        User user = new User();
        user.setName(sp.getString("name", ""));
        user.setImageUrl(sp.getString("imageurl", ""));
        user.setCredit(sp.getBoolean("iscredit", false));
        user.setPhone(sp.getString("phone", ""));
        return user;
    }
}
