package com.winshare.edu.modules.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winshare.edu.common.utils.DESUtil2;
import com.winshare.edu.modules.classes.entity.ClassGroup;
import com.winshare.edu.modules.classes.entity.GroupScoreItem;
import com.winshare.edu.modules.classes.entity.TeacherCourse;
import com.winshare.edu.modules.classes.entity.TeacherScoreItem;
import com.winshare.edu.modules.classes.service.*;
import com.winshare.edu.modules.entity.parameter.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.winshare.edu.common.entity.ClassVo;
import com.winshare.edu.common.entity.StudentVo;
import com.winshare.edu.common.entity.SubjectVo;
import com.winshare.edu.common.entity.TeacherVo;
import com.winshare.edu.common.exception.ParamException;
import com.winshare.edu.common.exceptions.BusinessException;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.validator.ParameterValidators;
import com.winshare.edu.modules.entity.ResponseEntity;
import com.winshare.edu.modules.system.entity.AccountInfo;
import com.winshare.edu.modules.teachersMgm.entity.Teacher;
import com.winshare.edu.modules.teachersMgm.service.TeacherMgmService;


@Controller
@RequestMapping("/teacher")
public class ApiTeacherController {

    @Autowired
    private TeacherMgmService teacherService;

    @Autowired
    private ClassGroupService classGroupService;

    @Autowired
    private GroupScoreService groupScoreService;

    @Autowired
    private StudentScoreService studentScoreService;

    @Autowired
    private TeacherCourseService teacherCourseService;

    @Autowired
    private TeacherScoreItemService teacherScoreItemService;

