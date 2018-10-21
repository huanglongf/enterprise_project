<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/templet/common.jsp"%>
<c:set var="root" value="<%=basePath %>"></c:set>
<title>工单首页</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" type="text/css" media="all" href="<%=basePath%>css/table.css" />
<link rel="stylesheet" type="text/css" media="all" href="<%=basePath%>work_order/css/work_order_index.css" />
<script type="text/javascript">var root = '${root}';</script>
<script type= "text/javascript" src= "<%=basePath %>echarts/echarts.min.js" ></script>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<div class="row">
				<div class="col-xs-12">
					<div class="widget-box">
						<div class="navigation">
							<div class="col-md-3 no-padding">
								<div class="col-xs-11 no-padding input-group">
									<input id="selectDate" name="selectDate" class="form-control data-range" value="今日" type="text" readonly />
									<span class="input-group-addon">
										<i class="icon-calendar bigger-110"></i>
									</span>
								</div>
							</div>
							<div class="card-foot"></div>
						</div>
						<div class="title-left">
							<div class="card">
								<div class="small-panel calendar">
									<span class="panel-title-wo em">我创建的</span>
									<span class="panel-body-small-wo">
										<font id="created" class="font-number-small-wo">0</font>
									</span>
								</div>
							</div>
							<div class="card">
								<div class="small-panel calendar">
									<span class="panel-title-wo em">已处理的</span>
									<span class="panel-body-small-wo">
										<font id="submited" class="font-number-small-wo">0</font>
									</span>
								</div>
							</div>
							<div class="card">
								<div class="small-panel calendar">
									<span class="panel-title-wo em">已完结的</span>
									<span class="panel-body-small-wo">
										<font id="ended" class="font-number-small-wo">0</font>
									</span>
								</div>
							</div>
							<div class="card-foot"></div>
						</div>
						<div class="title-right">
							<div class="card">
								<div class="big-panel calendar">
									<span class="panel-title-wo em">未处理的</span>
									<span class="panel-body-big-wo">
										<font id="waitting" class="font-number-big-wo">0</font>
									</span>
								</div>
							</div>
						</div>
						<div class="title-foot"></div>
					</div>
					<div class="content" id="content">
						<div id="data_content"></div>
<script type="text/javascript">
$(function(){
	var data = {
		title : '最近七日趋势图',
		//legend : ['新创建','已完结'],
		legend : ['我创建的','我完结的'],
		xAxis : [//最近七天天数值
			moment().subtract('days', 6).format('YYYY-MM-DD'),moment().subtract('days', 5).format('YYYY-MM-DD'),
			moment().subtract('days', 4).format('YYYY-MM-DD'),moment().subtract('days', 3).format('YYYY-MM-DD'),
			moment().subtract('days', 2).format('YYYY-MM-DD'),moment().subtract('days', 1).format('YYYY-MM-DD'),
			moment().format('YYYY-MM-DD')
		],
		series : [{
	        //name: '新创建',
	        name: '我创建的',
	        type: 'line',
	        data: [5, 20, 36, 10, 10, 20,11]
	    },
	    {
	        //name: '已完结',
	        name: '我完结的',
	        type: 'line',
	        data: [15, 25, 30, 11, 20, 9,20]
	    }]
	}

	function initDataChart(){
		$.ajax({
			url : root + 'control/workOrderPlatformStoreController/loadingHome2.do',
			type : 'post',
			data : null,
			dataType : 'json',
			success : function(resultData){
				//console.dir(resultData);
				if (resultData.result == 'success') {

					data.series = resultData.series;
					
					 // 基于准备好的dom，初始化echarts实例
				    var myChart = echarts.init(document.getElementById('data_content'));

				    // 指定图表的配置项和数据
				    var option = {
				        title: {
				            text: data.title
				        },
				        tooltip: {},
				        legend: {
				            data:data.legend
				        },
				        xAxis: {
				        	type: 'category',
				            boundaryGap: false,
				            data: data.xAxis
				        },
				        yAxis: {},
				        series: data.series
				    };

				    // 使用刚指定的配置项和数据显示图表。
				    myChart.setOption(option);
				}
			},
			error : function(e){
				alert(e);
			}
		});
	}

	//type 0当天，1昨天，2最近七天，3自定义
	function getTitleData(type,startDate,endDate){
		$.ajax({
			url : root + 'control/workOrderPlatformStoreController/loadingHome1.do',
			type : 'post',
			data : {
				startDate : startDate,
				endDate : endDate
			},
			dataType : 'json',
			success : function(resultData){

				if (resultData.result == 'success') {
					
					$("#created").html(resultData.created);
					$("#submited").html(resultData.submited);
					$("#ended").html(resultData.ended);
					$("#waitting").html(resultData.waitting);
					
				} else {
					alert(resultData.resultContent);
				}
				
			}
		});
	}

	//默认获取当天数据
	initDataChart();
	getTitleData(0,moment().format('YYYY-MM-DD'),moment().format('YYYY-MM-DD'));
		
})

	
</script>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
