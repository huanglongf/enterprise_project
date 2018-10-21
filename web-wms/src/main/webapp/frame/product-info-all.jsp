<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/product-info-all.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="f1">
		<form id="form_query" name="form_query">
		<input type="hidden" id="nikeFlag" name="flag" value="false"/>
		<table>
			<tr>
				<td class="label">商品编码:</td>
				<td><input loxiaType="input" name="proCmd.code" id="code" trim="true"/></td>
				<td class="label">商品条码:</td>
				<td><input loxiaType="input" name="proCmd.barCode" id="barCode" trim="true"/></td>
				<td class="label">品牌名称:</td>
				<td>
					<select loxiaType="select" id="brandName" trim="true" name="proCmd.brandName" data-placeholder="请选择品牌"></select>
				</td>
				<!-- <td class="label">上架类型:</td>
				<td><input loxiaType="input" name="proCmd.skuTypeName" id="skuTypeName" trim="true"/></td> -->
			</tr>
			<tr>
				<td class="label">货号:</td>
				<td><input loxiaType="input" name="proCmd.supplierCode" id="supplierCode" trim="true"/></td>
				<td class="label">商品名称:</td>
				<td><input loxiaType="input" name="proCmd.name" id="name" trim="true"/></td>
				<!-- <td class="label">箱型条码:</td>
				<td><input loxiaType="input" name="proCmd.packageBarCode" trim="true"/></td> -->
				<td class="label"><s:text name="label.warehouse.inpurchase.consumable"></s:text></td>
				<td>
					<select id="isConsumable" name="proCmd.isConsumable" loxiaType="select">
						<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</td>
			</tr>
		</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="query"><s:text name="button.query"></s:text></button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text></button><br/>
		</div>
		<table id="tbl-product">
		</table>
		<div id="pager"></div>
	</div>
	<div id="f2" class="hidden">
		<form id="editForm">
		<input loxiaType="input" type="hidden" id="id" name="proCmd.id"/>
		<input loxiaType="input" type="hidden" id="cuId" name="proCmd.cuId"/>
			<table>
				<tr>
					<td class="label" id="code11" style="color:#888">商品编码:</td>
					<td><input loxiaType="input" id="code1"  readonly="true" style="color:#888" name="proCmd.code"/></td>
					<td class="label">商品BAR编码:</td>
					<td><input loxiaType="input" id="barCode2" name="proCmd.barCode"/></td>
					<td class="label" id="customerSkuCode1" style="color:#888">客户商品编码:</td>
					<td><input loxiaType="input" id="customerSkuCode"  readonly="true" style="color:#888" name="proCmd.customerSkuCode"/></td>
					<td class="label">JMCODE:</td>
					<td><input loxiaType="input" id="jmCode2" name="proCmd.jmCode"/></td>
					<!-- <td colspan="6"></td> -->
				</tr>
				<tr>
					<td class="label" width="100px" >商品名称:</td>
					<td width="120px"><input loxiaType="input"   id="name1" name="proCmd.name"/></td>
					<td class="label" width="100px" >货号:</td>
					<td width="120px"><input loxiaType="input"   id="supplierCode1" name="proCmd.supplierCode"/></td>
					<td class="label" width="100px" style="color:#888">品牌名称:</td>
					<td width="120px"><input loxiaType="input"  readonly="true" style="color:#888" id="brandName1"/></td>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td class="label">长(单位/mm):</td>
					<td><input loxiaType="input" id="length" name="proCmd.length"/></td>
					<td class="label">宽(单位/mm):</td>
					<td><input loxiaType="input" id="width" name="proCmd.width"/></td>
					<td class="label">高(单位/mm):</td>
					<td><input loxiaType="input" id="height" name="proCmd.height"/></td>
					<td class="label">净重(单位/KG):</td>
					<td><input loxiaType="input" id="grossWeight" name="proCmd.grossWeight"/></td>
				</tr>
				<tr>
					<td class="label">是否陆运:</td>
					<td>
						<select loxiaType="select" id="railwayStr" name="proCmd.railwayStr">
							<option value="是">是</option>
							<option value="否">否</option>
						</select>
					</td>
					<td class="label">商品分类:</td>
					<td><input loxiaType="input" id="skuCategoriesName" name="proCmd.skuCategoriesName"/></td>
					<td class="label">箱型条码:</td>
					<td><input loxiaType="input" id="packageBarCode" name="proCmd.packageBarCode"/></td>
					<td class="label">商品上架类型</td>
					<td>
						<select id="skuType" loxiaType="select" name="proCmd.skuTypeId">
							<option value="">请选择</option>
						</select>
					</td>
				</tr>
			  <tr><td colspan="8"><span style="color:red;margin-left:50px;">*注意是否陆运的选择</span></td></tr>
			  <tr>
			  <td class="label">原产地</td>
			  <td><input loxiaType="input" id="countryOfOrigin" name="proCmd.countryOfOrigin"/></td>
			  <td class="label">报关编码</td>
			  <td><input loxiaType="input" id="htsCode" name="proCmd.htsCode"/></td>
			  <td class="label">单位名称</td>
			  <td><input loxiaType="input" id="unitName" name="proCmd.unitName"/></td>
			  <td class="label"><s:text name="label.warehouse.inpurchase.consumable"></s:text></td>
			  <td>
				<select id="consumable" name="proCmd.isConsumable" loxiaType="select">
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
		     </td>
			 </tr>
			<!-- 添加开始。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。-->
			<!-- 	<td><input loxiaType="input" readonly="true" style="color:#888" id="code1" name="proCmd.code"/></td> -->
			 <tr>
			  <td style="color:#888" class="label">ext_code1</td>
			  <td><input loxiaType="input"  id="extensionCode1" readonly="true" style="color:#888" name="proCmd.extensionCode1"/></td>
			  <td style="color:#888" class="label">ext_code2</td>
			  <td><input loxiaType="input" id="extensionCode2" readonly="true" style="color:#888" name="proCmd.extensionCode2"/></td>
			  <td  style="color:#888" class="label">ext_code3</td>
			  <td><input loxiaType="input" id="extensionCode3" readonly="true" style="color:#888" name="proCmd.extensionCode3"/></td>
			 </tr>
			 <tr>
			  <td class="label">颜色编码</td>
			  <td><input loxiaType="input" id="color"  name="proCmd.color"/></td>
			  <td class="label">颜色中文描述</td>
			  <td><input loxiaType="input" id="colorName" name="proCmd.colorName"/></td>
			  <td class="label">尺码</td>
			  <td><input loxiaType="input" id="skuSize" name="proCmd.skuSize"/></td>
			   <td class="label">商品英语名称</td>
			  <td><input loxiaType="input" id="enName" name="proCmd.enName"/></td>
			 </tr>
			 <tr>
			  <td class="label">ext_prop1</td>
			  <td><input loxiaType="input" id="extProp1" name="proCmd.extProp1"/></td>
			  <td class="label">ext_prop2</td>
			  <td><input loxiaType="input" id="extProp2" name="proCmd.extProp2"/></td>
			  <td class="label">ext_prop3</td>
			  <td><input loxiaType="input" id="extProp3" name="proCmd.extProp3"/></td>
			  <td class="label">ext_prop4</td>
			  <td><input loxiaType="input" id="extProp4" name="proCmd.extProp4"/></td>
			 </tr>
			  <tr>
			  <td class="label">商品吊牌价</td>
			  <td><input loxiaType="input" id="listPrice" name="proCmd.listPrice"/></td>
			  <td class="label">有效期天数</td>
			  <td><input loxiaType="input" id="validDate" name="proCmd.validDate"/></td>
			   <td class="label">预警天数</td>
			  <td><input loxiaType="input" id="warningDate" name="proCmd.warningDate"/></td>
			 </tr>
			 <tr>
			  <td class="label">扩展属性</td>
			  <td><input loxiaType="input" id="keyProperties" name="proCmd.keyProperties"/></td>
			  <td class="label">种类</td>
			  <td><input loxiaType="input" id="category" name="proCmd.category"/></td>
			 </tr>
			 <!-- 添加结束。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。-->
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="edit">修改</button>
			<button type="button" loxiaType="button" id="back">返回</button>
		</div>
	</div>
</body>
</html>