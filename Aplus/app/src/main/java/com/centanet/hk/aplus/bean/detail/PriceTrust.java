package com.centanet.hk.aplus.bean.detail;

/**
 * Created by yangzm4 on 2018/7/2.
 */

public class PriceTrust {

    private String CreateTime;     /** 叫價日期 */
    private String Price;          /** 價格 */
    private String ChangeRate;     /** 價格波動 */
    private String CreateUserName; /** 講價人 */
    private String Rate;           /** 价格差价 */

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setChangeRate(String changeRate) {
        ChangeRate = changeRate;
    }

    public void setCreateUserName(String createUserName) {
        CreateUserName = createUserName;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getRate() {
        return Rate;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getPrice() {
        return Price;
    }

    public String getChangeRate() {
        return ChangeRate;
    }

    public String getCreateUserName() {
        return CreateUserName;
    }

    @Override
    public String toString() {
        return "RentTrust{" +
                "CreateTime='" + CreateTime + '\'' +
                ", Price='" + Price + '\'' +
                ", ChangeRate='" + ChangeRate + '\'' +
                ", CreateUserName='" + CreateUserName + '\'' +
                '}';
    }
}
