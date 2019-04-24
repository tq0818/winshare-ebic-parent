     package com.winshare.edu.modules.students;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.winshare.edu.common.Constant;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.page.WebPage;
import com.winshare.edu.common.web.BaseController;
import com.winshare.edu.modules.classes.entity.ClassInfo;
import com.winshare.edu.modules.classes.service.ClassInfoService;
import com.winshare.edu.modules.system.service.AccountInfoService;
import com.winshare.edu.modules.system.utils.ReadExcelUtils;
import com.winshare.edu.modules.teachersMgm.entity.Teacher;
import com.winshare.edu.modules.user.entity.StudentInfo;
import com.winshare.edu.modules.user.service.StudentInfoService;

/** 
  * @author  hujie 
  * @date 创建时间：2017年9月26日 上午10:38:05  
  * @parameter  
  * @since  学生管理
  * @return  
*/

@Controller
@RequestMapping("/student")
public class StudentsController extends BaseController{
	
	@Autowired
	private StudentInfoService studentInfoService;
	
	@Autowired
	ClassInfoService classInfoService;
	
	@Autowired
	private AccountInfoService acountInfoService;
	
	private static Logger log = Logger.getLogger(StudentsController.class);
	
	@ModelAttribute("studentInfo")
	public StudentInfo get(@RequestParam(required=false) Long id) {
		if (id != null){
			return studentInfoService.get(id);
		}else{
			return new StudentInfo();
		}
	}
	
	@RequestMapping("/index")
	public String index(){
		return "modules/student/index";
	}
	
