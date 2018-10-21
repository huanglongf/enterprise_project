<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/priority_city_config.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/sta-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/hight_province_config.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.sta.query"></s:text></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="query-order-list">
		<form id="queryForm" name="queryForm">
			<table>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text></td>
					<td width="15%"><input loxiaType="date" id="c1" name="createTime" showTime="true"></input></td>
					<td width="10%" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="15%"><input loxiaType="date" id="e1" name="endCreateTime" showTime="true"/></td>
					<td width="10%" class="label"><s:text name="label.warehouse.sta.finish"></s:text> </td>
					<td width="15%"><input loxiaType="date" name="finishTime" showTime="true"/></td>
					<td width="10%" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text></td>
					<td width="15%"><input loxiaType="date" name="endFinishTime" showTime="true"/></td>
				</tr>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.code" /></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
					<td width="15%">
						<select loxiaType="select" id="select" name="sta.lpcode">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<!-- <option value="STO">申通快递</option>
							<option value="EMS">EMS速递</option>
							<option value="SF">顺丰快递</option>
							<option value="EMSCOD">EMS-COD</option>
							<option value="SF-COD">顺丰-COD</option> -->
						</select></td>
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
					<td width="10%" class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.refSlipCode"/></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta.delivery.code"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.trackingNo"/></td>
					<td width="10%" class="label"><s:text name="label.warehouse.sta.shop"></s:text> </td>
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
					<td width="10%" class="label"><s:text name="label.wahrhouse.sta.operator"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.operator"/></td>
				</tr>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.inpurchase.skucode"></s:text></td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.skuCode"/></td>
					<td width="10%" class="label"><s:text name="label.warehouse.inpurchase.barcode"></s:text></td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.barCode"/></td>
					<td width="10%" class="label"><s:text name="label.warehouse.inpurchase.jmcode"></s:text></td>
					<td width="19%"><input loxiaType="input" trim="true" name="sta.jmCode"/></td>
					<td width="10%" class="label"><s:text name="label.warehouse.inpurchase.supplierCode"></s:text></td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.supplierCode"/></td>
				</tr>
				<tr>
					<td width="10%" class="label">是否分包</td>
					<td width="15%">
						<input type="checkbox"  id="isMorePackage" />
						<input type="hidden" id="morePackageValue" name="sta.isMorePackage"/>
					</td>
					<td width="10%" class="label">相关单据号1</td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.slipCode1"/></td>
					<td width="10%" class="label">相关单据号2</td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.slipCode2"/></td>
					<td width="10%" class="label">是否货到付款</td>
					<td>
						<select loxiaType="select" id="isCod" name="sta.isCod">
							<option value="">请选择</option>
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</td>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td width="10%" class="label" style="color: blue;">O2O门店</td>
					<td width="15%"><select name="sta.o2oShop"
						id="selToLocation" loxiaType="select">
							<option value="">请选择</option>
					</select></td>
					<td width="10%" class="label">配货模式</td>
					<td width="15%"><select name="sta.pickingTypeString"
						id="pickingTypeString" loxiaType="select">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="1">单件</option>
							<option value="2">套装</option>
							<option value="3">O2OQS</option>
							<option value="10">多件</option>
							<option value="20">团购</option>
							<option value="30">秒杀</option>
					</select></td>
					<td width="10%" class="label">运单时限类型</td>
					<td width="15%"><select name="sta.transTimeType"
						id="transTimeType" loxiaType="select">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
						<option value="1">普通</option>
						<option value="3">及时达</option>
						<option value="5">当日</option>
						<option value="6">次日</option>
						<option value="7">次晨</option>
						<option value="8">云仓专配次日</option>
						<option value="9">云仓专配隔日</option>
					</select></td>
					<td width="10%" class="label">是否QS</td>
					<td width="15%"><select name="sta.isQs" id="selIsQs" loxiaType="select">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="0">否</option>
							<option value="1">是</option>
						</select></td>
				</tr>
				<tr>
					<td width="10%" class="label">批次号</td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.pickingCode"/></td>
					<td width="10%" class="label">是否合并订单</td>
					<td width="15%">
						<select id="isMergeInt" name="sta.isMergeInt" loxiaType="select">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</td>
					<td width="10%" class="label">合并订单号</td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.groupStaCode" /></td>
									
					<td width="10%" class="label">是否加入配货清单</td>
					<td width="15%"><select name="sta.isPickingList"
						id="isPickingList" loxiaType="select">
							<option value="" selected="selected">请选择</option>
							<option value="1">是</option>
							<option value="2">否</option>
					</select></td>								
				</tr>
				<tr>
					<td class="label" width="10%">平台订单相关时间</td>
					<td width="15%"><input loxiaType="date" name="orderCreateTime" showtime="true"/></td>
					<td class="label" width="10%"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="15%"><input loxiaType="date" name="toOrderCreateTime" showtime="true"/></td>
					<td colspan="2" class="label" style="color: red;">ps:平台订单相关时间:非COD订单为付款时间、COD订单为创建时间</td>
					<td class="label">优先发货城市</td>
					<td>
						<select name="city" id="priorityCity" width="200px" multiple="multiple"></select>
					</td>
				</tr>
				<tr>
						<td width="10%" class="label">是否显示合并订单号</td>
						<td width="15%">
							<input type="checkbox"  id="isShowMerge" />
							<input type="hidden" id="isShowMerge2" name="sta.isShowMerge"/>
						</td>
						<td width="10%" class="label">是否查询历史数据</td>
						<td width="15%">
							<input type="checkbox"  id="isQueryHis" />
							<input type="hidden" id="isQueryHis2" name="sta.isQueryHis"/>
							<font class="label" style="color:red">查询历史数据必须选择创建时间范围!</font>
						</td>
						<td class="label">优先发货省份</td>
						<td width="20%">
							<select name="priority" id="priorityId" width="200px" multiple="multiple">
							</select>
						</td>
				</tr>
				<tr>
						<td width="10%" class="label">是否预售订单</td>
						<td width="15%">
							<select name="sta.isPreSale" id="isPreSale" loxiaType="select">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</td>
				</tr> 
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text> </button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
			<font class="label" style="color:red">　　默认查询近三个月的单据，否则请加上时间条件！</font>
		</div>
		<table id="tbl-orderList"></table>
		<div id="pager"></div>
	</div>
	<div id="order-detail" class="hidden">
		<table>
			<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
					<td width="40%" colspan="3"><span id="createTime"></span></td>
					<td width="10%" class="label"><s:text name="label.warehouse.sta.finish"></s:text> </td>
					<td width="40%" colspan="3"><span id="finishTime"></span></td>
				</tr>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
					<td width="15%"><span id="code"></span></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
					<td width="15%"><span id="refSlipCode"></span></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta.trantype"></s:text> </td>
					<td width="15%"><span id="type"></span></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.status"></s:text> </td>
					<td width="15%"><span id="status"></span></td>
				</tr>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.sta.shop"></s:text> </td>
					<td width="15%"><span id="shopId"></span></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
					<td width="15%"><span id="lpcode"></span></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta.delivery.code"></s:text> </td>
					<td width="15%"><span id="trackingNo"></span></td>
					<td width="10%" class="label"><s:text name="label.wahrhouse.sta.operator"></s:text> </td>
					<td width="15%"><span id="operator"></span></td>
				</tr>
				<!-- bin.hu -->
				<tr class="hidden">
					<td width="0%"><span id="staid" class="label"></span></td><!-- ID -->
					<td width="0%"><span id="zyCode" class="label"></span></td><!-- 作业单号 code -->
					<td width="0%"><span id="intStatus" class="label"></span></td><!-- 状态 intstatus -->
					<td width="0%"><span id="intType" class="label"></span></td><!-- 作业类型名称 intType -->
					<td width="0%"><span id="ipCode" class="label"></span></td><!-- 物流服务 -->
					<td width="0%"><span id="pickListId" class="label"></span></td><!-- 批次号 -->
				</tr>
		</table>
		<br />
		
		<div id="detail_tabs">
			<ul>
				<li><a href="#m2_tabs-1"><s:text name="label.warehouse.sta.create_detail"></s:text></a></li>
				<li><a href="#m2_tabs-2"><s:text name="label.warehouse.sta.operate_detail"></s:text></a></li>
				<li><a href="#m2_tabs-3"><s:text name="label.warehouse.sta.sn_detail"></s:text></a></li>
				<li><a href="#m2_tabs-4"><s:text name="label.warehouse.sta.operate_log"></s:text></a></li>
				<li><a href="#m2_tabs-5">作业单行特殊处理信息</a></li>
			</ul>
			<div id="m2_tabs-1">
				<table id="tbl-order-detail"></table>
				<div id="pagerDetail"></div>
				<span style="text-align: right; padding-right: 4px; font-weight: bold; background-color: #ffffff;"><s:text name="label.warehouse.location.comment"/></span><br />
				<div style="display:block; background-color: #efefef; width: 560px; height: 80px;" id="sta_memo"></div>
			</div>
			
			<div id="m2_tabs-2">
				<form id="query_detail_form" name="query_detail_form">
					<table width="80%">
						<tr>
							<td width="10%" align="right"><s:text name="label.warehouse.pl.createtime"></s:text></td>
							<td width="15%"><input loxiaType="date" name="createTime" showTime="true"></input></td>
							<td width="10%" align="right" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
							<td width="15%"><input loxiaType="date" name="endCreateTime" showTime="true"/></td>
							<td width="10%" align="right"><s:text name="label.warehouse.sta.finish"></s:text> </td>
							<td width="15%"><input loxiaType="date" name="finishTime" showTime="true"/></td>
							<td width="10%" align="right" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text></td>
							<td width="15%"><input loxiaType="date" name="endFinishTime" showTime="true"/></td>
						</tr>
						<tr>
							<td align="right" ><s:text name="label.warehouse.inpurchase.skucode"></s:text></td>
							<td >
								<input loxiaType="input" trim="true" name="stvl.skuCode"/>
							</td>
							<td align="right" ><s:text name="label.warehouse.other.transaction"></s:text></td>
							<td >
								 <select loxiaType="select" name="stvl.directionInt">
									<option value=""><s:text name="label.warehouse.choose.transactiontype"></s:text></option>
									<option value="1"><s:text name="label.warehouse.other.transactionin"></s:text></option>
									<option value="2"><s:text name="label.warehouse.other.transactionout"></s:text></option>
								</select>
							</td>
							<td align="right" ><s:text name="label.wahrhouse.sta.creater"></s:text></td>
							<td >
								<input loxiaType="input" trim="true" name="stvl.creater"/>
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td align="right"><s:text name="label.warehouse.whlocation.code"></s:text></td>
							<td>
								 <input loxiaType="input" trim="true" name="stvl.locationCode"/>
							</td>
							<td align="right"><s:text name="label.warehouse.inventory.state"></s:text></td>
							<td>
								<s:select name="stvl.intInvstatus" headerKey="" headerValue="请选择库存状态" list="statusList" loxiaType="select" listKey="id" listValue="name" ></s:select>
							</td>
							<td colspan="4"></td>   
						</tr>
					</table>
				</form>
				<div class="buttonlist">
					<button id="searchDetail" loxiaType="button" class="confirm" staId=""><s:text name="button.search" /></button>
					<button type="button" loxiaType="button" id="resetDetail"><s:text name="button.reset"></s:text> </button>
				</div>
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
			<div id="m2_tabs-5">
				<table id="tbl-special-line"></table>
				<div id="pagerSpecialLog"></div>
			</div>
		</div>
		
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="printBoxTag">补打货箱标签</button>
			<button loxiaType="button" class="confirm" id="printSlipCode">补打作业单号</button>
			<button loxiaType="button" class="confirm" id="back"><s:text name="button.back"></s:text></button>
		</div>
	</div>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>