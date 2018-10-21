<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.dispatchList.title"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/warehouse-zoon-sort.js' includeParams='none' encode='false'/>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
<div id="div1">
		<table id="filterTable">
			<tr>
				<td class="label">
					仓库区域编码
				</td>
				<td>
					<input loxiaType="input" trim="true" id="code"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					仓库区域名称
				</td>
				<td>
					<input loxiaType="input" trim="true"  id="name"/>
				</td>
			</tr>
		</table>
		<div class="buttonlist">
			<button type="button"  class="confirm" loxiaType="button" id="search">查询</button><button  class="confirm" type="button" loxiaType="button" id="reset" >重置</button>
		</div>

	
	<div id="div-waittingList">
		<table id="tbl-waittingList"></table>
		<div id="pager_query"></div>
	</div>
</div>

<div id="showZoon" style="display:none;">
<input id ="zId" type="hidden" value=""/>
<table>
			<tr>
				<td class="label">
				 仓库区域名称
				</td>
				<td>
					<a  id="name1" ></a>
				</td>
			</tr>
			<tr>
				<td class="label">
					序列
				</td>
				<td>
					<input loxiaType="input" trim="true" id="seq" />
				</td>
			</tr>
		</table>
	<button type="button" loxiaType="button" id="commitZoon" >修改</button><button type="button" loxiaType="button" id="exitZoon" >取消</button>
	
</div>
</body>
</html>