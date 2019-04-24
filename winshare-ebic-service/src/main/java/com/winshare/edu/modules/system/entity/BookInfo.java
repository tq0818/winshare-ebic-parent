package com.winshare.edu.modules.system.entity;

import com.winshare.edu.common.entity.BaseEntity;

import java.util.Date;

public class BookInfo extends BaseEntity<BookInfo> {

    private String name;//书籍名称
    private String subjectName;//学科
    private String gradeName;//年级
    private String publisherName;//出版社
    private String fasciculeName;//册别
    private String version;//版次
    private String versionName;//版次名称
    private Long fileIdVarchar;//封面图id
    private String filePath;//封面地址
    private String sOperatorName;//操作者名称
    private Long sOperatorAccount;//操作者id
    private String sRemark;//备注
    private Integer sDelete;// 逻辑删除状态(0已删除、1未删除；默认1)
    private String subjectCode;//学科code
    private String fasciculeCode;//册别code
    private String publisherCode;//出版社code
    private String gradeCode;//年级code
    private Long fileId;
    private Integer relationState;//映射关系状态
    private String relationActor;//映射关系最后操作者ID
    private String actorName;//映射关系最后操作者名称
    private Date actTime; //映射关系最后操作时间
    private Integer isRecommend;//是否为推荐课本(0：否，1：是)
    private String editionCode;//版本code
    private String editionName;//版本名称
    private String phaseCode;//学段编码
    private String phaseName;//学段名称


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getFasciculeName() {
        return fasciculeName;
    }

    public void setFasciculeName(String fasciculeName) {
        this.fasciculeName = fasciculeName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Long getFileIdVarchar() {
        return fileIdVarchar;
    }

    public void setFileIdVarchar(Long fileIdVarchar) {
        this.fileIdVarchar = fileIdVarchar;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getsOperatorName() {
        return sOperatorName;
    }

    public void setsOperatorName(String sOperatorName) {
        this.sOperatorName = sOperatorName;
    }

    public Long getsOperatorAccount() {
        return sOperatorAccount;
    }

    public void setsOperatorAccount(Long sOperatorAccount) {
        this.sOperatorAccount = sOperatorAccount;
    }

    public String getsRemark() {
        return sRemark;
    }

    public void setsRemark(String sRemark) {
        this.sRemark = sRemark;
    }

    public Integer getsDelete() {
        return sDelete;
    }

    public void setsDelete(Integer sDelete) {
        this.sDelete = sDelete;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getFasciculeCode() {
        return fasciculeCode;
    }

    public void setFasciculeCode(String fasciculeCode) {
        this.fasciculeCode = fasciculeCode;
    }

    public String getPublisherCode() {
        return publisherCode;
    }

    public void setPublisherCode(String publisherCode) {
        this.publisherCode = publisherCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Integer getRelationState() {
        return relationState;
    }

    public void setRelationState(Integer relationState) {
        this.relationState = relationState;
    }

    public String getRelationActor() {
        return relationActor;
    }

    public void setRelationActor(String relationActor) {
        this.relationActor = relationActor;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public Date getActTime() {
        return actTime;
    }

    public void setActTime(Date actTime) {
        this.actTime = actTime;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getEditionCode() {
        return editionCode;
    }

    public void setEditionCode(String editionCode) {
        this.editionCode = editionCode;
    }

    public String getEditionName() {
        return editionName;
    }

    public void setEditionName(String editionName) {
        this.editionName = editionName;
    }

    public String getPhaseCode() {
        return phaseCode;
    }

    public void setPhaseCode(String phaseCode) {
        this.phaseCode = phaseCode;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }
}
