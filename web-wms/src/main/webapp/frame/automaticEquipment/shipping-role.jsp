<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>集货管理</title>
<%@include file="/common/meta.jsp"%>	
<script type="text/javascript" src="<s:url value='/scripts/frame/automaticEquipment/shipping-role.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<form id="form_query">
			<table>
				<tr>
					<td class="label">集货口：</td>
					<td width="160px"><input loxiaType="input" name="code"  id="pointIds"/></td>
					<td style="color: red" colspan="2">
							&nbsp;&nbsp;注：优先级数字越小，优先级越高
					</td>
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
		
		<table id="tbl-shipping-role-line"></table>
		<div id="pager"></div> 
		<table>
			<tr>
				<td>
					<button type="button" loxiaType="button" class="confirm" id="addSub">
						<s:text name="button.add"></s:text>
					</button>
				</td>
				<td align="left"><b>选择文件:</b></td>
				<td  align="center">
					<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
						<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
					</form>
				</td>
			<td> 
				<button loxiaType="button" class="confirm" id="import">批量导入</button>
	             <a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=集货规则导入.xls&inputPath=tplt_import_shipping_role_line.xls">模板文件下载</a>
	        </td>
			</tr>
		</table>
		<div class="buttonlist">
			<table>
				<tr>
					<button loxiaType="button" class="confirm" id="batchRemove">批量删除</button>
				</tr>
			</table>
		</div>
		
	<div id="dialog_addRoleLine">
			<form id="form_info2">
				<table>
					<tr>
						<td class="label" width="10%">物流商：</td>
						<td width="20%">
							<select loxiaType="select" id="selectLpCode" name="roleLine.lpcode">
								<option value="" selected="selected">--请选择--</option>
							</select>
						</td>
						<td class="label" width="10%">时效类型：</td>
						<td width="20%">
							<select loxiaType="select" name="roleLine.timeTypes" id="selTrans8" >
								<option value="">--请选择--</option>
								<option value="1">普通</option>
								<option value="3">及时达</option>
								<option value="5">当日</option>
								<option value="6">次日</option>
								<option value="7">次晨</option>
								<option value="8">云仓专配次日</option>
								<option value="9">云仓专配隔日</option>
							</select>
						</td>
						<td class="label" width="10%">店铺：</td>
						<td width="20%">
							<select loxiaType="select" id="shopLikeQuery" name="roleLine.owner">
								<option value="" selected="selected">--请选择--</option>
							</select>
						</td>
					<tr>
					<tr>
						<td class="label" width="10%">省：</td>
						<td width="20%">
							<input loxiaType="input" trim="true" id = "provinceInput" name="roleLine.province"/>
							<%-- <select loxiaType="select" id="provinceikeQuery" name="roleLine.province">
								<option value="" selected="selected">--请选择--</option>
							</select> --%>
						</td>
						<td class="label" width="10%">市：</td>
						<td width="20%">
							<input loxiaType="input" trim="true" id = "cityInput" name="roleLine.city"/>
							<%-- <select loxiaType="select" id="cityLikeQuery" name="roleLine.city">
								<option value="" selected="selected">--请选择--</option>
							</select> --%>
						</td>
						<td class="label" width="10%">区：</td>
						<td width="20%">
							<input loxiaType="input" trim="true" id = "districtInput" name="roleLine.district"/>
							<%-- <select loxiaType="select" id="districtLikeQuery" name="roleLine.district">
								<option value="" selected="selected">--请选择--</option>
							</select> --%>
						</td>
					<tr>
					<tr>
						<td class="label" width="10%">地址：</td>
						<td width="20%">
							<input loxiaType="input"  id = "address" maxlength="150" name="roleLine.address"/>
						</td>
						<td class="label" width="10%">作业类型：</td>
						<td width="20%">
							<select loxiaType="select" id="typeList" name="roleLine.types">
								<option value="">--请选择--</option>
								<!-- <option value="11">采购入库 </option>
								<option value="12">结算经销入库</option>
								<option value="13">自定义入库</option>
								<option value="14">代销入库</option>
								<option value="15">赠品入库 </option>
								<option value="16">移库入库 </option>
	                            <option value="17">货返入库 </option> -->
	   						    <option value="21">销售出库</option>
								<option value="22">自定义出库</option>
								<option value="25">外部订单销售出库 </option>
								<option value="31">库内移动 </option>
								<option value="32">库间移动 </option>
								<!-- <option value="41">退换货申请-退货入库 </option> -->
								<option value="42">换货出库 </option>
								<option value="45">库存状态修改 </option>
								<option value="50">库存初始化 </option>		
		                       <!--  <option value="55">GI 上架 </option> -->
								<option value="61">采购出库（采购退货出库） </option>
								<option value="62">结算经销出库 </option>
								<option value="63">包材领用出库</option>
								<option value="64">代销 出库</option>
								<!-- <option value="81">VMI移库入库 </option>
								<option value="85">VMI库存调整入库 </option> -->
								<option value="86">VMI库存调整出库 </option>    					
	                            <option value="88">转店 </option>
								<option value="90">同公司调拨 </option>
	                            <option value="91">不同公司调拨 </option> 
								<option value="101">VMI退大仓  </option>
								<option value="102">VMI转店退仓 </option>
								<option value="110">库存锁定 </option>
	                            <option value="201">福利领用 </option>
	                            <option value="202">固定资产领用 </option>
								<option value="204">报废出库 </option>
	                            <option value="205">促销领用 </option>
	                            <option value="206">低值易耗品 </option>
								<option value="210">样品领用出库 </option>
	                            <!-- <option value="211">样品领用入库 </option> -->
	                            <option value="212">商品置换出库 </option>
								<!-- <option value="213">商品置换入库 </option> -->
	                            <option value="214">送修出库 </option>
	                            <!-- <option value="215">送修入库 </option> -->
								<option value="216">串号拆分出库 </option>
	                           <!--  <option value="217">串号拆分入库 </option> -->
	                            <option value="218">串号组合出库 </option>
								<!-- <option value="219">串号组合入库 </option> -->
								<option value="251">盘点调整</option>
							</select>
						</td>
						<td class="label" width="10%">作业单号：</td>
						<td width="20%"><input loxiaType="input" trim="true" id = "staCode" name="roleLine.staCode"/></td>
					<tr>
					<tr>
						<td class="label" width="10%">是否COD：</td>
						<td width="20%">
							<select loxiaType="select" name="roleLine.isCods" id="isCod" >
								<option value="">--请选择--</option>
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</td>
						<td class="label">集货口：</td>
						<td width="160px">
							<select  loxiaType="select"  mandatory="true"  name="pointId"  id="pointName">
							</select>
						</td>
						<td>
							<span class="label">优先级:</span>
						</td>
						<td width = "160px">
							<input loxiaType="input" id="sort" name="roleLine.sort"  mandatory="true" onkeyup="this.value=this.value.replace(/\D/g,'')" width="120px"></input>
						</td>
					</tr>
				</table>
			</form>
			<div class="buttonlist">
				<table>
					<tr>
						<button type="button" loxiaType="button" class="confirm" id="saveSub">
							<s:text name="button.save"></s:text>
						</button>
						<button type="button" loxiaType="button" id="closeSub">
							关闭
						</button>
					</tr>
				</table>
			</div>
		</div>
		<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
		<iframe id="upload" name="upload" class="hidden"></iframe>
</body>
</html>