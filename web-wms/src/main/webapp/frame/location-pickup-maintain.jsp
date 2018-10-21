<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="title.warehouse.pickup.set"/></title>
<%@include file="/common/meta.jsp"%>	
<script type="text/javascript" src="<s:url value='/scripts/frame/location-pickup-maintain.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<!-- 这里是页面内容区 -->
	<div id="district-container" dirty="false">
		<ul>
			<!-- -->
		</ul>			
		<!-- -->
	</div>		
	<table>
		<tr>
			<td class="label" width="50%"><s:text name="label.warehouse.batch.pickup"/>：</td>
			<td width="20%">
		<s:select list="pickUpOptionList" mandatory="true" loxiaType="select" listKey="optionKey" listValue="optionValue" name="sel-pickup-model" id="sel-pickup-model" >	
			</s:select>	
		</td>
			<td><button type="button" loxiaType="button" id="btn-batch-pickup"><s:text name="button.batch"/></button></td>
		</tr>		
	</table>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="btn-save"><s:text name="button.save"/></button>
	</div>
</body>
</html>