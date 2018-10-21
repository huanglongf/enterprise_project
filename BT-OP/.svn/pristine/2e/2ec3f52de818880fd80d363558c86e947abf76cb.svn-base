<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%-- <%@ include file="/base/yuriy.jsp" %> --%>
<%@ include file="/templet/common.jsp"%>
<title>OP</title>
</head>
<body>
	<div class="page-header">
		<h1 style='margin-left: 20px'>运单</h1>
	</div>
	<div class="search-toolbar">
		<div class="widget-box">
			<div class="widget-header widget-header-small">
				<h5 class="widget-title lighter">查询栏</h5>
				<a class="pointer" title="初始化" onclick="refresh()"><i
					class="fa fa-refresh"></i></a>
			</div>
			<div class="widget-body">
				<div class="widget-main no-padding-bottom">
					<form id="query_logistics" name="query_logistics"
						class="container search_form">
						<div class="row form-group" style="height: 34px;">
							<div class="col-md-3 no-padding" style="display: none">
								<div class="col-md-11 no-padding">
									<input class="form-control" id="tabNo" name="tabNo" type="text"
										value='1' hidden="true">
								</div>
							</div>
							<div class="col-md-1 no-padding text-center search-title">
								<label class="blue" for="wo_no">状态</label>
							</div>
							<div class="col-md-3 no-padding">
								<div class="col-md-11 no-padding">

									<select id="query_state" name="status" data-edit-select="1"
										style="width: 160px; height: 34px;">
										<option value=""
											${""==queryParam.status?"selected='selected'":"" }>---请选择---</option>
										<option value="1"
											${"1"==queryParam.status?"selected='selected'":"" }>已录单</option>
										<option value="4"
											${"4"==queryParam.status?"selected='selected'":"" }>已下单</option>
										<option value="11"
											${"11"==queryParam.status?"selected='selected'":"" }>下单失败</option>
										<option value="2"
											${"2"==queryParam.status?"selected='selected'":"" }>待揽收</option>
										<option value="5"
											${"5"==queryParam.status?"selected='selected'":"" }>已揽收</option>
										<option value="6"
											${"6"==queryParam.status?"selected='selected'":"" }>已发运</option>
										<option value="7"
											${"7"==queryParam.status?"selected='selected'":"" }>已签收</option>
										<option value="8"
											${"8"==queryParam.status?"selected='selected'":"" }>签收失败</option>
										<option value="9"
											${"9"==queryParam.status?"selected='selected'":"" }>已退回</option>
										<option value="10"
											${"10"==queryParam.status?"selected='selected'":"" }>已取消</option>
										<option value="0"
											${"0"==queryParam.status?"selected='selected'":"" }>已作废</option>
									</select>
								</div>
							</div>
							<div class="col-md-1 no-padding text-center search-title">
								<label class="blue" for="waybill">客户单号</label>
							</div>
							<div class="col-md-3 no-padding">
								<div class="col-md-11 no-padding">
									<input type='text' id='customer_number'
										value="${queryParam.customer_number}" name='customer_number'
										style="width: 160px; height: 34px;">
								</div>
							</div>
							<div class="col-md-1 no-padding text-center search-title">
								<label class="blue" for="platform_number">快递公司</label>
							</div>
							<div class="col-md-3 no-padding">
								<div class="col-md-11 no-padding">
									<select id="expressCode" name="expressCode"
										data-edit-select="1" style="width: 160px; height: 34px;">
										<option value="" select>---请选择---</option>
										<c:forEach items="${venders}" var="street">
											<%--  <option value="${street.express_code}">${street.express_name}</option> --%>
											<c:if
												test='${queryParam.expressCode eq street.express_code }'>
												<option value="${street.express_code}"
													${street.express_code ==  queryParam.expressCode ? "selected = 'selected'" : "" }>${street.express_name}</option>
											</c:if>
											<c:if
												test='${queryParam.expressCode ne street.express_code }'>
												<option value="${street.express_code}">${street.express_name}</option>
											</c:if>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>

						<div class="row form-group">
							<div class="col-md-1 no-padding text-center search-title">
								<label class="blue" for="create_by_group_display">制单日期</label>
							</div>
							<div class="col-md-3 no-padding">
								<div class="col-md-11 no-padding">
									<input type='text' id='ess_time' name='ess_time'
										value="${queryParam.ess_time}"
										style="width: 160px; height: 34px;"
										onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})">
								</div>
							</div>
							<div class="col-md-1 no-padding text-center search-title">
								<label class="blue" for="current_processor_group_display">到</label>
							</div>
							<div class="col-md-3 no-padding">
								<div class="col-md-11 no-padding">
									<input type='text' id='ese_time' name='ese_time'
										value="${queryParam.ese_time}"
										style="width: 160px; height: 34px;"
										onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})">
								</div>
							</div>
							<div class="col-md-1 no-padding text-center search-title">
								<label class="blue" for="current_processor_display">快递业务类型</label>
							</div>
							<div class="col-md-3 no-padding">
								<div class="col-md-11 no-padding">
									<select id="producttype_name" name="producttype_code"
										data-edit-select="1"
										style="width: 160px; height: 34px; display: none;">
										<option value="" select>---请选择---</option>
										<c:forEach items="${type}" var="street">
											<%-- <option value="${street.product_type_code}">${street.product_type_name}</option> --%>
											<c:if
												test='${queryParam.producttype_code eq street.product_type_code }'>
												<option value="${street.product_type_code}"
													${street.product_type_code ==  queryParam.producttype_code ? "selected = 'selected'" : "" }>${street.product_type_name}</option>
											</c:if>
											<c:if
												test='${queryParam.producttype_code ne street.product_type_code }'>
												<option value="${street.product_type_code}">${street.product_type_name}</option>
											</c:if>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="senior-search">
							<div class="row form-group">
								<div class="col-md-1 no-padding text-center search-title">
									<label class="blue" for="create_by_display">下单日期</label>
								</div>
								<div class="col-md-3 no-padding">
									<div class="col-md-11 no-padding">
										<input type='text' id='s_time' name='s_time'
											value="${queryParam.s_time}"
											style="width: 160px; height: 34px;"
											onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})">
									</div>
								</div>
								<div class="col-md-1 no-padding text-center search-title">
									<label class="blue" for="wo_status_display">到</label>
								</div>
								<div class="col-md-3 no-padding">
									<div class="col-md-11 no-padding">
										<input type='text' id='e_time' name='e_time'
											value="${queryParam.e_time}"
											style="width: 160px; height: 34px;"
											onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})">
									</div>
								</div>
								<div class="col-md-1 no-padding text-center search-title">
									<label class="blue" for="wo_type_display">备注</label>
								</div>
								<div class="col-md-3 no-padding">
									<div class="col-md-11 no-padding">
										<input type='text' id='memo' name='memo'
											value="${queryParam.memo}"
											style="width: 160px; height: 34px;">
									</div>
								</div>
							</div>
							<div class="row form-group">
								<div class="col-md-1 no-padding text-center search-title">
									<label class="blue">揽件日期</label>
								</div>
								<div class="col-md-3 no-padding">
									<div class="col-md-11 no-padding">
										<input type='text' id='ljs_time' name='ljs_time'
											value="${queryParam.ljs_time}"
											style="width: 160px; height: 34px;"
											onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})">
									</div>
								</div>

								<div class="col-md-1 no-padding text-center search-title">
									<label class="blue" for="create_time">到</label>
								</div>
								<div class="col-md-3 no-padding">
									<div class="col-md-11 no-padding input-group">
										<input type='text' id='lje_time' name='lje_time'
											value="${queryParam.lje_time}"
											style="width: 160px; height: 34px;"
											onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})">
									</div>
								</div>
								<div class="col-md-1 no-padding text-center search-title">
									<label class="blue" for="last_process_time">运单号</label>
								</div>
								<div class="col-md-3 no-padding">
									<div class="col-md-11 no-padding input-group">
										<input type='text' id='id' name='order_id'
											value="${queryParam.order_id}"
											style="width: 160px; height: 34px;">
									</div>
								</div>
							</div>
							<div class="row form-group">
								<div class="col-md-1 no-padding text-center search-title">
									<label class="blue">预计发货日期</label>
								</div>
								<div class="col-md-3 no-padding">
									<div class="col-md-11 no-padding">
										<input type='text' id='expected_from_dateS'
											name='expected_from_dateS'
											value="${queryParam.expected_from_dateS}"
											style="width: 160px; height: 34px;"
											onFocus="WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})">
									</div>
								</div>

								<div class="col-md-1 no-padding text-center search-title">
									<label class="blue" for="expected_from_dateE">到</label>
								</div>
								<div class="col-md-3 no-padding">
									<div class="col-md-11 no-padding input-group">
										<input type='text' id='expected_from_dateE'
											name='expected_from_dateE'
											value="${queryParam.expected_from_dateE}"
											style="width: 160px; height: 34px;"
											onFocus="WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})">
									</div>
								</div>

								<div class="col-md-1 no-padding text-center search-title">
									<label class="blue">收货机构</label>
								</div>
								<div class="col-md-3 no-padding">
									<div class="col-md-11 no-padding">
										<select id="query_to_orgnization" name="to_orgnization"
											data-edit-select="1" style="width: 160px; height: 34px;">
											<option value="" select>---请选择---</option>
											<c:forEach items="${orgs}" var="street">
												<option value="${street.org_name}">${street.org_name}</option>
												<c:if
													test='${queryParam.to_orgnization eq street.org_name }'>
													<option value="${street.org_name}"
														${street.org_name ==  queryParam.to_orgnization ? "selected = 'selected'" : "" }>${street.org_name}</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="row form-group">
								<div class="col-md-1 no-padding text-center search-title">
									<label class="blue" for="create_time">收货城市</label>
								</div>
								<div class="col-md-3 no-padding">
									<div class="col-md-11 no-padding input-group">
										<select id="query_org_provice_code" name="to_province_code"
											data-edit-select="1" style="width: 160px; height: 34px;"
											onchange='shiftArea(1,"query_org_provice_code","query_org_city_code","query_org_state_code","")'>
											<option value="" select>---请选择---</option>
											<c:forEach items="${areas}" var="area">
												<c:if
													test='${queryParam.to_province_code eq area.area_code }'>
													<%-- <option selected='selected' value="${area.area_code}">${area.area_name}</option> --%>
													<option value="${area.area_code}"
														${area.area_code ==  queryParam.to_province_code ? "selected = 'selected'" : "" }>${area.area_name}</option>
												</c:if>
												<c:if
													test='${queryParam.to_province_code ne area.area_code }'>
													<option value="${area.area_code}">${area.area_name}</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-md-1 no-padding text-center search-title">
									<label class="blue" for="last_process_time">快递单号</label>
								</div>
								<div class="col-md-3 no-padding">
									<div class="col-md-11 no-padding input-group">
										<input type='text' id='query_waybill' name='waybill'
											value="${queryParam.waybill}"
											style="width: 160px; height: 34px;">
									</div>
								</div>
								<div class="col-md-1 no-padding text-center search-title">
									<label class="blue">收货联系人</label>
								</div>
								<div class="col-md-3 no-padding">
									<div class="col-md-11 no-padding">
										<input type='text' id='query_to_contacts' name='to_contacts'
											value="${queryParam.to_contacts}"
											style="width: 160px; height: 34px;">
									</div>
								</div>
							</div>
							<div class="row form-group">
								<div class="col-md-1 no-padding text-center search-title">
									<label class="blue" for="create_time">收货联系电话</label>
								</div>
								<div class="col-md-3 no-padding">
									<div class="col-md-11 no-padding input-group">
										<input type='text' id='query_to_phone' name='to_phone'
											value="${queryParam.to_phone}"
											style="width: 160px; height: 34px;">
									</div>
								</div>
								<div class="col-md-1 no-padding text-center search-title">
									<label class="blue" for="last_process_time">收货地址</label>
								</div>
								<div class="col-md-3 no-padding">
									<div class="col-md-11 no-padding input-group">
										<input type='text' id='query_to_address' name='to_address'
											value="${queryParam.to_address}"
											style="width: 160px; height: 34px;">
									</div>
								</div>
							</div>
						</div>
						<div class="row text-center">
							<a class="senior-search-shift pointer" title="高级查询"><i
								class="fa fa-angle-double-down fa-2x" aria-hidden="true"></i></a>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="dataTables_wrapper form-inline">
			<div class="row">
				<div class="col-md-12">
					<div class="dataTables_length">
						<button
							class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width"
							style="height: 30px; width: 120px;" onclick="queryLogistics();">
							<i class="icon-search icon-on-right bigger-120"></i>查询
						</button>
						<button
							class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width"
							style="height: 30px; width: 120px;" onclick="addlogistics();">
							<i class="icon-hdd"></i>新增
						</button>
						<button
							class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width"
							style="height: 30px; width: 120px;" onclick="confirmOrders();">
							<i class="icon-hdd"></i>确认
						</button>
						<button
							class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width"
							style="height: 30px; width: 120px;"
							onclick="cancelOrderLogistics();">
							<i class="icon-hdd"></i>取消
						</button>
						<button
							class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width"
							style="height: 30px; width: 120px;" onclick="cancelOrder();">
							<i class="icon-hdd"></i>作废
						</button>
						<!-- <button class="btn btn-xs btn-default" style= "height: 30px; width: 120px; " onclick="recoveryOrders();">
												<i class="icon-hdd"></i>订单恢复
											</button> -->
						<button
							class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width"
							style="height: 30px; width: 120px;"
							onclick="loadingCenterPanel('${root}/control/orderPlatform/waybillMasterController/waybiluploade.do');">
							<i class="icon-hdd"></i>导入
						</button>
						<button
							class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width"
							style="height: 30px; width: 120px;" onclick="print();">
							<i class="icon-hdd"></i>打印
						</button>
						<button
							class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width"
							style="height: 30px; width: 125px;" onclick="csvexportwmd();">
							<i class="icon-hdd"></i>VansReport导出
						</button>
						<button
							class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width"
							style="height: 30px; width: 120px;" onclick="exportwmd();">
							<i class="icon-hdd"></i>导出
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="tree_div" id="page_view">
		<div
			style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;"
			id="sc_content">
			<table id="table_content" title="运单列表"
				class="table table-hover table-striped" style="table-layout: fixed;">
				<thead align="center">
					<tr class='table_head_line'>
						<td><input type="checkbox" id="checkAll"
							onclick="inverseCkb('ckb')" /></td>
						<td class="td_cs" style="width: 100px">序号</td>
						<td class="td_cs" style="width: 200px">制单日期</td>
						<td class="td_cs" style="width: 200px">预计发货日期</td>
						<td class="td_cs" style="width: 100px">状态</td>
						<td class="td_cs" style="width: 200px">运单号</td>
						<td class="td_cs" style="width: 200px">客户单号</td>
						<td class="td_cs" style="width: 200px">发货机构</td>
						<td class="td_cs" style="width: 200px">快递公司</td>
						<td class="td_cs" style="width: 200px">快递业务类型</td>
						<td class="td_cs" style="width: 200px">快递单号</td>
						<td class="td_cs" style="width: 200px">支付方式</td>
						<td class="td_cs" style="width: 200px">备注</td>
						<td class="td_cs" style="width: 200px">发货联系人</td>
						<td class="td_cs" style="width: 200px">发货联系电话</td>
						<td class="td_cs" style="width: 200px">发货地址</td>
						<td class="td_cs" style="width: 200px">收货机构</td>
						<td class="td_cs" style="width: 200px">省</td>
						<td class="td_cs" style="width: 200px">市</td>
						<td class="td_cs" style="width: 200px">区</td>
						<td class="td_cs" style="width: 200px">街道</td>
						<td class="td_cs" style="width: 200px">收货联系人</td>
						<td class="td_cs" style="width: 200px">收货联系电话</td>
						<td class="td_cs" style="width: 200px">收货地址</td>
						<td class="td_cs" style="width: 200px">总件数</td>
						<td class="td_cs" style="width: 200px">总毛重</td>
						<td class="td_cs" style="width: 200px">总体积</td>
						<td class="td_cs" style="width: 200px">总金额</td>
						<td class="td_cs" style="width: 200px">下单日期</td>
					</tr>
				</thead>
				<tbody align="center">
					<c:forEach items="${pageView.records }" var="records"
						varStatus='status'>
						<tr ondblclick='toUpdate("${records.id}")'>
							<td><input id="ckb" name="ckb" type="checkbox"
								value="${records.id}"></td>
							<td class="td_cs" style="width: 100px; height: 72px">${status.count }</td>
							<td class="td_cs" style="width: 200px; height: 72px"
								title="${records.create_time }"><fmt:formatDate
									value="${records.create_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td class="td_cs" style="width: 200px; height: 72px">
								${records.expected_from_date}</td>
							<td class="td_cs" style="width: 100px; height: 72px"><c:if
									test="${records.status=='1'}">已录单</c:if> <c:if
									test="${records.status=='0'}">已作废</c:if> <c:if
									test="${records.status=='2'}">待揽收</c:if> <c:if
									test="${records.status=='4'}">下单中</c:if> <c:if
									test="${records.status=='5'}">已揽收</c:if> <c:if
									test="${records.status=='6'}">已发运</c:if> <c:if
									test="${records.status=='7'}">已签收</c:if> <c:if
									test="${records.status=='8'}">签收失败</c:if> <c:if
									test="${records.status=='9'}">已退回</c:if> <c:if
									test="${records.status=='10'}">已取消</c:if> <c:if
									test="${records.status=='11'}">下单失败</c:if></td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.order_id }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.customer_number }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.from_orgnization }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.express_name }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.producttype_name }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.waybill }</td>
							<td class="td_cs" style="width: 100px; height: 72px"><c:if
									test="${records.pay_path=='1'}">到付</c:if> <c:if
									test="${records.pay_path=='2'}">寄付</c:if> <c:if
									test="${records.pay_path=='4'}">寄付月结</c:if> <c:if
									test="${records.pay_path=='3'}">第三方付</c:if></td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.memo }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.from_contacts }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.from_phone }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.from_address }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.to_orgnization }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.to_province }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.to_city }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.to_state }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.to_street }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.to_contacts }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.to_phone }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.to_address }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.total_qty }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.total_weight }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.total_volumn }</td>
							<td class="td_cs" style="width: 200px; height: 72px">${records.total_amount }</td>
							<td class="td_cs" style="width: 200px; height: 72px"
								title="${records.order_time }"><fmt:formatDate
									value="${records.order_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div style="margin-right: 1%; margin-top: 20px;">${pageView.pageView }</div>
	</div>

