<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/wh-box-inbound.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
								<option value="0">--请选择--</option>
								<option value="1">已创建</option>
								<option value="5">部分转入</option>
								<option value="25">冻结</option>
								<option value="10">已完成</option>
							</select>
						</div>
					</td>
		               <td class="label" width="20%"><s:text name="作业单类型"/></td>
					<td width="30%">
						<div style="width:150px;">
							<select id="statypes" name="types" loxiaType="select">
								<option value="0"><s:text name="label.wahrhouse.sta.select"></s:text> </option>
								<option value="11">采购入库</option>
								<option value="81">VMI移库入库</option>
								<option value="12">结算经销入库</option>
								<option value="14">代销入库</option>
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
		<br />
		<br />
		<div>
			<div id='inputDiv'>
				<form method="post" enctype="multipart/form-data" id="importFormC" name="importFormC" target="upload">
					<s:text name="label.warehouse.inpurchase.selectFile"/><input type="file" loxiaType="input" id="file" name="file" style="width: 200px;"/>
					<button type="button" loxiaType="button" id="import" class="confirm"><s:text name="label.warehouse.inpurchase.import"/></button>
						<a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=导入模板生成箱作业单.xls&inputPath=tplt_wh_box_inbound.xls">
						<span class="ui-button-text">按箱收货模版文件下载</span>
						</a>
				</form>
				<br />
			</div>
			
		</div>
		<table id="tbl-cartonList"></table>
		<br />
		<br />
		<table id="tbl-orderNumber"></table>
		<br />
		<br />
		<button id="searchCartonSta" class="hidden" loxiaType="button" ><s:text name="button.search"/></button>
		<button id="searchRootStaLine" class="hidden" loxiaType="button"><s:text name="button.search"/></button>
		<button id="toBackDetial" loxiaType="button"><s:text name="button.back"/></button>
	</div>
	
	<div id="div-cartonSta-detail" class="hidden">
		<input type="hidden" name="cartonStaId" id="cartonStaId"/>
		<div >
			<table>
				<tr>
					<td class="label"><s:text name="label.warehouse.inpurchase.createTime"/>:</td>
					<td id="cartonCreateTime"></td>
					<td class="label"><s:text name="label.warehouse.pl.sta"/>:</td>
			        <td id="cartonStaCode"></td>
					<td class="label">前置单据：</td>
					<td id="cartonPo"></td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
				   <td class="label"><s:text name="label.warehouse.inpurchase.owner"/>:</td>
			        <td id="cartonOwner"></td>
					<td class="label"><s:text name="label.warehouse.inpurchase.status"/>:</td>
					<td id="cartonStatus"></td>
					<td class="label"><s:text name="作业单状态"/>:</td>
					<td id="cartonLeft"></td>
					<td colspan="2">&nbsp;</td>
				</tr>
			</table>
		</div>
		
		<table id="tbl-cartonDetailList"></table>
		<div class="buttonlist">
			<div id='inputDiv1'>
				<form method="post" enctype="multipart/form-data" id="importFormD" name="importFormD" target="upload1">
					<!-- <span class="label">备注：</span>
					<br />
					<textarea id="importRmk" class="ui-loxia-default ui-corner-all" rows="3" name="remark" aria-disabled="false"></textarea> -->
					<br />
					<s:text name="label.warehouse.inpurchase.selectFile"/><input type="file" loxiaType="input" id="staFile" name="staFile" style="width: 200px;"/>
					<button type="button" loxiaType="button" id="import1" class="confirm"><s:text name="label.warehouse.inpurchase.import"/></button>
						<a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=导入模板批量收货.xls&inputPath=tplt_batch_receiving.xls">
					<span class="ui-button-text">模版文件下载</span>
				</a>
				</form>
				<br />
			</div>
			<button id="toBackCartonStaDetial" loxiaType="button"><s:text name="button.back"/></button>
		</div>
	</div>
	
	<iframe id="upload" name="upload" style="display:none;"></iframe>
	<iframe id="upload1" name="upload1" style="display:none;"></iframe>
</body>
</html>