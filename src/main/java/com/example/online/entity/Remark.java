package com.example.online.entity;

import java.util.Date;

public class Remark {
    private long remarkId;
    private long remarkedUserId;//被评价者Id
    private long remarkUserId;//评价者Id;
    private String remarkInfo;//评价内容
    private Date createTime;//评价时间

    public long getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(long remarkId) {
        this.remarkId = remarkId;
    }

    public long getRemarkedUserId() {
        return remarkedUserId;
    }

    public void setRemarkedUserId(long remarkedUserId) {
        this.remarkedUserId = remarkedUserId;
    }

    public long getRemarkUserId() {
        return remarkUserId;
    }

    public void setRemarkUserId(long remarkUserId) {
        this.remarkUserId = remarkUserId;
    }

    public String getRemarkInfo() {
        return remarkInfo;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Remark{" +
                "remarkId=" + remarkId +
                ", remarkedUserId=" + remarkedUserId +
                ", remarkUserid=" + remarkUserId +
                ", remarkInfo='" + remarkInfo + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
