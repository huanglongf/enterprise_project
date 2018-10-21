<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/expressRadar/express-collect-info.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
.showborder {
	border: thin;
}
.divFloat{
	float: left;
	margin-right: 10px;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	<div id="div-sta-list">
		<form id="form_query">
			<table>
				<tr>
					<td class="label" width="20%">物流服务商:</td>
					<td>
						<select name="lpcode" id="selLpcode" loxiaType="select">
							<option value="">请选择</option>
							<option value="STO">申通</option>
							<option value="SF">顺丰</option>
							<option value="EMS">EMS</option>
						</select>
					</td>
					<td class="label" width="20%">店铺:</td>
					<td>
						<select name="code" id="code" loxiaType="select">
							<option value="">请选择</option>
							<option value="STO">001</option>
							<option value="SF">110</option>
							<option value="EMS">123</option>
						</select>
					</td>
					
				</tr>
				<tr>
					<td class="label" width="20%">发件仓库:</td>
					<td><select name="code" id="code" loxiaType="select">
							<option value="">请选择</option>
							<option value="STO">001</option>
							<option value="SF">110</option>
							<option value="EMS">123</option>
						</select>
					</td>
					<td class="label" width="20%">省:</td>
					<td><select name="code" id="code" loxiaType="select">
							<option value="">请选择</option>
							<option value="STO">001</option>
							<option value="SF">110</option>
							<option value="EMS">123</option>
						</select>
					</td>
				</tr>
				
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search">查询</button>
			<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
		</div>
		<table id="tbl-inbound-purchase"></table>
		<div id="pager"></div>
		<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
	</div>
	
	
	<div id="div-city-detail" class="hidden">
		<div>
		<table>
				<tr>
					<td class="label" width="20%">目的地（省）:</td>
					<td>
						江苏
					</td>
				</tr>
				
		</table>
		</div>
		<div >
			<table id="tb2-city-detail"></table>
		</div>
		<div class="buttonlist">
			<table>
				<tr>
					<td><button loxiaType="button" id="delete">返回</button></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="div-logistics-detail" class="hidden">
		<div>
		<table>
				<tr>
					<td class="label" width="20%">目的地（省）:</td>
					<td>
						江苏
					</td>
					<td class="label" width="20%">快递状态:</td>
					<td>
						异常件
					</td>
				</tr>
				
		</table>
		</div>
		<div >
			<table id="tb3-logistics-detail"></table>
		</div>
		<div class="buttonlist">
			<table>
				<tr>
					<td><button loxiaType="button" id="delete">返回</button></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="div-exception-detail" class="hidden">
		<div>
		<table>
				<tr>
					<td class="label" width="20%">目的地（省）:</td>
					<td>
						江苏
					</td>
					<td class="label" width="20%">快递状态:</td>
					<td>
						异常件
					</td>
				</tr>
				<tr>
					<td class="label" width="20%">物流服务商:</td>
					<td>
						顺丰快递
					</td>
					<td class="label" width="20%">发件仓库:</td>
					<td>
						吴江仓
					</td>
				</tr>
				
		</table>
		</div>
		<div >
			<table id="tb4-exception-detail"></table>
		</div>
		<div class="buttonlist">
			<table>
				<tr>
					<td><button loxiaType="button" id="delete">返回</button></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>