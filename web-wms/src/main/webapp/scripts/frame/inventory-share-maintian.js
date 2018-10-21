$j.extend(loxia.regional['zh-CN'],{
	INFO_SELECT_DATA 					: "请选择具体数据",
	INFO_RESULT_SUCCESS_SHARE 			: "库存共享设置成功",
	INFO_RESULT_SUCCESS_CANEL_SHARE		 : "取消库存共享成功",
	
	INFO_RESULT_FAILURE_SHARE 			: "库存共享设置失败",
	INFO_RESULT_FAILURE_CANEL_SHARE		: "取消库存共享失败",
	
	INFO_CONFIRM_SHARE 					: "您确定要共享库存？",
	INFO_CONFIRM_CANEL_SHARE 			: "您确定要取消共享库存？",
	
	WH_TAB_COLUMNNAME_INVSTATUS			: "库存共享状态",
	WH_TAB_COLUMNNAME_WHCODE			: "仓库编码",
	WH_TAB_COLUMNNAME_WHNAME			: "仓库名称",
	WH_TAB_COLUMNNAME_ISAVAILABLE		: "使用状态",
	WH_TAB_COLUMNNAME_PIC				: "负责人",
	WH_TAB_COLUMNNAME_MANAGEMODE		: "管理模式",
	WH_TAB_COLUMNNAME_OPMODE			: "运作模式",
	WH_TAB_COLUMNNAME_ACTUALNUMBER		: "实际库存总数(件)",
	WH_TAB_COLUMNNAME_AVAILNUMBER		: "实际可用量总数(件)",
	WH_TAB_COLUMNNAME_MEMO 				: "仓库备注",
	INFO 								: "仓库附加信息未填写完整的仓库组织信息",
	LOCATION_LIST 						: "仓库列表"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

$j(document).ready(function (){

	var ajax_post_ou_url = "";
	var ajax_post_url = "";
	if($j("#f1").attr("refsize") <= 0){
		$j("#f1").removeClass("hidden");
		$j("#f2").addClass("hidden");
		ajax_post_url = "";
		ajax_post_ou_url = $j('body').attr('contextpath')+"/json/ouwarehouselist.json";
	}else{
		$j("#f1").addClass("hidden");
		$j("#f2").removeClass("hidden");
		ajax_post_url = $j('body').attr('contextpath')+"/json/warehouselist.json";
		ajax_post_ou_url = "";
	}
	//运营中心下所有的仓库组织对应的仓库附加信息未填写完整，输出本表格——仓库组织信息
	//组织的信息
		$j("#tb-operationunitinfo").jqGrid({
		url: ajax_post_ou_url,
		datatype: "json",
		//colNames: ["ID","仓库编码","仓库名称","仓库备注"],
		colNames: ["ID",i18("WH_TAB_COLUMNNAME_WHCODE"),i18("WH_TAB_COLUMNNAME_WHNAME"),i18("WH_TAB_COLUMNNAME_MEMO")],
		colModel: [{name: "id", index: "id", hidden: true},
				   {name: "code", index: "code", width: 180, resizable: true},
		           {name: "name", index: "name", width: 180, resizable: true},
	               {name: "comment", index: "comment", width: 400, resizable: true}],
		caption: i18("INFO"), //仓库附加信息未填写完整的仓库组织信息
		rowNum:-1,
		height:"auto",
	   	//rowList:[10,20,30],
	   	sortname: 'code',
	   	//pager: '#pager9',
		//viewrecords: true, 
		sortorder: "asc", 
		// multiselect: true,
		jsonReader: { repeatitems : false, id: "0" }
		});
		
	//运营中心下所有的仓库组织对应的仓库附加信息都填写完整之后，输出本表格——仓库附加信息
	//仓库的详细信息
	var manageMode = loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whManageMode"});
	var opMode = loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whOpMode"});
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"trueOrFalse"});
	
	if(!manageMode.exception&&!opMode.exception&&!trueOrFalse.exception){
		$j("#tbl-warehouselist").jqGrid({
		url: ajax_post_url,
		datatype: "json",
		colNames: ["ID",i18("WH_TAB_COLUMNNAME_INVSTATUS"),i18("WH_TAB_COLUMNNAME_WHCODE"),i18("WH_TAB_COLUMNNAME_WHNAME"),i18("WH_TAB_COLUMNNAME_ISAVAILABLE"),i18("WH_TAB_COLUMNNAME_PIC"),i18("WH_TAB_COLUMNNAME_MANAGEMODE"),i18("WH_TAB_COLUMNNAME_OPMODE"),i18("WH_TAB_COLUMNNAME_ACTUALNUMBER"),i18("WH_TAB_COLUMNNAME_AVAILNUMBER")],
		colModel: [{name: "id", index: "id", hidden: true},
				   {name: "isShare", index: "isShare", width: 80, resizable: true, formatter:'select',editoptions:trueOrFalse},
				   	 {name: "code", index: "code", width: 100, resizable: true},
		             {name: "name", index: "name", width: 180, resizable: true},
		             {name: "isAvailable", index: "isAvailable", width: 100, resizable: true,formatter:'select',editoptions:trueOrFalse},
				   {name: "pic", index: "pic", width: 100, resizable: true},
				   {name: "intManageMode", index: "manageMode", width: 100, resizable: true,formatter:'select',editoptions:manageMode},
				   {name: "intOpMode", index: "opMode", width: 100, resizable: true,formatter:'select',editoptions:opMode},
				   {name: "qty", index: "qty", width: 120, resizable: true},
				   {name: "availQty", index: "availQty", width: 140, resizable: true}],
		caption: i18("LOCATION_LIST"), //仓库列表
		rowNum:-1,
		height:"auto",
	   	//rowList:[10,20,30],
	   	sortname: 'code',
	   	//pager: '#pager9',
		//viewrecords: true, 
		sortorder: "asc", 
		multiselect: true,
		
		jsonReader: { repeatitems : false, id: "0" }
		});
	}else{
		jumbo.showMsg(loxia.getLocaleMsg("ERROR_INIT"));
	}
	
	$j("#btn-share").click(function(){
		if($j("#tbl-warehouselist").jqGrid("getGridParam","selarrrow").length != 0){
			if(confirm(loxia.getLocaleMsg("INFO_CONFIRM_SHARE"))){
				setWhShareStatus(true);
			}
		}else{
			jumbo.showMsg(loxia.getLocaleMsg("INFO_SELECT_DATA"));
			setTimeout("jumbo.showMsg('')",3000);
	   	 	return;
		}
		
	});

	$j("#btn-unshare").click(function(){
		if($j("#tbl-warehouselist").jqGrid("getGridParam","selarrrow").length != 0){
			if(confirm(loxia.getLocaleMsg("INFO_CONFIRM_CANEL_SHARE"))){
				setWhShareStatus(false);
			}
		}else{
			jumbo.showMsg(loxia.getLocaleMsg("INFO_SELECT_DATA"));
			setTimeout("jumbo.showMsg('')",3000);
	   	 	return;
		}
	});
});

