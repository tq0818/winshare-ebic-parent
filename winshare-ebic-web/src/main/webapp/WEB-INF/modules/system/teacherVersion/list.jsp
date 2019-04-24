<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>帐号管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	
	
	</script>
</head>
<body>
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form id="searchForm" modelAttribute="" action="${ctx}/system/teaVersion/list" method="post" class="form-inline">
		<input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>版本号：</span>
				<input  id="version" name="version" value="" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
				
			<span>更新包地址：</span>
				<input  id="packageUrl"  name="packageUrl" value="" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
			
			<span>程序文件地址：</span>
				<input id="exeUrl"  name="exeUrl" value="" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
						
		 </div>
	</form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
		   <table:addRow url="${ctx}/system/teaVersion/form?" title="教师端版本" target="accountContent"></table:addRow><!-- 增加按钮 -->
		   <table:delRow url="${ctx}/system/teaVersion/del" id="contentTable"></table:delRow><!-- 删除按钮 -->
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
				<th class="sort-column a.version">版本号</th>
				<th class="sort-column a.packageUrl">更新包地址</th>
				<th class="sort-column a.exeUrl">程序文件地址</th>
				<th class="sort-column a.versionSort">版本序号</th>
				<th class="sort-column a.isForce">是否强制更新</th>
				<th class="sort-column a.descipt">描述</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tv">
			<tr>
				<td> <input type="checkbox" id="${tv.id}" class="i-checks"></td>
				<td >
					${tv.version}
				</td>
				<td >
					${tv.packageUrl}
				</td>
				<td >
					${tv.exeUrl} 
				</td>
				<td >
					${tv.versionSort}
				</td>
				<td >
					<c:if test="${tv.isForce eq 1}">是</c:if><c:if test="${tv.isForce eq 0}">否</c:if>
				</td>
				<td >
					${tv.descript}
				</td>
				
				<td>
					<a href="#" onclick="openDialog('修改', '${ctx}/system/teaVersion/form?id=${tv.id}','800px', '500px', 'accountContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					
			   </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
</body>
</html>