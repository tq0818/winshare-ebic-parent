package com.winshare.edu.modules.system.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;

import com.winshare.edu.common.utils.CacheUtils;
import com.winshare.edu.modules.system.entity.SysDic;

public class SysDicUtils {
	
	private static final String DIC_CACHE = "dicCache";
	
	/**
	 * 根据父级编码获取子级列表
	 * @param parentCode 父编码
	 * @return
	 */
	public static List<SysDic> getChildDicByParentCode(String parentCode){
		String regex = parentCode + "_*";
		List<SysDic> list = getDicByKeyRegex(regex);
		Collections.sort(list);
		return list;
	}

	/**
	 * 根据编码获取字典对象
	 * @param dicCode
	 * @return
	 */
	public static SysDic getDicByCode(String dicCode){
		return get(dicCode);
	}
	
	/**
	 * 根据字典父编码和字典值获取字典名称
	 * @param parentCode
	 * @param dicValue 
	 * @return 如果没找到对应的值,则返回该字典值
	 */
	public static String getNameByCode(String parentCode, String dicValue){
		List<SysDic> list = getChildDicByParentCode(parentCode);
		for(SysDic dic : list){
			if(dicValue.equals(dic.getDicValue())){
				return dic.getDicName() == null ? "" : dic.getDicName();
			}
		}
		return dicValue;	
	}
	
	/**
	 * 根据字典父编码和字典名称获取字典值
	 * @param dicName
	 * @param dicCode
	 * @return 未找到则返回字典名称
	 */
	public static String getDicValue(String dicName, String parentCode){
		List<SysDic> list = getDicByKeyRegex(parentCode+"*");
		for(SysDic dic : list){
			if(dicName.equals(dic.getDicName())){
				return dic.getDicValue() == null ? "" : dic.getDicValue();
			}

		}		
		return dicName;
	}
	
	
	/**
	 * 模糊查询方法
	 * @param regex
	 * @return
	 */
	public static List<SysDic> getDicByKeyRegex(String regex){
		Cache cache = CacheUtils.getCache(DIC_CACHE);
		Query query = cache.createQuery();
		query.includeKeys();
		query.includeValues();
		query.addCriteria(Query.KEY.ilike(regex));
		Results results = query.execute();
		List<SysDic> list = new ArrayList<SysDic>();
		for(Result result : results.all()){
			list.add((SysDic) result.getValue());
		}
		Collections.reverse(list);
		return list;
	}
	
	public static void put(String key, SysDic value){
		CacheUtils.put(DIC_CACHE, key, value);
	}
	
	public static SysDic get(String key){
		SysDic dic = (SysDic) CacheUtils.get(DIC_CACHE, key);
		return dic;
	}
	
	public static void removeAll(){
		CacheUtils.removeAll(DIC_CACHE);
	}
}
