package com.winshare.edu.common.page;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class PageRequest {

	private int page = 1;

	private int size = 10;

	private String orderBy = "";
	
	public PageRequest(HttpServletRequest request) {
		// TODO Auto-generated constructor stub
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String pageOrderBy = request.getParameter("orderBy");
	
		if (StringUtils.isNumeric(pageNum)){
			this.page = Integer.parseInt(pageNum);
			if (0 > page) {
				page = 1;
			}
		}
		
		if (StringUtils.isNumeric(pageSize)){
			this.size = Integer.parseInt(pageSize);
	        if (0 >= size) {
	        	size = 10;
	        }
		}
		
		if (StringUtils.isNotBlank(pageOrderBy)){
			this.orderBy = pageOrderBy;
		}
	}
	
	public PageRequest(int page, int size){
		this(page, size, null);
	}
	
	public PageRequest(int page, int size, String orderBy){
		if (0 > page) {
			page = 1;
		}
		
        if (0 >= size) {
        	size = 10;
        }
        this.page = page;
        this.size = size;
        this.orderBy = orderBy;       
	}

    public int getPage() {
		return page;
	}

	public int getSize() {
		return size;
	}

	public String getOrderBy() {
		if(StringUtils.isBlank(orderBy)){
			return "";
		}
		// SQL过滤，防止注入 
		String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
					+ "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
		Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		if (sqlPattern.matcher(orderBy).find()) {
			return "";
		}
		return orderBy;
	}
	
	public boolean  equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof PageRequest)) {
			return false;
		}
		PageRequest that = (PageRequest) obj;
		boolean pageEqual = this.page == that.page;
		boolean sizeEqual = this.size == that.size;
		boolean orderByEqual = this.orderBy == null ? that.orderBy == null : this.orderBy.equals(that.orderBy);
		return pageEqual && sizeEqual && orderByEqual;
    }

    public int hashCode() {
		int result = 17;
		result = 31 * result + page;
		result = 31 * result + size;
		result = 31 * result + (null == orderBy ? 0 : orderBy.hashCode());
		return result;
    }
}
