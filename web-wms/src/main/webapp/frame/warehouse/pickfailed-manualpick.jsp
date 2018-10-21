<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title>配货批次分配</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/pickfailed-manualpick.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/priority_city_config.js' includeParams='none' encode='false'/>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	<form id="query-form">
		<span class="label" style="font-size: 15px;" >基础条件</span>
		<table id="filterTable" width="80%">
				<tr>
					<td class="label"  style="color: red;" width="8%">店铺</td>
					<td width="20%">
						<select  class="special-flexselect" id="shopLikeQuery" name="shoplist" data-placeholder="*请输入店铺名称">
						</select>
					</td>
					<td class="label" width="8%">作业单号</td>
					<td width="20%"><input name="sta.code" loxiaType="input" trim="true"/></td>
					<td class="label">相关单据号</td>
					<td><input name="sta.refSlipCode" loxiaType="input" trim="true"/></td>
				</tr>
				<tr>
					<td class="label">优先发货城市</td>
					<td>
						<select name="city" id="priorityCity" loxiaType="select">
							<option value="">--请选择--</option>
						</select>
					</td>
					<td class="label">运单时限类型</td>
					<td>
						<select name="transTimeType" id="transTimeType" loxiaType="select" >
							<option value="">--请选择运单时限类型--</option>
						<option value="1">普通</option>
						<option value="3">及时达</option>
						<option value="5">当日</option>
						<option value="6">次日</option>
						<option value="7">次晨</option>
						<option value="8">云仓专配次日</option>
						<option value="9">云仓专配隔日</option>
						</select>
					</td>
					<td class="label">物流商</td>
					<td>
						<select name="lpcode" id="selLpcode" loxiaType="select">
							<option value="">--请选择--</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="label" width="13%">平台订单相关时间</td>
					<td width="20%"><input loxiaType="date" name="orderCreateTime" showtime="true"/></td>
					<td class="label" width="13%">至 </td>
					<td width="20%"><input loxiaType="date" name="toOrderCreateTime" showtime="true"/></td>
					<td class="label"  >是否COD订单</td>
					<td>
						<select name="isCod" id="isCod" loxiaType="select">
							<option value="">--不限制--</option>
							<option value="1">COD订单</option>
							<option value="0">非COD订单</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label" style="color: blue;" >分配状态</td>
					<td>
						<select name="pickStatus" id="pickStatus" loxiaType="select">
							<option value="">--请选择--</option>
							<option value="1">未分配</option>
							<option value="2">分配失败</option>
						</select>
					</td>
					<td class="label">优先级</td>
					<td>
						<select id="sortChoose1" name="pickSort" loxiaType="select">
							<option value="">--请选择--</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
						</select>
					</td>
					<td class="label">商品分类</td>
					<td>
						<select name="categoryId" id="categoryId" loxiaType="select">
							<option value="">--请选择商品分类--</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label" width="13%">创建时间 </td>
					<td width="20%"><input loxiaType="date" name="fromDate" showtime="true"  id="fromDate11"/></td>
					<td class="label" width="13%">至</td>
					<td width="20%"><input loxiaType="date" name="toDate" showtime="true"/></td>
					<td class="label">是否需要发票</td>
					<td>
						<select id="selIsNeedInvoice" name="isNeedInvoice" loxiaType="select">
							<option value="">--请选择--</option>
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="label" width="13%">预售订单 </td>
					<td width="20%">
							<select name="isPreSale" id="isPreSale" loxiaType="select">
								<option value="" selected="selected">--请选择--</option>
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
					</td>
					<td class="label" width="13%"></td>
					<td width="20%"></td>
					<td class="label"></td>
					<td>
						
					</td>
				</tr>
			</table>
	</form>
	</br>
	<button type="button" id="btn-query" loxiaType="button" class="confirm">查询</button>
	<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
	<div class="buttonlist">
	</div>
	<table>
		<tr>
			<td>
				<span class="label" style="font-size: 15px;" >快捷操作区</span>
			</td>
			<td>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;将所有分配失败的订单添加至下次分配队列：
			</td>
			<td>
				<button type="button" id="pickAgain" loxiaType="button" class="confirm">失败再分配</button>
			</td>
		</tr>
	</table>
	<div class="buttonlist">
		<table id="tbl-staList-query"></table>
		<div id="pager_query"></div>
		<br>
		<table>
		<tr>
			<td>
				<span class="label">下次硬分配优先级（非必选）:</span>
			</td>
			<td width = "160px">
				<select id="sortChoose2" name="sortChoose" loxiaType="select">
							<option value="">--请选择--</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							
				</select>
			</td>
			<td style="color: red">
				说明：数字越小，下次系统硬分配时将更优先分配
			</td>
		</tr>
	</table>
	<br>
	<button type="button" id="queryAgainPick" loxiaType="button" class="confirm">查询结果再分配</button>
	</div>
	
	
</body>
</html>