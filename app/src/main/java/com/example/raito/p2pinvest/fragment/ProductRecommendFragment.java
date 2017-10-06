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
import com.example.raito.p2pinvest.utils.UiUtils;
import com.example.raito.p2pinvest.view.randomLayout.StellarMap;
import com.loopj.android.http.RequestParams;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Raito on 2017/10/3.
 * Invest子Fragment推荐产品
 * 如何在不居中添加子视图？
 * 1.直接在布局文件中，以标签的方式添加。---静态
 * 2.在java代码中， 动态的添加子视图
 * -->andView():一个个添加；
 * -->设置adapter的方式，批量装配。
 */

public class ProductRecommendFragment extends BaseFragment {
    @BindView(R.id.StellarMap)
    com.example.raito.p2pinvest.view.randomLayout.StellarMap StellarMap;
    //提供装配的数据
    private String[] datas = new String[]{"新手福利计划", "财神道90天计划", "硅谷钱包计划", "30天理财计划(加息2%)", "180天理财计划(加息5%)", "月月升理财计划(加息10%)",
            "中情局投资商业经营", "大学老师购买车辆", "屌丝下海经商计划", "美人鱼影视拍摄投资", "Android培训老师自己周转", "养猪场扩大经营",
            "旅游公司扩大规模", "铁路局回款计划", "屌丝迎娶白富美计划"
    };
    //声明两个数组，分别显示
    private String[] oneData = new String[datas.length/2];
    private String[] towData = new String[datas.length-datas.length/2];

    Random random = new Random();

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
        //分别给数组封装数据
        setData();

        //设置数据适配器
        StellarMap.setAdapter(new MyStellarMapAdapter());
        //设置stellarMap的内边距
        int leftPadding = UiUtils.dp2px(10);
        int topPadding = UiUtils.dp2px(10);
        int rightPadding = UiUtils.dp2px(10);
        int bottomPadding = UiUtils.dp2px(10);
        StellarMap.setInnerPadding(leftPadding, topPadding, rightPadding, bottomPadding);

        //必须调用如下的两个方法，否则stellarMap不能显示数据
        //设置显示的数据在x轴、y轴方向上的稀疏度
        StellarMap.setRegularity(5, 5);
        //设置初始化显示的组别，以及是否需要使用动画
        StellarMap.setGroup(0, true);

    }
    //分别给数组封装数据
    private void setData() {

        for(int i=0;i<datas.length;i++){
            if (i<datas.length/2){

                oneData[i] = datas[i];
            }else{

                towData[i-datas.length/2] = datas[i];
            }

        }

    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.product_recommend_fragment;
    }

    //stellarMap Adapter
    private class MyStellarMapAdapter implements com.example.raito.p2pinvest.view.randomLayout.StellarMap.Adapter {

        //获取组个数
        @Override
        public int getGroupCount() {
            return 2;
        }

        //每组返回个数
        @Override
        public int getCount(int group) {
            if(group==0){

                return datas.length/2;
            }else {

                return datas.length-datas.length/2;//防止有奇数
            }

        }
        //返回具体view
        @Override
        public View getView(int group, final int position, View convertView) {
            final TextView textView = new TextView(getContext());

            if (group==0){
                String text = oneData[position];
                textView.setText(text);
            }else{
                String text = towData[position];
                textView.setText(text);
            }
            //随机获取字体大小
            textView.setTextSize(UiUtils.dp2px(6+random.nextInt(8)));//最小为5

            //随机获取字体颜色
            int red = random.nextInt(150)+100;//生成100到250之间的随机数
            int green = random.nextInt(150)+30;
            int blue = random.nextInt(150)+30;

            textView.setTextColor(Color.rgb(red,green,blue));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

            return textView;
        }
        //返回下一组显示平移动画的组别，平移动画可用
        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }
        //返回下一组显示缩放动画组别，缩放动画可用
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if(group==0){
                return 1;
            }else{
                return 0;
            }
        }
    }
}
