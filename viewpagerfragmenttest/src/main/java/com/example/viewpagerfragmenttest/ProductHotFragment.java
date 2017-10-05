package com.example.viewpagerfragmenttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Raito on 2017/10/3.
 * invest子Fragment热门产品
 */

public class ProductHotFragment extends Fragment {


    @BindView(R.id.tpi_pager)
    TabPageIndicator tpiPager;
    @BindView(R.id.vp_hot)
    ViewPager vpHot;
    Unbinder unbinder;
    private List<Fragment> listProductFragment = new ArrayList<>();

    public ProductHotFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.product_hot_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        //加载三个不同的Fragment
        initFragments();
        Log.i("s", "集合" + listProductFragment.size());
        //ViewPager vpInvest = findViewById(R.id.vp_pager);
        //TabPageIndicator tabPageInvest = findViewById(R.id.tpi_pager);
        //在ViewPage中设置
        //TabPageIndicator tpiPager = view.findViewById(R.id.tpi_pager);
        //ViewPager vpHot = view.findViewById(R.id.vp_hot);

        vpHot.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

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
        });
        vpHot.setCurrentItem(0);
        //TabPageIndicator的使用
        tpiPager.setViewPager(vpHot);


        return view;
    }


    //加载三个不同的Fragment
    private void initFragments() {

        BlankFragment productListFragment = new BlankFragment();
        BlankFragment2 productHotFragment = new BlankFragment2();
        BlankFragment3 productRecommendFragment = new BlankFragment3();

        listProductFragment.add(productListFragment);
        listProductFragment.add(productHotFragment);
        listProductFragment.add(productRecommendFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    //ViewPager适配器
    //如果viewPage中加载时fragment 可以用具体的fragmentAdapter加载
    //FragmentStatePagerAdapter 适用于viewPager中Fragment过多，根据最近最少使用算法，实现fragment内存中的清理，避免内存溢出
    //FragmentPagerAdapter 适用于viewPager中Fragment不多的情况，系统不会自动清理Fragment


}
