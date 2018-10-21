<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/VMI-flitting-enter.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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

<div id="div-sta-list" >
<table id="tbl-inbound-purchase"></table>
</div>
<div id="div-sta-detail" class="hidden">
<input type="hidden" name="staId" id="staId"/>
<input type="hidden" name="stvId" id="stvId"/>
<table>
	<tr>
		<td class="label" ><s:text name="label.warehouse.inpurchase.createTime"/></td>
		<td id="createTime"></td>
		<td class="label"><s:text name="label.warehouse.pl.sta"/></td>
		<td id="staCode"></td>
		<td class="label"></td>
		<td id="addiName"></td>
	</tr>
	<tr>
		<td class="label">库存状态</td>
		<td id="status"></td>
		<td class="label">源仓库</td>
		<td id="mainName"></td>
		<td class="label"></td>
		<td></td>
	</tr>
	<tr>
		<td class="label">源店铺</td>
		<td id="ownerOuName"></td>
		<td class="label">目标店铺</td>
		<td id="addiOwnerOuName"></td>
		<td></td>
		<td></td>
	</tr>
</table>
<br />
<iframe id="upload" name="upload" style="display:none;"></iframe>
<div class="divFloat">
	<s:text name="label.warehouse.inpurchase.selectFile"/>
</div>
<div class="divFloat">
	<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
		<input type="file" loxiaType="input" id="staFile" name="file" style="width: 200px;"/> 
		<button type="button" loxiaType="button" id="inImport" class="confirm"><s:text name="label.warehouse.inpurchase.import"/></button> 
		<button type="button" loxiaType="button" id="inExport"><s:text name="label.warehouse.inpurchase.export"/></button>
	</form>
</div>
<br />
<br />
<button class="hidden" type="button" loxiaType="button" id="switch-op-mode" current="barcode-select"><s:text name="buton.warehouse.inpurchase.manual"/></button>
<form id="addForm" name="addForm">
<table id="tbl-select" cellspacing="0" cellpadding="0">
	<tr id="barcode-select" class="hidden">
		<td class="label" width="20%"><s:text name="label.warehouse.inpurchase.code"/></td>
		<td width="20%"><input id="barCode" loxiaType="input" checkmaster="checkBarCode" trim="true"/></td>
		<td width="20%">
		<button loxiaType="button" id="barCodeAdd" type="button"><s:text name="button.warehouse.inpurchase.count"/></button>
		</td>
		<td width="20%"><input id="barCodeCount" loxiaType="number" value="1"/></td>
		<td width="20%" colspan="7">&nbsp;</td>
	</tr>
	<tr id="code-select" style="display: none;">
		<td class="label" width="10%"><s:text name="label.warehouse.inpurchase.jmcode"/></td>
		<td width="10%"><input id="skuCode" loxiaType="input"  checkmaster="checkJmCode" trim="true"/></td>
		<td width="8%" class="label"><s:text name="label.warehouse.inpurchase.extpro"/></td>
		<td width="10%"><select loxiaType="select" id="keyProps">
			<option value=""><s:text name="label.please.select"/></option>
		</select></td>
		<td width="6%" class="label"><s:text name="label.warehouse.inpurchase.name"/></td>
		<td width="10%" id="skuName">&nbsp;</td>
		<td width="10%" class="label"><s:text name="label.warehouse.inpurchase.supplierCode"/></td>
		<td width="8%" id="supplierCode" >&nbsp;</td>
		<td width="4%" class="label"><s:text name="label.warehouse.inpurchase.count"/></td>
		<td width="10%"><input id="skuCount" loxiaType="number" value="1" />
		</td>
		<td width="10%" style="padding-left: 5px;">
		<button id="skuAdd" loxiaType="button" type="button"><s:text name="button.newadd"/></button>
		</td>
	</tr>
</table>
</form>
<div class="district">

