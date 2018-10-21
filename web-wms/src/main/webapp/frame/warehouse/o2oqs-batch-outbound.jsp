<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	.label{
		font-size: 18px; color: #000000;
	}
	.label2{
		font-size: 22px; color: #000000;
	}
	.labelinput{
		height: 28px; line-height:  28px; width:  250px;
		font-size: 21px;
	}
	#outbound_dialog table tbody tr {height: 30px;}
	#outbound_result_dialog {min-height: 300px; width: 100%; font-size: 18px; font-weight: bold; color: red;}
	#weight_input_dialog {min-height: 150px; width: 100%; font-size: 18px; font-weight: bold; color: red;}
	#show_error_dialog {min-height: 150px; width: 100%; font-size: 18px; font-weight: bold; color: red;}
</style>
<%@include file="../../common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.outbound.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/o2oqs-batch-outbound.js' includeParams='none' encode='false'/>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<div id="staDiv" staId="" trackingNo="" plpId="" plId="">
		<div id="outbound">
		<table width="90%">
			<tr>
				<td width="20%" class="label"><s:text name="label.warehouse.pl.sta.delivery.code"></s:text></td>
				<td width="30%"><input id="billsId" loxiaType="input" mandatory="true" class="labelinput" trim="true"/></td>
				<td width="20%" class="label">当前重量</td>
				<td width="30%"><input id="autoWeigth" loxiaType="input" style="width: 150px"  class="labelinput" readonly="readonly"/><!--  readonly="readonly" -->
					<button id="restWeight" loxiaType="button">重启称</button>
				</td>
			</tr>
			<tr>
				<td class="label">关联出库<s:text name="label.warehouse.pl.batchno"></s:text></td>
				<td id="plbatchno" class="labelinput"></td><!-- soCode -->
				<td class="label"><s:text name="label.warehouse.package.weight"></s:text></td>
				<td><input id="goodsWeigth" loxiaType="input" class="labelinput" style="width: 150px" mandatory="true" /> <!--  checkmaster="doExecute" -->
					<s:text name="label.warehouse.package.weight.kg"></s:text>
				</td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.pl.sta.delivery"></s:text></td>
				<td id="lpcode" class="labelinput"></td>
				<td class="label">自动称重确认条码</td>
				<td><input id="confirmWeightInput" loxiaType="input" class="labelinput" style="width: 150px" mandatory="true" /></td>
			</tr>
			<tr>
				<td id="p1" class="label">包材条码</td>
				<td id="p2"><input id="wrapStuff" class="labelinput" loxiaType="input" mandatory="true" trim="true"/></td>
			</tr>
			<tr id="p_tr">
				<td class="label"></td>
				<td>
					<div loxiaType="edittable" >
						<table operation="add,delete" id="barCode_tab" >
							<thead>
								<tr>
									<th width="10"><input type="checkbox"/></th>
									<th width="200">条形码</th>
									<th width="100">数量</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
							<tbody >
								<tr class="add" index="(#)">
									<td><input type="checkbox"/></td>
									<td><input loxiatype="input" readonly="readonly" mandatory="true" name="addList(#).barcode" class="code"/></td>
									<td><input loxiatype="input" readonly="readonly" mandatory="true" name="addList(#).qty" class="qty"/></td>
								</tr>
							</tbody>
							<tfoot></tfoot>
						</table>
					</div>
					</td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td class="label">&nbsp;</td>
				<td><button id="executeOutbound" loxiaType="button" class="hidden confirm" style="width: 100%"><s:text name="button.sta.outbound"></s:text></button></td>
				<td><s:text name="label.wahrhouse.sta.outbound.msg"></s:text></td>
				<td>&nbsp;</td>
			</tr>
		</table>
		
		<div id="outbound_dialog" style="text-align: center;">
			 <div id="showError" style="width: 100%; font-size: 30px !important; font-weight:bold; color:red; min-height: 50px;" ></div>
			 <div id="showOutBoundTable">
			 	<table cellpadding="0" cellspacing="0" border="0" width="100%">
				 	<tbody>
				 		<tr>
				 			<td class="label2" width="20%">快递单号:</td>
				 			<td id="transportNo" width="30%" class="label2"></td>
				 			<td class="label2" width="20%">物流供应商:</td>
				 			<td id="transportName" width="30%" class="label2"></td>
				 		</tr>
				 		<tr>
				 			<td class="label2">关联出库配货批次号:</td>
				 			<td id="relativePickingListCode" class="label2"></td>
				 			<td class="label2">货物重量:</td>
				 			<td id="weight_" class="label2"></td>
				 		</tr>
				 	</tbody>
				 </table>
			 </div>
			 <button id="confirmOutBound" loxiaType="button" class="confirm hidden">确认出库</button>
			 <button id="confirmClose" loxiaType="button" class="confirm">确认</button>
			 <span class="label">确认条码：</span>
			 <input id="confirmValue" loxiaType="input" trim="true" style="width: 200px; height: 30px; line-height: 30px;"/>
		</div>
		<div id="outbound_result_dialog" style="text-align: center;">
			<div id="showOutBoundMsg" style="font-size: 22px"></div>
			<div style="margin-top: 100px;">
				<button id="confirmResultClose" loxiaType="button" class="confirm">确定</button>
				<span id="confirmInputSpan" class="label">确认条码：</span>
				<input id="confirmResultValue" loxiaType="input" trim="true" style="width: 200px; height: 30px; line-height: 30px;"/>
			</div>
		</div>
		
		<!-- 重量确认输入ok -->
		<div id="weight_input_dialog" style="text-align: center;">
			<div id="showWeightInfo"></div>
			<div style="margin-top: 30px;">
				<button id="confirmWeightInfoClose" loxiaType="button" class="confirm">确定</button>
				<span class="label">确认条码：</span>
				<input id="confirmWeightInfoValue" loxiaType="input" trim="true" style="width: 200px; height: 30px; line-height: 30px;"/>
			</div>
		</div>
		
		<br/><br/>
		
		<div style="display: inline">
		<table class="table" width="90%" style="vertical-align:top;">
			<tr>
				<td><span class="label"><s:text name="label.wahrhouse.sta.outbound.table.success"></s:text></span></td>
				<%-- <td><span class="label"><s:text name="label.wahrhouse.sta.outbound.table.failed"></s:text></span></td> --%>
			</tr>
			<tr>
				<td valign="top" class="executeResult" width="90%">
					<table id="rsSuccess" width="100%">
						<tr>
							<td width="240px"><s:text name="label.warehouse.pl.sta.slipcode"></s:text> </td>
							<td><s:text name="label.wahrhouse.sta.outbound.table.msg"></s:text> </td>
						</tr>
					</table>
				</td>
				<%-- <td valign="top" class="executeResult" width="45%">
					<table id="rsError" width="100%">
						<tr>
							<td width="120px"><s:text name="label.warehouse.pl.sta.slipcode"></s:text></td>
							<td><s:text name="label.wahrhouse.sta.outbound.table.msg"></s:text></td>
						</tr>
					</table>
				</td> --%>
			</tr>
		</table>
		</div>  
		</div>
	</div>
		<!-- 错误信息 显示 -->
		<div id="show_error_dialog" style="text-align: center;">
			<div id="showErrorInfo" style="font-size: 40pX"></div>
			<div style="margin-top: 40px;">
				<button id="confirmErrorInfoClose" loxiaType="button" class="confirm">确定</button>
				<%-- <span class="label">确认条码：</span>
				<input id="confirmErrorInfoValue" loxiaType="input" trim="true" style="width: 200px; height: 30px; line-height: 30px;"/> --%>
			</div>
		</div>
		<div id="dialog_error_ok">
		 	<div id="error_text_ok" ></div>
			<div class="buttonlist">
				<!-- <button loxiaType="button" id="closeBtn" >关闭</button>   -->
				  <span loxiaType="button" id="closeBtn" onclick="clickBtn();">关闭</span>
			</div>    
		</div>
</body>
</html>