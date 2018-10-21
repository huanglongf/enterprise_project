<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/outbound_feedback_pacs_operate.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
		<form id="form_query" method="post">
			<table>
				<tr>
					<td class="label">出库作业单号</td>
					<td>
						<input loxiaType="input" trim="true" name="wmsIntransitNoticeOmsCommand.staCode" id="staCode" maxlength="80"/>
					</td>
					<td class="label">店铺</td>
					<!-- <td>
						<input loxiaType="input" trim="true" name="msgToWcsCommand.containerCode" id="owner" maxlength="80"/>
					</td> -->
					<td width="35%">
						<table>
							<tr>
								<td>
									<div style="float: left;">
										<select id="companyshop" name="wmsIntransitNoticeOmsCommand.owner" loxiaType="select" style="width: 236px; height: 24px;">
											<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
										</select>
									</div>						
									<div style="float: left; padding-left: 8px;">
										<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
									</div>
								</td>
							</tr>
						</table>
					</td>
					
					<td class="label">错误次数</td>
					<td style="width: 50px;"><input loxiaType="input" style="width: 50px" name="wmsIntransitNoticeOmsCommand.numberUp" id="numberUp" /></td>
					<td class="label" style="width: 5px;">━</td>
					<td><input loxiaType="input" style="width: 50px" name="wmsIntransitNoticeOmsCommand.amountTo" id="amountTo" /></td>
					
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search">查询</button>
			<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
			<!-- <button loxiaType="button" id="reset">重置次数 :&nbsp;0</button>
			<button loxiaType="button" class="confirm" id="resetAll">重置次数 :&nbsp;99</button> -->
		</div>
		<table id="tbl-inbound-purchase"></table>
		<div id="pager"></div>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="resetToZero">重置次数 :&nbsp;0</button>
			<button loxiaType="button" class="confirm" id="resetToHundred">重置次数 :&nbsp;100</button>
		</div>
	</div>
	
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>