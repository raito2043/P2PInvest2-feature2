package com.example.raito.p2pinvest.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.common.AppNetConfig;
import com.example.raito.p2pinvest.common.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity {


    private static final int COMMIT = 0;
    @BindView(R.id.img_pre)
    ImageView imgPre;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.cb_fankui_tech)
    RadioButton cbFankuiTech;
    @BindView(R.id.cb_fankui_invest)
    RadioButton cbFankuiInvest;
    @BindView(R.id.cb_fankui_zixun)
    RadioButton cbFankuiZixun;
    @BindView(R.id.rg_fankui)
    RadioGroup rgFankui;
    @BindView(R.id.et_fankui_content)
    EditText etFankuiContent;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private String department;
    private String content;
    private boolean isFlag = true;

    @Override
    protected void initTitle() {



    }
    //获取用户填写数据
    private void getUserData() {
        //RadioGroup
        rgFankui.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rgChild = rgFankui.findViewById(checkedId);
                //发送至
                department = rgChild.getText().toString();
            }
        });
        //获取用户反馈内容
        content = etFankuiContent.getText().toString().trim();

    }

    @Override
    protected void initData() {
            tvTitle.setText("用户反馈");
            imgPre.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }



    @OnClick({R.id.img_pre, R.id.tv_history, R.id.cb_fankui_tech, R.id.cb_fankui_invest, R.id.cb_fankui_zixun, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_pre:
                break;
            case R.id.tv_history:
                break;
            case R.id.cb_fankui_tech:
                break;
            case R.id.cb_fankui_invest:
                break;
            case R.id.cb_fankui_zixun:
                break;
            case R.id.btn_commit:
                getUserData();

                if(!TextUtils.isEmpty(content)){
                  if(isFlag){

                      isFlag = false;
                      //五秒后更改isFlag为可发送
                      handler.sendEmptyMessageDelayed(COMMIT,5000);
                      commitToNet();
                  }else{

                      Toast.makeText(this, "已经提交，不用多次提交", Toast.LENGTH_SHORT).show();
                  }


                }

                break;
        }
    }
    //五秒后将isFlag改为true
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==COMMIT){
                isFlag = true;
            }

        }
    };
    //提交服务器
    private void commitToNet() {
        String url = AppNetConfig.FEEDBACK;
        //封装用户反馈数据
        RequestParams params = new RequestParams();
        params.put("department",department);
        params.put("content",content);
        client.post(url,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                Toast.makeText(FeedbackActivity.this, "反馈成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable error, String content) {
                Toast.makeText(FeedbackActivity.this, "联网失败", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
