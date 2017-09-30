package com.example.raito.p2pinvest.common;

import android.app.Application;


import android.content.Context;
import android.os.Handler;

/**
 * Created by Raito on 2017/9/29.
 *
 */

public class MyApplication extends Application {

    //在整个应用过程中提供的变量
    public static Context context; //需要使用的上下文对象（静态内存泄露）
    public static Handler handler;  //需要使用的handler对象
    public static Thread mainThread;   //提供主线程对象
    public static int mainThreadId;   //主线程id

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        handler = new Handler();
        mainThread = Thread.currentThread();//实例化当前线程为主线程
        mainThreadId = android.os.Process.myTid();//获取当前线程id
    }
}