<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">

	   function toLogPage(){
		   window.location = "${ctx}/system/org/powerLog/list";
	   }
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
	<form:form id="searchForm" modelAttribute="orgPower" action="${ctx}/system/org/power/list" method="post" class="form-inline">
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
		   <table:addRow url="${ctx}/system/org/power/form" title="授权" target="orgPowerContent" label="授权"></table:addRow><!-- 增加按钮 -->
		   <button class="btn btn-white btn-sm" onclick="toLogPage()" data-toggle="tooltip" data-placement="top"/><i class="fa fa-file-text-o">授权日志</i>
			<button class="btn btn-white btn-sm" onclick="updateStatus('${ctx}/system/org/power/updateStatus/open')" data-toggle="tooltip" data-placement="top"/><i class="fa fa-file-text-o">启用</i>
			<button class="btn btn-white btn-sm" onclick="updateStatus('${ctx}/system/org/power/updateStatus/stop')" data-toggle="tooltip" data-placement="top"/><i class="fa fa-file-text-o">停用 </i>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th class="sort-column a.org_code">机构名称</th>
				<th class="sort-column ">教师数量</th>
				<th class="sort-column ">教师空间</th>
				<th class="sort-column ">机构状态</th>
				<th  class="sort-column ">学生数量</th>
				<th  class="sort-column ">授权状态</th>
				<th  class="sort-column ">授权日期</th>
				<th  class="sort-column ">授权到期日期</th>
				<th>操作人</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orgPower">
			<tr>
			    <td> <input type="checkbox" id="${orgPower.id}" class="i-checks"></td>
				<td >
					${orgPower.orgName}
				</td>
				<td >
					${orgPower.teacherCount}
				</td>
				<td >
				    ${orgPower.teacherFree}
				</td>
				<td >
					${orgPower.studentCount}
				</td>
				<td >
					${orgPower.studentFree}
				</td>
				<td >
					${orgPower.state}
				</td>
				<td >
					<fmt:formatDate value="${orgPower.createTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td >
					<fmt:formatDate value="${orgPower.endTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td >
					${orgPower.creatorName}
				</td>
				<td>
					<a href="#" onclick="openDialog('修改机构信息', '${ctx}/system/org/power/form?id=${orgPower.id}','800px', '500px', 'orgPowerContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
</body>
</html>