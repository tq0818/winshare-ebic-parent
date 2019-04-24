package com.winshare.edu.modules.classes.entity;

import com.winshare.edu.common.entity.BaseEntity;

public class ClassGroup extends BaseEntity<ClassGroup> {

    private Integer classId;//班级id
    private Integer teacherId;//教师id
    private Integer leaderId;//组长id
    private String grade;//年级
    private String subject;//科目
    private String groupName;//组名
    private Integer groupScore;//积分
    private String isStatus;//删除状态
    private Long[] students;//改组学生id
    private String student;//改组学生id

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupScore() {
        return groupScore;
    }

    public void setGroupScore(Integer groupScore) {
        this.groupScore = groupScore;
    }

    public String getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(String isStatus) {
        this.isStatus = isStatus;
    }

    public Long[] getStudents() {
        return students;
    }

    public void setStudents(Long[] students) {
        this.students = students;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }
}
