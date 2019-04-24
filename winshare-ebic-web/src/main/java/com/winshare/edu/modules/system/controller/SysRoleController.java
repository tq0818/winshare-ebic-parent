package com.winshare.edu.modules.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.page.WebPage;
import com.winshare.edu.common.web.BaseController;
import com.winshare.edu.modules.system.entity.AccountInfo;
import com.winshare.edu.modules.system.entity.AreaInfo;
import com.winshare.edu.modules.system.entity.OrgInfo;
import com.winshare.edu.modules.system.entity.SysMenu;
import com.winshare.edu.modules.system.entity.SysOperation;
import com.winshare.edu.modules.system.entity.SysRole;
import com.winshare.edu.modules.system.service.SysMenuService;
import com.winshare.edu.modules.system.service.SysOperationService;
import com.winshare.edu.modules.system.service.SysRoleService;

@Controller
@RequestMapping("/system/sysRole")
public class SysRoleController extends BaseController{

	private static Logger log = Logger.getLogger(SysRoleController.class);
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysMenuService sysMenuService;
	
	@Autowired
	private SysOperationService sysOperationService;
	
	@RequestMapping("/index")
	public String index(OrgInfo orgInfo, Model model) {
		return "modules/system/sysRole/index";
	}
	
	@RequestMapping("/list")
	public String list(SysRole sysRole, HttpServletRequest request, Model model) {
		WebPage<SysRole> page = new WebPage<SysRole>(sysRoleService.find(new PageRequest(request), sysRole));
		model.addAttribute("page", page);
		return "modules/system/sysRole/list";
	}
	
	
	
	
	@RequestMapping("/form")
	public String form(SysRole sysRole, Model model){
		if(sysRole.getId() != null){
			sysRole = sysRoleService.get(sysRole.getId());
		}else{
			sysRole = new SysRole();
		}
		model.addAttribute("sysRole", sysRole);
		return "modules/system/sysRole/form";
	}
	
	@RequestMapping("/save")
	public String save(SysRole sysRole,HttpServletRequest request, RedirectAttributes redirectAttributes, Model model){
		String result = "失败";
		try{
			if(StringUtils.isNotBlank(sysRole.getRoleName()) && StringUtils.isNotBlank(sysRole.getRoleIden())){
				int row = sysRoleService.save(sysRole);
				if(row > 0){
					result = "成功";
				}
			}
		}catch(Exception e){
			log.error("SysRoleController save is error : ", e);
		}
		addMessage(redirectAttributes,"保存角色'" + sysRole.getRoleName() + "'"+result);
		log.info("SysRoleController save role name :"+sysRole.getRoleName());
		return "redirect:/system/sysRole/list";
	}
	
	@RequestMapping("/update/{status}")
	public String updateStatus(@PathVariable("status") String status,Long id[],HttpServletRequest request, RedirectAttributes redirectAttributes){
		Integer state = null;
		String result = "失败";
		List<Long> roleIdList = null;
		if(StringUtils.equals(status, "open")){
			state = new Integer(1);
		}else if(StringUtils.equals(status, "stop")){
			state = new Integer(0);
		}
		if(id !=null && id.length > 0){
			roleIdList = Arrays.asList(id);
			int row = sysRoleService.batchUpdateStatus(roleIdList,state);
			if(row > 0){
				result = "成功";
			}
		}
		addMessage(redirectAttributes,"修改角色状态"+result);
		return "redirect:/system/sysRole/list";
	}
	
	@RequestMapping("/delete")
	public String delete(Long[] ids,RedirectAttributes redirectAttributes){
		String msg = "存在有该角色帐号，删除失败";
		int count = 0;
		try{
			for(Long id : ids){
				int row = sysRoleService.delete(id);
				count = count + row;
			}
			if(count == ids.length){
				msg =  "删除成功";
			}
		}catch(Exception e){
			log.error("SysRoleController is error :", e);
		}
		addMessage(redirectAttributes,msg);
		log.info("SysRoleController delete ids :"+Arrays.toString(ids));
		return "redirect:/system/sysRole/list";
	}
	
	@RequestMapping("/authorization")
	public String authorization(ModelMap model,HttpServletRequest request){
		 long roleId = Long.parseLong(request.getParameter("roleId"));
		 model.addAttribute("roleId", roleId);
		 List<Long> menuIdList = sysRoleService.findMenuIdListByRoleId(roleId);
		 if(menuIdList.size() > 0){
			 String menuIds = Arrays.toString(menuIdList.toArray()).replace(" ", "");
			 menuIds = menuIds.substring(1,menuIds.length() - 1);
			 model.addAttribute("menuIds", menuIds); 
		 }
		 List<SysOperation> operationList = sysOperationService.findAll(roleId);
		 if(operationList.size() > 0){
			 JSONArray operations = (JSONArray)JSONObject.toJSON(operationList);
			 model.addAttribute("operations", operations); 
		 }
		 return "modules/system/sysRole/authorization";
	}
	
	@ResponseBody
	@RequestMapping("/treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		AccountInfo account = (AccountInfo) SecurityUtils.getSubject().getSession().getAttribute("WINSHARE_USER");
		List<Map<String, Object>> list = sysMenuService.findMenuTree();
		for (int i=0; i<list.size(); i++){
			Map<String, Object> e = list.get(i);
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.get("id"));
				map.put("pId", e.get("pId"));
				map.put("pIds", e.get("pIds"));
				map.put("name", e.get("name"));
				mapList.add(map);
			
		}
		return mapList;
	}
	
	@RequestMapping("/authorize")
	public String authorize(HttpServletRequest request, RedirectAttributes redirectAttributes){
		String msg = "授权失败";
		try{
			String menuIds = request.getParameter("menuIds");
			String operationIds = request.getParameter("operationIds");
			Long roleId = Long.parseLong(request.getParameter("roleId"));
			List<Long> menuIdList = new ArrayList<Long>();
	        List<Long> operList = new ArrayList<Long>();
	      	if(StringUtils.isNoneBlank(menuIds)){
				String menuArray[] = menuIds.split(",");
				for(String menuId : menuArray){
					menuIdList.add(Long.valueOf(menuId)); 
				}
				
			}
			if(StringUtils.isNotBlank(operationIds)){
				String operArray[] = operationIds.split(",");
				for(String operId : operArray){
					operList.add(Long.valueOf(operId));
				}
				
			}
			if(menuIdList.size() > 0 || operList.size() > 0){
				AccountInfo account = (AccountInfo) SecurityUtils.getSubject().getSession().getAttribute("WINSHARE_USER");
				sysRoleService.saveAuthorizeRole(roleId, menuIdList,operList);
			}
			msg = "授权成功";
		}catch(Exception e){
			log.error("SysRoleController is error :", e);
		}
		addMessage(redirectAttributes,msg);
		return "redirect:/system/sysRole/list";
	}
	
	
	@RequestMapping("isExistsByNameIden")
	@ResponseBody
	public boolean isExistsByNameIden(SysRole sysRole,HttpServletRequest request,HttpServletResponse response){
		boolean result = false;
		if(StringUtils.isNotBlank(sysRole.getRoleIden()) || StringUtils.isNotBlank(sysRole.getRoleName())){
			int count = sysRoleService.countByNameIden(sysRole);
			if(count == 0){
				result = true;
			}
		}
		return result;
	}
	
}
