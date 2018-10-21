var charts= [];
// 基于准备好的dom，初始化echarts实例
// 未处理工单统计
var untreatedWorkOrderChart;
// 工单处理状态统计
var workOrderProcessStatusChart;
// 组别处理工单统计
var groupProcessChart;
// 组别-人员处理工单统计
var employeeInGroupProcessChart;
function initUntreatedWorkOrderChart() {
    untreatedWorkOrderChart = echarts.init(document.getElementById('untreatedWorkOrder'));
    //
    charts.push(untreatedWorkOrderChart);
    // 指定图表的配置项和数据
    option = {
    	title: {
            text: '未处理工单统计',
            x:'center'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            	type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
        	orient: 'vertical',
        	left: 'left',
            data: []
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: true},
                magicType : {
                    show: true,
                    type: ['pie', 'funnel']
                },
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        grid: {
            left: '15%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis:  {
        	type: 'category',
            data: []
        },
        yAxis: {
        	type: 'value'
        },
        series: []
    };
    //使用刚指定的配置项和数据显示图表。
    untreatedWorkOrderChart.setOption(option);
	
}
function initWorkOrderProcessStatusChart() {
	workOrderProcessStatusChart= echarts.init(document.getElementById('workOrderProcessStatus'));
	//
	charts.push(workOrderProcessStatusChart);
	//
	option = {
		title: {
		    text: '工单处理状态统计',
		    x:'center'
		},
		tooltip: {
		    trigger: 'item',
		    formatter: "{a} <br/>{b} : {c} ({d}%)"
		},
		legend: {
		    orient: 'vertical',
		    left: 'left',
		    data: []
		},
		toolbox: {
	        show: true,
	        feature: {
	            mark: {show: true},
	            dataView: {show: true, readOnly: true},
	            magicType: {
	                show: true,
	                type: ['pie', 'funnel']
	            },
	            restore: {show: true},
	            saveAsImage: {show: true}
	        }
	    },
		series: [
		    {
		        name: '工单处理状态',
		        type: 'pie',
		        radius : '55%',
		        center: ['50%', '60%'],
		        data:[],
		        itemStyle: {
		            emphasis: {
		                shadowBlur: 10,
		                shadowOffsetX: 0,
		                shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};
	workOrderProcessStatusChart.setOption(option);
	
}
function initGroupProcessChart() {
	groupProcessChart= echarts.init(document.getElementById('groupProcess'));
	//
	charts.push(groupProcessChart);
	//
	option = {
		title: {
	        text: '组别处理工单统计'
	    },
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
	        	type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    legend: {
	        data: []
	    },
	    toolbox: {
	        show : true,
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: true},
	            magicType : {
	                show: true,
	                type: ['pie', 'funnel']
	            },
	            restore: {show: true},
	            saveAsImage: {show: true}
	        }
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis:  {
	    	type: 'category',
	        data: []
	    },
	    yAxis: {
	    	type: 'value'
	    },
	    series: []
	};
	//使用刚指定的配置项和数据显示图表。
	groupProcessChart.setOption(option);
	
}
function initEmployeeInGroupProcessChart() {
	employeeInGroupProcessChart= echarts.init(document.getElementById('employeeInGroupProcess'));
	//
	charts.push(employeeInGroupProcessChart);
	// 
	option = {
		title: {
	        text: '组别-人员处理工单统计'
	    },
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
	        	type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    legend: {
	        data: []
	    },
	    toolbox: {
	        show : true,
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: true},
	            magicType : {
	                show: true,
	                type: ['pie', 'funnel']
	            },
	            restore: {show: true},
	            saveAsImage: {show: true}
	        }
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis:  {
	    	type: 'category',
	        data: []
	    },
	    yAxis: {
	    	type: 'value'
	    },
	    series: []
	};
	//使用刚指定的配置项和数据显示图表。
	employeeInGroupProcessChart.setOption(option);
	
}
$(function(){
	//
	$(".chartDiv").height($(window).height() - 136);
	//
	$(".dateRange").daterangepicker(null, function(start, end, label) {});
	//
//	loadingUntreatedWorkOrderChart();
	//
//	loadingWorkOrderProcessStatusChart();
	//
//	loadingGroupProcessChart();
	//
	window.onresize = function(){
		for(var i = 0; i < charts.length; i++){
		    charts[i].resize();
		}
	};
	
})
// 加载未处理工单图表
function loadingUntreatedWorkOrderChart() {
	var dateRange= $("#dateRange1").val().split(" - ");
	var time1 = Date.parse(new Date(dateRange[0]));
    var time2 = Date.parse(new Date(dateRange[1]));
    var nDays = Math.abs(parseInt((time2 - time1)/1000/3600/24)) + 1;
    if(nDays> 7 || nDays< 0) {
    	alert("时间范围不能超过一周！");
    	return;
    	
    }
    //
	initUntreatedWorkOrderChart();
	untreatedWorkOrderChart.showLoading();
	$.ajax({
		type: "POST",
        url: "/BT-LMIS/control/workOrderController/loadingUntreatedWorkOrderChart.do",
        dataType: "json",
        data: {"dateRange": $("#dateRange1").val()},
        success: function(data) {
        	setTimeout("untreatedWorkOrderChart.hideLoading()", 800);
    		untreatedWorkOrderChart.setOption({
        		legend: {
        			data: data.subject
        			
        		},
        	    xAxis: {
        			data: data.dates
        			
        		},
        		series: data.series
        		
        	});
        		
        }
        
	}); 
	
}
//加载工单处理状态图表
function loadingWorkOrderProcessStatusChart() {
	var dateRange= $("#dateRange2").val().split(" - ");
	var time1 = Date.parse(new Date(dateRange[0]));
    var time2 = Date.parse(new Date(dateRange[1]));
    var nDays = Math.abs(parseInt((time2 - time1)/1000/3600/24)) + 1;
    if(nDays> 7 || nDays< 0) {
    	alert("时间范围不能超过一周！");
    	return;
    	
    }
    //
	initWorkOrderProcessStatusChart();
    workOrderProcessStatusChart.showLoading();
	$.ajax({
		type: "POST",
        url: "/BT-LMIS/control/workOrderController/loadingWorkOrderProcessStatusChart.do",
        dataType: "json",
        data: {"dateRange": $("#dateRange2").val()},
        success: function(data) {
        	setTimeout("workOrderProcessStatusChart.hideLoading()", 800);
        	workOrderProcessStatusChart.setOption({
        		legend: {
        			data: data.subject
        			
        		},
        		series: {
        			data: data.data
        			
        		}
        		
        	});
        	
        }
        
	}); 
	
}
//加载组别处理工单图表
function loadingGroupProcessChart() {
	var dateRange= $("#dateRange3").val().split(" - ");
	var time1 = Date.parse(new Date(dateRange[0]));
    var time2 = Date.parse(new Date(dateRange[1]));
    var nDays = Math.abs(parseInt((time2 - time1)/1000/3600/24)) + 1;
    if(nDays> 7 || nDays< 0) {
    	alert("时间范围不能超过一周！");
    	return;
    	
    }
    //
	initGroupProcessChart();
    groupProcessChart.showLoading();
	$.ajax({
		type: "POST",
        url: "/BT-LMIS/control/workOrderController/loadingGroupProcessChart.do",
        dataType: "json",
        data: {"dateRange": $("#dateRange3").val()},
        success: function(data) {
        	setTimeout("groupProcessChart.hideLoading()", 800);
    		groupProcessChart.setOption({
        		legend: {
        			data: data.subject
        			
        		},
        	    xAxis: {
        			data: data.groups
        			
        		},
        		series: data.series
        		
        	});
        	$("#employeeInGroupProcess").hide();
        	groupProcessChart.on('click', function(params){
        		if(params.componentType === 'series') {
        			if(params.seriesType === 'bar') {
        				if($("#employeeInGroupProcess").css("display") == "none") {
        					$("#employeeInGroupProcess").show();
        					
        				}
        				loadingEmployeeInGroupProcessChart(params.name);
        				
        			}
        			
        		} 
        		
        	});
        	
        }
        
	}); 
	
}
// 加载组别-人员处理工单图表
function loadingEmployeeInGroupProcessChart(group) {
	//
	initEmployeeInGroupProcessChart();
	employeeInGroupProcessChart.showLoading();
	$.ajax({
		type: "POST",
        url: "/BT-LMIS/control/workOrderController/loadingEmployeeInGroupProcessChart.do",
        dataType: "json",
        data: {"dateRange": $("#dateRange3").val(), "group": group},
        success: function(data) {
        	setTimeout("employeeInGroupProcessChart.hideLoading()", 800);
        	employeeInGroupProcessChart.setOption({
        		legend: {
        			data: data.subject
        			
        		},
        	    xAxis: {
        			data: data.employees
        			
        		},
        		series: data.series
        		
        	});
        	
        }
        
	});
	
}