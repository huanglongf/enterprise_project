<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/pda-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.sta.query"></s:text></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="query-order-list">
		<form id="queryForm" name="queryForm">
			<table>
			
			    <tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.code" /></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.refSlipCode"/></td>
					<td width="10%" class="label">店铺</td>
					<td width="19%">
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
					<td width="10%" class="label">辅助相关单据号</td>
				    <td width="15%"><input loxiaType="input" trim="true" name="sta.slipCode1"/></td>
				    <td width="10%" class="label"><s:text name="label.warehouse.transaction.name"></s:text> </td>
					<td width="15%">
						<select loxiaType="select" id="typeList" name="sta.intStaType">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="11"><s:text name="label.warehouse.sta.type.inbound.purchase"></s:text> </option>
							<option value="12"><s:text name="label.warehouse.sta.type.inbound.settlement"></s:text> </option>
							<option value="13"><s:text name="label.warehouse.sta.type.inbound.others"></s:text> </option>
							<option value="14"><s:text name="label.warehouse.sta.type.inbound.consignment"></s:text> </option>
							<option value="15">赠品入库 </option>
							<option value="16">移库入库 </option>
                            <option value="17">货返入库 </option>
   						    <option value="21"><s:text name="label.warehouse.sta.type.outbound.sales"></s:text> </option>
							<option value="22"><s:text name="label.warehouse.sta.type.outbound.others"></s:text> </option>
							<option value="25"><s:text name="label.warehouse.sta.type.outsales"></s:text> </option>
							<option value="31"><s:text name="label.warehouse.sta.type.tranist.inner"></s:text> </option>
							<option value="32"><s:text name="label.warehouse.sta.type.tranist.cross"></s:text> </option>
							<option value="41"><s:text name="label.warehouse.sta.type.inbound.return"></s:text> </option>
							<option value="42"><s:text name="label.warehouse.sta.type.outbound.return"></s:text> </option>
							<option value="45"><s:text name="label.warehouse.sta.type.inventory.status.change"></s:text> </option>
							<option value="50"><s:text name="label.warehouse.sta.type.inbound.initInventory"></s:text> </option>		
	                        <option value="55">GI 上架 </option>
							<option value="61">采购出库（采购退货出库） </option>
							<option value="62">结算经销出库 </option>
							<option value="63">包材领用出库</option>
							<option value="64">代销 出库</option>
							<option value="81"><s:text name="label.warehouse.sta.type.vmi.inbound.consignment"></s:text> </option>
							<option value="85">VMI库存调整入库 </option>
							<option value="86">VMI库存调整出库 </option>    					
                            <option value="88">转店 </option>
							<option value="90">同公司调拨 </option>
                             <option value="91">不同公司调拨 </option> 
							 <option value="101">VMI退大仓  </option>
							 <option value="102">VMI转店退仓 </option>
							 <option value="110">库存锁定 </option>
                             <option value="201">福利领用 </option>
                             <option value="202">固定资产领用 </option>
							 <option value="204">报废出库 </option>
                             <option value="205">促销领用 </option>
                             <option value="206">低值易耗品 </option>
							 <option value="210">样品领用出库 </option>
                             <option value="211">样品领用入库 </option>
                             <option value="212">商品置换出库 </option>
							 <option value="213">商品置换入库 </option>
                             <option value="214">送修出库 </option>
                             <option value="215">送修入库 </option>
							 <option value="216">串号拆分出库 </option>
                             <option value="217">串号拆分入库 </option>
                             <option value="218">串号组合出库 </option>
							 <option value="219">串号组合入库 </option>
							 <option value="251">盘点调整</option>
						</select>
					</td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.status"></s:text></td>
					<td width="15%">
						<select loxiaType="select" id="statusList" name="sta.intStaStatus">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="1"><s:text name="label.warehouse.sta.status.created"></s:text> </option>
							<option value="2"><s:text name="label.warehouse.sta.status.occupied"></s:text> </option>
							<option value="3"><s:text name="label.warehouse.sta.status.checked"></s:text> </option>
							<option value="4"><s:text name="label.warehouse.sta.status.intransit"></s:text> </option>
							<option value="5"><s:text name="label.warehouse.sta.status.partyReturned"></s:text> </option>
							<option value="8">装箱中</option>
							<option value="10"><s:text name="label.warehouse.sta.status.finished"></s:text> </option>
							<option value="15"><s:text name="label.warehouse.sta.status.cancelUndo"></s:text> </option>
							<option value="17"><s:text name="label.warehouse.sta.status.canceled"></s:text> </option>
							<option value="20"><s:text name="label.warehouse.sta.status.failed"></s:text> </option>
							<option value="25"><s:text name="label.warehouse.sta.status.frozen"></s:text> </option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text></td>
					<td width="15%"><input loxiaType="date" id="c1" name="createTime" showTime="true"></input></td>
					<td width="10%" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="15%"><input loxiaType="date" id="e1" name="endCreateTime" showTime="true"/></td>
				</tr>
				<tr>
				    <td width="10%" class="label"><s:text name="label.warehouse.sta.finish"></s:text> </td>
					<td width="15%"><input loxiaType="date" name="finishTime" showTime="true"/></td>
					<td width="10%" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text></td>
					<td width="15%"><input loxiaType="date" name="endFinishTime" showTime="true"/></td>
				</tr>
			
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text> </button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
		</div>
		<table id="tbl-orderList"></table>
		<div id="pager"></div>
	</div>
	<div id="order-detail" class="hidden">
		<table>
			<tr>    
			        <td width="10%" class="label">单据类型</td>
					<td width="15%"><span id="type"></span></td>
					<td width="10%" class="label">单据状态</td>
					<td width="15%"><span id="status"></span></td>
					<td width="10%" class="label">店铺</td>
					<td width="15%"><span id="shopId"></span></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
					<td width="40%" colspan="3"><span id="createTime"></span></td>
				</tr>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
					<td width="15%"><span id="code"></span></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
					<td width="15%"><span id="refSlipCode"></span></td>
					<td width="10%" class="label">辅助相关单据号</td>
					<td width="15%"><span id="slipCode1"></span></td>
					<td width="10%" class="label"><s:text name="label.warehouse.sta.finish"></s:text> </td>
					<td width="40%" colspan="3"><span id="finishTime"></span></td>
				</tr>
				<tr>
					<td width="10%" class="label">计划收货总量</td>
					<td width="15%"><span id="qty1"></span></td>
					<td width="10%" class="label">实际收货总量</td>
					<td width="15%"><span id="qty2"></span></td>
					<td width="10%" class="label">实际上架总量</td>
					<td width="15%"><span id="qty3"></span></td>
				</tr>
				<!-- bin.hu -->
				<tr class="hidden">
					<td width="0%"><span id="staid" class="label"></span></td><!-- ID -->
					<td width="0%"><span id="zyCode" class="label"></span></td><!-- 作业单号 code -->
					<td width="0%"><span id="intStatus" class="label"></span></td><!-- 状态 intstatus -->
					<td width="0%"><span id="intType" class="label"></span></td><!-- 作业类型名称 intType -->
				</tr>
		</table>
		<br />
		
		<div id="detail_tabs">
			<ul>
				<li><a href="#m2_tabs-1">收货明细</a></li>
				<li><a href="#m2_tabs-2">收货SN和残次明细</a></li>
				<li><a href="#m2_tabs-3">上架明细</a></li>
				<li><a href="#m2_tabs-4">上架SN和残次明细</a></li>
			</ul>
			<div id="m2_tabs-1">
				<table id="tbl-order-detail"></table>
				<div id="pagerDetail"></div>
			</div>
			
			<div id="m2_tabs-2">
				<table id="tbl-order-detail_operate"></table>
				<div id="pagerDetail_operate"></div>
			</div>
			
			<div id="m2_tabs-3">
				<table id="tbl-sn-detail"></table>
				<div id="pagerSn"></div> 
			</div>
			
			<div id="m2_tabs-4">
				<table id="tbl-operate-log"></table>
				<div id="pagerOperateLog"></div> 
			</div>
		</div>
		
		<div class="buttonlist">
			<!-- <button loxiaType="button" class="confirm" id="printBoxTag">补打货箱标签</button>
			<button loxiaType="button" class="confirm" id="printSlipCode">补打作业单号</button> -->
			<button loxiaType="button" class="confirm" id="back"><s:text name="button.back"></s:text></button>
		</div>
	</div>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>