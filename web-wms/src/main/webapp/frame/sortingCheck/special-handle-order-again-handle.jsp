<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/special-handle-order-again-handle.js' includeParams='none' encode='false'/>"></script>
<title><s:text name="title.warehouse.sta.query"></s:text></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="query-order-list">
		<form id="queryForm" name="queryForm">
			<table>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text></td>
					<td width="15%"><input loxiaType="date" id="createTimeId" name="createTime" showTime="true"></input></td>
					<td width="10%" class="label" style="text-align: right;"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="15%"><input loxiaType="date" id="endCreateTimeId" name="endCreateTime" showTime="true"/></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.code" /></td>
				</tr>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.refSlipCode"/></td>
					<td width="10%" class="label">相关单据号1</td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.slipCode1"/></td>
					<td width="10%" class="label">相关单据号2</td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.slipCode2"/></td>
				</tr>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.sta.shop"></s:text> </td>
					<td width="19%">
						<div style="float: left">
							<select id="companyshop" name="sta.owner" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</div>						
						<div style="float: left">
							<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
						</div>
					</td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.status"></s:text></td>
					<td width="15%">
						<select loxiaType="select" id="statusList" name="sta.intStaStatus">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="3" ><s:text name="label.warehouse.sta.status.checked"></s:text> </option>
							<option value="4"><s:text name="label.warehouse.sta.status.intransit"></s:text> </option>
							<option value="10"><s:text name="label.warehouse.sta.status.finished"></s:text> </option>
						</select>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text> </button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
		</div>
		<table id="tbl-orderList"></table>
		<div id="pager"></div>
	</div>
	<div id="order-detail" class="hidden">
		<table>
			<tr>
				<td width="100px" class="label"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
				<td width="200px"><span id="createTime"></span></td>
				<td width="100px" class="label"><s:text name="label.warehouse.sta.shop"></s:text> </td>
				<td width="200px"><span id="shopId"></span></td>
			</tr>
			<tr>
				<td width="100px" class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
				<td width="200px"><span id="refSlipCode"></span></td>
				<td width="100px" class="label"><s:text name="label.warehouse.pl.sta.trantype"></s:text> </td>
				<td width="200px"><span id="type"></span></td>
			</tr>
		</table>
		<br />
		<div id="detail_tabs">
				<table id="tbl-order-detail"></table>
				<div id="pagerDetail"></div>
		</div>
		
		 <div id="dialog_staSpecialExecute">
	  	 	<table width="100%" style="height: 100%">
	  	 		<tr style="height: 100%;width: 100%">
	  	 			<td align="center">
	  	 				<button loxiaType="button" class="confirm" id="dialog_coach_print">COACH小票打印</button>
	  	 				<button loxiaType="button" class="confirm" id="dialog_burberry_out_print">Burberry 寄货单 打印</button>
	  	 				<button loxiaType="button" class="confirm" id="dialog_burberry_return_print">Burberry 退货表格 打印</button>
	  	 				<button loxiaType="button" class="confirm" id="dialog_nike_gift_card_print">NIKE 礼品卡 打印</button>
	  	 			</td>
	  	 			<td align="center"><button loxiaType="button" class="confirm" id="dialog_jump1">跳过</button></td>
	  	 			<td align="center"><button loxiaType="button" class="confirm" id="dialog_jump2">跳过</button></td>
	  	 			<td align="center"><button loxiaType="button" class="confirm" id="dialog_jump3">跳过</button></td>
	  	 			<td align="center"><button loxiaType="button" class="confirm" id="dialog_jump8">跳过</button></td>
	  	 		</tr>
	  	 	</table>
		 </div>
		 
		 <div id="dialog_giftType">
	  	 	<table width="100%" style="height: 100%">
	  	 		<tr style="height: 100%;width: 100%;font-size:15px;color:red" id="supplierCodeTr1">
					<td colspan="1" class="label">货号：</td>
					<td colspan="3" width="200px"><span id="supplierCode1"></span></td>
	  	 		</tr>
	  	 		<tr style="height: 100%;width: 100%;font-size:15px;color:blue" id="supplierCodeTr2">
					<td colspan="1" class="label">备注：</td>
					<td colspan="3" width="200px"><span id="dialog_memo"></span></td>
	  	 		</tr>
	  	 		<tr style="height: 100%;width: 100%">
	  	 			<td align="center">
	  	 				<button loxiaType="button" class="confirm" id="dialog_zs_lpk"> 赠送礼品卡(需打印)</button>
	  	 				<button loxiaType="button" class="confirm" id="dialog_sp_ts_bz">商品特殊包装</button>
	  	 				<button loxiaType="button" class="confirm" id="dialog_sp_ts_yz">商品特殊印制</button>
	  	 			</td>
	  	 			<td align="center"><button loxiaType="button" class="confirm" id="dialog_jump4">跳过</button></td>
	  	 			<td align="center"><button loxiaType="button" class="confirm" id="dialog_jump5">跳过</button></td>
	  	 			<td align="center"><button loxiaType="button" class="confirm" id="dialog_jump6">跳过</button></td>
	  	 			<td align="center"><button loxiaType="button" class="confirm" id="dialog_jump7" align="center">跳过</button></td>
	  	 		</tr>
	  	 	</table>
		 </div>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="back"><s:text name="button.back"></s:text></button>
		</div>
	</div>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>