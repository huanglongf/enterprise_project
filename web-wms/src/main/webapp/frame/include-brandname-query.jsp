<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/include-brandname-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<div id="BrandNameQueryDialog">
	<table id="tbl_brandname_query_dialog"></table>
	<div id="tbl_brandname_query_dialog_pager"></div>
	<br/>
	<button type="button" loxiaType="button" class="confirm" id="btnBrandNameQueryConfirm" >确认</button>
	<button type="button" loxiaType="button" id="btnBrandNameQueryClose" >取消</button>
</div>

