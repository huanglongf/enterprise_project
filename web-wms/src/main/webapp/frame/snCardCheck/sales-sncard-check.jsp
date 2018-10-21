<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title>激活失败卡券处理</title>
<script type="text/javascript" src="<s:url value='/frame/snCardCheck/sales-sncard-check.js' includeParams='none' encode='false'/>"></script>
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
		<table width="700px">
			<tr>
				<td class="label"><s:text name="label.warehouse.pl.createtime"/></td>
				<td>
					<input loxiaType="date" style="width: 200px" name="cmd.pickingTime1" id="pickingTime" showTime="true"/>
				</td>
				<td class="label">
					<s:text name="label.warehouse.pl.to"/>
				</td>
				<td>
					<input loxiaType="date" style="width: 200px" name="cmd.executedTime1" id="executedTime" showTime="true"/>
				</td>
			</tr>
			<tr>
				<td class="label">相关单据号</td>
				<td ><input loxiaType="input"  style="width: 200px"  name="cmd.slipCode" id="slipCode" trim="true"/></td>
				<td class="label"  ><s:text name="label.warehouse.pl.batchno"/></td>
				<td><input loxiaType="input" name="cmd.code" style="width: 200px"  id="code" trim="true"/></td>
			</tr>
			<tr>
			<td class="label">作业单号</td>
			<td ><input loxiaType="input" name="cmd.staCode" id="staCode" style="width: 200px" trim="true"/></td>
			<td class="label" ><s:text name="label.warehouse.pl.status"/></td>
	            <td>
	            	<s:select list="plStatus" loxiaType="select" listKey="optionKey" listValue="optionValue" name="cmd.intStatus" id="status" style="width: 200px" headerKey="" headerValue="%{pselect}"/>
	            </td>
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button id="search" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
		<button id="reset" type="reset" loxiaType="button"><s:text name="button.reset"/></button>
	</div>
	<br/>
		作业单号扫描：<input id="quickSta" loxiaType="input" style="height:26px;font-size:24px;width: 300px;" trim="true"/>
	<br/>
	<br/>
	<table id="tbl-dispatch-list"></table>
	<div id="pager"></div>
</div>
<div id="dialog_error_ok">
	<div id="error_text_ok"></div>
	<!--<div><button id="closedialog" type="button" class="confirm">关闭</button></div>  -->
</div>
<div id="checkDiv" class="hidden">
	<div>
			<table width="900px">
				<tr>
					<td class="label" width="130px;"><s:text name="label.warehouse.pl.batchno"/>：<input type="hidden" id="verifyPlId"/><input type="hidden" id="staCodeValues"/></td>
					<td width="20%" id="verifyCode"></td>
					<td class="label" width="130px;">创建时间：</td>
					<td width="20%" id="creatTime"></td>
					<td class="label" width="130px;">最后核对时间：</td>
					<td width="20%" id="hdTime"></td>
				</tr>
				<tr>
					<td class="label" width="130px;">配货批状态：</td>
					<td id="pStatus"></td>
					<td class="label" width="130px;">计划配货商品件数：</td>
					<td id="planSkuQty"></td>
					<td class="label" width="130px;">已核对商品件数：</td>
					<td id="checkedSkuQty" ></td>
				</tr>
			</table>
	</div>
		<br/>
			<div>
			<table >
			<tr>
				<td class="label" >请录入激活失败卡(券)号：&nbsp;&nbsp;</td>
				<td>
					<input id="oldCard" loxiaType="input"  trim="true" style="height:26px;font-size:24px;width: 300px;" />
				</td>
			</tr>
			<tr>
				<td class="label" >卡(券)号：&nbsp;&nbsp;</td>
				<td class="label"  id="snDiv">
					<input id="newCard" loxiaType="input"  trim="true" style="height:26px;font-size:24px;width: 300px;" />
				</td>
			</tr>
		</table>
		</div>
		<br/>
	<div>
	<table id="tbl-sta-list"></table>
	</div>
		<br/>
	<div>
		<button id="btnTryError" loxiaType="button" >激活失败卡券拣货单补打</button>
		<button id="idBack" loxiaType="button" ><s:text name="button.toback"/></button>
	</div>
