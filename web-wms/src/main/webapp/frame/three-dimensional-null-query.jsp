<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/meta.jsp"%>
		<title><s:text name="label.warehouse.pl.dispatch1.query"/></title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/three-dimensional-null-query.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
	</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<!-- 这里是页面内容区 -->
		 <div  id="tabs">
	 			<ul>
					<li><a href="#tabs_1">收货商品缺失三维信息</a></li>
					<li><a href="#tabs_2">创批缺失三维信息商品</a></li>
				</ul>
   <div id="tabs_1">	
	<s:text id="pselect" name="label.please.select"/>
		<div id="showList">
			 <form id="plForm" name="plForm">
				<table>
					<tr>
						<td class="label">创建时间</td>
						<td >
							<input loxiaType="date" style="width: 130px" name="staCommand.startCreateTime1" id="formCrtime" showTime="true"/>
						</td>
						<td class="label">	
							<s:text name="label.warehouse.pl.to"/>
						</td>
						<td>
							<input loxiaType="date" style="width: 130px" name="staCommand.endCreateTime1" id="toCrtime" showTime="true"/>
						</td>
					
						<td class="label">作业单号</td>
						<td >
							<input loxiaType="input" style="width: 130px" name="staCommand.code" id="staCode" />
						</td>
						<td class="label">相关单据号</td>
						<td >
							<input loxiaType="input" style="width: 130px" name="staCommand.refSlipCode" id="refSlipCode" />
						</td>
					</tr>
					<tr>
						<td class="label">相关单据号1</td>
						<td >
							<input loxiaType="input" style="width: 130px" name="staCommand.slipCode1" id="slipCode1" />
						</td>
					    <td class="label">作业单类型</td>
						<td>
						 	<select loxiaType="select"  name="staCommand.strType" id="strType">
							 	<option value="">请选择</option>
							 	<option value="11">采购入库</option>
							 	<option value="12">结算经销入库</option>
							 	<option value="14">代销入库</option>
							 	<option value="15">赠品入库</option>
							 	<option value="16">移库入库</option>
							 	<option value="17">货返入库</option>
							 	<option value="32">库间移动</option>
							 	<option value="81">VMI移库入库</option>
							 	<option value="90">同公司调拨</option>
							 	<option value="91">不同公司调拨</option>
							 </select>
						</td>
						<td class="label">店铺</td>
						<td style="width: 110px">
						<input loxiaType="input" trim="true" style="width: 130px"  name="staCommand.owner" id="owner"/></td>
						<td class="label">状态</td>
						<td style="width: 100px">
							 <select  loxiaType="select"  name="staCommand.intStaStatus" id="intStaStatus">
							 	<option value="">请选择</option>
							 	<option value="1">未完成</option>
							 	<option value="2">已完成</option>
							 </select>
						</td>
					</tr>
				</table>
			</form>
			<div class="buttonlist">
				<button id="search" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
				<button id="reset" type="reset" loxiaType="button"><s:text name="button.reset"/></button>
				<button id="exportOrder" type="button" loxiaType="button" class="confirm">导出</button>
			</div>
			<div>
				<table id="tbl-dispatch-list"></table>
			</div>
			<div id="pager"></div>
			
			
	<div id="div-waittingList"  class="hidden">
		<table id="tbl-waittingList"></table>
	</div>
</div>
<div id="div2" class="hidden">

	<table width="90%">
		<tr>
			<td class="label">作业单号</td>
			<td>
				<label id="staCode_d"></label>
			</td>
			<td class="label">相关单据号</td>
			<td ><label id="slipCode_d"></label></td>
		</tr>
		
		<tr><td colspan="6"><input type="hidden" id="staId" value=""></input></td></tr>
	</table>
	<br /><br />
		<div id="divTbDetial"><table id="tbl-orderDetail"></table></div>
		<div><table id="tbl-product"></table></div>
		<div id="pager2"></div>
		<div id="btnlist" class="buttonlist" >
			<button loxiaType="button" class="confirm" id="back"><s:text name="button.back"></s:text> </button>
		</div>
</div>
<iframe id="exportFrame" class="hidden" target="_blank"></iframe>
</div>
<div id="tabs_2">
		<s:text id="pselect" name="label.please.select"/>
		<div id="showList2">
		  <form id="plForm2" name="plForm2">
			<table>
				 <tr>
				 		<td class="label">创建时间</td>
						<td >
							<input loxiaType="date" style="width: 130px" name="startCreateTime2" id="startCreateTime2" showTime="true"/>
						</td>
						<td class="label">	
							<s:text name="label.warehouse.pl.to"/>
						</td>
						<td>
							<input loxiaType="date" style="width: 130px" name="endCreateTime2" id="endCreateTime2" showTime="true"/>
						</td>
				 </tr>
				 		
				 <tr>
				 		<td class="label">批次号</td>
						<td >
							<input loxiaType="input" style="width: 130px" name="pinkingListId" id="pinkingListId" />
						</td>
				 </tr>
			</table>
		  </form>
		  <div class="buttonlist">
				<button id="search2" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
				<button id="reset2" type="reset" loxiaType="button"><s:text name="button.reset"/></button>
				<button id="exportOrder2" type="button" loxiaType="button" class="confirm">导出</button>
		  </div>
		  <div>
			    <table id="tbl-dispatch-list2"></table>
		  </div>
		  <div id="pager3"></div>
		</div>
		<div id="div3" class="hidden">
		
			<table width="90%">
				<tr>
					<td class="label">批次号</td>
					<td>
						<label id="pinkingListId_d"></label>
					</td>
				</tr>
				
			</table>
			<br /><br />
				<div><table id="tbl-product2"></table></div>
				<div id="pager4"></div>
				<div id="btnlist2" class="buttonlist" >
					<button loxiaType="button" class="confirm" id="back2"><s:text name="button.back"></s:text> </button>
				</div>
		</div>		
		
</div>

			<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="pdaLogFrame">	
				<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
				<button type="button" loxiaType="button" class="confirm" id="import" >基础信息导入</button>
	        	<a id="downloadTemplate" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_pro_info_maintain"></s:text>.xls&inputPath=tplt_import_pro_info_maintain.xls">
					<span class="ui-button-text">基础信息模版文件下载</span>
				</a>
			</form>
			
			<form method="post" enctype="multipart/form-data" id="skuSpTypeImportForm" name="skuSpTypeImportForm" target="pdaLogFrame">	
				<input type="file" name="skuSpTypeImportFile" loxiaType="input" id="skuSpTypeImportFile" style="width:200px"/>
				<button type="button" loxiaType="button" class="confirm" id="skuSpTypeImport" >SKU编码与耗材绑定</button>
	        	<a id="downloadTemplate" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=SKU编码与耗材绑定模板.xls&inputPath=tplt_sku_Supplies.xls">
					<span class="ui-button-text">SKU编码与耗材绑定模版下载</span>
				</a>
			</form>
			<iframe id="pdaLogFrame" name="pdaLogFrame" class="hidden"></iframe>
</div>
	</body>
</html>