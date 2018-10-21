<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="label.warehouse.other.opertion"/></title>
<style>
	.ui-loxia-table tr.error{background-color: #FFCC00;}
	.divFloat{
		float: left;
		margin-right: 10px;
	}
</style>
<script type="text/javascript" src="<s:url value='/scripts/frame/predefined-out-create.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div>
		<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
			<table>
				<tr style="height: 40px">
					<td class="label">店铺</td>
					<td>
						<select id="companyshop" name="sta.owner" loxiaType="select">
							<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
						</select>
					</td>
					<td>
						<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
					</td>
					<td class="label">作业类型</td>
					<td >
						<select id="type" name="sta.intType" loxiaType="select">
							<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							<option value="62">结算经销出库 </option>
							<option value="64">代销 出库 </option>
							<option value="63">包材领用出库 </option>
						</select>
					</td>
				</tr><tr style="height: 40px">
					<td class="label">送货地址</td>
					<td >
						<input loxiaType="input" trim="true" name="sta.address" id='address'/>
					</td>
					<td></td>
					<td class="label">收货人</td>
					<td >
						<input loxiaType="input" trim="true" name="sta.receiver" id='receiver'/>
					</td>
				</tr><tr style="height: 40px">
					<td class="label">联系电话</td>
					<td >
						<input loxiaType="input" trim="true" name="sta.telephone" id='telephone'/>
					</td>
					<td></td>
					<td class="label">手机</td>
					<td >
						<input loxiaType="input" trim="true" name="sta.mobile" id='mobile'/>
					</td>
				</tr><tr style="height: 40px">
					<td class="label">导入文件</td>
					<td >
						<input type="file" loxiaType="input" id="staFile" name="file" style="width: 200px;"/> 
					</td>
					<td >
						<a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=预定义出库.xls&inputPath=tplt_import_predefined_out_create.xls" role="button">
							<span class="ui-button-text">模版文件下载</span>
						</a>
					</td>
					<td colspan="2"></td>
				</tr>
			</table>
			<br />
			<span class="label">备注：</span><textarea  name='sta.memo' loxiaType="input" id="memo"></textarea>
			<div class="buttonlist">
				<button type="button" loxiaType="button" class="confirm" id="save" >导入执行创建</button>
				<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"/></button>&nbsp;&nbsp;
			</div>
		</form>
	</div>
	<iframe id="upload" name="upload" class="hidden"></iframe>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>