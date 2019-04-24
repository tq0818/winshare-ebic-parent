package com.winshare.edu.modules.teachersMgm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.winshare.edu.common.Constant;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.page.WebPage;
import com.winshare.edu.common.web.BaseController;
import com.winshare.edu.modules.classes.entity.ClassInfo;
import com.winshare.edu.modules.classes.service.ClassInfoService;
import com.winshare.edu.modules.system.controller.AccountInfoController;
import com.winshare.edu.modules.system.entity.OrgInfo;
import com.winshare.edu.modules.system.entity.OrgPower;
import com.winshare.edu.modules.system.entity.SysDic;
import com.winshare.edu.modules.system.service.AccountInfoService;
import com.winshare.edu.modules.system.service.OrgInfoService;
import com.winshare.edu.modules.system.service.OrgPowerService;
import com.winshare.edu.modules.system.utils.SysDicUtils;
import com.winshare.edu.modules.teachersMgm.entity.Teacher;
import com.winshare.edu.modules.teachersMgm.service.TeacherMgmService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/** 
  * @author  hujie 
  * @date 创建时间：2017年9月18日 下午3:20:30  
  * @parameter  
  * @since  教师管理
  * @return  
*/
@Controller
@RequestMapping("/teacher")
public class TeachersMgmController extends BaseController{
	
	
	private static Logger log = Logger.getLogger(TeachersMgmController.class);

	
	@Autowired
	private TeacherMgmService teacherMgmService;
	
	@Autowired
	private ClassInfoService classInfoService;
	
	@Autowired
	private AccountInfoService acountInfoService;
	
	@Autowired
	private OrgInfoService orgInfoService;
	
	@Autowired
	private OrgPowerService orgPowerService;
	
	@ModelAttribute("teacher")
	public Teacher get(@RequestParam(required=false) Long userId) {
		if (userId != null){
			return teacherMgmService.get(userId);
		}else{
			return new Teacher();
		}
	}
	
	@RequestMapping("/index")
	public String index(){
		return "modules/teacher/index";
	}
	
	
	@RequestMapping("/list")
	public String list(Teacher teacher, HttpServletRequest request, Model model) {
		if(StringUtils.contains(teacher.getLoginName(), "%")){
			String loginName = teacher.getLoginName().replace("%", "\\%");
			teacher.setLoginName(loginName);
		}
		if(StringUtils.contains(teacher.getName(), "%")){
			String name = teacher.getName().replace("%", "\\%");
			teacher.setName(name);
		}
		WebPage<Teacher> page = new WebPage<Teacher>(teacherMgmService.find(new PageRequest(request), teacher));
		model.addAttribute("page", page);
		if(teacher.getOrgId() != null){
			OrgInfo orgInfo = orgInfoService.get(teacher.getOrgId());
			request.setAttribute("orgInfo", orgInfo);
		}
		//model.addAttribute("orgName", teacher.getOrgName());
		return "modules/teacher/list";
	}
	
