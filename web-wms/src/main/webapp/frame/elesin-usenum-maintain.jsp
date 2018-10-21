
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/meta.jsp"%>
		<title>电子面单可用运单号维护</title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/elesin-usenum-maintain.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
		<style type="text/css">
			.tips{
				width:100%; height: 50%; text-align:center; color:red; font-size: 40px; font-weight: bold;
			}
		</style>
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<form id="form_query">
			<table  id="queryTable">
					<tr>
						<td align="left" width="75px"><b>物流商简称:</b></td>
						<td width="120px"><select name="whTransProvideNoCommand.lpcode" id="lpcode1" loxiaType="select" style="width: 200px">
							<option value="">请选择</option>
							<option value="JD">JD</option>
							<option value="ZTP">ZTP</option>
							<option value="ZTO">ZTO</option>
							<option value="EMS">EMS</option>
							<option value="STO">STO</option>
							<option value="SF">SF</option>
							<option value="WX">WX</option>
						</select></td>
					</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text> </button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
		</div>
		<br>
		<table id="tbl-provide"></table>
		<br>
		<table width ="550px">
			<tr>
				<td align="left"><b>选择物流商:</b></td>
				<td align="center">
					<select id="lpcode" name="lpcode" loxiaType="select"  width="150px">
						<option value="STO">STO</option>
						<option value="ZTO">ZTO</option>
						<option value="SF">SF</option>
						<option value="WX">WX</option>
					</select>
				</td>
				<td></td>
			</tr>
			<tr class="hidden" id ="transSfCheckbox">
			<td align="left"><b>是否SF子运单：</b></td>
			<td><input type="checkbox" id="checkboxSf"></input></td></tr>
			<tr class="hidden" id ="jcustidSf">
			<td align="left" ><b>SF子单月结账号：</b></td>
			<td><input type="text" id="jcustid"></input></td>
			</tr>
			<tr>
				<td align="left"><b>选择文件:</b></td>
				<td  align="center">
					<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
						<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
					</form>
				</td>
				<td> 
					<button loxiaType="button" class="confirm" id="import">导入</button>
		            <a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_tracking_no"></s:text>.xls&inputPath=tplt_import_tracking_no.xls">模板文件下载</a>
		        </td>
			</tr>
		</table>
		<iframe id="upload" name="upload" class="hidden"></iframe>
	</body>
</html>