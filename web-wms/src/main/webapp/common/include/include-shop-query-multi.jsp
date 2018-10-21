<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<s:url value='/scripts/common/include/include-shop-query-multi.js' includeParams='none' encode='false'/>"></script>
<div id="shopQueryDialog">
<input id="selTransopc" hidden/>
	<table id="tbl_shop_query_dialog"></table>
	<br/>
	<button type="button" loxiaType="button" class="confirm" id="btnShopQueryConfirm" >确认</button>
	<button type="button" loxiaType="button" id="btnShopQueryClose" >取消</button>
</div>

<div id="shopGroupQueryDialog">
<input id="selTransopc" hidden/>
	<table id="tbl_shopGroup_query_dialog"></table>
	<br/>
	<button type="button" loxiaType="button" class="confirm" id="btnShopGroupQueryConfirm" >确认</button>
	<button type="button" loxiaType="button" id="btnShopGroupQueryClose" >取消</button>
</div>
<div id="pickAreaQueryDialog">
<input id="selTransopc" hidden/>
	<table id="tbl_pickArea_query_dialog"></table>
	<br/>
	<button type="button" loxiaType="button" class="confirm" id="btnPickAreaQueryConfirm" >确认</button>
	<button type="button" loxiaType="button" id="btnPickAreaQueryClose" >取消</button>
</div>