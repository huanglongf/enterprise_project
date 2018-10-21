<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/include-reconcile-useredit.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<div id="dialog_user_edit">
		<table width="80%">
			<tr>
				<td width="20%" class="label">
					起始日期
				</td>
				<td width="30%">
					<input loxiaType="date" id="stardate" />
				</td>
				<td width="20%" class="label">
					结束日期
				</td>
				<td width="30%">
					<input loxiaType="date" id="enddate" />
				</td>
			</tr>
			<tr>
				<td class="label" >
					订单号
				</td>
				<td>
					<input id="code" loxiaType="input" trim="true" />
				</td>
				<td class="label">
					淘宝订单号
				</td>
				<td>
					<input id="tb_code" loxiaType="input" trim="true" />
				</td>
			</tr>
			<tr>
				<td class="label">
					商品编码
				</td>
				<td>
					<input id="sku_code" loxiaType="input" trim="true" />
				</td>
				<td class="label">
					供应商编码
				</td>
				<td>
					<input id="supply_code" loxiaType="input" trim="true" />
				</td>
			</tr>
		</table>
		<div class="buttonlist">
			<button id="btnQuery" loxiaType="button" class="confirm">查询</button>
		</div>
		<table id="tbl_so_edit"></table>
		<div id="tbl_so_page2"></div>
		<input type="hidden" id="rouid" name="staId" />
		<div class="buttonlist">
			<button loxiaType="button" id="delOrderref" class="confirm">移除选中订单</button>
			<button id="btnClose" loxiaType="button" >关闭</button>
		</div>
	</div>