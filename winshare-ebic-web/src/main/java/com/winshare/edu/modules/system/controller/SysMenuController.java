package com.winshare.edu.modules.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system/menu")
public class SysMenuController {

	@RequestMapping("/treeselect")
	public String treeselect(Model model) {
		return "modules/system/menuTreeselect";
	}
}
