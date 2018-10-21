
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/meta.jsp"%>
		<title>渠道信息管理</title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/group-bichannelwhref.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
		<style type="text/css">
			.tips{
				width:100%; height: 50%; text-align:center; color:red; font-size: 40px; font-weight: bold;
			}
		</style>
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<form id="channelForm" name="channelForm">
		<div id="bichannel-list">
				<table width="80%" id="queryTable">
					<tr>
						<td class="label" width="10%">渠道编码：</td>
						<td width="20%"><input loxiaType="input" name = "biChannel.code"  id="channelCode" trim="true"/></td>
						<td class="label" width="10%">渠道名称：</td>
						<td width="20%"><input loxiaType="input" name = "biChannel.name" id="channelName" trim="true"/></td>
						<td class="label" width="10%">客户：</td>
						<td width="20%">
							<select loxiaType="select" name="biChannel.customer.id" id="customerId">
								<option value="">--请选择客户--</option>
							</select>
						</td>
					</tr>
				</table>
				<div class="buttonlist">
					<button id="search" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
					<button id="reset" type="reset" loxiaType="button"><s:text name="button.reset"/></button>
				</div>
				<table id="tbl-bichannel-list"></table>
				<div id="pager"></div>
		</div>
		</form>
			<div id="channel_wh_refList" class="hidden">
					<table width="80%">
							<tr>
								<td width="10%" class="label">渠道编码：</td>
								<td colspan="2"><span id="cCode"></span></td>
								<td width="20%"><input type="text" name="cid" id="cid" class="hidden"/></td>
							</tr>
							<tr>
								<td width="10%" class="label">渠道名称：</td>
								<td colspan="2"><span id="cName"></span></td>
							</tr>
				</table>
				<br/>
				<table id="tbl-bichannelwh-list"></table>
				<div class="buttonlist">
					<button id="save" type="button" loxiaType="button" class="confirm" >保存</button>
					<button id="back" type="button" loxiaType="button">返回</button>
				</div>
			</div>

	</body>
</html>