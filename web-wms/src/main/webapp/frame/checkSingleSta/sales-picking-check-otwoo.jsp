<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.genDph.title"/></title>
<script type="text/javascript" src="<s:url value='/frame/checkSingleSta/sales-picking-check-otwoo.js' includeParams='none' encode='false'/>"></script>
<style type="text/css">
	.tbMsg td{
		font-size : 20px;
	};
</style>

</head>
<body contextpath="<%=request.getContextPath() %>">	
<div id="plDiv">
	<form id="plForm" name="plForm" style="float: left;width: 65%;">
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
	<div id="oneHandDiv" style="margin-left: 30%;">
<span class="label">本账号下当前未交接运单数:</span><span id="num" style="color:red;font-size:16px;"></span><span class="label">交接上限:</span><span id="maxNum" style="color:red;font-size:16px;"></span><br>
<span class="label">物流服务商:</span><select style="width:200px" class="special-flexselect" id="selTrans2" name="lpcode2" data-placeholder="*请输入物流信息"></select>
<div style="display:inline-block"  id="showShopList"></div><input type="hidden" value="" id="postshopInput" /><input type="hidden" value="" id="postshopInputName" />
<br><button loxiaType="button" class="confirm" id="oneHandoverPrint">一键交接并打印交接清单</button><button loxiaType="button" class="confirm" id="oneHandover">一键交接</button>
</div>
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
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.pltotal"/></td>
					<td id="verifyPlanBillCount"></td>
					<td class="label"><s:text name="label.warehouse.pl.plcomplete"/></td>
					<td id="verifyCheckedBillCount"></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.prototal"/></td>
					<td id="verifyPlanSkuQty"></td>
					<td class="label"><s:text name="label.warehouse.pl.procomplete"/></td>
					<td id="verifyCheckedSkuQty"></td>
					<td><input id="isBkCheckInteger" type="text" class="hidden"/></td>
				</tr>
			</table>
			<br/>
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
	<button id="btnTryError" loxiaType="button" >开始核对</button>
	<button id="idBack" loxiaType="button" >返回</button>
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
		<tr>
			<td>
			<table align="center" class="tbMsg" style="width:650px;">
			<tr>
				<td class="label" width="35%">
					条码
				</td>
				<td>
				<input loxiaType="input" name="slipCode" id="idSlipCode" trim="true" style="height:34px;font-size:32px;" />
				</td>
				<td width="25%">
					
				</td>
			</tr>
			<tr>
				<td class="label" width="35%">
					SN号
				</td>
				<td>
				<input loxiaType="input" name="sn" id="sn" trim="true" class="hidden" style="height:34px;font-size:32px;" />
				<input loxiaType="input" id="orderId" name="orderId" trim="true" class="hidden" >
				</td>
				<td width="25%">
					
				</td>
			</tr>
			<tr>
				<td class="label" width="35%">
					箱号
				</td>
				<td id="tbPgIndex"  style="color: red;font-size: 42px;">
				</td>
				<td width="25%">
					
				</td>
			</tr>
			<tr>
				<td class="label" width="35%">
					作业单号
				</td>
				<td id="staCode">
					
				</td>
				<td width="25%">
					
				</td>
			</tr>
			<tr>
				<td class="label">
					相关单据号
				</td>
				<td id="xslipCode"> 
					
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td class="label">
					作业单号/相关单据号
				</td>
				<td > 
					<input loxiaType="input" name="slipCode" id="slipCode" trim="true" style="height:34px;font-size:32px;" />
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td class="label">
					计数
				</td>
				<td id="tbPgIndexs"  style="color: red;font-size: 42px;">
				</td>
				<td>
					<input loxiaType="input" id="slipCodes" name="slipCodes" trim="true" class="hidden">
					<input loxiaType="input" id="data" name="data" trim="true" class="hidden">
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
			<button loxiaType="button" class="confirm" id="btnConfirm">核对</button>
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
	<%@include file="../../common/include-opencv.jsp"%>
</body>
</html>
