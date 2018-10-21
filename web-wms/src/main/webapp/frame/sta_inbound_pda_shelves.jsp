<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/sta_inbound_pda_shelves.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
							<option value="13">其他入库</option>
							<option value="14">代销入库 </option>
							<option value="15">赠品入库 </option>
							<option value="16">移库入库 </option>
							<option value="55">GI 上架入库</option>
							<option value="81">VMI移库入库</option>
							<option value="85">VMI库存调整入库 </option>
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
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">无差异明细</a></li>
				<li><a href="#tabs-2">差异明细</a></li>
				<li><a href="#tabs-3">操作明细</a></li>
			</ul>
			<div id="tabs-1" >
				<br />
				<table id="tbl_sta_line_list"></table>
				<div id="pagerLine"></div>
				<div class="buttonlist">
					<button loxiaType="button" class="confirm" id="executeLine">无差异优先执行</button>
				</div>
			</div>
			<div id="tabs-2">
				<br />
				<form id="queryLineForm" name="queryLineForm">
					<table width="700px">
						<tr>
							<td class="label">库位</td>
							<td>
								<input type="text" loxiaType="input" trim="true" name="line.locationCode" onclick=""></input>
							</td>
							<td class="label">商品条码</td>
							<td>
								<input type="text" loxiaType="input" trim="true" name="line.barCode"></input>
							</td>
							<td class="label">货号</td>
							<td>
								<input type="text" loxiaType="input" trim="true" name="line.supplierCode"></input>
							</td>
						</tr>
					</table>
				</form>
				<div class="buttonlist">
					<button loxiaType="button" class="confirm" id="lineSearch">查询</button>
					<button loxiaType="button" id="lineReset">重置</button>
				</div>
				<table id="tbl_sta_line_dif_list"></table>
				<div id="pagerDifLine"></div>
			</div>
			<div id="tabs-3">
				<br />
				<form id="queryPDALineForm" name="queryPDALineForm">
					<table width="700px">
						<tr>
							<td class="label">PDA操作人*</td>
							<td >
								<select loxiaType="select" id="pdaUserNameList" name="sta.intStaType">
								</select>
							</td>
							<td colspan="4"></td>
						</tr>
						<tr>
							<td class="label">库位</td>
							<td>
								<input type="text" loxiaType="input" trim="true" name="line.locationCode"></input>
							</td>
							<td class="label">商品条码</td>
							<td>
								<input type="text" loxiaType="input" trim="true" name="line.barCode"></input>
							</td>
							<td class="label">货号</td>
							<td>
								<input type="text" loxiaType="input" trim="true" name="line.supplierCode"></input>
							</td>
						</tr>
					</table>
				</form>
				<div class="buttonlist">
					<button loxiaType="button" class="confirm" id="linePDASearch">查询</button>
					<button loxiaType="button" id="linePDAReset">重置</button>
				</div>
				<table id="tbl_sta_line_shelves_list"></table>
				<div id="pagerShelves"></div>
				<div class="buttonlist">
					<button loxiaType="button" class="confirm" id="executePDALine">执行PDA上架操作</button>
				</div>
			</div>
		</div>
		<div class="buttonlist">
			<button loxiaType="button" id="back">返回</button>
		</div>
	</div>
	<div id="show_skusn_dialog" style="text-align: center;">
		<table id="tbl_skusn_list"></table>
		<input type="text" id = "orderSkuId" class = "hidden"></input>
			<div style="margin-top: 40px;">
			<table>
				<tr>
					<td><label>新增SN编码：</label></td>
					<td>
						<input loxiaType="input"  type="text" id = "skusn" ></input>
					</td>
					<td>
						<button id="newSkuSn" loxiaType="button" class="confirm" onclick="addNewSkuSn();">保存</button>
					</td>
				</tr>
			</table>
			<br/><br/>
				<tr>
					<td>
						<button id="okDialog" loxiaType="button" class="confirm" onclick="okDialog();">编辑完成</button>&nbsp;&nbsp;&nbsp;
					</td>
					<!--  <td>
						<button id="closeDialog" loxiaType="button" class="confirm" onclick="closeDialog();">关闭</button>
					</td>-->
				</tr>
			<table>
				
			</table>
			</div>
	</div>
	<iframe id="upload" name="upload" style="display:none;"></iframe>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>