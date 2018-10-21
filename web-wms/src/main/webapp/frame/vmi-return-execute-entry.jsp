<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/meta.jsp"%>
<title><s:text name="betweenlibary.move.query"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/vmi-return-execute.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
 <div id="divPd">
		<form name="form_query" id="form_query">
			<table width="70%">
				<tr>
					<td class="label" width="15%">
						创建时间
					</td>
					<td width="10%">
						<input loxiaType="date" name="startTime" showTime="true" style="width:150px;"/>
					</td>
					<td class="label" width="15%">
						到
					</td>
					<td width="15%">
						<input loxiaType="date" name="endTime" showTime="true" style="width:150px;"/>
					</td>
					<td class="label" width="15%">
						状态
					</td>
					<td width="15%">
						<select  name="status" loxiaType="select">
							<option value="">请选择</option>
							<option value="1">已创建</option>
							<option value="2">配货中</option>
							<option value="4">已转出</option>
							<option value="8">装箱中</option>
							<option value="10">已完成</option>
							<option value="17">取消已处理</option>
							<option value="25">冻结</option>
						</select>
					</td>			
				</tr>
				<tr>
					<td class="label">
						作业单号
					</td>
					<td>
						<input name="staCommand.code" loxiaType="input" trim="true" style="width:150px;"/>
					</td>
					<td class="label">
						店铺
					</td>
					<td>
						<select id="owner" name="staCommand.owner" loxiaType="select" style="width: 200px;">
							<option value="">--请选择店铺--</option>
						</select>
					</td>
					 
					<td class="label">
						相关单据号
					</td>
					<td>
						<input name="staCommand.refSlipCode" loxiaType="input" trim="true" style="width:150px;"/>
					</td>
					
				</tr>
				<tr>
					<td class="label">
						相关单据号2(LOAD KEY)
					</td>
					<td>
						<input name="staCommand.slipCode2" loxiaType="input" trim="true" style="width:150px;"/>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button id="query" loxiaType="button" class="confirm">查询</button>
			<button id="reset" loxiaType="button" class="confirm">重置</button>
		</div>
		<table id="tbl-query-info"></table>
		<div id="pager"></div>
	</div>
	<div id="divList" class="hidden">
		<input type="hidden" name="" id="staID" value=""/>
		<table width="60%">
			<tr>
				<td class="label" width="20%%">
					作业单号：
				</td>
				<td width="25%" id="code">
				</td>
				<td class="label">
					状态：
				</td>
				<td id="status_">
				</td>
			</tr>
			<tr>
				<td class="label">
					店铺：
				</td>
				<td id="shop">
				
				</td>
				 <td class="label">
					创建时间
				</td>
				<td id="createTime">
				</td>
			</tr>
			<tr>
				<td class="label">
					配送模式：
				</td>
				<td id="freightMode">
				
				</td>
				 <td>
					
				</td>
				<td>
				</td>
			</tr>
		</table>
		<input type="hidden"  id="isEsprit" value=""/>
		<input type="hidden"  id="isPf" value=""/>
		<br/>
		<table id="tbl-details"></table>
		<div  id="is_PM" class="hidden">
			<br/>
			<div id='divInv' class="hidden">
				<form id="importForm" method="post" enctype="multipart/form-data" name="importForm" target="upload" >
					<input type="file" accept="application/msexcel" name="file" id="tnFile" style="width:150px" loxiaType="input"/>
					<button loxiaType="button" class="confirm" id="input">导入占用</button>
					<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="退仓出库"></s:text>.xls&inputPath=tplt_import_warehouse_others_operate.xls"><s:text name="download.excel.template"></s:text></a>
				</form>
			</div>
			<div id="divSnImport" class="hidden">
				<div class="divFloat">
					<form method="post" enctype="multipart/form-data" id="importFormSN" name="importFormSN" target="upload">	
						<span class="label">SN号导入</span>
						<input type="hidden" name="staId" id="imp_staId" />
						<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
						<button loxiaType="button" class="confirm" id="import"><s:text name="button.import"></s:text></button>
						<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_warehouse_sn_import"></s:text>.xls&inputPath=tplt_import_warehouse_sn_import.xls"><s:text name="download.excel.template"></s:text></a>
					</form>
				</div>
				<div class="clear"></div>
				<br/>
			</div>
			<div id="packaging_materials" class="hidden">
				<form id="importPMForm" method="post" enctype="multipart/form-data" name="importPMForm" target="upload" >
					<span class="label">包材导入</span>
					<input type="file" accept="application/msexcel" name="file" id="pmFile" loxiaType="input" style="width:200px"/>
					<button loxiaType="button" class="confirm" id="inputPM">包材导入</button>
					<a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=包材条码.xls&inputPath=tplt_import_packaging_materials.xls" role="button">
						<span class="ui-button-text">模版文件下载</span>
					</a>
				</form>
			</div>
		</div>
		<div class="buttonlist">
			<button id="execute" loxiaType="button" class="confirm">执行</button>
			<button id="print" loxiaType="button" class="confirm">打印</button>
			<button loxiaType="button" class="confirm" id="printSendInfo"><s:text name="button.print.send.info"/></button>
			<button id="packing" loxiaType="button" class="confirm">装箱</button>
			<button id="printPod" loxiaType="button" class="confirm">打印POD</button>
			<button id="update" loxiaType="button" class="confirm">修改退货地址信息</button>
			<button id="cancel" loxiaType="button" class="confirm">取消</button>
			<button id="back" loxiaType="button">返回</button>
		</div>
	</div>
	<div id="show_address_dialog" style="text-align: center;">
	<br />
		<table>
		<tr>
			<td class="label">省:</td>
			<td><input loxiaType="input" value="" id="province" name="province" style="width: 250px;"/></td>
			<td class="label">市:</td>
			<td><input loxiaType="input" value="" id="city" name="city" style="width: 200px;"/></td>
		</tr>
		<tr>
			<td class="label">区:</td>
			<td><input loxiaType="input" value="" id="district" name="district" style="width: 250px;"/></td>
			<td class="label">联系人:</td>
			<td><input loxiaType="input" value="" id="username" name="username" style="width: 200px;"/></td>
		</tr>			 
		<tr>
			<td class="label">联系地址:</td>
			<td><input loxiaType="input" value="" id="address" name="address" style="width: 250px;"/></td>
			<td class="label">联系电话:</td>
			<td><input loxiaType="input" value="" id="telphone" name="telphone" style="width: 200px;"/></td>
			<td><input loxiaType="input" id="lpcode" name="lpcode" class="hidden"/></td>
		</tr>	
		<tr>
			<td class="label" >物流商:</td>
			<td>
				      <select loxiaType="select" id="select"  style="width: 250px;">
					       <option value="STO">申通快递</option>
					       <option value="ZTO">中通快递</option>
					       <option value="EMS">EMS速递</option>
					       <option value="SF">顺丰快递</option>
					       <option value="TTKDEX">天天快递</option>
					       <option value="YTO">圆通快递</option>
					       <option value="SFDSTH">顺丰电商特惠</option>
				      </select>
			</td>
			<td colspan="2"><font id="hint" class="hidden" color="red">此订单的商品已装箱，不可更改物流商！</font></td>
			
		</tr>	
		</table><br/>
		<table>
				<tr>
			<td>
					<button id="edit" loxiaType="button" class="confirm" onclick="editAddress();">编辑完成</button>
			</td>
			<td>
					<button id="close" loxiaType="button" class="confirm" onclick="closedialog();">取消</button>
			</td>
		</tr>
		</table>
	</div>
	<iframe id="upload" name="upload" class="hidden"></iframe>
</body>
</html>