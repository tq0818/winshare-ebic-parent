package com.winshare.edu.modules.classes.entity;

import com.winshare.edu.common.entity.BaseEntity;

public class TeacherScoreItem extends BaseEntity<TeacherScoreItem> {
   private Integer itemId;//id
   private Integer teacherId;//'教师ID',
   private Integer classId;//'班级ID',
   private String subject;//'科目',
   private String itemName;//'项目名称',
   private Integer score;//'分值',

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getSubjcet() {
        return subject;
    }

    public void setSubjcet(String subjcet) {
        this.subject = subjcet;
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
}
