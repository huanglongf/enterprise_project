<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/delivery-info-export.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="showList">
		<form id="exportForm">	
			<span class="label">起止时间：</span>
			<input loxiaType="date" style="width: 150px" name="packcmd.startTime" id="startTime"/> <span class="label">至</span> <input loxiaType="date" style="width: 150px" name="packcmd.endTime" id="endTime"/>
			<p style="clear: both;"></p>
			
			<span class="label">选择物流公司：</span><select name="packcmd.deliveryId" id="deliveryid" loxiaType="select" style="width: 150px" name="">
				<option value="">所有物流公司(默认)</option>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<span class="label">选择仓库组织：</span><s:select name="packcmd.ouId" id="ouid" list="whList" loxiaType="select" listKey="id" listValue="name" 
			 headerKey="" headerValue="当前公司(默认)" style="width: 150px"></s:select>
		</form>	 
			 
			<div class="buttonlist">
				<button id="export" loxiaType="button" class="confirm" title="默认导出本公司下的所有仓库的所有物流信息">物流对账信息导出</button>
				<button id="reset" loxiaType="button" class="confirm">重设</button>
			</div>
		</div> 
<iframe id="deliveryInfoExport" class="hidden"></iframe>
</body>
</html>