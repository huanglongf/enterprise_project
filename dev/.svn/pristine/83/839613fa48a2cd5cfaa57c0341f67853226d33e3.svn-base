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
		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
	</head>

	<script type="text/javascript">
		function inverseCkb(items) {
			$('[name=' + items + ']:checkbox').each(function() {
				this.checked = !this.checked;
			});
		}

		/**
		 * 查询
		 */
		function find() {
			var sku_number = $("#sku_number").val();
			var reservation = $("#reservation").val();
			var store_id = $('#store_id').val();
			var settle_flag = $('#settle_flag').val();
			openDiv('${root}/control/lmis/invitationUseanmountDataController/list.do?sku_number=' + sku_number + '&settle_flag='+settle_flag + '&store_id='+store_id);
		}

		function pageJump() {
			
      		openDiv('${root}control/lmis/invitationUseanmountDataController/list.do?startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
		}
		$(document).ready(function() {
			$("#reservation").daterangepicker(null, function(start, end, label) {
				console.log(start.toISOString(), end.toISOString(), label);
			});
			
			
			
			$('#settle_flag').val("${queryParam.settle_flag}");
			$('#store_id').val("${queryParam.store_id}");
		});
	</script>

	<body>
		<div class="page-header"></div>
		<div style="margin-left: 20px; margin-bottom: 20px;">
			<table>
				<tr>
					<td width="10%">耗材编码:</td>
					<td width="20%"><input id="sku_number" name="sku_number" type="text"
						value="${queryParam.sku_number}" /></td>
					<td width="10%">时间段:</td>
					<td width="20%">
						<div class="controls">
							<div class="input-prepend input-group">
								<span class="add-on input-group-addon"> <i
									class="glyphicon glyphicon-calendar fa fa-calendar"></i></span> <input
									type="text" readonly style="width: 100%" name="reservation"
									id="reservation" class="form-control"
									value="2016-6-7 - 2016-6-7" />
							</div>
						</div>
					</td>
	 
				</tr>
				<tr>
					<td width="10%">所属店铺:</td> 
					<td width="20%"><select id="store_id" name="store_id"  class='selectpicker'>
						<option value= -1>---请选择---</option>
						<c:forEach items="${storeList}" var = "store" >
							<option  value="${store.id}">${store.store_name}</option>
							
						</c:forEach>
						
					</select></td>
					<td width="10%">结算标识:</td>
					<td width="20%"><select id="settle_flag" name="settle_flag"
						 class='selectpicker'>
							<option value= -1>---请选择---</option>
							<option  value="1">已结算</option>
							<option  value="0">未结算</option>
					</select></td>
	
				</tr>
			</table>
		</div>
		<div style="margin-top: 10px; margin-left: 10px; margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="find();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
	
		</div>
		<div style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px; margin-top: 22px;">
			<table class="table table-striped">
				<thead align="center">
					<tr>
						<td><input type="checkbox" id="checkAll"
							onclick="inverseCkb('ckb')" /></td>
		
						<td>创建时间</td>
						<td>创建人</td>
						<td>更新时间</td>
						<td>更新人</td>
						<td>耗材编码</td>
						<td>使用量</td>
						<td>时间段</td>
	
						<td>店铺</td>
						<td>结算标识</td>
					<tr>
						<td></td>
						<%
							for (int i = 1; i <= 9; i++) {
						%>
						<%-- 			  	<c:forEach items="${power}" var="power"> --%>
						<td nowrap="nowrap"><span class="input-icon input-icon-right">
								<select style="text-align: center; height: 27px;">
									<option value="0">≈</option>
									<option value="1">=</option>
									<option value="2">></option>
									<option value="3">>=</option>
									<option value="4"><</option>
									<option value="5"><=</option>
							</select> <input type="text" id="form-field-icon-2" style="width: 80px;" />
								<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span></td>
						<%
							}
						%>
						<%-- 		  		</c:forEach> --%>
					</tr>
				</thead>
	
				<tbody align="center">
					<c:forEach items="${pageView.records}" var="power">
						<tr>
							<td><input id="ckb" name="ckb" type="checkbox"
								value="${power.id}"></td>
							<td><fmt:formatDate value="${power.create_time}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${power.create_user}</td>
							<td><fmt:formatDate value="${power.update_time}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${power.update_user}</td>
							<td>${power.sku_number}</td>
							<td>${power.application_amount}</td>
	
							<td><fmt:formatDate value="${power.start_time}"
									pattern="yyyy-MM-dd HH:mm:ss" /> - <fmt:formatDate
									value="${power.end_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${power.store_name}</td>
							<td><c:if test="${power.settle_flag=='0'}">未结算</c:if> <c:if
									test="${power.settle_flag=='1'}">已结算</c:if></td>
	
						</tr>
					</c:forEach>
	
				</tbody>
			</table>
		</div>
		<!-- 分页添加 -->
		<div style="margin-right: 5%; margin-top: 20px; margin-bottom: 10%;">${pageView.pageView}</div>
	</body> 
</html>
