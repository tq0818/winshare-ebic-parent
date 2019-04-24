<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		//日历组件
		var startTime = {
	        elem: "#startTime",
	        event: "focus",
	        format: "YYYY-MM-DD",
	        min: '1900-01-01',
	        max: laydate.now(),
	        istime: false,
	        istoday: false,
	        choose: function(datas){  
	             endTime.min = datas; //开始日选好后，重置结束日的最小日期  
	          
	        }  
	    };
		var endTime = {
		        elem: "#endTime",
		        event: "focus",
		        format: "YYYY-MM-DD",
		        min: '1900-01-01',
		        max: laydate.now(),
		        istime: false,
		        istoday: false,
		        choose: function(datas){  
		             startTime.max = datas; //开始日选好后，重置结束日的最小日期  
		          
		       }  
		    };
		
		laydate(startTime);
		laydate(endTime);
	});

	
	function formSubmit(){
		var startTime = $('#startTime').val();
		if($.trim(startTime) != ""){
			startTime = startTime + " 00:00:00";
			$('#startTime').val(startTime);
		}
		var endTime = $('#endTime').val();
		if($.trim(endTime) != ""){
			endTime = endTime + " 23:59:59";
			$('#endTime').val(endTime);
		}
		$('#searchForm').submit();
	}
	
	</script>
	
</head>

<body>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="sysLog" action="${ctx}/system/sysLog/list" method="post" class="form-inline">
		<input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>帐号：</span>
				<form:input path="account" value="${sysLog.account}" htmlEscape="false" maxlength="10"  class=" form-control input-sm" style="width:150px;" />
			<span>姓名：</span>
				<form:input path="userName" value="${sysLog.userName}" htmlEscape="false" maxlength="10"  class=" form-control input-sm" style="width:150px;" />	
		   <span>登录时间:</span>
		        <input id="startTime" name="startTime" type="text" maxlength="30" class="laydate-icon form-control layer-date input-sm" value='${fn:split(sysLog.startTime," ")[0]}' style="width:140px;" >
		   --
		        <input id="endTime" name="endTime" type="text" maxlength="30" class="laydate-icon form-control layer-date input-sm" value='${fn:split(sysLog.endTime," ")[0]}' style="width:140px;" >  
		 </div>		
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
	
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="formSubmit()" ><i class="fa fa-search"></i> 查询</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th class="sort-column a.org_code">帐号</th>
				<th class="sort-column a.org_name">姓名</th>
				<th  class="sort-column parent_name">所属机构</th>
				<th  class="sort-column parent_name">登录时间</th>
				
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysLog">
			<tr>
				<td >
					${sysLog.account}
				</td>
				<td >
					${sysLog.userName}
				</td>
								
				<td >
					${sysLog.orgName}
				</td>
				<td >
					<fmt:formatDate value="${sysLog.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				
			
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
</body>
</html>