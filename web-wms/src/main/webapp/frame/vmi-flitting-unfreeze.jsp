<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%@include file="/common/meta.jsp"%>
		<title></title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/vmi-flitting-unfreeze.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
	</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="searchCondition">
	<form id="form_query">
	<table>
		<tr>
			<td class="label" width="20%">作业单号：</td>
			<td width="30%"><input loxiaType="input" trim="true" name="sta.code" /></td>
			
			<td class="label" width="20%"><s:text name="label.warehouse.inpurchase.shop"/></td>
			<td width="30%">
				<div style="float: left">
					<select id="companyshop" name="sta.owner" loxiaType="select">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
					</select>
				</div>
				<div style="float: left">
					<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">相关单据号：</td>
			<td><input loxiaType="input" trim="true" name="sta.refSlipCode" /></td>
			<td></td><td></td>
		</tr>
		</table>
	</form>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
	<div class="buttonlist">
		<button loxiaType="button" class="confirm" id="search"><s:text name="button.search"/></button>
		<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
	</div>
	
		<table id="tbl-query-info"></table>
		<div id="pager"></div>
	</div>
	<br />
	<div id="details" class="hidden">
			<input type="hidden" value="" id="staID"/>
			<table width="70%">
			<tr>
				<td class="label" width="15%">作业单号：</td>
				<td id="code" width="20%"></td>
				<td class="label" width="15%">备注：</td>
				<td id="memo" width="20%"></td>
				<td class="label" width="15%">状态：</td>
				<td id="status" width="20%"></td>
			</tr>
			<tr>
				<td class="label">仓库：</td>
				<td id="mainWhName"></td>
				<td class="label">店铺：</td>
				<td id="owner"></td>
				<td class="label">相关单据号：</td>
				<td id="slipcode"></td>
			</tr>
		</table>
		<br />
		<table id="tbl-details"></table>
		<br/>
		<table width="70%">
			<tr>
				<td width="15%" class="label">
					发票号
				</td>
				<td  width="30%">
					<input loxiaType="input" mandatory="true" id="invoiceNumber" trim="true" maxlength="100" />
				</td>
				<td width="15%">
				<select loxiaType="select" name="invoiceNumberTYPE" id="invoiceNumberTYPE" mandatory="true" readonly="readonly">
					<option value="">请选择</option>
					<option value="1">直送</option>
					<option value="3">非直送</option>
					<option value="5">进口</option>
					<option value="7">特许证产品</option>
					<option value="9">大连自提</option>
				</select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td width="15%" class="label">
					税收系数
				</td>
				<td  width="30%">
					<input loxiaType="number" mandatory="true" decimal="2" id="dutyPercentage" trim="true" />
				</td>
				<td width="15%" class="label">
					其他系数
				</td>
				<td  width="30%">
					<input loxiaType="number" mandatory="true" decimal="2" id="miscFeePercentage" trim="true"  />
				</td>
			</tr>
			<tr>
				<td class="label">
					总量
				</td>
				<td>
					<input loxiaType="number" mandatory="true" decimal="0" id="totalQty" trim="true"/>
				</td>
				<td class="label">
					成本
				</td>
				<td>
					<input loxiaType="number" mandatory="true" decimal="2" id="totalFOB" trim="true" />
				</td>
			</tr>
		</table>
		<div class="buttonlist">
			<button type="button" loxiaType="button" id="unfreeze" class="confirm">解冻</button>
			<button type="button" loxiaType="button" id="back">返回</button>
		</div>
	</div>
	</body>
</html>