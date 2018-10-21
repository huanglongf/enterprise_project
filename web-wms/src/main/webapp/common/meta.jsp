<%@taglib prefix="s" uri="/struts-tags"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<%@include file="/common/common-include.jsp"%>

<link rel="stylesheet" type="text/css" href="<s:url value='/css/flexselect.css' includeParams='none' encode='false'/>"/>
<link rel="stylesheet" type="text/css" href="<s:url value='/css/jumbo/jquery-ui-1.8.14.custom.css' includeParams='none' encode='false'/>"/>
<link rel="stylesheet" type="text/css" href="<s:url value='/css/jumbo/loxia-ui-1.2.custom.css' includeParams='none' encode='false'/>"/>
<link rel="stylesheet" type="text/css" href="<s:url value='/css/jumbo/jquery.autocomplete.css' includeParams='none' encode='false'/>"/>
<link rel="stylesheet" type="text/css" href="<s:url value='/css/ui.jqgrid.css' includeParams='none' encode='false'/>"/> 
<link rel="stylesheet" type="text/css" href="<s:url value='/css/icon.css' includeParams='none' encode='false'/>">
<link rel="stylesheet" type="text/css" href="<s:url value='/css/tree.css' includeParams='none' encode='false'/>">
<link rel="stylesheet" type="text/css" href="<s:url value='/css/main.css' includeParams='none' encode='false'/>"/> 
<link rel="stylesheet" type="text/css" href="<s:url value='/css/multiple-select.css' includeParams='none' encode='false'/>"/> 



<script type="text/javascript" src="<s:url value='/scripts/main/jquery-1.4.4.min.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery-ui-1.8.7.custom.min.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.autocomplete.min.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.bgiframe.min.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

<script type="text/javascript" src="<s:url value='/scripts/main/grid.locale-cn.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.searchFilter.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.jqGrid.min.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/tool.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

<script type="text/javascript" src="<s:url value='/scripts/main/liquidmetal.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.flexselect.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>



<script type="text/javascript" src="<s:url value='/scripts/main/jquery.loxiacore-1.2.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.loxiainput-1.2.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.loxiaselect-1.2.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.loxiatable-1.2.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery-ui-timepicker-addon.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.loxia.locale-zh-CN.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.ui.datepicker-zh-CN.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.tree.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script>
<!--
var $j = jQuery.noConflict();
$j(document).ready(function (){
	loxia.init({debug: true, region: 'zh-CN'});
});
-->
</script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/multiple-select.js' includeParams='none' encode='false'/>"></script>

