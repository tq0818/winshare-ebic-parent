package com.winshare.edu.modules.classes.service;

import com.winshare.edu.modules.classes.entity.ClassGroup;
import com.winshare.edu.modules.classes.entity.GroupScoreItem;
import com.winshare.edu.modules.classes.entity.StudentScore;
import com.winshare.edu.modules.classes.entity.StudentScoreItem;
import com.winshare.edu.modules.classes.mapper.ClassGroupMapper;
import com.winshare.edu.modules.classes.mapper.GroupScoreMapper;
import com.winshare.edu.modules.classes.mapper.StudentScoreItemMapper;
import com.winshare.edu.modules.classes.mapper.StudentScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudentScoreService {

    @Autowired
    private StudentScoreMapper studentScoreMapper;
    @Autowired
    private StudentScoreItemMapper studentScoreItemMapper;

    public void stuBonusPoints(Long stuId,Long teacheerId,String grade ,String subject,String itemName, Integer score, String bookName, String chapterName) {
        StudentScore studentScore = new StudentScore();
        studentScore.setStudentId(Integer.parseInt(String.valueOf(stuId)));
        studentScore.setTeacherId(Integer.parseInt(String.valueOf(teacheerId)));
        studentScore.setGrade(grade);
        studentScore.setSubject(subject);
        //查询学生当前积分
        StudentScore student = studentScoreMapper.findSutdent(studentScore);

        if(null != student){
            studentScore.setId(student.getId());
            //修改学生积分
            studentScore.setModifyTime(new Date());
            studentScore.setStudentScore(score+student.getStudentScore());
            studentScoreMapper.update(studentScore);
        }else{
            //新增积分
            studentScore.setCreateTime(new Date());
            studentScore.setModifyTime(new Date());
            studentScore.setStudentScore(score);
            studentScoreMapper.insert(studentScore);
        }
        //增加明细
        StudentScoreItem stuScoreItem = new StudentScoreItem();
        stuScoreItem.setScoreId(Integer.parseInt(String.valueOf(studentScore.getId())));
        stuScoreItem.setItemName(itemName);
        stuScoreItem.setScore(score);
        stuScoreItem.setBookName(bookName);
        stuScoreItem.setChapterName(chapterName);
        stuScoreItem.setSource("2");
        stuScoreItem.setCreateTime(new Date());
        stuScoreItem.setModifyTime(new Date());
        try{
            studentScoreItemMapper.insert(stuScoreItem);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
