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
								<div  style="font-size:15px;" >当前有<font id="collectionListQty" style="font-size:17px;"></font>个集货库位待释放</div>
							</div>
							<div >
								<table class="am-table am-table-bordered" style="width:200px;">
									<thead>
										<tr style="font-size:13px;">
											<td  style="width: 100px;">集货库位</td>
											<td style="width: 100px;">批次</td>
										</tr>
									</thead>
									<tbody id="overList"></tbody>
								</table>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
									<input type="text"  class="input-element" name="collectionCode" id="collectionCode" required />
								</div>
							</div>
							<div class="am-g">
									<button type="button" class="am-btn am-btn-primary btn-right-margin" id="collectionCodeOk">确认</button>
									<button type="button" class="am-btn am-btn-primary  " id="collectionCodeBack">返回</button>
							</div>
							</div>
						</div>
					</div>
					
				</div>
			</div>
			<div style="display: none;"  id="two"  class="am-cf admin-main">
			<div class="admin-content">
				<div><font id="msg" color="red"></font></div>
				<div class="am-tabs " data-am-tabs="">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div  style="font-size:15px;color: red;" >此批次目前在该库位未集满,确定释放吗？</div>
							</div>
							<div class="am-g">
									<button type="button" class="am-btn am-btn-primary btn-right-margin" id="yes">是</button>
									<button type="button" class="am-btn am-btn-primary  " id="no">否</button>
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
		<script type="text/javascript" src="./goodsCollection/pda_collectionMoveByPickingOver.js"></script>
		<script type="text/javascript" src="./shelves/pda_zoomout.js"></script>
		
</body>
</html>