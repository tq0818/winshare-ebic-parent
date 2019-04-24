<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	    function goBack(){
	    	window.location = "${ctx}/system/org/power/list";
	    }
	    
	</script>
</head>
<body>
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="orgPowerLog" action="${ctx}/system/org/powerLog/list" method="post" class="form-inline">
		<input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
		     <button class="btn btn-white btn-sm" onclick="goBack()" data-toggle="tooltip" data-placement="top"><i class="fa fa-file-text-o">返回</i>
		   </div>
	
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th class="sort-column a.org_code">机构名称</th>
				<th class="sort-column ">教师数量</th>
				<th class="sort-column ">教师空间</th>
				<th class="sort-column ">机构状态</th>
				<th  class="sort-column ">学生数量</th>
				<th  class="sort-column ">授权状态</th>
				<th  class="sort-column ">授权日期</th>
				<th  class="sort-column ">授权到期日期</th>
				<th  class="sort-column ">变更日期</th>
				<th>操作人</th>
				<th>变更人</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orgPowerLog">
			<tr>
				<td >
					${orgPowerLog.orgName}
				</td>
				<td >
					${orgPowerLog.teacherCount}
				</td>
				<td >
				    ${orgPowerLog.teacherFree}
				</td>
				<td >
					${orgPowerLog.studentCount}
				</td>
				<td >
					${orgPowerLog.studentFree}
				</td>
				<td >
					${orgPowerLog.state}
				</td>
				<td >
					<fmt:formatDate value="${orgPowerLog.createTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td >
					<fmt:formatDate value="${orgPowerLog.endTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td >
					<fmt:formatDate value="${orgPowerLog.modifyTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td >
					${orgPowerLog.creatorName}
				</td>
				<td>
				    ${orgPowerLog.updateAccountName}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
</body>
</html>