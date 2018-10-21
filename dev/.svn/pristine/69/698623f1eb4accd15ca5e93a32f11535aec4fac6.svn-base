<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions" %>
<% String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"; %>
<c:set var="root" value="<%=basePath %>"></c:set>

<!-- 模板 -->
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>templet/css/templet.css" />
<!-- 新版ace关于class="btn" -->
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>templet/css/ace-btn.css" />
<!-- 图标 -->
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>font-awesome-4.7.0/css/font-awesome.css" />
<!-- 日期区间 -->
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>daterangepicker/font-awesome.min.css" />
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
<!-- 日期 -->
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>assets/css/datepicker.css" />
<!-- 时间 -->
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>My97DatePicker/skin/WdatePicker.css" />
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>multiple_select/multiple-select.css" />
<!-- bootstrap-table -->
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>bootstrap-table/bootstrap-table.css" />
<!-- 拖拉列宽 -->
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>bootstrap-table/bootstrap-table-fixed-columns.css" />
<!-- 旋转样式 -->
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css" />
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>css/loadingStyle.css" />
<!-- 上传文件控件 -->
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>uploadify/uploadify.css" />
<!-- jquery -->
<script type="text/javascript" src="<%=basePath %>jquery/jquery-2.0.3.min.js"></script>
<!-- jquery-resize -->
<script type="text/javascript" src="<%=basePath %>templet/js/jquery-resize.js"></script>
<!-- 模板 -->
<script type="text/javascript" src="<%=basePath %>templet/js/templet.js"></script>
<!-- 模糊查询 -->
<script type="text/javascript" src="<%=basePath %>templet/js/selectFilter.js"></script>
<!-- 日期区间 -->
<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
<!-- 日期 -->
<script type="text/javascript" src="<%=basePath %>assets/js/date-time/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="<%=basePath %>assets/js/date-time/locales/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script type="text/javascript" src="<%=basePath %>assets/js/jquery.ui.touch-punch.min.js"></script>
<!-- 时间 -->
<script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>multiple_select/multiple-select.js"></script>
<!-- bootstrap -->
<script type="text/javascript" src="<%=basePath %>bootstrap-table/bootstrap.min.js"></script>
<!-- bootstrap-table -->
<script type="text/javascript" src="<%=basePath %>bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=basePath %>bootstrap-table/bootstrap-table-zh-CN.js"></script>
<!-- 拖拉列宽 -->
<script type="text/javascript" src="<%=basePath %>bootstrap-table/bootstrap-table-fixed-columns.js"></script>
<script type="text/javascript" src="<%=basePath %>bootstrap-table/colResizable-1.6.js"></script>
<!--  -->
<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
<!-- 旋转样式 -->
<script type="text/javascript" src="<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js"></script>
<!-- 上传文件控件 -->
<script type="text/javascript" src="<%=basePath %>uploadify/jquery.uploadify.js"></script>
<script type="text/javascript" src="<%=basePath %>uploadify/JQueryUploadHelper.js"></script>
<!-- 粘贴板 -->
<script type="text/javascript" src="<%=basePath %>js/clipboard/clipboard.min.js"></script>
<!-- select多选框控件 -->
<script type="text/javascript" src="<%=basePath %>tool/bootstrap-multiselect/dist/js/bootstrap-multiselect.js"></script>

<script type="text/javascript" src="<%=basePath %>js/ajaxfileupload.js"></script>

