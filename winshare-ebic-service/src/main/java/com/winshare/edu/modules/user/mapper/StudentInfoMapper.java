package com.winshare.edu.modules.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.winshare.edu.modules.teachersMgm.entity.Teacher;
import com.winshare.edu.modules.user.entity.StudentInfo;

import tk.mybatis.mapper.common.Marker;

public interface StudentInfoMapper extends Marker {

	/**
	 * 根据班级id查询班级下所有学生
	 * @param classId
	 * @return
	 */
	List<StudentInfo> listByClassId(Long classId);
	
	/**
	 * 根据学生id获取学生信息
	 * @param id
	 * @return
	 */
	StudentInfo getById(@Param("id")Long id);
	
	
	/**
	 * 获取学生列表数据信息
	 * @param student
	 * @return
	 */
	List<StudentInfo> findList(StudentInfo student);
	
	
	/**
	 * 保存学生信息
	 * @param student
	 * @return
	 */
	int saveStudentInfo(StudentInfo student);
	
	
	/**
	 * 修改学生信息
	 * @param student
	 * @return
	 */
	int updateStudentInfo(StudentInfo student);
	
	/**
	 * 删除班级学生关联信息
	 * @param id
	 * @return
	 */
	int deleteClsRStu(@Param("id")Long id);
	
	/**
	 * 新增学生班级关联信息
	 * @param student
	 * @return
	 */
	int insertClsRStu(StudentInfo student);
	
	/**
	 * 启用学生账号
	 * @param ids
	 * @return
	 */
	int enableStudent(@Param("ids")List<Long> ids);
	
	/**
	 * 禁用学生账号
	 * @param ids
	 * @return
	 */
	int disableStudent(@Param("ids")List<Long> ids);
	
	int deleteStuById(Long id);
}
