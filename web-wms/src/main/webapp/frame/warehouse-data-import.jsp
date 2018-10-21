<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title>wms3.0数据导出</title>
<style>
	.ui-loxia-table tr.error{background-color: #FFCC00;}
	.divFloat{
		float: left;
		margin-right: 10px;
	}
</style>
<script type="text/javascript" src="<s:url value='/scripts/frame/include-shop-query-multi-data-import.js' includeParams='none' encode='false'/>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="tabs" >
		<ul>
			<li><a href="#tabs-1">历史SN、效期流水导出</a></li>
			<li><a href="#tabs-2">未完成品牌入库指令导出</a></li>
		</ul>
		<!------------------- tab1历史SN、效期流水导出  begin---------------->
		<div id="tabs-1" >
			<form id="form_info" name="form_info" method="post"  >
				<span style="font-size: 15px; font-weight:bold;" >导出条件</span>
				<table>
				   <tr>
						<td width="15%" class="label">销售单创建时间：</td>
						<td width="25%"><input loxiaType="date" id="c1" name="createTime" showTime="true" /></input></td>
						<td width="15%" class="label" style="text-align: center;">结束时间： </td>
						<td width="25%"><input loxiaType="date" id="e1" name="endCreateTime" showTime="true"/></td>
						<td width="20%"></td>
				   </tr>
				   <tr>
						<td width="15%" class="label" >店铺名称：</td>
						<td width="25%" id="clear">
							<select class="special-flexselect" id="shopLikeQuery" name="shopLikeQuery" data-placeholder="请输入店铺名称"></select>	
						</td>
						<td width="15%">
						    <button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
						</td>
				   </tr>
				   <tr>
				       <td class="label" width="15%"></td>
				       <td colspan="4">
				            <textarea id='showShopList' rows="3" cols="100" disabled="disabled"></textarea>
				            <input type="hidden"  id="postshopInput" name="postshopInput"/>
				            <input type="hidden"  id="postshopInputName" name="postshopInputName"/>
					   </td>
				   </tr>
				   <tr>
				       <td class="label" width="15%">平台单据号：</td>
				       <td colspan="4">
							<textarea id='slipcodes' name="slipcodes" rows="3" cols="100"></textarea>
					   </td>
				   </tr>
				   <tr>
				       <!-- font-weight:normal; -->
				       <td class="label" width="15%" style="color: red; ">说明：</td>
				       <td class="label" width="26%" style="text-align: left;color: red; ">多个平台单据号输入请以英文分号(;)隔开</td>
				   </tr>
				</table>
			</form>
			<!-- 生成、下载按钮 -->
			<div class="buttonlist" >
			    <button type="button" loxiaType="button" id="btn-create1" class="confirm" >生成</button>
			    <button type="button" loxiaType="button" id="btn-create-hidden1" class="hidden" disabled="true" >生成</button>
			    <button type="button" loxiaType="button" id="btn-dowload1" class="confirm">下载</button>
			    <button type="button" loxiaType="button" id="btn-dowload-hidden1" class="hidden" disabled="true">下载</button>
				<button type="button" loxiaType="button" id="reset1"><s:text name="button.reset"></s:text> </button>
				<br />
				<table id="tbl-staList-query"></table>
				<div id="pager_query"></div>
		    </div>
		    <span id='span1' style="color:red;font-weight:bold; class='hidden'">文件生成中，请耐心等待......</span>
	    </div>
        <!-- 店铺列表 -->
		<div id="shopQueryDialog">
		    <input id="selTransopc" hidden/>
		    <form id="shopQueryDialogForm" name="shopQueryDialogForm">
				<table width="95%">
					<tr>
						<td class="label" style="text-align: wright;">
							店铺名称:
						</td>
						<td>
							<input id="shopName" loxiaType="input" trim="true" name="shopName"/>
						</td>
					</tr>
				</table>
			</form>
		    <div class="buttonlist">
				<button type="button" loxiaType="button" class="confirm" id="btnShopQuery" >查询</button>
				<button type="button" loxiaType="button" id="btnShopFormRest" >重置</button>
	        </div>
			<table id="tbl_shop_query_dialog"></table>
			<div id="tbl_shop_query_dialog_pager"></div>
			<br/>
			<button type="button" loxiaType="button" class="confirm" id="btnShopQueryConfirm" >确认</button>
			<button type="button" loxiaType="button" id="btnShopQueryClose" >取消</button>
		</div>
		<!------------------- tab1历史SN、效期流水导出  end---------------->
		<!------------------- tab2未完成品牌入库指令导出  start---------------->
		<div id="tabs-2">
			<form id="form_info2" name="form_info2" method="post"  >
				<span style="font-size: 15px; font-weight:bold;" >导出条件</span>
				<table>
				   <tr>
						<td width="15%" class="label" >店铺名称：</td>
						<td width="25%" id="clear2">
						   <select class="special-flexselect" id="shopLikeQuery2" name="shopLikeQuery2" data-placeholder="请输入店铺名称"></select>	 
						</td>
						<td width="15%">
						    <button type="button" loxiaType="button" class="confirm" id="btnSearchShop2" >查询店铺</button>
						</td>
						<td width="45%"></td>
				   </tr>
				   <tr>
				       <td class="label" width="15%"></td>
				       <td colspan="3">
				            <textarea id='showShopList2' rows="3" cols="100" disabled="disabled"></textarea>
				            <input type="hidden"  id="postshopInput2" name="postshopInput"/>
				            <input type="hidden"  id="postshopInputName2" name="postshopInputName2"/> 
					   </td>
				   </tr>
				   <!-- <tr class='hidden'>
				       <td class="label" width="15%">仓库列表：</td>
				       <td colspan="4">
							<ul id="groupTree" animate="true"></ul>
					   </td>
				   </tr> -->
				   <tr>
				      <td class="label" width="15%">绑定数据来源：</td>
				      <td colspan="3">
						 <!--   <table id="myTab" style="border: 1px solid gray " >
						  		<tr>
						  			<td style="width:150px;font-weight:bold;  background-color:#efefef;" align="center">已选店铺</td>
						  			<td style="width:150px;font-weight:bold;  background-color:#efefef;" align="center">数据来源</td>
						  		</tr>
						  </table> -->
						<div  id="edittable" loxiaType="edittable" style="width:400px;" >
							<table id="dataSourceTable"  width="100%" operation="add,delete" append="1">
								<thead>
									<tr>
										<th style="width:30px;"><input type="checkbox"/></th>
										<th style="width:190px;">已选店铺</th>
										<th>数据来源</th>
										<th></th>
									</tr>
								</thead>
								<tbody></tbody>
								<tbody>
									<tr class="add" id="dataSourceList" index="(#)"  >
										<td><input type="checkbox"/></td>
									   <!--  <td id="dataSourceShopName"></td> -->
										<td><input  loxiatype="input"  name="addList(#).dataSourceShopName" mandatory="true"    /></td>
										<td><input  loxiatype="input"  name="addList(#).datasource" /></td>
			                            <td><input  type='hidden' loxiatype='input' name="addList(#).dataSourceShopCode" /></td> 
									</tr>
								</tbody>
								<tfoot></tfoot>
						    </table>
					    </div>
				    </td>
				   </tr>
				</table>
			</form>	
			<!-- 生成、下载按钮 -->
			<div class="buttonlist" >
			    <button type="button" loxiaType="button" id="btn-create2" class="confirm" >生成</button>
			    <button type="button" loxiaType="button" id="btn-create-hidden2" class="hidden" disabled="true" >生成</button>
			    <button type="button" loxiaType="button" id="btn-dowload2" class="confirm">下载</button>
			    <button type="button" loxiaType="button" id="btn-dowload-hidden2" class="hidden" disabled="true">下载</button>
				<button type="button" loxiaType="button" id="reset2"><s:text name="button.reset"></s:text> </button>
				<br />
		    </div>
		    <span id='span2' style="color:red;font-weight:bold;" class='hidden'>文件生成中，请耐心等待......</span>
		</div>
		<!-- 店铺列表 -->
		<div id="shopQueryDialog2">
		    <input id="selTransopc2" hidden/>
		    <form id="shopQueryDialogForm2" name="shopQueryDialogForm2">
				<table width="95%">
					<tr>
						<td class="label" style="text-align: wright;">
							店铺名称:
						</td>
						<td>
							<input id="shopName2" loxiaType="input" trim="true" name="shopName"/>
						</td>
					</tr>
				</table>
			</form>
		    <div class="buttonlist">
				<button type="button" loxiaType="button" class="confirm" id="btnShopQuery2" >查询</button>
				<button type="button" loxiaType="button" id="btnShopFormRest2" >重置</button>
	        </div>
			<table id="tbl_shop_query_dialog2"></table>
			<div id="tbl_shop_query_dialog_pager2"></div>
			<br/>
			<button type="button" loxiaType="button" class="confirm" id="btnShopQueryConfirm2" >确认</button>
			<button type="button" loxiaType="button" id="btnShopQueryClose2" >取消</button>
		</div>
		<!------------------- tab2未完成品牌入库指令导出  end---------------->
	</div>
</body>
</html>