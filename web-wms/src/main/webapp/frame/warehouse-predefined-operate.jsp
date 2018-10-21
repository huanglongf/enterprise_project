<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="label.warehouse.other.opertion"/></title>
<style>
	.ui-loxia-table tr.error{background-color: #FFCC00;}
	.divFloat{
		float: left;
		margin-right: 10px;
	}
</style>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse-predefined-operate.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<table width="100%">
		<tr>
			<td class="label" width="15%"><s:text name="label.warehouse.transactiontype"/>: </td>
			<td width="15%">
				<select loxiaType="select" id="opType" name="direction">
					<option value="">请选择</option>
					<option value="12">结算经销入库</option>
					<option value="14">代销入库</option>
					<option value="16">移库入库</option>
				</select>
			</td>
			<td class="label" width="15%">库存状态: </td>
			<td width="15%">
				<s:select id="invStatus" name="staLineCmd.invStatus.id" list="invStatusList" loxiaType="select" listKey="id" listValue="name" ></s:select>
			</td>
			<td class="label" width="15%"><s:text name="label.warehouse.inpurchase.shop"/>：</td>
			<td width="15%"> 
				<select id="selShopId" name="sta.owner" loxiaType="select">
					<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
				</select>
			</td>
			<td>
				<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
			</td>
		</tr>
	</table>
	<div id="tabs">
		<ul>
			<!-- <li><a href="#tabs-1"><s:text name="label.tabs.create"></s:text> </a></li> -->
			<li><a href="#tabs-2"  id="exp_li"><s:text name="label.tabs.import"></s:text> </a></li>
		</ul>
		<!-- <div id="tabs-1">
			
			<s:text id="trantyselect" name="label.warehouse.choose.transactiontype"/>
		  	<s:text id="shopselect" name="label.warehouse.choose.shop"/>
		   	<s:text id="warstaselect" name="label.warehouse.choose.inventory.state"/>
			
			<div id="divOp">
				<form id="op_form">
					<p></p>
					<div id="edittable" loxiaType="edittable">
						<table operation="add,delete" append="0" width="100%">
							<thead>
								<tr>
									<th width="1%"><input type="checkbox" /></th>
									<th width="15%"><s:text name="label.warehouse.goods.barcode"/></th>
									<th width="15%"><s:text name="label.warehouse.inpurchase.jmcode"/></th>
									<th width="15%"><s:text name="label.warehouse.inpurchase.extpro"/></th>
									<th width="15%"><s:text name="label.warehouse.inventory.state"/></th>
									<th width="10%"><s:text name="label.warehouse.inpurchase.count"/></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
							<tbody>
								<tr>
									<td><input type="checkbox"/></td>
									<td>
									<input loxiaType="input" name="staLineCmd.barCode" trim="true" checkmaster="checkbarcode"/>
									<input type="hidden" name="staLineCmd.skuId"  trim="true" size='4'/>
									</td>
									<td><input loxiaType="input" name="staLineCmd.jmCode" trim="true"  checkmaster="checkjmcode"/></td>
									<td><input loxiaType="input" name="staLineCmd.keyProperties" trim="true"  checkmaster="checkKeyProperties"/></td>
									<td>
										<s:select name="staLineCmd.invStatus.id" headerValue="%{warstaselect}" headerKey="" list="invStatusList" loxiaType="select" listKey="id" listValue="name" ></s:select>
									</td>
									<td><input loxiaType="number" name="staLineCmd.quantity" trim="true" mandatory="true"/></td>
								</tr>
							</tbody>
						</table>
					</div>		
				</form>
			</div>
			<br/>
			<div id="divRemork">
			<table width="80%">
				<tr>
					<td><s:text name="label.warehouse.location.comment"/></td>
				</tr>
				<tr>
					<td>
						<textarea id="txtRmk" name="sta.memo" loxiaType="input" rows="3"></textarea>
					</td>
				</tr>
			</table>
			</div>
			
			<div id="btnList" class="buttonlist">
				<button type="button" loxiaType="button" class="confirm" id="confirm"><s:text name="button.save"/></button>
			</div>
		</div> -->
		<div id="tabs-2">
			<div>
				<form method="POST" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
					<s:text name="label.warehouse.inpurchase.selectFile"></s:text>
					<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
					<table width="100%">
						<tr>
							<td align="left" style="text-align: left;"><s:text name="label.warehouse.location.comment"/></td>
						</tr>
						<tr>
							<td>
								<textarea name="importMemo" loxiaType="input" id="importMemo" rows="3"></textarea>
							</td>
						</tr>
					</table>
				</form>
				<div class="buttonlist">
					<table>
						<tr>
							<td>
								<button loxiaType="button" class="confirm" id="import"><s:text name="button.import"></s:text> </button>
							</td>
							<td>
								<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_warehouse_predefined"></s:text>.xls&inputPath=tplt_import_warehouse_predefined.xls"><s:text name="download.excel.template"></s:text></a>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<iframe id="upload" name="upload" class="hidden"></iframe>
	<iframe id="snsupload" name="snsupload" class="hidden"></iframe>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>