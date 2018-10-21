$(function(){
	$('#uploadFile').ace_file_input({
		style:'well',
		btn_choose:'*请选择需要上传的原始数据EXCEL*',
		btn_change:null,
		no_icon:'icon-cloud-upload',
		droppable:true,
		thumbnail:'small'//large | fit
		//,icon_remove:null//set null, to hide remove/reset button
		, before_change:function(files, dropped) {
			if(files != null) {
				// 获取文件
				var fileName = files[0].name;
				// 获取文件名后缀
				var suffix = fileName.substr(fileName.lastIndexOf("."));
				// 判断后缀是否合法
				if(suffix != ".xls" && suffix != ".xlsx" && suffix != ".csv") {
					alert("请选择EXCEL文件！");
					return false;
				}
			}
			//Check an example below
			//or examples/file-upload.html
			return true;
		}
		, before_remove:function() {
			if(!confirm("是否移除此文件？")){
				return false;
			}
			return true;
		}
		, preview_error:function(filename, error_code) {
			//name of the file that failed
			//error_code values
			//1 = 'FILE_LOAD_FAILED',
			//2 = 'IMAGE_LOAD_FAILED',
			//3 = 'THUMBNAIL_FAILED'
			//alert(error_code);
		}
	}).on('change', function(){
		//console.log($(this).data('ace_input_files'));
		//console.log($(this).data('ace_input_method'));
	});
	
	/*$(".m-input").on("focus", function(){
		$("#fileDiv").hide();
	});*/
});

function load(type, batId){
	if(!confirm("是否加载所选数据？")){
		return false;
	} else {
		$.ajax({
			url : root + "/control/rawDataController/invoke.do",
			type : "post",
			data : {
				"type" : type,
				"batId" : batId
			},
			dataType : "json",
			success : function(result) {
				alert(result.result_content);
				openIdDiv("rawDataList", "/BT-LMIS/control/rawDataController/tolist.do?batId=" 
						+ $("#batId_param").val()
						+ "&type="
						+ $("#type_param").val()
						);
			},
			error : function(result) {
				alert(result.result_content);
			}
		});
	}
}

function del(type, batId){
	if(!confirm("是否删除所选数据？")){
		return false;
	} else {
		$.ajax({
			url : root + "/control/rawDataController/del.do",
			type : "post",
			data : {
				"type" : type,
				"batId" : batId
			},
			dataType : "json",
			success : function(result) {
				alert(result.result_content);
				if(result.result_code == "SUCCESS"){
					openIdDiv("rawDataList", "/BT-LMIS/control/rawDataController/tolist.do?batId=" 
							+ $("#batId_param").val()
							+ "&type="
							+ $("#type_param").val()
							);
				}
			},
			error : function(result) {
				alert(result.result_content);
			}
		});
	}
}

function upload(){
	if($("#rawDataType").val() == ""){
		alert("请选择上传原始数据类型！");
	} else if($("#uploadFile").val() == ""){
		alert("请选择上传文件！");
	} else {
		var rawDataType = $('#rawDataType').val();
		if(rawDataType == "Order"){
			if(!confirm("是否已上传订单明细数据？")){
				return false;
			}
		}
		loadingStyle();
		$.ajaxFileUpload({
			//用于文件上传的服务器端请求地址
			url: '/BT-LMIS/control/rawDataController/upload.do',
			// 当要提交自定义参数时，这个参数要设置成post
			type: 'post',
			// 自定义参数。这个东西比较有用，当有数据是与上传的图片相关的时候，这个东西就要用到了。
			data: {rawDataType: rawDataType},
			// 是否需要安全协议，一般设置为false
			secureuri: false, 
			//文件上传域的ID
			fileElementId: 'uploadFile',
			//返回值类型 一般设置为json
			dataType: 'json',
			//服务器成功响应处理函数
			success: function (result, status){
				if(result.result_code=="FIE"){
					alert("未检测到文件！");
				} else if(result.result_code=="ULF"){
					alert("文件上传失败！原因：" + result.result_content);
				}else{
					alert("文件上传成功！成功导入" + result.success_records + "条原始数据！");
	            }
				cancelLoadingStyle();
				openDiv("/BT-LMIS/control/rawDataController/toForm.do");
			},
			//服务器响应失败处理函数
			error: function (data, status, e){
				alert("系统错误请联系管理员！" + e);
				cancelLoadingStyle();
				openDiv("/BT-LMIS/control/rawDataController/toForm.do");
			}
		})
	}
}