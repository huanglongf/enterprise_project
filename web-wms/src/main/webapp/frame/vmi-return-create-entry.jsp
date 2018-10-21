<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/meta.jsp"%>
<title><s:text name="betweenlibary.move.query"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/vmi-return-create.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
 <s:text id="pselect" name="label.please.select"/>
<input type="hidden" id="espritChannelFlag" value="false"/>
<input type="hidden" id="espritTransferFlag" value="false"/>	
	
	<table width="90%">
		<tr>
			<td class="label" width="15%">
				店铺：
			</td>
			<td width="20%" >
				<select id="owner" name="sta.owner" loxiaType="select" style="width: 200px;">
					<option value=""></option>
				</select>
			</td>
			
			<td class="label" width="15%">
				退仓类型：
			</td>
			<td>
				<select id="returnType" name="sta.intType" loxiaType="select" style="width: 200px;">
					<option value="101" selected="selected">VMI退大仓</option>
					<option value="102">VMI转店退仓</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="label" width="15%">
				退仓原因：
			</td>
			<td width="20%">
				<select loxiaType="select" name="reasonCode" id="reasonCode" style="width: 200px;">
					<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
				</select><font color="red">*CONVERSE 必填</font>
			</td>
			<td class="label" width="15%">
				<span id='toLocationName' class="label hidden">VMI入库目标地址:</span>
			</td>
			<td>
				<input loxiaType="input" class="hidden" value="" id="toLocation" name="toLocation" style="width: 150px;"/>
				<div id="espritLocGLNDiv" class="hidden">
				<select id="espritLocGLN" name="sta.toLocation"  style="width: 200px;">
				</select>
				</div>
				
			</td>
		</tr>
		<tr>
			<td class="label" width="15%">
				<span id='imperfectTypeName' class="label hidden">残次退仓类型：</span>
			</td>
				<td width="20%" >
				<select id="imperfectType" name="imperfectType" class="hidden" loxiaType="select" style="width: 200px;">
					<option value="">请选择</option>
					<option value="125">原残</option>
					<option value="220">售残</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="label" width="15%">
				仓库编码：
			</td>
			<td width="20%">
				<input loxiaType="input" value="" id="activitySource" name="activitySource" style="width: 150px;"/>
			</td>
			<td class="label" width="15%">
				<span id='orderCodeLabel' class="hidden">退仓指令单号:</span>
			</td>
			<td>
				<input loxiaType="input" class="hidden" value="" id="orderCode" name="orderCode" style="width: 150px;"/>
				 <div id="pumaOrderCodeDiv" class="hidden">
				   <select id="pumaRtoOrderCode"   style="width: 150px;">
				   </select>
				</div>
				<div id="pumatip" align="left"  class="hidden" ><font color="red">*PUMA 请选择退仓指令号</font></div>
			</td>
		</tr>
		  <tr>
		  	<td colspan="2" class="label" style="width: 200px;"> 
				退货地址详细信息
			</td>
			<td></td><td></td>
		  </tr>
		  <tr>
		  	<td colspan="3" style="width: 200px;">
		  		<font color="red" class="hidden" id= "zdtcerror">转店退仓: 省、市、区、联系人、联系地址、联系电话必填！</font>
		  	</td>
		  </tr>
		 
		<tr>
			<td class="label">省:</td>
			<td><input loxiaType="input" value="" id="province" name="province" style="width: 150px;"/></td>
			<td class="label">市:</td>
			<td><input loxiaType="input" value="" id="city" name="city" style="width: 150px;"/></td>
		</tr>
		<tr>
			<td class="label">区:</td>
			<td><input loxiaType="input" value="" id="district" name="district" style="width: 150px;"/></td>
			<td class="label">联系人:</td>
			<td><input loxiaType="input" value="" id="username" name="username" style="width: 150px;"/></td>
		</tr>			 
		<tr>
			<td class="label">联系地址:</td>
			<td><input loxiaType="input" value="" id="address" name="address" style="width: 350px;"/></td>
			<td class="label">联系电话:</td>
			<td><input loxiaType="input" value="" id="telphone" name="telphone" style="width: 150px;"/></td>
		</tr>	
		<tr>
			<td class="label">退货原因编码:</td>
			<td><input loxiaType="input" value="" id="slipCode1" name="slipCode1" style="width: 150px;"/><font color="red">*CONVERSE 必填</font></td>
			<td class="label" >收货方式:</td>
			<td  class="hidden" id="selectFreightMode">
				      <select loxiaType="select" id="freightMode"  style="width: 160px;">
					       <option value="10">上门自取</option>
					       <option value="20">物流派送</option>
				      </select>
			</td>
		</tr>	
		<tr class="hidden" id="selectTd">
			<td class="label" >物流商:</td>
			<td >
				      <select loxiaType="select" id="select"  style="width: 160px;">
					       <option value="">请选择...</option>
					       <option value="STO">申通快递</option>
					       <option value="ZTO">中通快递</option>
					       <option value="EMS">EMS速递</option>
					       <option value="SF">顺丰快递</option>
					       <option value="TTKDEX">天天快递</option>
				      </select>
			</td>
			<td></td>
			<td></td>
		</tr>	
		<tr>
			<td colspan="4">
				<div class="buttonlist">
					<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
						<span class="label"><s:text name="label.warehouse.choose.file"></s:text>:</span>
						<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
					</form>
				</div>
			</td>
		 </tr>
		  <tr>
		  	<td colspan="2"> 
				<button loxiaType="button" class="confirm" id="import"><s:text name="button.import"></s:text></button>
			</td>
			<td style="width: 200px;" colspan="2"> 
	            <a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_vmi_return"></s:text>.xls&inputPath=tplt_import_vmi_return.xls"><s:text name="download.excel.template"></s:text></a>
	        </td>
		  </tr>
		  
		  <tr>
		  	<td> 
				<button loxiaType="button" class="confirm" id="importpl">NikeCRW批量导入</button>
			</td>
			<td>
					<form method="post" enctype="multipart/form-data" id="importplForm" name="importplForm" target="upload">
						<span class="label">NikeCRW批量选择文件:</span>
						<input type="file" name="file" loxiaType="input" id="filepl" style="width:200px"/>
					</form>
			</td>
			
			<td style="width: 200px;" colspan="2"> 
	            <a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=NIKECRW退仓单导入.xls&inputPath=nike_vmi_import.xls">NikeCRW批量模板下载</a>
	        </td>
		  </tr>
	</table>
	<br/> 
	<iframe id="upload" name="upload" class="hidden"></iframe>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>