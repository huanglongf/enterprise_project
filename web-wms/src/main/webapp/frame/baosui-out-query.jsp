<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/baosui-out-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
	<div id="div-sta-list" >
		<form id="queryForm" name="queryForm">
			<table>
				<tr>
					<td class="label">店铺</td>
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
				<td width="5%" class="label">作业单号:</td>
				<td width="10%"><input loxiaType="input" trim="true" name="sta.code"/></td>
			    <td class="label" >创建时间:</td>
				<td>
				<input  id="startTime" loxiaType="date" name="sta.createTime1" trim="true" showTime="true"/>
				</td>
					<td class="label">至</td>
				<td>
					<input  id="endTime" loxiaType="date" name="sta.endCreateTime1" trim="true" showTime="true"/>
				</td>
				</tr>
				<tr>
					<td width="10%" class="label">相关单据号:</td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.slipCode"/></td>
					<td width="10%" class="label">平台订单号:</td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.slipCode1"/></td>
					<td width="10%" class="label">车牌号:</td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.licensePlateNumber"/></td>
					 <td width="10%" class="label">报税出入库类型:</td>
					<td>
						<select loxiaType="select" id="baoShuiType" name="sta.baoShuiType">
						<option value="">请选择</option>
						<option value="1">入库</option>
						<option value="2">出库</option>
						</select>
					</td>
				</tr>
				<tr><td><br /></td></tr>
				<tr>
					<td width="10%" class="label">配载单号:</td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.prestowageNo"/></td>
					<td width="10%" class="label">单据类型:</td>
					<td>
					<select loxiaType="select" id="staType" name="sta.wmsType">
							<option value="" selected="selected">请选择</option>
							<option value="销售出库">销售出库 </option>
							<option value="退货入库">退货入库 </option>
							<option value="换货出库 ">换货出库 </option>
							<option value="移库入库">移库入库 </option>
                            <option value="货返入库 ">货返入库 </option>
							<option value="代销 出库">代销 出库</option>
                            <option value="转店">转店 </option>
							<option value="VMI退大仓">VMI退大仓 </option>
							<option value="VMI转店退仓">VMI转店退仓 </option>
							
						</select>
					</td>
					<td width="10%" class="label">报关状态:</td>
					<td>
					<select loxiaType="select" id="staType" name="sta.baoShuiStatus">
						<option value="">请选择</option>
						<option value="1">未同步</option>
						<option value="2">同步成功</option>
						<option value="3">同步失败</option>
					</select>
					</td>
					
					<td width="10%" class="label">单据状态:</td>
					<td>
					<select loxiaType="select" id="staType" name="sta.wmsStatus">
						<option value="" selected="selected">请选择</option>
						<option value="已创建">已创建 </option>
						<option value="配货 ">配货 </option>
						<option value="已核对">已核对 </option>
						<option value="已转出 ">已转出 </option>
						<option value="部分转入">部分转入 </option>
						<option value="装箱中">装箱中</option>
						<option value="已完成">已完成 </option>
						<option value="取消未处理">取消未处理 </option>
						<option value="取消已处理">取消已处理 </option>
						<option value="配货失败">配货失败 </option>
						<option value="冻结">冻结 </option>
					</select>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text> </button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
			<button type="button" loxiaType="button" class="confirm" id="countGrossWtNetWt" ><s:text name="一键计算净重毛重"></s:text> </button>
		</div>
		<table id="tbl-baoShuiOutStaList"></table>
		<div id="pager"></div>
	</div>
	<div id="div-sta-detail" class="hidden">
			<form id="queryForm2" name="queryForm2">
				<table>
				<tr>
				<td width="10%" class="label">作业单号:</td>
				<td width="15%"><span id="code" name="sta.code"></span></td>
				<td width="10%" class="label">相关单据号:</td>
				<td width="15%"><span id="slipCode" name="sta.slipCode"></span>
				<input loxiaType="input" id="imp_staId" trim="true" name="imp_staId" hidden="true"/>
				</td>
		   		<tr>
					<td width="10%" class="label">商品编码:</td>
					<td width="15%"><input loxiaType="input" id="skuCode" trim="true" name="sta.skuCode"/></td>
					<td width="10%" class="label">UPC:</td>
					<td width="15%"><input loxiaType="input" id="skuUpc" trim="true" name="sta.skuUpc"/></td>
				</tr>
			</table>
		    </form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search2" ><s:text name="button.search"></s:text></button>
			<button type="button" loxiaType="button" id="reset2"><s:text name="button.reset"></s:text> </button>
			<button type="button" loxiaType="button" class="confirm" id="addSku">添加明细行</button>
		</div>
		<div>
			<table id="tbl-sta-line"></table>
			<div id="pagerDetail"></div>
			<button loxiaType="button" id="break"><s:text name="button.back"></s:text></button>
		</div>
	</div>
	<iframe id="upload" name="upload" class="hidden"></iframe>
	<div id="download"></div>
	<div id="editCustomsDeclarationLine" style="text-align: center;">
		<div class="buttonlist">
			<form>
				<table>
						<tr>
							<td>SKU编码<span style="color:red;">*</span></td>
								<td><input loxiaType="input" trim="true" id="skuCode1" readonly="readonly"/></td>
								<td><input loxiaType="input" trim="true" id="customsDeclarationDtoLineId" hidden="true"/></td>
						    </td>
						</tr>
						<tr>
							<td>申报数量<span style="color:red;">*</span></td>
							<td><input loxiaType="input" trim="true" id="gQty"/></td>
						</tr>
				</table>
					<button type="button" loxiaType="button" class="confirm" id="save">保存</button>
					<button type="button" loxiaType="button" id="closediv">关闭</button>
			</form>
			</div>
	</div> 
	
		<div id="editCustomsDeclaration" style="text-align: center;">
		<div class="buttonlist">
			<form>
				<table>
						<tr>
							<td>PO单号<span style="color:red;">*</span></td>
								<td><input loxiaType="input" trim="true" id="wmsCode" readonly="readonly"/></td>
								<td><input loxiaType="input" trim="true" id="customsDeclarationDtoId" hidden="true"/></td>
						    </td>
						</tr>
						<tr>
							<td>毛重<span style="color:red;">*</span></td>
								<td><input loxiaType="input" trim="true" id="grossWt" /></td>
						    </td>
						</tr>
						<tr>
							<td>包装种类<span style="color:red;">*</span></td>
							<td><select id="wrapType" loxiaType="select" >
								<option value="木箱">木箱</option>
								<option value="纸箱">纸箱</option>
								<option value="桶">桶</option>
								<option value="散装">散装</option>
								<option value="托盘">托盘</option>
								<option value="包">包</option>
								<option value="其他">其他</option>
							</select>
						</tr>
						<tr>
							<td>车牌号<span style="color:red;">*</span></td>
							<td><input loxiaType="input" trim="true" id="licensePlateNumber"/></td>
						</tr>
				</table>
					<button type="button" loxiaType="button" class="confirm" id="savecus">保存</button>
					<button type="button" loxiaType="button" id="closediv2">关闭</button>
			</form>
			</div>
	</div> 
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
	<jsp:include page="/common/include/include-offline-reweight.jsp"></jsp:include>
</body>
</html>