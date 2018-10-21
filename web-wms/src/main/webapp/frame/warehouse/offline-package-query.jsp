<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.dispatchList.title"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/offline-package-query.js' includeParams='none' encode='false'/>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	
<div id="div1">
	<!-- <form id="queryForm" name="queryForm"> -->
		<span class="label" style="font-size: 15px;" >基础条件</span>
		<table id="filterTable">
			<tr>
				<td class="label">面单号</td>
				<td>
					<input id="transNo" loxiaType="input" name="packageCommand.transNo" trim="true"/>
				</td>
				<td class="label">成本中心类型</td>
				<td>
					<select id="centerType" loxiaType="select" name="packageCommand.costCenterType" style="width: 100px;"onchange="centerType(this.id)">
	 				<option value=""><s:text name="label.wahrhouse.sta.select"></s:text></option>
					<option value="1">部门</option>
					<option value="2">店铺</option>
					</select>
				</td>
				<td  class="label">收货人省</td>
				<td>
					<input id="receiverProvince" loxiaType="input" name="packageCommand.receiverProvince" trim="true"/>
				</td>
				<td  class="label">收货人市</td>
				<td>
					<input id="receiverCity" loxiaType="input" name="packageCommand.receiverCity" trim="true"/>
				</td>
				<td  class="label">收货人区</td>
				<td>
					<input id="receiverArea" loxiaType="input" name="packageCommand.receiverArea" trim="true"/>
				</td>
				
			</tr>
			<tr>
				<td class="label">物流服务商</td>
				<td>
					<select loxiaType="select" name="packageCommand.transportatorCode" id="selectLpCode">					
						<option value="">请选择</option>
						<option value="EMS">EMS速递</option>
						<!-- <option value="EMS-COD">EMS-COD</option>
						<option value="SF-EXPRESS">SFHK</option> -->
						<option value="JD">京东快递</option>
						<option value="SFDSTH">顺丰电商特惠</option>
						<option value="SF">顺丰快递</option>
						<option value="YTO">圆通快递</option>
						<option value="STO">申通快递</option>
						<option value="中铁物流">中铁物流</option>
						<option value="V_BAISHI">百世物流</option>
						<option value="KY">跨越物流</option> 
						<option value="V_XINYI">心怡物流</option> 
						
		 				<!-- <option value="SF">顺丰快递</option>
		 				<option value="SFDSTH">顺丰电商特惠</option>
		 				<option value="SF1">顺丰特惠1</option>
		 				<option value="STO">申通快递</option>
		 				<option value="YTO">圆通快递</option>
		 				<option value="EMS">EMS速递</option>
		 				<option value="WX">万象</option>
		 				<option value="WX1">京东快递1</option>
		 				<option value="中铁物流">中铁物流1</option>
		 				<option value="其他物流资源">其他物流资源1</option>
		 				<option value="SFHK">SF-EXPRESS</option>
		 				<option value="SFHK1">顺丰快递-COD1</option>
		 				<option value="SFHK11">顺丰国际1</option>
		 				<option value="SFHK111">百世物流1</option>
		 				<option value="SFHK1111">跨越物流1</option> -->
					</select>
				</td>
				<td class="label">业务类型：</td>
				<td>
					<select loxiaType="select" name="packageCommand.businessType" style="width: 100px;" id="businessType">
				    </select>
				</td>
				<td class="label">部门/店铺</td>
				<td>
						<select id="departmentshop" loxiaType="select" name="packageCommand.costCenterDetail" style="width:120px;">
			</select>
				</td>
				<td class="label"><button type="button" loxiaType="button" class="confirm" id="btnSearch">查询</button></td>
				<td></td>
				<td class="label">相关单据号:</td>
				<td>
					<input id="slipCode" loxiaType="input" name="packageCommand.slipCode" trim="true"/>
				</td>
			</tr>
			<tr>
				<td class="label" >创建时间起</td>
				<td>
				<input  id="startTime" loxiaType="date" name="packageCommand.startTime" trim="true" showTime="true"/>
				</td>
				<td class="label">创建时间至</td>
				<td>
					<input id="endTime" loxiaType="date" name="packageCommand.endTime" trim="true" showTime="true"/>
				</td>
				<td class="label">操作人</td>
				<td>
					<input id="userName" loxiaType="input" name="packageCommand.userName" trim="true"/>
				</td>
				<td class="label">指令类型</td>
				<td>
							<select loxiaType="select" id="staType" name="packageCommand.staType">
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
				<td class="label">指令号</td>
				<td>
					<input id="staCode2" loxiaType="input" name="packageCommand.staCode2" trim="true"/>
				</td>
			</tr>
		</table>
<!-- 	</form> -->
	<div class="buttonlist">
		<button type="button" id="btn-query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
		<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
	</div>
	<%-- <span>输入配货批次号回车进入明细页面</span>
	<table width="25%">
		<tr><td class="label" style="color:red" width="100px;">配货批次号：</td><td><input loxiaType="input" id="pCode" trim="true"/></td></tr>
	</table> --%>
	<div id="div-waittingList">
		<table id="tbl-waittingList"></table>
		<div id="pager_query"></div>
	</div>
</div>

<div class="hidden">
	<OBJECT ID='emsObject' name='emsObject' CLASSID='CLSID:53C732B2-2BEA-4BCD-9C69-9EA44B828C7F' align=center hspace=0 vspace=0></OBJECT>
</div>
<iframe id="frmSoInvoice" class="hidden"></iframe>
<iframe id="exportFrame" class="hidden" target="_blank"></iframe>
<div id="showSta" style="display:none;">
<table>
			<tr>
				<td class="label">
					批次号
				</td>
				<td>
					<input loxiaType="input" trim="true" id="staCode" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					业务类型
				</td>
				<td>
					<input loxiaType="input" trim="true" id="staBusinessType" readonly="readonly"/>
				</td>
			</tr>
		</table>
		<table id="tbl_sta_query_dialog"></table>
	<button type="button" loxiaType="button" id="btnStaQueryClose" >取消</button>
		
</div>

<%-- <jsp:include page="/common/include/include-sta-show.jsp"></jsp:include> --%>
<jsp:include page="/common/include/include-offline-reweight.jsp"></jsp:include>
<jsp:include page="/common/include/include-shop-query2.jsp"></jsp:include>
<jsp:include page="/common/include/include-department-query.jsp"></jsp:include>
</body>
</html>