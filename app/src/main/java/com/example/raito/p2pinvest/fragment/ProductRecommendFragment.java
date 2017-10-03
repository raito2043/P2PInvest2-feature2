package com.example.raito.p2pinvest.fragment;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.common.BaseFragment;
import com.loopj.android.http.RequestParams;

/**
 * Created by Raito on 2017/10/3.
 * Invest子Fragment推荐产品
 */

public class ProductRecommendFragment extends BaseFragment {
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

    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.product_recommend_fragment;
    }
}
