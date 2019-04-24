package com.winshare.edu.modules.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.winshare.edu.modules.system.entity.AccountInfo;

import tk.mybatis.mapper.common.Marker;

public interface AccountInfoMapper extends Marker{

	AccountInfo findByLoginName(String loginName);
	
	AccountInfo get(Long id);
	
	AccountInfo getByExample(AccountInfo accountInfo);
	
	List<AccountInfo> findList(AccountInfo accountInfo);
	
	int save(AccountInfo accountInfo);
	
	int update(AccountInfo accountInfo);
	
	int delete(Long id);
	
	int resetPassword(@Param("id") Long id, @Param("defaultPassword") String defaultPassword);
	
	int updateStatus(@Param("id") Long id, @Param("status") String status);
	
	int increaseLoginCount(Long id);
	
	AccountInfo getAccountExists(AccountInfo accountInfo);
	
	int updateLoginInfo(AccountInfo accountInfo);
	
	int batchResetPassword(String ids);
	
	int updateStatusByClassId(Long classId,String status);
	
	int updateStatusByStuId(List<Long> stuIdList,String status);
	
	int deleteByStuId(Long stuId);
	
	int deleteByTeaId(Long teaId);
	
	int deleteRUserByAccountId(long accountId);
}
