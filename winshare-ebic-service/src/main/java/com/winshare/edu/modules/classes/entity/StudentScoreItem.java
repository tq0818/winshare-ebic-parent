package com.winshare.edu.modules.classes.entity;

import com.winshare.edu.common.entity.BaseEntity;

public class StudentScoreItem extends BaseEntity<StudentScoreItem> {

    private Integer scoreId;//积分id
    private String itemName;//得分项
    private Integer score;//分值
    private String bookName;//书籍
    private String chapterName;//章节
    private String remarks;//描述
    private String source;//来源

    public Integer getScoreId() {
        return scoreId;
    }

    public void setScoreId(Integer scoreId) {
        this.scoreId = scoreId;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
