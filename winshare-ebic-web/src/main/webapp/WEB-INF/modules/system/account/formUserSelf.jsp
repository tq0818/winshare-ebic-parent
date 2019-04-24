<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>帐户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	var validateForm;
	function doSubmit(){
		var loginName = $("input[name='loginName']").val();
		var oldPassword = $("input[name='password']").val();
		var newPassword = $("input[name='newPassword']").val();
		var result = false;
		if($.trim(loginName) =='' && ($.trim(oldPassword) == '' || $.trim(newPassword) =='')){
			top.layer.alert('帐号或密码不能同时为空', {icon: 0, title:'警告'});
			return false;
		}
	    var  reg =  new RegExp("^[0-9a-zA-Z_]{6,15}$");
		if($.trim(oldPassword) !="" && !reg.test(oldPassword)){
			top.layer.alert('密码限制6-15个字符。密码为字母 数字 下划线，以及三者的组合', {icon: 0, title:'警告'});
			return false;
		}else if($.trim(newPassword) !="" && !reg.test(newPassword)){
			top.layer.alert('密码限制6-15个字符。密码为字母 数字 下划线，以及三者的组合', {icon: 0, title:'警告'});
			return false;
		}
		$.ajax({
            url : "${ctx}/system/account/updateLoginInfo",
            data :{"loginName":loginName,"oldPassword":oldPassword,"newPassword":newPassword},
            async:false,
            success : function(data){
            	 if(data =='success'){
            		 top.layer.alert('修改成功', {icon: 0, title:'警告'});
            		 result = true;
            	 }else{
            		 top.layer.alert('登录名重复或旧密码不正确', {icon: 0, title:'警告'});
            	 }
            	
                 
            },
            error:function(e){
            	top.layer.alert('登录名重复或旧密码不正确', {icon: 0, title:'警告'});
            }
        })
    	return result;

	}
	


	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="accountInfo" action="${ctx}/system/account/updateLoginInfo" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		        
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>登录名:</label></td>
		         <td class="width-35">
		         	<input name="loginName" htmlEscape="false" maxlength="30" />
		         </td>
		         <td>登录名限制6-30个字符。为字母、数字、字母数字组合、字母下划线数字组合</td>
		      </tr>
		       <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>旧密码:</label></td>
		         <td class="width-35"><input name="password" htmlEscape="false" maxlength="15" /></td>
		         <td>密码限制6-15个字符。密码为字母 数字 下划线，以及三者的组合 </td>
		        
		      </tr>
		      <tr>				
		         <td  class="width-15 active"><label class="pull-right">新密码:</label></td>	         
		         <td class="width-35"><input  name="newPassword" type="text" maxlength="15" 
											 /></td>
		        <td>密码限制6-15个字符。密码为字母 数字 下划线，以及三者的组合 </td>
		      </tr>
		    
	</form:form>
	
						
	</select>
</body>
</html>