<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>班级成员管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	var validateForm;
	var pick ;
		
	function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
	  if(validateForm.form()){
		  var myform=$('#inputForm'); //得到form对象
		  
		  //动态绑定学生和班级信息
		  var stuClsValue = JSON.stringify(pick.getValues());
          var stuClsInput=$("<input type='text' name='stuClsInfo'/>");
          stuClsInput.attr("value", stuClsValue);
          myform.append(stuClsInput);
          
          //动态绑定班级关联信息
		 /*  var clsValue = JSON.stringify(classes.getValues());
          var clsInput=$("<input type='text' name='classes'/>");
          clsInput.attr("value", clsValue);
          myform.append(clsInput); */
          var leftLen  = getJsonLength(pick.getValues().leftSelected);
          var rightLen  = getJsonLength(pick.getValues().rightSelected);
         
          if(leftLen <= 0){
        	  top.layer.alert('请选择学生!', {icon: 0, title:'警告'});
  	    	  return false;
          }else if(rightLen <=0){
        	  top.layer.alert('请选择班级!', {icon: 0, title:'警告'});
  	    	  return false;
          }
          myform.submit();
		  return true; 
	  }
	  return false;
	}
	
	$(function(){
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
		
	var students = ${students};
	var classes = ${classes};

    pick = $("#multiSelect").multiSelect({
    	 rightSearch:true, 
    	 rightSingle:true, 
    	 leftTitle:'班级学生', 
    	 rightTitle:'学校班级', 
    	 leftData: students, 
    	 rightData:classes, 
    	 multiple:true, 
    	 leftPlaceholder:'姓名/学号', 
    	 rightPlaceholder:'班级名称'
    	});
	});
	
	function getJsonLength(jsonData){
	    var jsonLength = 0;
	    for(var item in jsonData){
	       jsonLength++;
	    }
	    return jsonLength;
	} 
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="product" action="${ctx}/class/changeClass" method="post" class="form-horizontal">
		<input id="orgId" type="hidden" name="orgId" value="${orgId}"></input>
		<div id="multiSelect">
		</div>
	</form:form>
</body>
</html>