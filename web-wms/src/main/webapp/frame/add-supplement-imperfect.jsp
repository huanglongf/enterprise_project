<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/add-supplement-imperfect.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
.showborder {
	border: thin;
}
.divFloat{
	float: left;
	margin-right: 10px;
}
	.tips{
				width:100%; height: 50%; text-align:center; color:red; font-size: 40px; font-weight: bold;
			}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	<div id="div-sta-list">
		<form id="form_query">
			<table>
				
				<tr>
				<td class="label" width="20%">商品编码</td>
					<td width="30%"><input loxiaType="input" trim="true" name="sku.jmCode" /></td>
					<td class="label" width="20%">条形码</td>
					<td width="30%"><input loxiaType="input" trim="true" name="sku.barCode" /></td>
				</tr>
				<tr>
					<td class="label" width="20%">货号</td>
					<td width="30%"><input loxiaType="input" trim="true" name="sku.supplierCode" /></td>
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
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search"><s:text name="button.search"/></button>
			<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
		</div>
		<table id="tbl-inbound-purchase"></table>
		<div id="pager"></div>
		<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
	</div>
	<div id="div-sta-detail" class="hidden">
		<input type="hidden" name="staId" id="staId"/>
		<input type="hidden" name="stvId" id="stvId"/>
		<table>
			<tr>
				<td class="label"><s:text name="label.warehouse.inpurchase.createTime"/></td>
				<td id="createTime"></td>
				<td class="label"><s:text name="label.warehouse.pl.sta"/></td>
		        <td id="staCode"></td>
		        <td class="label"><s:text name="label.warehouse.inpurchase.refcode"/></td>
				<td id="po"></td>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.inpurchase.owner"/></td>
		        <td id="owner"></td>
				<td class="label"><s:text name="label.warehouse.inpurchase.status"/></td>
				<td id="status"></td>
				<td class="label"><s:text name="label.warehouse.inpurchase.left"/></td>
				<td id="left"></td>
				<td colspan="2">&nbsp;</td>
			</tr>
		</table>
		<br />
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
				<td colspan="2"><button type="button" loxiaType="button" class="confirm" id="printSkuInfo"><s:text name="label.warehouse.inpurchase.rintGoodsShelves"/></button>
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
		<button type="button" loxiaType="button" id="exportInventory" class="confirm">收货上架模版导出</button>
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
	<div id="dialog_newArea" class="hidden">
			<table>
				<tr>
					<td width="85px"><label><b>残次类型：</b></label></td>
					<td><select loxiaType="select"  id="imperfect" style="width:100px" name="imperfect">
					<option values="其他">其他</option>
					</select></td>	
					
				</tr>
				<tr>
					<td><label><b>残次原因：</b></label></td>
					<td><select loxiaType="select"  id="imperfectLine" style="width:100px" name="imperfectLine">
					<option values="其他">其他</option>
					</select></td>
				</tr>
				<tr>
					<td><label><b>数量：</b></label></td>
					<td><input loxiaType="input" id="qty" name="qty" class="hidden" />
					<input loxiaType="input" id="skuId" name="skuId" class="hidden" />
					<input loxiaType="input" id="quantity" name="quantity" width="120px"></input></td>
				</tr>
					<tr>
					<td > <div id="factory" name="factory" class="hidden"><b>工厂代码：</b></div></td>
					<td> <div id="factory1" name="factory1" class="hidden">
					<input type="text" name="factoryCode" id="factoryCode" loxiaType="input" /></div></td>
		    	
				</tr>
				<tr>
				<td>
				<div id="poTime" name="poTime" class="hidden"><b>下单日期：</b></div></td>
				<td><div id="poTime1" name="poTime1" class="hidden">
				<input loxiaType="date" trim="true" id="poId" name="poId" showTime="true"/></div></td>
				</tr> 
				<tr>
					<td><label><b>备注：</b></label></td>
					<td><input type="text" name="memo" loxiaType="input" id="memo" style="width:200px"/></td>
				</tr>
			</table>
			<div class="buttonlist">
					<button id="areaNew" type="button" loxiaType="button" class="confirm" >生成并打印</button>
					<button id="areaCancel" type="button" loxiaType="button" >取消</button>
			</div>
		</div>
	<div id="dialog_error_ok">
		<div id="error_text_ok" ></div>
	</div>
</body>


</html>