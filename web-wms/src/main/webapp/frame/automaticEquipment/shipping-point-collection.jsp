<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>集货管理</title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/automaticEquipment/shipping-point-collection.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id = "div1">
		<form id="form_query">
			<table>
				<tr>
				    <td class="label">物理仓名称：</td>
					<td width="160px"><select id="physicalId" name="goodsCollection.physicalId.id" loxiaType="select" ></select></td>
<!-- 					<input loxiaType="input" name="goodsCollection.physicalId.id"  id="physicalId"  trim="true"/></td>
 -->					<td class="label">集货区域编码：</td>
					<td width="160px"><input loxiaType="input" name="goodsCollection.collectionCode"   trim="true"/></td>
					<td class="label">顺序：</td>
					<td width="160px"><input loxiaType="input" name="goodsCollection.sort" trim="true"/></td>
					<td class="label">弹出口：</td>
					<td width="160px"><input loxiaType="input" name="goodsCollection.popUpCode"  trim="true"/></td>
				</tr>
				<tr>
					<td class="label">通道：</td>
					<td width="160px"><input loxiaType="input" name="goodsCollection.passWay"   trim="true" id="passWay"/ ></td>
				    <td class="label">拣货模式：</td>
					<td width="160px"><select id="pickModel" name="goodsCollection.pickModel" loxiaType="select" ></select></td>
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
		<div class="buttonlist">
			<table>
			<tr>
			<td>物理仓名称：</td>
			<td><select id="physicalId3" name="goodsCollection.physicalId.id" loxiaType="select" ></select></td>
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
	          <a href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=collection_export.xls&inputPath=collection_export.xls" loxiaType="button">模板下载</a>
	        </td>
			</tr>
			</table>
		</div>
		<table id="tbl-shipping-point"></table>
		<div id="pager"></div> 
		<div class="buttonlist">
			<table>
				<button type="button" loxiaType="button"  class="confirm" id="add">
					新建
				</button>
				&nbsp;
				<button type="button" loxiaType="button"  class="confirm" id="batchRemove">
					删除
				</button>
			</table>
		</div>
	</div>
	
	<div id="dialog_addWeight" style="text-align: center;" >
				<table>
				        <tr><td><input id="t_wh_goods_collection_id" type="hidden" /></td></tr>
						
						<tr>
							<td>物理仓名称 <span style="color:red;">*</span></td>
							<td><select id="physicalId1" loxiaType="select" ></select>
							    <input loxiaType="input" trim="true" id="physicalId2" readonly="readonly"/>
						    </td>
						</tr>
						<tr>
							<td>集货区域编码 <span style="color:red;">*</span></td>
							<td><input loxiaType="input" trim="true" id="collectionCode"/>
						    </td>
						</tr>
						<tr>
							<td>顺序<span style="color:red;">*</span></td>
							<td><input loxiaType="input" trim="true" id="sort"/></td>
							
						</tr>
						<tr>
							<td>弹出口</td>
							<td><input loxiaType="input" trim="true" id="popUpCode"/></td>
						</tr>
						<tr>
							<td>通道<span style="color:red;">*</span></td>
							<td><input loxiaType="input" name="goodsCollection.passWay"   trim="true" id="passWay_1"/></td>
							
						</tr>
						<tr>
							<td>拣货模式</td>
							<td><select id="pickModel_1" name="goodsCollection.pickModel" loxiaType="select" ></td>
						</tr>
						
				</table>
			<div class="buttonlist">
					<button type="button" loxiaType="button" class="confirm" id="saveWeight">保存</button>
					<button type="button" loxiaType="button" id="closediv">关闭</button>
			</div>
         </div>
	<iframe id="upload" name="upload" class="hidden"></iframe>
</body>
</html>