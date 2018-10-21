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
<link type="text/css" rel="stylesheet" href="<%=basePath %>My97DatePicker/skin/WdatePicker.css">

<script type="text/javascript" src="<%=basePath %>assets/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function(){
	});
	
	function poiImport(){
		document.getElementById("poiImport").submit()
	};
	
	function pageJump() {
      	openDiv('${root}control/warehouseFeeRawDataController/list.do?startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
	};
</script>
</head>

<body>
	<div style="margin-top: 10px; margin-left: 10px; margin-bottom: 10px;">
		<form id="poiImport" name="poiImport" action="${root}/control/warehouseFeeRawDataController/fileUpload.do" method="post" enctype="multipart/form-data">  
		    <div style="float: left;"><input type="file" name="file">  </div>
		    <div style="float: left;">
			    <button class="btn btn-xs btn-pink" onclick="poiImport();">
					<i class="icon-search icon-on-right bigger-110"></i>导入
				</button> 
			</div>
		</form>  
	</div>
	<div style="height: 400px;  border: solid #F2F2F2 1px; margin-top: 22px;">
		<table class="table table-striped">
			<thead align="center">
				<tr>
					<td nowrap="nowrap">仓库代码</td>
					<td nowrap="nowrap">仓库名称</td>
					<td nowrap="nowrap">结算时间</td>
					<td nowrap="nowrap">店铺</td>
					<td nowrap="nowrap">区域代码</td>
					<td nowrap="nowrap">区域名称</td>
					<td nowrap="nowrap">商品类型</td>
					<td nowrap="nowrap">存储类型</td>
					<td nowrap="nowrap">存储大小</td>
					<td nowrap="nowrap">存储单位</td>
					<td nowrap="nowrap">结算标识</td>
					<td nowrap="nowrap">创建人</td>
					<td nowrap="nowrap">创建时间</td>
					<td nowrap="nowrap">更新人</td>
					<td nowrap="nowrap">更新时间</td>
				</tr>
			</thead>
			<tbody align="center">
				<c:forEach items="${pageView.records}" var="data">
					<tr>
						<td  nowrap="nowrap">${data.warehouse_code}</td>
						<td  nowrap="nowrap">${data.warehouse_name}</td>
						<td  nowrap="nowrap"><fmt:formatDate value="${data.start_time}" type="both"/></td>
						<td  nowrap="nowrap">${data.store_name}</td>
						<td  nowrap="nowrap">${data.area_code}</td>
						<td  nowrap="nowrap">${data.area_name}</td>
						<td  nowrap="nowrap">${data.item_type}</td>
						<td  nowrap="nowrap">${data.storage_type}</td>
						<td  nowrap="nowrap">${data.storage_acreage}</td>
						<td  nowrap="nowrap">${data.acreage_unit}</td>
						<td  nowrap="nowrap">${data.settle_flag}</td>
						<td  nowrap="nowrap">${data.create_user}</td>
						<td  nowrap="nowrap"><fmt:formatDate value="${data.create_time}" type="both"/></td>
						<td  nowrap="nowrap">${data.update_user}</td>
						<td  nowrap="nowrap"><fmt:formatDate value="${data.update_time}" type="both"/></td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>
	<div style="margin-right: 5%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>
</body>
</html>
