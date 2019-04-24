<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		//parent.window.refreshTree();
	});
	
	function findChildren(){
		var parentId  = $('#id').val();
		var childName  = $('#name').val();
		if($.trim(parentId) !=""){
			$('#searchForm').submit();
		}
	}
	</script>
</head>
<body>
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm"  action="${ctx}/system/area/list" method="post" class="form-inline">
		<input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="ids" name="id" type="hidden" value="${id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>区域名称：</span>
				<input id="name"  name="name" value="${childArea.name}" htmlEscape="false" maxlength="10" />
                <input type="hidden" id="id" name="id" value="${areaInfo.id}" htmlEscape="false"  />	
                	 </div>		
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
		   <table:addRow url="${ctx}/system/area/form?parent.id=${areaInfo.id}" title="添加区域" target="areaContent"></table:addRow><!-- 增加按钮 -->
		   <table:delRow url="${ctx}/system/area/delete" id="contentTable"></table:delRow><!-- 删除按钮 -->
		</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="findChildren()" ><i class="fa fa-search"></i> 查询</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th class="sort-column a.org_code">区域代码</th>
				<th class="sort-column a.org_name">区域名称</th>
		
				<th  class="sort-column parent_name">上级区域</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="areaInfo">
			<tr>
				<td> <input type="checkbox" id="${areaInfo.id}" class="i-checks"></td>
				<td >
					${areaInfo.code}
				</td>
				<td >
					${areaInfo.name}
				</td>
								
				<td >
					${areaInfo.parent.name}
				</td>
				<td>
				    <!--  
					<a href="#" onclick="openDialogView('查看区域信息','${ctx}/system/area/form?id=${areaInfo.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					-->
					<a href="#" onclick="openDialog('修改区域信息', '${ctx}/system/area/form?id=${areaInfo.id}','800px', '500px', 'areaContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
</body>
</html>