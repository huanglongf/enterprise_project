<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/lmis/yuriy.jsp"%>
<title>LMIS</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link type="text/css" rel="stylesheet" href="<%=basePath %>daterangepicker/font-awesome.min.css" >
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
<link type="text/css" rel="stylesheet" href="<%=basePath %>My97DatePicker/skin/WdatePicker.css">

<script type="text/javascript" src="<%=basePath %>assets/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	/**
	 * checkbox(全选&反选)
	 * 
	 * items 复选框的name
	 */
	function inverseCkb(items) {
		$('[name=' + items + ']:checkbox').each(function() {
			this.checked = !this.checked;
		});
	}

	/**
	 * 查询
	 */
		function find() {
			var storeName = $("#storeName").val();
			var goodsName = $("#goodsName").val();
			openDiv('${root}control/invitationController/list.do?storeName=' + storeName + '&goodsName=' + goodsName);
		}
	
	function pageJump() {
		openDiv('${root}control/contractController/list.do?startRow='
				+ $("#startRow").val() + '&endRow=' + $("#startRow").val()
				+ "&pageIndex=" + $("#pageIndex").val() + "&pageSize="
				+ $("#pageSize").val());
	}
	
</script>
</head>

<body>
  <div class="page-header"></div>
	<div style="margin-left: 20px; margin-bottom: 20px;">
	
				<table style="width: 80%;" class="form_table">
					<tr align="center">
						<td class="left" width="33%"><label class="no-padding-right blue" for="form-field-1">店铺&nbsp;: </label></td>
						<td class="left" width="33%"><input id="storeName" disabled="disabled" name="name" type="text" id="form-field-1" placeholder="" value="${queryParam.storeName}" datatype="s6-18" errormsg="信息至少6个字符,最多18个字符！" /></td>
					</tr>				
					<tr>
						<td class="left" width="33%"><label class="no-padding-right blue" for="form-field-1">商品名称&nbsp;: </label></td>
						<td class="left" width="33%"><input id="goodsName" disabled="disabled" name="name" type="text" id="form-field-1" placeholder="" value="${queryParam.goodsName}" datatype="s6-18" errormsg="信息至少6个字符,最多18个字符！" /></td>
					</tr>
		          </table>
	</div>
	<div style="margin-top: 10px; margin-left: 10px; margin-bottom: 10px;">
		<button class="btn btn-xs btn-pink" onclick="find();">
			<i class="icon-search icon-on-right bigger-110"></i>查询
		</button>
		&nbsp;
	</div>
	<div style="height: 400px; overflow: auto; overflow:scroll; border: solid #F2F2F2 1px; margin-top: 22px;">
		<table class="table table-striped">
			<thead align="center">
				<tr>
					<td nowrap="nowrap"><input type="checkbox" id="checkAll"onclick="inverseCkb('ckb')" /></td>
				   <td nowrap="nowrap">店铺</td>
					<td nowrap="nowrap">作业单类型</td>
					<td nowrap="nowrap">作业单号</td>
					<td nowrap="nowrap">库位编码</td>
					<td nowrap="nowrap">入库数量</td>
					<td nowrap="nowrap">出库数量</td>
					<td nowrap="nowrap">商品编码</td>
					<td nowrap="nowrap">sku编码</td>
					<td nowrap="nowrap">货号</td>
					<td nowrap="nowrap">商品名称</td>
					<td nowrap="nowrap">商品大小</td>
					<td nowrap="nowrap">库存状态</td>
					<td nowrap="nowrap">操作员</td>
				</tr>
                   <tr>
			  		     <td></td>
			  		     <% for(int i=1;i<=13;i++){ %>
<%-- 			  	<c:forEach items="${power}" var="power"> --%>
                        <td nowrap="nowrap">
						  <span class="input-icon input-icon-right">
						    <select style="text-align:center;">
						    <option value="0">≈</option>
						    <option value="1">=</option>
						    <option value="2">></option>
						    <option value="3">>=</option>
						    <option value="4"><</option>
						    <option value="5"><=</option>
						    </select>
							<input type="text" id="form-field-icon-2" style="width:80px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						 </span>
						</td>
						 <%} %>
<%-- 		  		</c:forEach> --%>
			  		</tr>  								
			</thead>
			<tbody align="center">
		  		<c:forEach items="${pageView.records}" var="res">
			  		<tr>
			  			<td><input id="ckb" name="ckb" type="checkbox" value="${res.id}"></td>
			  			<td>${res.store_name}</td>
			  			<td>${res.job_type}</td>
			  			<td nowrap="nowrap">${res.job_orderno}</td>
			  			<td nowrap="nowrap">${res.storaggelocation_code}</td>
			  			<td>${res.in_num}</td>
			  			<td>${res.out_num}</td>
			  			<td>${res.item_number}</td>
			  			<td>${res.sku_number}</td>
			  			
			  			<td nowrap="nowrap">${res.art_no}</td>
			  			<td nowrap="nowrap">${res.item_name}</td>
			  			<td>${res.item_size}</td>
			  			<td>${res.inventory_status}</td>
			  			<td>${res.operator}</td>
			  		</tr>
		  		</c:forEach>
			</tbody>
		</table>
	</div>
	<div style="margin-right: 30px; margin-top: 20px;">${pageView.pageView}</div>
</body>
</html>
