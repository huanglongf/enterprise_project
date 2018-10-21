
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/meta.jsp"%>
		<title>快递维护</title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/express-maintain.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
		<style type="text/css">
			.tips{
				width:100%; height: 50%; text-align:center; color:red; font-size: 40px; font-weight: bold;
			}
		</style>
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id = "div1">
		<form id="form_query">
		<table>
			<tr>
				<td class="label">物流商名称：</td>
				<td width="160px"><input loxiaType="input" name="lpName"  id="lpName"/></td>
				<td class="label">外部平台对接码：</td>
				<td width="160px"><input loxiaType="input" name="lpCode"  id="lpCode"/></td>
				<td class="label">是否可用：</td>
				<td width="160px">
					<select name="status" id="selTrans1" loxiaType="select">
						<option value="">--请选择--</option>
						<option value="1">可用</option>
						<option value="2">禁用</option>
					</select>
				</td>
				<td class="label">是否COD：</td>
				<td width="160px">
					<select name="isCod" id="selTrans2" loxiaType="select">
						<option value="">--请选择--</option>
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</td>
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
	
	<div id="tmt-list">
		<table id="tbl-tmt-list"></table>
		<button type="button" loxiaType="button" class="confirm" id="newTrans">
			<s:text name="button.add"></s:text>
		</button>
	</div>
	</div>
	<div id = "div2">
		<form id="form_info">
		<table>
			<tr>
				<td class="label" width="10%">物流商名称：</td>
				<td width="20%"><input loxiaType="input" trim="true" id="name" name="ma.name" mandatory="true"/></td>
				<td class="label" width="10%">内部平台对接码：</td>
				<td width="20%"><input loxiaType="input" trim="true" id="expCode" name="ma.expCode"/></td>
				<td class="label" width="10%">外部平台对接码：</td>
				<td width="20%"><input loxiaType="input" trim="true" id="platformCode" name="ma.platformCode"/></td>
			</tr>
			<tr>
				<td class="label" width="10%">物流商全称：</td>
				<td width="20%"><input loxiaType="input" trim="true" id="fullName" name="ma.fullName" /></td>
				<td class="label" width="10%">物流商编码：</td>
				<td width="20%"><input loxiaType="input" trim="true" id="code" name="ma.code"  mandatory="true" /></td>
				<td class="label" width="10%">是否可用：</td>
				<td width="20%">
					<select loxiaType="select" name="ma.statusId" id="selTrans3" mandatory="true">
						<option value="1">可用</option>
						<option value="0">禁用</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="label" width="10%">K3编码：</td>
				<td width="20%"><input loxiaType="input" trim="true" id="k3Code" name="ma.k3Code" /></td>
				<td class="label" width="10%">是否COD：</td>
				<td width="20%">
					<select loxiaType="select" name="ma.isSupportCod" id="selTrans4" mandatory="true">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</td>
				<td class="label" width="10%">COD最大金额：</td>
				<td width="20%"><input loxiaType="input" id="codMaxAmt" name="ma.codMaxAmt"  maxlength="10"  onkeyup="value=value.replace(/[^\d.]/g,'')" trim="true" width="120px"></input></td>	
			</tr>	
			<tr>
				<td class="label" width="10%">电子面单模板：</td>
				<td width="20%"><input loxiaType="input" trim="true" id="jasperOnLine" name="ma.jasperOnLine"/></td>
				<td class="label" width="10%">普通面单模板：</td>
				<td width="20%"><input loxiaType="input" trim="true" id="jasperNormal" name="ma.jasperNormal"/></td>
				<td class="label" width="10%">是否分区：</td>
				<td width="20%">
					<select loxiaType="select" name="ma.isReg" id="selTrans5" mandatory="true">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="label" width="10%">是否后置：</td>
				<td width="20%">
					<select loxiaType="select" name="ma.isAfter" id="selTrans6" mandatory="true">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</td>
			</tr>
			</table>
		</form>
		<table>
			<tr>
				<button type="button" loxiaType="button" class="confirm" id="save">
					<s:text name="button.save"></s:text>
				</button>
				<button type="button" loxiaType="button" id="back">
					<s:text name="button.back"></s:text>
				</button>
			</tr>
		</table>
		<div id="transport-service">
			<table id="tbl-transport-service"></table>
			<button type="button" loxiaType="button" class="confirm" id="addSub">
					<s:text name="button.add"></s:text>
			</button>
		</div>
		
		<div id="dialog_addTrans">
			<form id="form_info2">
				<table>
					<tr>
						<td class="label" width="10%">名称：</td>
						<td width="20%"><input loxiaType="input" trim="true" id = "serviceName" name="tran.name" mandatory="true"/></td>
						<td class="label" width="10%">服务类型：</td>
						<td width="20%">
							<select loxiaType="select" name="tran.serviceType" id="selTrans7" mandatory="true">
								<option value="1">普通</option>
								<option value="4">航空</option>
								<option value="7">电商特惠</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label" width="10%">时效类型：</td>
						<td width="20%">
							<select loxiaType="select" name="tran.timeTypes" id="selTrans8" mandatory="true">
								<option value="1">普通</option>
								<option value="3">及时达</option>
								<option value="5">当日</option>
								<option value="6">次日</option>
								<option value="7">次晨</option>
								<option value="8">云仓专配次日</option>
								<option value="9">云仓专配隔日</option>
							</select>
						</td>
						<td class="label" width="10%">状态：</td>
						<td width="20%">
							<select loxiaType="select" name="tran.statuss" id="selTrans9" mandatory="true">
								<option value="1">可用</option>
								<option value="2">禁用</option>
							</select>
						</td>
					</tr>
				</table>
			</form>
			<br>
			<table id="tbl-transport-service-area"></table>
			<table>
				<tr>
					<td ><b>选择文件:</b></td>
					<td  align="center">
						<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
							<input type="file" name="file" loxiaType="input" id="file" style="width:150px"/>
						</form>
					</td>
					<td>
		           		<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=配送范围导入.xls&inputPath=tplt_import_tracs_area.xls">模板文件下载</a>
			        </td>
				</tr>
			</table>
			<br>
			<table>
				<tr>
					<button type="button" loxiaType="button" class="confirm" id="saveSub">
						<s:text name="button.save"></s:text>
					</button>
					<button type="button" loxiaType="button" id="closeSub">
						<s:text name="button.back"></s:text>
					</button>
				</tr>
			</table>
		</div>
	</div>
	<iframe id="upload" name="upload" class="hidden"></iframe>
	</body>
</html>