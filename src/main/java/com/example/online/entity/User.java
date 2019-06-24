package com.example.online.entity;

public class User {
    private long userId;
    private String userName;
    private String schoolNumber;
    private String schoolPassword;
    private String userImg;
    private String telephone;
    private String reputation;
    private String qq;
    private int prosecutions;
    private String introduceMyself;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getSchoolPassword() {
        return schoolPassword;
    }

    public void setSchoolPassword(String schoolPassword) {
        this.schoolPassword = schoolPassword;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getReputation() {
        return reputation;
    }

    public void setReputation(String reputation) {
        this.reputation = reputation;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public int getProsecutions() {
        return prosecutions;
    }

    public void setProsecutions(int prosecutions) {
        this.prosecutions = prosecutions;
    }

    public String getIntroduceMyself() {
        return introduceMyself;
    }

    public void setIntroduceMyself(String introduceMyself) {
        this.introduceMyself = introduceMyself;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", schoolNumber='" + schoolNumber + '\'' +
                ", schoolPassword='" + schoolPassword + '\'' +
                ", userImg='" + userImg + '\'' +
                ", telephone='" + telephone + '\'' +
                ", reputation='" + reputation + '\'' +
                ", qq='" + qq + '\'' +
                ", prosecutions=" + prosecutions +
                ", introduceMyself='" + introduceMyself + '\'' +
                '}';
    }
}
