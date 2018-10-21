<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title>采购退货调整入库-确认收货</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/procurement-return-inbound-confirm.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
					<td class="label" style="color:blue">负向采购退货单号</td>
					<td><input loxiaType="input" trim="true" name="stacmd.slipCode2" /></td>				
<!-- 					<td class="label" width="20%"></td> -->
<!-- 					<td width="30%"> -->
<!-- 					</td>		 -->
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
                        <select loxiaType="select" id="add_status">
                        </select>
                    </td>
                    <td width="500px">
                    	<input id="addBarCode" type="hidden"  /> 
                        <button loxiaType="button" class="confirm" id="addSku">确认</button>
                        <span id="tip" class="label hidden" style="color:#f00">*店铺定制退货逻辑:该商品默认只能入残次品/良品不可销售</span>
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
							<button id="receiveAll" type="button" loxiaType="button" class="confirm">确认收货</button>
							<button type="button" loxiaType="button"  id="backto"><s:text name="button.toback"/></button>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div id="download"></div>
	</div>
	<iframe id="upload" name="upload" style="display:none;"></iframe>
	<div id="dialog_error_ok">
		<div id="error_text_ok" ></div>
<!-- 		<div class="buttonlist"> -->
<!-- 			<button type="button" loxiaType="button" class="confirm" id="dialog_error_close">关闭</button> -->
<!-- 		</div>    -->
	</div>
</body>
</html>