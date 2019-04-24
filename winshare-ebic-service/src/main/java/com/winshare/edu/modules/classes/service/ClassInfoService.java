package com.winshare.edu.modules.classes.service;

import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.modules.classes.entity.ClassInfo;
import com.winshare.edu.modules.classes.mapper.ClassInfoMapper;
import com.winshare.edu.modules.system.entity.TreeInfo;
import com.winshare.edu.modules.system.mapper.AccountInfoMapper;

@Service
public class ClassInfoService {

	@Autowired
	private ClassInfoMapper classInfoMapper;
	
	@Autowired
	private AccountInfoMapper accountInfoMapper;
	
	/**
	 * 班级信息分页查询
	 * @param classInfo
	 * @param pageRequest
	 * @return
	 */
	public List<ClassInfo> pageList(ClassInfo classInfo, PageRequest pageRequest){
		PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getOrderBy());		
		return classInfoMapper.pageList(classInfo);
	}
	
	/**
	 * 根据班级id获取班级信息
	 * @param classId
	 * @return
	 */
	public ClassInfo getByClassId(Long classId){
		return classInfoMapper.getByClassId(classId);
	}
	
	/**
	 * 保存班级信息
	 * @param classInfo
	 */
	public void save(ClassInfo classInfo){
		if(classInfo.getId() == null){
			classInfoMapper.insert(classInfo);
		}else{
			classInfoMapper.update(classInfo);
		}
	}
	
	/**
	 * 查询机构所有班级
	 * @return
	 */
	public List<ClassInfo> orgClasses(Long orgId){
		return classInfoMapper.orgClasses(orgId);
	}
	
	/**
	 * 查询机构下，除教师关联的班级之外的所有班级
	 * @return
	 */
	public List<ClassInfo> orgClassesEscapeUid(Long orgId,Long uid){
		return classInfoMapper.orgClassesEscapeUid(orgId,uid);
	}
	
	public void changeStatus(ClassInfo classInfo){
		classInfoMapper.changeStatus(classInfo);
		accountInfoMapper.updateStatusByClassId(classInfo.getId(),classInfo.getClassStatus());

	}
	
	
	public List<TreeInfo> getByOrgId(){
		return classInfoMapper.getByOrgId();
	}
	
	/**
	 * 查询机构下除当前班的所有班级
	 * @return
	 */
	public List<ClassInfo> getOtherClasses(ClassInfo classInfo){
		return classInfoMapper.getOtherClasses(classInfo);
	}
	
	public int deleteClass(List<Long> idList){
		int count = 0;
		for(Long id : idList){
			 int row = classInfoMapper.deleteClass(id);
			 if(row > 0){
				 count = count + row;
			 }
		}
	   return count;
	}
}
