<%@include file="/pda/commons/common.jsp"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html class="js cssanimations">
<head>

<title>演示</title>
<%@include file="/pda/commons/common-meta.jsp"%>
<%@include file="/pda/commons/common-css.jsp"%>
<!-- 
localhost:8080/wms/pda/pdalogin.do
 -->
</head>
<body>
	<div class="body-all">
		<!-- nav start -->
		<jsp:include page="/pda/commons/common-header.jsp">
			<jsp:param value="容器收货" name="title" />
		</jsp:include>
		<!-- nav end -->
		<div class="am-cf admin-main">
			<!-- sidebar start -->
			<!-- sidebar end -->
			<!-- content start -->
			<div class="admin-content">
				<div class="am-tabs " data-am-tabs="">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
					
						<div class="am-tab-panel am-fade  am-in" id="tab2">
							<form class="am-form" id="preReceipt_asnForm">
								<div class="am-text-left">
									<a id="back" href="#" onclick="javascript:history.go(-1)"><<spring:message code="label.return" /></a>
								</div>
								<div class="am-g">
									<div class="input-label am-text-right"></div>
									<div class="input-element-frame">
										<select class="input-element" id="containerType" name="containerType">
											<option value="container1">周转箱</option>
											<option value="container2">周转盘</option>
										</select>
										<!-- <input type="text" class="input-element" id= "containerType" name="containerType" required="required"> -->
									</div>
								</div>
								<div class="am-g">
									<div class="input-label am-text-right"></div>
									<div class="input-element-frame">
										<input type="text" class="input-element" id="containerNum" name="containerNum" readonly="readonly" required="required">
									</div>
								</div>
								<div class="am-g">
									<div class="input-label am-text-right"></div>
									<div class="input-element-frame">
										<input type="text" class="input-element" id="containerPlan" name="containerPlan" readonly="readonly" required="required">
									</div>
								</div>
								<div class="am-g">
									<div class="input-label am-text-right"></div>
									<div class="input-element-frame">
										<input type="text" class="input-element" id="containerCode" name="containerCode" autofocus="autofocus" required="required">
									</div>
								</div>
								<div class="am-g">
									<div class="input-label am-text-right"></div>
									<div class="input-element-frame">
										<input type="text" class="input-element" id="containerNum2" name="containerNum2" readonly="readonly" required="required">
									</div>
								</div>
								<!-- <br /> -->
								<div class="btn-center">
									<a class="am-btn am-btn-primary btn-right-margin" id="preReceipt_asn_save" href="/pages/containerReceiving/inventoryAttr-Valid.jsp">222</a>
									<button type="button" class="am-btn am-btn-primary " id="quit_Reset">11</button>
								</div>
							</form>
						</div>
				
					</div>
				</div>
				<!-- content end -->
			</div>
			<!-- footer -->
			<%@include file="/pda/commons/common-footer.jsp"%>
			<!-- footer end -->
		</div>
		<!-- modal start -->
		<%@include file="/pda/commons/common-modal.jsp"%>
		<%@include file="/pda/commons/common-play.jsp"%>
		<!-- modal end -->

		<%@include file="/pda/commons/common-script.jsp"%>
		<%-- <script src="${staticbase }/scripts/pages/pdaPreReceipt/commonPreReceipt-Asn.js?${version}"></script> --%>
		<script type="text/javascript">
			
		</script>
</body>
</html>