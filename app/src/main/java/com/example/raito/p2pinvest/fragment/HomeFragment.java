package com.example.raito.p2pinvest.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raito.p2pinvest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @BindView(R.id.img_pre)
    ImageView imgPre;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.fl_home)
    FrameLayout flHome;
    Unbinder unbinder;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        unbinder = ButterKnife.bind(this, view);
        setFragment();

        return view;
    }

    private void setFragment() {

        // 开启一个新事务
        android.support.v4.app.FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction begin = fragmentManager.beginTransaction();
        ItemHomeFragment homeFragment = new ItemHomeFragment();
        // 使用add方法添加Fragment，第一个参数是要把Fragment添加到的布局Id
        // 第二个就是要添加的Fragment
        begin.add(R.id.fl_home, homeFragment);
        // 提交事务，否则添加就没成功
        begin.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
