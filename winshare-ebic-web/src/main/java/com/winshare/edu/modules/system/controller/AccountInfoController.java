package com.winshare.edu.modules.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.winshare.edu.modules.system.entity.*;
import com.winshare.edu.modules.system.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.winshare.edu.common.Constant;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.page.WebPage;
import com.winshare.edu.common.security.PasswordHelper;
import com.winshare.edu.common.web.BaseController;

@Controller
@RequestMapping("/system/account")
public class AccountInfoController extends BaseController{

	private static Logger log = Logger.getLogger(AccountInfoController.class);
	
	@Autowired
	private AccountInfoService accountInfoService;

	@Autowired
	private SysDicService sysDicService;
	
	@Autowired
	private SysMenuService sysMenuService;
	
	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private OrgInfoService orgInfoService;

	@Autowired
	private AreaInfoService areaInfoService;
	
	@PostConstruct
	public void getAareList(){
		initAreaData();
	}
	
	@ModelAttribute("accountInfo")
	public AccountInfo get(@RequestParam(required=false) Long id) {
		if (id != null){
			return accountInfoService.get(id);
		}else{
			return new AccountInfo();
		}
	}
	
	@RequestMapping("/index")
	public String index(OrgInfo orgInfo, Model model) {
		return "modules/system/account/index";
	}
	
	@RequestMapping("/list")
	public String list(AccountInfo accountInfo, HttpServletRequest request, Model model) {
		if(null != accountInfo.getOrgInfo()){
			Long orgInfoId = accountInfo.getOrgInfo().getId();
			if(null != orgInfoId){
				model.addAttribute("orgInfoId", orgInfoId);
			}
		}
		String accountType = accountInfo.getAccountType();
		if(null != accountType){
			model.addAttribute("accountType", accountType);
		}

		WebPage<AccountInfo> page = new WebPage<AccountInfo>(accountInfoService.find(new PageRequest(request), accountInfo));
		SysRole sysRole = new SysRole();
		List<SysRole> roleList = sysRoleService.find(sysRole);
		Map<String,SysRole> roleMap = new HashMap<String,SysRole>();
		for(SysRole role : roleList){
			roleMap.put(role.getId().toString(), role);
		}
		model.addAttribute("roleMap", roleMap);
		model.addAttribute("page", page);
		return "modules/system/account/list";
	}
	
	@RequestMapping("/form")
	public String form(AccountInfo accountInfo,	String accountId, String orgInfoId ,Model model){
		if(null != accountId && !"".equals(accountId)){
			accountInfo.setId(Long.parseLong(accountId));
		}
		if(orgInfoId != null && !"".equals(orgInfoId)){
			model.addAttribute("orgInfoId", orgInfoId);
			OrgInfo orgInfo= orgInfoService.get(Long.parseLong(orgInfoId));
			accountInfo.setOrgInfo(orgInfo);
			String birthplace = Constant.areaMap.get(String.valueOf(orgInfoId));
			model.addAttribute("birthplace", birthplace);
			model.addAttribute("areaName", orgInfo.getOrgName());
		}else if(null != accountInfo.getAreaId() && !"".equals(accountInfo.getAreaId())){
			AreaInfo areaInfo = areaInfoService.get(accountInfo.getAreaId());
			model.addAttribute("birthplace", areaInfo.getName());
		}
		SysRole sysRole = new SysRole();
		List<SysRole> roleList = sysRoleService.find(sysRole);
		model.addAttribute("roleList", roleList);
		if(accountInfo.getId() != null){
			List<Long> roleIdList = sysRoleService.findRoleIdListByAccountId(accountInfo.getId());
		    if(roleIdList.size() > 0){
		    	model.addAttribute("roleIdList", roleIdList);
		    }
		}
		return "modules/system/account/form";
	}
	
