<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.company.modify.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/baseinfo.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<form id="companyShopForm" name="companyShopForm">
	<table width="90%">
		<tr>
			<td colspan="4"><h4><s:text name="companyShop.shopType"/></h4></td>
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="companyShop.ou.node.code"/>：</td>
			<td width="30%"><s:property value="ou.code"/></td>
			<td width="20%" class="label"><s:text name="companyShop.ou.node.type"/>：</td>
			<td width="30%"><s:property value="ou.ouType.displayName"/></td>
		</tr>
		<tr>
			<td class="label"><s:text name="companyShop.ou.node.name"/>：</td>
			<td ><input loxiaType="input" mandatory="true" name="shopId" value="<s:property value='companyShop.shopId'/>"/></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td class="label"><s:text name="companyShop.shopType"/>：</td>
			<td>
				<select loxiaType="select" name="shopType">
					<option value="">请选择</option>
					<option value="TB01">淘宝经销</option>
					<option value="TB02">淘宝代销(不发货)</option>
					<option value="TB03">淘宝代销(发货)</option>
				</select>
			</td>			
			<td class="label"><s:text name="companyShop.industry"/>：</td>
			<td><input loxiaType="input" mandatory="true" name="industry" value="<s:property value='companyShop.industry'/>"/></td>
		</tr>
		<tr>
			<td class="label"><s:text name="companyShop.addressId"/></td>
			<td><input loxiaType="input" mandatory="true" name="addressId" value="<s:property value='companyShop.addressId'/>" /></td>			
			<td class="label" ><s:text name="companyShop.platformType"/></td>
			<td>
				<select loxiaType="select" name="platformType">
					<option value="">请选择</option>
					<option value="1">淘宝</option>
					<option value="2">乐酷天</option>
					<option value="3">拍拍</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="label"><s:text name="companyShop.brans"/>：</td>
			<td><input loxiaType="input" mandatory="true" name="brans"  value="<s:property value='companyShop.brans'/>" /></td>			
			<td class="label"><s:text name="companyShop.rtnWarehouseAddress"/>：</td>
			<td><input loxiaType="input" mandatory="true" name="rtnWarehouseAddress" value="<s:property value='companyShop.rtnWarehouseAddress'/>"/></td>
		</tr>
		<tr>
			<td class="label"><s:text name="companyShop.address"/>：</td>
			<td><input loxiaType="input" mandatory="true" name="address" value="<s:property value='companyShop.address'/>"/></td>
			<td class="label"><s:text name="companyShop.zipcode"/>：</td>
			<td><input loxiaType="input" mandatory="true" name="zipcode" value="<s:property value='companyShop.zipcode'/>"/></td>	
		</tr>
	</table>
	<table width="90%">
		<tr>
			<td colspan="4"><h4><s:text name="companyShop.principalInfo"/></h4></td>
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="companyShop.shopName"/>：</td>
			<td width="30%"><input loxiaType="input" mandatory="true" name="shopName" value="<s:property value='companyShop.shopName'/>"/></td>
			<td width="20%"></td>
			<td width="30%"></td>
		</tr>
		<tr>
			<td class="label"><s:text name="companyShop.telephone"/>：</td>
			<td><input loxiaType="input" mandatory="true" name="telephone" value="<s:property value='companyShop.telephone'/>"/></td>			
			<td class="label"><s:text name="companyShop.mobile"/>：</td>
			<td><input loxiaType="input" mandatory="true" name="mobile"  value="<s:property value='companyShop.mobile'/>" /></td>
		</tr>
	</table>
	<table width="90%">
		<tr>
			<td colspan="4"><h4><s:text name="companyShop.k3Shopinfo"/></h4></td>
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="companyShop.k3ShopCode"/>：</td>
			<td width="30%"><input loxiaType="input" mandatory="true" name="k3ShopCode" value="<s:property value='companyShop.k3ShopCode'/>" /></td>
			<td width="20%" class="label"><s:text name="companyShop.isAutoOrder"/></td>
			<td width="30%">
			<input type="radio"  name="isAutoOrder" checked="<s:if test="companyShop.isAutoOrder">true</s:if>" />是 
			<input type="radio"  name="isAutoOrder" checked="<s:if test="!companyShop.isAutoOrder">true</s:if>" />否</td>
		</tr>
		<tr>
			<td class="label"><s:text name="companyShop.fixedOperatingItem"/>：</td>
			<td><input loxiaType="input" mandatory="true" name="fixedOperatingItem" value="<s:property value='companyShop.fixedOperatingItem'/>" /></td>
			<td class="label"><s:text name="companyShop.isBaozunBillingInvoice"/></td>
			<td><input type="radio"  name="isBaozunBillingInvoice" checked='<s:if test="companyShop.isBaozunBillingInvoice">true</s:if>' />是 
			    <input type="radio"  name="isBaozunBillingInvoice" checked='<s:if test="!companyShop.isBaozunBillingInvoice">true</s:if>' />否</td>
		</tr>
	</table>
	<table width="90%">
		<tr>
			<td colspan="4"><h4><s:text name="companyShop.ShopOtherinfo"/></h4></td>
		</tr>
		<tr>		
			<td width="20%" class="label"><s:text name="companyShop.tbRebateRate"/>：</td>
			<td width="30%"><input loxiaType="number" name="tbRebateRate" value="<s:property value='companyShop.tbRebateRate'/>" /></td>
			<td width="20%" class="label"><s:text name="companyShop.tbPointGainedRate"/>：</td>
			<td width="30%"><input loxiaType="number" name="tbPointGainedRate" value="<s:property value='companyShop.tbPointGainedRate'/>" /></td>
		</tr>
		<tr>		
			<td width="20%" class="label"><s:text name="companyShop.creditCateChangeRate"/>：</td>
			<td width="30%"><input loxiaType="number" name="creditCateChangeRate" value="<s:property value='companyShop.creditCateChangeRate'/>" /></td>
			<td></td>
			<td></td>
		</tr>
		
		<tr>		
			<td class="label"><s:text name="companyShop.appKey"/>：</td>
			<td><input loxiaType="input" name="appKey" value="<s:property value='companyShop.appKey'/>"/></td>
			<td class="label"><s:text name="companyShop.appSecret"/>：</td>
			<td><input loxiaType="input" name="appSecret" value="<s:property value='companyShop.appSecret'/>" /></td>
		</tr>
		<tr>		
			<td class="label"><s:text name="companyShop.sessionKey"/></td>
			<td><input loxiaType="input" name="sessionKey" value="<s:property value='companyShop.sessionKey'/>" /></td>
			<td></td>
			<td></td>
		</tr>
		<tr>		
			<td class="label"><s:text name="companyShop.isTiXian"/></td>
			 <td>
			
			 	<input type="radio"  name="isTiXian" checked='<s:if test="companyShop.isTiXian">true</s:if>'/><s:text name="companyShop.yes"/>
				<input type="radio"  name="isTiXian" checked='<s:if test="!companyShop.isTiXian">true</s:if>' /><s:text name="companyShop.no"/>
			</td>
			<td></td>
			<td></td>
		</tr>
		<tr>		
			<td class="label"><s:text name="companyShop.isSyncInventory"/></td>
			<td><input type="radio"  name="isSyncInventory" checked='<s:if test="companyShop.isSyncInventory">true</s:if>'/><s:text name="companyShop.yes"/>
			    <input type="radio"  name="isSyncInventory" checked='<s:if test="!companyShop.isSyncInventory">true</s:if>'/><s:text name="companyShop.no"/></td>
			<td></td>
			<td></td>
		</tr>
		<tr>		
			<td class="label"><s:text name="companyShop.isAutoOrder"/></td>
			<td>
			<input type="radio"  name="isAutoOrder"  checked='<s:if test="companyShop.isAutoOrder">true</s:if>' /><s:text name="companyShop.yes"/> 
			<input type="radio"  name="isAutoOrder" checked='<s:if test="!companyShop.isAutoOrder">true</s:if>' /><s:text name="companyShop.no"/></td>
			<td></td>
			<td></td>
		</tr>
		<tr>		
			<td class="label"><s:text name="companyShop.isAutoCommit"/></td>
			<td><input type="radio"  name="isAutoCommit" checked='<s:if test="companyShop.isAutoCommit">true</s:if>' /><s:text name="companyShop.yes"/>
			    <input type="radio"  name="isAutoCommit" checked='<s:if test="!companyShop.isAutoCommit">true></s:if>' /><s:text name="companyShop.no"/></td>
			<td></td>
			<td></td>
		</tr>
		<tr>		
			<td class="label"><s:text name="companyShop.isOpenWlb"/></td>
			<td><input type="radio"  name="isOpenWlb" checked='<s:if test="companyShop.isOpenWlb">true</s:if>' /><s:text name="companyShop.yes"/> 
			<input type="radio"  name="isOpenWlb" checked='<s:if test="companyShop.isOpenWlb">true</s:if>' /><s:text name="companyShop.no"/></td>
			<td></td>
			<td></td>
		</tr>
		<tr>		
					<td class="label"><s:text name="是否开通短信通知"/>:</td>
					<td><input type="radio"  name="isSms" <s:if test="companyShop.isSms">checked='checked'</s:if> /><s:text name="companyShop.yes"/> 
			        <input type="radio"  name="isSms" <s:if test="!companyShop.isSms">checked='checked'</s:if> /><s:text name="companyShop.no"/></td>
					<td></td>
					<td></td>
				</tr>
	</table>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="Comshopsavebtn" ><s:text name="button.save"/></button>
	</div>
</body>
</html>