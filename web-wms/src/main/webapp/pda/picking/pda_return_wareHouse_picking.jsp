<%@include file="/pda/commons/common.jsp"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html class="js cssanimations">
<head>
<title>WMS3.0 PDA</title>
<%@include file="/pda/commons/common-meta.jsp"%>
<%@include file="/pda/commons/common-css.jsp"%>
 <style type="text/css">
 .am-table-bordered {
  border: 1px solid #ddd;
  border-left: none;
}
.am-table-bordered > thead > tr > th,
.am-table-bordered > tbody > tr > th,
.am-table-bordered > tfoot > tr > th,
.am-table-bordered > thead > tr > td,
.am-table-bordered > tbody > tr > td,
.am-table-bordered > tfoot > tr > td {
  border-left: 1px solid #ddd;
  /*&:first-child {
          border-left: none;
        }*/
}
.am-table-bordered > tbody > tr:first-child > th,
.am-table-bordered > tbody > tr:first-child > td {
  border-top: none;
}
.am-table-bordered > thead + tbody > tr:first-child > th,
.am-table-bordered > thead + tbody > tr:first-child > td {
  border-top: 1px solid #ddd;
}
 
.am-table-bordered th,
  .am-table-bordered td {
    border: 1px solid #ddd !important;
  }
                     

