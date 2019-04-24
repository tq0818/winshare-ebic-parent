<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		var endTime = $('#endTime').val();
	    var result = validateForm.form();
	    console.log(result)
		if(result){
			endTime = endTime + " 23:59:59";
			$('#endTime').val(endTime);
			console.log(endTime)
			$("#inputForm").submit();
			return true;
		  }
		return false;
		
	}
	

	
	$(document).ready(function() {
		
		var endTime = {
		        elem: "#endTime",
		        event: "focus",
		        format: "YYYY-MM-DD",
		        min: '1900-01-01',
		        istime: false,
		        istoday: false
		    };
		
		laydate(endTime);
		var et = "<fmt:formatDate value="${orgPower.endTime}" pattern="yyyy-MM-dd"/>";
		if($.trim(et) !=""){
			$('#endTime').val(et);
		}
		
		validateForm = $("#inputForm").validate({
			rules: {
			     contractNumber:{remote:{type:"post",async:false ,url:"${ctx}/system/org/power/isExistsContractNumber?id=${orgPower.id}"},checkContractNumber : true},
			     teacherCount :{digits:true},
			     teacherFree :{digits:true,checkTeacherFree : true},
			     studentCount :{digits:true},
			     studentFree :{digits:true,checkStudentFree : true}
			},
			messages: {
				contractNumber :{remote : "合同编号已存"}
				
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

	$.validator.addMethod("checkContractNumber",function(value,element,params){  
        var contractNumber = new RegExp("^[0-9a-zA-Z_]{1,50}$");  
        return this.optional(element)||(contractNumber.test(value));  
    },"合同编号只能是包含数字和字母线划线");  
	
	$.validator.addMethod("checkTeacherFree",function(value,element,params){  
        
        return this.optional(element)||(value <= 1000);  
    },"教师空间最大1000MB");
	
    $.validator.addMethod("checkStudentFree",function(value,element,params){  
        
        return this.optional(element)||(value <= 500);  
    },"学生空间最大500MB");
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="orgPower" action="${ctx}/system/org/power/save" method="post" class="form-horizontal">
		<form:hidden path="id" value="${orgPower.id}" />
		<form:hidden path="status" value="${orgPower.status}" />
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>机构名称:</label></td>
		         <td class="width-35">
		         	<sys:treeselect id="orgPower" name="orgId" value="${orgPower.orgId}" labelName="orgName" labelValue="${orgPower.orgName}"
					title="机构" url="/system/org/treeData" extId="${orgPower.id}" cssClass="form-control required" />
				</td>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>合同编号:</label></td>
		         <td class="width-35"><form:input path="contractNumber" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		      </tr>
		      <tr>
		          <td class="width-15 active"></td>
		          <td class="width-35">			</td>
		           <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>到期时间:</label></td>
		           <td class="width-35"><form:input path="endTime"  htmlEscape="false" maxlength="50"   class="laydate-icon form-control layer-date input-sm required"  /></td>
	
		      </tr>
		       <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>教师数量:</label></td>
		         <td class="width-35">
		         	<form:input path="teacherCount" htmlEscape="false" maxlength="3" placeholder="999" class="form-control required"/>
		         </td>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>教师空间(MB):</label></td>
		         <td class="width-35">
		         	<form:input path="teacherFree" htmlEscape="false" maxlength="4"   placeholder="1000" class="form-control required" />
		         </td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>学生数量:</label></td>
		         <td class="width-35">
	             	<form:input path="studentCount" htmlEscape="false" maxlength="4"  placeholder="9999" class="form-control required"/>
	             </td>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>学生空间(MB):</label></td>
		         <td class="width-35"><form:input path="studentFree" htmlEscape="false" maxlength="3"  placeholder="500"  class="form-control required"/></td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">备注:</label></td>
		         <td class="width-35"><form:input path="remarks" htmlEscape="false" maxlength="100" class="form-control"/></td>
		         
		      </tr>	
		      
		      </tbody>
		      </table>
	</form:form>
</body>
</html>