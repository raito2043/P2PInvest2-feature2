package com.example.raito.p2pinvest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.example.raito.p2pinvest.R;

import java.lang.ref.WeakReference;

public class WelcomeActivity extends Activity {

    private static final int ENTER_MAIN_ACTIVITY = 0;
    private MyHandler handler;

    static class MyHandler extends Handler{
        private final WeakReference<Activity> activity;

        MyHandler(Activity activity){
           this.activity = new  WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            WelcomeActivity mActivity = (WelcomeActivity) activity.get();

            switch(msg.what){
                case ENTER_MAIN_ACTIVITY:
                    //进入MainActivity
                    mActivity.toActivity();

                    break;
            }

        }
    }

    //进入MainActivity
    private void toActivity() {
         Intent intent = new Intent(this,MainActivity.class);
         startActivity(intent);
         finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//去除状态栏，全屏显示
        setContentView(R.layout.activity_welcome);
        handler = new MyHandler(this);
        handler.sendEmptyMessageDelayed(ENTER_MAIN_ACTIVITY,2000);
    }

}
