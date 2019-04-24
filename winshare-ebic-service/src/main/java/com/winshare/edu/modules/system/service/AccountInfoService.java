package com.winshare.edu.modules.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.winshare.edu.common.exceptions.BusinessException;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.security.PasswordHelper;
import com.winshare.edu.modules.system.entity.AccountInfo;
import com.winshare.edu.modules.system.entity.AreaInfo;
import com.winshare.edu.modules.system.entity.SysMenu;
import com.winshare.edu.modules.system.entity.SysOperation;
import com.winshare.edu.modules.system.entity.SysRole;
import com.winshare.edu.modules.system.mapper.AccountInfoMapper;
import com.winshare.edu.modules.system.mapper.AreaInfoMapper;
import com.winshare.edu.modules.system.mapper.SysMenuMapper;
import com.winshare.edu.modules.system.mapper.SysOperationMapper;
import com.winshare.edu.modules.system.mapper.SysRoleMapper;
import com.winshare.edu.modules.teachersMgm.entity.Teacher;
import com.winshare.edu.modules.teachersMgm.mapper.TeacherMgmMapper;
import com.winshare.edu.modules.user.entity.StudentInfo;
import com.winshare.edu.modules.user.mapper.StudentInfoMapper;

@Service
public class AccountInfoService {

	@Autowired
	private AccountInfoMapper accountInfoMapper;
	
	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	@Autowired
	private AreaInfoMapper areaInfoMapper;
	
	@Autowired
	private SysOperationMapper sysOperationMapper;
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private StudentInfoMapper studentInfoMapper;
	
	@Autowired
	private TeacherMgmMapper teacherMgmMapper;
	
	public AccountInfo findByLoginName(String loginName){
		if(StringUtils.isBlank(loginName)){
			throw new BusinessException("Parameter error: loginName must not be null!");
		}
		return accountInfoMapper.findByLoginName(loginName);
	}

	public List<SysMenu> findMenuByAccountId(Long accountId){
		return sysMenuMapper.findMenuByAccountId(accountId);
	}
	
	public Set<String> findRoleAuthByAccountId(Long accountId){
		List<SysRole> roleList = sysRoleMapper.findRoleListByAccountId(accountId);
		Set<String> roles = new HashSet<String>();
		for(SysRole role : roleList){
			roles.add(role.getRoleIden());
		}
		return roles;
	}
	
	public Set<String> findPermissionsByAccountId(Long accountId){
		List<SysOperation> operList = sysOperationMapper.findOperationByAccountId(accountId);
		Set<String> permissions = new HashSet<String>();
		for(SysOperation oper : operList){
			permissions.add(oper.getOperationIden());
		}
		return permissions;
	}
	
	public AccountInfo get(Long id){
		return accountInfoMapper.get(id);
	}
	
	public AccountInfo get(AccountInfo accountInfo){
		return accountInfoMapper.getByExample(accountInfo);
	}
	
	public List<AccountInfo> find(PageRequest pageRequest, AccountInfo accountInfo){
		PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getOrderBy());
		return accountInfoMapper.findList(accountInfo);
	}
	
	public AccountInfo save(AccountInfo accountInfo){
		if(accountInfo.getId() == null){
			accountInfo.setAccountStatus("1");
			accountInfo.setLoginCount(0);
			accountInfoMapper.save(accountInfo);
		}else{
			accountInfoMapper.update(accountInfo);
		}
		return accountInfo;	
	}
	
	public int delete(Long id){
		StudentInfo stu = new StudentInfo();
		stu.setAccountId(id);
		List<StudentInfo> stuList = studentInfoMapper.findList(stu);
		if(stuList.size() > 0){//查询是否是学生或者老师
			stu = stuList.get(0);
			studentInfoMapper.deleteClsRStu(stu.getId());
			studentInfoMapper.deleteStuById(stu.getId());
		}else{
			Teacher teacher = new Teacher();
			teacher.setAccountId(id);
			List<Teacher> teaList = teacherMgmMapper.findList(teacher);
			if(teaList.size() > 0){
				Teacher tea = teaList.get(0);
				teacherMgmMapper.deleteTchRClassInfo(tea.getUserId());
				teacherMgmMapper.deleteTchRSubject(tea.getUserId());
				teacherMgmMapper.deleteTeaById(tea.getUserId());
			}

		}
		accountInfoMapper.deleteRUserByAccountId(id);
		return accountInfoMapper.delete(id);
	}
	
	public int resetPassword(Long id,String defaultPassword){
		return accountInfoMapper.resetPassword(id, defaultPassword);
	}
	
	public int updateStatus(Long id, String status){
		return accountInfoMapper.updateStatus(id, status);
	}
	
	public int increaseLoginCount(Long id){
		return accountInfoMapper.increaseLoginCount(id);
	}
	
	public List<AreaInfo> findAreaListByParentId(AreaInfo areaInfo){
		return areaInfoMapper.findListByParentId(areaInfo);
	}
	
	public AccountInfo getAccountExists(AccountInfo accountInfo){
		   return accountInfoMapper.getAccountExists(accountInfo);
	}
	
	public AccountInfo saveAccount(AccountInfo accountInfo){
		if(accountInfo.getId() == null){
			accountInfo.setAccountStatus("1");
			accountInfo.setLoginCount(0);
			accountInfo.setAccount(accountInfo.getLoginName());
			accountInfoMapper.save(accountInfo);
			String password = new PasswordHelper().encryptPassword(accountInfo.getPassword(), accountInfo.getLoginName());
			accountInfo.setPassword(password);
			accountInfoMapper.save(accountInfo);
		}else{
			accountInfoMapper.update(accountInfo);
		}
		Long roleId = Long.valueOf(accountInfo.getAccountType());
		Map<String,Object> params = new HashMap<String,Object>();
		List<Long> roleIdList = new ArrayList<Long>();
		roleIdList.add(roleId);
		params.put("roleIdList",roleIdList);
		params.put("accountId", accountInfo.getId());
		sysRoleMapper.delRelationByAccountId(accountInfo.getId());
		sysRoleMapper.saveUserRoleRelation(params);
		return accountInfo;	
	}
	
	public int updateLoginInfo(AccountInfo accountInfo){
		int row = accountInfoMapper.updateLoginInfo(accountInfo);
		return row;
	}
	

	public int updateStatusByStuId(List<Long> stuIdList,String status){
		int row = accountInfoMapper.updateStatusByStuId(stuIdList, status);
		return row;
	}
}
