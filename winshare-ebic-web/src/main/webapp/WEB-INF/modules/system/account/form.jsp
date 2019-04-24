<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>账户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	var validateForm;
	function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
	  if(validateForm.form()){
		  $("#inputForm").submit();
		  return true;
	  }
	  return false;
	}
	$(document).ready(function() {
        //外部js调用
        laydate({
            elem: '#birthDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });
		$("#loginName").focus();
		validateForm = $("#inputForm").validate({
			rules: {
				loginName: {remote: "${ctx}/system/account/isExistLoginName?oldLoginName=" + encodeURIComponent('${accountInfo.loginName}'),checkAccount:true},
			    name:{checkName:true}
			},
			messages: {
				loginName: {remote: "登录名已存在"},
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
	});
	
	$.validator.addMethod("checkAccount",function(value,element,params){  
        var checkAccount = new RegExp("^[0-9a-zA-Z_]{1,30}$");  
        return this.optional(element)||(checkAccount.test(value));  
    },"登录名只能是包含数字和字母线划线");  

	$.validator.addMethod("checkName",function(value,element,params){  
        var checkName = new RegExp("^[a-zA-Z\u0391-\uFFE5]{1,10}$");  
        return this.optional(element)||(checkName.test(value));  
    },"姓名只能是字母和中文");  

	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="accountInfo" action="${ctx}/system/account/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>所在机构:</label></td>
		         <td class="width-35">
					 <sys:treeselect id="orgInfo" name="orgInfo.id" value="${accountInfo.orgInfo.id}" labelName="orgInfo.orgName" labelValue="${accountInfo.orgInfo.orgName}"
					 title="机构" url="/system/org/treeData" extId="${orgInfo.id}" cssClass="form-control required" />
				 </td>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>登录名:</label></td>
		         <td class="width-35">
		         	<input id="oldLoginName" name="oldLoginName" type="hidden" value="${accountInfo.loginName}">
		         	<form:input path="loginName" htmlEscape="false" maxlength="30" class="form-control required"/>
		         </td>
		      </tr>
		       <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>姓名:</label></td>
		         <td class="width-35"><form:input path="name" htmlEscape="false" maxlength="10" class="form-control required"/></td>
		         <td  class="width-15 active"><label class="pull-right">性别:</label></td>
		         <td class="width-35">
	             	<form:select path="sex" class="form-control ">
						<form:options items="${fns:getChildDicByParentCode('sex')}" itemLabel="dicName" itemValue="dicValue" htmlEscape="false"/>
					</form:select>
		         </td>
		      </tr>
		      <tr>				
		         <td  class="width-15 active"><label class="pull-right">出生日期:</label></td>	         
		         <td class="width-35"><input id="birthDate" name="birthDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
											value="${accountInfo.birthDate}" /></td>
		         <td class="width-15 active"><label class="pull-right">账号类型:</label></td>
		         <td class="width-35">
	             	<select id="accountType" name="accountType" class="form-control required" >
	             	   <c:forEach items="${roleList}" var="role">
	             	      <option <c:if test="${roleIdList[0] eq role.id }"> selected="selected" </c:if>  value="${role.id}">${role.roleName}</option>
	             	   </c:forEach>
	             	</select>
		         </td>
		      </tr>
		     <tr>
		      <td  class="width-15 active"> <label class="pull-right">出生地:</label></td>
		      <td class="width-5">
					<sys:treeselect id="areaInfo" name="areaId" value="${accountInfo.areaId}"  labelName="areaId"  labelValue="${birthplace}"
					title="区域" url="/system/area/treeData" extId="${accountInfo.areaId}" cssClass="form-control"  ></sys:treeselect> 
  	         </td>
  	        
		     </tr>
	</form:form>
	

</body>
</html>