package com.winshare.edu.modules.system.controller;

import java.text.ParseException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.page.WebPage;
import com.winshare.edu.common.utils.DateUtils;
import com.winshare.edu.common.web.BaseController;
import com.winshare.edu.modules.system.entity.AccountInfo;
import com.winshare.edu.modules.system.entity.AreaInfo;
import com.winshare.edu.modules.system.entity.OrgPower;
import com.winshare.edu.modules.system.entity.OrgPowerLog;
import com.winshare.edu.modules.system.service.OrgPowerLogService;
import com.winshare.edu.modules.system.service.OrgPowerService;

@Controller
@RequestMapping("/system/org/power")
public class OrgPowerController extends BaseController {
	
	private static Logger log = Logger.getLogger(OrgPowerController.class);
	
	@Autowired
	private OrgPowerService orgPowerService;
	
	@Autowired
	private OrgPowerLogService orgPowerLogService;
	
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,ModelMap model){
		return "modules/system/orgPower/index";
	}

	
	@RequestMapping("/list")
	public String list(HttpServletRequest request,OrgPower orgPower,ModelMap model){
		WebPage<OrgPower> page = new WebPage<OrgPower>(orgPowerService.findList(new PageRequest(request), orgPower));
		model.addAttribute("page", page);
		return "modules/system/orgPower/list";
	}

	@RequestMapping("/form")
	public String form(HttpServletRequest request,OrgPower orgPower,ModelMap model){
		if(orgPower.getId() != null){
			orgPower = orgPowerService.getById(orgPower.getId());
			model.addAttribute("orgPower", orgPower);
		}
		return "modules/system/orgPower/form";
	}
	
	@RequestMapping(value="/save",method={RequestMethod.GET,RequestMethod.POST})
	public String save(HttpServletRequest request,OrgPower orgPower,RedirectAttributes redirectAttributes) throws ParseException{
		int row = 0;
		String result = "失败,当前机构合同还未到期.";
		try{
			AccountInfo account = (AccountInfo) SecurityUtils.getSubject().getSession().getAttribute("WINSHARE_USER");
			orgPower.setAccountId(account.getId());
			Date endTime = DateUtils.parse(request.getParameter("endTime"), "yyyy-MM-dd HH:mm:ss");
			boolean bl = DateUtils.isBefore(new Date(), endTime);
			if(bl){
				orgPower.setEndTime(endTime);
				if(orgPower.getId() == null){
					orgPower.setStatus(1);
					row = orgPowerService.save(orgPower);
				}else{
					row = orgPowerService.update(orgPower);
				}
				if(row > 0){
					result = "成功";
					OrgPowerLog log = new OrgPowerLog();
					BeanUtils.copyProperties(orgPower, log);
					log.setUpdateAccountId(account.getId());
					log.setUpdateAccountName(account.getLoginName());
					orgPowerLogService.save(log);
				}
			}else{
				result = "失败,合同截止时间小于当前时间";
			}
		}catch(Exception e){
			log.error("OrgPowerController save is error :", e);
		}
		addMessage(redirectAttributes,"授权'" + orgPower.getOrgName() + "'"+result);
		log.info("OrgPowerController save org name "+orgPower.getOrgName());
		return "redirect:/system/org/power/list";
	}
	
	
	@RequestMapping("isExistsContractNumber")
	@ResponseBody
	public boolean isExistsContractNumber(HttpServletRequest request,HttpServletResponse response,OrgPower orgPower){
		boolean result = false;
		int row = orgPowerService.countContractNumber(orgPower);
		if(row == 0){
			result = true; 
		}
		return result;
	}

	@RequestMapping("updateStatus/{status}")
	public String updateStates(HttpServletRequest request, Long ids[], @PathVariable("status") String status, RedirectAttributes redirectAttributes){
		Integer states = null;
		if(StringUtils.equals(status, "open")){
			states = new Integer(1);
		}else if(StringUtils.equals(status, "stop")){
			states = new Integer(0);
		}
		if(states != null && ids.length > 0){
			List<Long> idLists = Arrays.asList(ids);
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("idList", idLists);
			param.put("status",states);
			orgPowerService.updateStatus(param);
		}
		addMessage(redirectAttributes,"修改成功");
		return "redirect:/system/org/power/list";
	}

}
