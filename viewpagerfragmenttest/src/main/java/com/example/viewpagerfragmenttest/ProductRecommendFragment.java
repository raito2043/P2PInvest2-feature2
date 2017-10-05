package com.example.viewpagerfragmenttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * Created by Raito on 2017/10/3.
 * Invest子Fragment推荐产品
 */

public class ProductRecommendFragment extends Fragment {

    public ProductRecommendFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.product_recommend_fragment, container, false);
    }
}
