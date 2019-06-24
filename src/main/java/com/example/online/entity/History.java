package com.example.online.entity;

public class History {
    private long historyId;
    private long userId;
    public long shopId;
    //是否被收藏 1在收藏列表和历史记录里均展示 0只在历史记录里展示
    private int ifCollected;

    @Override
    public String toString() {
        return "History{" +
                "historyId=" + historyId +
                ", userId=" + userId +
                ", shopId=" + shopId +
                ", ifCollected=" + ifCollected +
                '}';
    }

    public long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(long historyId) {
        this.historyId = historyId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getShopId() {
        System.out.println("123");
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public int getIfCollected() {
        return ifCollected;
    }

    public void setIfCollected(int ifCollected) {
        this.ifCollected = ifCollected;
    }
}
