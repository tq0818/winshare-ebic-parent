package com.winshare.edu.modules.system.entity;

import com.winshare.edu.common.entity.BaseEntity;

import java.util.Date;

public class BooksCatalogInfo extends BaseEntity<BooksCatalogInfo> {
    private Integer bookId;
    private String name;
    private Integer sort;
    private String parentId;
    private String parentIds;
    private Integer level;
    private Date createTime;
    private Date modifyTime;
    private Date sRemark;
    private Integer sDelete;
    private String sortNum;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getModifyTime() {
        return modifyTime;
    }

    @Override
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getsRemark() {
        return sRemark;
    }

    public void setsRemark(Date sRemark) {
        this.sRemark = sRemark;
    }

    public Integer getsDelete() {
        return sDelete;
    }

    public void setsDelete(Integer sDelete) {
        this.sDelete = sDelete;
    }

    public String getSortNum() {
        return sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }
}
