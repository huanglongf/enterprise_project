<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
			<form id="saveForm" action="#">
				<input type="hidden" id="id" name="id" value="${parkId }" />
				<table>
					<tr>
						<td align="center" width="25%">
							<label class= "blue" for="parkCode">园区代码&nbsp;:</label>
						</td>
						<td width="25%">
							<input type="text" id="parkCode" <c:if test="${not empty parkId }">readonly="readonly"</c:if> name="parkCode" value="${model.parkCode }"/>
							<span class="required" style="color:#ed8888">*</span>
						</td>
						<td align="center" width= "25%">
							<label class= "blue" for="parkName">园区名称&nbsp;:</label>
						</td>
						<td width="25%">
							<input type="text" id="parkName" <c:if test="${not empty parkId }">readonly="readonly"</c:if> name="parkName" value="${model.parkName }"/>
							<span class="required" style="color:#ed8888">*</span>
						</td>
					</tr>
					<tr>
						<td align="center" width="25%">
							<label class= "blue" for="parkCostCenter">园区成本中心代码&nbsp;:</label>
						</td>
						<td width="25%">
							<input type="text" id="parkCostCenter" name="parkCostCenter" value="${model.parkCostCenter }"/>
							<span class="required" style="color:#ed8888">*</span>
						</td>
						<td align="center" width="25%">
							<label class= "blue" for="remark">备注&nbsp;:</label>
						</td>
						<td width="25%">
							<input type="text" id="remark" name="remark" value="${model.remark }"/>
						</td>
					</tr>
					<%-- <tr>
						<td align="center" width="25%">
							<label class= "blue" for="wareFlag">仓&nbsp;:</label>
						</td>
						<td width="25%">
							<select id="wareFlag" name="wareFlag">
								<option value="">--请选择--</option>
								<option <c:if test="${model.wareFlag == 'Y' }">selected="selected"</c:if> value="Y">Y</option>
								<option <c:if test="${model.wareFlag == 'N' }">selected="selected"</c:if> value="N">N</option>
							</select>
						</td>
						<td align="center" width="25%">
							<label class="blue" for="disFlag">配&nbsp;:</label>
						</td>
						<td width="25%">
							<select id="disFlag" name="disFlag">
								<option value="">--请选择--</option>
								<option <c:if test="${model.disFlag == 'Y' }">selected="selected"</c:if> value="Y">Y</option>
								<option <c:if test="${model.disFlag == 'N' }">selected="selected"</c:if> value="N">N</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="center" width="25%">
							<label class= "blue" for="showByCpFlag">是否由物流部出具账单&nbsp;:</label>
						</td>
						<td width="25%">
							<select id="showByCpFlag" name="showByCpFlag">
								<option value="">--请选择--</option>
								<option <c:if test="${model.showByCpFlag == 'Y' }">selected="selected"</c:if> value="Y">Y</option>
								<option <c:if test="${model.showByCpFlag == 'N' }">selected="selected"</c:if> value="N">N</option>
							</select>
						</td>
						<td align="center" width="25%">
							<label class= "blue" for="showStReFlag">是否出具店铺收入&nbsp;:</label>
						</td>
						<td width="25%">
							<select id="showStReFlag" name="showStReFlag">
								<option value="">--请选择--</option>
								<option <c:if test="${model.showStReFlag == 'Y' }">selected="selected"</c:if> value="Y">Y</option>
								<option <c:if test="${model.showStReFlag == 'N' }">selected="selected"</c:if> value="N">N</option>
							</select>
						</td>
					</tr> --%>
				</table>
			</form>
		</div>
	</div>
	<div style="margin-top: 10px;margin-left: 10px;margin-bottom: 10px;">
		<button class="btn btn-sm btn-white btn-danger btn-bold btn-round btn-width"
			onclick="doSaveForm();">
			<i class="ace-icon fa icon-save red bigger-120"> 保存 </i>
		</button>
		<c:if test="${not empty parkId }">
			<button class="btn btn-sm btn-white btn-danger btn-bold btn-round btn-width"
				onclick="showModel('addDetail');">
				<i class="ace-icon fa fa-plus-circle red bigger-120"> 新增 </i>
			</button>
			<button class="btn btn-sm btn-white btn-danger btn-bold btn-round btn-width"
				onclick="toEditDetail();">
				<i class="ace-icon fa icon-edit red bigger-120"> 修改 </i>
			</button>
			<button class="btn btn-sm btn-white btn-danger btn-bold btn-round btn-width"
				onclick="del();">
				<i class="ace-icon fa fa-trash-o red bigger-120"> 删除 </i>
			</button>
		</c:if>
		<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width"
			onclick="back();">
			<i class="ace-icon fa fa-angle-double-left blue bigger-120"> 返回 </i>
		</button>
	</div>
	<c:if test="${not empty parkId }">
		<div class="table-main">
			<iframe id="warehouseAndStore" name="warehouseAndStore" src="" runat="server" style="width:100%;min-height:250px;background-color:transparent;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="true" allowtransparency="true" onload="setIframeHeight();"></iframe>
		</div>
		<!-- 新增弹窗 -->
		<div id= "addDetailModel" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >园区关系添加</h4>
					</div>
					<div class= "modal-body" >
						<form id="addDetailForm" action="#">
							<input type="hidden" name="id" id="relationId" />
							<input type="hidden" name="parkId" id="parkId" value="${parkId }"/>
							<table>
								<tr>
									<td align="center" width="25%">
										<label class="blue" for="warehouseCode">仓库&nbsp;:</label>
									</td>
									<td width="25%">
										<select style="width:80%" id="warehouseCode" name="warehouseCode">
											<option value="">--请选择--</option>
										</select>
										<span class="required" style="color:#ed8888">*</span>
									</td>
									<td align="center" width="25%">
										<label class="blue" for="storeCode">店铺&nbsp;:</label>
									</td>
									<td width="25%">
										<select style="width:80%"  id="storeCode" name="storeCode">
											<option value="">--请选择--</option>
										</select>
										<span class="required" style="color:#ed8888">*</span>
									</td>
								</tr>
							</table>		
						</form>	
					</div>
					<div class= "modal-footer" >
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="doSaveDetail();" >
		     				<i class="icon-save" aria-hidden= "true" ></i><span id="btn_type_text_store">保存</span>
		     			</button>
						<button id= "btn_back" type= "button" class= "btn btn-default" data-dismiss="modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</body>
