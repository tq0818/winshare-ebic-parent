package com.winshare.edu.modules.system.mapper;

import java.util.List;

import com.winshare.edu.modules.system.entity.AccountInfo;
import com.winshare.edu.modules.system.entity.TeacherVersion;

import tk.mybatis.mapper.common.Marker;

public interface TeacherVersionMapper extends Marker{

	public int add(TeacherVersion tv);
	
	public int del(List<Long> ids);
	
	public int update(TeacherVersion tv);
	
	public List<TeacherVersion> findList(TeacherVersion tv);
	
	public TeacherVersion get(Long id);
}
