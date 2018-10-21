<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="betweenlibary.createoredit"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/VMI-flitting-create.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<table>
	<tr>
		<td class="label"><s:text name="betweenlibary.main.warehouse"/></td>
		<td width=150 >
                <s:select list="whList"  loxiaType="select" listKey="id" 
                    listValue="name"   headerKey="" headerValue="%{pselect}" name="betwenMoveCmd.startWarehouseId">            
            </s:select>
        </td>
        <td></td>
		<td class="label"><s:text name="betweenlibary.addi.warehouse"/></td>
		<td width=200>
			<s:select list="whList"  loxiaType="select" listKey="id" 
                    listValue="name"  headerKey="" headerValue="%{pselect}" name="betwenMoveCmd.endWarehouseId">            
            </s:select>
        </td>
		<td></td>
	</tr>
	<tr>
		<td class="label">源店铺:</td>
		<td >
				<select id="selMainShopId" name="betwenMoveCmd.owner" loxiaType="select">
					<option value=""></option>
				</select>
		</td>
		<td>
			<button type="button" loxiaType="button" class="confirm" id="btnSearchMainShop" >查询店铺</button>
		</td>
		<td class="label">目标店铺:</td>
		<td width=150>
				<select id="selTargetShopId" name="betwenMoveCmd.owner" loxiaType="select">
					<option value=""></option>
				</select>
		</td>
		<td>
			<button type="button" loxiaType="button" class="confirm" id="btnSearchTargetShop" >查询店铺</button>
		</td>
		
	</tr>
	<tr>
		<td class="label">库存状态:</td>
		<td>
			<s:select  id="keyProps" list="invStatusList"  loxiaType="select" listKey="id" 
                    listValue="name" headerKey="" headerValue="" name="betwenMoveCmd.owner">            
            </s:select>
		<td colspan=5></td>
	</tr>
	<tr>
		<td class="label"><span class="label"><s:text name="label.warehouse.choose.file"></s:text> </span></td>
		<td colspan=6 >
			<form method="POST" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
				<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
				<button type="button" loxiaType="button" id="import" class="confirm"><s:text name="button.import"></s:text></button>
				<button type="button" loxiaType="button" id="export"><s:text name="label.warehouse.inpurchase.export"/></button>
			</form>
		</td>
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