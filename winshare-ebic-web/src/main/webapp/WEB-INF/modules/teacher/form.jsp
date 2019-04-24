<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>教师管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	//var usrId = "${usrId}";//教师id
	var clsPick ;
	var subPick ;
	
	var validateForm;
	function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
	  if(validateForm.form()){
		  var myform=$('#inputForm'); //得到form对象
		  
		  //动态绑定教师班级关联信息
		  var clsValue = JSON.stringify(clsPick.getValues());
          var clsInput=$("<input type='text' name='choiceClass'/>");
          var classLen = getJsonLength(clsPick.getValues());
            
          clsInput.attr("value", clsValue);
          
          
          //动态绑定教师科目关联信息
		  var subValue = JSON.stringify(subPick.getValues());
          var subInput=$("<input type='text' name='choiceSubject'/>");
          
        
          var subLen = getJsonLength(subPick.getValues());
          subInput.attr("value", subValue);
          if(subLen <=0){
        	  top.layer.alert('该教师至少关联一个学科!', {icon: 0, title:'警告'});
         	  return false;
          }else if(classLen <= 0){
        	  top.layer.alert('该教师至少关联一个班级!', {icon: 0, title:'警告'});
        	  return false;
          }
          
          myform.append(subInput);
          myform.append(clsInput);
          console.log( $("input[name='choiceSubject']").val());
          console.log( $("input[name='choiceClass']").val());
          myform.submit();
		  return true; 
	  }
	  return false;
	}
	
	//页面初始化
	$(function() {
		
		validateForm = $("#inputForm").validate({
			rules : {
				loginName: {remote:{type:"post",async:false, url:"${ctx}/system/account/isExistLoginName?oldLoginName=${teacher.loginName}"},checkAccount:true},
				email : {
					email : true
				},
				phone : {
					isPhone : true
				},
				name : {
				    required: true
				}
			},
			messages : {
				loginName :{remote :"登录名重复"},
				name : {
					name : "请输入姓名"
				},
				phone : {
					phone : "请输入正确的手机号"
				},
				email : {
					email : "请输入正确的电子邮箱地址"
				}
			},
			submitHandler: function(form){
				loading('正在提交，请稍等...');
				form.submit();
			},
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
		
		//日历组件
		var birthDate = {
	        elem: "#birthDate",
	        event: "focus",
	        format: "YYYY-MM-DD",
	        min: '1900-01-01',
	        max: laydate.now(),
	        istime: false,
	        istoday: false
	    };
		laydate(birthDate);
		
	/* 	var val = [
		            {id: 01, text: '2017级1班（小）', search:'2017级1班（小）', selected:true},
		            {id: 02, text: '2017级2班（小）', search:'2017级2班（小）', selected:true},
		            {id: 03, text: '2017级3班（小）', search:'2017级3班（小）'},
		            {id: 04, text: '2017级4班（小）', search:'2017级4班（小）'},
		            {id: 05, text: '2016级1班（小）', search:'2016级1班（小）'},
		            {id: 06, text: '2016级2班（小）', search:'2016级2班（小）'},
		            {id: 07, text: '2016级3班（小）', search:'2016级3班（小）'},
		            {id: 08, text: '2015级1班（小）', search:'2015级1班（小）'},
		            {id: 09, text: '2015级2班（小）', search:'2015级2班（小）'},
		         ]; */
		        
		var val = ${classVal};//班级json字符串数组
		
		
		clsPick = $("#choiceclass").choicebox({
    	 	leftTitle:'所有班级', 
    	 	rightTitle:'所有班级', 
    	 	data:val, 
    	 	rightSearch:false, 
    	 	multiple:true, 
    	 	leftPlaceholder:'班级名称', 
    	 	rightPlaceholder:'班级名称',
    	 	height:220
    	 });
		
		/* var subjects = [
		            {id: 01, text: '语文', search:'语文', selected:true},
		            {id: 02, text: '数学', search:'数学', selected:true},
		            {id: 03, text: '外语', search:'外语'},
		            {id: 04, text: '体育', search:'体育'}
		         ]; */
		        
		     
		var subjects = ${subjectVal};
		
		subPick = $("#choicesubject").choicebox({
    	 	leftTitle:'全部科目', 
    	 	rightTitle:'所教科目', 
    	 	data:subjects, 
    	 	leftSearch:false,
    	 	rightSearch:false, 
    	 	multiple:true, 
    	 	leftPlaceholder:'科目', 
    	 	rightPlaceholder:'科目',
    	 	height:220
    	 });
	});
	
	
	$.validator.addMethod("checkAccount",function(value,element,params){  
        var checkAccount = new RegExp("^[0-9a-zA-Z_]{1,}$");  
        return this.optional(element)||(checkAccount.test(value));  
    },"登录名只能是包含数字和字母线划线");
	
	function getJsonLength(jsonData){
	    var jsonLength = 0;
	    for(var item in jsonData){
	       jsonLength++;
	    }
	    return jsonLength;
	} 
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="teacher" action="${ctx}/teacher/save" method="post"  class="form-horizontal">
	<sys:message content="${message}"/>
	<form:input name="orgId" type="hidden" path="orgId"/>
	<form:input name="userId" type="hidden" path="userId"/>
	<form:input name="accountId" type="hidden" path="accountId"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
	     <tbody>
		      <tr>
		         <td  class="width-15 active"><label class="pull-right">登录名:</label></td>
		         <td class="width-35">
		         	<form:input name="loginName" path="loginName" maxlength="10" class="form-control"/></td>
		      	 <td class="width-15 active"><label class="pull-right"><font color="red">*</font>姓名:</label></td>
		         <td class="width-35">
		         	<form:input name="name" path="name" maxlength="50" class="form-control required"/>
		         </td>
		      </tr>
		      <tr>
		       	 <td class="width-15 active"><label class="pull-right">性别:</label></td>
		         <td class="width-35">
			         <form:select path="sex" class="form-control ">
						<form:options items="${fns:getChildDicByParentCode('sex')}" itemLabel="dicName" itemValue="dicValue" htmlEscape="false"/>
			         </form:select>
		         </td>
		         <td class="width-15 active"><label class="pull-right">出生日期:</label></td>
		         <td class="width-35">
		         <input id="birthDate" name="birthDate" type="text" maxlength="30" class="laydate-icon form-control layer-date input-sm" value="<fmt:formatDate value="${teacher.birthDate}" pattern="yyyy-MM-dd"/>">
		         </td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">手机:</label></td>
		         <td class="width-35">
		         <form:input name="phone" path="phone" htmlEscape="false" maxlength="11" class="form-control isSpecialWords"/>
		         </td>
		         <td class="width-15 active"><label class="pull-right">电邮:</label></td>
		         <td class="width-35">
		         <form:input name="email" path="email"  maxlength="30" class="form-control isSpecialWords"/>
		         </td>
		      </tr>  
		
	      </tbody>
	 </table>
	
	 <hr/>
	 	<span><strong>班级关联信息</strong></span>
	 	 <hr/>
		 <div id="choiceclass">
		 </div>
		 
	<hr/>
		<span><strong>学科关联信息</strong></span>
	 	 <hr/>
		<div id="choicesubject">
		</div>
		
	</form:form>
	
</body>
</html>