	/**
	 * 启用教师
	 * @param teacher
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/enable")
	public String enable(String ids,Teacher teacher, RedirectAttributes redirectAttributes) {
		if(StringUtils.isNotBlank(ids)){
			String idArray[] = ids.split(",");
			for(String s : idArray){
				teacherMgmService.enableTeacher(Long.parseLong(s));
			}
		}

        addMessage(redirectAttributes, "启用教师成功");
        return "redirect:/teacher/list?repage";
	}
	
	/**
	 * 禁用教师
	 * @param teacher
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/disable")
	public String disable(String ids,Teacher teacher, RedirectAttributes redirectAttributes) {
		if(StringUtils.isNotBlank(ids)){
			String idArray[] = ids.split(",");
			for(String s : idArray){
				teacherMgmService.disableTeacher(Long.parseLong(s));
			}
		}

        addMessage(redirectAttributes, "禁用教师成功");
        return "redirect:/teacher/list";
	}
	
	/**
	 * 重置密码
	 * @param teacher
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/resetPwd")
	public String resetPwd(String ids,Teacher teacher, RedirectAttributes redirectAttributes) {
		if(StringUtils.isNotBlank(ids)){
			String idArray[] = ids.split(",");
			for(String s : idArray){
				acountInfoService.resetPassword(Long.parseLong(s),Constant.ACCOUNT_DEFAULT_PASSWORD);
			}
		}
        addMessage(redirectAttributes, "重置密码成功");
        return "redirect:/teacher/list?repage";
	}
	
	@RequestMapping("/form")
	public String form(Teacher teacher, Model model){
		JSONArray jsonCls = new JSONArray();
		List<ClassInfo> classInfoList = classInfoService.orgClasses(teacher.getOrgId());
		for(ClassInfo info:classInfoList){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("id", info.getId());
			jsonObj.put("text", info.getClassName());
			jsonObj.put("search", info.getClassName());
			jsonCls.add(jsonObj);
		}
		JSONArray jsonSub = new JSONArray();
		List<SysDic> dicList = SysDicUtils.getChildDicByParentCode("subject");
		for(SysDic info:dicList){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("id", info.getDicValue());
			jsonObj.put("text", info.getDicName());
			jsonObj.put("search", info.getDicName());
			jsonSub.add(jsonObj);
		}
		model.addAttribute("classVal",jsonCls);
		model.addAttribute("subjectVal",jsonSub);
		return "modules/teacher/form";
	}
	
	@RequestMapping("/edit")
	public String edit(Teacher teacher, Model model){
		model.addAttribute("teacher",teacher);
		
		//根据userid查询班级信息
		JSONArray jsonClsRight = new JSONArray();
		List<ClassInfo> clsRightList = teacherMgmService.selectClassByUid(teacher.getUserId());
		if(null != clsRightList){
			for(ClassInfo info:clsRightList){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", info.getId());
				jsonObj.put("text", info.getClassName());
				jsonObj.put("search", info.getClassName());
				jsonObj.put("selected", true);
				jsonClsRight.add(jsonObj);
			}
		}
		
		//根据userid查询科目信息
		JSONArray jsonSubRight = new JSONArray();
		List<Teacher> subListRight = teacherMgmService.selectSubjectsByUid(teacher.getUserId());
		if(null != subListRight){
			for(Teacher info:subListRight){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", info.getSubjectId());
				jsonObj.put("text", info.getSubjectName());
				jsonObj.put("search", info.getSubjectName());
				jsonObj.put("selected", true);
				jsonSubRight.add(jsonObj);
			}
		}
				
		JSONArray jsonClsLeft = new JSONArray();
		//查询除教师关联的班级之外的班级信息
		List<ClassInfo> classInfoList = classInfoService.orgClassesEscapeUid(teacher.getOrgId(),teacher.getUserId());
		if(null != classInfoList){
			for(ClassInfo info:classInfoList){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", info.getId());
				jsonObj.put("text", info.getClassName());
				jsonObj.put("search", info.getClassName());
				jsonClsLeft.add(jsonObj);
			}
		}
		
		JSONArray jsonSubLeft = new JSONArray();
		//筛选出教师关联的学科之外的学科信息
		List<SysDic> dicListLeft = SysDicUtils.getChildDicByParentCode("subject");
		if(null != dicListLeft){
			if(null != subListRight){
				for(int j=0;j<subListRight.size();j++){
					for(int i=0;i<dicListLeft.size();i++){
						if(dicListLeft.get(i).getDicValue().equals(subListRight.get(j).getSubjectId())){
							dicListLeft.remove(i);
							break;
						}
					}
				}
			}
			
			for(SysDic info:dicListLeft){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", info.getDicValue());
				jsonObj.put("text", info.getDicName());
				jsonObj.put("search", info.getDicName());
				jsonSubLeft.add(jsonObj);
			}
		}
		
		jsonClsLeft.addAll(jsonClsRight);
		jsonSubLeft.addAll(jsonSubRight);
		
		model.addAttribute("classVal",jsonClsLeft);
		model.addAttribute("subjectVal",jsonSubLeft);
		return "modules/teacher/form";
	}
	
	/**
	 * 保存教师基本信息
	 * @param teacher
	 * @param model
	 * @return
	
	@ResponseBody
	@RequestMapping(value="/saveOrUpdateTchInfo", method =RequestMethod.POST)
	public Map<String,Object> saveTchInfo(Teacher teacher, Model model){
		Map<String,Object> map = new HashMap<String, Object>();
		Long userId = teacherMgmService.saveOrUpdateTeacherInfo(teacher);
		if(userId > 0){
			map.put("userId",userId);
			map.put("success",true);
		}else{
			map.put("success",false);
		}
		return map;
	} */
	
