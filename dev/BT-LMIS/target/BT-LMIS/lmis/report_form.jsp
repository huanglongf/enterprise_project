<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp"%>
		<title>LMIS报表</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<script type="text/javascript" src="<%=basePath %>echarts/echarts.min.js"></script>
	</head>
	
	<body>
		<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
		<div>
	    	<div id="myChart1" style="width: 500px;height:400px;float: left;"></div>
	    	<div id="myChart2" style="width: 500px;height:400px;float: right;"></div>
    	</div>
    	<hr>
    	<div>
			<table class="table table-striped">
		   		<thead>
			  		<tr>
			  			<td><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> 
			  			<td>字段A</td>
			  			<td>字段B</td>
			  			<td>字段C</td>
			  			<td>字段D</td>
			  			<td>字段E</td>
			  			<td>字段F</td>
			  		</tr>
		  		</thead>
		  		<tbody>
			  		<tr>
			  			<td><input id="ckb" name="ckb" type="checkbox" value="${employee.id}"></td>
			  			<td>测试</td>
			  			<td>测试</td>
			  			<td>测试</td>
			  			<td>测试</td>
			  			<td>测试</td>
			  			<td>测试</td>
			  		</tr>
			  		<tr>
			  			<td><input id="ckb" name="ckb" type="checkbox" value="${employee.id}"></td>
			  			<td>测试1</td>
			  			<td>测试1</td>
			  			<td>测试1</td>
			  			<td>测试1</td>
			  			<td>测试1</td>
			  			<td>测试1</td>
			  		</tr>
			  		<tr>
			  			<td><input id="ckb" name="ckb" type="checkbox" value="${employee.id}"></td>
			  			<td>测试</td>
			  			<td>测试</td>
			  			<td>测试</td>
			  			<td>测试</td>
			  			<td>测试</td>
			  			<td>测试</td>
			  		</tr>
			  		<tr>
			  			<td><input id="ckb" name="ckb" type="checkbox" value="${employee.id}"></td>
			  			<td>测试1</td>
			  			<td>测试1</td>
			  			<td>测试1</td>
			  			<td>测试1</td>
			  			<td>测试1</td>
			  			<td>测试1</td>
			  		</tr>
		  		</tbody>
			</table>
		</div>
	</body>
	<script type="text/javascript">
		// 基于准备好的dom，初始化echarts实例
        var myChart1 = echarts.init(document.getElementById('myChart1'));
        var myChart2 = echarts.init(document.getElementById('myChart2'));
        // 指定图表的配置项和数据
        var option1 = {
            title: {
                text: 'ECharts 入门示例'
            },
            tooltip: {},
            legend: {
                data:['销量','销量2']
            },
            xAxis: {
                data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
            },
            yAxis: {},
            series: [{
                name: '销量',
                type: 'bar',
                data: [5, 20, 36, 10, 10, 20]
            },{
                name: '销量2',
                type: 'bar',
                data: [10, 40, 72, 20, 20, 40]
            }]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart1.setOption(option1);
        
        var xAxisData = [];
        var data1 = [];
        var data2 = [];
        for (var i = 0; i < 100; i++) {
            xAxisData.push('类目' + i);
            data1.push((Math.sin(i / 5) * (i / 5 -10) + i / 6) * 5);
            data2.push((Math.cos(i / 5) * (i / 5 -10) + i / 6) * 5);
        }
        option2 = {
        	    title: {
        	        text: '柱状图动画延迟'
        	    },
        	    legend: {
        	        data: ['bar', 'bar2'],
        	        align: 'left'
        	    },
        	    toolbox: {
        	        // y: 'bottom',
        	        feature: {
        	            magicType: {
        	                type: ['stack', 'tiled']
        	            },
        	            dataView: {},
        	            saveAsImage: {
        	                pixelRatio: 2
        	            }
        	        }
        	    },
        	    tooltip: {},
        	    xAxis: {
        	        data: xAxisData,
        	        silent: false,
        	        splitLine: {
        	            show: false
        	        }
        	    },
        	    yAxis: {
        	    },
        	    series: [{
        	        name: 'bar',
        	        type: 'bar',
        	        data: data1,
        	        animationDelay: function (idx) {
        	            return idx * 10;
        	        }
        	    }, {
        	        name: 'bar2',
        	        type: 'bar',
        	        data: data2,
        	        animationDelay: function (idx) {
        	            return idx * 10 + 100;
        	        }
        	    }],
        	    animationEasing: 'elasticOut',
        	    animationDelayUpdate: function (idx) {
        	        return idx * 5;
        	    }
        	};

        myChart2.setOption(option2);
    </script>

</html>
