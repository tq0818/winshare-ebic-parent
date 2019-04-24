package com.winshare.edu.modules.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.winshare.edu.common.Constant;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.page.WebPage;
import com.winshare.edu.common.web.BaseController;
import com.winshare.edu.modules.classes.service.ClassInfoService;
import com.winshare.edu.modules.system.entity.OrgInfo;
import com.winshare.edu.modules.system.entity.SysDic;
import com.winshare.edu.modules.system.entity.TreeInfo;
import com.winshare.edu.modules.system.service.OrgInfoService;
import com.winshare.edu.modules.system.service.SysDicService;

@Controller
@RequestMapping("/system/org")
public class OrgInfoController extends BaseController{

	private static Logger log = Logger.getLogger(OrgInfoController.class);
	
	@Autowired
	private OrgInfoService orgInfoService;
	
	@Autowired
	ClassInfoService classInfoService;
	
	@Autowired
	private SysDicService sysDicService;
	
	@ModelAttribute("orgInfo")
	public OrgInfo get(@RequestParam(required=false) Long id) {
		if (id != null){
			return orgInfoService.get(id);
		}else{
			return new OrgInfo();
		}
	}
	
	@RequestMapping("/index")
	public String index(OrgInfo orgInfo, Model model) {
		return "modules/system/org/index";
	}
	
	@RequestMapping("/list")
	public String list(OrgInfo orgInfo, HttpServletRequest request, Model model) {
		String parentId = request.getParameter("parentId");
		if(null != orgInfo.getParentIds()){
			model.addAttribute("parentIds", orgInfo.getParentIds());
		}
		if(StringUtils.isNotBlank(parentId)){
			OrgInfo parent = new OrgInfo();
			parent.setId(Long.valueOf(parentId));
			orgInfo.setParent(parent);
		}
		String id = request.getParameter("orgId");
		if(StringUtils.isNotBlank(id)){
			orgInfo.setId(Long.valueOf(id));
			model.addAttribute("id", id);
		}
		WebPage<OrgInfo> page = new WebPage<OrgInfo>(orgInfoService.findChildren(new PageRequest(request), orgInfo));
		if(orgInfo.getId() != null){
			OrgInfo org = orgInfoService.get(orgInfo.getId());
			request.setAttribute("orgInfo", org);
			model.addAttribute("orgInfo", org);
		}
		SysDic sysDic = new SysDic();
		sysDic.setDicCode("org\\_level\\_");
		List<SysDic> sysDicList = sysDicService.find(sysDic);
		model.addAttribute("page", page);
		model.addAttribute("sysDicList", sysDicList);
		return "modules/system/org/list";
	}
	
	@RequestMapping("/form")
	public String form(OrgInfo orgInfo, Model model){
		if(orgInfo != null && orgInfo.getParent() != null){
			String areaName = Constant.areaMap.get(String.valueOf(orgInfo.getParent().getId()));
			model.addAttribute("areaName", areaName);
			OrgInfo org= orgInfoService.get(orgInfo.getParent().getId());
			orgInfo.getParent().setOrgName(org.getOrgName());
		}
		return "modules/system/org/form";
	}
	
	@RequestMapping("/save")
	public String save(OrgInfo orgInfo,HttpServletRequest request, RedirectAttributes redirectAttributes, Model model){
		if (!beanValidator(model, orgInfo)){
			return list(orgInfo,request, model);
		}

		orgInfoService.save(orgInfo);
		addMessage(redirectAttributes,"保存机构'" + orgInfo.getOrgName() + "'成功");
		log.info("OrgInfoController save org name :"+ orgInfo.getOrgName());
		return "redirect:/system/org/list";
	}
	
	@RequestMapping("/delete")
	public String delete(Long[] ids,RedirectAttributes redirectAttributes){
		//String msg = "存在子机构，删除失败";
		String msg = "此机构下存在用户信息，不允许删除";
		int count = 0;
		for(Long id : ids){
			int row = orgInfoService.delete(id);
			count = count + row;
		}
		if(count == ids.length){
			msg = "删除机构成功";
		}
		addMessage(redirectAttributes,msg);
		log.info("OrgInfoController delete ids :"+Arrays.toString(ids));
		return "redirect:/system/org/list";
	}
	
