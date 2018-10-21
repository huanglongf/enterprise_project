<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/location-transtype-maintain.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.location.transtype"/></title>
<style>
#div-tt-sum {background-color: #fff;  color: #2E6E9E;}
#div-tt-sum table {border: 1px solid #2E6E9E;}
#div-tt-sum tr {border: 1px solid #BECEEB;}
#div-tt-sum th, #div-tt-sum td { padding: 3px; border-right-color: inherit; border-right-style: solid; border-right-width: 1px;
		border-bottom-color: inherit; border-bottom-style: solid; border-bottom-width: 1px;
		}
#div-tt-sum thead{background-color: #EFEFEF;}
</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<!-- 这里是页面内容区 -->
	<div id="district-container" dirty="false">
		<ul>
			<!-- -->
		</ul>
		<!-- -->
	</div>
	<div id="pager"></div>
	<input type="hidden" id="tabIndex" name="tabIndex" value="0"/>
	<input type="hidden" id="districtId" name="districtId"/>
	<input type="hidden" id="locationId" name="locId"/>
	<table id="tbl-transtypelist"></table>
	<div class="buttonlist">
		
		<button type="button" loxiaType="button" class="confirm" id="bind"><s:text name="buton.warehouse.location.transtype.bind"/></button>
		<button type="button" loxiaType="button" id="unbind"><s:text name="buton.warehouse.location.transtype.unbind"/></button>　
		<button type="button" loxiaType="button" class="confirm" id="bindDistrict"><s:text name="buton.warehouse.location.transtype.binddistrict"/></button>
		<button type="button" loxiaType="button" id="unbindDistrict"><s:text name="buton.warehouse.location.transtype.unbinddistrict"/></button>
	</div>
	<div id="div-tt-sum" class="hidden">
		
	</div>
</body>
</html>