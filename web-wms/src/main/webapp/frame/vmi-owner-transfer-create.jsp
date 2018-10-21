<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="title.inventory.query"/></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="../scripts/frame/vmi-owner-transfer-create.js"></script>
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
		<ul>
			<li>
				<a href="#tab_2">导入</a>
			</li>
		</ul>
		<div id="tab_2">
			<table>
				<tbody>
					<tr>
						<td class="label" width="15%">转出店铺:</td>
						<td class="label" width="20%">
							<select id="ownerout" name="" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</td>
						<td class="label" width="15%">转入店铺:</td>
						<td class="label" width="20%">
							<select id="ownerin" name="" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label">库存状态:</td>
						<td><s:select id="invstatus" name="invstatus" list="invstatus" listValue="name" listKey="id" headerValue="请选择" headerKey=""></s:select>  </td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
			
			
			<p>&nbsp;</p>
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
		            <a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_vmi_owner_transfer"></s:text>.xls&inputPath=tplt_import_vmi_owner_transfer.xls"><s:text name="download.excel.template"></s:text></a>
		        </td>
				</tr></table>
			</div>
			<iframe id="upload" name="upload" class="hidden"></iframe>
			<div class="clear"></div>
		</div>
	</div>
</body>
</html>