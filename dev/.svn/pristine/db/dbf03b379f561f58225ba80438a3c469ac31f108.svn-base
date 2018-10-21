<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/base/yuriy.jsp"%>
<title>Packaged Composite System</title>
<script type="text/javascript" src="<%=basePath%>My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<script type="text/javascript">
	$(document).ready(function() {
		loadding();
	});
	function closewin() {
		self.opener = null;
		self.close();
	}
	function clock() {
		i = i - 1
		if (i > 0) {
			setTimeout("clock();", 3000);
		} else {
			this.window.opener = null;
			window.close();
		}
	}
	document.getElementById('time').innerHTML = new Date().toLocaleString();
	setInterval(
			"document.getElementByIdx_x('time').innerHTML=new Date().toLocaleString();",
			1000);
	function loadding() {
		jsPrintSetup
				.setOption('orientation', jsPrintSetup.kPortraitOrientation);
		// set top margins in millimeters
		jsPrintSetup.setOption('marginTop', 1);
		jsPrintSetup.setOption('marginBottom', 0);
		jsPrintSetup.setOption('marginLeft', 3);
		jsPrintSetup.setOption('marginRight', 0);
		// set page header
		jsPrintSetup.setOption('headerStrLeft', '');
		jsPrintSetup.setOption('headerStrCenter', '');
		jsPrintSetup.setOption('headerStrRight', '');
		// set empty page footer
		jsPrintSetup.setOption('footerStrLeft', '');
		jsPrintSetup.setOption('footerStrCenter', '');
		jsPrintSetup.setOption('footerStrRight', '');
		// Suppress print dialog
		jsPrintSetup.setSilentPrint(true);
		// Do Print
		jsPrintSetup.print();
		// Restore print dialog
		//jsPrintSetup.setSilentPrint(false);
		closewin();
	}
</script>
<style>
/*初始化CSS*/
body {
	margin: 0;
	padding: 0;
	overflow: hidden;
}

body, html {
	-webkit-text-size-adjust: none;
	width: 100%;
}

* {
	text-decoration: none;
	list-style: none;
}

img {
	border: 0px;
}

ul, li, dl, dd, dt, p, ol, h1, h2, h3, h4, h5 {
	font-weight: 100;
	padding: 0;
	margin: 0;
}

a, input, button {
	outline: none;
}

::-moz-focus-inner {
	border: 0px;
}

a {
	color: #000;
}

table {
	border-collapse: collapse;
}

.printtable {
	margin: 5px;
}

.sflogo {
	height: 30px;
	margin-top: 4px;
}

.printtable>tbody>tr>td {
	border: 2px solid #000;
}

.printtable table {
	margin: 0px;
}

.tdtitle div {
	font-size: 8px;
	text-align: center;
}

.tdcontent div {
	font-size: 8px !important;
	margin: 0px 4px;
	line-height: 15px;
}

