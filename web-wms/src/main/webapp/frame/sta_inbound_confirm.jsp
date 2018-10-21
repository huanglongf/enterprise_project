<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/sta_inbound_confirm.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
						<div style="float: left">
							<select id="companyshop" name="sta.owner" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</div>						
						<div style="float: left">
							<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
						</div>
					</td>
					<td width="15%" class="label">作业单类型</td>
					<td width="35%">
						<select loxiaType="select" id="typeList" name="sta.intStaType">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="11">采购入库</option>
							<option value="12">结算经销入库</option>
							<option value="14">代销入库 </option>
							<option value="15">赠品入库 </option>
							<option value="16">移库入库 </option>
							<option value="17">货返入库</option>
							<option value="32">库间移动</option>
							<option value="81">VMI移库入库</option>
							<option value="90">同公司调拨</option>
							<option value="91">不同公司调拨</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">作业单号</td>
					<td>
						<input type="text" loxiaType="input" trim="true" name="sta.code"></input>
					</td>
					<td class="label">相关单据号</td>
					<td>
						<input type="text" loxiaType="input" trim="true" name="sta.refSlipCode"></input>
					</td>
				</tr>
				<tr>
					<td class="label">辅助相关单据号</td>
					<td>
						<input type="text" loxiaType="input" trim="true" name="sta.slipCode1"></input>
					</td>
					<td></td>
					<td>
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
		<table id="tbl_sta_list"></table>
		<div id="pager"></div>
	</div>
	<div id="divDetial" class="hidden">
		<input type="hidden" id='sta_id'/>
		<input type="hidden" id='stv_id'/>
		<input type="hidden" id='isInboundInvoice'/>
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
				<td class="label">
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
			<p>
			</p>
			<button loxiaType="button" class="confirm" id="confirm">查看</button>
			<button loxiaType="button" id="cancel">取消</button>
			<button loxiaType="button" id="back">返回</button>
		</div>
		<div >
			<table>
				<tr>
					<td class="label">
						 相关收货凭证导入：
					</td>
					<td>
						<form method="post" enctype="multipart/form-data" id="corForm" name="corForm" target="upload">
							<input type="file" loxiaType="input" id="corFile" name="file" style="width: 200px;"/>
						</form>
					</td>
					<td>
						<button loxiaType="button" class="confirm" id="corImport">导入</button>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="dialogConfirm">
		<div class="buttonlist">
			<form method="post" enctype="multipart/form-data" id="uploadForm" name="uploadForm" target="pdaLogFrame">	
				<font class="label">商品基本信息维护导入：</font><input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
				<button type="button" loxiaType="button" class="confirm" id="import" >导入</button>
				<button type="button" loxiaType="button" id="export" >模版文件下载</button>
			</form>
			<iframe id="pdaLogFrame" name="pdaLogFrame" class="hidden"></iframe>
		</div>
		<table id="tbl_sta_dif_line"></table>
		<div id="pager2"></div>
		<div class="buttonlist">
			<!-- <div id='invoice'>
				<table width="70%">
					<tr>
						<td width="15%" class="label">
							发票号
						</td>
						<td  width="30%">
							<input loxiaType="input" name="invoiceNumber" id="invoice_invoiceNumber" readonly="readonly" />
						</td>
						<td colspan="2" id="invoice_select2TD"  class='hidden'>
							<select  loxiaType="select" name="numberSelect" id="invoice_selectInv">
								<option value="">请选择发票号</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="15%" class="label">
							税收系数
						</td>
						<td  width="30%">
							<input loxiaType="number" mandatory="true" decimal="2"  readonly="readonly" id="invoice_dutyPercentage" trim="true" />
						</td>
						<td width="15%" class="label">
							其他系数
						</td>
						<td  width="30%">
							<input loxiaType="number" mandatory="true" decimal="2" readonly="readonly" id="invoice_miscFeePercentage" trim="true"  />
						</td>
					</tr>
				</table>
			</div> -->
			<p>
				<button loxiaType="button" class="confirm" id="submit">确认</button>
				<button loxiaType="button"  id="dialogCancel">取消</button>
				<button loxiaType="button" id="dialogClose">关闭稍后处理</button>
			</p>
			<table>
				<tr>
					<td class="label">
						确认收货导入：
					</td>
					<td>
						<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
							<input type="file" loxiaType="input" id="staFile" name="file" style="width: 200px;"/>
						</form>
					</td>
					<td>
						<button loxiaType="button" class="confirm" id="impDiff">导入确认收货差异调整</button>
					</td>
					<td>
						<button loxiaType="button" id="expDiff">导出确认收货差异明细</button>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<iframe id="upload" name="upload" style="display:none;"></iframe>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>


</html>