</div>
	<div id="dialog_msg_singel">
		<input class="hidden" id="isPostpositionPackingPage" value="">
		<input class="hidden" id="isPostpositionExpressBill" value="">
		<input class="hidden" id="plId" value="">
		<input class="hidden" id="hidStaId" value="">
		<input class="hidden" id="hidSn" value="">
		<input class="hidden" id="snid" value="">
		<button id="camerImgButton" type="button" loxiaType="button" class="hidden"></button>
		<button id="camerImgMultiButton" type="button" loxiaType="button" class="hidden"></button>
		<table>
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
		<table align="center" class="tbMsg" style="width:650px;">
			<tr>
				<td class="label" width="35%">
					相关单据号/作业单号
				</td>
				<td>
					<input loxiaType="input" name="slipCode" id="idSlipCode" trim="true" style="height:34px;font-size:32px;" />
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
					<button loxiaType="button" class="confirm" id="printExpressBill" style="font-size:12px;width: 130px;">打印面单</button>
				</td>
			</tr>
			<tr>
				<td id="tknoErrorMsg" colspan="4" align="center" style="color: red;">
				</td>
			</tr>
		</table>
			</td>
		</tr>
		</table>

		<div class="buttonlist" align="center">
			<button loxiaType="button" class="confirm" id="btnConfirm">核对</button>
			确认条码：<input id="confirmBarCode" loxiaType="input" style="width: 100px" trim="true"/>
			<button loxiaType="button"  id="close">关闭</button>
		</div>
 	</div>
 	<div id="dialog_msg_dis">
		<input class="hidden" id="dishidStaId" value="">
		<input class="hidden"  id="dissnid" value="">
		<input class="hidden" id="displId" value="">
		<button id="camerImgButton" type="button" loxiaType="button" class="hidden"></button>
		<button id="camerImgMultiButton" type="button" loxiaType="button" class="hidden"></button>
		<table>
		<tr>
			<td>
			<table align="center" class="tbMsg" style="width:650px;">
			<tr>
				<td class="label" width="25%">
					相关单据号
				</td>
				<td id="distbSlipCode" width="25%">
				</td>
				<td class="label" width="25%">
					作业单号
				</td>
				<td id="distbStaCode" width="25%">
				</td>
			</tr>
			<tr>
				<td class="label" colspan="2">
					快递单号
				</td>
				<td id="txtTk" colspan="2" >
				</td>
			</tr>
		</table>
		<table align="center" class="tbMsg" style="width:650px;">
			<tr>
				<td class="label" style="width:100px;">
					快递单号
				</td>
				<td>
					<input loxiaType="input" name="distxtTkNo" id="distxtTkNo" trim="true" style="height:34px;font-size:32px;" />
				</td>
				<td>
					<button loxiaType="button" class="confirm" id="disprintExpressBill" style="font-size:12px;width: 130px;">打印面单</button>
				</td>
			</tr>
			<tr>
				<td id="distknoErrorMsg" colspan="4" align="center" style="color: red;">
				</td>
			</tr>
		</table>
			</td>
		</tr>
		</table>
		<table id="tbl-trank-list" width="500px"  style="text-align: center;" border="1px" cellpadding="0px" cellspacing="0px">
			<tr>
				<td class="label" height="30" style="text-align: center;">拆包运单号</td>
				<td class="label" height="30" style="text-align: center;"><s:text name="label.warehouse.pl.sta.operation"/></td>
			</tr>
		</table>
		<div class="buttonlist" align="center">
			<button loxiaType="button" class="confirm" id="disbtnConfirm">核对</button>
			确认条码：<input id="disconfirmBarCode" loxiaType="input" style="width: 100px" trim="true"/>
			<button loxiaType="button"  id="disclose">关闭</button>
		</div>
 	</div>
</body>
</html>
