package com.example.raito.p2pinvest.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.common.BaseActivity;
import com.example.raito.p2pinvest.fragment.HomeFragment;
import com.example.raito.p2pinvest.fragment.MineFragment;
import com.example.raito.p2pinvest.fragment.InvestFragment;
import com.example.raito.p2pinvest.fragment.MoreFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final int WHAT_RESET_BACK = 0;
    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.img_home)
    ImageView imgHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.img_invest)
    ImageView imgInvest;
    @BindView(R.id.tv_invest)
    TextView tvInvest;
    @BindView(R.id.ll_invest)
    LinearLayout llInvest;
    @BindView(R.id.img_mine)
    ImageView imgMine;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.ll_mine)
    LinearLayout llMine;
    @BindView(R.id.img_more)
    ImageView imgMore;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    private HomeFragment homeFragment;
    private MineFragment mineFragment;
    private InvestFragment investFragment;
    private MoreFragment moreFragment;
    private FragmentTransaction begin;


    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {
        //初始化显示home
        setFragment(0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.ll_home, R.id.ll_invest, R.id.ll_mine, R.id.ll_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_home:
                setFragment(0);
                // Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_invest:
                setFragment(1);
                //Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_mine:
                setFragment(2);
                //Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_more:
                setFragment(3);
                //Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //设置fragment
    private void setFragment(int i) {

        android.support.v4.app.FragmentManager fragmentManager = this.getSupportFragmentManager();
        begin = fragmentManager.beginTransaction();
        //隐藏不必要的显示
        hideFragment();
        switch (i) {
            case 0:
                if (homeFragment == null) {//不重建 如果有就复用
                    //commit之后才会调用生命周期
                    homeFragment = new HomeFragment();
                    begin.add(R.id.fl_main, homeFragment);//添加到容器
                }
                begin.show(homeFragment);//如果不为空直接show
                setTextColorAndImg(0);
                break;
            case 1:
                if (investFragment == null) {//不重建 如果有就复用
                    //commit之后才会调用生命周期
                    investFragment = new InvestFragment();
                    begin.add(R.id.fl_main, investFragment);//添加到容器
                }
                begin.show(investFragment);//如果不为空直接show
                //改变图片和字体颜色
                setTextColorAndImg(1);
                imgInvest.setBackgroundResource(R.drawable.bottom04);
                tvInvest.setTextColor(getResources().getColor(R.color.home_back_selected));//通过获取颜色id获得相应的值
                break;
            case 2:
                if (mineFragment == null) {//不重建 如果有就复用
                    //commit之后才会调用生命周期
                    mineFragment = new MineFragment();
                    begin.add(R.id.fl_main, mineFragment);//添加到容器
                }
                begin.show(mineFragment);//如果不为空直接show
                //改变图片和字体颜色
                setTextColorAndImg(2);
                break;
            case 3:

                if (moreFragment == null) {//不重建 如果有就复用
                    //commit之后才会调用生命周期
                    moreFragment = new MoreFragment();
                    begin.add(R.id.fl_main, moreFragment);//添加到容器
                }
                begin.show(moreFragment);//如果不为空直接show
                //改变图片和字体颜色
                setTextColorAndImg(3);
                break;
        }
        begin.commit();
        //begin.replace()//重新生成


    }

    //设置非点击字体颜色和图片
    private void setTextColorAndImg(int i) {
        switch (i) {

            case 0:
                //改变图片和字体颜色
                imgHome.setBackgroundResource(R.drawable.bottom02);
                tvHome.setTextColor(getResources().getColor(R.color.home_back_selected));//通过获取颜色id获得相应的值
                imgInvest.setBackgroundResource(R.drawable.bottom03);
                tvInvest.setTextColor(getResources().getColor(R.color.home_back_unselected));//通过获取颜色id获得相应的值
                imgMine.setBackgroundResource(R.drawable.bottom05);
                tvMine.setTextColor(getResources().getColor(R.color.home_back_unselected));//通过获取颜色id获得相应的值
                imgMore.setBackgroundResource(R.drawable.bottom07);
                tvMore.setTextColor(getResources().getColor(R.color.home_back_unselected));//通过获取颜色id获得相应的值
                break;
            case 1:
                imgHome.setBackgroundResource(R.drawable.bottom01);
                tvHome.setTextColor(getResources().getColor(R.color.home_back_unselected));//通过获取颜色id获得相应的值
                imgInvest.setBackgroundResource(R.drawable.bottom04);
                tvInvest.setTextColor(getResources().getColor(R.color.home_back_selected));//通过获取颜色id获得相应的值
                imgMine.setBackgroundResource(R.drawable.bottom05);
                tvMine.setTextColor(getResources().getColor(R.color.home_back_unselected));//通过获取颜色id获得相应的值
                imgMore.setBackgroundResource(R.drawable.bottom07);
                tvMore.setTextColor(getResources().getColor(R.color.home_back_unselected));//通过获取颜色id获得相应的值
                break;
            case 2:
                imgHome.setBackgroundResource(R.drawable.bottom01);
                tvHome.setTextColor(getResources().getColor(R.color.home_back_unselected));//通过获取颜色id获得相应的值
                imgInvest.setBackgroundResource(R.drawable.bottom03);
                tvInvest.setTextColor(getResources().getColor(R.color.home_back_unselected));//通过获取颜色id获得相应的值
                imgMine.setBackgroundResource(R.drawable.bottom06);
                tvMine.setTextColor(getResources().getColor(R.color.home_back_selected));//通过获取颜色id获得相应的值
                imgMore.setBackgroundResource(R.drawable.bottom07);
                tvMore.setTextColor(getResources().getColor(R.color.home_back_unselected));//通过获取颜色id获得相应的值
                break;
            case 3:
                imgHome.setBackgroundResource(R.drawable.bottom01);
                tvHome.setTextColor(getResources().getColor(R.color.home_back_unselected));//通过获取颜色id获得相应的值
                imgInvest.setBackgroundResource(R.drawable.bottom03);
                tvInvest.setTextColor(getResources().getColor(R.color.home_back_unselected));//通过获取颜色id获得相应的值
                imgMine.setBackgroundResource(R.drawable.bottom05);
                tvMine.setTextColor(getResources().getColor(R.color.home_back_unselected));//通过获取颜色id获得相应的值
                imgMore.setBackgroundResource(R.drawable.bottom08);
                tvMore.setTextColor(getResources().getColor(R.color.home_back_selected));//通过获取颜色id获得相应的值
                break;
        }


    }

    @SuppressLint("HandlerLeak")//非静态handler 有可能会泄露
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_RESET_BACK:
                    flag = true;
                    break;
            }
        }
    };
    private boolean flag = true;

    //两次返回键退出
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        //第一次进入
        if (keyCode == KeyEvent.KEYCODE_BACK && flag) {
            //第二次不进入
            flag = false;
            //如果两秒之内没有下一步操作将flag设置回true
            Toast.makeText(this, "再按一下退出", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(WHAT_RESET_BACK, 2000);
            return true;

        }
        return super.onKeyUp(keyCode, event);//返回父类退出程序

    }

    //为了避免内存泄露，在onDestroy中移除所有未被调用的消息
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除所有id为WHAT_RESET_BACK的消息
        //  handler.removeMessages(WHAT_RESET_BACK);
        //移除所有消息
        handler.removeCallbacksAndMessages(null);//为null就为所有的都被移除

    }

    private void hideFragment() {
        if (homeFragment != null) {
            begin.hide(homeFragment);
        }
        if (investFragment != null) {
            begin.hide(investFragment);
        }
        if (mineFragment != null) {
            begin.hide(mineFragment);
        }
        if (moreFragment != null) {
            begin.hide(moreFragment);
        }

    }
}