	@ResponseBody
	@RequestMapping("/isExistOrgNumber")
	public Boolean isExistOrgNumber(String orgNumber, String oldOrgNumber){
		OrgInfo orgInfo = new OrgInfo();
		orgInfo.setOrgNumber(orgNumber);
		if (orgNumber !=null && orgNumber.equals(oldOrgNumber)) {
			return true;
		} else if (orgNumber !=null && orgInfoService.get(orgInfo) == null) {
			return true;
		}
		return false;
	}
	
	@ResponseBody
	@RequestMapping("/isExistOrgCode")
	public Boolean isExistOrgCode(String orgCode, String oldOrgCode){
		OrgInfo orgInfo = new OrgInfo();
		orgInfo.setOrgCode(orgCode);
		if (StringUtils.isNotBlank(orgCode) && orgCode.equals(oldOrgCode)) {
			return true;
		} else if (StringUtils.isNotBlank(orgCode)  && orgInfoService.get(orgInfo) == null) {
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
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId,HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		OrgInfo org = new OrgInfo();
		String boundedOrgLevel = request.getParameter("boundedOrgLevel");
		if(StringUtils.isNotBlank(boundedOrgLevel)){
			org.setBoundedOrgLevel(boundedOrgLevel);
		}
		List<OrgInfo> list = null;
		try{
			list = orgInfoService.find(org);
		}catch (Exception e){
			e.printStackTrace();
		}

		for (int i=0; i<list.size(); i++){
			OrgInfo e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getOrgName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	/**
	 * 查询带班级的机构树
	 * @param extId
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/classTreeData")
	public List<Map<String, Object>> classTreeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<TreeInfo> list = orgInfoService.findTreeInfo(new TreeInfo());
		List<TreeInfo> clsList = classInfoService.getByOrgId();
		list.addAll(clsList);
		for (TreeInfo e :list){
			Map<String, Object> map = Maps.newHashMap();
			if(e.getIsClass() == 1){
				map.put("id", "class_"+e.getId());//作为班级时虚构的机构id
			}else{
				map.put("id", e.getId());//作为班级时虚构的机构id
			}
			map.put("pId", e.getParentId());
			map.put("pIds", e.getParentIds());
			map.put("name", e.getOrgName());
			map.put("isClass", e.getIsClass());//班级标识，1表示为班级
			map.put("cId", e.getcId());//班级id
			mapList.add(map);
		}
		return mapList;
	}
	
	/**
	 * 返回有班级的机构集合
	 * @param list
	 * @return
	 */
	public List<TreeInfo> getResultTreeList(List<TreeInfo> list){
		List<TreeInfo> treeList = new ArrayList<TreeInfo>(); 
		for(TreeInfo info : list){
			if(info.getIsClass() == 1){//班级
				treeList.add(info);
				List<TreeInfo> pList = getParentByNode(new ArrayList<TreeInfo>(),list,info);
				treeList.addAll(pList);
			}
		}
		return removeDuplicate(treeList);
	}
	
	/**
	 * 获取当前节点的父级集合
	 * @param node
	 * @return
	 */
	public List<TreeInfo> getParentByNode(List<TreeInfo> pList,List<TreeInfo> list,TreeInfo node){
		//pList.add(node);
		for(TreeInfo info : list){
			if(node.getParentId() == info.getId()){
				pList.add(info);
				getParentByNode(pList,list,info);
				break;
			}
		}
		return pList;
	}

	/**
	 * 集合去重复
	 * @param treeList
	 * @return
	 */
	public List<TreeInfo> removeDuplicate(List<TreeInfo> treeList){      
		HashSet h = new HashSet(treeList);      
		treeList.clear();      
		treeList.addAll(h);      
		return treeList;
	} 
	
	
	@RequestMapping("updateStatus/{status}")
	public String updateStatus(HttpServletRequest request,Long ids[],@PathVariable("status") String status, RedirectAttributes redirectAttributes){
	    Integer state = null;
		if(StringUtils.equals(status, "open")){
			state = new Integer(1);
		}else if(StringUtils.equals(status, "stop")){
			state = new Integer(0);
		}
		if(state != null && ids.length > 0){
			List<Long> idList = Arrays.asList(ids);
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("idList", idList);
			params.put("status",state);
			orgInfoService.updateStatus(params);
		}
		addMessage(redirectAttributes,"修改机构成功");
		return "redirect:/system/org/list";
	}
}
