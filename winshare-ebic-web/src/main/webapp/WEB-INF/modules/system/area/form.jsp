<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
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
		validateForm = $("#inputForm").validate({
			rules: {
				code: {remote:{async:false,url:"${ctx}/system/area/isExistsByAreaCode?id=${areaInfo.id}"}}
			},
			messages: {
				code: {remote: "区域编码已存在"}
				
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
	<form:form id="inputForm" modelAttribute="areaInfo" action="${ctx}/system/area/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">上级区域:</label></td>
		         <td class="width-35">
		         	<c:if test="${empty areaInfo.id}"><sys:treeselect id="areaInfo" name="parent.id" value="${areaInfo.parent.id}"  labelName="parent.name"  labelValue="${areaInfo.parent.name}"
					title="区域" url="/system/area/treeData" extId="${areaInfo.id}" cssClass="form-control"  ></sys:treeselect> </c:if>
					<c:if test="${not empty areaInfo.id}"><form:input path="parent.name" disabled="true" htmlEscape="false" maxlength="10" class="form-control "/></c:if>
				</td>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>区域简称:</label></td>
		         <td class="width-35"><form:input path="name" htmlEscape="false" maxlength="10" class="form-control required"/></td>
		      </tr>
		       <tr>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>区域全称:</label></td>
		         <td class="width-35">
		         	<input id="oldAreaFullName" name="oldAreaFullName" type="hidden" value="${areaInfo.fullName}">
		         	<form:input path="fullName" htmlEscape="false" maxlength="15" class="form-control required"/>
		         </td>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>区域编码:</label></td>
		         <td class="width-35">
		         	<input id="oldAreaCode" name="oldAreaCode" type="hidden" value="${areaInfo.code}">
		         	<form:input path="code" htmlEscape="false" maxlength="15" class="form-control required"/>
		         </td>
		         
		      </tr>
		      
	</form:form>
</body>
</html>