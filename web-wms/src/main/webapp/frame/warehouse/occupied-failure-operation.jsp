<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title>配货失败处理</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/occupied-failure-operation.js' includeParams='none' encode='false'/>"></script>
<style>
.showborder {
	border: thin;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="orderList">
		<form id="queryForm">
			<table>
				<tr>
					<td width="15%" class="label">创建时间：</td>
					<td width="25%"><input loxiaType="date" name="createTime" showTime="true"></input></td>
					<td width="10%" class="label" style="text-align: center;">到</td>
					<td width="25%"><input loxiaType="date" name="endCreateTime" showTime="true"/></td>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td class="label">相关单据号:</td>
					<td><input loxiaType="input" name="sta.slipCode"/></td>
					<td class="label">店铺:</td>
					<td>
						<div style="float:left">
							<select id="companyshop" name="sta.owner" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</div>						
						<div style="float:left">
							<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
						</div>
					</td>
					<td class="label">状态:</td>
					<td>
						<select id="statusList" name="sta.intStaStatus" loxiaType="select">
							<option value="">--请选择--</option>
							<option value="1">新建</option>
							<option value="20">配货失败</option>
							<option value="25">冻结</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search" >查询</button>
			<button type="button" loxiaType="button" id="reset">重置</button>
			<font class="label" style="color:red">　　默认查询近一个月的单据，否则请加上时间条件！</font>
			　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　
			<button type="button" loxiaType="button" class="confirm" id="batchOcc">批量占用</button>
			<!-- 
			<button type="button" loxiaType="button" id="allOcc">全部重新占用</button>
			 -->
		</div>
		<table id="tbl-orderList"></table>
		<div id="pager"></div>
	</div>
	<div id="div2" class="hidden">
		<table id="tbl-orderList-detail"></table>
		<div class="buttonlist">
			<button type="button" loxiaType="button" id="back">返回</button>
		</div>
	</div>
	<jsp:include page="../../common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>