package com.winshare.edu.modules.test.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping(value = "",method ={RequestMethod.GET} )
	@ResponseBody
	public JSONObject test(String deviceId, String h2s,String nh3,String t ,String rh){
		System.out.println("deviceId:"+deviceId+",h2s:"+h2s+",nh3:"+nh3+",t:"+t+",rh:"+rh);
		return (JSONObject) new JSONObject().put("status","0");
	}


	@RequestMapping(value = "",method ={RequestMethod.POST} )
	public void test2(@RequestBody String s ){
		System.out.println(s);
	}


}
