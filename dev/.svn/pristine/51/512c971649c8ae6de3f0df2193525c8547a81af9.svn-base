<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>Login Page | Welcome LMIS</title>
		<meta name="description" content="User login page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
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
			if("ontouchend" in document) document.write("<script src='<%=basePath %>assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
			function show_box(id) {
			 jQuery('.widget-box.visible').removeClass('visible');
			 jQuery('#'+id).addClass('visible');
			}
			function login(){
				window.location.href="${root}lmis/center.jsp";
			}

			function forgetPassword() {
				var email = $("#login_email").val();
				if(email==''){
					alert("注册的邮箱不能为空！");
					return;
				}
				 $.ajax({
				       type: "POST",
				       url: "/BT-LMIS/loginController/forgetPassword.do",
				       dataType: "json",
				       data: {email:email},
				       success: function(result) {
				          if(result.flag==false){
				        	  alert(result.msg);
				          }else{
				        	  alert(result.msg);
				          }
				       },
				       error: function() {
				          alert("系统异常-error");
				       }
				    });
				}
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
									<span class="red">LMIS</span>
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
															<input id="username" name="username" type="text" class="form-control" placeholder="用户名" value="" />
															<i class="icon-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input id="password" name="password" type="password" class="form-control" placeholder="密码" value="" />
															<i class="icon-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														<label class="inline">
															<input type="checkbox" class="ace" />
															<span class="lbl"> 记住密码</span>
														</label>

														<button type="submit" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="icon-key"></i>登录
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>

										</div><!-- /widget-main -->

										<div class="toolbar clearfix">
											<div>
												<a href="#" onclick="show_box('forgot-box'); return false;" class="forgot-password-link">
													<i class="icon-arrow-left"></i>
													忘记密码?
												</a>
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
															<input type="email" id="login_email" class="form-control" placeholder="Email" />
															<i class="icon-envelope"></i>
														</span>
													</label>

													<div class="clearfix">
														<button type="button" onclick="forgetPassword()" class="width-35 pull-right btn btn-sm btn-danger">
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
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div>
		</div><!-- /.main-container -->

		<!-- basic scripts -->
	</body>
</html>
