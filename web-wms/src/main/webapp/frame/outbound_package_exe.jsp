<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.dispatchList.title"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/outbound_package_exe.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style type="text/css">
	#d{margin-left:   }
</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="divMain">
		<form id="formQuery" name="formQuery">
			<table width="70%">
				<tr>
					<td width="13%" class="label">创建时间</td>
					<td width="20%"><input id="startCreateTime" name="startCreateTime" showtime="true" loxiaType="date"/></td>
					<td width="13%" class="label">到</td>
					<td width="20%"><input id="endCreateTime" name="endCreateTime" showtime="true" loxiaType="date"/></td>
					<td width="13%" class="label">状态</td>
					<td width="20%">
						<select loxiaType="select" name="cmd.intStatus">
							<option value="">请选择</option>
							<option value="1">新建</option>
							<option value="5">执行中</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">箱号编码</td>
					<td><input loxiaType="input" name="cmd.code"  trim="true"/></td>
					<td class="label">作业单号</td>
					<td><input loxiaType="input" name="cmd.staCode" trim="true"/></td>
					<td class="label">相关单据号</td>
					<td><input loxiaType="input" name="cmd.staSlipCode" trim="true"/></td>
				</tr>
				<tr>
					<td class="label">相关单据号2(LOAD KEY)</td>
					<td><input loxiaType="input" name="cmd.slipCode2" trim="true"/></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button name="query" id="query" type="button" loxiaType="button" class="confirm">查询</button>
			<button id="btnReset" type="button" loxiaType="button" >重置</button>
		</div>
		<table id="tbl_sta_package"></table>
		<div id="tbl_sta_package_page"></div>
	</div>
	<div id="divDetail" class="hidden">
		<input type="hidden" id="cartonId" />
		<input type="hidden" id="staType" />
		<table width="80%">
			<tr>
				<td class="label" width="13%">
					箱号 
				</td>
				<td id="tblCartonSeqNo" width="15%">
				</td>
				<td class="label" width="13%">
					箱号编码
				</td>
				<td id="tblCartonCode" width="15%">
				</td>
				<td class="label" width="13%">
					作业单
				</td>
				<td id="tblStaCode" width="15%">
				</td>
				<td class="label" width="13%">
					相关单据
				</td>
				<td id="tblSlipCode" width="15%">
				</td>
			</tr>
			<tr>
				<td class="label">
					联系人
				</td>
				<td id="tblRececiver" >
				</td>
				<td class="label">
					手机
				</td>
				<td id="tblMobile">
				</td>
				<td class="label">
					创建时间
				</td>
				<td id="tblCreateTime" colspan="3">
				</td>
			</tr>
			<tr>
				<td class="label">
					送货地址
				</td>
				<td id="tblAddress" colspan="7">
				</td>
				<td>
			</tr>
		</table>
		<br/>
		<span class="label" style="color: red;font-size: 16px;">注：装箱工作无法分批执行，如离开本页面，本箱商品需全部重新核对。</span>
		<br/><br/>
		<span class="label" style="font-size: 24px;" >条码扫描:</span><input id="barcode" style="width: 300px;height:26px;font-size:24px;" loxiaType="input"  trim="true"/>
		<span class="label" style="font-size: 20px; margin-left:50px;" >已扫描的总数量:</span><span id="numbers" style="font-size: 20px;">0</span><span style="font-size: 20px;">件</span>
		<br/><br/>
		<div id="defectImperfect" class="hidden">
		<input type="text" class="hidden" id="statusName" name="statusName">
		<input type="text" class="hidden" id="defectCodes" name="defectCodes" value="">
		<span class="label" style="font-size: 24px;" >残次编码扫描:</span><input id="defectCode" style="width: 300px;height:26px;font-size:24px;" loxiaType="input"  trim="true"/>
		<span class="label" style="font-size: 20px; margin-left:50px;" >已扫描的总数量:</span><span id="defectnumbers" style="font-size: 20px;">0</span><span style="font-size: 20px;">件</span>
		</div>
		<br/><br/>
		<table id="tbl_sta_package_line"></table>
		<br/>
		<div id="divStuff" class="hidden">
			<span class="label" style="font-size: 24px;" >包材条码扫描:</span><input id="pgBarcode" style="width: 300px;height:26px;font-size:24px;" loxiaType="input"  trim="true"/>
			<br/>
			<div loxiaType="edittable" style="width: 60%;" >
				<table operation="add,delete" id="barCode_tab" >
					<thead>
						<tr>
							<th width="10"><input type="checkbox"/></th>
							<th width="200">条形码</th>
							<th width="100">数量</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
					<tbody >
						<tr class="add" index="(#)">
							<td><input type="checkbox"/></td>
							<td><input loxiatype="input" readonly="readonly" mandatory="true" name="addList(#).barcode" class="code"/></td>
							<td><input loxiatype="input" readonly="readonly" mandatory="true" name="addList(#).qty" class="qty"/></td>
						</tr>
					</tbody>
					<tfoot></tfoot>
				</table>
			</div>
			<br/>
		</div>
		<div class="buttonlist">
			<table>
				<tr>
					<td></td>
					<td>当前重量:<input id="autoWeigth" loxiaType="input" style="width: 150px"  class="labelinput" readonly="readonly"/> 
					<button id="restWeight" loxiaType="button">重启称</button></td>
				</tr>
				<tr>
					<td>本箱数量:<input loxiatype="input" name="packageNum" id="packageNum" style="width: 150px;" class="qty"/></td>
					<td>货物重量:<input loxiatype="input" name="goodsWeight" id="goodsWeight" style="width: 150px;"/>千克（KG） </td>
				</tr>
				<tr>
					<td></td>
					<td>自动称重确认条码:<input id="confirmWeightInput" loxiaType="input" class="labelinput" style="width: 150px" mandatory="true" /></td>
				</tr>
			</table><br/><br/>
			<button id="btnExe" type="button" loxiaType="button" class="confirm">装箱</button>
			确认条码:<input id="doConfirm" loxiaType="input" style="width: 150px;" />
			<button id="btnBack" type="button" loxiaType="button" >返回</button>
		</div><br>
		<div class="buttonlist">
			<button id="printDeliveryInfo" type="button" loxiaType="button" class="confirm" onclick="printDeliveryInfo();">打印物流面单</button>
		</div><br>
	</div>
	
	<div id="dialog_msg">
		<div id="divMsg" style="font-size:32px;">
			执行成功
		</div>
		<div class="buttonlist">
			<button id="btnExeConfirm" type="button" loxiaType="button" class="confirm">确定</button>
			确认条码:<input id="txtExtConfirm" loxiaType="input" style="width: 150px;" />
		</div>
	</div>
	
	<div id="dialog_change">
		<input type="hidden" id="txtId"/>
		<table width="95%" style="font-size: 22px;">
			<tr>
				<td width="40%" class="label">条形码</td>
				<td width="60%" id="tblBarcode"></td>
			</tr>
			<tr>
				<td class="label">数量</td>
				<td class="label">
					<input id="txtQty" loxiaType="number" style="height:22px;font-size:20px;"/>
				</td>
			</tr>
		</table>
		<div class="buttonlist">
			<button id="btnChange" type="button" loxiaType="button" class="confirm">确定</button>
			<button id="btnCancelChange" type="button" loxiaType="button">取消</button>
		</div>
	</div>
	<div id="dialog_barcode_error">
		<div id="barcodeErrorMsg" style="font-size:32px;">
		当前条码对应的商品不存在！
		</div>
		<button id="confirmClose" loxiaType="button" class="confirm">确认</button>
	</div>
</body>
</html>