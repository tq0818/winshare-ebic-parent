package com.winshare.edu.modules.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.page.WebPage;
import com.winshare.edu.common.web.BaseController;
import com.winshare.edu.modules.system.entity.AccountInfo;
import com.winshare.edu.modules.system.entity.TeacherVersion;
import com.winshare.edu.modules.system.service.TeacherVersionService;

@Controller
@RequestMapping("/system/teaVersion")
public class TeacherVersionController extends BaseController{

	private static Logger log = Logger.getLogger(TeacherVersionController.class);
	
	@Autowired
	private TeacherVersionService tvService;
	
	@RequestMapping("/index")
	public String index(TeacherVersion tv) {
		return "modules/system/teacherVersion/index";
	}
	
	@RequestMapping("/list")
	public String list(TeacherVersion tv, HttpServletRequest request, Model model) {
		WebPage<TeacherVersion> page = new WebPage<TeacherVersion>(tvService.findList(new PageRequest(request), tv));
		model.addAttribute("page", page);
		return "modules/system/teacherVersion/list";
	}
	

	@RequestMapping("/form")
	public String form(TeacherVersion tv,ModelMap map) {
		if(tv != null && tv.getId() !=null){
			tv = tvService.get(tv.getId());
			map.addAttribute("tv", tv);
		}
		return "modules/system/teacherVersion/form";
	}
	
	@RequestMapping("/save")
	public String save(TeacherVersion tv,RedirectAttributes redirectAttributes) {
		int result = tvService.add(tv);
		String msg = "失败";
		if(result > 0){
			msg = "成功";
		}
		addMessage(redirectAttributes,"版本号'" + tv.getVersion() + "'"+msg);
		log.info("TeacherVersionController save teacher version  :"+tv.getVersion());
		return "redirect:/system/teaVersion/list";
	
	}
	
	@RequestMapping("/del")
	public String del(String ids,RedirectAttributes redirectAttributes) {
		String msg = "失败";
		if(StringUtils.isNotBlank(ids)){
			List<Long> idList = new ArrayList<Long>();
			String idArray[] = ids.split(",");
			for(String id : idArray){
				idList.add(Long.parseLong(id));
			}
			int result = tvService.del(idList);
			if(result > 0){
				msg = "成功";
			}
			
		}
		addMessage(redirectAttributes,"删除"+msg);
		log.info("TeacherVersionController del teacher version ids :"+ids);
		return "redirect:/system/teaVersion/list";
	}
	
	
	@RequestMapping("/update")
	public String update(TeacherVersion tv,RedirectAttributes redirectAttributes) {
		String msg = "失败";
		if(tv != null && tv.getId() != null){
			int result = tvService.update(tv);
			if(result > 0){
				msg = "成功";
			}
		}
		addMessage(redirectAttributes,"修改"+msg);
		log.info("TeacherVersionController update teacher version id :"+tv.getId());
		return "redirect:/system/teaVersion/list";
	}
	
}
