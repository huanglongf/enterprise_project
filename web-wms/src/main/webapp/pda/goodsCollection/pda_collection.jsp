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
			<div class="admin-content">
				<div><font id="msg" color="red"></font></div>
				<div class="am-tabs " data-am-tabs="" id="picking1">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div  class="input-label am-text-right">扫描周转箱</div>
								<div class="input-element-frame">
									<input type="text" class="input-element" name="boxCode" id="boxCode" required />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="boxCodeOk">确认</button>
							<button type="button" class="am-btn am-btn-primary  " id="boxBack">返回</button>
						</div>
					</div>
					
				</div>
				<div class="am-tabs " data-am-tabs="" id="picking2">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div  style="font-size:15px;" >周转箱：<font id="zzx" style="font-size:17px;"></font></div>
							</div>
							<div class="am-g">
								<div  style="font-size:15px;" >集货库位：<font id="collection" style="font-size:17px;"></font></div>
							</div>
							<div class="am-g">
								<div class="input-label am-text-right">扫集货库位</div>
							<div class="input-element-frame">
								<input type="text"  class="input-element" name="collectionCode" id="collectionCode" required />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="collectionCodeOk">确认</button>
							<button type="button" class="am-btn am-btn-primary  " id="collectionCodeBack">返回</button>
						</div>
					</div>
					
				</div>
				<div class="am-tabs " data-am-tabs="" id="picking3">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div  style="font-size:15px;" >当前批次[<font id="pickingCode" style="font-size:17px;" color="red"></font>]已集满，可移走进行分拣工作，是否现在移走？</div>
							</div>
							
							<div class="am-g">
								<div class="input-label am-text-right">扫描OK移走</div>
								<div class="input-element-frame">
									<input type="text"  class="input-element" name="move" id="move" required />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="moveOk">确认</button>
							<button type="button" class="am-btn am-btn-primary  " id="moveBack">跳过</button>
						</div>
						
					</div>
					
				</div>
				
				
			</div>
			<%@include file="/pda/commons/common-footer.jsp"%>
		</div>
		<%@include file="/pda/commons/common-modal.jsp"%>
		<%@include file="/pda/commons/common-play.jsp"%>
		<%@include file="/pda/commons/common-script.jsp"%>
		<script type="text/javascript" src="./goodsCollection/pda_collection.js"></script>
		<script type="text/javascript" src="./shelves/pda_zoomout.js"></script>
		
</body>
</html>