<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>学生管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		  var url = window.location.href;
		  var uri = url.split("desideClassId");
	      if(uri.length > 1){
	    	  var subUri = uri[1].split("&");
	    	  var classId = subUri[0].replace("=","");
	    	  window.parent.selectTreeNode("class_"+classId);
	      }
	});
	  
	
	    function downloadStencil(){
	    	window.location = "${ctx}/student_stencil.xls";
	    }
	    
	    function uploadExcel(){
	        var max = 5*1024*1024;
            var studentExcel = document.getElementById("studentExcel");
            var filesize = studentExcel.files[0].size;
            console.log(filesize);
            if(filesize >max){
                alert("文件大小超过5MB，不能上传。");
			}
            return;
	    	$('#uploadForm').submit();
	    }
	    
	    function selectFile(){
	    	$("input[name='studentExcel']").click();
	    }

    function updateStatus(status){
        var url = "${ctx}/student/"+status;
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
        var url = "${ctx}/student/resetPwd";
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
	<form:form id="searchForm" modelAttribute="studentInfo" action="${ctx}/student/list" method="post" class="form-inline pull-left">
		<input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="cId" name="cId" type="hidden" value="${studentInfo.cId}"/>
		<input id="orgId" name="orgId" type="hidden" value="${orgId}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>登录名：</span>
				<form:input path="loginName" value="${studentInfo.loginName}" style="width:15%" htmlEscape="false" maxlength="10"  class="form-control input-sm"/>
			<span style="margin-left:10px;">姓名：</span>
				<form:input path="name" value="${studentInfo.name}" style="width:15%" htmlEscape="false" maxlength="10"  class="form-control input-sm"/>		
			<span style="margin-left:10px;">性别：</span>
			<form:select path="sex" class="form-control" style="width:11%">
				<form:option value="" label="请选择"/>
				<form:options items="${fns:getChildDicByParentCode('sex')}" itemLabel="dicName" itemValue="dicValue" htmlEscape="false"/>
			</form:select>
			
			<span>班级：</span>
				<form:input path="className" value="${studentInfo.className}" style="width:15%" htmlEscape="false" maxlength="10"  class="form-control input-sm"/>
			<span style="margin-left:10px;">状态：</span>
			<form:select path="status"  style="width:11%;min-width:90px" class="form-control m-b">
				<form:option value="" label="请选择"/>
				<form:options items="${fns:getChildDicByParentCode('status')}" itemLabel="dicName" itemValue="dicValue" htmlEscape="false"/>
			</form:select>					
		 </div>		
	</form:form>
	
	<br/>
	</div>
	
	
	</div>
	<br/>
	<form id="uploadForm" action="${ctx}/student/importExcel" method="post" enctype="multipart/form-data" style="display:none">
	    <input type="file" id="studentExcel" name="studentExcel" onchange="uploadExcel()"  accept="application/vnd.ms-excel">
	    <input type="text" name="cId" value="${cId}">
	    <input type="text" name="orgId" value="${orgId}">
	</form>
	
	<div class="pull-right" style="margin-bottom: 20px;width:20%">
		<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
		<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
	</div>
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<c:if test="${isClass eq '1'}">
			   <table:addRow url="${ctx}/student/form?orgId=${orgId}&cId=${cId}" title="学生" target="stuContent" ></table:addRow><!-- 增加按钮 -->
			   <button id="btnImport" class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" title="导入" onclick="selectFile()"><i class="fa fa-folder-open-o"></i> 导入</button>
			   <button id="download" class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" title="模版下载"  onclick="downloadStencil()" ><i class="fa fa-folder-open-o"></i>模版下载</button>
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
				<th>所属班级</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="student">
			<tr>
				<th> <input type="checkbox" id="${student.id}" class="i-checks"></th>
				<td >
					${student.account}
				</td>
				<td>
					${student.loginName}
				</td>
				<td>
					${student.name}
				</td>
				<td >
					${student.orgName}
				</td>
				<td >
					${student.className}
				</td>
				<td >
					${fns:getNameByCodeAndValue("status",student.status)}
				</td>
				<td>
					<a href="#" onclick="openDialog('修改学生信息', '${ctx}/student/form?id=${student.id}','800px', '500px', 'stuContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					<a href="#" onclick="openDialog('调班', '${ctx}/student/moveClass?id=${student.id}&classId=${student.classId}','1000px', '500px', 'stuContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 调班</a>
					<%--<c:if test="${student.status eq '0'}">
						<a href="${ctx}/student/enable?id=${student.id}" onclick="return confirmx('确认要启用该用户吗？', this.href)" class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 启用</a>
					</c:if>
					<c:if test="${student.status eq '1'}">
						<a href="${ctx}/student/disable?id=${student.id}" onclick="return confirmx('确认要停用该用户吗？', this.href)" class="btn btn-warning btn-xs"><i class="fa fa-edit"></i> 停用</a>
					</c:if>
					<a href="${ctx}/student/resetPwd?id=${student.id}" onclick="return confirmx('是否重置所选用户密码？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-edit"></i> 重置密码</a>
				--%></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
</body>
</html>