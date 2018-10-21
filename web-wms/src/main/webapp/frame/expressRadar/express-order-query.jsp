<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title><s:text
		name="baseinfo.warehouse.sales.dispatchList.title" /></title>

<script type="text/javascript"
	src="<s:url value='/scripts/frame/expressRadar/express-order-query.js' includeParams='none' encode='false'/>"></script>

</head>
<body style="background-color: #f2f2f2;"
	contextpath="<%=request.getContextPath()%>">
	<!-- 这里是页面内容区 -->

	<div id="div1" class="hidden1">
		<form method="post" id="query-form">
			<table id="filterTable">
				<tr>
					<td class="label">物流服务商：</td>
					<td>
						<select id="lpCode" loxiaType="select">
							<option value="">请选择</option>
						</select>
					</td>
					<td class="label" width="15%">快递单号：</td>
					<td width="15%"><input loxiaType="input"
						name="expressCode" id="expressCode" trim="true"></td>
					<td class="label" width="15%">平台订单号：</td>
					<td><input loxiaType="input" name="pCode"
						id="pCode" trim="true"></td>
				</tr>
				<tr>
					<td class="label">发件仓库：</td>
					<td>
						<select loxiaType="select" id="pwhName">
						<option value="">请选择</option>
						</select>
					</td>
					<td class="label">店铺：</td>
					<td width="15%">
						<select id="owner" loxiaType="select">
							<option value="">请选择</option>
						</select>
					</td>
					<td class="label">省：</td>
					<td>
						<select loxiaType="select" id="province">
						<option value="">请选择</option>
						</select>
					</td>
					<td class="label">市：</td>
					<td>
						<select loxiaType="select" id="city">
						<option value="">请选择</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">快递状态：</td>
					<td>
						<select loxiaType="select" id="status">
						<option value="">请选择</option>
						</select>
					</td>
					<td class="label">预警类型：</td>
					<td>
						<select loxiaType="select" id="warningType">
						<option value="">请选择</option>
						</select>
					</td>
					<td class="label">预警级别：</td>
					<td>
						<select loxiaType="select" id="warningLv">
						<option value="">请选择</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">作业单号：</td>
					<td><input loxiaType="input" name="orderCode"
					id="orderCode" trim="true" /></td>
					<td class="label">揽件时间：</td>
					<td><input loxiaType="date" name="startDate" id="startDate" showTime="true">
					</td>
					<td class="label">到：</td>
					<td><input loxiaType="date" name="endDate" id="endDate" showTime="true" />
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" id="btn-query" loxiaType="button"
				class="confirm">
				查询
			</button>
			<button type="button" loxiaType="button" id="reset">
				<s:text name="button.reset"></s:text>
			</button>
			<font class="label" style="color:red">　　默认查询近三个月的单据，否则请加上时间条件！</font>
		</div>
		<div id="div-expressInfoList" class="hidden1">
			<table id="tbl-expressInfoList"></table>
			<div id="express_order_pager_query"></div>
		</div>
	</div>
	<div id="div2" class="hidden">
		<div id="selectId" class="hidden"></div>
		<table width="90%">
			<tr>
				<td class="label">物流服务商：</td>
				<td width="15%" id="lpCode1">0</td>
				<td class="label">快递单号：</td>
				<td width="15%" id="expressCode1">0</td>
				<td class="label">作业单号：</td>
				<td width="15%" id="orderCode1">0</td>
				<td class="label">相关单据号：</td>
				<td width="15%" id="refSlipCode">0</td>

			</tr>
			<tr>
				<td class="label">平台订单号：</td>
				<td width="15%" id="pCode1">0</td>
				<td class="label">店铺：</td>
				<td width="15%" id="owner1">0</td>
			</tr>
			<tr>
				<td class="label">发件仓库：</td>
				<td width="15%" id="pwhName1">0</td>
				<td class="label">省：</td>
				<td width="15%" id="province1">0</td>
				<td class="label">市：</td>
				<td width="15%" id="city1"></td>

			</tr>
			<tr>
				<td class="label">收件人：</td>
				<td width="15%" id="receiver1"></td>
				<td class="label">联系电话：</td>
				<td width="15%" id="mobile1"></td>
				<td class="label">详细收货地址：</td>
				<td width="15%" id="address1"></td>
			</tr>
			<tr>
				<td class="label">快递状态：</td>
				<td width="15%" id="status1"></td>
				<td class="label">预警类型：</td>
				<td width="15%" id="warningType1"></td>
				<td class="label">预警级别：</td>
				<td width="15%" id="warningLv1"></td>
			</tr>
		</table>
		<br /> <br />

		<div id="btnlist" class="buttonlist">
		</div>
	</div>
	<div id="tabs" class="hidden">
		<ul>
			<li><a href="#tabs_1">快递明细</a></li>
			<li><a href="#tabs_2">订单明细</a></li>
			<li><a href="#tabs_3">物流明细</a></li>
		</ul>
		<div id="tabs_1">
			<s:hidden id="statusName" name="wss.statusName"></s:hidden>
			<table id="tbl-expressDetail">
			</table>
			<br /> <br />
			
		</div>
		<div id="tabs_2">
			<s:hidden id="statusName" name="wss.statusName"></s:hidden>
			<table id="tbl-orderDetail">
			</table>
			<br /> <br />
		</div>
			<div id="tabs_3">
				<s:hidden id="statusName" name="wss.statusName"></s:hidden>
				<table id="tbl-logisticsDetail">
				</table>
				<br /> <br />
			</div>
			<div class="buttonlist">
				<br /> <br />
				<table>
					<tr>
						<td class="label" width="125">预警类型：</td>
						<td width="15%">
							<select loxiaType="select" id="warningType2">
							</select>
						</td>
						<td class="label" width="125">预警级别：</td>
						<td width="15%">
							<select loxiaType="select" id="warningLv2">
							</select>
						</td>
						<td class="label" width="125">备注：</td>
						<td><input loxiaType="input" id="remark" /></td>
					</tr>
				</table>
				<br /> <br />
				<button type="button" id="btn-query1" loxiaType="button"
					class="confirm">
					确认预警
				</button>
				<button type="button" loxiaType="button" id="cancle">
					取消预警
				</button>
				<button type="button" loxiaType="button" id="back">
					返回
				</button>
			</div>
		</div>
	<div class="hidden">
		<OBJECT ID='emsObject' name='emsObject'
			CLASSID='CLSID:53C732B2-2BEA-4BCD-9C69-9EA44B828C7F' align=center
			hspace=0 vspace=0></OBJECT>
	</div>
	<iframe id="frmSoInvoice" class="hidden"></iframe>
	<iframe id="exportFrame" class="hidden" target="_blank"></iframe>
</body>
</html>