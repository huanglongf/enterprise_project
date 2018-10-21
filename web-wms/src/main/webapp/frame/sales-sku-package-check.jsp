<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.genDph.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/sales-sku-package-check.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style type="text/css">
	.tbMsg td{
		font-size : 20px;
	}
	.photoInfo{
		float:right;
		margin-left:20px;
		width:130px;
	}
</style>

</head>
<body contextpath="<%=request.getContextPath() %>">	
<div id="plDiv">
	<form id="plForm" name="plForm">
		<input type="hidden" name="plCmd.intCheckMode" value="20"/>
		<table width="70%">
			<tr>
				<td class="label"><s:text name="label.warehouse.pl.createtime"/></td>
				<td>
					<input loxiaType="date" style="width: 150px" name="plCmd.pickingTime1" id="pickingTime" showTime="true"/>
					<s:text name="label.warehouse.pl.to"/>
					<input loxiaType="date" style="width: 150px" name="plCmd.executedTime1" id="executedTime" showTime="true"/>
				</td>
				<td class="label"><s:text name="label.warehouse.pl.batchno"/></td>
				<td width="20%"><input loxiaType="input" name="plCmd.code" id="code" trim="true"/></td>
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
							<td class="label"><s:text name="label.warehouse.pl.batchno" /><input
								type="hidden" id="verifyPlId" />
							</td>
							<td width="15%" id="verifyCode"></td>
							<td class="label"><s:text name="label.warehouse.pl.status" />
							</td>
							<td width="15%" id="verifyStatus"></td>
							<td class="label">&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td class="label"><s:text name="label.warehouse.pl.pltotal" />
							</td>
							<td id="verifyPlanBillCount"></td>
							<td class="label"><s:text
									name="label.warehouse.pl.plcomplete" />
							</td>
							<td id="verifyCheckedBillCount"></td>
							<td class="label"><s:text name="label.warehouse.pl.plship" />
							</td>
							<td id="verifyShipStaCount"></td>
						</tr>
						<tr>
							<td class="label"><s:text name="label.warehouse.pl.prototal" />
							</td>
							<td id="verifyPlanSkuQty"></td>
							<td class="label"><s:text
									name="label.warehouse.pl.procomplete" />
							</td>
							<td id="verifyCheckedSkuQty"></td>
							<td class="label"><s:text name="label.warehouse.pl.proship" />
							</td>
							<td id="verifyShipSkuQty"></td>
						</tr>
					</table> <br />
					<table width="500px">
						<tr>
							<td class="label" width="30%">条码扫描</td>
							<td width="70%"><input id="skuBarCode" loxiaType="input"
								trim="true" style="height: 34px; font-size: 32px; width: 400px;" />
							</td>
						</tr>
					</table>
				</td>
				<td>
					<div class="photoInfo" style="display:none;">
						<%@include file="../common/include-opencvimgmulti.jsp"%>
					</div>
				</td>
			</tr>
		</table>
		<br/>
	<table id="tbl-sta-list"></table>
	<br/><br/>
	<button id="btnTryError" loxiaType="button" >异常再次核对</button>
	<button id="idBack" loxiaType="button" ><s:text name="button.toback"/></button>
</div>

	<div id="dialog_msg">
		<input type="hidden" id="hidStaId" value="">
		<table>
			<tr>
				<td>
					<table width="100%" align="center" class="tbMsg">
						<tr>
							<td class="label" width="25%">相关单据号</td>
							<td id="tbSlipCode" width="25%"></td>
							<td class="label" width="25%">作业单号</td>
							<td id="tbStaCode" width="25%"></td>
						</tr>
						<tr>
							<td class="label" colspan="2">箱号</td>
							<td id="tbPgIndex" colspan="2"
								style="color: red; font-size: 42px;"></td>
						</tr>
						<tr>
							<td class="label" colspan="2">套装商品</td>
							<td colspan="2"><label id="idPgSkuName"></label></td>
						</tr>
						<tr>
							<td class="label" colspan="2">请扫套装商品条码</td>
							<td colspan="2"><input loxiaType="input" name="pgskuBarcode"
								id="idPgskuBarcode" trim="true"
								style="height: 34px; font-size: 32px;" /></td>
						</tr>
						<tr>
							<td class="label" colspan="2">扫描数量</td>
							<td><label id="idScnQty"></label></td>
							<td>
								<button id="idresetQty" loxiaType="button">重置</button></td>
						</tr>
						<tr>
							<td class="label" colspan="2">相关单据号/作业单号</td>
							<td colspan="2"><input loxiaType="input" name="slipCode"
								id="idSlipCode" trim="true"
								style="height: 34px; font-size: 32px;" /></td>
						</tr>
						<tr>
							<td class="label" colspan="2">快递单号</td>
							<td colspan="2"><input loxiaType="input" name="txtTkNo"
								id="txtTkNo" trim="true" style="height: 34px; font-size: 32px;" />
							</td>
						</tr>
						<tr>
							<td id="tknoErrorMsg" colspan="4" align="center"
								style="color: red;"></td>
						</tr>
					</table>
				</td>
				<td>
				<div class="photoInfo" id="opencvImgDiv">
					<%@include file="../common/include-opencvimg.jsp"%>
				</div>	
				</td>
			</tr>
		</table>
		<div class="buttonlist" align="center">
			<button loxiaType="button" class="confirm" id="btnConfirm">核对</button>
			确认条码：<input id="confirmBarCode" loxiaType="input" style="width: 100px" trim="true"/>
			<button loxiaType="button" class="confirm" id="newCamera">照片重拍</button>
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
	<%@include file="../common/include-opencv.jsp"%>
</body>
</html>
