package com.example.raito.p2pinvest.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.bean.Product;

import java.util.List;

/**
 * Created by Raito on 2017/10/5.
 *
 */

public class ProductAdapter extends BaseAdapter {

    private final List<String> productList;
    private final Context context;

    public ProductAdapter(List<String> productList, Context context) {
            this.productList = productList;
            this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(context, R.layout.item_product_list, null);
        return view;
    }



}
