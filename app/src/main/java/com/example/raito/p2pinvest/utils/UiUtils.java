package com.example.raito.p2pinvest.utils;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.example.raito.p2pinvest.common.MyApplication;

/**
 * Created by Raito on 2017/9/30.
 * 处理Ui相关问题的类，提供资源获取的相关方法，避免重复代码
 */

public class UiUtils {
    //提供context
    public static Context getContext(){
        return MyApplication.context;//这个上下文不是通用的 导致getView 的  TabLayout  无法使用
    }

    //提供handler
    public static Handler getHandler(){

        return MyApplication.handler;
    }

    //获取颜色id的值
    public  static int getColor(int colorId){
        return getContext().getResources().getColor(colorId);
    }

    //加载返回的视图&&返回
    public static View getView(int viewID){
        return  View.inflate(getContext(),viewID,null);
    }//View view = inflater.inflate(R.layout.fragment_blank_fragment3, container, false);

    //获取String构成的数组
    public static String[] getStringArr(int stringArrId){
        return  getContext().getResources().getStringArray(stringArrId);
    }

    //dp转px
    public static int dp2px(int dp){
        //获取手机密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) ((dp*density)+0.5); //+0.5 等于四舍五入 加五够一上位强制转int，加五不够一直接转int舍去小数

    }

    //px2dp
    public static int px2dp(int px){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) ((px / density)+0.5);
    }


    //确保在主线程中运行
    public static void runOnUiThread(Runnable runnable) {
        //判断当前是否是主线程
        if(isMainThread()){
            runnable.run();

        }else{
            //如果不是主线程在handler中执行
             UiUtils.getHandler().post(runnable);
        }

    }
    //判断当前是否是主线程
    private static boolean isMainThread() {

        //MyApplication肯定是在主线程中，用MyApplication的线程和当前线程做对比
        return MyApplication.mainThreadId == android.os.Process.myTid();

    }
}
