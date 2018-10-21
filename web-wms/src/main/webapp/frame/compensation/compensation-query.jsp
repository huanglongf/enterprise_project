<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<style>
			.clear{
				clear:both;
				height:0;
			    line-height:0;
			}
			.photoInfo{
				float:right;
				margin-left:20px;
				width:130px;
			}
		</style>
		<%@include file="/common/meta.jsp"%>
		<title><s:text name="title.warehouse.pl.verify"/></title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/compensation/compensation-query.js"' includeParams='none' encode='false'/>"></script>
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<jsp:include page="/frame/compensation/compensation-querywarehouse.jsp"></jsp:include>
		<s:text id="pselect" name="label.please.select"/>
		<input type="hidden" id="backTarget" />
		<!-- 配货清单列表 -->
		<div id="showList">
			<div>
			    <form id="plForm" name="plForm" type="post">
					<table >
						<tr>
							<td class="label">索赔编码：</td>
							<td width="10%" id="">
								<input loxiaType="input" name="whCompensationCommand.claimCode" trim="true"/>
							</td>
							<td class="label">索赔分类：</td>
							<td>
								<select loxiaType="select" name="whCompensationCommand.claimTypeId" id="claimTypeId">
									<option value="">--请选择--</option>
								</select>
							</td>
							<td class="label">索赔原因：</td>
							<td>
								<select loxiaType="select" name="whCompensationCommand.claimReasonId" id="claimReasonId">
									<option value="">--请选择--</option>
								</select>
							</td>
							<td></td>
						</tr>
						<tr>
							<td class="label">索赔状态：</td>
							<td>
								
								<select loxiaType="select" name="whCompensationCommand.claimStatus" id="claimStatus">
									<option value="">--请选择--</option>
									<option value="1">已创建</option>
									<option value="2">已处理</option>
									<option value="3">已审核</option>
									<option value="5">索赔成功</option>
									<option value="10">索赔失败</option>
									<option value="17">作废单据</option>
								</select>
								<font color="red">作废单据默认不查询</font>
							</td>
							<td class="label">店铺名称：</td>
							<td>
								<input loxiaType="input" name="whCompensationCommand.shopOwner" trim="true"/>
							</td>
							<td class="label">相关单据号：</td>
							<td>
								<input loxiaType="input" name="whCompensationCommand.omsOrderCode" trim="true"/>
							</td>
							<td></td>
						</tr>
						<tr>
							<td class="label">快递单号：</td>
							<td>
								<input loxiaType="input" name="whCompensationCommand.transNumber" trim="true"/>
							</td>
							<td class="label">物流服务商：</td>
							<td>
								<select loxiaType="select" name="whCompensationCommand.transCode" id="transCode">
									<option value="">--请选择--</option>
								</select>
							</td>
							<td class="label">创建时间：</td>
							<td >
								<input loxiaType="date" style="width: 150px" name="startDate" id="startDate" showTime="true"/>
								到：
								<input loxiaType="date" style="width: 150px" name="endDate" id="endDate" showTime="true"/>
							</td>
							<td></td>
						</tr>
						<tr>
							<td class="label">创建人：</td>
							<td>
								<input loxiaType="input" name="whCompensationCommand.createUserName"  trim="true"/>
							</td>
							<td class="label">发货仓:</td>
							<td><select loxiaType="select" id="warehouseId" name="whCompensationCommand.warehouseId">
							      <option value="">--请选择--</option>
							    </select>
							    
							</td>
							<td class="label">订单发货时间：</td>
							<td>
								
								<input loxiaType="date" style="width: 150px" name="finishStartDate" id="finishStartDate" showTime="true"/>
								到：
								<input loxiaType="date" style="width: 150px" name="finishEndDate" id="finishEndDate" showTime="true"/>
							</td>
							<td align="left"><button id="btnSearchShop" type="button" loxiaType="button" class="confirm"><s:text name="button.queryw"/></button>   
							</td>
							
						</tr>
					</table>
					<div class="clear"></div>
				</form>
				<div class="buttonlist">
					<button id="search" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
					<button id="reset" type="reset" loxiaType="button"><s:text name="button.reset"/></button>
					<button id="fileDown" type="button" loxiaType="button" class="confirm">附件批量下载</button>
					<button id="exportClaimant" type="button" loxiaType="button" class="confirm">导出索赔信息</button>
				</div>
			</div>
			<div>
				<table id="tbl-dispatch-list"></table>
				<div id="pager"></div>
			</div>
		</div>
		<!-- 详情列表 -->
		<div id="showDetail" class="hidden">
			<table>
				<tr>
					<td class="label">索赔编码：</td>
					<td id="d_claimCode"></td>
					<td class="label">索赔状态：</td>
					<td   id="d_status"></td>
					<td class="label">索赔分类：</td>
					<td   id="d_type"></td>
					<td class="label">索赔原因：</td>
					<td   id="d_cause"></td>
				</tr>
				<tr>
					<td class="label">店铺：</td>
					<td   id="d_owner"></td>
					<td class="label">平台订单号：</td>
					<td   id="d_erpOrderCode"></td>
					<td class="label">相关订单号：</td>
					<td   id="d_omsOrderCode"></td>
					<td class="label">承担方：</td>
					<td   id="">物流部</td>
					<td class="label"></td>
					<td   id=""></td>
				</tr>
				<tr>
					<td class="label">物流服务商：</td>
					<td   id="d_lpCode"></td>
					<td class="label">快递单号：</td>
					<td   id="d_lpNum"></td>
					<td class="label">创建人：</td>
					<td   id="d_createUserName"></td>
					<td class="label">创建时间：</td>
					<td   id="d_createTime"></td>
					<td class="label"></td>
					<td   ><input type="hidden" id="inp_url"/></td>
				</tr>
				<tr>
					<td class="label" align="right">是否外箱破损:</td>
					<td align="right">
						是：<input type="radio" disabled="true" id="d_isOuterContainerDamaged_t" name="d_isOuterContainerDamaged"/>
						否：<input type="radio" disabled="true" id="d_isOuterContainerDamaged_f" name="d_isOuterContainerDamaged"/>
					</td>
					<td class="label" align="right">产品包装是否破损:</td>
					<td  align="left">
						是：<input type="radio" disabled="true" id="d_isPackageDamaged_t" name="d_isPackageDamaged"/>
						否：<input type="radio" disabled="true" id="d_isPackageDamaged_f" name="d_isPackageDamaged"/>
					</td>
					<td class="label" align="right">是否二次封箱:</td>
					<td  align="left">
						是：<input type="radio" disabled="true" id="d_isTwoSubBox_t" name="d_isTwoSubBox"/>
						否：<input type="radio" disabled="true" id="d_isTwoSubBox_f" name="d_isTwoSubBox"/>
					</td>
					<td class="label" align="right">是否有产品退回:</td>
					<td  align="left">
						是：<input type="radio" disabled="true" id="d_isHasProductReturn_t" name="d_isHasProductReturn"/>
						否：<input type="radio" disabled="true" id="d_isHasProductReturn_f" name="d_isHasProductReturn"/>
					</td>
					<td class="label" align="right">箱内填充是否充分:</td>
					<td  align="left">
						是：<input type="radio" disabled="true" id="d_isFilledWith_t" name="d_isFilledWith"/>
						否：<input type="radio" disabled="true" id="d_isFilledWith_f" name="d_isFilledWith"/>
					</td>
				</tr>
			</table>
			<hr />
			<br /><br />
				<div>
					<table>
						<tr>
							<td class="label">附加金额：</td>
							<td id="d_extralAmt"></td>
							<td class="label">申请理赔总金额（元）：</td>
							<td  id="d_totalClaimAmt"></td>
							<td class="label">店铺备注：</td>
							<td id="d_remark"></td>
							<td class="label"></td>
							<td><button id="file_button" type="button" loxiaType="button">附件下载</button></td>
						</tr>
						<tr>
							<td class="label" colspan="2">选择赔偿责任商：</td>
							<td  id=""></td>
							<td class="label" align="left">物流部赔偿</td>
							<td class="label">请输入赔偿金额（元）：</td>
							<td ><input type="text" id="d_logisticsepartmentAmt" onblur="validata(this)"/></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td class="label"></td>
							<td  id=""></td>
							<td  id=""></td>
							<td class="label">物流服务商赔偿</td>
							<td class="label">请输入赔偿金额（元）：</td>
							<td><input type="text" id="d_expressDeliveryAmt"  onblur="validata(this)"/></td>
							<td></td>
							<td></td>
						</tr>
					</table>
					<table id="tbl-showDetail"></table>
					<div>
						处理意见：<textarea rows="2" cols="38" id="d_disposeRemark"></textarea>
					</div>
				</div>
				<div class="buttonlist">
					<div id="div_dispose" class="hidden">
						<button id="dispose" type="button" loxiaType="button" class="confirm">处理索赔</button>
					</div>
					</br>
					<div id="div_success" class="hidden">
						<button id="success" type="button" loxiaType="button">索赔成功</button>
					</div>
					</br>
					<div id="div_fail" class="hidden">
						<button id="fail" type="button" loxiaType="button">索赔失败</button>
					</div>
					</br>
					<button  loxiaType="button" id="back">返回</button>
				</div>
		</div>
		<iframe id="deliveryInfoExport" class="hidden"></iframe>
	<iframe id="exportFrame" class="hidden" target="_blank"></iframe>
	</body>
	
</html>