<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>互动课堂</title>
	<%@ include file="/WEB-INF/include/head.jsp"%>
	<script src="${ctxStatic}/common/inspinia.js?v=3.2.0"></script>
	<script src="${ctxStatic}/common/contabs.js"></script> 
    <script type="text/javascript">
	$(document).ready(function() {
			 // 默认主题
        $("body").removeClass("skin-1");
        $("body").removeClass("skin-2");
        $("body").addClass("skin-3");
	 });
			
	</script>
	
</head>

<body class="fixed-sidebar full-height-layout gray-bg">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <span><img alt="image" title="修改账号" class="img-circle" style="height:64px;width:64px; cursor: pointer" src="${ctxStatic}/common/img/a1.jpg" onclick="openDialog('修改', '${ctx}/system/account/formUserSelf','800px', '500px', 'accountContent')"/></span>
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                               <span class="block m-t-xs"><strong class="font-bold">${user.account}</strong></span>
                               <%--<span class="text-muted text-xs block">${user.roleList.get(0).roleName}<b class="caret"></b></span>--%>
                                </span>
                            </a>
                           <%-- <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                &lt;%&ndash;<li><a onclick="changeStyle()" href="#">切换到ACE模式</a>
                                </li> 
                                 &ndash;%&gt;
                                <li >
                                <a href="#" onclick="openDialog('修改', '${ctx}/system/account/formUserSelf','800px', '500px', 'accountContent')">帐号修改</a>
                                </li>
                               &lt;%&ndash; <li><a href="${ctx}/logout">安全退出</a>
                                </li>&ndash;%&gt;
                            </ul>--%>
                        </div>
                        <div class="logo-element">WS
                        </div>
                    </li>
     				<t:menu mainStyle="default" isRoot="true"></t:menu>
                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
           <%-- <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                        <form role="search" class="navbar-form-custom" method="post" action="search_results.html">
                            <div class="form-group">
                                <input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search" id="top-search">
                            </div>
                        </form>
                    </div>
                    <ul class="nav navbar-top-links navbar-right">
                        <li class="dropdown">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-envelope"></i> <span class="label label-warning"></span>
                            </a>
                            
                        </li>
                        <li class="dropdown">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-bell"></i> <span class="label label-primary"></span>
                            </a>
                            
                        </li>
                      
                        <li class="dropdown">
							<a class="dropdown-toggle">
								<i></i><span class="label label-primary"></span>
							</a>
						</li>
                    </ul>
                </nav>
            </div>--%>
            <div class="row content-tabs">
                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:;" class="active J_menuTab" data-id="${ctx}/home">首页</a>
                    </div>
                </nav>
                <%--<button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                </button>--%>
                <%--<div class="btn-group roll-nav roll-right">
                    <button class="dropdown J_tabClose"  data-toggle="dropdown">关闭操作<span class="caret"></span>

                    </button>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabShowActive"><a>定位当前选项卡</a>
                        </li>
                        <li class="divider"></li>
                        <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                        </li>
                        <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                        </li>
                    </ul>
                </div>--%>
                <a href="${ctx}/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${ctx}/home" frameborder="0" data-id="${ctx}/home" seamless></iframe>
            </div>
            <div class="footer">
                <div class="pull-left"><a href="http://www.winshare-edu.com">http://www.winshare-edu.com</a> &copy; 2016-2025</div>
            </div>
        </div>
        <!--右侧部分结束-->
       
       
    </div>
</body>
<script type="text/javascript">
function changeStyle(){
   $.get('${pageContext.request.contextPath}/theme/ace?url='+window.top.location.href,function(result){window.location.reload();});
}

</script>
</html>