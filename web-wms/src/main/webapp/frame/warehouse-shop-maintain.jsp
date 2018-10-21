<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>仓库店铺关系维护</title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse-shop-maintain.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<table width="40%">
		<tr>
			<td width="20%" class="label">
				店铺
			</td>
			<td>
				<select id="shopS" loxiaType="select">
					<option value="">请选择 </option>
				</select>
			</td>
			<td>
				<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
			</td>
		</tr>
	</table>
	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">维护关联仓库</a></li>
			<li><a href="#tabs-2">维护关联物流</a></li>
		</ul>
		<div id="tabs-1" >
			<table id="tbl_wh"></table>
			<div class="buttonlist">
				<button type="button" loxiaType="button" class="confirm" id="refWhShop">关联选中</button>
				<button type="button" loxiaType="button" id="cancelWhShopRef">取消关联选中</button>
			</div>
		</div>
		<div id="tabs-2">
			<table id="tbl_trans"></table>
			<div class="buttonlist">
				<button type="button" loxiaType="button" class="confirm" id="refLogistics">关联选中</button>
				<button type="button" loxiaType="button" id="cancelRef">取消关联选中</button>
			</div>
		</div>
	</div>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>
