function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function modifyTemplet(obj){
	//var rowId=$j("#tbl-upload-templet-list").jqGrid("getGridParam","selrow");
	//var rowData = $j("#tbl-upload-templet-list").jqGrid('getRowData',rowId);
	//var id=rowData.id;
	//if(id==null){
	//	jumbo.showMsg("请先选中后再修改");
	//	return;
	//}
	var id = $j(obj).parents("tr").attr("id");
	var data=$j("#tbl-upload-templet-list").jqGrid("getRowData",id);
	$j("#id").val(data["id"]);
	$j("#dialog").dialog("open");
	$j("#mainJrxmlFile").val("");
	$j("#subJrxmlFile").val("");
	$j("#picture").val("");
	$j("#templetCode").val(data["templetCode"]);
	$j("#templetCode").attr('disabled',true);
	$j("#memo").val(data["memo"]);
}

function downloadFile(obj){
	//var rowId=$j("#tbl-upload-templet-list").jqGrid("getGridParam","selrow");
	//var rowData = $j("#tbl-upload-templet-list").jqGrid('getRowData',rowId);
	//var id=rowData.id;
	//if(id==null){
	//	jumbo.showMsg("请先选中后再下载");
	//	return;
	//}
	var ids = $j(obj).parents("tr").attr("id");
	var data=$j("#tbl-upload-templet-list").jqGrid("getRowData",ids);
	var id=data["id"];
	//下载主模板
	var url = $j("body").attr("contextpath") + "/downLoadFile.json?id="+id+"&type="+0;
	$j("#exportFrame").attr("src",url);
	//下载子模板
	var url = $j("body").attr("contextpath") + "/downLoadFile.json?id="+id+"&type="+1;
	$j("#exportFrame1").attr("src",url);
}
$j(document).ready(function (){
	//initShopQuery("companyshop","innerShopCode");
	$j("#dialog").dialog({title: "编辑", modal:true, autoOpen: false, width: 360, height: 360});
	/*var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransactionTypeName.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#lpCode"));
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#newlpCode"));
	}*/
	//jumbo.loadShopList("companyshop");
	$j("#tbl-upload-templet-list").jqGrid({
		datatype:"json",
		colNames:["ID","店铺","主模板","子模板","备注","模板编码","下载模板","修改"],	
		colModel:[
		          {name:"id",index:"id",hidden:true},
		          {name:"owner",index:"owner",width: 150,resizable: true},
		          {name:"masterTemplet",index:"masterTemplet",width: 150,resizable: true},
		          {name:"subTemplet",index:"subTemplet",width: 150,resizable: true},
		          {name:"memo",index:"memo",width: 150,resizable: true},
		          {name:"templetCode",index:"templetCode",width: 50,resizable: true},
			      {name: "download", width: 60, resizable: true},
			      {name: "update", index: "update",width: 60, resizable: true},
		          ],
		caption:"出库装箱清单模板列表",
		multiselect: false,
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		width:"1400",
		height:"auto",
		pager:"#pager_query",	   
		sortorder: "id",
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete: function(){
		var download = '<div><button type="button" style="width:88px;" class="confirm" id="download" name="download" loxiaType="button" onclick="downloadFile(this);">'+"下载模板"+'</button></div>';
		var update = '<div><button type="button" style="width:88px;" class="confirm" id="update" name="update" loxiaType="button" onclick="modifyTemplet(this);">'+"修改"+'</button></div>';
		var ids = $j("#tbl-upload-templet-list").jqGrid('getDataIDs');
		for(var i=0;i < ids.length;i++){
			$j("#tbl-upload-templet-list").jqGrid('setRowData',ids[i],{"update":update});
			$j("#tbl-upload-templet-list").jqGrid('setRowData',ids[i],{"download":download});
		}
	}
	});
	
	$j("#search").click(function(){
		var storeCode=$j("#companyshop").val();
		var url = $j("body").attr("contextpath") + "/getPrintCustomizeList.json?storeCode="+storeCode;
		$j("#tbl-upload-templet-list").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("searchForm")}).trigger("reloadGrid");
	});
	
	$j("#reset").click(function(){
		$j("#companyshop").val("");
	});
	
	//新增
	$j("#createPrintTemplate").click(function(){
		$j("#templetCode").removeAttr("disabled");
		$j("#id").val("");
		$j("#templetCode").val("");
		$j("#mainJrxmlFile").val("");
		$j("#subJrxmlFile").val("");
		$j("#picture").val("");
		$j("#memo").val("");
		$j("#dialog").dialog("open");
	});
	
	//$j("#btnSearchShop").click(function(){
	//	$j("#shopQueryDialog").dialog("open");
	//});
	
	//$j("#btnShopFormRest").click(function(){
	//	$j("#shopQueryDialogForm input").val("");
	//});
	$j("#import").click(function(){
		if(!/^.*\.jrxml$/.test($j("#mainJrxmlFile").val())||!/^.*\.jrxml$/.test($j("#subJrxmlFile").val())){
			jumbo.showMsg("请选择jrxml文件");
			return false;
		}
		//验证文件名是否含有中文
		var chinese=/[\u4e00-\u9fa5]/;
		if(chinese.test($j("#mainJrxmlFile").val())){
			jumbo.showMsg("文件名不能使用中文");
			return false;
		}
		//模板编码验证
		var en = new RegExp("^[a-zA-Z]+$"); 
		if(!en.test($j("#templetCode").val())){
			jumbo.showMsg("模板编码只能使用英文字母");
			return false;	
		}
		if($j("#templetCode").val()==null||$j("#templetCode").val()==""){
			jumbo.showMsg("模板编码不能为空");
			return false;	
		}
		if($j("#memo").val()==null||$j("#memo").val()==""){
			jumbo.showMsg("备注不能为空");
			return false;	
		}
		if($j("#mainJrxmlFile").val()==null||$j("#mainJrxmlFile").val()==""){
			jumbo.showMsg("主模板不能为空");
			return false;	
		}
		if($j("#subJrxmlFile").val()==null||$j("#subJrxmlFile").val()==""){
			jumbo.showMsg("子模板不能为空");
			return false;	
		}
		if($j("#mainJrxmlFile").val()==$j("#subJrxmlFile").val()){
			jumbo.showMsg("主模板和子模板名称不能相同");
			return false;	
		}
		var mainJrxmlName=$j("#mainJrxmlFile").val();
		var subJrxmlName=$j("#subJrxmlFile").val();
		var pictureName=$j("#picture").val();
		var templetCode=$j("#templetCode").val();
		$j("#importTempletForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importPrintTemplet.do?mainJrxmlName="+mainJrxmlName+"&subJrxmlName="+subJrxmlName+"&pictureName="+pictureName +"&templetCode="+templetCode));
		loxia.submitForm("importTempletForm");
		$j("#dialog").dialog("close");
	});
	$j("#closediv").click(function(){
		$j("#dialog").dialog("close");
	});
});