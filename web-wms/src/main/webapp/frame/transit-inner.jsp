<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><s:text name="label.wahrhouse.inner.title"></s:text></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/transit-inner.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<iframe id="upload" name="upload" class="hidden"></iframe>
	<div id="tabs">
		<ul>
			<li><a href="#tabs-1"><s:text name="label.tabs.create"></s:text> </a></li>
			<li><a href="#tabs-2"  id="exp_li"><s:text name="label.tabs.import"></s:text> </a></li>
		</ul>
		<div id="tabs-1">
			<div id="div-sta-detail" >
				<form id="form_query" name="form_query">
					<table width="100%">
						<tr>
							<td class="label" width="8%"><s:text name="label.warehouse.pl.createtime"></s:text></td>
							<td width="12%">
								<input loxiaType="date" name="staCom.createTime1" showTime="true"/>
							</td>
							<td class="label" width="5%" align="center"><s:text name="label.warehouse.pl.to"></s:text></td>
							<td width="12%">
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
				<div id="pager2"></div>
				<br/>
				<div class="buttonlist"></div>
				<div>
					<font class="label">创建库内移动作业单</font>
				</div>
				<div id="text"></div>
				<br />
				<div >
					<table width="100%">
						<tr>
							<td class="label" width="50px"><s:text name="label.warehouse.inpurchase.shop"></s:text> </td>
							<td colspan="8">
								<select id="shopS" name="" loxiaType="select" style="width: 150px">
									<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
								</select>
								<%--
								 <s:select id="shopS" style="width: 150px" headerKey="" headerValue="" list="shops" loxiaType="select" listKey="id" listValue="innerShopCode" ></s:select> 
								--%>
							</td>
						</tr>
						<tr>
							<td class="label"><s:text name="label.wahrhouse.inner.one"></s:text></td>
							<td class="label"><s:text name="label.warehouse.whlocation.code"></s:text> </td>
							<td  style="width: 180px"><input loxiaType="input" trim="true" checkmaster="findSku" aclist="<%=request.getContextPath() %>/findAvailLocation.json" name="locationCode" id="locationCode" mandatory="true" /></td>
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
							<td><input loxiaType="input" trim="true" mandatory="true" checkmaster="findLocationCode" aclist="<%=request.getContextPath() %>/findAvailSku.json" name="skuCode" id="skuCode" /></td>
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
				<table>
					<tr>
						<td class="label"><s:text name="label.warehouse.inpurchase.createTime"></s:text> </td>
						<td><input loxiaType="input" id="input_createTime" readonly
							style="background-color: #f2f2f2; border: 0"
							 /></td>
						<td class="label"><s:text name="label.warehouse.inpurchase.refcode"></s:text> </td>
						<td><input loxiaType="input" id="input_staCode" readonly
							style="background-color: #f2f2f2; border: 0"  /></td>
						<td class="label"><s:text name="label.wahrhouse.sta.creater"></s:text> </td>
						<td><input loxiaType="input" id="input_creater"  readonly
							style="background-color: #f2f2f2; border: 0" /></td>
					</tr>
					<tr>
						<td class="label"><s:text name="label.warehouse.transactiontype"></s:text> </td>
						<td><input loxiaType="input" id="input_staType" readonly
							style="background-color: #f2f2f2; border: 0" value="XXXXX" /></td>
						<td class="label"><s:text name="label.wahrhouse.inner.num"></s:text> </td>
						<td><input loxiaType="input" id="input_totalSkuQty" readonly
							style="background-color: #f2f2f2; border: 0" value="123" /></td>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td class="label"><s:text name="label.warehouse.inpurchase.status"></s:text> </td>
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
		</div>
		<div id="tabs-2">
			<div id="div-sta-exp">
				<form id="form_query_exp" name="form_query_exp">
					<table>
						<tr>
							<td class="label"><s:text name="label.warehouse.pl.createtime"></s:text></td>
							<td >
								<input loxiaType="date" name="staCom.createTime1" showTime="true"/>
							</td>
							<td class="label"><s:text name="label.warehouse.pl.to"></s:text></td>
							<td >
								<input loxiaType="date" name="staCom.finishTime1" showTime="true"/>
							</td>
							<td class="label" width="15%"><s:text name="label.warehouse.pl.sta"></s:text></td>
							<td width="15%">
								<input name="staCom.code" loxiaType="input" trim="true"/>
							</td>
							<td class="label"><s:text name="label.wahrhouse.sta.creater"></s:text></td>
							<td>
								<input name="staCom.creater" loxiaType="input" trim="true"/>
							</td>
							<td class="label" width="10%">导入状态</td>
							<td width="15%">
								<select loxiaType="select" id="importStatus" name="staCom.fileStatus" >
									<option value="">--请选择--</option>
									<option value="1">未执行</option>
									<option value="2">执行成功 </option>
									<option value="3">执行失败 </option>
							</select>
							</td>
						</tr>
					</table>
				</form>			
				<div class="buttonlist">
					<button id="query_exp" loxiaType="button" class="confirm"><s:text name="button.search"/></button>&nbsp;&nbsp;
					<button id="reset_exp" loxiaType="button"><s:text name="button.reset"/></button>
				</div>
				<table id="tbl_transit_list_exp"></table>
				<div id="pager3"></div>
				<br/>
				<div class="buttonlist"></div>
				<form id="inputFromId" method="post" enctype="multipart/form-data" name="importForm" target="upload" >
					<s:text name="label.warehouse.inpurchase.selectFile"></s:text>
					<input type="file" accept="application/msexcel" name="file" id="tnFile" />　
					<a href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_transit_inner"></s:text>.xls&inputPath=tplt_import_transit_inner.xls" loxiaType="button"><s:text name="label.warehouse.inpurchase.export"></s:text></a>
					<table width="100%">
						<tr>
							<td align="left" style="text-align: left;"><font class="label" ><s:text name="label.warehouse.location.comment"></s:text></font></td>
						</tr>
						<tr>
							<td>
								<textarea id="txtRmk1" name="remork" loxiaType="input" rows="3"></textarea>
							</td>
						</tr>
						<tr>
							<td>
								<textarea id="fileName" name="fileName" loxiaType="input" rows="3" hidden="true"></textarea>
							</td>
						</tr>
					</table>
					<table>
						<tr>
							<td align="left" style="text-align: left;"><font class="label" >导入方式:</font></td>
							<td width="100px">
								<select loxiaType="select" id="selectFileType" name="selectFileType" >
									<option value="SS">实时导入 </option>
									<option value="YB">异步导入</option>
							</select></td>
							<td><p id = "fileRemork"><font color="red"> (异步导入：适用于大文件，导入后10分钟后可查看导入结果,如文件特别大，请耐心等待)</font></p></td>
						</tr>
					</table>
				</form>
				
				<div class="buttonlist">
					<table>
						<tr>
							<td>
								<button loxiaType="button" class="confirm" id="inputSumit"><s:text name="label.warehouse.inpurchase.import"></s:text></button>
							</td>
							<td>
								
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div id="exp-detail" class="hidden">
				<form id="exp-info-exp">
				<input type="hidden" id="input_staId_exp" />
				<table>
					<tr>
						<td class="label"><s:text name="label.warehouse.inpurchase.createTime"></s:text> </td>
						<td><input loxiaType="input" id="input_createTime_exp" readonly
							style="background-color: #f2f2f2; border: 0"
							 /></td>
						<td class="label"><s:text name="label.warehouse.inpurchase.refcode"></s:text> </td>
						<td><input loxiaType="input" id="input_staCode_exp" readonly
							style="background-color: #f2f2f2; border: 0" /></td>
						<td class="label"><s:text name="label.wahrhouse.sta.creater"></s:text> </td>
						<td><input loxiaType="input" id="input_creater_exp"  readonly
							style="background-color: #f2f2f2; border: 0" /></td>
					</tr>
					<tr>
						<td class="label"><s:text name="label.warehouse.transactiontype"></s:text> </td>
						<td><input loxiaType="input" id="input_staType_exp" readonly
							style="background-color: #f2f2f2; border: 0"  /></td>
						<td class="label"><s:text name="label.wahrhouse.inner.num"></s:text> </td>
						<td><input loxiaType="input" id="input_totalSkuQty_exp" readonly
							style="background-color: #f2f2f2; border: 0" /></td>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td class="label"><s:text name="label.warehouse.inpurchase.status"></s:text> </td>
						<td colspan="5" id="input_memo_exp"></td>
					</tr>
				</table>
				</form>
				<div id="exp-stv-line">
					<ul>
						<li><a href="#stv-line-out"><s:text name="label.warehouse.transitInner.outWH"></s:text>  </a></li>
						<li><a href="#stv-line-in"><s:text name="label.warehouse.transitInner.inWH"></s:text></a></li>
					</ul>
					<div id="stv-line-out">
						<table id="tb-stv-line-out"></table>
						<div id="pager1"></div>
					</div>
					<div id="stv-line-in">
						<table id="tb-stv-line-in"></table>
						<div id="pager"></div>
					</div>
				</div>
				<div class="buttonlist">
					<button type="button" loxiaType="button" id="executeInventory_exp" class="confirm"><s:text name="button.wahrhouse.inner.execute"></s:text> </button>
					<button type="button" loxiaType="button" id="cancel_exp" class="confirm" id="cancelStv"><s:text name="button.wahrhouse.inner.cancel"></s:text> </button>
					<button type="button" loxiaType="button" id="back_exp" ><s:text name="button.back"></s:text> </button>
				</div>
			</div>
		</div>
	</div>
	<iframe id="frmSoInvoice" class="hidden"></iframe>
	<div id="message_id"></div>
</body>
</html>