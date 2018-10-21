<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/predefined-out-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
					<td width="10%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text></td>
					<td width="15%"><input loxiaType="date" name="sta.createTime1" showTime="true"></input></td>
					<td width="10%" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="15%"><input loxiaType="date" name="sta.endCreateTime1" showTime="true"/></td>
				</tr>
				<tr>
					<td class="label">店铺</td>
					<td>
					<div style="float: left">
							<select id="companyshop" name="sta.owner" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</div>
						<div style="float: left">
							<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
						</div>
					</td>
					<td width="10%" class="label">作业类型 </td>
					<td width="15%">
						<select id="type" name="sta.intStaType" loxiaType="select">
							<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							<option value="32">库间移动</option>
							<option value="61">采购出库</option>
							<option value="62">结算经销出库</option>
							<option value="63">包材领用</option>
							<option value="64">代销 出库</option>
							<option value="90">同公司调拨</option>
							<option value="91">不同公司调拨</option>
							<option value="201">福利领用</option>
							<option value="202">固定资产领用 </option>
							<option value="204">报废出库</option>
							<option value="205">促销领用</option>
							<option value="206">低值易耗品</option>
							<option value="210">样品出库</option>
							<option value="212">商品置换出库</option>
							<option value="214">送修出库</option>
							<option value="216">串号拆分出库</option>
							<option value="218">串号组合出库</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="10%" class="label">作业单号 </td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.code"/></td>
					<td width="10%" class="label">前置单号 </td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.slipCode"/></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text> </button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
		</div>
		<table id="tbl-staList"></table>
		<div id="pager"></div>
	</div>
	<div id="div-sta-detail" class="hidden">
		<table>
			<tr style="height: 25px">
				<td width="10%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
				<td width="15%"><span id="createTime"></span></td>
				<td width="10%" class="label"><s:text name="label.warehouse.sta.finish"></s:text> </td>
				<td width="15%"><span id="finishTime"></span></td>
				<td width="10%" class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
				<td width="15%"><span id="slipCode"></span></td>
			</tr>
			<tr style="height: 25px">
				<td width="10%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
				<td width="15%"><span id="code"></span></td>
				<td width="10%" class="label"><s:text name="label.warehouse.pl.status"></s:text> </td>
				<td width="15%"><span id="status"></span></td>
				<td width="10%" class="label"><s:text name="label.warehouse.pl.sta.trantype"></s:text> </td>
				<td width="15%"><span id="sta_type"></span></td>
			</tr>
			<tr style="height: 25px">
				<td width="10%" class="label"><s:text name="label.warehouse.sta.shop"></s:text> </td>
				<td width="15%"><span id="shopId"></span></td>
				<td width="10%" class="label">创建人 </td>
				<td width="15%"><span id="operator"></span></td>
				<td colspan="2"></td>
			</tr>
			<tr style="height: 25px">
				<td width="10%" class="label">收货人 </td>
				<td width="15%"><span id="receiver"></span></td>
				<td width="10%" class="label">联系电话 </td>
				<td width="15%"><span id="telephone"></span></td>
				<td width="10%" class="label">手机 </td>
				<td width="15%"><span id="mobile"></span></td>
			</tr>
			<tr style="height: 25px">
				<td width="10%" class="label">收货地址 </td>
				<td colspan="5"><span id="address"></span></td>
			</tr>
			<tr style="height: 25px">
				<td width="10%" class="label">备注 </td>
				<td colspan="5"><span id="sta_memo"></span></td>
			</tr>
		</table>
		<div>
			<table id="tbl-sta-line"></table>
			<div id="pagerDetail"></div>
		</div>
		<div class="buttonlist">
			<table width="100%">
				<tr id="tr_I" >
					<td width="30%" >
						<!-- <button loxiaType="button" class="confirm" id="occupation">系统自动占用</button> -->
					</td>
					<td >
						<form id="importForm" method="post" enctype="multipart/form-data" name="importForm" target="upload" >
							<input type="file" accept="application/msexcel" name="file" id="tnFile" style="width:150px" loxiaType="input"/>
							<button loxiaType="button" class="confirm" id="input">导入占用</button>
								<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="预定义出库"></s:text>.xls&inputPath=tplt_import_warehouse_others_operate.xls"><s:text name="download.excel.template"></s:text></a>
						</form>
					</td>
				</tr>
				<tr id="tr_II" style="height: 40px">
					<td  colspan="2">
						<div id="divSnImport" class="hidden">
							<div class="divFloat">
								<span class="label">SN号导入</span>
							</div>
							<div class="divFloat">
								<form method="post" enctype="multipart/form-data" id="importFormSN" name="importFormSN" target="upload">	
									<input type="hidden" name="staId" id="imp_staId" />
									<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
								</form>
							</div>
							<div class="divFloat">
								<button loxiaType="button" class="confirm" id="import"><s:text name="button.import"></s:text></button>
							</div>
							<div class="divFloat">
								<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_warehouse_sn_import"></s:text>.xls&inputPath=tplt_import_warehouse_sn_import.xls"><s:text name="download.excel.template"></s:text></a>
							</div>
							<div class="clear"></div>
							<br />
						</div>
						<div id="packaging_materials" class="hidden">
							<form id="importPMForm" method="post" enctype="multipart/form-data" name="importPMForm" target="upload" >
								<span class="label">包材导入</span>
								<input type="file" accept="application/msexcel" name="file" id="pmFile" />
								<button loxiaType="button" class="confirm" id="inputPM">包材导入</button>
								<a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=包材条码.xls&inputPath=tplt_import_packaging_materials.xls" role="button">
									<span class="ui-button-text">模版文件下载</span>
								</a>
							</form>
						</div>
						<br />
							
						<button loxiaType="button" class="confirm" id="execution">执行</button>
						<button loxiaType="button" id="outputInfo" class="confirm">导出/打印占用情况</button>
						<button loxiaType="button" class="confirm" id="printSendInfo"><s:text name="button.print.send.info"/></button>
						<button id="packing" loxiaType="button" class="confirm">装箱</button>
						<button id="cancelPacking" loxiaType="button" class="confirm">取消装箱</button>
						<br />
						<span class="label">备注：</span>
						<br />
						<textarea id="memo" class="ui-loxia-default ui-corner-all" name="sta.memo" aria-disabled="false"></textarea>
					</td>
				</tr>
				<tr id="tr_III" style="height: 40px">
					
					<td  colspan="2">
						<button  loxiaType="button" class="confirm" id="canceled" >取消</button>
						<button loxiaType="button" id="break"><s:text name="button.back"></s:text></button>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<iframe id="upload" name="upload" class="hidden"></iframe>
	<div id="download"></div>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>