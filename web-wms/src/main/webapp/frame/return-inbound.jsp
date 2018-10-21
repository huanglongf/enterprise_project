<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/return-inbound.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
							<select id="companyshop" name="sta.owner" loxiaType="select">
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
					<td><input loxiaType="input" trim="true" name="sta.code" /></td>
					<td class="label" style="color:blue">相关单据号（申请单号）</td>
					<td><input loxiaType="input" trim="true" name="sta.refSlipCode" /></td>
				</tr>
				<tr>
					<td class="label" width="20%">订单号（相关单据1）</td>
					<td width="30%"><input loxiaType="input" trim="true" name="slipCode1" /></td>
					<td class="label" width="20%" style="color:blue">平台单据号（相关单据2）</td>
					<td width="30%"><input loxiaType="input" trim="true" name="slipCode2" /></td>
				</tr>
				<tr>
					<td class="label" width="20%" style="color:blue">商城自主退单号（相关单据3）</td>
					<td width="30%"><input loxiaType="input" trim="true" name="sta.slipCode3" /></td>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td class="label">创建时间</td>
					<td>
						<input loxiaType="date" trim="true" name="startTime" showTime="true"/></td>
					<td class="label" style="text-align:center"><s:text name="label.warehouse.pl.to"/></td>
					<td><input loxiaType="date" trim="true" name="endTime" showTime="true"/></td>
				</tr>
				<tr>
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
				</tr>
				<tr>
					<td class="label"  width="20%">
						<s:text name="label.warehouse.sta.isneedinvoice"></s:text>
					</td>
					<td width="30%">
						<select id="isNeedInvoice" name="isNeedInvoice" loxiaType="select" >
							<option value=""><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="1"><s:text name="label.warehouse.sta.isneedinvoice.true"></s:text> </option>
							<option value="0"><s:text name="label.warehouse.sta.isneedinvoice.false"></s:text></option>
						</select>
					</td>
					
					
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search"><s:text name="button.search"/></button>
			<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
		</div>
		<table>
			<tr>
			  <td class="label"  width="20%" style="color: red;">
						RFID :
			  </td>
			  <td>                                                                                
					<input  type="text" name="nikeRFID"   id="nikeRFID" value="${ RFIDCode}"  readonly="readonly"/>
					
			  </td>
				<td class="label">快捷查询作业单号</td>
				<td>
					<input loxiaType="input" id="shortcut_code" trim="true" />
				</td>
				<td><label id = "tempList"></td>
			</tr>
		</table>
		<table id="tbl-inbound-purchase"></table>
		<div id="pager"></div>
		<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
	</div>
	<div id="div-sta-detail" class="hidden">
		<input type="hidden" name="staId" id="staId"/>
		<input type="hidden" name="stvId" id="stvId"/>
		<input type="hidden" name="vmiCodeId" id="vmiCodeId"/>
		<input type="hidden" name="totalActualId" id="totalActualId"/>
		<input type="hidden" name="isSpecialPackagingId" id="isSpecialPackagingId"/>
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
		<div class="district">
			<table id="tbl-orderNumber"></table>
			<div id="dialog-sns-view">
				<h3>SN序列号</h3>
				<div id="divSn">
					<div id="divClear" class="clear"></div>
				</div>
			</div>
			<br />
			<table id="tbl-select" cellspacing="0" cellpadding="0" style="font-size: 22">
			    <tr>
			    	<td width="120px" class="label">商品条码/SN号：</td>
                    <td>
                    	<input id="barCode" loxiaType="input" checkmaster="checkBarCode" trim="true"/>
                    	<input id="barCodeCount" loxiaType="number" class="hidden" value="1"/>
                    </td>
                   	<td width="200px" class="label">SN号导入：</td>
					<td  align="center">
						<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
							<input type="file" name="file" loxiaType="input" id="file" style="width:150px"/>
						</form>
					</td>
					<td >
		           		<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=退换货入库SN序列号批量导入.xls&inputPath=tplt_import_return_inbound_sn.xls">模板文件下载</a>
			       		<button loxiaType="button" class="confirm" id='sn_import' >导入</button>
			        </td>
		    	</tr>
		    	<tr>
                    <td width="120px" id="add_barCode"></td>
                    <td width="120px" id="add_jmCode"></td>
                    <td width="200px" id="add_skuName"></td>
                    <td width="120px">
                        <select loxiaType="select" id="add_status">
                        </select>
                    </td>
                    <td width="500px">
                    	<input id="addBarCode" type="hidden"  /> 
                        <button loxiaType="button" class="confirm" id="addSku">确认</button>
                        <span id="tip" class="label hidden" style="color:#f00">金额大于1500,QS商品,Jordon商品只能退成“待处理品”</span>
                        <span id="tipAD" class="label hidden" style="color:#f00">此商品属于定制商品</span>
                    </td>
		    	</tr>
		    	<tr>
                    <td colspan="5">备注：<textarea class="ui-loxia-default ui-corner-all" rows="3" name="sta_memo" aria-disabled="false" id='sta_memo'></textarea></td>
		    	</tr>
		    	<tr id="isJordonByVmiCode" style="display:none">
			  <td colspan="5" align="center" style="background-color: red">商品金额大于1500,或者QS商品,或者是Jordon商品只能退成“待处理品”</td>
			   </tr>
			</table>
			<div class="buttonlist">
				<table>
					<tr>
					<td style="color: red;font-weight: bold;" id="remind">原始订单开具了纸质发票</td>
					</tr>
					<tr>
						<td>
							<button id="receiveAll" type="button" loxiaType="button" class="confirm">确认收货</button>
							<button type="button" loxiaType="button"  id="backto"><s:text name="button.toback"/></button>
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
	<div id="dialog_gift">
		<input type="hidden" id='staLineId' />
 	 	<table width="100%">
 	 		<tr style="height: 50px;">
 	 			<td class="label">商品货号</td><td id='dialog_sku' style="font-size: 28px;font-weight: bold;"></td>
 	 		</tr>
 	 		<tr style="height: 50px;"> 
 	 			<td class="label">保修卡条码:</td><td style="font-size: 28px;font-weight: bold;"><input loxiaType="input" id="caochWarrantyCard" trim="true" style="width:200px;" /><font style="font-size: 14px">回车保存</font></td>
 	 		</tr>
 	 		<tr> 
 	 			<td class="label">已扫保修卡数量:</td><td style="font-size: 20px;font-weight: bold;"><div id="warrantyCardNumber" style="float:left;">0</div>　<a id="showGiftList" href="javascript:;" style="font-size: 14px">查看</a></td>
 	 		</tr>
 	 		<tr> 
 	 			<td colspan="2"><div id="warrantyCardList"></div></td>
 	 		</tr>
 	 		<tr style="height: 50px;">
 	 			<td colspan="2" style="font-size: 18px;font-weight: bold;">
 	 				<button loxiaType="button" class="confirm" id="gift_success">完成</button><font id="dialog_gift_error" style="color: red;"></font>
 	 			</td>
 	 		</tr>
 	 	</table>
	</div>
	<div id="dialog_error_ok">
		<div id="error_text_ok" ></div>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="dialog_error_close">关闭</button>
		</div>   
	</div>
	<div id="dialog_nike_rfid">
		<span class="label">确认条码：</span>
		<input id="nikeRFIDOK" loxiaType="input" trim="true" style="width: 200px; height: 30px; line-height: 30px;"/>
	</div>
</body>


</html>
