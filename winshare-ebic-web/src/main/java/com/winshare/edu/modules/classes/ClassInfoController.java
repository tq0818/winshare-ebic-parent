package com.winshare.edu.modules.classes;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.page.WebPage;
import com.winshare.edu.common.web.BaseController;
import com.winshare.edu.modules.classes.entity.ClassInfo;
import com.winshare.edu.modules.classes.service.ClassInfoService;
import com.winshare.edu.modules.system.entity.OrgInfo;
import com.winshare.edu.modules.system.service.OrgInfoService;
import com.winshare.edu.modules.system.utils.SysDicUtils;
import com.winshare.edu.modules.user.entity.StudentInfo;
import com.winshare.edu.modules.user.service.StudentInfoService;

@Controller
@RequestMapping("/class")
public class ClassInfoController extends BaseController{
	
	@Autowired
	ClassInfoService classInfoService;
	@Autowired
	StudentInfoService studentInfoService;
	@Autowired
	OrgInfoService orgInfoService;

	
	private static Logger log = Logger.getLogger(ClassInfoController.class);
	
	@ModelAttribute("classInfo")
	public ClassInfo get(@RequestParam(required=false) Long id) {
		if (id != null){
			return classInfoService.getByClassId(id);
		}else{
			return new ClassInfo();
		}
	}
	
	@RequestMapping("/index")
	public String index(){
		
		return "modules/class/class_index";
	}
	
	@RequestMapping("/pageList")
	public String pageList(ClassInfo classInfo, HttpServletRequest request, HttpServletResponse response, Model model){
		WebPage<ClassInfo> page = new WebPage<ClassInfo>(classInfoService.pageList(classInfo, new PageRequest(request)));
		if(classInfo.getOrgId() != null){
			OrgInfo orgInfo = orgInfoService.get(classInfo.getOrgId());
			request.setAttribute("orgInfo", orgInfo);
		}
		model.addAttribute("page",page);
		return "modules/class/class_list";
	}
	
	@RequestMapping("/form")
	public String form(Long orgId, Long id, HttpServletRequest request){
		if(orgId == null){
			if(id != null){
				ClassInfo classInfo = classInfoService.getByClassId(id);
				orgId = classInfo.getOrgId();
			}
		}
		OrgInfo orgInfo = orgInfoService.get(orgId);
		request.setAttribute("orgInfo", orgInfo);
		return "modules/class/class_form";
	}
	
	@RequestMapping("/save")
	public String save(ClassInfo classInfo){
		classInfoService.save(classInfo);
		return "redirect:pageList?orgId="+classInfo.getOrgId();
	}
	
	/**
	 * 启用班级
	 * @param classInfo
	 * @return
	 */
	@RequestMapping("/enable")
	public String enable(ClassInfo classInfo){
		classInfo.setClassStatus("1");
		classInfoService.changeStatus(classInfo);
		return "redirect:pageList?orgId="+classInfo.getOrgId();
	}
	
	/**
	 * 禁用班级
	 * @param classInfo
	 * @return
	 */
	@RequestMapping("/disable")
	public String disable(ClassInfo classInfo){
		classInfo.setClassStatus("0");
		classInfoService.changeStatus(classInfo);
		return "redirect:pageList?orgId="+classInfo.getOrgId();
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
				ClassInfo classInfo = new ClassInfo();
				classInfo.setId(Long.valueOf(id));
				classInfo.setClassStatus(state);
				classInfoService.changeStatus(classInfo);
			}
		}
		return "redirect:/class/pageList";
	}
	
	private JSONArray parseStudent(List<StudentInfo> students){
		JSONArray result = new JSONArray();
		if(students != null && !students.isEmpty()){
			for (StudentInfo studentInfo : students) {
				JSONObject info = new JSONObject();
				info.put("id", studentInfo.getId());
				info.put("text", studentInfo.getName() + "(" + studentInfo.getStudentNo() + ")");
				info.put("search", studentInfo.getName() + ","+ studentInfo.getStudentNo());
				result.add(info);
			}
		}
		return result;
	}
	private JSONArray parseClass(List<ClassInfo> classes){
		JSONArray result = new JSONArray();
		if(classes != null && !classes.isEmpty()){
			for (ClassInfo classInfo : classes) {
				JSONObject info = new JSONObject();
				info.put("id", classInfo.getId());
				info.put("text", classInfo.getClassName() + "(" + SysDicUtils.getNameByCode("study_section", classInfo.getStudySection()) + ")");
				info.put("search", classInfo.getClassName());
				result.add(info);
			}
		}
		return result;
	}
	
	@RequestMapping("/setting")
	public String classSetting(ClassInfo classInfo, HttpServletRequest request){
		classInfo = classInfoService.getByClassId(classInfo.getId());
		List<StudentInfo> students = studentInfoService.listByClassId(classInfo.getId());
		List<ClassInfo> classes = classInfoService.getOtherClasses(classInfo);
		
		request.setAttribute("students", parseStudent(students));
		request.setAttribute("classes", parseClass(classes));
		request.setAttribute("orgId", classInfo.getOrgId());
		
		return "modules/class/class_setting";
	}
	
	/**
	 * 班级成员管理
	 * @param stuClsInfo
	 * @return
	 */
	@RequestMapping("/changeClass")
	public String classSetting(String stuClsInfo,Long orgId,RedirectAttributes redirectAttributes){
		String stuAndCls = StringEscapeUtils.unescapeHtml4(stuClsInfo);
		JSONObject jsonObject = JSONObject.parseObject(stuAndCls);
		String stuInfo = jsonObject.getString("leftSelected");//左侧学生信息
		String clsInfo = jsonObject.getString("rightSelected");//右侧班级信息
		//解析学生信息
		JSONArray stuJson = JSONArray.parseArray(stuInfo);
		JSONObject stuOne;
		List<Map<String,Object>> stuList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<stuJson.size();i++){
			  Map<String,Object> map = new HashMap<String,Object>();
			  stuOne = stuJson.getJSONObject(i); 
	          map.put("id", stuOne.get("id"));
	          stuList.add(map);
		}
		//解析班级信息
		JSONArray clsJson = JSONArray.parseArray(clsInfo);
		JSONObject clsOne;
		List<Map<String,Object>> clsList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<clsJson.size();i++){
			  Map<String,Object> map = new HashMap<String,Object>();
			  clsOne = clsJson.getJSONObject(i); 
	          map.put("id", clsOne.get("id"));
	          clsList.add(map);
		}
		
		for(Map<String, Object> stuMap : stuList){
			Long stuId = Long.parseLong((String) stuMap.get("id"));
			Long clsId = Long.parseLong((String) clsList.get(0).get("id"));
			studentInfoService.changeClass(stuId, clsId);
		}
		addMessage(redirectAttributes, "调班成功");
		return "redirect:/class/pageList?orgId="+orgId;
	}
	
	
	@RequestMapping("/move")
	public String move(String studentIdStr, String classId){
		
		return "";
	}
	
	@RequestMapping("/deleteClass")
	public String deleteClass(String ids,Long orgId,RedirectAttributes redirectAttributes){
		String msg = "失败,班级有学生";
		try{
			List<Long> idList = new ArrayList<Long>();
			String idArray[] = ids.split(",");
			for(String id : idArray){
				idList.add(Long.valueOf(id));
			}
			int count = classInfoService.deleteClass(idList);
			if(count == idList.size()){
				msg = "成功";
			}
		}catch(Exception e){
			log.error("ClassInfoController deleteClass is error : ", e);
		}
		addMessage(redirectAttributes, "删除班级"+msg);
		return "redirect:/class/pageList?orgId="+orgId;
	}
}
