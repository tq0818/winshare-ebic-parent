package com.winshare.edu.modules.test.mapper;

import java.util.List;

import com.winshare.edu.modules.test.entity.TestDto;

import tk.mybatis.mapper.common.Marker;


public interface TestMapper extends Marker{

	List<TestDto> getlist(TestDto dto);
}
