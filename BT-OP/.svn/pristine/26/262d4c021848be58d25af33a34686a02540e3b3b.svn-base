<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang= "en" >
		<title>BT-OP</title>
		<meta charset="UTF-8" />
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		
		<%@ include file= "/base/base.jsp" %>
		
		<!-- css -->
		<link type= "text/css" href= "<%=basePath %>assets/css/bootstrap.min.css" rel= "stylesheet" />
		<link type= "text/css" href= "<%=basePath %>assets/css/font-awesome.min.css" rel= "stylesheet" />
		<link type= "text/css" href= "<%=basePath %>assets/css/ace-fonts.css" rel= "stylesheet" />
		<link type= "text/css" href= "<%=basePath %>assets/css/ace.min.css" rel= "stylesheet" />
		<link type= "text/css" href= "<%=basePath %>assets/css/ace-rtl.min.css" rel= "stylesheet" />
		<link type= "text/css" href= "<%=basePath %>assets/css/ace-skins.min.css" rel= "stylesheet" />
		
		<!-- js -->
		<script type= "text/javascript" src= "<%=basePath %>assets/js/jquery-2.0.3.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>assets/js/bootstrap.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>assets/js/ace-extra.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>assets/js/typeahead-bs2.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>assets/js/jquery-ui-1.10.3.custom.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>assets/js/jquery.ui.touch-punch.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>assets/js/jquery.slimscroll.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>assets/js/jquery.easy-pie-chart.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>assets/js/jquery.sparkline.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>assets/js/flot/jquery.flot.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>assets/js/flot/jquery.flot.pie.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>assets/js/flot/jquery.flot.resize.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>assets/js/ace-elements.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>assets/js/ace.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>base/base.js" ></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jquery.shCircleLoader-min.js"></script>
		<style type="text/css">
			.cover-bg{
				filter:alpha(opacity:30); 
				opacity:0.3;  
				-moz-opacity:0.3;
				-khtml-opacity: 0.3;
			}
			.ftCon-Wrapper{
				position: fixed;
				bottom: 0px;
				left:0px; 
				width:100%;
				text-align: center; 
				color:#000;
				margin: 0px;
				padding: 0px;
				list-style:none;
				font-size: 14px;
				line-height: 25px;
			}
		</style>
	</head>
	<body>
		<div class="navbar navbar-default" id="navbar">
			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<small>
							<i class="icon-adn"></i>&nbsp;&nbsp;BaoZun Logistics Order Platform&nbsp;&nbsp;&nbsp;[&nbsp;宝&nbsp;尊&nbsp;物&nbsp;流&nbsp;下&nbsp;单&nbsp;平&nbsp;台&nbsp;]
						</small>
					</a><!-- /.brand -->
				</div><!-- /.navbar-header -->
				<div class="navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="<%=basePath %>assets/avatars/avatar2.png" alt="Jason's Photo" />
								<span class="user-info">
									<small>Welcome,</small>
									${account.name }
								</span>
								<i class="icon-caret-down"></i>
							</a>
							<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<!-- <li>
									<a href="javascript:reset()">
										<i class="icon-cog"></i>Settings
									</a>
								</li>

								<li>
									<a href="#">
										<i class="icon-user"></i>Profile
									</a>
								</li> 

								<li class="divider"></li>-->
								<li>
									<a href="${root}loginController/logout.do">
										<i class="icon-off"></i>Logout
									</a>
								</li>
							</ul>
						</li>
					</ul><!-- /.ace-nav -->
				</div><!-- /.navbar-header -->
			</div><!-- /.container -->
		</div>
		<div id="main-container" class="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container','fixed')}catch(e){}
			</script>
			<div class="main-container-inner">
				<a id="menu-toggler" class="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>
				<div id="sidebar" class="sidebar">
					<div id="sidebar-shortcuts" class="sidebar-shortcuts">
					</div>
					<ul class="nav nav-list">
						<li class="active open">
							<a href="#" class="dropdown-toggle">
								<i class="icon-cogs"></i>
								<span class="menu-text">运单管理</span>
								<b class="arrow icon-angle-down"></b>
							</a>
							 <%-- <ul class="submenu">
								<li class="active">
									<a href="javascript:void(0);" onclick="loadingCenterPanel('${root}control/systemController/organization.do');">
										<i class="icon-double-angle-right"></i>组织架构
									</a>
								</li>
							</ul> --%>
							<%-- <ul class="submenu">
								<li>
									<a href="javascript:void(0);" onclick="loadingCenterPanel('${root}control/orderPlatform/transportVenderController/initail.do');">
										<i class="icon-double-angle-right"></i>快递信息维护
									</a>
								</li>
							</ul>
							 <ul class="submenu">
								<li>
									<a href="javascript:void(0);" onclick="loadingCenterPanel('${root }/control/orderPlatform/transportProductTypeController/initail.do');">
										<i class="icon-double-angle-right"></i>快递业务类型维护
									</a>
								</li>
							</ul>
							 <ul class="submenu">
								<li>
									<a href="javascript:void(0);" onclick="loadingCenterPanel('${root }/control/orderPlatform/areaController/toForm.do');">
										<i class="icon-double-angle-right"></i>省市区信息维护
									</a>
								</li>
							</ul>  --%>
							  <ul class="submenu">
							 	<c:if test="${account.orgid==5}">
								<li>
									<a href="javascript:void(0);" onclick="initail()" >
										<i class="icon-double-angle-right"></i>运单
									</a>
								</li>
								</c:if>
							 	<c:if test="${account.orgid!=5 && account.orgid!=3 && account.orgid!=165 && account.orgid!=166 &&account.orgid!=40&&account.orgid!=176&&account.orgid!=177&&account.orgid!=178&&account.orgid!=1&&account.orgid!=997&&account.orgid!=998&&account.orgid!=999}">
								<li>
									<a href="javascript:void(0);" onclick="initaila()">
										<i class="icon-double-angle-right"></i>门店运单
									</a>
								</li>
								</c:if>
							 	<c:if test="${account.orgid==3 || account.orgid==165 || account.orgid==166 ||account.orgid==40|| account.orgid==176|| account.orgid==177|| account.orgid==178|| account.orgid==1 || account.orgid==727|| account.orgid==997|| account.orgid==998|| account.orgid==999}">

								<li>
									<a href="javascript:void(0);" onclick="baozuninitaila()">
										<i class="icon-double-angle-right"></i>宝尊运单
									</a>
								</li>
								</c:if>
								<c:if test="${account.orgid==5}">
								<li>
									<a href="javascript:void(0);" onclick="loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/fastprint.do');">
										<i class="icon-double-angle-right"></i>快速打印面单
									</a>
								</li>
								<li>
									<a href="javascript:void(0);" onclick="loadingCenterPanel('${root }/control/orderPlatform/updExpectedFromDate/upd_exptime.do');">
										<i class="icon-double-angle-right"></i>预计发货日期批量更新
									</a>
								</li>
								</c:if>
								<li>
									<a href="javascript:void(0);" onclick="toForm()">
										<i class="icon-double-angle-right"></i>运单快速查询
									</a>
								</li>
								<!-- <li>
									<a href="javascript:void(0);" onclick="user()">
										<i class="icon-double-angle-right"></i>用户
									</a>
								</li> -->
								<%-- <li>
									<a href="javascript:void(0);" onclick="loadingCenterPanel('${root}control/orderPlatform/transportVenderController/initail.do');">
										<i class="icon-double-angle-right"></i>快递信息维护
									</a>
								</li> --%>
								<c:if test="${account.orgid==3 || account.orgid==165 || account.orgid==166||account.orgid==40|| account.orgid==176|| account.orgid==177|| account.orgid==178|| account.orgid==999}">
								<li>
									<a href="javascript:void(0);" onclick="loadingCenterPanel('${root }/control/orderPlatform/expressinfoMasterInputlistController/uploadresult.do');">
										<i class="icon-double-angle-right"></i>导入结果查询
									</a>
								</li>
								</c:if>
								<%-- <li>
									<a href="javascript:void(0);" onclick="loadingCenterPanel('${root }/control/orderPlatform/testWebsiteController/page.do');">
										<i class="icon-double-angle-right"></i>导入结果查询11
									</a>
								</li> --%>
							</ul> 
						</li>
					</ul>
				</div><!-- /.main-content -->
			</div><!-- /.main-container -->
			<div class= "main-content" >
			<input id="orgid" type=text name="orgid" style="display:none" value="${account.orgid}">
				<!-- <div id= "breadcrumbs" class= "breadcrumbs" > -->
					<script type= "text/javascript" >
						try{ace.settings.check("breadcrumbs", "fixed")}catch(e){}
						$(function(){ 
							var orgid =document.getElementById("orgid").value;
							if(orgid==3 || orgid==165 || orgid==166||orgid==40||orgid==176||orgid==177||orgid==178||orgid==1||orgid==997||orgid==998||orgid==999){
								baozuninitaila();
							}/* else if(orgid==5){
								initail();
							} */else if(orgid!=5 && orgid!=3 && orgid!=165 && orgid!=166){
								initaila();
							}else{
								toForm();
							}
							//
						}); 
						function toForm(){
							//loadingStyle();
							loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/toForm.do?time='+new Date().getTime());
							//cancelLoadingStyle();
						}
						function initail(){
							//loadingStyle();
							loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/initail.do?time='+new Date().getTime());
							//cancelLoadingStyle();
						}
						function initaila(){
							//loadingStyle();
							loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/initaila.do?time='+new Date().getTime());
							//cancelLoadingStyle();
						}
						function baozuninitaila(){
							//loadingStyle();
							loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/baozuninitaila.do?time='+new Date().getTime());
							//cancelLoadingStyle();
						}
						function user(){
							//loadingStyle();
							loadingCenterPanel('${root }/control/login/userController/user.do?time='+new Date().getTime());
							//cancelLoadingStyle();
						}
					</script>
					<!-- <div class="nav-search" id="nav-search">
							<form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="icon-search nav-search-icon"></i>
								</span>
							</form>
						</div> -->
				<!-- </div> -->
				<div id= "centerPanel" >
				</div>
				<div id= "background" >
				</div>
			</div>
		</div>
		<!-- basic scripts -->
		<div id="float_banner_table">
			<table class= "table table-hover table-striped">
				
			</table>
		</div>
		<ul class="ftCon-Wrapper">
			<li><a href="http://www.miitbeian.gov.cn" style="color: black;">苏ICP备18020088号-1</a>Copyright 2010-2020 by BAOZUN <a href="http://www.miitbeian.gov.cn">http://www.miitbeian.gov.cn</a></li>
		</ul>
	</body>
</html>