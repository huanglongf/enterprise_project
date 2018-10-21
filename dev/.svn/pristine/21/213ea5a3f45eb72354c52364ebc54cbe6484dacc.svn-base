<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>LMIS</title>
		<%@ include file= "../yuriy.jsp" %>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<!-- basic styles -->
		<link href="<%=basePath %>assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="<%=basePath %>assets/css/font-awesome.min.css" />
		<script type="text/javascript" src="<%=basePath %>manage/My97DatePicker/WdatePicker.js"></script>

		<!--[if IE 7]>
		  <link rel="stylesheet" href="<%=basePath %>assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->
		<!-- fonts -->
		<link rel="stylesheet" href="<%=basePath %>assets/css/ace-fonts.css" />
		<!-- ace styles -->
		<link rel="stylesheet" href="<%=basePath %>assets/css/ace.min.css" />
		<link rel="stylesheet" href="<%=basePath %>assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="<%=basePath %>assets/css/ace-skins.min.css" />
		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="<%=basePath %>assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->
		<!-- ace settings handler -->
		<script src="<%=basePath %>assets/js/ace-extra.min.js"></script>
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
		<script src="<%=basePath %>assets/js/html5shiv.js"></script>
		<script src="<%=basePath %>assets/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body>
		<div class="navbar navbar-default navbar-fixed-top" id="navbar">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<small>
							<i class="icon-laptop"></i>&nbsp;
							Warehouse Management System&nbsp;&nbsp;&nbsp;[&nbsp;WMS&nbsp;仓&nbsp;库&nbsp;管&nbsp;理&nbsp;系&nbsp;统&nbsp;]
						</small>
					</a><!-- /.brand -->
				</div><!-- /.navbar-header -->

				<div class="navbar-header pull-right" role="navigation">
				</div><!-- /.navbar-header -->
			</div><!-- /.container -->
		</div>

		<div class="main-container" id="main-container" style="margin-top: 46px;">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>

				<div class="sidebar" id="sidebar">
					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
					</script>

					<ul class= "nav nav-list" >
						<li>
							<a href= "#" class= "dropdown-toggle" >
								<i class= "icon-dashboard" ></i>
								<span class= "menu-text" > WMS上下架</span>
								<b class= "arrow icon-angle-down" ></b>
							</a>
							<ul class= "submenu" >
								<li>
									<a href= "javascript:void(0);" onclick= "openDiv('${root}control/mainController/a.do','容器查询');">
										<i class= "icon-double-angle-right" ></i>容器查询
									</a>
								</li>
								<li>
									<a href= "javascript:void(0);" onclick= "openDiv('${root}control/mainController/b.do','上架下架记录查询');">
										<i class= "icon-double-angle-right" ></i>上架下架记录查询
									</a>
								</li>
								<li>
									<a href= "javascript:void(0);" onclick= "openDiv('${root}control/mainController/c.do','上架下架记录汇总查询');">
										<i class= "icon-double-angle-right" ></i>上架下架记录汇总查询
									</a>
								</li>
								<li>
									<a href= "javascript:void(0);" onclick= "openDiv('${root}control/mainController/d.do','异常记录查询');">
										<i class= "icon-double-angle-right" ></i>异常记录汇总查询
									</a>
								</li>
							</ul>
						</li>
					</ul>

					<div class="sidebar-collapse" id="sidebar-collapse">
						<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
					</div>

					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
					</script>
				</div>

				<div class="main-content">
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="#">WMS上下架</a>
							</li>
							<li class="active"  id="home" ></li>
						</ul><!-- .breadcrumb -->

						<div class="nav-search" id="nav-search">
							<form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="icon-search nav-search-icon"></i>
								</span>
							</form>
						</div><!-- #nav-search -->
					</div>

					<div class="page-content">
						<div class="page-header">
							<h1>
								<span id="titles">首页</span>
								<small>
									<i class="icon-double-angle-right"></i>
									概况 &amp; 统计
								</small>
							</h1>
						</div><!-- /.page-header -->
						<div id="div_view" >
						</div><!-- /row -->


					</div><!-- /.page-content -->
				</div><!-- /.main-content -->

			</div><!-- /.main-container-inner -->

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath %>assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='<%=basePath %>assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
		</script>
		<![endif]-->

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='<%=basePath %>assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="<%=basePath %>assets/js/bootstrap.min.js"></script>
		<script src="<%=basePath %>assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<!--[if lte IE 8]>
		  <script src="<%=basePath %>assets/js/excanvas.min.js"></script>
		<![endif]-->

		<script src="<%=basePath %>assets/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="<%=basePath %>assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="<%=basePath %>assets/js/jquery.slimscroll.min.js"></script>
		<script src="<%=basePath %>assets/js/jquery.easy-pie-chart.min.js"></script>
		<script src="<%=basePath %>assets/js/jquery.sparkline.min.js"></script>
		<script src="<%=basePath %>assets/js/flot/jquery.flot.min.js"></script>
		<script src="<%=basePath %>assets/js/flot/jquery.flot.pie.min.js"></script>
		<script src="<%=basePath %>assets/js/flot/jquery.flot.resize.min.js"></script>

		<!-- ace scripts -->

		<script src="<%=basePath %>assets/js/ace-elements.min.js"></script>
		<script src="<%=basePath %>assets/js/ace.min.js"></script>

		<!-- inline scripts related to this page -->

	</body>
</html>
