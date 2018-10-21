<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/meta.jsp"%>
<title>二次分拣-核对</title>
<script type="text/javascript"
	src="<s:url value='/frame/pickingSuggestion/picking_sku_suggestion_check.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;"
	contextpath="<%=request.getContextPath()%>">
	<!-- 这里是页面内容区 -->
	<div id="picking-list">
		<table width="80%" id="queryTable">
			<tr>
				<td class="label" width="10%">批次号：</td>
				<td width="20%"><input loxiaType="input" name="cmd.code"
					id="pickinglistCode" trim="true" /></td>
				<td class="label" width="10%">创建时间 从：</td>
				<td width="20%"><input loxiaType="date" name="cmd.createTime1"
					id="createTimeStart" showTime="true" /></td>
				<td class="label" width="10%">至：</td>
				<td width="20%"><input loxiaType="date"
					name="cmd.executedTime1" id="createTimeEnd" showTime="true" /></td>
			</tr>
		</table>
		<div class="buttonlist">
			<button id="search" type="button" loxiaType="button" class="confirm">
				<s:text name="button.query" />
			</button>
			<button id="reset" type="reset" loxiaType="button">
				<s:text name="button.reset" />
			</button>
		</div>

		<span class="label"><s:text name="label.warehouse.pl.verify" /></span>输入配货批后按回车进入核对界面
		<br /> <span class="label" style="margin-left: 50px;">配货批</span> <input
			loxiaType="input" name="cmd.code" id="pickinglistCodeQuery"
			trim="true" style="width: 150px;" />
		<table width="80%">
			<tr>
				<td class="label" width="10%">是否后置打印装箱单：</td>
				<td width="20%"><input type="checkbox" id="boxPaper" /></td>
				<td class="label" width="10%">是否打印热敏纸装箱单：</td>
				<td width="20%"><input type="checkbox" id="thermalPaper" /></td>
				<td class="label" width="10%">打印机：</td>
				<td width="20%"><select id="printName" loxiaType="select"
					name="printName">
				</select></td>
			</tr>
		</table>
		<table id="tbl-dispatch-list"></table>
		<div id="pager"></div>
	</div>
	<input type="hidden" id="plid" />
	<input type="hidden" id="staPgindex" />
	<div id="div2" class="hidden">

		<table width="80%">
			<tr>
				<td width="20%" class="label">批次号：</td>
				<td width="30%" id="plCode"></td>
				<td width="20%" class="label">创建时间：</td>
				<td width="30%" id="plCreateTime"></td>
			</tr>
			<tr>
				<td class="label">创建人：</td>
				<td id="plCreateUser"></td>
				<td class="label">计划单据数：</td>
				<td id="plBillCount"></td>
			</tr>
			<tr>
				<td class="label">SKU条码：</td>
				<td><input id="barCode" loxiaType="input"
					style="width: 240px; height: 35px; line-height: 35px; font-size: 24px;"
					trim="true" /></td>
				<td></td>
				<td></td>
			</tr>
		</table>
		<div style="float:left;width:880px;padding-right:20px;border-right:1px #9bb3cd">
			<table id="tbl-detail-list"></table>
			<table id="tbl-detail-check-list"></table>
			<div id="btnlist" class="buttonlist">
				<button loxiaType="button" class="confirm" id="back">
					<s:text name="button.back"></s:text>
				</button>
			</div>
		</div>
		<div style="float:right;width:200px">
			   <table cellspacing="0"  border="1" id="tbl-detail-ruleCode" style="border-bottom:solid ;border:1px;font-size: 20px; ">			   </table>
		</div>
		
	</div>
	<div id="pl_check_dialog" style="text-align: center;">
		    <div id="showinfo" class="tips" style="font-size:30px"></div>
		    <div id="showinfoOver" class="tips" style="font-size:30px"></div>
		    <div id="inputBarCode" class="label" style="text-align: center;">SKU条码：<input id="barCode0" loxiaType="input" style="width:240px; height: 35px; line-height: 35px; font-size: 24px;" trim="true"/></div>
		    <button loxiaType="button" class="confirm hidden" id="doCheck0">确认</button>
		    <input id="checkValue" loxiaType="input" style="width:100px; height: 35px; line-height: 35px; font-size: 24px;" trim="true" class="hidden"/>
		</div>
</body>
</html>