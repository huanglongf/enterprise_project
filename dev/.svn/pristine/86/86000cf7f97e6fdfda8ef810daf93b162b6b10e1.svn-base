<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/lmis/yuriy.jsp"%>
<title>LMIS</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
<script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
<link rel="stylesheet" href="<%=basePath %>validator/css/style.css" type="text/css" media="all" />
<link href="<%=basePath %>validator/css/demo.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>/css/table.css" type="text/css" rel="stylesheet" />
<link type= "text/css" href= "<%=basePath %>daterangepicker/daterangepicker-bs3.css" rel="stylesheet" />
<script type="text/javascript" src="<%=basePath %>validator/js/Validform_v5.3.2_min.js"></script>
<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
        <meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type="text/css" href="<%=basePath %>css/table.css" rel="stylesheet" />
		<link type= "text/css" href= "<%=basePath %>daterangepicker/font-awesome.min.css" rel= "stylesheet" />
		<link type= "text/css" href= "<%=basePath %>daterangepicker/daterangepicker-bs3.css" rel="stylesheet" />
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
        <script type="text/javascript" src="<%=basePath %>js/ajaxfileupload.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>daterangepicker/moment.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>daterangepicker/daterangepicker.js"></script>

       <script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath %>assets/js/jquery-2.0.3.min.js'>" + "<"+"/script>");
			$(document).ready(function() {
				$(".form-control").daterangepicker(null, function(start, end, label) {
	               	console.log(start.toISOString(), end.toISOString(), label);
				});
				
			});
		</script>

<script type="text/javascript">
	$(function() {
		$("#upload").click(function() {
			fileUpload();
		});
		$('input[id=file1]').change(function() {
			$('#photoCover').val($(this).val());
		});
	})

 	function fileUpload() {
 		$.ajaxFileUpload({
 			url : '/BT-LMIS/control/manhoursController/fileUploadHours.do', //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
 			fileElementId: 'file1', //文件上传域的ID
 			dataType: 'json', //返回值类型 一般设置为json
 			//服务器成功响应处理函数
 			success : function(data, status) {
 				if (data.code == 200) {
 					openDiv('/BT-LMIS/control/manhoursController/workingHoursList.do?msg='
 							+ data.msg);
 				}
 			},
 			error : function(data, status, e) {
 				//服务器响应失败处理函数
 				openDiv('/BT-LMIS/control/manhoursController/workingHoursList.do');
 			}
 		})
	    return false;
 	} 
	//清空数据
	function empty() {
		if (!confirm("确定清空吗?")) {
			return;
		}
		       $.ajax({
					type : "POST",
					url : root
							+ "/control/manhoursController/updateWoHourInterim.do?",
					dataType : "text",
					data : "",
					success : function(data) {
						if (data == 'true') {
							openDiv('/BT-LMIS/control/manhoursController/workingHoursList.do');
						} else if (data == 'false') {
							alert("成功!");
						} else {
							alert("失败!");
						}
					}
				});
	}

	function importWoHourInterim() {
		if (!confirm("确定导入吗?")) {
			return;
		}
		     $.ajax({
					type : "POST",
					url : root
							+ "/control/manhoursController/importWoHourInterim.do?",
					dataType : "text",
					data : "",
					success : function(data) {
						if (data == 'true') {
							openDiv('/BT-LMIS/control/manhoursController/workingHoursList.do');
						} else if (data == 'false') {
							alert("成功!");
						} else {
							alert("失败!");
						}
					}
				});
	}

	function addRow() {
		$("#type_Row").modal('show');
		$("#btn_type_text_Row").text("提交");
	}
	function saveWoHourInterim() {
		var to_delete = 0;
		var import_status = $('#import_status').val();
		var error_info = $('#error_info').val();
		var name = $('#name').val();
		var work_number = $('#work_number').val();
		var data_time_new = $('#data_time').val();
		var strs = new Array(); //定义一数组 
		strs = data_time_new.split("-");
		var data_time = strs[0]+"-"+strs[1]+"-"+strs[2];
		var man_hour = $('#man_hour').val();
		if(work_number==''){
			alert("填写账户！");
			return;
		}
		if(name==''){
			alert("填写姓名！");
			return;
		}
		if(data_time.indexOf("undefined") > 0 ){
			alert("填写日期！");
			return;
		}
		if(man_hour==''){
			alert("填写维护工时！");
			return;
		}
		var url = root + "/control/manhoursController/addWoHourInterim.do";
		var data = "import_status=" + import_status + "&error_info="
				+ error_info + "&name=" + name + "&work_number=" + work_number
				+ "&data_time=" + data_time + "&man_hour=" + man_hour
				+ "&to_delete=" + to_delete;
		if ($("#btn_type_text_Row").text() == "确认修改") {
			data = data + "&hourType=2&id=" + $("#id").val();
		} else {
			data = data + "&hourType=1";
		}
		       $.ajax({
					type : "POST",
					url : url,
					data : data,
					dataType : "json",
					success : function(jsonStr) {  
						alert(jsonStr.out_result_reason);
						if (jsonStr.out_result == 1) {
							$("#type_Row").modal("hide");
		            		refresh();
						} 
					}
				});
	}
	function refresh(){
		openDiv('/BT-LMIS/control/manhoursController/workingHoursList.do');
	}
	function inverseCkb(items) {
		$('[name=' + items + ']:checkbox').each(function() {
			this.checked = !this.checked;
		});
	}
	function updateHourInterim() {
		if ($("input[name='ckb']:checked").length == 1) {
			var priv_ids = [];
			$("input[name='ckb']:checked").each(function() {
				id = $(this).val();
			});
			var strs = new Array(); //定义一数组 
			strs = id.split("_");
			var ids = strs[0];
			var start = strs[1];
			var error_info = strs[2];
			var work_number = strs[3];
			var name = strs[4];
			var data_time = strs[5];
			var man_hour = strs[6];

			if ("正确" == start) {
				alert("不可修改！");
				return;
			} else {
				if (!confirm("确定修改吗?")) {
					return;
				}
			}
			$("#type_Row").modal('show');
			$("#btn_type_text_Row").text("确认修改");

			$("#import_status").val(start);
			$("#error_info").val(error_info);
			$("#work_number").val(work_number);
			$("#name").val(name);
			$("#data_time").val(data_time);
			$("#man_hour").val(man_hour);
			$("#id").val(ids);
		} else {
			alert("请选择一行!");
		}
	}
