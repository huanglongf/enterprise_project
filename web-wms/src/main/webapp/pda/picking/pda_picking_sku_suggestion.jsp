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
				<div><font id="msg" color="red" style="font-size:20px;"></font></div>
				<div class="am-tabs " data-am-tabs="" id="picking1">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div  style="font-size:15px;">配货批次\周转箱\拣货条码</div>
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
				<div class="am-tabs " data-am-tabs="" id="picking2">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div  style="font-size:15px;" >配货批次号[<font id="pickingCode" style="font-size:17px;"></font>]
								      
								</div>
							</div>
							
							<div class="am-g">
								<div style="font-size:15px;">商品条码:</div>
								<div class="input-element-frame">
								<input type="text"  class="input-element" name="skuBarCode" id="skuBarCode" required />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="skuBarCodeOk">确认</button>
							<button type="button" class="am-btn am-btn-primary  " id="skuBarCodeBack">返回</button>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="pickingOver">强制分拣完成</button>
						</div>
					</div>
					
				</div>
				<div class="am-tabs " data-am-tabs="" id="picking3">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div  style="font-size:15px;" >货格号<font id="ruleCode3" style="font-size:25px;" color="red"></font></div>
							</div>
							<div class="am-g">
								<div  style="font-size:15px;" >订单号<font id="staCode3" style="font-size:17px;" color="red"></font></div>
							</div>
							<div class="am-g">
								<div  style="font-size:15px;" >配货批次号<font id="pickingListCode3" style="font-size:17px;" color="red"></font></div>
							</div>
							<div class="am-g">
								<div  style="font-size:15px;" >商品条码<font id="showSkuBarCode3" style="font-size:17px;" color="red"></font></div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
									<input type="text"  class="input-element" name="picking3Inp" id="picking3Inp" required />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary" id="picking3Ok">确认</button>
						</div>
						
					</div>
					
				</div>
				
				<div class="am-tabs " data-am-tabs="" id="picking4">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div  style="font-size:15px;" >货格号
									<font id="ruleCode4" style="font-size:25px;" color="red"></font>
									<font style="font-size:20px;" color="red">分拣完成</font>
								</div>
							</div>
							<div class="am-g">
								<div  style="font-size:15px;" >订单号<font id="staCode4" style="font-size:17px;" color="red"></font></div>
							</div>
							<div class="am-g">
								<div  style="font-size:15px;" >配货批次号<font id="pickingListCode4" style="font-size:17px;" color="red"></font></div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
									<input type="text"  class="input-element" name="picking4Inp" id="picking4Inp" required />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary" id="picking4Ok">确认</button>
						</div>
						
					</div>
					
				</div>
				
				<div class="am-tabs " data-am-tabs="" id="picking5">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div  style="font-size:15px;" >配货批次号<font id="pickingListCode5" style="font-size:17px;" color="red"></font></div>
							</div>
							<div class="am-g">
								<div  style="font-size:20px;" >分拣完成</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
									<input type="text"  class="input-element" name="picking5Inp" id="picking5Inp" required />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary" id="picking5Ok">确认</button>
						</div>
					</div>
					
				</div>
				
				<div class="am-tabs " data-am-tabs="" id="picking6">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div  style="font-size:15px;" ><font style="font-size:17px;" color="red">未分拣完成数据</font></div>
							</div>
							<div>
								<table class="am-table am-table-bordered" style="width:200px;">
									<thead>
										<tr style="font-size:13px;">
											<td  style="width: 100px;">货格号</td>
											<td style="width: 100px;">件数</td>
										</tr>
									</thead>
									<tbody id="notOverQtyList"></tbody>
								</table>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary" id="picking6Ok">确认强制完成</button>
							<button type="button" class="am-btn am-btn-primary" id="picking6No">取消</button>
						</div>
					</div>
					
				</div>
			
				
				
			</div>
			<%@include file="/pda/commons/common-footer.jsp"%>
		</div>
		<%@include file="/pda/commons/common-modal.jsp"%>
		<%@include file="/pda/commons/common-play.jsp"%>
		<%@include file="/pda/commons/common-script.jsp"%>
		<script type="text/javascript" src="./picking/pda_picking_sku_suggestion.js"></script>
		<script type="text/javascript" src="./shelves/pda_zoomout.js"></script>
		
</body>
</html>