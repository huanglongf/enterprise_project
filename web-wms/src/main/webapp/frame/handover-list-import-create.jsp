<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
	.executeResult{
		background-color: white;
		border: 1px gray solid;	
	}
	
	.table{
		border:1px gray solid;	
		margin: 0px;
		padding: 0px
	}
	
	.label2{
		/*font-size: 18px;*/ 
	}
	.labelinput{
		height:  30px; line-height:  30px; width:  250px;
		font-size: 22px;
	}
	
</style>
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.outbound.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/handover-list-import-create.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<%-- <div>
<span class="label">本账号下当前未交接运单数:</span><span id="num" style="color:red;font-size:16px;"></span><span class="label">交接上限:</span><span id="maxNum" style="color:red;font-size:16px;"></span><br>
<span class="label">物流服务商:</span><select style="width:200px" class="special-flexselect" id="selTrans2" name="lpcode2" data-placeholder="*请输入物流信息"></select>
<div style="display:inline-block"  id="showShopList"></div><input type="hidden" value="" id="postshopInput" /><input type="hidden" value="" id="postshopInputName" />
<br><button loxiaType="button" class="confirm" id="oneHandoverPrint">一键交接并打印交接清单</button><button loxiaType="button" class="confirm" id="oneHandover">一键交接</button>
</div> --%>
 <div id="tabs">
 	<ul>
		<li><a href="#tabs-1">导入-创建物流交接清单</a></li>
		<li><a href="#tabs-2">手工录入-创建物流交接清单</a></li>
		<li><a href="#tabs-3">自动化-创建物流交接清单</a></li>
	</ul>
 	<div id="tabs-1">	<!-- tab1 start  import-->
		<div style="float: left;">
			<a id="download" loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_tracking_no"></s:text>.xls&inputPath=tplt_import_tracking_no.xls"><s:text name="download.excel.template"></s:text></a>
			<iframe id="upload" name="upload" class="hidden"></iframe>
				<div id="searchInfo">
					<form method="POST" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
						<table width="300px">
							<tr>
								<td class="label" width="20%"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
								<td width="30%">
									<select loxiaType="select" name="lpcode" id="selTrans">
										<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="label"><s:text name="label.wahrhouse.handoverlist.sender"></s:text> </td>
								<td><s:text name="label.wahrhouse.handoverlist.sender.baozun"></s:text> </td>
							</tr>
						</table>
						<div class="buttonlist"></div>
						<div id="searchFile" class="hidden">
							<span class="label"><s:text name="label.warehouse.choose.file"></s:text> ：</span><input id="tnFile" type="file" loxiaType="input" accept="application/msexcel" name="trackingNoFile" style="width:200px"/> &nbsp; &nbsp; &nbsp;
							<button loxiaType="button" class="confirm" id="import"><s:text name="button.import"></s:text> </button>
							<br/>
							<br/>
						</div>
					</form>
					<span class="label"><s:text name="label.wahrhouse.handoverlist.list"></s:text> </span>
					<br/><br/>
					<table width="300px">
						<tr>
							<td class="label" widht="30%"> &nbsp;&nbsp; <s:text name="label.wahrhouse.handoverlist.totalpgnum"></s:text> </td>
							<td width="20%" align="right"> <span id="packageCount"></span> <s:text name="label.wahrhouse.handoverlist.count"></s:text> </td>
							<td class="label" width="30%"><s:text name="label.wahrhouse.handoverlist.totalweight"></s:text> </td>
							<td width="020%" align="right"> <span id="weight"></span> <s:text name="label.warehouse.package.weight.kg"></s:text> </td>
						</tr>
					</table>
					<br/><br/>	
					<table id="tbl-goodsList"></table>
				
					<div class="buttonlist hidden" id="buttonList">
						<button type="button" loxiaType="button" class="confirm" id="createOrder"><s:text name="button.handoverlist.create"></s:text> </button>
					</div>
					<div id="divRemoveInfo" style="margin-top: 20px;" class="hidden"><table id="tbl-removeInfo"  width="400px" style="text-align: center"></table></div>
				</div>
			<div id="confirm_ho_form_div" class="hidden">
				<form id="confirm_ho_form">
					<input type="hidden" name="handOverList.id" id="hidHoId">
					<table width="600px" id="tblHandoverInfo">
						<tr>
							<td class="label" width="160px"><s:text name="label.wahrhouse.handoverlist.code"></s:text> </td>
							<td id="hoCode"></td>
							<td class="label"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
							<td id="hoCreateTime"></td>
						</tr>
						<tr>
							<td class="label" width="160px"><s:text name="label.wahrhouse.handoverlist.totalpgnum"></s:text> </td>
							<td id="hoPackageCount"></td>
							<td class="label"><s:text name="label.wahrhouse.handoverlist.totalweight"></s:text> </td>
							<td id="hoTotalWeight"></td>
						</tr>
					</table>
					<table id="tblHandoverInput">
						<tr>
							<td class="label" width="160px"><s:text name="label.wahrhouse.handoverlist.partyAOperator"></s:text> </td>
							<td width="120px"><input loxiaType="input" name="handOverList.partyAOperator" style="width: 120px" trim="true"  mandatory="true"/></td>
							<td class="label" width="120px"><s:text name="label.wahrhouse.handoverlist.paytyAMobile"></s:text> </td>
							<td><input loxiaType="input" name="handOverList.paytyAMobile" style="width: 120px" trim="true"  mandatory="true"/></td>
						</tr>
						<tr>
							<td class="label"><s:text name="label.wahrhouse.handoverlist.partyb"></s:text> </td>
							<td><input loxiaType="input" name="handOverList.partyBOperator" style="width: 120px" trim="true"  mandatory="true"/></td>
							<td class="label"><s:text name="label.wahrhouse.handoverlist.paytyBMobile"></s:text> </td>
							<td><input loxiaType="input" name="handOverList.paytyBMobile" style="width: 120px" trim="true"  mandatory="true"/></td>
						</tr>
						<tr>
							<td class="label"><s:text name="label.wahrhouse.handoverlist.paytyBPassPort"></s:text></td>
							<td><input loxiaType="input" name="handOverList.paytyBPassPort"  style="width: 120px" trim="true"  mandatory="true"/></td>
							<td></td>
							<td></td>
						</tr>
					</table>
					<table id="tblHandoverSuccess" class="hidden">
						<tr>
							<td class="label" width="160px"><s:text name="label.wahrhouse.handoverlist.partyAOperator"></s:text></td>
							<td id="partyAOperator" width="120px"></td>
							<td class="label" width="120px"><s:text name="label.wahrhouse.handoverlist.paytyAMobile"></s:text></td>
							<td id="partyAMobile"></td>
						</tr>
						<tr>
							<td class="label"><s:text name="label.wahrhouse.handoverlist.partyb"></s:text></td>
							<td id="partyBOperator"></td>
							<td class="label"><s:text name="label.wahrhouse.handoverlist.paytyBMobile"></s:text></td>
							<td id="paytyBMobile"></td>
						</tr>
						<tr>
							<td class="label"><s:text name="label.wahrhouse.handoverlist.paytyBPassPort"></s:text> </td>
							<td id="paytyBPassPort"></td>
							<td></td>
							<td></td>
						</tr>
					</table>
					<br />
				</form>
					<table id="tbl-detial"></table>
					<div  class="buttonlist" >
						<button id="btnDetialHo" loxiaType="button" class="confirm"><s:text name="button.handover"></s:text> </button>
						<button id="btnDetialPrint" loxiaType="button" class="confirm"><s:text name="button.saveAndPrint"></s:text> </button>
						<button id="back" loxiaType="button" class="confirm"><s:text name="button.goCreate"></s:text> </button>
						
					</div>
			</div>
			<div id="allDelete1" class="hidden">
				<div style="text-align:center;font-size:25px;color:red;font-weight:bold">交接单明细行已全部作废该交接单也被作废</div>
				<div class="buttonlist" >
					<button id="back4" loxiaType="button">返回</button>
				</div>
			</div>
			</div>	
