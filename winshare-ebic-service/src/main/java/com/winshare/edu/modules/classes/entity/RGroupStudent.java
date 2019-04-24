package com.winshare.edu.modules.classes.entity;

import com.winshare.edu.common.entity.BaseEntity;

public class RGroupStudent extends BaseEntity<RGroupStudent> {
    private Integer groupId;
    private Integer studentId;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}
