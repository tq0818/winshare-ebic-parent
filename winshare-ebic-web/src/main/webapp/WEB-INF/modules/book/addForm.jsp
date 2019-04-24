<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>书籍管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$('body').on('click','.layui-layer-btn0',function () {
            doSubmit();
        });

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

        });


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
<form  id="inputForm" modelAttribute="studentInfo" action="${ctx}/book/saveBook" method="post"  class="form-horizontal" enctype="multipart/form-data">

	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		<tbody>
		<tr>
			<td  class="width-15 "><label class="pull-right">教材名称:</label></td>
			<td class="width-35">
				<input name="name" path="loginName" maxlength="10" class="form-control"/></td>
			<td class="width-15 "><label class="pull-right"><%--<font color="red">*</font>--%>章节知识点:</label></td>
			<td class="width-35">

			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">封面:</label></td>
			<td class="width-35">
				<input id="up" name="" class="hide" accept=".png,.jpg,.jpeg" type="file">
				<img id="preview" class=""  width="120" height="120">
				<input type="file" name="userPic" id="doc" style="width:150px;" onchange="setImagePreview(),checkImgSize()" accept="image/png, image/jpeg,image/bmp">
			</td>
		</tr>

		<tr>
			<td  class="width-15 active"><label class="pull-right">教材属性:</label></td>
			<td class="width-35">
				<select id="gradeName" name="gradeName">
					<c:forEach items="${sysDicNj}" var="dic">
						<option value="${dic.dicCode},${dic.dicName}">${dic.dicName}</option>
					</c:forEach>
				</select>
			</td>
			<td class="width-35">
				<select id="subjectName" name="subjectName">
					<c:forEach items="${sysDicXk}" var="dic">
						<option value="${dic.dicCode},${dic.dicName}">${dic.dicName}</option>
					</c:forEach>
				</select>
			</td>
			<td class="width-35">
				<select id="fasciculeName" name="fasciculeName">
					<option value="上册" selected>上册</option>
					<option value="下册">下册</option>
				</select>
			</td>
			<td class="width-35">
				<select id="publisherName" name="publisherName">
					<c:forEach items="${sysDicCbs}" var="dic">
						<option value="${dic.dicCode},${dic.dicName}" >${dic.dicName}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		</tbody>
	</table>
</form >
<div style="display:none">
	<img id="tempimg" class="" src="" width="120" height="120">
</div>
</body>
</html>
