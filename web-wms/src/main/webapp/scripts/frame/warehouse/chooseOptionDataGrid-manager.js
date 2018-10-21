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
	$j("#showType5").dialog({title: "修改", modal:true,closeOnEscape:false, autoOpen: false, width: 300});
	$j("#tabs").tabs({
		 select: function(event, ui) {
		  }
	});
	$j("#tbl-ChooseOptionList").jqGrid({
		url:$j("body").attr("contextpath")+"/json/getAllChooseOption.json",
		datatype:"json",
		colNames:["ID","categoryCode","名字","key","value","操作"],	
		colModel:[
			      {name:"id",index:"id",hidden:true,width: 60,resizable: true},
			      {name:"categoryCode",index:"categoryCode",hidden:true,width: 60,resizable: true},
		          {name:"categoryName",index:"categoryName",width: 95,resizable: true},
		          {name:"optionKey",index:"optionKey",width: 70,resizable: true},
		          {name:"optionValue",index:"optionValue",width: 60,resizable: true},
		          {name: "update", index: "update", width: 58,  resizable: true}
		          ],
		caption: i18("利丰批次"),
		multiselect: false,
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		width:"500",
		height:"350",
		//pager:"#pager_query5",
		sortorder: "id",
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete: function(){
			var update = '<div><button type="button" style="width:88px;" class="confirm" id="update" name="update" loxiaType="button" onclick="update5(this);">'+"修改"+'</button></div>';
			var ids = $j("#tbl-ChooseOptionList").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-ChooseOptionList").jqGrid('setRowData',ids[i],{"update":update});
			}
		}
	});
	$j("#btnTypeClose5").click(function(){//关闭指令明细
		$j("#showType5").hide();
		$j("#showType5").dialog("close");
	});
});
function update5(obj){//修改跳转
	var id = $j(obj).parents("tr").attr("id");
	var data=$j("#tbl-ChooseOptionList").jqGrid("getRowData",id);
	 $j("#tbl-ChooseOptionList-id").val(id);
	 $j("#optionValue5").val(data["optionValue"]);
	/* $j("#tbl-ChooseOptionList-categoryCode").val(data["categoryCode"]);
	 $j("#tbl-ChooseOptionList-optionKey").val(data["optionKey"]);*/
	 $j("#showType5").show();
	 $j("#showType5").dialog("open");
	 
}
function updateType5(){//修改库位类型
	if($j("#optionValue5").val()==""){
		jumbo.showMsg("不可为空！");
		return false;
	}
	if(isNaN($j("#optionValue5").val())){
		jumbo.showMsg("请输入数字！");
		return false;
	}
	
	var postData = {
			"optionValue":$j("#optionValue5").val(),
			"staId":$j("#tbl-ChooseOptionList-id").val(),
			/*"categoryCode":$j("#tbl-ChooseOptionList-categoryCode").val(),
			"optionKey":$j("#tbl-ChooseOptionList-optionKey").val(),*/
	}; 
	var flag=loxia.syncXhr($j("body").attr("contextpath") + "/json/updateChooseOptionValueLF.json",postData);
	if(flag["flag"]=="success"){
		jumbo.showMsg("修改成功！");
		$j("#showType5").hide();
		$j("#showType5").dialog("close");
		$j("#tbl-ChooseOptionList").jqGrid('setGridParam',{
			page:1}).trigger("reloadGrid");
	}else if(flag["flag"]=="error"){
		jumbo.showMsg("系统异常！");
	}else {
		jumbo.showMsg(flag["flag"]);
	}
	
}