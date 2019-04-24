package com.winshare.edu.modules.system.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;

import tk.mybatis.mapper.common.Marker;

import com.winshare.edu.modules.system.entity.SysMenu;
import com.winshare.edu.modules.system.entity.TreeInfo;

public interface SysMenuMapper extends Marker{

	List<SysMenu> findMenuByAccountId(Long accountId);
	
	List<Map<String, Object>> findMenuTree();
}
