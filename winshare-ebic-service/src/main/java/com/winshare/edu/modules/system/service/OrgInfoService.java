package com.winshare.edu.modules.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.modules.system.mapper.OrgInfoMapper;
import com.winshare.edu.modules.system.entity.OrgInfo;
import com.winshare.edu.modules.system.entity.TreeInfo;

@Service("orgInfoService")
public class OrgInfoService {

	@Autowired
	private OrgInfoMapper orgInfoMapper;
	
	public OrgInfo get(Long id){
		return orgInfoMapper.getById(id);
	}
	
	public OrgInfo get(OrgInfo orgInfo){
		return orgInfoMapper.getByExample(orgInfo);
	}
	
	public List<OrgInfo> find(OrgInfo orgInfo){
		return orgInfoMapper.findList(orgInfo);
	}

	public List<TreeInfo> findTreeInfo(TreeInfo treeInfo){
		return orgInfoMapper.findTreeInfo(treeInfo);
	}
	
	public List<OrgInfo> find(PageRequest pageRequest, OrgInfo orgInfo){
		PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getOrderBy());
		return orgInfoMapper.findList(orgInfo);
	}
	
	public OrgInfo save(OrgInfo orgInfo){		
		if(orgInfo.getId() == null){
			orgInfo.setOrgStatus("1");
			if(orgInfo.getParent().getId() == null){
				orgInfo.getParent().setId(0l);
				orgInfo.setParentIds("0,");
			}else{
				OrgInfo parent = orgInfoMapper.getById(orgInfo.getParent().getId());
				orgInfo.setParentIds(parent.getParentIds()+parent.getId()+",");
			}
			try{
				orgInfoMapper.save(orgInfo);
			}catch (Exception e){
				e.printStackTrace();
			}
		}else{
			try{
				orgInfoMapper.update(orgInfo);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return orgInfo;
	}
	
	
	public int delete(Long id){
		return orgInfoMapper.delete(id);
	}
	
	public boolean updateStatus(Map<String,Object> params){
		boolean result = false;
		int row = orgInfoMapper.updateStatus(params);
		if(row > 0){
			result = true;
		}
		return result;
	}
	public List<OrgInfo> findChildren(PageRequest pageRequest, OrgInfo orgInfo){
		PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getOrderBy());
		return orgInfoMapper.findListChildren(orgInfo);
	}
}
