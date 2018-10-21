<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/templet/common.jsp"%>
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
<link type="text/css" href="<%=basePath%>css/table.css"
	rel="stylesheet" />
</head>
<body style="background-color:transparent;">
	<div class="table-main">
		<div class="table-border" style="border:0px;">
			<div class="table-content-parent" style="overflow: auto;border:0px;border-left:1px solid #DDD;border-right:1px solid #DDD;min-height:100px;">
				<div class="table-content table-content-free" id="templet_test_content" style="overflow: visible;">
					<table class="table table-striped table-bordered table-hover table-fixed no-margin" style="overflow: visible;">
						<thead class="table-title">
					  		<tr>
								<c:if test="${isShow == false }">
					  				<th class="text-center table-text col-sm">
										<label class="pos-rel">
											<input class="ace" type="checkbox" id="checkAll_templetTest" onclick="inverseCkb('ckb')" />
											<span class="lbl"></span>
										</label>
									</th>
								</c:if>
				  				<th class="text-center table-text col-lg" title="系统仓">
				  					系统仓
					  			</th>
				  				<th class="text-center table-text col-lg" title="店铺">
				  					店铺
					  			</th>
					  		</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageView.records }" var="res">
								<tr class="pointer" >
									<c:if test="${isShow == false }">
						  				<td class="text-center table-text col-sm">
											<label class="pos-rel">
												<input class="ace" type="checkbox" id="ckb${res.id}" name="ckb" value="${res.id}" data-warehouse_code="${res.warehouseCode }" data-store_code="${res.storeCode }" />
												<span class="lbl"></span>
											</label>
										</td>
									</c:if>
					  				<td class="text-center table-text col-lg" title="${res.warehouseName }">
						  				${res.warehouseName }
						  			</td>
					  				<td class="text-center table-text col-lg" title="${res.storeName }">
						  				${res.storeName }
						  			</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="dataTables_wrapper form-inline">
			<div class="row">
				<div class="col-md-12">
					${pageView.pageView }
				</div>
			</div>
		</div>
	</div>
</body>
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
		</script>
<script type="text/javascript" src="<%=basePath%>js/common_view.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/work_order/work_type.js"></script>
<script type="text/javascript" src="<%=basePath%>js/selectFilter.js"></script>
<script type="text/javascript">
	var root = '<%=basePath %>';
	function inverseCkb(items){
		$('[name='+items+']:checkbox').each(function(){
			this.checked=!this.checked;
		});
	}

	//跳转
	function pageJump() {
		$("#warehouseAndStore", window.parent.document).attr("src",root+"/control/wareParkController/wareParkWarehouseStoreList.do?parkId=${queryParam.parkId}&isShow=${isShow}&startRow="+$("#startRow").val()+"&endRow="+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
	}

</script>
</html>
