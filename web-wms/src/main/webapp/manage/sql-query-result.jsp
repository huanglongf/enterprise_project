<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title><s:text name="title.login"/></title>
<link rel="stylesheet" type="text/css" href="<s:url value='/css/redmond/jquery-ui-1.8.7.custom.css' includeParams='none' encode='false'/>"/>
<link rel="stylesheet" type="text/css" href="<s:url value='/css/redmond/loxia-ui-1.2.custom.css' includeParams='none' encode='false'/>"/>
<link rel="stylesheet" type="text/css" href="<s:url value='/css/main.css' includeParams='none' encode='false'/>"/>
<style>
	.highlight {font-weight: bold;}
	th {border-bottom: 2px solid #ffcc00;}
	td {border-bottom: 1px dashed #00ccff;}
</style>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery-1.4.4.min.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery-ui-1.8.7.custom.min.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.bgiframe.min.js' includeParams='none' encode='false'/>"></script>

<script type="text/javascript" src="<s:url value='/scripts/main/jquery.loxiacore-1.2.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.loxiainput-1.2.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.loxia.locale-zh-CN.js' includeParams='none' encode='false'/>"></script>
	
<script type="text/javascript"><!--
var $j = jQuery.noConflict();

$j(document).ready(function (){	
	loxia.init({debug: true, region: 'zh-CN'});	
});

--></script>
<!--<script type="text/javascript" src="<s:url value='/scripts/login.js' includeParams='none' encode='false'/>"></script> -->
</head>
<body contextpath="<%=request.getContextPath() %>">
<h6>Result List[<s:property value="#request.value.size()"/>]</h6>
<dl>
<table class="ui-loxia-table" cellpadding="0" cellspacing="0">
<thead>
	<tr>
		<s:iterator value="#request.header">
		<th class="ui-state-default pad2"><s:property /></th>
		</s:iterator>
	</tr>
</thead>
<s:iterator value="#request.value" var="line">
<tr>
	<s:iterator value="#line">
	<td><s:property /></td>
	</s:iterator>
</tr>
</s:iterator>
</table>
</body>
</html>