<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title>采购退货调整入库-执行上架</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/procurement-return-inbound-putaway.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
	<div id="div-sta-list">
		<form id="form_query">
			<table>
				<tr>
					<td class="label" width="20%"><s:text name="label.warehouse.inpurchase.shop"/></td>
					<td width="30%">
						<div style="float: left">
							<select id="companyshop" name="stacmd.owner" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</div>
						<div style="float: left">
							<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
						</div>
					</td>					
					<td class="label" width="20%"></td>
					<td width="30%">
					</td>		
				</tr>
				<tr>
					<td class="label">作业单号</td>
					<td><input loxiaType="input" trim="true" name="stacmd.code" /></td>
					<td class="label" style="color:blue">相关单据号（申请单号）</td>
					<td><input loxiaType="input" trim="true" name="stacmd.refSlipCode" /></td>
				</tr>
				<tr>
					<td class="label">创建时间</td>
					<td>
						<input loxiaType="date" trim="true" name="startTime" showTime="true"/></td>
					<td class="label" style="text-align:center"><s:text name="label.warehouse.pl.to"/></td>
					<td><input loxiaType="date" trim="true" name="endTime" showTime="true"/></td>
				</tr>
<!-- 				<tr> 
					<td class="label" width="20%"><s:text name="label.warehouse.pl.sta.delivery"></s:text></td>
					<td width="30%">
						<select name="lpcode" id="selLpcode" loxiaType="select">
							<option value=""><s:text name="label.wahrhouse.sta.select"></s:text></option>
						</select>
					</td>
					<td class="label" width="20%">快递单号</td>
					<td>
						<input loxiaType="input" trim="true" name="trackingNo" />
					</td>
				</tr>-->
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search"><s:text name="button.search"/></button>
			<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
		</div>
		<table>
			<tr>
				<td><label id = "tempList"></td>
			</tr>
		</table>
		<table id="tbl-inbound-purchase"></table>
		<div id="pager"></div>
<!-- 		<div class="buttonlist"> -->
<!-- 			<button id="export" loxiaType="button" class="confirm">导出批量执行模板</button> -->
<!-- 			<td> -->
<!-- 						<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload"> -->
<!-- 							<input type="file" loxiaType="input" id="staFile" name="file" style="width: 200px;"/> -->
<!-- 						</form> -->
<!-- 					</td> -->
<!-- 		</div> -->
		<div class="buttonlist">
			<table>
				<tr>
					<td class="label">
						采购退货入库上架导入：
					</td>
					<td>
						<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
							<input type="file" loxiaType="input" id="staFile" name="staFile" style="width: 200px;"/>
						</form>
					</td>
					<td>
					<button id="export" loxiaType="button" >导出批量执行模板</button>
					</td>
					<td>
						<button loxiaType="button" id="expDiff" class="confirm">导入执行</button>
					</td>
				</tr>
			</table>
		<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
	</div>
	</div>
	    <input type="hidden" name="staId" id="staId"/>
		<input type="hidden" name="stvId" id="stvId"/>
		<input type="hidden" name="vmiCodeId" id="vmiCodeId"/>
		<input type="hidden" name="totalActualId" id="totalActualId"/>
		<input type="hidden" name="isSpecialPackagingId" id="isSpecialPackagingId"/>
	<iframe id="upload" name="upload" class="hidden"></iframe>
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
			<td><s:text name="label.warehouse.inpurchase.refcode"/>:</td>
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
			<td><s:text name="label.warehouse.inpurchase.bzCount"/>:</td>
			<td><input loxiaType="input" id="nowNum" readonly
				style="background-color: #f2f2f2; border: 0" value="220" /></td>
<%-- 			<td colspan="2"><button type="button" loxiaType="button" class="confirm" id="printSkuInfo"><s:text name="label.warehouse.inpurchase.rintGoodsShelves"/></button> --%>
			</td>
		</tr>
	</table>

	<table border="black" cellspacing=0 cellpadding=0 id="stvlineListtable">
			<tr id="stvlineList">
				<td class="label"><s:text name="label.warehouse.inpurchase.code"/></td>
				<td class="label"><s:text name="label.warehouse.inpurchase.jmcode"/></td>
				<td class="label"><s:text name="label.warehouse.inpurchase.extpro"/></td>
				<td class="label"><s:text name="label.warehouse.inpurchase.name"/></td>
				<td class="label"><s:text name="label.warehouse.inpurchase.supplierCode"/></td>
				<td class="label">库存状态</td>
				<td class="label"><s:text name="label.warehouse.inpurchase.bzCount"/></td>
				<td class="label">上架情况（保质期商品 必须填写 生产日期或过期时间）</td>
			</tr>
		</table>
	<iframe id="upload" name="upload" style="display:none;"></iframe>
	<div class="buttonlist">
	<button type="button" loxiaType="button" id="executeInventory" class="confirm"><s:text name="buton.warehouse.inpurchase.executeThisInventory"/></button>
<!-- 	<button type="button" loxiaType="button" id="exportInventory" class="confirm">收货上架模版导出</button> -->
	<button type="button" loxiaType="button"  id="cancelStv"><s:text name="buton.warehouse.inpurchase.closeInventory"/></button>
	</div>
	<div class="hidden">
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
</div>
</body>


</html>