	@RequestMapping("/save")
	public String save(AccountInfo accountInfo,HttpServletRequest request, RedirectAttributes redirectAttributes, Model model){
		String msg = "失败";
		try{
			if (!beanValidator(model, accountInfo)){
				return list(accountInfo,request, model);
			}
			if(StringUtils.isBlank(accountInfo.getBirthDate())){
				accountInfo.setBirthDate(null);
			}
			if(accountInfo.getId() == null){
				accountInfo.setPassword(Constant.ACCOUNT_DEFAULT_PASSWORD);
			}
			accountInfoService.saveAccount(accountInfo);
			msg = "成功";
		}catch(Exception e){
			log.error("AccountInfoController save is error :", e);
		}
		addMessage(redirectAttributes,"保存帐号'" + accountInfo.getLoginName() + "'"+msg);
		log.info("AccountInfoController save account login name :"+accountInfo.getLoginName());
		return "redirect:/system/account/list";
	}
	
	@RequestMapping("/delete")
	public String delete(Long[] ids,RedirectAttributes redirectAttributes){
		String msg = "失败";
		try{
			for(Long id : ids){
				accountInfoService.delete(id);
			}
			msg = "成功";
			log.info("AccountInfoController delete ids :"+Arrays.toString(ids));
		}catch(Exception e){
			log.error("AccountInfoController delete is error :", e);
		}
		addMessage(redirectAttributes,"删除帐号" + msg);
		return "redirect:/system/account/list";
	}
	
	@RequestMapping("/enable")
	public String enable(Long id, RedirectAttributes redirectAttributes) {
		accountInfoService.updateStatus(id, "1");
		return "redirect:/system/account/list";
	}
	
	@RequestMapping("/disable")
	public String disable(Long id, RedirectAttributes redirectAttributes) {
		accountInfoService.updateStatus(id, "0");
		return "redirect:/system/account/list";
	}
	
	@RequestMapping("updateBatchStatus")
	public String updateBatchStatus(String ids,String status, RedirectAttributes redirectAttributes) {
		String state = null;
		if(StringUtils.equals(status, "open")){
			state = "1";
		}else if(StringUtils.equals(status, "stop")){
			state = "0";
		}
		if(StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(state)){
			String idArray[] = ids.split(",");	
			for(String id : idArray){
				accountInfoService.updateStatus(Long.valueOf(id), state);
			}
		}
		return "redirect:/system/account/list";
	}
	
	@ResponseBody
	@RequestMapping("/isExistLoginName")
	public Boolean isExistLoginName(String loginName, String oldLoginName){
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setKeyword(loginName);
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return true;
		} else if (loginName !=null && accountInfoService.get(accountInfo) == null) {
			return true;
		}
		return false;
	}
	
	@ResponseBody
	@RequestMapping("/getAreaInfoByParentId")
	public List<AreaInfo> getAreaInfoByParentId(HttpServletRequest request){
		Long id = Long.parseLong(request.getParameter("parentId"));
		AreaInfo areaInfo = new AreaInfo();
		areaInfo.setId(id);
		List<AreaInfo> list = accountInfoService.findAreaListByParentId(areaInfo);
		return list;
				
	}
	
	@RequestMapping("/addRolePage")
	public String addRolePage(HttpServletRequest request,ModelMap model){
		Long accountId = Long.valueOf(request.getParameter("id"));
		List<Long> roleIdList = sysRoleService.findRoleIdListByAccountId(accountId);
		if(roleIdList.size() > 0){
			 String roleIds = Arrays.toString(roleIdList.toArray()).replace(" ", "");
			 roleIds = roleIds.substring(1,roleIds.length() - 1);
			 model.addAttribute("roleIds", roleIds); 
		}
		List<SysRole> roleList = sysRoleService.find(null);
		model.addAttribute("accountId", accountId);
		model.addAttribute("roleList", roleList);
		return "modules/system/account/addRole";
				
	}
	
