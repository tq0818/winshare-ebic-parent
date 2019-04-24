<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/include/treeview.jsp" %>
	<script type="text/javascript">
		$(function () {
			if($("#messageBox").hasClass('alert-success')){
			    $("#left").find("a").trigger(('click'));
			}
        })
	</script>
	<script type="text/javascript">
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
		  window.location = url+"?ids="+ids;
	}
	

	</script>
</head>
<body>
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="orgInfo" action="${ctx}/system/org/list" method="post" class="form-inline">
		<input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="parentIds" name="parentIds" type="hidden" value="${parentIds}"/>
		<input id="parentId" name="orgId" type="hidden" value="${id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>机构名称：</span>
				<form:input path="orgName" value="${orgInfo.orgName}" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
			<span>机构层级：</span>
				<form:select path="orgLevel"  class="form-control m-b">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getChildDicByParentCode('org_level')}" itemLabel="dicName" itemValue="dicCode" htmlEscape="false"/>
				</form:select>			
			<span>机构状态：</span>
				<form:select path="orgStatus"  class="form-control m-b">
					<form:option value="" label="请选择"/>
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
			<c:if test="${not empty orgInfo && not empty orgInfo.id && orgInfo.orgLevel != 'org_level_school'}">
				<table:addRow url="${ctx}/system/org/form?parent.id=${orgInfo.id}" title="添加机构" target="orgContent"></table:addRow><!-- 增加按钮 -->
			</c:if>

		   <table:delRow url="${ctx}/system/org/delete" id="contentTable"></table:delRow><!-- 删除按钮 -->
			<button class="btn btn-white btn-sm" onclick="updateStatus('${ctx}/system/org/updateStatus/open')" data-toggle="tooltip" data-placement="top"><i class="fa fa-file-text-o">启用</i></button>
			<button class="btn btn-white btn-sm" onclick="updateStatus('${ctx}/system/org/updateStatus/stop')" data-toggle="tooltip" data-placement="top"><i class="fa fa-file-text-o">停用 </i></button>

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
				<th class="sort-column a.org_code">机构代码</th>
				<th class="sort-column a.org_name">机构名称</th>
				<th class="sort-column a.org_level">机构层级</th>
				<th class="sort-column a.org_status">机构状态</th>
				<th  class="sort-column parent_name">上级机构</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orgInfo">
			<tr>
				<td> <input type="checkbox" id="${orgInfo.id}" class="i-checks"></td>
				<td >
					${orgInfo.orgCode}
				</td>
				<td >
					${orgInfo.orgName}
				</td>
				<td >
					${fns:getDicByCode(orgInfo.orgLevel).dicName} 
				</td>
				<td >
					${fns:getNameByCodeAndValue("status",orgInfo.orgStatus)}
				</td>
				<td >
					${orgInfo.parent.orgName}
				</td>
				<td>
				   <!-- 
					<a href="#" onclick="openDialogView('查看机构信息','${ctx}/system/org/form?id=${orgInfo.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					 -->
					<a href="#" onclick="openDialog('修改机构信息', '${ctx}/system/org/form?id=${orgInfo.id}','800px', '500px', 'orgContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
</body>
</html>