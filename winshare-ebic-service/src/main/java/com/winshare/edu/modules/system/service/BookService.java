package com.winshare.edu.modules.system.service;

import com.github.pagehelper.PageHelper;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.modules.system.entity.BookInfo;
import com.winshare.edu.modules.system.entity.TeacherVersion;
import com.winshare.edu.modules.system.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("booKService")
public class BookService {
    @Autowired
    private BookMapper bookMapper;

    public List<BookInfo> findList(PageRequest pageRequest, BookInfo bookInfo){
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getOrderBy());
        List<BookInfo> list = bookMapper.findList(bookInfo);
        return list;
    }

    public List<BookInfo> findBookList(String gradeCode,String bookName,String publisherCode,String subjectCode){
        BookInfo bookInfo = new BookInfo();
        bookInfo.setGradeCode(gradeCode);
        bookInfo.setName(bookName);
        bookInfo.setPublisherCode(publisherCode);
        bookInfo.setSubjectCode(subjectCode);
        List<BookInfo> list = bookMapper.findBookList(bookInfo);
        return list;
    }

    public int saveOrUpdateStudentInfo(BookInfo bookInfo){
        int result = 0;
        if(null != bookInfo.getId()){
            //修改
            result = bookMapper.update(bookInfo);
        }else{
            //新增
            result = bookMapper.add(bookInfo);
        }
        return result;
    }

    public BookInfo findById(BookInfo id){
        try{
            BookInfo bookInfo = bookMapper.findById(id);
            return bookInfo;
        }catch (Exception i){
            i.printStackTrace();
        }
        return null;
    }
}