//	@ResponseBody
//	@RequestMapping("/treeData")
//	public List<Map<String, Object>> roleData(HttpServletRequest request){
//		List<Map<String, Object>> roleList = sysMenuService.findMenuTree();
//        return roleList;
//	}
	
	@RequestMapping("/addRole")
	public String addRole(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Map<String,Object> params = new HashMap<String,Object>();
		Long accountId  = Long.valueOf(request.getParameter("accountId"));
		String roleIds = request.getParameter("roleIds");
		List<Long> roleIdList = new ArrayList<Long>();
		if(StringUtils.isNoneBlank(roleIds)){
			for(String id : roleIds.split(",")){
				roleIdList.add(Long.valueOf(id));
			}
		}
		params.put("roleIdList", roleIdList);
		params.put("accountId", accountId);
		int row = sysRoleService.saveUserRoleRelation(params);
		if(row > 0){
			addMessage(redirectAttributes,"帐号添加角色成功");
		}else{
			addMessage(redirectAttributes,"帐号添加角色失败");
		}
		return "redirect:/system/account/list";
	}
	
	
	private void initAreaData(){
		AreaInfo areaInfo = new AreaInfo();
		areaInfo.setId(0l);
		List<AreaInfo> provinceList = accountInfoService.findAreaListByParentId(areaInfo);//初始化省
		Constant.provinceList.addAll(provinceList);
		if(Constant.provinceList.size() > 0){//初始化市
			for(AreaInfo province : Constant.provinceList){
				areaInfo.setId(province.getId());
				List<AreaInfo> cList = accountInfoService.findAreaListByParentId(areaInfo);
				if(cList.size() > 0){
					Constant.cityList.addAll(cList);
				}
			}
			
		}
		if(Constant.cityList != null && Constant.cityList.size() > 0){
			for(AreaInfo city : Constant.cityList){
				areaInfo.setId(city.getId());
				List<AreaInfo> dList = accountInfoService.findAreaListByParentId(areaInfo);
				if(dList.size() > 0){
					Constant.districtList.addAll(dList);
				}
			}
		}
		List<AreaInfo> allList = new ArrayList<AreaInfo>();
		allList.addAll(Constant.provinceList);
		allList.addAll(Constant.cityList);
		allList.addAll(Constant.districtList);
		for(int i = 0;i < allList.size();i++){
			Constant.areaMap.put(String.valueOf(allList.get(i).getId()), allList.get(i).getName());
		}
	}
	
	@RequestMapping("formUserSelf")
	public String formUserSelf(HttpServletRequest request,HttpServletResponse response){
		return "modules/system/account/formUserSelf";
	}
	
	@RequestMapping("updateLoginInfo")
    @ResponseBody
	public String updateLoginInfo(HttpServletRequest request,HttpServletResponse response){
		String result = "failed";
		try{
			AccountInfo accountInfo = new AccountInfo();
			String loginName = request.getParameter("loginName");
			String oldPassword = request.getParameter("oldPassword");
			String newPassword = request.getParameter("newPassword");
			if(StringUtils.isBlank(loginName) && (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword))){
				return result;
			}
			AccountInfo currAccount = (AccountInfo)SecurityUtils.getSubject().getSession().getAttribute("WINSHARE_USER");
			if(StringUtils.isNotBlank(loginName)){
				accountInfo.setLoginName(loginName);
			}
			if(StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
				oldPassword = new PasswordHelper().encryptPassword(oldPassword, currAccount.getAccount());
				accountInfo.setPassword(oldPassword);
				newPassword = new PasswordHelper().encryptPassword(newPassword, currAccount.getAccount());
				accountInfo.setNewPassword(newPassword);
			}
			accountInfo.setId(currAccount.getId());
			int row = accountInfoService.updateLoginInfo(accountInfo);
			if(row > 0){
				result = "success";
			}
		}catch(Exception e){
			log.error("AccountInfoController updateLoginInfo is error :", e);
		}
		return result;
	}
	
	@RequestMapping("passwordReset")
	public String passwordReset(HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		String msg = "密码重置失败";
		try{
			String ids[] = request.getParameter("ids").split(",");
			for(String id : ids){
				accountInfoService.resetPassword(Long.valueOf(id), Constant.ACCOUNT_DEFAULT_PASSWORD);
			}
			msg = "密码重置成功";
		}catch(Exception e){
			log.error("AccountInfoController passwordReset is error : ", e);
		}
		addMessage(redirectAttributes,msg);
		return "redirect:/system/account/list";
	}
}
