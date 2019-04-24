package com.winshare.edu.modules.classes.entity;

import com.winshare.edu.common.entity.BaseEntity;

public class StudentTemp extends BaseEntity<StudentTemp> {
    private Integer accountId;//账户id
    private Integer aeraId;//区域id
    private String name;//姓名
    private String sex;//性别
    private String userAvatar;//用户头像
    private String cardNum;//卡号

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getAeraId() {
        return aeraId;
    }

    public void setAeraId(Integer aeraId) {
        this.aeraId = aeraId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
}
