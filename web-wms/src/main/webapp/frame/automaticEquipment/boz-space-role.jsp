<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>集货管理</title>
<%@include file="/common/meta.jsp"%>	
<script type="text/javascript" src="<s:url value='/scripts/frame/automaticEquipment/boz-space-role.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/priority_city_config.js' includeParams='none' encode='false'/>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<form id="form_query">
			<table>
				<tr>
					<td class="label">店铺：</td>
					<td width="160px">
							<select loxiaType="select" id="shopLikeQuery" name="owner">
								<option value="" selected="selected">--请选择--</option>
							</select>
					</td>
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
		
		<table id="tbl-checkSpace-role-line"></table>
		<div id="pager"></div> 
		<table>
			<tr>
				<td>
					<button type="button" loxiaType="button" class="confirm" id="addSub">
						<s:text name="button.add"></s:text>
					</button>
				</td>
			</tr>
		</table>
		
		<div id="dialog_addRoleLine">
			<form id="form_info">
				<table>
					<tr>
						<td class="label" width="10%">店铺：</td>
						<td width="20%">
							<select loxiaType="select" id="shopLikeQuery1" name="owner">
								<option value="" selected="selected">--请选择--</option>
							</select>
						</td>
						<td width="10%"></td>
						<td class="label" width="10%">OTO门店：</td>
						<td width="20%">
							<select loxiaType="select" id="otoLocation" name="toLocation">
								<option value="" selected="selected">--请选择--</option>
							</select>
						</td>
					<tr>
					<tr>
						<td class="label" width="10%">是否QS：</td>
						<td width="20%">
							<select loxiaType="select" name="isQs" id="isQs" >
								<option value="">--请选择--</option>
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</td>
						<td width="10%"></td>
						<td class="label" width="10%">是否特殊处理：</td>
						<td width="20%">
							<select loxiaType="select" name="isSpaice" id="isSpaice" >
								<option value="">--请选择--</option>
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</td>
					</tr>	
					<tr>
						<td class="label" width="10%">物流商：</td>
						<td width="20%">
							<select loxiaType="select" id="selLpcode" name="lpcode">
								<option value="" selected="selected">--请选择--</option>
							</select>
						</td>
						<td width="10%"></td>
						<td class="label" width="10%">优先发货城市：</td>
						<td width="20%">
							<select loxiaType="select" id="priorityCity" name="city">
								<option value="" selected="selected">--请选择--</option>
							</select>
						</td>
					<tr>
					<tr>
						<td class="label" width="10%">优先级：</td>
						<td width = "160px">
							<input loxiaType="input" id="sort" name="sort"  maxlength="4" m mandatory="true" onkeyup="this.value=this.value.replace(/\D/g,'')" width="120px"></input>
						</td>
						<td width="10%"></td>
						<td class="label" width="10%">播种墙编码：</td>
						<td width="20%"><input loxiaType="input" trim="true"  maxlength="20" mandatory="true"  id = "checkCode" name="checkCode"/></td>
					</tr>
					<tr>
						<td width="10%"></td>
					</tr>
				</table>
			</form>
			<div class="buttonlist">
				<table>
					<tr>
						<button type="button" loxiaType="button" class="confirm" id="saveSub">
							<s:text name="button.save"></s:text>
						</button>
						<button type="button" loxiaType="button" id="closeSub">
							关闭
						</button>
					</tr>
				</table>
			</div>
		</div>
		<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>