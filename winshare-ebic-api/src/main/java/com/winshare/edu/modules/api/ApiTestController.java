package com.winshare.edu.modules.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.winshare.edu.common.exception.ParamException;
import com.winshare.edu.common.validator.ParameterValidators;
import com.winshare.edu.modules.entity.ResponseEntity;
import com.winshare.edu.modules.entity.TestVo;
import com.winshare.edu.modules.entity.parameter.T100_V1_0;

@Controller
@RequestMapping("/test")
public class ApiTestController {

	@RequestMapping(value = "T100/{version:.+}", method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
	public ResponseEntity test(@RequestBody String json, @PathVariable("version") String version){
		ResponseEntity response = new ResponseEntity();
		try{
			T100_V1_0 t100 = ParameterValidators.validate(json, T100_V1_0.class);
			TestVo test = new TestVo();
			test.setResult("Hello! " + t100.getParam());
			response.setStatusCode("1");
			response.setExceptionCode("-1");
			response.setMessage("成功");
			response.setContent(test);	
		}catch(ParamException ex){
			response.setStatusCode("0");
			response.setExceptionCode(ex.getCode());
			response.setMessage(ex.getMessage());
		}catch(Exception ex){
			response.setStatusCode("0");
			response.setExceptionCode("SYSTEM_ERROR");
			response.setMessage(ex.getMessage());
		}
		System.out.println(JSONObject.toJSON(response).toString());	
		return response;
	}
	
}
