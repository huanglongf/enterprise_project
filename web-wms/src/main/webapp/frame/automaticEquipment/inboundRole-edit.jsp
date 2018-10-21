<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title>区域维护</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/automaticEquipment/inboundRole-edit.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
.showborder {
	border: thin;
}
.divFloat{
	float: left;
	margin-right: 10px;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	<div id="div-sta-list">
		<form id="form_query">
			<table>
				
				<tr>
					
					<td class="label">店铺列表:</td>
					<td>
						<select id="channel" loxiaType="select" name="inboundRoleCommand.channelId">
							<option value="">请选择</option>
						</select>
					</td>
					<td class="label" >商品上架类型:</td>
					<td>
						<select id="skuType" loxiaType="select" name="inboundRoleCommand.skuTypeId">
							<option value="">请选择</option>
						</select>
					</td>
					<td class="label" >SKU编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="inboundRoleCommand.skuCode" id="skuCode"  maxlength="80"/>
					</td>
					<td class="label" >库位编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="inboundRoleCommand.locationCode" id="locationCode" maxlength="80"/>
					</td>
					<td class="label" >弹出口列表:</td>
					<td>
						<select id="targetLocation" loxiaType="select" name="inboundRoleCommand.popUpAraeCode">
							<option value="">请选择</option>
						</select>
					</td>
				</tr>
				
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search">查询</button>
			<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
		</div>
		<table id="tbl-inbound-purchase"></table>
		<div id="pager"></div>
		<div class="buttonlist">
				<button loxiaType="button" class="confirm" id="delete">删除选中</button>
		</div>
	</div>
	<div>
		<table> 
		    <tr>
		        <td width="30%">
		            <form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
		                <input type="file" loxiaType="input" id="inboundRoleFile" name="inboundRoleFile"/>
		            </form>
		        </td>
		        <td>
		            <button type="button" loxiaType="button" id="import">规则批量导入</button>
	             <a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=货箱定位规则导入.xls&inputPath=tplt_inbound_role.xls">模板文件下载</a>
		        </td>
		        <td>
		        </td>
			</tr>
		</table>
	
	</div>
	<div id="dialog_create">
		<div class="buttonlist">
			<form id="form_save">
			<table>
				<tr>
					
					<td class="label">店铺列表:</td>
					<td>
						<select id="channelS" loxiaType="select" name="inboundRoleCommand.channelId">
							<option value="">请选择</option>
						</select>
					</td>
					<td class="label" >商品上架类型:</td>
					<td>
						<select id="skuTypeS" loxiaType="select" name="inboundRoleCommand.skuTypeId">
							<option value="">请选择</option>
						</select>
					</td>
					<td class="label" >SKU编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="inboundRoleCommand.skuCode" id="skuCodeS" maxlength="80"/>
					</td>
					<td class="label" >库位编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="inboundRoleCommand.locationCode" id="locationCodeS" maxlength="80"/>
					</td>
					<td class="label" >规则优先级:</td>
					<td>
						<input loxiaType="input" trim="true" name="inboundRoleCommand.lv" id="lvS" maxlength="5"/>
					</td>
					<td class="label" >弹出口列表:</td>
					<td>
						<select id="targetLocationS" loxiaType="select" name="inboundRoleCommand.popUpAraeCode">
							<option value="">请选择</option>
						</select>
					</td>
					<td >
						<button loxiaType="button" class="confirm" id="save1">新增</button>
					</td>
				</tr>
				
				
			</table>
		</form>
		</div>
		</div>
	<div id="dialog_update" >
		<div class="buttonlist">
			<form id="form_update">
			<table>
				
				<tr>
					
					<td class="label">店铺列表:</td>
					<td>
						<select id="channelU" loxiaType="select" name="inboundRoleCommand.channelId">
							<option value="">请选择</option>
						</select>
					</td>
					<td class="label" >商品上架类型:</td>
					<td>
						<select id="skuTypeU" loxiaType="select" name="inboundRoleCommand.skuTypeId">
							<option value="">请选择</option>
						</select>
					</td>
					<td class="label" >SKU编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="inboundRoleCommand.skuCode" id="skuCodeU" maxlength="80"/>
					</td>
					<td></td>
				</tr>
				<tr>	
					
					<td class="label" >库位编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="inboundRoleCommand.locationCode" id="locationCodeU" maxlength="80"/>
					</td>
					<td class="label" >规则优先级:</td>
					<td>
						<input loxiaType="input" trim="true" name="inboundRoleCommand.lv" id="lvU" maxlength="5"/>
							<input type="hidden" name="inboundRoleCommand.id" id="irId"/>
						
						
					</td>
					<td class="label" >弹出口列表:</td>
					<td>
						<select id="targetLocationU" loxiaType="select" name="inboundRoleCommand.popUpAraeCode">
							<option value="">请选择</option>
						</select>
					</td>
					<td>
						<button loxiaType="button" class="confirm" id="update">修改</button>
					</td>
				</tr>
			</table>
		</form>
		</div>
		</div>
		<iframe id="upload" name="upload" class="hidden"></iframe>
</body>
</html>