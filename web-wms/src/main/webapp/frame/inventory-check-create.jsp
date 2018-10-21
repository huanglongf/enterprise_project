<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/inventory-check-create.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.inventory.check.create"/></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<input type="hidden" value="" id="invcheckid"/>
	<div id="divCreate">
		<div id="modes">
			<ul>
				<li><a href="#modes-1"><s:text name="label.warehouse.inventoryCheck.byLoc"></s:text> </a></li>
				<li><a href="#modes-2"><s:text name="label.warehouse.inventoryCheck.byOwner"></s:text> </a></li>
				<li><a href="#modes-3"><s:text name="label.warehouse.inventoryCheck.byImport"></s:text> </a></li>
			</ul>
			<div id="modes-1">
				<table id="tbl_list"></table>
					<table width="100%">
						<tr>
							<td><s:text name="label.warehouse.location.comment" /></td>
						</tr>
						<tr>
							<td>
								<textarea id="txtRmk" name="remark" loxiaType="input" rows="3"></textarea>
							</td>
						</tr>
					</table>
				<div class="buttonlist">
					<button id="create" loxiaType="button"  class="confirm"><s:text name="button.warehouse.create" /></button>
					<button id="delSelect" loxiaType="button"><s:text name="button.warehouse.selected.delete" /></button>
				</div>
				
				<div id="tabs">
					<ul>
						<li><a href="#m2_tabs-1"><s:text name="label.warehouse.inv.check.district.list" /></a></li>
						<li><a href="#m2_tabs-2"><s:text name="label.warehouse.inv.check.location.list" /></a></li>
					</ul>
					<div id="m2_tabs-1">
						<table id="tbl_dit_list"></table>
						<div class="buttonlist">
							<button id="addDecTo" loxiaType="button" class="confirm"><s:text name="button.warehouse.inv.check.add" /></button>
						</div>
					</div>
					
					<div id="m2_tabs-2">
						<form id="query_loc_form">
							<table>
								<tr>
									<td class="label"><s:text name="label.warehouse.whlocation.code" /></td>
									<td class="label">
										<input loxiaType="input" trim="true" name="locationcmd.code"/>
									</td>
									<td class="label"><s:text name="label.warehouse.location.code" /></td>
									<td class="label">
										 <input loxiaType="input" trim="true" name="locationcmd.districtCode"/>
									</td>
									<td class="label"><s:text name="label.warehouse.location.name" /></td>
									<td class="label">
										<input loxiaType="input" trim="true" name="locationcmd.districtName"/>
									</td>
								</tr>
								<tr>
									<td class="label"><s:text name="label.warehouse.inpurchase.shop"></s:text> </td>
									<td class="label">
										<input loxiaType="input" trim="true" name="locationcmd.owner"/>
									</td>
									<td class="label"><s:text name="label.warehouse.inpurchase.brand"></s:text> </td>
									<td class="label">
										 <input loxiaType="input" trim="true" name="locationcmd.brand"/>
									</td>
									<td></td>
									<td></td>
								</tr>
							</table>
						</form>
						<div class="buttonlist">
							<button id="query" loxiaType="button" class="confirm"><s:text name="button.search" /></button>
						</div>
						<table id="tbl_loc_list"></table>
						<div id="pager"></div>
						<div class="buttonlist">
							<button id="addLocTo" loxiaType="button" class="confirm"><s:text name="button.warehouse.inv.check.add" /></button>
						</div>
					</div>
				</div>
			</div>
			<div id="modes-2">
				<table id="tbl_ob_list"></table>
				<table width="100%">
					<tr>
						<td><s:text name="label.warehouse.location.comment" /></td>
					</tr>
					<tr>
						<td>
							<textarea id="txtRmk1" name="remark" loxiaType="input" rows="3"></textarea>
						</td>
					</tr>
				</table>
				<div class="buttonlist">
					<button id="create1" loxiaType="button"  class="confirm"><s:text name="button.warehouse.create" /></button>
					<button id="delSelect1" loxiaType="button"><s:text name="button.warehouse.selected.delete" /></button>
				</div>
				<div id="m2_tabs">
					<ul>
						<li><a href="#m2_tabs-1"><s:text name="label.warehouse.inpurchase.shop"></s:text> </a></li>
						<li><a href="#m2_tabs-2"><s:text name="label.warehouse.inpurchase.brand"></s:text> </a></li>
					</ul>
					<div id="m2_tabs-1">
						<table id="tbl_owner_list"></table>
						<div class="buttonlist">
							<button id="addObTo1" loxiaType="button" class="confirm"><s:text name="button.warehouse.inv.check.add" /></button>
						</div>
					</div>
					<div id="m2_tabs-2">
						<table id="tbl_brand_list"></table>
						<div class="buttonlist">
							<button id="addObTo2" loxiaType="button" class="confirm"><s:text name="button.warehouse.inv.check.add" /></button>
						</div>
					</div>
				</div>
			</div>
			
			<div id="modes-3">
				 <table width="90%">
					<tr>
						<td class="label">选择导入的Excel文件:</td>
						<td>
							<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
								<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
							</form>
						</td>
					 </tr>
					  <tr>
					  	<td colspan="2"> 
							<div class="buttonlist">
								<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_inventory_check_location"></s:text>.xls&inputPath=tplt_import_inventory_check_location.xls"><s:text name="download.excel.template"></s:text></a>
								<button loxiaType="button" class="confirm" id="import"><s:text name="button.import"></s:text></button>
							</div>
						</td>
					  </tr>
					   <tr>
							<td colspan="2">
								<span class="label">备注：</span>
							</td>
					  </tr>
					  <tr>
							<td colspan="2">
								<textarea id="importRmk" name="remark" loxiaType="input" rows="3"></textarea>
							</td>
					  </tr>
				</table>
				<iframe id="upload" name="upload" class="hidden"></iframe>
			</div>
			
		</div>	
	</div>

	<div id="divExport" class="hidden">
		<table>
			<tr>
				<td class="label" width="25%">
					<s:text name="label.warehouse.inv.check.code" />
				</td>
				<td width="25%" id="invCheckCode">
				 
				</td>
				<td class="label" width="25%">
					<s:text name="label.warehouse.pl.createtime" />
				</td>
				<td width="25%" id="createTime">
					 
				</td>
			</tr>
			<tr>
				<td class="label">
					<s:text name="label.wahrhouse.sta.creater" />
				</td>
				<td id="creator">
					
				</td>
				<td class="label">
					<s:text name="label.warehouse.pl.status" />
				</td>
				<td id="status">
					
				</td>
			</tr>
		</table>
		<table id="tbl_detial"></table>
		<div id="pager9"></div>
			<table width="100%">
				<tr>
					<td><s:text name="label.warehouse.location.comment" /></td>
				</tr>
				<tr>
					<td id="remark">						 
					</td>
				</tr>
			</table>
		<div class="buttonlist">
			<button id="export" loxiaType="button" class="confirm"><s:text name="button.export.inv.check.list" /></button>
		</div>
	</div>
<iframe id="exportInventoryCheck" class="hidden"></iframe>
</body>
</html>