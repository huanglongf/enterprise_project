<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/meta.jsp"%>
<title><s:text name="betweenlibary.move.query"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/vmi-toshop-return-converse.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
 <s:text id="pselect" name="label.please.select"/>
<input type="hidden" id="espritChannelFlag" value="false"/>
<input type="hidden" id="espritTransferFlag" value="false"/>	
	
	<table width="90%">
		<tr>
			<td class="label" width="15%">
				CONVERSE店铺:
			</td>
			<td width="20%" >
				<select id="owner" name="sta.owner" loxiaType="select" style="width: 200px;">
					<option value=""></option>
				</select>
			</td>
			<td class="label" width="15%">
				退仓原因：
			</td>
			<td>
				<select id="returnReason" name="sta.intType" loxiaType="select" style="width: 200px;">
					<option value="" >--请选择--</option>
					<option value="01" >残次品</option>
					<option value="02">停止销售</option>
					<option value="03">其他原因</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="label" width="15%">退货原因编码:</td>
			<td><input loxiaType="input" value="" id="returnCode" name="returnCode" style="width: 150px;"/></td>
			<td>
			</td>
			<td>
			</td>
		</tr>
		<tr>
			<td class="label" width="15%">选择文件:</td>
			<td>
				<div class="buttonlist">
					<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
						<input type="file" name="file" loxiaType="input" id="file" style="width:250px"/>
					</form>
				</div>
					
			</td>
			<td><a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="CONVERSE转店退仓"></s:text>.xls&inputPath=tplt_converse_vmi_create_returnsta.xls"><s:text name="download.excel.template"></s:text></a></td>
			<td></td>
		</tr>
		<tr>
			<td class="label"><button loxiaType="button" class="confirm" id="import"><s:text name="button.import"></s:text></button></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>
	<br/> 
	<iframe id="upload" name="upload" class="hidden"></iframe>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>