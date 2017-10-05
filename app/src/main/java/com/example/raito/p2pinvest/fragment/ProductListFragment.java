package com.example.raito.p2pinvest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.raito.p2pinvest.R;
import com.example.raito.p2pinvest.adapter.ProductAdapter;
import com.example.raito.p2pinvest.bean.Product;
import com.example.raito.p2pinvest.common.BaseFragment;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Raito on 2017/10/3.
 * invest子Fragment全部产品
 */

public class ProductListFragment extends BaseFragment {
    @BindView(R.id.tv_list_product)
    TextView tvListProduct;
    @BindView(R.id.lv_list_product)
    ListView lvListProduct;
    Unbinder unbinder;

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content, View view_success) {
        //List<Product> listProduct = getListProductByParse(content);
      List<String> list = new ArrayList<>();
        list.add("s");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("s");



        //设置数据适配器
        lvListProduct.setAdapter(new ProductAdapter(list,getContext()));


    }


    //解析数据
    private List<Product> getListProductByParse(String content) {
        //解析Json数据 fastJson
        JSONObject jsonObject = JSON.parseObject(content);
        boolean success = jsonObject.getBooleanValue("success");
        if (success) {

            String data = jsonObject.getString("data");
            return JSON.parseArray(data, Product.class);
        }
        return null;
    }

    //跑马灯  textView获取焦点
    private void initText() {
        tvListProduct.setFocusable(true);
        tvListProduct.setFocusableInTouchMode(true);
        tvListProduct.requestFocus();
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.product_list_fragment;
    }

}
