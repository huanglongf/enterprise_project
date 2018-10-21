<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>404 页面</title>

		<meta name="description" content="404 Error Page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<!-- basic styles -->

		<link href="/BT-LMIS/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="/BT-LMIS/assets/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="/BT-LMIS/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<!-- fonts -->

		<link rel="stylesheet" href="/BT-LMIS/assets/css/ace-fonts.css" />

		<!-- ace styles -->

		<link rel="stylesheet" href="/BT-LMIS/assets/css/ace.min.css" />
		<link rel="stylesheet" href="/BT-LMIS/assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="/BT-LMIS/assets/css/ace-skins.min.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="/BT-LMIS/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="/BT-LMIS/assets/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="/BT-LMIS/assets/js/html5shiv.js"></script>
		<script src="/BT-LMIS/assets/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body>
		<div class="error-container">
			<div class="well">
				<h1 class="grey lighter smaller">
					<span class="blue bigger-125">
						<i class="icon-sitemap"></i>
						404
					</span>
					页面找不到啦！
				</h1>

				<hr>
				<h3 class="lighter smaller">我们找遍了所有的地方都没有找到呢!
							<i class="icon-search"></i></h3>

				<div>
					<!-- <form class="form-search">
						<span class="input-icon align-middle">
							<i class="icon-search"></i>
							<input type="text" class="search-query" placeholder="Give it a search...">
						</span>
						<button class="btn btn-sm" onclick="return false;">Go!</button>
					</form> -->

					<div class="space"></div>
					<h4 class="smaller">试着做下面这些事:</h4>

					<ul class="list-unstyled spaced inline bigger-110 margin-15">
						<li>
							<i class="icon-hand-right blue"></i>
							重新加载页面
						</li>

						<li>
							<i class="icon-hand-right blue"></i>
							查看帮助
						</li>

						<li>
							<i class="icon-hand-right blue"></i>
							联系我们
						</li>
					</ul>
				</div>

				<hr>
				<div class="space"></div>

				<div class="center">
					<a href="javascript:window.history.go(-1);" class="btn btn-grey">
						<i class="icon-arrow-left"></i>
						返回
					</a>

					<a href="javascript:window.location.reload();" class="btn btn-primary">
						<i class="icon-dashboard"></i>
						刷新
					</a>
				</div>
			</div>
		</div>

		<!-- basic scripts -->

		<!--[if !IE]> -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='/BT-LMIS/assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='/BT-LMIS/assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='/BT-LMIS/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="/BT-LMIS/assets/js/bootstrap.min.js"></script>
		<script src="/BT-LMIS/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->

		<script src="/BT-LMIS/assets/js/ace-elements.min.js"></script>
		<script src="/BT-LMIS/assets/js/ace.min.js"></script>

		<!-- inline scripts related to this page -->
	</body>
</html>
