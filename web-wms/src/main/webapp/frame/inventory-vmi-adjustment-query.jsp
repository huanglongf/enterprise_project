<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/inventory-vmi-adjustment-query.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.inventory.check.query"/></title>
<style>
.clear{
	clear:both;
	height:0;
    line-height:0;
}
</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="divPd">
		<form id="query_form" name="query_form">
			<table width="80%">
				<tr>
					<td class="label" width="15%">
						<s:text name="label.warehouse.pl.createtime"/>
					</td>
					<td width="15%">
						<input loxiaType="date" name="iccommand.startDate1" showTime="true"/>
					</td>
					<td class="label" width="15%">
						<s:text name="label.warehouse.inventory.check.to"/>
					</td>
					<td width="15%">
						<input loxiaType="date" name="iccommand.endDate1" showTime="true"/>
					</td>
					<td width="15%" class="label"><s:text name="选择店铺"></s:text> </td>
					<td width="15%">
						<div style="float: left;width: 260px">
							<select id="companyshop" name="iccommand.owner" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</div>						
					</td>
						
					</td>
				</tr>
				<tr>
					<td class="label">
						<s:text name="label.vmi.inventory.check.code"/>
					</td>
					<td>
						<input name="iccommand.code" loxiaType="input" trim="true"/>
					</td>
					<td class="label" width="15%">
						前置单据
					</td>
					<td width="15%">
						<input name="iccommand.slipCode" loxiaType="input" trim="true"/>
					</td>	
					<td class="label" width="15%">
						<s:text name="label.warehouse.pl.status"/>
					</td>
					<td width="15%">
						<select id="intStatus" loxiaType="select" name="iccommand.intStatus">
							<option value="">请选择</option>
							<option value="0">取消</option>
							<option value="1">新建</option>
							<option value="5">差异未处理</option>
							<option value="10">完成</option>
						</select>
					</td>		
					
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
	<div id="divList" class="hidden">
		<table width="65%">
			<tr>
				<td class="label">
					<s:text name="label.vmi.inventory.check.code"/>
				</td>
				<td id="code"></td>
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
					<s:text name="label.warehouse.pl.slipCode"/>
				</td>
				<td id="slipCode">
				</td>
			</tr>
		</table>
		
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1"><s:text name="excel.tplt_export_vmi_adjustment_invcheck"></s:text> </a></li>
				<li><a href="#tabs-2"  id="exp_li"><s:text name="excel.tplt_export_vmi_adjustment_detail"></s:text> </a></li>
			</ul>
			<div id="tabs-1">
				<table id="tbl_ex_detial"></table>
				<div id="pager1"></div>
			</div>
			<div id="tabs-2">
				<table id="tbl_ex_detial2"></table>
				<div id="pager2"></div>
			</div>
		</div>
		<div class="buttonlist">
			<button id="back" loxiaType="button" class="confirm"><s:text name="button.back"/></button>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>