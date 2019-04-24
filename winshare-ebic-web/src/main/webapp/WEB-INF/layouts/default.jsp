<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html style="overflow-x:auto;overflow-y:auto;">
<head>
	<title><sitemesh:title/></title>
	<%@include file="/WEB-INF/include/head.jsp" %>		
	<sitemesh:head/>
</head>
<body id="<sitemesh:getProperty property='body.id'/>" class="<sitemesh:getProperty property='body.class'/>"  style="<sitemesh:getProperty property='body.style'/>">
	<sitemesh:body/>
	<script type="text/javascript">//<!-- 无框架时，左上角显示菜单图标按钮。
		if(!(self.frameElement && self.frameElement.tagName=="IFRAME")){
			$("body").prepend("<i id=\"btnMenu\" class=\"icon-th-list\" style=\"cursor:pointer;float:right;margin:10px;\"></i><div id=\"menuContent\"></div>");
			$("#btnMenu").click(function(){

				top.layer.open({
				    type: 2, 
				    area:['300px','350px'],
				    content: 'get:${ctx}/sys/menu/treeselect;' //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
				});
			});
		}//-->
	</script>
</body>
</html>