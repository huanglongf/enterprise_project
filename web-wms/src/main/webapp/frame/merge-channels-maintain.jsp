<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.outbound.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/merge-channels-maintain.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<table id="tbl-channel"></table>
	<div id="pager1"></div>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="addHbqd">创建合并渠道</button>
		<button type="button" loxiaType="button" class="confirm" id="updateHbqd">编辑合并渠道</button>
	</div>
	<div id="dialog_addHbqd">
			<div id="one" height="50%" style="display:none">
				<table  id="oneTable">
					<tr>
						<td align="left" width="75px"><b>选择仓库:</b></td>
						<td width="180px"><select id="selTransOpc" name="whOuId" loxiaType="select" loxiaType="select">
							<option value="">--请选择仓库--</option>
						</select></td>
					</tr>
				</table>
			</div>
			<div id="two" height="50%" style="display:none">
				<table  id="twoTable">
					<tr>
						<td align="left" width="75px"><b>仓库:</b></td>
						<td width="180px"><label id="whNameLabel"></label></td>
					</tr>
					<tr>
						<td align="left" width="75px"><b>选择渠道:</b></td>
						<td width="180px">
							<input type="radio" name="xzqd" value="single"  checked="checked">单 
							<input type="radio" name="xzqd" value="more"  >多
						</td>
					</tr>
				</table>
			</div>   
			<div id="three" height="50%" style="display:none">
				<div style="float:top;">
					<form id="channelForm" name="channelForm">
						<table  id="queryTable">
							<tr>
								<td align="left" width="295px"><label id="qdLabel" style="font-weight:bold;"></label></td>
								<td width="70px">渠道名称：</td>
								<td width="120px"><input loxiaType="input" name = "biChannel.name" id="channelName" trim="true"/></td>		
								<td>
										<button id="search" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div style="margin-top:1px">
					<div style="float:left; width:200px; height:250px; /* border:2px solid #0000FF; */">
						<table id="threeLeft"></table>
					</div>
					<div style="float:left; margin-left:10px;width:85px; height:250px;">
						<table>
							<tr height="80px">
								<td ></td>
							</tr>
							<tr>
								<td><button type="button" loxiaType="button" class="confirm" id="add">添加</button></td>
							</tr>
							<tr>
								<td><button type="button" loxiaType="button" class="confirm" id="delete">删除</button></td>
							</tr>
						</table>
					</div>
					<div style="float:left; margin-left:5px;width:280px; height:250px;">
						<table id="threeRight"></table>
						<div id="pager2"></div>
					</div>
					<div style="clear:both"></div>
				</div>
			</div>   
			<div id="fourSingle" height="50%" style="display:none">
				<label id = "fourSingleLebel"></label>
				<table  id="fourSingleTable"></table>
				<table>
					<tr>
						<td ><label><b>过期时间：</b></label></td>
						<td ><input loxiaType="date" id="expTime" name="expTime" mandatory="true"  showTime="true" width="180px"></input></td>
					</tr>
				</table>
			</div> 
			<div id="fourMore" height="50%" style="display:none">
				<label id = "fourMoreLebel"></label>
				<table  id="fourMoreTable"></table>
				<form id = "infoForm">
					<table >		
						<tr>
							<td ><label><b>过期时间：</b></label></td>
							<td ><input loxiaType="date" id="expTime2" name="expTime2" mandatory="true"  showTime="true" width="200px"></input></td>
						</tr>
						<tr>
							<td ><label><b>店铺编码：</b></label></td>
							<td ><input loxiaType="input" id="code" name="code" mandatory="true"   width="180px"></input></td>		
							<td ><label><b>店铺名称：</b></label></td>
							<td ><input loxiaType="input" id="name" name="name" mandatory="true"   width="180px"></input></td>
						</tr>
						<tr>
							<td ><label><b>装箱店铺名称：</b></label></td>
							<td ><input loxiaType="input" id="zxShopName" name="zxShopName" width="180px"></input></td>		
							<td ><label><b>运单店铺名称：</b></label></td>
							<td ><input loxiaType="input" id="ydShopName" name="ydShopName" width="180px"></input></td>
						</tr>
					</table>
				</form>
			</div> 
		
			<div class="buttonlist" id="buttonGroup">
				<button type="button" loxiaType="button" class="confirm" id="next" style="width:90px;">下一步</button>
				<button type="button" loxiaType="button"  id="back" style="width:75px;">取消</button>
			</div>		
	</div>
	<div id="dialog_updateHbqd">
			<div id ="updateOne">
					<table>		
						<tr>
							<td ><label>渠道编码：</label></td>
							<td width="215px"><label id="updateCode"></label></td>
							<td ><label>渠道名称：</label></td>
							<td ><label id="updateName"></label></td>
						</tr>
						<tr>
							<td ><label>仓库名称：</label></td>
							<td ><label id="updateWhNameLabel"></label></td>
							<td ><label>选择仓库：</label></td>
							<td width="180px"><select id="updateSelTransOpc" name="upWhOuId" loxiaType="select" loxiaType="select">
							<option value="">--请选择仓库--</option>
						</select></td>	
						</tr>
						<tr>
							<td align="left" width="75px">是否合并发货:</td>
							<td width="180px" id="change">
								<input type="radio" name="xzhbfh" value="yes"  checked="checked">是
								<input type="radio" name="xzhbfh" value="no"  >否
							</td>	
							<td ><label>过期时间：</label></td>
							<td ><input loxiaType="date" id="updateexpTime" name="updateexpTime" showTime="true" width="200px"></input></td>
						</tr>
						<tr >
							<td colspan="3" ><label id = "showMsg" style="color:red"></label></td>
						</tr>
					</table>
			</div>
			<div id ="updateTwo">
				<div style="float:top;">
					<form id="updateChannelForm" name="updateChannelForm">
						<table  id="updateQueryTable">
							<tr>
								<td align="left" width="295px"><label id="updateqdLabel" style="font-weight:bold;"></label></td>
								<td width="70px">渠道名称：</td>
								<td width="120px"><input loxiaType="input" name = "biChannel.name" id="updateChannelName" trim="true"/></td>		
								<td>
										<button id="updateSearch" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div style="float:left; width:200px; height:250px; /* border:2px solid #0000FF; */">
					<table id="updatethreeLeft"></table>
				</div>
				<div style="float:left; margin-left:10px;width:85px; height:250px;">
					<table>
						<tr height="80px">
							<td ></td>
						</tr>
						<tr>
							<td><button type="button" loxiaType="button" class="confirm" id="updateAdd">添加</button></td>
						</tr>
						<tr>
							<td><button type="button" loxiaType="button" class="confirm" id="updateDelete">删除</button></td>
						</tr>
					</table>
				</div>
				<div style="float:left; margin-left:5px;width:280px; height:250px;">
					<table id="updatethreeRight"></table>
					<div id="pager3"></div>
				</div>
				<div style="clear:both"></div>
			</div>
			<div class="buttonlist" id="buttonGroup">
				<button type="button" loxiaType="button" class="confirm" id="save">保存</button>
				<button type="button" loxiaType="button" id="cancel">取消</button>
			</div>		
		</div>
</body>
</html>