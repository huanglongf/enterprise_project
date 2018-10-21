<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.dispatchList.title"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/sku-distribution-failure-export.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	<p><span class="label">配货失败订单批量导出缺货的SKU信息</span></p>
	
	<div id="btnlist" class="buttonlist" >
		<button loxiaType="button" class="confirm" id="btnSoInvoice">导出缺货商品信息</button>	
	</div> 
	
	<iframe id="frmSoInvoice" class="hidden"></iframe>
<!-- 	<iframe id="exportFrame" class="hidden" target="_blank"></iframe> -->
</body>
</html>