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

body {
	background-color: #c6edcc;
}

td {
	word-break: break-all;
	word-wrap: break-word;
}


</style>
<title>rfid</title>
<script type="text/javascript" src="<s:url value='/frame/rfid/save_rfid.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body contextpath="<%=request.getContextPath()%>">

<input type="text" id="rfidSession"  hidden="true" value="${RFIDCode}"/>
	<div >
		<table style="width: 80%;height: 100%;border-collapse:separate; border-spacing:0px 30px;" align="center" >
			<tr>
				<td >
						<label style="font-size: 20px">请扫描商品条码barcode：</label>
						<input type="text" id="barcode"  style="width:200px;height: 40px" placeholder="请扫描barcode" />
						<button id="btnOrder" type="button" class="btn btn-primary btn-l" onclick="barcodeNext()">确认</button>
				</td>
				
			</tr>
			<tr>

				<td>
					<label style="font-size: 20px">请扫描OK码：</label>
					<input type="text" id="okcode"  style="width:200px;height: 40px" placeholder="请扫描OK码" />
					<button  type="button" class="btn btn-primary btn-l" onclick="getRfid()">确认</button>

				</td>
			</tr>
			<tr>
				<td>
				<label style="font-size: 20px">RFID：</label>
				<input type="text" id="rf1"  style="width:300px;height: 40px"  readonly ="readonly"/>
				<input type="text" id="rf2"  style="width:300px;height: 40px"  readonly ="readonly"/>
				</td>
			</tr>
			<tr>
				<td>
				<button  type="button" class="btn btn-primary btn-l" onclick="saveRfid()">确认</button>
				<button  type="button" class="btn btn-primary btn-l" onclick="reset()">重置</button>
				</td>
			</tr>
			<tr>
			<td>
			<label id="tipshow" style="font-size: 20px;color: red"></label>
			</td>
			</tr>
		</table>
	</div>

</body>
	<%@include file="../../common/include-opencv.jsp"%>
</html>