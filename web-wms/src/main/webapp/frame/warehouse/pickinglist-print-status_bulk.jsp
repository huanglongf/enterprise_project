<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.dispatchList.title"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/priority_city_config.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/multiple-select.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/pickinglist-print-status_bulk.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/hight_province_config.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

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
				<td class="label">状态：</td>
				<td>
					<select loxiaType="select" name="pickingList.status">
						<option value="PACKING">配货中</option>
						<option value="FAILED_SEND">快递无法送达</option>
						<option value="PARTLY_RETURNED">部分完成</option>
						<option value="FAILED">配货失败</option>
						<option value="WAITING">待配货</option>
					</select>
				</td>
				<td class="label">物流服务商：</td>
				<td>
					<select loxiaType="select" name="pickingList.lpcode" id="selLpcode">
						<option value="">--请选择物流服务商--</option>
						<option value="null">混物流商</option>
					</select>
				</td>
				<td class="label">是否包含sn号订单：</td>
				<td>
					<select loxiaType="select" name="pickingList.isSn1">
						<option value="">--请选择--</option>
						<option value="true">是</option>
						<option value="false">否</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="label">是否需要发票</td>
				<td>
					<select loxiaType="select" name="pickingList.isInvoice1">
						<option value="">--请选择--</option>
						<option value="true">是</option>
						<option value="false">否</option>
					</select>
				</td>
				<%-- <td class="label">优先发货城市</td>
				<td>
					<select loxiaType="select" name="pickingList.city">
						<option value="">请选择</option>
						<option value="上海市">上海市</option>
						<option value="广州市">广州市</option>
						<option value="北京市">北京市</option>
						<option value="杭州市">杭州市</option>
						<option value="深圳市">深圳市</option>
					</select>
				</td> --%>
				<td class="label">优先发货城市</td>
					<td>
						<select name="pickingList.city" id="priorityCity" width="200px" multiple="multiple"></select>
					</td>
				<td class="label">商品大小</td>
				<td>
					<select loxiaType="select" name="pickingList.skuSizeId" id="skuSizeSelect">
					</select>
				</td>
				<td class="label">商品分类</td>
				<td>
					<select loxiaType="select" name="pickingList.categoryId" id="categoryId">
						<option value="">--请选择--</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="label" style="color: blue;">O2O门店</td>
				<td>
					<select name="pickingList.toLocation" id="selToLocation" loxiaType="select">
						<option value="">--默认为空--</option>
					</select>
				</td>
				<td class="label">生成批次操作员：</td>
				<td>
					<input loxiaType="input" name="pickingList.worker" trim="true"/>
				</td>
				<td class="label"  style="color: red;" width="13%">是否预售订单</td>
					<td width="20%">
						<div>
							<select id="pickingList.isPreSale" name="pickingList.isPreSale" loxiaType="select">
							<option value="0" selected="selected">非预售订单</option>
							<option value="1">预售订单</option>
						</select>
						</div>
					</td>	
			</tr>
			<tr>
			   <td class="label">优先发货省份</td>
					<td>
						<select name="priority" id="priorityId" width="200px" multiple="multiple">
						</select>
					</td>	
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button type="button" id="btn-query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
		<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
	</div>
	<span>输入配货批次号回车进入明细页面</span>
	<table width="25%">
		<tr><td class="label" style="color:red" width="100px;">配货批次号：</td><td><input loxiaType="input" id="pCode" trim="true"/></td></tr>
	</table>
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
				<button loxiaType="button" id="changeCode"><s:text name="button.change"></s:text> </button>
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
	<div id="btnlist" class="buttonlist" >
		<p>
			<button loxiaType="button" id="back"><s:text name="button.back"></s:text> </button>
			<button loxiaType="button" class="confirm"  id="exportAgv" >Agv导出</button>
		</p>			
	</div>
	<div id="printerChoose">
		<div id="postPrintInfo" class="hidden" style="font-size:14px;">该配货清单是后置打印面单,打印确认</div><br/>
		<button loxiaType="button" class="confirm" id="useDefaultPrinter">使用当前的默认打印机打印</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button loxiaType="button" class="confirm" id="chooseDefaultPrinter">退出，配置默认打印机</button>
	</div>
	</div>
	<div id="div3">
	<p>
			<button loxiaType="button" class="confirm" id="btnSoInvoice"><s:text name="button.export.invoice"></s:text></button>
			<button loxiaType="button" class="confirm" id="printPickingList"><s:text name="button.pl.print"/></button>
			<button loxiaType="button" id="printPickingListNew2"><s:text name="button.pl.print2"/></button>
			<button loxiaType="button" class="confirm" id="printTrunkPackingList"><s:text name="button.pg.print"></s:text></button>
			<select loxiaType="select" name="pickZoneCode" id="pickZoneCode"  style="width:150px;">
					 <option value="">--请选择区域--</option>
			 </select>
		</p>
		<p>
			<button loxiaType="button" class="confirm" id="printDeliveryInfo"><s:text name="button.trans.print"></s:text></button>
			<button loxiaType="button" class="confirm"  id="printDone" >打印完成</button>
			<button loxiaType="button" class="confirm"  id="printPdaBarCode" >打印PDA拣货条码</button>
			</p>
	</div>
	
<!--  <div class="hidden">
	<OBJECT ID='emsObject' name='emsObject' CLASSID='CLSID:53C732B2-2BEA-4BCD-9C69-9EA44B828C7F' align=center hspace=0 vspace=0></OBJECT>
</div>-->
<iframe id="frmSoInvoice" class="hidden"></iframe>
<iframe id="exportFrame" class="hidden" target="_blank"></iframe>
</body>
</html>