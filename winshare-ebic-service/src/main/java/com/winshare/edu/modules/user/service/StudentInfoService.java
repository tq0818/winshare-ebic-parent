package com.winshare.edu.modules.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.security.PasswordHelper;
import com.winshare.edu.common.utils.DateUtils;
import com.winshare.edu.modules.system.entity.AccountInfo;
import com.winshare.edu.modules.system.entity.OrgInfo;
import com.winshare.edu.modules.system.service.AccountInfoService;
import com.winshare.edu.modules.system.service.OrgInfoService;
import com.winshare.edu.modules.user.entity.StudentInfo;
import com.winshare.edu.modules.user.mapper.StudentInfoMapper;

@Service("studentInfoService")
public class StudentInfoService {

	@Autowired
	StudentInfoMapper studentInfoMapper;
	
	@Autowired
	private AccountInfoService accountInfoService;
	
	@Autowired
	private OrgInfoService orgInfoService;
	/**
	 * 根据班级id查询班级下的学生
	 * @param classId
	 * @return
	 */
	public List<StudentInfo> listByClassId(Long classId){
		return studentInfoMapper.listByClassId(classId);
	}
	
	public StudentInfo get(Long id){
		return studentInfoMapper.getById(id);
	};
	
	public List<StudentInfo> find(PageRequest pageRequest,StudentInfo student){
		PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getOrderBy());
		return studentInfoMapper.findList(student);
	}
	
	public int saveOrUpdateStudentInfo(StudentInfo student,String defaultPassword){
		int num ;
		AccountInfo account = new AccountInfo();
		OrgInfo org = null;
		Long orgId = student.getOrgId();
		org = orgInfoService.get(orgId);
		account.setOrgInfo(org);
		account.setLoginName(student.getLoginName());
		account.setName(student.getName());
		account.setSex(student.getSex());
		if(student.getBirthDate() != null)
			account.setBirthDate(DateUtils.format(student.getBirthDate(), "yyyy-MM-dd"));
		account.setId(student.getAccountId());
		account.setAreaId(student.getAreaId());
	    account.setAccount("s");
		AccountInfo accountInfo = accountInfoService.save(account);
		accountInfo = accountInfoService.get(accountInfo.getId());
		String password = new PasswordHelper().encryptPassword(defaultPassword, accountInfo.getAccount());
		accountInfo.setPassword(password);
		accountInfoService.save(accountInfo);
		//student.setAreaCode(orgInfo.getAreaCode());
		student.setAccountId(accountInfo.getId());
		if(null == student.getId()){//新增
			num = studentInfoMapper.saveStudentInfo(student);
			//student.setId((long)num);
			studentInfoMapper.insertClsRStu(student);//新增学生班级关联
		}else{//修改
			num = studentInfoMapper.updateStudentInfo(student);
			studentInfoMapper.deleteClsRStu(student.getId());//删除学生班级关联
			studentInfoMapper.insertClsRStu(student);//新增学生班级关联
		}
		return num ;
	}
	
	/**
	 * 启用学生账号
	 * @param ids
	 * @return
	 */
	public int enableStudent(List<Long> ids){
		int row = 0;
		if(ids.size() > 0){
			accountInfoService.updateStatusByStuId(ids, "1");
			row = studentInfoMapper.enableStudent(ids);
		}
		return row;
	}
	
	/**
	 * 禁用学生账号
	 * @param ids
	 * @return
	 */
	public int disableStudent(List<Long> ids){
		int row = 0;
		if(ids.size() > 0){
			accountInfoService.updateStatusByStuId(ids, "0");
			row = studentInfoMapper.disableStudent(ids);
		}
		return row;
	
	}
	
	/**
	 * 变换班级
	 * @param studentId
	 * @param clsId
	 * @return
	 */
	public boolean changeClass(Long studentId,Long clsId){
		int del = 0,insert = 0;
		try {
			StudentInfo stu = new StudentInfo();
			stu.setcId(clsId);
			stu.setId(studentId);
			del = studentInfoMapper.deleteClsRStu(studentId);//删除学生班级关联
			insert = studentInfoMapper.insertClsRStu(stu);//新增学生班级关联
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (del > 0 && insert > 0);
	}
	
	
	
}
