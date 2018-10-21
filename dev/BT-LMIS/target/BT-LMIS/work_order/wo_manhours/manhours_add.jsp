<%@ page language= "java" import= "java.util.*" pageEncoding= "utf-8" %>
<!DOCTYPE html>
<html>
	<head lang= "en" >
		<meta charset= "UTF-8" >
		<%@ include file= "/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name= "description" content= "overview &amp; stats" />
		<meta name= "viewport" content= "width=device-width, initial-scale=1.0" />
		<link rel= "stylesheet" type= "text/css" media= "all" href= "<%=basePath %>css/table.css" />
		<link rel= "stylesheet" type= "text/css" media= "all" href= "<%=basePath %>daterangepicker/font-awesome.min.css" />
		<link rel= "stylesheet" type= "text/css" media= "all" href= "<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
		<link rel= "stylesheet" type= "text/css" media= "all" href= "<%=basePath %>assets/css/datepicker.css" />
		
		<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
	    <script type= "text/javascript" src= "<%=basePath %>daterangepicker/moment.js" ></script>
	    <script type= "text/javascript" src= "<%=basePath %>assets/js/date-time/bootstrap-datepicker.min.js" ></script>
	    <script type= "text/javascript" src= "<%=basePath %>assets/js/date-time/locales/bootstrap-datepicker.zh-CN.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>daterangepicker/daterangepicker.js" ></script>
	    <script type= "text/javascript" src= "<%=basePath %>js/common.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>work_order/wo_manhours/js/manhours_add.js" ></script>
	</head>
	<body>
		<div class= "page-header" >
			<h1>工时新增</h1>
		</div>
		<div class= "container" >
			<div class= "col-xs-12 row form-group" >
				<div class= "col-xs-5" >
					<div class= "widget-box" >
						<div class= "widget-header header-color-blue" >
							<h4>已选账户</h4>
						</div>
						<div class= "widget-body" >
							<div class= "widget-main wysiwyg-editor no-padding-top no-padding-bottom no-padding-left no-padding-right" >
								<div class= "title_div no-margin-top no-margin-bottom" id= "sc_title_2" style= "height: 26px;" >		
									<table class="table table-striped" style= "table-layout: fixed; width: 100%;" >
								   		<thead>
									  		<tr class= "table_head_line no-padding-top no-padding-bottom" >
									  			<td class= "td_ch no-padding-top no-padding-bottom no-padding-left no-padding-right" style= "width: 4%;" ><input type= "checkbox" id= "checkAll2" onclick= "inverseCkb('ckb2')" disabled= "disabled" /></td>
									  			<td class= "td_cs no-padding-top no-padding-bottom no-padding-left no-padding-right" style= "width: 32%;" >组别</td>
									  			<td class= "td_cs no-padding-top no-padding-bottom no-padding-left no-padding-right" style= "width: 32%;" >工号</td>
									  			<td class= "td_cs no-padding-top no-padding-bottom no-padding-left no-padding-right" style= "width: 32%;" >姓名</td>
									  		</tr>
										</thead>
									</table>
								</div>
								<div class= "tree_div" style= "height: 26px; margin-top: -26px;" ></div>
								<div style= "height: 220px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content_2" onscroll= "init_td('sc_title_2', 'sc_content_2')">
									<table id= "table_content_2" class= "table table-hover table-striped" style= "table-layout: fixed;" >
								  		<tbody align= "center" >
								  		</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class= "col-xs-2" style= "text-align: center; height: 300px;" >
					<div style= "margin-top: 100px" >
						<button class= "btn btn-primary btn-sm" onclick= "importSubordinates();" >
							<i class= "icon-angle-left bigger-200" ></i>
							<i class= "icon-angle-left bigger-200" ></i>
							导入
						</button>
					</div>
					<div>
						<button class= "btn btn-danger btn-sm" onclick= "clearSubordinates();" >
							<i class= "icon-angle-right bigger-200" ></i>
							<i class= "icon-angle-right bigger-200" ></i>
							清除
						</button>
					</div>
				</div>
				<div class= "col-xs-5" >
					<div class= "widget-box" >
						<div class= "widget-header header-color-blue" >
							<h4>可选账户</h4>
							<div class= "widget-toolbar" >
								<a href= "#" data-action= "reload" onclick= "refreshSubordinates();" >
									<i class= "icon-refresh bigger-125" ></i>
								</a>
							</div>
						</div>
						<div class= "widget-body" >
							<div id= "subordinates" class= "widget-main wysiwyg-editor no-padding-top no-padding-bottom no-padding-left no-padding-right" >
								<jsp:include page= "/work_order/wo_manhours/employee_list.jsp" flush= "true" />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class= "col-xs-12 row form-group" >
				<div class= "col-xs-6" >
					<label class= "no-padding-right blue">
						<input id= "oneAdd" name= "radio" class= "ace" type= "radio" checked= "checked" />
						<span class= "lbl">单日新增</span>
					</label>
					<label class= "no-padding-right blue">
						<input id= "rangeAdd" name= "radio" class= "ace" type= "radio" />
						<span class= "lbl">时间段新增</span>
					</label>
				</div>
			</div>
			<div id= "oneDate_div" class= "col-xs-12 row form-group input-group" >
				<input id= "oneDate" name= "oneDate" class= "form-control date-picker" type= "text" readonly />
				<span class="input-group-addon">
					<i class="icon-calendar bigger-110"></i>
				</span>
			</div>
			<div id= "rangeDate_div" class= "col-xs-12 row form-group input-group" style= "display: none;" >
				<input id= "rangeDate" name= "rangeDate" class= "form-control" type= "text" readonly />
				<span class="input-group-addon">
					<i class="icon-calendar bigger-110"></i>
				</span>
			</div>
			<div class= "col-xs-12 row form-group" >
				<label class="control-label blue">
					维护工时（分钟/天）&nbsp;:
				</label>
				<input id= "manhours" name= "manhours" type= "text" style= "width: 100%;" />
			</div>
			<div class= "col-xs-12 row form-group form-actions no-margin-bottom no-padding-bottom no-border-bottom" align= "center" >
				<button class= "btn btn-info" type= "button" onclick = "add();">
					<i class= "icon-ok bigger-110" ></i> 提交
				</button>
				&nbsp; &nbsp; &nbsp;
				<button class= "btn" type= "reset" onclick= "openDiv('/BT-LMIS/control/manhoursController/query.do');" >
					<i class= "icon-undo bigger-110" ></i> 返回
				</button>
			</div>
		</div>
	</body>
</html>
