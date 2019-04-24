package com.winshare.edu.modules.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.utils.CacheUtils;
import com.winshare.edu.modules.test.entity.TestDto;
import com.winshare.edu.modules.test.mapper.TestMapper;

@Service
public class TestService {

	@Autowired
	private TestMapper testMapper;
	
	public List<TestDto> Test(PageRequest pageRequest){
		PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getOrderBy());		
		return testMapper.getlist(new TestDto());
	}
	
}
