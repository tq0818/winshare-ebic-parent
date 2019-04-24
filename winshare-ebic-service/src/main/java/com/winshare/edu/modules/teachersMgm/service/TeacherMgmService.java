package com.winshare.edu.modules.teachersMgm.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winshare.edu.modules.classes.entity.ClassGroup;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.winshare.edu.common.entity.ClassVo;
import com.winshare.edu.common.entity.StudentVo;
import com.winshare.edu.common.entity.SubjectVo;
import com.winshare.edu.common.entity.TeacherVo;
import com.winshare.edu.common.exceptions.BusinessException;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.security.PasswordHelper;
import com.winshare.edu.common.utils.DateUtils;
import com.winshare.edu.modules.classes.entity.ClassInfo;
import com.winshare.edu.modules.system.entity.AccountInfo;
import com.winshare.edu.modules.system.entity.OrgInfo;
import com.winshare.edu.modules.system.service.AccountInfoService;
import com.winshare.edu.modules.system.service.OrgInfoService;
import com.winshare.edu.modules.teachersMgm.entity.Teacher;
import com.winshare.edu.modules.teachersMgm.mapper.TeacherMgmMapper;

/** 
  * @author  hujie 
  * @date 创建时间：2017年9月18日 下午3:32:08  
  * @parameter  
  * @since  
  * @return  
*/

@Service
public class TeacherMgmService {
	@Autowired
	private TeacherMgmMapper teacherMgmMapper;
	
	@Autowired
	private AccountInfoService accountInfoService;
	
