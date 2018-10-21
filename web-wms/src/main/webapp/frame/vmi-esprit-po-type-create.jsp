<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="title.inventory.query"/></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="../scripts/frame/vmi-esprit-po-type-create.js"></script>
<style type="text/css">
.divFloat{
		/** float: left; margin-left: 230px; margin-top: -25px; */
		float: left;
		margin-right: 10px;
	}
</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">

<div id="tabs">
		<div id="tab_1">
			<div id="searchFile">
				<table><tr>
				<td><span class="label"><s:text name="label.warehouse.choose.file"></s:text> </span></td>
				<td>
					<form method="POST" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
						<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
					</form>
				</td>
				<td> 
					<button loxiaType="button" class="confirm" id="import"><s:text name="button.import"></s:text></button>
		            <a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_vmi_ESPRIT_PO_TYPE"></s:text>.xls&inputPath=tplt_esprit_PO_TYPE_export.xls"><s:text name="download.excel.template"></s:text></a>
		        </td>
				</tr></table>
			</div>
		</div>
		
		<div id="tab_2">
			<div class="buttonlist">
				<h3><s:text name="label.esprit.po.type.search"></s:text> </h3>
			</div>
			<div>
				<form id="query_form" name="query_form">
				<table width="65%">
				<tr>
					<td class="label" width="15%">
						<s:text name="label.warehouse.pl.createtime"/>
					</td>
					<td width="15%">
						<input loxiaType="date" name="ptCommand.startTime1" showTime="true"/>
					</td>
					<td class="label" width="15%">
						<s:text name="label.warehouse.inventory.check.to"/>
					</td>
					<td width="15%">
						<input loxiaType="date" name="ptCommand.endTime1" showTime="true"/>
					</td>
						
				</tr>
				<tr>
					<td class="label">
						<s:text name="PO单号"/>
					</td>
					<td>
						<input name="ptCommand.po" loxiaType="input" trim="true"/>
					</td>
					<td class="label" width="15%">
						<s:text name="PO类型"/>
					</td>
					<td width="15%">
						<select loxiaType="select" id="intStatus" name="ptCommand.typeName" >
							<option value="">请选择</option>
							<option value="1">直送</option>
							<option value="3">非直送</option>
							<option value="5">进口</option>
							<option value="7">特许证产品</option>
							<option value="9">大连自提</option>
						</select>
					</td>		
					
				</tr>
			</table>
			</form>
			<div class="buttonlist">
			<button id="query" loxiaType="button" class="confirm" s><s:text name="button.search"/></button>&nbsp;&nbsp;
			<button id="reset" loxiaType="button" class=""><s:text name="button.reset"/></button>
			
		</div>
			<table id="tbl_poType"></table>
			<div id="pager"></div>
		 	</div>
		</div>
		<iframe id="upload" name="upload" class="hidden"></iframe>
	</div>
</body>
</html>