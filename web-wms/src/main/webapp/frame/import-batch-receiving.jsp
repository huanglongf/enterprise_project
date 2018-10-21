<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/import-batch-receiving.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
		<div style="width:100%;overflow:hidden">
				<form method="post" enctype="multipart/form-data" id="importFormC" name="importForm" target="upload" style="float:right;">
					<input type="file" loxiaType="input" id="staFile1" name="staFile" style="width: 200px;"/> <button type="button" loxiaType="button" class="confirm" id="import1" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;模板批量收货</button>
					<a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=导入模板批量收货.xls&inputPath=tplt_batch_receiving.xls" role="button">
						<span class="ui-button-text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;模版文件下载 &nbsp;&nbsp;&nbsp;</span>
					</a>
				</form>
		</div>
		<div style="width:100%;overflow:hidden">
				<form method="post" enctype="multipart/form-data" id="importFormD" name="importFormD" target="upload" style="float:right;">
					<input type="file" loxiaType="input" id="staFile2" name="staFile" style="width: 200px;"/> <button type="button" loxiaType="button" class="confirm" id="import2" >保质期商品批量收货</button>
					<a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=保质期商品导入模板批量收货.xls&inputPath=tplt_shelf_life_batch_receiving.xls" role="button">
						<span class="ui-button-text">保质期商品模板下载</span>
					</a>
				</form>
		</div>
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
		       <tr>
		             <td class="label" width="20%"><s:text name="作业单状态"/></td>
					<td width="30%">
						<div style="width:150px;">
							<select id="stastatus" name="status" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</div>
					</td>
		               <td class="label" width="20%"><s:text name="作业单类型"/></td>
					<td width="30%">
						<div style="width:150px;">
							<select id="statypes" name="types" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
								<option value="11">采购入库</option>
								<option value="81">VMI移库入库</option>
							</select>
						</div>
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
		<div >
			<table>
				<tr>
					<td class="label"><s:text name="label.warehouse.inpurchase.createTime"/>:</td>
					<td id="createTime"></td>
					<td class="label"><s:text name="label.warehouse.pl.sta"/>:</td>
			        <td id="staCode"></td>
					<td class="label">前置单据：</td>
					<td id="po"></td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
				   <td class="label"><s:text name="label.warehouse.inpurchase.owner"/>:</td>
			        <td id="owner"></td>
					<td class="label"><s:text name="label.warehouse.inpurchase.status"/>:</td>
					<td id="status"></td>
					<td class="label"><s:text name="作业单状态"/>:</td>
					<td id="left"></td>
					<td colspan="2">&nbsp;</td>
				</tr>
			</table>
		</div>
		<table id="tbl-orderNumber"></table>
		<div class="buttonlist">
			<div id='inputDiv'>
				<form method="post" enctype="multipart/form-data" id="importFormB" name="importForm" target="upload">
					<span class="label">备注：</span>
					<br />
					<textarea id="importRmk" class="ui-loxia-default ui-corner-all" rows="3" name="remark" aria-disabled="false"></textarea>
					<br />
					<s:text name="label.warehouse.inpurchase.selectFile"/><input type="file" loxiaType="input" id="staFile" name="staFile" style="width: 200px;"/>
					<button type="button" loxiaType="button" id="import" class="confirm"><s:text name="label.warehouse.inpurchase.import"/></button>
					<a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=导入模板批量收货.xls&inputPath=tplt_batch_receiving.xls">
					 <span class="ui-button-text">模&nbsp;&nbsp;版&nbsp;&nbsp;文&nbsp;&nbsp;件&nbsp;&nbsp;下&nbsp;&nbsp;载</span>
					</a><br/>
					<s:text name="label.warehouse.inpurchase.selectShelfLifeFile"/><input type="file" loxiaType="input" id="staFile3" name="staFile" style="width: 200px;"/>
					<button type="button" loxiaType="button" id="import3" class="confirm"><s:text name="label.warehouse.inpurchase.importShelfLife"/></button>
					<a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=保质期商品导入模板批量收货.xls&inputPath=tplt_shelf_life_batch_receiving.xls">
					 <span class="ui-button-text">保质期商品模板下载</span>
					</a>
				</form>
				<br />
			</div>
			<button id="toBackDetial" loxiaType="button"><s:text name="button.back"/></button>
		</div>
	</div>
	<iframe id="upload" name="upload" style="display:none;"></iframe>
</body>


</html>