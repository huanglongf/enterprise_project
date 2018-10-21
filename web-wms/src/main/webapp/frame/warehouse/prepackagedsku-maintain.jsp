<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/prepackagedsku-maintain.js' includeParams='none' encode='false'/>"></script>
<style>
.showborder {
	border: thin;
}
</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="div1">
		<form method="post" id="queryForm">
			<span class="label">预包装商品条码:</span><input style="width:120px" loxiaType="input" id ="p_barCode" name="barCode" trim="true"/>
		</form>
		<br/>
		<button type="button" id="btn-add_1" loxiaType="button" class="confirm"><s:text name="button.wahrhouse.inner.add"></s:text> </button>
		<button type="button" id="btn-query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
		<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button><br/>
		<br/>
		<span class="label" style="font-size:15px;">快捷扫描区</span><br/>
		<span class="label" style="color:red">预包装商品条码:</span><input style="width:120px" loxiaType="input" id="skuBarCode" trim="true"/>
		<br/>
		<br/>
		<table id="tbl_main_list"></table>
		<div id="pager"></div>
	</div>
	<div id="div2">
		<form id="addform">
			<table>
				<tr>
					<td class="label">商品条码：</td>
					<td width="200px"><input id="barcode" loxiaType="input" trim="true" name="barCode"/></td>
					<td class="label">商品件数：</td>
					<td width="200px"><input id="skuqty" loxiaType="number" name="skuQty"/></td>
					<td><button type="button" loxiaType="button" id="btn-add_2" class="confirm">添加</button></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist"></div>
		
		<button loxiaType="button" id="btnInQty" style="width: 100px;height: 30px" >商品件数</button>
		<input loxiaType="number" value="1" id="inQty" style="width: 60px;height: 30px" /> &nbsp;
		
		<span class="label" style="height: 30px" >
		 	<s:text name="label.warehouse.pl.sta.sku.barcode"/>
		</span>
		<span><input id="scan_barCode" loxiaType="input" style="width: 200px;height: 25px" trim="true"/></span> 
		<div class="buttonlist"></div>
		<table id="tbl_detail_list"></table>
		<br/>
		<button id="backToPlList" loxiaType="button" >返回</button>
	</div>
	
	
	<!-- <div id="dialog_error_ok">
		<div id="error_text_ok" ></div>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="dialog_error_close_ok">关闭</button>
			<span class="label">快捷关闭条码</span><input loxiaType="input" style="width:100px" id="okCode1" trim="true"></input>
		</div>   
	</div> -->
</body>
</html>