<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse" /></title>
 <script type="text/javascript"
	src="<s:url value='/scripts/frame/warehouse-outsourcingwh-association.js' includeParams='none' encode='false'/>"></script>  
</head>
<body style="background-color: #f2f2f2;"
	contextpath="<%=request.getContextPath()%>">
	<form id="form_query">
		<table>
			<tr>
				<td class="label">品牌：</td>
				<td width="200px"><select name="brandId" id="brandName" loxiaType="select">
						<option value="">--请选择--</option>
				</select></td>
				<td class="label">外包仓来源：</td>
				<td width="200px"><input loxiaType="input" name="source"  id="source"/></td>
				<td class="label">外包仓编码：</td>
				<td width="200px"><input loxiaType="input" name="sourcewh"  id="sourcewh"/></td>
				<td class="label">店铺：</td>
				<td width="200px"><select name="channelId" id="channelId" loxiaType="select">
						<option value="">--请选择--</option>
				</select></td>
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<table>
			<tr>
				<button type="button" loxiaType="button" class="confirm" id="search">
					<s:text name="button.search"></s:text>
				</button>
				<button type="button" loxiaType="button" id="reset">
					<s:text name="button.reset"></s:text>
				</button>
			</tr>
		</table>
	</div>
	
	<div class="buttonlist">
		<table id="new-sec-kill-sku">
			<div id="pager"></div>
		</table>
	</div>

	<div>
		<button type="button" loxiaType="button" id="create" class="confirm">新建</button>
	</div>
	<dir id='showAdd' class="hidden">
	  <form id="add_form">
			<table>
				<tr>
					<td class="label">品牌：</td>
					<td><select class="special-flexselect" id='brandNameAll'
						name="brandId" data-placeholder="请选择品牌">
					</select></td>
					<td class="label">外包仓来源：</td>
					<td><input loxiaType="input" trim="true" name="source"
						id='source1' /></td>

				</tr>
				<tr>
					<td class="label">外包仓编码：</td>
					<td><input loxiaType="input" name="sourcewh" id="sourcewh1" /></td>
					<td class="label">店铺：</td>
					<td><select class="special-flexselect" name='channelId'
						id='channelId1' data-placeholder="请选择店铺">
					</select></td>
				</tr>
			</table>
		</form>
		<div>
			<button type="button" loxiaType="button" id="confirm" class="confirm">确认</button>
		</div>
	</dir>
</body>
</html>