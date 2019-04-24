<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>班级编辑</title>
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
		$("#orgName").focus();
		validateForm = $("#inputForm").validate({
			rules: {
				className:{checkClassName : true},
			   
			},
			messages: {
				
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
	
	$.validator.addMethod("checkClassName",function(value,element,params){  
        var checkClassName = new RegExp("^[0-9a-zA-Z\u0391-\uFFE5]{1,}$");  
        return this.optional(element)||(checkClassName.test(value));  
    },"请输入正确的班级名称");  
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="classInfo" action="${ctx}/class/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">机构名称:</label></td>
		         <td class="width-35">
		         	<c:if test="${orgInfo != null }">
			         	<input type="hidden" name="orgId" class="form-control" value="${orgInfo.id }">
		         	</c:if>
		         	<c:if test="${orgInfo == null }">
			         	<input type="hidden" name="orgId" class="form-control" value="${classInfo.id }">
		         	</c:if>
		         	<input type="text" readonly="readonly" class="form-control" value="${orgInfo.orgName }">
				</td>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>学段:</label></td>
		         <td class="width-35">
		         	<c:if test="${classInfo.id == null }">
		         	<form:select path="studySection"  class="form-control required">
						<form:option value="" label="请选择"/>
						<form:options items="${fns:getChildDicByParentCode('study_section')}" itemLabel="dicName" itemValue="dicValue" htmlEscape="false"/>
					</form:select>
					</c:if>
		         	<c:if test="${classInfo.id != null }">
		         	<form:select disabled="true" path="studySection"  class="form-control">
						<form:option value="" label="请选择"/>
						<form:options items="${fns:getChildDicByParentCode('study_section')}" itemLabel="dicName" itemValue="dicValue" htmlEscape="false"/>
					</form:select>
					</c:if>
				</td>
		      </tr>
		       <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>建班年份:</label></td>
		         <td class="width-35">
		         	<c:if test="${classInfo.id != null }">
			         	<form:select disabled="true" path="classYear"  class="form-control required">
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getChildDicByParentCode('class_year')}" itemLabel="dicName" itemValue="dicValue" htmlEscape="false"/>
						</form:select>
		         	</c:if>
		         	<c:if test="${classInfo.id == null }">
			         	<form:select path="classYear"  class="form-control required" >
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getChildDicByParentCode('class_year')}" itemLabel="dicName" itemValue="dicValue" htmlEscape="false"/>
						</form:select>
		         	</c:if>
		         </td>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>班级名称:</label></td>
		         <td class="width-35">
		         	<form:input path="className" htmlEscape="false" maxlength="10" class="form-control required"/>
		         </td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">班级介绍:</label></td>
		         <td class="width-85" colspan="3">
		         	<form:textarea path="remarks" htmlEscape="false" maxlength="100" class="form-control"/>
		         </td>
		      </tr>		      

	</form:form>
</body>
</html>