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
	$j("#showType2").dialog({title: "修改", modal:true,closeOnEscape:false, autoOpen: false, width: 300});
	$j("#tabs").tabs({
		 select: function(event, ui) {
		  }
	});
	$j("#tbl-threadList").jqGrid({
		url:$j("body").attr("contextpath")+"/json/getAllThreadPools.json",
		datatype:"json",
		colNames:["ID","线程池编码","大小","备注","系统编码","操作"],	
		colModel:[
			      {name:"id",index:"id",width: 60,resizable: true},
		          {name:"threadCode",index:"threadCode",width: 150,resizable: true},
		          {name:"threadCount",index:"threadCount",width: 40,resizable: true},
		          {name:"memo",index:"memo",width: 190,resizable: true},
		          {name:"sysKey",index:"sysKey",width: 100,resizable: true},
		          {name: "update", index: "update", width: 58,  resizable: true}
		          ],
		caption: i18("线程池列表"),
		multiselect: false,
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		width:"auto",
		height:"auto",
		pager:"#pager_query2",
		sortorder: "id",
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete: function(){
			var update = '<div><button type="button" style="width:88px;" class="confirm" id="update" name="update" loxiaType="button" onclick="update2(this);">'+"修改"+'</button></div>';
			var ids = $j("#tbl-threadList").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-threadList").jqGrid('setRowData',ids[i],{"update":update});
			}
		}
	});
	$j("#addupdate").click(function(){
		
			if($j("#threadConfig_threadCode").val()==""){
				jumbo.showMsg("新增时 线程池编码不可为空！");
				return false;
			}
				
			if($j("#threadConfig_threadCount").val()==""){
				jumbo.showMsg("新增时 线程池数量不可为空！");
				return false;
			}
			if(isNaN($j("#threadConfig_threadCount").val())){
				jumbo.showMsg("线程池大小请输入数字！");
				return false;
			}
			if($j("threadConfig_sysKey").val()==""){
				jumbo.showMsg("新增时 系统编码不可为空！");
				return false;
			}
			if($j("#threadConfig_memo").val()==""){
				jumbo.showMsg("新增时 备注不可为空！");
				return false;
			}
		
		
		var postData = {
				//"threadConfig.id":$j("#threadConfig_id").val(),
				"threadConfig.threadCode":$j("#threadConfig_threadCode").val(),
				"threadConfig.threadCount":$j("#threadConfig_threadCount").val(),
				"threadConfig.sysKey":$j("#threadConfig_sysKey").val(),
				"threadConfig.memo":$j("#threadConfig_memo").val(),
				"path":$j("#path").val()
		}; 
		var flag=loxia.syncXhr($j("body").attr("contextpath") + "/json/addUpdateThreadConfig.json",postData);
		if(flag["flag"]=="success"){
			jumbo.showMsg("保存成功！");
		}else if(flag["flag"]=="error"){
			jumbo.showMsg("系统异常！");
		}else {
			jumbo.showMsg(flag["flag"]);
		}
		$j("#tbl-threadList").jqGrid('setGridParam',{
			page:1}).trigger("reloadGrid");
	});
	$j("#thread_reset").click(function(){
		$j("#threadConfig_id").val("");
		$j("#threadConfig_threadCode").val("");
		$j("#threadConfig_threadCount").val("");
		$j("#threadConfig_sysKey").val("");
		$j("#threadConfig_memo").val("");
	});
});
function update2(obj){//修改跳转
	var id = $j(obj).parents("tr").attr("id");
	var data=$j("#tbl-threadList").jqGrid("getRowData",id);
	 $j("#tbl-ChooseOptionList2-id").val(id);
	 $j("#optionValue2").val(data["threadCount"]);
	 $j("#showType2").show();
	 $j("#showType2").dialog("open");
	 
}
function updateType2(){//修改线程池
	if($j("#optionValue2").val()==""){
		jumbo.showMsg("线程池大小不可为空！");
		return false;
	}
	if(isNaN($j("#optionValue2").val())){
		jumbo.showMsg("请输入数字！");
		return false;
	}
	
	var postData = {
			"threadConfig.id":$j("#tbl-ChooseOptionList2-id").val(),
			"threadConfig.threadCount":$j("#optionValue2").val(),
			"path":$j("#path2").val()
	}; 
	var flag=loxia.syncXhr($j("body").attr("contextpath") + "/json/addUpdateThreadConfig.json",postData);
	if(flag["flag"]=="success"){
		jumbo.showMsg("修改成功！");
		$j("#showType2").hide();
		$j("#showType2").dialog("close");
		$j("#tbl-threadList").jqGrid('setGridParam',{
			page:1}).trigger("reloadGrid");
	}else if(flag["flag"]=="error"){
		jumbo.showMsg("系统异常！");
	}else {
		jumbo.showMsg(flag["flag"]);
	}
	
}