<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/nike-vmi-inbound.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
	<div class='hidden'>
		<form id="queryForm" name="queryForm">
			<table width="700px">
				<tr>
					<td width="15%" class="label">前置单据：</td>
					<td width="35%">
						<input type="text" loxiaType="input" trim="true" name="check.checkCode"></input>
					</td>
					<td class="label">作业单号：</td>
					<td>
						<input type="text" loxiaType="input" trim="true" name="check.upc"></input>
					</td>
				</tr>
				<tr>
					<td class="label">调整单状态</td>
					<td>
						<select loxiaType="select" id="typeList" name="check.status">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="1">新建状态</option>
							<option value="2">准备写文件状态</option>
							<option value="10">完成状态</option>
						</select>
					</td>
					<td class="label">调整单类型</td>
					<td>
						<select loxiaType="select" id="typeList" name="check.type">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="1">系统调整</option>
							<option value="2">人为调整</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="10%" class="label">创建时间</td>
					<td width="15%"><input loxiaType="date" name="check.createDate" showTime="true"></input></td>
					<td width="10%" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="15%"><input loxiaType="date" name="check.endCreateDate" showTime="true"/></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search">查询</button>
			<button loxiaType="button" id="reset">重置</button>
		</div>
		<table id="tbl_nike_vmi_inbound"></table>
		<div id="pager"></div>
	</div>
	<div class="buttonlist">
		<table>
			<tr>
					<td class="label" >选择店铺:</td>
					<td class="label" colspan="2"><select id="owner" name="owner" loxiaType="select">
							<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
						</select>
					</td>
			</tr>
			<tr><td colspan="3"></td></tr>
			<tr>
				<td class="label">NIKE VMI 入库单创建导入:</td>
				<td><form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
						<input type="file" loxiaType="input" id="file" name="file" style="width: 200px;"/>
					</form>
				</td>
				<td>
					<button loxiaType="button" class="confirm" id="import">导入</button>
				</td>
				<td>
					<a href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=NIKE_VMI_INBOUND.xls&inputPath=tplt_import_nike_vmi_inbound.xls" loxiaType="button"><s:text name="label.warehouse.inpurchase.export"></s:text></a>
				</td>
			</tr>
		</table >
		<br />
		<div style="width: 100%">
			<div class="label" style="text-align: center;">导入结果提示</div>
			<div style="float:left;width: 50%">
				<div class="label" style="text-align: left;">成功数据</div>
				<div id='successMag'></div>
			</div>
			<div style="float:left;width: 50%">
				<div class="label" style="text-align: left;">错误数据(同一批数据里面有一行出错会导致整批数据失败!)</div>
				<div id='errorMsg'></div>
			</div>
		</div>
	</div>
	<iframe id="upload" name="upload" style="display:none;"></iframe>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>