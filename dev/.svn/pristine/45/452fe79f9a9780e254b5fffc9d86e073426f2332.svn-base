<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>

<title>工单平台-店铺</title>

<%@ include file="/templet/common.jsp" %>
<%@ taglib prefix="func" uri="/WEB-INF/tld/func.tld" %>
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>work_order/wo_platform_store/css/wo_platform_store.css" />
<script type="text/javascript" src="<%=basePath %>work_order/wo_platform_store/js/main.js"></script>
<script type="text/javascript">

$(document).ready(function() { 
	cancelLoadingStyle();
	if($("#userType").val()==1){
  		//$("#add_btn").hide();
  		$("#finish_btn").hide();
  		$("#cancle_btn").hide();
  		$("#relay_btn").hide();
      }else if($("#userType").val()==0){
    	  $("#myDepartment").hide();
    	  $("#12_div").hide();
    	  $("#relay_btn").hide();
      }else{
    	  $("#finish_btn").hide();
    	  $("#cancle_btn").hide();
    	  $("#add_btn").hide(); 
    	  $("#relay_btn").hide();
    	  $("#myGroup").hide();
    	  $("#12_div").hide();
    	  $("#myDepartment").hide();
      }
	if($("#tabNo").val()==""){
		$("#1_div").addClass("orange");
	}else{
		var strNode="#"+$("#tabNo").val()+"_div";
		$(strNode).addClass("orange");
	}
	if($("#tabNo").val()>4&&$("#tabNo").val()!=9&&$("#ifGroupShare").val()!=1){
		$("#relay_btn").hide();
	}
	if($("#userType").val()==1&&($("#tabNo").val()==10||$("#tabNo").val()==11)){
		$("#obtain_btn").show();
	}else{
		$("#obtain_btn").hide();
	}
	
	if($("#tabNo").val()==2){
		$("#relay_btn").show();
		$("#finish_btn").show();
		$("#cancle_btn").show();
	} 
	if($("#tabNo").val()==2||$("#tabNo").val()==6){
		$("#batch_reply_btn").show();
	}
	
})
//增量提醒
//var timer_times = setInterval(countAllTab,60000);
//我未处理
var div_2 = -1;
//组未分配
var div_6 = -1;
//组未处理
var div_10 = -1;
function countAllTab() {
	if(!$("#timer_close_flag")||$("#timer_close_flag").val()!="OK"){
		clearInterval(timer_times);
	}
	   $.ajax({
	       type: "POST",
	       url: "/BT-LMIS/control/workOrderPlatformStoreController/countAllTab.do",
	       dataType: "json",
	       data: {},
	       success: function(result) {
	          if(result.div_2>div_2&&div_2!=-1){
	        	  var aa=$("#2_div").text().split("+");
	        	  if(aa[1]==NaN||aa[1]==undefined){
	        		  aa[1]=0; 
	        	  }
	        	  var n = result.div_2-div_2+Number(aa[1]);
	        	  $("#2_div").text(aa[0]+"+"+n);
	          }
	          div_2=result.div_2;
	          if(result.div_6>div_6&&div_6!=-1){
	        	  var aa=$("#6_div").text().split("+");
	        	  if(aa[1]==NaN||aa[1]==undefined){
	        		  aa[1]=0; 
	        	  }
	        	  var n = result.div_6-div_6+Number(aa[1]);
	        	  $("#6_div").text(aa[0]+"+"+n);
	          }
	          div_6=result.div_6;
	          if(result.div_10>div_10&&div_10!=-1){
	        	  var aa=$("#10_div").text().split("+");
	        	  if(aa[1]==NaN||aa[1]==undefined){
	        		  aa[1]=0; 
	        	  }
	        	  var n = result.div_10-div_10+Number(aa[1]);
	        	  $("#10_div").text(aa[0]+"+"+n);
	          }
	          div_10=result.div_10;
	       },
	       error: function() {
	         // alert("系统异常-error");
	       }
	    });
}
</script>
<style type="text/css">
	#div_main {
		width: 100%;
	}
	#div_left {
		float: left;
		width: 12%;
		/* border: medium solid #0000CC; */
	}
	#div_right {
		float: left;
		width: 87%;
		/* border: medium solid #0000CC; */
	}
