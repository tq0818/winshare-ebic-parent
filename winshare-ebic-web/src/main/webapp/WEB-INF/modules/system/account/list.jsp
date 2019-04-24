<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>帐号管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	    function  passwordReset(){
	    	 var ids="";
	    	 var str="";
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
			  window.location = "${ctx}/system/account/passwordReset?ids="+ids;
	    }
	    
	    function updateStatus(status){
	    	 var url = "${ctx}/system/account/updateBatchStatus";
	    	 var ids="";
	    	 var str="";
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
			  window.location = url +"?ids="+ids+"&status="+status;
	    }

	    function changeType() {
            $("#account").val($("#accountType").val())
        }
	</script>
</head>
<body>
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="accountInfo" action="${ctx}/system/account/list" method="post" class="form-inline">
		<input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orgInfo" name="orgInfo.id" type="hidden" value="${orgInfoId}"/>
		<input id="account" name="accountType" type="hidden" value=""/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>关键词：</span>
				<form:input path="keyword" value="${accountInfo.keyword}" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/>
			<span>帐号类型：</span>
				<select path="accountType" id="accountType" class="form-control m-b" onchange="changeType()">
					<option value="" label="请选择"/>
					 <c:forEach items="${roleMap}" var="role">
						 <c:if test="${accountType == role.key}">
							 <option    value="${role.key}" selected>${role.value.roleName}</option>
						 </c:if>
						 <c:if test="${accountType != role.key}">
							 <option    value="${role.key}">${role.value.roleName}</option>
						 </c:if>
	             	   </c:forEach>
				</select>			
			<span>帐号状态：</span>
				<form:select path="accountStatus"  class="form-control m-b">
					<form:option value="" label="请选择"  />
					<form:options items="${fns:getChildDicByParentCode('status')}" itemLabel="dicName" itemValue="dicValue" htmlEscape="false"/>
				</form:select>					
		 </div>
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
		   <table:addRow url="${ctx}/system/account/form?orgInfoId=${orgInfoId}" title="账号" target="accountContent"></table:addRow><!-- 增加按钮 -->
		   <table:delRow url="${ctx}/system/account/delete" id="contentTable"></table:delRow><!-- 删除按钮 -->
			<button class="btn btn-white btn-sm" onclick="passwordReset()" data-toggle="tooltip" data-placement="top"><i class="fa fa-file-text-o">密码重置</i></button>

			   <button class="btn btn-white btn-sm" onclick="updateStatus('open')" data-toggle="tooltip" data-placement="top"><i class="fa fa-file-text-o">启用</i></button>

			   <button class="btn btn-white btn-sm" onclick="updateStatus('stop')" data-toggle="tooltip" data-placement="top"><i class="fa fa-file-text-o">禁用</i></button>
		   
		</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th class="sort-column a.account">帐号</th>
				<th class="sort-column a.login_name">登录名</th>
				<th class="sort-column a.name">姓名</th>
				<th class="sort-column a.account_type">帐号类型</th>
				<th class="sort-column a.account_status">状态</th>
				<th class="sort-column a.login_last_time">上次登录时间</th>
				<th class="sort-column a.login_count">登录次数</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="accountInfo">
			<tr>
				<td> <input type="checkbox" id="${accountInfo.id}" class="i-checks"></td>
				<td >
					${accountInfo.account}
				</td>
				<td >
					${accountInfo.loginName}
				</td>
				<td >
					${accountInfo.name} 
				</td>
				<td >
					<c:forEach items="${roleMap}" var="role">
						<c:if test="${role.key == accountInfo.accountType}">
							${role.value.roleName}
						</c:if>
					</c:forEach>

					<%--${roleMap[accountInfo.accountType].roleName}--%>
				</td>
				
				<td >
					${fns:getNameByCodeAndValue("status",accountInfo.accountStatus)}
				</td>
				<td >
					<fmt:formatDate value="${accountInfo.loginLastTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td >
					${accountInfo.loginCount}
				</td>
				<td>
					<a href="#" onclick="openDialog('修改', '${ctx}/system/account/form?accountId=${accountInfo.id}','800px', '500px', 'accountContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					<c:if test="${accountInfo.accountStatus eq '0'}">
						<a href="${ctx}/system/account/enable?id=${accountInfo.id}" onclick="return confirmx('确认要启用该帐号吗？', this.href)" class="btn btn-success btn-xs"> 启用</a>
					</c:if>
					<c:if test="${accountInfo.accountStatus eq '1'}">
						<a href="${ctx}/system/account/disable?id=${accountInfo.id}" onclick="return confirmx('确认要禁用该帐号吗？', this.href)" class="btn btn-warning btn-xs"> 禁用</a>
					</c:if>
					<!-- 
					<a href="#" onclick="openDialog('添加角色', '${ctx}/system/account/addRolePage?id=${accountInfo.id}','400px', '500px', 'accountContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 角色</a>
					 -->
					</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
</body>
</html>