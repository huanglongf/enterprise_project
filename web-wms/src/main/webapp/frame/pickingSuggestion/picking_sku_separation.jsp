
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/meta.jsp"%>
		<title><s:text name="title.warehouse.pl.verify"/></title>
		<script type="text/javascript" src="<s:url value='/frame/pickingSuggestion/picking_sku_separation.js"' includeParams='none' encode='false'/>"></script>
		<style type="text/css">
			.tips{
				width:100%; height: 50%; text-align:center; color:red; font-size: 40px; font-weight: bold;
			}
		</style>
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<div id="picking-list">
				<table width="80%" id="queryTable">
					<tr>
						<td class="label" width="10%">批次号：</td>
						<td width="20%"><input loxiaType="input" name="cmd.code" id="pickinglistCode" trim="true"/></td>
						<td class="label" width="10%">创建时间 从：</td>
						<td width="20%"><input loxiaType="date" name="cmd.createTime1" id="createTimeStart" showTime="true"/></td>
						<td class="label" width="10%">至：</td>
						<td width="20%"><input loxiaType="date" name="cmd.executedTime1" id="createTimeEnd" showTime="true"/></td>
					</tr>
				</table>
				<div class="buttonlist">
					<button id="search" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
					<button id="reset" type="reset" loxiaType="button"><s:text name="button.reset"/></button>
				</div>
				
				<span class="label"><s:text name="label.warehouse.pl.verify"/></span>输入配货批后按回车进入核对界面
				<br/>
				<span class="label" style="margin-left: 50px;">配货批</span>
				<input loxiaType="input" name="cmd.code" id="pickinglistCodeQuery" trim="true" style="width: 150px;"/>
				<table id="tbl-dispatch-list"></table>
				<div id="pager"></div>
		</div>
		<input type="hidden" id="plid" />
		<input type="hidden" id="staPgindex" />
		
		<div id="detail" class="hidden">
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
					<td><input id="barCode" loxiaType="input" style="width:240px; height: 35px; line-height: 35px; font-size: 24px;" trim="true" /></td>
					<td></td><td></td>
				</tr>
			</table>
			
			<table id="tbl-detail-list"></table>
			<div class="buttonlist">
				<button id="back" type="button" loxiaType="button" >返回</button>
			</div>
		</div>
		<div id="pldill" class="hidden">
			<table width="60%">
				<tr>
					<td width="20%" class="label">批次号：</td>
					<td width="30%" id="plCode1"></td>
					<td width="20%" class="label">创建时间：</td>
					<td width="30%" id="plCreateTime1"></td>
				</tr>
				<tr>
					<td class="label">创建人：</td>
					<td id="plCreateUser1"></td>
					<td class="label">计划单据数：</td>
					<td id="plBillCount1"></td>
				</tr>
			</table>
			<table id="tbl-pldill-list"></table>
			<div class="buttonlist">
			<table>
				<tr>
					<td>
						<button id="pldillok" type="button" loxiaType="button" class="confirm">确认完成</button>
						<button id="pldBack" type="button" loxiaType="button" >返回</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td>
						OK条码：<input id="okText" loxiaType="input" style="width:240px; height: 35px; line-height: 35px; font-size: 24px;" trim="true"/>
					</td>
				</tr>
			</table>
			</div>
		</div>
		<div id="pldillAll" class="hidden">
		<table width="60%">
				<tr>
					<td width="20%" class="label">批次号：</td>
					<td width="30%" id="plCode2"></td>
					<td width="20%" class="label">创建时间：</td>
					<td width="30%" id="plCreateTime2"></td>
				</tr>
				<tr>
					<td class="label">创建人：</td>
					<td id="plCreateUser2"></td>
					<td class="label">计划单据数：</td>
					<td id="plBillCount2"></td>
				</tr>
			</table><br/>
			<table id="tbl-pldill-sep"></table><br/>
			<table id="tbl-pldill-ds"></table>
			<div class="buttonlist">
				<button id="pldillAllok" type="button" loxiaType="button" class="confirm">确认完成</button>
				<button id="rehandle" type="button" loxiaType="button" class="confirm">重新操作</button>
				<!--  <button id="pldAllBack" type="button" loxiaType="button" >返回</button>-->
			</div>
		</div>
		<div id="pl_check_dialog" style="text-align: center;">
		    <div id="showinfo" class="tips">
		    </div>
		    <div id="inputBarCode" class="label" style="text-align: center;">SKU条码：<input id="barCode0" loxiaType="input" style="width:240px; height: 35px; line-height: 35px; font-size: 24px;" trim="true"/></div><br/><br/>
		    <button id="checkok" type="button" loxiaType="button" class="confirm">确认完成</button>
		    <button loxiaType="button" class="confirm hidden" id="doCheck0">确认</button>
		    <input id="checkValue" loxiaType="input" style="width:100px; height: 35px; line-height: 35px; font-size: 24px;" trim="true" class="hidden"/>
		</div>
	</body>
</html>