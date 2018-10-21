<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/checkoutbound/seckill-checkoutbound.js' includeParams='none' encode='false'/>"></script>
<style>
.showborder {
	border: thin;
}
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
	font-size: 18px; color:#000;text-align:right;font-weight:bold;
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
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="div1">
	<div>
		<form action="" id="queryForm">
			<table>
				<tr>
					<td class="label">创建时间:</td>
					<td colspan="3"><input loxiaType="date" showTime="true" style="width: 150px" name="fromTime"/>到<input loxiaType="date" showTime="true" style="width: 150px" name="endTime"/></td>
					<td class="label">配货批次号:</td>
					<td><input loxiaType="input" name="pickingList.code" trim="true"/></td>
				</tr>
				<tr>
					<td class="label">状态:</td>
					<td style="width:80px">
						<select loxiaType="select" name="pickingList.status">
							<option value="PACKING">配货中</option>
							<option value="FINISHED">完成</option>
						</select>
					</td>
					<td colspan="2"></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search">查询</button>
			<button loxiaType="button" id="reset">重置</button>
		</div>
	</div>
	<div>
		<span class="label">批次号扫描:</span><input loxiaType="input" style="width:200px" id="pickId"/>
		<br/><br/>
		<table id="pickinglist-table"></table>
		<div id="pager_query"></div>
	</div>
	</div>
	<div id="div2" class="hidden">
		<input loxiaType="input" hidden id="pId"/>
		<table>
			<tr>
				<td class="label2" width="100px">配货批次号:</td>
				<td id="code" width="200px"></td>
				<td class="label2" width="100px">状态:</td>
				<td id="status" width="200px"></td>
			</tr>
			<tr>
				<td class="label2">单据数:</td>
				<td id="planSta"></td>
				<td id="pickListId"></td>
				<td></td>
			</tr>
		</table>
		<div id="div5" class="hidden">
			<table id="cancleStaList"></table>
			<div class="buttonlist">
			<button loxiaType="button" id="reback2">返回</button>
			</div>
		</div>
		<div id="div6" class="hidden">
			<div style="text-align:center;font-size:20px;font-weight:bold;color:red;padding:10px 50px;">当前配货清单中所有作业单已被删除，故配货清单也被删除!</div>
			<div class="buttonlist">
			<button loxiaType="button" id="reback3">返回</button>
			</div>
		</div>
		<div id="div3" class="hidden">
			<div style="margin-bottom: 15px;margin-top: 25px;">
				<button loxiaType="button" id="btnInQty" style="width: 100px;height: 30px" >录入数量</button>
				<input loxiaType="number" value="1" id="inQty" style="width: 60px;height: 30px" /> &nbsp;
				<span class="label" style="height: 30px" >
				 <s:text name="label.warehouse.pl.sta.sku.barcode"/>
				</span>
				<span><input id="barCode" loxiaType="input" style="width: 200px;height: 25px" trim="true"/></span>
			</div>
			<table id="waitcheck"></table>
			
			<table width="50%">
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
				</tr>
				<tr>
					<td width="20%" class="label2">当前重量</td>
					<td width="30%"><input id="autoWeigth" loxiaType="input" style="width: 150px"  class="labelinput" readonly="readonly"/> 
						<button id="restWeight" loxiaType="button">重启称</button>
					</td>
				</tr>
				<tr>
					<td class="label2"><s:text name="label.warehouse.package.weight"></s:text></td>
					<td><input id="goodsWeigth" loxiaType="input" class="labelinput" style="width: 150px" mandatory="true" /> <!--  checkmaster="doExecute" -->
						<s:text name="label.warehouse.package.weight.kg"></s:text>
					</td>
				</tr>
				<tr>
					<td class="label2">自动称重确认条码</td>
					<td><input id="confirmWeightInput" loxiaType="input" class="labelinput" style="width: 150px" mandatory="true" /></td>
				</tr>
			</table>
			<div class="buttonlist">
			<button loxiaType="button" id="reback1">返回</button>
			</div>
		</div>
		<div id="div4" class="hidden">
			<br/>
			<br/>
			<input loxiaType="input" hidden id="handId"/>
			<button loxiaType="button" id="printHand" class="confirm">打印交接清单</button><button loxiaType="button" id="reback">返回</button>
		</div>
	</div>
	<input loxiaType="input" hidden id="flag"/>
	<div id="dialog_error">
	    <div style="width:100%;font-size:18px;font-weight:bold;color:red;text-align:center;padding:20px 0;overflow:hidden;" id="errorText"></div>
	    <button loxiaType="button" class="confirm" id="doCheck0">确认</button>
		确认条码：<input id="barCode0" loxiaType="input" style="width: 100px" trim="true" maxlength="6" />       
	</div>
	<div id="dialog_Show">
	    <div style="width:100%;font-size:18px;font-weight:bold;color:red;text-align:center;padding:20px 0;overflow:hidden;" id="showText"></div>
	    <button loxiaType="button" class="confirm" id="doNothingSure">确认</button>
	</div>
	<div id="dialog_confirm">
		<div style="width:100%;font-size:18px;font-weight:bold;color:red;text-align:center;padding:20px 0;overflow:hidden;">
			<table>
				<tr>
					<td class="label">配货批次号:</td>
					<td id="cpid"></td>
					<td class="label">单据数:</td>
					<td id="csta"></td>
				</tr>
				<tr>
					<td class="label">物流商:</td>
					<td id="cde"></td>
					<td class="label">重量:</td>
					<td id="cweight"></td>
				</tr>
			</table>
		</div>
		<button loxiaType="button" class="confirm" id="confirm1">确认出库</button>
		<button loxiaType="button" id="exitbutton">取消返回</button>
	</div>
</body>