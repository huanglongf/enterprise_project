<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse-predefined-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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


<form id="form_query">
	<table>
		<tr>
			<td class="label" width="20%">作业单号：</td>
			<td width="30%"><input loxiaType="input" trim="true" name="sta.code" /></td>
			
			<td class="label" width="20%"><s:text name="label.warehouse.inpurchase.shop"/></td>
			<td width="30%">
				<div style="float: left">
					<select id="companyshop" name="sta.owner" loxiaType="select">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
					</select>
				</div>
				<div style="float: left">
					<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">相关单据号</td>
			<td><input loxiaType="input" trim="true" name="sta.refSlipCode" /></td>
			<td class="label"></td>
			<td></td>
		</tr>
		<tr>
			<td class="label">创建时间：</td>
			<td>
				<input loxiaType="date" trim="true" name="startTime" showTime="true"/></td>
			<td class="label" style="text-align:center"><s:text name="label.warehouse.pl.to"/></td>
			<td><input loxiaType="date" trim="true" name="endTime" showTime="true"/></td>
		</tr>
		<tr>
			<td class="label">到货时间：</td>
			<td>
				<input loxiaType="date" trim="true" name="arriveStartTime" showTime="true"/></td>
			<td class="label" style="text-align:center"><s:text name="label.warehouse.pl.to"/></td>
			<td>
				<input loxiaType="date" trim="true" name="arriveEndTime" showTime="true"/>
			</td>
		</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button loxiaType="button" class="confirm" id="search"><s:text name="button.search"/></button>
		<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
	</div>
<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
<table id="tbl-inbound-purchase"></table>
<div id="pager"></div>
</div>
<div id="div-sta-detail" class="hidden">
<input type="hidden" name="staId" id="staId"/>
<input type="hidden" name="stvId" id="stvId"/>
<div >
	<table>
		<tr>
			<td class="label"><s:text name="label.warehouse.inpurchase.createTime"/>:</td>
			<td id="createTime"></td>
			<td class="label"><s:text name="label.warehouse.pl.sta"/>:</td>
	        <td id="staCode"></td>
			<td class="label"></td>
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
	<iframe id="upload" name="upload" style="display:none;"></iframe>
	<div class="divFloat">
		<s:text name="label.warehouse.inpurchase.selectFile"/>
	</div>
	<div class="divFloat">
		<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
			<input type="file" loxiaType="input" id="staFile" name="staFile" style="width: 200px;"/>
		</form>
	</div>
	<div class="divFloat">
		<button type="button" loxiaType="button" id="import" class="confirm"><s:text name="label.warehouse.inpurchase.import"/></button>
	</div>
	<div class="divFloat">
		<button type="button" loxiaType="button" id="export"><s:text name="label.warehouse.inpurchase.export"/></button>
	</div>
	<br /><br />
</div>

<button type="button" loxiaType="button" id="switch-op-mode" current="barcode-select"><s:text name="buton.warehouse.inpurchase.manual"/></button>
<form id="addForm" name="addForm">
<table id="tbl-select" cellspacing="0" cellpadding="0">
    <tr id="barcode-select">
        <td class="label" width="20%"><s:text name="label.warehouse.inpurchase.code"/></td>
        <td width="20%"><input id="barCode" loxiaType="input" checkmaster="checkBarCode" trim="true" /></td>
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
	<!--<textarea rows="10" cols="80" id="sns">
	</textarea>
	-->
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
	<div style="margin-bottom: 10px;">
		<button id="receive" type="button" loxiaType="button" class="confirm"><s:text name="button.warehouse.inpurchase.receive"/></button>
		<button id="receiveAll" type="button" loxiaType="button" class="confirm"><s:text name="button.warehouse.inpurchase.receiveall"/></button>
		<button id="closeSta" type="button" loxiaType="button" class="hidden"><s:text name="button.warehouse.inpurchase.closesta"/></button>
		<button type="button" loxiaType="button"  id="backto"><s:text name="button.toback"/></button>
	</div>
</div>
<div class="buttonlist hidden">
	<div class="divFloat">
		<form method="post" enctype="multipart/form-data" id="importReceiveForm" name="importReceiveForm" target="upload">
			<span>入库数量确认导入</span> <input type="file" loxiaType="input" id="goodsInfoFile" name="goodsInfoFile" style="width: 200px;"/>
		</form>
	</div>
	<div class="divFloat">
		<button type="button" loxiaType="button" id="importReveive" class="confirm"><s:text name="label.warehouse.inpurchase.import.receive"/></button>
		<a id="outputNUM" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="/wms/warehouse/excelDownload.do?fileName=收货入库数量确认导入.xls&inputPath=tplt_import_inbound_purchase_receive_confirm.xls" role="button">
			<span class="ui-button-text" id="exportReveive"><s:text name="label.warehouse.inpurchase.export.receive"/></span>
		</a>
	</div>
	<br/>
	<br/>
	<div style="clear: both; margin-top: 10px;"> 
		<button id="pdaReceive" type="button" loxiaType="button" class="confirm">PDA<s:text name="button.warehouse.inpurchase.receive"/></button>
		<button id="pdaReceiveAll" type="button" loxiaType="button" class="confirm">PDA<s:text name="button.warehouse.inpurchase.receiveall"/></button>
	</div>
