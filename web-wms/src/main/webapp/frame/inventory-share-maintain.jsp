<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inventory.share"/></title>
</head>
<script type="text/javascript" src="../scripts/frame/inventory-share-maintian.js"></script>
<body contextpath="<%=request.getContextPath() %>">	
 <!-- 这里是页面内容区 -->
 	<div id="f1" refsize="<s:property value='flag' />">
 		<span class="label"><s:text name="label.inventory.warehouseInfo"/></span>
 		<table id="tb-operationunitinfo"></table>
 	</div>
 	
	<div id="f2">
		<table id="tbl-warehouselist"></table>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="btn-share"><s:text name="button.inventory.share"/></button>
			<button type="button" loxiaType="button" id="btn-unshare"><s:text name="button.inventory.unshare"/></button>
		</div>
	</div>
</body>
</html>
