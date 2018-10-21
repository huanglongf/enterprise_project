<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<!--  --><script type="text/javascript" src="<s:url value='/scripts/main-content-frame.js' includeParams='none' encode='false'/>"></script>
<%-- <script type="text/javascript" src="<s:url value='/scripts/main-content.js' includeParams='none' encode='false'/>"></script>
 --%><script type="text/javascript" src="<s:url value='/scripts/frame/product-three-dimensional-collect.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
 <script type="text/javascript">
 	/* function setValues(ip,port) {
		var arr = document.threeDimensional.scan(ip,port);
		if(arr && arr.length() > 0){
			$j("#length").val(arr[0]);
			$j("#width").val(arr[1]);
			$j("#height").val(arr[2]);
			$j("#grossWeight").val(arr[3]);
		}else{
			jumbo.showMsg('系统异常,请联系管理员');
		}
		
	} */
</script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="div-sta-list">
	<div>
		<form id="formQuery" name="formQuery">
		
			<table>
				<tr>
					<td class="label">更新到货号</td>
					<td>
						<select id="isSupplierCode" name="product.isSupplierCode" loxiaType="select">
							<option value="" selected="selected">请选择</option>
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">SKU</td>
					<td><input loxiaType="input" name="product.barCode" id="barCode" trim = "true" /></td>
				</tr>
				<tr>
					<td class="label">长/宽/高</td>
					<td colspan="4">
						<input loxiaType="input" name="product.length" id="length" trim="true" style="width: 80px" disabled="disabled"/>
						<input loxiaType="input" name="product.width" id="width" trim="true" style="width: 80px" disabled="disabled"/>
						<input loxiaType="input" name="product.height" id="height" trim="true" style="width: 80px" disabled="disabled"/>
						<input loxiaType="input"  name="" id="" trim="true" style="width: 30px" value=" cm" disabled="disabled" />
					</td>
				</tr>
				<tr>
					<td class="label">重量</td>
					<td><input loxiaType="input" name="product.grossWeight" id="grossWeight" trim="true"style="width: 250px" disabled="disabled"/>
						<input loxiaType="input"  name="" id="" trim="true" style="width: 30px" value=" Kg" disabled="disabled"/>
					</td>
				</tr>
				
			</table>
		</form>
		</div>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search"><s:text name="button.query"></s:text></button>
		</div>
		<table id="tbl_query"></table>
		<div id="tbl_query_page"></div> 
		
		<div id="pager"></div>
		<div class="buttonlist">
				<button type="button" loxiaType="button" class="confirm" id="updateData" >更新三维</button>
<!-- 				<button type="button" loxiaType="button" class="confirm" id="updateData1" onclick='setValues("10.7.31.220", 1050)'>更新三维</button>
 -->		</div>
	</div>
	<APPLET name="threeDimensional" CODE = "com.jumbo.util.comm.CubiScanApplet.class" width="0" height="0" ARCHIVE = "<%=request.getContextPath() %>/common/cubiScan.jar" type="applet"></APPLET>
</body>
</html>