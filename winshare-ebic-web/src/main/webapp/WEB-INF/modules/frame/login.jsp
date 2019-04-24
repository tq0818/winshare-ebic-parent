<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="no-cache" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/common/login/login.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/common/login/base.css" />
<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-cookie/jquery.cookie.js" type="text/javascript"></script>
<title>登录界面</title>
	<script>
		if (window.top !== window.self) {
			window.top.location = window.location;
		}
	</script>
	<script type="text/javascript">
	
	$(document).ready(function(){ 
		var rememberAccount =  $.cookie('rememberAccount');
			if($.trim(rememberAccount) != null && $.trim(rememberAccount) != 'null' && $.trim(rememberAccount) !=""){
			   $('#rememberMe').attr("checked",'true');
			   $('#loginName').val(rememberAccount);
			}
		}); 
	    
	
			// 如果在框架或在对话框中，则弹出提示并跳转到首页
			if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
				alert('未登录或登录超时。请重新登录，谢谢！');
				top.location = "${ctx}";
			}
			
			
			
			
			function checkLogin(){
				var loginName = $('#loginName').val();
				var password =  $('#password').val();
				if(loginName.length >  30 || password.length > 30){
					$('#msg').html("用户名或密码长度不能起过15个字符");
					}else{
						$('#msg').html("");
				}
		
				
			}
			
			function checkRemember(){
				if($('#rememberMe').is(":checked")){
					$('#rememberMe').val('true');
				}else{
					$('#rememberMe').val('');
				}
			}
			
			function rememberAccount(){
				if($('#rememberMe').is(":checked")){
					var account = $('#loginName').val();
					var rememberAccount =  $.cookie('rememberAccount');
					if(account != rememberAccount){
						$.cookie('rememberAccount',account);
					}
				}else{
					$('#rememberMe').val('');
					var rememberAccount =  $.cookie('rememberAccount');
					$.cookie('rememberAccount',null);
					
				}
			}
	</script>
</head>
<body onload="">
	<div class="bg">
	    <div class="logo"></div>
	    <div class="img_bg"></div>
		<div class="login">
			<form id="loginForm" action="${pageContext.request.contextPath}/login" method="post">
		        <ul>
				<h3>用户名</h3><input type="text" name="loginName" id="loginName" class="input01" value="" onkeydown="checkLogin()"  maxlength="30"/>
				<h3>密码</h3><input type="password" name="password" id="password"  class="input01" onkeydown="checkLogin()"  maxlength="30" />
		        <p class="flt" style="padding-left:26px; margin-top:-6px; margin-bottom:8px; color:#a4a4a4; line-height:17px;"><input id="rememberMe" class="flt" name="rememberMe" type="checkbox" value="" onclick="" />记住账号</p>
		        <input  onclick="submitform()" type="button" value="登录"  class="input03"/>
		       	<p id="msg" style="padding-left:26px; margin-top:-6px; margin-bottom:8px; color:#FF0000; line-height:17px;">${message}</p>
		        </ul>	        
			</form>
		</div>
	</div>

	<div class="banquan">
		<p><a href="http://www.eduyun.cn/" target="_blank">国家教育资源公共服务平台</a><a href="http://www.scedu.com.cn/" target="_blank">四川省教育资源公共服务平台</a><a href="http://zxxs.scedu.net/" target="_blank">四川省中小学学生学籍管理系统</a></p>
		<p>版权所有 四川文轩教育科技有限公司　　　ICP备案号：蜀ICP备14000302号　　　版本号：</p>
	</div>

<script type="text/javascript">
	function submitform() {
		//登录判断
		var account = document.getElementById("loginName").value;
		var password = document.getElementById("password").value;
		rememberAccount();
		$("#loginForm").submit();
	}
	// 回车提交事件
	document.onkeydown = function(e){
	    if(!e) e = window.event;
	    if((e.keyCode || e.which) == 13){
	        document.getElementById("submitForm").click();
	    }
    };
</script>
</body>
</html>