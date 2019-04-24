<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
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
		$("#dicName").focus();
		validateForm = $("#inputForm").validate({
			rules: {
				dicCode: {remote: "${ctx}/system/dic/isExistDicCode?oldDicCode=" + encodeURIComponent('${sysDic.dicCode}')}
			},
			messages: {
				dicCode: {remote: "字典代码已存在"}
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
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="sysDic" action="${ctx}/system/dic/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">上级字典:</label></td>
		         <td class="width-35">
		         	<c:if test="${empty sysDic.id}"><sys:treeselect id="sysDic" name="parent.id" value="${sysDic.parent.id}" labelName="parent.dicName" labelValue="${sysDic.parent.dicName}"
					title="字典" url="/system/dic/treeData" extId="${sysDic.id}" cssClass="form-control" /></c:if>
					<c:if test="${not empty sysDic.id}"><form:input path="parent.dicName" disabled="true" htmlEscape="false" maxlength="10" class="form-control "/></c:if>
				</td>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>字典名称:</label></td>
		         <td class="width-35"><form:input path="dicName" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		      </tr>
		       <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>字典代码:</label></td>
		         <td class="width-35">
		         	<input id="oldDicCode" name="oldDicCode" type="hidden" value="${sysDic.dicCode}">
		         	<form:input path="dicCode" htmlEscape="false" maxlength="50" class="form-control required"/>
		         </td>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>字典值:</label></td>
		         <td class="width-35"><form:input path="dicValue" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>字典序号:</label></td>
		         <td class="width-35"><form:input path="dicSort" htmlEscape="false" maxlength="50"  class="form-control digits required"/></td>
		         <td  class="width-15 active"><label class="pull-right"></label></td>
		         <td class="width-35"></td>
		      </tr>
	</form:form>
</body>
</html>