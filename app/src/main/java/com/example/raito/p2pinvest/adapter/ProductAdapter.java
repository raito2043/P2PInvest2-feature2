package com.example.raito.p2pinvest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.bean.Product;
import com.example.raito.p2pinvest.utils.UiUtils;
import com.example.raito.p2pinvest.view.RoundView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Raito on 2017/10/5.
 *
 */

public class ProductAdapter extends BaseAdapter {

    private final List<Product> productList;
    private final Context context;


    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productList==null?8:productList.size()+1;//多加一种类型标题
    }

    //重写这个方法用来增加不同类型的item
    //ListView有2种类型
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    //获取具体类型
    @Override
    public int getItemViewType(int position) {
        if(position == 4){
            return 0;
        }else{
            return 1;
        }
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
        ViewHolder viewHolder;
        if(getItemViewType(position)==0){//此item类型如果是0就显示text，如果是其她就显示别的
            TextView textView = new TextView(parent.getContext());
            textView.setText("热销推荐");
            textView.setBackgroundColor(UiUtils.getColor(R.color.colorAccent));
            textView.setTextColor(UiUtils.getColor(R.color.default_circle_indicator_fill_color));
            textView.setTextSize(UiUtils.dp2px(16));
            return textView;
        }
            if(position>4){
                position--;
            }


        if (convertView == null) {//parent.getContext() 父类的上下文  父类为listView
            convertView = View.inflate(parent.getContext(), R.layout.item_product_list, null);
            viewHolder = new ViewHolder(convertView);//传入绑定
            convertView.setTag(viewHolder);//设置标签

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        if(productList!=null&&productList.size()>0){

            //设置数据
            Product product = productList.get(position);
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
        @BindView(R.id.rv_item_listProduct)
        RoundView rvItemListProduct;
        @BindView(R.id.tv_minTouMoney_item_listProduct)
        TextView tvMinTouMoneyItemListProduct;
        @BindView(R.id.tv_member_item_listProduct)
        TextView tvMemberItemListProduct;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
