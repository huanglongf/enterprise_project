<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.genDph.title"/></title>
<script type="text/javascript" src="<s:url value='/frame/checkSingleSta/sales-picking-check-big.js' includeParams='none' encode='false'/>"></script>
<%-- <script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/sales-outbound-handover.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/handover-list-import-create.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script> --%>
<style type="text/css">
	.tbMsg td{
		font-size : 20px;
	};
</style>

</head>
<body contextpath="<%=request.getContextPath() %>">	
<div id="plDiv">
	<form id="plForm" name="plForm">
		<input type="hidden" name="cmd.sortingModeInt" value="1"/>
		<table width="70%">
			<tr>
				<td class="label" width="10%"><s:text name="label.warehouse.pl.whname"/></td>
				<td colspan="3" id="whName" width="90%"></td>
			</tr>
			<tr>
				<td class="label"  width="10%"><s:text name="label.warehouse.pl.batchno"/></td>
				<td width="20%"><input loxiaType="input" name="cmd.code" id="code" trim="true"/></td>
				<td class="label" width="10%"><s:text name="label.warehouse.pl.status"/></td>
	            <td width="20%">
	            	<s:select list="plStatus" loxiaType="select" listKey="optionKey" listValue="optionValue" name="cmd.intStatus" id="status" headerKey="" headerValue="%{pselect}"/>
	            </td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.pl.createtime"/></td>
				<td colspan="3">
					<input loxiaType="date" style="width: 150px" name="cmd.pickingTime1" id="pickingTime" showTime="true"/>
					<s:text name="label.warehouse.pl.to"/>
					<input loxiaType="date" style="width: 150px" name="cmd.executedTime1" id="executedTime" showTime="true"/>
				</td>
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button id="search" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
		<button id="reset" type="reset" loxiaType="button"><s:text name="button.reset"/></button>
	</div>
	<br/>
		批次号扫描：<input id="quickPl" loxiaType="input" style="height:26px;font-size:24px;width: 300px;" trim="true"/>
	<br/>
	<br/>
	<table id="tbl-dispatch-list"></table>
	<div id="pager"></div>
</div>
<div id="checkDiv" class="hidden">
<table>
	<tr>
		<td>
			<table width="700px">
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.batchno"/><input type="hidden" id="verifyPlId"/></td>
					<td width="15%" id="verifyCode"></td>
					<td class="label"><s:text name="label.warehouse.pl.status"/></td>
					<td width="15%" id="verifyStatus"></td>
					<td class="label">&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.pltotal"/></td>
					<td id="verifyPlanBillCount"></td>
					<td class="label"><s:text name="label.warehouse.pl.plcomplete"/></td>
					<td id="verifyCheckedBillCount"></td>
					<td class="hidden"><s:text name="label.warehouse.pl.plship"/></td>
					<td id="verifyShipStaCount" class="hidden"></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.prototal"/></td>
					<td id="verifyPlanSkuQty"></td>
					<td class="label"><s:text name="label.warehouse.pl.procomplete"/></td>
					<td id="verifyCheckedSkuQty"></td>
					<td class="hidden"><s:text name="label.warehouse.pl.proship"/></td>
					<td id="verifyShipSkuQty" class="hidden"></td>
					<td><input id="isBkCheckInteger" type="text" class="hidden"/></td>
				</tr>
			</table>
			<br/>
			<table width="100%">
			<tr>
				<td class="label" width="20%" >条码/卡号扫描</td>
				<td width="30%">
					<input id="skuBarCode" loxiaType="input"  trim="true" style="height:34px;font-size:32px;width: 300px;" />
				</td>
				<td class="label"  width="50%"   id="snDiv">
					SN号&nbsp;&nbsp;<input id="snCode" loxiaType="input"  trim="true" style="height:34px;font-size:32px;width: 300px;" />
				</td>
			</tr>
		</table>
		<table id="tbl-trank-button" width="500px"  border="1px" cellpadding="0px" cellspacing="0px" class="hidden">
						<tr>
							<td class="label" height="30" style="text-align: center;">作业单号</td>
							<td class="label" height="30" style="text-align: center;">商品编码</td>
							<td class="label" height="30" style="text-align: center;">商品名称</td>
							<td class="label" height="30" style="text-align: center;">数量</td>
							<td class="label" height="30" style="text-align: center;">备注</td>
						</tr>
					</table>
				</td>
		<td id="opencvImgMultiDiv">
			<%@include file="../../common/include-opencvimgmulti.jsp"%>
		</td>
	</tr>
</table>
	<br/>
	<table id="tbl-sta-list"></table>
	<br/><br/>
	<button id="btnTryError" loxiaType="button" >异常再次核对</button>
	<button id="btnSnCardError" loxiaType="button" >激活失败卡券拣货单补打</button>
	<button id="idBack" loxiaType="button" ><s:text name="button.toback"/></button>
	<button id="lpCodeJoinAndPrint" loxiaType="button">物流交接并打印交接清单</button>
	<button id="lpCodeJoin" loxiaType="button">物流交接</button>
