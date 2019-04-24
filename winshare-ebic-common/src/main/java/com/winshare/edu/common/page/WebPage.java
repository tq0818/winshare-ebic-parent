package com.winshare.edu.common.page;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

public class WebPage<T> extends PageInfo<T>{

	private static final long serialVersionUID = 1039214295473693473L;
	
	protected int slider = 1;// 前后显示页面长度
	
	protected  int first = 1;
	
	protected String funcName = "page"; // 设置点击页码调用的js函数名称，默认为page，在一页有多个分页对象时使用。
	
	protected String funcParam = ""; // 函数的附加参数，第三个参数值。

	protected String orderBy = "";
	
    public WebPage() {
    }

    /**
     * 包装Page对象
     *
     * @param list
     */
    public WebPage(List<T> list) {
        this(list, 8);
    }

    /**
     * 包装Page对象
     *
     * @param list          page结果
     * @param navigatePages 页码数量
     */
    public WebPage(List<T> list, int navigatePages) {
    	super(list,navigatePages);
    	 if (list instanceof Page) {
    		 Page page = (Page) list;
    		 this.orderBy = page.getOrderBy();
    	 }
    }
	
	/**
	 * 默认输出当前分页标签 <div class="page">${page}</div>
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"fixed-table-pagination\" style=\"display: block;\">");
		long startIndex = (getPageNum() - 1) * getPageSize() + 1;
		long endIndex = getPageNum() * getPageSize() <= getTotal() ? getPageNum() * getPageSize() : getTotal();

		sb.append("<div class=\"pull-left pagination-detail\">");
		//sb.append("<span class=\"pagination-info\">显示第 " + startIndex + " 到第 " + endIndex + " 条记录，总共 " + getTotal()
		//		+ " 条记录</span>");
		
		sb.append("<span class=\"pagination-info\">总共 " + getTotal()
						+ " 条记录</span>");
		sb.append("<span class=\"page-list\">每页显示 <span class=\"btn-group dropup\">");
		sb.append(
				"<button type=\"button\" class=\"btn btn-default  btn-outline dropdown-toggle\" data-toggle=\"dropdown\" aria-expanded=\"false\">");
		sb.append("<span class=\"page-size\">" + getPageSize() + "</span> <span class=\"caret\"></span>");
		sb.append("</button>");
		sb.append("<ul class=\"dropdown-menu\" role=\"menu\">");
		sb.append("<li class=\"" + getSelected(getPageSize(), 10) + "\"><a href=\"javascript:" + funcName + "(" + getPageNum()
				+ ",10,'" + funcParam + "');\">10</a></li>");
		sb.append("<li class=\"" + getSelected(getPageSize(), 25) + "\"><a href=\"javascript:" + funcName + "(" + getPageNum()
				+ ",25,'" + funcParam + "');\">25</a></li>");
		sb.append("<li class=\"" + getSelected(getPageSize(), 50) + "\"><a href=\"javascript:" + funcName + "(" + getPageNum()
				+ ",50,'" + funcParam + "');\">50</a></li>");
		sb.append("<li class=\"" + getSelected(getPageSize(), 100) + "\"><a href=\"javascript:" + funcName + "(" + getPageNum()
				+ ",100,'" + funcParam + "');\">100</a></li>");
		sb.append("</ul>");
		sb.append("</span> 条记录</span>");
		sb.append("</div>");

		sb.append("<div class=\"pull-right pagination-roll\">");
		sb.append("<ul class=\"pagination pagination-outline\">");
		if (isIsFirstPage()) {// 如果是首页
			sb.append(
					"<li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-left\"></i></a></li>\n");
			sb.append(
					"<li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-left\"></i></a></li>\n");
		} else {
			sb.append("<li class=\"paginate_button previous\"><a href=\"javascript:\" onclick=\"" + funcName + "("
					+ first + "," + getPageSize() + ",'" + funcParam
					+ "');\"><i class=\"fa fa-angle-double-left\"></i></a></li>\n");
			sb.append("<li class=\"paginate_button previous\"><a href=\"javascript:\" onclick=\"" + funcName + "("
					+ getPrePage() + "," + getPageSize() + ",'" + funcParam + "');\"><i class=\"fa fa-angle-left\"></i></a></li>\n");
		}
		
		int begin = getPageNum() - (getNavigatePages() / 2);

		if (begin < first) {
			begin = first;
		}

		int end = begin + getNavigatePages() - 1;

		if (end >= getPages()) {
			end = getPages();
			begin = end - getNavigatePages() + 1;
			if (begin < first) {
				begin = first;
			}
		}
		

		if (begin > first) {
			int i = 0;
			for (i = first; i < first + slider && i < begin; i++) {
				sb.append("<li class=\"paginate_button \"><a href=\"javascript:\" onclick=\"" + funcName + "(" + i + ","
						+ getPageSize() + ",'" + funcParam + "');\">" + (i + 1 - first) + "</a></li>\n");
			}
			if (i < begin) {
				sb.append("<li class=\"paginate_button disabled\"><a href=\"javascript:\">...</a></li>\n");
			}
		}

		for (int i = begin; i <= end; i++) {
			if (i == getPageNum()) {
				sb.append("<li class=\"paginate_button active\"><a href=\"javascript:\">" + (i + 1 - first)
						+ "</a></li>\n");
			} else {
				sb.append("<li class=\"paginate_button \"><a href=\"javascript:\" onclick=\"" + funcName + "(" + i + ","
						+ getPageSize() + ",'" + funcParam + "');\">" + (i + 1 - first) + "</a></li>\n");
			}
		}

		if (getPages() - end > slider) {
			sb.append("<li class=\"paginate_button disabled\"><a href=\"javascript:\">...</a></li>\n");
			end = getPages() - slider;
		}

		for (int i = end + 1; i <= getPages(); i++) {
			sb.append("<li class=\"paginate_button \"><a href=\"javascript:\" onclick=\"" + funcName + "(" + i + ","
					+ getPageSize() + ",'" + funcParam + "');\">" + (i + 1 - first) + "</a></li>\n");
		}

		if (getPageNum() == getPages()) {
			sb.append(
					"<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-right\"></i></a></li>\n");
			sb.append(
					"<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-right\"></i></a></li>\n");
		} else {
			sb.append("<li class=\"paginate_button next\"><a href=\"javascript:\" onclick=\"" + funcName + "(" + getNextPage()
					+ "," + getPageSize() + ",'" + funcParam + "');\">" + "<i class=\"fa fa-angle-right\"></i></a></li>\n");
			sb.append("<li class=\"paginate_button next\"><a href=\"javascript:\" onclick=\"" + funcName + "(" + getPages()
					+ "," + getPageSize() + ",'" + funcParam + "');\">"
					+ "<i class=\"fa fa-angle-double-right\"></i></a></li>\n");
		}
			
		
		sb.append("</ul>");
		sb.append("</div>");
		sb.append("</div>");
		return sb.toString();
	}
	
	protected String getSelected(int pageSize, int selectedPageNo){
		if(pageSize == selectedPageNo){
			//return "selected";
			return "active";
		}else{
			return "";
		}
	}
	
	public String getOrderBy() {
		if(StringUtils.isBlank(orderBy)){
			return "";
		}
		return orderBy;
	}
	
	/**
	 * 设置查询排序，标准查询有效， 实例： updatedate desc, name asc
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
}
