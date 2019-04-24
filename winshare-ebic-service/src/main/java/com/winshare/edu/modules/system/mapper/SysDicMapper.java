package com.winshare.edu.modules.system.mapper;

import java.util.List;
import java.util.Map;

import com.winshare.edu.common.entity.SysDicVo;
import com.winshare.edu.modules.system.entity.SysDic;

import tk.mybatis.mapper.common.Marker;

public interface SysDicMapper extends Marker{
	
	SysDic get(Long id);
	
	SysDic getByExample(SysDic sysDic);

	List<SysDic> findAll();
	
	List<SysDic> findList(SysDic sysDic);

	List<SysDic> findLists(SysDic sysDic);
	
	int save(SysDic sysDic);
	
	int update(SysDic sysDic);
	
	int delete(Long id);

	List<SysDicVo> findTypeByCode(Map<String,Object> map);
}
