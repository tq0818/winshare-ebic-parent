package com.winshare.edu.modules.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.modules.system.entity.AreaInfo;
import com.winshare.edu.modules.system.entity.TreeInfo;
import com.winshare.edu.modules.system.mapper.AreaInfoMapper;

@Service("areaInfoService")
public class AreaInfoService {

	@Autowired
	private AreaInfoMapper areaInfoMapper;
	
	public AreaInfo get(Long id){
		return areaInfoMapper.getById(id);
	}
	
	public int save(AreaInfo AreaInfo){
		int row = 0;
		if(AreaInfo.getId() == null){
		   row = areaInfoMapper.save(AreaInfo);			
		}else{
		   row = areaInfoMapper.update(AreaInfo);
		}
		return row;
	}
	
	
	public int delete(Long id){
		return areaInfoMapper.delete(id);
	}
	
	public List<TreeInfo> findTreeInfo(TreeInfo treeInfo){
		return areaInfoMapper.findTreeInfo(treeInfo);
	}
	
	public List<AreaInfo> find(PageRequest pageRequest, AreaInfo areaInfo){
		PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getOrderBy());
		return areaInfoMapper.findList(areaInfo);
	}
	
	public List<AreaInfo> find(AreaInfo areaInfo){
		return areaInfoMapper.findList(areaInfo);
	}
	
	public List<AreaInfo> findListByParentId(PageRequest pageRequest,AreaInfo areaInfo){
		PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getOrderBy());
		return areaInfoMapper.findListByParentId(areaInfo);
	}
	
	public int countByCode(AreaInfo areaInfo){
		return areaInfoMapper.countByCode(areaInfo);
	}
	
	public List<AreaInfo> findListByChildrenName(PageRequest pageRequest,AreaInfo areaInfo){
		PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getOrderBy());
		return areaInfoMapper.findListByChildrenName(areaInfo);
	}
}
