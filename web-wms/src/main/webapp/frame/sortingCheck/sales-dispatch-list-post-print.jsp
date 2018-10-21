<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<style>
			.clear{
				clear:both;
				height:0;
			    line-height:0;
			}
			.photoInfo{
				float:right;
				margin-left:20px;
				width:130px;
			}
		</style>
		<%@include file="/common/meta.jsp"%>
		<title>多件核对（后置送货单）</title>
		<script type="text/javascript" src="<s:url value='/frame/sortingCheck/sales-dispatch-list-post-print.js"' includeParams='none' encode='false'/>"></script>
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<s:text id="pselect" name="label.please.select"/>
		<!-- 配货清单列表 -->
		<div id="showList">
			<div>
			    <form id="plForm" name="plForm">
			    	<input type="hidden" name="cmd.sortingModeInt" value="2"/>
					<table width="70%">
						<tr>
							<td class="label" width="5%"><s:text name="label.warehouse.pl.whname"/></td>
							<td colspan="3" id="whName" width="90%"></td>
						</tr>
						<tr>
							<td class="label"><s:text name="label.warehouse.pl.batchno"/></td>
							<td width="16%"><input loxiaType="input" name="cmd.code" id="code" trim="true"/></td>
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
					<div class="clear"></div>
				</form>
				<div class="buttonlist">
					<button id="search" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
					<button id="reset" type="reset" loxiaType="button"><s:text name="button.reset"/></button>
				</div>
			</div>
			<table>
				<tr>
					<td class="label">批次号扫描：</td>
					<td><input id="iptPlCode" name="iptPlCode" class="refSlipCode" type="text" style="width: 200px;height:26px;font-size:24px;" loxiaType="input" trim="true"></input></td>
				</tr>
			</table>
			<table id="tbl-dispatch-list"></table>
			<div id="pager"></div>
		</div>
		<!-- 详情列表 -->
		<div id="showDetail" class="hidden">
			<table style = "margin-left:10px;">
				<tr>
					<td><b>配货批次号:</b></td>
					<td><label id="verifyCode"></label></td>
					<td width="50px"></td>
					<td><b>状态:</b></td>
					<td ><label id="verifyStatus"></label></td>
				</tr>
				<tr>
					<td><b>计划配货单据数:</b></td>
					<td><label id="verifyPlanBillCount"></label></td>
					<td width="50px"></td>
					<td><b>已核对单据数:</b></td>
					<td ><label id="verifyCheckedBillCount"></label></td>
				</tr>
				<tr>
					<td><b>计划配货商品件数:</b></td>
					<td><label id="verifyPlanSkuQty"></label></td>
					<td width="50px"></td>
					<td><b>已核对商品件数:</b></td>
					<td ><label id="verifyCheckedSkuQty"></label></td>
				</tr>
			</table>
			<div class="buttonlist" >
			</div>
			<div style="margin-bottom: 10px;margin-left:10px;" >
				<table>
					<tr>
						<td><label><b>条码扫描:</b></label></td>
						<td ><input loxiaType="input" id="barCode" name="barCode"  style="width: 200px;height:26px;font-size:24px;" trim="true"></input></td>
						<td><label id = "snNum"  style = "display:none;color:red;"><b>SN号：</b></label></td>
						<td><input loxiaType="input" id="snNumber"  style = "display:none; width: 200px;height:26px;font-size:24px;" name="snNumber"  trim="true"></input></td>		
						<td><button id="finish" type="button" loxiaType="button" class="confirm" >扫描完成</button></td>
						<td><button id="backToPlList" loxiaType="button" ><s:text name="button.toback"/></button></td>
					</tr>
				</table>
			</div>
			<div id="opencvImgDiv" style = "position:absolute; left:75%; top:16%">
					<%@include file="../../common/include-opencvimgmulti.jsp"%>
			</div>
			<table id="tbl-showReady"></table>
			<br><br><br><br><br><br><br>
			<table id="tbl-showDetail"></table>
		</div>
		<div id="dialog_msg" >
			<table>
				<tr style="font-size: 21px;">
					<td align="left" width="130px">相关单据号:</td>
					<td width="220px"><label id="slipCode"></label></td>
					<td align="left" width="120px">作业单号:</td>
					<td width="220px"><label id="staCode"></label></td>
				</tr>
				<tr>
					<td align="left" width="130px"  style="font-size: 21px;">箱号:</td>
					<td width="220px" style="color: red;font-size: 32px;"><label id="boxNum"></label></td>
					<td colspan = "2"><button id="printTrunkPackingList" type="button" loxiaType="button" class="confirm" >打印装箱清单</button></td>
				</tr>
				<tr>
					<td align="left" width="130px"  style="font-size: 21px;">快递单号:</td>
					<td width="220px"  style="font-size: 21px;"><label id="expressBill"></label></td>
					<td colspan = "2"><button id="printDeliveryInfo" type="button" loxiaType="button" class="confirm" >打印物流面单</button></td>
				</tr>
			</table>
			<table id="tbl-trank-button" style="margin-top:20px;">
					<tr height = "38px">
						<td  valign="top">
							<button loxiaType="button" class="confirm" id="addTrank">新增运单</button>
						</td>
					</tr>
					<tr>
						<td>
							<table id="tbl-trank-list" width="500px"  style="text-align: center;" border="1px" cellpadding="0px" cellspacing="0px">
								<tr>
									<td class="label" height="30" style="text-align: center;">拆包运单号</td>
									<td class="label" height="30" style="text-align: center;"><s:text name="label.warehouse.pl.sta.operation"/></td>
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
	<div id="dialog_error_ok">
		<div id="error_text_ok" ></div>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="dialog_error_close_ok">删除并重新扫描</button>
			<button type="button" loxiaType="button" id="dialog_error_close">关闭</button>
		</div>   
	</div>
	<div id="dialog_sure_ok">
		<div id="dialog_text_ok" ></div>
		<div class="buttonlist">
			<button type="button" loxiaType="button"  class="confirm" id="dialog_sure_button">确认并直接核对</button>
			确认条码：<input id="confirmBarCode2" loxiaType="input" style="width: 100px" trim="true"/>
			<button type="button" loxiaType="button" id="dialog_sure_delete">删除并重新扫描</button>
		</div>   
	</div>
	<%@include file="../../common/include-opencv.jsp"%>
	</body>
	
</html>