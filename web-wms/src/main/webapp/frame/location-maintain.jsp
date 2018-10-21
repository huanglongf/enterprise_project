<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.location"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/location-maintain.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
.ui-loxia-table tr.error{background-color: #FFCC00;}
</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<!-- 这里是页面内容区 -->
	<input type="hidden" name="targetTab" value="<s:property value='district.code'/>" id="targetTab" />
	<h3><s:text name="label.warehouse.location.header"/></h3>
	<form name="addForm" id="addForm">
		<table width="40%">			
			<tr>
				<td width="20%" class="label"><s:text name="label.warehouse.location.code"/></td>
				<td width="30%"><input name="district.code" loxiaType="input" mandatory="true" max="50" id="code" maxLength="50"/></td>
				<td width="20%" class="label"><s:text name="label.warehouse.location.name"/></td>
				<td width="30%"><input name="district.name" loxiaType="input" mandatory="true" max="100" id="name" maxLength="100"/></td>
			</tr>	
			<tr>
				<td width="20%" class="label"><s:text name="label.comment"/></td>
				<td colspan="3"><textarea loxiaType="input" name="district.memo" max="255" id="comment" maxLength="255"></textarea></td>		
			</tr>	
		</table>
	</form>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="btn-district-create"><s:text name="button.add"/></button>
	</div>
	<div id="district-container" class="custom1" dirty="false">
	   <div id="download"></div>
        <iframe id="upload" name="upload" style="display:none;"></iframe>
		<ul></ul>			
		<!-- -->
	</div>		
	<div class="buttonlist">
		<table> 
		    <tr>
		        <td width="20%"><s:text name="label.warehouse.inpurchase.selectFile"/></td>
		        <td width="30%">
		            <form method="POST" enctype="multipart/form-data" id="importPopUpForm" name="importPopUpForm" target="upload">
		                <input type="file" loxiaType="input" id="importPopUpFile" name="importPopUpFile"/>
		            </form>
		        </td>
		        <td>
		            <button type="button" loxiaType="button" id="importPopUp">导入库位与弹出口的关联</button>
		        </td>
		        <td>
		        	<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=库位与弹出口绑定.xls&inputPath=tpl_location_popup_import.xls"><s:text name="download.excel.template"></s:text></a>
		        </td>
			</tr>
		</table>
	</div>
</body>
</html>