package com.winshare.edu.modules.frame.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;

import com.winshare.edu.common.utils.SpringContextHolder;
import com.winshare.edu.modules.system.entity.AccountInfo;
import com.winshare.edu.modules.system.entity.SysMenu;
import com.winshare.edu.modules.system.service.AccountInfoService;


/**
 * 
 * 类描述：菜单标签
 * 
 * liyujiang @date： 日期：2016-7-1 时间：上午10:17:45
 * 
 * @version 1.0
 */

public class MenuTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String mainStyle = "default";
	private Boolean isRoot = false;
	private AccountInfoService accountInfoService = SpringContextHolder.getBean(AccountInfoService.class);

	public String getMainStyle() {
		return mainStyle;
	}

	public void setMainStyle(String mainStyle) {
		this.mainStyle = mainStyle;
	}

	public Boolean getIsRoot() {
		return isRoot;
	}

	public void setIsRoot(Boolean isRoot) {
		this.isRoot = isRoot;
	}

	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			String menu = (String) this.pageContext.getSession().getAttribute("menu");
			if (menu != null) {
				out.print(menu);
			} else {
				menu = end().toString();
				out.print(menu);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public StringBuffer end() {
		StringBuffer sb = new StringBuffer();
		AccountInfo accountInfo = (AccountInfo)SecurityUtils.getSubject().getSession().getAttribute("WINSHARE_USER");
		List<SysMenu> menus = accountInfoService.findMenuByAccountId(accountInfo.getId());
		SysMenu parent = new SysMenu();
		if (isRoot) {
			parent.setId(0l);
			parent.setDisplayStatus("1");
		} else {
			parent = menus.get(0);
		}
		if (StringUtils.isBlank(mainStyle) || mainStyle.equals("default")) {
			sb.append(getChildOfTree(parent, 0, menus));
		} else if (mainStyle.equals("ace")) {
			sb.append(getAceChildOfTree(parent, 0, menus));
		}
		return sb;
	}

	// 默认风格菜单
	private static String getChildOfTree(SysMenu parent, int level, List<SysMenu> menuList) {
		StringBuffer menuString = new StringBuffer();
		String href = "";
		if (level > 0) {// level 为0是功能菜单

			menuString.append("<li>");
			ServletContext context = SpringContextHolder.getBean(ServletContext.class);
			if (StringUtils.isNotBlank(parent.getMenuUrl())) {
				href = context.getContextPath() + parent.getMenuUrl();
			}
		}

		if (StringUtils.isBlank(parent.getMenuUrl()) && "1".equals(parent.getDisplayStatus())) {// 如果是父节点且显示
			if (level > 0) {
				menuString.append("<a href=\"" + href + "\"><i class=\"fa " + parent.getMenuIcon()
						+ "\"></i> <span class=\"nav-label\">" + parent.getMenuName()
						+ "</span><span class=\"fa arrow\"></span></a>");
			}
			if (level == 1) {
				menuString.append("<ul class=\"nav nav-second-level\">");
			} else if (level == 2) {
				menuString.append("<ul class=\"nav nav-third-level\">");
			} else if (level == 3) {
				menuString.append("<ul class=\"nav nav-forth-level\">");
			} else if (level == 4) {
				menuString.append("<ul class=\"nav nav-fifth-level\">");
			}
			for (SysMenu child : menuList) {
				if (child.getParent().getId().equals(parent.getId()) && "1".equals(parent.getDisplayStatus())) {
					menuString.append(getChildOfTree(child, level + 1, menuList));
				}
			}
			if (level > 0) {
				menuString.append("</ul>");
			}
		} else {
			menuString.append("<a class=\"J_menuItem\"  href=\"" + href + "\" ><i class=\"fa " + parent.getMenuIcon()
					+ "\"></i> <span class=\"nav-label\">" + parent.getMenuName() + "</span></a>");
		}
		if (level > 0) {
			menuString.append("</li>");
		}

		return menuString.toString();
	}

	// Ace风格菜单
	private static String getAceChildOfTree(SysMenu parent, int level, List<SysMenu> menuList) {
		StringBuffer menuString = new StringBuffer();
		String href = "";
		ServletContext context = SpringContextHolder.getBean(ServletContext.class);
		if (StringUtils.isNotBlank(parent.getMenuUrl())) {
			href = context.getContextPath() + parent.getMenuUrl();
		}

		if (level > 0) {// level 为0是功能菜单
			menuString.append("<li>");
			if (StringUtils.isBlank(parent.getMenuUrl()) && "1".equals(parent.getDisplayStatus())) {
				menuString.append("<a  href=\"" + href + "\" class=\"dropdown-toggle\">");
			} else {
				menuString.append("<a class=\"J_menuItem\" href=\"" + href + "\">");
			}
			menuString.append("<i class=\"menu-icon fa " + parent.getMenuIcon() + "\"></i>");
			menuString.append("<span class=\"menu-text\">" + parent.getMenuName() + "</span>");
			if (StringUtils.isBlank(parent.getMenuUrl()) && "1".equals(parent.getDisplayStatus())) {
				menuString.append("<b class=\"arrow fa fa-angle-down\"></b>");
			}
			menuString.append("</a>");
			menuString.append("<b class=\"arrow\"></b>");
		}
		if (StringUtils.isBlank(parent.getMenuUrl()) && "1".equals(parent.getDisplayStatus())) {
			if (level == 0) {
				menuString.append("<ul class=\"nav nav-list\">");
			} else {
				menuString.append("<ul class=\"submenu\">");
			}

			for (SysMenu child : menuList) {

				if (child.getParent().getId().equals(parent.getId()) && "1".equals(child.getDisplayStatus())) {
					menuString.append(getAceChildOfTree(child, level + 1, menuList));
				}

			}
			menuString.append("</ul>");
		}
		if (level > 0) {
			menuString.append("</li>");
		}
		return menuString.toString();
	}

}
