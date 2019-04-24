package com.winshare.edu.modules.classes.entity;

import com.winshare.edu.common.entity.BaseEntity;

public class GroupScoreItem extends BaseEntity<GroupScoreItem> {

    private Integer groupId;//分组id
    private String itemName;//的分项
    private Integer score;//分值
    private String bookName;//书籍
    private String chapterName;//章节
    private String remarks;//描述

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
