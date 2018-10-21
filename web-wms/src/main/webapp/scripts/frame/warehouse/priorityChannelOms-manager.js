/*function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
function confirmModification(obj){
	var rowId=$j("#tbl-threadList").jqGrid("getGridParam","selrow");
	var rowData = $j("#tbl-threadList").jqGrid('getRowData',rowId);
	var id=rowData.id;
	var beanName=rowData.beanName;
	var methodName=rowData.methodName;
	$j("#beanName_1").val(beanName);
	$j("#methodName_1").val(methodName);
	$j("#id").val(id);
	$j("#timeExp").focus();
}*/
$j(document).ready(function (){
	
	$j("#showType").dialog({title: "修改", modal:true,closeOnEscape:false, autoOpen: false, width: 300});
	$j("#tabs").tabs({
		 select: function(event, ui) {
		  }
	});
	$j("#tbl-PriorityChannelOmsList").jqGrid({
		url:$j("body").attr("contextpath")+"/json/getAllPriorityChannelOms.json",
		datatype:"json",
		colNames:["ID","店铺","百分比","操作"],	
		colModel:[
			      {name:"id",index:"id",hidden:true,width: 60,resizable: true},
		          {name:"code",index:"code",width: 125,resizable: true},
		          {name:"qty",index:"qty",width: 60,resizable: true},
		          {name: "update", index: "update", width: 58,  resizable: true}
		          ],
		caption: i18("店铺列表"),
		multiselect: false,
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		width:"430",
		height:"300",
		pager:"#pager_query3",
		sortorder: "id",
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete: function(){
			var update = '<div><button type="button" style="width:90px;" class="confirm" id="update" name="update" loxiaType="button" onclick="update(this);">'+"修改"+'</button></div>';
			var ids = $j("#tbl-PriorityChannelOmsList").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-PriorityChannelOmsList").jqGrid('setRowData',ids[i],{"update":update});
			}
		}
	});
	$j("#addupdate3").click(function(){
		
		if($j("#priorityChannelOms_code").val()==""){
			jumbo.showMsg("店铺不可为空！");
			return false;
		}
		if($j("#priorityChannelOms_qty").val()==""){
			jumbo.showMsg("百分比不可为空！");
			return false;
		}
		if(isNaN($j("#priorityChannelOms_qty").val())){
			jumbo.showMsg("百分比 请输入数字！");
			return false;
		}
		
		var postData = {
				"priorityChannelOms.code":$j("#priorityChannelOms_code").val(),
				"priorityChannelOms.qty":$j("#priorityChannelOms_qty").val(),
		}; 
		var flag=loxia.syncXhr($j("body").attr("contextpath") + "/json/addPriorityChannelOms.json",postData);
		if(flag["flag"]=="success"){
			jumbo.showMsg("新增成功！");
			$j("#tbl-PriorityChannelOmsList").jqGrid('setGridParam',{
				page:1}).trigger("reloadGrid");
		}else if(flag["flag"]=="error"){
			jumbo.showMsg("系统异常！");
		}else {
			jumbo.showMsg(flag["flag"]);
		}
		
	});
	$j("#thread_reset3").click(function(){
		$j("#priorityChannelOms_code").val("");
		$j("#priorityChannelOms_qty").val("");
	});
$j("#update32").click(function(){
		
		if($j("#optionValue").val()==""){
			jumbo.showMsg("不可为空！");
			return false;
		}
		
		
		var postData = {
				"optionValue":$j("#optionValue").val(),
		}; 
		var flag=loxia.syncXhr($j("body").attr("contextpath") + "/json/updateChooseOption.json",postData);
		if(flag["flag"]=="success"){
			jumbo.showMsg("修改成功！");
		}else if(flag["flag"]=="error"){
			jumbo.showMsg("系统异常！");
		}else {
			jumbo.showMsg(flag["flag"]);
		}
		
	});
	$j("#thread_reset32").click(function(){
		$j("#optionValue").val("");
	});
	$j("#btnTypeClose").click(function(){//关闭指令明细
		$j("#showType").hide();
		$j("#showType").dialog("close");
	});
	
	//初始化店铺占比
	var chooseOption = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/buildChooseOption.json");
	$j("#optionValue").val(chooseOption.optionValue);
	//初始化直连批次大小
	var chooseOption = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/buildOmsChooseOptionUpdate.json");
	$j("#oms_optionValue").val(chooseOption.optionValue);
	//初始化非直连批次大小
	var chooseOption = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/buildPacChooseOptionUpdate.json");
	$j("#pac_optionValue").val(chooseOption.optionValue);
});
function update(obj){//修改跳转
	var id = $j(obj).parents("tr").attr("id");
	var data=$j("#tbl-PriorityChannelOmsList").jqGrid("getRowData",id);
	 $j("#tbl-PriorityChannelOmsList-id").val(id);
	 $j("#priorityChannelOms_code2").val(data["code"]);
	 $j("#priorityChannelOms_qty2").val(data["qty"]);
	 $j("#showType").show();
	 $j("#showType").dialog("open");
	 
}
function updateType(){//修改库位类型
	if($j("#priorityChannelOms_code2").val()==""){
		jumbo.showMsg("店铺不可为空！");
		return false;
	}
	if($j("#priorityChannelOms_qty2").val()==""){
		jumbo.showMsg("百分比不可为空！");
		return false;
	}
	if(isNaN($j("#priorityChannelOms_qty2").val())){
		jumbo.showMsg("请输入数字！");
		return false;
	}
	
	var postData = {
			"priorityChannelOms.code":$j("#priorityChannelOms_code2").val(),
			"priorityChannelOms.qty":$j("#priorityChannelOms_qty2").val(),
			"priorityChannelOms.id":$j("#tbl-PriorityChannelOmsList-id").val(),
	}; 
	var flag=loxia.syncXhr($j("body").attr("contextpath") + "/json/addPriorityChannelOms.json",postData);
	if(flag["flag"]=="success"){
		jumbo.showMsg("修改成功！");
		$j("#showType").hide();
		$j("#showType").dialog("close");
		$j("#tbl-PriorityChannelOmsList").jqGrid('setGridParam',{
			page:1}).trigger("reloadGrid");
	}else if(flag["flag"]=="error"){
		jumbo.showMsg("系统异常！");
	}else {
		jumbo.showMsg(flag["flag"]);
	}
	
}