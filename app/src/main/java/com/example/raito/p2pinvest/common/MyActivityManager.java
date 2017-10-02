package com.example.raito.p2pinvest.common;


import android.app.Activity;
import android.util.Log;

import java.util.Stack;

/**
 * Created by Raito on 2017/10/2.
 * 自定义Activity管理者 统一管理 可以对Activity 进行增删改查
 */

public class MyActivityManager {


    //单例（饿汉式）
    private void MyActivityMenager() {
    }

    //一开始直接就创建
    private static MyActivityManager mActivityManager = new MyActivityManager();

    public static MyActivityManager getInstance() {

        return mActivityManager;
    }

    //提供栈对象
    private Stack<Activity> activityStack = new Stack<>();


    //Activity的添加
    public void add(Activity activity) {
        if (activity != null) {
            activityStack.add(activity);

        }
    }


    //Activity指定移除
    public void removeActivity(Activity activity) {
        if (activity != null) {

            for (int i = activityStack.size() - 1; i >= 0; i--) {//倒着遍历避免类似于并发修改异常的错误出现
                Activity current = activityStack.get(i);
                if (current.getClass().equals(activity.getClass())) {//此Activity是否属于这个类
                    current.finish();//销毁当前
                    activityStack.remove(i);//从栈中移除
                }

            }
        }
    }

    //Activity移除当前
    public void removeCurrent() {
        //方法一
        if (activityStack != null && activityStack.size() > 0) {

            activityStack.get(activityStack.size() - 1).finish();//销毁
            activityStack.remove(activityStack.size() - 1);//移除
            Log.i("E", "出错了");
        }

        //方法二
        /*Activity activity = activityStack.lastElement();
        activity.finish();
        activityStack.remove(activity);*/

    }

    //Activity移除所有
    public void removeAll() {

        if (activityStack != null && activityStack.size() > 0) {

            for (int i = activityStack.size() - 1; i >= 0; i--) {
                activityStack.get(i).finish();
                activityStack.remove(activityStack.get(i));
            }
        }
    }

    //返回栈的大小
    public int getStackSize() {
        return activityStack.size();
    }
}
