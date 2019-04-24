package com.winshare.edu.modules.frame.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.winshare.edu.modules.system.entity.AccountInfo;
import com.winshare.edu.modules.system.entity.OrgInfo;
import com.winshare.edu.modules.system.entity.SysLog;
import com.winshare.edu.modules.system.service.AccountInfoService;
import com.winshare.edu.modules.system.service.SysLogService;


public class WinshareAuthenticationFilter extends FormAuthenticationFilter{
	
    @Autowired
    private AccountInfoService accountInfoService;

    @Autowired
    private SysLogService sysLogService;
    
	private static final Logger LOGGER = LoggerFactory.getLogger(WinshareAuthenticationFilter.class);   
    
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		String loginName = (String)token.getPrincipal();
		AccountInfo accountInfo = accountInfoService.findByLoginName(loginName);
		accountInfoService.increaseLoginCount(accountInfo.getId());
		SecurityUtils.getSubject().getSession().setAttribute("WINSHARE_USER", accountInfo);
		SysLog log = new SysLog();
		log.setAccount(accountInfo.getLoginName());
		log.setUserName(accountInfo.getName());
		OrgInfo orgInfo = accountInfo.getOrgInfo();
		if(orgInfo != null){
			log.setOrgName(orgInfo.getOrgName());
			log.setOrgId(orgInfo.getId());
		}
		sysLogService.save(log);
		return super.onLoginSuccess(token, subject, request, response);
	}
	
}
