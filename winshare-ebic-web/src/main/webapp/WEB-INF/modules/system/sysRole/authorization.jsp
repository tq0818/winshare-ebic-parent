<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<%@ include file="/WEB-INF/include/head.jsp"%>
<html>
<head>
	<title>角色授权</title>
	<meta name="decorator" content="default"/>
     <%@include file="/WEB-INF/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
	
	
</head>
<body>
   <div id="content" class="row">
		<div id="left"  style="background-color:#e7eaec" class="leftBox col-sm-1">
			<a onclick="refreshTree()" class="pull-right">
				<i class="fa fa-refresh"></i>
			</a>
			<div id="ztree" class="ztree leftBox-content"></div>
</div>
</div>

	<form id="inputForm" action="${ctx}/system/sysRole/authorize" method="post" target="sysRoleContent">
		<input type="hidden" id="menuIds" name="menuIds">
		<input type="hidden" id="roleId" name="roleId" value="${roleId}">
		<input type="hidden" id="operationIds" name="operationIds">
	</form>
	
<script type="text/javascript">

var menuIdStr = "${menuIds}";
var operationArray = ${operations};
var setting = {check:{enable:true,chkboxType:{"Y":"ps","N":"ps"}},data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
		callback:{
			onCheck:onCheck
		}
	};
	
	function refreshTree(){
		$.getJSON("${ctx}/system/sysRole/treeData",function(data){
			$.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
			if($.trim(menuIdStr) !=""){
				var zTree = $.fn.zTree.getZTreeObj("ztree");
				var nodes = zTree.getCheckedNodes(false);
				var menuArray = menuIdStr.split(",");
				for(i = 0;i < menuArray.length;i++){
					var node = zTree.getNodeByParam("id",menuArray[i]);
					var nodeChildren = node.children;
					zTree.checkNode(node, true);
										
				}
			}
			if(operationArray.length >0){
				var zTree = $.fn.zTree.getZTreeObj("ztree");
				 $.each(operationArray, function (n,oper ) {
						var parentNode = zTree.getNodeByParam("id",oper.menuId);
						var newNode = { id: "oper_"+oper.id, name : oper.operationName};
						zTree.addNodes(parentNode, newNode);
						if(oper.roleRelaId != null){
							var newNode2 = zTree.getNodeByParam("id","oper_"+oper.id);
							zTree.checkNode(newNode2, true);
						}
						
		           });
			
			}
			
		});
	}
	refreshTree();

	

	var leftWidth = 360; // 左侧窗口大小
	
	var menuIds = "";
	
	var operationIds = "";
	function onCheck(e,treeId,treeNode){
		menuIds = "";
		operationIds = "";
        var treeObj=$.fn.zTree.getZTreeObj("ztree"),
        nodes=treeObj.getCheckedNodes(true),
        ids="";
        var checkId = new RegExp("^oper");  
        for(var i=0;i<nodes.length;i++){
           if(!checkId.test(nodes[i].id)){
        	   ids+=nodes[i].id + ",";
           }else{
        	   operationIds +=nodes[i].id.replace("oper_","") + ",";
           }	
           
        
        }
       menuIds = ids;
       console.log(operationIds)
	}

function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
    if($.trim(menuIds) ==""){
    	alert("请给角色授权");
    	return;
    }else if($.trim($('#roleId').val()) == ""){
    	return;
    }
    $('#menuIds').val(menuIds);
    $('#operationIds').val(operationIds);
	$("#inputForm").submit();
	return true;
}


</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>


</body>


</html>