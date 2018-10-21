<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>店铺SKU库存分配</title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/nike-sku-inv-allocation.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id = "div1">
		<div class="buttonlist" id="save_div">
			<table>
				<tr>
				    <td class="label">库存优先店铺配置：</td>
				    <td class="label">来源店铺</td>
					<td width="160px"><select id="sourceOwner" name="sourceOwner" loxiaType="select" ></select></td>
					<td class="label">目标店铺</td>
					<td width="160px"><select id="targetOwner" name="targetOwner" loxiaType="select" ></select></td>
					<td class="label">优先店铺</td>
					<td width="160px"><select id="priorityOwner" name="priorityOwner" loxiaType="select" ></select></td>
					<td width="160px"><button type="button" loxiaType="button" class="confirm" id="save" >保存</button></td>
				</tr>
			</table>
			
		</div>
		<table id="tbl-priority-owner"></table>
		<div id="pager1"></div> 
		<div class="buttonlist" id="update_div">
			<table>
				<tr>
				    <td class="label">来源店铺</td>
					<td width="160px"><input loxiaType="input" type="hidden" readonly="readonly" name="sourceOwnerU" trim="true" id="sourceOwnerU"/>
						<input loxiaType="input" readonly="readonly" name="sourceOwnerU1" trim="true" id="sourceOwnerU1"/>
					</td>
					<td class="label">目标店铺</td>
					<td width="160px"><input loxiaType="input" type="hidden" readonly="readonly" name="targetOwnerU" trim="true" id="targetOwnerU"/>
						<input loxiaType="input"  readonly="readonly" name="targetOwnerU1" trim="true" id="targetOwnerU1"/>
					</td>
					<td class="label">优先店铺</td>
					<td width="160px"><select id="priorityOwnerU" name="priorityOwnerU" loxiaType="select" ></select></td>
					<td ><input type="hidden" id="priorityOwnerId"/><button type="button" loxiaType="button" class="confirm" id="update" >修改</button></td>
					<td ><button type="button" loxiaType="button" class="confirm" id="cancel" >取消</button></td>
				</tr>
			</table>
			
		</div>
		<div class="buttonlist">
			<table>
				<tr>
					<td class="label">请选择导入文件:</td>
					<td>
						<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
							<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
						</form>
					</td>
			        <td>
			        	<button type="button" loxiaType="button" class="confirm" id="import" >导入</button>
			        	
			        </td>
			        <td>	
			          <a href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=店铺商品转店比例.xls&inputPath=tpl_sku_inv_allocation.xls" loxiaType="button">模板下载</a>
			        </td>
				</tr>
			</table>
		</div>
		
		
		<form id="form_query">
			<table>
				<tr>
				    <td class="label">SKU编码：</td>
					<td width="160px"><input loxiaType="input" name="skuCodeQ" id="skuCodeQ"  trim="true"/></td>
					<td class="label">来源店铺：</td>
					<td width="160px"><input loxiaType="input" name="sourceOwnerQ" id="sourceOwnerQ"  trim="true"/></td>
					<td class="label">目标店铺：</td>
					<td width="160px"><input loxiaType="input" name="targetOwnerQ" id="targetOwnerQ" trim="true"/></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<table>
				<tr>
					<button type="button" loxiaType="button" class="confirm" id="search">
						查询
					</button>
					<button type="button" loxiaType="button" id="reset">
						重置
					</button>
				</tr>
			</table>
		</div>
		
		<table id="tbl-transfer-owner-target"></table>
		<div id="pager"></div> 
		<!-- <div class="buttonlist">
			<table>
				<button type="button" loxiaType="button"  class="confirm" id="batchRemove">
					删除
				</button>
			</table>
		</div> -->
	</div>
	
	<div id="dialog_addWeight" style="text-align: center;" >
				<table id="tb_targetRatioUpdate">
				        <tr>
				        	<td class="label">SKU编码</td>
				        	<td><span id="skuCodeD"></span></td>
				        	<td class="label">SKU条码</td>
				        	<td><span id="skuBarCodeD"></span><input id="transferOwnerTargetIdD" type="hidden" /></td>
				        </tr>
						
						<tr>
							<td class="label">商品名称</td>
				        	<td colspan="3"><span id="skuNameD"></span></td>
						</tr>
						<tr>
							<td class="label">来源店铺</td>
				        	<td><span id="sourceOwnerD"></span></td>
				        	<td class="label">来源店铺占比</td>
				        	<td><span id="sourceRatioD"></span></td>
						</tr>
						<tr><td class="label">目标店铺</td><td><span id="targetOwner0"></span></td><td class="label">目标店铺占比</td><td><input id="targetRatio0" type="text" /></td></tr>
						
				</table>
			<div class="buttonlist">
					<button type="button" loxiaType="button" class="confirm" id="saveTargetRatio">保存</button>
					<button type="button" loxiaType="button" id="closediv">关闭</button>
			</div>
         </div>
	<iframe id="upload" name="upload" class="hidden"></iframe>
</body>
</html>