</div>

	<div id="dialog_msg" >
		<input type="hidden" id="isPostpositionPackingPage" value="">
		<input type="hidden" id="isPostpositionExpressBill" value="">
		<input type="hidden" id="plId" value="">
		<input type="hidden" id="hidStaId" value="">
		<input type="hidden" id="hidSn" value="">
		<button id="camerImgButton" type="button" loxiaType="button" class="hidden"></button>
		<button id="camerImgMultiButton" type="button" loxiaType="button" class="hidden"></button>
		<table>
		<tr><td id="confirmErrorMsg" colspan="4" align="center" style="color: red;font-size: 30px;">
				</td></tr>
		<tr>
			<td>
			<table align="center" class="tbMsg" style="width:650px;">
			<tr>
				<td class="label" width="25%">
					相关单据号
				</td>
				<td id="tbSlipCode" width="25%">
				</td>
				<td class="label" width="25%">
					作业单号
				</td>
				<td id="tbStaCode" width="25%">
				</td>
			</tr>
			<tr>
				<td class="label" colspan="2">
					箱号
				</td>
				<td id="tbPgIndex" colspan="2" style="color: red;font-size: 42px;">
				</td>
			</tr>
			<tr>
				<td class="label" colspan="2">
					系统推荐箱型:
				</td>
				<td id="recommandSku" colspan="2" style="color:#f00;font-size: 42px;">
				</td>
			</tr>
		</table>
		<table align="center" class="tbMsg" style="width:700px;">
			<tr>
				<td class="label" width="35%">
					相关单据号/作业单号
				</td>
				<td>
					<input loxiaType="input" name="slipCode" id="idSlipCode" trim="true" style="height:34px;font-size:32px;" />
				</td>
				<td width="25%">
					<button loxiaType="button" class="confirm" id="printPackingPage" style="font-size:12px;">打印装箱清单</button>
				</td>
			</tr>
			<tr>
				<td class="label">
					快递单号
				</td>
				<td>
					<input loxiaType="input" name="txtTkNo" id="txtTkNo" trim="true" style="height:34px;font-size:32px;" />
				</td>
				<td>
					<button loxiaType="button" class="confirm" id="printExpressBill" style="font-size:12px;">打印面单</button>
				</td>
			</tr>
			<tr>
				<td id="tknoErrorMsg" colspan="4" align="center" style="color: red;">
				</td>
			</tr>
			<tr>
				<td class="label" id="p1">包材条码</td>
				<td id="p2"><input id="wrapStuff" class="labelinput" loxiaType="input"  trim="true" mandatory="true"/></td>
			</tr>
			<tr>
				<td class="label">当前重量</td>
				<td><input id="autoWeigth" loxiaType="input"   class="labelinput" readonly="readonly"/></td>
				<td><button id="restWeight" >重启称</button></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.package.weight"></s:text></td>
				<td><input id="goodsWeigth" loxiaType="input" class="labelinput"  mandatory="true"/></td>
				<td><s:text name="label.warehouse.package.weight.kg"></s:text></td>
			</tr>
			<tr>
			<td class="label">自动称重确认条码</td>
				<td><input id="confirmWeightInput" loxiaType="input" class="labelinput"  mandatory="true" /></td>
			</tr>
			<tr>
				<td id="weightErrorMsg" colspan="4" align="center" style="color: red;">
				</td>
			</tr>
		</table>
			</td>
			<td>
				<div id="opencvImgDiv">
					<%@include file="../../common/include-opencvimg.jsp"%>
				</div>
			</td>
		</tr>
		</table>

		<div class="buttonlist" align="center">
			<button loxiaType="button" class="confirm" id="btnConfirm">确认</button>
			确认条码：<input id="confirmBarCode" loxiaType="input" style="width: 100px" trim="true"/>
			<button loxiaType="button"  id="close">关闭</button>
			<button loxiaType="button" class="confirm" id="newCamera">重拍</button>
			<button loxiaType="button" class="confirm" id="jump" style="width: 100px">跳过</button>
		</div>
 	</div>
  
	<div id="dialog_error">
		<div id="errorMsg" style="font-size:32px" align="center">
		</div>
		<div id="divAllChecked1" class="hidden"  style="font-size:32px;color: red;" align="center">
			该批次已经核对完成
		</div>
		<div class="buttonlist" align="center">
			<button loxiaType="button" class="confirm" id="btnConfirm1">确认</button>
			确认条码：<input id="confirmBarCode1" loxiaType="input" style="width: 100px" trim="true"/>
		</div>
	</div>
	<div id="dialog_error_ok">
		<div id="error_text_ok"></div>
		<div class="buttonlist">
		           确认条码：<input id="closeCancel" loxiaType="input" style="width: 100px" trim="true"/>
			<button type="button" loxiaType="button" class="confirm" id="dialog_error_close_ok">关闭</button>
		</div>   
	</div>
	
	<div id="dialog_success_msg">
		<div style="font-size:32px" align="center">
			执行成功
		</div>
		<div id="divAllChecked"  class="hidden"  style="font-size:32px;color: red;" align="center">
			该批次已经核对完成
		</div>
		<div class="buttonlist" align="center">
			<button loxiaType="button" class="confirm" id="btnConfirm4">确认</button>
			确认条码：<input id="confirmBarCode4" loxiaType="input" style="width: 100px" trim="true"/>
		</div>
	</div>
	
	<div id="tabs-2" class="hidden">	<!-- tab2 start -->
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
	 						<input id="wrapStuff1" class="labelinput" loxiaType="input" mandatory="true" trim="true"/>
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
				<button type="button" loxiaType="button" class="confirm" id="createOrderByHand1"><s:text name="button.handoverlist.create"></s:text> </button>
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
					<div  class="buttonlist">
						<button id="btnDetialHo3" loxiaType="button" class="confirm"><s:text name="button.handover"></s:text> </button>
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
	
	<%@include file="../../common/include-opencv.jsp"%>
</body>
</html>
