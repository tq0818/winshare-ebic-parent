package com.winshare.edu.modules.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.page.WebPage;
import com.winshare.edu.modules.system.entity.OrgPower;
import com.winshare.edu.modules.system.entity.OrgPowerLog;
import com.winshare.edu.modules.system.service.OrgPowerLogService;

@Controller("orgPowerLogController")
@RequestMapping("/system/org/powerLog")
public class OrgPowerLogController {

	@Autowired
	private OrgPowerLogService orgPowerLogService;
	


	@RequestMapping("/list")
	public String list(HttpServletRequest request,OrgPowerLog orgPowerLog,ModelMap model){
		WebPage<OrgPowerLog> page = new WebPage<OrgPowerLog>(orgPowerLogService.findList(new PageRequest(request), orgPowerLog));
		model.addAttribute("page", page);
		return "modules/system/orgPowerLog/list";
	}
	
}
