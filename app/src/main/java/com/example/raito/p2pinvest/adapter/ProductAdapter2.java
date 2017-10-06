package com.example.raito.p2pinvest.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.bean.Product;
import com.example.raito.p2pinvest.view.RoundView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Raito on 2017/10/6.
 *
 */

public class ProductAdapter2 extends MyBaseAdapter2<Product> {
    ProductAdapter2(List<Product> list) {
        super(list);
    }

    @Override
    BaseHolder<Product> getHolder() {
        return new MyHolder();
    }//泛型适配 MyBaseAdapter<Product>

}
