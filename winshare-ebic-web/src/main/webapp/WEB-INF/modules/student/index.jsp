<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>学生管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
	<script type="text/javascript">
		function refresh(){//刷新
			window.location="${ctx}/student/index";
		}
		
		function selectTreeNode(){
			
		}
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-content">
	<sys:message content="${message}"/>
	<div id="content" class="row">
		<div id="left"  style="background-color:#e7eaec" class="leftBox col-sm-1">
			<a onclick="refresh()" class="pull-right">
				<i class="fa fa-refresh"></i>
			</a>
			<div id="ztree" class="ztree leftBox-content"></div>
		</div>
		<div id="right"  class="col-sm-11  animated fadeInRight">
			<iframe id="stuContent" name="stuContent" src="${ctx}/student/list?orgId=&orgName=" width="100%" height="91%" frameborder="0"></iframe>
		</div>
	</div>
	</div>
	</div>
	<script type="text/javascript">
	    var currNode = "";
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
			view: {
		        fontCss: function (treeId, treeNode) {
		            return (treeNode.highlight) ? {color: "#A60000", "font-weight": "bold"} : {
		                    color: "#000000", "font-weight": "normal"
		                };
		        }
		    },		
			callback:{onClick:function(event, treeId, treeNode){
				    clickTreeNode(treeNode);
				}
			}
		};
		
		function refreshTree(){
			/* $.getJSON("${ctx}/system/org/treeData",function(data){
				$.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
			}); */
			$.getJSON("${ctx}/system/org/classTreeData",function(data){
				var zTree = $.fn.zTree.init($("#ztree"), setting, data);
				// 默认展开一级节点
				var nodes = zTree.getNodesByParam("level", 0);
			    for(var i=0; i<nodes.length; i++) {
				   zTree.expandNode(nodes[i], true, false, false);
			    }
			});
		}
		refreshTree();
		 
		var leftWidth = 180; // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
			mainObj.css("width","auto");
			frameObj.height(strs[0] - 60);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -60);
			$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
		}
		
	 function selectTreeNode(treeId){
		 var zTree = $.fn.zTree.getZTreeObj("ztree");
		 var treeNode = zTree.getNodeByParam("id",treeId);
		 clickTreeNode(treeNode);
	 }	
	 
	 function clickTreeNode(treeNode){
		 var id = treeNode.id == '0' ? '' :treeNode.id;
			var cid = treeNode.cId;//作为班级时的班级id
			var isClass = treeNode.isClass;//是否为班级标识(0:机构，1：班级)
			if(isClass == 1){
				$('#stuContent').attr("src","${ctx}/student/list?&orgName="+treeNode.name+"&isClass="+isClass+"&cId="+cid);
			}else{
				$('#stuContent').attr("src","${ctx}/student/list?orgId="+id+"&orgName="+treeNode.name+"&isClass="+isClass);
			}
			var zTree = $.fn.zTree.getZTreeObj("ztree");
			treeNode.highlight = true;
			if(currNode == ""){
				currNode  = treeNode;
			}else{
				currNode.highlight = false;
				zTree.updateNode(currNode);
				currNode = treeNode;
			}
			zTree.updateNode(treeNode);
	 }
	 
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>