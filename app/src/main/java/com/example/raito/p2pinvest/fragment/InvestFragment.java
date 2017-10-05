package com.example.raito.p2pinvest.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.common.BaseFragment;
import com.example.raito.p2pinvest.utils.UiUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class InvestFragment extends BaseFragment {




    @BindView(R.id.img_pre)
    ImageView imgPre;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.vp_TabLayout)
    TabLayout vpTabLayout;
    @BindView(R.id.vp_invest)
    ViewPager vpInvest;

    private String[] mTitles;


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

    //TabLayout Design包
    @Override
    protected void initData(String content, View view_success) {
        //mTitles = new String[]{"全部理财", "推荐理财", "热门理财"};
        //1.加载三个不同的Fragment：ProductListFragment,ProductRecommendFragment,ProductHotFragment.
        initFragments();
        //2.ViewPager设置三个Fragment的显示
        MyAdapter adapter = new MyAdapter(getChildFragmentManager());
        vpInvest.setAdapter(adapter);
        vpInvest.setCurrentItem(0);
        //将TabPagerIndicator与ViewPager关联
        vpTabLayout.setupWithViewPager(vpInvest);//关联;//如果viewPage没有数据时有可能会报错


    }




    private List<Fragment> fragmentList = new ArrayList<>();

    //添加Fragment
    private void initFragments() {

        ProductListFragment productListFragment = new ProductListFragment();
        ProductRecommendFragment productRecommendFragment = new ProductRecommendFragment();
        ProductHotFragment productHotFragment = new ProductHotFragment();
        //添加到集合中
        fragmentList.add(productListFragment);
        fragmentList.add(productRecommendFragment);
        fragmentList.add(productHotFragment);

    }

    //初始化title
    protected void initTitle() {
        tvTitle.setText("投资");
        imgPre.setVisibility(View.INVISIBLE);
        imgSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_invest;
    }



    /**
     * 提供PagerAdapter的实现
     * 如果ViewPager中加载的是Fragment,则提供的Adpater可以继承于具体的：FragmentStatePagerAdapter或FragmentPagerAdapter
     * FragmentStatePagerAdapter:适用于ViewPager中加载的Fragment过多，会根据最近最少使用算法，实现内存中Fragment的清理，避免溢出
     * FragmentPagerAdapter:适用于ViewPager中加载的Fragment不多时，系统不会清理已经加载的Fragment。
     */
    class MyAdapter extends FragmentPagerAdapter {


        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }

        //提供TabPageIndicator的显示内容
        @Override
        public CharSequence getPageTitle(int position) {
            //方式一：
//            if(position == 0){
//                return "全部理财";
//            }else if(position == 1){
//                return "推荐理财";
//            }else if(position == 2){
//                return "热门理财";
//            }
//            return "";
            //方式二：
            return UiUtils.getStringArr(R.array.invest_tab)[position];
        }
    }


}
