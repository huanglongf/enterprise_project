<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inbound.cartonnum"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/sta-carton-manager.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
	<div id="divHead">
		<form id="queryForm" name="queryForm">
			<table width="700px">
				<tr>
					<td class="label" width="15%">店铺</td>
					<td width="35%">
						<table>
							<tr>
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
							</tr>
						</table>
					</td>
					<td width="15%" class="label">作业单类型</td>
					<td width="35%">
						<select loxiaType="select" id="typeList" name="sta.intStaType">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="11">采购入库</option>
							<option value="12">结算经销入库</option>
							<option value="14">代销入库 </option>
							<option value="15">赠品入库 </option>
							<option value="16">移库入库 </option>
							<option value="17">货返入库</option>
							<option value="32">库间移动</option>
							<option value="81">VMI移库入库</option>
							<option value="90">同公司调拨</option>
							<option value="91">不同公司调拨</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">作业单号</td>
					<td>
						<input type="text" loxiaType="input" trim="true" name="sta.code"></input>
					</td>
					<td class="label">相关单据号</td>
					<td>
						<input type="text" id="refSlipCode" loxiaType="input" trim="true" name="sta.refSlipCode"></input>
					</td>
				</tr>
				<tr>
					<td class="label">辅助相关单据号</td>
					<td>
						<input type="text" loxiaType="input" trim="true" name="sta.slipCode1"></input>
					</td>
					<td class="label">入库单状态</td>
					<td>
						<select loxiaType="select" id="typeList" name="sta.intStaStatus">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="1">已创建</option>
							<option value="2">库存占用（配货中）</option>
							<option value="3">已核对</option>
							<option value="4">已转出</option>
							<option value="5">部分转入</option>
							<option value="8">装箱中</option>
							<option value="10">已完成 </option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="10%" class="label">创建时间</td>
					<td width="15%"><input loxiaType="date" name="createTime" showTime="true"></input></td>
					<td width="10%" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="15%"><input loxiaType="date" name="endCreateTime" showTime="true"/></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search">查询</button>
			<button loxiaType="button" id="reset">重置</button>
		</div>

		<table id="tbl_sta_list"></table>
		<div id="pager"></div>
	</div>
	<iframe id="upload" name="upload" style="display:none;"></iframe>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
	
	<table id="filterTable" width="95%">
			 <tr>
				<td class="label" width="13%">纸箱数量导入：</td>
				<td width="20%">
					<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
						<input type="file" name="file" loxiaType="input" id="carton-num-file" style="width:200px"/>
					</form>
                </td>
			    <td><b>限制导入纸箱数据在1000条以下</b><a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=入库单纸箱数量导入.xls&inputPath=tplt_carton_num_import.xls">
			    <span class="ui-button-text">模版文件下载</span>
			    </a>
				<button loxiaType="button" class="confirm" id="carton-import">导入</button>
			   </tr>
		</table>
</body>

</html>