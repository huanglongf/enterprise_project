<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.locationVolume"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/locationVolume.js' includeParams='none' encode='false'/>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
<div id="div1">
		<table id="filterTable">
			<tr>
				<td class="label">
					货号
				</td>
				<td>
					<input loxiaType="input" trim="true" id="supplierCode"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					SKU编码
				</td>
				<td>
					<input loxiaType="input"  trim="true" id="skuCode" />
				</td>
				<td class="label">
					SKU名称
				</td>
				<td>
					 <input loxiaType="input"  trim="true" id="skuName" />
					 <div style="float: left">
							<button type="button" loxiaType="button" class="confirm" id="btnSearchSKU" >查询</button>
					</div>
				</td>
			</tr>
			<tr>
				<td class="label">
					库位类型名称
				</td>
				<td>
					<select loxiaType="select"  style="width: 100px;" id="typeName"></select>
				</td>
				<td class="label">
					库位类型编码
				</td>
				<td>
					 <select loxiaType="select"  style="width: 100px;" id="typeCode"></select>
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
						<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
							<input type="file" loxiaType="input" id="file" name="file" style="width: 200px;"/>
						</form>
					</td>
					<td>
						<button loxiaType="button" class="confirm" id="import">导入商品库位类型容量</button>
					</td>
					<td>
						<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=商品库位类型容量配置.xls&inputPath=warehouse-volume.xls">模板文件下载</a>
					</td>
				</tr>
			</table>
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
					货号
				</td>
				<td>
					<input loxiaType="input" trim="true" id="supplierCode2"/>
				</td>
				<td class="label">
					SKU编码
				</td>
				<td>
					 <input loxiaType="input"  trim="true" id="skuCode2" />
				</td>
			</tr>
			<tr>
				<td class="label">
					库位类型名称
				</td>
				<td>
					 <select loxiaType="select"  style="width: 100px;" id="typeName2"></select>
				</td>
				<td class="label">
					SKU名称
				</td>
				<td>
					 <input loxiaType="input" trim="true"  id="skuName2" />
					 <div style="float: left">
							<button type="button" loxiaType="button" class="confirm" id="btnSearchSKU2" >查询</button>
					</div>
				</td>
			</tr>
			<tr>
				<td class="label">
					库位类型编码
				</td>
				<td>
					 <select loxiaType="select"  style="width: 100px;" id="typeCode2"></select>
				</td>
				<td class="label">
					容量
				</td>
				<td>
					 <input loxiaType="input" trim="true"  id="qty"/>
				</td>
			</tr>
		</table>
     <button type="button" class="confirm" loxiaType="button" id="save">创建</button>
     
	 <iframe id="upload" name="upload" class="hidden"></iframe>
	 
</div>
<jsp:include page="/common/include/include-skuame-query.jsp"></jsp:include> 
</body>
</html>