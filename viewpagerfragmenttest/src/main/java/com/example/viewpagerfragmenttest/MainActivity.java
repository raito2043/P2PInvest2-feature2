package com.example.viewpagerfragmenttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {
    @BindView(R.id.tpi_pager)
    TabPageIndicator tpiPager;
    @BindView(R.id.vp_pager)
    ViewPager vpPager;
    private List<Fragment> listProductFragment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //加载三个不同的Fragment
        initFragments();
        Log.i("s", "集合" + listProductFragment.size());
        //ViewPager vpInvest = findViewById(R.id.vp_pager);
        //TabPageIndicator tabPageInvest = findViewById(R.id.tpi_pager);
        //在ViewPage中设置
        FragmentManager fm = getSupportFragmentManager();
        FragmentPagerAdapter viewPagerAdapter = new ViewPagerPagerAdapter(fm) {
        };

        vpPager.setAdapter(viewPagerAdapter);
        vpPager.setCurrentItem(0);
        //TabPageIndicator的使用
        tpiPager.setViewPager(vpPager);
    }

    //ViewPager适配器
    //如果viewPage中加载时fragment 可以用具体的fragmentAdapter加载
    //FragmentStatePagerAdapter 适用于viewPager中Fragment过多，根据最近最少使用算法，实现fragment内存中的清理，避免内存溢出
    //FragmentPagerAdapter 适用于viewPager中Fragment不多的情况，系统不会自动清理Fragment
    class ViewPagerPagerAdapter extends FragmentPagerAdapter {
        ViewPagerPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //具体哪一项
        @Override
        public Fragment getItem(int position) {
            return listProductFragment.get(position);
        }

        //个数，个数可为空
        @Override
        public int getCount() {
            return listProductFragment == null ? 0 : listProductFragment.size();
        }

        //提供TabPageIndicator的显示内容
        @Override
        public CharSequence getPageTitle(int position) {
            //方式一：
            if (position == 0) {
                return "全部理财";
            } else if (position == 1) {
                return "推荐理财";
            } else if (position == 2) {
                return "热门理财";
            }
            //方式二：
            //return UIUtils.getStringArr(R.array.invest_tab)[position];
            return "";
        }
    }


    //加载三个不同的Fragment
    private void initFragments() {

        ProductListFragment productListFragment = new ProductListFragment();
        ProductHotFragment productHotFragment = new ProductHotFragment();
        ProductRecommendFragment productRecommendFragment = new ProductRecommendFragment();

        listProductFragment.add(productListFragment);
        listProductFragment.add(productHotFragment);
        listProductFragment.add(productRecommendFragment);
    }

}