	/**
	 * 保存教师关联班级信息
	 * @param usrId
	 * @param claVal
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveTchRClassInfo", method =RequestMethod.POST)
	public Map<String,Object> saveTchRClassInfo(@RequestParam Long usrId, @RequestParam String claVal,Model model){
		Map<String,Object> result = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(claVal)){
			//JSONArray jsonData = JSONArray.fromObject(claVal);
			JSONArray jsonData = JSONArray.parseArray(claVal);
			JSONObject jsonOne;
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for(int i=0;i<jsonData.size();i++){
				  Map<String,Object> map = new HashMap<String,Object>();
		          jsonOne = jsonData.getJSONObject(i); 
		          map.put("id", jsonOne.get("id"));
		          map.put("userId", usrId);
		          list.add(map);
			}
			int tag = teacherMgmService.saveTchRClassInfo(list);
			if(tag > 0){
				result.put("success", true);
			}else{
				result.put("success", false);
			}
		}else{
			result.put("success", false);
		}
		return result;
	}
	
	
	/**
	 * 保存教师和学科关联关系
	 * @param usrId
	 * @param subVal
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveTchRSubjectInfo", method =RequestMethod.POST)
	public Map<String,Object> saveTchRSubjectInfo(@RequestParam Long usrId, @RequestParam String subVal,Model model){
		Map<String,Object> result = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(subVal)){
			//JSONArray jsonData = JSONArray.fromObject(subVal);
			JSONArray jsonData = JSONArray.parseArray(subVal);
			JSONObject jsonOne;
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for(int i=0;i<jsonData.size();i++){
				  Map<String,Object> map = new HashMap<String,Object>();
		          jsonOne = jsonData.getJSONObject(i); 
		          map.put("text", jsonOne.get("text"));
		          map.put("userId", usrId);
		          list.add(map);
			}
			int tag = teacherMgmService.saveTchRSubject(list);
			if(tag > 0){
				result.put("success", true);
			}else{
				result.put("success", false);
			}
		}else{
			result.put("success", false);
		}
		
		return result;
	}
	
	/**
	 * 保存教师信息
	 * @param teacher
	 * @param choiceClass
	 * @param choiceSubject
	 * @param request
	 * @param redirectAttributes
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(Teacher teacher,@RequestParam String choiceClass,@RequestParam String choiceSubject,
			HttpServletRequest request, RedirectAttributes redirectAttributes, Model model){
		teacher.setPassword(Constant.ACCOUNT_DEFAULT_PASSWORD);
		String msg = "失败";
		if(StringUtils.isNotBlank(teacher.getLoginName()) && teacher.getOrgId() != null){
			try{
			    long userId = teacherMgmService.saveOrUpdateTeacherInfo(teacher,StringEscapeUtils.unescapeHtml4(choiceClass),StringEscapeUtils.unescapeHtml4(choiceSubject));
			    if(userId > 0){
			    	msg = "成功";
			    }
			}catch(Exception e){
				log.error("TeachersMgmController save is error :", e);
			}
		}else{
			msg = "登录名或机构为空";
		}
		addMessage(redirectAttributes,"保存教师信息'" + teacher.getLoginName() + "'"+msg);
		return "redirect:/teacher/list";
	}
	
	
	@RequestMapping("/redirect")
	public String redirect(Long orgId,RedirectAttributes redirectAttributes){
		return "redirect:/teacher/list?orgId="+orgId;
	}
	
	
	
}