<div id="oneHandDiv" style="margin-left: 55%;">
<span class="label">本账号下当前未交接运单数:</span><span id="num" style="color:red;font-size:16px;"></span><span class="label">交接上限:</span><span id="maxNum" style="color:red;font-size:16px;"></span><br>
<span class="label">物流服务商:</span><select style="width:200px" class="special-flexselect" id="selTrans2" name="lpcode2" data-placeholder="*请输入物流信息"></select>
<div style="display:inline-block"  id="showShopList"></div><input type="hidden" value="" id="postshopInput" /><input type="hidden" value="" id="postshopInputName" />
<br><button loxiaType="button" class="confirm" id="oneHandoverPrint">一键交接并打印交接清单</button><button loxiaType="button" class="confirm" id="oneHandover">一键交接</button>
</div>
 	</div> 	<!-- tab1 end -->

 	<div id="tabs-2"><!-- tab2 start -->
	<div style="float: left;">
 		<div id="searchInfo2">
			<table>
	 			<tbody>
	 				<tr>
						<td class="label" width="20%"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
						<td width="30%">
							<select loxiaType="select" name="lpcode" id="selTrans-tabs2">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</td>
						<td class="label" width="20%"><s:text name="label.wahrhouse.handoverlist.sender"></s:text> </td>
						<td width="30%"><s:text name="label.wahrhouse.handoverlist.sender.baozun"></s:text> </td>
	 				</tr>
	 				<tr>
	 					<td class="label">输入快递单号： </td>
	 					<td>
	 						<input id="wrapStuff" class="labelinput" loxiaType="input" mandatory="true" trim="true"/>
	 					</td>
	 					<td class="label"></td>
	 					<td></td>
	 				</tr>
	 				<tr>
	 					<td colspan="4">
	 						<div loxiaType="edittable" >
								<table operation="add,delete" id="barCode_tab" width="100%">
									<thead>
										<tr>
											<th width="20%"><input type="checkbox"/></th>
											<th>快递单号</th>
											<!-- <th width="100">数量</th> -->
										</tr>
									</thead>
									<tbody>
									</tbody>
									<tbody>
										<tr class="add" index="(#)">
											<td><input type="checkbox"/></td>
											<td><input loxiatype="input" readonly="readonly" mandatory="true" name="addList(#).barcode" class="code"/></td>
											<!-- <td><input loxiatype="input" readonly="readonly" mandatory="true" name="addList(#).qty" class="qty"/></td> -->
										</tr>
									</tbody>
									<tfoot></tfoot>
								</table>
							</div>
	 					</td>
	 				</tr>
	 			</tbody>
	 		</table>
	 		<form method="POST" id="importForm2" name="importForm2" target="upload"></form>
	 		<button type="button" loxiaType="button" class="confirm" id="inputTransNoByHand">确定</button>
	 		<br/><br/>
	 		<span class="label"><s:text name="label.wahrhouse.handoverlist.list"></s:text> </span>
			<br/><br/>
			<table width="300px">
				<tr>
					<td class="label" widht="30%"> &nbsp;&nbsp; <s:text name="label.wahrhouse.handoverlist.totalpgnum"></s:text> </td>
					<td width="20%" align="right"> <span id="packageCount2"></span> <s:text name="label.wahrhouse.handoverlist.count"></s:text> </td>
					<td class="label" width="30%"><s:text name="label.wahrhouse.handoverlist.totalweight"></s:text> </td>
					<td width="20%" align="right"> <span id="weight2"></span> <s:text name="label.warehouse.package.weight.kg"></s:text> </td>
				</tr>
			</table>
			<br/><br/>	
			
			<table id="tbl-goodsList2"></table>				
			
	 		<div class="buttonlist hidden" id="buttonList2">
				<button type="button" loxiaType="button" class="confirm" id="createOrderByHand"><s:text name="button.handoverlist.create"></s:text> </button>
			</div>
			<div id="divRemoveInfo2" style="margin-top: 20px;" class="hidden">
				<table id="tbl-removeInfo2"  width="400px" style="text-align: center"></table>
			</div>
 		</div>
		<div id="confirm_ho_form_div2" class="hidden">
				<form id="confirm_ho_form2">
					<input type="hidden" name="handOverList.id" id="hidHoId2">
					<table width="600px" id="tblHandoverInfo2">
						<tr>
							<td class="label" width="160px"><s:text name="label.wahrhouse.handoverlist.code"></s:text> </td>
							<td id="hoCode2"></td>
							<td class="label"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
							<td id="hoCreateTime2"></td>
						</tr>
						<tr>
							<td class="label" width="160px"><s:text name="label.wahrhouse.handoverlist.totalpgnum"></s:text> </td>
							<td id="hoPackageCount2"></td>
							<td class="label"><s:text name="label.wahrhouse.handoverlist.totalweight"></s:text> </td>
							<td id="hoTotalWeight2"></td>
						</tr>
					</table>
					<table id="tblHandoverInput2">
						<tr>
							<td class="label" width="160px"><s:text name="label.wahrhouse.handoverlist.partyAOperator"></s:text> </td>
							<td width="120px"><input loxiaType="input" name="handOverList.partyAOperator" style="width: 120px" trim="true"  mandatory="true"/></td>
							<td class="label" width="120px"><s:text name="label.wahrhouse.handoverlist.paytyAMobile"></s:text> </td>
							<td><input loxiaType="input" name="handOverList.paytyAMobile" style="width: 120px" trim="true"  mandatory="true"/></td>
						</tr>
						<tr>
							<td class="label"><s:text name="label.wahrhouse.handoverlist.partyb"></s:text> </td>
							<td><input loxiaType="input" name="handOverList.partyBOperator" style="width: 120px" trim="true"  mandatory="true"/></td>
							<td class="label"><s:text name="label.wahrhouse.handoverlist.paytyBMobile"></s:text> </td>
							<td><input loxiaType="input" name="handOverList.paytyBMobile" style="width: 120px" trim="true"  mandatory="true"/></td>
						</tr>
						<tr>
							<td class="label"><s:text name="label.wahrhouse.handoverlist.paytyBPassPort"></s:text></td>
							<td><input loxiaType="input" name="handOverList.paytyBPassPort"  style="width: 120px" trim="true"  mandatory="true"/></td>
							<td></td>
							<td></td>
						</tr>
					</table>
					<table id="tblHandoverSuccess2" class="hidden">
						<tr>
							<td class="label" width="160px"><s:text name="label.wahrhouse.handoverlist.partyAOperator"></s:text></td>
							<td id="partyAOperator2" width="120px"></td>
							<td class="label" width="120px"><s:text name="label.wahrhouse.handoverlist.paytyAMobile"></s:text></td>
							<td id="partyAMobile2"></td>
						</tr>
						<tr>
							<td class="label"><s:text name="label.wahrhouse.handoverlist.partyb"></s:text></td>
							<td id="partyBOperator2"></td>
							<td class="label"><s:text name="label.wahrhouse.handoverlist.paytyBMobile"></s:text></td>
							<td id="paytyBMobile2"></td>
						</tr>
						<tr>
							<td class="label"><s:text name="label.wahrhouse.handoverlist.paytyBPassPort"></s:text> </td>
							<td id="paytyBPassPort2"></td>
							<td></td>
							<td></td>
						</tr>
					</table>
					<br />
				</form>
					<table id="tbl-detial2"></table>
					<div  class="buttonlist" >
						<button id="btnDetialHo2" loxiaType="button" class="confirm"><s:text name="button.handover"></s:text> </button>
						<button id="btnDetialPrint2" loxiaType="button" class="confirm"><s:text name="button.saveAndPrint"></s:text> </button>
						<button id="back2" loxiaType="button" class="confirm"><s:text name="button.goCreate"></s:text> </button>
					</div>
			</div>
			<div id="allDelete" class="hidden">
				<div style="text-align:center;font-size:25px;color:red;font-weight:bold">交接单明细行已全部作废该交接单也被作废</div>
				<div class="buttonlist" >
					<button id="back3" loxiaType="button">返回</button>
				</div>
			</div>	
			</div>