</style>
<div id="div_main">
	<div class="col-md-1" id="div_left">
		<div class="row form-group">
			<ul class="ul-common">
				<li>
					<h4 class="blue page-header ul-header">我的</h4>
					<ul>
						<li><h5><a id="1_div" class="pointer text-inline" onclick="shiftPlatform($(this), 1);">我创建的(${countMap.iCreate})</a></h5></li>
						<li><h5><a id="2_div" class="pointer text-inline" onclick="shiftPlatform($(this), 2);">未处理(${countMap.iNotProcessed})</a></h5></li>
						<li><h5><a id="3_div" class="pointer text-inline" onclick="shiftPlatform($(this), 3);">已处理(${countMap.iProcessed})</a></h5></li>
						<li><h5><a id="4_div" class="pointer text-inline" onclick="shiftPlatform($(this), 4);">已完结(${countMap.iEnd})</a></h5></li>
						<li><h5><a id="9_div" class="pointer text-inline" onclick="shiftPlatform($(this), 9);">跟进中(${countMap.iFollowUp})</a></h5></li>
					</ul>
				</li>
			</ul>
		</div>
		<div class="row form-group" id="myGroup">
			<ul class="ul-common">
				<li>
					<h4 class="blue page-header ul-header">我的组</h4>
					<ul>
						<li><h5><a id="5_div" class="pointer text-inline" onclick="shiftPlatform($(this), 5);">组创建的(${countMap.groupCreate})</a></h5></li>
						<li><h5><a id="6_div" class="pointer text-inline" onclick="shiftPlatform($(this), 6);">组未处理(${countMap.groupNotProcessed})</a></h5></li>
						<li><h5><a id="7_div" class="pointer text-inline" onclick="shiftPlatform($(this), 7);">组已处理(${countMap.groupProcessed})</a></h5></li>
						<li><h5><a id="8_div" class="pointer text-inline" onclick="shiftPlatform($(this), 8);">组已完结(${countMap.groupEnd})</a></h5></li>
						<li><h5><a id="12_div" class="pointer text-inline" onclick="shiftPlatform($(this), 12);">组所有的(${countMap.groupAllCount})</a></h5></li>
					</ul>
				</li>
			</ul>
		</div>
		<div class="row form-group" id="myDepartment">
			<ul class="ul-common">
				<li>
					<h4 class="blue page-header ul-header">我的部门</h4>
					<ul>
						<li><h5><a id="11_div" class="pointer text-inline" onclick="shiftPlatform($(this), 11);">部门所有的(${countMap.allCount})</a></h5></li>
						<li><h5><a id="10_div" class="pointer text-inline" onclick="shiftPlatform($(this), 10);">部门未分配(${countMap.unallocated})</a></h5></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
	<div class="col-md-11 platform" id="div_right">
		<div class="row">
			<div class="col-xs-12">
				<div class="row">
					<div class="col-xs-12">
						<div class="search-toolbar">
							<div class="widget-box no-margin">
								<div class="widget-header widget-header-small">
									<h5 class="widget-title lighter">查询栏</h5>
									<a class="pointer" title="初始化" onclick="refresh()"><i class="fa fa-refresh"></i></a>
								</div>
								<div class="widget-body">
									<div class="widget-main no-padding-bottom">
										<input id="userType" name="userType" type="text" value="${userType}" hidden="true">
										<input id="timer_close_flag" name="timer_close_flag" type="text" value="OK" hidden="true">
										<input id="tabNo" name="tabNo" type="text" value='${paraMap.tabNo}' hidden="true">
										<input id="ifGroupShare" name="ifGroupShare" type="text" value='${ifGroupShare}' hidden="true">
										<form id="wk_form" name="wk_form" class="container search_form">
											<div class="row form-group">
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="wo_no">工单号：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input class="form-control" id="wo_no" name="wo_no" type="text" value="${paraMap.wo_no}">
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="waybill">快递单号：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input class="form-control" id="waybill" name="waybill" type="text" value="${paraMap.waybill}">
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="platform_number">平台订单号：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input class="form-control" id="platform_number" name="platform_number" type="text" value="${paraMap.platform_number}">
													</div>
												</div>
											</div>
											<div class="senior-search"> 
											<div class="row form-group">
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="wo_status_display">工单状态：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<select id="wo_status_display" name="wo_status_display" data-edit-select="1">
															<option value="">---请选择---</option>
															<option value="0" ${"0"==paraMap.wo_status_display?"selected='selected'":"" }>未处理</option>
															<option value="1" ${"1"==paraMap.wo_status_display?"selected='selected'":"" }>已处理</option>
															<option value="2" ${"2"==paraMap.wo_status_display?"selected='selected'":"" }>已完结</option>
															<option value="3" ${"3"==paraMap.wo_status_display?"selected='selected'":"" }>已取消</option>
												 		</select>
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="current_processor_group_display">当前处理组：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input class="form-control" id="current_processor_group_display" name="current_processor_group_display" type="text" value="${paraMap.current_processor_group_display}">
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="current_processor_display">当前处理人：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input class="form-control" id="current_processor_display" name="current_processor_display" type="text" value="${paraMap.current_processor_display}">
													</div>
												</div>
											</div>
											<div class="row form-group">
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue">处理部门：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<select id="process_department" name="process_department" data-edit-select="1" onchange="shiftProcessDepartment('#process_department',{'woTypeSelector': '#wo_type_display', 'woTypeStatus': '1', 'checkFlag': '0' });">
															<option value="">---请选择---</option>
															<option value="0" ${"0"==paraMap.process_department?"selected='selected'":"" }>物流部</option>
															<option value="1" ${"1"==paraMap.process_department?"selected='selected'":"" }>销售运营部</option>
							                        	</select>
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="wo_type_display">工单类型：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<select id="wo_type_display" name="wo_type_display" data-edit-select="1" onchange= "shiftWoType('#wo_type_display', {'errorTypeSelector': '#error_type_display' });">
															<option value="">---请选择---</option>
															<c:forEach var="list" items="${woType}">
																<option value="${list.code}" ${list.code==paraMap.wo_type_display?"selected='selected'":"" }>${list.name}</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue">工单子类型：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<select id="error_type_display" name="error_type_display" data-edit-select="1">
															<option value="">---请选择---</option>
															<c:forEach var="errorType" items="${errorType }">
																<option value="${errorType.id}" ${errorType.id ==  paraMap.error_type_display ? "selected = 'selected'" : "" }>${errorType.type_name }</option>
															</c:forEach>
							                        	</select>
													</div>
												</div>
											</div>
											<div class="row form-group">
							                    <div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="title">标题：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input class="form-control" id="title" name="title" type="text" value="${paraMap.title}">
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="create_time">创建时间：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding input-group">
														<input id="create_time" name="create_time" class="form-control data-range" type="text" value="${paraMap.create_time}" readonly />
														<span class="input-group-addon">
															<i class="icon-calendar bigger-110"></i>
														</span>
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="last_process_time">最新处理时间：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding input-group">
														<input id="last_process_time" name="last_process_time" class="form-control data-range" type="text" value="${paraMap.last_process_time}" readonly />
														<span class="input-group-addon">
															<i class="icon-calendar bigger-110"></i>
														</span>
													</div>
												</div>
											</div>
											<div class="row form-group">
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue">跟进状态：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<select id="follow_up_flag" name="follow_up_flag" data-edit-select="1">
															<option value="">---请选择---</option>
															<option value="0" ${"0"==paraMap.follow_up_flag?"selected='selected'":"" }>未跟进</option>
															<option value="1" ${"1"==paraMap.follow_up_flag?"selected='selected'":"" }>跟进中</option>
							                        	</select>
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="owner_display">负责人：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input class="form-control" id="owner_display" name="owner_display" type="text" value="${paraMap.owner_display}">
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="owner_group_display">负责组：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input class="form-control" id="owner_group_display" name="owner_group_display" type="text" value="${paraMap.owner_group_display}">
													</div>
												</div>
											</div>
											<div class="row form-group">
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="create_by_display">创建人：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input class="form-control" id="create_by_display" name="create_by_display" type="text" value="${paraMap.create_by_display}">
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="create_by_group_display">创建组：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input class="form-control" id="create_by_group_display" name="create_by_group_display" type="text" value="${paraMap.create_by_group_display}">
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="related_number">相关单据号：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input class="form-control" id="related_number" name="related_number" type="text" value="${paraMap.related_number}">
													</div>
												</div>
											</div>
											<div class="row form-group">
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue">操作系统：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<select id="operation_system" name="operation_system" data-edit-select="1">
															<option value="">---请选择---</option>
															<option value="OMS" ${"OMS"==paraMap.operation_system?"selected='selected'":"" }>OMS</option>
															<option value="PACS" ${"PACS"==paraMap.operation_system?"selected='selected'":"" }>PACS</option>
															<option value="WMS" ${"WMS"==paraMap.operation_system?"selected='selected'":"" }>WMS</option>
															<option value="BI" ${"BI"==paraMap.operation_system?"selected='selected'":"" }>BI</option>
															<option value="码栈" ${"码栈"==paraMap.operation_system?"selected='selected'":"" }>码栈</option>
															<option value="非系统问题" ${"非系统问题"==paraMap.operation_system?"selected='selected'":"" }>非系统问题</option>
							                        	</select>
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue">问题所属店铺：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<select id="problem_store_display" name="problem_store_display" data-edit-select="1">
															<option value="">---请选择---</option>
															<c:forEach var="store" items="${storeList}">
															<option value="${store.store_code}" ${store.store_code==paraMap.problem_store_display?"selected='selected'":"" }
																>${store.store_name}</option>
															</c:forEach>
							                        	</select>
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue">是否处理中：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<select id="isProcing" name="isProcing" data-edit-select="1">
															<option value="">---请选择---</option>
															<option value="1" ${"1"==paraMap.isProcing?"selected='selected'":"" }>是-（未处理，已处理）</option>
															<option value="0" ${"0"==paraMap.isProcing?"selected='selected'":"" }>否-（已完结，已取消）</option>
							                        	</select>
													</div>
												</div>
											</div>
											<div class="row form-group">
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue">问题描述：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input class="form-control" id="issue_description" name="issue_description" type="text" value="${paraMap.issue_description}">
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
											<button type="button" class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="tablesearch('', '');">
												<i class="ace-icon fa fa-search grey">
													查询
												</i>
											</button>
											<button type="button" class="btn btn-sm btn-white btn-success btn-bold btn-round btn-width" onclick="details();">
												<i class="ace-icon fa fa-folder-open-o green">
													详情
												</i>
											</button>
											<button id="add_btn"  type="button" class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width" onclick="toAdd();">
												<i class="ace-icon fa fa-plus-circle blue">
													新建
												</i>
											</button>
											<button id="relay_btn" type="button" class="btn btn-sm btn-white btn-warning btn-bold btn-round btn-width" ${userType==1?"onclick='loadTeam();'":"onclick='loadUsers();'"}>
												<i class="ace-icon fa fa-pencil-square-o yellow">
													转发
												</i>
											</button>
											<button id="finish_btn" type="button" class="btn btn-sm btn-white btn-danger btn-bold btn-round btn-width" onclick="finishMain();">
												<i class="ace-icon fa fa-trash-o red">
													完结
												</i>
											</button>
											<button id="cancle_btn" type="button" class="btn btn-sm btn-white btn-danger btn-bold btn-round btn-width" onclick="cancleMain();">
												<i class="ace-icon fa fa-trash red">
													取消
												</i>
											</button>
											<button type="button" class="btn btn-sm btn-white btn-primary btn-bold btn-round btn-width" style="display: none" id="obtain_btn" onclick="empGetWorkOrder();">
												<i class="ace-icon fa fa-hand-lizard-o blue">
													获取
												</i>
											</button>
											<button type="button" class="btn btn-sm btn-white btn-success btn-bold btn-round btn-width" onclick="exportWorkOrder();">
												<i class="ace-icon fa fa-download green">
													导出
												</i>
											</button>
											<button type="button" class="btn btn-sm btn-white btn-success btn-bold btn-round btn-width" style="display: none" id="batch_reply_btn" onclick="javascript:selectUploadModel();">
												<i class="ace-icon fa fa-upload green">
													批量回复
												</i>
											</button>
											<a class="btn btn-sm btn-white btn-success btn-bold btn-round btn-width" href="${func:getNginxDownloadPreFix() }工单指引汇总-20180517.xlsx" target='_blank'>新手引导</a>
											<!-- <button type="button" class="btn btn-sm btn-white btn-success btn-bold btn-round btn-width" id="newer" onclick="showNewer();">
													新手引导
											</button> -->
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
	</div>
