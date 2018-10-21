<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/nike-check-import.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
	<form id="queryForm" name="queryForm">
		<table width="700px">
			<tr>
				<td width="15%" class="label">调整单号</td>
				<td width="35%">
					<input type="text" loxiaType="input" trim="true" name="check.checkCode"></input>
				</td>
				<td class="label" width="15%">UPC</td>
				<td width="35%">
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
					<select loxiaType="select" id="typeList" name="check.manualType">
						<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
						<option value="11">RSO调整</option>
						<option value="13">J0调整</option>
						<option value="15">财务调整</option>
						<option value="99">其他调整</option>
						
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
	<table id="tbl_nike_check"></table>
	<div id="pager"></div>
	<div class="buttonlist">
		<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
		<table>
			<tr>
				<td class="label">
					类型:
				</td>
				<td>
					<select loxiaType="select" id="typeList" id='check_manualType' name="check.manualType">
						<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
						<option value="11">RSO调整</option>
						<option value="13">J0调整</option>
						<option value="15">财务调整</option>
						<option value="99">其他调整</option>
					</select>
				</td>
				<td class="label">
					确认收货导入:
				</td>
				<td>
					<input type="file" loxiaType="input" id="file" name="file" style="width: 200px;"/>
				</td>
			</tr><tr>
				<td class="label">
					备注:
				</td>
				<td colspan="3">
					<textarea rows="4" cols="100" id='check_remark' loxiaType="input" name="check.remark" ></textarea>
				</td>
			</tr><tr>
				<td colspan="4">
					<button loxiaType="button" class="confirm" id="import">导入</button>
					<a href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=NIKE_Adjustment.xls&inputPath=tplt_import_nike_check.xls" loxiaType="button"><s:text name="label.warehouse.inpurchase.export"></s:text></a>
				</td>
			</tr>
		</table>
		</form>
	</div>
	<iframe id="upload" name="upload" style="display:none;"></iframe>
</body>
</html>