<div  id="oneHandDiv2" style="margin-left: 55%;">
<span class="label">本账号下当前未交接运单数:</span><span id="num2" style="color:red;font-size:16px;"></span><span class="label">交接上限:</span><span id="maxNum2" style="color:red;font-size:16px;"></span><br>
<span class="label">物流服务商:</span><select style="width:200px" class="special-flexselect2" id="selTrans22" name="lpcode2" data-placeholder="*请输入物流信息"></select>
<div style="display:inline-block"  id="showShopList2"></div><input type="hidden" value="" id="postshopInput2" /><input type="hidden" value="" id="postshopInputName2" />
<br><button loxiaType="button" class="confirm" id="oneHandoverPrint2">一键交接并打印交接清单</button><button loxiaType="button" class="confirm" id="oneHandover2">一键交接</button>
</div>
 	</div> 	<!-- tab2 end -->
 	<div id="tabs-3">
 		<div>
 			<table>
 				<tr>
 					<td class="label" width="20%"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
						<td width="30%">
							<select loxiaType="select" name="lpcode" id="selTrans-tabs3">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</td>
 				</tr>
 				<tr>
 					<td class="label" >已选择物流商：</td>
 					<td><font id="wlsList" color="red"></font></td>
 				</tr>
 				<tr>
 					<td><button type="button" loxiaType="button" class="confirm" id="queryWld">查询</button></td>
 					<td><button type="button" loxiaType="button" class="confirm"  id="reset">重置</button></td>
 				</tr>
 			</table>
 		</div>
 		<div>
 			<table id="tbl-zdhlist"></table>
			<div id="pager_query"></div>
 		</div>
 		<div class="buttonlist">
 				<button loxiaType="button" class="confirm" id="autoWhHandover">交接</button>
 				<button loxiaType="button" class="confirm" id="autoWhHandoverAndPrint">交接并打印</button>
 		</div>
 	
 	</div>
 </div>
</body>
</html>