package com.example.raito.p2pinvest.bean;

/**
 * Created by Raito on 2017/9/27.
 */

public class Image {
    public int ID;
    public String IMAPAURL;
    public String IMAURL;

    public Image() {
    }

    @Override
    public String toString() {
        return "Image{" +
                "ID=" + ID +
                ", IMAPAURL='" + IMAPAURL + '\'' +
                ", IMAURL='" + IMAURL + '\'' +
                '}';
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getIMAPAURL() {
        return IMAPAURL;
    }

    public void setIMAPAURL(String IMAPAURL) {
        this.IMAPAURL = IMAPAURL;
    }

    public String getIMAURL() {
        return IMAURL;
    }

    public void setIMAURL(String IMAURL) {
        this.IMAURL = IMAURL;
    }
}
