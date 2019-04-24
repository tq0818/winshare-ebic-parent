<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		
	});
	
	function updateStatus(url){
		var str="";
		  var ids="";
		  $("#contentTable tbody tr td input.i-checks:checkbox").each(function(){
		    if(true == $(this).is(':checked')){
		      str+=$(this).attr("id")+",";
		    }
		  });
		  if(str.substr(str.length-1)== ','){
		    ids = str.substr(0,str.length-1);
		  }
		  if(ids == ""){
			top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
			return;
		  }
		  window.location = url+"?id="+ids;
	}
	</script>
</head>
<body>
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="sysRole" action="${ctx}/system/sysRole/list" method="post" class="form-inline">
		<input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>角色名称：</span>
				<form:input path="roleName" value="${sysRole.roleName}" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/>
					
		 </div>		
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
		   <table:addRow url="${ctx}/system/sysRole/form" title="添加" target="sysRoleContent"></table:addRow><!-- 增加按钮 -->
		   <%--<shiro:hasPermission name="sys:role:operation_start">  --%>
		       <button class="btn btn-white btn-sm" onclick="updateStatus('${ctx}/system/sysRole/update/open')" data-toggle="tooltip" data-placement="top"><i class="fa fa-file-text-o">启用</i>
		    <%--</shiro:hasPermission>
		     <shiro:hasPermission name="sys:role:operation_stop">--%>
		       <button class="btn btn-white btn-sm" onclick="updateStatus('${ctx}/system/sysRole/update/stop')" data-toggle="tooltip" data-placement="top"><i class="fa fa-file-text-o">停用 </i>
		    <%-- </shiro:hasPermission>   --%>
		   
		   
		</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th class="sort-column a.role_iden">角色编码</th>
				<th class="sort-column a.role_name">角色名称</th>
		        <th class="sort-column a.create_time">创建日期</th>
				<th class="sort-column a.modify_time">修改日期</th>
				<th class="sort-column a.status">角色状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysRole">
			<tr>
				<td> <input type="checkbox" id="${sysRole.id}" class="i-checks"></td>
				<td >
					${sysRole.roleIden}
				</td>
				<td >
					${sysRole.roleName}
				</td>
			    <td >
					
					<fmt:formatDate value="${sysRole.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>					
				<td >
					<fmt:formatDate value="${sysRole.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td >
					<c:if test="${sysRole.status eq 1}">启用</c:if><c:if test="${sysRole.status eq 0}">停用</c:if>
				</td>
				<td>
					<a href="#" onclick="openDialog('修改','${ctx}/system/sysRole/form?id=${sysRole.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 修改</a>
					<a href="${ctx}/system/sysRole/delete?ids=${sysRole.id}" onclick="return confirm('确定删除该角色?');" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 删除</a>
				    <a href="#" onclick="openDialog('授权', '${ctx}/system/sysRole/authorization?roleId=${sysRole.id}','400px', '500px', 'sysRoleContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 授权</a>
				    
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
</body>
</html>