package com.winshare.edu.modules.classes.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.winshare.edu.modules.classes.entity.ClassInfo;
import com.winshare.edu.modules.system.entity.TreeInfo;

import tk.mybatis.mapper.common.Marker;

public interface ClassInfoMapper extends Marker{

	/**
	 * 分页查询班级
	 * @param classInfo
	 * @return
	 */
	List<ClassInfo> pageList(ClassInfo classInfo);

	/**
	 * 根据id获取班级信息
	 * @param classId
	 * @return
	 */
	ClassInfo getByClassId(Long classId);

	void insert(ClassInfo classInfo);
	
	void update(ClassInfo classInfo);

	void changeStatus(ClassInfo classInfo);

	/**
	 * 机构下所有班级
	 * @param orgId
	 * @return
	 */
	List<ClassInfo> orgClasses(Long orgId);
	
	List<ClassInfo> orgClassesEscapeUid(@Param("orgId")Long orgId,@Param("uid")Long uid);
	
	List<TreeInfo> getByOrgId();
	
	List<ClassInfo> getOtherClasses(ClassInfo classInfo);
	
	int deleteClass(Long ids);
	
}
