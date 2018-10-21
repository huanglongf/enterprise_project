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
	.pad2{padding: 2px;}
	.highlight {font-weight: bold;}
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
	
	$j("#btn-rtn").click(function(){
		window.location.href = $j("body").attr("contextpath") + "/manage/dslist.do";
	});
});
--></script>
<!--<script type="text/javascript" src="<s:url value='/scripts/login.js' includeParams='none' encode='false'/>"></script> -->
</head>
<body contextpath="<%=request.getContextPath() %>">
<h3>Current Datasource</h3>
<div style="width:95%">
<div style="width:580px; padding: 15px 5px 5px 5px; float: left;">
<table>
<tr>
<td class="ui-state-default pad2">Key:</td><td><s:property value="#request.ds.key"/></td>
<td class="ui-state-default pad2">DriverClass:</td><td><s:property value="#request.ds.driverClassName"/></td>
</tr>
<tr>
<td class="ui-state-default pad2">UserName:</td><td><s:property value="#request.ds.username"/></td>
<td class="ui-state-default pad2">Url:</td><td><s:property value="#request.ds.url"/></td>
</tr>
</table>
<br/>
<s:form target="resultframe" action="hqlquery" namespace="/manage">
<s:hidden name="dsId" />
<label for="pageSize">读取记录数:</label><s:textfield name="pageSize" value="50" cssClass="ui-loxia-default ui-loxia-active" style="width: 100px;"/>
<br/>
<label for="pageSize">查询语句:</label><textarea name="query" class="ui-loxia-default ui-loxia-active" style="width: 400px;" rows="8"></textarea>
<br/>
<button>查询</button>
<button id="btn-rtn" type="button">返回</button>
</s:form>
<iframe name="resultframe" width="560" height="500" style="clear:both; border: 1px solid #777;"></iframe>
</div>
<div style="width: 360px; border: 1px solid #777; padding: 5px; margin-left: 600px;">
<dl>
<s:iterator value="#request.entities">
<dt><s:property /></dt>
</s:iterator>
</dl>
</div>
<br/>
</div>
</body>
</html>