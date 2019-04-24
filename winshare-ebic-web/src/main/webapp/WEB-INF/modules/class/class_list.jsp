<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>班级管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		
		});
		
		function deleteClass(orgId){
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
			  window.location = "${ctx}/class/deleteClass?ids="+ids+"&orgId="+orgId;
		}
		
		function updateStatus(status){
	    	 var url = "${ctx}/class/updateBatchStatus";
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
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>班级列表 </h5>
		<%--<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>--%>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="classInfo" action="${ctx}/class/pageList" method="post" class="form-inline">
		<input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orgId" name="orgId" type="hidden" value="${classInfo.orgId}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>班级名称：</span>
				<form:input style="width:15%" path="className" value="${classInfo.className}" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
			<span>建班年份：</span>
				<form:select path="classYear"  class="form-control m-b" style="width:15%" >
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getChildDicByParentCode('class_year')}" itemLabel="dicName" itemValue="dicValue" htmlEscape="false"/>
				</form:select>
			<span>学段：</span>
				<form:select path="studySection"  class="form-control m-b" style="width:15%" >
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getChildDicByParentCode('study_section')}" itemLabel="dicName" itemValue="dicValue" htmlEscape="false"/>
				</form:select>
			<span>状态：</span>
				<form:select path="classStatus"  class="form-control m-b" style="width:15%" >
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getChildDicByParentCode('status')}" itemLabel="dicName" itemValue="dicValue" htmlEscape="false"/>
				</form:select>
		 </div>
	</form:form>
	<br/>
	</div>
	</div>
	
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
		    <c:if test="${not empty orgInfo && not empty orgInfo.id && orgInfo.orgLevel eq 'org_level_school' && orgInfo.orgStatus eq 1}">
			  <table:addRow url="${ctx}/class/form?orgId=${classInfo.orgId }" title="添加班级" target="classContent"></table:addRow><!-- 增加按钮 -->
			</c:if>
			 <button  class="btn btn-white btn-sm" onclick="deleteClass('${classInfo.orgId}')" ><i class="fa fa-file-text-o"></i> 删除</button>
			  <button  class="btn btn-white btn-sm" onclick="updateStatus('open')" ><i class="fa fa-file-text-o"></i> 启用</button>
			  <button  class="btn btn-white btn-sm" onclick="updateStatus('stop')" ><i class="fa fa-file-text-o"></i> 禁用</button>
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
				<!-- <th class="sort-column class_name">班级名称</th>
				<th class="sort-column class_year">建班年份</th>
				<th class="sort-column study_section">学段</th>
				<th class="sort-column o.org_name">所属机构</th>
				<th class="sort-column class_status">状态</th>
				<th class="sort-column create_time">创建时间</th> -->
				<th >班级名称</th>
				<th >建班年份</th>
				<th >学段</th>
				<th >所属机构</th>
				<th >状态</th>
				<th >创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="entity">
			<tr>
				<td> <input type="checkbox" id="${entity.id}" class="i-checks"></td>
				<td >
					${entity.className}
				</td>
				<td >
					${entity.classYear}
				</td>
				<td >
					${fns:getNameByCodeAndValue("study_section",entity.studySection)}
				</td>
				<td >
					${entity.orgInfo.orgName }
				</td>
				<td >
					${fns:getNameByCodeAndValue("status",entity.classStatus)}
				</td>
				<td >
					<fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<a href="#" onclick="openDialog('班级成员管理','${ctx}/class/setting?id=${entity.id}','800px', '500px','classContent')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i>成员管理</a>
					
					<a href="#"  onclick="openDialog('修改编辑管理','${ctx }/class/form?id=${entity.id}','800px', '500px','classContent')" class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
					
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>