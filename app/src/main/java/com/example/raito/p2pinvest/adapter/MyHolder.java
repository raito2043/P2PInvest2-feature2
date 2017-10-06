package com.example.raito.p2pinvest.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.bean.Product;
import com.example.raito.p2pinvest.utils.UiUtils;
import com.example.raito.p2pinvest.view.RoundView;

import butterknife.BindView;

/**
 * Created by Raito on 2017/10/6.
 *
 */

public class MyHolder extends BaseHolder<Product> {

    private
    RoundView rvItemListProduct;

    @Override
    void refreshData() {
        Product data = this.getData();


    }

    @Override
    View initView() {
        return View.inflate(UiUtils.getContext(), R.layout.item_product_list, null);
    }
}
