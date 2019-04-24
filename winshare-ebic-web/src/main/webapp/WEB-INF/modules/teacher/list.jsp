<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>教师管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
    function updateStatus(status){
        var url = "${ctx}/teacher/"+status;
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
        window.location = url +"?ids="+ids;
    }

    function resetPwd(){
        var url = "${ctx}/teacher/resetPwd";
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
        window.location = url +"?ids="+ids;
    }

	</script>
</head>
<body>
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="teacher" action="${ctx}/teacher/list" method="post" class="form-inline pull-left">
		<input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="orgId"  value="${teacher.orgId}" />
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>登录名：</span>
				<form:input path="loginName" value="${teacher.loginName}" style="width:15%" htmlEscape="false" maxlength="10"  class="form-control input-sm"/>
			<span style="margin-left:10px;">姓名：</span>
				<form:input path="name" value="${teacher.name}" style="width:15%" htmlEscape="false" maxlength="50"  class="form-control input-sm"/>		
			<span style="margin-left:10px;">性别：</span>
			<form:select path="sex" class="form-control" style="width:15%">
				<form:option value="" label="请选择"/>
				<form:options items="${fns:getChildDicByParentCode('sex')}" itemLabel="dicName" itemValue="dicValue" htmlEscape="false"/>
			</form:select>
			<span style="margin-left:10px;">状态：</span>
				<form:select path="accStatus"  style="width:11%;min-width:90px" class="form-control m-b">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getChildDicByParentCode('status')}" itemLabel="dicName" itemValue="dicValue" htmlEscape="false"/>
				</form:select>					
		 </div>		
	</form:form>
	<div class="pull-right" style="margin-bottom: 20px;width:20%">
		<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
		<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
	</div>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
		   <%-- <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="add()" title="添加"><i class="fa fa-plus"></i> ${label==null?'添加':label}</button> --%>
		   <c:if test="${not empty orgInfo && not empty orgInfo.id && orgInfo.orgLevel eq 'org_level_school' && orgInfo.orgStatus eq 1}">
		      <table:addRow url="${ctx}/teacher/form?orgId=${orgInfo.id}" title="教师" target="tchContent" ></table:addRow><!-- 增加按钮 -->
		   </c:if>
			   <button  class="btn btn-white btn-sm" onclick="updateStatus('enable')" ><i class="fa fa-file-text-o"></i> 启用</button>
			   <button  class="btn btn-white btn-sm" onclick="updateStatus('disable')" ><i class="fa fa-file-text-o"></i> 禁用</button>
			   <button  class="btn btn-white btn-sm" onclick="resetPwd()" ><i class="fa fa-file-text-o"></i> 重置密码</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th>账号</th>
				<th>登录名</th>
				<th>姓名</th>
				<th>所属学校</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="teacher">
			<tr>
				 <td> <input type="checkbox" id="${teacher.userId}" class="i-checks"></td>
				<td >
					${teacher.account}
				</td>
				<td>
					${teacher.loginName}
				</td>
				<td>
					${teacher.name}
				</td>
				<td >
					${teacher.orgName}
				</td>
				<td >
					<c:if test="${teacher.accStatus == 1}">启用</c:if>
					<c:if test="${teacher.accStatus == 0}">禁用</c:if>
				</td>
				<td>
					<a href="#" onclick="openDialog('修改教师信息', '${ctx}/teacher/edit?userId=${teacher.userId}','800px', '500px', 'tchContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
</body>
</html>