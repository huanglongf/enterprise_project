<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="<s:url value='/css/bootstrap-theme.min.css' includeParams='none' encode='false'/>" />
<link rel="stylesheet" type="text/css" href="<s:url value='/css/bootstrap.min.css' includeParams='none' encode='false'/>" />
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/main/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/main/jquery-barcode.js"></script>
<style>
/* .barcodeImg{
margin:10px 0px
}; */
body {
	background-color: #c6edcc;
}

td {
	word-break: break-all;
	word-wrap: break-word;
}

.txtShow {
	font-size: 32px;
	border: 2px solid #390;
	margin: 0 0 0 0;
	padding: 0 0 0 0;
	color: blue;
	width: 75px;
}

.scan {
	font-size: 28pt;
	border: 2px solid #390;
	margin: 0 0 0 0;
	padding: 0 0 0 0;
	color: blue;
	width: 350px;
}

.scan_ok {
	font-size: 22pt;
	border: 2px solid #390;
	color: blue;
	width: 110px;
	height:50px;
}

.font-blue {
	color: blue;
}
.tr{
	width:100%;
	text-align:right;
}
.font-red {
	color: red;
}

.font-M {
	font-size: 18px;
}
#step1_2 p,#step2_4 div{
	margin:0;
	pargin:0;
	width:100%;
	float:left;
}
#step1_2 p input,#step2_4 div input{
	float:left;
}
#step1_2 p button,#step2_4 div button{
	float:left;
	margin-left:5px;
	margin-top:10px;
}
.fl{
	float:left;
	width:40%;
	text-align:right;
}
.fr{
	float:left;
	width:50%;
	text-align:left;
}
.font-L {
	font-size: 30px;
}

.font-XL {
	font-size: 64px;
}

.top_pane {
	padding-top: 20px;
	width: 430px;
	height: 260px;
	float: left;
	border: 1px solid #1c4587;
}

.top_pane_weight {
	padding-top: 20px;
	width: 430px;
	height: 200px;
	float: left;
	border: 1px solid #1c4587;
	width: 430px;
}

.ui-jqgrid .ui-jqgrid-bdiv .ui-state-highlight {
	background: white none repeat scroll 0 0;
}