.erweima {
	margin-top:5px;
	width: 240px;
	height: 29px;
}
</style>
</head>
<body>
	<div>
		<table class="printtable" style="border: 3px solid #000; border-collapse: collapse">
			<tr>
				<td colspan="4" style="height:40px;">
				</td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td colspan=4 rowspan=2">
					<img alt="" src="${root}onebarcode/${queryParam.waybill}.png" style="margin-left :18px;margin-right :17px;width: 240px;height: 35px;">
					<div style="font-size: 8px; text-align: center;">运单号：${queryParam.waybill}</div>
				</td>
				<td width="96px" colspan="2">
					<div style="font-family: '黑体'; font-size: 12px; font-weight: bold; text-align: center;">${queryParam.producttype_name}</div>
				</td>
			</tr>
			<tr>
				<td style="height: 41px;" colspan="2"></td>
			</tr>
			<tr>
				<td width="24px" class="tdtitle">
					<div>目</div>
					<div>的</div>
					<div>地</div>
				</td>
				<td colspan="5">
					<div style="font-family: '黑体'; font-size: 32sspx; font-weight: bold; margin-left: 5px;">${queryParam.mark_destination }</div>
				</td>
			</tr>
			<tr style="height: 41px;">
				<td class="tdtitle">
					<div>收</div>
					<div>件</div>
					<div>人</div>
				</td>
				<td style="overflow: hidden;" colspan="5" class="tdcontent">
					<div style="font-family: '黑体'; font-weight: bold;">${queryParam.to_contacts }&nbsp;&nbsp;${queryParam.to_phone }</div>
					<div style="font-family: '黑体'; font-weight: bold;">${queryParam.to_province }${queryParam.to_city }${queryParam.to_state }${queryParam.to_address }</div>
				</td>
			</tr>
			<tr>
				<td class="tdtitle">
					<div>寄</div>
					<div>件</div>
					<div>人</div>
				</td>
				<td colspan="3" class="tdcontent">
					<div>${queryParam.from_contacts }&nbsp;&nbsp;${queryParam.from_phone }</div>
					<div>${queryParam.from_province }${queryParam.from_city }${queryParam.from_state }${queryParam.from_address }</div>
				</td>
				<td style="width: 96px; text-align: center;" colspan="2">
					<div style="font-family: '黑体'; font-size: 14px; font-weight: bold; text-align: center;">定时派送</div>
					<div style="font-family: '黑体'; font-size: 12px; font-weight: bold; text-align: center;">自寄&nbsp;自取</div>
				</td>
			</tr>
			<tr>
				<td colspan="6">
					<table style="font-size: 8px;">
						<tr>
							<td style="width: 133px;">
								<div>
									<c:if test="${queryParam.pay_path=='1'}">付款方式：寄方付</c:if>
									<c:if test="${queryParam.pay_path=='2'}">付款方式：收方付</c:if>
									<c:if test="${queryParam.pay_path=='4'}">付款方式：寄付月结</c:if>
									<c:if test="${queryParam.pay_path=='3'}">付款方式：寄付月结</c:if>
								</div>
								<div>月结账号：${queryParam.custid }</div>
								<div>实际重量：${queryParam.total_weight }</div>
								<div>计费重量：${queryParam.total_weight }</div>
							</td>
							<td style="width: 133px;">
								<div>声明价值：&nbsp;&nbsp;&nbsp;</div>
								<div>保价费用：&nbsp;&nbsp;&nbsp;</div>
								<div>总价值：${queryParam.total_amount }</div>
								<div>总数量：${queryParam.total_qty }</div>
							</td>
							<td style="width: 133px;">
								<div>报关方式：&nbsp;&nbsp;&nbsp;</div>
								<div>运费：&nbsp;&nbsp;&nbsp;</div>
								<div>费用合计：&nbsp;&nbsp;&nbsp;</div>
								<div>&nbsp;</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="tdtitle">
					<div>寄</div>
					<div>托</div>
					<div>物</div>
				</td>
				<td colspan="2">
					<table>
						<tr>
							<td>
								<div style="font-size: 8px;">
									<c:forEach items="${list}" var="list">
										<c:if test="${not empty list.sku_name }">商品名称：${list.sku_name}&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
										<c:if test="${not empty list.qty }">件数：${list.qty}&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
										<c:if test="${not empty list.weight}">重量：${list.weight}&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
									</c:forEach>
								</div>
							</td>
						</tr>
					</table>
				</td>
				<td>
					<table style="font-size: 8px;">
						<tr>
							<td>
								<div>收件员：&nbsp;&nbsp;&nbsp;</div>
								<div>寄件日期:&nbsp;&nbsp;&nbsp;</div>
								<div>派件员：&nbsp;&nbsp;&nbsp;</div>
							</td>
						</tr>
					</table>
				</td>
				<td colspan="2">
					<table style="font-size: 8px;">
						<tr>
							<td>
								<div>签收：</div>
								<div>&nbsp;</div>
								<div>&nbsp;</div>
							</td>

							<td align="left">
								<div style="font-size: 8px;">&nbsp;</div>
								<div style="font-size: 8px;">&nbsp;</div>
								<div style="font-size: 8px;" align="left">月&nbsp;&nbsp;&nbsp;日</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr style="border-top: 3px solid #000;">
				<td colspan="2">
					<img alt="" src="${root}onebarcode/sf.jpg" class="sflogo" />
				</td>
				<td colspan="4">
					<div style="text-align: center;">
						<img alt="" src="${root}onebarcode/${queryParam.waybill}.png" class="erweima" />
					</div>
					<div style="font-size: 8px; text-align: center">运单号：${queryParam.waybill}</div>
				</td>
			</tr>
			<tr>
				<td class="tdtitle">
					<div>寄</div>
					<div>件</div>
					<div>人</div>
				</td>
				<td colspan="5" class="tdcontent">
					<div>${queryParam.from_contacts }&nbsp;&nbsp;${queryParam.from_phone }</div>
					<div>${queryParam.from_province }省${queryParam.from_city }市${queryParam.from_state }${queryParam.from_address }</div>
				</td>
			</tr>
			<tr>
				<td class="tdtitle">
					<div>收</div>
					<div>件</div>
					<div>人</div>
				</td>
				<td colspan="5" class="tdcontent">
					<div>${queryParam.to_contacts }&nbsp;&nbsp;${queryParam.to_phone }</div>
					<div>${queryParam.to_province }省${queryParam.to_city }市${queryParam.to_state }${queryParam.to_address }</div>
				</td>
			</tr>
			<tr>
				<td colspan="6">
					<table>
						<tr>
							<td>
								<div>
									<c:if test="${not empty queryParam.customer_number}">
										<img alt="" src="${root}onebarcode/${queryParam.customer_number}.png" style="vertical-align: middle;" class="erweima">
									</c:if>
									<span style="font-family: '黑体'; font-size: 20px; font-weight: bold;">${queryParam.expected_from_date}</span>
									<c:if test="${not empty queryParam.customer_number}">
										<div style="font-size: 8px; margin-left: 50px; margin-top: 2px;">客户订单号: ${queryParam.customer_number}</div>
									</c:if>
								</div>
								<div style="font-size: 8px;">
									<c:if test="${not empty queryParam.memo }">
									   备注: ${queryParam.memo }&nbsp;&nbsp;&nbsp;&nbsp;
									</c:if>
									<c:forEach items="${list}" var="list">
										<c:if test="${not empty list.sku_name }">
										   商品名称：${list.sku_name}&nbsp;&nbsp;&nbsp;&nbsp;
										</c:if>
										<c:if test="${not empty list.qty}">
										   件数：${list.qty}&nbsp;&nbsp;&nbsp;&nbsp;
										</c:if>
										<c:if test="${not empty list.weight}">
										   重量：${list.weight}&nbsp;&nbsp;&nbsp;&nbsp;
										</c:if>
									</c:forEach>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>