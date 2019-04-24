package com.winshare.edu.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.winshare.edu.modules.system.entity.AreaInfo;

public interface Constant {

    public static List<AreaInfo> provinceList = new ArrayList<AreaInfo>();
	
	public static List<AreaInfo> cityList = new ArrayList<AreaInfo>();
	
	public static List<AreaInfo> districtList = new ArrayList<AreaInfo>();
	
	public static Map<String,String> areaMap = new HashMap<String,String>();
	
	public static final String ACCOUNT_DEFAULT_PASSWORD = "000000";
	
	public static ResourceBundle myResources =  ResourceBundle.getBundle("winshare", Locale.getDefault());
		     

	
}
