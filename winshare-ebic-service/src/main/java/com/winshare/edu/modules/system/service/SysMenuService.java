package com.winshare.edu.modules.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.winshare.edu.modules.system.entity.SysMenu;
import com.winshare.edu.modules.system.entity.TreeInfo;
import com.winshare.edu.modules.system.mapper.SysMenuMapper;

@Service("sysMenuService")
public class SysMenuService {

	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	public List<SysMenu> findListByAccountId(Long accountId){
		List<SysMenu> list = null;
		list = sysMenuMapper.findMenuByAccountId(accountId);
		return list;
	}
	
	public List<Map<String, Object>> findMenuTree(){
		return sysMenuMapper.findMenuTree();
	}
}
