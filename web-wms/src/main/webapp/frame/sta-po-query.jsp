<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/sta-po-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.sta.query"></s:text></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="query-poOrder-list">
		<form id="queryForm" name="queryForm">
			<table>
			<tr>
					<td width="200" class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
					<td width="200"><input loxiaType="input" trim="true" name="sta.refSlipCode"/></td>
					<td width="200" class="label"><s:text name="相关单据号1"></s:text> </td>
					<td width="200"><input loxiaType="input" trim="true" name="sta.slipCode1"/></td>
			</tr>
			<tr>
					<td width="200" class="label"><s:text name="作业单号"></s:text> </td>
					<td width="200"><input loxiaType="input" trim="true" name="sta.code"/></td>
					<td width="200" class="label"><s:text name="label.warehouse.sta.shop"></s:text> </td>
					<td width="200">
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
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text> </button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
			<font class="label" style="color:red">　　默认查询近三个月的单据!</font>
		</div>
		<table id="tbl-poOrderList"></table>
		<div id="pager"></div>
	</div>
	
	<div id="detail_tabs" class="hidden">
			<ul>
				<li><a href="#m2_tabs-1"><s:text name="商品明细"></s:text></a></li>
				<li><a href="#m2_tabs-2"><s:text name="箱明细"></s:text></a></li>
			</ul>
			<div id="m2_tabs-1">
				<table id="tbl-po-order-detail"></table>
				<div id="pagerDetail"></div>
				<span style="text-align: right; padding-right: 4px; font-weight: bold; background-color: #ffffff;"></span><br />
				<div style="display:block; background-color: #efefef; width: 560px; height: 80px;" id="sta_memo"></div>
			</div>
			
			<div id="m2_tabs-2">
				<table id="tbl-po-order-detail2"></table>
				<div id="pagerDetail2"></div>
				<span style="text-align: right; padding-right: 4px; font-weight: bold; background-color: #ffffff;"></span><br />
				<div style="display:block; background-color: #efefef; width: 560px; height: 80px;" id="sta_memo"></div>
			</div>
			
			<div class="buttonlist1">
			<button loxiaType="button" class="confirm" id="back"><s:text name="button.back"></s:text></button>
		</div>
		</div>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>