<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/locationVolume.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.capacity"/></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<!-- 这里是页面内容区 -->
	<div id="district-container" dirty="false">
		<ul>
			<!-- -->
		</ul>			
		<!-- -->
	</div>		
	<table width="85%">
		<tr>
			<td class="label" width="50%"><s:text name="label.warehouse.capacity.setcapacity"/></td>
			<td width="20%"><input loxiaType="number" value="" min="0" id="batch-cap"/></td>
			<td><button type="button" loxiaType="button" id="btn-batch-cap"><s:text name="button.batchset"/></button></td>
		</tr>
		<tr>
			<td class="label" width="50%"><s:text name="label.warehouse.capacity.setcaparatio"/></td>
			<td width="20%"><input loxiaType="number" value="" min="0" max="100" id="batch-cappa"/></td>
			<td><button type="button" loxiaType="button" id="btn-batch-cappa"><s:text name="button.batchset"/></button></td>
		</tr>
	</table>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="btn-save"><s:text name="button.save"/></button>
	</div>
</body>
</html>