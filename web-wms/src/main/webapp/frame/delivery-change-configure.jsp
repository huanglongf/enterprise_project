<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/delivery-change-configure.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.location.transtype"/></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div>
	<br />
		<p id="expressNo" class="hidden"><span class="label"><s:text name="label.warehouse.pl.sta.delivery.code"></s:text>：</span><span id="expressNos"></span></p>
		<form name="searchForm" id="searchForm">
		<table>
			<tr>
				<td class="label"><b><s:text name="原始物流商"></s:text></b></td>
				<td>
					<select  loxiaType="select" id="lpcode" name="deliveryChangeConfigure.lpcode">
								<option value="" selected="selected">请选择</option>
								<option value="YTO" > 圆通快递</option>
								<option value="STO" >申通快递</option>
								<option value="EMS" >EMS</option>
								<option value="SF" >顺丰快递</option>
								<option value="SFDSTH">顺丰电商特惠</option>
							</select>
				</td>
				<td class="label"><b><s:text name="目标物流商"></s:text></b></td>
				<td>
					<select  loxiaType="select" id="newLpcode" name="deliveryChangeConfigure.newLpcode">
								<option value="" selected="selected">请选择</option>
								<option value="YTO" > 圆通快递</option>
								<option value="STO" >申通快递</option>
								<option value="EMS" >EMS</option>
								<option value="SF" >顺丰快递</option>
								<option value="SFDSTH">顺丰电商特惠</option>
							</select>
				</td>
			</tr>
			<tr>
			<td colspan="4" ><hr style="height:1px;border:none;border-top:1px solid #555555;" /></td>
			</tr>
			<tr>
			</tr>
		</table>
		</form>
		<button  loxiaType="button" class="confirm" id="search"><s:text name="查询"></s:text> </button>
		<button  loxiaType="button" id="reset"><s:text name="重设"></s:text> </button>
		</br>
		</br>
		<table id="tbl-deliver-change"></table>
		</br>
		<button   loxiaType="button" class="confirm" id="delete"><s:text name="删除"></s:text> </button>
	</div>
	</br>
	<div>
	<form name="insertForm" id="insertForm">
		<table>
			<tr>
				<td class="label"><b><s:text name="原始物流商"></s:text></b></td>
				<td>
					<select  loxiaType="select" id="lpcode" name="deliveryChangeConfigure.lpcode">
								<option value="" selected="selected">请选择</option>
								<option value="YTO" > 圆通快递</option>
								<option value="STO" >申通快递</option>
								<option value="EMS" >EMS</option>
								<option value="SF" >顺丰快递</option>
								<option value="SFDSTH">顺丰电商特惠</option>
							</select>
				</td>
				<td class="label"><b><s:text name="目标物流商"></s:text></b></td>
				<td>
					<select  loxiaType="select" id="newLpcode" name="deliveryChangeConfigure.newLpcode">
								<option value="" selected="selected">请选择</option>
								<option value="YTO" > 圆通快递</option>
								<option value="STO" >申通快递</option>
								<option value="EMS" >EMS</option>
								<option value="SF" >顺丰快递</option>
								<option value="SFDSTH">顺丰电商特惠</option>
					</select>
				</td>
				<td>
				</td>
			</tr>
		</table>
		</form>
		</br>
		<button   loxiaType="button" class="confirm" id="insert"><s:text name="新增"></s:text> </button>
	</div>
</body>
</html>