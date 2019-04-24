package com.winshare.edu.modules.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.modules.system.entity.TeacherVersion;
import com.winshare.edu.modules.system.mapper.TeacherVersionMapper;

@Service("teacherVersionService")
public class TeacherVersionService {

	@Autowired
	private TeacherVersionMapper teacherVersionMapper;
	
	
    public int add(TeacherVersion tv){
    	int result = 0;
    	result = teacherVersionMapper.add(tv);
    	return result;
    }
	
	public int del(List<Long> ids){
		int result = 0;
    	result = teacherVersionMapper.del(ids);
    	return result;
	}
	
	public int update(TeacherVersion tv){
		int result = 0;
    	result = teacherVersionMapper.update(tv);
    	return result;
	}
	
	public List<TeacherVersion> findList(PageRequest pageRequest,TeacherVersion tv){
		PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getOrderBy());
		List<TeacherVersion> list = null;
		list = teacherVersionMapper.findList(tv);
		return list;
	}
	
	public TeacherVersion get(Long id){
		TeacherVersion tv = teacherVersionMapper.get(id);
		return tv;
	}
}
