package com.winshare.edu.modules.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.winshare.edu.common.entity.SysDicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.modules.system.entity.SysDic;
import com.winshare.edu.modules.system.mapper.SysDicMapper;
import com.winshare.edu.modules.system.utils.SysDicUtils;

@Service
public class SysDicService {
	
	private static final String DIC_CACHE = "dicCache";

	@Autowired
	private SysDicMapper sysDicMapper;
	
	@PostConstruct
	public void loadDicToCache(){
		SysDicUtils.removeAll();
		List<SysDic> dicList = sysDicMapper.findAll();
		for(SysDic dic : dicList){
			SysDicUtils.put(dic.getDicCode(), dic);
		}
	}
	
	public SysDic get(Long id){
		return sysDicMapper.get(id);
	}
	
	public SysDic get(SysDic sysDic){
		return sysDicMapper.getByExample(sysDic);
	}
	
	public List<SysDic> find(SysDic sysDic){
		return sysDicMapper.findList(sysDic);
	}

	public List<SysDic> finds(SysDic sysDic){
		return sysDicMapper.findLists(sysDic);
	}
	
	public List<SysDic> find(PageRequest pageRequest, SysDic sysDic){
		PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getOrderBy());
		return sysDicMapper.findList(sysDic);
	}
	
	public SysDic save(SysDic sysDic){
		if(sysDic.getId() == null){
			if(sysDic.getParent().getId() == null){
				sysDic.getParent().setId(0l);
				sysDic.setParentIds("0,");
			}
			sysDicMapper.save(sysDic);
		}else{
			sysDicMapper.update(sysDic);
		}
		return sysDic;
	}
	
	public int delete(Long id){
		return sysDicMapper.delete(id);
	}

	public List<SysDicVo> findTypeByCode(String typeCode){
		Map<String,Object> map = new HashMap<>();
		map.put("dicCode",typeCode+"_");
		return sysDicMapper.findTypeByCode(map);
	}

}
 