</style>
</head>
<body contextpath="<%=request.getContextPath() %>">
	<div class="body-all">
		<%@include file="/pda/commons/common-header.jsp"%>
		<div class="am-cf admin-main">
		<div><font id="me" color="red"></font></div>
			<div class="admin-content">
				<div><font id="msg" color="red"></font></div>
				<div><font id="vas" color="red"></font></div>
				<div class="am-tabs " data-am-tabs="" id="picking1">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div  class="input-label am-text-right">扫拣货条码</div>
								<div class="input-element-frame">
									<input type="text" class="input-element" name="pickingBarCode" id="pickingBarCode" required />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="pickingCodeOk">确认</button>
							<button type="button" class="am-btn am-btn-primary  " id="pcBack">返回</button>
						</div>
					</div>
					
				</div>
				<div class="am-tabs " data-am-tabs="" id="picking1_2">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div tyle="font-size:15px;">
								<font color="red">VAS拣货任务</font>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="vasOk">确认</button>
						</div>
					</div>
					
				</div>
				<div class="am-tabs " data-am-tabs="" id="picking2">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div  style="font-size:15px;" >当前小批次[<font id="pickingCode" style="font-size:17px;"></font>]
								      
								</div>
							</div>
							<div class="am-g">
								<div class="input-label am-text-right">扫描周转箱</div>
							<div class="input-element-frame">
								<input type="text"  class="input-element" name="boxCode" id="boxCode" required />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="boxCodeOk">确认</button>
							<button type="button" class="am-btn am-btn-primary  " id="boxBack">返回</button>
						</div>
					</div>
					
				</div>
				<div class="am-tabs " data-am-tabs="" id="picking3">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div  style="font-size:15px;" >当前周转箱[<font id="boxCode1" style="font-size:17px;" color="red"></font>]</div>
							</div>
							<div class="am-g">
								<div  style="font-size:15px;" >已拣货<font id="pickingNum" style="font-size:17px;" color="red"></font>件</div>
							</div>
							<div class="am-g">
								<div  style="font-size:13px;" >请扫描如下库位进行拣货</div>
							</div>
							<div class="am-g">
								<div  ><font id="locCode"  style="font-size:20px;"></font></div>
							</div>
							<div class="am-g">
								<div class="input-label am-text-right">扫描库位号</div>
								<div class="input-element-frame">
									<input type="text"  class="input-element" name="locationCode" id="locationCode" required />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="locationCodeOk">确认</button>
							<button type="button" class="am-btn am-btn-primary  " id="locBack">返回</button>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary" id="pbOver">拣货完成</button>
						</div>
						
					</div>
					
				</div>
				
				<div class="am-tabs " data-am-tabs="" id="picking4">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
						<div>
							<table style="width:290px;">
								<tr >
									<td style="width: 60px;"><div class="am-text-right"  style="font-size:11px;">周转箱:</div></td><td><font id="boxCode2" style="font-size:12px; font-weight:bold;" color="red"></font></td>
								</tr>
								<tr>
									<td><div class="am-text-right"     style="font-size:11px;">库位:</div></td><td><font id="locCode1" style="font-size:12px; font-weight:bold;" color="red"></font></td>
								</tr>
								<tr >
									<td style="width: 60px;"><div class="am-text-right"  style="font-size:10px;">货号:</div></td><td><font id="supplierCode" style="font-size:10px;"></font></td>
								</tr>
								<tr>
									<td><div   class="am-text-right"   style="font-size:10px;">关键属性:</div></td><td><font id="keyProperties" style="font-size:12px; font-weight:bold;" color="red"></font></td>
								</tr>
								<tr>
									<td><div class="am-text-right"    style="font-size:10px;">条形码:</div></td><td><font id="sbc" style="font-size:10px;"></font></td>
								</tr>
								<tr id="skuInvStatusTr">
									<td><div class="am-text-right"    style="font-size:10px;">库存状态:</div></td><td><font id="skuInvStatus" style="font-size:10px;color: red"></font></td>
								</tr>
								<tr>
									<td><div class="am-text-right"    style="font-size:10px;">商品名:</div></td><td><font id="skuName" style="font-size:10px;"></font></td>
								</tr>
								<tr>
									<td><div class="am-text-right"   style="font-size:10px;">失效日期:</div></td><td><font id="expDate" style="font-size:10px;"></font></td>
								</tr>
								<tr>
									<td><div class="am-text-right"   style="font-size:12px;">计数:</div></td><td><strong><font id="qty" style="font-size:17px;" color="red"></font></strong></td>
								</tr>
							</table>
						</div>
							
							<div class="am-g">
								<div class="input-label am-text-right">扫描SKU:</div>
								<div class="input-element-frame">
									<input type="text" class="input-element" name="skuBarCode" id="skuBarCode" required />
								</div>
							</div> 
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="skuBarCodeOk">确认</button>
							<button type="button" class="am-btn am-btn-primary  " id="skuBack">返回</button>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary " id="boxOver">容器拣货完成</button>
						</div>
					</div>
					
				</div>
				
				<div class="am-tabs " data-am-tabs="" id="picking5">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div>
								<table style="width:290px;">
									<tr >
										<td style="width: 160px;"><div class="am-text-right"  style="font-size:15px;">该库位此商品应拣数量:</div></td><td align="center"><font id="sortPqty" style="font-size:16px;"></font></td>
									</tr>
									<tr >
										<td><div class="am-text-right"  style="font-size:15px;">当前仅拣数量:</div></td><td><font id="sortSqty" style="font-size:16px;"></font></td>
									</tr>
									<tr >
										<td ><div class="am-text-right"  style="font-size:15px;">确定短拣数量:</div></td><td><font id="sortQqty" style="font-size:16px;"></font></td>
									</tr>
								</table>
							</div>
							
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="skuShortOK">确认短拣</button>
							<button type="button" class="am-btn am-btn-primary " id="skuShortNO">继续拣货</button>
						</div>
					</div>
					
				</div>
		
				
				<div class="am-tabs " data-am-tabs="" id="picking6">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<table style="width:290px;">
									<tr >
										<td style="width:100px;"><div class="input-label am-text-light" >拣货条码:</div></td><td align="left"><font id="pbCode" style="font-size:15px;"></font></td>
									</tr>
								</table>
							</div>
							<div>
								<table class="am-table am-table-bordered" style="width:290px;">
									<thead>
										<tr style="font-size:13px;">
											<td  style="width: 80px;">库位条码</td>
											<td style="width: 80px;">商品条码</td>
											<td style="width: 70px;">货号</td>
											<td style="width: 60px;">短拣数量</td>
										</tr>
									</thead>
									<tbody id="pbSortTable"></tbody>
								</table>
							</div>
							
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="countBoxQtyOk">确认短拣</button>
							<button type="button" class="am-btn am-btn-primary " id="pbShortNO">继续拣货</button>
						</div>
					</div>
					
				</div>
				
				<div class="am-tabs " data-am-tabs="" id="picking7">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div  style="font-size:15px;" >请扫描该小批次下任一相关单据号</div>
							</div>
							<div class="am-g">
								<div  class="input-label am-text-right">相关单据号</div>
								<div class="input-element-frame">
									<input type="text" class="input-element" name="slipCode" id="slipCode" required />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="slipCodeOK">确认</button>
							<button type="button" class="am-btn am-btn-primary " id="slipCodeNO">取消</button>
						</div>
					</div>
					
				</div>
				<div class="am-tabs " data-am-tabs="" id="picking9">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div  style="font-size:15px;" >补绑周转箱</div>
							</div>
							<div class="am-g">
								<!-- <div  class="input-label am-text-right">箱号</div> -->
								<div class="input-element-frame">
									<input type="text" class="input-element" name="newBoxCode" id="newBoxCode" required />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="newBoxCodeOK">确认</button>
							<button type="button" class="am-btn am-btn-primary " id="newBoxCodeNO">取消</button>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="newBoxCodeOver">补绑完成</button>
						</div>
					</div>
					
				</div>
				
				
				<div class="am-tabs " data-am-tabs="" id="picking10">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<table style="width:290px;">
									<tr >
										<td style="width:100px;"><div class="input-label am-text-light" >拣货条码:</div></td><td align="left"><font id="pbCode1" style="font-size:15px;" color="red"></font></td>
									</tr>
									<tr >
										<td style="width:100px;"><div class="input-label am-text-light" >作业单号:</div></td><td align="left"><font id="staCode" style="font-size:15px;" color="red"></font></td>
									</tr>
								</table>
							</div>
							<div>
								<div class="am-g">
								<div  style="font-size:10px;" >本次短拣任务共短拣<font id="pbShortCount" style="font-size:10px;" color="red"></font>件</div>
							</div>
							<div class="am-g">
								<div  style="font-size:10px;" ><a id="showShortDiv" href="#" onclick="">点击链接查看明细</a><font id="pickingNum" style="font-size:10px;" color="red"></font></div>
							</div>
							</div>
							
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="countBoxQtyOk1">确认短拣</button>
							<button type="button" class="am-btn am-btn-primary " id="pbShortNO1">继续拣货</button>
						</div>
					</div>
					
				</div>
			</div>
			<%@include file="/pda/commons/common-footer.jsp"%>
		</div>
		<%@include file="/pda/commons/common-modal.jsp"%>
		<%@include file="/pda/commons/common-play.jsp"%>
		<%@include file="/pda/commons/common-script.jsp"%>
		<script type="text/javascript" src="./picking/pda_return_wareHouse_picking.js"></script>
		<script type="text/javascript" src="./shelves/pda_zoomout.js"></script>
		
</body>
</html>