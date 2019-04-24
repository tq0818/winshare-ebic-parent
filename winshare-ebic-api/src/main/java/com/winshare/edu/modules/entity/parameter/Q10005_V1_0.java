package com.winshare.edu.modules.entity.parameter;


import javax.validation.constraints.NotNull;

public class Q10005_V1_0 {

	@NotNull(message = "书籍ID不能为空")
	private Integer bookId;

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
}
