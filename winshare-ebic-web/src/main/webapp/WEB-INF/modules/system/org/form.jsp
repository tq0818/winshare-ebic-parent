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
		$("#orgName").focus();
		validateForm = $("#inputForm").validate({
			rules: {
				orgCode: {remote: {async:false, url: "${ctx}/system/org/isExistOrgCode?oldOrgCode=" + encodeURIComponent('${orgInfo.orgCode}')},checkOrgCode:true},
			    orgPhone:{checkPhone:true}
			},
			messages: {
				orgCode: {remote: "机构代码已存在"}
				
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
	
	$.validator.addMethod("checkOrgCode",function(value,element,params){  
        var checkOrgCode = new RegExp("^[0-9a-zA-Z]{1,10}$");  
        return this.optional(element)||(checkOrgCode.test(value));  
    },"机构代码只能包含数字和字母");  

	
	$.validator.addMethod("checkPhone",function(value,element,params){  
        var checkPhone = new RegExp("^[0-9]{1,11}$");  
        return this.optional(element)||(checkPhone.test(value));  
    },"电话只能是11位数字");  

	
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="orgInfo" action="${ctx}/system/org/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">上级机构:</label></td>
		         <td class="width-35">
		         	<c:if test="${empty orgInfo.id}"><sys:treeselect id="orgInfo" name="parent.id" value="${orgInfo.parent.id}" labelName="parent.orgName" labelValue="${orgInfo.parent.orgName}"
					title="机构" url="/system/org/treeData" extId="${orgInfo.id}" cssClass="form-control" /></c:if>
					<c:if test="${not empty orgInfo.id}"><form:input path="parent.orgName" disabled="true" htmlEscape="false" maxlength="10" class="form-control "/></c:if>
				</td>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>机构名称:</label></td>
		         <td class="width-35"><form:input path="orgName" htmlEscape="false" maxlength="10" class="form-control required"  />
		         </td>
		      </tr>
		       <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>机构代码:</label></td>
		         <td class="width-35">
		         	<input id="oldOrgCode" name="oldOrgCode" type="hidden" value="${orgInfo.orgCode}">
		         	<form:input path="orgCode" htmlEscape="false" maxlength="10" class="form-control required"/>
		         </td>
		         <td  class="width-15 active"><label class="pull-right">机构编码:</label></td>
		         <td class="width-35">
		         	<input id="oldOrgNumber" name="oldOrgNumber" type="hidden" value="${orgInfo.orgNumber}">
		         	<form:input path="orgNumber" htmlEscape="false" maxlength="50" class="form-control required" disabled="true" />
		         </td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>机构层级:</label></td>
		         <td class="width-35">
	             	<form:select path="orgLevel" class="form-control ">
						<form:options items="${fns:getChildDicByParentCode('org_level')}" itemLabel="dicName" itemValue="dicValue" htmlEscape="false"/>
					</form:select>
		         </td>
		         <td  class="width-15 active"><label class="pull-right">机构联系人:</label></td>
		         <td class="width-35"><form:input path="orgContact" htmlEscape="false" maxlength="50" class="form-control"/></td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">机构联系电话:</label></td>
		         <td class="width-35"><form:input path="orgPhone" htmlEscape="false" maxlength="11" class="form-control"/></td>
		         <td  class="width-15 active"><label class="pull-right">联系人邮箱:</label></td>
		         <td class="width-35"><form:input path="orgEmail" htmlEscape="false" maxlength="30" class="form-control"/></td>
		      </tr>	
		      <tr>
		         <td class="width-15 active"><label class="pull-right">区域:</label></td>
		         <td class="width-35">
		         <sys:treeselect id="orgInfoArea" name="areaId" value="${orgInfo.areaId}"  labelName="areaId"  labelValue="${areaName}"
					title="区域" url="/system/area/treeData" extId="${orgInfo.areaId}" cssClass="form-control"  ></sys:treeselect> 
		         </td>
		         <td  class="width-15 active"><label class="pull-right">机构地址:</label></td>
		         <td class="width-35"><form:input path="address" htmlEscape="false" maxlength="30" class="form-control"/></td>
		      </tr>
	</form:form>
</body>
</html>