package com.example.raito.p2pinvest.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.common.BaseFragment;
import com.example.raito.p2pinvest.utils.DrawUtils;
import com.example.raito.p2pinvest.utils.UiUtils;
import com.example.raito.p2pinvest.view.FlowLayout;
import com.loopj.android.http.RequestParams;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Raito on 2017/10/3.
 * invest子Fragment热门产品
 */

public class ProductHotFragment extends BaseFragment {
    @BindView(R.id.flowLayout_hot)
    FlowLayout flowLayoutHot;

    private String[] datas = new String[]{"新手福利计划", "财神道90天计划", "硅谷计划", "30天理财计划", "180天理财计划", "月月升", "中情局投资商业经营", "大学老师购买车辆", "屌丝下海经商计划", "美人鱼影视拍摄投资", "Android培训老师自己周转", "养猪场扩大经营",
            "旅游公司扩大规模", "摩托罗拉洗钱计划", "铁路局回款计划", "屌丝迎娶白富美计划","财神道90天计划", "硅谷计划", "30天理财计划", "180天理财计划", "月月升"
    };

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content, View view_success) {

        for(int i = 0; i < datas.length; i++) {
            final TextView tv = new TextView(getContext());

            //设置属性
            tv.setText(datas[i]);
            ViewGroup.MarginLayoutParams mp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mp.leftMargin = UiUtils.dp2px(5);
            mp.rightMargin = UiUtils.dp2px(5);
            mp.topMargin = UiUtils.dp2px(5);
            mp.bottomMargin = UiUtils.dp2px(5);
            tv.setLayoutParams(mp);//设置边距

            int padding = UiUtils.dp2px(10);
            tv.setPadding(padding+10, padding, padding+10, padding);//设置内边距
            tv.setTextColor(UiUtils.getColor(R.color.white));
            tv.setTextSize(UiUtils.dp2px(8));

            Random random = new Random();
            int red = random.nextInt(150)+100;//生成100到250之间的随机数
            int green = random.nextInt(150)+50;
            int blue = random.nextInt(150)+50;
            //设置单一背景
            //tv.setBackgroundColor(Color.rgb(red,green,blue));
//             tv.setBackground(DrawUtils.getDrawable(Color.rgb(red,green,blue),UiUtils.dp2px(5)));
            //设置具有选择器功能的背景
            tv.setBackground(DrawUtils.getSelector(DrawUtils.getDrawable(Color.rgb(red, green, blue), UiUtils.dp2px(20)), DrawUtils.getDrawable(Color.WHITE, UiUtils.dp2px(20))));
            //设置textView是可点击的.如果设置了点击事件，则TextView就是可点击的。
//             tv.setClickable(true);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),tv.getText().toString() , Toast.LENGTH_SHORT).show();
                }
            });
            flowLayoutHot.addView(tv);
        }

    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.product_hot_fragment;
    }


}
