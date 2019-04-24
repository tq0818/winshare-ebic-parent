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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.page.WebPage;
import com.winshare.edu.common.web.BaseController;
import com.winshare.edu.modules.system.entity.AreaInfo;
import com.winshare.edu.modules.system.entity.OrgInfo;
import com.winshare.edu.modules.system.service.AreaInfoService;

@Controller
@RequestMapping("/system/area")
public class AreaInfoController extends BaseController{

	private static Logger log = Logger.getLogger(AreaInfoController.class);
	
	@Autowired
	private AreaInfoService areaInfoService;
	
	@RequestMapping("/index")
	public String index(OrgInfo orgInfo, Model model) {
		return "modules/system/area/index";
	}
	
	@RequestMapping("/list")
	public String list(AreaInfo areaInfo, HttpServletRequest request, Model model) {
		AreaInfo ai = null;
		Long id = (Long)request.getSession().getAttribute("aeraId");
		if(null != id){
			model.addAttribute("id", id);
		}
		if(areaInfo.getId() != null){
			ai = areaInfoService.get(areaInfo.getId());
			model.addAttribute("areaInfo", ai);
		}
		WebPage<AreaInfo> page = new WebPage<AreaInfo>(areaInfoService.find(new PageRequest(request), areaInfo));
		model.addAttribute("page", page);
		return "modules/system/area/list";
	}
	
	@RequestMapping("/findChildren")
	public String findChildren(AreaInfo areaInfo, HttpServletRequest request, Model model) {
		AreaInfo ai = null;
		Long id = areaInfo.getId();
		if(null != id){
			request.getSession().setAttribute("aeraId",id);
			model.addAttribute("id", id);
		}
		if(areaInfo.getId() != null){
			ai = areaInfoService.get(areaInfo.getId());
			model.addAttribute("areaInfo", ai);
		}
		WebPage<AreaInfo> page = new WebPage<AreaInfo>(areaInfoService.findListByParentId(new PageRequest(request),areaInfo));
		model.addAttribute("page", page);
		return "modules/system/area/list";
	}
	
	@RequestMapping("/findChildrenName")
	public String findChildrenName(AreaInfo areaInfo, HttpServletRequest request, Model model) {
		Long id = (Long)request.getSession().getAttribute("aeraId");
		if(null != id){

		}
		WebPage<AreaInfo> page = new WebPage<AreaInfo>(areaInfoService.findListByChildrenName(new PageRequest(request),areaInfo));
		model.addAttribute("page", page);
		model.addAttribute("childArea", areaInfo);
		return "modules/system/area/list";
	}
	
	
	@RequestMapping("/form")
	public String form(AreaInfo areaInfo, Model model){
		if(areaInfo.getId() != null){
			areaInfo = areaInfoService.get(areaInfo.getId());
		}else{
			areaInfo = new AreaInfo();
		}
		model.addAttribute("areaInfo", areaInfo);
		return "modules/system/area/form";
	}
	
	@RequestMapping("/save")
	public String save(AreaInfo areaInfo,HttpServletRequest request, RedirectAttributes redirectAttributes, Model model){
		AreaInfo parent = null;
		try{
			if(areaInfo.getParent() !=null && areaInfo.getParent().getId() != null){
				parent = areaInfoService.get(areaInfo.getParent().getId());
				areaInfo.setParentIds(parent.getParentIds()+parent.getId()+",");
			}else{
				parent = new AreaInfo();
				parent.setId(0l);
				areaInfo.setParent(parent);
				areaInfo.setParentIds(parent.getId()+",");
			}
			int row = areaInfoService.save(areaInfo);
			String result = "失败";
			if(row > 0){
				result = "成功";
			}
			addMessage(redirectAttributes,"保存区域'" + areaInfo.getName() + "'"+result);
			log.info("AreaInfoController save area name :"+areaInfo.getName());
		}catch(Exception e){
			log.error("AreaInfoController save is error :", e);
		}
		return "redirect:/system/area/list";
	}
	
	@RequestMapping("/delete")
	public String delete(Long[] ids,RedirectAttributes redirectAttributes){
		int count = 0;
		String msg = "存在子区域，删除失败";
		try{
			for(Long id : ids){
				int row = areaInfoService.delete(id);
				count = count + row;
			}
			if(count > 0 && count == ids.length){
				msg = "区域删除成功";
			}
			addMessage(redirectAttributes,msg);
			log.info("AreaInfoController delete ids :"+Arrays.toString(ids));
		}catch(Exception e){
			log.error("AreaInfoController delete is error :", e);
		}
		return "redirect:/system/area/list";
	}
	
	@ResponseBody
	@RequestMapping("/treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<AreaInfo> list = areaInfoService.find(new AreaInfo());
		for (int i=0; i<list.size(); i++){
			AreaInfo e = list.get(i);
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				mapList.add(map);
			
		}
		return mapList;
	}
	
	@RequestMapping("isExistsByAreaCode")
	@ResponseBody
	public boolean isExistsByAreaCode(AreaInfo areaInfo,HttpServletRequest request,HttpServletResponse response){
		boolean result = false;
		if(StringUtils.isNotBlank(areaInfo.getCode())){
			int count = areaInfoService.countByCode(areaInfo);
			if(count == 0){
				result = true;
			}
		}
		return result;
	}
}
