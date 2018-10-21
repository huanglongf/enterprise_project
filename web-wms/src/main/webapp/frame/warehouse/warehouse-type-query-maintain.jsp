<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.dispatchList.title"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/warehouse-type-query-maintain.js' includeParams='none' encode='false'/>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	
<div id="div1">
		<table id="filterTable">
			<tr>
				<td class="label">库位类型名称</td>
				<td>
					<input id="name" loxiaType="input"  trim="true"/>
				</td>
				<td class="label">库位类型编码</td>
				<td>
					<input id="code" loxiaType="input"  trim="true"/>
				</td>
				<td>
					<button type="button" id="save" loxiaType="button" class="confirm">创建/保存</button>
				</td>
			</tr>
		</table>
		<div class="buttonlist">
		</div>
	
	<div id="div-waittingList">
		<table id="tbl-waittingList"></table>
		<div id="pager_query"></div>
	</div>
	<button type="button" class="confirm" loxiaType="button" id="del">删除选中项</button>
</div>
<div class="hidden">
	<OBJECT ID='emsObject' name='emsObject' CLASSID='CLSID:53C732B2-2BEA-4BCD-9C69-9EA44B828C7F' align=center hspace=0 vspace=0></OBJECT>
</div>
<iframe id="frmSoInvoice" class="hidden"></iframe>
<iframe id="exportFrame" class="hidden" target="_blank"></iframe>
<div id="showType" style="display:none;">
<input type="hidden" id="typeId"/>
<table>
			<tr>
				<td class="label">
					库位类型名称
				</td>
				<td>
					<input loxiaType="input" trim="true" id="name2"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					库位类型编码
				</td>
				<td>
					<input loxiaType="input" trim="true" id="code2"/>
				</td>
			</tr>
		</table>
	<button type="button" loxiaType="button" id="updateType" onclick="updateType()">修改</button>
	<button type="button" loxiaType="button" id="btnTypeClose" >取消</button>
</div>

</body>
</html>