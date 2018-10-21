<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>集货管理</title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/automaticEquipment/shipping-point-collection-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id = "div1">
		<form id="form_query">
			<table>
				<tr>
				    <td class="label">物理仓名称：</td>
					<td width="160px"><select id="physicalId" name="goodsCollection.physicalId.id" loxiaType="select" ></select></td>
<!-- 					<input loxiaType="input" name="goodsCollection.physicalId.id"  id="physicalId"  trim="true"/></td>
 -->					<td class="label">集货区域编码：</td>
					<td width="160px"><input loxiaType="input" name="goodsCollection.collectionCode"   trim="true"/></td>
					<td class="label">弹出口：</td>
					<td width="160px"><input loxiaType="input" name="goodsCollection.popUpCode"  trim="true"/></td>
				</tr>
				<tr>
				    <td class="label">批次号：</td>
					<td width="160px"><input loxiaType="input" name="goodsCollection.plCode"   trim="true"/></select></td>
					<td class="label">周转箱编号：</td>
					<td width="160px"><input loxiaType="input" name="goodsCollection.container"   trim="true"/></td>
					<td ></td>
					<td ></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<table>
				<tr>
					<button type="button" loxiaType="button" class="confirm" id="search">
						查询
					</button>
					<button type="button" loxiaType="button" id="reset">
						重置
					</button>
				</tr>
			</table>
		</div>
		
		<table id="tbl-shipping-point"></table>
		<div id="pager"></div> 
		
	</div>
	
	<div id="dialog_addWeight" style="text-align: center;" >
				<table>
				        <tr><td><input id="t_wh_goods_collection_id" type="hidden" /></td></tr>
						
						<tr>
							<td>物理仓名称 <span style="color:red;">*</span></td>
							<td><select id="physicalId1" loxiaType="select" ></select>
							    <input loxiaType="input" trim="true" id="physicalId2" readonly="readonly"/>
						    </td>
						</tr>
						<tr>
							<td>集货区域编码 <span style="color:red;">*</span></td>
							<td><input loxiaType="input" trim="true" id="collectionCode"/>
						    </td>
						</tr>
						<tr>
							<td>顺序<span style="color:red;">*</span></td>
							<td><input loxiaType="input" trim="true" id="sort"/></td>
							
						</tr>
						<tr>
							<td>弹出口</td>
							<td><input loxiaType="input" trim="true" id="popUpCode"/></td>
						</tr>
						
				</table>
			<div class="buttonlist">
					<button type="button" loxiaType="button" class="confirm" id="saveWeight">保存</button>
					<button type="button" loxiaType="button" id="closediv">关闭</button>
			</div>
         </div>
	<iframe id="upload" name="upload" class="hidden"></iframe>
</body>
</html>