</div>
<!-- 转发弹窗 -->
<div id="transmit-model" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" style="width:800px;" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button id="transmit_close" type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title">转发</h4>
            </div>
            <div class="modal-body container">
            	<div class="row" >
            		<div ${userType!=1?"style='display: none;'":""}>
            		<div class="col-md-2 search-title" >
						<label class="blue" for="SOgroup">销售运营组别列表&nbsp;:</label>
					</div>
                    <div class="col-md-4">
                    	<div class="col-md-11 no-padding">
	                   		<select id="SOgroup" name="SOgroup" style="width:100%;" onchange="loadSOUser();">
	                   			<option value="">没有可选的组别</option>
	                   		</select>
                   		</div>
                    </div>
                    </div>
            		<div class="col-md-2 search-title" align="center">
						<label class="blue" for="employee">人员列表&nbsp;:</label>
					</div>
                    <div class="col-md-4">
                    	<div class="col-md-11 no-padding">
	                   		<select id="employee" name="employee" style="width:100%;">
	                   			<option value="">没有可选的人员</option>
	                   		</select>
                   		</div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
            	<button type="button" class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="transmitMain();" >
					<i class="ace-icon fa fa-mail-reply grey">
						转发
					</i>
				</button>
            </div>
        </div>
    </div>
</div>
<div id="newer-model" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
    <div id="newer_width" class="modal-dialog modal-lg" style="width:100%;" role="document">
        <div class="modal-content">
            <div class="modal-header">
                
                <button id="newer_close" type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                
                <!-- <button id="newer_big" class="close" type="button"><span onclick="bigerPic()" class="blue">放大</span></button>
                <button id="newer_small" class="close" type="button"><span onclick="smallPic()" class="green">缩小</span></button> -->
                <h4 class="modal-title">新手引导</h4>
            </div>
            <div style="height:550px">
            	<iframe id="newer_iframe" src="${root }control/workOrderPlatformStoreController/toNewer.do" style="width:100%;height:100%;overflow: visible;"></iframe>
            </div>
        </div>
    </div>
</div>
<div id= "batch_store" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
	<div class= "modal-dialog modal-lg" role= "document" >
		<div class= "modal-content" style= "border: 3px solid #394557;" >
			<div class= "modal-header" >
				<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
				<h4 id= "formLabel" class= "modal-title" >工单批量回复上传</h4>
			</div>
			<div class= "modal-body" >
			<table>
				<tr align="center">
	 				<td width="15%" align="center">
						<button class="btn btn-xs btn-primary" onclick="$('input[id=file1]').click();">
							<i class="icon-edit"></i>选择文件
						</button>
					</td>
		  			<td width="30%" align="center" >
						<input id="file1" type="file" name='file' style="display:none;">  
						<input id="photoCover" class="input-large" type="text" style="height:30px;width: 100%;" readonly="readonly">  
	  				</td>
	  				<td align="center" width="10%">
						<button id="imps" name="imps" class="btn btn-xs btn-yellow" onclick="imp();">
							<i class="icon-hdd"></i>导入
						</button>
					</td>		
				</tr>
			</table>
				
			</div>
			<div class= "modal-footer" >
			</div>
		</div>
	</div>
</div>