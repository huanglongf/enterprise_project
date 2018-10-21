<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name= "description" content= "overview &amp; stats" />
		<meta name= "viewport" content= "width=device-width, initial-scale=1.0" />
		<link rel= "stylesheet" type= "text/css" media= "all" href= "<%=basePath %>daterangepicker/font-awesome.min.css" />
		<link rel= "stylesheet" type= "text/css" media= "all" href= "<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
		<link rel= "stylesheet" type= "text/css" media= "all" href= "<%=basePath %>assets/css/datepicker.css" />
		<link rel= "stylesheet" type= "text/css" media= "all" href= "<%=basePath %>work_order/wo_management/css/wo_summary_chart.css" />
		
		<script type= "text/javascript" src= "<%=basePath %>assets/js/date-time/bootstrap-datepicker.min.js" ></script>
	    <script type= "text/javascript" src= "<%=basePath %>assets/js/date-time/locales/bootstrap-datepicker.zh-CN.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>daterangepicker/moment.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>daterangepicker/daterangepicker.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>echarts/echarts.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>work_order/wo_management/js/wo_summary_chart.js" ></script>
	</head>
	<body>
		<div class= "page-header no-margin-bottom" align= "left" >
			<h1>工单汇总图表</h1>
		</div>
		<div class= "chartDiv container-fruid" >
			<div class= "row form-group" >
				<div class= "col-xs-2" style= "text-align: right;" >
                	<label class= "no-padding-right blue" for= "dateRange1" style= "white-space: nowrap;" >
              			日期范围&nbsp;: 
                    </label>
                </div>
                <div class= "col-xs-4" style= "text-align: left;" >
                	<div class= "col-xs-8 input-group" >
						<input id= "dateRange1" name= "dateRange1" class= "form-control dateRange" type= "text" readonly value= "${defaultStartDate } - ${defaultEndDate }" />
						<span class="input-group-addon">
							<i class="icon-calendar bigger-110"></i>
						</span>
					</div>
					<div class="control-btn-panel">
						<a href="javascript:;" onclick="loadingUntreatedWorkOrderChart();" class="btn btn-primary btn-sm">Run</a>
					</div>
                </div>
                <div class= "col-xs-2" style= "text-align: right;" >
	          		<label class= "no-padding-right blue" for= "dateRange2" style= "white-space: nowrap;" >
	      				日期范围&nbsp;: 
	                </label>
	            </div>
	            <div class= "col-xs-4" style= "text-align: left;" >
	            	<div class= "col-xs-8 input-group" >
						<input id= "dateRange2" name= "dateRange2" class= "form-control dateRange" type= "text" readonly value= "${defaultStartDate } - ${defaultEndDate }" />
						<span class="input-group-addon">
							<i class="icon-calendar bigger-110"></i>
						</span>
					</div>
					<div class="control-btn-panel">
						<a href="javascript:;" onclick="loadingWorkOrderProcessStatusChart();" class="btn btn-primary btn-sm">Run</a>
					</div>
	            </div>
			</div>
			<div class= "row form-group" >
				<div id= "untreatedWorkOrder" class= "col-xs-6 left-container" style= "height: 500px" >
				</div>
				<div id= "workOrderProcessStatus" class= "col-xs-6 right-container" style= "height: 500px" >
				</div>
			</div>
			<div class= "row form-group" >
				<div class= "col-xs-2" style= "text-align: right;" >
                	<label class= "no-padding-right blue" for= "dateRange3" style= "white-space: nowrap;" >
              			日期范围&nbsp;: 
                    </label>
                </div>
                <div class= "col-xs-4" style= "text-align: left;" >
                	<div class= "col-xs-8 input-group" >
						<input id= "dateRange3" name= "dateRange3" class= "form-control dateRange" type= "text" readonly value= "${defaultStartDate } - ${defaultEndDate }" />
						<span class="input-group-addon">
							<i class="icon-calendar bigger-110"></i>
						</span>
					</div>
					<div class="control-btn-panel">
						<a href="javascript:;" onclick="loadingGroupProcessChart();" class="btn btn-primary btn-sm">Run</a>
					</div>
                </div>
			</div>
			<div class= "row form-group" >
				<div id= "groupProcess" class= "col-xs-6 left-container" style= "height: 500px;" >
				</div>
				<div id= "employeeInGroupProcess" class= "col-xs-6 right-container" style= "height: 500px; display: none;" >
				</div>
			</div>
		</div>
	</body>
</html>