<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>调班</title>
	<meta name="decorator" content="default"/>
	 <script type="text/javascript">
	 var validateForm;
	 function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		 if(validateForm.form()){
			 var chk_value =[]; 
			  $('input[name="classCheck"]:checked').each(function(){ 
			  	  chk_value.push($(this).val()); 
			  }); 
			  if(chk_value.length==0){
				  alert("您还没有选择班级！");
				  return false;
			  }
			  if(chk_value.length > 1){//选择了2个班级
				  alert("请选择一个班级！");
			  	  return false;
			  }
			  $("input[name='clsId']").val(chk_value);
			  var myform=$('#inputForm'); //得到form对象
			  myform.submit();
			  return true; 
		  }
		  return false;
	 }
	 
	//页面初始化
	$(function() {
		validateForm = $("#inputForm").validate({
			submitHandler: function(form){
				loading('正在提交，请稍等...');
				form.submit();
			},
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
	}); 
	</script>
</head>
<body>
	<sys:message content="${message}"/>
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="classInfo" action="${ctx}/student/moveClass" method="post" class="form-inline pull-left">
		<input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orgId" name="orgId" type="hidden" value="${orgId}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>班级名称：</span>
				<form:input path="className" value="${classInfo.className}" style="width:15%" htmlEscape="false" maxlength="10"  class="form-control input-sm"/>
			<span style="margin-left:10px;">建班年份：</span>
			<form:select path="classYear" class="form-control" style="width:15%">
				<form:option value="" label="请选择"/>
				<form:options items="${fns:getChildDicByParentCode('class_year')}" itemLabel="dicName" itemValue="dicValue" htmlEscape="false"/>
			</form:select>
			<span style="margin-left:10px;">学段：</span>
			<form:select path="studySection" class="form-control" style="width:15%">
				<form:option value="" label="请选择"/>
				<form:options items="${fns:getChildDicByParentCode('study_section')}" itemLabel="dicName" itemValue="dicValue" htmlEscape="false"/>
			</form:select>
			<span style="margin-left:10px;">状态：</span>
			<form:select path="classStatus"  style="width:11%;min-width:90px" class="form-control m-b">
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
	<!-- 提交表单必须用inputForm，要不然重定向有问题！！！ -->
	<form:form id="inputForm" action="${ctx}/student/desideClass" method="post" >
	<input name="studentId" type="hidden" value="${studentId}">
	<input name="clsId" type="hidden" >
	<input id="orgId" name="orgId" type="hidden" value="${orgId}"/>
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox"  class="i-checks"></th>
				<th>班级名称</th>
				<th>建班年份</th>
				<th>学段</th>
				<th>所属学校</th>
				<th>状态</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="classInfo">
			<tr>
				<td> <input type="checkbox" name="classCheck" id="${classInfo.id}" value="${classInfo.id}" class="i-checks"></td>
				<td >
					${classInfo.className}
				</td>
				<td>
					${fns:getNameByCodeAndValue("class_year",classInfo.classYear)}
				</td>
				<td>
					${fns:getNameByCodeAndValue("study_section",classInfo.studySection)}
				</td>
				<td >
					${classInfo.orgInfo.orgName}
				</td>
				<td >
					${fns:getNameByCodeAndValue("status",classInfo.classStatus)}
				</td>
				<td >
					<fmt:formatDate value="${classInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</form:form>
	
	
	<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
</body>
</html>