<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/vmi-rto-query.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="VMI退仓接收指令查询"/></title>
<style>
.clear{
	clear:both;
	height:0;
    line-height:0;
}
</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="divRtoList">
		<form id="query_form" name="query_form">
			<table width="80%">
				<tr>
					<td class="label" width="15%">
						<s:text name="创建时间"/>
					</td>
					<td width="15%">
						<input loxiaType="date" name="cmd.startDate1" showTime="true"/>
					</td>
					<td class="label" width="15%">
						<s:text name="label.warehouse.inventory.check.to"/>
					</td>
					<td width="15%">
						<input loxiaType="date" name="cmd.endDate1" showTime="true"/>
					</td>
					<td class="label" width="15%">
						<s:text name="状态"/>
					</td>
					<td width="15%">
						<select id="intStatus" loxiaType="select" name="cmd.intStatus">
							<option value="">请选择</option>
							<option value="0">锁定</option>
							<option value="1">新建/未发送</option>
							<option value="10">完成/发送成功</option>
							<option value="11">重复订单</option>
							<option value="17">取消</option>
							<option value="20">处理失败/待邮件通知</option>
							<option value="21">邮件通知成功</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="15%" class="label"><s:text name="绑定店铺"></s:text> </td>
					<td width="15%">
						<div style="float: left">
							<select id="companyshop" name="sta.owner" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</div>						
					</td>
					<td width="15%" class="label">
						<s:text name="退仓单号"/>
					</td>
					<td>
						<input name="cmd.orderCode" loxiaType="input" trim="true"/>
					</td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</form>			
		<div class="buttonlist">
			<button id="query" loxiaType="button" class="confirm"><s:text name="button.search"/></button>&nbsp;&nbsp;
			<button id="reset" loxiaType="button" class=""><s:text name="button.reset"/></button>
			<div class="clear"></div>
		</div>
		<table id="tbl_list"></table>
		<div id="pager"></div>
		<div class="clear"></div>
	</div>
	<div id="divDetailList" class="hidden">
		<table width="65%">
			<tr>
				<td class="label">
					<s:text name="VMI指令"/>
				</td>
				<td id="orderCode"></td>
				<td class="label">
					<s:text name="label.warehouse.pl.status"/>
				</td>
				<td id="status">
				</td>
			</tr>
			<tr>
				<td class="label">
					<s:text name="label.warehouse.pl.createtime"/>
				</td>
				<td id="createTime">
				</td>
				<td class="label">
					<s:text name="作业单号"/>
				</td>
				<td id="staCode">
				</td>
			</tr>
		</table>
		<table id="tbl_detail"></table>
		<div id="pagerDetail"></div>
		<div class="buttonlist">
			<button id="back" loxiaType="button" class="confirm"><s:text name="button.back"/></button>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>