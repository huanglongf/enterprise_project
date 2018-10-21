<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	    <link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
	    <script type="text/javascript" src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
	    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	    <script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
	    <script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
	</head>
	<script type="text/javascript">
		$(function(){ pollingRunningStatus(); });
		
		function pollingRunningStatus(){
			var jump= false;
			var currentFunction = arguments.callee;
			$.ajax({
				url: root + "/control/jobTaskController/pollingRunningStatus.do",
				type: "post",
				async: false,
				cache: false,
				data: {},
				dataType: "json",
				success: function(result) {
					// 存在运行中任务
					var current_node = $("#edit_row").next();
					var key = "";
					var flag = false;
					while(true){
						if(current_node.attr("id") != undefined){
							if(current_node.attr("id") == "end_row"){
								break;
								
							} else {
								// 每个任务的组合唯一性标识
								key = current_node.children().eq(2).text() + current_node.children().eq(3).text();
								for(var i = 0; i < result.obj.length; i++){
									if(key == (result.obj[i].jobGroup + result.obj[i].jobName)){
										current_node.children().eq(1).text("运行中");
										flag = true;
										break;
										
									}
									
								}
								if(!flag && current_node.eq(1).text() == "运行中"){
									current_node.children().eq(1).text("未运行");
									
								}
								current_node= current_node.next();
								continue;
								
							}
							
						}
						jump= true;
						break;
						
					}
					// 无运行中任务
					if(!jump) {
						setTimeout(currentFunction, 5000);
						
					}
					
				},
				error: function(result) {
					alert("监控运行状态异常！");
					
				}
				
			});
			
		}
		
		function validateAdd() {
			if ($.trim($('#jobName').val()) == '') {
				alert('name不能为空！');
				$('#jobName').focus();
				return false;
			}
			if ($.trim($('#jobGroup').val()) == '') {
				alert('group不能为空！');
				$('#jobGroup').focus();
				return false;
			}
			if ($.trim($('#cronExpression').val()) == '') {
				alert('cron表达式不能为空！');
				$('#cronExpression').focus();
				return false;
			}
			if ($.trim($('#beanClass').val()) == '' && $.trim($('#springId').val()) == '') {
				$('#beanClass').focus();
				alert('类路径和spring id至少填写一个');
				return false;
			}
			if ($.trim($('#methodName').val()) == '') {
				$('#methodName').focus();
				alert('方法名不能为空！');
				return false;
			}
			return true;
		};
		
		function changeJobStatus(jobId, cmd, jobType) {
			$.ajax({
				url: "${root }/control/jobTaskController/changeJobStatus.do",
				type: "POST",
				async: false,
				cache: false,
				data: {
					jobId: jobId,
					cmd: cmd
				},
				dataType: "JSON",
				success: function(data) {
					if (data.flag) {
						openDiv("${root}/control/jobTaskController/taskList.do?jobType=" + jobType);
					} else {
						alert(data.msg);
					}
				}
			});
		};
		
		function runTask(taskname, taskgroup, isconcurrent){
			$.ajax({
				url: "${root}/control/jobTaskController/runTask.do",
				type: "POST",
				async: false,
				cache: false,
				data: {
					jobName: taskname,
					jobGroup: taskgroup,
					isConcurrent: isconcurrent
				},
				dataType: "JSON",
				success: function(data) {
					alert(data.msg);
				}
			});
		};
		
		function updateCron(jobId) {
			var cron = prompt("输入cron表达式！", "")
			if (cron) {
				$.ajax({
					url : "${root}/control/jobTaskController/updateCron.do",
					type: "POST",
					async: false,
					cache: false,
					data: {
						jobId: jobId,
						cron: cron
					},
					dataType: "JSON",
					success: function(data) {
						if (data.flag) {
							openDiv("${root}/control/jobTaskController/taskList.do");
						} else {
							alert(data.msg);
						}

					}
				});
			}
		};
		
		function add(jobType) {
			if (validateAdd()) {
				$.ajax({
					url: "${root}/control/jobTaskController/add.do?jobType=" + jobType,
					type: "POST",
					async: false,
					cache: false,
					data: $("#addForm").serialize(),
					dataType: "JSON",
					success: function(data) {
						if (data.flag) {
							openDiv("${root}/control/jobTaskController/taskList.do?jobType=" + jobType);
						} else {
							alert(data.msg);
						}
					}
				});
			}
		};
	</script>
	<body style="text-align: center;" >
		<form id="addForm" method="post" >
			<div class="col-xs-12" style="margin-top: 10px;" >
				<table class="table table-striped table-bordered table-hover" >
			   		<thead>
				  		<tr>
				  			<td>任务状态</td>
				  			<td>运行状态</td>
				  			<td>任务组</td>
							<td>任务名</td>
							<td>描述</td>
							<td>是否同步</td>
							<td>类路径</td>
							<td>方法名</td>
							<td>cron表达式</td>
							<td style="display: none">spring id</td>
							<td colspan="2" align="center">操作</td>
				  		</tr>
			  		</thead>
			  		<tbody>
						<tr id="edit_row">
							<td>停&nbsp;&nbsp;用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="hidden" name="jobStatus" value="0" /></td>
							<td>未&nbsp;运&nbsp;行&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td><input type="text" name="jobGroup" id="jobGroup" width="10px;" /></td>
							<td><input type="text" name="jobName" id="jobName" /></td>
							<td><input type="text" name="description" id="description" /></td>
							<td><select name="isConcurrent" id="isConcurrent" ><option value="0">同步</option><option value="1">异步</option></select></td>
							<td><input type="text" name="beanClass" id="beanClass" /></td>
							<td><input type="text" name="methodName" id="methodName" /></td>
							<td><input type="text" name="cronExpression" id="cronExpression" /></td>
							<td style="display: none"><input type="text" name="springId" id="springId" /></td>
							<td colspan="2" align="center"><input type="button" onclick="add(${jobType })" value="保存" /></td>
						</tr>
				  		<c:forEach var="job" items="${taskList }">
							<tr id= "job_${job_id }" >
								<td>
									<c:choose>
										<c:when test="${job.jobStatus=='1' }">
											启用/<a href="javascript:;" onclick="changeJobStatus('${job.jobId}', 'stop', ${jobType })">停止</a>
										</c:when>
										<c:otherwise>
											停止/<a href="javascript:;" onclick="changeJobStatus('${job.jobId}', 'start', ${jobType })">开启</a>
										</c:otherwise>
									</c:choose>
								</td>
								<td>未运行</td>
								<td>${job.jobGroup }</td>
								<td>${job.jobName }</td>
								<td>${job.description }</td>
								<td><c:if test="${job.isConcurrent == 0 }">同步</c:if><c:if test="${job.isConcurrent == 1 }">异步</c:if></td>
								<td colspan="2" >${job.beanClass }.${job.methodName }();</td>	
								<td>${job.cronExpression }</td>
								<td style="display: none">${job.springId }</td>
								<td>
									<input type= "button" onclick= "updateCron('${job.jobId}');" value= "cron" />
								</td>
								<td>
									<input type= "button" onclick="runTask('${job.jobName }','${job.jobGroup }','${job.isConcurrent }');" value= "run" />
								</td>
							</tr>
						</c:forEach>
						<tr id= "end_row" style= "display: none" ></tr>
			  		</tbody>
				</table>
			</div>
		</form>
	</body>
</html>