function setWhShareStatus(status){
	var whlist = $j("#tbl-warehouselist").jqGrid("getGridParam","selarrrow");
	if(whlist==null||whlist==''){
	   	 jumbo.showMsg(loxia.getLocaleMsg("INFO_SELECT_DATA"));
	   	 return;
	}
	 whlist=""+whlist;
     var idlist = whlist.split(",");
     var data = {};
     for(var i=0;i<idlist.length;i++){
        var warehouseId=idlist[i];
        data["warehouseList[" + i + "].id"] = warehouseId;
     } 
	if(status){
		// 设置共享
		loxia.asyncXhrPost( 
	    	 window.$j("body").attr("contextpath")+"/json/addinventoryshare.do",
			data,
			{
				success:function(data) {
					if(data.rs=='true'){
						jumbo.showMsg(loxia.getLocaleMsg("INFO_RESULT_SUCCESS_SHARE"));
						setTimeout(function(){jumbo.showMsg();},5000);
						window.location = window.$j("body").attr("contextpath")+"/warehouse/inventorymaintain.do";
					}else{
						jumbo.showMsg(loxia.getLocaleMsg("INFO_RESULT_FAILURE_SHARE"));
						setTimeout(function(){jumbo.showMsg();},5000);
					}
			   }
			}
		);
	}else{
		// 取消共享
		loxia.asyncXhrPost( 
	    	window.$j("body").attr("contextpath")+"/json/deleteinventoryshare.do",
			data,
			{
				success:function(data) {
					if(data.rs=='true'){
						jumbo.showMsg(loxia.getLocaleMsg("INFO_RESULT_SUCCESS_CANEL_SHARE"));
						window.location = window.$j("body").attr("contextpath")+"/warehouse/inventorymaintain.do";
					}else{
						jumbo.showMsg(loxia.getLocaleMsg("INFO_RESULT_FAILURE_CANEL_SHARE"));
					}
			   }
			}
		);
	}
}