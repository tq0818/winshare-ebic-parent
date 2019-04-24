<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>学生管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	var validateForm;
	function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
	  	
	  if(validateForm.form()){
		  var myform=$('#inputForm'); //得到form对象
          myform.submit();
		  return true; 
	  }
	  return false;
	}
	
	//页面初始化
	$(function() {
		
		validateForm = $("#inputForm").validate({
			 rules : {
				loginName: {remote:{type:"post",async:false, url:"${ctx}/system/account/isExistLoginName?oldLoginName=${studentInfo.loginName}"},checkAccount:true}
			},
			messages : {
				name : {
					name : "请输入姓名"
				},
				loginName :{remote:"登录名重复"}
			
			}, 
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
		
		//日历组件
		var birthDate = {
	        elem: "#birthDate",
	        event: "focus",
	        format: "YYYY-MM-DD",
	        min: '1900-01-01',
	        max: laydate.now(),
	        istime: false,
	        istoday: false
	    };
		laydate(birthDate);
		
		getSelectArea();
	});
	
	
	function getAreaInfoByParentId(selectId,childSelectId){
		var parentId = $('#'+selectId).val();
		$.ajax({
            url : "${ctx}/system/account/getAreaInfoByParentId",
            data :{"parentId":parentId},
            success : function(data){
            	 
                var optionstring = "";  
                for (var j = 0; j < data.length; j++) {
                	var jsonObj =data[j]; 
                    optionstring += "<option value=\"" + jsonObj.id + "\" >" + jsonObj.name + "</option>";  
                }
                if($.trim(optionstring) != ""){
                	$("#"+childSelectId).empty();
                	$("#"+childSelectId).html(optionstring); 
                }
                 
            }
        })
	}
	
	function getSelectArea(){
		var districtPath = $("#areaId").find("option:selected").attr("treePath");
		$("#city option").each(function (){  
            var cityPath = $(this).attr("treePath")+$(this).val()+",";
               if(cityPath == districtPath){  
               $(this).prop('selected',true);
               return false;
            }  
         });
		
		var cityPath = $("#city").find("option:selected").attr("treePath");
		$("#province option").each(function (){  
            var provincePath = $(this).attr("treePath")+$(this).val()+",";
               if(cityPath == districtPath){  
               $(this).prop('selected',true);
               return false;
            }  
         });
	}
	
	$.validator.addMethod("checkAccount",function(value,element,params){  
        var checkAccount = new RegExp("^[0-9a-zA-Z_]{1,}$");  
        return this.optional(element)||(checkAccount.test(value));  
    },"登录名只能是包含数字和字母线划线");
	
	
	
	
	function setImagePreview(avalue) {
		var docObj=document.getElementById("doc");
		 
		var imgObjPreview=document.getElementById("preview");
		if(docObj.files &&docObj.files[0])
		{
		//火狐下，直接设img属性
		imgObjPreview.style.display = 'block';
		imgObjPreview.style.width = '150px';
		imgObjPreview.style.height = '180px'; 
		//imgObjPreview.src = docObj.files[0].getAsDataURL();
		 
		//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
		imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
		}
		else
		{

		//IE下，使用滤镜
		docObj.select();
		var imgSrc = document.selection.createRange().text;
		alert(imgSrc);
		var localImagId = document.getElementById("localImag");
		//必须设置初始大小
		localImagId.style.width = "150px";
		localImagId.style.height = "180px";
		//图片异常的捕捉，防止用户修改后缀来伪造图片
		try{
		localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
		localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
		}
		catch(e)
		{
		alert("您上传的图片格式不正确，请重新选择!");
		return false;
		}
		imgObjPreview.style.display = 'none';
		document.selection.empty();
		}
		return true;
		}
	
	function checkImgSize(){
		var maxsize = 5*1024*1024;//5M  
        var errMsg = "上传的附件文件不能超过5M！！！";  
        var tipMsg = "您的浏览器暂不支持计算上传文件的大小，确保上传文件不要超过5M，建议使用IE、FireFox、Chrome浏览器。";  
        var  browserCfg = {};  
        var ua = window.navigator.userAgent;  
        if (ua.indexOf("MSIE")>=1){  
            browserCfg.ie = true;  
        }else if(ua.indexOf("Firefox")>=1){  
            browserCfg.firefox = true;  
        }else if(ua.indexOf("Chrome")>=1){  
            browserCfg.chrome = true;  
        }  
      
            try{  
                 var obj_file = document.getElementById("doc");  
                 if(obj_file.value==""){  
                     alert("请先选择上传文件");  
                     return;  
                 }  
                 var filesize = 0;  
                 if(browserCfg.firefox || browserCfg.chrome ){  
                     filesize = obj_file.files[0].size;  
                 }else if(browserCfg.ie){  
                     var obj_img = document.getElementById('tempimg');  
                     obj_img.dynsrc=obj_file.value;  
                     filesize = obj_img.fileSize;  
                 }else{  
                     alert(tipMsg);  
                   return;  
                 }  
                 if(filesize==-1){  
                     alert(tipMsg);  
                     return;  
                 }else if(filesize>maxsize){  
                     alert(errMsg);  
                     return;  
                }
            }catch(e){  
                alert(e);  
            }  
	}
	
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="studentInfo" action="${ctx}/student/save" method="post"  class="form-horizontal" enctype="multipart/form-data">
	<sys:message content="${message}"/>
	<form:input name="orgId" type="hidden" path="orgId"/>
	<form:input name="cId" type="hidden" path="cId"/>
	<form:input name="id" type="hidden" path="id"/>
	<form:input name="accountId" type="hidden" path="accountId"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
	     <tbody>
		      <tr>
		         <td  class="width-15 active"><label class="pull-right">登录名:</label></td>
		         <td class="width-35">
		         	<form:input name="loginName" path="loginName" maxlength="10" class="form-control"/></td>
		      	 <td class="width-15 active"><label class="pull-right"><font color="red">*</font>姓名:</label></td>
		         <td class="width-35">
		         	<form:input name="name" path="name" maxlength="10" class="form-control required"/>
		         </td>
		      </tr>
		      <tr>
		       	 <td class="width-15 active"><label class="pull-right">性别:</label></td>
		         <td class="width-35">
			         <form:select path="sex" class="form-control ">
						<form:options items="${fns:getChildDicByParentCode('sex')}" itemLabel="dicName" itemValue="dicValue" htmlEscape="false"/>
			         </form:select>
		         </td>
		         <td class="width-15 active"><label class="pull-right">出生日期:</label></td>
		         <td class="width-35">
		         <input id="birthDate" name="birthDate" type="text" maxlength="30" class="laydate-icon form-control layer-date input-sm" value="<fmt:formatDate value="${student.birthDate}" pattern="yyyy-MM-dd"/>">
		         </td>
		      </tr>
		      <tr>
		       <td class="width-15 active"><label class="pull-right">出生地:</label></td>
		       <td class="width-5">
				<sys:treeselect id="studentInfo" name="areaId" value="${studentInfo.areaId}"  labelName="areaId"  labelValue="${areaName}"
					title="区域" url="/system/area/treeData" extId="${studentInfo.areaId}" cssClass="form-control"  ></sys:treeselect> 
  	           </td>
  	         
		      </tr> 
		      <tr>
		      		<td class="width-15 active"><label class="pull-right">头像:</label></td>
		      		<td class="width-35">
		      		<input id="up" name="" class="hide" accept=".png,.jpg,.jpeg" type="file">
		      		<img id="preview" class="" <c:if test="${not empty studentInfo.userAvatar }"> src="${ebicSystemUrl}/${studentInfo.userAvatar}" </c:if> width="120" height="120">
		      		<input type="file" name="userPic" id="doc" style="width:150px;" onchange="setImagePreview(),checkImgSize()" accept="image/png, image/jpeg,image/bmp">
	      			</td>
		      </tr>
	      </tbody>
	 </table>
	</form:form>
	<div style="display:none">
	  <img id="tempimg" class="" src="" width="120" height="120">
	</div>
</body>
</html>
