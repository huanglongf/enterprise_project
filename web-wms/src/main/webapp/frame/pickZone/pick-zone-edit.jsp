<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title><s:text
		name="baseinfo.warehouse.sales.dispatchList.title" /></title>

<script type="text/javascript"
	src="<s:url value='/scripts/frame/pickZone/pick-zone-edit.js' includeParams='none' encode='false'/>"></script>

</head>
<body style="background-color: #f2f2f2;"
	contextpath="<%=request.getContextPath()%>">
	<!-- 这里是页面内容区 -->
	<div id="tabs">
		<ul>
			<li><a href="#tabs_1">拣货区域定义/编辑</a></li>
			<li><a href="#tabs_2">创建/查询/导出</a></li>
		</ul>

		<div id="tabs_1">
			<form method="post" id="query-form">
				<table id="filterTable">
					<tr>
						<td class="label" width="85">拣货区域编码：</td>
						<td width="185"><input loxiaType="input" name="code"
							id="code" trim="true" /></td>
						<td class="label" width="85">拣货区域名称：</td>
						<td width="185"><input loxiaType="input" name="name"
							id="name" trim="true"></td>
						<td class="label" width="85">仓库区域(自动化)：</td>
						<td width="185">
							<select id="whZoonId" loxiaType="select" name="whZoonId">
								<option value="">请选择</option>
							</select>
						</td>
						<td>
							<button type="button" id="btn-add" loxiaType="button"
								class="confirm">添加</button>
						</td>
						<td>
							<button type="button" loxiaType="button" id="btn-query">查询</button>
						</td>
					</tr>
				</table>
			</form>
			<div class="buttonlist"></div>
			<div id="div-waittingList">
				<table id="tbl-waittingList"></table>
				<div id="pager_query"></div>
			</div>
			<div id="zoneNameEdit">
				<table>
					<tr>
						<td class="label" width="85">拣货区域名称：</td>
						<td width="185">
							<input id="zoneNewName" /> <input id="zoneSelectedId" hidden /> <br />
						</td>
					</tr>
					<tr>
						<td class="label" width="85">仓库区域：</td>
						<td width="185">
							<select id="whZoonIdS" loxiaType="select" name="whZoonIdS">
								<option value="">请选择</option>
							</select>
						</td>
					</tr>
				</table>
				<button type="button" loxiaType="button" class="confirm"
					id="btn-name-confirm">确认</button>
				<button type="button" loxiaType="button" id="btn-name-cancel">取消</button>
			</div>
		</div>
		<div id="tabs_2">
			<form method="post" id="query-form1">
				<table id="filterTable1">
					<tr>
						<td class="label" width="20%">库区：</td>
						<td width="25%"><select id="district" loxiaType="select">
								<option value="">请选择</option>
						</select></td>
						<td class="label" width="30%">库位：</td>
						<td width="25%"><input loxiaType="input" name="location"
							id="location" trim="true"></td>
					</tr>
					<tr>
						<td class="label">拣货区域名称：</td>
						<td><select loxiaType="select" id="pickZoneName">
								<option value="">请选择</option>
						</select></td>
						<td class="label">拣货区域编码：</td>
						<td><select id="pickZoneCode" loxiaType="select">
								<option value="">请选择</option>
						</select></td>
					</tr>
				</table>
			</form>
			<br>
				<input id="districtSelected" hidden />
				<input id="locationSelected" hidden />
				<input id="nameSelected" hidden />
				<input id="codeSelected" hidden />
			</br>
			<button type="button" id="btn-query1" loxiaType="button"
				class="confirm">查询</button>
			<button type="button" loxiaType="button" id="reset">
				<s:text name="button.reset"></s:text>
			</button>
			<button type="button" loxiaType="button" id="export">导出库位</button>
			<font class="label" style="color: red"> 建议选择库区后再导出库区的库位信息</font>
			<div class="buttonlist"></div>
			<div id="div-pickZoneInfoList" class="hidden1">
				<table id="tbl-pickZoneInfoList"></table>
				<div id="pager_query1"></div>
			</div>
			<div class="buttonlist">
				<span><b>请选择文件导入创建拣货区域和拣货顺序</b></span>
				<table>
					<tr>
						<td class="label">请选择导入文件:</td>
						<td>
							<form method="post" enctype="multipart/form-data" id="importForm"
								name="importForm" target="upload">
								<input type="file" name="uploadFile" loxiaType="input" id="uploadFile"
									style="width: 200px; margin: 20px;" />
							</form>
						</td>
						<td>
							<button type="button" loxiaType="button" class="confirm"
								id="import">导入创建</button>
						</td>
						<td><a
							class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
							href="<%=request.getContextPath()%>/warehouse/excelDownload.do?fileName=导入模板批量创建拣货区和拣货顺序.xls&inputPath=tplt_import_pick_zone_info.xls"
							role="button"> <span class="ui-button-text">模版文件下载</span>
						</a></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<iframe id="frmExportPickZone" class="hidden"></iframe>
	<iframe id="exportFrame" class="hidden" target="_blank"></iframe>
	<iframe id="upload" name="upload" style="display:none;"></iframe>
	<div id="showErrorDialog">
		<div id="errorMsg" class="ui-loxia-default ui-corner-all" style="margin:10;height:250px;overflow-y:scroll"></div>
	</div>
	<div id="showOverrideDialog">
		<div id="overrideMsg" class="ui-loxia-default ui-corner-all" style="margin:10;height:250px;overflow-y:scroll"></div>
		<button type="button" loxiaType="button" class="confirm" id="btn-override-confirm">继续导入</button>
		<button type="button" loxiaType="button" id="btn-override-cancel">取消导入</button>
	</div>
</body>
</html>