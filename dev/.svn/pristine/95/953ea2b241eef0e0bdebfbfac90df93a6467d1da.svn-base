<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="func" uri="/WEB-INF/tld/func.tld" %>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/templet/common.jsp"%>
<title>LMIS</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link type="text/css" href="<%=basePath%>css/table.css"
	rel="stylesheet" />
</head>
<body>
	<div class="page-header">
		<div style="margin-left: 20px;margin-bottom: 20px;">
			<table>
		  		<tr>
		  			<td width="30%" align="right">
						<select id="templeteSelect" name="templeteSelect" onchange="toChangeUploadUrl(this)">
							<option value="" data-uploadUrl="">
								--请选择--
							</option>
							<c:forEach items="${templeteList }" var="obj">
								<option value="${obj.businessType }" data-uploadUrl="${obj.uploadUrl }" 
									data-templeteName = "${obj.templeteName }">
									${obj.templeteName }
								</option>
							</c:forEach>
						</select> 
					</td>
		  			<td width="10%" align="center">
						<button class="btn btn-xs btn-primary" onclick="$('input[id=file1]').click();">
							<i class="icon-edit"></i>选择文件
						</button>
						<input id="projectCategorySizeId" type="hidden" name="id" value=""> 
					</td>
		  			<td width="20%" align="center" >
		  				<form action='' enctype="multipart/form-data" method="post" id="fileForm">
							<input id="file1" type="file" onchange="fileChange()" name='file' style="display:none;">  
							<input id="photoCover" class="input-large" type="text" style="height:30px;width: 100%;" readonly="readonly">  
						</form>
	  				</td>
	  				<td align="center" width="20%">
						<button id="imps" name="imps" class="btn btn-sm btn-white btn-warn btn-bold btn-round btn-width" 
							onclick="importProduct();">
							<i class="ace-icon fa upload blue bigger-120"></i>导入
						</button>
						<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width"
							onclick="templeteMode()">
							<i class="ace-icon fa download blue bigger-120"></i>下载模版
						</button>
					</td>
					<td align="center" width="5%">
						<input type="hidden" id="uploadUrl" name="uploadUrl" value="" />
						<form id="searchForm" action="">
							<input type="hidden" id="businessType" name="businessType" value="" />
							<input type="hidden" id="businessGroup" name="businessGroup" value="${fileTemplete.businessGroup}" />
						</form>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="margin-top: 10px;margin-left: 10px;margin-bottom: 10px;">
		<button class="btn btn-sm btn-white btn-danger btn-bold btn-round btn-width"
			onclick="del();">
			<i class="ace-icon fa fa-trash-o red bigger-120"> 删除 </i>
		</button>
		<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width"
			onclick="reload();">
			<i class="ace-icon fa refresh blue bigger-120"> 刷新 </i>
		</button>
	</div>
	<div class="table-main">
		<div class="table-border" style="border:0px;">
			<div class="table-content-parent" style="overflow: auto;border:0px;border-left:1px solid #DDD;border-right:1px solid #DDD;min-height:250px;">
				<div class="table-content table-content-free" id="templet_test_content" style="overflow: visible;">
					<table class="table table-striped table-bordered table-hover table-fixed no-margin" style="overflow: visible;">
						<thead class="table-title">
							<tr>
				  				<th class="text-center table-text col-sm">
									<label class="pos-rel">
										<input class="ace" type="checkbox" id="checkAll_templetTest" onclick="inverseCkb('ckb')" />
										<span class="lbl"></span>
									</label>
								</th>
				  				<th class="text-center table-text col-lg" title="创建时间">
				  					创建时间
					  			</th>
				  				<th class="text-center table-text col-lg" title="文件名">
				  					文件名
					  			</th>
				  				<th class="text-center table-text col-lg" title="总行数">
				  					总记录数
					  			</th>
				  				<th class="text-center table-text col-lg" title="总行数">
				  					已成功记录数
					  			</th>
				  				<th class="text-center table-text col-lg" title="状态">
				  					状态
					  			</th>
				  				<th class="text-center table-text col-lg" title="操作">
				  					操作
					  			</th>
					  		</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageView.records }" var="res">
								<tr class="pointer" ondblclick="toSaveForm('${res.id}');">
					  				<td class="text-center table-text col-sm">
										<label class="pos-rel">
											<input class="ace" type="checkbox" id="ckb${res.id}" name="ckb" value="${res.id}" />
											<span class="lbl"></span>
										</label>
									</td>
					  				<td class="text-center table-text col-lg" title="${res.createTime }">
						  				<fmt:formatDate value="${res.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						  			</td>
					  				<td class="text-center table-text col-lg" title="${res.fileName }">
						  				${res.fileName }
						  			</td>
					  				<td class="text-center table-text col-lg" title="${res.totalCount }">
						  				${res.totalCount }
						  			</td>
					  				<td class="text-center table-text col-lg" title="${res.totalCount }">
						  				${res.successSum }
						  			</td>
					  				<td class="text-center table-text col-lg" title="${res.status }">
						  				<c:if test="${res.status == '-1' }">任务进行中</c:if>
						  				<c:if test="${res.status == '0' }">任务处理异常</c:if>
						  				<c:if test="${res.status == '1' }">任务处理成功</c:if>
						  				<c:if test="${res.status == '2' }">任务等待中</c:if>
						  			</td>
					  				<td class="text-center table-text col-lg" title="操作">
					  					<c:if test="${not empty res.resultFilePath && res.resultFilePath != ''}">
						  					<a href="javascript:toDownloadResultFile('${res.resultFilePath}');">查看结果</a>
						  				</c:if>
						  			</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="dataTables_wrapper form-inline">
			<div class="row">
				<div class="col-md-12">
					${pageView.pageView }
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="<%=basePath%>js/common_view.js"></script>
<script type="text/javascript">
	function inverseCkb(items){
		$('[name='+items+']:checkbox').each(function(){
			this.checked=!this.checked;
		});
	}
	
	function toChangeUploadUrl(obj){
		$("#uploadUrl").val($("#templeteSelect").find("option:selected").attr("data-uploadUrl"));
		$("#businessType").val($(obj).val());
	}

	function toDownloadResultFile(url) {//这里改成下载文件
		/* openDivs(root+"/control/importTaskController/toShowImportTaskDetail.do?twitId=" + id); */
		window.location.href = '${func:getNginxDownloadPreFix() }express_raw_data/'+url;
	}

	function downloadTemplete(businessType){
		window.location.href = root+"control/importTaskController/downloadTemplete.do?businessType=" 
				+ businessType;
	}

	function reload(){
		openDivs(root+"/control/importTaskController/importTaskList.do?" + $("#searchForm").serialize());
	}

	//跳转
	function pageJump() {
		openDivs(root+"/control/importTaskController/importTaskList.do?startRow="+$("#startRow").val()+"&endRow="+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val() + "&" + $("#searchForm").serialize());
	}

	function del() {
		
		var checked = $("input[name='ckb']:checked");
		
		if(checked.length < 1){
			alert("请选择至少一行数据");
			return;
		}
		var ids = [];
		checked.each(function(){
			ids.push($(this).val());
		});
		if(confirm("你所勾选的数据会连带明细数据一同删除,请谨慎使用,确定要删除所选数据吗？")){
			loadingStyle();
			$.ajax({
				url:root+"control/importTaskController/delImportTaskByIds.do",
				type:"post",
				data:{
					ids : ids.join(",")
				},
				dataType:"json",
				success:function(data){
					if(data.out_result == 1){
						alert("删除成功");
					} else {
						alert(data.out_result_reason);
					}
					pageJump();
					cancelLoadingStyle();
				}
			});
		}
	}
	
	function importProduct(){
		var businessType = $("#templeteSelect").val();
		var businessGroup = $("#businessGroup").val();
		if(businessType == ''){
			alert("请选择对应的模板");
			return;
		}
		if($("#photoCover").val()==null || $("#photoCover").val()==''){
			alert("请选择上传文件");
			return;
		}
		if(confirm("您已选择" + $("#templeteSelect").find("option:selected").attr("data-templeteName") 
				+ "确定要上传吗？")){
			loadingStyle();
		    var formData = new FormData(document.getElementById("fileForm"));
		    formData.append("businessType",businessType);
		    formData.append("businessGroup",businessGroup);
		    //约定businessType值为前缀
		    $.ajax({    
		        contentType:"multipart/form-data",  
		        url:root+$("#uploadUrl").val(),    
		        type:"post",  
		        data:formData,  
		        dataType:"json",  
		        processData: false,  // 告诉jQuery不要去处理发送的数据  
		        contentType: false,   // 告诉jQuery不要去设置Content-Type请求头  
		        success: function(jsonStr){  
					alert(jsonStr.out_result_reason);
					cancelLoadingStyle();
					if(jsonStr.out_result==1){
						$('#file1').val("");
						$('#photoCover').val("");
						pageJump();
					}
		        }  
		    });  
		}
	}
	
	//查询模板类型
	function templeteMode(){
		var businessType = $("#templeteSelect").val();
		if(businessType == ''){
			alert("请选择对应的模板");
			return;
		}
		loadingStyle();
		sendMessage("control/importTaskController/getTempleteMode.do",
			{"businessType" : businessType},
			function(jsonStr){  
				cancelLoadingStyle();
				if(jsonStr != null){
					if(jsonStr.templeteMode==0){
						//请求动态生成模板
						downloadTemplete(businessType);
					} else if(jsonStr.templeteMode==1){
						//请求静态资源模板
						toDownloadResultFile(jsonStr.templeteModeUrl);
					} else {
						alert("后台对应模板配置类型不识别");
					}
				} else {
					alert("后台未匹配到对应模板");
				}
			}
		);
	}

	function fileChange(){
		$('#photoCover').val($('#file1').val());
	};
	
	function sendMessage(url,data,callback){
		$.ajax({
			url:root+url,    
	        type:"post",  
	        data:data,  
	        dataType:"json",
	        success: callback,
			error: callback
		});
	}

</script>
</html>