	/**
	 * 跳转学生列表页面
	 * @param student
	 * @param request 
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(StudentInfo student, HttpServletRequest request, Model model) {
		Long cId = student.getcId();
		Long orgId = student.getOrgId();
		if(null != cId){//点选班级
			ClassInfo clsInfo = classInfoService.getByClassId(cId);
			student.setOrgId(clsInfo.getOrgId());
			model.addAttribute("cId", cId);
		}
		if(null != orgId){
			model.addAttribute("orgId", orgId);
		}
		WebPage<StudentInfo> page = new WebPage<StudentInfo>(studentInfoService.find(new PageRequest(request), student));
		model.addAttribute("page", page);
		model.addAttribute("orgId", student.getOrgId());
		model.addAttribute("isClass", student.getIsClass());
		return "modules/student/list";
	}

	
	@RequestMapping("/form")
	public String form(StudentInfo student, Model model){
		if(null != student.getId())
			model.addAttribute("student",student);
		if(student != null && student.getAreaId() != null){
			String areaName = Constant.areaMap.get(String.valueOf(student.getAreaId()));
			model.addAttribute("areaName", areaName);
		}
		model.addAttribute("ebicSystemUrl", Constant.myResources.getString("ebicSystemUrl"));
		return "modules/student/form";
	}
	

	/**
	 * 保存修改学生信息
	 * @param student
	 * @param request
	 * @param redirectAttributes
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(StudentInfo student,HttpServletRequest request,RedirectAttributes redirectAttributes,MultipartRequest multiPartRquest){
		int result = 0;
		String msg = "失败";
		try{
		    String userAvatar = uploadImg(multiPartRquest);
		    if(StringUtils.isNoneBlank(userAvatar)){
		    	student.setUserAvatar(userAvatar);
		    }
		    result = studentInfoService.saveOrUpdateStudentInfo(student,Constant.ACCOUNT_DEFAULT_PASSWORD);
			msg = "成功";
		}catch(Exception e){
			log.error("StudentsController save is error :",e);
		}
		addMessage(redirectAttributes,"保存学生信息'" + student.getLoginName() + "'"+msg);
		return "redirect:/student/list?orgId="+student.getOrgId()+"&cId="+student.getcId()+"&isClass=1";
	}
	
	/**
	 * 启用学生
	 * @param student
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/enable")
	public String enable(String ids,StudentInfo student, RedirectAttributes redirectAttributes) {
		List<Long> id = Lists.newArrayList();
        //ids.add(student.getAccountId());
		if(StringUtils.isNotBlank(ids)){
			String idArray[] = ids.split(",");
			for(String s : idArray){
				id.add(Long.parseLong(s));
			}
		}
        studentInfoService.enableStudent(id);
        addMessage(redirectAttributes, "启用学生成功");
        return "redirect:/student/list?orgId="+student.getOrgId()+"&cId="+student.getcId()+"&isClass=1";
	}
	
	/**
	 * 禁用学生
	 * @param student
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/disable")
	public String disable(String ids,StudentInfo student, RedirectAttributes redirectAttributes) {
		List<Long> id = Lists.newArrayList();
        //ids.add(student.getAccountId());
		if(StringUtils.isNotBlank(ids)){
			String idArray[] = ids.split(",");
			for(String s : idArray){
				id.add(Long.parseLong(s));
			}
		}
        studentInfoService.disableStudent(id);
        addMessage(redirectAttributes, "禁用学生成功");
        return "redirect:/student/list?orgId="+student.getOrgId()+"&cId="+student.getcId()+"&isClass=1";
	}
	
	/**
	 * 重置学生账号密码
	 * @param student
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/resetPwd")
	public String resetPwd(String ids,StudentInfo student, RedirectAttributes redirectAttributes) {
		if(StringUtils.isNotBlank(ids)){
			String idArray[] = ids.split(",");
			for(String s : idArray){
				acountInfoService.resetPassword(Long.parseLong(s),Constant.ACCOUNT_DEFAULT_PASSWORD);
			}
		}
	/*	acountInfoService.resetPassword(student.getAccountId(),Constant.ACCOUNT_DEFAULT_PASSWORD);*/
        addMessage(redirectAttributes, "重置密码成功");
        return "redirect:/student/list?orgId="+student.getOrgId()+"&cId="+student.getcId()+"&isClass=1";
	}
	
	
	/**
	 * 调班
	 * @param
	 * @param model
	 * @return
	 */
	@RequestMapping("/moveClass")
	public String moveClass(String classId,StudentInfo studentInfo,ClassInfo classInfo, Model model, HttpServletRequest request){
		classInfo.setOrgId(studentInfo.getOrgId());
		model.addAttribute("orgId",studentInfo.getOrgId());
		model.addAttribute("studentId",studentInfo.getId());
		WebPage<ClassInfo> page = new WebPage<ClassInfo>(classInfoService.pageList(classInfo, new PageRequest(request)));
		for(int i = 0;i <page.getList().size();i++){
			if(page.getList().get(i).getId().toString().equals(classId)){
				page.getList().remove(i);
			}
		}
		model.addAttribute("page",page);
		return "modules/student/cls_list";
	}
	
	/**
	 * 变更学生班级
	 * @param orgId
	 * @param studentId
	 * @param clsId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/desideClass")
	public String desideClass(Long orgId,Long studentId,Long clsId,HttpServletRequest request,RedirectAttributes redirectAttributes){
		studentInfoService.changeClass(studentId,clsId);
		redirectAttributes.addAttribute("desideClassId", clsId);
		addMessage(redirectAttributes, "变更班级成功");
	    return "redirect:/student/list?orgId="+orgId+"&cId="+clsId+"&isClass=1";
	}
	
	@RequestMapping("/importExcel")
	public String importExcel(HttpServletRequest request,RedirectAttributes redirectAttributes,MultipartRequest multiPartRquest){
		int count = 0;
		try{
			Long cId = Long.valueOf(request.getParameter("cId"));
			Long orgId = Long.valueOf(request.getParameter("orgId"));
			MultipartFile excelFile = multiPartRquest.getFile("studentExcel");
			List<Map<String,Object>> list = ReadExcelUtils.readExcel(excelFile.getInputStream(), excelFile.getOriginalFilename());
		    for(Map<String,Object> map : list){
		    	StudentInfo stu = new StudentInfo();
		    	String name = (String) map.get("姓名");
		    	if(StringUtils.isBlank(name)){
		    		continue;
		    	}
		    	stu.setName(name);
		    	stu.setCreateTime(new Date());
		    	stu.setStatus("1");
		    	stu.setOrgId(orgId);
		    	stu.setcId(cId);
		        int row = studentInfoService.saveOrUpdateStudentInfo(stu,Constant.ACCOUNT_DEFAULT_PASSWORD);
		        if(row > 0){
		        	count++;
		        }
		    }
		}catch(Exception e){
			log.error("StudentsController importExcel is error :", e);
		}
		addMessage(redirectAttributes, "成功导入"+count+"个学生");
		return "redirect:/student/list";
	}
	
	
	private String uploadImg(MultipartRequest multiPartRquest) throws Exception{
		String relativePath = "";
		InputStream picStream = null;
		try{
			MultipartFile pic = multiPartRquest.getFile("userPic");
			if(pic == null || pic.getSize() <= 0){
				return relativePath;
			}
			picStream = pic.getInputStream();
			String strs[] = pic.getOriginalFilename().split("\\.");
			String suffixType = strs[strs.length - 1];
			String picName = UUID.randomUUID().toString().replaceAll("-", "")+"."+suffixType;
			File basePath = new File(this.getClass().getResource("/").getFile());
			String storePath = basePath.getParentFile().getParentFile().getPath() + File.separator + Constant.myResources.getString("uploadPicPath");
			File dir = new File(storePath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			relativePath =  Constant.myResources.getString("uploadPicPath") +File.separator+ picName;
			File picFile = new File(dir.getPath() +File.separator+ picName);
			FileUtils.copyInputStreamToFile(picStream, picFile);
		}finally{
			if(picStream != null){
				IOUtils.closeQuietly(picStream);
			}
		}
		return relativePath;
	}
	
	
	
}
