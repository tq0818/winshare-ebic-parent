package com.winshare.edu.modules.system.entity;

import java.util.Date;

import com.winshare.edu.common.entity.BaseEntity;

public class TeacherVersion extends BaseEntity<TeacherVersion>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7608816973265676068L;

	private String version;
	
	private String packageUrl;
	
	private String exeUrl;
	
	private Integer  versionSort;
	
	private String isForce;
	
	private String descript;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPackageUrl() {
		return packageUrl;
	}

	public void setPackageUrl(String packageUrl) {
		this.packageUrl = packageUrl;
	}

	public String getExeUrl() {
		return exeUrl;
	}

	public void setExeUrl(String exeUrl) {
		this.exeUrl = exeUrl;
	}

	public Integer getVersionSort() {
		return versionSort;
	}

	public void setVersionSort(Integer versionSort) {
		this.versionSort = versionSort;
	}

	public String getIsForce() {
		return isForce;
	}

	public void setIsForce(String isForce) {
		this.isForce = isForce;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}
	
	

	
	
}
