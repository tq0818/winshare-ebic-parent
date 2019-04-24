package com.winshare.edu.modules.api;

import com.alibaba.fastjson.JSONObject;
import com.winshare.edu.common.exception.ParamException;
import com.winshare.edu.common.exceptions.BusinessException;
import com.winshare.edu.common.validator.ParameterValidators;
import com.winshare.edu.modules.classes.entity.TeacherCourse;
import com.winshare.edu.modules.classes.service.TeacherCourseService;
import com.winshare.edu.modules.entity.ResponseEntity;
import com.winshare.edu.modules.entity.parameter.Q20001_V1_0;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student")
public class ApiStudentController {
    @Autowired
    private TeacherCourseService teacherCourseService;

    /**
     * 获取教师端信息
     * @param json
     * @param version
     * @return
     */
    @RequestMapping(value = "Q20001/{version:.+}", method={RequestMethod.POST})
    @ResponseBody
    public ResponseEntity stuFindTeacherInfo(@RequestBody String json, @PathVariable("version") String version){
        ResponseEntity response = new ResponseEntity();

        try{
            Q20001_V1_0 obj = ParameterValidators.validate(json, Q20001_V1_0.class);
            List<TeacherCourse> courseList = teacherCourseService.stuFindTeacherInfoByStu(obj.getAccount());
            JSONObject jsonObject = new JSONObject();
            response.setStatusCode("1");
            response.setExceptionCode("-1");
            if(courseList.size() ==0){

            }
            response.setMessage("成功");
            jsonObject.put("courseList",courseList);
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
