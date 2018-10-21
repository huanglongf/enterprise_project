<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang= "en" >
		<meta charset= "UTF-8" >
		<%@ include file= "/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type="text/css" href="<%=basePath %>font-awesome-4.7.0/css/font-awesome.css" rel="stylesheet" media="all" />
		<link type="text/css" href="<%=basePath %>assets/css/bootstrap.min.css" rel="stylesheet" />
		<link type="text/css" href="<%=basePath %>assets/css/font-awesome.min.css" rel="stylesheet" />
		<link type="text/css" href="<%=basePath %>assets/css/ace-fonts.css" rel="stylesheet" />
		<link type="text/css" href="<%=basePath %>assets/css/ace.min.css" rel="stylesheet" />
		<link type="text/css" href="<%=basePath %>assets/css/ace-rtl.min.css" rel="stylesheet" />
		<link type="text/css" href="<%=basePath %>assets/css/ace-skins.min.css" rel="stylesheet" />
		<script type="text/javascript">window.jQuery || document.write("<script src= '<%=basePath %>assets/js/jquery-2.0.3.min.js'>" + "<" + "/script>");</script>
		<script type="text/javascript">if("ontouchend" in document) document.write("<script src='<%=basePath %>assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");</script>
		<script type="text/javascript" src="<%=basePath %>assets/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>assets/js/ace-extra.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>assets/js/typeahead-bs2.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>assets/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>assets/js/jquery.ui.touch-punch.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>assets/js/jquery.slimscroll.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>assets/js/jquery.easy-pie-chart.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>assets/js/jquery.sparkline.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>assets/js/flot/jquery.flot.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>assets/js/flot/jquery.flot.pie.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>assets/js/flot/jquery.flot.resize.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>assets/js/ace-elements.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>assets/js/ace.min.js"></script>
		<script type="text/javascript">
			var Global_var='';
			// 0-普通页面 1-下拉菜单
			var menu_type = 1;
			jQuery(function($) {
// 		    	var height=window.innerHeight-100;
// 		    	$("#navbar").css({"height":"45px"});
//                 $("#sidebar").css({"height":height+"px","overflow-y":"scroll","width":"205px"});
// 		    	$("#div_view").css({"height":height+"px","overflow-y":"scroll"});
				// 菜单栏选中控制
				$('ul.nav li').click(function (e) {
					// e.preventDefault();
					// $('ul.nav li').removeClass('active');
					// e.stopPropagation();
					if($(this).children(":first").hasClass("dropdown-toggle")) {
						if($(this).hasClass("open")) {
							if(menu_type == 0) {
								$(this).addClass('active');
							} else {
								if($(this).hasClass("active")) {
									$(this).removeClass("active");
								}
							}
						}
						$(this).siblings().removeClass("active");
						$(this).siblings().find("li").removeClass("active");
					} else {
						menu_type = 0;
						$(this).addClass('active');
						$(this).siblings().removeClass("active");
						$(this).siblings().find("li").removeClass("active");
					}
					if($(this).parent().hasClass("nav")) {
						menu_type = 1;
					}
				});
				//
				$('.light-blue .dropdown-toggle').dropdown();
				//
// 				if ("${not empty session_employee.teamId }" == "true") {
// 					// 店铺客服用户默认进入首页// 设置导航
// 					$('#tree_icon').attr('class','icon-home');
// 					$('#tree_name').text('首页');
// 					$('#tree1_name').text('');
// 					$('#tree1_name_li').css('display','none');
// 					$('#tree2_name_li').css('display','none');
// 					//openDiv('${root }/control/workOrderPlatformStoreController/workOrderIndex.do');
// 				}
				
				//
				$('.easy-pie-chart.percentage').each(function(){
					var $box = $(this).closest('.infobox');
					var barColor = $(this).data('color') || (!$box.hasClass('infobox-dark') ? $box.css('color') : 'rgba(255,255,255,0.95)');
					var trackColor = barColor == 'rgba(255,255,255,0.95)' ? 'rgba(255,255,255,0.25)' : '#E2E2E2';
					var size = parseInt($(this).data('size')) || 50;
					$(this).easyPieChart({
						barColor: barColor,
						trackColor: trackColor,
						scaleColor: false,
						lineCap: 'butt',
						lineWidth: parseInt(size/10),
						animate: /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase()) ? false : 1000,
						size: size
					});
				})
				
				$('.sparkline').each(function(){
					var $box = $(this).closest('.infobox');
					var barColor = !$box.hasClass('infobox-dark') ? $box.css('color') : '#FFF';
					$(this).sparkline('html', {tagValuesAttribute:'data-values', type: 'bar', barColor: barColor , chartRangeMin:$(this).data('min') || 0} );
				});
			
			  	var placeholder = $('#piechart-placeholder').css({'width':'90%' , 'min-height':'150px'});
			  	var data = [
					{ label: "social networks",  data: 38.7, color: "#68BC31"},
					{ label: "search engines",  data: 24.5, color: "#2091CF"},
					{ label: "ad campaigns",  data: 8.2, color: "#AF4E96"},
					{ label: "direct traffic",  data: 18.6, color: "#DA5430"},
					{ label: "other",  data: 10, color: "#FEE074"}
			  	]
			  	function drawPieChart(placeholder, data, position) {
		 			$.plot(placeholder, data, {
						series: {
							pie: {
								show: true,
								tilt:0.8,
								highlight: {
									opacity: 0.25
								},
								stroke: {
									color: '#fff',
									width: 2
								},
								startAngle: 2
							}
						},
						legend: {
							show: true,
							position: position || "ne", 
							labelBoxBorderColor: null,
							margin:[-30,15]
						},
						grid: {
							hoverable: true,
							clickable: true
						}
					})
			 	};
			 	drawPieChart(placeholder, data);
			 	/**
				 	we saved the drawing function and the data to redraw with different position later when switching to RTL mode dynamically
					so that's not needed actually.
				*/
				placeholder.data('chart', data);
				placeholder.data('draw', drawPieChart);
				var $tooltip = $("<div class='tooltip top in'><div class='tooltip-inner'></div></div>").hide().appendTo('body');
			 	var previousPoint = null;
			 	placeholder.on('plothover', function (event, pos, item) {
			 		if(item) {
			 			if (previousPoint != item.seriesIndex) {
			 				previousPoint = item.seriesIndex;
			 				var tip = item.series['label']+ ": "+ item.series['percent']+ '%';
			 				$tooltip.show().children(0).text(tip);
			 				
			 			}
			 			$tooltip.css({top:pos.pageY + 10, left:pos.pageX + 10});
			 			
			 		} else {
			 			$tooltip.hide();
			 			previousPoint = null;
			 			
			 		}
			 		
			 	});
			 	var d1= [];
			 	for(var i= 0; i< Math.PI * 2; i+= 0.5) {
			 		d1.push([i, Math.sin(i)]);
			 		
			 	}
			 	var d2= [];
			 	for(var i= 0; i< Math.PI* 2; i+= 0.5){
			 		d2.push([i, Math.cos(i)]);
			 		
			 	}
			 	var d3= [];
			 	for(var i= 0; i< Math.PI* 2; i+= 0.2) {
			 		d3.push([i, Math.tan(i)]);
			 		
			 	}
			 	var sales_charts= $('#sales-charts').css({'width': '100%', 'height': '220px'});
			 	$.plot("#sales-charts", [
			 		{ label: "Domains", data: d1 },
			 		{ label: "Hosting", data: d2 },
			 		{ label: "Services", data: d3 }
			 		], {
			 		hoverable: true,
			 		shadowSize: 0,
			 		series: {
			 			lines: { show: true },
			 			points: { show: true }
			 			
			 		},
			 		xaxis: {
			 			tickLength: 0
			 			
			 		},
			 		yaxis: {
			 			ticks: 10,
			 			min: -2,
			 			max: 2,
			 			tickDecimals: 3
			 			
			 		},
			 		grid: {
			 			backgroundColor: { colors: [ "#fff", "#fff" ] },
			 			borderWidth: 1,
			 			borderColor:'#555'
			 			
			 		}
			 		
			 	});
			 	$('#recent-box [data-rel="tooltip"]').tooltip({placement: tooltip_placement});
			 	function tooltip_placement(context, source) {
			 		var $source = $(source);
			 		var $parent = $source.closest('.tab-content')
			 		var off1 = $parent.offset();
			 		var w1 = $parent.width();
			 		var off2 = $source.offset();
			 		var w2 = $source.width();
			 		if(parseInt(off2.left)< parseInt(off1.left)+ parseInt(w1/ 2) )return 'right';
			 		return 'left';
			 		
			 	}
			 	$('.dialogs,.comments').slimScroll({
			 		height: '300px'
			 		
			 	});
			 	//Android's default browser somehow is confused when tapping on label which will lead to dragging the task
			 	//so disable dragging when clicking on label
			 	var agent = navigator.userAgent.toLowerCase();
			 	if("ontouchstart" in document && /applewebkit/.test(agent) && /android/.test(agent))
					$('#tasks').on('touchstart', function(e){
						var li = $(e.target).closest('#tasks li');
					 	if(li.length == 0)return;
					 	var label = li.find('label.inline').get(0);
					 	if(label == e.target || $.contains(label, e.target)) e.stopImmediatePropagation();
					 	
					});
			 	$('#tasks').sortable({
			 		opacity: 0.8,
			 		revert: true,
				 	forceHelperSize: true,
				 	placeholder: 'draggable-placeholder',
				 	forcePlaceholderSize: true,
				 	tolerance: 'pointer',
				 	stop: function( event, ui ) {//just for Chrome!!!! so that dropdowns on items don't appear below other items after being moved
						$(ui.item).css('z-index', 'auto');
				 	
				 	}
			 	
			 	});
			 	$('#tasks').disableSelection();
			 	$('#tasks input:checkbox').removeAttr('checked').on('click', function(){
					if(this.checked) $(this).closest('li').addClass('selected');
					else $(this).closest('li').removeClass('selected');
					
			 	});
			 	
			})
			function reset(){
				openDiv('/BT-LMIS/lmis/reset.jsp');
			}
			function toProfile(){
				openDiv('${root}/loginController/toProfile.do');
			}
		</script>
	</head>
	<body>
		<div class="navbar navbar-default" id="navbar">
			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<small>
							<i class="icon-adn"></i>&nbsp;&nbsp;Logistics Management Information System&nbsp;&nbsp;&nbsp;[&nbsp;物&nbsp;流&nbsp;管&nbsp;理&nbsp;信&nbsp;息&nbsp;系&nbsp;统&nbsp;]
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
									${session_employee.username }
								</span>
								<i class="icon-caret-down"></i>
							</a>
							<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="javascript:reset()">
										<i class="icon-cog"></i>Settings
									</a>
								</li>

								<li>
									<a href="javascript:toProfile()">
										<i class="icon-user"></i>Profile
									</a>
								</li>

								<li class="divider"></li>
								<li>
									<a href="${root}loginController/outLogin.do">
										<i class="icon-off"></i>Logout
									</a>
								</li>
							</ul>
						</li>
					</ul><!-- /.ace-nav -->
				</div><!-- /.navbar-header -->
			</div><!-- /.container -->
		</div>
		<div id= "main-container" class= "main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>
			<div class= "main-container-inner" >
				<a id= "menu-toggler" class= "menu-toggler" href= "#" >
					<span class= "menu-text" ></span>
				</a>
				<div id= "sidebar" class= "sidebar" >
					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
					</script>
					<div id= "sidebar-shortcuts" class= "sidebar-shortcuts" >
						<div id= "sidebar-shortcuts-large" class= "sidebar-shortcuts-large" >
							<button class= "btn btn-success" >
								<i class= "icon-signal" ></i>
							</button>
							<button class= "btn btn-info" >
								<i class= "icon-pencil" ></i>
							</button>
							<button class= "btn btn-warning" >
								<i class= "icon-group" ></i>
							</button>
							<button class= "btn btn-danger" >
								<i class= "icon-cogs" ></i>
							</button>
						</div>
						<div id= "sidebar-shortcuts-mini" class= "sidebar-shortcuts-mini" >
							<span class= "btn btn-success" ></span>
							<span class= "btn btn-info" ></span>
							<span class= "btn btn-warning" ></span>
							<span class= "btn btn-danger" ></span>
						</div>
					</div>
					<ul class="nav nav-list">
						<li class="active">
							<a id="firstPage" href="javascript:void(0)"
								onclick="<c:if test='${not empty session_employee.teamId }'>openDiv('${root }/control/workOrderPlatformStoreController/workOrderIndex.do');</c:if>
								$('#tree_icon').attr('class','icon-home');
								$('#tree_name').text('首页');
								$('#tree1_name').text('');
								$('#tree1_name_li').css('display','none');
								$('#tree2_name_li').css('display','none');"
							>
								<i class="icon-home"></i>
								<span class="menu-text"> 首页 </span>
							</a>
						</li>
						<c:forEach items="${menuTree }" var="tree">
							<li>
								<a href="#" class="dropdown-toggle">
									<i class="${tree.icon }"></i>
									<span class="menu-text">${tree.name }</span>
									<b class="arrow icon-angle-down"></b>
								</a>
								<ul class="submenu">
									<c:forEach items="${tree.tree1 }" var="tree1">
										<li>
											<c:if test="${empty tree1.tree2 }">
												<a href="javascript:void(0);" <c:if test="${not empty tree1.url }">
												onclick="openDiv('${root }${tree1.url }');
												$('#tree_icon').attr('class','${tree.icon}');
												$('#tree_name').text('${tree.name}');
												$('#tree1_name').text('${tree1.name}');
												$('#tree1_name_li').css('display','');
												$('#tree2_name_li').css('display','none');
												"</c:if>>
													<i class="icon-double-angle-right"></i>${tree1.name }
												</a>
											</c:if>	
											<c:if test="${not empty tree1.tree2 }">
												<a href="#" class="dropdown-toggle">
													<i class="icon-double-angle-right"></i>${tree1.name }
													<b class="arrow icon-angle-down"></b>
												</a>
												<ul class="submenu">
													<c:forEach items="${tree1.tree2 }" var="tree2">
														<li>
															<a href="javascript:void(0);" 
															onclick="openDiv('${root }${tree2.url }');
															$('#tree_icon').attr('class','${tree.icon}');
															$('#tree_name').text('${tree.name}');
															$('#tree1_name').text('${tree1.name}');
															$('#tree2_name').text('${tree2.name}');
															$('#tree1_name_li').css('display','');
															$('#tree2_name_li').css('display','');
															">
																<i class="${tree2.icon }"></i>${tree2.name }
															</a>
														</li>							
													</c:forEach>
												</ul>	
											</c:if>	
										</li>
									</c:forEach>
								</ul>
							</li>
						</c:forEach>
					</ul>
<!-- 					<div class="sidebar-collapse" id="sidebar-collapse" onclick="sidebar_collapse();"> -->
<!-- 						<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i> -->
<!-- 					</div> -->
				</div><!-- /.main-content -->
			</div><!-- /.main-container -->
			<div class="main-content">
				<div class="main-content-inner">
					<div id="breadcrumbs" class="breadcrumbs">
						<script type= "text/javascript" >
							try{ace.settings.check("breadcrumbs", "fixed")}catch(e){}
						</script>
						<ul class="breadcrumb">
							<li>
								<i id="tree_icon" class=""></i>
								<a id="tree_name" href="#"></a>
							</li>
							<li id="tree1_name_li" style="display: none">
								<a id="tree1_name" href="#"></a>
							</li>
							<li id="tree2_name_li" style="display: none">
								<a id="tree2_name" href="#"></a>
							</li>
						</ul>
					</div>
					<div class="page-content">
						<div id="div_view" class="page-content-area" data-ajax-content="true">
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
