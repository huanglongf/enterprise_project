<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>

<title>页面模板-查询界面</title>

<%@ include file="../templet/common.jsp" %>

<script type="text/javascript" src="<%=basePath %>templet/js/templet-test.js"></script>

<div class="row">
	<div class="col-xs-12">
		<div class="row">
			<div class="col-xs-12">
				<div class="search-toolbar">
					<div class="widget-box no-margin">
						<div class="widget-header widget-header-small">
							<h5 class="widget-title lighter">查询栏</h5>
							<a class="pointer" title="初始化" onclick=""><i class="fa fa-refresh"></i></a>
						</div>
						<div class="widget-body">
							<div class="widget-main no-padding-bottom">
								<form class="container search_form">
									<div class="row form-group">
										<div class="col-md-1 no-padding text-center search-title">
											<label class="blue" for="form-field-1">默认字段一：</label>
										</div>
										<div class="col-md-3 no-padding">
											<div class="col-md-11 no-padding">
												<input class="form-control" id="form-field-1" name="form-field-1" type="text">
											</div>
										</div>
										<div class="col-md-1 no-padding text-center search-title">
											<label class="blue" for="form-field-2">默认字段二：</label>
										</div>
										<div class="col-md-3 no-padding">
											<div class="col-md-11 no-padding">
												<select id="form-field-2" name="form-field-2" data-edit-select="1">
													<option value="">请选择</option>
													<option value="A">A</option>
													<option value="B">B</option>
													<option value="C">C</option>
													<option value="D">D</option>
												</select>
											</div>
										</div>
										<div class="col-md-1 no-padding text-center search-title">
											<label class="blue" for="form-field-3">默认字段三：</label>
										</div>
										<div class="col-md-3 no-padding">
											<div class="col-md-11 no-padding input-group">
												<input id="form-field-3" name="form-field-3" class="form-control data-range" type="text" readonly />
												<span class="input-group-addon">
													<i class="icon-calendar bigger-110"></i>
												</span>
											</div>
										</div>
									</div>
									<div class="senior-search">
										<div class="row form-group">
											<div class="col-md-1 no-padding text-center search-title">
												<label class="blue" for="form-field-4">默认字段四：</label>
											</div>
											<div class="col-md-3 no-padding">
												<div class="col-md-11 no-padding input-group">
													<input id="form-field-4" name="form-field-4" class="form-control date-picker" type="text" readonly />
													<span class="input-group-addon">
														<i class="icon-calendar bigger-110"></i>
													</span>
												</div>
											</div>
											<div class="col-md-1 no-padding text-center search-title">
												<label class="blue" for="form-field-5">默认字段五：</label>
											</div>
											<div class="col-md-3 no-padding">
												<div class="col-md-11 no-padding">
													<select id="form-field-5" name="form-field-5" class="multiselect" multiple="">
														<option value="number1">选项一</option>
														<option value="number2">选项二</option>
														<option value="number3">选项三</option>
														<option value="number4">选项四</option>
														<option value="number5">选项五</option>
													</select>
												</div>
											</div>
											<div class="col-md-1 no-padding text-center search-title">
												<label class="blue" for="form-field-6">默认字段六：</label>
											</div>
											<div class="col-md-3 no-padding">
												<div class="col-md-11 no-padding">
													<select id="form-field-6" name="form-field-6" data-edit-select="1">
														<option value="">请选择</option>
														<option value="E">E</option>
														<option value="F">F</option>
														<option value="G">G</option>
														<option value="H">H</option>
													</select>
												</div>
											</div>
										</div>
										<div class="row time-picker">
											<div class="col-md-1 no-padding text-center search-title">
												<label class="blue" for="form-field-7">默认字段七：</label>
											</div>
											<div class="col-md-3 no-padding">
												<div class="col-xs-11 no-padding input-group">
													<input id="form-field-7" name="form-field-7" class="form-control data-range" type="text" readonly />
													<span class="input-group-addon">
														<i class="icon-calendar bigger-110"></i>
													</span>
												</div>
											</div>
											<div class="col-md-1 no-padding text-center search-title">
												<label class="blue" for="form-field-8">默认字段八：</label>
											</div>
											<div class="col-md-3 no-padding">
												<div class="col-md-11 no-padding">
													<div class="col-xs-12 input-group no-padding">
														<input id="form-field-8" name="form-field-8" class="form-control date-picker" type="text" readonly />
														<span class="input-group-addon">
															<i class="icon-calendar bigger-110"></i>
														</span>
													</div>
												</div>
											</div>
											<div class="col-md-1 no-padding text-center search-title">
												<label class="blue" for="form-field-9">默认字段九：</label>
											</div>
											<div class="col-md-3 no-padding">
												<div class="col-md-11 no-padding">
													<input id="form-field-9" name="form-field-9" style="width:100%;height:34px;" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
												</div>
											</div>
										</div>
									</div>
									<div class="row text-center">
										<a class="senior-search-shift pointer" title="高级查询"><i class="fa fa-angle-double-down fa-2x" aria-hidden="true"></i></a>
									</div>
								</form>
							</div>
						</div>
					</div>
					<div class="dataTables_wrapper form-inline">
						<div class="row">
							<div class="col-md-12">
								<div class="dataTables_length">
									<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="tablesearch('', '');">
										<i class="ace-icon fa fa-search grey bigger-120">
											查询
										</i>
									</button>
									<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width">
										<i class="ace-icon fa fa-plus-circle blue bigger-120">
											新增
										</i>
									</button>
									<button class="btn btn-sm btn-white btn-warning btn-bold btn-round btn-width">
										<i class="ace-icon fa fa-pencil-square-o yellow bigger-120">
											编辑
										</i>
									</button>
									<button class="btn btn-sm btn-white btn-danger btn-bold btn-round btn-width">
										<i class="ace-icon fa fa-trash-o red bigger-120">
											删除
										</i>
									</button>
									<button class="btn btn-sm btn-white btn-success btn-bold btn-round btn-width">
										<i class="ace-icon fa fa-unlock green bigger-120">
											启用
										</i>
									</button>
									<button class="btn btn-sm btn-white btn-warning btn-bold btn-round btn-width">
										<i class="ace-icon fa fa-lock orange bigger-120">
											禁用
										</i>
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="search-table">
					<jsp:include page="/templet/table.jsp" flush= "true" />
				</div>
			</div>
		</div>
	</div>
</div>