package com.winshare.edu.modules.api;


import com.alibaba.fastjson.JSONObject;
import com.winshare.edu.common.entity.SysDicVo;
import com.winshare.edu.common.exception.ParamException;
import com.winshare.edu.common.exceptions.BusinessException;
import com.winshare.edu.common.validator.ParameterValidators;
import com.winshare.edu.modules.entity.ResponseEntity;
import com.winshare.edu.modules.entity.parameter.Q10003_V1_0;
import com.winshare.edu.modules.system.entity.SysDic;
import com.winshare.edu.modules.system.service.SysDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class ApiSysDicController {
    @Autowired
    private SysDicService sysDicService;

    @RequestMapping(value = "Q10003/{version:.+}", method={RequestMethod.POST})
    @ResponseBody
    public ResponseEntity findCodeTye(@RequestBody String json, @PathVariable("version") String version, HttpServletRequest request){
        ResponseEntity response = new ResponseEntity();

        try{
            Q10003_V1_0 type = ParameterValidators.validate(json, Q10003_V1_0.class);
            List<SysDicVo> typeList  = sysDicService.findTypeByCode(type.getTypeCode());
            response.setStatusCode("1");
            response.setExceptionCode("-1");
            response.setMessage("成功");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("typeList",typeList);
            response.setContent(jsonObject);
        }catch(ParamException ex){
            response.setStatusCode("0");
            response.setExceptionCode(ex.getCode());
            response.setMessage(ex.getMessage());
        }catch(BusinessException ex){
            response.setStatusCode("0");
            response.setExceptionCode("-1");
            response.setMessage(ex.getMessage());
        }catch(Exception ex){
            response.setStatusCode("0");
            response.setExceptionCode("SYSTEM_ERROR");
            response.setMessage(ex.getMessage());
        }
        return response;
    }



}
