package com.centanet.hk.aplus.bean.detail;

public class APDayBook {

    private String date;        // 最新成交日期
    private String owner;       // 最新成交 (新業主)
    private String price;       // 最新成交價
    private String daybookURL;  // 成交網頁

    public APDayBook(String date, String owner, String price, String daybookURL) {
        this.date = date;
        this.owner = owner;
        this.price = price;
        this.daybookURL = daybookURL;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDaybookURL(String daybookURL) {
        this.daybookURL = daybookURL;
    }

    public String getDate() {
        return date;
    }

    public String getOwner() {
        return owner;
    }

    public String getPrice() {
        return price;
    }

    public String getDaybookURL() {
        return daybookURL;
    }
}
