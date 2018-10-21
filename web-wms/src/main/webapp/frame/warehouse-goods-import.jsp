<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title>商品导入</title>
<script type="text/javascript" src="../scripts/frame/warehouse-goods-import.js"></script>
</head>
<body>
<div>
							<form id="queryForm">
								<table>
									<tr>
										<td width="5%" >品牌:</td>
										<td width="15%"><input loxiaType="input" trim="true" maxlength="50" name="owner" id="owner"/></td>
										<td width="5%" >UPC:</td>
										<td width="15%"><input loxiaType="input" trim="true" maxlength="50" name="upc" id="upc"/></td>
										<td width="5%" >HScode:</td>
										<td width="15%"><input loxiaType="input" trim="true" maxlength="50" name="hsCode" id="hsCode"/></td>
										<td width="5%" >商品编码:</td>
										<td width="15%"><input loxiaType="input" trim="true" maxlength="50" name="skuCode" id="skuCode"/></td>
									</tr>
									<tr>
										<td width="5%" >款式:</td>
										<td width="15%"><input loxiaType="input" trim="true" maxlength="50" name="style" id="style"/></td>
										<td width="5%" >颜色:</td>
										<td width="15%"><input loxiaType="input" trim="true" maxlength="50" name="color" id="color"/></td>
										<td width="5%" >尺码:</td>
										<td width="15%"><input loxiaType="input" trim="true" maxlength="50" name="skuSize" id="skuSize"/></td>
									</tr>
									</tr>
										<td width="5%" >是否打折商品:</td>
										<td width="15%">
											<select loxiaType="select" name="isDiscount" id="isDiscount" >
												<option value="0">否</option>
												<option value="1">是</option>
											</select>
										</td>
										<td width="5%" >状态:</td>
										<td width="15%">
											<select loxiaType="select" name="status" id="status" >
												<option value="1">未同步</option>
												<option value="2">同步成功</option>
												<option value="3">同步失败</option>
											</select>
										</td>
										<td width="5%" >商品中文名:</td>
										<td width="15%"><input loxiaType="input" trim="true" maxlength="50" name="skuName" id="skuName"/></td>
									</tr>
									<tr>
										<td width="5%"><input type="checkbox"  maxlength="50" name="isService" id="isService"/></td>
										<td width="5%" >只显示需维护数据的商品</td>
									</tr>
								</table>
							</form>
							<div class="buttonlist">
								<button type="button" loxiaType="button" class="confirm" id="search" >查询</button>
								<button type="button" loxiaType="button" id="reset">重置</button>
							</div>
							
							<table> 
							    <tr>
							        <td width="30%">
							            <form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
							                <input type="file" loxiaType="input" id="inboundRoleFile" name="inboundRoleFile"/>
							            </form>
							        </td>
							        <td>
							            <button type="button" loxiaType="button" id="import" class="confirm">保税仓商品主档导入</button>
							            <button type="button" loxiaType="button" id="export" class="confirm">保税仓商品主档导出</button>
						             	<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=保税仓商品主档.xls&inputPath=warehouse_goods_import.xls" class="confirm">模板文件下载</a>
						             	<button type="button" loxiaType="button" id="delete" style="color:red; background:white;border-style:solid;border-width:1px;border-color:red;">删除</button>
						             	<button type="button" loxiaType="button" id="push" style="color:red; background:white;border-style:solid;border-width:1px;border-color:red;">推送</button>
							        </td>
							        <td>
							        </td>
								</tr>
							</table>
							<table id="tbl-list"></table>
</div>
</body>
</html>