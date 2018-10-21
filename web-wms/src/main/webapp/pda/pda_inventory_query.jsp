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
		<div  id="one" class="am-cf admin-main">
			<div class="admin-content">
				<div><font id="msg" color="red"></font></div>
				<div class="am-tabs " data-am-tabs="" id="picking1">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div  style="font-size:10px;color: red;display: none;" id="errorMsg" name="errorMsg">数据过多，请至web端查看</div>
							</div>
							<div class="am-g">
								<div  style="font-size:15px;">请扫描库位编码：</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
									<input type="text"  class="input-element" name="locationCode" id="locationCode" />
								</div>
							</div>
							<div class="am-g">
								<div  style="font-size:15px;">请扫描sku条码：</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
									<input type="text"  class="input-element" name="barCode" id="barCode" />
								</div>
							</div>
							<div class="am-g">
									<button type="button" class="am-btn am-btn-primary btn-right-margin" id="confirm">确认</button>
									<button type="button" class="am-btn am-btn-primary  " id="collectionCodeBack">返回</button>
							</div>
							</div>
						</div>
					</div>
					
					<div class="am-tabs " data-am-tabs="" id="picking2">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<table style="width:290px;">
									<tr >
										<td style="width:70px;"><div class="input-label am-text-light" >商品条码:</div></td><td align="left"><font id="barCodeValue" style="font-size:15px;"></font></td>
									</tr>
								</table>
							</div>
							<div>
								<table class="am-table am-table-bordered" style="width:290px;">
									<thead>
										<tr style="font-size:12px;">
											<td style="width: 10px;">库位编码</td>
											<td style="width: 10px;">可用库存</td>
											<td style="width: 10px;">占用库存</td>
											<td style="width: 10px;">实际库存</td>
										</tr>
									</thead>
									<tbody id="inventorySku"></tbody>
								</table>
							</div>
							<div class="am-g">
									<button type="button" class="am-btn am-btn-primary  " id="collectionCodeBack1">返回</button>
							</div>
						</div>
					</div>
				</div>
				
					<div class="am-tabs " data-am-tabs="" id="picking3">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<table style="width:290px;">
									<tr >
										<td style="width:70px;"><div class="input-label am-text-light" >库位编码:</div></td><td align="left"><font id="loctionCodeValue" style="font-size:15px;"></font></td>
									</tr>
								</table>
							</div>
							<div>
								<table class="am-table am-table-bordered" style="width:290px;">
									<thead>
										<tr style="font-size:10px;">
											<td style="width: 10px;">库位编码</td>
											<td style="width: 10px;">可用库存</td>
											<td style="width: 10px;">占用库存</td>
											<td style="width: 10px;">实际库存</td>
										</tr>
									</thead>
									<tbody id="inventorySkuLoc"></tbody>
								</table>
							</div>
							<div class="am-g">
									<button type="button" class="am-btn am-btn-primary  " id="collectionCodeBack2">返回</button>
							</div>
						</div>
					</div>
				</div>
					
				</div>
			</div>
			
			<%@include file="/pda/commons/common-footer.jsp"%>
		</div>
		<%@include file="/pda/commons/common-modal.jsp"%>
		<%@include file="/pda/commons/common-play.jsp"%>
		<%@include file="/pda/commons/common-script.jsp"%>
		<script type="text/javascript" src="./pda_inventory_query.js"></script>
		<script type="text/javascript" src="./shelves/pda_zoomout.js"></script>
</body>
</html>