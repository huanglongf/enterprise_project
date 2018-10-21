<%@include file="/pda/commons/common.jsp"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html class="js cssanimations">
<head>
<title>WMS3.0 PDA</title>
<%@include file="/pda/commons/common-meta.jsp"%>
<%@include file="/pda/commons/common-css.jsp"%>
</head>
<body contextpath="<%=request.getContextPath() %>">
	<div class="body-all">
		<%@include file="/pda/commons/common-header.jsp"%>
		<div class="am-cf admin-main">
			<div class="admin-content">
			<div><font id="msg" color="red"></font></div>
				<div class="am-tabs " data-am-tabs="" id="receive1">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
						    <div  style="font-size:15px;text-align: left;">扫描作业指令/相关单据号(货箱号)</div>
							</div>
							
							<div class="am-g">
								<div class="input-element-frame">
								    <select name="select" id="inventoryStatus">
                                        <option value="良品">良品</option>
                                        <option value="残次品">残次品</option>
                                     </select>
								</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element" id="staCode" />
								</div>
							</div>
							
						</div>
						<div class="btn-center">
							<button type="submit" class="am-btn am-btn-primary btn-right-margin" id="receiveOk">确认</button>
							<button type="button" class="am-btn am-btn-primary " id="receiveBack">返回</button>
						</div>
					</div>
					
				</div>
				
			 </div>	
			<%@include file="/pda/commons/common-footer.jsp"%>
		</div>
		<%@include file="/pda/commons/common-modal.jsp"%>
		<%@include file="/pda/commons/common-play.jsp"%>
		<%@include file="/pda/commons/common-script.jsp"%>
		<script type="text/javascript" src="./receive/pda_receiveByBox.js"></script>
		<script type="text/javascript" src="./shelves/pda_zoomout.js"></script>
		
</body>
</html>