<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title>退货申请</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse-return-application-ad.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="div-main" >
		<form id="form_query">
		<table>
			<tr>
				<td class="label" width="10%">退换货店铺：</td>
				<td width="20%">
					<select id="companyshop" name="app.owner" loxiaType="select">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
					</select>
				</td>
				<td class="label" width="10%">事件编码：</td>
				<td width="20%"><input loxiaType="input" trim="true" id = "appCode" name="app.code" /></td>
				<td class="label" width="10%">退换货相关单据号：</td>
				<td width="20%"><input loxiaType="input" trim="true" id="slipCode" name="app.slipCode" /></td>
			</tr>
			<tr>
				<td class="label" width="10%">销售出平台订单号：</td>
				<td width="20%"><input loxiaType="input" trim="true" id="slipCode2" name="app.slipCode2" /></td>
				<td class="label" width="10%">创建时间：</td>
				<td width="20%"><input loxiaType="date" trim="true" name="startTime" showTime="true"/></td>
				<td class="label" width="10%" style="text-align:center"><s:text name="label.warehouse.pl.to"/></td>
				<td width="20%"><input loxiaType="date" trim="true" name="endTime" showTime="true"/></td>
			</tr>
			<tr>
				<td class="label" width="10%">事件状态：</td>
				<td width="20%">
					<select id="status" name="app.statusName" loxiaType="select">
						<option value="">请选择</option>
						<option value="0">已作废</option>
						<option value="1">已创建</option>
						<option value="2">已提交</option>
						<option value="3">已反馈</option>
					</select>
				</td>
				<!-- <td class="label" width="10%">OMS反馈状态：</td>
				<td width="20%">
					<select id="omsStatus" name="app.omsStatusName" loxiaType="select">
						<option value="">请选择</option>
						<option value="1">已创建</option>
						<option value="2">拒收</option>
						<option value="3">无法确认</option>
					</select>
				</td> -->
					<td class="label" width="10%">退换货人姓名：</td>
				<td width="20%"><input loxiaType="input" trim="true" name="app.returnUser" /></td>
				<td class="label" width="10%">退回快递公司：</td>
				<td width="20%">
					<select loxiaType="select" name="app.lpCode" id="selTrans">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
					</select>
				</td>
			</tr>		
			<tr>
				<td class="label" width="10%">退换货作业单号：</td>
				<td width="20%"><input loxiaType="input" trim="true" id="staCode" name="app.staCode" /></td>
				<!-- <td class="label" width="10%">退换货人姓名：</td>
				<td width="20%"><input loxiaType="input" trim="true" name="app.returnUser" /></td> -->
				<td class="label" width="10%">运单号：</td>
				<td width="20%"><input loxiaType="input" trim="true"  id="trankNo"  name="app.trankNo" /></td>
				<td class="label" width="10%">退货日期：</td>
				<td width="20%">2323</td>
			</tr>
		<%-- 	<tr>
			    <td class="label" width="10%">反馈时间：</td>
				<td width="20%"><input loxiaType="date" trim="true" name="feedBackstartTime" showTime="true"/></td>
				<td class="label" width="10%" style="text-align:center"><s:text name="label.warehouse.pl.to"/></td>
				<td width="20%"><input loxiaType="date" trim="true" name="feedBackendTime" showTime="true"/></td>
				<td width="10%" class="label"><s:text name="退货指令状态："></s:text></td>
					<td width="15%">
						<select loxiaType="select" id="statusList" name="app.status">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="1"><s:text name="label.warehouse.sta.status.created"></s:text> </option>
							<option value="10"><s:text name="label.warehouse.sta.status.finished"></s:text> </option>
							<option value="17"><s:text name="label.warehouse.sta.status.canceled"></s:text> </option>
						</select>
					</td>
			</tr> --%>
			<input type="hidden" trim="true"  id="brand"  name="app.brand" value="1" />
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search"><s:text name="button.search"/></button>
			<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<button loxiaType="button" class="confirm" id="add">创建</button>
		</div>
		<div style="margin-bottom: 10px;">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<text style="margin-left: 100px;">输入运单号后按回车进入详情界面</text>
				<br/>
				<span class="label" style="margin-left: 50px;">快捷扫描区:</span>
				<input id="trackingNo"  loxiaType="input" style="width: 200px; ime-mode: disabled;" trim="true"/>&nbsp; 
		</div>
		<table id="tbl-return-app"></table>
		<div id="pager"></div>
		
	</div>
	<div id="div-detail" >
	<form id="form_info">
		<table>
			<tr>
				<td class="label" width="10%">退换货店铺：</td>
				<td width="20%">
					<select id="companyshop2" mandatory="true" name="app.owner" loxiaType="select">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
					</select>
				</td>
				<td class="label" width="10%">退换货相关单据号：</td>
				<td width="20%"><input loxiaType="input" trim="true" id="slipCodes" name="app.slipCode" /></td>
				<td class="label" width="10%">销售平台订单号：</td>
				<td width="20%"><input loxiaType="input" trim="true" id="slipCodes1" name="app.slipCode2" /></td>
			</tr>
			<tr>
				<td class="label" width="10%">退回快递公司：</td>
				<td width="20%">
					<select loxiaType="select" name="app.lpCode" id="selTrans2" mandatory="true">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
					</select>
				</td>
				<td class="label" width="10%">快递单号：</td>
				<td width="20%"><input loxiaType="input" trim="true" id="trankNos" mandatory="true"  name="app.trankNo" /></td>
				<td class="label" width="10%">退货人：</td>
				<td width="20%"><input loxiaType="input" trim="true" id="returnUsers"   name="app.returnUser" /></td>
			</tr>
			<tr>
				<td class="label" width="10%">退换货作业单号：</td>
				<td width="20%"><input loxiaType="input" trim="true" id="staCodes" name="app.staCode" /></td>
				<td class="label" width="10%">联系方式：</td>
				<td width="20%"><input loxiaType="input" trim="true"  id="telephones"  name="app.telephone" /></td>
				<td class="label" width="10%">暂存库位：</td>
				<td width="20%"><input loxiaType="input" trim="true" id="locationCodes" mandatory="true"  name="app.locationCode" /></td>
			</tr>
			<tr>
					<td class="label" width="10%">销售相关单据号：</td>
					<td width="20%"><input loxiaType="input" trim="true"  id="appSlipCode1" name="app.slipCode1" /></td>
					<td class="label">WMS状态:</td>
					<td id="omsStatusLable"></td>
					<!-- <td class="label">收货日期:</td>
					<td id="omsVluesLable"></td> -->
			</tr>	
		<!-- 	<tr>	
					<td class="label">OMS反馈指令号:</td>
					<td id="omsReturnCodeLable"></td>
					<td class="label">OMS反馈备注:</td>
					<td id="omsRemarkLable" colspan="4"></td>
			</tr> -->	
			</table>
		</form>
			<div style="margin-bottom: 15px;margin-top: 5px;margin-left: 20px;">
			<!-- 	<button loxiaType="button" id="btnInQty" style="width: 100px;height: 30px" >录入数量</button> -->
				<input type="hidden" value="1" id="inQty" style="width: 60px;height: 30px" /> &nbsp;
				<span class="label" style="height: 30px" >
				 商品条码：
				</span>
				<span><input id="barCode" loxiaType="input" style="width: 200px;height: 25px" trim="true"/></span>
			</div>
			<form id = "tableForm">
				<table id = "detail_tbl_one" > </table><br><br><br>
				<!-- <table id = "detail_tbl_two"> </table> -->
			</form>
			<!-- <button id="skuAdd" type="button" loxiaType="button" class="confirm" >新增</button> -->
			<!-- <table>
				<tr>
					<td ><b>请选择文档： </b></td>
					<td  align="center">
						<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
							<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
						</form>
					</td>
				</tr>
			</table> <br> -->
			
			<table>
				<tr>
					<td class="label"  width="60px">备注： </td>
					<td width = "400px">
						<textarea loxiaType="input" trim="true" maxlength="200"   id = "memo" mandatory="true" name="memo"></textarea>
					</td>
				</tr>
			</table>
			<div class="buttonlist">
				<button id="suerAdd" type="button" loxiaType="button" class="confirm" >创建</button>
				<button id="addAndConmit" type="button" loxiaType="button" class="confirm" >创建并提交</button>
				<button id="usEless" loxiaType="button">作废</button>
				<button id="again" type="button" loxiaType="button" class="confirm" >重新提交</button>
				<button loxiaType="button" id="back">返回</button>
			</div>
	</div>
	<iframe id="upload" name="upload" class="hidden"></iframe>
</body>
</html>