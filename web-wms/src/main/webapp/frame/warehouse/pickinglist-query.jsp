<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.dispatchList.title"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/pickinglist-query.js' includeParams='none' encode='false'/>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	
<div id="div1">
	<form method="post" id="query-form">
		<span class="label" style="font-size: 15px;" >基础条件</span>
		<table id="filterTable">
			<tr>
				<td class="label">配货清单号：</td>
				<td>
					<input loxiaType="input" name="pickingList.code" trim="true"/>
				</td>
				<td class="label">物流服务商：</td>
				<td>
					<select loxiaType="select" name="pickingList.lpcode" id="selLpcode">
						<option value="">--请选择物流服务商--</option>
					</select>
				</td>
				
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button type="button" id="btn-query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
		<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
	</div>
	<div id="div-waittingList">
		<table id="tbl-waittingList"></table>
		<div id="pager_query"></div>
	</div>
</div>
<div id="div2" class="hidden">
	<div id="pickingListId" class="hidden"></div>
	<table width="90%">
		<tr>
			<td class="label"><s:text name="label.warehouse.pl.batchno"></s:text> </td>
			<td>
				<label id="dphCode"></label>
				<div id="hidden-waittingList" class="hidden" style="position: absolute;z-index: 99999"></div>
			</td>
			<td class="label"><s:text name="label.warehouse.pl.status"></s:text> </td>
			<td id="dphStatus"></td>
			<td class="label">物流</td>
			<td id="tdIdLpcode"></td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.wahrhouse.sta.creater"></s:text> </td>
			<td width="20%" id="creator" ></td>
			<td class="label" class="15%"><s:text name="label.wahrhouse.sta.operator"></s:text> </td>
			<td id="operator"></td>
			<td class="label">是否包含sn订单</td>
			<td id="isSn"></td>
		</tr>
		<tr>
			<td class="label" width="15%"><s:text name="label.warehouse.pl.pltotal"></s:text> </td>
			<td width="15%" id="planBillCount">0</td>
			<td class="label" width="15%"><s:text name="label.warehouse.pl.plcomplete"></s:text> </td>
			<td width="15%" id="checkedBillCount">0</td>
			<td class="label" width="15%"><s:text name="label.warehouse.pl.plship"></s:text> </td>
			<td width="15%" id="sendStaQty"></td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.warehouse.pl.prototal"></s:text> </td>
			<td id="planSkuQty"></td>
			<td class="label"><s:text name="label.warehouse.pl.procomplete"></s:text> </td>
			<td id="checkedSkuQty">0</td>
			<td class="label"><s:text name="label.warehouse.pl.proship"></s:text> </td>
			<td id="sendSkuQty">0</td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.wahrhouse.pl.picking.time"></s:text> </td>
			<td id="pickingTime"></td>
			<td class="label"><s:text name="label.wahrhouse.pl.last.checked.time"></s:text> </td>
			<td id="checkedTime"></td>
			<td class="label"><s:text name="label.wahrhouse.pl.last.send.time"></s:text> </td>
			<td id="lastSendTime"></td>
		</tr>
	</table>
	<br /><br />
	<div id="divTbDetial"><table id="tbl-orderDetail"></table></div>
	
	<div id="btnlist" class="buttonlist">
		<button loxiaType="button" id="plCancel" class="confirm"><s:text name="button.cancel"></s:text> </button>
		<button loxiaType="button" id="back2"><s:text name="button.back"></s:text></button>
	</div>
</div>
<div class="hidden">
	<OBJECT ID='emsObject' name='emsObject' CLASSID='CLSID:53C732B2-2BEA-4BCD-9C69-9EA44B828C7F' align=center hspace=0 vspace=0></OBJECT>
</div>
<iframe id="frmSoInvoice" class="hidden"></iframe>
<iframe id="exportFrame" class="hidden" target="_blank"></iframe>
</body>
</html>