.ui-state-highlight, .ui-widget-content .ui-state-highlight, .ui-widget-header .ui-state-highlight {
	background: white;
	border: 0 none;
	color: black;
	border: 0 none;
}
</style>
<title>多件出库</title>
<script type="text/javascript" src="<s:url value='/frame/salesoutbound/skus_outbound_customs.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body contextpath="<%=request.getContextPath()%>">
<input id="isHaoCai" type="hidden" value="" />
<input id="dialog_is_pre2" type="hidden" value="0" />
	<div id="dialogMsg" align="center">
		<div id="errorMsg" class="alert alert-danger font-L"></div>
		<table>
			<tr>
				<td valign="middle">
					<button id="btnErrorOk" type="button" class="btn btn-primary">确认</button>
				</td>
				<td valign="middle">
					<input type="text" id="txtErrorOk" class="scan_ok" size="12" style="font-size: 15pt;" placeholder="请扫描OK..."></input>
				</td>
			</tr>
		</table>
	</div>

	<div id="dialogInfoMsg" align="center">
		<div id="infoMsg" class="alert alert-success font-L"></div>
		<table>
			<tr>
				<td valign="middle">
					<button id="btnInfoOk" type="button" class="btn btn-primary">确认</button>
				</td>
				<td valign="middle">
					<input type="text" id="txtInfoOk" class="scan_ok" size="12" style="font-size: 15pt;" placeholder="请扫描OK..."></input>
				</td>
			</tr>
		</table>
	</div>

	<%
		//step1  复核
		//step2 称重
		//step3 交接
	%>
	<div id="menuSelect" style="width:500px;height:200px;margin:0 auto;text-align:center;margin-top:100px;border:1px solid #ccc;border-radius:5px;box-shadow: 0px 0px 20px #111111;">
		<p style="font-size:20px;margin:30px auto;">请选择你的操作权限</p>
		<label><input name="menu" type="checkbox" id="isC"/>核对</label>
		<label><input name="menu" type="checkbox" id="isW"/>称重出库</label>
		<label><input name="menu" type="checkbox" id="isH"/>交接</label>
		<br/>
		<button id="menuConfirm" class="btn btn-primary btn-XL" type="button">确认</button>
	</div>
	<div id="step1" step="1">
		<table style="border: 0; margin-top: 200px;" align="center">
			<tr>
				<td valign="middle">
					<label style="font-size: 32px">周转箱/相关单据号/批次号：</label>
					<input type="text" id="txtOrder" class="scan" size="30" index="1_1" placeholder=""></input>
				</td>
				<td>
					<button id="btnOrder" type="button" class="btn btn-primary btn-l">确认</button>
				</td>
			</tr>
		</table>
	</div>
	<div id="step1_1" step="1_1">
		<div style="height: 250px; width: 1300px; margin: 0 auto;" align="center">
			<div id="panOrder" class="top_pane">
				<table>
					<tr>
						<td width="120px"><label class="font-M tr">波次号:</label></td>
						<td width="305px"><label id="labPicking" class="font-M">X000000sss00</label></td>
					</tr>
					<tr>
						<td><label class="font-M tr">平台订单号:</label></td>
						<td><label id="labSlipCode1" class="font-M">1200000000001</label><button id="btnPrintPick" type="button" class="btn btn-primary btn-sm hidden">装箱单补打</button></td>
					</tr>
					<tr>
						<td><label class="font-M tr">快递:</label></td>
						<td><label id="labLpcode" class="font-M">STO</label>
							<button id="btnSplitPg" type="button" class="btn btn-primary btn-sm hidden">拆包</button></td>
					</tr>
					<tr>
						<td valign="top">
							<div id="paneTrans" style="position: absolute; height: 114px; overflow: auto;">
								<table id="tabTrans" class="table table-condensed">
									<tr>
										<th width="75px">重量</th>
										<th width="180px">运单号</th>
										<th>打印</th>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div id="panTrans" class="top_pane">
				<table>
					<tr>
						<td width="100px" height="70px"><label class="font-M tr">订单号:</label></td>
						<td width="325px"><label id="labSlipCode" data="" class="font-L font-blue">X000sssssssss</label></td>
					</tr>
					<tr>
						<td height="70px"><label class="font-M tr">运单号:</label></td>
						<td><label id="labTransNo" class="font-L font-blue">08998998224</label>
							<!-- 
							<button type="button" id="printParent" class="btn btn-info btn-sm">打印</button>
							 -->
						</td>
					</tr>
					<tr>
						<td style="height:70px"><label class="font-M tr">箱号:</label></td>
						<td><label id="labPgIndex" class="font-XL font-red">10</label></td>
					</tr>
				</table>
			</div>
			<div id="panImage" align="center" class="top_pane" style="padding-top:10px;">
				<%@include file="../../common/include-opencvimgmulti.jsp"%>
			</div>
		</div>
		<div align="center" style="width:1300px;margin:20px auto;overflow:hidden">
			<div id="step1_2" step="1_2">
				<p id="P_1">
				<table>
					<tr>
						<td style="width:25%"><label style="font-size: 30px;float:right" >商品：</label></td>
						<td><input id="txtScanSku" type="text" index="1_2" class="scan" /></td>
						<td><select id="origin" loxiaType="select" name="origin" class="scan" style="width:300px">
							<option value="">请选择</option>
						</select></td>
						<td><button id="btnScanSku" type="button" class="btn btn-primary btn-XL">确认</button></td>
						<td><button id="btnShort" type="button" class="btn btn-danger btn-XL">报缺</button></td>
						<td><button id="btnPartly" type="button" class="btn btn-info btn-XL">部分发货</button></td>
					</tr>
					</table>
					
				</p>
				<p id="P_2">
					<label class="font-L fl">商品：</label><label id="labScanBarcode" class="font-L fr"></label>
				</p>
				<p id="P_3">
					<label class="font-L fl">SN：</label><input id="txtScanSn" index="1_3" type="text" index="2" class="scan" /></label>
					<button id="btnScanSn" type="button" class="btn btn-primary btn-XL">确认</button>
				</p>
				<p id="P_4">
					<label class="font-L fl">SN：</label><label id="labScanSn" class="font-L fr"></label>
				</p>
				<p id="P_5">
					<label class="font-L fl">箱号：</label><label id="labScanIndex" class="font-L fr" style="color:red;"></label>
				</p>
				<p id="P_6">
					<label class="font-L fl" style="color:red;">系统推荐耗材：</label><label id="labScanCM" class="font-L fr" style="color:red;"></label>
				</p>
				<p id="P_7">
					<label class="font-L fl">相关单据号/作业单号：</label><input id="txtScanSlipCode" index="1_4" type="text" index="2" class="scan" /></label>
					<button id="btnScanSlipCode" type="button" class="btn btn-primary btn-XL">确认</button>
					<button id="btnSkip" type="button" class="btn btn-danger btn-XL">跳过</button>
				</p>
				<p id="P_8">
					<label class="font-L fl">相关单据号/作业单号：</label><label id="labScanSlipCode" class="font-L fr"></label>
				</p>
				<p id="P_9">
					<label class="font-L fl">快递单号：</label><input id="txtScanTransNo" index="1_5" type="text" index="2" class="scan" /></label>
					<button id="btnScanTranNo" type="button" class="btn btn-primary btn-XL">确认</button>
				</p>
			</div>
		</div>
		<div align="center">
			<table width="1300px">
				<tr>
					<td>
						<div style="position: absolute; height: 300px; width: 1300px; overflow: auto;">
							<p>
								<table id="to_check_list" class="table table-striped"></table>
							</p>
							<p>
								<table id="checked_list" class="table table-striped"></table>
							</p>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>

	<div id="step2" step="2">
		<table style="border: 0; margin-top: 200px;" align="center">
			<tr>
				<td valign="middle">
					<label style="font-size: 32px">运单号：</label>
					<input type="text" id="txtTransNo" class="scan" size="30" index="2_1" placeholder="请扫描运单号..."></input>
				</td>
				<td>
					<button id="btnTransNo" type="button" class="btn btn-primary btn-XL">确认</button>
				</td>
			</tr>
		</table>
	</div>

	<div id="step2_1" step="2_1">
		<div style="height: 250px; width: 900px; margin: 0 auto;" align="center">
			<div id="panWeightOrder" class="top_pane_weight">
				<table>
					<tr>
						<td><label class="font-M">订单号:</label></td>
						<td><label id="labWeightSlipCode" class="font-M">X000sssssssss</label></td>
					</tr>
					<tr>
						<td><label class="font-M">运单号:</label></td>
						<td><label id="labWeightTransNo" class="font-L font-blue" data="">08998998224</label></td>
					</tr>
					<tr>
						<td><label class="font-M">快递:</label></td>
						<td><label id="labWeightLpcode" class="font-L">STO</label></td>
					</tr>
				</table>
					<div id="dialog_is_pre" style="display:none;">
		 				<span style="color:red;font-size:17px;font-weight:bold;">此订单是预售订单</span>
					</div>
			</div>
			<div id="panWeight" class="top_pane_weight">
				<table>
					<tr>
						<td><label class="font-M">称读数(KG)：</label></td>
						<td><input id="autoWeigth" type="text" readonly="" class="txtShow"/></td>
						<td>
							<button type="button" class="btn btn-primary btn-l" id="restartWeight">重启称</button>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- 
		<div id="step2_3" step="2_3" align="center">
			<table>
				<tr>
					<td><label class="font-L">耗材：</label></td>
					<td><input id="txtScanMate" type="text" index="2_3" class="scan" /></label>
						<button id="btnScanMate" type="button" class="btn btn-primary btn-XL">确认</button></td>
				</tr>
			</table>
		</div> -->
		<div id="step2_4" step="2_4" style="height: 400px; width: 900px; margin: 0 auto;" align="center">
			<div id="step2_4_0">
				<label class="font-L fl">耗材：</label><input id="txtScanMate" type="text" index="2_3" class="scan" />
				<button id="btnScanMate" type="button" class="btn btn-primary btn-XL">确认</button>
			</div>
			<div id="step2_4_1">
				<label class="font-L fl">耗材：</label><label id="labMate" class="font-L fr"></label>
			</div>
			<div id="step2_4_2">
				<label class="font-L fl">手动称重：</label><input id="txtInputWeight" type="text" index="2_4_2" class="scan" />
				<button id="btnInputWeight" type="button" class="btn btn-primary btn-XL">确认</button>
			</div>
			<div id="step2_4_5">
				<label class="font-L fl">重量确认：</label><input id="txtWightOK" type="text" index="2_4_5" class="scan" />
				<button id="btnWightOK" type="button" class="btn btn-primary btn-XL">确认</button>
			</div>
			<div id="step2_4_3">
				<label class="font-L fl">货物重量：</label><label id="labEditWeight" class="font-L fr" style="width:100px;">X0000001</label>
				<button id="btnEditWeight" type="button" class="btn btn-primary btn-XL">修改重量</button>
			</div>
			<div id="step2_4_4">
				<label class="font-L fl">确认出库：</label><input type="text" id="txtOutboundOk" index="2_5" class="scan_ok" size="12" style="font-size: 15pt;" placeholder="请扫描OK..."/>
				<button id="btnOutboundOk" type="button" class="btn btn-primary">出库</button>
			</div>
		</div>
		<!-- 
		<div id="step2_5" step="2_5" align="center">
			<table>
				<tr>
					<td align="right"><label class="font-L">耗材：</label></td>
					<td><label id="labMate25" class="font-L">X0000001</label></td>
				</tr>
				<tr>
					<td align="right"><label class="font-L">重量(KG)：</label></td>
					<td><label id="labWeight" class="font-XL font-blue">1.4</label></td>
				</tr>
				<tr>
					<td valign="top" align="right">
						<button id="btnOutboundOk" type="button" class="btn btn-primary">出库</button>
					</td>
					<td valign="top" align="left"><input type="text" id="txtOutboundOk" index="2_5" class="scan_ok" size="12" style="font-size: 15pt;" placeholder="请扫描OK..."></input></td>
				</tr>
			</table>
		</div>
		 -->
	</div>

	<div id="step3" step="3">
		<table style="border: 0; margin-top: 50px;" align="center">
			<tr>
				<td><label class="font-L">待交接单数:</label><label id="labAllHo" class="font-XL font-blue">100</label>
					<button id="btnHo" type="button" class="btn btn-primary btn-sm">交接</button></td>
			</tr>
			<tr>
				<td>
					<div style="position: absolute; height: 131px; overflow: auto;">
						<table id="tbHo" class="table table-condensed" width="400px">
							<tr>
								<th><input type="checkbox" id="selectAll" /></th>
								<th width="120px">快递</th>
								<th width="120px">待交接数</th>
							</tr>
							<tr>
								<td><input type="checkbox" /></td>
								<td><label name="labLpcode" class="font-M">SF</label></td>
								<td><label name="labToHoQty" class="font-M">100</label></td>
							</tr>
							<tr>
								<td><input type="checkbox" /></td>
								<td><label name="labLpcode" class="font-M">STO</label></td>
								<td><label name="labToHoQty" class="font-M">60</label></td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</div>
  <div id="dialog_gift">
         	<table>
	  	 		<tr id='tr_memo'>
	  	 			<td colspan="2">
	  	 			<textarea   id="gift_memo" style="width: 400px;height: 50px;"></textarea><br/>
	  	 			<div id="bcTarget" class="barcodeImg"></div>	
	  	 			
	  	 			</td>
	  	 		</tr>
	  	 		<tr>
	  	 			<td colspan="2" id='23'>
	  	 				<button type="button" loxiaType="button" id="PASS" class="confirm" >处理完成 </button>PASS:<input id="PASS1" loxiaType="input" trim="true" style="width: 50px; height: 30px; line-height: 30px;"/>
	  	 				<button type="button" loxiaType="button" id="OK" class="confirm" >复制        </button>OK:<input id="OK1" loxiaType="input" trim="true" style="width: 50px; height: 30px; line-height: 30px;"/>
	  	 			
	  	 			</td>
	  	 		</tr>
	  	 	</table>
  </div>
</body>
	<%@include file="../../common/include-opencv.jsp"%>
</html>