<table id="tbl-orderNumber"></table>
<div id="dialog-sns">
	<!--<textarea rows="10" cols="80" id="sns"></textarea>-->
	<table id="tbl-add-sns">
	    <tr><td>请填入序列号</td><td><input loxiaType="input" id="snscode" style="width: 120px;" /><input type="hidden" id="snsindex" /></td></tr>
	</table>
	<table id="tbl-sns"></table>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="confirmsns">确定</button>
		<button type="button" loxiaType="button" class="confirm" id="delSelRows">删除选中行</button>
	</div>
</div>
<div class="buttonlist">
<table>
	<tr>
		<td>
			<button id="receive" type="button" loxiaType="button" class="confirm hidden"><s:text name="button.warehouse.inpurchase.receive"/></button>
			<button id="receiveAll" type="button" loxiaType="button" class="confirm"><s:text name="button.warehouse.inpurchase.receiveall"/></button>
			<button type="button" loxiaType="button" class="confirm" name="backto"><s:text name="button.toback"/></button>
		</td>
	</tr>
</table>
</div>

</div>
<div id="download"></div>
</div>
<div id="div-stv" class="hidden">
<table>
	<tr>
		<td><s:text name="label.warehouse.inpurchase.createTime"/></td>
		<td><input loxiaType="input" id="createTime3" readonly
			style="background-color: #f2f2f2; border: 0"
			 /></td>
	    <td><s:text name="label.warehouse.inpurchase.status"/></td>
        <td><input loxiaType="input" id="status3" readonly
            style="background-color: #f2f2f2; border: 0" value="" /></td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
	</tr>
	<tr>
		<td><s:text name="label.warehouse.inpurchase.left"/></td>
		<td><input loxiaType="input" id="left3" readonly
			style="background-color: #f2f2f2; border: 0"  /></td>
		<td><s:text name="label.warehouse.inpurchase.bzCount"/>：</td>
		<td><input loxiaType="input" id="nowNum" readonly
			style="background-color: #f2f2f2; border: 0"  /></td>
		<td colspan="2"><button type="button" loxiaType="button" class="confirm" id="printSkuInfo"><s:text name="label.warehouse.inpurchase.rintGoodsShelves"/></button>
		</td>
	</tr>
</table>

<table border="black" cellspacing="0"cellpadding="0" id="stvlineListtable">
	<tr id="stvlineList">
		<td class="label"><s:text name="label.warehouse.inpurchase.code"/></td>
		<td class="label"><s:text name="label.warehouse.inpurchase.jmcode"/></td>
		<td class="label"><s:text name="label.warehouse.inpurchase.extpro"/></td>
		<td class="label"><s:text name="label.warehouse.inpurchase.name"/></td>
		<td class="label"><s:text name="label.warehouse.inpurchase.supplierCode"/></td>
		<td class="label"><s:text name="label.warehouse.inpurchase.owner"/></td>
		<td class="label"><s:text name="label.warehouse.inpurchase.bzCount"/></td>
		<td class="label"><s:text name="label.warehouse.inpurchase.ShelvesSituation"/></td>
	</tr>
</table>

<div class="buttonlist">
<button type="button" loxiaType="button" id="executeInventory" class="confirm"><s:text name="buton.warehouse.inpurchase.executeThisInventory"/></button>
<button type="button" loxiaType="button" class="confirm" id="cancelStv"><s:text name="buton.warehouse.inpurchase.closeInventory"/></button>
<!-- <button type="button" loxiaType="button" id="exportInventory" class="confirm">收货上架模版导出</button> -->
<button type="button" loxiaType="button" name="backto"><s:text name="button.toback"/></button>
</div>
 <!-- 
 <div class="divFloat">
			<form method="post" enctype="multipart/form-data" id="importPurchaseInboundForm" name="importPurchaseInboundForm" target="upload">	
				选择导入数据文件&nbsp;&nbsp;<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
			</form>
		</div>
		<div class="divFloat">
			<button loxiaType="button" class="confirm" id="importPurchaseInbound">入库上架导入</button>
		</div>
		<div class="divFloat">
			<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_purchase_inbound"></s:text>.xls&inputPath=tplt_import_purchase_inbound.xls"><s:text name="download.excel.template"></s:text></a>
 </div>
  -->
</div>
</body>
</html>