<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.dispatchList.title"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/warehouse-type-binding-maintain.js' includeParams='none' encode='false'/>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
<div id="div1">
		<table id="filterTable">
			<tr>
				<td class="label">
					库位编码
				</td>
				<td>
					<input loxiaType="input" trim="true" id="locationCode"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					库位类型名称
				</td>
				<td>
					<select loxiaType="select"  style="width: 100px;" id="name"></select>
				</td>
				<td class="label">
					库位类型编码
				</td>
				<td>
					 <select loxiaType="select"  style="width: 100px;" id="code"></select>
				</td>
			</tr>
		</table>
		<div class="buttonlist">
			<button type="button"  class="confirm" loxiaType="button" id="search">查询</button><button  class="confirm" type="button" loxiaType="button" id="reset" >重置</button>
		</div>
		
		<div class="buttonlist">
			<table>
				<tr>
					<td class="label">
						<button type="button"  class="confirm" loxiaType="button">选择文件</button>
					</td>
					<td>
						<form method="post" enctype="multipart/form-data" id="importForm2" name="importForm" target="upload">
							<input type="file" loxiaType="input" id="file" name="file" style="width: 200px;"/>
							<button loxiaType="button" class="confirm" id="import">导入库位类型库位关系</button>
						</form>
					</td>
					<!-- <td>
						<button loxiaType="button" class="confirm" id="import">导入库位类型库位关系</button>
					</td> -->
					<td>
						<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=库位类型绑定导入.xls&inputPath=warehouse-type-bind-inbound-instruction.xls">模板文件下载</a>
					<%--     <a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_vmi_inbound_instruction"></s:text>.xls&inputPath=tplt_import_vmi_inbound-instruction.xls"><s:text name="excel.tplt_import_vmi_inbound_instruction"></s:text></a>
					 --%>
					</td>
				</tr>
			</table>
			<iframe id="upload" name="upload" class="hidden"></iframe>
		</div>
	
	<div id="div-waittingList">
		<table id="tbl-waittingList"></table>
		<div id="pager_query"></div>
	</div>
	<button type="button" class="confirm" loxiaType="button" id="del">删除选中项</button>
	<br/>
	<br/>
		<table id="filterTable">
			<tr>
				<td class="label">
					库位编码
				</td>
				<td>
					<input loxiaType="input" trim="true" id="locationCode2"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					库位类型名称
				</td>
				<td>
					 <select loxiaType="select"  style="width: 100px;" id="name2" onchange="name2(this.id)"></select>
				</td>
				<td class="label">
					库位类型编码
				</td>
				<td>
					 <select loxiaType="select"  style="width: 100px;" id="code2" onchange="code2(this.id)"></select>
				</td>
			</tr>
		</table>
<button type="button" class="confirm" loxiaType="button" id="save">创建</button>
</div>
</body>
</html>