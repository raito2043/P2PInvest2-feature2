package com.example.raito.p2pinvest.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.common.BaseFragment;
import com.loopj.android.http.RequestParams;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 *  子布局联网
 */
public class InvestFragment extends BaseFragment {


    @BindView(R.id.img_pre)
    ImageView imgPre;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.tabPage_invest)
    TabPageIndicator tabPageInvest;
    @BindView(R.id.vp_invest)
    ViewPager vpInvest;



    public InvestFragment() {
        // Required empty public constructor
    }


    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {
        //加载三个不同的Fragment
            initFragments();
        //在ViewPage中设置

        PagerAdapter viewPagerAdapter = new ViewPagerPagerAdapter() {
        };
        vpInvest.setAdapter(viewPagerAdapter);

    }
    //ViewPager适配器
    class  ViewPagerPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return 0;
        }



        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }

    private  List<Fragment> listProductFragment = new ArrayList<>();
    //加载三个不同的Fragment
    private void initFragments() {

        ProductListFragment productListFragment = new ProductListFragment();
        ProductHotFragment productHotFragment = new ProductHotFragment();
        ProductRecommendFragment productRecommendFragment = new ProductRecommendFragment();

        listProductFragment.add(productListFragment);
        listProductFragment.add(productHotFragment);
        listProductFragment.add(productRecommendFragment);
    }

    //初始化title
    protected void initTitle() {
        imgPre.setVisibility(View.INVISIBLE);
        imgSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_invest;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


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


}
