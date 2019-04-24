package com.winshare.edu.modules.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.page.WebPage;
import com.winshare.edu.modules.system.entity.AreaInfo;
import com.winshare.edu.modules.system.entity.OrgInfo;
import com.winshare.edu.modules.system.entity.SysLog;
import com.winshare.edu.modules.system.service.SysLogService;

@Controller
@RequestMapping("/system/sysLog")
public class SysLogController {

	@Autowired
	private SysLogService sysLogService;
	
	@RequestMapping("/index")
	public String index(SysLog sysLog, Model model) {
		return "modules/system/log/index";
	}
	
	@RequestMapping("/list")
	public String list(SysLog sysLog, HttpServletRequest request, Model model) {
		WebPage<SysLog> page = new WebPage<SysLog>(sysLogService.find(new PageRequest(request), sysLog));
		model.addAttribute("page", page);
		return "modules/system/log/list";
	}
}
