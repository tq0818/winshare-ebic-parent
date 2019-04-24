package com.winshare.edu.modules.api;


import com.alibaba.fastjson.JSONObject;
import com.winshare.edu.common.exception.ParamException;
import com.winshare.edu.common.exceptions.BusinessException;
import com.winshare.edu.common.validator.ParameterValidators;
import com.winshare.edu.modules.entity.ResponseEntity;
import com.winshare.edu.modules.entity.parameter.Q10003_V1_0;
import com.winshare.edu.modules.entity.parameter.Q10004_V1_0;
import com.winshare.edu.modules.entity.parameter.Q10005_V1_0;
import com.winshare.edu.modules.system.entity.BookInfo;
import com.winshare.edu.modules.system.entity.BooksCatalogInfo;
import com.winshare.edu.modules.system.entity.SysDic;
import com.winshare.edu.modules.system.service.BookService;
import com.winshare.edu.modules.system.service.BooksCatalogService;
import com.winshare.edu.modules.system.service.SysDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class ApiBookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private BooksCatalogService booksCatalogService;

    @RequestMapping(value = "Q10004/{version:.+}", method={RequestMethod.POST})
    @ResponseBody
    public ResponseEntity bookList(@RequestBody String json, @PathVariable("version") String version, HttpServletRequest request){
        ResponseEntity response = new ResponseEntity();

        try{
            Q10004_V1_0 book = ParameterValidators.validate(json, Q10004_V1_0.class);
            List<BookInfo> bookList  = bookService.findBookList(book.getGradeCode(),book.getBookName(),book.getPublisherCode(),book.getSubjectCode());
            response.setStatusCode("1");
            response.setExceptionCode("-1");
            response.setMessage("成功");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bookList",bookList);
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


    @RequestMapping(value = "Q10005/{version:.+}", method={RequestMethod.POST})
    @ResponseBody
    public ResponseEntity booksCatalog(@RequestBody String json, @PathVariable("version") String version, HttpServletRequest request){
        ResponseEntity response = new ResponseEntity();

        try{
            Q10005_V1_0 book = ParameterValidators.validate(json, Q10005_V1_0.class);
            List<BooksCatalogInfo> catalog = booksCatalogService.findByBook(book.getBookId());
            response.setStatusCode("1");
            response.setExceptionCode("-1");
            response.setMessage("成功");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("catalog",catalog);
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
