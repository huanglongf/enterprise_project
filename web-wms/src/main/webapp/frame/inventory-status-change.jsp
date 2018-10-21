<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><s:text name="label.wahrhouse.inner.title"></s:text></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/inventory-status-change.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<iframe id="upload" name="upload" class="hidden"></iframe>
	<div id="tabs">
		<ul>
		<!-- 
			<li class="hidden"><a href="#tabs-1"><s:text name="label.tabs.create"></s:text> </a></li> -->
			<li><a href="#tabs-2"><s:text name="label.tabs.import"></s:text> </a></li>
		</ul>
		<!-- 
		<div id="tabs-1" class="hidden">
			<div id="div-sta-detail" >
				<form id="form_query" name="form_query">
					<table  width="100%">
						<tr>
							<td class="label" width="8%"><s:text name="label.warehouse.pl.createtime"></s:text></td>
							<td width="12%">
								<input loxiaType="date" name="staCom.createTime1" showTime="true"/>
							</td>
							<td class="label" width="5%"><s:text name="label.warehouse.pl.to"></s:text></td>
							<td  width="12%">
								<input loxiaType="date" name="staCom.finishTime1" showTime="true"/>
							</td>
							<td class="label" width="10%"><s:text name="label.warehouse.pl.sta"></s:text></td>
							<td width="15%">
								<input name="staCom.code" loxiaType="input" trim="true"/>
							</td>
							<td class="label" width="10%"><s:text name="label.wahrhouse.sta.creater"></s:text></td>
							<td width="15%">
								<input name="staCom.creater" loxiaType="input" trim="true"/>
							</td>
						</tr>
					</table>
				</form>			
				<div class="buttonlist">
					<button id="query" loxiaType="button" class="confirm"><s:text name="button.search"/></button>&nbsp;&nbsp;
					<button id="reset" loxiaType="button" class=""><s:text name="button.reset"/></button>
				</div>
				<table id="tbl_transit_list"></table>
				<br/>
				<div class="buttonlist"></div>
				<div>
					<font class="label"><s:text name="label.wahrhouse.inner.create"></s:text></font>
				</div>
				<div id="text"></div>
				<br />
				<div >
					<table width="100%">
						<tr>
							<span class="label">选择店铺：</span><select id="shopS" name="" loxiaType="select" style="width: 150px">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</tr>
						<tr>
							<td class="label"><s:text name="label.wahrhouse.inner.one"></s:text></td>
							<td class="label"><s:text name="label.warehouse.whlocation.code"></s:text> </td>
							<td  style="width: 180px"><input loxiaType="input" trim="true" checkmaster="findSku" aclist="<%=request.getContextPath() %>/invStatusChangeFindAvailLocation.json" name="locationCode" id="locationCode" mandatory="true" /></td>
							<td class="label"><s:text name="label.wahrhouse.inner.sku"></s:text> </td>
							<td>
								<select style="width: 150px" loxiaType="select" id="selectSku">
									<option><s:text name="label.wahrhouse.sta.select"></s:text></option>
								</select>
								<div id="selectLocationSkuNum" class="hidden"></div>
							</td>
							<td class="label"><s:text name="label.warehouse.inventory.state"></s:text> </td>
							<td>
								<select style="width: 150px" loxiaType="select" id="selectStatus">
									<option><s:text name="label.wahrhouse.sta.select"></s:text> </option>
								</select>
								<div id="statusSkuNum" class="hidden"></div>
							</td>
							<td class="label"><s:text name="label.warehouse.inpurchase.count"></s:text> </td>
							<td style="width: 180px"><input loxiaType="number" min="1" trim="true" id="skuNum" /></td>
							<td><button loxiaType="button" class="confirm" id="searchOne"><s:text name="button.wahrhouse.inner.add"></s:text></button></td>
						</tr>
						<tr>
							<td class="label"><s:text name="label.wahrhouse.inner.two"></s:text> </td>
							<td class="label"><s:text name="label.warehouse.inpurchase.skucode"></s:text> </td>
							<td><input loxiaType="input" trim="true" mandatory="true" checkmaster="findLocationCode" aclist="<%=request.getContextPath() %>/invStatusChangeFindAvailSku.json" name="skuCode" id="skuCode" /></td>
							<td class="label"><s:text name="label.wahrhouse.inner.loc"></s:text></td>
							<td>
								<select  style="width: 150px" loxiaType="select" id="selectLocation">
									<option><s:text name="label.wahrhouse.sta.select"></s:text> </option>
								</select>
								<div id="selectLocationSkuNum1" class="hidden"></div>
							</td>
							<td class="label"><s:text name="label.warehouse.inventory.state"></s:text> </td>
							<td>
								<select  style="width: 150px" loxiaType="select" id="selectStatus1">
									<option><s:text name="label.wahrhouse.sta.select"></s:text> </option>
								</select>
								<div id="statusSkuNum1" class="hidden"></div>
							</td>
							<td class="label"><s:text name="label.warehouse.inpurchase.count"></s:text> </td>
							<td><input trim="true" loxiaType="number" min="1" id="skuNum1" /></td>
							<td><button loxiaType="button" class="confirm" id="searchTwo"><s:text name="button.wahrhouse.inner.add"></s:text></button></td>
						</tr>
					</table>
				</div>
				<br />
				<table id="tbl_temp_list"></table>
				<br/>
				<table width="100%">
					<tr>
						<td align="left" style="text-align: left;"><font class="label" ><s:text name="label.warehouse.location.comment"></s:text></font></td>
					</tr>
					<tr>
						<td>
							<textarea id="txtRmk" name="remork" loxiaType="input" rows="3"></textarea>
						</td>
					</tr>
				</table>
				<div class="buttonlist">
					<button loxiaType="button" id="turn_out" class="confirm"><s:text name="button.wahrhouse.inner.create"></s:text> </button>
					<button loxiaType="button" id="deleteSku"><s:text name="button.wahrhouse.inner.delete"></s:text> </button>
				</div>
			</div>
			
			<div id="div-sta" class="hidden" >
				<input type="hidden" id="input_staId" />
				<table width="80%">
					<tr>
						<td class="label" width="13%"><s:text name="label.warehouse.inpurchase.createTime"></s:text> </td>
						<td width="20%"><input loxiaType="input" id="input_createTime" readonly style="background-color: #f2f2f2; border: 0"
							 /></td>
						<td class="label" width="13%"><s:text name="label.warehouse.inpurchase.refcode"></s:text> </td>
						<td width="20%"><input loxiaType="input" id="input_staCode" readonly style="background-color: #f2f2f2; border: 0"  /></td>
						<td class="label" width="13%"><s:text name="label.wahrhouse.sta.creater"></s:text> </td>
						<td width="20%"><input loxiaType="input" id="input_creater"  readonly style="background-color: #f2f2f2; border: 0" /></td>
					</tr>
					<tr>
						<td class="label"><s:text name="label.warehouse.location.comment"></s:text> </td>
						<td colspan="5" id="input_memo"></td>
					</tr>
				</table>
				<table border="black" cellspacing=0 cellpadding=0 id="stvlineListtable">
					<thead>
						<tr id="stvlineList">
							<td class="label"><s:text name="label.warehouse.pl.sta.sku.barcode"></s:text> </td>
							<td class="label"><s:text name="label.warehouse.inpurchase.jmcode"></s:text> </td>
							<td class="label"><s:text name="label.warehouse.inpurchase.extpro"></s:text> </td>
							<td class="label"><s:text name="label.wahrhouse.inner.batch"></s:text> </td>
							<td class="label"><s:text name="label.warehouse.inventory.state"></s:text> </td>
							<td class="label"><s:text name="label.warehouse.inpurchase.shop"></s:text> </td>
							<td class="label"><s:text name="label.warehouse.inpurchase.name"></s:text> </td>
							<td class="label"><s:text name="label.warehouse.inpurchase.supplierCode"></s:text> </td>
							<td class="label"><s:text name="label.wahrhouse.inner.upnum"></s:text> </td>
							<td class="label"><s:text name="label.warehouse.inpurchase.ShelvesSituation"></s:text> </td>
						</tr>
					</thead>
				</table>
				<div class="buttonlist">
					<button type="button" loxiaType="button" id="executeInventory" class="confirm"><s:text name="button.wahrhouse.inner.execute"></s:text> </button>
					<button type="button" loxiaType="button" id="cancel" class="confirm" id="cancelStv"><s:text name="button.wahrhouse.inner.cancel"></s:text> </button>
					<button type="button" loxiaType="button" id="back" ><s:text name="button.back"></s:text> </button>
				</div>
			</div>
		</div>-->
		<div id="tabs-2">
			<div id="tabs-2-import">
				<form id="form_query1" name="form_query1">
					<table width="80%">
						<tr>
							<td width="10%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text></td>
							<td width="15%">
								<input loxiaType="date" name="staCom.createTime1" showTime="true"/>
							</td>
							<td width="8%" class="label"><s:text name="label.warehouse.pl.to"></s:text></td>
							<td width="15%">
								<input loxiaType="date" name="staCom.finishTime1" showTime="true"/>
							</td>
							<td width="10%" class="label" width="15%"><s:text name="label.warehouse.pl.sta"></s:text></td>
							<td width="15%">
								<input name="staCom.code" loxiaType="input" trim="true"/>
							</td>
							<td width="10%" class="label"><s:text name="label.wahrhouse.sta.creater"></s:text></td>
							<td width="15%">
								<input name="staCom.creater" loxiaType="input" trim="true"/>
							</td>
						</tr>
					</table>
				</form>			
				<div class="buttonlist">
					<button id="query1" loxiaType="button" class="confirm"><s:text name="button.search"/></button>&nbsp;&nbsp;
					<button id="reset1" loxiaType="button" class=""><s:text name="button.reset"/></button>
				</div>
				<table id="tbl_transit_list1"></table>
				<div class="buttonlist"></div>
				<form id="inputFromId" method="post" enctype="multipart/form-data" name="importForm" target="upload" >
					<span class="label">选择店铺：</span>
					<select id="shopS2" name="shopId" loxiaType="select" style="width: 150px">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
					</select><br/><br/>
					<span class="label"><s:text name="label.warehouse.inpurchase.selectFile"></s:text></span>
					<input style="width:150px" loxiaType="input" type="file" accept="application/msexcel" name="file" id="tnFile" />　
					<a href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_inventory_status_change"></s:text>.xls&inputPath=tplt_import_inventory_status_change.xls" loxiaType="button"><s:text name="label.warehouse.inpurchase.export"></s:text></a>
					<table width="100%">
						<tr>
							<td align="left" style="text-align: left;"><font class="label" ><s:text name="label.warehouse.location.comment"></s:text></font></td>
						</tr>
						<tr>
							<td>
								<textarea id="txtRmk1" name="remork" loxiaType="input" rows="3"></textarea>
							</td>
						</tr>
					</table>
				</form>
				<div class="buttonlist">
					<button loxiaType="button" class="confirm" id="inputSumit"><s:text name="label.warehouse.inpurchase.import"></s:text></button>　
				</div>
			</div>
		</div>
		<div id="tabs-2-detial" class="hidden">
			<input type="hidden" id="imp_staId" />
			<table>
				<tr>
					<td class="label"><s:text name="label.warehouse.inpurchase.createTime"></s:text></td>
					<td id="imp_createTime"></td>
					<td class="label"><s:text name="label.warehouse.inpurchase.refcode"></s:text> </td>
					<td id="imp_staCode"></td>
					<td class="label"><s:text name="label.wahrhouse.sta.creater"></s:text> </td>
					<td id="imp_creater"></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.location.comment"></s:text> </td>
					<td colspan="5" id="imp_memo"></td>
				</tr>
			</table>
			<br/>
			<div id="tabs_2_d">
				<!-- <ul>
					<li><a href="#tabs_2_d_1"><s:text name="label.warehouse.plan.moveout"></s:text> </a></li>
					<li><a href="#tabs_2_d_2"><s:text name="label.warehouse.plan.movein"></s:text></a></li>
				</ul> 
				-->
				<div id="tabs_2_d_1">
					<table id="tbl_move_out"></table>
				</div>
				<div id="tabs_2_d_2">
					<table id="tbl_move_in"></table>
				</div>
			</div>
			<div class="buttonlist">
				<button type="button" loxiaType="button" id="executeInventory1" class="confirm"><s:text name="button.wahrhouse.inner.execute"></s:text> </button>
				<button type="button" loxiaType="button" id="cancel1" class="confirm" id="cancelStv"><s:text name="button.wahrhouse.inner.cancel"></s:text> </button>
				<button type="button" loxiaType="button" id="back1" ><s:text name="button.back"></s:text> </button>
			</div>
		</div>
	</div>

	<div id="message_id"></div>
</body>
</html>