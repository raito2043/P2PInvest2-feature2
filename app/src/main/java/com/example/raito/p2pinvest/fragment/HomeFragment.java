package com.example.raito.p2pinvest.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.bean.Image;
import com.example.raito.p2pinvest.bean.Index;
import com.example.raito.p2pinvest.bean.Product;
import com.example.raito.p2pinvest.common.AppNetConfig;
import com.example.raito.p2pinvest.common.BaseFragment;


import com.example.raito.p2pinvest.view.RoundView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    private int currentProgress;
    @BindView(R.id.img_pre)
    ImageView imgPre;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    Unbinder unbinder;
    @BindView(R.id.vp_banner)
    ViewPager vpBanner;

    @BindView(R.id.tv_title_product)
    TextView tvTitleProduct;
    @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.btn_join)
    Button btnJoin;
    @BindView(R.id.cpi_banner)
    CirclePageIndicator cpiBanner;
    @BindView(R.id.rv_round)
    RoundView rvRound;
    private Index index;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //没多久加载一点进度到最终的progress

            for (int i = 1; i <= 89; i++) {

                SystemClock.sleep(12);
                rvRound.setProgress(i);
                //休眠100毫秒

                //强制重绘
                //rvRound.invalidate();//只有主线程可以重绘
                rvRound.postInvalidate();//主线程子线程都可以重绘
            }
        }
    };



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                rvRound.setText(89);//模拟数据
                SystemClock.sleep(500);


                //未联网测试动态增长round进度
                new Thread(runnable).start();
            }
        }).start();

    }




    //初始化数据
    protected void initData() {


        //获取homeFragment数据 使用第三方AsyncHttpClient
        AsyncHttpClient client = new AsyncHttpClient();

        //封装Json到Index中
        index = new Index();
        //访问的url
        String url = AppNetConfig.INDEX;
        Log.i("url", "url" + url);


        //建立连接
        client.post(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String content) {
                //super.onSuccess(content);//响应200
                //解析json数据 GSON   \ FastJSON
                JSONObject jsonObject = JSON.parseObject(content);
                //fastJSON解析json对象
                String proInfo = jsonObject.getString("proInfo");
                //将解析的封装到相应的类中
                Product product = JSON.parseObject(proInfo, Product.class);

                //fastJson解析数组
                String imageArr = jsonObject.getString("imageArr");
                //封装到集合中
                List<Image> listImg = JSON.parseArray(imageArr, Image.class);
                index.product = product;
                index.listImg = listImg;

                tvTitleProduct.setText(product.name);
                tvRate.setText(product.yearRate + "%");
                //将网络数据中的进度设置给控件
                //rvRound.setProgress(89);//模拟数据
                //currentProgress = Integer.valueOf(product.progress);//网络资源
                //rvRound.setProgress(progress);//网络资源

                //开启子线程，动态加载round控件的进度 定义Runnable变量出去
                new Thread(runnable).start();


                Log.i("url", "url" + product.name);
                //设置viewPage显示
                vpBanner.setAdapter(new MyPagerAdapter());
                //设置小圆点显示
                cpiBanner.setViewPager(vpBanner);
            }

            class MyPagerAdapter extends PagerAdapter {
                //viewPager显示个数
                @Override
                public int getCount() {
                    List<Image> listImg = index.listImg;
                    return listImg == null ? 0 : listImg.size();
                }

                //显示item
                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    //创建image
                    ImageView imageView = new ImageView(getActivity());//上下文穿activity image依赖activity存在
                    //图片显示 使用第三方picasso获取url image资源
                    String imgUrl = index.listImg.get(position).IMAURL;
                    Log.i("s", "图片路径" + imgUrl);
                    //图片显示位置
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//居中显示
                    //picasso联网获取图片
                    Picasso.with(getActivity()).load(imgUrl).into(imageView);

                    //将image添加到容器
                    container.addView(imageView);

                    return imageView;
                }

                //移除操作
                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                Log.i("s", "网络不通" + error + "----" + content);

            }
        });

    }

    //初始化title
    protected void initTitle() {
        imgPre.setVisibility(View.INVISIBLE);
        imgSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }



    @OnClick({R.id.img_pre, R.id.img_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_pre:
                break;
            case R.id.img_setting:
                break;
        }
    }

    @OnClick(R.id.btn_join)
    public void onViewClicked() {
    }
}
