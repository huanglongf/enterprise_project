<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type="text/css" href="<%=basePath %>css/table.css" rel="stylesheet" />
		
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common_view.js"></script>
		<script type="text/javascript" src="<%=basePath %>validator/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>radar/physical_warehouse/js/physical_warehouse_list.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
	</head>
	<body>
		<div class="page-header" align="left">
			<h1>物理仓信息查询</h1>
		</div>
		<div class="page-header" style="margin-left : 20px; margin-bottom : 20px;">
			<table>
		  		<tr>
		  			<td align= "right" width="10%"><label class="blue" for="physicalWarehouse_code">物理仓代码&nbsp;:</label></td>
		  			<td width="20%">
		  				<input id="physicalWarehouse_code" name="physicalWarehouse_code"/>
		  			</td>
		  			<td align= "right" width="10%"><label class="blue" for="physicalWarehouse_name">物理仓名称&nbsp;:</label></td>
		  			<td width="20%">
		  				<input id="physicalWarehouse_name" name="physicalWarehouse_name"/>
		  			</td>
		  		</tr>
			</table>
		</div>
		<div style="margin-top : 10px; margin-bottom: 10px; margin-left : 20px;">
			<button class="btn btn-xs btn-pink" onclick="pageJump();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>&nbsp;
			<button class="btn btn-xs btn-yellow" onclick="toForm();">
				<i class="icon-hdd"></i>新增
			</button>&nbsp;
			<button class="btn btn-xs btn-inverse" onclick="del();">
				<i class="icon-trash"></i>删除
			</button>&nbsp;	
			<button class="btn btn-xs btn-danger" onclick="submitBatch();">
				<i class="icon-thumbs-up"></i>提交
			</button>&nbsp;	
		</div>
		<div id="partDiv">
			<%@ include file="/radar/physical_warehouse/physical_warehouse_list_div.jsp" %>
		</div>
	</body>
</html>