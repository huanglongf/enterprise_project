<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/include-pda-inbound-log-detial.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<div id="padLogTabs">
	<ul>
		<li><a href="#padLogTabs_1">PDA记录处理</a></li>
		<li><a href="#padLogTabs_2">PDA处理结果记录</a></li>
	</ul>
	<div id="padLogTabs_1">
		<button type="button" loxiaType="button" class="confirm" id="btnExePostLog" >处理上传记录</button>
		<button type="button" loxiaType="button" id="btnExpErrorLog" >导出无效记录</button>
		<button type="button" loxiaType="button" id="btnDelErrorLog" >删除无效记录</button>
	</div>
	<div id="padLogTabs_2">
		<form id="pdaLogQueryForm" name="pdaLogQueryForm">
			<table width="70%">
				<tr>
					<td width="10%" class="label">
						创建开始时间
					</td>
					<td width="15%">
						<input loxiaType="date" name="cmd.fromDateStr" showtime="true" ></input>
					</td>
					<td width="10%" class="label">
						到
					</td>
					<td width="15%">
						<input loxiaType="date" name="cmd.toDateStr" showtime="true" ></input>
					</td>
					<td width="10%">
					</td>
					<td width="15%">
					</td>
				</tr>
				<tr>
					<td class="label">
						商品名称
					</td>
					<td>
						<input loxiaType="input" trim="true" name="cmd.skuName"></input>
					</td>
					<td class="label">
						商品Sku编码
					</td>
					<td>
						<input loxiaType="input" trim="true" name="cmd.skuCode"></input>
					</td>
					<td class="label">
						商品条码
					</td>
					<td>
						<input loxiaType="input" trim="true" name="cmd.skuBarcode"></input>
					</td>
				</tr>
				<tr>
					<td class="label">
						货号
					</td>
					<td>
						<input loxiaType="input" trim="true" name="cmd.skuSupplierCode"></input>
					</td>
					<td class="label">
						库位编码
					</td>
					<td>
						<input loxiaType="input" trim="true" name="cmd.locCode"></input>
					</td>
					<td class="label">
						PDA机器编码
					</td>
					<td>
						<input loxiaType="input" trim="true" name="cmd.pdaCode"></input>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="pdaLogQuery" ><s:text name="button.search"></s:text> </button>
			<button type="button" loxiaType="button" id="pdaLogQuseryReset"><s:text name="button.reset"></s:text> </button>
		</div>
		<table id="pda_detial"></table>
		<div id="pda_detial_pager"></div>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="pdaLogDelete" >删除选中</button>
			<button type="button" loxiaType="button" id="pdaLogDeleteAll" >清空日志</button>
			<button type="button" loxiaType="button" class="confirm" id="pdaLogExport" >PDA操作汇总导出</button>
		</div>
		<iframe id="pdaLogFrame" name="pdaLogFrame" class="hidden"></iframe>
	</div>
</div>