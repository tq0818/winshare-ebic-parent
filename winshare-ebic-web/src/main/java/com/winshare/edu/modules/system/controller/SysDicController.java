package com.winshare.edu.modules.system.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.page.WebPage;
import com.winshare.edu.common.web.BaseController;
import com.winshare.edu.modules.system.entity.SysDic;
import com.winshare.edu.modules.system.service.SysDicService;

@Controller
@RequestMapping("/system/dic")
public class SysDicController extends BaseController{

	private static Logger log = Logger.getLogger(SysDicController.class);
	
	@Autowired
	private SysDicService sysDicService;
	
	@ModelAttribute("sysDic")
	public SysDic get(@RequestParam(required=false) Long id) {
		if (id != null){
			return sysDicService.get(id);
		}else{
			return new SysDic();
		}
	}
	
	@RequestMapping("/index")
	public String index(SysDic sysDic, Model model) {
		return "modules/system/dic/index";
	}
	
	@RequestMapping("/list")
	public String list(SysDic sysDic, HttpServletRequest request, Model model) {
		WebPage<SysDic> page = new WebPage<SysDic>(sysDicService.find(new PageRequest(request), sysDic));
		model.addAttribute("page", page);
		return "modules/system/dic/list";
	}
	
	@RequestMapping("/form")
	public String form(SysDic sysDic, Model model){
		return "modules/system/dic/form";
	}
	
	@RequestMapping("/save")
	public String save(SysDic sysDic,HttpServletRequest request, RedirectAttributes redirectAttributes, Model model){
		if (!beanValidator(model, sysDic)){
			return list(sysDic,request, model);
		}

		sysDicService.save(sysDic);
		addMessage(redirectAttributes,"保存机构'" + sysDic.getDicName() + "'成功");
		log.info("SysDicController save dic name "+sysDic.getDicName());
		return "redirect:/system/dic/list";
	}
	
	@RequestMapping("/delete")
	public String delete(Long[] ids,RedirectAttributes redirectAttributes){
		for(Long id : ids){
			sysDicService.delete(id);
		}
		log.info("SysDicController delete ids :"+Arrays.toString(ids));
		return "redirect:/system/dic/list";
	}
	
	@ResponseBody
	@RequestMapping("/isExistDicCode")
	public Boolean isExistDicCode(String dicCode, String oldDicCode){
		SysDic sysDic = new SysDic();
		sysDic.setDicCode(dicCode);
		if (dicCode !=null && dicCode.equals(oldDicCode)) {
			return true;
		} else if (dicCode !=null && sysDicService.get(sysDic) == null) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * @param extId
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<SysDic> list = sysDicService.find(new SysDic());
		for (int i=0; i<list.size(); i++){
			SysDic e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("dicCode", e.getDicCode());
				map.put("name", e.getDicName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}
