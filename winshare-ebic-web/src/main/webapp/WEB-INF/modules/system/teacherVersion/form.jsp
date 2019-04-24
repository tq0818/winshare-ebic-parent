<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	var validateForm;
	
	var updateUrl = '${ctx}/system/teaVersion/update';
	function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		var result = isNumeric($('#versionSort').val());
	    if(!result){
	    	top.layer.alert('版本序号请输入数字!', {icon: 0, title:'警告'});
	    	return false;
	    }
		$("#inputForm").submit();
	    return true;
	}
	
	$(function() {
		var id = $("input[name='id']").val();
		if($.trim(id) !=""){
			$("#inputForm").attr("action",updateUrl);
		}
	});

	function isNumeric(a){
	 
	    var   reg =  new RegExp("^[0-9]*$");
	    return(reg.test(a));
	 }
	
	</script>
</head>
<body>
	<form id="inputForm" action="${ctx}/system/teaVersion/save" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${tv.id}">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">版本号:</label></td>
		         <td class="width-35">
		         	<input id="version" name="version"  value="${tv.version }" maxlength="20">
				</td>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>更新包地址:</label></td>
		         <td class="width-35">
		         	<input id="packageUrl" name="packageUrl"  value="${tv.packageUrl}" maxlength="100">
		         </td>
		      </tr>
		       <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>程序文件地址:</label></td>
		         <td class="width-35">
		         <input id="exeUrl" name="exeUrl"  value="${tv.exeUrl}">
		         </td>
		         <td  class="width-15 active"><label class="pull-right">版本序号:</label></td>
		         <td class="width-35">
	             		<input id="versionSort" name="versionSort"  value="${tv.versionSort}" maxlength="100">
	             
		         </td>
		      </tr>
		      <tr>				
		         <td  class="width-15 active"><label class="pull-right">是否强制更新:</label></td>	         
		         <td class="width-35">
		         <select id="isForce" name="isForce">
		           <option    value="1">是</option>
		           <option  <c:if test="${tv.isForce eq 0}"> selected="selected" </c:if>   value="0">否</option>
		         </select>
		       </td>
		         <td class="width-15 active"><label class="pull-right">描述:</label></td>
		         <td class="width-35">
	             	<input id="descript" name="descript"  value="${tv.descript}">
		         </td>
		      </tr>
		    
	</form>
	
						
	</select>
</body>
</html>