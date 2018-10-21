<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><s:text name="label.wahrhouse.inner.title"></s:text></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/sku_group_create.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<table width="350px">
	<tr>
		<td class="label">店铺</td>
		<td>
				<select id="selShopId" name="sta.owner" loxiaType="select">
					<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
				</select>
		</td>
		<td>
			<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
		</td>
	</tr>
</table>
<div id="tabs">
	<ul>
		<li><a href="#tab-1">商品拆分</a></li>
		<li><a href="#tab-2">商品组合</a></li>
	</ul>
	<div id="tab-1">
		<table width="600px">
			<tr>
				<td width="20%" class="label">
					原商品SKU编码:
				</td>
				<td width="25%" >
					<input loxiaType="input" mandatory="true" id="tab1SkuCode" trim="true" checkmaster="skuCheck" aclist="<%=request.getContextPath() %>/findAvailSku.json"  />
				</td>
				<td width="45%" id="tblSkuCost" colspan="2">
					<span class="label" >平均成本</span>
					<div id="tab1SkuCodeDiv" style="color:red;"></div>
				</td>
			</tr>
			<tr>
				<td class="label">
					<br/>
					拆分商品明细：
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<div loxiaType="edittable" >
						<table operation="add,delete" append="1" id="tab1Table" >
							<thead>
								<tr>
									<th width="10"><input type="checkbox"/></th>
									<th width="200">商品SKU编码</th>
									<th width="100">入库成本</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
							<tbody >
								<tr class="add" index="(#)">
									<td><input type="checkbox"/></td>
									<td><input loxiatype="input" isSkuTrue='1' checkmaster="skuCheck" mandatory="true" name="addList(#).code" class="code" aclist="<%=request.getContextPath() %>/findAvailSku.json"/></td>
									<td><input loxiatype="input" mandatory="true" name="addList(#).skuCost" class="qty"/></td>
								</tr>
							</tbody>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td class="label">
					库存状态:
				</td>
				<td>
					<s:select  id="tab1Sts" list="invStatusList"  loxiaType="select" listKey="id" 
		                    listValue="name" headerKey="" headerValue="请选择">            
		            </s:select>
					
				</td>
				<td class="label">
					数量
				</td>
				<td>
					<input id="tabs1Qty" loxiaType="number" mandatory="true" min="1" />
				</td>
			</tr>
		</table>
		<div class="buttonlist">
			<button id="tab1Create" loxiaType="button" class="confirm">创建</button>
		</div>
	</div>
	<div id="tab-2">
		<table width="600px">
			<tr>
				<td class="label">
					组合商品明细：
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<div loxiaType="edittable" >
						<table operation="add,delete" id="tab2Table" >
							<thead>
								<tr>
									<th width="10"><input type="checkbox"/></th>
									<th width="200">商品SKU编码</th>
									<th width="100">入库成本</th>
								</tr>
							</thead>
							<tbody>
								<tr class="add" index="(#)">
									<td><input type="checkbox"/></td>
									<td><input loxiatype="input" mandatory="true" name="addList(#).barcode" class="code"  checkmaster="skuCheck" aclist="<%=request.getContextPath() %>/findAvailSku.json" /></td>
									<td><input loxiatype="input" mandatory="true" name="addList(#).skuCost" class="qty" readonly="readonly"/></td>
								</tr>
							</tbody>
							<tbody >
								<tr class="add" index="(#)">
									<td><input type="checkbox"/></td>
									<td><input loxiatype="input" mandatory="true" name="addList(#).barcode" class="code"  checkmaster="skuCheck" aclist="<%=request.getContextPath() %>/findAvailSku.json" /></td>
									<td><input loxiatype="input" mandatory="true" name="addList(#).skuCost" class="qty" readonly="readonly"/></td>
								</tr>
							</tbody>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td width="20%" class="label">
					组合商品SKU编码:
				</td>
				<td width="25%" >
					<input loxiaType="input" isSkuTrue='1' mandatory="true" trim="true" id="tab2SkuCode" checkmaster="skuCheck" aclist="<%=request.getContextPath() %>/findAvailSku.json"/>
				</td>
				<td width="20%" >
				</td>
				<td width="25%"  id="tab2SkuCost">
				</td>
			</tr>
			<tr>
				<td class="label">
					库存状态:
				</td>
				<td>
					<s:select  id="tab2Sts" list="invStatusList"  loxiaType="select" listKey="id" 
		                    listValue="name" headerKey="" headerValue="请选择">            
		            </s:select>
				</td>
				<td class="label">
					数量
				</td>
				<td>
					<input loxiaType="number" mandatory="true" id="tab2Qty" min="1" />
				</td>
			</tr>
			<tr>
				<td class="label">
					组合商品入库库位
				</td>
				<td>
					<input loxiaType="input" id="locCode" trim="true" />
				</td>
				<td></td>
				<td></td>
			</tr>
		</table>
		<div class="buttonlist">
			<button id="tab2Create" loxiaType="button" class="confirm">创建</button>
		</div>
	</div>
</div>
<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>