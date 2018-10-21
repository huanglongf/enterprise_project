<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title>快递时效设置</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/expressRadar/express-period.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

</head>
<body style="background-color: #f2f2f2;"
	contextpath="<%=request.getContextPath()%>">
	<!-- 这里是页面内容区 -->

	<div id="div1">
		<form method="post" id="query-form">
			<table id="filterTable">
				<tr>
					<td class="label" width="85">物流商简称：</td>
					<td>
						<select loxiaType="select" id= "selLpcode" name = "rc.lpCode">
						</select>
					</td>
					<td class="label" width="85">物流服务类型：</td>
					<td>
						<select loxiaType="select" style="width: 100px"  id = "wlService" name = "rc.wlService">
								<option value="">--请选择--</option>
								<option value="1">普通</option>
								<option value="4">航空</option>
								<option value="7">电商特惠</option>
						</select>
					</td>
					<td class="label" width="85">快递时效类型：</td>
					<td><select loxiaType="select" style="width: 100px" id = "dateTimeType" name = "rc.dateTimeType">
							<option value="">--请选择--</option>
							<option value="1">普通</option>
							<option value="3">及时达</option>
							<option value="5">当日达</option>
							<option value="6">次日达</option>
					</select></td>
				</tr>
				<tr>
					<td class="label" width="85" style="width: 100px" >发件仓库：</td>
					<td><select loxiaType="select" id = "fjWh" name = "rc.phyid">
							<option value="">--请选择物流商--</option>
					</select></td>
					<td class="label" width="85">省：</td>
					<td><select loxiaType="select" style="width: 100px" id = "province" name = "rc.province">
							<option value="">--请选择--</option>
					</select></td>
					<td class="label" width="85" style="width: 100px" >市：</td>
					<td><select loxiaType="select" id="city" name="rc.city">
						<option value="">--请选择省--</option>
					</select></td>
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
		<div id="radarTimeRuleList">
			<table id="tbl-radarTimeRuleList"></table>
			<div id="pager_query"></div>
		</div>
		<div class="buttonlist">
			<button type="button" id="deleteButton" loxiaType="button" class="confirm">选中删除</button>
		</div>
		<table id="filterTable1" style="margin-top: 10px;">
			<tr>
				<td><br /></td>
			</tr>
			<tr>
				<td class="label" width="85">物流商简称：</td>
				<td>
					<select loxiaType="select" id= "selLpcodeAdd">
					
					</select>	
				</td>
				<td class="label" width="85">物流服务类型：</td>
				<td>
					<select loxiaType="select"  id = "wlServiceAdd">
									<option value="">--请选择--</option>
									<option value="1">普通</option>
									<option value="4">航空</option>
									<option value="7">电商特惠</option>
					</select>
				</td>
				<td class="label" width="85">快递时效类型：</td>
				<td><select loxiaType="select" id = "dateTimeTypeAdd">
							<option value="">--请选择--</option>
							<option value="1">普通</option>
							<option value="3">及时达</option>
							<option value="5">当日达</option>
							<option value="6">次日达</option>
				</select></td>
			</tr>
			<tr>
				<td class="label" width="85">发件仓库：</td>
				<td><select loxiaType="select" id = "fjWhAdd">
							<option value="">--请选择物流商--</option>
				</select></td>
				<td class="label" width="85">省：</td>
				<td><select loxiaType="select" id = "provinceAdd"'>
						<option value="">--请选择--</option>
				</select></td>
				<td class="label" width="85">市：</td>
				<td><select loxiaType="select" id = "cityAdd">
						<option value="">--请选择省--</option>
				</select></td>
			</tr>
			<tr>
				<td class="label" width="100">标准时效（天）：</td>
				<td width="20%"><input loxiaType="input" id="dateTime"
					name="jubNumber1" trim="true" /></td>
				<td class="label" width="85">截止揽件时间：</td>
				<td colspan="3">
					<select loxiaType="select"
						style="width: 100px" id = "hour">
					</select>  ： 
					<select loxiaType="select" 
						style="width: 100px" id = "minute">
					</select>
				</td>
			</tr>
			<tr>
				<td><br /></td>
			</tr>
		</table>
		<div class="buttonlist">
			<button type="button" id="save" loxiaType="button" class="confirm">创建</button>
		</div>
	</div>
	<div id="show_rr_dialog" style="text-align: center;">
		<table id="filterTable2" style="margin-top: 10px;">
			<tr>
				<td><br /></td>
			</tr>
			<tr>
				<td class="label" width="85">物流商简称：</td>
				<td>
					<select loxiaType="select" id= "selLpcodeUpdate">
					
					</select>	
				</td>
				<td class="label" width="85">物流服务类型：</td>
				<td>
					<select loxiaType="select"  id = "wlServiceUpdate">
									<option value="">--请选择--</option>
									<option value="1">普通</option>
									<option value="4">航空</option>
									<option value="7">电商特惠</option>
					</select>
				</td>
				<td class="label" width="85">快递时效类型：</td>
				<td><select loxiaType="select" id = "dateTimeTypeUpdate">
							<option value="">--请选择--</option>
							<option value="1">普通</option>
							<option value="3">及时达</option>
							<option value="5">当日达</option>
							<option value="6">次日达</option>
				</select></td>
			</tr>
			<tr>
				<td class="label" width="85">发件仓库：</td>
				<td><select loxiaType="select" id = "fjWhUpdate">
							<option value="">--请选择物流商--</option>
				</select></td>
				<td class="label" width="85">省：</td>
				<td><select loxiaType="select" id = "provinceUpdate"'>
						<option value="">--请选择--</option>
				</select></td>
				<td class="label" width="85">市：</td>
				<td><select loxiaType="select" id = "cityUpdate">
						<option value="">--请选择省--</option>
				</select></td>
			</tr>
			<tr>
				<td class="label" width="100">标准时效（天）：</td>
				<td width="18%"><input loxiaType="input" id="dateTimeUpdate"
					name="jubNumber1" trim="true" /></td>
			<td class="label" width="85">截止揽件时间：</td>
				<td colspan="2">
					<select loxiaType="select"
						style="width: 130px" id = "hourUpdate">
					</select>  ： 
					<select loxiaType="select" 
						style="width: 130px" id = "minuteUpdate">
					</select>
				</td>
			</tr>
			<tr>

			</tr>
		</table>
		<input type="text" id = "rrid" class="hidden">
		<div class="buttonlist">
			<button type="button" id="update" loxiaType="button" class="confirm">修改</button>
			<button type="button" loxiaType="button" onclick="deleteR();">删除</button>
			<button type="button" loxiaType="button" onclick="closeDialog();">返回</button>
		</div>
	</div>
</body>
</html>