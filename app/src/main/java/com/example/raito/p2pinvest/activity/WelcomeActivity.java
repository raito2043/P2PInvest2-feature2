package com.example.raito.p2pinvest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.common.MyActivityManager;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends Activity {

    private static final int ENTER_MAIN_ACTIVITY = 0;
    @BindView(R.id.tv_version_welcome)
    TextView tvVersionWelcome;
    @BindView(R.id.rl_welcome)
    RelativeLayout rlWelcome;
    private MyHandler handler;

    static class MyHandler extends Handler {
        private final WeakReference<Activity> activity;

        MyHandler(Activity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            WelcomeActivity mActivity = (WelcomeActivity) activity.get();

            switch (msg.what) {
                case ENTER_MAIN_ACTIVITY:
                    //进入MainActivity
                    mActivity.toActivity();

                    break;
            }

        }
    }

    //进入MainActivity
    private void toActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //从栈中移除
        MyActivityManager.getInstance().removeActivity(WelcomeActivity.this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//去除状态栏，全屏显示
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        //加入到自定义栈
        MyActivityManager.getInstance().add(WelcomeActivity.this);
        //handler = new MyHandler(this);
        //handler.sendEmptyMessageDelayed(ENTER_MAIN_ACTIVITY,2000);
        initAnim();
    }

    private void initAnim() {
        //初始化alpha动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);//0完全透明，1完全不透
        alphaAnimation.setDuration(1000);//动画时间
        alphaAnimation.setInterpolator(new AccelerateInterpolator());//设置动画的变化率
        //动画监听器
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            //动画开始
            @Override
            public void onAnimationStart(Animation animation) {

            }

            //动画结束
            @Override
            public void onAnimationEnd(Animation animation) {
                toActivity();
            }

            //动画重复
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rlWelcome.startAnimation(alphaAnimation);
    }

}
