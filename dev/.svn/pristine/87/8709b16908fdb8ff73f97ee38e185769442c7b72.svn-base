<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>LMIS</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/lmis/yuriy.jsp"%>
<link type="text/css" href="<%=basePath %>css/table.css"
	rel="stylesheet" />
<link type="text/css"
	href="<%=basePath %>daterangepicker/font-awesome.min.css"
	rel="stylesheet" />
<style type="text/css">
label {
	margin-right: 15px;
	font-size: 14px;
}
</style>
</head>
<body>
	<input id="lastPage" name="lastPage" style='display: none'
		value='${queryParam.maxResult}'>
	<input id="master_id" name="master_id" style='display: none'
		value='${queryParam.master_id}'>
	<input id="lastTotalCount" name="lastTotalCount" style='display: none'
		value='${totalSize}'>
	
	<div class= "title_div" id= "sc_title" style='height:38px'>
		<form id="diff">
			<table class= "table table-striped" style= "table-layout: fixed;overflow-x:show;" >
		   			<thead align="center">
						<tr class= "table_head_line"  >
						<td class='td_ch'>
						<input type="checkbox" id="checkAll" onclick="inverseCkb('ckb1234')"/></td>
							<td class="td_cs">账单名称</td>
							<td class="td_cs">汇总时间</td>
							<td class="td_cs">汇总人</td>
							<td>明细导出</td>
						</tr>
					</thead>
			</table>
			</form>
		</div>
		<div class= "tree_div" ></div>
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
			<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;overflow-x:show;" >
		  		<tbody align= "center">
			  			<c:forEach items= "${pageView.records }" var= "records" varStatus='status'>
				  		<tr ondblclick='toUpdate("${records.id}")'>
					  		<td class="td_ch" ><input id="ckb1234" name="ckb1234" type="checkbox" value="${records.id}"></td>
							<td class="td_cs"  title="${status.count }">${status.count }</td>
							<!-- <td class="td_cs"  title="${records.billingCycle }">${records.billingCycle }</td> -->
							<td class="td_cs" title="${records.create_time }">
							<fmt:formatDate value="${records.create_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							
							<td class="td_cs"  title="${records.username }">${records.username }</td>
							<td><a  class="td_cs" class="btn btn-xs btn-grey" onclick="uploade_expressbillmasterhx('${records.id}')">导出</a></td>
						</tr>
		  			</c:forEach>
		  		</tbody>
			</table>
		</div>
	<!-- 分页添加 -->
	<div style="margin-right: 5%; margin-top: 20px; margin-bottom: 10%;">${pageView.pageView}</div>
	<!-- 分页添加 -->
</body>
<script type="text/javascript">









	</script>
</html>
<style>
.select {
	padding: 3px 4px;
	height: 30px;
	width: 160px;
	text-align: enter;
}

.table_head_line td {
	font-weight: bold;
	font-size: 16px
}

.modal-header {
	height: 50px;
}
</style>