</script>
</head>
<body>
	<div class="page-header" align="left">
		<h1>工时导入列表</h1>
	</div>
	<input type="hidden" name="id" id="id">
	<div style="margin-top: 10px; margin-bottom: 10px; margin-left: 20px;">
		<button class="btn btn-xs btn-pink" onclick="addRow();">
			<i class="icon-search icon-on-right bigger-110"></i>添加行
		</button>
		&nbsp;
		<button class="btn btn-xs btn-yellow"
			onclick="empty();">
			<i class="icon-hdd"></i>清空
		</button> 
		&nbsp;
		<!-- <button id="btn_export" class="btn btn-xs btn-inverse"
			onclick="deleteGroup();">
			<i class="icon-trash"></i>下载模板
		</button> -->
		<button class="btn btn-xs btn-pink" onclick="updateHourInterim();">
			<i class="icon-search icon-on-right bigger-110"></i>修改
		</button>
		&nbsp;
		<button id="btn_export" class="btn btn-xs btn-inverse"
			   onclick="importWoHourInterim();">
			<i class="icon-trash"></i>导入
		</button>
		<input id="file1" type="file" name='file' style='display: none'>
		<input id="photoCover" class="input-large" type="text"
			style="height: 20px; width: 170px;"> <a class="btn"
			onclick="$('input[id=file1]').click();">浏览</a> <a id='upload'
			class="btn" href='javascript:void(0)' onclick="">上传数据</a>
	</div>
	<div class="title_div" id="sc_title">
		<table class="table table-striped" style="table-layout: fixed;">
			<thead>
				<tr class="table_head_line">
					<td class="td_ch"><input type="checkbox" id="checkAll"
						onclick="inverseCkb('ckb')" /></td>
					<td class="td_cs">序号</td>
					<td class="td_cs">导入正确</td>
					<td class="td_cs">错误信息</td>
					<td class="td_cs">工号</td>
					<td class="td_cs">姓名</td>
					<td class="td_cs">日期</td>
					<td class="td_cs">维护工时(min)</td>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tree_div"></div>
	<div style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;"
		id="sc_content" onscroll="init_td('sc_title', 'sc_content')">
		<table id="table_content" class="table table-hover table-striped"
			style="table-layout: fixed;">
			<tbody align="center">
				<c:forEach items="${pageView.records }" var="res" varStatus="status">
					<tr>
						<td class="td_ch"><input id="ckb" name="ckb" type="checkbox" value="${res.id}_${res.import_status}_${res.error_info}_${res.work_number}_${res.name}_${res.data_time}_${res.man_hour}"></td>
						<td class="td_cs" title="">${status.index + 1}</td>
						<td class="td_cs" title="${res.import_status }">${res.import_status }</td>
						<td class="td_cs" title="${res.error_info }">${res.error_info }</td>
						<td class="td_cs" title="${res.work_number }">${res.work_number }</td>
						<td class="td_cs" title="${res.name }">${res.name }</td>
						<td class="td_cs" title="${res.data_time }">${res.data_time }</td>
						<td class="td_cs" title="${res.man_hour }">${res.man_hour }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div style="margin-right: 1%; margin-top: 20px;">${pageView.pageView }</div>
	<div id= "type_Row" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
		<div class= "modal-dialog modal-lg" role= "document" >
			<div class= "modal-content" style= "border: 3px solid #394557;" >
				<div class= "modal-header" >
					<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" >
						<span aria-hidden= "true" >×</span>
					</button>
					<h4 id= "formLabel" class= "modal-title" >工时新增</h4>
				</div>
				<div class= "modal-body" >
					<table align="center">
						<tr align="center">
				  			<td style="width: 20%">导入状态：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						  	<td>
							    <input style="width: 50%" id="import_status" name="import_status"  style="border:0px;background-color:blue"></input> 
						  	</td>
						</tr>
						<tr align="center">
						  <td style="width: 20%">错误信息：&nbsp;&nbsp;&nbsp;&nbsp;</td>
						  <td>
						     <input style="width: 50%" id="error_info" name="error_info" ></input> 
						  </td>
						</tr>
						<tr align="center">
						  <td style="width: 20%">账户：&nbsp;&nbsp;&nbsp;&nbsp;</td>
						  <td>
						     <input style="width: 50%" id="work_number" name="work_number" ></input>
						  </td>
						</tr>
						<tr align="center">
						  <td style="width: 20%">姓名：&nbsp;&nbsp;&nbsp;&nbsp;</td>
						  <td>
						     <input style="width: 50%" id="name" name="name"></input>
						  </td>
						</tr>
						<tr align="center">
						  <td style="width: 20%">日期：&nbsp;&nbsp;&nbsp;&nbsp;</td>
						  <td>
						       <div class="input-prepend input-group" style="width: 240px;">
									<span class="add-on input-group-addon"> <i
										class="glyphicon glyphicon-calendar fa fa-calendar"></i></span> <input
										type="text" readonly style="width: 100%" name="data_time"
										id="data_time" class="form-control"
										value="" />
								</div> 
						  </td>
						</tr>
						<tr align="center">
						  <td style="width: 20%">维护工时(min)：&nbsp;&nbsp;&nbsp;&nbsp;</td>
						  <td>
						     <input style="width: 50%" id="man_hour" name="man_hour"></input>
						  </td>
						</tr>
					</table>
				</div>
				<div class= "modal-footer" >
					<button id= "btn_back" type= "button" class= "btn btn-default" data-dismiss="modal" >
						<i class= "icon-undo" aria-hidden= "true" ></i>返回
					</button>
	     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="saveWoHourInterim();" >
	     				<i class="icon-save" aria-hidden= "true" ></i><span id="btn_type_text_Row">保存</span>
	     			</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
