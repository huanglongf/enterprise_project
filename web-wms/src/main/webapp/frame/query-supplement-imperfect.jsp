<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/query-supplement-imperfect.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
					<td class="label" style="color:blue">相关单据号</td>
					<td><input loxiaType="input" trim="true" name="sta.refSlipCode" /></td>
					<td class="label" width="20%">条形码</td>
					<td width="30%"><input loxiaType="input" trim="true" name="sku.barCode" /></td>
				</tr>
				<tr>
					<td class="label" width="20%">货号</td>
					<td width="30%"><input loxiaType="input" trim="true" name="sku.supplierCode" /></td>
					<td class="label" width="20%" style="color:blue">残次条码</td>
					<td width="30%"><input loxiaType="input" trim="true" name="skuImperfect.defectCode" /></td>
				</tr>
				<tr>
					<td class="label" width="20%" style="color:blue">收货人</td>
					<td width="30%"><input loxiaType="input" trim="true" name="sta.staDeliveryInfo.receiver" /></td>
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
				<!-- <tr>
					<td class="label">残次类型</td>
					<td>
						<select id="imperfect" name="skuImperfect.defectType" loxiaType="select">
						</select>
						</td>
					<td class="label">残次原因</td>
					<td>
					<select id="imperfectLine" name="skuImperfect.defectWhy" loxiaType="select">
					</select>
					</td>
				</tr> -->
				<tr>
				    <td class="label">作业单类型</td>
					<td>
						<select id="imperfect" name="sta.shipmentCode" loxiaType="select">
						    <option value="">请选择</option>
							<option value="11">采购入库</option>
							<option value="12">结算经销入库</option>
							<option value="13">其他入库</option>
							<option value="14">代销入库</option>
							<option value="15">赠品入库 </option>
							<option value="16">移库入库 </option>
                            <option value="17">货返入库 </option>
   						    <option value="41">退货入库</option>
							<option value="81">VMI移库入库</option>
							<option value="85">VMI库存调整入库 </option>
                            <option value="211">样品领用入库 </option>
							<option value="213">商品置换入库 </option>
                            <option value="215">送修入库 </option>
                            <option value="217">串号拆分入库 </option>
							<option value="219">串号组合入库 </option>
						</select>
					</td>
					<td class="label">作业单号</td>
					<td>
					    <input loxiaType="input" trim="true" name="sta.code" />
					</td>
				</tr>
				<tr>
				    <td class="label">收货时间起</td>
					<td>
						<input loxiaType="date" name="createDate" showTime="true"></input>
					</td>
					<td class="label">收货时间至</td>
					<td>
					    <input loxiaType="date" name="endDate" showTime="true"></input>
					</td>
				</tr>
				<tr>
				    <td class="label">容器号</td>
					<td>
						<input loxiaType="input" trim="true" name="sta.memo" />
					</td>
					<td></td>
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
			<div>
			<table>
				<tr>
					<td>
						<button loxiaType="button" class="confirm" id="print">批量打印</button>
					</td>
				</tr>
			</table>
			<br/>
			<button class="hidden" loxiaType="button" id="showPdaLog">PDA操作日志</button>
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
                    <td width="120px" >
                        <select loxiaType="select" id="add_status" name="add_status">
                        </select>
                    </td>
                    <td width="500px">
                    <input class="hidden" id="supplierCode" name="supplierCode"/>
                    <div class="hidden" id="add_imperfect">
                    残次品类型： <select loxiaType="select"  id="imperfect" style="width:100px" name="imperfect">
                    <option selected="selected">其他</option></select>
                      残次品原因： <select loxiaType="select"  id="imperfectLine" style="width:100px" name="imperfectLine">
                       <option selected="selected">其他</option> </select>
                       </div>
                    	<input id="addBarCode" type="hidden"  /> 
                    	<input id="owner" type="hidden"  /> 
                    </td>
                    <td>
                    </td>
		    	</tr>
		    	<tr>
                    <td colspan="5">备注：<textarea class="ui-loxia-default ui-corner-all" rows="3" name="sta_memo" aria-disabled="false" id='sta_memo'></textarea></td>
		    	</tr>
			</table>
			<div class="buttonlist">
				<table>
					<tr>
						<td>
						    <button id="addSku" type="button" loxiaType="button" class="confirm">打印标签并确认</button>
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
	</div>
	<div id="dialog_newArea" class="hidden">
			<table>
				<tr>
					<td width="85px"><label><b>残次类型：</b></label></td>
					<td><input type="text" name="id" loxiaType="input" id="id" class="hidden"/>
					<select loxiaType="select"  id="imperfectType" style="width:100px" name="imperfectType">
					<option values="其他">其他</option>
					</select></td>	
					
				</tr>
				<tr>
					<td><label><b>残次原因：</b></label></td>
					<td><select loxiaType="select"  id="imperfectWhy" style="width:100px" name="imperfectWhy">
					<option values="其他">其他</option>
					</select></td>
				</tr>
			 	<tr>
					<td > <div id="factory" name="factory" class="hidden"><b>工厂代码：</b></div></td>
					<td> <div id="factory1" name="factory1" class="hidden">
					<input type="text" name="factoryCode" id="factoryCode" loxiaType="input" /></div></td>
		    	
				</tr>
				<tr>
				<td>
				<div id="poTime" name="poTime" class="hidden"><b>下单日期：</b></div></td>
				<td><!-- <div id="poTime1" name="poTime1" class="hidden"> -->
				<input type="text" trim="true" id="poId" name="poId"/></div></td>
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
		<div class="buttonlist">
		</div>   
	</div>
</body>


</html>