<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="title.inventory.query"/></title>
<%@include file="/common/meta.jsp"%>

<style>
#div-inventory-detail {background-color: #fff;  color: #2E6E9E;}
#div-inventory-detail table {border: 1px solid #2E6E9E;}
#div-inventory-detail tr {border: 1px solid #BECEEB;}
#div-inventory-detail th, #div-inventory-detail td { padding: 3px; border-right-color: inherit; border-right-style: solid; border-right-width: 1px;
		border-bottom-color: inherit; border-bottom-style: solid; border-bottom-width: 1px;
		}
#div-inventory-detail thead{background-color: #EFEFEF;}
</style>

<script type="text/javascript" src="../scripts/frame/padcode-operate.js"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
<div class="buttonlist"></div>
<form action="" name="quert-form" id="quert-form">
	<input loxiaType="input" name="code" id="code" style="width: 200px">　
	<button type="button" id="submit" loxiaType="button" class="confirm">查寻 </button>
</form>
<br />
<table id="query-info"></table>
<button type="button" id="deleteSelect" loxiaType="button" class="confirm">删除选中行 </button>
<br /><br />
<div class="buttonlist"></div>
<input loxiaType="input" id="addCode" style="width: 200px">　<button type="button" id="save" loxiaType="button" class="confirm">保存 </button>

</html>