<script type="text/javascript" src="<%=basePath%>js/common_view.js"></script>
<script type="text/javascript">
	function inverseCkb(items){
		$('[name='+items+']:checkbox').each(function(){
			this.checked=!this.checked;
		});
	}

	function doSaveForm(id) {
		
		var parkCode = $("#parkCode").val();
		var parkName = $("#parkName").val();
		var parkCostCenter = $("#parkCostCenter").val();
		var remark = $("#remark").val();
		/* var wareFlag = $("#wareFlag").val();
		var disFlag = $("#disFlag").val();
		var showByCpFlag = $("#showByCpFlag").val();
		var showStReFlag = $("#showStReFlag").val(); */
		if(parkCode == null || parkCode == ''){
			alert("园区代码不能为空");
			return;
		}
		if(parkName == null || parkName == ''){
			alert("园区名称不能为空");
			return;
		}
		if(parkCostCenter == null || parkCostCenter == ''){
			alert("园区成本中心不能为空");
			return;
		}
		/* if(wareFlag == null || wareFlag == ''){
			alert("是否自营仓请选择");
			return;
		}
		if(disFlag == null || disFlag == ''){
			alert("是否自配请选择");
			return;
		}
		if(disFlag == null || disFlag == ''){
			alert("是否自配请选择");
			return;
		}
		if(showByCpFlag == null || showByCpFlag == ''){
			alert("是否由物流部出具账单请选择");
			return;
		}
		if(showStReFlag == null || showStReFlag == ''){
			alert("是否出具店铺收入请选择");
			return;
		} */
		
		if(confirm("确认保存吗？")){
			loadingStyle();
			$.ajax({
				url:root+"/control/wareParkController/doSaveForm.do",
				type:"post",
				data:$("#saveForm").serialize(),
				dataType:"json",
				success:function(data){
					if(data.out_result == 1){
						alert("保存成功");
					} else {
						alert(data.out_result_reason);
					}
					openDivs(root+"/control/wareParkController/toSaveForm.do?id=${parkId}");
					cancelLoadingStyle();
				}
			});
		}
	}
	
	function back(){
		openDivs(root+"/control/wareParkController/list.do");
	}
	
	//<c:if test="${not empty parkId }">
	
	function del() {
		
		var checked = $("input[name='ckb']:checked",document.getElementById("warehouseAndStore").contentWindow.document);
		
		if(checked.length < 1){
			alert("请选择至少一行数据");
			return;
		}
		var ids = [];
		checked.each(function(){
			ids.push($(this).val());
		});
		
		if(confirm("确定要删除所选数据吗？")){
			loadingStyle();
			$.ajax({
				url:root+"/control/wareParkController/delDetailByIds.do",
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
					$("#warehouseAndStore").attr("src",root+"/control/wareParkController/wareParkWarehouseStoreList.do?parkId=${parkId}&isShow=false");
					cancelLoadingStyle();
				}
			});
		}
	}
	
	function loadWarehouseAndStoreSelect(){
		$.ajax({
			url:root+"/control/wareParkController/loadWarehouseAndStore.do",
			type:"post",
			data:$("#addDetailForm").serialize(),
			dataType:"json",
			success:function(data){
				if(data != null){
					if(data.storeList != null && data.storeList.length > 0){
						$("#storeCode").html("<option value=''>--请选择--</option>");
						for(var i = 0 ; i < data.storeList.length ; i++){
							$("#storeCode").append("<option value='" + 
									data.storeList[i].store_code + "'>" + 
									data.storeList[i].store_name + "</option>");
						}
					}
					if(data.warehouseList != null && data.warehouseList.length > 0){
						$("#warehouseCode").html("<option value=''>--请选择--</option>");
						for(var i = 0 ; i < data.warehouseList.length ; i++){
							$("#warehouseCode").append("<option value='" + 
									data.warehouseList[i].warehouse_code + "'>" + 
									data.warehouseList[i].warehouse_name + "</option>");
						}
					}
					alReady = true;
				}
				/* $("#storeCode");
				$("#warehouseCode"); */
			}
		});
	}

	function showModel(obj) {
		if(!alReady){
			alert("数据未加载完成,暂时无法打开弹窗，请稍等。");
			return;
		}
		$("#relationId").val("");
		$("#warehouseCode").val("");
		$("#storeCode").val("");
		$("#" + obj + "Model").modal('show');
		$(".modal-backdrop").hide();
	}
	
	function toEditDetail(){
		if(!alReady){
			alert("数据未加载完成,暂时无法打开弹窗，请稍等。");
			return;
		}
		var checked = $("input[name='ckb']:checked",document.getElementById("warehouseAndStore").contentWindow.document);
		if(checked.length != 1){
			alert("请选择一行数据");
			return;
		}
		
		$("#relationId").val(checked.val());
		$("#warehouseCode").val(checked.data("warehouse_code"));
		$("#storeCode").val(checked.data("store_code"));

		$("#addDetailModel").modal('show');
		$(".modal-backdrop").hide();
	}
	
	function doSaveDetail(){
		var warehouseCode = $("#warehouseCode").val();
		var storeCode = $("#storeCode").val();
		
		if(warehouseCode == null || warehouseCode == ""){
			alert("请选择仓库");
			return;
		}
		if(storeCode == null || storeCode == ""){
			alert("请选择店铺");
			return;
		}
		
		if(confirm("确定要保存吗？")){
			loadingStyle();
			$.ajax({
				url:root+"/control/wareParkController/doSaveDetail.do",
				type:"post",
				data:$("#addDetailForm").serialize(),
				dataType:"json",
				success:function(data){
					if(data.out_result == 1){
						alert("保存成功");
					} else {
						alert(data.out_result_reason);
					}
					//openDivs(root+"/control/wareParkController/toSaveForm.do?id=${parkId}");
					$("#warehouseAndStore").attr("src",root+"/control/wareParkController/wareParkWarehouseStoreList.do?parkId=${parkId}&isShow=false");
					$("#addDetailModel").modal('hide');
					$(".modal-backdrop").hide();
					cancelLoadingStyle();
				}
			});
		}

	}
	
	function setIframeHeight() {
		var iframe = document.getElementById("warehouseAndStore");
		if (iframe) {
			var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
			if (iframeWin.document.body) {
				iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
			}
		}
	}

	var alReady = false;
	loadWarehouseAndStoreSelect();
	$("#warehouseAndStore").attr("src",root+"/control/wareParkController/wareParkWarehouseStoreList.do?parkId=${parkId}&isShow=false");
	//</c:if>
</script>
</html>
