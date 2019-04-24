<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>帐户管理</title>
	 <%@include file="/WEB-INF/include/treeview.jsp" %>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	


	</script>
</head>
<body>
<div id="roleContent">
	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <c:forEach items="${roleList}" var="role">
		      <tr>
		        <td style="text-align: right;"><input type="checkbox" id="role${role.id}" name="roleCheck" value="${role.id}"></td> <td> <label >${role.roleName}</label></td>
		      </tr>
		      </c:forEach>
		    </tbody>
    </table>		      
		   
</div>

	<form id="inputForm" action="${ctx}/system/account/addRole" method="post" target="accountContent">
		<input type="hidden" id="roleIds" name="roleIds">
		<input type="hidden" id="accountId" name="accountId" value="${accountId}">
	</form>
	
	<script type="text/javascript">
	var roleIdStr = "${roleIds}";

	function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		var roleIds = "";
		$(":checkbox[name='roleCheck']").each(function(){
			if($(this).prop('checked')){
				roleIds = roleIds + $(this).val()+",";
			}
			
		});
	    if($.trim(roleIds) ==""){
	    	top.layer.alert('请至少选择一个角色!', {icon: 0, title:'警告'});
	    	return;
	    }else if($.trim($('#accountId').val()) == ""){
	    	return;
	    }
	    roleIds = roleIds.substring(0,roleIds.length - 1);
	    $('#roleIds').val(roleIds);
		$("#inputForm").submit();
		return true;
	}

	$(document).ready(function() {
		if($.trim(roleIdStr) !=""){
			var roleArray = roleIdStr.split(",");
			for(i = 0;i < roleArray.length;i++){
				$('#role'+roleArray[i]).attr("checked",true);
			}
		}
	});
	</script>
</body>

</html>