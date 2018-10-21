<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title>快递预警信息设置</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/expressRadar/express-warning-reason.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

</head>
<body style="background-color: #f2f2f2;"
	contextpath="<%=request.getContextPath()%>">
	<!-- 这里是页面内容区 -->

	<div id="ooc_rwr_list">
		<form method="post" id="query-form">
			<table id="filterTable">
				<tr>
					<td class="label" width="85">预警类型：</td>
					<td>
						<select loxiaType="select" style="width: 160px"  id= "errorCode" name = "rwr.eid">
							<option value="">--请选择--</option>
						</select>
					</td>
					<td class="label" width="85">预警级别：</td>
					<td>
						<select loxiaType="select" style="width: 160px"  id = "lv" name = "rwr.lvid">
								<option value="">--请选择--</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" id="btn-query" loxiaType="button"
				class="confirm">
				查询
			</button>
			<button type="button" loxiaType="button" id="reset">
				重置
			</button>
		</div>
		<div id="radarWarningReasonList">
			<table id="tbl-radarWarningReasonList"></table>
			<div id="pager_query"></div>
		</div>
		<div class="buttonlist">
			<button type="button" id="deleteButton" loxiaType="button" class="confirm">删除选中</button>
		</div>
		<div>
		<table id="filterTable1" style="margin-top: 10px;">
			<tr>
				<td><br /></td>
			</tr>
				<tr>
					<td class="label" width="85">预警类型：</td>
					<td>
						<select loxiaType="select" style="width: 160px" id= "errorCodeAdd">
							<option value="">--请选择--</option>
						</select>
					</td>
					<td class="label" width="85">初始预警级别：</td>
					<td>
						<select loxiaType="select" style="width: 160px"  id = "lvAdd" >
								<option value="">--请选择--</option>
						</select>
					</td>
					<td class="label" width="85">备注：</td>
					<td>
						<input loxiaType="input" id="memo"   style="width: 200px" />
					</td>
				</tr>
		</table>
		</div>
		<div class="buttonlist">
			<button type="button" id="save" loxiaType="button" class="confirm">创建</button>
		</div>
	</div>
	<div id="ooc_rwr_lineList" class="hidden">
		<table id="filterTable2" >
				<tr>
					<td class="label" width="85">预警类型：</td>
					<td width="100px;">
						<span id="eCode"></span><input type="text"  id="rid" class="hidden"/>
					</td>
					<td class="label" width="85">初始预警级别：</td>
					<td width="60px;">
						<span id="lName"></span>
					</td>
					<td class="label" width="85">备注：</td>
					<td>
						<span id="memor"></span>
					</td>
				</tr>
		</table>
		<br/>
		<div>
			<table id="tbl-rwrline-list"></table>
		</div>
		<div>
		<table id="filterTable3" style="margin-top: 10px;">
			<tr>
				<td><br /></td>
			</tr>
				<tr>
					<td class="label" width="85">预警级别：</td>
					<td>
						<select loxiaType="select" style="width: 160px"  id = "lvlAdd" >
						</select>
					</td>
					<td class="label" width="100">有效时间（h）：</td>
					<td>
						<input loxiaType="input" id="hour"   style="width: 100px" />
					</td>
					<td class="label" width="85">预警说明：</td>
					<td>
						<input loxiaType="input" id="linememo"   style="width: 200px" />
					</td>
				</tr>
		</table>
		</div>
		<div class="buttonlist">
			<button id="saveLine" type="button" loxiaType="button" class="confirm" >保存</button>
			<button id="back" type="button" loxiaType="button">返回</button>
		</div>
	</div>
</body>
</html>