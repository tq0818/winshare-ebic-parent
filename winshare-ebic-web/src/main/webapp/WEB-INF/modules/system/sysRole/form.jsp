<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
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
				roleIden: {remote:{type:"post",async:false,url:"${ctx}/system/sysRole/isExistsByNameIden?id=${sysRole.id}"}},
				roleName: {remote:{type:"post",async:false,url:"${ctx}/system/sysRole/isExistsByNameIden?id=${sysRole.id}"},checkRoleName:true}
			},
			messages: {
				roleIden: {remote: "角色编码已存在"},
				roleName: {remote: "角色名称已存在",checkRoleName: "角色名称只能输入中文"}
				
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
	
	$.validator.addMethod("checkRoleName",function(value,element,params){  
        var checkRoleName = new RegExp("^[\u0391-\uFFE5]{1,}$");  
        return this.optional(element)||(checkRoleName.test(value));  
    },"角色名只能是中文");  
	
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="sysRole" action="${ctx}/system/sysRole/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td class="width-15 active"><font color="red">*</font><label class="pull-right">角色名称:</label></td>
		         <td class="width-35">
		         	<form:input path="roleName"  htmlEscape="false" maxlength="10" class="form-control required"/>
				</td>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>角色编码:</label></td>
		         <td class="width-35"><form:input path="roleIden" htmlEscape="false" maxlength="10" class="form-control required"/></td>
		      </tr>
		       <tr>
		         <td  ><label >角色备注:</label></td>
		         <td>
		         	<form:textarea path="remarks" rows="10" cols="50" htmlEscape="false" maxlength="100" />
		         </td>
		        
		         
		      </tr>
		     </tbody>
		     </table> 
	</form:form>
</body>
</html>