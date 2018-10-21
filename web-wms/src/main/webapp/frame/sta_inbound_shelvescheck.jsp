<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/sta_inbound_shelvescheck.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
.showborder {
	border: thin;
}
.divFloat{
	float: left;
	margin-right: 10px;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	<div id="divHead">
		<form id="queryForm" name="queryForm">
			<table width="700px">
				<tr>
					<td class="label" width="15%">店铺</td>
					<td width="35%">
						<div style="float: left">
							<select id="companyshop" name="sta.owner" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</div>						
						<div style="float: left">
							<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
						</div>
					</td>
					<td width="15%" class="label">作业单类型</td>
					<td width="35%">
						<select loxiaType="select" id="typeList" name="sta.intStaType">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="11">采购入库</option>
						<!-- 	<option value="12">结算经销入库</option>
							<option value="14">代销入库 </option>
							<option value="15">赠品入库 </option> -->
							<option value="16">移库入库 </option>
						<!-- 	<option value="17">货返入库</option>
							<option value="32">库间移动</option> -->
							<option value="81">VMI移库入库</option>
						<!-- 	<option value="90">同公司调拨</option>
							<option value="91">不同公司调拨</option> -->
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">作业单号</td>
					<td>
						<input type="text" loxiaType="input" trim="true" name="sta.code"></input>
					</td>
					<td class="label">相关单据号</td>
					<td>
						<input type="text" id="refSlipCode" loxiaType="input" trim="true" name="sta.refSlipCode"></input>
					</td>
				</tr>
				<tr>
					<td class="label">辅助相关单据号</td>
					<td>
						<input type="text" loxiaType="input" trim="true" name="sta.slipCode1"></input>
					</td>
					<td></td>
					<td>
					</td>
				</tr>
				<tr>
					<td width="10%" class="label">创建时间</td>
					<td width="15%"><input loxiaType="date" name="createTime" showTime="true"></input></td>
					<td width="10%" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="15%"><input loxiaType="date" name="endCreateTime" showTime="true"/></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search">查询</button>
			<button loxiaType="button" id="reset">重置</button>
		</div>
		<table id="tbl_sta_list"></table>
		<div id="pager"></div>
	</div>
	<div id="divDetial" class="hidden">
		<input type="hidden" id='sta_id'/>
		<input type="hidden" id='stv_id'/>
		<table width="700px">
			<tr>
				<td width="15%" class="label">
					作业单号
				</td>
				<td width="35%" id="sta_code"></td>
				<td width="15%" class="label">
					相关单据号
				</td>
				<td width="35%" id="sta_refSlipCode"></td>
			</tr>
			<tr>
				<td class="label">
					辅助相关单据号
				</td>
				<td id="sta_slipCode1"></td>
				<td class="label">
					创建时间
				</td>
				<td id="sta_createTime"></td>
			</tr>
			<tr>
				<td class="label" >
					作业类型
				</td>
				<td id="sta_type"></td>
				<td class="label">
					作业单状态
				</td>
				<td id="sta_status"></td>
			</tr>
			<tr>
				<td class="label">
					备注
				</td>
				<td colspan="3" id='sta_memo'></td>
			</tr>
		</table>
		<br/>
		
		<table id="tbl_sta_line_list"></table>
		<br/>
		<div class="buttonlist">
			<table>
				<tr>
					<!-- <td>
						<button type="button" loxiaType="button" class="confirm" id="confim" onclick="confimPutOn()">审核</button>
					</td> -->
					<td><button loxiaType="button" id="mergeBack" onclick="backClick()">返回</button></td>
				</tr>
			</table>
			<br/>
		</div>
	</div>
	
	<iframe id="upload" name="upload" style="display:none;"></iframe>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>