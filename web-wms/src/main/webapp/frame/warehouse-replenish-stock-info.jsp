<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>仓库库内补货--基础信息</title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse-replenish-stock-info.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
   <iframe id="upload" name="upload" class="hidden"></iframe>
	<div id="divHead">
	<form id="query_form">
		<table width="95%">
			<tr>
				<td class="label">
					库位编码
				</td>
				<td>
					<input loxiaType="input" trim="true" id="LocationCode" name="LocationCode"/>
				</td>
				<td class="label">
					库区编码
				</td>
				<td>
					<input loxiaType="input" trim="true" id="DistrictCode" name="DistrictCode"/>
				</td>
			</tr>
		</table>
		</form>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="query" >查询</button>
		<button type="button" loxiaType="button" id="reset" >重置</button>
	</div>
		<br/>
		<table id="tbl_rec"></table>
		<div id="tbl_rec_page"></div>
		
		</div>

                  <br/>
                    <div class="buttonlist">
					    <form method="POST" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
					       导入文件： <input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
		            </form>
		            <button type="button" loxiaType="button" class="confirm" id="refWhShop">库存安全警告数设置导入</button>
		             <button type="button" loxiaType="button" id="export"><s:text name="label.warehouse.inpurchase.export"/></button>
		            </div>
		            
		            <div id="download"></div>
	
</body>
</html>