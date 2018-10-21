<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/sta_inbound_turn.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
	<div id="divHead">
		<form id="queryForm" name="queryForm">
			<table width="700px">
				<tr>
					<td class="label" width="15%">店铺</td>
					<td width="35%">
						<table>
							<tr>
								<td>
									<div style="float: left">
										<select id="companyshop" name="sta.owner" loxiaType="select">
											<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
										</select>
									</div>						
									<div style="float: left">
										<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
									</div>
								</td>
							</tr>
						</table>
					</td>
					<td width="15%" class="label">作业单号</td>
					<td width="35%">
						<input type="text" loxiaType="input" trim="true" name="sta.code"></input>
					</td>
				</tr>
				<tr>
					
					<td class="label">相关单据号</td>
					<td>
						<input type="text" loxiaType="input" trim="true" name="sta.refSlipCode"></input>
					</td>
					<td class="label">辅助相关单据号</td>
					<td>
						<input type="text" loxiaType="input" trim="true" name="sta.slipCode1"></input>
					</td>
				</tr>
				<tr>
					<td width="10%" class="label">创建时间</td>
					<td width="15%"><input loxiaType="date" name="createTime" showTime="true"></input></td>
					<td width="10%" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="15%"><input loxiaType="date" name="endCreateTime" showTime="true"/></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search">查询</button>
			<button loxiaType="button" id="reset">重置</button>
		</div>
		<table>
				<tr>
				<td><select class="special-flexselect" id="brandSelect1" name="ouId" data-placeholder="请选择仓库">
						</select></td>
					<td class="label">
						确认转仓导入：
					</td>
					<td>
						<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
							<input type="file" loxiaType="input" id="staFile" name="file" style="width: 200px;"/>
						</form>
					</td>
					<td>
						<button loxiaType="button" class="confirm" id="import">导入</button>
					</td>
					<td>
						  <a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.InboundTurn"></s:text>.xls&inputPath=InboundTurn.xls">模板文件下载</a>
					</td>
				</tr>
			</table>
		<table id="tbl_sta_list"></table>
		<div id="pager"></div>
	</div>
	<div id="divDetial" class="hidden">
		<input type="hidden" id='sta_id'/>
		<table width="700px">
			<tr>
				<td width="15%" class="label">
					作业单号
				</td>
				<td width="35%" id="sta_code"></td>
				<td width="15%" class="label">
					相关单据号
				</td>
				<td width="35%" id="sta_refSlipCode"></td>
			</tr>
			<tr>
				<td class="label" >
					辅助相关单据号
				</td>
				<td id="sta_slipCode1"></td>
				<td class="label">
					创建时间
				</td>
				<td id="sta_createTime"></td>
			</tr>
			<tr>
				<td class="label" >
					作业类型
				</td>
				<td id="sta_type"></td>
				<td class="label">
					作业单状态
				</td>
				<td id="sta_status"></td>
			</tr>
			<tr>
				<td class="label">
					备注
				</td>
				<td colspan="3" id='sta_memo'></td>
			</tr>
		</table>
		<br/>
		<table id="tbl_sta_line_list"></table>
		<div id="pager1"></div>
		<br/>
		<div class="buttonlist">
			<table>
				<tr>
					<td class="label">
						<select class="special-flexselect" id="brandSelect" name="ouId" data-placeholder="请选择仓库">
						</select>
					</td>
				</tr>
			</table>
			<br/>
			<button loxiaType="button" class="confirm" id="confirm">确认</button>
			<button loxiaType="button" id="back">返回</button>
		</div>
	</div>
	<iframe id="upload" name="upload" style="display:none;"></iframe>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>


</html>