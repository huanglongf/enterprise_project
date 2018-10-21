
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%@include file="/common/meta.jsp"%>
		<title>仓库渠道组信息维护</title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/channel-group-maintain.js' includeParams='none' encode='false'/>"></script>
		<style type="text/css">
			.tips{
				width:100%; height: 50%; text-align:center; color:red; font-size: 40px; font-weight: bold;
			}
		</style>
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="dialog_channelGroup">
		<div id="channel-group-list">
			<table id="tbl-channel-group-list"></table>
		</div>
		<div class="buttonlist">
			<button id="add" type="button" loxiaType="button"
				class="confirm">新增</button>
		</div>
	</div>
	<div id="dialog_addGroup" style="display:none;">
		<table>
			<tr>
				<td class="label">编码:</td>
				<td><input loxiaType="input" id="code" name="code" width="120px"/></td>
			</tr>
			<tr>
				<td class="label">名称:</td>
				<td><input loxiaType="input" id="name" name="name" width="120px"/></td>
			</tr>
			<tr>
				<td class="label">优先级:</td>
				<td><input loxiaType="input" id="sort" name="sort" width="120px"/>
				</td>
			</tr>
			<tr>
				<td><button id="saveGroup" type="button" loxiaType="button" class="confirm">创建</button></td>
				<td><button id="addGroupBack" type="button" loxiaType="button">取消</button></td>
			</tr>
		</table>
	</div>
	<div id="dialog_groupDetail" style="display:none;">
		<table>
			<tr>
				<td class="label">编码:</td>
				<td><input type="hidden" id="groupId" name="group.id"
					width="120px" /> <input loxiaType="input" id="groupCode"
					name="group.code" width="120px" /></td>
				<td class="label">名称:</td>
				<td><input type="hidden" id="oldGroupName" name="oldGroupName"
					width="120px" />
					<input loxiaType="input" id="groupName" name="group.name"
					width="120px" />
				</td>
			</tr>
			<tr>
				<td class="label">优先级:</td>
				<td><input loxiaType="input" id="groupSort" name="group.sort"
					width="120px" />
				</td>
			</tr>
		</table>
		<div id="channel-ref-list">
			<table id="tbl-channel-ref-list"></table>
			<div id="pager"></div>
		</div>
		<div class="buttonlist">
			<button id="save" type="button" loxiaType="button"
				class="confirm">保存</button>
			<button id="back" type="button" loxiaType="button">返回</button>
		</div>
	</div>
</body>
</html>