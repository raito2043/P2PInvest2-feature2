package com.example.raito.p2pinvest.bean;

/**
 * Created by Raito on 2017/10/8.
 * 用户信息
 */


public class User {

    private int id;// 编号
    private String name;// 姓名
    private String password;// 密码
    private String phone;// 手机号
    private String imageUrl;// 头像地址
    private boolean isCredit;// 是否公安部认证 调用公安部的接口用查看是否以实名

    public User() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isCredit() {
        return isCredit;
    }

    public void setCredit(boolean credit) {
        this.isCredit = credit;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", isCredit=" + isCredit +
                '}';
    }
}
