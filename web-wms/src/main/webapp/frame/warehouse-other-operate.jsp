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
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse-other-operate.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<table id='fromTab' width="100%">
		<tr>
			<td class="label" width="20%"><s:text name="label.warehouse.transactiontype"/>: </td>
			<td width="20%">
				<s:text id="trantyselect" name="label.please.select"/>
				<s:select id="opType" headerKey="" headerValue="%{trantyselect}" name="direction" list="transactionTypes" loxiaType="select" listKey="id" listValue="name" >
				</s:select>
			</td>
			<td class="label" width="30%">
				<s:text name="label.warehouse.transactiontype.direction"/>：
				<input type="hidden" id="dircetion" value="" />
			</td>
			<td width="30%" id="oprType">
			</td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.warehouse.inpurchase.shop"/>：</td>
			<td>
				<div style="float: left">
					<select id="selShopId" name="sta.owner" loxiaType="select">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
					</select>
				</div>
				<div style="float: left">
					<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
				</div>
			</td>
			<td>
				<div id="shopValue"></div>
			</td>
			<td></td>
		</tr>
		<tr>
			<td class="label">相关单据号：</td>
			<td>
				<input loxiaType="input" trim="true" id="staSlipCode" name="sta.refSlipCode" />
			</td>
			<td>
			</td>
			<td>
			</td>
		</tr>
		<tr ids="Out_bound_address" class="hidden">
			<td colspan="2" class="label" style="width: 200px;"> 
				出库送货地址详细信息
			</td>
			<td></td><td></td><td></td>
		</tr>
		<tr ids="Out_bound_address" class="hidden">
			<td class="label">省:</td>
			<td><input loxiaType="input" value="" id="province" name="province" style="width: 150px;"/></td>
			<td class="label">市:</td>
			<td><input loxiaType="input" value="" id="city" name="city" style="width: 150px;"/></td>
			<td></td>
		</tr>
		<tr ids="Out_bound_address" class="hidden">
			<td class="label">区:</td>
			<td><input loxiaType="input" value="" id="district" name="district" style="width: 150px;"/></td>
			<td class="label">联系人:</td>
			<td><input loxiaType="input" value="" id="username" name="username" style="width: 150px;"/></td>
			<td></td>
		</tr>			 
		<tr ids='Out_bound_address' class="hidden">
			<td class="label">联系地址:</td>
			<td><input loxiaType="input" value="" id="address" name="address" style="width: 350px;"/></td>
			<td class="label">联系电话:</td>
			<td><input loxiaType="input" value="" id="telphone" name="telphone" style="width: 150px;"/></td>
			<td></td>
		</tr>	
	</table>
	<div id="tabs">
		<ul>
			<li><a href="#tabs-2"  id="exp_li"><s:text name="label.tabs.import"></s:text> </a></li>
		</ul>
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
								<textarea name="importMemo" loxiaType="input" rows="3"></textarea>
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
								<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_sta_other"></s:text>.xls&inputPath=tplt_import_warehouse_others_operate.xls"><s:text name="download.excel.template"></s:text></a>
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