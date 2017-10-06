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

public class ProductAdapter1 extends MyBaseAdapter<Product> {//泛型适配 MyBaseAdapter<Product>

    public ProductAdapter1(List<Product> list) {
        super(list);
    }

    @Override
    View myGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {//parent.getContext() 父类的上下文  父类为listView
            convertView = View.inflate(parent.getContext(), R.layout.item_product_list, null);
            viewHolder = new ViewHolder(convertView);//传入绑定
            convertView.setTag(viewHolder);//设置标签

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        if (list != null && list.size() > 0) {

            //设置数据
            Product product =  list.get(position);
            viewHolder.titleItemListProduct.setText(product.name);
            viewHolder.tvDaysItemListProduct.setText(product.suodingDays);
            viewHolder.tvMemberItemListProduct.setText(product.memberNum);
            viewHolder.tvMinTouMoneyItemListProduct.setText(product.minTouMoney);
            viewHolder.tvRateItemListProduct.setText(product.yearRate);
            viewHolder.rvItemListProduct.setProgress(Integer.valueOf(product.progress));
        }


        return convertView;



    }

    static class ViewHolder {
        @BindView(R.id.title_item_listProduct)
        TextView titleItemListProduct;
        @BindView(R.id.arrow_item_listProduct)
        ImageView arrowItemListProduct;
        @BindView(R.id.tv_sum_item_listProduct)
        TextView tvSumItemListProduct;
        @BindView(R.id.tv_rate_item_listProduct)
        TextView tvRateItemListProduct;
        @BindView(R.id.tv_days_item_listProduct)
        TextView tvDaysItemListProduct;
        @BindView(R.id.tv_minTouMoney_item_listProduct)
        TextView tvMinTouMoneyItemListProduct;
        @BindView(R.id.tv_member_item_listProduct)
        TextView tvMemberItemListProduct;
        @BindView(R.id.rv_item_listProduct)
        RoundView rvItemListProduct;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
