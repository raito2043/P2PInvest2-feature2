package com.example.raito.p2pinvest.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.utils.UiUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.w3c.dom.Text;

/**
 * Created by Raito on 2017/10/2.
 * 每个展示界面的四个状态界面的抽取
 */

public abstract class LoadingPage extends FrameLayout {
    //四种显示状态
    private static final int STATE_LOADING = 1;
    private static final int STATE_ERROR = 2;
    private static final int STATE_EMPTY = 3;
    private static final int STATE_SUCCESS = 4;
    private int state_current = STATE_SUCCESS;//每个状态都不同的一个变量,默认情况下为加载
    //四种显示界面
    private View view_loading;
    private View view_error;
    private View view_empty;
    private View view_success;
    private LayoutParams layoutParams;


    public LoadingPage(@NonNull Context context) {
        this(context, null);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化方法写在这里
        init();
    }

    private void init() {
        //实例化view
        //提供布局参数
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //view_loading
        if (view_loading == null) {
            //加载布局
            view_loading = UiUtils.getView(R.layout.page_loading);
            //添加到frameLayout
            addView(view_loading, layoutParams);
        }


        //view_error
        if (view_error == null) {
            //加载布局
            view_error = UiUtils.getView(R.layout.page_error);
            //添加到frameLayout
            addView(view_error, layoutParams);
        }


        //view_empty
        if (view_empty == null) {
            //加载布局
            view_empty = UiUtils.getView(R.layout.page_empty);
            //添加到frameLayout
            addView(view_empty, layoutParams);
        }


        //不确定此时是不是在其他线程中操作，但修改UI必须在主线程中，此方法必须在主线程中执行
        showSafePage();//第一次进入时默认加载loading，后续根据状态改变
    }

    //保证此方法在主线程中执行，更改显示
    private void showSafePage() {

        //在工具类中实现在主线程中调用
        UiUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //显示界面
                showPage();
            }
        });
    }

    //根据状态加载布局
    private void showPage() {
        view_loading.setVisibility(state_current == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        view_error.setVisibility(state_current == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        view_empty.setVisibility(state_current == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);

        //view_success
        if (view_success == null) {
            //加载view_success，非公共布局创建抽象方法待调用者实现
            //view_success = UiUtils.getView(layoutId());
            view_success = View.inflate(getContext(),layoutId(),null);
            //添加到frameLayout
            addView(view_success, layoutParams);
            Log.i("s","到这里view_success"+view_success);
        }
        view_success.setVisibility(state_current == STATE_SUCCESS ? View.VISIBLE : View.INVISIBLE);
    }

    //非公共布局创建抽象方法待调用者实现
    public abstract int layoutId();

    private ResultState resultState;

    //联网获取数据，此数据loadingPage不做处理，封装发送给相应布局处理显示，loadingPage只负责加载不带数据的布局
    public void show() {
        String url = url();
        if (TextUtils.isEmpty(url)) {
            resultState = ResultState.SUCCESS;
            resultState.setContent("");
            loadingImg();//修改state_current，并且决定加载哪个页面：showSafePage()
            return;
        }


        //第三方联网 异步
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, params(), new AsyncHttpResponseHandler() {
            //联网成功有两种状态
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                if (TextUtils.isEmpty(content)) {//"" && null

                    //state_current = STATE_EMPTY;
                    //封装状态和数据
                    resultState = ResultState.EMPTY;
                    resultState.setContent("");
                    Log.i("s","到这里state_current == STATE_SUCCESS"+view_success);
                } else {
                    //state_current = STATE_SUCCESS;
                    //封装状态和数据
                    resultState = ResultState.SUCCESS;
                    resultState.setContent(content);

                }
                //showSafePage();
                //根据枚举类值设置选择加载布局,
                loadingImg();

            }

            //联网失败一种状态
            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                //state_current = STATE_ERROR;
                resultState = ResultState.ERROR;
                resultState.setContent("");//避免出现空指针
                //showSafePage();
                //根据枚举类值设置选择加载布局

                loadingImg();

            }
        });

    }
    //获取联网数据状态下调用，发送封装数据&&布局给相应布局做处理
    protected abstract void onSuccessState(ResultState resultState, View view_success);

    //根据枚举类值设置选择加载布局
    private void loadingImg() {
        switch (resultState) {
            case EMPTY:
                state_current = STATE_EMPTY;
                break;
            case SUCCESS:
                state_current = STATE_SUCCESS;
                break;
            case ERROR:
                state_current = STATE_ERROR;
                break;
        }
        //加载布局
        showSafePage();


        //如果有数据，加载布局，把封装好的数据发送给具体布局处理,LoadingPage只负责加载相应布局
        if(state_current == STATE_SUCCESS){
            Log.i("s","到这里state_current == STATE_SUCCESS"+view_success);
            //获取联网数据状态下调用，发送封装数据&&布局给相应布局做处理
            onSuccessState(resultState,view_success);

        }


    }

    //提供联网请求参数
    protected abstract RequestParams params();

    //提供联网请求Url
    protected abstract String url();


    //创建一个枚举类封装联网后的状态信息和数据
    protected enum ResultState {
        //状态
        ERROR(2), EMPTY(3), SUCCESS(4);

        int state;

        ResultState(int state) {
            this.state = state;

        }

        //数据
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }


    }
}
