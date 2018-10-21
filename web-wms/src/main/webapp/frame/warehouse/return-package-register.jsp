<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<%-- <%@include file="../../frame/warehouse/return-import-result.jsp"%> --%>
<title>退换货快递登记</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/return-package-register.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	<table>
		<tr>
			<td>
				<table width="620px" cellpadding='15%'>
					<tr>
						<td width="15%" class="label" style="font-size: 14px">是否需要记录包裹重量：</td>
						<td width="30%" style="font-size: 14px">
							<label><input  name='saveWeight' type="radio" value="记录" />记录</label>   
							<label><input  name='saveWeight' type="radio" value="不记录" checked="checked"/>不记录</label> 
						</td>
					</tr>
					<tr>
						<td width="15%" class="label" style="font-size: 14px">登记类型:</td>
						<td width="30%" style="font-size: 14px">
							<label><input  name='type' type="radio" value="已录入" />录入</label>   
							<label><input  name='type' type="radio" value="已拒收" />拒收</label> 
						</td>
						<td name="rejection_reasons" width="25%" class="label hidden" style="font-size: 14px">拒收原因</td>
						<td name="rejection_reasons" width="40%" class="hidden">
							<select id='rejection_reasons' loxiaType="select" id="lpcode">
								<option value="" selected="selected">请选择</option>
								<option value="空包裹" selected="selected">空包裹</option>
								<option value="非本公司商品" selected="selected">非本公司商品</option>
								<option value="其他" selected="selected">其他</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label" style="font-size: 14px">物流商:</td>
						<td style="font-size: 14px">
							<select loxiaType="select" id="lpcode">
								<option value="" selected="selected">请选择</option>
							</select>
						</td>
						<td ></td>
					</tr>
				
					<tr>
					 	<td class="label" style="font-size: 14px">物理仓:</b></td>
						<td width="180px"><select id="selTransOpc"  loxiaType="select" loxiaType="select">
							<option value="">--请选择仓库--</option>
						</select>
						</td>
						<td ></td>
					</tr>
					<tr>
						<td  class="label" valign="bottom" style="font-size: 14px">备注:</td>
						<td colspan="3">
							<textarea id='remarks' rows="2" cols="70"></textarea>
						</td>
					</tr>
					<tr>
						<td  class="label" valign="bottom" style="font-size: 14px">快递单号:</td>
						<td>
							<input loxiaType="input" trim="true" id="trackingNo" />
						</td>
						<td colspan="2"><button type="button" loxiaType="button" id="addPacking">添加至列表</button>(同录完快递单号按"回车"键)</td>
					</tr>
					<tr name="entering_weight" class="label hidden">
						<td  class="label" valign="bottom" style="font-size: 14px">包裹重量:</td>
						<td>
							<input loxiaType="input" trim="true" id="weight" />
						</td>
					<td colspan="1">千克(KG)<button type="button" loxiaType="button" id="">重启称</button></td>
					</tr>
					<tr name="confirm_sku" class="label hidden">
						<td  class="label" valign="bottom" style="font-size: 14px">自动称重确认条码:</td>
						<td>
							<input loxiaType="input" trim="true" id="barcode" />
						</td>
					</tr>
					<tr>
						<td  colspan="4" style="font-size: 14px">已经扫描包裹数量：<font id='packageNum' style="color: blue;": ">0</font></td>
					</tr>
				
					<tr >
					<form method="post" enctype="multipart/form-data" id="importForm"	name="importForm" target="upload">
					<td width="200px" class="label">批量导入：</td>
					<td align="center">
							<input type="file" name="file" loxiaType="input" id="file"
								style="width: 150px" />
					</td>
					<td><a loxiaType="button"
						href="<%=request.getContextPath()%>/warehouse/excelDownload.do?fileName=退换货快递登记导入.xls&inputPath=tpl_import_expressDelivery.xls">模板下载</a>
					<button loxiaType="button" class="confirm" id='sn_import'>导入</button>
					</td>
					</form>
				</tr>
				</table>
			</td>
			<td>
				<div class="photoInfo" id="photoInfo">
					<%@include file="../../common/include-opencvimgmulti.jsp"%>
				</div>
			</td>
			<td>
				 <button type="button" loxiaType="button" id="photograph" style="font-size: 16px">重　拍</button>
			</td>
		</tr>
	</table>
	<div  class="buttonlist"></div>
	<table id="tbl_temp_list"></table>
	<br>
	<div  class="buttonlist"></div>
	<input  id='print' type="checkbox" value="已录入" checked="checked" /><font style="font-size: 14px">是否打印交接清单(登记确认后自动打印)</font>
	<br>
	<br>
	<button type="button" loxiaType="button" id="executeInventory" class="confirm">登记确认</button>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<iframe id="upload" name="upload" class="hidden"></iframe>
	<jsp:include page="/common/include-opencv.jsp"></jsp:include>
</body>
</html>