	@Autowired
	private OrgInfoService orgInfoService;
	
		
	public List<Teacher> find(PageRequest pageRequest,Teacher teacher){
		PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getOrderBy());
		return teacherMgmMapper.findList(teacher);
	}
	
	public Long saveOrUpdateTeacherInfo(Teacher teacher,String choiceClass,String choiceSubject) throws Exception{
		    Long num = null;
			AccountInfo account = new AccountInfo();
			OrgInfo org = new OrgInfo();
			Long orgId = teacher.getOrgId();
			org = orgInfoService.get(orgId);
			account.setOrgInfo(org);
			account.setLoginName(teacher.getLoginName());
			account.setName(teacher.getName());
			account.setSex(teacher.getSex());
			if(teacher.getBirthDate() != null){
				account.setBirthDate(DateUtils.format(teacher.getBirthDate(), "yyyy-MM-dd"));
			}
			account.setId(teacher.getAccountId());
			account.setAccount("t");
			AccountInfo accountInfo = accountInfoService.save(account);
			accountInfo = accountInfoService.get(accountInfo.getId());
			String password = new PasswordHelper().encryptPassword(teacher.getPassword(), accountInfo.getAccount());
			accountInfo.setPassword(password);
			accountInfoService.save(accountInfo);
			teacher.setAreaCode(org.getAreaCode());
			teacher.setAccountId(accountInfo.getId());
			if(!StringUtils.isBlank(teacher.getBirDate()))
				teacher.setBirthDate(DateUtils.parse(teacher.getBirDate(), "yyyy-MM-dd"));
			if(null != teacher.getUserId()){//修改账户信息
				teacherMgmMapper.updateTeacherInfo(teacher);
				num = teacher.getUserId();
			}else{//新增账户信息
				teacherMgmMapper.saveTeacherInfo(teacher);
				num = teacher.getId();
				if(num == null || num <=0){
					throw new Exception("save teacher is error num : "+num);
				}
			}
			
			if(!StringUtils.isBlank(choiceClass)){
				if(null != teacher.getUserId()){//删除班级教师关联信息
					this.deleteTchRClassInfo(teacher.getUserId());
				}
				//JSONArray jsonData = JSONArray.fromObject(choiceClass);
				JSONArray jsonData = JSONArray.parseArray(choiceClass);
				JSONObject jsonOne;
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				for(int i=0;i<jsonData.size();i++){
					  Map<String,Object> map = new HashMap<String,Object>();
			          jsonOne = jsonData.getJSONObject(i); 
			          map.put("id", jsonOne.get("id"));
			          map.put("userId", num);
			          list.add(map);
				}
				if(!list.isEmpty())
					//新增教师班级关联信息
					this.saveTchRClassInfo(list);
			}
			
			//新增教师学科关联信息
			if(!StringUtils.isBlank(choiceSubject)){
				if(null != teacher.getUserId()){//删除班级教师关联信息
					this.deleteTchRSubject(teacher.getUserId());
				}
				//JSONArray jsonData = JSONArray.fromObject(choiceSubject);
				JSONArray jsonData = JSONArray.parseArray(choiceSubject);
				JSONObject jsonOne;
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				for(int i=0;i<jsonData.size();i++){
					  Map<String,Object> map = new HashMap<String,Object>();
			          jsonOne = jsonData.getJSONObject(i); 
			          map.put("text", jsonOne.get("id"));
			          map.put("userId", num);
			          list.add(map);
				}
				if(!list.isEmpty())
					this.saveTchRSubject(list);
			}
		
		return num;
	}
	
	public int saveTchRClassInfo(List clsList){
		return teacherMgmMapper.saveTchRClassInfo(clsList);
	}
	
	public int deleteTchRClassInfo(Long userId){
		return teacherMgmMapper.deleteTchRClassInfo(userId);
	}
	
	
	public int deleteTchRSubject(Long userId){
		return teacherMgmMapper.deleteTchRSubject(userId);
	}
	
	public int saveTchRSubject(List subList){
		return teacherMgmMapper.saveTchRSubject(subList);
	}
	
	public Teacher get(Long userId){
		return teacherMgmMapper.getById(userId);
	};
	
	
	public List<ClassInfo> selectClassByUid(Long userId){
		return teacherMgmMapper.selectClassByUid(userId);
	}
	
	public List<Teacher> selectSubjectsByUid(Long userId){
		return teacherMgmMapper.selectSubjectsByUid(userId);
	}
	
	/**
	 * 启用教师
	 * @param ids
	 * @return
	 */
	public int enableTeacher(Long ids){
		return teacherMgmMapper.enableTeacher(ids);
	}
	
	/**
	 * 禁用教师
	 * @param ids
	 * @return
	 */
	public int disableTeacher(Long ids){
		return teacherMgmMapper.disableTeacher(ids);
	}
	
	public AccountInfo login(String account,String password){
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setKeyword(account);
		AccountInfo result = accountInfoService.get(accountInfo);
		if(result != null && StringUtils.isNotBlank(password)){
			String md5Pwd = "";
			String accountName = "";
			if(StringUtils.isNotBlank(result.getAccount())){
				accountName = result.getAccount();
			}
			md5Pwd = new PasswordHelper().encryptPassword(password, accountName);
			if(!StringUtils.equals(md5Pwd, result.getPassword())){
				result = null;
			}
		}else{	
			result = null;
		}
		return result;
	}
	
	public List<SubjectVo> findSubjectVoByTeacherId(Long teacherId){
		  return teacherMgmMapper.findSubjectVoByTeacherId(teacherId);
	}
	
	public List<ClassVo> findClassVoByTeacherId(Long teacherId){
		  return teacherMgmMapper.findClassVoByTeacherId(teacherId);
	}
	
	public List<StudentVo> findStudentVoByClassId(Long classId,Long teacherId,String subject){
		ClassGroup classGroup = new ClassGroup();
		if(null != classId && !"".equals(String.valueOf(classId))){
			classGroup.setClassId(Integer.parseInt(classId.toString()));
		}
		if(null != teacherId && !"".equals(String.valueOf(teacherId))){
			classGroup.setTeacherId(Integer.parseInt(teacherId.toString()));
		}
		if(null != subject && !"".equals(subject)){
			classGroup.setSubject(subject);
		}
		return teacherMgmMapper.findStudentVoByClassId(classGroup);
	}

	public List<StudentVo> getTempList(Long classId){
		ClassGroup classGroup = new ClassGroup();
		if(null != classId && !"".equals(String.valueOf(classId))){
			classGroup.setClassId(Integer.parseInt(classId.toString()));
		}
		return teacherMgmMapper.getTempList(classGroup);
	}
	
	public TeacherVo teacherLogin(String accountName,String password,HttpServletRequest request) throws Exception{
		AccountInfo account = login(accountName,password);
		if(account != null){
			if("0".equals(account.getAccountStatus())){
				throw  new BusinessException("0","账号已禁用，请与管理员联系！");
			}else{
			Teacher search  =new Teacher();
			search.setAccountId(account.getId());
			Teacher teacher = find(new PageRequest(request),search).get(0);
			List<SubjectVo> subjectList = findSubjectVoByTeacherId(Long.parseLong(String.valueOf(account.getTeacherId())));
			List<ClassVo> classList = findClassVoByTeacherId(Long.parseLong(String.valueOf(account.getTeacherId())));
			TeacherVo teacherVo = new TeacherVo();
			teacherVo.setSubjectList(subjectList);
			teacherVo.setClassList(classList);
			teacherVo.setTeacherId(account.getTeacherId());
			teacherVo.setTeacherName(account.getTeacherName());
			teacherVo.setOrgId(Long.parseLong(String.valueOf(account.getOrgId())));
			teacherVo.setSex(account.getSex());
			teacherVo.setOrgName(account.getOrgName());
			return teacherVo;
			}
		}else{
			throw  new BusinessException("0","帐号或密码错误");
		}
	}
	
	
}
