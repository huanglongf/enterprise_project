<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse-predefined-cancel.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	<div id="div-sta-list" >
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
				<td class="label">相关单据号</td>
				<td><input loxiaType="input" trim="true" name="sta.refSlipCode" /></td>
				<td class="label"></td>
				<td></td>
			</tr>
			<tr>
				<td class="label">创建时间：</td>
				<td>
					<input loxiaType="date" trim="true" name="startTime" showTime="true"/></td>
				<td class="label" style="text-align:center"><s:text name="label.warehouse.pl.to"/></td>
				<td><input loxiaType="date" trim="true" name="endTime" showTime="true"/></td>
			</tr>
			<tr>
				<td class="label">到货时间：</td>
				<td>
					<input loxiaType="date" trim="true" name="arriveStartTime" showTime="true"/></td>
				<td class="label" style="text-align:center"><s:text name="label.warehouse.pl.to"/></td>
				<td>
					<input loxiaType="date" trim="true" name="arriveEndTime" showTime="true"/>
				</td>
			</tr>
			<tr>
			<td class="label" width="15%">状态 </td>
			<td width="15%">
				<select loxiaType="select" id="opType" name="intStaStatus">
					<option value="">请选择</option>
					<option value="1">已创建</option>
					<option value="5">部分转入</option>
				</select>
			</td>
			<td class="label" width="15%"><s:text name="label.warehouse.transactiontype"/>: </td>
			<td width="15%">
				<select loxiaType="select" id="opType" name="intStaType">
					<option value="">请选择</option>
					<option value="12">结算经销入库</option>
					<option value="14">代销入库</option>
				</select>
			</td>
			</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search"><s:text name="button.search"/></button>
			<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
		</div>
		<table id="tbl-inbound-purchase"></table>
		<div id="pager"></div>
	</div>
	<div id="order-detail" class="hidden">
		<table>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
					<td width="40%" colspan="1"><span id="createTime"></span></td>
					<td width="10%" class="label"><s:text name="label.warehouse.sta.finish"></s:text> </td>
					<td width="40%" colspan="1"><span id="finishTime"></span></td>
				</tr>
				<tr>
					<td width="15%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
					<td width="35%"><span id="code"></span></td>
					<td width="15%" class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
					<td width="35%"><span id="refSlipCode"></span></td>
				</tr>
				<tr>
					<td width="15%" class="label"> 作业类型</td>
					<td width="35%"><span id="type"></span></td>
					<td width="15%" class="label"><s:text name="label.warehouse.sta.shop"></s:text> </td>
					<td width="35%"><span id="shopId"></span></td>
				</tr>
		</table>
		<br />
		<div id="detail_tabs">
				<table id="tbl-order-detail"></table>
				<div id="pagerDetail"></div>
		</div>
		<div class="buttonlist">
		    <button loxiaType="button"  id="back"><s:text name="button.back"></s:text></button>
			<button loxiaType="button" class="confirm" id="cancel"><s:text name="button.cancel"></s:text></button>
			<button loxiaType="button" class="confirm" id="close">关闭作业单（不在到货）</button>
		</div>
		<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>