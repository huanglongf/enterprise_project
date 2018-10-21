<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/lmis/yuriy.jsp"%>
<title>LMIS</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
<script type="text/javascript" src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
<script type="text/javascript" src="<%=basePath %>js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
<link rel="stylesheet" href="<%=basePath %>validator/css/style.css" type="text/css" media="all" />
<link href="<%=basePath %>validator/css/demo.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>/css/table.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=basePath %>validator/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">

function check(id){
	openDiv('${root}/control/differenceController/imp_list.do?id='+id);
}
function c_imp(id){
	openDiv('${root}/control/differenceController/imp.do?id='+id);
}
</script>
</head>

<body>
	<div class="page-header" align="left">
		<h1>时效导入</h1>
	</div>
	<span style="color: red;">错误${size}条!</span>
	<hr>
	<button class="btn btn-xs btn-yellow" onclick="check('${id}');">
		<i class="icon-hdd"></i>校验
	</button>&nbsp;
	<button class="btn btn-xs btn-yellow" onclick="c_imp('${bat_id}');">
		<i class="icon-hdd"></i>导入
	</button>&nbsp;
	<div style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px; margin-top: 22px;">
		<form id= "main_search" >
			<table class="table table-striped">
				<thead align="center">
					<tr>	
						<td>状态</td>
						<td>错误信息</td>
						<td>物流商</td>
						<td>产品类型</td>
						<td>物理仓</td>
						<td>揽件截止时间</td>
						<td>目的省</td>
						<td>目的市</td>
						<td>目的区</td>
						<td>目的街道</td>
						<td>预警类型</td>
						<td>时效</td>
						<td>时效单位</td>
						<td>到达省</td>
						<td>到达市</td>
						<td>到达区</td>
						<td>到达街道</td>
					</tr>
				</thead>
				
				<tbody align="center">
			  		<c:forEach items="${impList}" var="impList">
						<tr>	
							<td>
								<c:if test="${impList.flag==0}">成功</c:if>
								<c:if test="${impList.flag==1}">
									<span class="badge badge-transparent tooltip-error" title="2&nbsp;Important&nbsp;Events">
										<i class="icon-warning-sign red bigger-130"></i>
									</span>
								</c:if>
							</td>
							<td><span style="color: red;">${impList.error_msg }</span></td>
							<td>${impList.field1 }</td>
							<td>${impList.field2 }</td>
							<td>${impList.field3 }</td>
							<td>
								<fmt:parseDate value="${impList.field4 }" pattern="yyyy-MM-dd HH:mm:ss" var="test"/>   
								<fmt:formatDate value="${test}" pattern="HH:mm:ss"/> 
							</td>
							<td>${impList.field5 }</td>
							<td>${impList.field6 }</td>
							<td>${impList.field7 }</td>
							<td>${impList.field8 }</td>
							<td>${impList.field9 }</td>
							<td>${impList.field10 }</td>
							<td>${impList.field11 }</td>
							<td>${impList.field12 }</td>
							<td>${impList.field13 }</td>
							<td>${impList.field14 }</td>
							<td>${impList.field15 }</td>
							<td>${impList.field16 }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>
