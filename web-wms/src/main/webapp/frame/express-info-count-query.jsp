<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/meta.jsp"%>
		<title><s:text name="label.warehouse.pl.dispatch1.query"/></title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/express-info-count-query.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
	</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<!-- 这里是页面内容区 -->
	<s:text id="pselect" name="label.please.select"/>
		<div id="showList">
			 <form id="query-form" name="query-form">
				<table>
					<tr>
						<td>
							<select name="rdcmd.lpcode" id="selLpcode" loxiaType="select">
								<option value="">--请选择物流商--</option>
							</select>
					    </td>
						<td width="30%">
							<select name="rdcmd.owner" id="owner" loxiaType="select">
								<option value="">--店铺--</option>
							</select>
					    </td>
						<td width="30%">
							<select name="rdcmd.physicalWarehouseId" id="warehouseName" loxiaType="select">
								<option value="">--发货仓--</option>
							</select>
					    </td>
					</tr>
					<tr>
						<td width="30%">
							<select name="rdcmd.destinationProvince" id="destinationProvince" loxiaType="select">
								<option value="">--省--</option>
							</select>
					    </td>
					</tr>
				</table>
			</form>
			<div class="buttonlist">
				<button id="search" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
				<button id="reset" type="reset" loxiaType="button"><s:text name="button.reset"/></button>
				<font class="label" style="color:red">　　默认查询近30天的数据！</font>
			</div>
		<div>
		<table id="tbl-dispatch-total"></table>
			<table id="tbl-dispatch-list"></table>
			<div id="pager_query"></div>
		</div>
 </div>
 		
	<!--省市明细列表  -->
	<div id="express_info" class="hidden">
				<table>
					<tr>
						<td colspan="2" style="font-weight:bold ">目的地（省）：<span id="province"></span></td>
					</tr>
					<tr >
						<td height="20"></td>
					</tr>
				</table>
				<div id="radarList">
				<table id="tbl-showDetail-total"></table>
					<table id="tbl-showDetail"></table>
					<div id="pagerDetail"></div>
				</div>
			<div class="buttonlist">
				<button loxiaType="button" class="confirm" id="back"><s:text name="返回"></s:text></button>
			</div>
	</div>
<!-- 物流商统计明细列表  待揽收-->
	<div id="tran_info" class="hidden">
				<table>
					<tr>
						<td colspan="2" style="font-weight:bold ">目的地（省）：<span id="StayProvince"></span></td>
						<td colspan="3">快递状态：<span id="expressStatus"></span></td>
					</tr>
				</table>
				<div id="stayLanshou">
					<table id="tbl-tran-Detail"></table>
					<div id="pagerTran"></div>
				</div>
			<div class="buttonlist">
				<button loxiaType="button" class="confirm" id="stayBack"><s:text name="返回"></s:text></button>
			</div>
	</div>
	<!-- 物流商统计明细列表 已揽收-->
	<div id="hasLanshou_info" class="hidden">
				<table>
					<tr>
						<td colspan="2" style="font-weight:bold ">目的地（省）：<span id="hasLanshouProvince"></span></td>
						<td colspan="3">快递状态：<span id="hasLanshouStatus"></span></td>
					</tr>
				</table>
				<div>
					<table id="tbl-hasLanshou-Detail"></table>
					<div id="pagerHasLanshou"></div>
				</div>
			<div class="buttonlist">
				<button loxiaType="button" class="confirm" id="hasLanshouBack"><s:text name="返回"></s:text></button>
			</div>
	</div>
	
 <!-- 物流商统计明细列表 中转中 -->
	<div id="inTheTransif_info" class="hidden">
				<table>
					<tr>
						<td colspan="2" style="font-weight:bold ">目的地（省）：<span id="inTheTransifProvince"></span></td>
						<td colspan="3">快递状态：<span id="inTheTransifStatus"></span></td>
					</tr>
				</table>
				<div>
					<table id="tbl-inTheTransif-Detail"></table>
					<div id="pagerInTheTransif"></div>
				</div>
			<div class="buttonlist">
				<button loxiaType="button" class="confirm" id="inTheTransifBack"><s:text name="返回"></s:text></button>
			</div>
	</div>
	
 <!-- 物流商统计明细列表 异常件-->
	<div id="exception_info" class="hidden">
				<table>
					<tr>
						<td colspan="2" style="font-weight:bold ">目的地（省）：<span id="exceptionProvince"></span></td>
						<td colspan="3">快递状态：<span id="exceptionStatus"></span></td>
					</tr>
				</table>
				<div>
					<table id="tbl-exception-Detail"></table>
					<div id="pagerException"></div>
				</div>
			<div class="buttonlist">
				<button loxiaType="button" class="confirm" id="exceptionBack"><s:text name="返回"></s:text></button>
			</div>
	</div>
 
 	<!-- 异常件详细列表 -->
	<div id="exception_dtail" class="hidden">
				<table width="90%">
					<tr>
						<td colspan="2">目的地（省）：<span id="excProvince"></td>
						<td colspan="3">快递状态：<span id="excExpressStatus"></td>
					</tr>
					<tr>
						<td colspan="2">物流服务商：<span id="excLpcode"></td>
						<td colspan="3">发货仓库：<span id="excWarehouse"></td>
					</tr>
				</table>
				<div>
					<table id="tbl-exp-Detail"></table>
					<div id="pagerExp"></div>
				</div>
			<div class="buttonlist">
				<button loxiaType="button" class="confirm" id="backexe"><s:text name="返回"></s:text></button>
			</div>
	</div>
	</body>
</html>