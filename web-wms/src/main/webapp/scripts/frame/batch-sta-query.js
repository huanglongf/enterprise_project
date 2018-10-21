$j.extend(loxia.regional['zh-CN'],{
	DISTRIBUTION_LIST_SELECT : "请选择配货清单",
	CODE : "作业单号",
	INTSTATUS : "状态",
	REFSLIPCODE : "相关单据号",
	INTTYPE : "作业类型名称",
	SHOPID : "淘宝店铺ID",
	LPCODE : "物流服务商简称",
	CREATETIME : "创建时间",
	STVTOTAL : "计划执行总件数",
	INT_LIST : "作业单列表",
	
	BATCHNO : "配货清单号",
	CREATETIME : "创建时间",
	PLAN_BILL_COUNT : "计划配货单据数",
	PLAN_SKU_QTY : "计划配货商品件数",
	INTSTATUS : "状态",
	NEW_LIST : "新建配货清单列表",
	
	BATCHNO : "配货批次号",
	CREATETIME : "创建时间",
	PLANBILLCOUNT : "计划配货单据数", 
	PLAYSKUQTY : "计划配货商品件数",
	INTSTATUS : "状态",
	WAITING_LIST : "待配货清单列表",
	
	DISPATCH_FAILED : "由于您的配货单中所有作业单都失败，所以您的配货单被取消",
	DIAPATCH_CANCEL : "配货清单已取消",
	OPERATING : "配货清单打印中，请等待...",
	TRUNKPACKINGLISTINFO:"装箱清单打印中，请等待...",
	DELIVERYINFO:"物流面单打印中，请等待...",
	OPETION : "操作",
	PRINGT : "打印",
	CREATE_DISABLE_PRINT : "当前订单状态为‘新建状态’不能打印",
	CANEL_DISABLE_PRINT : "当前订单状态为‘取消已处理’不能打印",
	FAILED_DISABLE_PRINT : "当前订单状态为‘配货失败’不能打印",
	INPUT_FILE_ERROR	:"请选择正确的Excel导入文件"
		
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function reloadOrderDetail(url){
	$j("#sta-list").jqGrid('setGridParam',{url:loxia.getTimeUrl(url)}).trigger("reloadGrid");
}

function showDetail(tag){
	var baseUrl = $j("body").attr("contextpath");
	$j("#query-batch-list").addClass("hidden");
	$j("#order-List").removeClass("hidden");
	var id = $j(tag).parents("tr").attr("id");
	$j("#div2").attr("plId",id);
	$j("#pickingListId").html(id);
	var tr = $j(tag).parents("tr");
	var data=$j("#batch-list").jqGrid("getRowData",id);
	var rs = loxia.syncXhrPost(baseUrl + "/queryPickingListDetail.json",{"pickingListId":id});
	if(rs && rs["pickingList"]){
		$j("#dphCode").html( rs["pickingList"]["code"]);
		$j("#planBillCount").html( rs["pickingList"]["planBillCount"]);
		$j("#planSkuQty").html( rs["pickingList"]["planSkuQty"]);
		$j("#pickingTime").html( rs["pickingList"]["pickingTime"]);
		$j("#checkedBillCount").html( rs["pickingList"]["checkedBillCount"]);
		$j("#checkedTime").html( rs["pickingList"]["checkedTime"]);
		$j("#checkedSkuQty").html( rs["pickingList"]["checkedSkuQty"]);
		$j("#sendSkuQty").html( rs["pickingList"]["sendSkuQty"]);
		$j("#sendStaQty").html( rs["pickingList"]["sendStaQty"]);
		$j("#lastSendTime").html( rs["pickingList"]["lastSendTime"]);
	}
	$j("#dphStatus").html(tr.find("td[aria-describedby='pickingList_intStatus']").html());
	$j("#creator").html(data["createName"]);
	$j("#operator").html(data["operUserName"]);
	$j("#dphMode").html(data["pkModeInt"]);
	reloadOrderDetail(baseUrl + "/detialList.json?pickingListId="+id);
}

$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");
	
	var plStatus=loxia.syncXhr(baseUrl + "/json/formateroptions.do",{"categoryCode":"pickingListStatus"});
	
	$j("#batch-list").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("BATCHNO"),i18("CREATETIME"),"操作时间",i18("PLANBILLCOUNT"),i18("PLAYSKUQTY"),"模式",i18("INTSTATUS"),"操作人","创建人"],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "code", index: "code", formatter:"linkFmatter", formatoptions:{onclick:"showDetail"}, width:120,resizable:true},         
		           {name: "createTime", index: "createTime", width: 150, resizable: true},
		           {name: "executedTime", index: "executedTime", width: 150, resizable: true},
		           {name: "planBillCount", index: "planBillCount", width: 80, resizable: true},
		           {name: "planSkuQty", index: "planSkuQty", width: 80, resizable: true},
		           {name: "pkModeInt", index: "pkModeInt",width: 100, resizable: true},
	               {name: "intStatus",index:"intStatus",width:150,resizable:true,formatter:'select',editoptions:plStatus},
		           {name: "operUserName", index: "operUserName",width: 100, resizable: true},
		           {name: "createName", index: "createName",width: 100, resizable: true}],
		caption: i18("WAITING_LIST"),// 待配货清单列表
	   	sortname: 'pl.CREATE_TIME',
	   	height:"auto",
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		pager:"#pager",
		sortorder: "desc",
		rownumbers:true,
		viewrecords: true,
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#batch-list").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#batch-list").jqGrid('getRowData',ids[i]);
				if(datas["pkModeInt"] == '1'){
					$j("#batch-list").jqGrid('setRowData',ids[i],{"pkModeInt":"模式一"});
				}
				if(datas["pkModeInt"] == '2'){
					$j("#batch-list").jqGrid('setRowData',ids[i],{"pkModeInt":"模式二"});
				}
				if(datas["pkModeInt"] == '3'){
					$j("#batch-list").jqGrid('setRowData',ids[i],{"pkModeInt":"模式三"});
				}
			}
		}
	});
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	var intStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	$j("#sta-list").jqGrid({
		datatype: "json",
		// ["ID","作业单号","状态","相关单据号","作业类型名称","淘宝店铺ID","物流服务商简称","创建时间","计划执行总件数","单批打印"],
		colNames: ["ID","status",i18("CODE"),i18("INTSTATUS"),i18("REFSLIPCODE"),i18("INTTYPE"),i18("SHOPID"),i18("LPCODE"),i18("CREATETIME"),i18("STVTOTAL")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "status", index: "status",hidden: true},
		           {name: "code", index: "code", width: 120, resizable: true},
		           {name: "intStatus", index: "status", width: 80, resizable: true,formatter:'select',editoptions:intStatus},
		           {name: "refSlipCode", index: "slipCode",width: 120, resizable: true},
		           {name: "intType", index: "type", width: 100, resizable: true,formatter:'select',editoptions:staType},
		           {name: "shopId", index: "shopId", width: 120, resizable: true},
		           {name: "lpcode", index: "lpcode", width: 100, resizable: true,formatter:'select',editoptions:trasportName},
	               {name:"createTime",index:"createTime",width:150,resizable:true},
	               {name: "stvTotal", index: "stvTotal", width: 100, resizable: true}
	               ],
		caption: i18("INT_LIST"),// 作业单列表
		rowNum:-1,
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
		sortorder: "asc",
		gridComplete : function(){
			loxia.initContext($j(this));
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	
	$j("#search").click(function(){
		var url = baseUrl + "/batchQuery.json";
		$j("#batch-list").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
	});
	
	$j("#back").click(function(){
		$j("#query-batch-list").removeClass("hidden");
		$j("#order-List").addClass("hidden");
	});
	
	$j("#export").click(function(){
		var iframe = document.createElement("iframe");
		iframe.src = loxia.getTimeUrl(baseUrl + "/warehouse/exportPickingList.do?comd.id="+$j("#pickingListId").text());
		iframe.style.display = "none";
		$j("#upload").append($j(iframe));
	});
	
	$j("#save").click(function(){
		
		var file = $j("#file").val();
		var errors = [];
		if(file == ""){
			errors.push(i18("INPUT_FILE_ERROR"));//请选择正确的Excel导入文件
		}else{
			var postfix = file.split(".")[1];
			if(postfix != "xls" && postfix != "xlsx"){
				errors.push(i18("INPUT_FILE_ERROR"));//请选择正确的Excel导入文件
			}
		}
		if(errors.length > 0){
			jumbo.showMsg(errors.join("<br />&nbsp; &nbsp; &nbsp;"));
			return false;
		}
		loxia.lockPage();
		$j("#importForm").attr("action",loxia.getTimeUrl(baseUrl + "/warehouse/importRefreshPickingList.do?comd.id="+$j("#pickingListId").text()));
		$j("#importForm").submit();
	});
});

function showMsg(msg){
	jumbo.showMsg(msg);
}

function setInit(){
	
}