<script type="text/javascript">
	//NGINX配置地址
	var FileUpload;
	var FileDown;
	// 总高度控制
	var totalHeight = 0;
	$(function(){
		// 日期区间
		if($(".daterangepicker")) {
			$(".daterangepicker").remove();
		}
		$(".data-range").daterangepicker({
	        applyClass: 'btn-sm btn-success',
	        cancelClass: 'btn-sm btn-default',
	        locale: {
	            applyLabel: '确认',
	            cancelLabel: '取消',
	            fromLabel: '起始时间',
	            toLabel: '结束时间',
	            customRangeLabel: '自定义',
	            firstDay: 1
	        },
	        ranges: {
	            // '最近1小时': [moment().subtract('hours',1), moment()],
	            '今日': [moment().startOf('day'), moment()],
	            '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
	            '最近7日': [moment().subtract('days', 6), moment()],
	            '最近30日': [moment().subtract('days', 29), moment()],
	            '本月': [moment().startOf("month"),moment().endOf("month")],
	            '上个月': [moment().subtract(1,"month").startOf("month"),moment().subtract(1,"month").endOf("month")]
	        },
	        opens: 'left', // 日期选择框的弹出位置
	        separator: ' - ',
	        showWeekNumbers: true, // 是否显示第几周
	        // timePicker: true,
	        // timePickerIncrement: 10, // 时间的增量，单位为分钟
	        // timePicker12Hour: false, // 是否使用12小时制来显示时间
	        // maxDate: moment(), // 最大时间
	        format: 'YYYY-MM-DD'
	    }, function(start, end, label) {
	    	// 格式化日期显示框
	        $('#beginTime').val(start.format('YYYY-MM-DD'));
	        $('#endTime').val(end.format('YYYY-MM-DD'));
	    }).next().on('click', function(){
	        $(this).prev().focus();
	    });
		// 日期
		$(".date-picker").datepicker({
			language: 'zh-CN',
			autoclose: true,
			todayHighlight: true,
			todayBtn: 'linked',
			format: "yyyy-mm-dd"
		}).next().on(ace.click_event, function(){
			$(this).prev().focus();
		});
		// 切换高级查询
		$(".senior-search-shift").click(function(){
			if($(".senior-search-shift i").hasClass("fa-angle-double-down")){
				$(".senior-search").slideDown();
				$(".senior-search-shift i").removeClass("fa-angle-double-down").addClass("fa-angle-double-up");
				
			} else if($(".senior-search-shift i").hasClass("fa-angle-double-up")){
				$(".senior-search").slideUp();
				$(".senior-search-shift i").removeClass("fa-angle-double-up").addClass("fa-angle-double-down");
			}
		})
		// 查询栏收缩
		$(".search-toolbar").resize(function(){
			$("#templet_test_content").height(totalHeight - $(this).height() - 100);
		});
		//
		$(".multiselect").multiselect({
			enableFiltering: true,
			buttonClass: 'btn btn-white btn-primary',
			templates: {
				button: '<button type="button" class="multiselect dropdown-toggle" data-toggle="dropdown"></button>',
				ul: '<ul class="multiselect-container dropdown-menu"></ul>',
				filter: '<li class="multiselect-item filter"><div class="input-group"><span class="input-group-addon"><i class="fa fa-search"></i></span><input class="form-control multiselect-search" type="text"></div></li>',
				filterClearBtn: '<span class="input-group-btn"><button class="btn btn-default btn-white btn-grey multiselect-clear-filter" type="button"><i class="fa fa-times-circle red2"></i></button></span>',
				li: '<li><a href="javascript:void(0);"><label></label></a></li>',
				divider: '<li class="multiselect-item divider"></li>',
				liGroup: '<li class="multiselect-item group"><label class="multiselect-group"></label></li>'
			}
		});
	})
	function hideYScroll(contentSelector, contentWidth) {
		$(contentSelector).parent().width(contentWidth);
		try {
			if($(contentSelector)[0].offsetWidth - $(contentSelector)[0].clientWidth == 0) {
				$(contentSelector).width(contentWidth + 16);
			} else {
				$(contentSelector).width(contentWidth + ($(contentSelector)[0].offsetWidth - $(contentSelector)[0].clientWidth));
			}
		} catch (error) {
			console.log("元素未捕获");
		} finally {}
	}
</script>

<input id="sort_column" type="hidden" />
<input id="sort" type="hidden" />
<!-- 列表配置弹窗 -->
<div id="table-config" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" style="width:800px;" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick= ""><span aria-hidden="true">×</span></button>
                <h4 class="modal-title">列表配置<a class="pointer" title="初始化" onclick="initTableConfig($('#table_name').val());"><i class="fa fa-refresh"></i></a></h4>
            </div>
            <div class="modal-body container">
            	<input id="table_name" type="hidden" />
	      		<div class="row">
					<div class="col-md-5 no-padding">
						<div class="table-border">
							<div class="table-title" id="table_column_title">
								<table class="table table-bordered no-margin">
									<thead>
										<tr>
											<th class="text-center table-text col-sm" style="display:none;">
												<label class="pos-rel">
													<input class="ace" type="checkbox" id="checkAll" onclick="inverseCkb('ckb')" />
													<span class="lbl"></span>
												</label>
											</th>
								  			<th class="text-center table-text col-lg" title="字段名称">
								  				<input id="column_name_search" onkeydown="searchColumn(window.event);" onblur="searchColumn(null)" placeholder="字段名称" />
								  			</th>
										</tr>
									</thead>
								</table>
							</div>
							<div id="table_column_content_parent" class="table-content-parent">
								<div class="table-content-fix" id="table_column_content" onscroll="syntable($('#table_column_title'),$('#table_column_content'))">
									<%@ include file="../templet/table_column.jsp" %>
								</div>
							</div>
						</div>
						<div></div>
					</div>
					<div class="col-md-2 no-padding">
						<div class="text-center" style="margin-top:150px">
							<a class="pointer" onclick="addAllColumn();">
								<i class= "icon-angle-right bigger-200" ></i>
								<i class= "icon-angle-right bigger-200" ></i>
							</a>
						</div>
						<div class="text-center">
							<a class="pointer" onclick="removeAllColumn();">
								<i class="icon-angle-left bigger-200"></i>
								<i class="icon-angle-left bigger-200"></i>
							</a>
						</div>
					</div>
					<div class="col-md-5 no-padding">
						<div class="table-border">
							<div class="table-title" id="table_column_config_title">		
								<table class="table table-bordered no-margin table-fixed">
									<thead>
										<tr>
								  			<th class="text-center table-text col-md" title="显示字段">显示字段</th>
								  			<th class="text-center table-text col-md" title="排序">排序</th>
										</tr>
									</thead>
								</table>
							</div>
							<div id="table_column_config_content_parent" class="table-content-parent">
								<div class="table-content table-content-fix" id="table_column_config_content" onscroll="syntable($('#table_column_config_title'),$('#table_column_config_content'))">
									<%@ include file="../templet/table_column_config.jsp" %>
								</div>
							</div>
						</div>
					</div>
	      		</div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveTableColumnConfig();" >
                    <i class="icon-save" aria-hidden="true"></i>提交
                </button>
            </div>
        </div>
    </div>
</div>