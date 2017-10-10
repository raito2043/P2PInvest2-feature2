package com.example.raito.p2pinvest.adapter;

import android.content.Context;
import android.os.SystemClock;
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
 */

public class ProductAdapter extends BaseAdapter {

    private final List<Product> productList;
    private final Context context;
    private final boolean isNet;
    private Integer progress;
    private ViewHolder viewHolder;

    public ProductAdapter(List<Product> productList, Context context, boolean isNet) {
        this.productList = productList;
        this.context = context;
        this.isNet = isNet;

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //没多久加载一点进度到最终的progress

            for (int i = 0; i <= progress; i++) {

                SystemClock.sleep(10);
                //rvItemListProduct.setProgress(i);
                viewHolder.rvItemListProduct.setProgress(i);
                //休眠100毫秒

                //强制重绘
                //rvRound.invalidate();//只有主线程可以重绘
                viewHolder.rvItemListProduct.postInvalidate();//主线程子线程都可以重绘
            }
        }
    };


    @Override
    public int getCount() {
        return productList == null ? 8 : productList.size() + 1;//多加一种类型标题
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
        if (position == 4) {
            return 0;
        } else {
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
        if (getItemViewType(position) == 0) {//此item类型如果是0就显示text，如果是其她就显示别的
            TextView textView = new TextView(parent.getContext());
            textView.setText("热销推荐");
            textView.setBackgroundColor(UiUtils.getColor(R.color.colorAccent));
            textView.setTextColor(UiUtils.getColor(R.color.default_circle_indicator_fill_color));
            textView.setTextSize(UiUtils.dp2px(16));
            return textView;
        }
        if (position > 4) {
            position--;
        }


        if (convertView == null) {//parent.getContext() 父类的上下文  父类为listView
            convertView = View.inflate(parent.getContext(), R.layout.item_product_list, null);
            viewHolder = new ViewHolder(convertView);//传入绑定
            convertView.setTag(viewHolder);//设置标签

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        if (productList != null && productList.size() > 0) {

            //设置数据
            Product product = productList.get(position);
            viewHolder.titleItemListProduct.setText(product.name);
            viewHolder.tvDaysItemListProduct.setText(product.suodingDays);
            viewHolder.tvMemberItemListProduct.setText(product.memberNum);
            viewHolder.tvMinTouMoneyItemListProduct.setText(product.minTouMoney);
            viewHolder.tvRateItemListProduct.setText(product.yearRate);
            viewHolder.tvMoneyItemListProduct.setText(product.money);
            progress = Integer.valueOf(product.progress);
            viewHolder.rvItemListProduct.setProgress(progress);
            if (isNet) {
                viewHolder.tvTypeItemListProduct.setText("本息全保");
            }
            //动态加载progress 有问题
            //new Thread(runnable).start();//
        }


        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.title_item_listProduct)
        TextView titleItemListProduct;
        @BindView(R.id.arrow_item_listProduct)
        ImageView arrowItemListProduct;
        @BindView(R.id.tv_money_item_listProduct)
        TextView tvMoneyItemListProduct;
        @BindView(R.id.tv_rate_item_listProduct)
        TextView tvRateItemListProduct;
        @BindView(R.id.tv_days_item_listProduct)
        TextView tvDaysItemListProduct;
        @BindView(R.id.tv_minTouMoney_item_listProduct)
        TextView tvMinTouMoneyItemListProduct;
        @BindView(R.id.tv_type_item_listProduct)
        TextView tvTypeItemListProduct;
        @BindView(R.id.tv_member_item_listProduct)
        TextView tvMemberItemListProduct;
        @BindView(R.id.rv_item_listProduct)
        RoundView rvItemListProduct;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
