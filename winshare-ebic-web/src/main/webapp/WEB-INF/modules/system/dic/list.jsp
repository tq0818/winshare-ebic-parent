<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="sysDic" action="${ctx}/system/dic/list" method="post" class="form-inline">
		<input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>字典名称：</span>
				<form:input path="dicName" value="${sysDic.dicName}" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>			
			<span>字典编码：</span>
				<form:input path="dicCode" value="${sysDic.dicCode}" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
		 </div>		
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
		   <table:addRow url="${ctx}/system/dic/form?parent.id=${sysDic.id}" title="添加字典" target="dicContent"></table:addRow><!-- 增加按钮 -->
		   <table:delRow url="${ctx}/system/dic/delete" id="contentTable"></table:delRow><!-- 删除按钮 -->
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
				<th class="sort-column a.dic_name">字典名称</th>
				<th class="sort-column a.dic_code">字典编码</th>
				<th class="sort-column a.dic_value">字典值</th>
				<th class="sort-column a.dic_sort">字典序号</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysDic">
			<tr>
				<td> <input type="checkbox" id="${sysDic.id}" class="i-checks"></td>
				<td >
					${sysDic.dicName}
				</td>
				<td >
					${sysDic.dicCode}
				</td>
				<td >
					${sysDic.dicValue} 
				</td>
				<td >
					${sysDic.dicSort}
				</td>
				<td>
					<a href="#" onclick="openDialogView('查看机构信息','${ctx}/system/dic/form?id=${sysDic.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					<a href="#" onclick="openDialog('修改机构信息', '${ctx}/system/dic/form?id=${sysDic.id}','800px', '500px', 'dicContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
</body>
</html>