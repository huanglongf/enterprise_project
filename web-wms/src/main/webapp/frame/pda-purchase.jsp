<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/pda-purchase.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
.showborder {
	border: thin;
}
	.divFloat{
		float: left;
		margin-right: 10px;
	}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	
	<div id="div-sta-list" >
		<form id="queryForm" name="queryForm">
			<table>
				<tr>
					<td width="20%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="staCommand.code" /></td>
					<td width="20%" class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="staCommand.refSlipCode"/></td>
				</tr><tr>
					<td class="label"><s:text name="label.warehouse.pl.createtime"></s:text></td>
					<td><input loxiaType="date" name="createDate" showTime="true"></input></td>
					<td class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td><input loxiaType="date" name="endCreateTime" showTime="true"/></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="PDA_search" ><s:text name="button.search"></s:text> </button>
			<button type="button" loxiaType="button" id="PDA_reset"><s:text name="button.reset"></s:text> </button>
		</div>
		<table id="tbl-inbound-purchase"></table>
		<div id="pager"></div>
	</div>
	<div id="div-sta-detail" class="hidden">
		<input type="hidden" name="staId" id="staId"/>
		<input type="hidden" name="stvId" id="stvId"/>
		<table>
			<tr>
				<td class="label"><s:text name="label.warehouse.inpurchase.createTime"/>:</td>
				<td id="createTime"></td>
				<td class="label"><s:text name="label.warehouse.pl.sta"/>:</td>
		        <td id="staCode"></td>
				<td class="label"><s:text name="label.warehouse.inpurchase.po"/>:</td>
				<td id="po"></td>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
			   <td class="label"><s:text name="label.warehouse.inpurchase.owner"/>:</td>
		        <td id="owner"></td>
				<td class="label"><s:text name="label.warehouse.inpurchase.status"/>:</td>
				<td id="status"></td>
				<td class="label"><s:text name="label.warehouse.inpurchase.left"/>:</td>
				<td id="left"></td>
				<td colspan="2">&nbsp;</td>
			</tr>
		</table>
		<table id="tbl-orderNumber"></table>
		<div id="pager1"></div>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="pdaSave" >PDA收货 </button>
			<button type="button" loxiaType="button" id="PDA_break">返回 </button>
		</div>
	</div>
	<div id="div-stv" class="hidden">
		<table>
			<tr>
				<td><s:text name="label.warehouse.inpurchase.createTime"/>:</td>
				<td><input loxiaType="input" id="createTime3" readonly
					style="background-color: #f2f2f2; border: 0"
					 /></td>
				<td><s:text name="label.warehouse.pl.sta"/>:</td>
		        <td><input loxiaType="input" id="staCode3" readonly
		            style="background-color: #f2f2f2; border: 0"  /></td>
				<td><s:text name="label.warehouse.inpurchase.po"/>:</td>
				<td><input loxiaType="input" id="po3" readonly
					style="background-color: #f2f2f2; border: 0"  /></td>
				<td><s:text name="label.warehouse.inpurchase.owner"/>:</td>
				<td><input loxiaType="input" id="owner3"  readonly
					style="background-color: #f2f2f2; border: 0" /></td>
			</tr>
			<tr>
				<td><s:text name="label.warehouse.inpurchase.status"/>:</td>
				<td><input loxiaType="input" id="status3" readonly
					style="background-color: #f2f2f2; border: 0" value="XXXXX" /></td>
				<td><s:text name="label.warehouse.inpurchase.left"/>:</td>
				<td><input loxiaType="input" id="left3" readonly
					style="background-color: #f2f2f2; border: 0" value="123" /></td>
				<td></td>
				<td></td>
				<td colspan="2"><button type="button" loxiaType="button" class="confirm hidden" id="printSkuInfo"><s:text name="label.warehouse.inpurchase.rintGoodsShelves"/></button>
				</td>
			</tr>
			<tr>
				<td>PDA机器编码:</td>
				<td>
					<select loxiaType="select" name="pdaCode" id="pdaCode"></select>
				</td>
				<td>
					<button type="button" loxiaType="button" id="queryPDACode" class="confirm">查寻PDA机器编码</button>
				</td>
				<td colspan="5">
				</td>
			</tr>
		</table>
		<div id="pdaOpDiv" class="hidden">
			<%@include file="include-pda-inbound-log-detial.jsp"%>
			<!--  -->
			<br/>
			<br/>
			<div id="divSnImport" class="hidden">
				<div class="divFloat">
					<span class="label"><s:text name="label.warehouse.choose.file"></s:text></span>
				</div>
				<div class="divFloat">
					<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="pdaLogFrame">	
						<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
					</form>
				</div>
				<div class="divFloat">
					<button loxiaType="button" class="confirm" id="import"><s:text name="button.import_SN"></s:text></button>
				</div>
				<div class="divFloat">
					<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_pda_purchase_sn_import"></s:text>.xls&inputPath=tplt_import_pda_purchase_sn.xls"><s:text name="download.excel.template"></s:text></a>
				</div>
				<div class="clear"></div>
			</div>
			<!-- <div id="invoice" class="hidden">
				<table width="70%">
					<tr>
						<td width="15%" class="label">
							发票号
						</td>
						<td  width="30%">
							<input loxiaType="input" name="invoiceNumber" id="invoice_invoiceNumber" readonly="readonly" />
						</td>
						<td colspan="2" id="invoice_select2TD"  class='hidden'>
							<select  loxiaType="select" name="numberSelect" id="invoice_selectInv">
								<option value="">请选择发票号</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="15%" class="label">
							税收系数
						</td>
						<td  width="30%">
							<input loxiaType="number" mandatory="true" decimal="2"  readonly="readonly" id="invoice_dutyPercentage" trim="true" />
						</td>
						<td width="15%" class="label">
							其他系数
						</td>
						<td  width="30%">
							<input loxiaType="number" mandatory="true" decimal="2" readonly="readonly" id="invoice_miscFeePercentage" trim="true"  />
						</td>
					</tr>
				</table>
			</div> -->
 			<div class="buttonlist">
				<button type="button" loxiaType="button" id="pdaExecuteInventory" class="confirm"><s:text name="buton.warehouse.inpurchase.executeThisInventory"/></button>
				<button type="button" loxiaType="button" id="pdaClosePO" class="confirm"><s:text name="buton.warehouse.inpurchase.closePo"/></button>
				<button type="button" loxiaType="button" id="pdaCancelStv"><s:text name="buton.warehouse.inpurchase.closeInventory"/></button>
			</div>
		</div>
	</div>
</body>
</html>