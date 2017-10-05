package com.example.viewpagerfragmenttest;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment3 extends Fragment {


    private List<Fragment> fragmentList;
    private String[] mTitles;

    public BlankFragment3() {

        // Required empty public constructor
    }

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank_fragment3, container, false);
          mTitles = new String[]{"唐僧", "大师兄", "二师兄","沙师弟"};

        mViewPager = view.findViewById(R.id.vp_view);
        mTabLayout = view.findViewById(R.id.tabs);


        BlankFragment4 blankFragment4 = new BlankFragment4();
        BlankFragment5 blankFragment5 = new BlankFragment5();
        BlankFragment6 blankFragment6 = new BlankFragment6();


        fragmentList = new ArrayList<>();
        fragmentList.add(blankFragment4);
        fragmentList.add(blankFragment5);
        fragmentList.add(blankFragment6);

        mTabLayout.setupWithViewPager(mViewPager);//关联

        //mTabLayout.addTab(mTabLayout.newTab().setText("Tab 1").setIcon(R.mipmap.ic_launcher));
        //mTabLayout.addTab(mTabLayout.newTab().setText("投资理财"));
        //mTabLayout.addTab(mTabLayout.newTab().setText("Tab 1").setIcon(R.mipmap.ic_launcher));

       

        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList == null ? 0 : fragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });


        return view;
    }

}
