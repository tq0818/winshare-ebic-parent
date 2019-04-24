package com.winshare.edu.modules.frame.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.winshare.edu.common.utils.CookieUtils;
import com.winshare.edu.modules.system.entity.AccountInfo;
import com.winshare.edu.modules.system.service.AccountInfoService;

@Controller
public class IndexController {
	

    @Autowired
    private AccountInfoService accountInfoService;
	
	@RequestMapping("/")
	public String index(HttpServletRequest request, Model model) {
		AccountInfo accountInfo = (AccountInfo)SecurityUtils.getSubject().getSession().getAttribute("WINSHARE_USER");
        if(accountInfo == null){
        	Subject subject = SecurityUtils.getSubject();
        	String loginName = subject.getPrincipal().toString();
    		accountInfo = accountInfoService.findByLoginName(loginName);
    		SecurityUtils.getSubject().getSession().setAttribute("WINSHARE_USER", accountInfo);
        }
		System.out.println();
		// 风格切换
		String indexStyle = "default";
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
				continue;
			}
			if (cookie.getName().equalsIgnoreCase("theme")) {
				indexStyle = cookie.getValue();
			}
		}
		// 要添加自己的风格，复制下面三行即可
		if (StringUtils.isNotEmpty(indexStyle) && indexStyle.equalsIgnoreCase("ace")) {
			return "modules/frame/main-ace";
		}
		return "modules/frame/main";
	}
	
	@RequestMapping("home")
	public String home(HttpServletRequest request) {
		return "modules/frame/home";
	}
	
	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isNotBlank(theme)) {
			CookieUtils.setCookie(response, "theme", theme);
		} else {
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:" + request.getParameter("url");
	}
}
