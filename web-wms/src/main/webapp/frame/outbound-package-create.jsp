<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/outbound-package-create.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.outbound.package"/></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="divMain">
		<form id="formQuery" name="formQuery">
			<table width="70%">
				<tr>
					<td width="13%" class="label">创建时间</td>
					<td width="18%"><input id="startDate" name="staCmd.startCreateTime1" loxiaType="date" showTime="true"/></td>
					<td width="13%" class="label">到</td>
					<td width="18%"><input id="endDate" name="staCmd.endCreateTime1" loxiaType="date" showTime="true"/></td>
					<td width="13%" class="label">状态</td>
					<td width="18%">
						<select loxiaType="select" id="statusList" name="staCmd.intStaStatus">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="8"><s:text name="label.warehouse.sta.status.packing"></s:text> </option>
							<option value="10"><s:text name="label.warehouse.sta.status.finished"></s:text> </option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">作业单号</td>
					<td><input loxiaType="input"  trim="true" id="staCode" name="staCmd.code"/></td>
					<td class="label">相关单据号</td>
					<td><input loxiaType="input"  trim="true" id="staSlipCode" name="staCmd.refSlipCode"/></td>
					<td class="label">相关单据号2(LOAD KEY)</td>
					<td><input loxiaType="input"  trim="true" id="staSlipCode2" name="staCmd.slipCode2"/></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button name="query" id="query" type="button" loxiaType="button" class="confirm">查询</button>
			<button id="btnReset" type="button" loxiaType="button" >重置</button>
		</div>
		<table id="tbl_sta"></table>
		<div id="tbl_sta_page"></div>
	</div>
	<div id="divDetail" class="hidden">
		<input type="hidden" value="" id="staid"/>
		<table width="80%">
			<tr>
				<td class="label" width="13%">
					作业单
				</td>
				<td id="staCode_" width="20%">
				 
				</td>
				<td class="label" width="13%">
					相关单据
				</td>
				<td id="staSlipCode_" width="20%">
				 
				</td>
				<td class="label" width="13%">
					创建时间
				</td>
				<td id="createTime" width="20%">
				 
				</td>
			</tr>
			<tr>
				<td class="label" width="13%">
					联系人
				</td>
				<td id="principal" width="20%">
				 
				</td>
				<td class="label" width="13%">
					手机
				</td>
				<td id="telephone" width="20%">
				 
				</td>
				<td class="label" width="13%">
					送货地址
				</td>
				<td id="address" width="20%">
					 
				</td>
			</tr>
		</table>
		<div id="tabs">
			<ul>
				<li><a href="#tabs_1">箱号列表</a></li>
				<li><a href="#tabs_2">计划执行明细</a></li>
				<li><a href="#tabs_3">已装箱明细</a></li>
				<li><a href="#tabs_4">差异明细</a></li>
			</ul>
			<div id="tabs_1">
				<table id="tbl_sta_package"></table>
			</div>
			<div id="tabs_2">
				<table id="tbl_sta_line"></table>
				<div id="tbl_sta_line_page"></div>
			</div>
			<div id="tabs_3">
				<form id="formPgQuery" name="formPgQuery">
					<table width="70%">
						<tr>
							<td class="label" width="13%">箱号编码</td>
							<td width="20%"><input loxiaType="input"  trim="true" name="skuCmd.cartonCode"/></td>
							<td class="label" width="13%">商品SKU编码</td>
							<td width="20%"><input loxiaType="input"  trim="true" name="skuCmd.code"/></td>
							<td class="label" width="13%">商品条码</td>
							<td width="20%"><input loxiaType="input"  trim="true" name="skuCmd.barCode"/></td>
						</tr>
						<tr>
							<td class="label">货号</td>
							<td><input loxiaType="input"  trim="true" name="skuCmd.supplierCode"/></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</table>
				</form>
				<div class="buttonlist">
					<button name="pgQuery" id="pgQuery" type="button" loxiaType="button" class="confirm">查询</button>
					<button id="btnPgReset" type="button" loxiaType="button" >重置</button>
				</div>
				<table id="tbl_pg_line"></table>
				<div id="tbl_pg_line_page"></div>
			</div>
			<div id="tabs_4">
				<table id="tbl_stv_line"></table>
				<div id="tbl_stv_line_page"></div>
			</div>
		</div>
		
		<br/>
		<div class="buttonlist">
			<button id="btnPgCreate" type="button" loxiaType="button" class="confirm">新建箱</button>
			<button id="btnExe" type="button" loxiaType="button" class="confirm">确认执行出库</button>
			<button id="btnPrintTotal" type="button" loxiaType="button" class="confirm">打印装箱汇总</button>
			<button id="printPod" loxiaType="button" class="confirm">打印POD</button>
			<button id="btnBack" type="button" loxiaType="button" >返回</button>
		</div>
		
	</div>
</body>
</html>