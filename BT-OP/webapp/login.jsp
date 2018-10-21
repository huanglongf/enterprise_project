<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<title>Login Page | Welcome BT-OP</title>
		<meta charset="UTF-8">
		<meta name="description" content="User login page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@ include file= "/base/base.jsp" %>
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
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
		 window.onload=function(){
			try{
			    v = jsPrintSetup.getVersion();
			} catch(e) {
			    if(confirm('请先安装打印插件，安装插件否则将不能打印！你可以进入点击firefox，工具->插件，搜索"JSPrintSetup "安装，或者点击确定进入插件首页安装！')){
			        window.location.href="https://addons.mozilla.org/zh-CN/firefox/addon/js-print-setup/developers";
			    }
			}; 
			$('input[type=text]:first').focus();
		};
			if("ontouchend" in document) document.write("<script src='<%=basePath %>assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
			function show_box(id) {
				jQuery('.widget-box.visible').removeClass('visible');
				jQuery('#'+id).addClass('visible');
			}
			function login(){
				window.location.href="${root}common/main.jsp";
			}
			 $(document).ready(function(){$('input[type=text]:first').focus();});
			/*  function focusNextInput(){
				 alert(1);
		          var input = document.getElementById("loginPassword");
		          input.focus();s
		          break;
		      }   */
			 
			 $('#code').on('keypress',function(event){ 
		          
		         if(event.keyCode == 13){  
		        	 var input = document.getElementById("loginPassword");
			          input.focus();
			          break;
		         }  
		     });
		</script>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<!-- basic styles -->
		<link href="<%=basePath %>assets/css/bootstrap.min.css" rel="stylesheet" />
		<link href="<%=basePath %>assets/css/font-awesome.min.css" rel="stylesheet" />
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
		<style type="text/css">
			.ftCon-Wrapper{
				position: fixed;
				bottom: 0px;
				left:0px; 
				width:100%;
				text-align: center; 
				color:#FFF;
				margin: 0px;
				padding: 0px;
				list-style:none;
				font-size: 14px;
				line-height: 40px;
			}
		</style>
	</head>
	<body class="login-layout">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<i class="icon-adn green"></i>
									<span class="red">BT-OP</span>
									<span class="white" id="id-text2">Application</span>
								</h1>
								<h4 class="blue" id="id-company-text">&copy; 宝尊电子商务有限公司</h4>
							</div>
							<div class="space-6"></div>
							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="icon-coffee green"></i>登录
											</h4>
											<div class="space-6"></div>
											<form method="post" action="${root}loginController/login.do">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">${message}</span>
													</label>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input id="code" name="code" type="text" class="form-control" placeholder="用户名" />
															<i class="icon-user"></i>
														</span>
													</label>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input id="loginPassword" name="loginPassword" type="password" class="form-control" placeholder="密码" />
															<i class="icon-lock"></i>
														</span>
													</label>
													<div class="space"></div>
													<div class="clearfix">
<!-- 														<label class="inline">
															<input type="checkbox" class="ace" />
															<span class="lbl"> 记住密码</span>
														</label>
 -->														<button type="submit" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="icon-key"></i>登录
														</button>
													</div>
													<div class="space-4"></div>
												</fieldset>
											</form>
										</div><!-- /widget-main -->
										<div class="toolbar clearfix">
											<div>
												<!-- <a href="#" onclick="show_box('forgot-box'); return false;" class="forgot-password-link">
													<i class="icon-arrow-left"></i>
													忘记密码?
												</a> -->
											</div>
										</div>
									</div><!-- /widget-body -->
								</div><!-- /login-box -->
								<div id="forgot-box" class="forgot-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header red lighter bigger">
												<i class="icon-key"></i>找回密码
											</h4>
											<div class="space-6"></div>
											<p>
												请输入您注册时填写的电子邮件!
											</p>
											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="icon-envelope"></i>
														</span>
													</label>
													<div class="clearfix">
														<button type="button" class="width-35 pull-right btn btn-sm btn-danger">
															<i class="icon-lightbulb"></i>
															找回!
														</button>
													</div>
												</fieldset>
											</form>
										</div><!-- /widget-main -->
										<div class="toolbar center">
											<a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
												返回登录!
												<i class="icon-arrow-right"></i>
											</a>
										</div>
									</div><!-- /widget-body -->
								</div><!-- /forgot-box -->
							</div><!-- /position-relative -->
							<ul class="ftCon-Wrapper">
								<li><a href="http://www.miitbeian.gov.cn" style="color: white;">苏ICP备18020088号-1</a>Copyright 2010-2020 by BAOZUN <a href="http://www.miitbeian.gov.cn">http://www.miitbeian.gov.cn</a></li>
							</ul>
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div>
		</div><!-- /.main-container -->
		<!-- basic scripts -->
	</body>
</html>