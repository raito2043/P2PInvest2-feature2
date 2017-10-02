package com.example.raito.p2pinvest.common;

import android.app.ActivityManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.raito.p2pinvest.activity.MainActivity;
import com.example.raito.p2pinvest.utils.UiUtils;

/**
 * Created by Raito on 2017/10/2.
 * 应急Handler 处理未捕获异常 类似于web的自动加载Error页面
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    //系统默认处理未捕获异常的处理器
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    //单例模式（懒汉式）异常的处理是系统专门做的 是独立的一个线程 没有线程安全问题 懒汉式延迟创建更好
    private CrashHandler() {
    }

    private static CrashHandler crashHandler = null;

    public static CrashHandler getInstanse() {
        if (crashHandler == null) {
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }


    public void init() {
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //将此方法设置为未捕获异常的处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    //一旦出现异常就会调用下面方法 类似web的Error页面
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.i("uncaughtException", "defaultUncaughtExceptionHandler" + throwable.getMessage());
        new Thread() {
            @Override
            public void run() {
                //独立的线程是没有在主线程中执行的，修改ui必须放在主线程中
                //prepare 和loop 之间的操作就是在主线程中的操作
                //一般线程无法调用prepare，只有主线程可以
                Looper.prepare();
                Toast.makeText(UiUtils.getContext(), "未捕获异常", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        //收集异常信息
        collectUncaughtException(throwable);

        //两秒后移除进程，退出虚拟机 最好退出异常界面暂时还可以使用进程
        try {
            Thread.sleep(2000);
            //从自定义Activity栈中移除当前activity

            MyActivityManager.getInstance().removeCurrent();

            //结束当前进程
            android.os.Process.killProcess(android.os.Process.myPid());
            //结束虚拟机
            System.exit(0);

        } catch (InterruptedException e) {
            e.printStackTrace();

        }


    }

    //收集异常信息
    private void collectUncaughtException( Throwable throwable){
        final String throwableMsg = throwable.getMessage();
        //获取想要收集的手机信息  Android Build 及相关类
        final String phoneMsg = Build.DEVICE + Build.MODEL + Build.PRODUCT + Build.VERSION.SDK_INT;

        //模拟开启子线程发送给服务器 服务器收集并处理这些异常
        new Thread(){
            @Override
            public void run() {
                Log.i("Exception",throwableMsg+":"+phoneMsg);
            }
        }.start();
    }
}
