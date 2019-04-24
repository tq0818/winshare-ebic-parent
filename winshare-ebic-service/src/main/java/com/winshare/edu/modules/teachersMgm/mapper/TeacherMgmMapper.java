package com.winshare.edu.modules.teachersMgm.mapper;

import java.util.List;

import com.winshare.edu.modules.classes.entity.ClassGroup;
import org.apache.ibatis.annotations.Param;

import com.winshare.edu.common.entity.StudentVo;
import com.winshare.edu.common.entity.SubjectVo;
import com.winshare.edu.modules.classes.entity.ClassInfo;
import com.winshare.edu.modules.teachersMgm.entity.Teacher;
import com.winshare.edu.common.entity.ClassVo;

import tk.mybatis.mapper.common.Marker;

/** 
  * @author  hujie 
  * @date 创建时间：2017年9月18日 下午3:36:44  
  * @parameter  
  * @since  
  * @return  
*/
public interface TeacherMgmMapper extends Marker{

	List<Teacher> findList(Teacher teacher);
	
	int saveTeacherInfo(Teacher teacher);
	
	int updateTeacherInfo(Teacher teacher);
	
	int saveTchRClassInfo(@Param("entity")List clsList);
	
	int deleteTchRClassInfo(@Param("userId")Long userId);
	
	int deleteTchRSubject(@Param("userId")Long userId);
	
	int saveTchRSubject(@Param("entity")List subList);
	
	Teacher getById(@Param("userId")Long userId);
	
	List<ClassInfo> selectClassByUid(@Param("userId")Long userId);
	
	List<Teacher> selectSubjectsByUid(@Param("userId")Long userId);
	
	int enableTeacher(Long ids);
	
	int disableTeacher(Long ids);
	
	List<SubjectVo> findSubjectVoByTeacherId(@Param("id")Long teacherId);
	
	List<ClassVo> findClassVoByTeacherId(@Param("id")Long teacherId);
	
	List<StudentVo> findStudentVoByClassId(ClassGroup classGroup);

	List<StudentVo> getTempList(ClassGroup classGroup);
	
	int deleteTeaById(Long teacherId);
	
	
}
