<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/msg-inbound-order.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.sta.query"></s:text></title>
<style type="text/css">
	.divFloat{
		float: left;
		margin-right: 10px;
	}
	.divForm{
	   clear:both
	}
</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath()%>">
<iframe id="upload" name="upload" class="hidden"></iframe>

<div id="tabs">
	<ul>
		<li><a href="#tab-1"><s:text name="title.warehouse.msg.inbound.notice"/></a></li>
		<li><a href="#tab-2"><s:text name="title.warehouse.msg.inbound.rtn"/></a></li>
	</ul>
	<div id="tab-1">
		<div class="divForm">
		  <s:text id="pselect" name="label.please.select"/>
			<form id="quert-form" method="post">
					<table width="90%">
						<tr>
							<td class="label" width="13%"><s:text name="label.warehouse.msg.stacode"/></td>
							<td width="20%"><input loxiaType="input" trim="true" name="msgCmd.staCode" id="staCode" /></td>
							<td class="label" width="13%"><s:text name="label.warehouse.msg.slipcode"/></td>
							<td width="20%"><input loxiaType="input" trim="true" name="msgCmd.slipCode" id="slipCode"/></td>
						</tr>	
						<tr>
				            <td class="label"><s:text name="label.warehouse.pl.createtime"/></td>
							<td colspan="3">
								<input loxiaType="date" style="width: 150px" name="msgCmd.startDate1" id="startDate" showTime="true"/>
								<s:text name="label.warehouse.pl.to"/>
								<input loxiaType="date" style="width: 150px" name="msgCmd.endDate1" id="endDate" showTime="true"/>
							</td>
						</tr>
						<tr>
						    <td class="label" width="10%"><s:text name="label.warehouse.pl.status"/></td>
							<td width="20%"><s:select list="msgStatus" loxiaType="select" listKey="optionKey" listValue="optionValue" name="msgCmd.intStatus" id="status" headerKey="-2" headerValue="%{pselect}"/></td>
							<td colspan="2"></td>
						</tr>		
				</table>
			</form>
			<div class="buttonlist">
				<button type="button" id="query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
				<button type="button" id="reset" loxiaType="button"><s:text name="button.reset"></s:text> </button>
				<br /><br />
				<table id="tbl-inventory-query"></table>
				<div id="pager1"></div>
			</div>
		</div>
	</div>
	<div id="tab-2">
	    <div class="divForm">
	       <div id="divRtnList">
				<form id="quert-form2" method="post">
						<table width="90%">
							<tr>
								<td class="label" width="13%"><s:text name="label.warehouse.msg.stacode"/></td>
								<td width="20%"><input loxiaType="input" trim="true" name="msgRtnCmd.staCode" id="rtnStaCode" /></td>
								<td class="label" width="13%"><s:text name="label.warehouse.msg.slipcode"/></td>
								<td width="20%"><input loxiaType="input" trim="true" name="msgRtnCmd.slipCode" id="rtnSlipCode"/></td>
							</tr>	
							<tr>
					            <td class="label"><s:text name="label.warehouse.pl.createtime"/></td>
								<td colspan="3">
									<input loxiaType="date" style="width: 150px" name="msgRtnCmd.startDate1" id="rtnStartDate" showTime="true"/>
									<s:text name="label.warehouse.pl.to"/>
									<input loxiaType="date" style="width: 150px" name="msgRtnCmd.endDate1" id="rtnEndDate" showTime="true"/>
								</td>
							</tr>
							<tr>
							    <td class="label" width="10%"><s:text name="label.warehouse.pl.status"/></td>
								<td width="20%"><s:select list="msgRtnStatus" loxiaType="select" listKey="optionKey" listValue="optionValue" name="msgRtnCmd.intStatus" id="rtnStatus" headerKey="-2" headerValue="%{pselect}"/></td>
								<td colspan="2"></td>
							</tr>		
					</table>
				</form>
				<div class="buttonlist">
					<button type="button" id="query2" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
					<button type="button" id="reset2" loxiaType="button"><s:text name="button.reset"></s:text> </button>
					<br /><br />
					<table id="tbl-inventory-query2"></table>
					<div id="pager2"></div>
				</div>
		   </div>
		    <div id="divRtnDeital" class="hidden">
				<table width="600px">
					<tr>
						<td class="label" width="160px"><s:text name="label.warehouse.msg.stacode"/></td>
						<td id="d_staCode" width="120px"></td>
						<td class="label" width="120px"><s:text name="label.warehouse.msg.slipcode"/></td>
						<td id="d_slipCode"></td>
					</tr>
					<tr>
						<td class="label" width="160px"><s:text name="label.warehouse.pl.createtime"/></td>
						<td id="d_createTime"></td>
						<td colspan="2"></td>
					</tr>
				</table>
			    <br />
			    <table id="tbl-line-detial"></table>
				    <div class="buttonlist">
						<button id="back" loxiaType="button"><s:text name="button.back"></s:text> </button>
			        </div>
			</div>
	  </div>
	</div>
</div>
</body>
</html>