</body>

<script type='text/javascript'>
	$(function() {
		$(".m-list").attr('style', 'display:none');
		//setTimeout("test();",200);
	});

	function test() {
		$(".m-list").attr('style', 'display:none');
	}

	function test1() {
		setTimeout("test();", 200);
		$(".m-list").attr('style', 'display:none');
	}
	function refresh() {
		$('#query_state').next().val("");
		$('#query_state').next().attr("placeholder", "---请选择---");
		$('#expressCode').next().val("");
		$('#expressCode').next().attr("placeholder", "---请选择---");
		$('#producttype_name').next().val("");
		$('#producttype_name').next().attr("placeholder", "---请选择---");
		$('#query_to_orgnization').next().val("");
		$('#query_to_orgnization').next().attr("placeholder", "---请选择---");
		$('#query_org_provice_code').next().val("");
		$('#query_org_provice_code').next().attr("placeholder", "---请选择---");
		document.getElementById("customer_number").value = "";
		document.getElementById("ess_time").value = "";
		document.getElementById("ese_time").value = "";
		document.getElementById("s_time").value = "";
		document.getElementById("e_time").value = "";
		document.getElementById("memo").value = "";
		document.getElementById("s_time").value = "";
		document.getElementById("id").value = "";
		document.getElementById("query_waybill").value = "";
		document.getElementById("query_to_contacts").value = "";
		document.getElementById("query_to_phone").value = "";
		document.getElementById("query_to_address").value = "";
		document.getElementById("ljs_time").value = "";
		document.getElementById("lje_time").value = "";
		test1();
	}

	function pageJump() {
		var param = '';
		param = $('#query_logistics').serialize();
		document.cookie = "url=/page.do?" + param + '&startRow='
				+ $("#startRow").val() + '&endRow=' + $("#startRow").val()
				+ "&page=" + $("#pageIndex").val() + "&pageSize="
				+ $("#pageSize").val();
		$
				.ajax({
					type : "POST",
					url : '${root}control/orderPlatform/waybillMasterController/page.do?'
							+ param
							+ '&startRow='
							+ $("#startRow").val()
							+ '&endRow='
							+ $("#startRow").val()
							+ "&page="
							+ $("#pageIndex").val()
							+ "&pageSize="
							+ $("#pageSize").val(),
					dataType : "text",
					data : '',
					cache : false,
					contentType : "application/x-www-form-urlencoded;charset=UTF-8",
					success : function(data) {
						$("#page_view").html(data);
					}
				});
	}
	function addlogistics() {
		loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/addlogistics.do?time='
				+ new Date().getTime());
	}
	function toUpdate(uuid) {
		loadingStyle();
		loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/updatelogistics.do?id='
				+ uuid);
		cancelLoadingStyle();
	}

	$(document).on("change", 'select#expressCode', function() {
		ExpressCodeChange("expressCode", "producttype_name");
	});

	function ExpressCodeChange(upper, lower) {
		upper = "#" + upper;
		lower = "#" + lower;
		var express_code = $(upper).val();
		if (express_code == "") {
			$(lower).children(":first").siblings().remove();
			$(lower).val("");
			$(lower).next().val("");
			$(lower).next().attr("placeholder", "---请选择---");
			$(lower).next().attr("disabled", "disabled");
		} else {
			$
					.ajax({
						url : "${root}control/orderPlatform/transportProductTypeController/getProductTypeCode.do",
						type : "post",
						data : {
							"express_code" : express_code
						},
						dataType : "json",
						async : false,
						success : function(result) {
							$(lower).next().attr("disabled", false);
							$(lower).children(":first").siblings().remove();
							$(lower).siblings("ul").children(":first")
									.siblings().remove();
							var content1 = '';
							var content2 = '';
							for (var i = 0; i < result.product.length; i++) {
								content1 += '<option value="' 
							+ result.product[i].product_type_code 
							+ '">'
										+ result.product[i].product_type_name
										+ '</option>'
								content2 += '<li class="m-list-item"  data-value="'
							+ result.product[i].product_type_code
							+ '">'
										+ result.product[i].product_type_name
										+ '</li>'
							}

							$(lower).next().val("");
							$(lower).next().attr("placeholder", "---请选择---");

							$(lower + " option:eq(0)").after(content1);
							$(lower + " option:eq(0)").attr("selected", true);
							$(lower).siblings("ul").children(":first").after(
									content2);
							$(lower).siblings("ul").children(":first")
									.addClass("m-list-item-active");
						},
						error : function(result) {
							alert(result);
						}
					});
		}
	}

	function queryLogistics() {
		jumpToPage(1);
	}

	function cancelOrder() {
		if ($("input[name='ckb']:checked").length == 1) {
			var result = confirm('是否作废！');
			if (result) {
				var ids = $("input[name='ckb']:checked");
				var idsStr = '';
				$.each(ids, function(index, item) {
					idsStr = idsStr + this.value + ';';
				});
				$
						.post(
								'${root}/control/orderPlatform/waybillMasterController/cancelOrder.do?ids='
										+ idsStr,
								function(data) {
									if (data.data == 1) {
										alert("操作成功！");
										loadingCenterPanel("${root }/control/orderPlatform/waybillMasterController/backToMain.do?time="
												+ new Date().getTime());
									} else if (data.data == 2) {
										alert("操作失败！");
										alert(data.msg);
									} else if (data.data == 0) {
										alert("状态必须为已录单才能作废！");
									}
									//find();
								});

			}

		} else {
			alert("请选择一行!");
		}
	}

	function confirmOrders() {
		if ($("input[name='ckb']:checked").length >= 1) {
			var sum = $("input[name='ckb']:checked").length;
			var result = confirm('是否对选中' + sum + '进行确认操作！');
			if (result) {
				var ids = $("input[name='ckb']:checked");
				var idsStr = '';
				$.each(ids, function(index, item) {
					idsStr = idsStr + this.value + ';';
				});
				$
						.post(
								'${root}/control/orderPlatform/waybillMasterController/confirmOrders.do?ids='
										+ idsStr,
								function(data) {
									if (data.data == 1) {
										alert("操作成功！");
										loadingCenterPanel("${root }/control/orderPlatform/waybillMasterController/backToMain.do?time="
												+ new Date().getTime());
									} else if (data.data == 2) {
										alert("操作失败！");
										alert(data.msg);
									} else if (data.data == 0) {
										alert("状态必须为已录单才能确认下单！");
									}
								});

			}

		} else {
			alert("请勾选!");
		}
	}

	function cancelOrderLogistics() {
		if ($("input[name='ckb']:checked").length == 1) {
			var sum = $("input[name='ckb']:checked").length;
			var result = confirm('是否对选中项进行取消操作！');
			if (result) {
				var ids = $("input[name='ckb']:checked");
				var idsStr = '';
				$.each(ids, function(index, item) {
					idsStr = idsStr + this.value + ';';
				});
				$
						.post(
								'${root}/control/orderPlatform/waybillMasterController/cancelOrderLogistics.do?ids='
										+ idsStr,
								function(data) {
									if (data.data == 1) {
										alert("操作成功！");
										loadingCenterPanel("${root }/control/orderPlatform/waybillMasterController/backToMain.do?time="
												+ new Date().getTime());
									} else if (data.data == 2) {
										alert("操作失败！");
										alert(data.msg);
									} else if (data.data == 0) {
										alert("已经下单才能取消下单！");
									}
								});

			}

		} else {
			alert("请勾选一项!");
		}
	}

	function recoveryOrders() {
		if ($("input[name='ckb']:checked").length == 1) {
			var result = confirm('是否恢复！');
			if (result) {
				var ids = $("input[name='ckb']:checked");
				var idsStr = '';
				$.each(ids, function(index, item) {
					idsStr = idsStr + this.value + ';';
				});
				$
						.post(
								'${root}/control/orderPlatform/waybillMasterController/recoveryOrders.do?ids='
										+ idsStr,
								function(data) {
									if (data.data == 1) {
										alert("操作成功！");
										loadingCenterPanel("${root }/control/orderPlatform/waybillMasterController/backToMain.do?time="
												+ new Date().getTime());
									} else if (data.data == 2) {
										alert("操作失败！");
										alert(data.msg);
									} else if (data.data == 0) {
										alert("状态必须为已作废才能恢复！");
									}
									//find();
								});

			}

		} else {
			alert("请选择一行!");
		}
	}

	function exportwmd() {
		var result = confirm('是否导出！');
		if (result) {
			param = $('#query_logistics').serialize();
			$
					.ajax({
						type : "POST",
						url : root
								+ "control/orderPlatform/waybillMasterController/exportwmd.do?"
								+ param,
						//json格式接收数据  
						dataType : "json",
						success : function(jsonStr) {
							if (jsonStr.out_result == 1) {
								window.open(root + jsonStr.path);
								// alert(root + jsonStr.path);
							}
						}
					});

		}

	}
	function csvexportwmd() {
		var result = confirm('是否导出！');
		if (result) {
			param = $('#query_logistics').serialize();
			$
					.ajax({
						type : "POST",
						url : root
								+ "control/orderPlatform/waybillMasterController/csvexportwmd.do?"
								+ param,
						//json格式接收数据  
						dataType : "json",
						success : function(jsonStr) {
							if (jsonStr.out_result == 1) {
								window.open(root + jsonStr.path);
							} else if (jsonStr.out_result == 0) {
								alert("运单不存在，或运单未下单！");
							}
						}
					});

		}

	}

	function printPdf() {
		if ($("input[name='ckb']:checked").length == 1) {
			var result = confirm('是否打印！');
			if (result) {
				var ids = $("input[name='ckb']:checked");
				var idsStr = '';
				$.each(ids, function(index, item) {
					idsStr = this.value;
				});
				$
						.post(
								'${root}/control/orderPlatform/waybillMasterController/printOrder.do?ids='
										+ idsStr,
								function(data) {
									if (data.data == 1) {
										if (data.code == 4) {
											var result1 = confirm('该运单已打印，是否打印！');
											if (result1) {
												$
														.ajax({
															type : "POST",
															url : root
																	+ "control/orderPlatform/SFPrintController/printBaozunWaybilMaster.do?ids="
																	+ idsStr,
															data : "",
															dataType : "json",
															success : function(
																	jsonStr) {
																if (jsonStr.out_result == 1) {
																	window
																			.open(root
																					+ jsonStr.path);
																} else {
																	alert(jsonStr.out_result_reason);
																}
															}
														});
											}
										} else {
											window.open("http://localhost/"
													+ jsonStr.file_name);
										}
									} else if (data.data == 2) {
										alert("操作失败！");
										alert(data.msg);
									} else if (data.data == 0) {
										alert("尚未下单，无法打印！");
									} else if (data.data == 3) {
										alert("该运单无需打印！");
									} else if (data.data == 5) {
										alert("该运单已取消，无法打印！");
									} else if (data.data == 6) {
										alert("该运单下单失败，无法打印！");
									} else if (data.data == 7) {
										alert("该运单正在下单，无法打印！");
									} else if (data.data == 8) {
										alert("该运单已发运，无法打印！");
									} else if (data.data == 9) {
										alert("该运单已签收，无法打印！");
									} else if (data.data == 10) {
										alert("该运单签收失败，无法打印！");
									} else if (data.data == 11) {
										alert("该运单已退回，无法打印！");
									}
									//find();
								});
			}
		} else {
			alert("请选择一行!");
		}
	}

	function print() {
		if ($("input[name='ckb']:checked").length == 1) {
			var result = confirm('是否打印！');
			if (result) {
				var ids = $("input[name='ckb']:checked");
				var idsStr = '';
				$.each(ids, function(index, item) {
					idsStr = this.value;
				});
				$
						.post(
								'${root}/control/orderPlatform/waybillMasterController/printOrder.do?ids='
										+ idsStr,
								function(data) {
									if (data.data == 1) {
										if (data.code == 4) {
											var result1 = confirm('该运单已打印，是否打印！');
											if (result1) {
												// window.open('${root }/control/orderPlatform/waybillMasterController/print.do?ids='+idsStr,'_blank','height=680,width=400,status=yes,toolbar=yes,fullscreen=no, menubar=no,location=no');
												$
														.post(
																'${root}/control/orderPlatform/SFPrintController/printBaozunWaybilMaster.do?ids='
																		+ idsStr,
																function(data) {
																	if (data.out_result == 1) {
																		window
																				.open(root
																						+ data.path);
																	} else if (data.out_result == 2) {
																		alert("操作失败!");
																	}
																});
											}
										} else {
											//window.open('${root }/control/orderPlatform/waybillMasterController/print.do?ids='+idsStr,'_blank','height=680,width=400,status=yes,toolbar=yes,fullscreen=no, menubar=no,location=no');
											$
													.post(
															'${root}/control/orderPlatform/SFPrintController/printBaozunWaybilMaster.do?ids='
																	+ idsStr,
															function(data) {
																if (data.out_result == 1) {
																	window
																			.open(root
																					+ data.path);
																} else if (data.out_result == 2) {
																	alert("操作失败!");
																}
															});
										}

									} else if (data.data == 2) {
										alert("操作失败！");
										alert(data.msg);
									} else if (data.data == 0) {
										alert("尚未下单，无法打印！");
									} else if (data.data == 3) {
										alert("该运单无需打印！");
									} else if (data.data == 5) {
										alert("该运单已取消，无法打印！");
									} else if (data.data == 6) {
										alert("该运单下单失败，无法打印！");
									} else if (data.data == 7) {
										alert("该运单正在下单，无法打印！");
									} else if (data.data == 8) {
										alert("该运单已发运，无法打印！");
									} else if (data.data == 9) {
										alert("该运单已签收，无法打印！");
									} else if (data.data == 10) {
										alert("该运单签收失败，无法打印！");
									} else if (data.data == 11) {
										alert("该运单已退回，无法打印！");
									}
									//find();
								});
			}
		} else {
			alert("请选择一行!");
		}
	}
</script>

</html>
<style>
.divclass {
	border: 5px solid #E0EEEE
}
/**.m-list{
	display:none!important;
}*/
.select {
	padding: 3px 4px;
	height: 30px;
	width: 160px;
	text-align: enter;
}

.table_head_line td {
	font-weight: bold;
	font-size: 16px
}

.m-input-select {
	display: inline-block;
	position: relative;
	-webkit-user-select: none;
	width: 160px;
}
</style>
