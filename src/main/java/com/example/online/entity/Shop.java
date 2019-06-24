package com.example.online.entity;

public class Shop {

	private long shopId;
	private String shopName;
	private String shopImg;
	private String shopInfo;
    private long sellerId;
    private long buyerId;
	private int ifSolved;//是否全部被卖出 ：1(当quantity为0):全部被卖，该商品信息不再展示   0:未全部被卖出，继续展示

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public String getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(String shopInfo) {
        this.shopInfo = shopInfo;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    public int getIfSolved() {
        return ifSolved;
    }

    public void setIfSolved(int ifSolved) {
        this.ifSolved = ifSolved;
    }

}
