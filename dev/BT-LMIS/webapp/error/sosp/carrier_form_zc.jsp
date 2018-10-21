<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<form class="container-fluid">
	<div style="margin-left: 20px; margin-bottom: 20px;">
		<table>
			<tr>
				<td width="10%">成本中心:</td>
				<td width="20%"><input id="cost_center" name="cost_center"
					type="text" /></td>
				<td width="10%">店铺名称:</td>
				<td width="20%"><input id="store_name" name="store_name"
					type="text" /></td>
				<td width="10%">仓库名称:</td>
				<td width="20%"><input id="warehouse" name="warehouse"
					type="text" /></td>
			</tr>
			<tr>
				<td width="10%">承运商:</td>
				<td width="20%"><input id="transport_name"
					name="transport_name" type="text" /></td>
				<td width="10%">产品类型:</td>
				<td width="20%"><input id="itemtype_name" name="itemtype_name"
					type="text" /></td>
				<td width="10%">运单号:</td>
				<td width="20%"><input id="express_number"
					name="express_number" type="text" /></td>
			</tr>
			<tr>
				<td width="10%">前置单据:</td>
				<td width="20%"><input id="epistatic_order"
					name="epistatic_order" type="text" /></td>
				<td width="10%">始发地:</td>
				<td width="20%"><input id="delivery_address"
					name="delivery_address" type="text" /></td>
				<td width="10%">省:</td>
				<td width="20%"><input id="province" name="province"
					type="text" /></td>
			</tr>
			<tr>
				<td width="10%">市:</td>
				<td width="20%"><input id="city" name="city" type="text" /></td>
				<td width="10%">区:</td>
				<td width="20%"><input id="state" name="state" type="text" /></td>
			</tr>
			<tr>
				<td width="10%">开始时间:</td>
		  		<td width="20%">
		  			<input type="text" id="s_time" name="s_time"  onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" 
		  			/>
		  		</td>
		  		<td width="10%">结束时间:</td>
	  			<td width="20%">
					<input type="text" id="e_time" name="e_time"  onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" 
					/>
	  			</td>
			</tr>
		</table>
		<label style="color:red">所有导出的数据量大小限制在65000条，超出会异常，请将超出的数据按照查询条件分段导出</label>
	</div>
</form>
<div class="tabbable" style="text-align: center">
	<ul id="ccf_sheet"
		class="nav-tabs padding-8 tab-color-blue"
		id="myTab4">
		<li id="carrierChargeZC1"><a data-toggle="tab"
			onclick="query_zc_settlemented_tab()">已结算数据</a></li>
		<li id="carrierChargeZC2"><a data-toggle="tab"
			onclick="query_zc_unsettlement_tab()">未结算数据</a></li>
		<li id="carrierChargeZC3"><a data-toggle="tab"
			onclick="query_zc_error_tab()">异常数据</a></li>
	</ul>
	<div id="div_carrier_zc_settlemented" style="display: none">
		<jsp:include page="/error/sosp/carrier_zc_settlemented.jsp"
			flush="true" />
	</div>
	<div id="div_carrier_zc_unsettlement" style="display: none">
		<jsp:include page="/error/sosp/carrier_zc_unsettlement.jsp"
			flush="true" />
	</div>
	<div id="div_carrier_zc_error" style="display: none">
		<jsp:include page="/error/sosp/carrier_zc_error.jsp" flush="true" />
		<%-- <%@ include file="/error/sosp/carrier_zc_error.jsp"%> --%>
	</div>
</div>
