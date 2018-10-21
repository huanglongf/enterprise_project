<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/companyshop-info.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="tabs_m">
		<ul>
			<li><a href="#tabs1"><s:text name="companyShop.shopinfo"/></a></li>
			<li><a href="#tabs2"><s:text name="companyShop.shoprefinfo"/></a></li>
		</ul>
		<div id="tabs1">
			<table width="80%">
				<tr>
					<td colspan="4" style="font-size: 20px;"><s:text name="companyShop.shopbaseinfo"/></td>
				</tr>
				<tr>
					<td width="20%" class="label"><s:text name="companyShop.ou.node.code"/>：</td>
					<td width="30%" id="oucode"><s:property value="ou.code"/></td>
					<td width="20%" class="label"><s:text name="companyShop.ou.node.type"/>：</td>
					<td width="30%" id="outype"><s:property value="ou.ouType.displayName"/></td>
				</tr>
				<tr>
					<td class="label"><s:text name="companyShop.ou.node.name"/>：</td>
					<td ><s:property value="companyShop.shopId"/></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td class="label"><s:text name="companyShop.shopType"/>：</td>
					<td>
					<s:if test="companyShop.shopType=='TB01'">
						 淘宝经销
				     </s:if>
				     	<s:if test="companyShop.shopType=='TB02'">
						淘宝代销+不发货
				     </s:if>
				     	<s:if test="companyShop.shopType=='TB03'">
						淘宝代销+发货
				     </s:if>
					</td>			
					<td class="label"><s:text name="companyShop.industry"/>:</td>
					<td><s:property value="companyShop.industry" /></td>
				</tr>
				<tr>
					<td class="label"><s:text name="companyShop.addressId"/> :</td>
					<td><s:property value="companyShop.addressId" /></td>			
					<td class="label" ><s:text name="companyShop.platformType"/>:</td>
					<td>
			
						<s:if test="companyShop.platformType==1">
						淘宝
				     </s:if>
				     	<s:if test="companyShop.platformType==2">
						乐酷天
				     </s:if>
				     <s:if test="companyShop.platformType==3">
						拍拍
				     </s:if>
					</td>
				</tr>
				<tr>
					<td class="label"><s:text name="companyShop.brans"/>：</td>
					<td><s:property value="companyShop.brand" /></td>			
					<td class="label"><s:text name="companyShop.rtnWarehouseAddress"/>:</td>
					<td><s:property value="companyShop.rtnWarehouseAddress" /></td>
				</tr>
				<tr>
					<td class="label"><s:text name="companyShop.address"/>:</td>
					<td><s:property value="companyShop.address" /></td>
					<td class="label"><s:text name="companyShop.zipcode"/>:</td>
					<td><s:property value="companyShop.zipcode" /></td>	
				</tr>
			</table>
			<table width="80%">
				<tr>
					<td colspan="4" style="font-size: 20px;"><s:text name="companyShop.principalInfo"/>:</td>
				</tr>
				<tr>
					<td width="20%" class="label"><s:text name="companyShop.shopName"/>：</td>
					<td width="30%"><s:property value="companyShop.shopName" /></td>
					<td width="20%"></td>
					<td width="30%"></td>
				</tr>
				<tr>
					<td class="label"><s:text name="companyShop.telephone"/>:</td>
					<td><s:property value="companyShop.telephone" /></td>			
					<td class="label"><s:text name="companyShop.mobile"/>：</td>
					<td><s:property value="companyShop.mobile" /></td>
				</tr>
			</table>
			<table width="80%">
			    <tr>
				<td colspan="4" style="font-size: 20px;"><s:text name="companyShop.ShopOtherinfo"/>:</td>
				</tr>
				<tr>		
					<td width="20%" class="label"><s:text name="companyShop.isSyncInventory"/>:</td>
					<td width="30%">
				    <input type="radio" name="isSyncInventory" <s:if test="companyShop.isSyncInventory">checked='checked'</s:if>/><s:text name="companyShop.yes"/>
			        <input type="radio" name="isSyncInventory" <s:if test="!companyShop.isSyncInventory">checked='checked'</s:if>/><s:text name="companyShop.no"/>
				    </td>
					<td width="20%"></td>
					<td width="30%"></td>
				</tr>
				<tr>
					<td class="label"><s:text name="companyShop.isAutoOrder"/>:</td>
					<td><input type="radio"  name="isAutoOrder"  <s:if test="companyShop.isAutoOrder">checked='checked'</s:if> /><s:text name="companyShop.yes"/> 
			            <input type="radio"  name="isAutoOrder" <s:if test="!companyShop.isAutoOrder">checked='checked'</s:if> /><s:text name="companyShop.no"/></td>
					<td></td>
					<td></td>
				</tr>
				<tr>		
					<td class="label"><s:text name="companyShop.isAutoCommit"/>:</td>
					<td>
					<input type="radio"  name="isAutoCommit" <s:if test="companyShop.isAutoCommit">checked='checked'</s:if> /><s:text name="companyShop.yes"/>
			        <input type="radio"  name="isAutoCommit" <s:if test="!companyShop.isAutoCommit">checked='checked'</s:if> /><s:text name="companyShop.no"/>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>		
					<td class="label"><s:text name="companyShop.isOpenWlb"/>:</td>
					<td><input type="radio"  name="isOpenWlb" <s:if test="companyShop.isOpenWlb">checked='checked'</s:if> /><s:text name="companyShop.yes"/> 
			        <input type="radio"  name="isOpenWlb" <s:if test="!companyShop.isOpenWlb">checked='checked'</s:if> /><s:text name="companyShop.no"/></td>
					<td></td>
					<td></td>
				</tr>
				<tr>		
					<td class="label"><s:text name="companyShop.isSms"/>:</td>
					<td>
					 <input type="radio"  name="isSms" <s:if test="companyShop.isSms">checked='checked'</s:if> /><s:text name="companyShop.yes"/>
			         <input type="radio"  name="isSms" <s:if test="!companyShop.isSms">checked='checked'</s:if> /><s:text name="companyShop.no"/>
				     </td>
				<td></td>
				<td></td>
				</tr>
				<tr>		
					<td class="label"><s:text name="companyShop.smsTemplate"/>:</td>
					<td colspan="2"><s:property value="companyShop.smsTemplate" /></td>
					<td></td>
				</tr>
			</table>
		</div>
		<div id="tabs2">
			<div id="tabs">
				<ul>
					<li><a href="#tabs-1"><s:text name="companyShop.shoprefwarehouse"/></a></li>
					<li><a href="#tabs-2"><s:text name="companyShop.shoprefwl"/></a></li>
				</ul>
				<div id="tabs-1">
					<table id="tbl_wh"></table>
				</div>
				<div id="tabs-2">
					<table id="tbl_trans"></table>
				</div>
			</div>
		</div>
	</div>
</body>