</div>
</div>
<div id="download"></div>
<table>
	<tr>
		<td class="label"><s:text name="label.warehouse.inpurchase.createTime"/>:</td>
		<td id="createTime2"></td>
		<td class="label"><s:text name="label.warehouse.pl.sta"/>:</td>
        <td id="staCode2"></td>
		<td class="label"></td>
		<td ></td>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td class="label"><s:text name="label.warehouse.inpurchase.owner"/>:</td>
        <td id="owner2"></td>
		<td class="label"><s:text name="label.warehouse.inpurchase.status"/>:</td>
		<td id="status2"></td>
		<td class="label"><s:text name="label.warehouse.inpurchase.left"/>:</td>
		<td id="left2"></td>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td class="label"><s:text name="label.warehouse.location.comment"></s:text>:</td>
        <td id="memo2"></td>
	</tr>
</table>
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
			<td><s:text name="label.warehouse.inpurchase.owner"/>:</td>
			<td><input loxiaType="input" id="owner3"  readonly
				style="background-color: #f2f2f2; border: 0" /></td>
			<td></td>
		</tr>
		<tr>
			<td><s:text name="label.warehouse.inpurchase.status"/>:</td>
			<td><input loxiaType="input" id="status3" readonly
				style="background-color: #f2f2f2; border: 0" value="XXXXX" /></td>
			<td><s:text name="label.warehouse.inpurchase.left"/>:</td>
			<td><input loxiaType="input" id="left3" readonly
				style="background-color: #f2f2f2; border: 0" value="123" /></td>
			<td><s:text name="label.warehouse.inpurchase.bzCount"/>:</td>
			<td><input loxiaType="input" id="nowNum" readonly
				style="background-color: #f2f2f2; border: 0" value="220" /></td>
			<td><button type="button" loxiaType="button" class="confirm hidden" id="printSkuInfo"><s:text name="label.warehouse.inpurchase.rintGoodsShelves"/></button>
			</td>
		</tr>
	</table>
	<div id="inboundNormalDiv">
		<table border="black" cellspacing=0 cellpadding=0 id="stvlineListtable">
			<tr id="stvlineList">
				<td class="label"><s:text name="label.warehouse.inpurchase.code"/></td>
				<td class="label"><s:text name="label.warehouse.inpurchase.jmcode"/></td>
				<td class="label"><s:text name="label.warehouse.inpurchase.extpro"/></td>
				<td class="label"><s:text name="label.warehouse.inpurchase.name"/></td>
				<td class="label"><s:text name="label.warehouse.inpurchase.supplierCode"/></td>
				<td class="label"><s:text name="label.warehouse.inpurchase.bzCount"/></td>
				<td class="label"><s:text name="label.warehouse.inpurchase.ShelvesSituation"/></td>
			</tr>
			
		</table>
		<div class="buttonlist">
			<button type="button" loxiaType="button" id="executeInventory" class="confirm"><s:text name="buton.warehouse.inpurchase.executeThisInventory"/></button>
			<button type="button" loxiaType="button" id="closePO" onclick="closePO()" class="confirm"><s:text name="buton.warehouse.inpurchase.closePo"/></button>
			<button type="button" loxiaType="button" id="exportInventory" class="confirm">收货上架模版导出</button>
			<button type="button" loxiaType="button"  id="cancelStv"><s:text name="buton.warehouse.inpurchase.closeInventory"/></button>
		</div>
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
	</div>
	<div id="pdaOpDiv" class="hidden">
		<%@include file="include-pda-inbound-log-detial.jsp"%>
		<div class="buttonlist">
			<button type="button" loxiaType="button" id="pdaExecuteInventory" class="confirm"><s:text name="buton.warehouse.inpurchase.executeThisInventory"/></button>
			<button type="button" loxiaType="button" id="pdaClosePO" class="confirm"><s:text name="buton.warehouse.inpurchase.closePo"/></button>
			<button type="button" loxiaType="button"  id="pdaCancelStv"><s:text name="buton.warehouse.inpurchase.closeInventory"/></button>
		</div>
	</div>
</div>
</body>


</html>