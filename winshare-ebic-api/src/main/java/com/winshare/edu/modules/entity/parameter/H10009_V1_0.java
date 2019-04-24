package com.winshare.edu.modules.entity.parameter;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class H10009_V1_0 {

	@NotNull(message = "分组ID不能为空")
	private Long groupId;

	@NotNull(message = "得分项不能为空")
	@Length(max=100, message="得分项长度必须小于100")
	private String  itemName;

	private Integer score;

	@NotNull(message = "书籍名称不能为空")
	@Length(max=100, message="分组ID长度必须小于100")
	private String bookName;

	@NotNull(message = "章节名称不能为空")
	@Length(max=100, message="章节名称长度必须小于100")
	private String chapterName;


	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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
}
