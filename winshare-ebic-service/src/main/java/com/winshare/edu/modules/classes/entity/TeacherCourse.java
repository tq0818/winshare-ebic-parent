package com.winshare.edu.modules.classes.entity;

import com.winshare.edu.common.entity.BaseEntity;

public class TeacherCourse extends BaseEntity<TeacherCourse> {

   private Long courseId;
   private Integer classId;            // '班级ID',
   private Integer teacherId;        // '教师ID',
   private String subject;              // '科目',
   private String teacherName;         // '教师姓名',
   private String teacherProtocol;     // '教师端协议',
   private String teacherIp;           // '教师端IP',
   private String teacherPort;         //'教师端端口',
   private String gradeName;           // '年级名称',
   private String subjectName;         // '科目名称',
   private String bookName;            // '书籍',
   private String chapterName;         // '章节',
   private String courseStatus;        // '授课状态',


    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

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

    public String getSubjcet() {
        return subject;
    }

    public void setSubjcet(String subjcet) {
        this.subject = subjcet;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherProtocol() {
        return teacherProtocol;
    }

    public void setTeacherProtocol(String teacherProtocol) {
        this.teacherProtocol = teacherProtocol;
    }

    public String getTeacherIp() {
        return teacherIp;
    }

    public void setTeacherIp(String teacherIp) {
        this.teacherIp = teacherIp;
    }

    public String getTeacherPort() {
        return teacherPort;
    }

    public void setTeacherPort(String teacherPort) {
        this.teacherPort = teacherPort;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getSubjcetName() {
        return subjectName;
    }

    public void setSubjcetName(String subjcetName) {
        this.subjectName = subjcetName;
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

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }
}