    @RequestMapping(value = "Q10001/{version:.+}", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity teacherLogin(@RequestBody String json, @PathVariable("version") String version, HttpServletRequest request) {
        ResponseEntity response = new ResponseEntity();

        try {
            Q10001_V1_0 login = ParameterValidators.validate(json, Q10001_V1_0.class);
            TeacherVo teacherVo = teacherService.teacherLogin(login.getAccount(), login.getPassword(), request);
            response.setStatusCode("1");
            response.setExceptionCode("-1");
            response.setMessage("成功");
            response.setContent(teacherVo);
        } catch (ParamException ex) {
            response.setStatusCode("0");
            response.setExceptionCode(ex.getCode());
            response.setMessage(ex.getMessage());
        } catch (BusinessException ex) {
            response.setStatusCode("0");
            response.setExceptionCode("-1");
            response.setMessage(ex.getMessage());
        } catch (Exception ex) {
            response.setStatusCode("0");
            response.setExceptionCode("SYSTEM_ERROR");
            response.setMessage(ex.getMessage());
        }
        return response;
    }


    @RequestMapping(value = "Q10002/{version:.+}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity getStudent(@RequestBody String json, @PathVariable("version") String version) {
        ResponseEntity response = new ResponseEntity();
        try {
            Q10002_V1_0 obj = ParameterValidators.validate(json, Q10002_V1_0.class);
            List<StudentVo> studentList = teacherService.findStudentVoByClassId(obj.getClassId(), obj.getTeacherId(), obj.getSubject());
            List<StudentVo> getTempList = teacherService.getTempList(obj.getClassId());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("studentList", studentList);
            jsonObject.put("tempList", getTempList);
            response.setStatusCode("1");
            response.setExceptionCode("-1");
            response.setMessage("成功");
            response.setContent(jsonObject);
        } catch (ParamException ex) {
            response.setStatusCode("0");
            response.setExceptionCode(ex.getCode());
            response.setMessage(ex.getMessage());
        } catch (BusinessException ex) {
            response.setStatusCode("0");
            response.setExceptionCode("-1");
            response.setMessage(ex.getMessage());
        } catch (Exception ex) {
            response.setStatusCode("0");
            response.setExceptionCode("SYSTEM_ERROR");
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    private List<StudentVo> getTempList() {
        List<StudentVo> tempList = new ArrayList<StudentVo>();
        for (int i = 0; i < 4; i++) {
            StudentVo stu = new StudentVo();
            stu.setId((long) i);
            stu.setAccount("tempAccount" + i);
            stu.setLoginAlias("tempLoginAlias" + i);
            stu.setSex("1");
            stu.setUserAvatar("http://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=2226833606,345026538&os=815943065,3776425799&simid=0,0&pn=88&rn=1&di=97425778060&ln=1946&fr=&fmq=1510301522272_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=2951050078,207756558&istype=0&ist=&jit=&bdtype=17&spn=0&pi=0&gsm=1e&objurl=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fd4628535e5dde711a5533cebadefce1b9d16612d.jpg&rpstart=0&rpnum=0&adpicid=0");
            tempList.add(stu);
        }
        return tempList;
    }

    /**
     * 学员分组信息
     *
     * @param json
     * @param version
     * @return
     */
    @RequestMapping(value = "Q10006/{version:.+}", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity getClassGroup(@RequestBody String json, @PathVariable("version") String version) {
        ResponseEntity response = new ResponseEntity();
        try {
            Q10006_V1_0 obj = ParameterValidators.validate(json, Q10006_V1_0.class);
            List<ClassGroup> groupList = classGroupService.getClassGroup(obj.getClassId(), obj.getTeacherId(), obj.getGrade(), obj.getSubject());
            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i < groupList.size(); i++) {
                String[] str = groupList.get(i).getStudent().split(",");
                Long[] l = new Long[str.length];
                for (int j = 0; j < str.length; j++) {
                    l[j] = Long.parseLong(str[j]);
                }
                groupList.get(i).setStudents(l);
            }
            jsonObject.put("groupList", groupList);
            response.setStatusCode("1");
            response.setExceptionCode("-1");
            response.setMessage("成功");
            response.setContent(jsonObject);
        } catch (ParamException ex) {
            response.setStatusCode("0");
            response.setExceptionCode(ex.getCode());
            response.setMessage(ex.getMessage());
        } catch (BusinessException ex) {
            response.setStatusCode("0");
            response.setExceptionCode("-1");
            response.setMessage(ex.getMessage());
        } catch (Exception ex) {
            response.setStatusCode("0");
            response.setExceptionCode("SYSTEM_ERROR");
            response.setMessage(ex.getMessage());
        }
        return response;
    }


    /**
     * 学生分组处理
     *
     * @param json
     * @param version
     * @return
     */
    @RequestMapping(value = "H10007/{version:.+}", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity getClassGroupDeal(@RequestBody String json, @PathVariable("version") String version) {
        ResponseEntity response = new ResponseEntity();

        try {
            H10007_V1_0 obj = ParameterValidators.validate(json, H10007_V1_0.class);
            classGroupService.getClassGroupDeal(obj.getClassId(), obj.getTeacherId(), obj.getSubject(), obj.getGrade(), obj.getGroupList());
            JSONObject jsonObject = new JSONObject();
            response.setStatusCode("1");
            response.setExceptionCode("-1");
            response.setMessage("成功");
            response.setContent(jsonObject);
        } catch (ParamException ex) {
            response.setStatusCode("0");
            response.setExceptionCode(ex.getCode());
            response.setMessage(ex.getMessage());
        } catch (BusinessException ex) {
            response.setStatusCode("0");
            response.setExceptionCode("-1");
            response.setMessage(ex.getMessage());
        } catch (Exception ex) {
            response.setStatusCode("0");
            response.setExceptionCode("SYSTEM_ERROR");
            response.setMessage(ex.getMessage());
        }
        return response;
    }


    /**
     * 小组加分
     *
     * @param json
     * @param version
     * @return
     */
    @RequestMapping(value = "H10009/{version:.+}", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity groupBonusPoints(@RequestBody String json, @PathVariable("version") String version) {
        ResponseEntity response = new ResponseEntity();

        try {
            H10009_V1_0 obj = ParameterValidators.validate(json, H10009_V1_0.class);
            groupScoreService.groupBonusPoints(obj.getGroupId(), obj.getItemName(), obj.getScore(), obj.getBookName(), obj.getChapterName());
            JSONObject jsonObject = new JSONObject();
            response.setStatusCode("1");
            response.setExceptionCode("-1");
            response.setMessage("成功");
            response.setContent(jsonObject);
        } catch (ParamException ex) {
            response.setStatusCode("0");
            response.setExceptionCode(ex.getCode());
            response.setMessage(ex.getMessage());
        } catch (BusinessException ex) {
            response.setStatusCode("0");
            response.setExceptionCode("-1");
            response.setMessage(ex.getMessage());
        } catch (Exception ex) {
            response.setStatusCode("0");
            response.setExceptionCode("SYSTEM_ERROR");
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    /**
     * 个人加分
     *
     * @param json
     * @param version
     * @return
     */
    @RequestMapping(value = "H10010/{version:.+}", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity stuBonusPoints(@RequestBody String json, @PathVariable("version") String version) {
        ResponseEntity response = new ResponseEntity();

        try {
            H100010_V1_0 obj = ParameterValidators.validate(json, H100010_V1_0.class);
            studentScoreService.stuBonusPoints(obj.getStudentId(), obj.getTeacherId(), obj.getGrade(), obj.getSubject(), obj.getItemName(), obj.getScore(), obj.getBookName(), obj.getChapterName());
            JSONObject jsonObject = new JSONObject();
            response.setStatusCode("1");
            response.setExceptionCode("-1");
            response.setMessage("成功");
            response.setContent(jsonObject);
        } catch (ParamException ex) {
            response.setStatusCode("0");
            response.setExceptionCode(ex.getCode());
            response.setMessage(ex.getMessage());
        } catch (BusinessException ex) {
            response.setStatusCode("0");
            response.setExceptionCode("-1");
            response.setMessage(ex.getMessage());
        } catch (Exception ex) {
            response.setStatusCode("0");
            response.setExceptionCode("SYSTEM_ERROR");
            response.setMessage(ex.getMessage());
        }
        return response;
    }


    /**
     * 教师授课信息
     *
     * @param json
     * @param version
     * @return
     */
    @RequestMapping(value = "H10011/{version:.+}", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity saveTeacherInfo(@RequestBody String json, @PathVariable("version") String version) {
        ResponseEntity response = new ResponseEntity();

        try {
            H100011_V1_0 obj = ParameterValidators.validate(json, H100011_V1_0.class);
            Long courseId = teacherCourseService.saveTeacherInfo(obj.getClassId(), obj.getTeacherId(), obj.getSubject(),
                    obj.getTeacherName(), obj.getTeacherProtocol(),
                    obj.getTeacherIp(), obj.getTeacherPort(),
                    obj.getGradeName(), obj.getSubjectName(),
                    obj.getBookName(), obj.getChapterName());
            JSONObject jsonObject = new JSONObject();
            response.setStatusCode("1");
            response.setExceptionCode("-1");
            response.setMessage("成功");
            jsonObject.put("courseId", courseId);
            response.setContent(jsonObject);
        } catch (ParamException ex) {
            response.setStatusCode("0");
            response.setExceptionCode(ex.getCode());
            response.setMessage(ex.getMessage());
        } catch (BusinessException ex) {
            response.setStatusCode("0");
            response.setExceptionCode("-1");
            response.setMessage(ex.getMessage());
        } catch (Exception ex) {
            response.setStatusCode("0");
            response.setExceptionCode("SYSTEM_ERROR");
            response.setMessage(ex.getMessage());
        }
        return response;
    }


    /**
     * 获取分数项
     *
     * @param json
     * @param version
     * @return
     */
    @RequestMapping(value = "Q10012/{version:.+}", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity getTeacherScoreItem(@RequestBody String json, @PathVariable("version") String version) {
        ResponseEntity response = new ResponseEntity();

        try {
            Q100012_V1_0 obj = ParameterValidators.validate(json, Q100012_V1_0.class);
            List<TeacherScoreItem> itemList = teacherScoreItemService.getTeacherScoreItem(obj.getTeacherId(), obj.getClassId(), obj.getSubject());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("itemList", itemList);
            response.setStatusCode("1");
            response.setExceptionCode("-1");
            response.setMessage("成功");
            response.setContent(jsonObject);
        } catch (ParamException ex) {
            response.setStatusCode("0");
            response.setExceptionCode(ex.getCode());
            response.setMessage(ex.getMessage());
        } catch (BusinessException ex) {
            response.setStatusCode("0");
            response.setExceptionCode("-1");
            response.setMessage(ex.getMessage());
        } catch (Exception ex) {
            response.setStatusCode("0");
            response.setExceptionCode("SYSTEM_ERROR");
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    /**
     * 增加分数项
     *
     * @param json
     * @param version
     * @return
     */
    @RequestMapping(value = "H10013/{version:.+}", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity saveTeacherScoreItem(@RequestBody String json, @PathVariable("version") String version) {
        ResponseEntity response = new ResponseEntity();

        try {
            JSONObject jsonObject = new JSONObject();
            H100013_V1_0 obj = ParameterValidators.validate(json, H100013_V1_0.class);
            //获取该班级该教师该科目下所有的加分项
            List<TeacherScoreItem> list = teacherScoreItemService.getTeacherScoreItem(obj.getTeacherId(), obj.getClassId(), obj.getSubject());
            for (TeacherScoreItem item : list) {
                if (item.getItemName().equals(obj.getItemName())) {
                    response.setStatusCode("0");
                    response.setExceptionCode("-1");
                    response.setMessage("存在相同加分项");
                    response.setContent(jsonObject);
                    return response;
                }
            }
            teacherScoreItemService.saveTeacherScoreItem(obj.getTeacherId(), obj.getClassId(), obj.getSubject(), obj.getItemName(), obj.getScore());
            response.setStatusCode("1");
            response.setExceptionCode("-1");
            response.setMessage("成功");
            response.setContent(jsonObject);
        } catch (ParamException ex) {
            response.setStatusCode("0");
            response.setExceptionCode(ex.getCode());
            response.setMessage(ex.getMessage());
        } catch (BusinessException ex) {
            response.setStatusCode("0");
            response.setExceptionCode("-1");
            response.setMessage(ex.getMessage());
        } catch (Exception ex) {
            response.setStatusCode("0");
            response.setExceptionCode("SYSTEM_ERROR");
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    /**
     * 删除分数项
     *
     * @param json
     * @param version
     * @return
     */
    @RequestMapping(value = "H10014/{version:.+}", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity deleteTeacherScoreItem(@RequestBody String json, @PathVariable("version") String version) {
        ResponseEntity response = new ResponseEntity();

        try {
            H100014_V1_0 obj = ParameterValidators.validate(json, H100014_V1_0.class);
            teacherScoreItemService.delete(obj.getItemId());
            JSONObject jsonObject = new JSONObject();
            response.setStatusCode("1");
            response.setExceptionCode("-1");
            response.setMessage("成功");
            response.setContent(jsonObject);
        } catch (ParamException ex) {
            response.setStatusCode("0");
            response.setExceptionCode(ex.getCode());
            response.setMessage(ex.getMessage());
        } catch (BusinessException ex) {
            response.setStatusCode("0");
            response.setExceptionCode("-1");
            response.setMessage(ex.getMessage());
        } catch (Exception ex) {
            response.setStatusCode("0");
            response.setExceptionCode("SYSTEM_ERROR");
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    /**
     * 下课
     *
     * @param json
     * @param version
     * @return
     */
    @RequestMapping(value = "H10015/{version:.+}", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity updateTeacherInfo(@RequestBody String json, @PathVariable("version") String version) {
        ResponseEntity response = new ResponseEntity();

        try {
            H100015_V1_0 obj = ParameterValidators.validate(json, H100015_V1_0.class);
            teacherCourseService.updateTeacherInfo(obj.getCourseId());
            JSONObject jsonObject = new JSONObject();
            response.setStatusCode("1");
            response.setExceptionCode("-1");
            response.setMessage("成功");
            response.setContent(jsonObject);
        } catch (ParamException ex) {
            response.setStatusCode("0");
            response.setExceptionCode(ex.getCode());
            response.setMessage(ex.getMessage());
        } catch (BusinessException ex) {
            response.setStatusCode("0");
            response.setExceptionCode("-1");
            response.setMessage(ex.getMessage());
        } catch (Exception ex) {
            response.setStatusCode("0");
            response.setExceptionCode("SYSTEM_ERROR");
            response.setMessage(ex.getMessage());
        }
        return response;
    }


    /**
     * 课程确认
     *
     * @param json
     * @param version
     * @return
     */
    @RequestMapping(value = "Q10016/{version:.+}", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity findTeacherInfo(@RequestBody String json, @PathVariable("version") String version) {
        ResponseEntity response = new ResponseEntity();
        TeacherCourse teacherCourse = null;
        JSONObject jsonObject = new JSONObject();
        try {
            Q100016_V1_0 obj = ParameterValidators.validate(json, Q100016_V1_0.class);
            teacherCourse = teacherCourseService.findTeacherInfo(obj.getClassId(), obj.getTeacherId(), obj.getSubject());
            response.setStatusCode("1");
            response.setExceptionCode("-1");
            response.setMessage("成功");
            response.setContent(jsonObject);
        } catch (ParamException ex) {
            response.setStatusCode("0");
            response.setExceptionCode(ex.getCode());
            response.setMessage(ex.getMessage());
        } catch (BusinessException ex) {
            response.setStatusCode("0");
            response.setExceptionCode("-1");
            response.setContent(ex.getParam());
            response.setMessage(ex.getMessage());
        } catch (Exception ex) {
            response.setStatusCode("0");
            response.setExceptionCode("SYSTEM_ERROR");
            response.setMessage(ex.getMessage());
        }
        return response;
    }

}
