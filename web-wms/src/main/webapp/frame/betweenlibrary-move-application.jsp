<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="betweenlibary.createoredit"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/betweenlibrary-move-application.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<s:text id="pselect" name="label.please.select"/>
<table>
	<tr>
		<td class="label"><s:text name="betweenlibary.main.warehouse"/></td>
		<td width=150>
            <s:select list="whList"  loxiaType="select" listKey="id" 
                    listValue="name"   headerKey="" headerValue="%{pselect}" name="betwenMoveCmd.startWarehouseId">            
            </s:select>
        </td>
		<td class="label"><s:text name="betweenlibary.addi.warehouse"/></td>
		<td width=200>
            <s:select list="whList"  loxiaType="select" listKey="id" 
                    listValue="name"  headerKey="" headerValue="%{pselect}" name="betwenMoveCmd.endWarehouseId">            
            </s:select>
        </td>
        <td class="label">库存状态</td>
		<td width=200>
            <s:select list="invList"  loxiaType="select" listKey="id" 
                    listValue="name"  headerKey="" headerValue="%{pselect}" name="betwenMoveCmd.invStatusId">            
            </s:select>
        </td>
		<td class="label"><s:text name="label.warehouse.inpurchase.shop"/>：</td>
		<td width=150>
			<select id="selShopId" name="betwenMoveCmd.owner" loxiaType="select">
				<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
			</select>
		</td>
		<td>
		</td>
		
	</tr>
	<tr>
		<td class="label"><span class="label"><s:text name="label.warehouse.choose.file"></s:text> </span></td>
		<td colspan=2 >
			<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
				<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
			</form>
		</td>
		<td >
		<button type="button" loxiaType="button" id="import" class="confirm"><s:text name="button.import"></s:text></button>
		<button type="button" loxiaType="button" id="export"><s:text name="label.warehouse.inpurchase.export"/></button>
		</td>
		<td ></td>
		<td ></td>
	</tr>
</table>
<br />

<table id="tbl-invList"></table>
	<div id="download"></div>
	<iframe id="upload" name="upload" class="hidden"></iframe>
	<br/>
	<button type="button" loxiaType="button" class="confirm" id="confirm"><s:text name="betweenlibary.submit"/></button>
<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>